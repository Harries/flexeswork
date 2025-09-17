# Flexes API 接口文档 - 首页模块

## 🏠 首页功能模块

### 1.1 获取首页数据

**接口地址**: `GET /homepage`

**接口描述**: 获取首页所有模块的数据，包括搜索、最新职位、公司展示、用户评价等

**请求参数**: 无

**响应示例**:
```json
{
  "success": true,
  "message": "获取成功",
  "data": {
    "searchStats": {
      "totalJobs": 1256,
      "totalCompanies": 234,
      "totalCandidates": 3456,
      "newJobsToday": 23
    },
    "hotSearchTags": [
      "React", "Node.js", "Python", "Java", "前端工程师", "后端开发", "全栈工程师"
    ],
    "latestJobs": [
      {
        "jobId": 1,
        "title": "高级前端工程师",
        "companyName": "科技公司",
        "companyLogo": "https://example.com/logo.jpg",
        "salaryMin": 20000,
        "salaryMax": 35000,
        "salaryCurrency": "CNY",
        "location": "北京市",
        "remoteType": "FULLY_REMOTE",
        "featured": true,
        "createdAt": "2025-09-17T10:30:00"
      }
    ],
    "topCompanies": [
      {
        "employerId": 1,
        "companyName": "科技公司",
        "companyLogo": "https://example.com/logo.jpg",
        "location": "北京市",
        "industry": "互联网",
        "activeJobCount": 5,
        "verified": true
      }
    ],
    "userReviews": [
      {
        "reviewId": 1,
        "userName": "张三",
        "userAvatar": "https://example.com/avatar.jpg",
        "userRole": "CANDIDATE",
        "rating": 5,
        "content": "平台很好用，找到了理想的工作",
        "createdAt": "2025-09-17T10:30:00"
      }
    ],
    "blogArticles": [
      {
        "articleId": 1,
        "title": "2025年前端开发趋势分析",
        "summary": "深入分析前端技术发展趋势...",
        "coverImage": "https://example.com/article-cover.jpg",
        "author": "技术团队",
        "publishedAt": "2025-09-17T10:30:00",
        "readCount": 1256
      }
    ]
  },
  "timestamp": "2025-09-17T10:30:00"
}
```

### 1.2 获取职位搜索建议

**接口地址**: `GET /homepage/search-suggestions`

**接口描述**: 获取搜索框的智能建议

**请求参数**:
| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| q | String | 是 | 搜索关键词 |
| limit | Integer | 否 | 建议数量，默认10 |

**响应示例**:
```json
{
  "success": true,
  "message": "获取成功",
  "data": {
    "jobTitles": [
      "前端工程师",
      "前端开发工程师",
      "高级前端工程师"
    ],
    "skills": [
      "React",
      "React Native",
      "Redux"
    ],
    "companies": [
      "字节跳动",
      "腾讯",
      "阿里巴巴"
    ],
    "locations": [
      "北京市",
      "上海市",
      "深圳市"
    ]
  },
  "timestamp": "2025-09-17T10:30:00"
}
```

### 1.3 获取最新职位

**接口地址**: `GET /homepage/latest-jobs`

**接口描述**: 获取首页展示的最新职位列表

**请求参数**:
| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| limit | Integer | 否 | 职位数量，默认20 |
| featured | Boolean | 否 | 是否只显示精选职位 |

**响应示例**:
```json
{
  "success": true,
  "message": "获取成功",
  "data": [
    {
      "jobId": 1,
      "title": "高级前端工程师",
      "companyName": "科技公司",
      "companyLogo": "https://example.com/logo.jpg",
      "salaryMin": 20000,
      "salaryMax": 35000,
      "salaryCurrency": "CNY",
      "location": "北京市",
      "remoteType": "FULLY_REMOTE",
      "employmentType": "FULL_TIME",
      "experienceLevel": "SENIOR",
      "skills": ["React", "TypeScript", "Node.js"],
      "featured": true,
      "viewCount": 156,
      "applicationCount": 23,
      "createdAt": "2025-09-17T10:30:00",
      "applicationDeadline": "2025-10-17"
    }
  ],
  "timestamp": "2025-09-17T10:30:00"
}
```

### 1.4 获取顶级公司

**接口地址**: `GET /homepage/top-companies`

**接口描述**: 获取首页展示的顶级合作公司

**请求参数**:
| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| limit | Integer | 否 | 公司数量，默认12 |

**响应示例**:
```json
{
  "success": true,
  "message": "获取成功",
  "data": [
    {
      "employerId": 1,
      "companyName": "科技公司",
      "companyLogo": "https://example.com/logo.jpg",
      "location": "北京市",
      "industry": "互联网",
      "companySize": "MEDIUM",
      "activeJobCount": 5,
      "totalJobCount": 12,
      "verified": true,
      "rating": 4.8,
      "reviewCount": 156
    }
  ],
  "timestamp": "2025-09-17T10:30:00"
}
```

### 1.5 获取用户评价

**接口地址**: `GET /homepage/reviews`

**接口描述**: 获取首页展示的用户评价

**请求参数**:
| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| limit | Integer | 否 | 评价数量，默认6 |

**响应示例**:
```json
{
  "success": true,
  "message": "获取成功",
  "data": [
    {
      "reviewId": 1,
      "userName": "张三",
      "userAvatar": "https://example.com/avatar.jpg",
      "userRole": "CANDIDATE",
      "userTitle": "前端工程师",
      "rating": 5,
      "content": "通过Flexes找到了理想的远程工作，平台体验很好，推荐给大家！",
      "createdAt": "2025-09-17T10:30:00",
      "helpful": 23
    }
  ],
  "timestamp": "2025-09-17T10:30:00"
}
```

