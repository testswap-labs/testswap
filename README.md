# TestSwap.org：为开发者而生的 DeFi 测试沙盒

一个面向 Web3 开发者的在线平台，提供测试网交易工具、合约交互控制台、主网 Fork 模拟器、调试与分析工具，帮助开发者快速验证、调试、部署和模拟真实 DeFi 场景。

## 目录
- 概述
- 核心功能
- 技术架构
- 项目目录结构
- 快速开始
- 开发与运行
- API 参考
- 路线图（MVP → V1）
- 商业化路径
- 技术栈
- 许可证

## 概述
TestSwap.org 致力于为开发者提供一站式的 DeFi 实验与验证平台：从测试网 Swap、合约交互，到主网 Fork 环境的创建与调试分析，覆盖开发、调试、教学、演示等全流程。

> Badges（可选，占位）：Build | License | Java | Node | Docs
> 例如：`![Build](https://img.shields.io/badge/build-passing-brightgreen)`

## 核心功能

| 模块名称 | 功能定位 |
| --- | --- | 
| Swap 模拟工具 | 在测试网上模拟真实代币交换 | 
| 合约交互控制台 | 上传 ABI，调用任意函数 | 
| 一键部署标准合约 | 部署 ERC20、池子、FlashLoan 等 | 
| 主网 Fork 测试环境 | 在本地 fork 主网，模拟真实交易逻辑 | 
| 交易可视化与日志分析 | 提供交易 trace、状态变更、调试日志 | 
| API & SDK 接口 | 开放后端服务供脚本使用 | 

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

> Postman/Insomnia：后续可提供导入文件（占位：`docs/api.postman_collection.json`）。

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

> Windows 环境变量设置示例（PowerShell）
> - `$env:SERVER_PORT=8080; mvn spring-boot:run`
> - 构建后运行：`$env:SERVER_PORT=8080; java -jar target/testswap-api-*.jar`

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
  - 建议的 mapper XML 目录：`testswap-api/src/main/resources/mapper/**/*.xml`
  - 安全与限流（环境变量，可选）：
    - `TESTSWAP_API_KEY`：API Key（设置后需通过请求头 `X-API-Key` 鉴权；未设置则不启用鉴权）
    - `TESTSWAP_RATE_LIMIT_PER_MIN`：每分钟限流次数（默认 60，基于客户端 IP）
- 调试
  - 开启 trace：`debug_traceTransaction`
  - 事务回放：对关键调用打点记录 & 复现
- 测试
  - 后端：Java `JUnit` / `Spring Boot Test`
  - 合约：Foundry `forge test`
  - 前端：Playwright / Vitest（视脚手架而定）

## API 参考

> 当前 API 为占位实现，便于前端联调与演示；返回示例与结构已稳定，可逐步替换为真实逻辑。

> 在线文档：启动后访问 Swagger UI
> - `http://localhost:8080/swagger-ui.html`（将重定向至）
> - `http://localhost:8080/swagger-ui/index.html`

- 元信息与健康检查
  - GET `/` → `{ name, status }`
  - GET `/healthz` → `{ status }`
  - GET `/version` → `{ version }`
  - GET `/api/ping` → `{ message: "pong" }`

- Fork 管理
  - POST `/api/forks`
    - 请求体：
      ```json
      {
        "label": "local-fork",
        "rpcUrl": "http://localhost:8545", // 可选，缺省使用 testswap.rpc.url
        "baseBlock": 21000000                 // 可选
      }
      ```
    - 响应体：
      ```json
      {
        "id": "<uuid>",
        "label": "local-fork",
        "rpcUrl": "http://localhost:8545",
        "baseBlock": 21000000,
        "createdAt": "2025-01-01T00:00:00Z"
      }
      ```
  - GET `/api/forks` → `Fork[]`
  - DELETE `/api/forks/{id}` → `{ deleted: true }`

