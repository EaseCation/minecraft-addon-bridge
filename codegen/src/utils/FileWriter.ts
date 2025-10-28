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
   * @param version - 版本号
   */
  clean(version?: string): void {
    if (version) {
      const versionSegment = `v${version.replace(/\./g, '_')}`;
      const versionDir = path.join(this.baseOutputDir, versionSegment);

      if (fs.existsSync(versionDir)) {
        fs.rmSync(versionDir, { recursive: true, force: true });
        console.log(`✓ 已清理版本目录: ${versionDir}`);
      }
    } else {
      if (fs.existsSync(this.baseOutputDir)) {
        fs.rmSync(this.baseOutputDir, { recursive: true, force: true });
        console.log(`✓ 已清理输出目录: ${this.baseOutputDir}`);
      }
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
