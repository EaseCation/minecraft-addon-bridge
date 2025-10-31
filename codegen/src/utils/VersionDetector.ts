import * as fs from 'fs';
import * as path from 'path';

/**
 * 从 schema JSON 文件中提取 format_version 的默认值
 */
export function extractFormatVersionFromSchema(schemaPath: string): string | null {
  try {
    if (!fs.existsSync(schemaPath)) {
      return null;
    }

    const content = fs.readFileSync(schemaPath, 'utf-8');
    const schema = JSON.parse(content);

    // 查找 definitions.A.default（通常是 format_version 的定义）
    const formatVersionDef = schema.definitions?.A;
    if (formatVersionDef && formatVersionDef.default) {
      return formatVersionDef.default;
    }

    return null;
  } catch (error) {
    console.error(`Failed to extract format_version from ${schemaPath}:`, error);
    return null;
  }
}

/**
 * 自动检测当前 schema 目录的版本号
 * @param schemaDir schema 根目录路径（如 .../behavior）
 * @returns 检测到的 format_version
 */
export function detectVersionFromSchema(schemaDir: string): string {
  // 优先尝试从 blocks.json 提取
  const blocksPath = path.join(schemaDir, 'blocks/blocks.json');
  if (fs.existsSync(blocksPath)) {
    const version = extractFormatVersionFromSchema(blocksPath);
    if (version) {
      console.log(`✓ 从 blocks.json 检测到 format_version: ${version}`);
      return version;
    }
  }

  // 备用：从 items.json 提取
  const itemsPath = path.join(schemaDir, 'items/items.json');
  if (fs.existsSync(itemsPath)) {
    const version = extractFormatVersionFromSchema(itemsPath);
    if (version) {
      console.log(`✓ 从 items.json 检测到 format_version: ${version}`);
      return version;
    }
  }

  console.warn('⚠️ 无法从 schema 提取版本，使用默认值');

  // 默认值
  return '1.21.60';
}
