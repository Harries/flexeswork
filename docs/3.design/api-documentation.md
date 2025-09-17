# Flexes 远程工程招聘平台 API 接口文档

## 📋 文档概述

本文档详细描述了 Flexes 远程工程招聘平台的后端 REST API 接口规范，包括认证、用户管理、职位管理、申请管理等核心功能模块的接口定义。

**API 基础信息**
- **Base URL**: `http://localhost:8080/api`
- **API 版本**: v1.0
- **认证方式**: JWT Bearer Token
- **数据格式**: JSON
- **字符编码**: UTF-8

---

## 🔐 认证授权

### 统一响应格式

所有 API 接口都使用统一的响应格式：

```json
{
  "success": true,
  "message": "操作成功",
  "data": {},
  "timestamp": "2025-09-17T10:30:00"
}
```

### 错误响应格式

```json
{
  "success": false,
  "message": "错误描述",
  "data": null,
  "timestamp": "2025-09-17T10:30:00"
}
```

### HTTP 状态码

| 状态码 | 说明 |
|--------|------|
| 200 | 请求成功 |
| 201 | 创建成功 |
| 400 | 请求参数错误 |
| 401 | 未认证 |
| 403 | 权限不足 |
| 404 | 资源不存在 |
| 409 | 资源冲突 |
| 500 | 服务器内部错误 |

---

## 🔑 1. 认证管理模块

### 1.1 用户注册

**接口地址**: `POST /auth/register`

**接口描述**: 新用户注册账户

**请求参数**:
```json
{
  "email": "user@example.com",
  "password": "password123",
  "confirmPassword": "password123",
  "role": "CANDIDATE",
  "name": "张三",
  "phone": "13800138000",
  "companyName": "科技公司",
  "industry": "互联网",
  "website": "https://company.com"
}
```

**参数说明**:
| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| email | String | 是 | 邮箱地址 |
| password | String | 是 | 密码(6-20位) |
| confirmPassword | String | 是 | 确认密码 |
| role | String | 是 | 用户角色: CANDIDATE/EMPLOYER |
| name | String | 是 | 姓名 |
| phone | String | 否 | 手机号 |
| companyName | String | 否 | 公司名称(雇主必填) |
| industry | String | 否 | 所属行业 |
| website | String | 否 | 公司网站 |

**响应示例**:
```json
{
  "success": true,
  "message": "注册成功",
  "data": null,
  "timestamp": "2025-09-17T10:30:00"
}
```

### 1.2 用户登录

**接口地址**: `POST /auth/login`

**接口描述**: 用户登录获取访问令牌

**请求参数**:
```json
{
  "email": "user@example.com",
  "password": "password123",
  "rememberMe": false
}
```

**响应示例**:
```json
{
  "success": true,
  "message": "登录成功",
  "data": {
    "accessToken": "eyJhbGciOiJIUzUxMiJ9...",
    "refreshToken": "eyJhbGciOiJIUzUxMiJ9...",
    "tokenType": "Bearer",
    "expiresIn": 86400,
    "userId": 1,
    "email": "user@example.com",
    "role": "CANDIDATE",
    "name": "张三",
    "avatarUrl": "https://example.com/avatar.jpg",
    "emailVerified": true
  },
  "timestamp": "2025-09-17T10:30:00"
}
```

### 1.3 刷新令牌

**接口地址**: `POST /auth/refresh`

**接口描述**: 使用刷新令牌获取新的访问令牌

**请求参数**:
```
refreshToken: eyJhbGciOiJIUzUxMiJ9...
```

**响应示例**: 同登录接口

### 1.4 用户登出

**接口地址**: `POST /auth/logout`

**接口描述**: 用户登出，使令牌失效

**请求头**:
```
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9...
```

**响应示例**:
```json
{
  "success": true,
  "message": "登出成功",
  "data": null,
  "timestamp": "2025-09-17T10:30:00"
}
```

### 1.5 邮箱验证

**接口地址**: `POST /auth/verify-email`

**接口描述**: 验证用户邮箱地址

**请求参数**:
```
token: verification_token_here
```

### 1.6 忘记密码

**接口地址**: `POST /auth/forgot-password`

**接口描述**: 发送重置密码邮件

**请求参数**:
```
email: user@example.com
```

### 1.7 重置密码

**接口地址**: `POST /auth/reset-password`

**接口描述**: 使用重置令牌重置密码

**请求参数**:
```
token: reset_token_here
newPassword: new_password_123
```

---

## 👤 2. 用户管理模块

### 2.1 获取用户信息

**接口地址**: `GET /users/profile`

**接口描述**: 获取当前登录用户的详细信息

**请求头**:
```
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9...
```

