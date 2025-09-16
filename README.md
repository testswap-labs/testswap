# TestSwap.org：为开发者而生的 DeFi 测试沙盒

一个面向 Web3 开发者的在线平台，提供测试网交易工具、合约交互控制台、主网 Fork 模拟器、调试与分析工具，帮助开发者快速验证、调试、部署和模拟真实 DeFi 场景。

## 目录
- 概述
- 核心功能
- 技术架构
- 项目目录结构
- 快速开始
- 开发与运行
- 路线图（MVP → V1）
- 商业化路径
- 技术栈
- 许可证

## 概述
TestSwap.org 致力于为开发者提供一站式的 DeFi 实验与验证平台：从测试网 Swap、合约交互，到主网 Fork 环境的创建与调试分析，覆盖开发、调试、教学、演示等全流程。

## 核心功能

| 模块名称 | 功能定位 | 来源方向 |
| --- | --- | --- |
| Swap 模拟工具 | 在测试网上模拟真实代币交换 | ✅ 方向一 |
| 合约交互控制台 | 上传 ABI，调用任意函数 | ✅ 方向二 |
| 一键部署标准合约 | 部署 ERC20、池子、FlashLoan 等 | ✅ 方向二 |
| 主网 Fork 测试环境 | 在本地 fork 主网，模拟真实交易逻辑 | ✅ 方向二 |
| 交易可视化与日志分析 | 提供交易 trace、状态变更、调试日志 | ✅ 方向二 |
| API & SDK 接口 | 开放后端服务供脚本使用 | ✅ 方向一 & 二 |

### 详细能力
1. 测试网 Swap 工具
   - 钱包连接（测试网：Sepolia、Holesky）
   - 输入 token0 / token1 地址
   - 自动或手动选择 fee tier
   - 发起 `swapExactInputSingle`
   - 提供模拟输出、slippage、gas 成本显示

2. 合约交互控制台
   - 上传 ABI 或选择标准模板
   - 显示函数列表 + 参数输入框
   - 支持 call 和 sendTransaction
   - 读取 `storage` / `view` 方法自动处理

3. 合约快速部署
   - 一键部署：ERC20、Uniswap Pool、Vesting 等
   - 自定义参数（name、supply、fee 等）
   - 自动记录部署地址、生成交互链接

4. 主网 Fork 模拟器
   - 选择主网 block 号 → 创建 fork 实例（Anvil）
   - 分配测试 ETH / 自定义代币余额
   - 在 fork 上测试 swap、部署、策略验证
   - 实时销毁 / 重启 fork 环境

5. 交易调试与分析
   - 展示交易 receipt / gas 使用 / revert 原因
   - 使用 `debug_traceTransaction` 获取 call stack、storage 变化
   - 高亮常见错误（如 `INSUFFICIENT_OUTPUT_AMOUNT`）

6. API / SDK 支持
   - 提供 API（合约调用、部署、trace 等）
   - SDK（Go / JS）封装调用流程，支持本地与 CI

## 技术架构

```text
User (Developer)
  └─ Front-end (Next.js + Wagmi)
       ├─ Swap UI (Testnet / Fork)
       ├─ Contract Interaction Console
       ├─ Fork Manager Interface
       └─ Token Deployment Wizard

Back-end (Java Spring Boot API)
  ├─ Fork Manager (Anvil Containers)
  ├─ Contract Execution Engine
  ├─ RPC Proxy Layer (Testnet / Fork)
  ├─ Transaction Trace & Analysis
  └─ Deployment Service

Blockchain Layer
  ├─ Public Testnets (Sepolia, Holesky)
  └─ Local Forks (Anvil, Hardhat, Foundry)
```

## 项目目录结构

```text
/ (repo root)
├─ testswap-api/                 # 后端（Java / Spring Boot + MyBatis-Plus）
│  ├─ pom.xml                   # Maven 构建文件
│  └─ src/
│     ├─ main/
│     │  ├─ java/...            # 源码目录（com.testswap.api 等）
│     │  │  ├─ controller/      # 控制器层（REST API）
│     │  │  ├─ service/         # 业务层
│     │  │  ├─ mapper/          # MyBatis-Plus Mapper 接口
│     │  │  └─ entity/          # 实体（DO/PO）
│     │  └─ resources/
│     │     └─ application.yml  # 应用配置（端口、RPC、数据库等）
│     └─ test/                  # 单元测试（JUnit）
├─ testswap-ui/                  # 前端（Next.js，预留）
├─ README.md
└─ .idea/                  # IDE 配置
```

