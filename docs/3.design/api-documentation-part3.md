# Flexes API 接口文档 - 第三部分

## 👥 5. 候选人管理模块

### 5.1 获取候选人列表

**接口地址**: `GET /candidates`

**接口描述**: 雇主搜索和浏览候选人

**权限要求**: EMPLOYER 角色

**请求参数**:
| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| page | Integer | 否 | 页码，默认0 |
| size | Integer | 否 | 每页大小，默认15 |
| keyword | String | 否 | 关键词搜索 |
| location | String | 否 | 地点搜索 |
| radius | Integer | 否 | 搜索半径(km) |
| skills | String | 否 | 技能标签，逗号分隔 |
| minExperience | Integer | 否 | 最少工作经验 |
| maxExperience | Integer | 否 | 最多工作经验 |
| educationLevel | String | 否 | 教育水平 |
| jobStatus | String | 否 | 求职状态 |

**响应示例**:
```json
{
  "success": true,
  "message": "获取成功",
  "data": {
    "content": [
      {
        "candidateId": 1,
        "name": "张三",
        "avatarUrl": "https://example.com/avatar.jpg",
        "currentPosition": "前端工程师",
        "location": "北京市",
        "experienceYears": 3,
        "educationLevel": "BACHELOR",
        "skills": ["React", "Vue.js", "JavaScript"],
        "expectedSalaryRange": "15000 - 25000",
        "jobStatus": "OPEN_TO_OPPORTUNITIES",
        "lastActiveAt": "2025-09-17T10:30:00"
      }
    ],
    "totalElements": 156,
    "totalPages": 11
  },
  "timestamp": "2025-09-17T10:30:00"
}
```

### 5.2 获取候选人详情

**接口地址**: `GET /candidates/{candidateId}`

**接口描述**: 获取候选人详细信息

**权限要求**: EMPLOYER 角色或候选人本人

**响应示例**:
```json
{
  "success": true,
  "message": "获取成功",
  "data": {
    "candidateId": 1,
    "name": "张三",
    "email": "zhangsan@example.com",
    "phone": "13800138000",
    "avatarUrl": "https://example.com/avatar.jpg",
    "location": "北京市",
    "currentPosition": "前端工程师",
    "experienceYears": 3,
    "educationLevel": "BACHELOR",
    "expectedSalaryMin": 15000,
    "expectedSalaryMax": 25000,
    "jobStatus": "OPEN_TO_OPPORTUNITIES",
    "bio": "热爱技术的前端工程师，专注于用户体验",
    "skills": ["React", "Vue.js", "JavaScript", "TypeScript"],
    "resumeUrl": "https://example.com/resume.pdf",
    "portfolioUrl": "https://portfolio.example.com",
    "githubUrl": "https://github.com/zhangsan",
    "linkedinUrl": "https://linkedin.com/in/zhangsan",
    "workExperience": [
      {
        "company": "ABC科技",
        "position": "前端工程师",
        "startDate": "2022-01-01",
        "endDate": "2025-09-01",
        "description": "负责公司主要产品的前端开发"
      }
    ],
    "education": [
      {
        "school": "北京大学",
        "degree": "本科",
        "major": "计算机科学与技术",
        "startDate": "2018-09-01",
        "endDate": "2022-06-30"
      }
    ],
    "projects": [
      {
        "name": "电商平台前端",
        "description": "基于React的现代化电商平台",
        "technologies": ["React", "Redux", "Ant Design"],
        "url": "https://project.example.com"
      }
    ],
    "createdAt": "2025-09-17T10:30:00",
    "updatedAt": "2025-09-17T10:30:00"
  },
  "timestamp": "2025-09-17T10:30:00"
}
```

### 5.3 候选人中心面板

**接口地址**: `GET /candidates/dashboard`

