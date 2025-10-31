import * as fs from 'fs';
import * as path from 'path';

export interface VersionEntry {
  format_version: string;
  commit: string;
  notes: string;
}

export interface VersionMapping {
  modules: {
    [moduleName: string]: VersionEntry[];
  };
}

/**
 * 加载版本映射配置
 */
export function loadVersionMapping(): VersionMapping {
  const configPath = path.resolve(__dirname, '../../version-mapping.json');

  if (!fs.existsSync(configPath)) {
    throw new Error(`版本映射配置文件不存在: ${configPath}`);
  }

  const content = fs.readFileSync(configPath, 'utf-8');
  return JSON.parse(content) as VersionMapping;
}

/**
 * 获取指定模块的所有版本
 */
export function getModuleVersions(module: string): VersionEntry[] {
  const mapping = loadVersionMapping();
  return mapping.modules[module] || [];
}

/**
 * 获取指定模块和 format_version 的配置
 */
export function getVersionEntry(module: string, formatVersion: string): VersionEntry | null {
  const versions = getModuleVersions(module);
  return versions.find(v => v.format_version === formatVersion) || null;
}

/**
 * 列出所有需要生成的版本
 */
export function listAllVersions(): Array<{
  module: string;
  version: VersionEntry;
}> {
  const mapping = loadVersionMapping();
  const result: Array<{ module: string; version: VersionEntry }> = [];

  for (const [moduleName, versions] of Object.entries(mapping.modules)) {
    for (const version of versions) {
      result.push({ module: moduleName, version });
    }
  }

  return result;
}