## 快速开始
> 当前代码库为结构化初始化阶段，后端/前端实现将逐步补全。以下为建议的环境与启动方式，具体命令会在实现落地后补充。

- 前置要求
  - Java 8（JDK 8）
  - Maven 3.6+
  - Node.js 18+ / pnpm 8+
  - Foundry（含 anvil），或等效本地链
  - 可用的以太坊节点服务（Infura/Alchemy/自建 Geth）

- 克隆与安装
  ```bash
  git clone https://github.com/your-org/testswap.git
  cd testswap
  ```

- 启动本地 fork（示例）
  ```bash
  anvil --fork-url $MAINNET_RPC --fork-block-number 21000000
  ```

- 启动后端（Spring Boot，Maven）
  ```bash
  cd testswap-api
  # 方式一：开发运行
  SERVER_PORT=8080 mvn spring-boot:run

  # 方式二：构建可执行 JAR 并运行
  mvn clean package -DskipTests
  SERVER_PORT=8080 java -jar target/testswap-api-*.jar
  ```

- 启动前端（占位说明）
  ```bash
  cd testswap-ui
  npm install
  npm run dev
  ```

## 开发与运行
- 配置
  - 环境变量：RPC_URL、FORK_BLOCK、CHAIN_ID、DATABASE_URL（可选）
  - 针对 Fork 管理：容器化 anvil 实例或进程级管理
  - 前后端统一配置：
    - 后端：`SERVER_PORT`（默认 8080）
    - 前端：`NEXT_PUBLIC_API_BASE`（默认 `http://localhost:8080`）
  - Spring Boot 常用配置（application.yml 或环境变量）：
    - `spring.datasource.url` / `spring.datasource.username` / `spring.datasource.password`（可选）
    - `testswap.rpc.url`（后端访问以太坊节点的 RPC）
    - `testswap.fork.block`（Fork 基准区块，可选）
  - MyBatis-Plus 配置（如使用数据库功能）：
    - `mybatis-plus.mapper-locations: classpath*:/mapper/**/*.xml`（如使用 XML Mapper）
    - `mybatis-plus.type-aliases-package: com.testswap.api.entity`
    - `mybatis-plus.configuration.map-underscore-to-camel-case: true`
  - 兼容性说明：
    - 建议后端基线为 Spring Boot 2.7.x（兼容 JDK 8）。
    - 如需升级到 Spring Boot 3.x，需要 Java 17+，届时请同步升级到 JDK 17+ 并适配 Jakarta 命名空间变更。
- 调试
  - 开启 trace：`debug_traceTransaction`
  - 事务回放：对关键调用打点记录 & 复现
- 测试
  - 后端：Java `JUnit` / `Spring Boot Test`
  - 合约：Foundry `forge test`
  - 前端：Playwright / Vitest（视脚手架而定）

## 路线图（MVP → V1）

- 阶段 1：MVP 测试版（进行中）
  - 钱包连接
  - 测试网 Swap 工具
  - 合约交互控制台
  - 部署 ERC20 合约
  - 简单的后端服务 + RPC 转发

- 阶段 2：功能增强（计划）
  - Fork 测试管理器（支持多个实例）
  - Gas / Trace 分析
  - 部署池子合约 + Swap 策略模拟
  - 导出调用脚本（支持 SDK）

- 阶段 3：商业化基础（计划）
  - 注册 / 登录 / 项目管理
  - 配额限制（fork 时间 / API 次数）
  - 高级套餐（更大内存、更多节点）
  - 团队账户 / 教学支持

## 商业化路径

| 模式 | 示例 |
| --- | --- |
| 高级订阅 | $9/月：更多 Fork 实例、更长运行时间 |
| API 接入 | 提供策略模拟 API，按调用量计费 |
| 教育合作 | DAO / 学校教学，提供定制版 |
| 白标部署 | 给 DeFi 团队部署专属版本 |

## 技术栈

| 组件 | 技术选型 |
| --- | --- |
| 前端 | Next.js + TailwindCSS + Wagmi + Ethers.js |
| 后端 | Java + Spring Boot + MyBatis-Plus + PostgreSQL（可选） |
| 合约 | Solidity + Foundry |
| Fork 引擎 | Anvil（Foundry 内置） |
| 区块链服务 | Infura / Alchemy / 自建 Geth |
| 部署 | Docker + Fly.io / Railway / Vercel |

## 许可证
本项目将采用 MIT License（待补充 LICENSE 文件）。