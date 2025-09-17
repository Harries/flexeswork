# Flexes 远程工程招聘平台技术架构设计

## 📋 概述

Flexes 平台采用现代化的前后端分离架构，确保系统的可扩展性、可维护性和高性能。本文档详细描述了技术选型、架构设计和部署策略。

---

## 🏗️ 整体架构

### 架构模式
- **前后端分离架构**: 前端和后端独立开发、部署和扩展
- **RESTful API**: 标准化的API接口设计
- **微服务就绪**: 架构支持未来向微服务演进
- **云原生部署**: 支持容器化和云平台部署

### 系统分层
```
┌─────────────────────────────────────┐
│           前端层 (React)             │
├─────────────────────────────────────┤
│         API网关 (可选)               │
├─────────────────────────────────────┤
│        应用层 (Spring Boot)          │
├─────────────────────────────────────┤
│         缓存层 (Redis)               │
├─────────────────────────────────────┤
│        数据层 (MySQL)                │
└─────────────────────────────────────┘
```

### 源码目录结构
```
flexeswork/
├── flexes-frontend/        # 前端源码目录
│   ├── src/               # React应用源码
│   ├── public/            # 静态资源
│   ├── package.json       # 前端依赖配置
│   └── README.md          # 前端说明文档
├── flexes-backend/         # 后端源码目录
│   ├── src/main/java/     # Java源码
│   ├── src/main/resources/ # 配置文件
│   ├── pom.xml            # Maven配置
│   └── README.md          # 后端说明文档
└── docs/                  # 项目文档
```

---

## 🎨 前端技术栈

### 核心框架
- **React 18.x**: 现代化的前端框架
  - 函数式组件和Hooks
  - 虚拟DOM优化性能
  - 丰富的生态系统
  - 强大的社区支持

### 开发工具链
- **Create React App / Vite**: 项目脚手架和构建工具
- **TypeScript**: 类型安全的JavaScript超集
- **ESLint + Prettier**: 代码质量和格式化工具
- **Husky**: Git hooks管理

### UI组件库
- **Ant Design**: 企业级UI设计语言
  - 丰富的组件库
  - 响应式设计
  - 国际化支持
  - 主题定制能力

### 状态管理
- **Redux Toolkit**: 现代化的Redux状态管理
  - 简化的Redux写法
  - 内置异步处理
  - 开发工具支持
- **React Query**: 服务端状态管理
  - 数据缓存和同步
  - 自动重新获取
  - 乐观更新

### 路由管理
- **React Router v6**: 声明式路由
  - 嵌套路由支持
  - 代码分割
  - 路由守卫

### HTTP客户端
- **Axios**: HTTP请求库
  - 请求/响应拦截器
  - 自动JSON转换
  - 请求取消支持
  - 错误处理

### 样式方案
- **CSS Modules**: 模块化CSS
- **Styled Components**: CSS-in-JS解决方案
- **Sass/SCSS**: CSS预处理器

### 测试框架
- **Jest**: JavaScript测试框架
- **React Testing Library**: React组件测试
- **Cypress**: 端到端测试

### 项目结构
```
flexes-frontend/
├── public/                 # 静态资源
├── src/
│   ├── components/         # 通用组件
│   ├── pages/             # 页面组件
│   ├── hooks/             # 自定义Hooks
│   ├── services/          # API服务
│   ├── store/             # 状态管理
│   ├── utils/             # 工具函数
│   ├── styles/            # 样式文件
│   └── types/             # TypeScript类型定义
├── package.json
└── tsconfig.json
```

---

## ⚙️ 后端技术栈

### 核心框架
- **Spring Boot 2.7.x**: 企业级Java框架
  - 自动配置和起步依赖
  - 内嵌Web服务器
  - 生产就绪特性
  - 丰富的生态系统

### Java版本
- **JDK 8 (Java 8)**: 长期支持版本
  - Lambda表达式和Stream API
  - 稳定性和兼容性
  - 企业级支持
  - 丰富的第三方库

### 核心依赖
- **Spring Web**: RESTful API开发
- **Spring Data JPA**: 数据访问层
- **Spring Security**: 安全框架
- **Spring Boot Actuator**: 监控和管理
- **Spring Boot DevTools**: 开发工具

