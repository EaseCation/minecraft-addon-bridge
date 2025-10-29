/**
 * 类型解析器
 * 将 JSON Schema 类型映射到 Java 类型
 */

import { JSONSchema7, JSONSchema7TypeName } from 'json-schema';
import { JavaTypeKind } from './types';
import { TypeRegistry } from '../core/TypeRegistry';

export class TypeResolver {
  private typeRegistry: TypeRegistry;

  constructor(typeRegistry: TypeRegistry) {
    this.typeRegistry = typeRegistry;
  }

  /**
   * 解析 JSON Schema 类型到 Java 类型字符串
   */
  resolveType(schema: JSONSchema7, context?: { isRequired?: boolean; filePath?: string }): string {
    // 优先处理 $ref - 使用 bundle() 后会保留内部引用
    if (schema.$ref && context?.filePath) {
      // 尝试从 TypeRegistry 解析引用
      const className = this.typeRegistry.resolveDefinitionRef(context.filePath, schema.$ref);
      if (className) {
        return className;
      }

      // 如果找不到映射，回退到简单提取（向后兼容）
      const parts = schema.$ref.split('/');
      return parts[parts.length - 1];
    }

    // 处理 oneOf/anyOf（多态类型）
    if (schema.oneOf || schema.anyOf) {
      // 这些需要生成 sealed interface，此处返回占位符
      return 'Object';  // 实际类型将由 SchemaParser 生成
    }

    // 处理 allOf（通常是属性合并或继承）
    if (schema.allOf) {
      return 'Object';  // 实际类型将由 SchemaParser 处理
    }

    // 处理 enum
    if (schema.enum && schema.enum.length > 0) {
      return 'String';  // 默认枚举类型，实际将生成 Java enum
    }

    // 处理基本类型
    if (schema.type) {
      return this.resolvePrimitiveType(schema.type, schema, context);
    }

    // 处理 const
    if (schema.const !== undefined) {
      return this.resolveConstType(schema.const);
    }

    // 默认返回 Object
    return 'Object';
  }

  /**
   * 解析基本类型
   */
  private resolvePrimitiveType(
    type: JSONSchema7TypeName | JSONSchema7TypeName[],
    schema: JSONSchema7,
    context?: { isRequired?: boolean; filePath?: string }
  ): string {
    // 处理联合类型（如 ["string", "null"]）
    if (Array.isArray(type)) {
      // 过滤掉 null
      const nonNullTypes = type.filter(t => t !== 'null');
      if (nonNullTypes.length === 0) {
        return 'Object';
      }
      if (nonNullTypes.length === 1) {
        return this.resolveSingleType(nonNullTypes[0], schema, false, context);
      }
      // 多种非 null 类型，返回 Object
      return 'Object';
    }

    // 单一类型
    return this.resolveSingleType(type, schema, context?.isRequired ?? true, context);
  }

