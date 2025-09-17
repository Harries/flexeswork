# Flexes 远程工程招聘平台 - 开发工具和环境配置

## 📋 概述

本文档详细描述了 Flexes 远程工程招聘平台的开发环境配置、工具链选择和环境要求。

---

## 🛠️ 开发环境要求

### 系统要求
- **操作系统**: Windows 10/11, macOS 10.15+, Ubuntu 18.04+
- **内存**: 最低 8GB RAM，推荐 16GB+
- **存储**: 至少 10GB 可用空间
- **网络**: 稳定的互联网连接

### 核心开发工具

#### Java 开发环境
- **JDK**: OpenJDK 8 或 Oracle JDK 8
  - 版本要求: `1.8.0_271+`
  - 环境变量: `JAVA_HOME` 配置正确
  - 验证命令: `java -version`

#### Node.js 环境
- **Node.js**: 16.x LTS 版本
  - 版本要求: `16.14.0+`
  - 包管理器: npm 8.x 或 yarn 1.22.x
  - 验证命令: `node --version` 和 `npm --version`

#### 构建工具
- **Maven**: 3.8.6+
  - 配置文件: `~/.m2/settings.xml`
  - 验证命令: `mvn -version`
- **Git**: 2.30+
  - 配置用户信息和SSH密钥

---

## 🗄️ 数据库环境

### MySQL 数据库
- **版本**: MySQL 8.0.33
- **字符集**: utf8mb4
- **存储引擎**: InnoDB
- **端口**: 3306 (默认)

#### 开发环境配置
```sql
-- 创建数据库
CREATE DATABASE flexes_platform 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

-- 创建用户
CREATE USER 'flexes_user'@'localhost' IDENTIFIED BY 'flexes_password';
GRANT ALL PRIVILEGES ON flexes_platform.* TO 'flexes_user'@'localhost';
FLUSH PRIVILEGES;
```

#### 连接配置
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/flexes_platform?useUnicode=true&characterEncoding=utf8mb4&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
    username: flexes_user
    password: flexes_password
    driver-class-name: com.mysql.cj.jdbc.Driver
```

### Redis 缓存
- **版本**: Redis 6.x
- **端口**: 6379 (默认)
- **配置**: 无密码 (开发环境)
- **数据库**: database 0

---

## 🔧 IDE 和编辑器配置

### 推荐 IDE

#### 后端开发 (Java)
- **IntelliJ IDEA Ultimate** (推荐)
  - Spring Boot 插件
  - Lombok 插件
  - MapStruct 插件
  - Database Tools
- **Eclipse IDE** (备选)
  - Spring Tools Suite (STS)

#### 前端开发 (React)
- **Visual Studio Code** (推荐)
  - ES7+ React/Redux/React-Native snippets
  - TypeScript Importer
  - Prettier - Code formatter
  - ESLint
  - Auto Rename Tag
- **WebStorm** (备选)

### IDE 配置文件
```json
// .vscode/settings.json (前端)
{
  "editor.formatOnSave": true,
  "editor.codeActionsOnSave": {
    "source.fixAll.eslint": true
  },
  "typescript.preferences.importModuleSpecifier": "relative",
  "emmet.includeLanguages": {
    "typescript": "html",
    "typescriptreact": "html"
  }
}
```

---

## 📦 依赖管理

### 后端依赖 (Maven)
```xml
<!-- 核心版本 -->
<properties>
    <java.version>8</java.version>
    <spring-boot.version>2.7.18</spring-boot.version>
    <mysql.version>8.0.33</mysql.version>
    <jwt.version>0.11.5</jwt.version>
    <lombok.version>1.18.30</lombok.version>
</properties>
```

### 前端依赖 (npm)
```json
{
  "engines": {
    "node": ">=16.14.0",
    "npm": ">=8.0.0"
  },
  "dependencies": {
    "react": "^18.2.0",
    "typescript": "^4.9.5",
    "antd": "^5.12.8",
    "@reduxjs/toolkit": "^1.9.7"
  }
}
```

---

## 🚀 本地开发环境搭建

### 1. 环境准备
```bash
# 检查 Java 版本
java -version

# 检查 Node.js 版本
node --version

# 检查 Maven 版本
mvn -version

# 检查 Git 配置
git config --global user.name
git config --global user.email
```

### 2. 数据库准备
```bash
# 启动 MySQL 服务
# Windows: net start mysql
# macOS: brew services start mysql
# Linux: sudo systemctl start mysql

