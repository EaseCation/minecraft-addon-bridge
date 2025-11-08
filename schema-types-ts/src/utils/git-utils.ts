import { execSync } from 'child_process';
import chalk from 'chalk';
import * as path from 'path';

export interface GitInfo {
  currentBranch: string;
  currentCommit: string;
  isClean: boolean;
}

/**
 * 获取当前 Git 信息
 */
export function getGitInfo(cwd: string): GitInfo {
  try {
    const currentBranch = execSync('git rev-parse --abbrev-ref HEAD', {
      cwd,
      encoding: 'utf-8'
    }).trim();

    const currentCommit = execSync('git rev-parse HEAD', {
      cwd,
      encoding: 'utf-8'
    }).trim();

    const status = execSync('git status --porcelain', {
      cwd,
      encoding: 'utf-8'
    }).trim();

    return {
      currentBranch,
      currentCommit,
      isClean: status.length === 0
    };
  } catch (error) {
    throw new Error(`无法获取 Git 信息: ${error}`);
  }
}

/**
 * 切换到指定的 commit
 */
export function gitCheckout(commit: string, cwd: string): void {
  console.log(chalk.gray(`  📌 Checkout: ${commit.substring(0, 8)}...`));

  try {
    execSync(`git checkout ${commit}`, {
      cwd,
      stdio: 'pipe' // 静默输出
    });
  } catch (error) {
    throw new Error(`无法切换到 commit ${commit}: ${error}`);
  }
}

/**
 * 恢复到原始分支或 commit
 */
export function gitRestore(ref: string, cwd: string): void {
  console.log(chalk.gray(`  🔙 恢复到: ${ref}`));

  try {
    execSync(`git checkout ${ref}`, {
      cwd,
      stdio: 'pipe'
    });
  } catch (error) {
    console.error(chalk.red(`❌ 无法恢复到 ${ref}: ${error}`));
    throw error;
  }
}

/**
 * 检查指定 commit 是否存在
 */
export function commitExists(commit: string, cwd: string): boolean {
  try {
    execSync(`git cat-file -e ${commit}^{commit}`, {
      cwd,
      stdio: 'pipe'
    });
    return true;
  } catch {
    return false;
  }
}

/**
 * 获取 commit 的简短描述
 */
export function getCommitInfo(commit: string, cwd: string): string {
  try {
    return execSync(`git log -1 --format="%h - %s (%ci)" ${commit}`, {
      cwd,
      encoding: 'utf-8'
    }).trim();
  } catch {
    return `${commit.substring(0, 8)}`;
  }
}

/**
 * 检查工作区是否干净
 */
export function isWorkingDirectoryClean(cwd: string): boolean {
  try {
    const status = execSync('git status --porcelain', {
      cwd,
      encoding: 'utf-8'
    }).trim();

    return status.length === 0;
  } catch {
    return false;
  }
}

/**
 * 安全的 Git 操作包装器
 * 自动保存和恢复原始状态
 */
export async function withGitCheckout<T>(
  commit: string,
  schemaDir: string,
  operation: () => Promise<T>
): Promise<T> {
  const gitInfo = getGitInfo(schemaDir);
  const originalRef = gitInfo.currentBranch === 'HEAD'
    ? gitInfo.currentCommit
    : gitInfo.currentBranch;

  console.log(chalk.blue(`\n📦 准备切换 Schema 版本`));
  console.log(chalk.gray(`  当前: ${originalRef}`));
  console.log(chalk.gray(`  目标: ${commit.substring(0, 8)}`));

  try {
    // 检查 commit 是否存在
    if (!commitExists(commit, schemaDir)) {
      throw new Error(`Commit ${commit} 不存在，可能需要先 fetch/pull`);
    }

    // 切换到目标 commit
    gitCheckout(commit, schemaDir);

    // 执行操作
    const result = await operation();

    return result;
  } catch (error) {
    console.error(chalk.red(`\n❌ 操作失败: ${error}`));
    throw error;
  } finally {
    // 无论成功或失败，都恢复原始状态
    try {
      gitRestore(originalRef, schemaDir);
      console.log(chalk.green(`✓ 已恢复到: ${originalRef}\n`));
    } catch (restoreError) {
      console.error(chalk.red(`❌ 严重错误: 无法恢复 Git 状态!`));
      console.error(chalk.yellow(`请手动执行: cd ${schemaDir} && git checkout ${originalRef}`));
      throw restoreError;
    }
  }
}

/**
 * 获取 Schema 仓库的默认路径
 */
export function getSchemaDirectory(): string {
  return path.resolve(__dirname, '../../../schemas/minecraft-bedrock-json-schemas');
}

/**
 * 验证 Schema 目录是否有效
 */
export function validateSchemaDirectory(schemaDir: string): boolean {
  try {
    // 检查是否是 Git 仓库
    execSync('git rev-parse --is-inside-work-tree', {
      cwd: schemaDir,
      stdio: 'pipe'
    });

    return true;
  } catch {
    return false;
  }
}
