# Flexes 平台数据库文档

## 📋 概述

本目录包含 Flexes 远程工程招聘平台的完整数据库设计和相关脚本文件。

## 📁 文件说明

### 1. `flexes_database.sql` - 主数据库结构
- **用途**: 创建完整的数据库结构
- **包含内容**:
  - 数据库创建
  - 所有数据表定义
  - 索引和外键约束
  - 视图定义
  - 存储过程
  - 触发器
  - 初始化数据

### 2. `database_indexes.sql` - 性能优化索引
- **用途**: 数据库性能优化
- **包含内容**:
  - 复合索引优化
  - 查询性能监控脚本
  - 数据清理脚本
  - 备份脚本示例

### 3. `test_data.sql` - 测试数据
- **用途**: 开发和测试环境数据填充
- **包含内容**:
  - 测试用户账户
  - 示例公司和职位
  - 模拟申请和消息
  - 测试文章和配置

## 🚀 快速开始

### 1. 创建数据库

```bash
# 连接到MySQL
mysql -u root -p

# 执行主数据库脚本
source /path/to/flexes_database.sql

# 执行索引优化脚本
source /path/to/database_indexes.sql

# (可选) 插入测试数据
source /path/to/test_data.sql
```

### 2. 验证安装

```sql
-- 检查数据库是否创建成功
USE flexes_platform;
SHOW TABLES;

-- 检查表结构
DESCRIBE users;
DESCRIBE jobs;
DESCRIBE job_applications;

-- 检查初始数据
SELECT * FROM job_categories;
SELECT * FROM system_configs;
```

## 📊 数据库设计概览

### 核心表结构

```
users (用户主表)
├── candidates (求职者信息)
├── employers (雇主信息)
└── operation_logs (操作日志)

jobs (职位表)
├── job_categories (职位分类)
├── job_applications (职位申请)
└── daily_application_limits (申请限制)

messages (消息表)
notifications (通知表)
articles (文章表)
tags (标签表)
reviews (评价表)
```

### 用户角色定义

| 角色ID | 角色名称 | 说明 |
|--------|----------|------|
| 0 | 管理员 | 平台管理和维护 |
| 1 | 求职者 | 寻找工作机会 |
| 2 | 雇主 | 发布职位和招聘 |

### 关键业务规则

1. **申请限制**: 求职者每天最多申请20个职位
2. **重复申请**: 不允许对同一职位重复申请
3. **职位审核**: 新发布职位需要管理员审核
4. **邮箱验证**: 用户注册需要邮箱验证

## 🔧 配置说明

### 系统配置项

| 配置键 | 默认值 | 说明 |
|--------|--------|------|
| daily_application_limit | 20 | 每日申请限制 |
| job_approval_required | true | 职位是否需要审核 |
| email_verification_required | true | 是否需要邮箱验证 |
| max_resume_size | 5242880 | 简历文件最大大小(5MB) |

### 数据库连接配置

```yaml
# 推荐配置
database:
  host: localhost
  port: 3306
  name: flexes_platform
  charset: utf8mb4
  collation: utf8mb4_unicode_ci
  
# 连接池配置
pool:
  min_connections: 5
  max_connections: 20
  idle_timeout: 300
```

## 📈 性能优化

### 索引策略

1. **主键索引**: 所有表都有自增主键
2. **外键索引**: 所有外键字段都有索引
3. **查询索引**: 基于常用查询模式创建复合索引
4. **全文索引**: 职位标题和描述支持全文搜索

### 查询优化建议

```sql
-- 职位搜索优化
SELECT * FROM jobs 
WHERE status = 1 
  AND category_id = ? 
  AND location LIKE ? 
ORDER BY created_at DESC 
LIMIT 20;

-- 使用复合索引: idx_jobs_search

-- 申请记录查询优化
SELECT * FROM job_applications 
WHERE candidate_id = ? 
  AND status IN (1,2,3) 
ORDER BY applied_at DESC;

-- 使用复合索引: idx_applications_candidate_status
```

## 🔒 安全考虑

### 数据安全

1. **密码加密**: 使用bcrypt加密存储
2. **SQL注入防护**: 使用参数化查询
3. **数据备份**: 定期自动备份
4. **访问控制**: 基于角色的权限控制

### 备份策略

```bash
# 每日备份脚本
#!/bin/bash
DATE=$(date +%Y%m%d_%H%M%S)
mysqldump -u backup_user -p --single-transaction \
  --routines --triggers flexes_platform > \
  /backup/flexes_backup_$DATE.sql

# 保留30天备份
find /backup -name "flexes_backup_*.sql" -mtime +30 -delete
```

## 🧪 测试数据说明

### 测试账户

| 邮箱 | 角色 | 密码 | 说明 |
|------|------|------|------|
| admin@flexes.work | 管理员 | password123 | 系统管理员 |
| john.doe@example.com | 求职者 | password123 | 高级前端开发 |
| hr@techcorp.com | 雇主 | password123 | TechCorp HR |

### 测试数据包含

- 5个求职者账户和详细资料
- 3个雇主账户和公司信息
- 3个测试职位
- 职位申请记录
- 消息和通知示例
- 博客文章示例

## 🔄 数据迁移

### 版本升级

```sql
-- 检查当前版本
SELECT config_value FROM system_configs 
WHERE config_key = 'database_version';

-- 升级脚本示例
-- ALTER TABLE users ADD COLUMN phone VARCHAR(20);
-- UPDATE system_configs SET config_value = '1.1' 
-- WHERE config_key = 'database_version';
```

### 数据导入导出

```bash
# 导出特定表数据
mysqldump -u username -p flexes_platform users candidates > users_backup.sql

# 导入数据
mysql -u username -p flexes_platform < users_backup.sql
```

## 📞 支持和维护

### 常见问题

1. **连接超时**: 检查连接池配置和网络连接
2. **查询慢**: 使用EXPLAIN分析查询计划，检查索引使用
3. **磁盘空间**: 定期清理日志表和临时文件
4. **锁等待**: 优化事务大小，避免长事务

### 监控指标

- 连接数使用率
- 查询响应时间
- 慢查询数量
- 磁盘使用率
- 索引命中率

---

**维护团队**: Flexes 开发团队  
**文档版本**: v1.0  
**最后更新**: 2025-09-17
