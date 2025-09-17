# Flexes API 接口文档 - 第四部分

## ⚙️ 7. 管理员模块

### 7.1 管理员仪表板

**接口地址**: `GET /admin/dashboard`

**接口描述**: 获取管理员仪表板统计数据

**权限要求**: ADMIN 角色

**响应示例**:
```json
{
  "success": true,
  "message": "获取成功",
  "data": {
    "userStatistics": {
      "totalUsers": 1256,
      "totalCandidates": 892,
      "totalEmployers": 364,
      "activeUsers": 456,
      "newUsersToday": 23,
      "newUsersThisWeek": 89
    },
    "jobStatistics": {
      "totalJobs": 234,
      "activeJobs": 156,
      "pendingApproval": 12,
      "closedJobs": 66,
      "newJobsToday": 8,
      "newJobsThisWeek": 34
    },
    "applicationStatistics": {
      "totalApplications": 3456,
      "todayApplications": 67,
      "successfulApplications": 234,
      "applicationSuccessRate": 6.8
    },
    "platformStatistics": {
      "totalRevenue": 125600,
      "monthlyRevenue": 23400,
      "averageJobsPerEmployer": 2.3,
      "averageApplicationsPerJob": 14.8
    }
  },
  "timestamp": "2025-09-17T10:30:00"
}
```

### 7.2 用户管理

**接口地址**: `GET /admin/users`

**接口描述**: 管理员获取用户列表

**权限要求**: ADMIN 角色

**请求参数**:
| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| page | Integer | 否 | 页码，默认0 |
| size | Integer | 否 | 每页大小，默认20 |
| role | String | 否 | 用户角色筛选 |
| status | String | 否 | 用户状态筛选 |
| keyword | String | 否 | 邮箱搜索 |

**响应示例**:
```json
{
  "success": true,
  "message": "获取成功",
  "data": {
    "content": [
      {
        "userId": 1,
        "email": "user@example.com",
        "role": "CANDIDATE",
        "status": "ACTIVE",
        "emailVerified": true,
        "lastLoginAt": "2025-09-17T10:30:00",
        "createdAt": "2025-09-17T10:30:00",
        "profile": {
          "name": "张三",
          "phone": "13800138000"
        }
      }
    ],
    "totalElements": 1256,
    "totalPages": 63
  },
  "timestamp": "2025-09-17T10:30:00"
}
```

### 7.3 更新用户状态

**接口地址**: `PUT /admin/users/{userId}/status`

**接口描述**: 管理员更新用户状态

**权限要求**: ADMIN 角色

**请求参数**:
```json
{
  "status": "DISABLED",
  "reason": "违反平台规定"
}
```

### 7.4 职位审核

**接口地址**: `GET /admin/jobs/pending`

**接口描述**: 获取待审核职位列表

