/**
 * Schema 解析器
 * 将 JSON Schema 解析为中间表示（IR）
 */

import { JSONSchema7 } from 'json-schema';
import { ParsedType, ParsedProperty, JavaAnnotation, ParserContext, JavaTypeKind } from './types';
import { TypeResolver } from './TypeResolver';
import { TypeRegistry } from '../core/TypeRegistry';
import { toCamelCase, toPascalCase, sanitizeIdentifier, getJavaPackage, fileNameToClassName, extractModuleName } from '../utils/JavaNaming';
import * as path from 'path';

export class SchemaParser {
  private typeResolver: TypeResolver;
  private typeRegistry: TypeRegistry;
  private version: string;

  constructor(typeRegistry: TypeRegistry, version: string) {
    this.typeResolver = new TypeResolver(typeRegistry);
    this.typeRegistry = typeRegistry;
    this.version = version;
  }

  /**
   * 解析 Schema 文件
   * @param schema - 已解引用的 Schema 对象
   * @param context - 解析上下文
   * @returns 解析后的类型列表
   */
  parse(schema: JSONSchema7, context: ParserContext): ParsedType[] {
    const results: ParsedType[] = [];

    // 提取文件名作为主类名和前缀
    const fileName = path.basename(context.filePath);
    const mainClassName = fileNameToClassName(fileName);
    // 提取模块名作为前缀（如 blocks, items, entities）
    const modulePrefix = toPascalCase(context.module || '');

    // 解析主定义
    const mainType = this.parseSchema(schema, mainClassName, context);
    if (mainType) {
      results.push(mainType);
    }

    // 解析 definitions（添加模块前缀避免命名冲突）
    if (schema.definitions) {
      for (const [defName, defSchema] of Object.entries(schema.definitions)) {
        // 优先使用 title，如果没有才使用 defName
        const titleOrName = (defSchema as JSONSchema7).title || defName;
        const defClassName = toPascalCase(titleOrName);
        // 为短名称（如 A, B, C）添加模块前缀
        const prefixedClassName = this.needsPrefix(defClassName)
          ? `${modulePrefix}${defClassName}`
          : defClassName;

        const defType = this.parseSchema(
          defSchema as JSONSchema7,
          prefixedClassName,
          {
            ...context,
            currentPath: [...(context.currentPath || []), 'definitions', defName],
          }
        );
        if (defType) {
          results.push(defType);

          // 注册 definition 引用映射，用于解析 $ref
          if (defType.javaClassName) {
            this.typeRegistry.registerDefinitionRef(
              context.filePath,
              defName,
              defType.javaClassName
            );
          }
        } else {
          // defType 为 null 表示这是一个空对象，注册映射到 EmptyObject
          this.typeRegistry.registerDefinitionRef(
            context.filePath,
            defName,
            'net.easecation.bridge.core.dto.EmptyObject'
          );
        }
      }
    }

    return results;
  }

  /**
   * 判断类名是否需要添加前缀
   * 短名称（2个字符以内）或全大写字母需要前缀
   */
  private needsPrefix(className: string): boolean {
    // 如果是短名称（如 A, B, C, Ba, Bb）
    if (className.length <= 2) {
      return true;
    }
    // 如果是全大写字母后跟小写字母的模式（如 BA, BB, CAa）
    if (/^[A-Z]{1,2}[a-z]?$/.test(className)) {
      return true;
    }
    return false;
  }

  /**
   * 解析单个 Schema 定义
   */
  private parseSchema(
    schema: JSONSchema7,
    suggestedName: string,
    context: ParserContext
  ): ParsedType | null {
    if (!schema || typeof schema !== 'object') {
      return null;
    }

    // 确定 Java 类型种类
    const kind = this.typeResolver.determineJavaTypeKind(schema);

    // 根据类型种类调用相应的解析方法
    switch (kind) {
      case JavaTypeKind.ENUM:
        return this.parseEnum(schema, suggestedName, context);

      case JavaTypeKind.SEALED_INTERFACE:
        return this.parseSealedInterface(schema, suggestedName, context);

      case JavaTypeKind.RECORD:
        return this.parseRecord(schema, suggestedName, context);

      default:
        return this.parseRecord(schema, suggestedName, context);
    }
  }

  /**
   * 解析枚举类型
   */
  private parseEnum(
    schema: JSONSchema7,
    name: string,
    context: ParserContext
  ): ParsedType {
    const enumValues = (schema.enum || []).map(v => String(v));

    return {
      name,
      kind: JavaTypeKind.ENUM,
      description: schema.description,
      enumValues,
      annotations: this.buildClassAnnotations(schema, JavaTypeKind.ENUM),
      javaPackage: getJavaPackage(context.filePath, this.version),
      javaClassName: sanitizeIdentifier(name),
    };
  }

