# Flexes API 接口文档 - 用户面板中心

## 👤 求职者面板中心详细接口

### 1.1 求职者仪表板概览

**接口地址**: `GET /candidate/dashboard/overview`

**接口描述**: 获取求职者仪表板概览数据

**权限要求**: CANDIDATE 角色

**响应示例**:
```json
{
  "success": true,
  "message": "获取成功",
  "data": {
    "profile": {
      "candidateId": 1,
      "name": "张三",
      "avatarUrl": "https://example.com/avatar.jpg",
      "currentPosition": "前端工程师",
      "jobStatus": "OPEN_TO_OPPORTUNITIES",
      "profileCompleteness": 85,
      "lastLoginAt": "2025-09-17T10:30:00"
    },
    "todayStats": {
      "applicationsToday": 5,
      "dailyLimit": 20,
      "remainingApplications": 15,
      "resetTime": "2025-09-18T00:00:00"
    },
    "overallStats": {
      "totalApplications": 23,
      "interviewInvitations": 3,
      "profileViews": 45,
      "unreadMessages": 2,
      "savedJobs": 12
    },
    "applicationStatusBreakdown": {
      "submitted": 8,
      "viewed": 10,
      "interviewing": 3,
      "rejected": 1,
      "hired": 1
    }
  },
  "timestamp": "2025-09-17T10:30:00"
}
```

### 1.2 获取最近申请记录

**接口地址**: `GET /candidate/dashboard/recent-applications`

**接口描述**: 获取求职者最近的申请记录

**权限要求**: CANDIDATE 角色

**请求参数**:
| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| limit | Integer | 否 | 记录数量，默认10 |

**响应示例**:
```json
{
  "success": true,
  "message": "获取成功",
  "data": [
    {
      "applicationId": 1,
      "jobId": 1,
      "jobTitle": "高级前端工程师",
      "companyName": "科技公司",
      "companyLogo": "https://example.com/logo.jpg",
      "status": "VIEWED",
      "statusText": "已查看",
      "appliedAt": "2025-09-17T10:30:00",
      "updatedAt": "2025-09-17T11:30:00",
      "salaryRange": "20000 - 35000",
      "location": "北京市",
      "hasNewUpdate": true
    }
  ],
  "timestamp": "2025-09-17T10:30:00"
}
```

### 1.3 获取通知中心

**接口地址**: `GET /candidate/dashboard/notifications`

**接口描述**: 获取求职者通知中心数据

**权限要求**: CANDIDATE 角色

