# 版本回退策略 (Version Fallback Strategy)

## 概述

当解析behavior pack的JSON文件时，如果`format_version`字段指定的版本不在我们直接支持的版本列表中，系统会自动查找**最接近的较低版本**进行解析，然后通过升级链路升级到最新版本。

## 支持的版本列表

目前直接支持的版本（从旧到新）：

1. `1.19.0` (2022-08-05)
2. `1.19.40` (2022-09-08)
3. `1.19.50` (2022-10-29)
4. `1.20.10` (2023-03-09)
5. `1.20.41` (2023-08-17)
6. `1.20.81` (2024-05-11)
7. `1.21.50` (2024-12-05)
8. `1.21.60` (2024-12-24) ← 当前最新

## 版本匹配规则

### 规则 1: 精确匹配
如果`format_version`恰好是支持的版本之一，直接使用该版本的DTO进行解析。

**示例**：
- `format_version: "1.20.41"` → 使用 `v1_20_41` DTO

### 规则 2: 最接近的较低版本
如果`format_version`不在支持列表中，查找**最大的但不超过目标版本**的版本。

**示例**：
```
format_version: "1.19.10"
  → 查找: 1.19.0 ≤ 1.19.10
  → 使用: v1_19_0

format_version: "1.19.60"
  → 查找: 1.19.50 ≤ 1.19.60 < 1.20.10
  → 使用: v1_19_50

format_version: "1.20.0"
  → 查找: 1.19.50 ≤ 1.20.0 < 1.20.10
  → 使用: v1_19_50

format_version: "1.20.50"
  → 查找: 1.20.41 ≤ 1.20.50 < 1.20.81
  → 使用: v1_20_41

format_version: "1.21.80"
  → 查找: 1.21.60 ≤ 1.21.80
  → 使用: v1_21_60 (最新版本)
```

### 规则 3: 版本过旧
如果`format_version`小于所有支持的版本，使用最旧的支持版本。

**示例**：
```
format_version: "1.18.30"
  → 小于最旧版本 1.19.0
  → 使用: v1_19_0
  → 警告: "format_version 1.18.30 is older than all supported versions, using 1.19.0"
```

### 规则 4: 无效版本
如果`format_version`格式无效或为null，使用默认版本（最新版本）。

**示例**：
```
format_version: null
  → 使用: v1_21_60 (DEFAULT)

format_version: "invalid"
  → 使用: v1_21_60 (DEFAULT)
  → 警告: "Invalid format_version 'invalid', using default 1.21.60"
```

## 完整解析流程

```
JSON文件 (format_version: "1.20.50")
    ↓
1. 解析format_version字符串
    ↓
2. 查找最接近的版本: 1.20.41
    ↓
3. 使用v1_20_41 DTO反序列化
    ↓
4. 自动升级链路:
   v1.20.41 → v1.20.81 → v1.21.50 → v1.21.60
    ↓
5. 最终得到v1.21.60 DTO
    ↓
6. 转换为应用层Def对象
```

## 日志输出示例

### 精确匹配（无日志）
```
format_version: "1.20.41"
→ (无额外日志，直接使用)
```

### 版本回退（INFO日志）
```
format_version: "1.20.50"
→ [INFO] format_version 1.20.50 not directly supported, using closest version 1.20.41
→ [INFO] Starting upgrade from 1.20.41 to 1.21.60
→ [DEBUG] Upgrading from 1.20.41 to 1.20.81
→ [DEBUG] Upgrading from 1.20.81 to 1.21.50
→ [DEBUG] Upgrading from 1.21.50 to 1.21.60
→ [INFO] Upgrade completed successfully with no warnings
```

### 版本过旧（WARNING日志）
```
format_version: "1.18.0"
→ [WARN] format_version 1.18.0 is older than all supported versions, using 1.19.0
→ [INFO] Starting upgrade from 1.19.0 to 1.21.60
→ ...
```

### 无效版本（WARNING日志）
```
format_version: "not-a-version"
→ [WARN] Invalid format_version 'not-a-version', using default 1.21.60
```

## 优势

1. **向后兼容性**：支持更多的Minecraft版本，即使没有对应的DTO
2. **自动降级**：自动选择最合适的版本进行解析
3. **透明升级**：用户无需关心版本差异，系统自动处理
4. **日志可追溯**：所有版本选择和升级过程都有详细日志
5. **失败友好**：即使版本不匹配，也会尝试最佳方案而不是直接失败

## 实现细节

- **查找算法**：线性扫描已排序的版本数组，时间复杂度 O(n)，n为支持版本数（当前为8）
- **缓存策略**：版本映射表使用静态Map缓存，避免重复反射
- **线程安全**：所有静态数据为不可变，天然线程安全

## 未来扩展

当Minecraft发布新版本时，只需：
1. 运行`npm run generate:all`生成新版本DTO
2. 在`SUPPORTED_VERSIONS`数组中添加新版本常量
3. 在各个`*VersionUpgrader`中注册新的升级步骤

大多数情况下，使用`GenericUpgradeStep`即可自动处理新版本。
