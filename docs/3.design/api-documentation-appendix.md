# Flexes API 接口文档 - 附录

## 📋 错误码说明

### HTTP 状态码

| 状态码 | 说明 | 示例场景 |
|--------|------|----------|
| 200 | 请求成功 | 正常获取数据 |
| 201 | 创建成功 | 成功创建资源 |
| 204 | 无内容 | 成功删除资源 |
| 400 | 请求参数错误 | 参数验证失败 |
| 401 | 未认证 | Token无效或过期 |
| 403 | 权限不足 | 无权限访问资源 |
| 404 | 资源不存在 | 请求的资源未找到 |
| 409 | 资源冲突 | 邮箱已存在 |
| 422 | 业务逻辑错误 | 超出每日申请限制 |
| 429 | 请求过于频繁 | 触发限流 |
| 500 | 服务器内部错误 | 系统异常 |

### 业务错误码

| 错误码 | 说明 | HTTP状态码 |
|--------|------|------------|
| AUTH_001 | 邮箱已存在 | 409 |
| AUTH_002 | 用户不存在 | 404 |
| AUTH_003 | 密码错误 | 401 |
| AUTH_004 | Token无效 | 401 |
| AUTH_005 | Token已过期 | 401 |
| AUTH_006 | 邮箱未验证 | 403 |
| USER_001 | 用户状态异常 | 403 |
| USER_002 | 权限不足 | 403 |
| JOB_001 | 职位不存在 | 404 |
| JOB_002 | 职位已关闭 | 422 |
| JOB_003 | 职位已过期 | 422 |
| APP_001 | 重复申请 | 409 |
| APP_002 | 超出每日申请限制 | 422 |
| APP_003 | 申请不存在 | 404 |
| FILE_001 | 文件类型不支持 | 422 |
| FILE_002 | 文件大小超限 | 422 |
| SYS_001 | 系统维护中 | 503 |

### 错误响应示例

```json
{
  "success": false,
  "message": "超出每日申请限制",
  "data": {
    "errorCode": "APP_002",
    "details": {
      "dailyLimit": 20,
      "todayApplications": 20,
      "resetTime": "2025-09-18T00:00:00"
    }
  },
  "timestamp": "2025-09-17T10:30:00"
}
```

---

## 🔧 数据字典

### 用户角色 (UserRole)

| 值 | 说明 |
|----|------|
| ADMIN | 管理员 |
| CANDIDATE | 求职者 |
| EMPLOYER | 雇主 |

### 用户状态 (UserStatus)

| 值 | 说明 |
|----|------|
| DISABLED | 禁用 |
| ACTIVE | 正常 |
| PENDING_VERIFICATION | 待验证 |

### 工作类型 (EmploymentType)

| 值 | 说明 |
|----|------|
| FULL_TIME | 全职 |
| PART_TIME | 兼职 |
| CONTRACT | 合同工 |
| INTERNSHIP | 实习 |

### 经验要求 (ExperienceLevel)

| 值 | 说明 |
|----|------|
| ENTRY_LEVEL | 应届生 |
| JUNIOR | 初级 |
| MID_LEVEL | 中级 |
| SENIOR | 高级 |
| EXPERT | 专家 |

### 教育水平 (EducationLevel)

| 值 | 说明 |
|----|------|
| NO_REQUIREMENT | 不限 |
| HIGH_SCHOOL | 高中 |
| ASSOCIATE | 专科 |
| BACHELOR | 本科 |
| MASTER | 硕士 |
| DOCTORATE | 博士 |

### 远程工作类型 (RemoteType)

| 值 | 说明 |
|----|------|
| FULLY_REMOTE | 完全远程 |
| HYBRID | 混合办公 |
| ON_SITE | 现场办公 |

### 职位状态 (JobStatus)

| 值 | 说明 |
|----|------|
| PENDING_APPROVAL | 待审核 |
| ACTIVE | 招聘中 |
| PAUSED | 暂停 |
| CLOSED | 已关闭 |

### 申请状态 (ApplicationStatus)

| 值 | 说明 |
|----|------|
| SUBMITTED | 已提交 |
| VIEWED | 已查看 |
| INTERVIEWING | 面试中 |
| REJECTED | 已拒绝 |
| HIRED | 已录用 |

### 求职状态 (JobStatus)

| 值 | 说明 |
|----|------|
| NOT_LOOKING | 不找工作 |
| OPEN_TO_OPPORTUNITIES | 看机会 |
| ACTIVELY_LOOKING | 急找工作 |