### 数据访问
- **Spring Data JPA**: ORM框架
  - 简化数据访问代码
  - 自动查询生成
  - 分页和排序支持
- **HikariCP**: 高性能连接池
- **MySQL Connector**: MySQL数据库驱动

### 安全框架
- **Spring Security**: 认证和授权
  - JWT Token认证
  - 角色权限控制
  - 密码加密
  - CORS配置

### 缓存集成
- **Spring Data Redis**: Redis集成
  - 缓存抽象
  - 分布式锁
  - 会话存储

### 文档工具
- **Swagger/OpenAPI 3**: API文档生成
  - 自动生成API文档
  - 在线测试接口
  - 客户端代码生成

### 工具库
- **Lombok**: 减少样板代码
- **MapStruct**: 对象映射
- **Jackson**: JSON序列化
- **Validation**: 参数校验

### 测试框架
- **JUnit 5**: 单元测试框架
- **Mockito**: Mock测试
- **TestContainers**: 集成测试
- **Spring Boot Test**: Spring测试支持

### 项目结构
```
flexes-backend/
├── src/main/java/com/flexes/
│   ├── controller/         # 控制器层
│   ├── service/           # 业务逻辑层
│   ├── repository/        # 数据访问层
│   ├── entity/            # 实体类
│   ├── dto/               # 数据传输对象
│   ├── config/            # 配置类
│   ├── security/          # 安全配置
│   ├── utils/             # 工具类
│   └── FlexesApplication.java
├── src/main/resources/
│   ├── application.yml    # 配置文件
│   └── db/migration/      # 数据库迁移脚本
├── src/test/              # 测试代码
├── pom.xml                # Maven配置
└── Dockerfile             # Docker配置
```

---

## 🗄️ 数据库设计

### MySQL 5.8
- **关系型数据库**: 成熟稳定的数据存储方案
- **ACID特性**: 保证数据一致性和可靠性
- **高性能**: 优化的查询引擎
- **丰富功能**: 支持事务、索引、视图、存储过程

### 数据库特性
- **InnoDB存储引擎**: 支持事务和外键
- **UTF8MB4字符集**: 完整的Unicode支持
- **索引优化**: 复合索引和全文索引
- **分区表**: 支持大数据量场景

### 连接池配置
```yaml
spring:
  datasource:
    type: com.zaxxer.hikari.HikariDataSource
    hikari:
      maximum-pool-size: 20
      minimum-idle: 5
      idle-timeout: 300000
      connection-timeout: 20000
```

### 数据库连接信息
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/flexes_platform?useUnicode=true&characterEncoding=utf8mb4&useSSL=false&serverTimezone=UTC
    username: ${DB_USERNAME:flexes_user}
    password: ${DB_PASSWORD:flexes_password}
    driver-class-name: com.mysql.cj.jdbc.Driver
```

---

## 🚀 缓存架构

### Redis 6.x
- **内存数据库**: 高性能键值存储
- **多数据结构**: String、Hash、List、Set、ZSet
- **持久化**: RDB和AOF双重保障
- **集群支持**: 主从复制和哨兵模式

### 缓存策略
- **用户会话**: Session存储和JWT Token缓存
- **热点数据**: 职位列表、公司信息、候选人信息
- **计数器**: 浏览量、申请次数、每日申请限制
- **分布式锁**: 防止重复申请、并发控制
- **消息队列**: 异步任务处理、邮件发送队列

### 缓存配置
```yaml
spring:
  redis:
    host: ${REDIS_HOST:localhost}
    port: ${REDIS_PORT:6379}
    password: ${REDIS_PASSWORD:}
    timeout: 2000ms
    database: 0
    lettuce:
      pool:
        max-active: 8
        max-idle: 8
        min-idle: 0
        max-wait: -1ms
```

### 缓存使用场景
```java
// 用户会话缓存
@Cacheable(value = "user_sessions", key = "#token")
public UserSession getUserSession(String token) { ... }

// 职位列表缓存
@Cacheable(value = "job_list", key = "#page + '_' + #size")
public Page<Job> getJobList(int page, int size) { ... }

