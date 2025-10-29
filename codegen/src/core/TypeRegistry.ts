/**
 * 全局类型注册表
 * 管理所有生成的 Java 类型
 */

import { TypeInfo } from '../parsers/types';

export class TypeRegistry {
  // 按完全限定名索引的类型信息
  private typesByFQN: Map<string, TypeInfo> = new Map();

  // 按包名索引的类型信息
  private typesByPackage: Map<string, TypeInfo[]> = new Map();

  // 按模块索引的类型信息
  private typesByModule: Map<string, TypeInfo[]> = new Map();

  // $ref 路径到类名的映射（用于解析 #/definitions/X 形式的引用）
  // key: "schemaFilePath#/definitions/X", value: "ClassName"
  private refToClassName: Map<string, string> = new Map();

  // 外部文件路径到类名的映射（用于解析 ../types/trigger.json 形式的引用）
  // key: "normalized file path", value: "ClassName"
  private filePathToClassName: Map<string, string> = new Map();

  /**
   * 注册一个类型
   */
  register(typeInfo: TypeInfo): void {
    const fqn = this.getFQN(typeInfo.javaPackage, typeInfo.javaClassName);

    // 检查是否已存在
    if (this.typesByFQN.has(fqn)) {
      console.warn(`⚠️  类型 ${fqn} 已存在，将被覆盖`);
    }

    // 注册到 FQN 索引
    this.typesByFQN.set(fqn, typeInfo);

    // 注册到包名索引
    if (!this.typesByPackage.has(typeInfo.javaPackage)) {
      this.typesByPackage.set(typeInfo.javaPackage, []);
    }
    this.typesByPackage.get(typeInfo.javaPackage)!.push(typeInfo);

    // 注册到模块索引
    if (typeInfo.module) {
      if (!this.typesByModule.has(typeInfo.module)) {
        this.typesByModule.set(typeInfo.module, []);
      }
      this.typesByModule.get(typeInfo.module)!.push(typeInfo);
    }
  }

  /**
   * 根据完全限定名查找类型
   */
  resolve(fullyQualifiedName: string): TypeInfo | undefined {
    return this.typesByFQN.get(fullyQualifiedName);
  }

  /**
   * 根据包名和类名查找类型
   */
  resolveByPackageAndClass(javaPackage: string, className: string): TypeInfo | undefined {
    const fqn = this.getFQN(javaPackage, className);
    return this.typesByFQN.get(fqn);
  }

  /**
   * 获取所有注册的类型
   */
  getAllTypes(): TypeInfo[] {
    return Array.from(this.typesByFQN.values());
  }

  /**
   * 按包名获取类型
   */
  getTypesByPackage(packageName: string): TypeInfo[] {
    return this.typesByPackage.get(packageName) || [];
  }

  /**
   * 按模块获取类型
   */
  getTypesByModule(moduleName: string): TypeInfo[] {
    return this.typesByModule.get(moduleName) || [];
  }

  /**
   * 获取所有模块及其类型
   */
  getByModule(): Map<string, TypeInfo[]> {
    return this.typesByModule;
  }

  /**
   * 替换为公共类型
   * 将重复的类型引用更新为公共类型包
   */
  replaceWithCommonTypes(commonTypes: TypeInfo[]): void {
    const commonTypeNames = new Set(
      commonTypes.map(t => t.javaClassName)
    );

    // 遍历所有类型，更新依赖中的公共类型引用
    for (const typeInfo of this.getAllTypes()) {
      if (typeInfo.isCommon) {
        continue;
      }

      // 更新依赖
      const updatedDependencies = new Set<string>();
      for (const dep of typeInfo.dependencies) {
        // 检查依赖是否是公共类型
        const [, className] = this.parseFQN(dep);
        if (className && commonTypeNames.has(className)) {
          // 查找对应的公共类型
          const commonType = commonTypes.find(t => t.javaClassName === className);
          if (commonType) {
            const commonFQN = this.getFQN(commonType.javaPackage, commonType.javaClassName);
            updatedDependencies.add(commonFQN);
            continue;
          }
        }
        updatedDependencies.add(dep);
      }
      typeInfo.dependencies = updatedDependencies;
    }
  }

