/**
 * 通用 Java 代码生成器
 */

import { ParsedType, JavaTypeKind } from '../parsers/types';
import { TemplateEngine } from './TemplateEngine';
import { AnnotationBuilder } from './AnnotationBuilder';
import { generateJavadoc } from '../utils/JavadocConverter';
import { toUpperSnakeCase } from '../utils/JavaNaming';

export class JavaGenerator {
  private templateEngine: TemplateEngine;
  private annotationBuilder: AnnotationBuilder;

  constructor() {
    this.templateEngine = new TemplateEngine();
    this.annotationBuilder = new AnnotationBuilder();
  }

  /**
   * 生成 Java 代码（通用方法）
   * @param parsedType - 解析后的类型信息
   * @returns 生成的 Java 代码字符串
   */
  generate(parsedType: ParsedType): string {
    switch (parsedType.kind) {
      case JavaTypeKind.RECORD:
        return this.generateRecord(parsedType);

      case JavaTypeKind.REGULAR_CLASS:
        return this.generateClass(parsedType);

      case JavaTypeKind.SEALED_INTERFACE:
        return this.generateSealedInterface(parsedType);

      case JavaTypeKind.ENUM:
        return this.generateEnum(parsedType);

      default:
        throw new Error(`不支持的类型种类: ${parsedType.kind}`);
    }
  }

  /**
   * 生成 Java record
   */
  private generateRecord(type: ParsedType): string {
    // 处理属性注解，为 value wrapper 添加 @JsonValue
    const properties = type.properties?.map(prop => {
      const annotations = this.annotationBuilder.buildPropertyAnnotations(prop);

      // 如果这是原始值包装类，且属性名是 value，添加 @JsonValue
      if (type.isValueWrapper && prop.javaName === 'value') {
        annotations.unshift('@JsonValue');
      }

      return {
        name: prop.javaName,
        type: prop.type,
        annotations,
        javadoc: prop.description ? generateJavadoc(prop.description, undefined, '') : undefined,
      };
    }) || [];

    // 如果是原始值包装类，生成 @JsonCreator 静态工厂方法
    let jsonCreatorMethod = undefined;
    if (type.isValueWrapper && type.properties && type.properties.length === 1) {
      const prop = type.properties[0];
      jsonCreatorMethod = `@JsonCreator
    public static ${type.javaClassName} of(${prop.type} value) {
        return new ${type.javaClassName}(value);
    }`;
    }

    const data = {
      package: type.javaPackage,
      className: type.javaClassName,
      javadoc: generateJavadoc(type.description, undefined, ''),
      annotations: this.annotationBuilder.buildClassAnnotations(type),
      imports: this.annotationBuilder.generateImports(type),
      properties,
      nestedTypes: this.prepareNestedTypes(type.nestedTypes),
      jsonCreatorMethod,  // 传递给模板
    };

    return this.templateEngine.render('Record.hbs', data);
  }

  /**
   * 生成 Java class（用于字段过多的情况）
   */
  private generateClass(type: ParsedType): string {
    const data = {
      package: type.javaPackage,
      className: type.javaClassName,
      javadoc: generateJavadoc(type.description, undefined, ''),
      annotations: this.annotationBuilder.buildClassAnnotations(type),
      imports: this.annotationBuilder.generateImports(type),
      properties: type.properties?.map(prop => ({
        name: prop.javaName,
        type: prop.type,
        annotations: this.annotationBuilder.buildPropertyAnnotations(prop),
        javadoc: prop.description ? generateJavadoc(prop.description, undefined, '') : undefined,
      })) || [],
      nestedTypes: this.prepareNestedTypes(type.nestedTypes),
    };

    return this.templateEngine.render('Class.hbs', data);
  }