**响应示例**:
```json
{
  "success": true,
  "message": "获取成功",
  "data": {
    "userId": 1,
    "email": "user@example.com",
    "role": "CANDIDATE",
    "status": "ACTIVE",
    "emailVerified": true,
    "lastLoginAt": "2025-09-17T10:30:00",
    "createdAt": "2025-09-17T10:30:00",
    "candidate": {
      "candidateId": 1,
      "name": "张三",
      "phone": "13800138000",
      "location": "北京市",
      "currentPosition": "前端工程师",
      "experienceYears": 3,
      "expectedSalaryMin": 15000,
      "expectedSalaryMax": 25000,
      "jobStatus": "OPEN_TO_OPPORTUNITIES",
      "bio": "热爱技术的前端工程师",
      "skills": ["JavaScript", "React", "Vue.js"]
    }
  },
  "timestamp": "2025-09-17T10:30:00"
}
```

### 2.2 更新用户信息

**接口地址**: `PUT /users/profile`

**接口描述**: 更新当前登录用户的信息

**请求参数**:
```json
{
  "name": "张三",
  "phone": "13800138000",
  "location": "北京市",
  "currentPosition": "高级前端工程师",
  "experienceYears": 4,
  "expectedSalaryMin": 18000,
  "expectedSalaryMax": 30000,
  "jobStatus": "ACTIVELY_LOOKING",
  "bio": "资深前端工程师，专注于React生态",
  "skills": ["JavaScript", "React", "Vue.js", "Node.js"]
}
```

### 2.3 修改密码

**接口地址**: `POST /users/change-password`

**接口描述**: 修改当前用户密码

**请求参数**:
```json
{
  "currentPassword": "old_password",
  "newPassword": "new_password",
  "confirmPassword": "new_password"
}
```

---

## 💼 3. 职位管理模块

### 3.1 获取职位列表

**接口地址**: `GET /jobs`

**接口描述**: 分页获取职位列表，支持搜索和筛选

**请求参数**:
| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| page | Integer | 否 | 页码，从0开始，默认0 |
| size | Integer | 否 | 每页大小，默认20 |
| keyword | String | 否 | 关键词搜索 |
| location | String | 否 | 工作地点 |
| categoryId | Integer | 否 | 职位分类ID |
| employmentType | String | 否 | 工作类型 |
| experienceLevel | String | 否 | 经验要求 |
| remoteType | String | 否 | 远程类型 |
| minSalary | Integer | 否 | 最低薪资 |
| maxSalary | Integer | 否 | 最高薪资 |
| sort | String | 否 | 排序字段 |

**响应示例**:
```json
{
  "success": true,
  "message": "获取成功",
  "data": {
    "content": [
      {
        "jobId": 1,
        "title": "高级前端工程师",
        "description": "负责前端架构设计和开发",
        "companyName": "科技公司",
        "companyLogo": "https://example.com/logo.jpg",
        "location": "北京市",
        "remoteType": "FULLY_REMOTE",
        "employmentType": "FULL_TIME",
        "experienceLevel": "SENIOR",
        "salaryMin": 20000,
        "salaryMax": 35000,
        "salaryCurrency": "CNY",
        "skills": ["React", "TypeScript", "Node.js"],
        "featured": true,
        "viewCount": 156,
        "applicationCount": 23,
        "createdAt": "2025-09-17T10:30:00"
      }
    ],
    "pageable": {
      "page": 0,
      "size": 20,
      "sort": "createdAt,desc"
    },
    "totalElements": 100,
    "totalPages": 5,
    "first": true,
    "last": false
  },
  "timestamp": "2025-09-17T10:30:00"
}
```

### 3.2 获取职位详情

**接口地址**: `GET /jobs/{jobId}`

**接口描述**: 获取指定职位的详细信息

**路径参数**:
| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| jobId | Long | 是 | 职位ID |

**响应示例**:
```json
{
  "success": true,
  "message": "获取成功",
  "data": {
    "jobId": 1,
    "title": "高级前端工程师",
    "description": "我们正在寻找一位经验丰富的前端工程师...",
    "requirements": "3年以上前端开发经验，熟练掌握React...",
    "responsibilities": "负责前端架构设计、代码开发、性能优化...",
    "skillsRequired": ["React", "TypeScript", "JavaScript"],
    "skillsPreferred": ["Node.js", "GraphQL", "Docker"],
    "employmentType": "FULL_TIME",
    "experienceLevel": "SENIOR",
    "educationLevel": "BACHELOR",
    "salaryMin": 20000,
    "salaryMax": 35000,
    "salaryCurrency": "CNY",
    "location": "北京市",
    "remoteType": "FULLY_REMOTE",
    "benefits": "五险一金、弹性工作、技术培训...",
    "applicationDeadline": "2025-10-17",
    "status": "ACTIVE",
    "viewCount": 156,
    "applicationCount": 23,
    "featured": true,
    "createdAt": "2025-09-17T10:30:00",
    "employer": {
      "employerId": 1,
      "companyName": "科技公司",
      "companyLogo": "https://example.com/logo.jpg",
      "industry": "互联网",
      "companySize": "MEDIUM",
      "location": "北京市",
      "website": "https://company.com",
      "description": "专注于前沿技术的创新公司",
      "verified": true
    },
    "category": {
      "categoryId": 1,
      "name": "前端开发",
      "slug": "frontend"
    }
  },
  "timestamp": "2025-09-17T10:30:00"
}
```
