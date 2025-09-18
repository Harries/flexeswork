# Flexes 数据库技术规范

## 📋 概述

本文档定义了 Flexes 远程工程招聘平台数据库设计和开发的技术规范，确保数据一致性、性能优化和可维护性。

## 🏗️ 技术栈

### 核心技术
- **MySQL**: 8.0+ - 关系型数据库
- **InnoDB**: 存储引擎
- **utf8mb4**: 字符集编码
- **HikariCP**: 数据库连接池
- **Spring Data JPA**: ORM框架
- **Hibernate**: JPA实现

### 开发工具
- **MySQL Workbench**: 数据库设计和管理
- **Flyway**: 数据库版本控制 (可选)
- **phpMyAdmin**: Web管理界面 (开发环境)

## 🗄️ 数据库配置规范

### 1. 数据库创建标准
```sql
-- ✅ 推荐的数据库创建方式
CREATE DATABASE IF NOT EXISTS flexes_platform 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

-- 设置默认存储引擎
SET default_storage_engine = InnoDB;

-- 设置SQL模式 (严格模式)
SET sql_mode = 'STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
```

### 2. 用户权限管理
```sql
-- 创建应用用户
CREATE USER 'flexes_user'@'localhost' IDENTIFIED BY 'secure_password';
CREATE USER 'flexes_user'@'%' IDENTIFIED BY 'secure_password';

-- 授予权限
GRANT SELECT, INSERT, UPDATE, DELETE ON flexes_platform.* TO 'flexes_user'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE ON flexes_platform.* TO 'flexes_user'@'%';

-- 创建只读用户 (用于报表和分析)
CREATE USER 'flexes_readonly'@'localhost' IDENTIFIED BY 'readonly_password';
GRANT SELECT ON flexes_platform.* TO 'flexes_readonly'@'localhost';

-- 创建备份用户
CREATE USER 'flexes_backup'@'localhost' IDENTIFIED BY 'backup_password';
GRANT SELECT, LOCK TABLES ON flexes_platform.* TO 'flexes_backup'@'localhost';

FLUSH PRIVILEGES;
```

### 3. 连接池配置
```yaml
# application.yml
spring:
  datasource:
    type: com.zaxxer.hikari.HikariDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    hikari:
      maximum-pool-size: 20          # 最大连接数
      minimum-idle: 5                # 最小空闲连接数
      idle-timeout: 300000           # 空闲超时时间 (5分钟)
      connection-timeout: 20000      # 连接超时时间 (20秒)
      max-lifetime: 1800000          # 连接最大生命周期 (30分钟)
      pool-name: FlexesHikariCP
      connection-test-query: SELECT 1
      leak-detection-threshold: 60000 # 连接泄漏检测阈值 (1分钟)
```

## 📊 表设计规范

### 1. 命名规范

#### 表命名
```sql
-- ✅ 推荐 - 使用复数形式，下划线分隔
CREATE TABLE users (...);
CREATE TABLE job_applications (...);
CREATE TABLE daily_application_limits (...);

-- ❌ 不推荐
CREATE TABLE User (...);
CREATE TABLE jobApplication (...);
CREATE TABLE DailyApplicationLimit (...);
```

#### 字段命名
```sql
-- ✅ 推荐 - 下划线分隔，描述性强
CREATE TABLE users (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ❌ 不推荐
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,  -- 不够描述性
    Email VARCHAR(255) NOT NULL,           -- 大小写混合
    createTime TIMESTAMP                   -- 驼峰命名
);
```

### 2. 数据类型规范

#### 主键设计
```sql
-- ✅ 推荐 - 使用BIGINT自增主键
user_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID'

-- ✅ 推荐 - 复合主键 (必要时)
PRIMARY KEY (user_id, job_id)

-- ❌ 不推荐 - 使用UUID作为主键 (性能考虑)
user_id VARCHAR(36) PRIMARY KEY
```

#### 字符串类型
```sql
-- ✅ 推荐的字符串类型使用
email VARCHAR(255) NOT NULL COMMENT '邮箱地址',           -- 邮箱
name VARCHAR(100) NOT NULL COMMENT '姓名',                -- 姓名
phone VARCHAR(20) COMMENT '手机号',                       -- 电话
url VARCHAR(500) COMMENT '链接地址',                      -- URL
description TEXT COMMENT '描述信息',                      -- 长文本
bio TEXT COMMENT '个人简介',                              -- 个人简介
content LONGTEXT COMMENT '文章内容'                       -- 超长文本

-- ❌ 不推荐
name VARCHAR(255),  -- 过长
phone VARCHAR(50),  -- 过长
description VARCHAR(1000)  -- 应该使用TEXT
```

