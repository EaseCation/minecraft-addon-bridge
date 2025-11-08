#!/usr/bin/env node

import { Command } from 'commander';
import * as path from 'path';
import chalk from 'chalk';
import { TypeGenerator } from './generator';

// 默认路径
const DEFAULT_SCHEMA_DIR = path.resolve(__dirname, '../../schemas/minecraft-bedrock-json-schemas/source');
const DEFAULT_OUTPUT_DIR = path.resolve(__dirname, '../types');

async function main() {
  const program = new Command();

  program
    .name('schema-types-ts')
    .description('为 Minecraft Bedrock JSON Schema 生成 TypeScript 类型定义')
    .version('1.0.0');

  program
    .option('-s, --schema-dir <path>', 'Schema 源目录', DEFAULT_SCHEMA_DIR)
    .option('-o, --output-dir <path>', '输出目录', DEFAULT_OUTPUT_DIR)
    .option('-c, --category <category>', '类别 (behavior/resource)', undefined)
    .option('-m, --module <module>', '指定模块（如 items, blocks, entities）', undefined)
    .option('--no-clean', '不清理输出目录')
    .action(async (options) => {
      try {
        const generator = new TypeGenerator({
          schemaBaseDir: options.schemaDir,
          outputDir: options.outputDir,
          category: options.category,
          clean: options.clean,
        });

        if (options.module) {
          await generator.generateModule(options.module);
        } else {
          await generator.generate();
        }

        console.log(chalk.bold.green('\n✨ 完成！\n'));
        process.exit(0);
      } catch (error) {
        console.error(chalk.red('\n❌ 生成失败:'), error);
        process.exit(1);
      }
    });

  program.parse();
}

// 处理未捕获的异常
process.on('unhandledRejection', (error) => {
  console.error(chalk.red('\n❌ 未处理的错误:'), error);
  process.exit(1);
});

main();
