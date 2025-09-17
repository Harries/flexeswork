-- =============================================
-- Flexes 平台数据库索引优化
-- 版本: v1.0
-- 创建日期: 2025-09-17
-- 用途: 性能优化索引
-- =============================================

USE flexes_platform;

-- =============================================
-- 1. 复合索引优化
-- =============================================

-- 职位搜索优化索引
CREATE INDEX idx_jobs_search ON jobs (status, category_id, location, employment_type, experience_level, created_at);

-- 职位申请查询优化索引
CREATE INDEX idx_applications_candidate_status ON job_applications (candidate_id, status, applied_at);
CREATE INDEX idx_applications_job_status ON job_applications (job_id, status, applied_at);

-- 消息查询优化索引
CREATE INDEX idx_messages_receiver_read ON messages (receiver_id, read_status, created_at);
CREATE INDEX idx_notifications_user_read ON notifications (user_id, read_status, created_at);

-- 用户活跃度查询索引
CREATE INDEX idx_users_role_status_login ON users (role, status, last_login_at);

-- 候选人搜索优化索引
CREATE INDEX idx_candidates_search ON candidates (job_status, location, experience_years, education_level);

-- 雇主验证状态索引
CREATE INDEX idx_employers_verified_industry ON employers (verified, industry, created_at);

-- =============================================
-- 2. 分区表设计 (可选)
-- =============================================

-- 对于大数据量的日志表，可以考虑按月分区
-- ALTER TABLE operation_logs 
-- PARTITION BY RANGE (YEAR(created_at) * 100 + MONTH(created_at)) (
--     PARTITION p202501 VALUES LESS THAN (202502),
--     PARTITION p202502 VALUES LESS THAN (202503),
--     PARTITION p202503 VALUES LESS THAN (202504),
--     PARTITION p202504 VALUES LESS THAN (202505),
--     PARTITION p202505 VALUES LESS THAN (202506),
--     PARTITION p202506 VALUES LESS THAN (202507),
--     PARTITION p202507 VALUES LESS THAN (202508),
--     PARTITION p202508 VALUES LESS THAN (202509),
--     PARTITION p202509 VALUES LESS THAN (202510),
--     PARTITION p202510 VALUES LESS THAN (202511),
--     PARTITION p202511 VALUES LESS THAN (202512),
--     PARTITION p202512 VALUES LESS THAN (202513),
--     PARTITION p_future VALUES LESS THAN MAXVALUE
-- );

-- =============================================
-- 3. 性能监控查询
-- =============================================

-- 查看表大小和行数
SELECT 
    TABLE_NAME,
    TABLE_ROWS,
    ROUND(((DATA_LENGTH + INDEX_LENGTH) / 1024 / 1024), 2) AS 'Size (MB)'
FROM information_schema.TABLES 
WHERE TABLE_SCHEMA = 'flexes_platform'
ORDER BY (DATA_LENGTH + INDEX_LENGTH) DESC;

-- 查看索引使用情况
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

-- =============================================
-- 4. 数据清理脚本
-- =============================================

-- 清理过期的每日申请限制记录 (保留30天)
-- DELETE FROM daily_application_limits 
-- WHERE application_date < DATE_SUB(CURDATE(), INTERVAL 30 DAY);

-- 清理过期的操作日志 (保留90天)
-- DELETE FROM operation_logs 
-- WHERE created_at < DATE_SUB(NOW(), INTERVAL 90 DAY);

-- 清理已读的旧通知 (保留30天)
-- DELETE FROM notifications 
-- WHERE read_status = 1 AND read_at < DATE_SUB(NOW(), INTERVAL 30 DAY);

-- =============================================
-- 5. 备份脚本示例
-- =============================================

-- 创建备份用户 (在生产环境中执行)
-- CREATE USER 'backup_user'@'localhost' IDENTIFIED BY 'secure_backup_password';
-- GRANT SELECT, LOCK TABLES ON flexes_platform.* TO 'backup_user'@'localhost';
-- FLUSH PRIVILEGES;

-- 备份命令示例 (在shell中执行)
-- mysqldump -u backup_user -p --single-transaction --routines --triggers flexes_platform > flexes_backup_$(date +%Y%m%d_%H%M%S).sql

-- =============================================
-- 6. 性能优化建议
-- =============================================

-- 设置MySQL配置优化建议:
-- innodb_buffer_pool_size = 70% of RAM
-- innodb_log_file_size = 256M
-- innodb_flush_log_at_trx_commit = 2
-- query_cache_size = 128M
-- max_connections = 200
-- slow_query_log = ON
-- long_query_time = 2