#### 数值类型
```sql
-- ✅ 推荐的数值类型使用
user_id BIGINT,                    -- 大整数ID
role TINYINT NOT NULL DEFAULT 1,   -- 枚举值
status TINYINT NOT NULL DEFAULT 1, -- 状态值
salary_min INT,                    -- 薪资
experience_years TINYINT DEFAULT 0, -- 工作年限
usage_count INT DEFAULT 0          -- 计数器

-- ❌ 不推荐
role INT,           -- 浪费空间
salary_min BIGINT,  -- 过大
count TINYINT       -- 可能溢出
```

#### 时间类型
```sql
-- ✅ 推荐的时间类型使用
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
published_at TIMESTAMP NULL COMMENT '发布时间',
birth_date DATE COMMENT '出生日期',
application_date DATE NOT NULL COMMENT '申请日期'

-- ❌ 不推荐
created_at DATETIME,  -- 不支持时区
timestamp_field INT   -- 使用时间戳整数
```

#### JSON类型
```sql
-- ✅ 推荐 - 使用JSON类型存储结构化数据
skills JSON COMMENT '技能标签JSON',
preferences JSON COMMENT '偏好设置JSON',
metadata JSON COMMENT '元数据JSON'

-- ✅ 推荐 - JSON字段的查询示例
SELECT * FROM candidates 
WHERE JSON_CONTAINS(skills, '"JavaScript"');

SELECT * FROM candidates 
WHERE JSON_EXTRACT(preferences, '$.remote_work') = true;
```

### 3. 约束和索引规范

#### 主键和外键
```sql
-- ✅ 推荐的主键定义
CREATE TABLE users (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    -- 其他字段...
) ENGINE=InnoDB COMMENT='用户主表';

-- ✅ 推荐的外键定义
CREATE TABLE job_applications (
    application_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    job_id BIGINT NOT NULL COMMENT '职位ID',
    candidate_id BIGINT NOT NULL COMMENT '候选人ID',
    
    -- 外键约束
    FOREIGN KEY (job_id) REFERENCES jobs(job_id) ON DELETE CASCADE,
    FOREIGN KEY (candidate_id) REFERENCES candidates(candidate_id) ON DELETE CASCADE,
    
    -- 唯一约束
    UNIQUE KEY uk_job_candidate (job_id, candidate_id)
) ENGINE=InnoDB COMMENT='职位申请表';
```

#### 索引设计
```sql
-- ✅ 推荐的索引设计
CREATE TABLE users (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL UNIQUE,
    role TINYINT NOT NULL DEFAULT 1,
    status TINYINT NOT NULL DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    -- 单列索引
    INDEX idx_email (email),
    INDEX idx_role (role),
    INDEX idx_status (status),
    INDEX idx_created_at (created_at),
    
    -- 复合索引 (最常用的查询组合)
    INDEX idx_role_status (role, status),
    INDEX idx_status_created (status, created_at)
) ENGINE=InnoDB;

-- ✅ 推荐的复合索引设计原则
-- 1. 最常用的查询条件放在前面
-- 2. 选择性高的字段放在前面
-- 3. 范围查询的字段放在最后
CREATE INDEX idx_jobs_search ON jobs (
    status,           -- 等值查询，选择性高
    category_id,      -- 等值查询
    location,         -- 等值查询
    employment_type,  -- 等值查询
    created_at        -- 范围查询，放在最后
);
```

#### 全文索引
```sql
-- ✅ 推荐的全文索引使用
CREATE TABLE articles (
    article_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    content LONGTEXT NOT NULL,
    
    -- 全文索引
    FULLTEXT idx_title_content (title, content)
) ENGINE=InnoDB;

-- 全文搜索查询示例
SELECT * FROM articles 
WHERE MATCH(title, content) AGAINST('远程工作 招聘' IN NATURAL LANGUAGE MODE);
```

## 🔧 性能优化规范

### 1. 查询优化

