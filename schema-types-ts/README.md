# Minecraft Bedrock Schema TypeScript Types

为 Minecraft 基岩版 JSON Schema 自动生成的 TypeScript 类型定义，支持多版本。

## 📖 简介

本项目使用 `json-schema-to-typescript` 将 Minecraft 基岩版的官方 JSON Schema 转换为 TypeScript 类型定义，为开发 Add-on 时提供完整的类型提示和智能补全。

基于 Git Submodule 的版本管理机制，自动生成 8 个历史版本（MC 1.19.0 - 1.21.60）的类型定义。

## 🎯 特性

- ✅ **完整覆盖** - 包含 behavior 和 resource 包的所有 Schema
- ✅ **多版本支持** - 支持 8 个历史版本（1.19.0 - 1.21.60），基于 Git 版本管理
- ✅ **版本隔离** - 每个版本独立生成，互不干扰
- ✅ **类型安全** - 提供完整的 TypeScript 类型定义
- ✅ **智能提示** - 在 IDE 中获得完整的代码补全
- ✅ **自动生成** - 与官方 Schema 保持同步

## 📦 安装

```bash
# 安装依赖
npm install

# 或使用 pnpm
pnpm install
```

## 🚀 使用

### 生成类型定义

#### 单版本生成（生成当前 Schema 版本）

```bash
# 生成所有类型
npm run generate

# 仅生成 behavior 类型
npm run generate:behavior

# 仅生成 resource 类型
npm run generate:resource
```

#### 多版本批量生成（推荐）

```bash
# 生成所有模块的所有版本（8个版本 × 多个模块）
npm run generate:all

# 仅生成 behavior 模块的所有版本
npm run generate:all:behavior

# 仅生成 resource 模块的所有版本
npm run generate:all:resource

# 仅生成 items 模块的所有版本
npm run generate:items

# 仅生成 blocks 模块的所有版本
npm run generate:blocks

# 仅生成 entities 模块的所有版本
npm run generate:entities

# 强制重新生成（覆盖已存在的版本）
npm run generate:all:force

# 预览模式（查看将生成什么，不实际生成）
npm run generate:all:preview
```

#### 清理

```bash
# 清理输出目录
npm run clean
```

### 在项目中使用

生成的类型定义位于 `types/` 目录下，按照**版本隔离**的方式组织：

```
types/
├── behavior/
│   ├── items/
│   │   ├── v1_19_0/          # MC 1.19.0
│   │   │   ├── Items.d.ts
│   │   │   ├── MinecraftItem.d.ts
│   │   │   └── index.d.ts
│   │   ├── v1_19_40/         # MC 1.19.40
│   │   ├── v1_20_10/         # MC 1.20.10
│   │   ├── v1_20_41/         # MC 1.20.41
│   │   ├── v1_20_81/         # MC 1.20.81
│   │   ├── v1_21_50/         # MC 1.21.50
│   │   └── v1_21_60/         # MC 1.21.60 (最新)
│   ├── blocks/
│   │   ├── v1_19_0/
│   │   ├── v1_19_40/
│   │   └── v1_21_60/
│   ├── entities/
│   │   └── v1_21_60/
│   └── ...
└── resource/
    ├── animations/
    ├── models/
    └── ...
```

#### 导入类型示例

```typescript
// 导入最新版本（1.21.60）的 items 类型
import { Item } from './types/behavior/items/v1_21_60';

// 导入特定历史版本（1.20.81）的 items 类型
import { Item as Item_1_20_81 } from './types/behavior/items/v1_20_81';

// 导入 blocks 类型
import { BlocksDefinition } from './types/behavior/blocks/v1_21_60';

// 使用类型
const myItem: Item = {
  format_version: '1.21.60',
  'minecraft:item': {
    description: {
      identifier: 'mypack:custom_item',
      category: 'items'
    },
    components: {
      'minecraft:max_stack_size': 64
    }
  }
};

// 同时使用多个版本
function migrateItem(oldItem: Item_1_20_81): Item {
  // 将旧版本的 item 迁移到新版本
  return {
    format_version: '1.21.60',
    'minecraft:item': oldItem['minecraft:item']
  };
}
```

## 🏗️ 项目结构

```
schema-types-ts/
├── src/                      # 生成器源码
│   ├── index.ts             # CLI 入口
│   ├── generator.ts         # 主生成器
│   ├── schema-loader.ts     # Schema 加载器
│   ├── version-detector.ts  # 版本检测器
│   └── output-manager.ts    # 输出管理器
├── types/                   # 生成的类型定义（输出）
├── package.json
├── tsconfig.json
└── README.md
```

