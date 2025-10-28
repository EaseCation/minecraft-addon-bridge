/**
 * 公共类型检测器
 * 识别跨模块的相同类型定义，提取为公共类型
 */

import { createHash } from 'crypto';
import { TypeInfo, TypeFingerprint, JavaTypeKind } from '../parsers/types';

interface TypeOccurrence {
  module: string;
  type: TypeInfo;
  fingerprint: TypeFingerprint;
}

export class CommonTypeDetector {
  private typeFingerprints = new Map<string, TypeOccurrence[]>();

  /**
   * 注册类型到全局索引
   */
  register(module: string, type: TypeInfo): void {
    const fingerprint = this.computeFingerprint(type);
    const existing = this.typeFingerprints.get(fingerprint.hash) || [];
    existing.push({ module, type, fingerprint });
    this.typeFingerprints.set(fingerprint.hash, existing);
  }

  /**
   * 识别公共类型（仅在当前版本内）
   * @returns 应该提取到版本内 common/ 的类型列表
   */
  detectCommonTypes(): TypeInfo[] {
    const commonTypes: TypeInfo[] = [];

    for (const [hash, occurrences] of this.typeFingerprints) {
      // 如果同一类型出现在当前版本的 2+ 个模块中，提取为公共类型
      if (occurrences.length >= 2) {
        const uniqueModules = new Set(occurrences.map(o => o.module));
        if (uniqueModules.size >= 2) {
          // 使用第一个出现的类型作为公共类型的基础
          const baseType = occurrences[0].type;

          // 标记为公共类型
          const commonType: TypeInfo = {
            ...baseType,
            isCommon: true,
            module: 'common',  // 标记为 common 模块
          };

          commonTypes.push(commonType);

          console.log(
            `  🔗 公共类型: ${baseType.javaClassName} ` +
            `(出现在 ${Array.from(uniqueModules).join(', ')} 中)`
          );
        }
      }
    }

    return commonTypes;
  }

  /**
   * 计算类型指纹
   */
  private computeFingerprint(type: TypeInfo): TypeFingerprint {
    // 规范化类型信息
    const normalized: any = {
      kind: type.javaType,
    };

    // 根据类型种类提取关键信息
    switch (type.javaType) {
      case JavaTypeKind.RECORD:
      case JavaTypeKind.REGULAR_CLASS:
        // 提取属性信息
        if (type.schema.properties) {
          normalized.properties = Object.keys(type.schema.properties)
            .sort()
            .map(key => ({
              name: key,
              type: this.normalizeType(type.schema.properties![key]),
              required: type.schema.required?.includes(key) || false,
            }));
        }
        break;

      case JavaTypeKind.ENUM:
        // 提取枚举值
        if (type.schema.enum) {
          normalized.enum = [...type.schema.enum].sort();
        }
        break;

      case JavaTypeKind.SEALED_INTERFACE:
      case JavaTypeKind.SEALED_CLASS:
        // 提取 oneOf/anyOf 变体
        const variants = type.schema.oneOf || type.schema.anyOf;
        if (variants) {
          normalized.variants = variants.map(v => this.normalizeType(v));
        }
        break;
    }

    // 计算 SHA256 哈希
    const hash = createHash('sha256')
      .update(JSON.stringify(normalized))
      .digest('hex');

    return {
      kind: type.javaType,
      properties: normalized.properties || [],
      hash,
    };
  }

  /**
   * 规范化类型引用
   */
  private normalizeType(schema: any): string {
    if (!schema) {
      return 'unknown';
    }

    // 基本类型
    if (schema.type) {
      if (Array.isArray(schema.type)) {
        return schema.type.sort().join('|');
      }
      return schema.type;
    }

    // $ref 引用
    if (schema.$ref) {
      // 提取最后一个路径段作为类型名
      const parts = schema.$ref.split('/');
      return parts[parts.length - 1];
    }

    // oneOf/anyOf
    if (schema.oneOf || schema.anyOf) {
      return 'union';
    }

    // allOf
    if (schema.allOf) {
      return 'intersection';
    }

    return 'object';
  }

  /**
   * 清空检测器
   */
  clear(): void {
    this.typeFingerprints.clear();
  }

  /**
   * 获取统计信息
   */
  getStats(): { totalTypes: number; commonTypes: number } {
    let totalTypes = 0;
    let commonTypes = 0;

    for (const occurrences of this.typeFingerprints.values()) {
      totalTypes += occurrences.length;
      if (occurrences.length >= 2) {
        const uniqueModules = new Set(occurrences.map(o => o.module));
        if (uniqueModules.size >= 2) {
          commonTypes++;
        }
      }
    }

    return { totalTypes, commonTypes };
  }
}
