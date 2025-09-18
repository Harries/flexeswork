-- =============================================
-- Migration: Fix users table schema mismatches
-- Version: V1.3
-- Date: 2025-09-18
-- Description: Fix users table enum column types and ensure updated_at column exists
-- =============================================

-- =============================================
-- 1. Fix users table enum columns
-- =============================================

-- Fix role column type (TINYINT -> INT)
ALTER TABLE users 
MODIFY COLUMN role INT NOT NULL DEFAULT 1 COMMENT '用户角色: 0-管理员, 1-求职者, 2-雇主';

-- Fix status column type (TINYINT -> INT)
ALTER TABLE users 
MODIFY COLUMN status INT NOT NULL DEFAULT 1 COMMENT '账户状态: 0-禁用, 1-正常, 2-待验证';

-- Check if updated_at column exists, if not add it
-- Note: The users table should already have updated_at from the original schema,
-- but we'll ensure it's the correct type
ALTER TABLE users 
MODIFY COLUMN updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间';

-- Ensure created_at is also the correct type
ALTER TABLE users 
MODIFY COLUMN created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间';