**请求参数**:
| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| page | Integer | 否 | 页码，默认0 |
| size | Integer | 否 | 每页大小，默认20 |
| type | String | 否 | 通知类型筛选 |
| unreadOnly | Boolean | 否 | 只显示未读通知 |

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
          "jobTitle": "高级前端工程师",
          "companyName": "科技公司",
          "newStatus": "VIEWED"
        },
        "readStatus": false,
        "createdAt": "2025-09-17T10:30:00"
      },
      {
        "notificationId": 2,
        "type": "JOB_RECOMMENDATION",
        "title": "新职位推荐",
        "content": "根据您的技能匹配，为您推荐了3个新职位",
        "data": {
          "recommendedJobs": [
            {
              "jobId": 2,
              "jobTitle": "React开发工程师",
              "companyName": "创新科技"
            }
          ]
        },
        "readStatus": true,
        "createdAt": "2025-09-16T15:20:00"
      }
    ],
    "totalElements": 15,
    "unreadCount": 5
  },
  "timestamp": "2025-09-17T10:30:00"
}
```

### 1.4 获取推荐职位

**接口地址**: `GET /candidate/dashboard/recommended-jobs`

**接口描述**: 获取为求职者推荐的职位

**权限要求**: CANDIDATE 角色

**请求参数**:
| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| limit | Integer | 否 | 推荐数量，默认10 |

**响应示例**:
```json
{
  "success": true,
  "message": "获取成功",
  "data": [
    {
      "jobId": 2,
      "title": "React开发工程师",
      "companyName": "创新科技",
      "companyLogo": "https://example.com/logo2.jpg",
      "salaryMin": 18000,
      "salaryMax": 28000,
      "location": "上海市",
      "remoteType": "HYBRID",
      "matchScore": 92,
      "matchReasons": [
        "技能匹配度高",
        "薪资符合期望",
        "工作地点合适"
      ],
      "createdAt": "2025-09-17T08:00:00"
    }
  ],
  "timestamp": "2025-09-17T10:30:00"
}
```

### 1.5 获取收藏的职位

**接口地址**: `GET /candidate/dashboard/saved-jobs`

**接口描述**: 获取求职者收藏的职位列表

**权限要求**: CANDIDATE 角色

**请求参数**:
| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| page | Integer | 否 | 页码，默认0 |
| size | Integer | 否 | 每页大小，默认20 |

**响应示例**:
```json
{
  "success": true,
  "message": "获取成功",
  "data": {
    "content": [
      {
        "savedJobId": 1,
        "jobId": 3,
        "jobTitle": "全栈工程师",
        "companyName": "未来科技",
        "companyLogo": "https://example.com/logo3.jpg",
        "salaryRange": "22000 - 38000",
        "location": "深圳市",
        "remoteType": "FULLY_REMOTE",
        "status": "ACTIVE",
        "savedAt": "2025-09-16T14:20:00",
        "hasApplied": false
      }
    ],
    "totalElements": 12
  },
  "timestamp": "2025-09-17T10:30:00"
}
```

---

## 🏢 雇主面板中心详细接口

### 2.1 雇主仪表板概览

**接口地址**: `GET /employer/dashboard/overview`

**接口描述**: 获取雇主仪表板概览数据

**权限要求**: EMPLOYER 角色

**响应示例**:
```json
{
  "success": true,
  "message": "获取成功",
  "data": {
    "company": {
      "employerId": 1,
      "companyName": "科技公司",
      "companyLogo": "https://example.com/logo.jpg",
      "verified": true,
      "profileCompleteness": 90
    },
    "jobStats": {
      "activeJobs": 5,
      "pausedJobs": 2,
      "closedJobs": 8,
      "totalJobs": 15,
      "jobsPublishedThisMonth": 3
    },
    "applicationStats": {
      "totalApplications": 156,
      "pendingApplications": 23,
      "viewedApplications": 89,
      "interviewingCandidates": 8,
      "hiredCandidates": 3,
      "newApplicationsToday": 5
    },
    "engagementStats": {
      "profileViews": 234,
      "jobViews": 1567,
      "searchAppearances": 456,
      "candidateSearches": 12
    }
  },
  "timestamp": "2025-09-17T10:30:00"
}
```

### 2.2 获取最新申请

**接口地址**: `GET /employer/dashboard/recent-applications`

**接口描述**: 获取雇主收到的最新申请

**权限要求**: EMPLOYER 角色

**请求参数**:
| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| limit | Integer | 否 | 申请数量，默认10 |
| status | String | 否 | 申请状态筛选 |

**响应示例**:
```json
{
  "success": true,
  "message": "获取成功",
  "data": [
    {
      "applicationId": 1,
      "jobId": 1,
      "jobTitle": "高级前端工程师",
      "candidateId": 1,
      "candidateName": "张三",
      "candidateAvatar": "https://example.com/avatar.jpg",
      "candidateExperience": 3,
      "candidateLocation": "北京市",
      "candidateSkills": ["React", "Vue.js", "JavaScript"],
      "status": "SUBMITTED",
      "statusText": "待查看",
      "appliedAt": "2025-09-17T10:30:00",
      "isNew": true,
      "matchScore": 85
    }
  ],
  "timestamp": "2025-09-17T10:30:00"
}
```

### 2.3 获取职位表现数据

**接口地址**: `GET /employer/dashboard/job-performance`

**接口描述**: 获取雇主发布职位的表现数据

**权限要求**: EMPLOYER 角色

**请求参数**:
| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| limit | Integer | 否 | 职位数量，默认10 |
| sortBy | String | 否 | 排序字段: views/applications/created |

**响应示例**:
```json
{
  "success": true,
  "message": "获取成功",
  "data": [
    {
      "jobId": 1,
      "jobTitle": "高级前端工程师",
      "status": "ACTIVE",
      "viewCount": 156,
      "applicationCount": 23,
      "qualifiedApplications": 15,
      "interviewingCount": 3,
      "createdAt": "2025-09-10T10:30:00",
      "applicationDeadline": "2025-10-10",
      "daysRemaining": 23,
      "conversionRate": 14.7,
      "trending": "UP"
    }
  ],
  "timestamp": "2025-09-17T10:30:00"
}
```

### 2.4 获取候选人推荐

**接口地址**: `GET /employer/dashboard/recommended-candidates`

**接口描述**: 获取为雇主推荐的候选人

**权限要求**: EMPLOYER 角色

**请求参数**:
| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| jobId | Long | 否 | 特定职位的推荐 |
| limit | Integer | 否 | 推荐数量，默认10 |

**响应示例**:
```json
{
  "success": true,
  "message": "获取成功",
  "data": [
    {
      "candidateId": 2,
      "name": "李四",
      "avatarUrl": "https://example.com/avatar2.jpg",
      "currentPosition": "前端开发工程师",
      "experienceYears": 4,
      "location": "北京市",
      "skills": ["React", "TypeScript", "Node.js"],
      "expectedSalaryRange": "18000 - 30000",
      "jobStatus": "ACTIVELY_LOOKING",
      "matchScore": 88,
      "matchReasons": [
        "技能高度匹配",
        "经验符合要求",
        "地理位置合适"
      ],
      "lastActiveAt": "2025-09-17T09:15:00"
    }
  ],
  "timestamp": "2025-09-17T10:30:00"
}
```

### 2.5 获取招聘分析数据

**接口地址**: `GET /employer/dashboard/analytics`

**接口描述**: 获取雇主招聘数据分析

**权限要求**: EMPLOYER 角色

**请求参数**:
| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| period | String | 否 | 时间周期: 7d/30d/90d，默认30d |

**响应示例**:
```json
{
  "success": true,
  "message": "获取成功",
  "data": {
    "period": "30d",
    "jobViewsTrend": [
      {"date": "2025-09-01", "views": 45},
      {"date": "2025-09-02", "views": 52}
    ],
    "applicationsTrend": [
      {"date": "2025-09-01", "applications": 8},
      {"date": "2025-09-02", "applications": 12}
    ],
    "topPerformingJobs": [
      {
        "jobId": 1,
        "jobTitle": "高级前端工程师",
        "views": 156,
        "applications": 23
      }
    ],
    "candidateSourceAnalysis": [
      {"source": "搜索", "count": 45, "percentage": 65.2},
      {"source": "推荐", "count": 18, "percentage": 26.1},
      {"source": "直接访问", "count": 6, "percentage": 8.7}
    ],
    "skillDemandAnalysis": [
      {"skill": "React", "demand": 89},
      {"skill": "Node.js", "demand": 67},
      {"skill": "TypeScript", "demand": 54}
    ]
  },
  "timestamp": "2025-09-17T10:30:00"
}
```

---

## ⚙️ 管理员面板中心详细接口

### 3.1 管理员仪表板概览

**接口地址**: `GET /admin/dashboard/overview`

**接口描述**: 获取管理员仪表板概览数据

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
      "newUsersThisWeek": 89,
      "userGrowthRate": 12.5
    },
    "jobStatistics": {
      "totalJobs": 234,
      "activeJobs": 156,
      "pendingApproval": 12,
      "closedJobs": 66,
      "newJobsToday": 8,
      "newJobsThisWeek": 34,
      "jobApprovalRate": 94.2
    },
    "applicationStatistics": {
      "totalApplications": 3456,
      "todayApplications": 67,
      "successfulApplications": 234,
      "applicationSuccessRate": 6.8,
      "averageApplicationsPerJob": 14.8
    },
    "platformHealth": {
      "systemStatus": "HEALTHY",
      "serverUptime": "99.9%",
      "responseTime": 125,
      "errorRate": 0.1
    }
  },
  "timestamp": "2025-09-17T10:30:00"
}
```