## ⚙️ 配置

### CLI 选项

```bash
schema-types-ts [options]

选项:
  -s, --schema-dir <path>     Schema 源目录 (默认: ../schemas/minecraft-bedrock-json-schemas/source)
  -o, --output-dir <path>     输出目录 (默认: ./types)
  -c, --category <category>   类别 (behavior/resource)
  -m, --module <module>       指定模块 (如 items, blocks, entities)
  --no-clean                  不清理输出目录
  -h, --help                  显示帮助信息
  -V, --version               显示版本号
```

### 示例

```bash
# 只生成 items 模块
npm run generate -- -m items

# 指定自定义输出目录
npm run generate -- -o ./my-types

# 不清理现有文件，增量生成
npm run generate -- --no-clean
```

## 📅 支持的版本

### 版本对照表

| Minecraft 版本 | format_version | 发布时间 | 状态 |
|---------------|----------------|----------|------|
| 1.19.0 | 1.19.0 | 2022-08-05 | ✅ 支持 |
| 1.19.40 | 1.19.40 | 2022-09-08 | ✅ 支持 |
| 1.19.50 | 1.19.50 | 2022-10-29 | ✅ 支持 |
| 1.20.10 | 1.20.10 | 2023-03-09 | ✅ 支持 |
| 1.20.41 | 1.20.41 | 2023-08-17 | ✅ 支持 |
| 1.20.81 | 1.20.81 | 2024-05-11 | ✅ 支持 |
| 1.21.50 | 1.21.50 | 2024-12-05 | ✅ 支持 |
| 1.21.60 | 1.21.60 | 2024-12-24 | ✅ 最新 |

### 版本管理机制

本项目使用 **Git Submodule + 版本映射配置** 的方式管理多版本：

1. **Git Submodule**: `schemas/minecraft-bedrock-json-schemas` 指向官方 Schema 仓库
2. **版本映射**: `version-mapping.json` 定义每个版本对应的 commit hash
3. **自动切换**: 生成时自动 `git checkout` 到对应 commit
4. **安全恢复**: 生成完成后自动恢复到原始分支

配置文件示例（`version-mapping.json`）：

```json
{
  "modules": {
    "items": [
      {
        "format_version": "1.21.60",
        "commit": "2d7ba565356605ee83bd052b068c358cbf0277eb",
        "notes": "MC 1.21.60 - 当前最新 (2024-12-24)"
      }
    ]
  }
}
```

## 📊 支持的模块

### Behavior 包

- `animations` - 动画
- `animation_controllers` - 动画控制器
- `biomes` - 生物群系
- `blocks` - 方块
- `cameras` - 相机
- `dialogue` - 对话
- `entities` - 实体
- `features` - 特征
- `feature_rules` - 特征规则
- `functions` - 函数
- `items` - 物品
- `loot_tables` - 战利品表
- `recipes` - 配方
- `spawn_rules` - 生成规则
- `trading` - 交易

### Resource 包

- `animations` - 资源动画
- `entity` - 实体资源
- `models` - 模型
- `particles` - 粒子
- `render_controllers` - 渲染控制器
- `sounds` - 声音
- `textures` - 纹理

## 🔧 开发

### 构建项目

```bash
# 编译 TypeScript
npm run build

# 开发模式（监听文件变化）
npm run dev
```

### 代码结构

生成器采用模块化设计：

1. **SchemaLoader** - 负责加载和解析 JSON Schema，处理 `$ref` 引用
2. **VersionDetector** - 自动检测 Schema 版本并分组
3. **TypeGenerator** - 使用 `json-schema-to-typescript` 生成类型定义
4. **OutputManager** - 管理文件输出和目录结构

## 📝 注意事项

1. **生成的文件** - `types/` 目录下的所有文件都是自动生成的，请勿手动修改
2. **版本检测** - 版本信息从 Schema 的 `format_version` 字段提取
3. **循环引用** - 生成器会自动处理 Schema 中的循环引用
4. **复杂类型** - 对于特别复杂的 Schema，可能需要手动调整生成选项

## 🤝 贡献

欢迎提交 Issue 和 Pull Request！

## 📄 许可证

MIT License

## 🔗 相关项目

- [minecraft-bedrock-json-schemas](https://github.com/Mojang/bedrock-samples) - Minecraft 官方 JSON Schema
- [json-schema-to-typescript](https://github.com/bcherny/json-schema-to-typescript) - JSON Schema 转 TypeScript 工具
- [nukkit-addon-bridge](../) - Nukkit Add-on 桥接插件（父项目）

## 📞 支持

如有问题，请在 GitHub Issues 中提交。