### 公司规模 (CompanySize)

| 值 | 说明 |
|----|------|
| STARTUP | 1-10人 |
| SMALL | 11-50人 |
| MEDIUM | 51-200人 |
| LARGE | 201-1000人 |
| ENTERPRISE | 1000+人 |

---

## 🔐 认证授权说明

### JWT Token 结构

```
Header: {
  "alg": "HS512",
  "typ": "JWT"
}

Payload: {
  "sub": "user@example.com",
  "userId": 1,
  "role": "CANDIDATE",
  "iat": 1694952600,
  "exp": 1695039000
}
```

### 权限控制

| 角色 | 权限说明 |
|------|----------|
| ADMIN | 所有权限，包括用户管理、职位审核、系统配置 |
| EMPLOYER | 发布职位、查看申请、搜索候选人、管理公司信息 |
| CANDIDATE | 申请职位、查看申请记录、管理个人信息 |

### 接口权限矩阵

| 接口模块 | ADMIN | EMPLOYER | CANDIDATE | 游客 |
|----------|-------|----------|-----------|------|
| 认证管理 | ✅ | ✅ | ✅ | ✅ |
| 用户管理 | ✅ | ❌ | 🔒 | ❌ |
| 职位管理 | ✅ | 🔒 | ❌ | 👁️ |
| 申请管理 | ✅ | 🔒 | 🔒 | ❌ |
| 候选人管理 | ✅ | ✅ | 🔒 | ❌ |
| 雇主管理 | ✅ | 🔒 | ❌ | 👁️ |
| 管理员模块 | ✅ | ❌ | ❌ | ❌ |

**图例说明**:
- ✅ 完全访问
- 🔒 仅限自己的数据
- 👁️ 仅查看权限
- ❌ 无权限

---

## 📊 限流规则

### API 限流配置

| 接口类型 | 限制规则 | 说明 |
|----------|----------|------|
| 认证接口 | 10次/分钟 | 防止暴力破解 |
| 搜索接口 | 60次/分钟 | 防止恶意搜索 |
| 申请接口 | 20次/天 | 业务规则限制 |
| 文件上传 | 10次/小时 | 防止滥用存储 |
| 普通接口 | 1000次/小时 | 正常使用限制 |

### 限流响应

```json
{
  "success": false,
  "message": "请求过于频繁，请稍后再试",
  "data": {
    "errorCode": "RATE_LIMIT_EXCEEDED",
    "retryAfter": 60
  },
  "timestamp": "2025-09-17T10:30:00"
}
```

---

## 🌐 国际化支持

### 支持语言

| 语言代码 | 语言名称 |
|----------|----------|
| zh-CN | 简体中文 |
| en-US | 英语 |

### 请求头设置

```
Accept-Language: zh-CN,zh;q=0.9,en;q=0.8
```

### 响应示例

```json
{
  "success": false,
  "message": "邮箱格式不正确",
  "messageEn": "Invalid email format",
  "data": null,
  "timestamp": "2025-09-17T10:30:00"
}
```

---

## 📝 版本控制

### API 版本策略

- **当前版本**: v1.0
- **版本格式**: 语义化版本控制 (Semantic Versioning)
- **兼容性**: 向后兼容，废弃功能会提前通知

### 版本请求头

```
API-Version: v1.0
```

### 版本响应头

```
API-Version: v1.0
API-Deprecated: false
```

---

## 🔍 调试信息

### 开发环境调试

在开发环境下，响应会包含额外的调试信息：

```json
{
  "success": true,
  "message": "获取成功",
  "data": {...},
  "timestamp": "2025-09-17T10:30:00",
  "debug": {
    "requestId": "req-uuid-123",
    "executionTime": 125,
    "sqlQueries": 3,
    "cacheHits": 2
  }
}
```

### 请求追踪

每个请求都会分配唯一的请求ID，用于问题追踪：

```
X-Request-ID: req-uuid-123
```

---

## 📞 技术支持

### 联系方式

- **技术文档**: https://docs.flexes.work
- **API 控制台**: https://console.flexes.work
- **问题反馈**: https://github.com/flexes/flexes-backend/issues
- **技术支持**: dev@flexes.work

### 更新日志

| 版本 | 发布日期 | 更新内容 |
|------|----------|----------|
| v1.0.0 | 2025-09-17 | 初始版本发布 |

---

**© 2025 Flexes Team. All rights reserved.**
