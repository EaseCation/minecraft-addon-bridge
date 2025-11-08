import { compile, Options } from 'json-schema-to-typescript';
import * as path from 'path';
import chalk from 'chalk';
import { SchemaInfo, SchemaLoader } from './schema-loader';
import { VersionDetector } from './version-detector';
import { OutputManager, TypeOutput } from './output-manager';

export interface GeneratorOptions {
  schemaBaseDir: string;
  outputDir: string;
  category?: 'behavior' | 'resource';
  clean?: boolean;
  version?: string;
  module?: string;
}

export class TypeGenerator {
  private loader: SchemaLoader;
  private versionDetector: VersionDetector;
  private outputManager: OutputManager;
  private options: GeneratorOptions;

  constructor(options: GeneratorOptions) {
    this.options = options;
    this.loader = new SchemaLoader(options.schemaBaseDir);
    this.versionDetector = new VersionDetector();
    this.outputManager = new OutputManager(options.outputDir);
  }

  /**
   * 执行生成流程
   */
  async generate(): Promise<void> {
    console.log(chalk.bold.blue('\n🚀 开始生成 TypeScript 类型定义...\n'));

    // 清理输出目录
    if (this.options.clean !== false) {
      await this.outputManager.clean();
    }

    // 加载所有 Schema
    const schemas = await this.loader.loadAllSchemas(this.options.category);

    if (schemas.length === 0) {
      console.log(chalk.yellow('⚠ 没有找到任何 Schema 文件'));
      return;
    }

    // 解析引用
    console.log(chalk.blue('\n🔗 解析 Schema 引用...'));
    for (const schema of schemas) {
      await this.loader.dereferenceSchema(schema);
    }

    // 按模块和版本分组
    const groupedSchemas = this.versionDetector.groupByModuleAndVersion(
      schemas,
      this.options.version  // 传递显式版本（如果有）
    );
    this.versionDetector.printVersionStats(groupedSchemas);

    // 生成类型定义
    console.log(chalk.blue('\n⚙️  生成类型定义...'));
    let successCount = 0;
    let errorCount = 0;

    for (const [moduleKey, versions] of groupedSchemas.entries()) {
      console.log(chalk.cyan(`\n${moduleKey}:`));

      for (const [version, schemaInfos] of versions.entries()) {
        for (const schemaInfo of schemaInfos) {
          try {
            await this.generateTypeForSchema(schemaInfo, moduleKey, version);
            successCount++;
          } catch (error) {
            console.error(
              chalk.red(`  ✗ ${schemaInfo.relativePath}: ${error}`),
            );
            errorCount++;
          }
        }
      }
    }

    // 生成索引文件
    await this.outputManager.generateIndexFiles();
    await this.outputManager.generateRootIndex();

    // 打印统计
    this.outputManager.printStats();
    console.log(chalk.green(`  成功: ${successCount}`));
    if (errorCount > 0) {
      console.log(chalk.red(`  失败: ${errorCount}`));
    }
  }

  /**
   * 为单个 Schema 生成类型定义
   */
  private async generateTypeForSchema(
    schemaInfo: SchemaInfo,
    moduleKey: string,
    version: string
  ): Promise<void> {
    // 获取 schema 并总是预处理：删除简单类型的 title
    let schema = schemaInfo.dereferencedSchema || schemaInfo.schema;
    schema = this.simplifySchema(schema);

    // 生成类型名称（从文件名）
    const fileName = path.basename(schemaInfo.filePath, '.json');
    const typeName = this.formatTypeName(fileName);

    // 配置 json-schema-to-typescript
    const compileOptions: Partial<Options> = {
      bannerComment: this.outputManager.generateFileHeader(
        `${schemaInfo.relativePath} 的类型定义`
      ),
      style: {
        semi: true,
        singleQuote: true,
        trailingComma: 'es5',
        bracketSpacing: true,
        printWidth: 100,
      },
      strictIndexSignatures: false,
      declareExternallyReferenced: true,
      unreachableDefinitions: false,
      $refOptions: {
        resolve: {
          external: false, // 外部引用已在 loader 中处理
        },
      },
    };

    // 编译 Schema 到 TypeScript
    const tsContent = await compile(schema, typeName, compileOptions);

    // 输出文件
    const output: TypeOutput = {
      modulePath: `${moduleKey}/${version}`,
      fileName: `${typeName}.d.ts`,
      content: tsContent,
    };

    await this.outputManager.writeTypeFile(output);
  }