**权限要求**: ADMIN 角色

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
        "companyName": "科技公司",
        "employerId": 1,
        "status": "PENDING_APPROVAL",
        "createdAt": "2025-09-17T10:30:00",
        "description": "职位描述...",
        "salaryRange": "20000 - 35000"
      }
    ],
    "totalElements": 12,
    "totalPages": 1
  },
  "timestamp": "2025-09-17T10:30:00"
}
```

### 7.5 审核职位

**接口地址**: `PUT /admin/jobs/{jobId}/review`

**接口描述**: 管理员审核职位

**权限要求**: ADMIN 角色

**请求参数**:
```json
{
  "action": "APPROVE",
  "comment": "职位信息完整，审核通过"
}
```

**审核动作**:
- `APPROVE`: 审核通过
- `REJECT`: 审核拒绝

### 7.6 系统配置管理

**接口地址**: `GET /admin/configs`

**接口描述**: 获取系统配置

**权限要求**: ADMIN 角色

**响应示例**:
```json
{
  "success": true,
  "message": "获取成功",
  "data": [
    {
      "configKey": "daily_application_limit",
      "configValue": "20",
      "description": "每日职位申请限制数量",
      "type": "integer"
    },
    {
      "configKey": "job_approval_required",
      "configValue": "true",
      "description": "职位发布是否需要审核",
      "type": "boolean"
    }
  ],
  "timestamp": "2025-09-17T10:30:00"
}
```

### 7.7 更新系统配置

**接口地址**: `PUT /admin/configs/{configKey}`

**接口描述**: 更新系统配置

**权限要求**: ADMIN 角色

**请求参数**:
```json
{
  "configValue": "25"
}
```

---

## 📊 8. 统计分析模块

### 8.1 平台统计概览

**接口地址**: `GET /statistics/overview`

**接口描述**: 获取平台整体统计数据

**权限要求**: ADMIN 角色

**响应示例**:
```json
{
  "success": true,
  "message": "获取成功",
  "data": {
    "userGrowth": {
      "totalUsers": 1256,
      "monthlyGrowth": 12.5,
      "weeklyGrowth": 3.2
    },
    "jobMarket": {
      "totalJobs": 234,
      "averageSalary": 25600,
      "topCategories": [
        {"name": "前端开发", "count": 45},
        {"name": "后端开发", "count": 38}
      ]
    },
    "applicationTrends": {
      "totalApplications": 3456,
      "successRate": 6.8,
      "averageResponseTime": 2.3
    }
  },
  "timestamp": "2025-09-17T10:30:00"
}
```

### 8.2 用户行为分析

**接口地址**: `GET /statistics/user-behavior`

**接口描述**: 获取用户行为分析数据

**权限要求**: ADMIN 角色

**请求参数**:
| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| startDate | String | 否 | 开始日期 |
| endDate | String | 否 | 结束日期 |
| userType | String | 否 | 用户类型 |

---

## 📁 9. 文件管理模块

### 9.1 上传文件

**接口地址**: `POST /files/upload`

**接口描述**: 上传文件（简历、头像等）

**权限要求**: 已认证用户

**请求参数**: 
- Content-Type: multipart/form-data
- file: 文件对象
- type: 文件类型（resume/avatar/company_logo）

**响应示例**:
```json
{
  "success": true,
  "message": "文件上传成功",
  "data": {
    "fileId": "uuid-file-id",
    "fileName": "resume.pdf",
    "fileUrl": "https://example.com/files/resume.pdf",
    "fileSize": 1024000,
    "fileType": "application/pdf",
    "uploadedAt": "2025-09-17T10:30:00"
  },
  "timestamp": "2025-09-17T10:30:00"
}
```

### 9.2 删除文件

**接口地址**: `DELETE /files/{fileId}`

**接口描述**: 删除文件

**权限要求**: 文件上传者或管理员

---

## 💬 10. 消息通知模块

### 10.1 获取消息列表

**接口地址**: `GET /messages`

**接口描述**: 获取用户消息列表

**权限要求**: 已认证用户

**请求参数**:
| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| page | Integer | 否 | 页码，默认0 |
| size | Integer | 否 | 每页大小，默认20 |
| type | String | 否 | 消息类型筛选 |
| read | Boolean | 否 | 已读状态筛选 |

**响应示例**:
```json
{
  "success": true,
  "message": "获取成功",
  "data": {
    "content": [
      {
        "messageId": 1,
        "senderId": 2,
        "senderName": "科技公司",
        "messageType": "JOB_INVITATION",
        "subject": "职位邀请",
        "content": "我们对您的简历很感兴趣...",
        "readStatus": false,
        "createdAt": "2025-09-17T10:30:00"
      }
    ],
    "totalElements": 15,
    "unreadCount": 5
  },
  "timestamp": "2025-09-17T10:30:00"
}
```

### 10.2 发送消息

**接口地址**: `POST /messages`

**接口描述**: 发送消息

**权限要求**: 已认证用户

**请求参数**:
```json
{
  "receiverId": 1,
  "messageType": "JOB_INVITATION",
  "subject": "职位邀请",
  "content": "我们对您的简历很感兴趣..."
}
```

### 10.3 标记消息已读

**接口地址**: `PUT /messages/{messageId}/read`

**接口描述**: 标记消息为已读

**权限要求**: 消息接收者

### 10.4 获取通知列表

**接口地址**: `GET /notifications`

**接口描述**: 获取系统通知列表

**权限要求**: 已认证用户

**响应示例**:
```json
{
  "success": true,
  "message": "获取成功",
  "data": {
    "content": [
      {
        "notificationId": 1,
        "type": "APPLICATION_STATUS",
        "title": "申请状态更新",
        "content": "您申请的高级前端工程师职位已被查看",
        "data": {
          "applicationId": 1,
          "jobId": 1,
          "status": "VIEWED"
        },
        "readStatus": false,
        "createdAt": "2025-09-17T10:30:00"
      }
    ],
    "totalElements": 12,
    "unreadCount": 3
  },
  "timestamp": "2025-09-17T10:30:00"
}
```

---

## 🔍 11. 搜索模块

### 11.1 全局搜索

**接口地址**: `GET /search`

**接口描述**: 全局搜索职位、公司、候选人

**请求参数**:
| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| q | String | 是 | 搜索关键词 |
| type | String | 否 | 搜索类型: jobs/companies/candidates |
| page | Integer | 否 | 页码，默认0 |
| size | Integer | 否 | 每页大小，默认20 |

**响应示例**:
```json
{
  "success": true,
  "message": "搜索成功",
  "data": {
    "jobs": {
      "content": [...],
      "totalElements": 45
    },
    "companies": {
      "content": [...],
      "totalElements": 12
    },
    "candidates": {
      "content": [...],
      "totalElements": 23
    },
    "totalResults": 80
  },
  "timestamp": "2025-09-17T10:30:00"
}
```

### 11.2 搜索建议

**接口地址**: `GET /search/suggestions`

**接口描述**: 获取搜索建议

**请求参数**:
| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| q | String | 是 | 搜索关键词 |
| type | String | 否 | 建议类型 |

**响应示例**:
```json
{
  "success": true,
  "message": "获取成功",
  "data": {
    "suggestions": [
      "前端工程师",
      "前端开发",
      "React开发工程师",
      "Vue.js工程师"
    ]
  },
  "timestamp": "2025-09-17T10:30:00"
}
```
