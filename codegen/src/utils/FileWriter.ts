/**
 * 文件写入工具
 */

import * as fs from 'fs';
import * as path from 'path';

export class FileWriter {
  constructor(private baseOutputDir: string) {}

  /**
   * 写入 Java 文件
   * @param javaPackage - Java 包名
   * @param className - 类名
   * @param content - Java 代码内容
   */
  write(javaPackage: string, className: string, content: string): void {
    const packagePath = javaPackage.replace(/\./g, path.sep);
    const fullDir = path.join(this.baseOutputDir, packagePath);
    const filePath = path.join(fullDir, `${className}.java`);

    // 创建目录
    fs.mkdirSync(fullDir, { recursive: true });

    // 写入文件
    fs.writeFileSync(filePath, content, 'utf-8');

    console.log(`  ✓ 生成: ${filePath}`);
  }

  /**
   * 清理已生成的文件
   * @param version - 版本号（必需）
   *
   * 注意：只清理指定版本的 DTO 目录，不清理整个输出目录，以保护手写的代码
   */
  clean(version: string): void {
    const versionSegment = `v${version.replace(/\./g, '_')}`;
    // 只清理 DTO 包目录，不清理整个 src/main/java
    const dtoDir = path.join(this.baseOutputDir, 'net', 'easecation', 'bridge', 'core', 'dto', versionSegment);

    if (fs.existsSync(dtoDir)) {
      fs.rmSync(dtoDir, { recursive: true, force: true });
      console.log(`✓ 已清理 DTO 目录: ${dtoDir}`);
    } else {
      console.log(`⚠️  DTO 目录不存在: ${dtoDir}`);
    }
  }

  /**
   * 检查文件是否存在
   */
  exists(javaPackage: string, className: string): boolean {
    const packagePath = javaPackage.replace(/\./g, path.sep);
    const filePath = path.join(this.baseOutputDir, packagePath, `${className}.java`);
    return fs.existsSync(filePath);
  }

  /**
   * 读取文件内容
   */
  read(javaPackage: string, className: string): string | null {
    const packagePath = javaPackage.replace(/\./g, path.sep);
    const filePath = path.join(this.baseOutputDir, packagePath, `${className}.java`);

    if (!fs.existsSync(filePath)) {
      return null;
    }

    return fs.readFileSync(filePath, 'utf-8');
  }
}
