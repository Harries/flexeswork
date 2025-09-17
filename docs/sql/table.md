# Flexes 平台数据库表结构文档

## 📋 概述

本文档详细描述了 Flexes 远程工程招聘平台的完整数据库表结构，包括所有表的字段定义、数据类型、约束条件、索引和业务规则。

## 📊 数据库信息

- **数据库名称**: `flexes_platform`
- **存储引擎**: InnoDB
- **字符集**: utf8mb4
- **排序规则**: utf8mb4_unicode_ci
- **表总数**: 15 个核心表
- **版本**: v1.0

## 🗂️ 表分类

### 1. 用户管理模块
- [users](#1-users-用户主表) - 用户主表
- [candidates](#2-candidates-求职者信息表) - 求职者信息表
- [employers](#3-employers-雇主信息表) - 雇主信息表

### 2. 职位管理模块
- [job_categories](#4-job_categories-职位分类表) - 职位分类表
- [jobs](#5-jobs-职位表) - 职位表

### 3. 申请管理模块
- [job_applications](#6-job_applications-职位申请表) - 职位申请表
- [daily_application_limits](#7-daily_application_limits-每日申请限制记录表) - 每日申请限制记录表

### 4. 消息通知模块
- [messages](#8-messages-消息表) - 消息表
- [notifications](#9-notifications-系统通知表) - 系统通知表

### 5. 内容管理模块
- [articles](#10-articles-博客文章表) - 博客文章表
- [tags](#11-tags-标签表) - 标签表
- [article_tags](#12-article_tags-文章标签关联表) - 文章标签关联表

### 6. 用户行为模块
- [user_favorites](#13-user_favorites-用户收藏表) - 用户收藏表
- [job_alerts](#14-job_alerts-职位提醒表) - 职位提醒表
- [reviews](#15-reviews-用户评价表) - 用户评价表

### 7. 系统管理模块
- [system_configs](#16-system_configs-系统配置表) - 系统配置表
- [operation_logs](#17-operation_logs-操作日志表) - 操作日志表

---

## 📋 详细表结构

### 1. users (用户主表)

**表描述**: 存储平台所有用户的基本信息，包括求职者、雇主和管理员。

| 字段名 | 数据类型 | 约束 | 默认值 | 描述 |
|--------|----------|------|--------|------|
| user_id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | - | 用户ID |
| email | VARCHAR(255) | NOT NULL, UNIQUE | - | 邮箱地址 |
| password | VARCHAR(255) | NOT NULL | - | 密码哈希 |
| role | TINYINT | NOT NULL | 1 | 用户角色: 0-管理员, 1-求职者, 2-雇主 |
| status | TINYINT | NOT NULL | 1 | 账户状态: 0-禁用, 1-正常, 2-待验证 |
| email_verified | BOOLEAN | - | FALSE | 邮箱是否已验证 |
| last_login_at | TIMESTAMP | NULL | NULL | 最后登录时间 |
| created_at | TIMESTAMP | - | CURRENT_TIMESTAMP | 创建时间 |
| updated_at | TIMESTAMP | ON UPDATE | CURRENT_TIMESTAMP | 更新时间 |

**索引**:
- `PRIMARY KEY` (user_id)
- `UNIQUE KEY` (email)
- `INDEX idx_email` (email)
- `INDEX idx_role` (role)
- `INDEX idx_status` (status)
- `INDEX idx_created_at` (created_at)

**业务规则**:
- 邮箱地址必须唯一
- 用户角色决定了用户的权限和功能访问
- 账户状态控制用户是否可以正常使用平台

---

### 2. candidates (求职者信息表)

**表描述**: 存储求职者的详细信息，包括个人资料、技能、经验等。

| 字段名 | 数据类型 | 约束 | 默认值 | 描述 |
|--------|----------|------|--------|------|
| candidate_id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | - | 求职者ID |
| user_id | BIGINT | NOT NULL, FOREIGN KEY | - | 关联用户ID |
| name | VARCHAR(100) | NOT NULL | - | 姓名 |
| phone | VARCHAR(20) | - | NULL | 手机号 |
| avatar_url | VARCHAR(500) | - | NULL | 头像URL |
| location | VARCHAR(200) | - | NULL | 所在地 |
| current_position | VARCHAR(200) | - | NULL | 当前职位 |
| experience_years | TINYINT | - | 0 | 工作经验年数 |
| education_level | TINYINT | - | NULL | 教育水平: 1-高中, 2-专科, 3-本科, 4-硕士, 5-博士 |
| expected_salary_min | INT | - | NULL | 期望薪资最低值 |
| expected_salary_max | INT | - | NULL | 期望薪资最高值 |
| job_status | TINYINT | - | 1 | 求职状态: 0-不找工作, 1-看机会, 2-急找工作 |
| resume_url | VARCHAR(500) | - | NULL | 简历文件URL |
| bio | TEXT | - | NULL | 个人简介 |
| skills | JSON | - | NULL | 技能标签JSON |
| created_at | TIMESTAMP | - | CURRENT_TIMESTAMP | 创建时间 |
| updated_at | TIMESTAMP | ON UPDATE | CURRENT_TIMESTAMP | 更新时间 |

**外键约束**:
- `FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE`

**索引**:
- `PRIMARY KEY` (candidate_id)
- `INDEX idx_user_id` (user_id)
- `INDEX idx_location` (location)
- `INDEX idx_experience` (experience_years)
- `INDEX idx_job_status` (job_status)

**业务规则**:
- 每个用户只能有一个求职者档案
- 技能信息以JSON格式存储，支持灵活的技能标签
- 求职状态影响职位推荐和搜索结果

---

### 3. employers (雇主信息表)

**表描述**: 存储雇主公司的详细信息，包括公司资料、联系方式等。

| 字段名 | 数据类型 | 约束 | 默认值 | 描述 |
|--------|----------|------|--------|------|
| employer_id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | - | 雇主ID |
| user_id | BIGINT | NOT NULL, FOREIGN KEY | - | 关联用户ID |
| company_name | VARCHAR(200) | NOT NULL | - | 公司名称 |
| company_logo | VARCHAR(500) | - | NULL | 公司Logo URL |
| industry | VARCHAR(100) | - | NULL | 所属行业 |
| company_size | TINYINT | - | NULL | 公司规模: 1-1-10人, 2-11-50人, 3-51-200人, 4-201-1000人, 5-1000+人 |
| founded_year | YEAR | - | NULL | 成立年份 |
| website | VARCHAR(300) | - | NULL | 公司官网 |
| location | VARCHAR(200) | - | NULL | 公司地址 |
| description | TEXT | - | NULL | 公司简介 |
| contact_person | VARCHAR(100) | - | NULL | 联系人姓名 |
| contact_phone | VARCHAR(20) | - | NULL | 联系电话 |
| verified | BOOLEAN | - | FALSE | 是否已认证 |
| created_at | TIMESTAMP | - | CURRENT_TIMESTAMP | 创建时间 |
| updated_at | TIMESTAMP | ON UPDATE | CURRENT_TIMESTAMP | 更新时间 |

**外键约束**:
- `FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE`

**索引**:
- `PRIMARY KEY` (employer_id)
- `INDEX idx_user_id` (user_id)
- `INDEX idx_company_name` (company_name)
- `INDEX idx_industry` (industry)
- `INDEX idx_verified` (verified)

**业务规则**:
- 每个用户只能有一个雇主档案
- 认证状态影响职位发布权限和可信度
- 公司信息用于职位展示和候选人筛选

---

### 4. job_categories (职位分类表)

**表描述**: 存储职位分类信息，用于职位归类和筛选。

| 字段名 | 数据类型 | 约束 | 默认值 | 描述 |
|--------|----------|------|--------|------|
| category_id | INT | PRIMARY KEY, AUTO_INCREMENT | - | 分类ID |
| name | VARCHAR(100) | NOT NULL | - | 分类名称 |
| slug | VARCHAR(100) | NOT NULL, UNIQUE | - | 分类标识 |
| description | TEXT | - | NULL | 分类描述 |
| sort_order | INT | - | 0 | 排序权重 |
| status | TINYINT | - | 1 | 状态: 0-禁用, 1-启用 |
| created_at | TIMESTAMP | - | CURRENT_TIMESTAMP | 创建时间 |

**索引**:
- `PRIMARY KEY` (category_id)
- `UNIQUE KEY` (slug)
- `INDEX idx_slug` (slug)
- `INDEX idx_status` (status)
- `INDEX idx_sort_order` (sort_order)

**业务规则**:
- 分类标识(slug)必须唯一，用于URL友好的分类访问
- 排序权重控制分类在前端的显示顺序
- 状态控制分类是否在前端显示

---

### 5. jobs (职位表)

**表描述**: 存储职位信息，包括职位详情、要求、薪资等核心信息。

| 字段名 | 数据类型 | 约束 | 默认值 | 描述 |
|--------|----------|------|--------|------|
| job_id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | - | 职位ID |
| employer_id | BIGINT | NOT NULL, FOREIGN KEY | - | 发布雇主ID |
| category_id | INT | FOREIGN KEY | NULL | 职位分类ID |
| title | VARCHAR(200) | NOT NULL | - | 职位标题 |
| description | TEXT | NOT NULL | - | 职位描述 |
| requirements | TEXT | - | NULL | 职位要求 |
| responsibilities | TEXT | - | NULL | 工作职责 |
| skills_required | JSON | - | NULL | 必需技能JSON |
| skills_preferred | JSON | - | NULL | 优先技能JSON |
| employment_type | TINYINT | NOT NULL | - | 工作类型: 1-全职, 2-兼职, 3-合同工, 4-实习 |
| experience_level | TINYINT | - | NULL | 经验要求: 1-应届生, 2-初级, 3-中级, 4-高级, 5-专家 |
| education_level | TINYINT | - | NULL | 学历要求: 1-不限, 2-高中, 3-专科, 4-本科, 5-硕士, 6-博士 |
| salary_min | INT | - | NULL | 薪资最低值 |
| salary_max | INT | - | NULL | 薪资最高值 |
| salary_currency | VARCHAR(10) | - | 'USD' | 薪资币种 |
| location | VARCHAR(200) | - | NULL | 工作地点 |
| remote_type | TINYINT | - | 1 | 远程类型: 1-完全远程, 2-混合办公, 3-现场办公 |
| benefits | TEXT | - | NULL | 福利待遇 |
| application_deadline | DATE | - | NULL | 申请截止日期 |
| status | TINYINT | - | 0 | 职位状态: 0-待审核, 1-招聘中, 2-暂停, 3-已关闭 |
| view_count | INT | - | 0 | 浏览次数 |
| application_count | INT | - | 0 | 申请次数 |
| featured | BOOLEAN | - | FALSE | 是否精选职位 |
| created_at | TIMESTAMP | - | CURRENT_TIMESTAMP | 创建时间 |
| updated_at | TIMESTAMP | ON UPDATE | CURRENT_TIMESTAMP | 更新时间 |

**外键约束**:
- `FOREIGN KEY (employer_id) REFERENCES employers(employer_id) ON DELETE CASCADE`
- `FOREIGN KEY (category_id) REFERENCES job_categories(category_id) ON DELETE SET NULL`

**索引**:
- `PRIMARY KEY` (job_id)
- `INDEX idx_employer_id` (employer_id)
- `INDEX idx_category_id` (category_id)
- `INDEX idx_status` (status)
- `INDEX idx_created_at` (created_at)
- `INDEX idx_location` (location)
- `INDEX idx_employment_type` (employment_type)
- `INDEX idx_experience_level` (experience_level)
- `INDEX idx_featured` (featured)
- `FULLTEXT idx_title_description` (title, description)

**业务规则**:
- 职位必须关联到有效的雇主
- 技能要求以JSON格式存储，支持灵活的技能匹配
- 全文搜索索引支持职位标题和描述的快速搜索
- 精选职位在搜索结果中优先显示

---

### 6. job_applications (职位申请表)

**表描述**: 存储求职者的职位申请记录，包括申请状态和雇主反馈。

| 字段名 | 数据类型 | 约束 | 默认值 | 描述 |
|--------|----------|------|--------|------|
| application_id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | - | 申请ID |
| job_id | BIGINT | NOT NULL, FOREIGN KEY | - | 职位ID |
| candidate_id | BIGINT | NOT NULL, FOREIGN KEY | - | 求职者ID |
| cover_letter | TEXT | - | NULL | 求职信 |
| resume_url | VARCHAR(500) | - | NULL | 简历URL |
| status | TINYINT | - | 1 | 申请状态: 1-已提交, 2-已查看, 3-面试中, 4-已拒绝, 5-已录用 |
| employer_notes | TEXT | - | NULL | 雇主备注 |
| applied_at | TIMESTAMP | - | CURRENT_TIMESTAMP | 申请时间 |
| updated_at | TIMESTAMP | ON UPDATE | CURRENT_TIMESTAMP | 更新时间 |

**外键约束**:
- `FOREIGN KEY (job_id) REFERENCES jobs(job_id) ON DELETE CASCADE`
- `FOREIGN KEY (candidate_id) REFERENCES candidates(candidate_id) ON DELETE CASCADE`

**索引**:
- `PRIMARY KEY` (application_id)
- `UNIQUE KEY uk_job_candidate` (job_id, candidate_id) - 防止重复申请
- `INDEX idx_job_id` (job_id)
- `INDEX idx_candidate_id` (candidate_id)
- `INDEX idx_status` (status)
- `INDEX idx_applied_at` (applied_at)

**业务规则**:
- 同一求职者不能重复申请同一职位
- 申请状态变更会触发通知机制
- 雇主可以添加备注信息用于内部管理

---

### 7. daily_application_limits (每日申请限制记录表)

**表描述**: 记录求职者每日申请次数，用于实施申请频率限制。

| 字段名 | 数据类型 | 约束 | 默认值 | 描述 |
|--------|----------|------|--------|------|
| limit_id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | - | 记录ID |
| candidate_id | BIGINT | NOT NULL, FOREIGN KEY | - | 求职者ID |
| application_date | DATE | NOT NULL | - | 申请日期 |
| application_count | INT | - | 0 | 当日申请次数 |
| created_at | TIMESTAMP | - | CURRENT_TIMESTAMP | 创建时间 |
| updated_at | TIMESTAMP | ON UPDATE | CURRENT_TIMESTAMP | 更新时间 |

**外键约束**:
- `FOREIGN KEY (candidate_id) REFERENCES candidates(candidate_id) ON DELETE CASCADE`

**索引**:
- `PRIMARY KEY` (limit_id)
- `UNIQUE KEY uk_candidate_date` (candidate_id, application_date)
- `INDEX idx_application_date` (application_date)

**业务规则**:
- 每个求职者每天的申请记录唯一
- 系统配置中定义每日申请上限
- 自动清理过期记录以节省存储空间

---

### 8. messages (消息表)

**表描述**: 存储用户之间的消息通信记录，包括面试邀请、工作邀请等。

| 字段名 | 数据类型 | 约束 | 默认值 | 描述 |
|--------|----------|------|--------|------|
| message_id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | - | 消息ID |
| sender_id | BIGINT | NOT NULL, FOREIGN KEY | - | 发送者用户ID |
| receiver_id | BIGINT | NOT NULL, FOREIGN KEY | - | 接收者用户ID |
| message_type | TINYINT | - | 1 | 消息类型: 1-普通消息, 2-面试邀请, 3-工作邀请, 4-系统通知 |
| subject | VARCHAR(200) | - | NULL | 消息主题 |
| content | TEXT | NOT NULL | - | 消息内容 |
| read_status | BOOLEAN | - | FALSE | 是否已读 |
| read_at | TIMESTAMP | NULL | NULL | 阅读时间 |
| created_at | TIMESTAMP | - | CURRENT_TIMESTAMP | 创建时间 |

**外键约束**:
- `FOREIGN KEY (sender_id) REFERENCES users(user_id) ON DELETE CASCADE`
- `FOREIGN KEY (receiver_id) REFERENCES users(user_id) ON DELETE CASCADE`

**索引**:
- `PRIMARY KEY` (message_id)
- `INDEX idx_sender_id` (sender_id)
- `INDEX idx_receiver_id` (receiver_id)
- `INDEX idx_message_type` (message_type)
- `INDEX idx_read_status` (read_status)
- `INDEX idx_created_at` (created_at)

**业务规则**:
- 消息类型决定了消息的处理方式和显示样式
- 已读状态和时间用于消息管理和统计
- 支持不同类型的消息模板

---

### 9. notifications (系统通知表)

**表描述**: 存储系统生成的通知信息，如申请状态更新、职位匹配等。

| 字段名 | 数据类型 | 约束 | 默认值 | 描述 |
|--------|----------|------|--------|------|
| notification_id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | - | 通知ID |
| user_id | BIGINT | NOT NULL, FOREIGN KEY | - | 用户ID |
| type | VARCHAR(50) | NOT NULL | - | 通知类型 |
| title | VARCHAR(200) | NOT NULL | - | 通知标题 |
| content | TEXT | - | NULL | 通知内容 |
| data | JSON | - | NULL | 相关数据JSON |
| read_status | BOOLEAN | - | FALSE | 是否已读 |
| read_at | TIMESTAMP | NULL | NULL | 阅读时间 |
| created_at | TIMESTAMP | - | CURRENT_TIMESTAMP | 创建时间 |

**外键约束**:
- `FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE`

**索引**:
- `PRIMARY KEY` (notification_id)
- `INDEX idx_user_id` (user_id)
- `INDEX idx_type` (type)
- `INDEX idx_read_status` (read_status)
- `INDEX idx_created_at` (created_at)

**业务规则**:
- 通知类型用于分类和过滤不同类型的通知
- JSON数据字段存储通知相关的结构化信息
- 支持批量标记已读和自动清理机制

---

### 10. articles (博客文章表)

**表描述**: 存储平台博客文章，用于内容营销和用户教育。

| 字段名 | 数据类型 | 约束 | 默认值 | 描述 |
|--------|----------|------|--------|------|
| article_id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | - | 文章ID |
| author_id | BIGINT | NOT NULL, FOREIGN KEY | - | 作者用户ID |
| title | VARCHAR(300) | NOT NULL | - | 文章标题 |
| slug | VARCHAR(300) | NOT NULL, UNIQUE | - | 文章标识 |
| content | LONGTEXT | NOT NULL | - | 文章内容 |
| excerpt | TEXT | - | NULL | 文章摘要 |
| featured_image | VARCHAR(500) | - | NULL | 特色图片URL |
| status | TINYINT | - | 0 | 文章状态: 0-草稿, 1-已发布, 2-已下线 |
| view_count | INT | - | 0 | 浏览次数 |
| published_at | TIMESTAMP | NULL | NULL | 发布时间 |
| created_at | TIMESTAMP | - | CURRENT_TIMESTAMP | 创建时间 |
| updated_at | TIMESTAMP | ON UPDATE | CURRENT_TIMESTAMP | 更新时间 |

**外键约束**:
- `FOREIGN KEY (author_id) REFERENCES users(user_id) ON DELETE CASCADE`

**索引**:
- `PRIMARY KEY` (article_id)
- `UNIQUE KEY` (slug)
- `INDEX idx_author_id` (author_id)
- `INDEX idx_slug` (slug)
- `INDEX idx_status` (status)
- `INDEX idx_published_at` (published_at)
- `FULLTEXT idx_title_content` (title, content)

**业务规则**:
- 文章标识(slug)必须唯一，用于SEO友好的URL
- 全文搜索索引支持文章内容的快速搜索
- 发布状态控制文章的可见性

---

### 11. tags (标签表)

**表描述**: 存储系统中的各种标签，包括技能标签、行业标签和文章标签。

| 字段名 | 数据类型 | 约束 | 默认值 | 描述 |
|--------|----------|------|--------|------|
| tag_id | INT | PRIMARY KEY, AUTO_INCREMENT | - | 标签ID |
| name | VARCHAR(100) | NOT NULL, UNIQUE | - | 标签名称 |
| slug | VARCHAR(100) | NOT NULL, UNIQUE | - | 标签标识 |
| type | TINYINT | - | 1 | 标签类型: 1-技能标签, 2-行业标签, 3-文章标签 |
| usage_count | INT | - | 0 | 使用次数 |
| created_at | TIMESTAMP | - | CURRENT_TIMESTAMP | 创建时间 |

**索引**:
- `PRIMARY KEY` (tag_id)
- `UNIQUE KEY` (name)
- `UNIQUE KEY` (slug)
- `INDEX idx_name` (name)
- `INDEX idx_slug` (slug)
- `INDEX idx_type` (type)
- `INDEX idx_usage_count` (usage_count)

**业务规则**:
- 标签名称和标识必须唯一
- 使用次数用于标签热度排序
- 不同类型的标签用于不同的业务场景

---

### 12. article_tags (文章标签关联表)

**表描述**: 文章和标签的多对多关联表。

| 字段名 | 数据类型 | 约束 | 默认值 | 描述 |
|--------|----------|------|--------|------|
| article_id | BIGINT | NOT NULL, FOREIGN KEY | - | 文章ID |
| tag_id | INT | NOT NULL, FOREIGN KEY | - | 标签ID |

**外键约束**:
- `FOREIGN KEY (article_id) REFERENCES articles(article_id) ON DELETE CASCADE`
- `FOREIGN KEY (tag_id) REFERENCES tags(tag_id) ON DELETE CASCADE`

**索引**:
- `PRIMARY KEY` (article_id, tag_id)

**业务规则**:
- 实现文章和标签的多对多关联
- 文章删除时自动删除关联关系
- 标签删除时自动删除关联关系

---

### 13. user_favorites (用户收藏表)

**表描述**: 存储用户的收藏记录，包括收藏的职位、候选人和公司。

| 字段名 | 数据类型 | 约束 | 默认值 | 描述 |
|--------|----------|------|--------|------|
| favorite_id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | - | 收藏ID |
| user_id | BIGINT | NOT NULL, FOREIGN KEY | - | 用户ID |
| target_type | TINYINT | NOT NULL | - | 收藏类型: 1-职位, 2-候选人, 3-公司 |
| target_id | BIGINT | NOT NULL | - | 目标ID |
| created_at | TIMESTAMP | - | CURRENT_TIMESTAMP | 创建时间 |

**外键约束**:
- `FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE`

**索引**:
- `PRIMARY KEY` (favorite_id)
- `UNIQUE KEY uk_user_target` (user_id, target_type, target_id)
- `INDEX idx_user_id` (user_id)
- `INDEX idx_target` (target_type, target_id)

**业务规则**:
- 同一用户不能重复收藏同一目标
- 收藏类型决定了目标的解释方式
- 支持不同类型对象的统一收藏管理

---

### 14. job_alerts (职位提醒表)

**表描述**: 存储用户设置的职位提醒条件，用于自动推送匹配的职位。

| 字段名 | 数据类型 | 约束 | 默认值 | 描述 |
|--------|----------|------|--------|------|
| alert_id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | - | 提醒ID |
| user_id | BIGINT | NOT NULL, FOREIGN KEY | - | 用户ID |
| name | VARCHAR(200) | NOT NULL | - | 提醒名称 |
| keywords | VARCHAR(500) | - | NULL | 关键词 |
| location | VARCHAR(200) | - | NULL | 地点 |
| category_id | INT | FOREIGN KEY | NULL | 职位分类 |
| employment_type | TINYINT | - | NULL | 工作类型 |
| experience_level | TINYINT | - | NULL | 经验要求 |
| salary_min | INT | - | NULL | 最低薪资 |
| frequency | TINYINT | - | 1 | 提醒频率: 1-实时, 2-每日, 3-每周 |
| status | TINYINT | - | 1 | 状态: 0-暂停, 1-启用 |
| last_sent_at | TIMESTAMP | NULL | NULL | 最后发送时间 |
| created_at | TIMESTAMP | - | CURRENT_TIMESTAMP | 创建时间 |
| updated_at | TIMESTAMP | ON UPDATE | CURRENT_TIMESTAMP | 更新时间 |

**外键约束**:
- `FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE`
- `FOREIGN KEY (category_id) REFERENCES job_categories(category_id) ON DELETE SET NULL`

**索引**:
- `PRIMARY KEY` (alert_id)
- `INDEX idx_user_id` (user_id)
- `INDEX idx_status` (status)
- `INDEX idx_frequency` (frequency)

**业务规则**:
- 用户可以设置多个不同条件的职位提醒
- 提醒频率控制推送的时间间隔
- 状态控制提醒是否生效

---

### 15. reviews (用户评价表)

**表描述**: 存储用户对平台和公司的评价信息。

| 字段名 | 数据类型 | 约束 | 默认值 | 描述 |
|--------|----------|------|--------|------|
| review_id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | - | 评价ID |
| reviewer_id | BIGINT | NOT NULL, FOREIGN KEY | - | 评价者用户ID |
| target_type | TINYINT | NOT NULL | - | 评价类型: 1-平台评价, 2-公司评价 |
| target_id | BIGINT | - | NULL | 目标ID (公司ID等) |
| rating | TINYINT | NOT NULL | - | 评分 (1-5星) |
| title | VARCHAR(200) | - | NULL | 评价标题 |
| content | TEXT | - | NULL | 评价内容 |
| status | TINYINT | - | 0 | 审核状态: 0-待审核, 1-已通过, 2-已拒绝 |
| created_at | TIMESTAMP | - | CURRENT_TIMESTAMP | 创建时间 |
| updated_at | TIMESTAMP | ON UPDATE | CURRENT_TIMESTAMP | 更新时间 |

**外键约束**:
- `FOREIGN KEY (reviewer_id) REFERENCES users(user_id) ON DELETE CASCADE`

**索引**:
- `PRIMARY KEY` (review_id)
- `INDEX idx_reviewer_id` (reviewer_id)
- `INDEX idx_target` (target_type, target_id)
- `INDEX idx_rating` (rating)
- `INDEX idx_status` (status)

**业务规则**:
- 评价需要经过审核才能公开显示
- 评分范围限制在1-5星之间
- 支持对不同目标类型的评价

---

### 16. system_configs (系统配置表)

**表描述**: 存储系统的各种配置参数，支持动态配置管理。

| 字段名 | 数据类型 | 约束 | 默认值 | 描述 |
|--------|----------|------|--------|------|
| config_id | INT | PRIMARY KEY, AUTO_INCREMENT | - | 配置ID |
| config_key | VARCHAR(100) | NOT NULL, UNIQUE | - | 配置键 |
| config_value | TEXT | - | NULL | 配置值 |
| description | VARCHAR(500) | - | NULL | 配置描述 |
| type | VARCHAR(50) | - | 'string' | 数据类型 |
| created_at | TIMESTAMP | - | CURRENT_TIMESTAMP | 创建时间 |
| updated_at | TIMESTAMP | ON UPDATE | CURRENT_TIMESTAMP | 更新时间 |

**索引**:
- `PRIMARY KEY` (config_id)
- `UNIQUE KEY` (config_key)
- `INDEX idx_config_key` (config_key)

**业务规则**:
- 配置键必须唯一
- 支持不同数据类型的配置值
- 用于系统参数的动态管理

**默认配置项**:
- `daily_application_limit`: 每日职位申请限制数量 (20)
- `job_approval_required`: 职位发布是否需要审核 (true)
- `email_verification_required`: 注册是否需要邮箱验证 (true)
- `platform_name`: 平台名称 (Flexes)
- `support_email`: 客服邮箱 (support@flexes.work)
- `max_resume_size`: 简历文件最大大小 (5242880 字节)
- `allowed_resume_types`: 允许的简历文件类型 (pdf,doc,docx)

---

### 17. operation_logs (操作日志表)

**表描述**: 记录用户在系统中的各种操作，用于审计和安全监控。

| 字段名 | 数据类型 | 约束 | 默认值 | 描述 |
|--------|----------|------|--------|------|
| log_id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | - | 日志ID |
| user_id | BIGINT | FOREIGN KEY | NULL | 操作用户ID |
| action | VARCHAR(100) | NOT NULL | - | 操作动作 |
| resource_type | VARCHAR(50) | - | NULL | 资源类型 |
| resource_id | BIGINT | - | NULL | 资源ID |
| ip_address | VARCHAR(45) | - | NULL | IP地址 |
| user_agent | TEXT | - | NULL | 用户代理 |
| details | JSON | - | NULL | 操作详情JSON |
| created_at | TIMESTAMP | - | CURRENT_TIMESTAMP | 创建时间 |

**外键约束**:
- `FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE SET NULL`

**索引**:
- `PRIMARY KEY` (log_id)
- `INDEX idx_user_id` (user_id)
- `INDEX idx_action` (action)
- `INDEX idx_resource` (resource_type, resource_id)
- `INDEX idx_created_at` (created_at)

**业务规则**:
- 用户删除时日志保留但用户ID设为NULL
- 支持IPv4和IPv6地址格式
- JSON详情字段存储操作的具体信息
- 定期清理过期日志以节省存储空间

---

## 📊 数据库统计信息

### 表大小预估

| 表名 | 预估行数 | 存储大小 | 增长速度 |
|------|----------|----------|----------|
| users | 10万 | 50MB | 中等 |
| candidates | 8万 | 100MB | 中等 |
| employers | 2万 | 30MB | 慢 |
| jobs | 50万 | 500MB | 快 |
| job_applications | 200万 | 300MB | 快 |
| messages | 100万 | 200MB | 快 |
| notifications | 500万 | 800MB | 很快 |
| articles | 1万 | 100MB | 慢 |
| operation_logs | 1000万 | 2GB | 很快 |

### 性能优化索引

**复合索引**:
- `idx_jobs_search` (status, category_id, location, employment_type, experience_level, created_at)
- `idx_applications_candidate_status` (candidate_id, status, applied_at)
- `idx_applications_job_status` (job_id, status, applied_at)
- `idx_messages_receiver_read` (receiver_id, read_status, created_at)
- `idx_notifications_user_read` (user_id, read_status, created_at)
- `idx_users_role_status_login` (role, status, last_login_at)
- `idx_candidates_search` (job_status, location, experience_years, education_level)
- `idx_employers_verified_industry` (verified, industry, created_at)

**全文搜索索引**:
- `idx_title_description` (jobs.title, jobs.description)
- `idx_title_content` (articles.title, articles.content)

---

## 🔧 数据库维护

### 存储过程

1. **CheckDailyApplicationLimit**: 检查求职者每日申请限制
2. **UpdateDailyApplicationCount**: 更新每日申请计数

### 触发器

1. **after_user_insert**: 用户注册后自动创建角色信息
2. **after_job_application_insert**: 职位申请后更新申请计数

### 视图

1. **job_details_view**: 职位详细信息综合视图
2. **candidate_details_view**: 求职者详细信息综合视图

### 数据清理策略

- **每日申请限制记录**: 保留30天
- **操作日志**: 保留90天
- **已读通知**: 保留30天
- **临时文件**: 保留7天

---

## 📋 枚举值定义

### 用户相关
- **用户角色** (users.role): 0-管理员, 1-求职者, 2-雇主
- **账户状态** (users.status): 0-禁用, 1-正常, 2-待验证
- **教育水平**: 1-高中, 2-专科, 3-本科, 4-硕士, 5-博士
- **求职状态** (candidates.job_status): 0-不找工作, 1-看机会, 2-急找工作
- **公司规模** (employers.company_size): 1-1-10人, 2-11-50人, 3-51-200人, 4-201-1000人, 5-1000+人

### 职位相关
- **工作类型** (jobs.employment_type): 1-全职, 2-兼职, 3-合同工, 4-实习
- **经验要求** (jobs.experience_level): 1-应届生, 2-初级, 3-中级, 4-高级, 5-专家
- **远程类型** (jobs.remote_type): 1-完全远程, 2-混合办公, 3-现场办公
- **职位状态** (jobs.status): 0-待审核, 1-招聘中, 2-暂停, 3-已关闭
- **申请状态** (job_applications.status): 1-已提交, 2-已查看, 3-面试中, 4-已拒绝, 5-已录用

### 其他
- **消息类型** (messages.message_type): 1-普通消息, 2-面试邀请, 3-工作邀请, 4-系统通知
- **标签类型** (tags.type): 1-技能标签, 2-行业标签, 3-文章标签
- **收藏类型** (user_favorites.target_type): 1-职位, 2-候选人, 3-公司
- **提醒频率** (job_alerts.frequency): 1-实时, 2-每日, 3-每周
- **评价类型** (reviews.target_type): 1-平台评价, 2-公司评价

---

*本文档基于 Flexes v1.0 数据库设计，最后更新时间：2025-09-17*