#### 高效查询编写
```sql
-- ✅ 推荐的查询方式
-- 使用索引字段进行查询
SELECT * FROM users WHERE email = 'user@example.com';

-- 使用LIMIT限制结果集
SELECT * FROM jobs WHERE status = 1 ORDER BY created_at DESC LIMIT 20;

-- 使用EXISTS代替IN (大数据集)
SELECT * FROM users u 
WHERE EXISTS (
    SELECT 1 FROM job_applications ja 
    WHERE ja.candidate_id = u.user_id
);

-- ❌ 不推荐的查询方式
-- 避免在WHERE子句中使用函数
SELECT * FROM users WHERE YEAR(created_at) = 2025;  -- 不推荐
SELECT * FROM users WHERE created_at >= '2025-01-01' AND created_at < '2026-01-01';  -- 推荐

-- 避免SELECT *
SELECT * FROM users;  -- 不推荐
SELECT user_id, email, name FROM users;  -- 推荐
```

#### 分页查询优化
```sql
-- ✅ 推荐的分页查询 (小偏移量)
SELECT * FROM jobs 
WHERE status = 1 
ORDER BY created_at DESC 
LIMIT 20 OFFSET 0;

-- ✅ 推荐的深分页优化 (大偏移量)
SELECT * FROM jobs 
WHERE status = 1 AND job_id > 1000
ORDER BY job_id 
LIMIT 20;

-- 或使用游标分页
SELECT * FROM jobs 
WHERE status = 1 AND created_at < '2025-01-15 10:00:00'
ORDER BY created_at DESC 
LIMIT 20;
```

### 2. 批量操作优化

#### 批量插入
```sql
-- ✅ 推荐的批量插入
INSERT INTO job_applications (job_id, candidate_id, status, applied_at) VALUES
(1, 101, 1, NOW()),
(2, 102, 1, NOW()),
(3, 103, 1, NOW()),
-- ... 更多记录
(20, 120, 1, NOW());

-- ✅ 推荐的批量更新
UPDATE job_applications 
SET status = 2, updated_at = NOW() 
WHERE application_id IN (1, 2, 3, 4, 5);
```

#### 事务处理
```sql
-- ✅ 推荐的事务使用
START TRANSACTION;

INSERT INTO users (email, password, role) VALUES ('user@example.com', 'hashed_password', 1);
SET @user_id = LAST_INSERT_ID();

INSERT INTO candidates (user_id, name, phone) VALUES (@user_id, 'John Doe', '+1234567890');

COMMIT;

-- 错误处理
START TRANSACTION;
-- SQL操作...
-- 如果出错则 ROLLBACK;
-- 如果成功则 COMMIT;
```

## 📈 监控和维护规范

### 1. 性能监控

#### 慢查询监控
```sql
-- 开启慢查询日志
SET GLOBAL slow_query_log = 'ON';
SET GLOBAL long_query_time = 2;  -- 2秒以上的查询记录
SET GLOBAL log_queries_not_using_indexes = 'ON';

-- 查看慢查询统计
SHOW GLOBAL STATUS LIKE 'Slow_queries';

-- 分析慢查询
SELECT 
    query_time,
    lock_time,
    rows_sent,
    rows_examined,
    sql_text
FROM mysql.slow_log 
ORDER BY query_time DESC 
LIMIT 10;
```

#### 索引使用分析
```sql
-- 查看表的索引使用情况
SELECT 
    TABLE_NAME,
    INDEX_NAME,
    CARDINALITY,
    SUB_PART,
    NULLABLE,
    INDEX_TYPE
FROM information_schema.STATISTICS 
WHERE TABLE_SCHEMA = 'flexes_platform'
ORDER BY TABLE_NAME, INDEX_NAME;

-- 查看未使用的索引
SELECT 
    object_schema,
    object_name,
    index_name
FROM performance_schema.table_io_waits_summary_by_index_usage 
WHERE index_name IS NOT NULL
AND count_star = 0
AND object_schema = 'flexes_platform';
```

### 2. 数据维护

#### 定期清理脚本
```sql
-- 清理过期的每日申请限制记录 (保留30天)
DELETE FROM daily_application_limits 
WHERE application_date < DATE_SUB(CURDATE(), INTERVAL 30 DAY);

-- 清理过期的操作日志 (保留90天)
DELETE FROM operation_logs 
WHERE created_at < DATE_SUB(NOW(), INTERVAL 90 DAY);

-- 清理已读的旧通知 (保留30天)
DELETE FROM notifications 
WHERE read_status = 1 AND read_at < DATE_SUB(NOW(), INTERVAL 30 DAY);

-- 优化表
OPTIMIZE TABLE users, jobs, job_applications;

-- 分析表
ANALYZE TABLE users, jobs, job_applications;
```