**接口描述**: 获取求职者仪表板数据

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
      "jobStatus": "OPEN_TO_OPPORTUNITIES"
    },
    "statistics": {
      "todayApplications": 5,
      "totalApplications": 23,
      "interviewInvitations": 3,
      "profileViews": 45,
      "unreadMessages": 2
    },
    "recentApplications": [
      {
        "applicationId": 1,
        "jobTitle": "高级前端工程师",
        "companyName": "科技公司",
        "status": "VIEWED",
        "appliedAt": "2025-09-17T10:30:00"
      }
    ],
    "notifications": [
      {
        "id": 1,
        "type": "APPLICATION_STATUS",
        "title": "申请状态更新",
        "message": "您申请的高级前端工程师职位已被查看",
        "read": false,
        "createdAt": "2025-09-17T10:30:00"
      }
    ]
  },
  "timestamp": "2025-09-17T10:30:00"
}
```

---

## 🏢 6. 雇主管理模块

### 6.1 获取雇主列表

**接口地址**: `GET /employers`

**接口描述**: 获取平台注册的雇主/公司列表

**请求参数**:
| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| page | Integer | 否 | 页码，默认0 |
| size | Integer | 否 | 每页大小，默认20 |
| keyword | String | 否 | 公司名称搜索 |
| industry | String | 否 | 行业筛选 |
| location | String | 否 | 地点筛选 |
| companySize | String | 否 | 公司规模筛选 |
| verified | Boolean | 否 | 认证状态筛选 |

**响应示例**:
```json
{
  "success": true,
  "message": "获取成功",
  "data": {
    "content": [
      {
        "employerId": 1,
        "companyName": "科技公司",
        "companyLogo": "https://example.com/logo.jpg",
        "industry": "互联网",
        "companySize": "MEDIUM",
        "location": "北京市",
        "website": "https://company.com",
        "verified": true,
        "activeJobCount": 5,
        "totalJobCount": 12,
        "foundedYear": 2018,
        "description": "专注于前沿技术的创新公司"
      }
    ],
    "totalElements": 89,
    "totalPages": 5
  },
  "timestamp": "2025-09-17T10:30:00"
}
```

### 6.2 获取雇主详情

**接口地址**: `GET /employers/{employerId}`

**接口描述**: 获取雇主/公司详细信息

**响应示例**:
```json
{
  "success": true,
  "message": "获取成功",
  "data": {
    "employerId": 1,
    "companyName": "科技公司",
    "companyLogo": "https://example.com/logo.jpg",
    "industry": "互联网",
    "companySize": "MEDIUM",
    "foundedYear": 2018,
    "website": "https://company.com",
    "location": "北京市",
    "description": "我们是一家专注于前沿技术的创新公司...",
    "contactPerson": "李经理",
    "contactPhone": "010-12345678",
    "verified": true,
    "companyImages": [
      "https://example.com/office1.jpg",
      "https://example.com/office2.jpg"
    ],
    "benefits": [
      "五险一金",
      "弹性工作时间",
      "技术培训",
      "年终奖金"
    ],
    "culture": "开放、创新、协作的企业文化",
    "activeJobs": [
      {
        "jobId": 1,
        "title": "高级前端工程师",
        "location": "北京市",
        "salaryRange": "20000 - 35000",
        "createdAt": "2025-09-17T10:30:00"
      }
    ],
    "statistics": {
      "totalJobs": 12,
      "activeJobs": 5,
      "totalApplications": 156,
      "hiredCandidates": 8
    },
    "createdAt": "2025-09-17T10:30:00",
    "updatedAt": "2025-09-17T10:30:00"
  },
  "timestamp": "2025-09-17T10:30:00"
}
```

### 6.3 雇主中心面板

**接口地址**: `GET /employers/dashboard`

**接口描述**: 获取雇主仪表板数据

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
      "verified": true
    },
    "statistics": {
      "activeJobs": 5,
      "totalApplications": 156,
      "pendingApplications": 23,
      "interviewingCandidates": 8,
      "hiredCandidates": 3,
      "profileViews": 234
    },
    "recentApplications": [
      {
        "applicationId": 1,
        "jobTitle": "高级前端工程师",
        "candidateName": "张三",
        "candidateAvatar": "https://example.com/avatar.jpg",
        "status": "SUBMITTED",
        "appliedAt": "2025-09-17T10:30:00"
      }
    ],
    "jobPerformance": [
      {
        "jobId": 1,
        "jobTitle": "高级前端工程师",
        "viewCount": 156,
        "applicationCount": 23,
        "status": "ACTIVE"
      }
    ],
    "notifications": [
      {
        "id": 1,
        "type": "NEW_APPLICATION",
        "title": "新的职位申请",
        "message": "张三申请了您发布的高级前端工程师职位",
        "read": false,
        "createdAt": "2025-09-17T10:30:00"
      }
    ]
  },
  "timestamp": "2025-09-17T10:30:00"
}
```

### 6.4 更新雇主信息

**接口地址**: `PUT /employers/profile`

**接口描述**: 雇主更新公司信息

**权限要求**: EMPLOYER 角色

**请求参数**:
```json
{
  "companyName": "科技公司",
  "industry": "互联网",
  "companySize": "MEDIUM",
  "foundedYear": 2018,
  "website": "https://company.com",
  "location": "北京市",
  "description": "我们是一家专注于前沿技术的创新公司...",
  "contactPerson": "李经理",
  "contactPhone": "010-12345678"
}
```