// 每日申请次数缓存
@CacheEvict(value = "daily_applications", key = "#candidateId")
public void resetDailyApplicationCount(Long candidateId) { ... }
```

---

## 🔧 开发工具和环境

### 开发环境
- **IDE**: IntelliJ IDEA Ultimate / VS Code
- **版本控制**: Git + GitHub
- **包管理**: Maven 3.8+ (后端) + npm/yarn (前端)
- **API测试**: Postman / Insomnia
- **数据库工具**: MySQL Workbench / DataGrip

### 构建工具
- **Maven 3.8+**: Java项目构建和依赖管理
- **Node.js 16+**: 前端开发环境
- **Docker**: 容器化部署
- **Docker Compose**: 本地开发环境编排

### 代码质量
- **SonarQube**: 代码质量检查和安全扫描
- **Checkstyle**: Java代码规范检查
- **ESLint**: JavaScript/TypeScript代码规范
- **JaCoCo**: Java测试覆盖率统计

### 开发配置文件
```yaml
# application-dev.yml (开发环境)
spring:
  profiles:
    active: dev
  datasource:
    url: jdbc:mysql://localhost:3306/flexes_dev
  redis:
    host: localhost
  jpa:
    show-sql: true
    hibernate:
      ddl-auto: update
logging:
  level:
    com.flexes: DEBUG
```

---

## 🌐 部署架构

### 容器化部署
```yaml
# docker-compose.yml
version: '3.8'
services:
  frontend:
    build:
      context: ./flexes-frontend
      dockerfile: Dockerfile
    ports:
      - "3000:80"
    environment:
      - REACT_APP_API_URL=http://localhost:8081

  backend:
    build:
      context: ./flexes-backend
      dockerfile: Dockerfile
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
    image: mysql:5.8
    environment:
      MYSQL_ROOT_PASSWORD: root_password
      MYSQL_DATABASE: flexes_platform
      MYSQL_USER: flexes_user
      MYSQL_PASSWORD: flexes_password
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql
      - ./docs/sql:/docker-entrypoint-initdb.d

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

### 生产环境架构
```
Internet
    ↓
[Load Balancer (Nginx)]
    ↓
[Frontend Servers] ← → [CDN]
    ↓
[API Gateway (Optional)]
    ↓
[Backend Servers (Spring Boot)]
    ↓
[Cache Layer (Redis Cluster)] ← → [Database (MySQL Master/Slave)]
```

### 部署配置
- **负载均衡**: Nginx / HAProxy
- **反向代理**: Nginx配置SSL终止
- **HTTPS**: Let's Encrypt SSL证书
- **CDN**: 静态资源加速分发
- **监控**: Prometheus + Grafana + ELK Stack

---

## 📊 性能优化

### 前端优化
- **代码分割**: React.lazy + Suspense实现按需加载
- **懒加载**: 图片和组件延迟加载
- **缓存策略**: HTTP缓存和浏览器缓存优化
- **压缩优化**: Gzip压缩和资源压缩
- **CDN加速**: 静态资源CDN分发

### 后端优化
- **数据库优化**: 索引优化和查询优化
- **缓存策略**: 多级缓存架构 (L1: 本地缓存, L2: Redis)
- **连接池**: 数据库连接池和Redis连接池优化
- **异步处理**: @Async注解和CompletableFuture
- **分页查询**: 避免大数据量查询

### 数据库优化
- **索引策略**: 复合索引和覆盖索引
- **查询优化**: SQL优化和执行计划分析
- **分库分表**: 水平扩展支持 (未来规划)
- **读写分离**: 主从架构 (生产环境)

### 缓存优化策略
```java
// 多级缓存示例
@Cacheable(value = "jobs", key = "#page + '_' + #size",
           condition = "#page < 10") // 只缓存前10页
public Page<JobDTO> getJobs(int page, int size) {
    return jobService.findAll(PageRequest.of(page, size));
}

// 缓存预热
@EventListener(ApplicationReadyEvent.class)
public void warmUpCache() {
    // 预加载热门职位
    jobService.getHotJobs();
    // 预加载职位分类
    categoryService.getAllCategories();
}
```

