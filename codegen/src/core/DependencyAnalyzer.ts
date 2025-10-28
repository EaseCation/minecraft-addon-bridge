/**
 * 依赖分析器
 * 分析类型之间的依赖关系，并进行拓扑排序
 */

import { TypeInfo } from '../parsers/types';

export class DependencyAnalyzer {
  /**
   * 分析类型依赖关系并进行拓扑排序
   * @param types - 所有类型信息
   * @returns 排序后的类型列表（无循环依赖）
   * @throws Error 如果检测到循环依赖
   */
  analyze(types: TypeInfo[]): TypeInfo[] {
    // 检测循环依赖
    const cycles = this.detectCycles(types);
    if (cycles && cycles.length > 0) {
      throw new Error(
        `检测到循环依赖:\n${cycles.map(cycle => `  - ${cycle.join(' -> ')}`).join('\n')}`
      );
    }

    // 执行拓扑排序
    return this.topologicalSort(types);
  }

  /**
   * 检测循环依赖
   * @returns 如果存在循环，返回循环路径数组；否则返回 null
   */
  detectCycles(types: TypeInfo[]): string[][] | null {
    const typeMap = new Map(
      types.map(t => [this.getFQN(t), t])
    );

    const visited = new Set<string>();
    const recursionStack = new Set<string>();
    const cycles: string[][] = [];

    const dfs = (fqn: string, path: string[]): void => {
      if (recursionStack.has(fqn)) {
        // 找到循环
        const cycleStart = path.indexOf(fqn);
        if (cycleStart >= 0) {
          cycles.push([...path.slice(cycleStart), fqn]);
        }
        return;
      }

      if (visited.has(fqn)) {
        return;
      }

      visited.add(fqn);
      recursionStack.add(fqn);
      path.push(fqn);

      const type = typeMap.get(fqn);
      if (type) {
        for (const dep of type.dependencies) {
          if (typeMap.has(dep)) {
            dfs(dep, [...path]);
          }
        }
      }

      path.pop();
      recursionStack.delete(fqn);
    };

    // 对每个类型执行 DFS
    for (const type of types) {
      const fqn = this.getFQN(type);
      if (!visited.has(fqn)) {
        dfs(fqn, []);
      }
    }

    return cycles.length > 0 ? cycles : null;
  }

  /**
   * 拓扑排序（Kahn 算法）
   */
  private topologicalSort(types: TypeInfo[]): TypeInfo[] {
    const typeMap = new Map(
      types.map(t => [this.getFQN(t), t])
    );

    // 计算每个节点的入度
    const inDegree = new Map<string, number>();
    for (const type of types) {
      const fqn = this.getFQN(type);
      if (!inDegree.has(fqn)) {
        inDegree.set(fqn, 0);
      }
    }

    for (const type of types) {
      for (const dep of type.dependencies) {
        if (typeMap.has(dep)) {
          inDegree.set(dep, (inDegree.get(dep) || 0) + 1);
        }
      }
    }

    // 找到所有入度为 0 的节点
    const queue: string[] = [];
    for (const [fqn, degree] of inDegree) {
      if (degree === 0) {
        queue.push(fqn);
      }
    }

    const result: TypeInfo[] = [];

    // Kahn 算法
    while (queue.length > 0) {
      const fqn = queue.shift()!;
      const type = typeMap.get(fqn);

      if (type) {
        result.push(type);

        // 减少依赖此节点的其他节点的入度
        for (const otherType of types) {
          if (otherType.dependencies.has(fqn)) {
            const otherFQN = this.getFQN(otherType);
            const newDegree = (inDegree.get(otherFQN) || 0) - 1;
            inDegree.set(otherFQN, newDegree);

            if (newDegree === 0) {
              queue.push(otherFQN);
            }
          }
        }
      }
    }

    // 如果结果数量不等于输入数量，说明存在循环依赖
    if (result.length !== types.length) {
      throw new Error('拓扑排序失败：存在循环依赖或孤立节点');
    }

    return result;
  }

  /**
   * 获取类型的完全限定名
   */
  private getFQN(type: TypeInfo): string {
    return `${type.javaPackage}.${type.javaClassName}`;
  }
}
