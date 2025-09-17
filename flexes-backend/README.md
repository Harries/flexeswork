# Flexes Backend

Flexes 远程工程招聘平台后端服务，基于 Spring Boot 2.7.x 构建。

## 📋 项目概述

这是一个现代化的招聘平台后端服务，专注于远程工程职位的招聘。采用前后端分离架构，提供完整的 RESTful API 接口。

## 🛠️ 技术栈

- **Java 8** - 编程语言
- **Spring Boot 2.7.x** - 核心框架
- **Spring Security** - 安全框架
- **Spring Data JPA** - 数据访问层
- **MySQL 8.0** - 关系型数据库
- **Redis** - 缓存和会话存储
- **JWT** - 身份认证
- **Maven** - 项目构建工具
- **Docker** - 容器化部署
- **Swagger/OpenAPI 3** - API 文档

## 🏗️ 项目结构

```
flexes-backend/
├── src/main/java/com/flexes/
│   ├── FlexesApplication.java          # 主应用类
│   ├── config/                         # 配置类
│   │   └── SecurityConfig.java         # 安全配置
│   ├── controller/                     # 控制器层
│   │   └── AuthController.java         # 认证控制器
│   ├── dto/                           # 数据传输对象
│   │   ├── ApiResponse.java           # 统一响应格式
│   │   └── auth/                      # 认证相关DTO
│   ├── entity/                        # 实体类
│   │   ├── User.java                  # 用户实体
│   │   ├── Candidate.java             # 求职者实体
│   │   ├── Employer.java              # 雇主实体
│   │   ├── Job.java                   # 职位实体
│   │   └── JobApplication.java        # 申请实体
│   ├── exception/                     # 异常处理
│   │   ├── GlobalExceptionHandler.java # 全局异常处理器
│   │   ├── BusinessException.java      # 业务异常
│   │   └── ResourceNotFoundException.java # 资源未找到异常
│   ├── repository/                    # 数据访问层
│   │   ├── UserRepository.java        # 用户数据访问
│   │   ├── JobRepository.java         # 职位数据访问
│   │   └── JobApplicationRepository.java # 申请数据访问
│   ├── security/                      # 安全相关
│   │   ├── JwtTokenProvider.java      # JWT令牌提供者
│   │   ├── JwtAuthenticationFilter.java # JWT认证过滤器
│   │   ├── JwtAuthenticationEntryPoint.java # JWT认证入口点
│   │   └── UserPrincipal.java         # 用户主体
│   └── service/                       # 业务逻辑层
│       ├── UserService.java           # 用户服务接口
│       └── impl/                      # 服务实现
│           └── UserServiceImpl.java   # 用户服务实现
├── src/main/resources/
│   ├── application.yml                # 主配置文件
│   └── application-dev.yml            # 开发环境配置
├── src/test/                          # 测试代码
├── Dockerfile                         # Docker配置
├── pom.xml                           # Maven配置
└── README.md                         # 项目说明
```

## 🚀 快速开始

### 环境要求

- JDK 8 或更高版本
- Maven 3.6+
- MySQL 8.0+
- Redis 6.0+

### 本地开发

1. **克隆项目**
   ```bash
   git clone <repository-url>
   cd flexes-backend
   ```

2. **配置数据库**
   ```bash
   # 创建数据库
   mysql -u root -p
   CREATE DATABASE flexes_platform CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   
   # 创建用户（可选）
   CREATE USER 'flexes_user'@'localhost' IDENTIFIED BY 'flexes_password';
   GRANT ALL PRIVILEGES ON flexes_platform.* TO 'flexes_user'@'localhost';
   FLUSH PRIVILEGES;
   ```

3. **导入数据库结构**
   ```bash
   mysql -u flexes_user -p flexes_platform < ../docs/sql/flexes_database.sql
   ```

4. **配置环境变量**
   ```bash
   export DB_USERNAME=flexes_user
   export DB_PASSWORD=flexes_password
   export REDIS_HOST=localhost
   export REDIS_PORT=6379
   ```

5. **启动应用**
   ```bash
   mvn spring-boot:run
   ```

6. **访问应用**
   - API 文档: http://localhost:8081/api/swagger-ui.html
   - 健康检查: http://localhost:8081/api/actuator/health

