# Flexes 远程工程招聘平台 API 接口文档

## 📚 文档导航

本文档详细描述了 Flexes 远程工程招聘平台的完整 REST API 接口规范。文档分为多个部分，便于查阅和维护。

### 📖 文档结构

1. **[主文档 - 认证与用户管理](./api-documentation.md)**
   - API 概述和基础信息
   - 认证授权模块
   - 用户管理模块
   - 统一响应格式说明

2. **[第二部分 - 职位与申请管理](./api-documentation-part2.md)**
   - 职位管理模块
   - 申请管理模块
   - 职位分类管理

3. **[第三部分 - 候选人与雇主管理](./api-documentation-part3.md)**
   - 候选人管理模块
   - 雇主管理模块
   - 用户中心面板

4. **[第四部分 - 管理员与扩展功能](./api-documentation-part4.md)**
   - 管理员模块
   - 统计分析模块
   - 文件管理模块
   - 消息通知模块
   - 搜索模块

5. **[首页功能模块](./api-documentation-homepage.md)** ⭐ 新增
   - 首页数据获取
   - 搜索建议
   - 最新职位展示
   - 顶级公司展示
   - 用户评价
   - 博客文章
   - 定价计划
   - 职位提醒功能

6. **[用户面板中心](./api-documentation-dashboards.md)** ⭐ 新增
   - 求职者仪表板详细接口
   - 雇主仪表板详细接口
   - 管理员仪表板详细接口
   - 通知中心
   - 数据分析接口

7. **[附录 - 错误码与数据字典](./api-documentation-appendix.md)**
   - 错误码说明
   - 数据字典
   - 认证授权详解
   - 限流规则
   - 国际化支持

---

## 🚀 快速开始

### API 基础信息

- **Base URL**: `http://localhost:8081/api`
- **API 版本**: v1.0
- **认证方式**: JWT Bearer Token
- **数据格式**: JSON
- **字符编码**: UTF-8

### 认证流程

1. **用户注册**: `POST /auth/register`
2. **用户登录**: `POST /auth/login`
3. **获取Token**: 登录成功后获得 `accessToken` 和 `refreshToken`
4. **使用Token**: 在请求头中添加 `Authorization: Bearer {accessToken}`
5. **刷新Token**: `POST /auth/refresh`

### 示例请求

```bash
# 用户登录
curl -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "password123"
  }'

# 使用Token访问受保护接口
curl -X GET http://localhost:8080/api/users/profile \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..."
```

---

## 📋 核心功能模块

### 🏠 0. 首页功能 ⭐ 完整覆盖
- 职位搜索与分类
- 最新职位发布展示
- 定价计划展示
- 顶级公司展示
- 用户评价展示
- 博客与职业建议
- 职位提醒功能
- 平台统计数据

### 🔐 1. 认证管理
- 用户注册/登录/登出
- JWT Token 管理
- 邮箱验证
- 密码重置

### 👤 2. 用户管理
- 用户信息管理
- 个人资料更新
- 密码修改

### 💼 3. 职位管理 ⭐ 完整覆盖
- 职位发布/更新/删除
- 职位搜索和筛选
- 职位分类管理
- 相关职位推荐
- 职位列表页面 (`/jobs`)
- 职位详情页面 (`/jobs/{job_id}`)

### 📝 4. 申请管理
- 职位申请提交
- 申请状态跟踪
- 每日申请限制 (20个/天)
- 申请统计分析

### 👥 5. 候选人管理 ⭐ 完整覆盖
- 候选人搜索和筛选
- 简历查看
- 技能匹配
- 候选人推荐
- 候选人列表页面 (`/candidates`)
- 候选人详情页面 (`/candidates/{candidate_id}`)
- 候选人中心面板 (`/candidate/dashboard`)

### 🏢 6. 雇主管理 ⭐ 完整覆盖
- 公司信息管理
- 雇主认证
- 招聘统计
- 企业展示
- 雇主列表页面 (`/companies`)
- 雇主详情页面 (`/companies/{company_id}`)
- 雇主中心面板 (`/employer/dashboard`)

### ⚙️ 7. 管理员功能 ⭐ 完整覆盖
- 用户管理
- 职位审核
- 系统配置
- 数据统计
- 管理员中心面板 (`/admin/dashboard`)

