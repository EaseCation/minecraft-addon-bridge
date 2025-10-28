/**
 * Java 命名转换工具
 */

/**
 * 将字符串转换为 PascalCase
 * 例如: "loot_tables.json" -> "LootTables"
 */
export function toPascalCase(str: string): string {
  // 移除文件扩展名
  str = str.replace(/\.(json|ts|js)$/, '');

  // 将下划线、连字符、点号分隔的单词转换为 PascalCase
  return str
    .split(/[_\-.\s]+/)
    .map(word => word.charAt(0).toUpperCase() + word.slice(1).toLowerCase())
    .join('');
}

/**
 * 将字符串转换为 camelCase
 * 例如: "format_version" -> "formatVersion"
 */
export function toCamelCase(str: string): string {
  const pascal = toPascalCase(str);
  return pascal.charAt(0).toLowerCase() + pascal.slice(1);
}

/**
 * 将字符串转换为 UPPER_SNAKE_CASE
 * 例如: "double_sided" -> "DOUBLE_SIDED"
 */
export function toUpperSnakeCase(str: string): string {
  // 处理特殊运算符和符号 - 每个符号使用唯一名称
  const operatorMap: Record<string, string> = {
    '>=': 'GREATER_THAN_OR_EQUAL',
    '<=': 'LESS_THAN_OR_EQUAL',
    '!=': 'NOT_EQUAL',
    '<>': 'NOT_EQUAL_ALT',
    '==': 'EQUAL_EQUAL',
    '>': 'GREATER_THAN',
    '<': 'LESS_THAN',
    '=': 'EQUAL',
    'equals': 'EQUALS',
    'not': 'NOT',
    '+': 'PLUS',
    '-': 'MINUS',
    '*': 'MULTIPLY',
    '/': 'DIVIDE',
    '%': 'MODULO',
    '&': 'AND',
    '|': 'OR',
    '!': 'NOT_OPERATOR',
    '^': 'XOR',
    '~': 'TILDE',
    '?': 'QUESTION',
    ':': 'COLON',
    '.': 'DOT',
    ',': 'COMMA',
    ';': 'SEMICOLON',
    '@': 'AT',
    '#': 'HASH',
    '$': 'DOLLAR',
  };

  // 检查是否是纯运算符
  if (operatorMap[str]) {
    return operatorMap[str];
  }

  // 处理已经是 snake_case 的情况
  if (str.includes('_')) {
    return str.toUpperCase();
  }

  // 处理 camelCase 或 PascalCase
  return str
    .replace(/([A-Z])/g, '_$1')
    .replace(/^_/, '')
    .toUpperCase();
}

/**
 * 清理并规范化标识符
 * 移除非法字符，处理 Java 保留字
 */
export function sanitizeIdentifier(str: string): string {
  // 移除非法字符（只保留字母、数字、下划线）
  let cleaned = str.replace(/[^a-zA-Z0-9_]/g, '_');

  // 如果以数字开头，添加下划线前缀
  if (/^\d/.test(cleaned)) {
    cleaned = '_' + cleaned;
  }

  // 处理 Java 保留字
  if (isJavaKeyword(cleaned)) {
    cleaned = cleaned + '_';
  }

  return cleaned;
}

/**
 * 检查是否是 Java 保留字
 */
function isJavaKeyword(str: string): boolean {
  const javaKeywords = new Set([
    'abstract', 'assert', 'boolean', 'break', 'byte', 'case', 'catch', 'char',
    'class', 'const', 'continue', 'default', 'do', 'double', 'else', 'enum',
    'extends', 'final', 'finally', 'float', 'for', 'goto', 'if', 'implements',
    'import', 'instanceof', 'int', 'interface', 'long', 'native', 'new',
    'package', 'private', 'protected', 'public', 'return', 'short', 'static',
    'strictfp', 'super', 'switch', 'synchronized', 'this', 'throw', 'throws',
    'transient', 'try', 'void', 'volatile', 'while', 'true', 'false', 'null',
    // Java 9+ 保留字
    'var', 'yield', 'record', 'sealed', 'permits', 'non-sealed'
  ]);

  return javaKeywords.has(str.toLowerCase());
}

/**
 * 从文件名生成 Java 类名
 * 例如: "blocks.json" -> "BlockDefinition"
 */
export function fileNameToClassName(fileName: string, suffix: string = 'Definition'): string {
  const baseName = toPascalCase(fileName);

  // 如果已经以常见后缀结尾，不再添加
  const commonSuffixes = ['Definition', 'Config', 'Schema', 'Data'];
  const hasCommonSuffix = commonSuffixes.some(s => baseName.endsWith(s));

  if (hasCommonSuffix) {
    return baseName;
  }

  return baseName + suffix;
}

/**
 * 从 schema 路径提取模块名
 * 例如: "behavior/blocks/blocks.json" -> "blocks"
 */
export function extractModuleName(filePath: string): string {
  const parts = filePath.split('/').filter(p => p && p !== '.' && p !== '..');

  // 找到 behavior 或 resource 后的第一个目录
  const behaviorIndex = parts.indexOf('behavior');
  const resourceIndex = parts.indexOf('resource');
  const baseIndex = Math.max(behaviorIndex, resourceIndex);

  if (baseIndex >= 0 && baseIndex < parts.length - 1) {
    return parts[baseIndex + 1];
  }

  // 如果没有找到，返回文件所在目录名
  if (parts.length >= 2) {
    return parts[parts.length - 2];
  }

  return 'unknown';
}

/**
 * 生成 Java 包名
 * @param schemaPath - Schema 文件路径
 * @param version - 版本号，如 "1.21.60"
 * @param basePackage - 基础包名
 */
export function getJavaPackage(
  schemaPath: string,
  version: string,
  basePackage: string = 'net.easecation.bridge.core.dto'
): string {
  const versionSegment = `v${version.replace(/\./g, '_')}`;

  // 提取相对路径（从 behavior 或 resource 开始）
  const parts = schemaPath.split('/').filter(p => p && p !== '.' && p !== '..');
  const behaviorIndex = parts.indexOf('behavior');
  const resourceIndex = parts.indexOf('resource');
  const baseIndex = Math.max(behaviorIndex, resourceIndex);

  if (baseIndex < 0) {
    // 如果没有找到 behavior 或 resource，使用默认路径
    return `${basePackage}.${versionSegment}`;
  }

  // 提取从 behavior/resource 到文件所在目录的路径
  const relativeParts = parts.slice(baseIndex, parts.length - 1);
  const pathSegment = relativeParts.join('.');

  return `${basePackage}.${versionSegment}.${pathSegment}`;
}
