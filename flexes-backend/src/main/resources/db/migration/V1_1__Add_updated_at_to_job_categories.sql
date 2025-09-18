-- =============================================
-- Migration: Fix job_categories table schema
-- Version: V1.1
-- Date: 2025-09-18
-- Description: Add missing updated_at column to job_categories table
-- =============================================

-- =============================================
-- 1. Fix job_categories table - Add updated_at column
-- =============================================

-- Step 1: Add updated_at column as nullable first
ALTER TABLE job_categories
ADD COLUMN updated_at DATETIME(6) NULL COMMENT '更新时间';

-- Step 2: Update existing rows with current timestamp
-- Use created_at if it exists and is valid, otherwise use current timestamp
UPDATE job_categories
SET updated_at = CASE
    WHEN created_at IS NOT NULL AND created_at != '0000-00-00 00:00:00'
    THEN created_at
    ELSE CURRENT_TIMESTAMP(6)
END
WHERE updated_at IS NULL;

-- Step 3: Now make the column NOT NULL
ALTER TABLE job_categories
MODIFY COLUMN updated_at DATETIME(6) NOT NULL COMMENT '更新时间';

-- Step 4: Add index for better query performance
CREATE INDEX idx_job_categories_updated_at ON job_categories (updated_at);