#### 数据统计更新
```sql
-- 更新标签使用次数
UPDATE tags t 
SET usage_count = (
    SELECT COUNT(*) 
    FROM candidates c 
    WHERE JSON_CONTAINS(c.skills, CONCAT('"', t.name, '"'))
) 
WHERE t.type = 1;

-- 更新职位分类统计
UPDATE job_categories jc
SET job_count = (
    SELECT COUNT(*)
    FROM jobs j
    WHERE j.category_id = jc.category_id AND j.status = 1
);
```

## 🔒 安全规范

### 1. 数据安全

#### 敏感数据处理
```sql
-- ✅ 推荐的密码存储 (应用层处理)
-- 密码字段设计
password VARCHAR(255) NOT NULL COMMENT '密码哈希值',

-- 敏感信息字段加密存储
phone VARCHAR(255) COMMENT '加密后的手机号',
id_card VARCHAR(255) COMMENT '加密后的身份证号'

-- ❌ 不推荐 - 明文存储敏感信息
password VARCHAR(50),  -- 明文密码
phone VARCHAR(20),     -- 明文手机号
```

#### 数据访问控制
```sql
-- 创建不同权限级别的用户
-- 应用用户 (读写权限)
CREATE USER 'app_user'@'%' IDENTIFIED BY 'secure_app_password';
GRANT SELECT, INSERT, UPDATE, DELETE ON flexes_platform.* TO 'app_user'@'%';

-- 只读用户 (报表查询)
CREATE USER 'report_user'@'%' IDENTIFIED BY 'secure_report_password';
GRANT SELECT ON flexes_platform.* TO 'report_user'@'%';

-- 备份用户 (备份权限)
CREATE USER 'backup_user'@'localhost' IDENTIFIED BY 'secure_backup_password';
GRANT SELECT, LOCK TABLES ON flexes_platform.* TO 'backup_user'@'localhost';

-- 限制特定表的访问
REVOKE ALL ON flexes_platform.operation_logs FROM 'app_user'@'%';
GRANT SELECT, INSERT ON flexes_platform.operation_logs TO 'app_user'@'%';
```

#### SQL注入防护
```sql
-- ✅ 推荐 - 使用参数化查询 (应用层)
-- 在Java中使用PreparedStatement
-- String sql = "SELECT * FROM users WHERE email = ? AND status = ?";

-- ❌ 不推荐 - 字符串拼接
-- String sql = "SELECT * FROM users WHERE email = '" + email + "'";

-- 数据库层面的输入验证
CREATE TABLE users (
    email VARCHAR(255) NOT NULL CHECK (email REGEXP '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$'),
    phone VARCHAR(20) CHECK (phone REGEXP '^[+]?[0-9-() ]+$')
);
```

### 2. 备份和恢复

#### 备份策略
```bash
#!/bin/bash
# 数据库备份脚本

# 配置变量
DB_NAME="flexes_platform"
BACKUP_DIR="/backup/mysql"
DATE=$(date +%Y%m%d_%H%M%S)
BACKUP_FILE="${BACKUP_DIR}/${DB_NAME}_${DATE}.sql"

# 创建备份目录
mkdir -p $BACKUP_DIR

# 全量备份
mysqldump -u backup_user -p \
  --single-transaction \
  --routines \
  --triggers \
  --events \
  --hex-blob \
  --default-character-set=utf8mb4 \
  $DB_NAME > $BACKUP_FILE

# 压缩备份文件
gzip $BACKUP_FILE

# 删除7天前的备份
find $BACKUP_DIR -name "*.sql.gz" -mtime +7 -delete

echo "备份完成: ${BACKUP_FILE}.gz"
```

#### 恢复策略
```bash
#!/bin/bash
# 数据库恢复脚本

BACKUP_FILE=$1
DB_NAME="flexes_platform"

if [ -z "$BACKUP_FILE" ]; then
    echo "使用方法: $0 <backup_file.sql.gz>"
    exit 1
fi

# 解压备份文件
gunzip -c $BACKUP_FILE > /tmp/restore.sql

# 创建数据库
mysql -u root -p -e "CREATE DATABASE IF NOT EXISTS ${DB_NAME}_restore CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"

# 恢复数据
mysql -u root -p ${DB_NAME}_restore < /tmp/restore.sql

# 清理临时文件
rm /tmp/restore.sql

echo "恢复完成到数据库: ${DB_NAME}_restore"
```

