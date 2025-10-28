/**
 * Jackson 注解构建器
 */

import { ParsedType, ParsedProperty, JavaAnnotation, JavaTypeKind } from '../parsers/types';

export class AnnotationBuilder {
  /**
   * 构建类级别的注解字符串列表
   */
  buildClassAnnotations(type: ParsedType): string[] {
    const annotations: string[] = [];

    for (const annotation of type.annotations) {
      annotations.push(this.formatAnnotation(annotation));
    }

    return annotations;
  }

  /**
   * 构建属性级别的注解字符串列表
   */
  buildPropertyAnnotations(property: ParsedProperty): string[] {
    const annotations: string[] = [];

    for (const annotation of property.annotations) {
      annotations.push(this.formatAnnotation(annotation));
    }

    return annotations;
  }

  /**
   * 格式化注解
   */
  private formatAnnotation(annotation: JavaAnnotation): string {
    if (!annotation.parameters || Object.keys(annotation.parameters).length === 0) {
      return annotation.name;
    }

    const params = Object.entries(annotation.parameters)
      .map(([key, value]) => {
        // 如果参数名是 value，直接返回值（可能已包含引号）
        if (key === 'value') {
          return String(value);
        }
        // Boolean 和 number 类型不需要引号
        if (typeof value === 'boolean' || typeof value === 'number') {
          return `${key} = ${value}`;
        }
        // 如果字符串已经包含引号或点号（枚举引用如 JsonTypeInfo.Id.DEDUCTION），直接使用
        if (typeof value === 'string' && (value.startsWith('"') || value.includes('.'))) {
          return `${key} = ${value}`;
        }
        // 其他字符串需要加引号
        return `${key} = "${value}"`;
      })
      .join(', ');

    return `${annotation.name}(${params})`;
  }

  /**
   * 生成导入语句
   */
  generateImports(type: ParsedType): string[] {
    const imports = new Set<string>();

    // Jackson 相关导入
    imports.add('import com.fasterxml.jackson.annotation.*;');

    // 如果是 sealed interface 且有 value wrapper variant，需要额外的导入
    if (type.kind === 'sealed_interface' && type.oneOfVariants?.some(v => v.isValueWrapper)) {
      imports.add('import com.fasterxml.jackson.databind.annotation.JsonDeserialize;');
      imports.add('import com.fasterxml.jackson.databind.JsonDeserializer;');
      imports.add('import com.fasterxml.jackson.databind.DeserializationContext;');
      imports.add('import com.fasterxml.jackson.core.JsonParser;');
      imports.add('import com.fasterxml.jackson.databind.JsonNode;');
      imports.add('import com.fasterxml.jackson.databind.JsonMappingException;');
      imports.add('import java.io.IOException;');
    }

    // 收集所有需要检查的属性（包括主类型、oneOf 变体和嵌套类型）
    const allProperties: ParsedProperty[] = [];

    // 递归收集所有属性的辅助函数
    const collectProperties = (t: ParsedType): void => {
      if (t.properties) {
        allProperties.push(...t.properties);
      }
      if (t.oneOfVariants) {
        for (const variant of t.oneOfVariants) {
          collectProperties(variant);
        }
      }
      if (t.nestedTypes) {
        for (const nested of t.nestedTypes) {
          collectProperties(nested);
        }
      }
    };

    collectProperties(type);

    // 如果有可选字段，导入 @Nullable
    if (allProperties.some(p => !p.required)) {
      imports.add('import javax.annotation.Nullable;');
    }

    // 如果有 List 类型，导入 java.util.List
    if (allProperties.some(p => p.type.includes('List<'))) {
      imports.add('import java.util.List;');
    }

    // 如果有 Map 类型，导入 java.util.Map
    if (allProperties.some(p => p.type.includes('Map<'))) {
      imports.add('import java.util.Map;');
    }

    return Array.from(imports).sort();
  }
}
