-- =============================================
-- Migration: Fix enum column types from TINYINT to INT
-- Version: V1.2
-- Date: 2025-09-18
-- Description: Fix enum column types to match JPA @Enumerated(EnumType.ORDINAL) expectations
-- =============================================

-- =============================================
-- 1. Fix candidates table enum columns
-- =============================================

-- Fix education_level column type (TINYINT -> INT)
ALTER TABLE candidates 
MODIFY COLUMN education_level INT COMMENT '教育水平: 1-高中, 2-专科, 3-本科, 4-硕士, 5-博士';

-- Fix job_status column type (TINYINT -> INT)
ALTER TABLE candidates 
MODIFY COLUMN job_status INT DEFAULT 1 COMMENT '求职状态: 0-不找工作, 1-看机会, 2-急找工作';

-- Add updated_at column if it doesn't exist
ALTER TABLE candidates 
ADD COLUMN IF NOT EXISTS updated_at DATETIME(6) NULL COMMENT '更新时间';

-- Update existing rows with current timestamp for candidates
UPDATE candidates 
SET updated_at = CASE 
    WHEN created_at IS NOT NULL AND created_at != '0000-00-00 00:00:00' 
    THEN created_at 
    ELSE CURRENT_TIMESTAMP(6) 
END
WHERE updated_at IS NULL;

-- Make updated_at NOT NULL
ALTER TABLE candidates 
MODIFY COLUMN updated_at DATETIME(6) NOT NULL COMMENT '更新时间';

-- =============================================
-- 2. Fix jobs table enum columns
-- =============================================

-- Fix employment_type column type (TINYINT -> INT)
ALTER TABLE jobs 
MODIFY COLUMN employment_type INT NOT NULL COMMENT '工作类型: 1-全职, 2-兼职, 3-合同工, 4-实习';

-- Fix experience_level column type (TINYINT -> INT)
ALTER TABLE jobs 
MODIFY COLUMN experience_level INT COMMENT '经验要求: 1-应届生, 2-初级, 3-中级, 4-高级, 5-专家';

-- Fix education_level column type (TINYINT -> INT)
ALTER TABLE jobs 
MODIFY COLUMN education_level INT COMMENT '学历要求: 1-不限, 2-高中, 3-专科, 4-本科, 5-硕士, 6-博士';

-- Fix remote_type column type (TINYINT -> INT)
ALTER TABLE jobs 
MODIFY COLUMN remote_type INT DEFAULT 1 COMMENT '远程类型: 1-完全远程, 2-混合办公, 3-现场办公';

-- Fix status column type (TINYINT -> INT)
ALTER TABLE jobs 
MODIFY COLUMN status INT DEFAULT 0 COMMENT '职位状态: 0-待审核, 1-招聘中, 2-暂停, 3-已关闭';

-- Add updated_at column if it doesn't exist
ALTER TABLE jobs 
ADD COLUMN IF NOT EXISTS updated_at DATETIME(6) NULL COMMENT '更新时间';

-- Update existing rows with current timestamp for jobs
UPDATE jobs 
SET updated_at = CASE 
    WHEN created_at IS NOT NULL AND created_at != '0000-00-00 00:00:00' 
    THEN created_at 
    ELSE CURRENT_TIMESTAMP(6) 
END
WHERE updated_at IS NULL;

-- Make updated_at NOT NULL
ALTER TABLE jobs 
MODIFY COLUMN updated_at DATETIME(6) NOT NULL COMMENT '更新时间';

-- =============================================
-- 3. Fix employers table enum columns
-- =============================================

-- Fix company_size column type (TINYINT -> INT)
ALTER TABLE employers 
MODIFY COLUMN company_size INT COMMENT '公司规模: 1-1-10人, 2-11-50人, 3-51-200人, 4-201-1000人, 5-1000+人';

-- Add updated_at column if it doesn't exist
ALTER TABLE employers 
ADD COLUMN IF NOT EXISTS updated_at DATETIME(6) NULL COMMENT '更新时间';

-- Update existing rows with current timestamp for employers
UPDATE employers 
SET updated_at = CASE 
    WHEN created_at IS NOT NULL AND created_at != '0000-00-00 00:00:00' 
    THEN created_at 
    ELSE CURRENT_TIMESTAMP(6) 
END
WHERE updated_at IS NULL;

-- Make updated_at NOT NULL
ALTER TABLE employers 
MODIFY COLUMN updated_at DATETIME(6) NOT NULL COMMENT '更新时间';

-- =============================================
-- 4. Fix job_applications table enum columns
-- =============================================

-- Fix status column type (TINYINT -> INT)
ALTER TABLE job_applications 
MODIFY COLUMN status INT DEFAULT 1 COMMENT '申请状态: 1-已提交, 2-已查看, 3-面试中, 4-已拒绝, 5-已录用';

-- Add updated_at column if it doesn't exist
ALTER TABLE job_applications 
ADD COLUMN IF NOT EXISTS updated_at DATETIME(6) NULL COMMENT '更新时间';

-- Update existing rows with current timestamp for job_applications
UPDATE job_applications 
SET updated_at = CASE 
    WHEN applied_at IS NOT NULL AND applied_at != '0000-00-00 00:00:00' 
    THEN applied_at 
    ELSE CURRENT_TIMESTAMP(6) 
END
WHERE updated_at IS NULL;

-- Make updated_at NOT NULL
ALTER TABLE job_applications 
MODIFY COLUMN updated_at DATETIME(6) NOT NULL COMMENT '更新时间';

-- =============================================
-- 5. Fix job_categories table enum columns
-- =============================================

-- Fix status column type (TINYINT -> INT)
ALTER TABLE job_categories 
MODIFY COLUMN status INT DEFAULT 1 COMMENT '状态: 0-禁用, 1-启用';