  /**
   * 获取注册的类型数量
   */
  size(): number {
    return this.typesByFQN.size;
  }

  /**
   * 清空注册表
   */
  clear(): void {
    this.typesByFQN.clear();
    this.typesByPackage.clear();
    this.typesByModule.clear();
    this.refToClassName.clear();
    this.filePathToClassName.clear();
  }

  /**
   * 注册 definition 引用到类名的映射
   * @param schemaPath - Schema 文件路径（相对于 behavior 目录）
   * @param defName - Definition 名称（如 "C", "CollisionBox"）
   * @param className - Java 类名（如 "CollisionBox", "BlocksCollisionBox"）
   */
  registerDefinitionRef(schemaPath: string, defName: string, className: string): void {
    const key = `${schemaPath}#/definitions/${defName}`;
    this.refToClassName.set(key, className);
  }

  /**
   * 注册外部文件引用到类名的映射
   * @param filePath - Schema 文件路径（规范化后的相对路径）
   * @param className - Java 类名
   */
  registerExternalFileRef(filePath: string, className: string): void {
    const normalizedPath = this.normalizeFilePath(filePath);
    this.filePathToClassName.set(normalizedPath, className);
  }

  /**
   * 解析 $ref 引用获取类名
   * @param schemaPath - 当前 Schema 文件路径
   * @param ref - $ref 值（如 "#/definitions/C" 或 "../types/trigger.json"）
   * @returns 对应的 Java 类名，如果找不到返回 undefined
   */
  resolveDefinitionRef(schemaPath: string, ref: string): string | undefined {
    // 处理内部引用（#/definitions/...）
    if (ref.startsWith('#/definitions/')) {
      const key = `${schemaPath}${ref}`;
      return this.refToClassName.get(key);
    }

    // 处理外部文件引用（相对路径）
    if (!ref.startsWith('#')) {
      // 解析相对路径
      const path = require('path');
      const schemaDir = path.dirname(schemaPath);
      const absoluteRefPath = path.resolve(schemaDir, ref);
      const normalizedPath = this.normalizeFilePath(absoluteRefPath);

      // 先尝试精确匹配
      let className = this.filePathToClassName.get(normalizedPath);
      if (className) {
        return className;
      }

      // 尝试去掉 .json 扩展名匹配
      const withoutExt = normalizedPath.replace(/\.json$/, '');
      className = this.filePathToClassName.get(withoutExt);
      if (className) {
        return className;
      }

      // 尝试只用文件名匹配（不含路径）
      const fileName = path.basename(normalizedPath, '.json');
      for (const [registeredPath, registeredClass] of this.filePathToClassName.entries()) {
        const registeredFileName = path.basename(registeredPath, '.json');
        if (registeredFileName === fileName) {
          return registeredClass;
        }
      }
    }

    return undefined;
  }

  /**
   * 规范化文件路径（转换为小写，统一分隔符）
   */
  private normalizeFilePath(filePath: string): string {
    const path = require('path');
    return filePath
      .split(path.sep)
      .join('/')
      .toLowerCase()
      .replace(/\.json$/, ''); // 去掉扩展名以便更灵活地匹配
  }

  /**
   * 生成完全限定名
   */
  private getFQN(javaPackage: string, className: string): string {
    return `${javaPackage}.${className}`;
  }

  /**
   * 解析完全限定名
   */
  private parseFQN(fqn: string): [string, string] {
    const lastDot = fqn.lastIndexOf('.');
    if (lastDot === -1) {
      return ['', fqn];
    }
    return [fqn.substring(0, lastDot), fqn.substring(lastDot + 1)];
  }

  /**
   * 调试：打印注册表信息
   */
  debug(): void {
    console.log('\n=== TypeRegistry 状态 ===');
    console.log(`总类型数: ${this.size()}`);
    console.log(`\n按模块分类:`);
    for (const [module, types] of this.typesByModule) {
      console.log(`  ${module}: ${types.length} 个类型`);
    }
    console.log(`\n按包名分类:`);
    for (const [pkg, types] of this.typesByPackage) {
      console.log(`  ${pkg}: ${types.length} 个类型`);
    }
  }
}
