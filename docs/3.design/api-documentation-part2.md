# Flexes API 接口文档 - 第二部分

## 💼 3. 职位管理模块（续）

### 3.3 发布职位

**接口地址**: `POST /jobs`

**接口描述**: 雇主发布新职位

**权限要求**: EMPLOYER 角色

**请求参数**:
```json
{
  "categoryId": 1,
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
  "applicationDeadline": "2025-10-17"
}
```

**响应示例**:
```json
{
  "success": true,
  "message": "职位发布成功",
  "data": {
    "jobId": 1,
    "status": "PENDING_APPROVAL"
  },
  "timestamp": "2025-09-17T10:30:00"
}
```

### 3.4 更新职位

**接口地址**: `PUT /jobs/{jobId}`

**接口描述**: 雇主更新职位信息

**权限要求**: EMPLOYER 角色，且为职位发布者

**请求参数**: 同发布职位接口

### 3.5 删除职位

**接口地址**: `DELETE /jobs/{jobId}`

**接口描述**: 雇主删除职位

**权限要求**: EMPLOYER 角色，且为职位发布者

### 3.6 获取职位分类

**接口地址**: `GET /jobs/categories`

**接口描述**: 获取所有职位分类

**响应示例**:
```json
{
  "success": true,
  "message": "获取成功",
  "data": [
    {
      "categoryId": 1,
      "name": "前端开发",
      "slug": "frontend",
      "description": "前端开发相关职位",
      "jobCount": 45
    },
    {
      "categoryId": 2,
      "name": "后端开发",
      "slug": "backend",
      "description": "后端开发相关职位",
      "jobCount": 38
    }
  ],
  "timestamp": "2025-09-17T10:30:00"
}
```

### 3.7 获取相关职位

**接口地址**: `GET /jobs/{jobId}/similar`

**接口描述**: 获取与指定职位相关的职位推荐

**响应示例**: 返回职位列表格式

---

## 📝 4. 申请管理模块

### 4.1 申请职位

**接口地址**: `POST /applications`

**接口描述**: 求职者申请职位

**权限要求**: CANDIDATE 角色

**业务规则**: 
- 每个用户每天最多申请20个职位
- 不允许重复申请同一职位

**请求参数**:
```json
{
  "jobId": 1,
  "coverLetter": "我对这个职位非常感兴趣...",
  "resumeUrl": "https://example.com/resume.pdf"
}
```

**响应示例**:
```json
{
  "success": true,
  "message": "申请提交成功",
  "data": {
    "applicationId": 1,
    "jobId": 1,
    "status": "SUBMITTED",
    "appliedAt": "2025-09-17T10:30:00",
    "remainingApplications": 19
  },
  "timestamp": "2025-09-17T10:30:00"
}
```

### 4.2 获取申请列表（求职者）

**接口地址**: `GET /applications/candidate`

**接口描述**: 求职者查看自己的申请记录

**权限要求**: CANDIDATE 角色

**请求参数**:
| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| page | Integer | 否 | 页码，默认0 |
| size | Integer | 否 | 每页大小，默认20 |
| status | String | 否 | 申请状态筛选 |

**响应示例**:
```json
{
  "success": true,
  "message": "获取成功",
  "data": {
    "content": [
      {
        "applicationId": 1,
        "jobId": 1,
        "jobTitle": "高级前端工程师",
        "companyName": "科技公司",
        "companyLogo": "https://example.com/logo.jpg",
        "status": "VIEWED",
        "appliedAt": "2025-09-17T10:30:00",
        "updatedAt": "2025-09-17T11:30:00"
      }
    ],
    "totalElements": 15,
    "totalPages": 1
  },
  "timestamp": "2025-09-17T10:30:00"
}
```

### 4.3 获取申请列表（雇主）

**接口地址**: `GET /applications/employer`

**接口描述**: 雇主查看收到的申请

**权限要求**: EMPLOYER 角色

**请求参数**:
| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| page | Integer | 否 | 页码，默认0 |
| size | Integer | 否 | 每页大小，默认20 |
| jobId | Long | 否 | 职位ID筛选 |
| status | String | 否 | 申请状态筛选 |

**响应示例**:
```json
{
  "success": true,
  "message": "获取成功",
  "data": {
    "content": [
      {
        "applicationId": 1,
        "jobId": 1,
        "jobTitle": "高级前端工程师",
        "candidateId": 1,
        "candidateName": "张三",
        "candidateAvatar": "https://example.com/avatar.jpg",
        "candidateExperience": 3,
        "candidateLocation": "北京市",
        "coverLetter": "我对这个职位非常感兴趣...",
        "resumeUrl": "https://example.com/resume.pdf",
        "status": "SUBMITTED",
        "appliedAt": "2025-09-17T10:30:00"
      }
    ],
    "totalElements": 23,
    "totalPages": 2
  },
  "timestamp": "2025-09-17T10:30:00"
}
```

### 4.4 获取申请详情

**接口地址**: `GET /applications/{applicationId}`

**接口描述**: 获取申请的详细信息

**权限要求**: 申请者本人或职位发布者

**响应示例**:
```json
{
  "success": true,
  "message": "获取成功",
  "data": {
    "applicationId": 1,
    "jobId": 1,
    "jobTitle": "高级前端工程师",
    "companyName": "科技公司",
    "candidateId": 1,
    "candidateName": "张三",
    "candidateEmail": "zhangsan@example.com",
    "candidatePhone": "13800138000",
    "candidateLocation": "北京市",
    "candidateExperience": 3,
    "candidateSkills": ["React", "Vue.js", "JavaScript"],
    "coverLetter": "我对这个职位非常感兴趣...",
    "resumeUrl": "https://example.com/resume.pdf",
    "status": "VIEWED",
    "employerNotes": "候选人技能匹配度较高",
    "appliedAt": "2025-09-17T10:30:00",
    "updatedAt": "2025-09-17T11:30:00"
  },
  "timestamp": "2025-09-17T10:30:00"
}
```

### 4.5 更新申请状态

**接口地址**: `PUT /applications/{applicationId}/status`

**接口描述**: 雇主更新申请状态

**权限要求**: EMPLOYER 角色，且为职位发布者

**请求参数**:
```json
{
  "status": "INTERVIEWING",
  "employerNotes": "安排下周面试"
}
```

**状态说明**:
- `SUBMITTED`: 已提交
- `VIEWED`: 已查看
- `INTERVIEWING`: 面试中
- `REJECTED`: 已拒绝
- `HIRED`: 已录用

### 4.6 获取每日申请统计

**接口地址**: `GET /applications/daily-stats`

**接口描述**: 获取求职者当日申请统计

**权限要求**: CANDIDATE 角色

**响应示例**:
```json
{
  "success": true,
  "message": "获取成功",
  "data": {
    "todayApplications": 5,
    "dailyLimit": 20,
    "remainingApplications": 15,
    "resetTime": "2025-09-18T00:00:00"
  },
  "timestamp": "2025-09-17T10:30:00"
}
```
