-- 创建初始数据库表结构

-- 用户表
CREATE TABLE users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role TINYINT NOT NULL DEFAULT 1 COMMENT '0=管理员, 1=求职者, 2=雇主',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '0=禁用, 1=启用',
    email_verified BOOLEAN DEFAULT FALSE,
    last_login_at DATETIME,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_email (email),
    INDEX idx_role (role),
    INDEX idx_status (status),
    INDEX idx_created_at (created_at)
);

-- 职位分类表
CREATE TABLE job_categories (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    slug VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    sort_order INT DEFAULT 0,
    status TINYINT DEFAULT 1 COMMENT '0=禁用, 1=启用',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_slug (slug),
    INDEX idx_status (status),
    INDEX idx_sort_order (sort_order)
);

-- 雇主表
CREATE TABLE employers (
    employer_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    company_name VARCHAR(200) NOT NULL,
    company_logo VARCHAR(500),
    industry VARCHAR(100),
    company_size TINYINT COMMENT '1=1-10人, 2=11-50人, 3=51-200人, 4=201-1000人, 5=1000+人',
    founded_year YEAR,
    website VARCHAR(300),
    location VARCHAR(200),
    description TEXT,
    contact_person VARCHAR(100),
    contact_phone VARCHAR(20),
    verified BOOLEAN DEFAULT FALSE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_company_name (company_name),
    INDEX idx_industry (industry),
    INDEX idx_verified (verified)
);

-- 求职者表
CREATE TABLE candidates (
    candidate_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    phone VARCHAR(20),
    location VARCHAR(200),
    bio TEXT,
    skills JSON,
    experience_level TINYINT COMMENT '1=应届生, 2=初级, 3=中级, 4=高级, 5=专家',
    education_level TINYINT COMMENT '1=不限, 2=高中, 3=专科, 4=本科, 5=硕士, 6=博士',
    expected_salary_min INT,
    expected_salary_max INT,
    salary_currency VARCHAR(10) DEFAULT 'USD',
    remote_preference TINYINT DEFAULT 1 COMMENT '1=完全远程, 2=混合办公, 3=现场办公',
    availability_status TINYINT DEFAULT 1 COMMENT '1=立即可用, 2=2周内, 3=1个月内, 4=不急于换工作',
    resume_url VARCHAR(500),
    portfolio_url VARCHAR(500),
    linkedin_url VARCHAR(500),
    github_url VARCHAR(500),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_location (location),
    INDEX idx_experience_level (experience_level),
    INDEX idx_availability_status (availability_status)
);

-- 职位表
CREATE TABLE jobs (
    job_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    employer_id BIGINT NOT NULL,
    category_id INT,
    title VARCHAR(200) NOT NULL,
    description TEXT NOT NULL,
    requirements TEXT,
    responsibilities TEXT,
    skills_required JSON,
    skills_preferred JSON,
    employment_type TINYINT NOT NULL COMMENT '1=全职, 2=兼职, 3=合同工, 4=实习',
    experience_level TINYINT COMMENT '1=应届生, 2=初级, 3=中级, 4=高级, 5=专家',
    education_level TINYINT COMMENT '1=不限, 2=高中, 3=专科, 4=本科, 5=硕士, 6=博士',
    salary_min INT,
    salary_max INT,
    salary_currency VARCHAR(10) DEFAULT 'USD',
    location VARCHAR(200),
    remote_type TINYINT DEFAULT 1 COMMENT '1=完全远程, 2=混合办公, 3=现场办公',
    benefits TEXT,
    application_deadline DATE,
    status TINYINT DEFAULT 0 COMMENT '0=待审核, 1=招聘中, 2=暂停, 3=已关闭',
    view_count INT DEFAULT 0,
    application_count INT DEFAULT 0,
    featured BOOLEAN DEFAULT FALSE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (employer_id) REFERENCES employers(employer_id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES job_categories(category_id) ON DELETE SET NULL,
    INDEX idx_employer_id (employer_id),
    INDEX idx_category_id (category_id),
    INDEX idx_status (status),
    INDEX idx_created_at (created_at),
    INDEX idx_location (location),
    INDEX idx_employment_type (employment_type),
    INDEX idx_experience_level (experience_level),
    INDEX idx_featured (featured)
);

-- 职位申请表
CREATE TABLE job_applications (
    application_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    job_id BIGINT NOT NULL,
    candidate_id BIGINT NOT NULL,
    cover_letter TEXT,
    resume_url VARCHAR(500),
    status TINYINT DEFAULT 0 COMMENT '0=已提交, 1=已查看, 2=面试中, 3=已录用, 4=已拒绝',
    applied_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (job_id) REFERENCES jobs(job_id) ON DELETE CASCADE,
    FOREIGN KEY (candidate_id) REFERENCES candidates(candidate_id) ON DELETE CASCADE,
    UNIQUE KEY unique_application (job_id, candidate_id),
    INDEX idx_job_id (job_id),
    INDEX idx_candidate_id (candidate_id),
    INDEX idx_status (status),
    INDEX idx_applied_at (applied_at)
);

-- 每日申请限制表
CREATE TABLE daily_application_limits (
    limit_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    candidate_id BIGINT NOT NULL,
    application_date DATE NOT NULL,
    application_count INT DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (candidate_id) REFERENCES candidates(candidate_id) ON DELETE CASCADE,
    UNIQUE KEY unique_daily_limit (candidate_id, application_date),
    INDEX idx_candidate_id (candidate_id),
    INDEX idx_application_date (application_date)
);

-- 用户评价表
CREATE TABLE user_reviews (
    review_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    user_role VARCHAR(20) NOT NULL,
    user_title VARCHAR(100),
    rating INT NOT NULL CHECK (rating >= 1 AND rating <= 5),
    content TEXT NOT NULL,
    helpful_count INT DEFAULT 0,
    status TINYINT DEFAULT 0 COMMENT '0=待审核, 1=已通过, 2=已拒绝',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_rating (rating),
    INDEX idx_status (status),
    INDEX idx_created_at (created_at)
);

-- 博客文章表
CREATE TABLE blog_articles (
    article_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    slug VARCHAR(200) NOT NULL UNIQUE,
    summary VARCHAR(500) NOT NULL,
    content LONGTEXT NOT NULL,
    cover_image VARCHAR(500),
    author_id BIGINT NOT NULL,
    author_name VARCHAR(100),
    author_avatar VARCHAR(500),
    category VARCHAR(50),
    tags JSON,
    read_count INT DEFAULT 0,
    like_count INT DEFAULT 0,
    comment_count INT DEFAULT 0,
    read_time INT,
    featured BOOLEAN DEFAULT FALSE,
    status TINYINT DEFAULT 0 COMMENT '0=草稿, 1=已发布, 2=已归档',
    published_at DATETIME,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (author_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_slug (slug),
    INDEX idx_author_id (author_id),
    INDEX idx_category (category),
    INDEX idx_status (status),
    INDEX idx_published_at (published_at),
    INDEX idx_featured (featured)
);
