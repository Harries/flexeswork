# Flexes - 远程工程招聘平台

<div align="center">
  <img src="docs/images/logo.png" alt="Flexes Logo" width="200"/>

  <p><strong>专业的远程工程招聘平台，连接优秀的工程师人才与远程工作机会</strong></p>

  [![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
  [![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.java.net/projects/jdk/17/)
  [![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.18-green.svg)](https://spring.io/projects/spring-boot)
  [![React](https://img.shields.io/badge/React-18.2.0-blue.svg)](https://reactjs.org/)
  [![TypeScript](https://img.shields.io/badge/TypeScript-4.9.5-blue.svg)](https://www.typescriptlang.org/)
</div>

## 📋 项目概述

Flexes 是一个现代化的远程工程招聘平台，专注于为工程师和技术公司提供高质量的远程工作匹配服务。平台采用前后端分离架构，提供完整的招聘流程管理功能。

### 🎯 核心功能

- **智能职位匹配**: 基于技能、经验和偏好的智能推荐系统
- **完整招聘流程**: 从职位发布到面试安排的全流程管理
- **多角色支持**: 求职者、雇主、管理员三种角色权限管理
- **实时通信**: 内置消息系统，支持实时沟通
- **数据分析**: 招聘数据统计和分析报告
- **移动端适配**: 响应式设计，支持移动设备访问

### 🏗️ 技术架构

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   前端 (React)   │────│   后端 (Spring)  │────│   数据库 (MySQL) │
│                 │    │                 │    │                 │
│ • React 18      │    │ • Spring Boot   │    │ • MySQL 8.0     │
│ • TypeScript    │    │ • Spring Security│    │ • Redis 6.0     │
│ • Ant Design    │    │ • JWT 认证       │    │ • 数据持久化     │
│ • Redux Toolkit │    │ • RESTful API   │    │ • 缓存管理       │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

## 🚀 快速开始

### 环境要求

- **Java**: 8+
- **Node.js**: 16+
- **MySQL**: 8.0+
- **Redis**: 6.0+
- **Maven**: 3.8+
- **Git**: 2.30+

### 一键启动

```bash
# 克隆项目
git clone https://github.com/your-username/flexes-platform.git
cd flexes-platform

# 启动所有服务 (需要 Docker)
docker-compose up -d

# 或者分别启动各个服务
./start-all.sh
```

### 手动部署

#### 1. 数据库初始化

```bash
# 创建数据库
mysql -u root -p
CREATE DATABASE flexes_platform CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

# 导入初始数据
mysql -u root -p flexes_platform < docs/sql/flexes_database.sql
```

#### 2. 启动后端服务

```bash
cd flexes-backend

# 配置环境变量
cp src/main/resources/application-dev.yml.example src/main/resources/application-dev.yml
# 编辑配置文件，设置数据库连接等信息

# 构建并启动
mvn clean package -DskipTests
java -jar target/flexes-backend-1.0.0.jar --spring.profiles.active=dev
```

#### 3. 启动前端服务

```bash
cd flexes-frontend

# 安装依赖
npm install

# 配置环境变量
cp .env.example .env
# 编辑 .env 文件，设置 API 地址等信息

# 启动开发服务器
npm start
```

### 访问应用

- **前端应用**: http://localhost:3000
- **后端API**: http://localhost:8081/api
- **API文档**: http://localhost:8081/api/swagger-ui.html
- **健康检查**: http://localhost:8081/api/actuator/health

## 📁 项目结构

```
flexes-platform/
├── flexes-backend/          # 后端服务 (Spring Boot)
│   ├── src/main/java/       # Java 源代码
│   ├── src/main/resources/  # 配置文件
│   ├── src/test/           # 测试代码
│   └── pom.xml             # Maven 配置
├── flexes-frontend/         # 前端应用 (React)
│   ├── src/                # React 源代码
│   ├── public/             # 静态资源
│   └── package.json        # npm 配置
├── docs/                   # 项目文档
│   ├── 1.requirements/     # 需求文档
│   ├── 2.analysis/         # 分析文档
│   ├── 3.design/          # 设计文档
│   └── sql/               # 数据库脚本
├── docker-compose.yml      # Docker 编排
└── README.md              # 项目说明
```

## 🔧 开发指南

### 后端开发

```bash
cd flexes-backend

# 运行测试
mvn test

# 代码检查
mvn checkstyle:check

# 生成测试报告
mvn jacoco:report
```

### 前端开发

```bash
cd flexes-frontend

# 代码检查
npm run lint

# 类型检查
npm run type-check

# 运行测试
npm test

# 构建生产版本
npm run build
```

### 代码规范

- **后端**: 遵循 Google Java Style Guide
- **前端**: 使用 ESLint + Prettier 进行代码格式化
- **提交**: 使用 Conventional Commits 规范

## 📊 功能模块

### 用户管理
- 用户注册/登录
- 个人资料管理
- 权限控制

### 职位管理
- 职位发布/编辑
- 职位搜索/筛选
- 职位推荐

### 申请管理
- 简历投递
- 申请状态跟踪
- 面试安排

### 消息系统
- 实时消息通知
- 邮件提醒
- 系统公告

## 🔒 安全特性

- **JWT 认证**: 无状态身份验证
- **RBAC 权限**: 基于角色的访问控制
- **数据加密**: 敏感数据加密存储
- **SQL 注入防护**: 参数化查询
- **XSS 防护**: 输入输出过滤
- **CSRF 防护**: 跨站请求伪造防护

## 📈 性能优化

- **数据库优化**: 索引优化、查询优化
- **缓存策略**: Redis 缓存热点数据
- **CDN 加速**: 静态资源 CDN 分发
- **代码分割**: 前端代码按需加载
- **图片优化**: 图片压缩和懒加载

## 🧪 测试

### 后端测试
- **单元测试**: JUnit 5 + Mockito
- **集成测试**: TestContainers
- **API 测试**: REST Assured

### 前端测试
- **单元测试**: Jest + React Testing Library
- **端到端测试**: Cypress
- **组件测试**: Storybook

## 📚 文档

- [需求分析](docs/1.requirements/)
- [系统设计](docs/3.design/)
- [API 文档](docs/3.design/api-documentation.md)
- [部署指南](docs/3.design/deployment.md)
- [开发规范](docs/3.design/development-standards.md)

## 🐳 Docker 部署

### 使用 Docker Compose

```bash
# 启动所有服务
docker-compose up -d

# 查看服务状态
docker-compose ps

# 查看日志
docker-compose logs -f

# 停止服务
docker-compose down
```

### 单独构建镜像

```bash
# 构建后端镜像
cd flexes-backend
docker build -t flexes-backend:latest .

# 构建前端镜像
cd flexes-frontend
docker build -t flexes-frontend:latest .
```

## 🔧 配置说明

### 环境变量

#### 后端环境变量
```bash
# 数据库配置
DB_HOST=localhost
DB_PORT=3306
DB_USERNAME=flexes_user
DB_PASSWORD=flexes_password

# Redis 配置
REDIS_HOST=localhost
REDIS_PORT=6379
REDIS_PASSWORD=redis_password

# JWT 配置
JWT_SECRET=your-jwt-secret-key
JWT_EXPIRATION=86400000

# 应用配置
SPRING_PROFILES_ACTIVE=prod
SERVER_PORT=8081
```

#### 前端环境变量
```bash
# API 配置
REACT_APP_API_BASE_URL=http://localhost:8081/api
REACT_APP_API_TIMEOUT=10000

# 功能开关
REACT_APP_ENABLE_MOCK=false
REACT_APP_ENABLE_DEVTOOLS=false

# 业务配置
REACT_APP_DAILY_APPLICATION_LIMIT=20
REACT_APP_MAX_FILE_SIZE=5242880
```

## 📊 监控和日志

### 应用监控
- **Spring Boot Actuator**: 应用健康检查和指标
- **Micrometer**: 应用指标收集
- **Prometheus**: 指标存储和查询
- **Grafana**: 指标可视化

### 日志管理
- **Logback**: 日志框架
- **ELK Stack**: 日志收集和分析
- **日志级别**: 支持动态调整日志级别

## 🚀 部署指南

### 生产环境部署

1. **服务器要求**
   - CPU: 4核心以上
   - 内存: 8GB以上
   - 存储: 100GB以上
   - 操作系统: Ubuntu 20.04+ / CentOS 8+

2. **部署步骤**
   ```bash
   # 1. 克隆代码
   git clone https://github.com/your-username/flexes-platform.git
   cd flexes-platform

   # 2. 配置环境变量
   cp .env.production.example .env.production
   # 编辑 .env.production 文件

   # 3. 启动服务
   docker-compose -f docker-compose.prod.yml up -d

   # 4. 初始化数据库
   docker-compose exec backend java -jar app.jar --spring.profiles.active=prod --init-db
   ```

3. **反向代理配置 (Nginx)**
   ```nginx
   server {
       listen 80;
       server_name your-domain.com;

       location / {
           proxy_pass http://localhost:3000;
           proxy_set_header Host $host;
           proxy_set_header X-Real-IP $remote_addr;
       }

       location /api {
           proxy_pass http://localhost:8081;
           proxy_set_header Host $host;
           proxy_set_header X-Real-IP $remote_addr;
       }
   }
   ```

## 🤝 贡献指南

我们欢迎所有形式的贡献！请遵循以下步骤：

1. **Fork 项目**
2. **创建功能分支** (`git checkout -b feature/AmazingFeature`)
3. **提交更改** (`git commit -m 'Add some AmazingFeature'`)
4. **推送到分支** (`git push origin feature/AmazingFeature`)
5. **创建 Pull Request**

### 代码贡献规范

- 遵循现有的代码风格
- 添加适当的测试用例
- 更新相关文档
- 确保所有测试通过

### 问题报告

如果发现 bug 或有功能建议，请：

1. 检查是否已有相关 issue
2. 使用 issue 模板创建新问题
3. 提供详细的复现步骤
4. 包含相关的错误日志

## 📄 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

## 👥 团队

- **项目负责人**: [Your Name] - 项目架构和管理
- **后端开发**: [Backend Developer] - Spring Boot 开发
- **前端开发**: [Frontend Developer] - React 开发
- **UI/UX 设计**: [Designer] - 界面设计
- **DevOps 工程师**: [DevOps Engineer] - 部署和运维

## 📞 联系我们

- **邮箱**: contact@flexes.work
- **官网**: https://flexes.work
- **问题反馈**: [GitHub Issues](https://github.com/your-username/flexes-platform/issues)
- **技术支持**: support@flexes.work

## 🙏 致谢

感谢以下开源项目和社区的支持：

- [Spring Boot](https://spring.io/projects/spring-boot) - 后端框架
- [React](https://reactjs.org/) - 前端框架
- [Ant Design](https://ant.design/) - UI 组件库
- [MySQL](https://www.mysql.com/) - 数据库
- [Redis](https://redis.io/) - 缓存数据库

---

<div align="center">
  <p>Made with ❤️ by Flexes Team</p>
  <p>© 2025 Flexes Platform. All rights reserved.</p>
</div>