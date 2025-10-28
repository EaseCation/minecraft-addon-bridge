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
    const data = {
      package: type.javaPackage,
      interfaceName: type.javaClassName,
      javadoc: generateJavadoc(type.description, undefined, ''),
      annotations: this.annotationBuilder.buildClassAnnotations(type),
      imports: this.annotationBuilder.generateImports(type),
      variants: type.oneOfVariants?.map(variant => ({
        className: variant.javaClassName,
        annotations: this.annotationBuilder.buildClassAnnotations(variant),
        javadoc: variant.description ? generateJavadoc(variant.description, undefined, '') : undefined,
        properties: variant.properties?.map(prop => ({
          name: prop.javaName,
          type: prop.type,
          annotations: this.annotationBuilder.buildPropertyAnnotations(prop),
          javadoc: prop.description ? generateJavadoc(prop.description, undefined, '') : undefined,
        })) || [],
      })) || [],
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
