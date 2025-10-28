# Nukkit Addon Bridge

> **⚠️ WIP (Work In Progress)** - 项目正在积极开发中，核心功能尚未完全实现。

面向 Nukkit 系列分支（Cloudburst/Nukkit、PM1E、Nukkit‑MOT、PowerNukkitX）的"基岩版 Add‑on 桥接插件"。
将 Add‑on 的 JSON 解析为通用模型（Common Model），通过"分支适配器"完成服务器注册，并统一打包与下发资源包。

**当前状态**：最小可运行骨架（MVP）。解析仅覆盖自定义方块和实体；适配器实现为 Demo；DTO 代码生成器已完成并可正常使用。

## 功能概览
- 扫描插件数据目录 `addons/`，读取每个包的 `manifest.json`（提取 uuid/name/version）。
- 通用模型承载 items/blocks/entities/recipes 结构（占位，便于扩展）。
- 打包资源文件为 ZIP、计算 SHA1，并写入 `resource_packs.yml`（通用部署器）。
- 运行期自动检测服务器分支并选择适配器（Cloudburst/PN/PM1E/MOT/PNX）。

## 架构与模块
```
nukkit-addon-bridge/
├─ addon-bridge-core/   # 核心：通用模型、SPI、解析器、依赖排序、默认桥接
│   └─ src/main/java/net/easecation/bridge/core/dto/  # 自动生成的 DTO 类
├─ codegen/             # TypeScript 代码生成器：从 JSON Schema 生成 Java DTO
├─ schemas/             # Minecraft Bedrock JSON Schema 定义（Git Submodule）
├─ addon-pack-tools/    # 工具：ZIP 打包、SHA1、resource_packs.yml 写入
├─ adapter-* /          # 分支适配器（EaseCation/Cloudburst/PM1E/MOT/PNX），MVP 仅日志占位
└─ plugin-runner/       # 插件入口（PluginBase）：装配 core + tools + 适配器
```

关键设计
- Core 不依赖 Nukkit API；适配器以 compileOnly 引入，运行期由服务器提供。
- 不可映射特性将记录并优雅降级（后续版本完善）。
- 资源下发支持本地 ZIP+SHA1；可通过 `BRIDGE_RP_BASE_URL` 注入 CDN 前缀。

## DTO 自动生成

`codegen/` 模块从 Minecraft 基岩版 JSON Schema（Git Submodule）自动生成 Java 17 DTO 类（使用 record、sealed interface），输出至 `addon-bridge-core/src/main/java/net/easecation/bridge/core/dto/`。

使用方法：
```bash
git submodule update --init --recursive  # 首次克隆需初始化
cd codegen && npm install && npm run generate
```

Schema 更新或升级 Minecraft 版本时需重新生成。

## plugin-runner 工作原理
1) 服务器加载插件 → `BridgeBootstrap#onEnable()` 执行。
2) 适配器选择：`AdapterFactory` 按特征类检测依次选择 PNX → MOT → PowerNukkit → PM1E → Cloudburst/Nukkit；否则回退 `NoopRegistry`。
3) 组装桥接器：`DefaultAddonBridge(parser, deps, registry, deployer)`。
4) 扫描 `plugins/AddonBridge/addons/` → 解析 `manifest.json` → 简单依赖顺序（保持扫描顺序）。
5) 对每个包执行：打包资源 ZIP（+SHA1，写 `resource_packs.yml`）→ 调用适配器注册（MVP 为日志占位）。

数据目录与产物
- 输入：`plugins/AddonBridge/addons/`
- 输出：`plugins/AddonBridge/packs/*.zip`、`plugins/AddonBridge/resource_packs.yml`
- 日志：
  - 控制台：`PluginLogger`（info/error）
  - 内部组件：`java.util.logging.Logger`
 - 适配器覆盖：环境变量 `BRIDGE_FORCE_ADAPTER`（easecation/pnx/mot/pm1e/cloudburst）。

## 适配器依赖来源
- EaseCation Nukkit：`com.github.EaseCation:Nukkit:<tag-with-*-SNAPSHOT>`（JitPack）
  - 可通过 `-PeasecationNukkitTag=<branch-or-tag-SNAPSHOT>` 覆盖版本（默认 `master-SNAPSHOT`）
- Cloudburst/Nukkit：`cn.nukkit:nukkit:1.0-SNAPSHOT`（opencollab 仓库）
- Nukkit‑MOT：`cn.nukkit:Nukkit:MOT-SNAPSHOT`（LANink 仓库 `https://repo.lanink.cn/repository/maven-public/`）
- PowerNukkitX：`com.github.PowerNukkitX:PowerNukkitX:master-SNAPSHOT`（JitPack）
- PM1E：使用服务器生成的 `patched.jar`（无 Maven 构件）
  - 将服务器 `.cache/patched.jar` 复制到 `adapter-pm1e/lib/patched.jar`，或构建时指定：
    - `./gradlew -PPM1E_PATCHED_JAR=/absolute/path/to/patched.jar :adapter-pm1e:assemble`

说明：以上依赖在适配器中均为 compileOnly，胖包不会包含这些库；运行期由目标服务器提供。

## 构建与打包
前置：JDK 21+、Git 与网络（首次拉取 Wrapper 与依赖）。

常用命令
- 构建全部（跳过测试）：`./gradlew clean build -x test`
- 插件胖包（推荐上服）：`./gradlew :plugin-runner:shadowJar`
  - 产物固定：`plugin-runner/build/libs/nukkit-addon-bridge.jar`
- 仅插件 Jar：`./gradlew :plugin-runner:jar`

可选：将 Gradle 缓存写入项目目录（规避权限限制）
- `GRADLE_USER_HOME=$(pwd)/.gradle-local ./gradlew build -x test`

## 上服使用
1) 将 `plugin-runner/build/libs/nukkit-addon-bridge.jar` 放入服务器 `plugins/`。
2) 启动后在 `plugins/AddonBridge/` 下放置 Add‑on 至 `addons/` 目录。
3) 关注控制台日志（检测到的分支、打包资源 ZIP 路径与 SHA1）。
4) 可设置外链资源前缀：`BRIDGE_RP_BASE_URL=https://cdn.example.com/nukkit/rp/`。

## 当前限制与路线图
- 解析器仅覆盖 `manifest.json` 基本信息；其它 JSON 解析尚未实现。
- 适配器为 Demo 级（日志占位），未实际注册到服务器；后续按分支补齐映射（不再支持 PowerNukkit）。
- 路线图：完善常用字段解析 → 接入实际注册 → 冲突与降级策略 → 热重载与样例包。

## 目录速览
- 构建脚本：`settings.gradle.kts`, `build.gradle.kts`
- 插件入口：`plugin-runner/src/main/java/net/easecation/bridge/runner/BridgeBootstrap.java`
- 通用模型与 SPI：`addon-bridge-core/src/main/java/net/easecation/bridge/core/*`
- 资源打包：`addon-pack-tools/src/main/java/net/easecation/bridge/pack/*`