  /**
   * 生成 sealed interface
   */
  private generateSealedInterface(type: ParsedType): string {
    // 检查是否需要自定义 deserializer（有原始值 variant）
    const hasValueWrapper = type.oneOfVariants?.some(v => v.isValueWrapper);
    const needsCustomDeserializer = hasValueWrapper;

    // 构建注解
    let annotations = this.annotationBuilder.buildClassAnnotations(type);

    if (needsCustomDeserializer) {
      // 移除 @JsonTypeInfo(DEDUCTION)，因为我们使用自定义 deserializer
      annotations = annotations.filter(a => !a.includes('@JsonTypeInfo'));
      // 添加自定义 deserializer 注解
      annotations.push(`@JsonDeserialize(using = ${type.javaClassName}.Deserializer.class)`);
    }

    const data = {
      package: type.javaPackage,
      interfaceName: type.javaClassName,
      javadoc: generateJavadoc(type.description, undefined, ''),
      annotations,
      imports: this.annotationBuilder.generateImports(type),
      needsCustomDeserializer,
      variants: type.oneOfVariants?.map(variant => {
        // 处理属性注解，为 value wrapper 添加 @JsonValue
        const properties = variant.properties?.map(prop => {
          const annotations = this.annotationBuilder.buildPropertyAnnotations(prop);

          // 如果这是原始值包装类，且属性名是 value，添加 @JsonValue
          if (variant.isValueWrapper && prop.javaName === 'value') {
            annotations.unshift('@JsonValue');
          }

          return {
            name: prop.javaName,
            type: prop.type,
            annotations,
            javadoc: prop.description ? generateJavadoc(prop.description, undefined, '') : undefined,
          };
        }) || [];

        // 如果是原始值包装类，生成 @JsonCreator 静态工厂方法
        let jsonCreatorMethod = undefined;
        if (variant.isValueWrapper && variant.properties && variant.properties.length === 1) {
          const prop = variant.properties[0];
          jsonCreatorMethod = `@JsonCreator
        public static ${variant.javaClassName} of(${prop.type} value) {
            return new ${variant.javaClassName}(value);
        }`;
        }

        return {
          className: variant.javaClassName,
          annotations: this.annotationBuilder.buildClassAnnotations(variant),
          javadoc: variant.description ? generateJavadoc(variant.description, undefined, '') : undefined,
          properties,
          jsonCreatorMethod,
          isValueWrapper: variant.isValueWrapper,  // 传递给模板
          nestedTypes: this.prepareNestedTypes(variant.nestedTypes),  // 包含嵌套类型
        };
      }) || [],
    };

    return this.templateEngine.render('SealedInterface.hbs', data);
  }

  /**
   * 生成 Java enum
   */
  private generateEnum(type: ParsedType): string {
    const data = {
      package: type.javaPackage,
      enumName: type.javaClassName,
      javadoc: generateJavadoc(type.description, undefined, ''),
      values: type.enumValues?.map(value => ({
        javaName: toUpperSnakeCase(value),
        jsonValue: value,
      })) || [],
    };

    return this.templateEngine.render('Enum.hbs', data);
  }

  /**
   * 准备嵌套类型数据（递归）
   */
  private prepareNestedTypes(nestedTypes?: ParsedType[]): any[] | undefined {
    if (!nestedTypes || nestedTypes.length === 0) {
      return undefined;
    }

    return nestedTypes.map(nested => ({
      javaClassName: nested.javaClassName,
      javadoc: nested.description ? generateJavadoc(nested.description, undefined, '') : undefined,
      annotations: this.annotationBuilder.buildClassAnnotations(nested),
      isRecord: nested.kind === JavaTypeKind.RECORD,
      properties: nested.properties?.map(prop => ({
        name: prop.javaName,
        type: prop.type,
        annotations: this.annotationBuilder.buildPropertyAnnotations(prop),
        javadoc: prop.description ? generateJavadoc(prop.description, undefined, '') : undefined,
      })) || [],
      nestedTypes: this.prepareNestedTypes(nested.nestedTypes), // 递归处理
    }));
  }
}
