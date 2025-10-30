#!/usr/bin/env node

/**
 * JSON Schema 到 Java DTO 代码生成器
 * CLI 入口
 */

import { Command } from 'commander';
import { SchemaLoader } from './core/SchemaLoader';
import { TypeRegistry } from './core/TypeRegistry';
import { DependencyAnalyzer } from './core/DependencyAnalyzer';
import { CommonTypeDetector } from './core/CommonTypeDetector';
import { SchemaParser } from './parsers/SchemaParser';
import { JavaGenerator } from './generator/JavaGenerator';
import { FileWriter } from './utils/FileWriter';
import { GenerateOptions, TypeInfo } from './parsers/types';
import { extractModuleName, getJavaPackage } from './utils/JavaNaming';
import { detectVersionFromSchema } from './utils/VersionDetector';
import { getModuleVersions } from './utils/VersionMapping';
import * as path from 'path';
import * as fs from 'fs';

/**
 * 主函数
 */
async function main(options: GenerateOptions) {
  console.log('🚀 JSON Schema 到 Java DTO 代码生成器');
  console.log('=====================================\n');

  // 1. 确定目录和版本
  const schemaDir = options.schemaDir || path.resolve(__dirname, '../../schemas/minecraft-bedrock-json-schemas/behavior');
  const version = (options as any).mcVersion || detectVersion(schemaDir);
  console.log(`📦 目标版本: ${version}`);

  // 2. 确定输出目录（只到 java 目录，让 FileWriter 根据包名创建子目录）
  const versionSegment = `v${version.replace(/\./g, '_')}`;
  const outputDir = options.outputDir || path.resolve(
    __dirname,
    '../../addon-bridge-core/src/main/java'
  );

  console.log(`📁 输出目录: ${outputDir}`);

  // 3. 检查版本是否已存在（检查 item 和 block 模块）
  // 注意：batch 脚本（generate-all.ts）会在调用前做检查，这里只是额外的安全检查
  const criticalModules = ['item', 'block'];
  const existingModules: string[] = [];

  for (const moduleName of criticalModules) {
    const versionOutputPath = path.join(
      outputDir,
      'net/easecation/bridge/core/dto',
      moduleName,
      versionSegment
    );

    if (fs.existsSync(versionOutputPath)) {
      const javaFiles = fs.readdirSync(versionOutputPath).filter(f => f.endsWith('.java'));
      if (javaFiles.length > 0) {
        existingModules.push(`${moduleName}(${javaFiles.length})`);
      }
    }
  }

  if (existingModules.length > 0 && !options.force) {
    console.log(`⏭️  版本 ${version} 已存在: ${existingModules.join(', ')}`);
    console.log(`   使用 --force 参数强制重新生成\n`);
    return;
  }

  console.log('');

  // 4. 清理旧文件（如果需要）
  if (options.clean) {
    console.log('🧹 清理旧文件...');
    const fileWriter = new FileWriter(outputDir);
    fileWriter.clean(version);  // 传递版本号，只清理对应版本的 DTO 目录
    console.log('');
  }

  // 5. 加载所有 Schema
  console.log('📂 加载 Schema 文件...');
  const schemaLoader = new SchemaLoader();
  const schemas = await schemaLoader.loadDirectory(schemaDir);
  console.log(`✓ 加载了 ${schemas.size} 个 schema 文件\n`);

  // 6. 解析并注册类型
  console.log('📝 解析类型定义...');
  const typeRegistry = new TypeRegistry();
  const schemaParser = new SchemaParser(typeRegistry, version);

  for (const [filePath, schema] of schemas) {
    const module = extractModuleName(filePath);
    // 添加 'behavior' 前缀以便 getJavaPackage 能正确识别路径
    const fullPath = `behavior/${filePath}`;

    const parsedTypes = schemaParser.parse(schema, {
      filePath: fullPath,
      module,
      version,
    });

    for (const parsedType of parsedTypes) {
      const typeInfo: TypeInfo = {
        schemaPath: filePath,
        schemaPointer: '#',
        javaPackage: parsedType.javaPackage || getJavaPackage(fullPath, version),
        javaClassName: parsedType.javaClassName || parsedType.name,
        javaType: parsedType.kind,
        dependencies: new Set(),
        schema: schema,
        module,
      };
      typeRegistry.register(typeInfo);

      // 注册外部文件引用：将 schema 文件路径映射到生成的 Java 类名
      // 这样当其他 schema 引用这个文件时（如 ../types/trigger.json），可以解析到 Trigger 类
      const absoluteSchemaPath = path.resolve(schemaDir, filePath);
      typeRegistry.registerExternalFileRef(absoluteSchemaPath, typeInfo.javaClassName);
    }
  }

  console.log(`✓ 解析了 ${typeRegistry.size()} 个类型定义\n`);

  // 7. 公共类型检测（跨模块去重）
  console.log('🔍 检测公共类型...');
  const commonTypeDetector = new CommonTypeDetector();
  for (const [module, types] of typeRegistry.getByModule()) {
    types.forEach(type => commonTypeDetector.register(module, type));
  }

  const commonTypes = commonTypeDetector.detectCommonTypes();
  console.log(`✓ 检测到 ${commonTypes.length} 个公共类型\n`);

  // 更新类型引用（将重复类型替换为 common 引用）
  if (commonTypes.length > 0) {
    typeRegistry.replaceWithCommonTypes(commonTypes);
  }

  // 8. 依赖分析
  console.log('🔗 分析类型依赖关系...');
  const dependencyAnalyzer = new DependencyAnalyzer();
  let sortedTypes: TypeInfo[];

  try {
    sortedTypes = dependencyAnalyzer.analyze(typeRegistry.getAllTypes());
    console.log(`✓ 分析了 ${sortedTypes.length} 个类型的依赖关系\n`);
  } catch (error) {
    console.error('❌ 依赖分析失败:', error);
    if (options.debug) {
      typeRegistry.debug();
    }
    process.exit(1);
  }

  // 9. 生成代码
  console.log('⚙️  生成 Java 代码...');

  if (options.dryRun) {
    console.log('（干运行模式，不会写入文件）\n');
  }

  const javaGenerator = new JavaGenerator();
  const fileWriter = new FileWriter(outputDir);

  // 9.1 生成公共类型（到版本内 common 包）
  if (commonTypes.length > 0 && !options.dryRun) {
    const commonPackage = `net.easecation.bridge.core.dto.${versionSegment}.common`;
    console.log(`\n📦 生成公共类型到 ${commonPackage}:`);
    for (const type of commonTypes) {
      // TODO: 将 TypeInfo 转换为 ParsedType 并生成代码
      // 这里需要重新解析或存储 ParsedType
      console.log(`  - ${type.javaClassName}`);
    }
  }

  // 9.2 生成版本化类型
  console.log(`\n📦 生成版本化类型:`);
  let generatedCount = 0;

  // 由于当前架构，我们需要重新解析以获取 ParsedType
  // 这里我们简化处理，直接从 schema 重新解析
  for (const [filePath, schema] of schemas) {
    const module = extractModuleName(filePath);
    // 添加 'behavior' 前缀以便生成正确的包结构
    const fullPath = `behavior/${filePath}`;

    const parsedTypes = schemaParser.parse(schema, {
      filePath: fullPath,
      module,
      version,
    });

    for (const parsedType of parsedTypes) {
      if (!parsedType.javaPackage || !parsedType.javaClassName) {
        console.warn(`⚠️  跳过类型 ${parsedType.name}（缺少包名或类名）`);
        continue;
      }

      try {
        const javaCode = javaGenerator.generate(parsedType);

        if (!options.dryRun) {
          fileWriter.write(parsedType.javaPackage, parsedType.javaClassName, javaCode);
        } else {
          console.log(`  [DRY] ${parsedType.javaPackage}.${parsedType.javaClassName}`);
        }

        generatedCount++;
      } catch (error) {
        console.error(`❌ 生成失败: ${parsedType.javaClassName}`, error);
        if (options.debug) {
          console.error('Type info:', parsedType);
        }
      }
    }
  }

  console.log(`\n✅ 总计生成 ${generatedCount} 个 Java 类`);

  if (options.debug) {
    console.log('\n📊 调试信息:');
    typeRegistry.debug();
    const stats = commonTypeDetector.getStats();
    console.log(`\n公共类型统计: ${stats.commonTypes} / ${stats.totalTypes}`);
  }

  console.log('\n🎉 代码生成完成！');
}

/**
 * 检测 Schema 版本
 */
function detectVersion(schemaDir?: string): string {
  const dir = schemaDir || path.resolve(__dirname, '../../schemas/minecraft-bedrock-json-schemas/behavior');
  return detectVersionFromSchema(dir);
}

/**
 * CLI 程序
 */
const program = new Command();

program
  .name('addon-bridge-codegen')
  .description('JSON Schema 到 Java DTO 代码生成器');

program
  .option('--mc-version <version>', '目标版本号（如 1.21.60）')
  .option('-V, --cli-version', '显示 CLI 版本号', () => {
    console.log('1.0.0');
    process.exit(0);
  })
  .option('-o, --output-dir <dir>', '输出目录')
  .option('-s, --schema-dir <dir>', 'Schema 目录')
  .option('-d, --debug', '调试模式')
  .option('--dry-run', '干运行（不写入文件）')
  .option('--clean', '清理已有文件')
  .option('--force', '强制重新生成已存在的版本')
  .action(async (options: GenerateOptions) => {
    try {
      await main(options);
    } catch (error) {
      console.error('❌ 错误:', error);
      process.exit(1);
    }
  });

program.parse();
