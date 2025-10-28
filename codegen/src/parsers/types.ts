/**
 * 核心类型定义
 */

import { JSONSchema7 } from 'json-schema';

/**
 * Java 类型种类
 */
export enum JavaTypeKind {
  RECORD = 'record',
  SEALED_INTERFACE = 'sealed_interface',
  SEALED_CLASS = 'sealed_class',
  ENUM = 'enum',
  REGULAR_CLASS = 'class',
}

/**
 * Java 注解
 */
export interface JavaAnnotation {
  name: string;
  parameters?: Record<string, string | number | boolean>;
}

/**
 * 解析后的属性
 */
export interface ParsedProperty {
  name: string;                 // JSON 字段名
  javaName: string;             // Java 字段名（驼峰）
  type: string;                 // Java 类型（含泛型）
  required: boolean;
  description?: string;
  defaultValue?: string;
  annotations: JavaAnnotation[];
  inlineType?: ParsedType;      // 如果是内联对象，存储其类型定义
}

/**
 * 解析后的类型
 */
export interface ParsedType {
  name: string;
  kind: JavaTypeKind;
  description?: string;
  properties?: ParsedProperty[];
  oneOfVariants?: ParsedType[];
  enumValues?: string[];
  annotations: JavaAnnotation[];
  javaPackage?: string;
  javaClassName?: string;
  nestedTypes?: ParsedType[];  // 内部类/接口列表
  isValueWrapper?: boolean;     // 是否是原始值包装类（需要 @JsonValue）
}

/**
 * 类型信息（注册表中的条目）
 */
export interface TypeInfo {
  schemaPath: string;           // 原始 schema 文件路径
  schemaPointer: string;        // JSON Pointer（如 "#/definitions/Block"）
  javaPackage: string;          // 生成的 Java 包名
  javaClassName: string;        // Java 类名
  javaType: JavaTypeKind;       // record | sealed | enum | class
  dependencies: Set<string>;    // 依赖的其他类型
  schema: JSONSchema7;          // 原始 Schema 定义
  module?: string;              // 所属模块（blocks, items, entities...）
  isCommon?: boolean;           // 是否是公共类型
}

/**
 * 解析器上下文
 */
export interface ParserContext {
  filePath: string;
  module: string;
  version: string;
  currentPath?: string[];
  typeRegistry?: any;  // TypeRegistry 实例
}

/**
 * 代码生成选项
 */
export interface GenerateOptions {
  version?: string;        // 版本号，如 "1.21.60"
  outputDir?: string;      // 输出目录
  debug?: boolean;         // 调试模式
  dryRun?: boolean;        // 干运行
  clean?: boolean;         // 清理已有文件
  schemaDir?: string;      // Schema 目录
}

/**
 * 类型指纹（用于公共类型检测）
 */
export interface TypeFingerprint {
  kind: JavaTypeKind;
  properties: Array<{
    name: string;
    type: string;
    required: boolean;
  }>;
  hash: string;  // SHA256 hash
}