### 1.6 获取博客文章

**接口地址**: `GET /homepage/blog-articles`

**接口描述**: 获取首页展示的博客文章

**请求参数**:
| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| limit | Integer | 否 | 文章数量，默认4 |

**响应示例**:
```json
{
  "success": true,
  "message": "获取成功",
  "data": [
    {
      "articleId": 1,
      "title": "2025年前端开发趋势分析",
      "summary": "深入分析前端技术发展趋势，包括React 19、Vue 4等新特性...",
      "coverImage": "https://example.com/article-cover.jpg",
      "author": "技术团队",
      "authorAvatar": "https://example.com/author-avatar.jpg",
      "category": "技术趋势",
      "tags": ["前端", "React", "Vue"],
      "publishedAt": "2025-09-17T10:30:00",
      "readCount": 1256,
      "likeCount": 89,
      "commentCount": 23
    }
  ],
  "timestamp": "2025-09-17T10:30:00"
}
```

### 1.7 获取定价计划

**接口地址**: `GET /homepage/pricing-plans`

**接口描述**: 获取雇主定价计划信息

**响应示例**:
```json
{
  "success": true,
  "message": "获取成功",
  "data": [
    {
      "planId": 1,
      "planName": "基础计划",
      "planType": "FREE",
      "price": 0,
      "currency": "USD",
      "billingCycle": "MONTHLY",
      "features": [
        "发布5个职位",
        "基础搜索功能",
        "邮件支持"
      ],
      "limitations": [
        "不支持精选职位",
        "展示期15天"
      ]
    },
    {
      "planId": 2,
      "planName": "扩展计划",
      "planType": "PREMIUM",
      "price": 250,
      "currency": "USD",
      "billingCycle": "MONTHLY",
      "features": [
        "8个精选职位",
        "20个职位发布/月",
        "30天展示期",
        "24/7客服支持",
        "高级搜索功能"
      ],
      "popular": true
    },
    {
      "planId": 3,
      "planName": "高级计划",
      "planType": "ENTERPRISE",
      "price": 500,
      "currency": "USD",
      "billingCycle": "YEARLY",
      "features": [
        "10个精选职位",
        "无限职位发布",
        "60天展示期",
        "24/7客服支持",
        "全套服务",
        "专属客户经理"
      ]
    }
  ],
  "timestamp": "2025-09-17T10:30:00"
}
```

### 1.8 获取平台统计数据

**接口地址**: `GET /homepage/platform-stats`

**接口描述**: 获取平台整体统计数据用于首页展示

**响应示例**:
```json
{
  "success": true,
  "message": "获取成功",
  "data": {
    "totalJobs": 1256,
    "totalCompanies": 234,
    "totalCandidates": 3456,
    "successfulMatches": 567,
    "newJobsThisWeek": 89,
    "averageSalary": 25600,
    "topSkills": [
      {"skill": "React", "count": 234},
      {"skill": "Node.js", "count": 189},
      {"skill": "Python", "count": 156}
    ],
    "topLocations": [
      {"location": "北京市", "count": 345},
      {"location": "上海市", "count": 289},
      {"location": "深圳市", "count": 234}
    ]
  },
  "timestamp": "2025-09-17T10:30:00"
}
```

---

## 📊 职位提醒功能

### 1.9 创建职位提醒

**接口地址**: `POST /job-alerts`

**接口描述**: 用户创建基于搜索条件的职位提醒

**权限要求**: 已认证用户

**请求参数**:
```json
{
  "alertName": "前端工程师职位提醒",
  "keywords": "前端工程师,React",
  "location": "北京市",
  "salaryMin": 15000,
  "salaryMax": 30000,
  "employmentType": "FULL_TIME",
  "remoteType": "FULLY_REMOTE",
  "frequency": "DAILY",
  "active": true
}
```

**响应示例**:
```json
{
  "success": true,
  "message": "职位提醒创建成功",
  "data": {
    "alertId": 1,
    "alertName": "前端工程师职位提醒",
    "matchCount": 23,
    "nextNotification": "2025-09-18T09:00:00"
  },
  "timestamp": "2025-09-17T10:30:00"
}
```

### 1.10 获取职位提醒列表

**接口地址**: `GET /job-alerts`

**接口描述**: 获取用户的职位提醒列表

**权限要求**: 已认证用户

**响应示例**:
```json
{
  "success": true,
  "message": "获取成功",
  "data": [
    {
      "alertId": 1,
      "alertName": "前端工程师职位提醒",
      "keywords": "前端工程师,React",
      "location": "北京市",
      "frequency": "DAILY",
      "active": true,
      "matchCount": 23,
      "lastNotified": "2025-09-17T09:00:00",
      "nextNotification": "2025-09-18T09:00:00",
      "createdAt": "2025-09-17T10:30:00"
    }
  ],
  "timestamp": "2025-09-17T10:30:00"
}
```

### 1.11 更新职位提醒

**接口地址**: `PUT /job-alerts/{alertId}`

**接口描述**: 更新职位提醒设置

**权限要求**: 提醒创建者

### 1.12 删除职位提醒

**接口地址**: `DELETE /job-alerts/{alertId}`

**接口描述**: 删除职位提醒

**权限要求**: 提醒创建者
