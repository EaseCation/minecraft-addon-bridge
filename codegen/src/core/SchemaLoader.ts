/**
 * JSON Schema 加载器
 * 负责加载和解引用 JSON Schema 文件
 */

import * as $RefParser from '@apidevtools/json-schema-ref-parser';
import { JSONSchema7 } from 'json-schema';
import * as fs from 'fs';
import * as path from 'path';
import { glob } from 'glob';

export class SchemaLoader {
  private schemaCache: Map<string, JSONSchema7> = new Map();

  /**
   * 加载并解引用单个 Schema 文件
   * @param schemaPath - Schema 文件的绝对路径或相对路径
   * @returns 完全解引用的 Schema 对象
   */
  async loadSchema(schemaPath: string): Promise<JSONSchema7> {
    // 检查缓存
    const absolutePath = path.resolve(schemaPath);
    if (this.schemaCache.has(absolutePath)) {
      return this.schemaCache.get(absolutePath)!;
    }

    try {
      // 使用 bundle() 保留内部引用，只解引用外部文件
      // 这样可以保留 #/definitions/X 形式的引用，便于类型推断
      const schema = await $RefParser.bundle(absolutePath) as JSONSchema7;

      // 缓存结果
      this.schemaCache.set(absolutePath, schema);

      return schema;
    } catch (error) {
      throw new Error(`无法加载 Schema 文件 ${schemaPath}: ${error}`);
    }
  }

  /**
   * 批量加载目录下的所有 Schema 文件
   * @param dirPath - 目录路径
   * @param pattern - 文件匹配模式（默认 **\/*.json）
   * @returns Map<文件路径, Schema对象>
   */
  async loadDirectory(dirPath: string, pattern: string = '**/*.json'): Promise<Map<string, JSONSchema7>> {
    const absoluteDirPath = path.resolve(dirPath);

    if (!fs.existsSync(absoluteDirPath)) {
      throw new Error(`目录不存在: ${absoluteDirPath}`);
    }

    // 使用 glob 查找所有 JSON 文件
    const files = await glob(pattern, {
      cwd: absoluteDirPath,
      absolute: true,
      nodir: true,
    });

    console.log(`📂 在目录 ${absoluteDirPath} 中找到 ${files.length} 个 schema 文件`);

    const schemas = new Map<string, JSONSchema7>();

    // 并行加载所有 schema
    const loadPromises = files.map(async (file) => {
      try {
        const schema = await this.loadSchema(file);
        // 使用相对路径作为 key
        const relativePath = path.relative(absoluteDirPath, file);
        schemas.set(relativePath, schema);
        console.log(`  ✓ 加载: ${relativePath}`);
      } catch (error) {
        console.error(`  ✗ 加载失败: ${file}`, error);
      }
    });

    await Promise.all(loadPromises);

    return schemas;
  }

  /**
   * 清除缓存
   */
  clearCache(): void {
    this.schemaCache.clear();
  }

  /**
   * 获取缓存大小
   */
  getCacheSize(): number {
    return this.schemaCache.size;
  }
}