# 启动 Redis 服务
# Windows: redis-server
# macOS: brew services start redis
# Linux: sudo systemctl start redis
```

### 3. 项目启动

#### 后端启动
```bash
cd flexes-backend

# 方式1: 使用启动脚本
./start.sh          # Linux/macOS
start.bat           # Windows

# 方式2: 使用 Maven
mvn spring-boot:run

# 方式3: 使用 IDE
# 运行 FlexesApplication.java 主类
```

#### 前端启动
```bash
cd flexes-frontend

# 安装依赖
npm install

# 启动开发服务器
npm start

# 或使用启动脚本
./start.sh          # Linux/macOS
start.bat           # Windows
```

### 4. 验证环境
- 后端服务: http://localhost:8081/api/actuator/health
- 前端应用: http://localhost:3000
- API 文档: http://localhost:8081/api/swagger-ui.html

---

## 🔍 开发工具链

### 代码质量工具

#### 后端 (Java)
- **Checkstyle**: 代码规范检查
- **SpotBugs**: 静态代码分析
- **JaCoCo**: 测试覆盖率
- **SonarQube**: 代码质量分析

#### 前端 (TypeScript/React)
- **ESLint**: 代码规范检查
- **Prettier**: 代码格式化
- **TypeScript**: 类型检查
- **Husky**: Git hooks 管理

### 测试工具

#### 后端测试
- **JUnit 5**: 单元测试框架
- **Mockito**: Mock 测试
- **TestContainers**: 集成测试
- **H2 Database**: 测试数据库

#### 前端测试
- **Jest**: JavaScript 测试框架
- **React Testing Library**: React 组件测试
- **Cypress**: 端到端测试

### API 测试工具
- **Postman**: API 接口测试
- **Insomnia**: REST 客户端
- **Swagger UI**: 在线 API 文档和测试

---

## 🐳 容器化开发

### Docker 环境
- **Docker**: 20.10+
- **Docker Compose**: 2.0+

### 容器化启动
```bash
# 构建并启动所有服务
docker-compose up -d

# 查看服务状态
docker-compose ps

# 查看日志
docker-compose logs -f backend
docker-compose logs -f frontend
```

### 服务端口映射
- **前端**: http://localhost:3000
- **后端**: http://localhost:8081
- **MySQL**: localhost:3306
- **Redis**: localhost:6379

---

## 📊 监控和调试

### 应用监控
- **Spring Boot Actuator**: 应用健康检查
  - Health: `http://localhost:8081/api/actuator/health`
  - Metrics: `http://localhost:8081/api/actuator/metrics`
  - Info: `http://localhost:8081/api/actuator/info`

### 日志配置
```yaml
# 开发环境日志级别
logging:
  level:
    com.flexes: DEBUG
    org.springframework.security: DEBUG
    org.hibernate.SQL: DEBUG
  pattern:
    console: "%clr(%d{HH:mm:ss.SSS}){faint} %clr(%5p) %clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %m%n%wEx"
```

### 调试端口
- **后端调试**: 5005 (JPDA)
- **前端调试**: Chrome DevTools

---

## 🔒 环境变量配置

### 后端环境变量
```bash
# 数据库配置
export DB_HOST=localhost
export DB_PORT=3306
export DB_USERNAME=flexes_user
export DB_PASSWORD=flexes_password

# Redis 配置
export REDIS_HOST=localhost
export REDIS_PORT=6379

# 应用配置
export SPRING_PROFILES_ACTIVE=dev
export JAVA_OPTS="-Xms512m -Xmx1024m"
```

### 前端环境变量
```bash
# .env.development
REACT_APP_ENV=development
REACT_APP_API_BASE_URL=http://localhost:8081/api
REACT_APP_ENABLE_MOCK=true
REACT_APP_LOG_LEVEL=debug
```

---

## 🚦 开发流程

### Git 工作流
1. **克隆仓库**: `git clone <repository-url>`
2. **创建分支**: `git checkout -b feature/your-feature`
3. **开发提交**: 遵循 Conventional Commits 规范
4. **推送分支**: `git push origin feature/your-feature`
5. **创建 PR**: 通过 GitHub/GitLab 创建合并请求

### 提交规范
```bash
# 功能开发
git commit -m "feat: 添加用户注册功能"

# 问题修复
git commit -m "fix: 修复登录验证问题"

# 文档更新
git commit -m "docs: 更新API文档"
```

---

**文档版本**: v1.0  
**创建日期**: 2025-09-17  
**最后更新**: 2025-09-17  
**维护团队**: Flexes 开发团队