### 📊 8. 统计分析
- 平台数据概览
- 用户行为分析
- 招聘趋势分析

### 📁 9. 文件管理
- 简历上传
- 头像上传
- 公司Logo上传

### 💬 10. 消息通知
- 站内消息
- 系统通知
- 邮件通知

### 🔍 11. 搜索功能
- 全局搜索
- 智能推荐
- 搜索建议

---

## 🎯 接口特性

### ✅ 已实现功能

- **RESTful API 设计**: 遵循 REST 架构风格
- **JWT 认证**: 无状态的安全认证机制
- **角色权限控制**: 基于角色的访问控制 (RBAC)
- **统一响应格式**: 标准化的 API 响应结构
- **参数验证**: 完整的请求参数验证
- **错误处理**: 统一的错误处理和错误码
- **分页查询**: 支持分页的列表查询
- **搜索筛选**: 多条件搜索和筛选
- **文件上传**: 支持文件上传和管理
- **限流保护**: API 访问频率限制
- **国际化**: 多语言支持
- **API 文档**: 完整的接口文档

### 🔄 业务规则

- **每日申请限制**: 求职者每天最多申请 20 个职位
- **职位审核**: 新发布的职位需要管理员审核
- **邮箱验证**: 用户注册后需要验证邮箱
- **权限控制**: 不同角色有不同的操作权限
- **数据安全**: 敏感信息加密存储

---

## 🛠️ 开发指南

### 环境要求

- **Java**: 8+
- **Spring Boot**: 2.7.x
- **MySQL**: 8.0+
- **Redis**: 6.0+
- **Maven**: 3.6+

### 本地开发

1. **启动后端服务**:
   ```bash
   cd flexes-backend
   mvn spring-boot:run
   ```

2. **访问 API 文档**:
   - Swagger UI: http://localhost:8081/api/swagger-ui.html
   - OpenAPI JSON: http://localhost:8081/api/api-docs

3. **健康检查**:
   ```bash
   curl http://localhost:8081/api/actuator/health
   ```

### 测试工具推荐

- **Postman**: API 测试和调试
- **Insomnia**: 轻量级 API 客户端
- **curl**: 命令行工具
- **HTTPie**: 现代化的命令行 HTTP 客户端

---

## 📈 性能指标

### 响应时间目标

| 接口类型 | 目标响应时间 |
|----------|--------------|
| 认证接口 | < 200ms |
| 查询接口 | < 300ms |
| 创建接口 | < 500ms |
| 搜索接口 | < 800ms |
| 文件上传 | < 2s |

### 并发能力

- **QPS**: 1000+ (单机)
- **并发用户**: 10000+
- **数据库连接池**: 20 个连接
- **Redis 连接池**: 8 个连接

---

## 🔒 安全措施

### 认证安全

- **JWT Token**: 使用 HS512 算法签名
- **Token 过期**: 访问令牌 24 小时，刷新令牌 7 天
- **密码加密**: 使用 BCrypt 加密存储
- **会话管理**: 无状态设计，支持水平扩展

### 数据安全

- **SQL 注入防护**: 使用参数化查询
- **XSS 防护**: 输入输出过滤
- **CSRF 防护**: Token 验证
- **敏感数据**: 加密存储和传输

### 访问控制

- **角色权限**: 基于角色的访问控制
- **API 限流**: 防止恶意请求
- **IP 白名单**: 管理员接口限制
- **审计日志**: 记录关键操作

---

## 📞 支持与反馈

### 技术支持

- **文档地址**: https://docs.flexes.work
- **GitHub**: https://github.com/flexes/flexes-backend
- **问题反馈**: https://github.com/flexes/flexes-backend/issues
- **邮箱支持**: dev@flexes.work

### 更新通知

- **版本发布**: 关注 GitHub Releases
- **重要更新**: 邮件通知
- **API 变更**: 提前 30 天通知

---

## 📄 许可证

本项目采用 MIT 许可证，详见 [LICENSE](../../LICENSE) 文件。

---

**Flexes Team** - 让远程工作更简单 🚀

*最后更新: 2025-09-17*