### Docker 部署

1. **构建镜像**
   ```bash
   docker build -t flexes-backend:latest .
   ```

2. **运行容器**
   ```bash
   docker run -d \
     --name flexes-backend \
     -p 8081:8081 \
     -e DB_USERNAME=flexes_user \
     -e DB_PASSWORD=flexes_password \
     -e REDIS_HOST=redis \
     flexes-backend:latest
   ```

### Docker Compose 部署

```yaml
version: '3.8'
services:
  backend:
    build: .
    ports:
      - "8081:8081"
    environment:
      - SPRING_PROFILES_ACTIVE=docker
      - DB_HOST=mysql
      - REDIS_HOST=redis
    depends_on:
      - mysql
      - redis

  mysql:
    image: mysql:8.0
    environment:
      MYSQL_ROOT_PASSWORD: root_password
      MYSQL_DATABASE: flexes_platform
      MYSQL_USER: flexes_user
      MYSQL_PASSWORD: flexes_password
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql

  redis:
    image: redis:6-alpine
    ports:
      - "6379:6379"
    volumes:
      - redis_data:/data

volumes:
  mysql_data:
  redis_data:
```

## 📚 API 文档

启动应用后，可以通过以下地址访问 API 文档：

- Swagger UI: http://localhost:8081/api/swagger-ui.html
- OpenAPI JSON: http://localhost:8081/api/api-docs

### 主要 API 端点

#### 认证相关
- `POST /auth/register` - 用户注册
- `POST /auth/login` - 用户登录
- `POST /auth/refresh` - 刷新令牌
- `POST /auth/logout` - 用户登出

#### 职位相关
- `GET /jobs` - 获取职位列表
- `GET /jobs/{id}` - 获取职位详情
- `POST /jobs` - 发布职位（雇主）
- `PUT /jobs/{id}` - 更新职位（雇主）

#### 申请相关
- `POST /applications` - 申请职位（求职者）
- `GET /applications/candidate/{id}` - 获取求职者申请列表
- `GET /applications/employer/{id}` - 获取雇主收到的申请

## 🔧 配置说明

### 应用配置

主要配置项在 `application.yml` 中：

```yaml
flexes:
  jwt:
    secret: your-jwt-secret-key
    expiration: 86400000  # 24小时
  business:
    daily-application-limit: 20  # 每日申请限制
    job-approval-required: true  # 职位是否需要审核
```

### 环境变量

| 变量名 | 描述 | 默认值 |
|--------|------|--------|
| `DB_USERNAME` | 数据库用户名 | `flexes_user` |
| `DB_PASSWORD` | 数据库密码 | `flexes_password` |
| `REDIS_HOST` | Redis 主机 | `localhost` |
| `REDIS_PORT` | Redis 端口 | `6379` |

## 🧪 测试

```bash
# 运行所有测试
mvn test

# 运行特定测试
mvn test -Dtest=UserServiceTest

# 生成测试报告
mvn jacoco:report
```

## 📊 监控

应用集成了 Spring Boot Actuator，提供以下监控端点：

- `/actuator/health` - 健康检查
- `/actuator/metrics` - 应用指标
- `/actuator/info` - 应用信息

## 🔒 安全

- 使用 JWT 进行身份认证
- 密码使用 BCrypt 加密
- 实现了角色权限控制（RBAC）
- 配置了 CORS 跨域支持
- 集成了请求限流机制

## 📝 开发规范

### 代码规范
- 使用 Lombok 减少样板代码
- 统一异常处理
- RESTful API 设计
- 完整的参数验证

### 提交规范
- feat: 新功能
- fix: 修复bug
- docs: 文档更新
- style: 代码格式调整
- refactor: 代码重构
- test: 测试相关
- chore: 构建过程或辅助工具的变动

## 🤝 贡献指南

1. Fork 项目
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 打开 Pull Request

## 📄 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

## 📞 联系我们

- 项目地址: https://github.com/flexes/flexes-backend
- 问题反馈: https://github.com/flexes/flexes-backend/issues
- 邮箱: dev@flexes.work

---

**Flexes Team** - 让远程工作更简单 🚀
