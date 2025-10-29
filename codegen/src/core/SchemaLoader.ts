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
   * 混合策略：保留外部引用，展开内部引用
   * @param schemaPath - Schema 文件的绝对路径或相对路径
   * @returns 处理后的 Schema 对象
   */
  async loadSchema(schemaPath: string): Promise<JSONSchema7> {
    // 检查缓存
    const absolutePath = path.resolve(schemaPath);
    if (this.schemaCache.has(absolutePath)) {
      return this.schemaCache.get(absolutePath)!;
    }

    try {
      // 使用 bundle() 保留外部文件引用，但合并所有文件到 definitions
      const schema = await $RefParser.bundle(absolutePath) as JSONSchema7;

      // 不展开内部引用！让 TypeResolver 通过 TypeRegistry 来解析
      // bundle() 已经将所有引用（包括外部文件）都合并到 definitions 中
      // 并且都是内部引用 (#/definitions/X) 的形式
      // TypeResolver 会通过 TypeRegistry.resolveDefinitionRef() 来解析这些引用
      const processedSchema = schema; // 直接使用 bundle 后的 schema

      // 缓存结果
      this.schemaCache.set(absolutePath, processedSchema);

      return processedSchema;
    } catch (error) {
      throw new Error(`无法加载 Schema 文件 ${schemaPath}: ${error}`);
    }
  }

  /**
   * 递归解析内部引用 (#/definitions/X)，保留外部引用
   * @param obj - 要处理的对象
   * @param definitions - definitions 映射表
   * @param visited - 已访问的引用，防止循环引用
   * @returns 处理后的对象
   */
  private resolveInternalRefs(
    obj: any,
    definitions?: Record<string, any>,
    visited: Set<string> = new Set()
  ): any {
    // 提取 definitions（第一次调用时）
    if (definitions === undefined && obj && typeof obj === 'object' && obj.definitions) {
      definitions = obj.definitions;
      // 先递归处理 definitions 本身
      obj.definitions = this.resolveInternalRefsInObject(obj.definitions, definitions, visited);
    }

    // 处理根对象
    return this.resolveInternalRefsInObject(obj, definitions, visited);
  }

  private resolveInternalRefsInObject(
    obj: any,
    definitions?: Record<string, any>,
    visited: Set<string> = new Set()
  ): any {
    if (typeof obj !== 'object' || obj === null) {
      return obj;
    }

    // 处理数组
    if (Array.isArray(obj)) {
      return obj.map(item => this.resolveInternalRefsInObject(item, definitions, visited));
    }

    // 处理 $ref
    if (obj.$ref && typeof obj.$ref === 'string') {
      // 只解析内部引用 (#/definitions/X 或 #/X)
      if (obj.$ref.startsWith('#/definitions/') || obj.$ref.startsWith('#/$defs/')) {
        const refPath = obj.$ref.substring(1); // 移除开头的 #
        const refKey = obj.$ref;

        // 防止循环引用
        if (visited.has(refKey)) {
          console.warn(`检测到循环引用: ${refKey}`);
          return obj; // 保留原引用
        }

        visited.add(refKey);

        // 从 definitions 中提取
        const refName = obj.$ref.split('/').pop();
        if (definitions && refName && definitions[refName]) {
          // 递归解析引用的内容
          const resolved = this.resolveInternalRefsInObject(
            definitions[refName],
            definitions,
            new Set(visited) // 创建新的 visited 副本
          );
          visited.delete(refKey);
          return resolved;
        }

        visited.delete(refKey);
      }

      // 外部引用保留不变
      return obj;
    }

    // 递归处理对象的所有属性
    const result: any = {};
    for (const key in obj) {
      if (obj.hasOwnProperty(key)) {
        result[key] = this.resolveInternalRefsInObject(obj[key], definitions, visited);
      }
    }

    return result;
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