---

## 🔒 安全考虑

### 认证授权
- **JWT Token**: 无状态认证机制
- **角色权限**: RBAC权限模型 (Role-Based Access Control)
- **密码加密**: BCrypt加密算法
- **会话管理**: Redis存储用户会话

### 数据安全
- **SQL注入防护**: JPA参数化查询
- **XSS防护**: 输入输出过滤和转义
- **CSRF防护**: CSRF Token验证
- **HTTPS**: 全站HTTPS加密传输

### 接口安全
- **限流**: 基于Redis的接口访问频率限制
- **参数校验**: @Valid注解严格参数验证
- **日志审计**: 操作日志记录和监控
- **异常处理**: 统一异常处理，避免敏感信息泄露

### 安全配置示例
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/admin/**").hasRole("ADMIN")
                .antMatchers("/api/employer/**").hasRole("EMPLOYER")
                .antMatchers("/api/candidate/**").hasRole("CANDIDATE")
                .anyRequest().authenticated()
            .and()
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
```

---

## 🚦 CI/CD流程

### 持续集成
```yaml
# .github/workflows/ci.yml
name: CI/CD Pipeline
on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main ]

jobs:
  test-backend:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Set up JDK 8
        uses: actions/setup-java@v3
        with:
          java-version: '8'
      - name: Run tests
        run: |
          cd flexes-backend
          mvn clean test

  test-frontend:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Setup Node.js
        uses: actions/setup-node@v3
        with:
          node-version: '16'
      - name: Run tests
        run: |
          cd flexes-frontend
          npm ci
          npm run test
```

### 部署流程
1. **开发环境**: 自动部署到开发服务器
2. **测试环境**: 手动触发部署到测试环境
3. **生产环境**: 经过审核后部署到生产环境

---

## 📈 监控和日志

### 应用监控
- **Spring Boot Actuator**: 应用健康检查和指标
- **Micrometer**: 指标收集和监控
- **Prometheus**: 指标存储和查询
- **Grafana**: 可视化监控面板

### 日志管理
- **Logback**: 日志框架配置
- **ELK Stack**: Elasticsearch + Logstash + Kibana
- **日志分级**: ERROR, WARN, INFO, DEBUG
- **结构化日志**: JSON格式日志输出

### 监控配置
```yaml
# application.yml
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  endpoint:
    health:
      show-details: always
  metrics:
    export:
      prometheus:
        enabled: true

logging:
  level:
    com.flexes: INFO
    org.springframework.security: DEBUG
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n"
    file: "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n"
```

---

## 🔄 版本管理

### Git工作流
- **主分支**: main (生产环境)
- **开发分支**: develop (开发环境)
- **功能分支**: feature/* (新功能开发)
- **修复分支**: hotfix/* (紧急修复)

### 版本发布
- **语义化版本**: v1.0.0 (主版本.次版本.修订版本)
- **发布标签**: Git标签管理版本
- **变更日志**: CHANGELOG.md记录版本变更

---

## 📚 技术选型理由

### 为什么选择React?
- **生态丰富**: 庞大的社区和第三方库
- **性能优秀**: 虚拟DOM和Fiber架构
- **开发效率**: 组件化开发和丰富工具链
- **团队熟悉**: 团队技术栈匹配

### 为什么选择Spring Boot?
- **企业级**: 成熟的企业级开发框架
- **生态完整**: Spring全家桶解决方案
- **开发效率**: 自动配置和约定优于配置
- **社区支持**: 活跃的社区和丰富文档

### 为什么选择MySQL 5.8?
- **稳定可靠**: 经过验证的关系型数据库
- **性能优秀**: 优化的查询引擎和索引
- **功能丰富**: 支持事务、视图、存储过程
- **运维成熟**: 完善的备份和监控方案

### 为什么选择Redis?
- **高性能**: 内存存储，毫秒级响应
- **数据结构**: 丰富的数据类型支持
- **持久化**: 数据安全保障
- **集群支持**: 水平扩展能力

---

**文档版本**: v1.0
**创建日期**: 2025-09-17
**最后更新**: 2025-09-17
**维护团队**: Flexes 开发团队