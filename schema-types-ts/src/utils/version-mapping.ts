import * as fs from 'fs-extra';
import * as path from 'path';
import chalk from 'chalk';

export interface VersionEntry {
  format_version: string;
  commit: string;
  notes?: string;
}

export interface VersionMapping {
  $comment?: string;
  description?: string;
  modules: {
    [moduleName: string]: VersionEntry[];
  };
  default_commit?: string;
  schema_repository?: string;
  schema_directory?: string;
}

/**
 * 加载版本映射配置
 */
export function loadVersionMapping(configPath?: string): VersionMapping {
  const defaultPath = path.resolve(__dirname, '../../version-mapping.json');
  const filePath = configPath || defaultPath;

  if (!fs.existsSync(filePath)) {
    throw new Error(`版本映射配置文件不存在: ${filePath}`);
  }

  try {
    const content = fs.readFileSync(filePath, 'utf-8');
    const mapping: VersionMapping = JSON.parse(content);

    // 验证配置格式
    if (!mapping.modules || typeof mapping.modules !== 'object') {
      throw new Error('配置文件格式错误: 缺少 modules 字段');
    }

    return mapping;
  } catch (error) {
    if (error instanceof SyntaxError) {
      throw new Error(`配置文件 JSON 格式错误: ${error.message}`);
    }
    throw error;
  }
}

/**
 * 获取指定模块的所有版本
 */
export function getVersionsForModule(
  mapping: VersionMapping,
  moduleName: string
): VersionEntry[] {
  const versions = mapping.modules[moduleName];

  if (!versions || !Array.isArray(versions)) {
    console.warn(chalk.yellow(`⚠ 模块 "${moduleName}" 未配置版本信息`));
    return [];
  }

  return versions;
}

/**
 * 获取所有已配置的模块名称
 */
export function getAllModuleNames(mapping: VersionMapping): string[] {
  return Object.keys(mapping.modules);
}

/**
 * 获取指定模块的指定版本
 */
export function getVersionEntry(
  mapping: VersionMapping,
  moduleName: string,
  formatVersion: string
): VersionEntry | null {
  const versions = getVersionsForModule(mapping, moduleName);
  return versions.find(v => v.format_version === formatVersion) || null;
}

/**
 * 检查版本是否存在
 */
export function hasVersion(
  mapping: VersionMapping,
  moduleName: string,
  formatVersion: string
): boolean {
  return getVersionEntry(mapping, moduleName, formatVersion) !== null;
}

/**
 * 获取模块的最新版本
 */
export function getLatestVersion(
  mapping: VersionMapping,
  moduleName: string
): VersionEntry | null {
  const versions = getVersionsForModule(mapping, moduleName);

  if (versions.length === 0) {
    return null;
  }

  // 返回最后一个版本（假设配置文件中版本按时间顺序排列）
  return versions[versions.length - 1];
}

/**
 * 打印版本映射统计信息
 */
export function printVersionMappingStats(mapping: VersionMapping): void {
  console.log(chalk.blue('\n📊 版本映射配置统计:\n'));

  const moduleNames = getAllModuleNames(mapping);

  for (const moduleName of moduleNames) {
    const versions = getVersionsForModule(mapping, moduleName);
    console.log(chalk.cyan(`  ${moduleName}: ${chalk.white(versions.length)} 个版本`));

    versions.forEach(v => {
      const shortCommit = v.commit.substring(0, 8);
      const notes = v.notes ? chalk.gray(` - ${v.notes}`) : '';
      console.log(chalk.gray(`    ${v.format_version} (${shortCommit})${notes}`));
    });
  }

  console.log();
}

/**
 * 验证版本配置的完整性
 */
export function validateVersionMapping(mapping: VersionMapping): boolean {
  const moduleNames = getAllModuleNames(mapping);

  if (moduleNames.length === 0) {
    console.error(chalk.red('❌ 没有配置任何模块'));
    return false;
  }

  let hasError = false;

  for (const moduleName of moduleNames) {
    const versions = getVersionsForModule(mapping, moduleName);

    if (versions.length === 0) {
      console.warn(chalk.yellow(`⚠ 模块 "${moduleName}" 没有配置任何版本`));
      continue;
    }

    // 检查每个版本的必需字段
    versions.forEach((v, index) => {
      if (!v.format_version) {
        console.error(chalk.red(`❌ ${moduleName}[${index}]: 缺少 format_version`));
        hasError = true;
      }

      if (!v.commit) {
        console.error(chalk.red(`❌ ${moduleName}[${index}]: 缺少 commit`));
        hasError = true;
      }

      // 检查 commit hash 格式（应该是 40 位十六进制）
      if (v.commit && !/^[a-f0-9]{40}$/i.test(v.commit)) {
        console.warn(
          chalk.yellow(
            `⚠ ${moduleName}[${index}]: commit hash 格式可能不正确 (${v.commit})`
          )
        );
      }
    });
  }

  if (hasError) {
    console.error(chalk.red('\n❌ 版本配置验证失败\n'));
    return false;
  }

  console.log(chalk.green('✓ 版本配置验证通过\n'));
  return true;
}
