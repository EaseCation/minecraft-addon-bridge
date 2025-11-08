import chalk from 'chalk';
import { SchemaInfo } from './schema-loader';

export interface VersionInfo {
  version: string;
  schemas: SchemaInfo[];
}

export class VersionDetector {
  /**
   * 从 Schema 中提取版本信息
   */
  detectVersion(schema: any): string | null {
    // 尝试多种方式提取版本
    const version =
      this.extractFromTitle(schema) ||
      this.extractFromDescription(schema) ||
      this.extractFromPath(schema) ||
      this.extractFromFormatVersion(schema);

    return version;
  }

  /**
   * 从 title 字段提取版本（如 "v1.21.60"）
   */
  private extractFromTitle(schema: any): string | null {
    if (schema.title && typeof schema.title === 'string') {
      const match = schema.title.match(/v?(\d+\.\d+\.\d+)/i);
      if (match) {
        return this.normalizeVersion(match[1]);
      }
    }
    return null;
  }

  /**
   * 从 description 字段提取版本
   */
  private extractFromDescription(schema: any): string | null {
    if (schema.description && typeof schema.description === 'string') {
      const match = schema.description.match(/v?(\d+\.\d+\.\d+)/i);
      if (match) {
        return this.normalizeVersion(match[1]);
      }
    }
    return null;
  }

  /**
   * 从文件路径提取版本（如果路径包含版本信息）
   */
  private extractFromPath(schemaInfo: any): string | null {
    if (schemaInfo.filePath) {
      const match = schemaInfo.filePath.match(/v?(\d+\.\d+\.\d+)/i);
      if (match) {
        return this.normalizeVersion(match[1]);
      }
    }
    return null;
  }

  /**
   * 从 format_version 属性枚举中提取版本
   */
  private extractFromFormatVersion(schema: any): string | null {
    // 查找 properties.format_version
    if (schema.properties?.format_version) {
      const formatVersion = schema.properties.format_version;

      // 检查 enum 值
      if (formatVersion.enum && Array.isArray(formatVersion.enum)) {
        // 取最新的版本（通常是数组最后一个）
        const versions = formatVersion.enum
          .filter((v: any) => typeof v === 'string')
          .map((v: string) => this.normalizeVersion(v))
          .filter((v: string | null) => v !== null) as string[];

        if (versions.length > 0) {
          // 返回最新版本
          return this.getLatestVersion(versions);
        }
      }

      // 检查 const 值
      if (formatVersion.const && typeof formatVersion.const === 'string') {
        return this.normalizeVersion(formatVersion.const);
      }
    }

    return null;
  }

  /**
   * 标准化版本格式（确保是 x.y.z 格式）
   */
  private normalizeVersion(version: string): string {
    // 移除 'v' 前缀
    version = version.replace(/^v/i, '');

    // 确保是三段式版本号
    const parts = version.split('.');
    while (parts.length < 3) {
      parts.push('0');
    }

    return parts.slice(0, 3).join('.');
  }

  /**
   * 从版本列表中获取最新版本
   */
  private getLatestVersion(versions: string[]): string {
    return versions.sort((a, b) => {
      const aParts = a.split('.').map(Number);
      const bParts = b.split('.').map(Number);

      for (let i = 0; i < 3; i++) {
        if (aParts[i] !== bParts[i]) {
          return bParts[i] - aParts[i]; // 降序排列
        }
      }
      return 0;
    })[0];
  }

  /**
   * 按模块和版本分组 Schemas
   * @param schemas Schema 列表
   * @param explicitVersion 显式指定的版本，如果提供则使用此版本而不是自动检测
   */
  groupByModuleAndVersion(
    schemas: SchemaInfo[],
    explicitVersion?: string
  ): Map<string, Map<string, SchemaInfo[]>> {
    const grouped = new Map<string, Map<string, SchemaInfo[]>>();

    for (const schemaInfo of schemas) {
      const moduleKey = `${schemaInfo.category}/${schemaInfo.module}`;

      if (!grouped.has(moduleKey)) {
        grouped.set(moduleKey, new Map());
      }

      const moduleGroup = grouped.get(moduleKey)!;

      // 使用显式版本或自动检测
      let version: string | undefined = explicitVersion;
      if (!version) {
        version = this.detectVersion(schemaInfo.schema) || undefined;
        if (!version && schemaInfo.dereferencedSchema) {
          version = this.detectVersion(schemaInfo.dereferencedSchema) || undefined;
        }
      }

      // 如果仍然无法检测，使用默认版本
      if (!version) {
        version = 'latest';
        if (!explicitVersion) {
          console.log(
            chalk.yellow(`⚠ 无法检测版本，使用默认版本 'latest': ${schemaInfo.relativePath}`)
          );
        }
      }

      const versionKey = `v${version.replace(/\./g, '_')}`;

      if (!moduleGroup.has(versionKey)) {
        moduleGroup.set(versionKey, []);
      }

      moduleGroup.get(versionKey)!.push(schemaInfo);
    }

    return grouped;
  }

  /**
   * 打印版本统计信息
   */
  printVersionStats(groupedSchemas: Map<string, Map<string, SchemaInfo[]>>): void {
    console.log(chalk.blue('\n📊 版本统计:'));

    for (const [moduleKey, versions] of groupedSchemas.entries()) {
      console.log(chalk.cyan(`\n  ${moduleKey}:`));

      for (const [version, schemas] of versions.entries()) {
        console.log(chalk.gray(`    ${version}: ${schemas.length} 个 Schema`));
      }
    }
  }
}