  /**
   * 解析单一类型
   */
  private resolveSingleType(
    type: JSONSchema7TypeName,
    schema: JSONSchema7,
    isRequired: boolean,
    context?: { isRequired?: boolean; filePath?: string }
  ): string {
    switch (type) {
      case 'string':
        return 'String';

      case 'integer':
        // 根据范围决定使用 Integer 还是 Long
        if (this.needsLongType(schema)) {
          return 'Long';
        }
        return 'Integer';

      case 'number':
        // 根据精度决定使用 Double 还是 Float
        if (schema.format === 'float') {
          return 'Float';
        }
        return 'Double';

      case 'boolean':
        return 'Boolean';

      case 'array':
        // 解析数组元素类型
        if (schema.items) {
          // 检查是否是 tuple 类型（items 是数组）
          if (Array.isArray(schema.items)) {
            // 分析所有元素的类型
            const types = schema.items.map(item =>
              this.resolveType(item as JSONSchema7, context)
            );

            // 如果所有类型相同，使用该类型
            const uniqueTypes = [...new Set(types)];
            if (uniqueTypes.length === 1) {
              return `List<${uniqueTypes[0]}>`;
            }

            // 否则回退到 Object（混合类型的 tuple）
            return 'List<Object>';
          }

          // 正常的数组（items 是单个 schema）
          const itemType = this.resolveType(schema.items as JSONSchema7, context);
          return `List<${itemType}>`;
        }
        return 'List<Object>';

      case 'object':
        // 对象类型需要生成新的类
        // 检查是否是空对象（没有 properties 且不允许额外属性）
        const hasProperties = schema.properties && Object.keys(schema.properties).length > 0;
        const hasAdditionalProps = schema.additionalProperties === true ||
                                   (typeof schema.additionalProperties === 'object' &&
                                    Object.keys(schema.additionalProperties).length > 0);
        const hasPatternProps = schema.patternProperties &&
                               Object.keys(schema.patternProperties).length > 0;

        if (!hasProperties && !hasAdditionalProps && !hasPatternProps) {
          // 空对象定义，使用全局 EmptyObject
          return 'net.easecation.bridge.core.dto.EmptyObject';
        }

        // 检查是否有 patternProperties 或 additionalProperties
        // additionalProperties 可以是 true 或者对象（如 {$ref: ...}）
        if (schema.patternProperties ||
            schema.additionalProperties === true ||
            (typeof schema.additionalProperties === 'object' && Object.keys(schema.additionalProperties).length > 0)) {
          // 使用 Map
          const valueType = this.resolveAdditionalPropertiesType(schema, context);
          return `Map<String, ${valueType}>`;
        }
        // 普通对象，需要生成新类
        return 'Object';  // 占位符，实际将生成新类

      case 'null':
        return 'Void';

      default:
        return 'Object';
    }
  }

  /**
   * 判断是否需要使用 Long 类型
   */
  private needsLongType(schema: JSONSchema7): boolean {
    if (schema.minimum !== undefined && Math.abs(schema.minimum) > 2147483647) {
      return true;
    }
    if (schema.maximum !== undefined && Math.abs(schema.maximum) > 2147483647) {
      return true;
    }
    return false;
  }

  /**
   * 解析 additionalProperties 的值类型
   * 公开此方法以便 SchemaParser 可以在注册 Map 类型定义时使用
   */
  public resolveAdditionalPropertiesType(schema: JSONSchema7, context?: { isRequired?: boolean; filePath?: string }): string {
    if (typeof schema.additionalProperties === 'object') {
      return this.resolveType(schema.additionalProperties, context);
    }
    if (schema.patternProperties) {
      // 取第一个 patternProperties 的类型
      const firstPattern = Object.values(schema.patternProperties)[0];
      if (firstPattern) {
        return this.resolveType(firstPattern as JSONSchema7, context);
      }
    }
    return 'Object';
  }

  /**
   * 解析 const 类型
   */
  private resolveConstType(value: any): string {
    if (typeof value === 'string') {
      return 'String';
    }
    if (typeof value === 'number') {
      return Number.isInteger(value) ? 'Integer' : 'Double';
    }
    if (typeof value === 'boolean') {
      return 'Boolean';
    }
    return 'Object';
  }

  /**
   * 判断 Schema 应该生成什么类型的 Java 类
   */
  determineJavaTypeKind(schema: JSONSchema7): JavaTypeKind {
    // enum -> Java enum
    if (schema.enum && schema.enum.length > 0) {
      return JavaTypeKind.ENUM;
    }

    // oneOf/anyOf -> sealed interface
    if (schema.oneOf || schema.anyOf) {
      return JavaTypeKind.SEALED_INTERFACE;
    }

    // allOf -> 看情况，如果是简单合并，生成 record；如果涉及继承，生成 class
    if (schema.allOf) {
      // 简化处理：默认生成 record
      return JavaTypeKind.RECORD;
    }

    // object with properties -> record
    if (schema.type === 'object' || schema.properties) {
      return JavaTypeKind.RECORD;
    }

    // 默认 record
    return JavaTypeKind.RECORD;
  }

  /**
   * 检查类型是否为可选（nullable）
   */
  isNullable(schema: JSONSchema7): boolean {
    if (Array.isArray(schema.type)) {
      return schema.type.includes('null');
    }
    return false;
  }
}