  /**
   * 解析 sealed interface（oneOf/anyOf）
   */
  private parseSealedInterface(
    schema: JSONSchema7,
    name: string,
    context: ParserContext
  ): ParsedType {
    const variants = schema.oneOf || schema.anyOf || [];
    const oneOfVariants: ParsedType[] = [];
    const usedNames = new Set<string>();

    for (let i = 0; i < variants.length; i++) {
      const variant = variants[i] as JSONSchema7;
      let variantName = this.inferVariantName(variant, i);

      // 确保 variant 名称唯一
      let uniqueName = variantName;
      let suffix = 0;
      while (usedNames.has(uniqueName)) {
        uniqueName = `${variantName}${suffix++}`;
      }
      usedNames.add(uniqueName);

      const variantType = this.parseSchema(variant, `${name}_${uniqueName}`, context);
      if (variantType) {
        oneOfVariants.push(variantType);
      }
    }

    return {
      name,
      kind: JavaTypeKind.SEALED_INTERFACE,
      description: schema.description,
      oneOfVariants,
      annotations: this.buildClassAnnotations(schema, JavaTypeKind.SEALED_INTERFACE),
      javaPackage: getJavaPackage(context.filePath, this.version),
      javaClassName: sanitizeIdentifier(name),
    };
  }

  /**
   * 解析 record 类型
   */
  private parseRecord(
    schema: JSONSchema7,
    name: string,
    context: ParserContext
  ): ParsedType | null {
    // 处理 allOf（属性合并）
    let mergedSchema = schema;
    if (schema.allOf) {
      mergedSchema = this.mergeAllOf(schema);
    }

    // 解析属性
    let properties = this.parseProperties(mergedSchema, context);

    // 检查是否是空对象（type: object 但没有 properties 且不是基本类型）
    if (properties.length === 0 && mergedSchema.type === 'object') {
      const hasAdditionalProps = mergedSchema.additionalProperties === true ||
                                 (typeof mergedSchema.additionalProperties === 'object' &&
                                  Object.keys(mergedSchema.additionalProperties).length > 0);
      const hasPatternProps = mergedSchema.patternProperties &&
                             Object.keys(mergedSchema.patternProperties).length > 0;

      if (!hasAdditionalProps && !hasPatternProps) {
        // 这是一个空对象，不生成类型定义（使用全局 EmptyObject）
        return null;
      }
    }

    // 如果是基本类型（没有 properties），为它生成单字段 wrapper
    if (properties.length === 0 && (mergedSchema.type === 'string' || mergedSchema.type === 'boolean' ||
        mergedSchema.type === 'number' || mergedSchema.type === 'integer')) {
      const javaType = this.typeResolver.resolveType(mergedSchema, { filePath: context.filePath });
      properties = [{
        name: 'value',
        javaName: 'value',
        type: javaType,
        required: true,
        description: mergedSchema.description,
        annotations: []
      }];
    }

    // 决定使用 record 还是 class（Java record 最多支持 255 个参数）
    const kind = properties.length > 200 ? JavaTypeKind.REGULAR_CLASS : JavaTypeKind.RECORD;

    // 收集所有内联类型作为嵌套类型
    const nestedTypes: ParsedType[] = [];
    const sanitizedName = sanitizeIdentifier(name);

    for (const prop of properties) {
      if (prop.inlineType) {
        // 检查是否与父类型名称冲突
        if (prop.inlineType.javaClassName === sanitizedName) {
          // 添加后缀避免冲突
          prop.inlineType.javaClassName = `${sanitizedName}Data`;
          prop.type = prop.inlineType.javaClassName;
        }
        nestedTypes.push(prop.inlineType);
      }
    }

    return {
      name,
      kind,
      description: mergedSchema.description,
      properties,
      annotations: this.buildClassAnnotations(mergedSchema, kind),
      javaPackage: getJavaPackage(context.filePath, this.version),
      javaClassName: sanitizedName,
      nestedTypes: nestedTypes.length > 0 ? nestedTypes : undefined,
    };
  }

  /**
   * 解析属性列表
   */
  private parseProperties(
    schema: JSONSchema7,
    context: ParserContext
  ): ParsedProperty[] {
    if (!schema.properties) {
      return [];
    }

    const required = new Set(schema.required || []);
    const properties: ParsedProperty[] = [];

    for (const [propName, propSchema] of Object.entries(schema.properties)) {
      const prop = this.parseProperty(
        propName,
        propSchema as JSONSchema7,
        required.has(propName),
        context
      );
      properties.push(prop);
    }

    return properties;
  }

