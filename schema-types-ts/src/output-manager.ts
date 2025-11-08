import * as fs from 'fs-extra';
import * as path from 'path';
import chalk from 'chalk';

export interface TypeOutput {
  modulePath: string; // 如 'behavior/items/v1_21_60'
  fileName: string; // 如 'Items.d.ts'
  content: string;
}

export class OutputManager {
  private outputBaseDir: string;
  private generatedFiles: string[] = [];

  constructor(outputBaseDir: string) {
    this.outputBaseDir = outputBaseDir;
  }

  /**
   * 写入类型定义文件
   */
  async writeTypeFile(output: TypeOutput): Promise<void> {
    const fullPath = path.join(this.outputBaseDir, output.modulePath, output.fileName);

    // 确保目录存在
    await fs.ensureDir(path.dirname(fullPath));

    // 写入文件
    await fs.writeFile(fullPath, output.content, 'utf-8');

    this.generatedFiles.push(fullPath);
    console.log(chalk.gray(`  ✓ ${output.modulePath}/${output.fileName}`));
  }

  /**
   * 为每个模块版本生成 index.ts 导出文件
   */
  async generateIndexFiles(): Promise<void> {
    console.log(chalk.blue('\n📝 生成 index 导出文件...'));

    // 获取所有生成的目录
    const directories = new Set<string>();
    for (const filePath of this.generatedFiles) {
      directories.add(path.dirname(filePath));
    }

    // 为每个目录生成 index.ts
    for (const dir of directories) {
      await this.generateIndexFileForDirectory(dir);
    }
  }

  /**
   * 为指定目录生成 index.ts
   */
  private async generateIndexFileForDirectory(dir: string): Promise<void> {
    const files = await fs.readdir(dir);
    const typeFiles = files.filter(
      file => file.endsWith('.d.ts') && file !== 'index.d.ts'
    );

    if (typeFiles.length === 0) {
      return;
    }

    // 生成导出语句
    const exports = typeFiles
      .map(file => {
        const baseName = path.basename(file, '.d.ts');
        return `export * from './${baseName}';`;
      })
      .join('\n');

    const indexPath = path.join(dir, 'index.d.ts');
    const content = this.generateFileHeader('索引文件') + '\n' + exports + '\n';

    await fs.writeFile(indexPath, content, 'utf-8');

    const relativePath = path.relative(this.outputBaseDir, indexPath);
    console.log(chalk.gray(`  ✓ ${relativePath}`));
  }

  /**
   * 生成根目录的 index.ts，导出所有模块
   */
  async generateRootIndex(): Promise<void> {
    console.log(chalk.blue('\n📝 生成根 index 文件...'));

    const categories = await fs.readdir(this.outputBaseDir);
    const validCategories = [];

    for (const category of categories) {
      const categoryPath = path.join(this.outputBaseDir, category);
      const stat = await fs.stat(categoryPath);

      if (stat.isDirectory() && category !== 'node_modules') {
        validCategories.push(category);
      }
    }

    // 生成导出语句
    const exports = validCategories
      .map(category => `export * as ${category} from './${category}';`)
      .join('\n');

    const content = this.generateFileHeader('根索引文件') + '\n' + exports + '\n';

    const indexPath = path.join(this.outputBaseDir, 'index.d.ts');
    await fs.writeFile(indexPath, content, 'utf-8');

    console.log(chalk.green(`✓ ${path.relative(process.cwd(), indexPath)}`));
  }

  /**
   * 生成文件头注释
   */
  generateFileHeader(description: string = '类型定义'): string {
    return `/**
 * ${description}
 *
 * 此文件由 schema-types-ts 自动生成
 * 请勿手动修改此文件
 *
 * 生成时间: ${new Date().toISOString()}
 */
`;
  }

  /**
   * 清理输出目录
   */
  async clean(): Promise<void> {
    if (await fs.pathExists(this.outputBaseDir)) {
      console.log(chalk.yellow(`🧹 清理输出目录: ${this.outputBaseDir}`));
      await fs.remove(this.outputBaseDir);
    }
    await fs.ensureDir(this.outputBaseDir);
  }

  /**
   * 获取生成统计信息
   */
  getStats(): { totalFiles: number; directories: number } {
    const directories = new Set<string>();
    for (const filePath of this.generatedFiles) {
      directories.add(path.dirname(filePath));
    }

    return {
      totalFiles: this.generatedFiles.length,
      directories: directories.size
    };
  }

  /**
   * 打印生成统计
   */
  printStats(): void {
    const stats = this.getStats();
    console.log(chalk.green('\n✓ 生成完成!'));
    console.log(chalk.blue(`  生成文件数: ${stats.totalFiles}`));
    console.log(chalk.blue(`  目录数: ${stats.directories}`));
    console.log(chalk.blue(`  输出目录: ${path.relative(process.cwd(), this.outputBaseDir)}`));
  }
}