## 📊 数据迁移规范

### 1. 版本控制

#### 数据库版本管理
```sql
-- 创建版本控制表
CREATE TABLE schema_versions (
    version_id INT PRIMARY KEY AUTO_INCREMENT,
    version VARCHAR(20) NOT NULL UNIQUE,
    description TEXT,
    applied_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    applied_by VARCHAR(100) NOT NULL
) ENGINE=InnoDB COMMENT='数据库版本控制表';

-- 插入初始版本
INSERT INTO schema_versions (version, description, applied_by)
VALUES ('1.0.0', '初始数据库结构', 'system');
```

#### 迁移脚本模板
```sql
-- =============================================
-- 数据库迁移脚本
-- 版本: v1.1.0
-- 描述: 添加用户偏好设置表
-- 作者: 开发团队
-- 日期: 2025-01-18
-- =============================================

-- 检查版本
SELECT version FROM schema_versions ORDER BY version_id DESC LIMIT 1;

-- 开始事务
START TRANSACTION;

-- 创建新表
CREATE TABLE user_preferences (
    preference_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    preference_key VARCHAR(100) NOT NULL,
    preference_value TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    UNIQUE KEY uk_user_preference (user_id, preference_key),
    INDEX idx_user_id (user_id)
) ENGINE=InnoDB COMMENT='用户偏好设置表';

-- 更新版本记录
INSERT INTO schema_versions (version, description, applied_by)
VALUES ('1.1.0', '添加用户偏好设置表', 'developer');

-- 提交事务
COMMIT;

-- 验证迁移
SELECT COUNT(*) FROM user_preferences;
SHOW CREATE TABLE user_preferences;
```

### 2. 数据迁移

#### 大数据量迁移
```sql
-- 分批迁移大表数据
DELIMITER $$

CREATE PROCEDURE MigrateUserData()
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE batch_size INT DEFAULT 1000;
    DECLARE current_id BIGINT DEFAULT 0;
    DECLARE max_id BIGINT;

    -- 获取最大ID
    SELECT MAX(user_id) INTO max_id FROM users_old;

    -- 分批处理
    WHILE current_id < max_id DO
        INSERT INTO users_new (email, password, role, status, created_at)
        SELECT email, password, role, status, created_at
        FROM users_old
        WHERE user_id > current_id AND user_id <= current_id + batch_size;

        SET current_id = current_id + batch_size;

        -- 提交当前批次
        COMMIT;

        -- 避免长时间锁表
        SELECT SLEEP(0.1);
    END WHILE;
END$$

DELIMITER ;

-- 执行迁移
CALL MigrateUserData();
```

## 🔍 监控和告警

### 1. 关键指标监控

#### 性能指标查询
```sql
-- 数据库连接数监控
SHOW GLOBAL STATUS LIKE 'Threads_connected';
SHOW GLOBAL STATUS LIKE 'Max_used_connections';

-- 查询性能监控
SHOW GLOBAL STATUS LIKE 'Slow_queries';
SHOW GLOBAL STATUS LIKE 'Questions';

-- 表锁监控
SHOW GLOBAL STATUS LIKE 'Table_locks_waited';
SHOW GLOBAL STATUS LIKE 'Table_locks_immediate';

-- InnoDB状态监控
SHOW ENGINE INNODB STATUS;

-- 缓冲池命中率
SELECT
    (1 - (Innodb_buffer_pool_reads / Innodb_buffer_pool_read_requests)) * 100 AS buffer_pool_hit_rate
FROM (
    SELECT
        VARIABLE_VALUE AS Innodb_buffer_pool_reads
    FROM information_schema.GLOBAL_STATUS
    WHERE VARIABLE_NAME = 'Innodb_buffer_pool_reads'
) AS reads,
(
    SELECT
        VARIABLE_VALUE AS Innodb_buffer_pool_read_requests
    FROM information_schema.GLOBAL_STATUS
    WHERE VARIABLE_NAME = 'Innodb_buffer_pool_read_requests'
) AS requests;
```