  /**
   * 格式化类型名称（PascalCase）
   */
  private formatTypeName(fileName: string): string {
    return fileName
      .split(/[._-]/)
      .map(part => part.charAt(0).toUpperCase() + part.slice(1))
      .join('');
  }

  /**
   * 判断是否是简单类型（只有 type，没有复杂结构）
   */
  private isSimpleType(schema: any): boolean {
    if (!schema || typeof schema !== 'object') return false;

    // 必须有明确的基础类型
    if (!schema.type || typeof schema.type !== 'string') return false;

    const primitiveTypes = ['string', 'number', 'integer', 'boolean', 'null'];
    if (!primitiveTypes.includes(schema.type)) return false;

    // 如果有这些复杂结构，不是简单类型
    if (
      schema.properties ||
      schema.items ||
      schema.allOf ||
      schema.anyOf ||
      schema.oneOf ||
      schema.not ||
      schema.enum ||
      schema.const ||
      schema.$ref
    ) {
      return false;
    }

    return true;
  }

  /**
   * 简化 Schema（移除可能导致编译问题的字段，删除简单类型的 title）
   */
  private simplifySchema(schema: any): any {
    const simplified = JSON.parse(JSON.stringify(schema));  // 深拷贝

    // 递归删除简单类型的 title
    const removeTitlesFromSimpleTypes = (obj: any) => {
      if (!obj || typeof obj !== 'object') return;

      // 处理当前对象：如果是简单类型且有 title，删除 title
      if (this.isSimpleType(obj) && obj.title) {
        delete obj.title;
      }

      // 递归处理所有子对象
      for (const key in obj) {
        if (typeof obj[key] === 'object' && obj[key] !== null) {
          if (Array.isArray(obj[key])) {
            obj[key].forEach((item: any) => removeTitlesFromSimpleTypes(item));
          } else {
            removeTitlesFromSimpleTypes(obj[key]);
          }
        }
      }
    };

    removeTitlesFromSimpleTypes(simplified);

    // 移除 $schema 和 $id
    delete simplified.$schema;
    delete simplified.$id;

    // 清理 definitions 中的空对象
    if (simplified.definitions) {
      const cleanedDefinitions: any = {};
      for (const [key, value] of Object.entries(simplified.definitions)) {
        if (typeof value === 'object' && value !== null) {
          cleanedDefinitions[key] = value;
        }
      }
      simplified.definitions = cleanedDefinitions;
    }

    return simplified;
  }

  /**
   * 生成特定模块的类型
   */
  async generateModule(moduleName: string): Promise<void> {
    console.log(chalk.bold.blue(`\n🚀 生成 ${moduleName} 模块类型...\n`));

    const schemas = await this.loader.loadAllSchemas(this.options.category);
    const filtered = schemas.filter(s => s.module === moduleName);

    if (filtered.length === 0) {
      console.log(chalk.yellow(`⚠ 未找到模块: ${moduleName}`));
      return;
    }

    console.log(chalk.green(`✓ 找到 ${filtered.length} 个 Schema`));

    // 解析并生成
    for (const schema of filtered) {
      await this.loader.dereferenceSchema(schema);
    }

    const groupedSchemas = this.versionDetector.groupByModuleAndVersion(
      filtered,
      this.options.version  // 传递显式版本（如果有）
    );

    for (const [moduleKey, versions] of groupedSchemas.entries()) {
      for (const [version, schemaInfos] of versions.entries()) {
        for (const schemaInfo of schemaInfos) {
          await this.generateTypeForSchema(schemaInfo, moduleKey, version);
        }
      }
    }

    await this.outputManager.generateIndexFiles();
    this.outputManager.printStats();
  }
}