- 合约交互（占位模拟）
  - POST `/api/contracts/call`
    - 请求体（示例）：
      ```json
      {
        "address": "0x...",
        "method": "balanceOf",
        "params": { "account": "0x..." },
        "rpcUrl": "http://localhost:8545"
      }
      ```
    - 响应体：`{ success, message, data: { address, method, params, rpcUrl } }`
  - POST `/api/contracts/send`
    - 请求体（示例）：
      ```json
      {
        "from": "0x...",
        "address": "0x...",
        "method": "transfer",
        "params": { "to": "0x...", "amount": "1000000000000000000" },
        "value": "0"
      }
      ```
    - 响应体：`{ success, message, data: { txHash, ... } }`
  - POST `/api/contracts/deploy`
    - 请求体（示例）：
      ```json
      {
        "template": "ERC20",
        "args": { "name": "TST", "symbol": "TST", "supply": "1000000" }
      }
      ```
    - 响应体：`{ success, message, data: { address, ... } }`

- 交易 Trace（占位模拟）
  - POST `/api/trace/tx`
    - 请求体：`{ "txHash": "0x...", "rpcUrl": "http://localhost:8545" }`
    - 响应体：`{ success, message, data: { callStack: string[], storageDiff: string[] } }`

- Curl 快速验证
  ```bash
  # 健康
  curl http://localhost:8080/healthz
  # Fork 创建 + 列表 + 删除
  curl -X POST http://localhost:8080/api/forks -H 'Content-Type: application/json' \
    -d '{"label":"local-fork","rpcUrl":"http://localhost:8545","baseBlock":21000000}'
  curl http://localhost:8080/api/forks
  curl -X DELETE http://localhost:8080/api/forks/<id>
  # 合约交互（示例）
  curl -X POST http://localhost:8080/api/contracts/call -H 'Content-Type: application/json' \
    -d '{"address":"0x...","method":"balanceOf","params":{"account":"0x..."}}'
  # Trace
  curl -X POST http://localhost:8080/api/trace/tx -H 'Content-Type: application/json' \
    -d '{"txHash":"0x..."}'
  
  # 若设置了 API Key，需带上请求头
  # export APIK=your_api_key
  curl -H "X-API-Key: $APIK" http://localhost:8080/healthz
  ```

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

## 故障排查（Troubleshooting）

- 端口被占用
  - 后端：修改 `SERVER_PORT` 或 `application.yml` 中的 `server.port`
  - 前端：`npm run dev -- -p 3001`
- 数据库未配置导致启动失败
  - 项目已默认排除 `DataSourceAutoConfiguration`，如仍报错，确认未引入强制依赖；或暂时注释 `spring.datasource.*`
- Java 版本不匹配
  - 当前使用 JDK 8，若引入需要更高版本的依赖会报编译失败；可锁定依赖版本或升级到 Boot 3.x + JDK 17

## 常见问题（FAQ）

- 是否必须有数据库？
  - 否。当前后端可在无数据库下启动；只有需要持久化时才配置数据源与 MyBatis-Plus 映射。
- 能否在本地没有节点的情况下使用？
  - 可以。可使用公有节点（Infura/Alchemy），或本地 `anvil` 启动 fork 环境。
- 生产环境是否安全？
  - 当前 API 多为占位，未进行鉴权、限流与安全加固。生产前需补充认证、审计与安全策略。

## 贡献（Contributing）

- 分支策略：`main`（稳定）/ `develop`（集成）/ `feature/*`（特性）
- 提交规范：`feat: ...`、`fix: ...`、`docs: ...`、`refactor: ...`
- PR 要求：说明动机、变更范围、测试方式、对 README 的影响

## 安全与隐私（Security & Privacy）

- 不要在代码库中提交私钥、助记词、RPC 凭据等敏感信息
- 推荐通过环境变量或密钥管理服务注入敏感配置
- 对公开 API 应添加鉴权、速率限制与审计日志

## 版本策略（Versioning）

- 采用语义化版本（SemVer）：`MAJOR.MINOR.PATCH`
- 重大变更需在 RELEASE NOTES 中列明迁移指南

## 许可证
本项目采用 MIT License，见 [LICENSE](./LICENSE) 文件。