#### 空间使用监控
```sql
-- 数据库大小监控
SELECT
    table_schema AS 'Database',
    ROUND(SUM(data_length + index_length) / 1024 / 1024, 2) AS 'Size (MB)'
FROM information_schema.tables
WHERE table_schema = 'flexes_platform'
GROUP BY table_schema;

-- 表大小监控
SELECT
    table_name AS 'Table',
    table_rows AS 'Rows',
    ROUND(((data_length + index_length) / 1024 / 1024), 2) AS 'Size (MB)',
    ROUND((data_length / 1024 / 1024), 2) AS 'Data (MB)',
    ROUND((index_length / 1024 / 1024), 2) AS 'Index (MB)'
FROM information_schema.tables
WHERE table_schema = 'flexes_platform'
ORDER BY (data_length + index_length) DESC;
```

### 2. 自动化监控脚本

#### 健康检查脚本
```bash
#!/bin/bash
# 数据库健康检查脚本

DB_HOST="localhost"
DB_USER="monitor_user"
DB_PASS="monitor_password"
DB_NAME="flexes_platform"

# 检查数据库连接
mysql -h $DB_HOST -u $DB_USER -p$DB_PASS -e "SELECT 1;" > /dev/null 2>&1
if [ $? -ne 0 ]; then
    echo "ERROR: 无法连接到数据库"
    exit 1
fi

# 检查关键表是否存在
TABLES=("users" "jobs" "job_applications" "candidates" "employers")
for table in "${TABLES[@]}"; do
    COUNT=$(mysql -h $DB_HOST -u $DB_USER -p$DB_PASS -D $DB_NAME -e "SELECT COUNT(*) FROM $table;" 2>/dev/null | tail -1)
    if [ -z "$COUNT" ]; then
        echo "ERROR: 表 $table 不存在或无法访问"
        exit 1
    fi
    echo "INFO: 表 $table 有 $COUNT 条记录"
done

# 检查慢查询数量
SLOW_QUERIES=$(mysql -h $DB_HOST -u $DB_USER -p$DB_PASS -e "SHOW GLOBAL STATUS LIKE 'Slow_queries';" | tail -1 | awk '{print $2}')
echo "INFO: 慢查询数量: $SLOW_QUERIES"

# 检查连接数
CONNECTIONS=$(mysql -h $DB_HOST -u $DB_USER -p$DB_PASS -e "SHOW GLOBAL STATUS LIKE 'Threads_connected';" | tail -1 | awk '{print $2}')
echo "INFO: 当前连接数: $CONNECTIONS"

echo "INFO: 数据库健康检查完成"
```

## 📋 开发规范检查清单

### 数据库设计检查
- [ ] 表名使用复数形式，下划线分隔
- [ ] 字段名具有描述性，使用下划线分隔
- [ ] 主键使用BIGINT AUTO_INCREMENT
- [ ] 外键约束正确设置
- [ ] 索引设计合理，覆盖常用查询
- [ ] 字段类型选择合适，避免浪费空间
- [ ] 必要字段设置NOT NULL约束
- [ ] 添加适当的注释说明

### 性能优化检查
- [ ] 避免使用SELECT *
- [ ] 查询条件使用索引字段
- [ ] 大数据集使用分页查询
- [ ] 批量操作使用事务
- [ ] 避免在WHERE子句中使用函数
- [ ] 复杂查询进行执行计划分析
- [ ] 定期清理过期数据
- [ ] 监控慢查询日志

### 安全性检查
- [ ] 敏感数据加密存储
- [ ] 使用参数化查询防止SQL注入
- [ ] 数据库用户权限最小化
- [ ] 定期备份数据库
- [ ] 备份文件安全存储
- [ ] 访问日志记录完整
- [ ] 定期更新数据库版本

## 📚 参考资源

- [MySQL 8.0 官方文档](https://dev.mysql.com/doc/refman/8.0/en/)
- [InnoDB 存储引擎指南](https://dev.mysql.com/doc/refman/8.0/en/innodb-storage-engine.html)
- [MySQL 性能优化指南](https://dev.mysql.com/doc/refman/8.0/en/optimization.html)
- [Spring Data JPA 官方文档](https://spring.io/projects/spring-data-jpa)
- [HikariCP 连接池文档](https://github.com/brettwooldridge/HikariCP)
- [数据库设计最佳实践](https://www.mysql.com/why-mysql/benchmarks/)

---

**版本**: 1.0.0
**最后更新**: 2025-01-18
**维护者**: Flexes 数据库团队
```
