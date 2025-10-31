#!/usr/bin/env node

import { execSync } from 'child_process';
import * as path from 'path';
import * as fs from 'fs';
import { loadVersionMapping } from './utils/VersionMapping';

const SCHEMA_DIR = path.resolve(__dirname, '../../schemas/minecraft-bedrock-json-schemas');
const OUTPUT_DIR = path.resolve(__dirname, '../../addon-bridge-core/src/main/java');

interface GenerateOptions {
  module?: string;   // 指定模块，如 'item'，留空则生成所有
  force?: boolean;   // 强制重新生成
  dryRun?: boolean;  // 只显示计划，不实际生成
}

function parseArgs(): GenerateOptions {
  const args = process.argv.slice(2);
  const options: GenerateOptions = {};

  for (let i = 0; i < args.length; i++) {
    if (args[i] === '--module' && args[i + 1]) {
      options.module = args[i + 1];
      i++;
    } else if (args[i] === '--force') {
      options.force = true;
    } else if (args[i] === '--dry-run') {
      options.dryRun = true;
    }
  }

  return options;
}

function checkIfVersionExists(module: string, version: string): boolean {
  const versionDir = path.join(
    OUTPUT_DIR,
    'net/easecation/bridge/core/dto',
    module,
    `v${version.replace(/\./g, '_')}`
  );
  return fs.existsSync(versionDir) && fs.readdirSync(versionDir).some(f => f.endsWith('.java'));
}

function gitCheckout(commit: string) {
  console.log(`  📌 Checkout: ${commit}`);
  try {
    execSync(`git checkout ${commit}`, { cwd: SCHEMA_DIR, stdio: 'inherit' });
  } catch (error) {
    console.error(`  ❌ 无法切换到 commit ${commit}`);
    throw error;
  }
}

function generateForVersion(module: string, version: string, force: boolean) {
  console.log(`  🔨 生成 ${module} v${version}...`);

  const cmd = [
    'npm run generate --',
    `--mc-version ${version}`,
    // 注意：这里不传递 --module 参数，让主生成器根据 schema 路径自动判断
  ];

  if (force) cmd.push('--force');

  try {
    execSync(cmd.join(' '), { cwd: path.resolve(__dirname, '..'), stdio: 'inherit' });
  } catch (error) {
    console.error(`  ❌ 生成失败`);
    throw error;
  }
}

async function main() {
  const options = parseArgs();
  const mapping = loadVersionMapping();

  console.log('================================================');
  console.log('📦 批量生成 DTO');
  console.log('================================================\n');

  if (options.dryRun) {
    console.log('🔍 预览模式（不实际生成）\n');
  }

  // 保存当前分支
  let originalBranch: string;
  try {
    originalBranch = execSync('git rev-parse --abbrev-ref HEAD', {
      cwd: SCHEMA_DIR,
      encoding: 'utf-8'
    }).trim();
  } catch {
    originalBranch = 'main';
  }

  try {
    const modulesToProcess = options.module
      ? [options.module]
      : Object.keys(mapping.modules);

    for (const moduleName of modulesToProcess) {
      const versions = mapping.modules[moduleName];
      if (!versions || versions.length === 0) {
        console.log(`⚠️  模块 "${moduleName}" 没有配置版本，跳过\n`);
        continue;
      }

      console.log(`${'='.repeat(60)}`);
      console.log(`模块: ${moduleName.toUpperCase()}`);
      console.log(`${'='.repeat(60)}\n`);

      for (const entry of versions) {
        const { format_version, commit, notes } = entry;

        console.log(`▶ ${format_version} (${commit})`);
        console.log(`  ${notes}`);

        // 检查 commit 是否已填写
        if (commit === '待填写') {
          console.log(`  ⏭️  commit 未填写，跳过\n`);
          continue;
        }

        // 检查是否已存在
        if (!options.force && checkIfVersionExists(moduleName, format_version)) {
          console.log(`  ⏭️  已存在，跳过\n`);
          continue;
        }

        if (options.dryRun) {
          console.log(`  [预览] 将生成到: dto/${moduleName}/v${format_version.replace(/\./g, '_')}/\n`);
          continue;
        }

        // Checkout 到指定 commit
        gitCheckout(commit);

        // 生成代码
        generateForVersion(moduleName, format_version, options.force || false);

        console.log(`  ✅ 完成\n`);
      }
    }
  } finally {
    // 恢复原始分支
    if (!options.dryRun) {
      console.log(`\n📌 恢复到分支: ${originalBranch}`);
      try {
        execSync(`git checkout ${originalBranch}`, { cwd: SCHEMA_DIR, stdio: 'inherit' });
      } catch (error) {
        console.error(`⚠️  无法恢复到分支 ${originalBranch}`);
      }
    }
  }

  console.log('\n================================================');
  console.log('🎉 批量生成完成！');
  console.log('================================================\n');

  // 显示生成的目录
  console.log('生成的版本目录:');
  const dtoDir = path.join(OUTPUT_DIR, 'net/easecation/bridge/core/dto');
  if (fs.existsSync(dtoDir)) {
    for (const module of fs.readdirSync(dtoDir)) {
      const modulePath = path.join(dtoDir, module);
      if (fs.statSync(modulePath).isDirectory()) {
        const versions = fs.readdirSync(modulePath).filter(v => v.startsWith('v'));
        if (versions.length > 0) {
          console.log(`  ${module}/: ${versions.join(', ')}`);
        }
      }
    }
  }
}

main().catch(err => {
  console.error('❌ 错误:', err);
  process.exit(1);
});