  /**
   * 解析单个属性
   */
  private parseProperty(
    name: string,
    schema: JSONSchema7,
    required: boolean,
    context: ParserContext
  ): ParsedProperty {
    const javaName = toCamelCase(name);

    // 检测内联对象定义（需要满足所有条件）
    // JSON Schema 允许省略 type: "object"，只要有 properties 就隐含为 object
    const isInlineObject = (schema.type === 'object' || !schema.type)
      && schema.properties
      && Object.keys(schema.properties).length > 0
      && !schema.$ref
      && !schema.additionalProperties  // 避免动态属性对象
      && !schema.patternProperties;    // 避免模式属性对象

    if (isInlineObject) {
      // 生成内联类型名称（使用属性名的 PascalCase，并 sanitize）
      const inlineTypeName = sanitizeIdentifier(toPascalCase(name));

      // 递归解析内联对象为嵌套类型
      const inlineType = this.parseRecord(schema, inlineTypeName, context);

      // 如果 parseRecord 返回 null（空对象），回退到 TypeResolver
      if (!inlineType) {
        const type = this.typeResolver.resolveType(schema, {
          isRequired: required,
          filePath: context.filePath
        });
        return {
          name,
          javaName: sanitizeIdentifier(javaName),
          type,
          required,
          description: schema.description,
          defaultValue: schema.default !== undefined ? String(schema.default) : undefined,
          annotations: this.buildPropertyAnnotations(name, schema, required),
        };
      }

      return {
        name,
        javaName: sanitizeIdentifier(javaName),
        type: inlineTypeName,  // 使用内部类名作为类型
        required,
        description: schema.description,
        defaultValue: schema.default !== undefined ? String(schema.default) : undefined,
        annotations: this.buildPropertyAnnotations(name, schema, required),
        inlineType,  // 保存内联类型定义
      };
    }

    // 正常情况：使用 TypeResolver
    const type = this.typeResolver.resolveType(schema, {
      isRequired: required,
      filePath: context.filePath
    });

    return {
      name,
      javaName: sanitizeIdentifier(javaName),
      type,
      required,
      description: schema.description,
      defaultValue: schema.default !== undefined ? String(schema.default) : undefined,
      annotations: this.buildPropertyAnnotations(name, schema, required),
    };
  }

  /**
   * 合并 allOf 中的所有 schema
   */
  private mergeAllOf(schema: JSONSchema7): JSONSchema7 {
    if (!schema.allOf) {
      return schema;
    }

    const merged: JSONSchema7 = {
      ...schema,
      properties: {},
      required: [],
    };

    for (const subSchema of schema.allOf) {
      const sub = subSchema as JSONSchema7;

      // 合并 properties
      if (sub.properties) {
        merged.properties = {
          ...merged.properties,
          ...sub.properties,
        };
      }

      // 合并 required
      if (sub.required) {
        merged.required = [...(merged.required || []), ...sub.required];
      }

      // 合并 description（取第一个非空的）
      if (!merged.description && sub.description) {
        merged.description = sub.description;
      }
    }

    return merged;
  }

  /**
   * 推断 oneOf/anyOf 变体的名称
   */
  private inferVariantName(schema: JSONSchema7, index: number): string {
    // 优先使用 title
    if (schema.title) {
      return toPascalCase(schema.title);
    }

    // 如果没有 title，为了避免命名冲突，直接使用索引
    // 因为多个 variant 可能有相同的 type（如都是 object）
    return `Variant${index}`;
  }

  /**
   * 构建类级别注解
   */
  private buildClassAnnotations(schema: JSONSchema7, kind: JavaTypeKind): JavaAnnotation[] {
    const annotations: JavaAnnotation[] = [];

    // 对于 record 和 sealed interface，添加 @JsonIgnoreProperties
    if (kind === JavaTypeKind.RECORD || kind === JavaTypeKind.SEALED_INTERFACE) {
      annotations.push({
        name: '@JsonIgnoreProperties',
        parameters: { ignoreUnknown: true },
      });
    }

    // 对于 sealed interface，添加 @JsonTypeInfo
    if (kind === JavaTypeKind.SEALED_INTERFACE) {
      annotations.push({
        name: '@JsonTypeInfo',
        parameters: {
          use: 'JsonTypeInfo.Id.DEDUCTION',
        },
      });
    }

    return annotations;
  }

  /**
   * 构建属性级别注解
   */
  private buildPropertyAnnotations(
    jsonName: string,
    schema: JSONSchema7,
    required: boolean
  ): JavaAnnotation[] {
    const annotations: JavaAnnotation[] = [];

    // 添加 @JsonProperty
    annotations.push({
      name: '@JsonProperty',
      parameters: { value: `"${jsonName}"` },
    });

    // 如果不是必需的，添加 @Nullable
    if (!required || this.typeResolver.isNullable(schema)) {
      annotations.push({
        name: '@Nullable',
      });
    }

    return annotations;
  }
}
