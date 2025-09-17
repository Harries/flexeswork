-- =============================================
-- Flexes 远程工程招聘平台数据库设计
-- 版本: v1.0
-- 创建日期: 2025-09-17
-- 数据库: MySQL 8.0+
-- =============================================

-- 创建数据库
CREATE DATABASE IF NOT EXISTS flexes_platform 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

USE flexes_platform;

-- =============================================
-- 1. 用户相关表
-- =============================================

-- 用户主表
CREATE TABLE users (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    email VARCHAR(255) NOT NULL UNIQUE COMMENT '邮箱地址',
    password VARCHAR(255) NOT NULL COMMENT '密码哈希',
    role TINYINT NOT NULL DEFAULT 1 COMMENT '用户角色: 0-管理员, 1-求职者, 2-雇主',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '账户状态: 0-禁用, 1-正常, 2-待验证',
    email_verified BOOLEAN DEFAULT FALSE COMMENT '邮箱是否已验证',
    last_login_at TIMESTAMP NULL COMMENT '最后登录时间',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    
    INDEX idx_email (email),
    INDEX idx_role (role),
    INDEX idx_status (status),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB COMMENT='用户主表';

-- 求职者信息表
CREATE TABLE candidates (
    candidate_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '求职者ID',
    user_id BIGINT NOT NULL COMMENT '关联用户ID',
    name VARCHAR(100) NOT NULL COMMENT '姓名',
    phone VARCHAR(20) COMMENT '手机号',
    avatar_url VARCHAR(500) COMMENT '头像URL',
    location VARCHAR(200) COMMENT '所在地',
    current_position VARCHAR(200) COMMENT '当前职位',
    experience_years TINYINT DEFAULT 0 COMMENT '工作经验年数',
    education_level TINYINT COMMENT '教育水平: 1-高中, 2-专科, 3-本科, 4-硕士, 5-博士',
    expected_salary_min INT COMMENT '期望薪资最低值',
    expected_salary_max INT COMMENT '期望薪资最高值',
    job_status TINYINT DEFAULT 1 COMMENT '求职状态: 0-不找工作, 1-看机会, 2-急找工作',
    resume_url VARCHAR(500) COMMENT '简历文件URL',
    bio TEXT COMMENT '个人简介',
    skills JSON COMMENT '技能标签JSON',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_location (location),
    INDEX idx_experience (experience_years),
    INDEX idx_job_status (job_status)
) ENGINE=InnoDB COMMENT='求职者信息表';

-- 雇主信息表
CREATE TABLE employers (
    employer_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '雇主ID',
    user_id BIGINT NOT NULL COMMENT '关联用户ID',
    company_name VARCHAR(200) NOT NULL COMMENT '公司名称',
    company_logo VARCHAR(500) COMMENT '公司Logo URL',
    industry VARCHAR(100) COMMENT '所属行业',
    company_size TINYINT COMMENT '公司规模: 1-1-10人, 2-11-50人, 3-51-200人, 4-201-1000人, 5-1000+人',
    founded_year YEAR COMMENT '成立年份',
    website VARCHAR(300) COMMENT '公司官网',
    location VARCHAR(200) COMMENT '公司地址',
    description TEXT COMMENT '公司简介',
    contact_person VARCHAR(100) COMMENT '联系人姓名',
    contact_phone VARCHAR(20) COMMENT '联系电话',
    verified BOOLEAN DEFAULT FALSE COMMENT '是否已认证',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_company_name (company_name),
    INDEX idx_industry (industry),
    INDEX idx_verified (verified)
) ENGINE=InnoDB COMMENT='雇主信息表';

-- =============================================
-- 2. 职位相关表
-- =============================================

-- 职位分类表
CREATE TABLE job_categories (
    category_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '分类ID',
    name VARCHAR(100) NOT NULL COMMENT '分类名称',
    slug VARCHAR(100) NOT NULL UNIQUE COMMENT '分类标识',
    description TEXT COMMENT '分类描述',
    sort_order INT DEFAULT 0 COMMENT '排序权重',
    status TINYINT DEFAULT 1 COMMENT '状态: 0-禁用, 1-启用',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    
    INDEX idx_slug (slug),
    INDEX idx_status (status),
    INDEX idx_sort_order (sort_order)
) ENGINE=InnoDB COMMENT='职位分类表';

-- 职位表
CREATE TABLE jobs (
    job_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '职位ID',
    employer_id BIGINT NOT NULL COMMENT '发布雇主ID',
    category_id INT COMMENT '职位分类ID',
    title VARCHAR(200) NOT NULL COMMENT '职位标题',
    description TEXT NOT NULL COMMENT '职位描述',
    requirements TEXT COMMENT '职位要求',
    responsibilities TEXT COMMENT '工作职责',
    skills_required JSON COMMENT '必需技能JSON',
    skills_preferred JSON COMMENT '优先技能JSON',
    employment_type TINYINT NOT NULL COMMENT '工作类型: 1-全职, 2-兼职, 3-合同工, 4-实习',
    experience_level TINYINT COMMENT '经验要求: 1-应届生, 2-初级, 3-中级, 4-高级, 5-专家',
    education_level TINYINT COMMENT '学历要求: 1-不限, 2-高中, 3-专科, 4-本科, 5-硕士, 6-博士',
    salary_min INT COMMENT '薪资最低值',
    salary_max INT COMMENT '薪资最高值',
    salary_currency VARCHAR(10) DEFAULT 'USD' COMMENT '薪资币种',
    location VARCHAR(200) COMMENT '工作地点',
    remote_type TINYINT DEFAULT 1 COMMENT '远程类型: 1-完全远程, 2-混合办公, 3-现场办公',
    benefits TEXT COMMENT '福利待遇',
    application_deadline DATE COMMENT '申请截止日期',
    status TINYINT DEFAULT 0 COMMENT '职位状态: 0-待审核, 1-招聘中, 2-暂停, 3-已关闭',
    view_count INT DEFAULT 0 COMMENT '浏览次数',
    application_count INT DEFAULT 0 COMMENT '申请次数',
    featured BOOLEAN DEFAULT FALSE COMMENT '是否精选职位',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    
    FOREIGN KEY (employer_id) REFERENCES employers(employer_id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES job_categories(category_id) ON DELETE SET NULL,
    INDEX idx_employer_id (employer_id),
    INDEX idx_category_id (category_id),
    INDEX idx_status (status),
    INDEX idx_created_at (created_at),
    INDEX idx_location (location),
    INDEX idx_employment_type (employment_type),
    INDEX idx_experience_level (experience_level),
    INDEX idx_featured (featured),
    FULLTEXT idx_title_description (title, description)
) ENGINE=InnoDB COMMENT='职位表';

-- =============================================
-- 3. 申请相关表
-- =============================================

-- 职位申请表
CREATE TABLE job_applications (
    application_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '申请ID',
    job_id BIGINT NOT NULL COMMENT '职位ID',
    candidate_id BIGINT NOT NULL COMMENT '求职者ID',
    cover_letter TEXT COMMENT '求职信',
    resume_url VARCHAR(500) COMMENT '简历URL',
    status TINYINT DEFAULT 1 COMMENT '申请状态: 1-已提交, 2-已查看, 3-面试中, 4-已拒绝, 5-已录用',
    employer_notes TEXT COMMENT '雇主备注',
    applied_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    
    FOREIGN KEY (job_id) REFERENCES jobs(job_id) ON DELETE CASCADE,
    FOREIGN KEY (candidate_id) REFERENCES candidates(candidate_id) ON DELETE CASCADE,
    UNIQUE KEY uk_job_candidate (job_id, candidate_id) COMMENT '防止重复申请',
    INDEX idx_job_id (job_id),
    INDEX idx_candidate_id (candidate_id),
    INDEX idx_status (status),
    INDEX idx_applied_at (applied_at)
) ENGINE=InnoDB COMMENT='职位申请表';

-- 每日申请限制记录表
CREATE TABLE daily_application_limits (
    limit_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '记录ID',
    candidate_id BIGINT NOT NULL COMMENT '求职者ID',
    application_date DATE NOT NULL COMMENT '申请日期',
    application_count INT DEFAULT 0 COMMENT '当日申请次数',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    
    FOREIGN KEY (candidate_id) REFERENCES candidates(candidate_id) ON DELETE CASCADE,
    UNIQUE KEY uk_candidate_date (candidate_id, application_date),
    INDEX idx_application_date (application_date)
) ENGINE=InnoDB COMMENT='每日申请限制记录表';

-- =============================================
-- 4. 消息通知相关表
-- =============================================

-- 消息表
CREATE TABLE messages (
    message_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '消息ID',
    sender_id BIGINT NOT NULL COMMENT '发送者用户ID',
    receiver_id BIGINT NOT NULL COMMENT '接收者用户ID',
    message_type TINYINT DEFAULT 1 COMMENT '消息类型: 1-普通消息, 2-面试邀请, 3-工作邀请, 4-系统通知',
    subject VARCHAR(200) COMMENT '消息主题',
    content TEXT NOT NULL COMMENT '消息内容',
    read_status BOOLEAN DEFAULT FALSE COMMENT '是否已读',
    read_at TIMESTAMP NULL COMMENT '阅读时间',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',

    FOREIGN KEY (sender_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (receiver_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_sender_id (sender_id),
    INDEX idx_receiver_id (receiver_id),
    INDEX idx_message_type (message_type),
    INDEX idx_read_status (read_status),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB COMMENT='消息表';

-- 系统通知表
CREATE TABLE notifications (
    notification_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '通知ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    type VARCHAR(50) NOT NULL COMMENT '通知类型',
    title VARCHAR(200) NOT NULL COMMENT '通知标题',
    content TEXT COMMENT '通知内容',
    data JSON COMMENT '相关数据JSON',
    read_status BOOLEAN DEFAULT FALSE COMMENT '是否已读',
    read_at TIMESTAMP NULL COMMENT '阅读时间',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',

    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_type (type),
    INDEX idx_read_status (read_status),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB COMMENT='系统通知表';

-- =============================================
-- 5. 内容管理相关表
-- =============================================

-- 博客文章表
CREATE TABLE articles (
    article_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '文章ID',
    author_id BIGINT NOT NULL COMMENT '作者用户ID',
    title VARCHAR(300) NOT NULL COMMENT '文章标题',
    slug VARCHAR(300) NOT NULL UNIQUE COMMENT '文章标识',
    content LONGTEXT NOT NULL COMMENT '文章内容',
    excerpt TEXT COMMENT '文章摘要',
    featured_image VARCHAR(500) COMMENT '特色图片URL',
    status TINYINT DEFAULT 0 COMMENT '文章状态: 0-草稿, 1-已发布, 2-已下线',
    view_count INT DEFAULT 0 COMMENT '浏览次数',
    published_at TIMESTAMP NULL COMMENT '发布时间',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

    FOREIGN KEY (author_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_author_id (author_id),
    INDEX idx_slug (slug),
    INDEX idx_status (status),
    INDEX idx_published_at (published_at),
    FULLTEXT idx_title_content (title, content)
) ENGINE=InnoDB COMMENT='博客文章表';

-- 标签表
CREATE TABLE tags (
    tag_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '标签ID',
    name VARCHAR(100) NOT NULL UNIQUE COMMENT '标签名称',
    slug VARCHAR(100) NOT NULL UNIQUE COMMENT '标签标识',
    type TINYINT DEFAULT 1 COMMENT '标签类型: 1-技能标签, 2-行业标签, 3-文章标签',
    usage_count INT DEFAULT 0 COMMENT '使用次数',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',

    INDEX idx_name (name),
    INDEX idx_slug (slug),
    INDEX idx_type (type),
    INDEX idx_usage_count (usage_count)
) ENGINE=InnoDB COMMENT='标签表';

-- 文章标签关联表
CREATE TABLE article_tags (
    article_id BIGINT NOT NULL COMMENT '文章ID',
    tag_id INT NOT NULL COMMENT '标签ID',

    PRIMARY KEY (article_id, tag_id),
    FOREIGN KEY (article_id) REFERENCES articles(article_id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tags(tag_id) ON DELETE CASCADE
) ENGINE=InnoDB COMMENT='文章标签关联表';

-- =============================================
-- 6. 用户行为相关表
-- =============================================

-- 用户收藏表
CREATE TABLE user_favorites (
    favorite_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '收藏ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    target_type TINYINT NOT NULL COMMENT '收藏类型: 1-职位, 2-候选人, 3-公司',
    target_id BIGINT NOT NULL COMMENT '目标ID',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',

    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    UNIQUE KEY uk_user_target (user_id, target_type, target_id),
    INDEX idx_user_id (user_id),
    INDEX idx_target (target_type, target_id)
) ENGINE=InnoDB COMMENT='用户收藏表';

-- 职位提醒表
CREATE TABLE job_alerts (
    alert_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '提醒ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    name VARCHAR(200) NOT NULL COMMENT '提醒名称',
    keywords VARCHAR(500) COMMENT '关键词',
    location VARCHAR(200) COMMENT '地点',
    category_id INT COMMENT '职位分类',
    employment_type TINYINT COMMENT '工作类型',
    experience_level TINYINT COMMENT '经验要求',
    salary_min INT COMMENT '最低薪资',
    frequency TINYINT DEFAULT 1 COMMENT '提醒频率: 1-实时, 2-每日, 3-每周',
    status TINYINT DEFAULT 1 COMMENT '状态: 0-暂停, 1-启用',
    last_sent_at TIMESTAMP NULL COMMENT '最后发送时间',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES job_categories(category_id) ON DELETE SET NULL,
    INDEX idx_user_id (user_id),
    INDEX idx_status (status),
    INDEX idx_frequency (frequency)
) ENGINE=InnoDB COMMENT='职位提醒表';

-- 用户评价表
CREATE TABLE reviews (
    review_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '评价ID',
    reviewer_id BIGINT NOT NULL COMMENT '评价者用户ID',
    target_type TINYINT NOT NULL COMMENT '评价类型: 1-平台评价, 2-公司评价',
    target_id BIGINT COMMENT '目标ID (公司ID等)',
    rating TINYINT NOT NULL COMMENT '评分 (1-5星)',
    title VARCHAR(200) COMMENT '评价标题',
    content TEXT COMMENT '评价内容',
    status TINYINT DEFAULT 0 COMMENT '审核状态: 0-待审核, 1-已通过, 2-已拒绝',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

    FOREIGN KEY (reviewer_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_reviewer_id (reviewer_id),
    INDEX idx_target (target_type, target_id),
    INDEX idx_rating (rating),
    INDEX idx_status (status)
) ENGINE=InnoDB COMMENT='用户评价表';

-- =============================================
-- 7. 系统配置相关表
-- =============================================

-- 系统配置表
CREATE TABLE system_configs (
    config_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '配置ID',
    config_key VARCHAR(100) NOT NULL UNIQUE COMMENT '配置键',
    config_value TEXT COMMENT '配置值',
    description VARCHAR(500) COMMENT '配置描述',
    type VARCHAR(50) DEFAULT 'string' COMMENT '数据类型',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

    INDEX idx_config_key (config_key)
) ENGINE=InnoDB COMMENT='系统配置表';

-- 操作日志表
CREATE TABLE operation_logs (
    log_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '日志ID',
    user_id BIGINT COMMENT '操作用户ID',
    action VARCHAR(100) NOT NULL COMMENT '操作动作',
    resource_type VARCHAR(50) COMMENT '资源类型',
    resource_id BIGINT COMMENT '资源ID',
    ip_address VARCHAR(45) COMMENT 'IP地址',
    user_agent TEXT COMMENT '用户代理',
    details JSON COMMENT '操作详情JSON',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',

    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE SET NULL,
    INDEX idx_user_id (user_id),
    INDEX idx_action (action),
    INDEX idx_resource (resource_type, resource_id),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB COMMENT='操作日志表';

-- =============================================
-- 8. 初始化数据
-- =============================================

-- 插入默认职位分类
INSERT INTO job_categories (name, slug, description, sort_order) VALUES
('前端开发', 'frontend', '前端开发相关职位', 1),
('后端开发', 'backend', '后端开发相关职位', 2),
('全栈开发', 'fullstack', '全栈开发相关职位', 3),
('移动开发', 'mobile', '移动应用开发相关职位', 4),
('DevOps', 'devops', 'DevOps和运维相关职位', 5),
('数据科学', 'data-science', '数据科学和分析相关职位', 6),
('产品经理', 'product', '产品管理相关职位', 7),
('UI/UX设计', 'design', 'UI/UX设计相关职位', 8),
('测试工程师', 'testing', '软件测试相关职位', 9),
('项目管理', 'project-management', '项目管理相关职位', 10);

-- 插入默认技能标签
INSERT INTO tags (name, slug, type, usage_count) VALUES
-- 编程语言
('JavaScript', 'javascript', 1, 0),
('Python', 'python', 1, 0),
('Java', 'java', 1, 0),
('TypeScript', 'typescript', 1, 0),
('Go', 'go', 1, 0),
('PHP', 'php', 1, 0),
('C++', 'cpp', 1, 0),
('C#', 'csharp', 1, 0),
('Ruby', 'ruby', 1, 0),
('Swift', 'swift', 1, 0),
-- 前端框架
('React', 'react', 1, 0),
('Vue.js', 'vuejs', 1, 0),
('Angular', 'angular', 1, 0),
('Next.js', 'nextjs', 1, 0),
('Nuxt.js', 'nuxtjs', 1, 0),
-- 后端框架
('Node.js', 'nodejs', 1, 0),
('Express', 'express', 1, 0),
('Django', 'django', 1, 0),
('Flask', 'flask', 1, 0),
('Spring Boot', 'spring-boot', 1, 0),
('Laravel', 'laravel', 1, 0),
-- 数据库
('MySQL', 'mysql', 1, 0),
('PostgreSQL', 'postgresql', 1, 0),
('MongoDB', 'mongodb', 1, 0),
('Redis', 'redis', 1, 0),
-- 云服务
('AWS', 'aws', 1, 0),
('Azure', 'azure', 1, 0),
('Google Cloud', 'google-cloud', 1, 0),
('Docker', 'docker', 1, 0),
('Kubernetes', 'kubernetes', 1, 0);

-- 插入系统配置
INSERT INTO system_configs (config_key, config_value, description, type) VALUES
('daily_application_limit', '20', '每日职位申请限制数量', 'integer'),
('job_approval_required', 'true', '职位发布是否需要审核', 'boolean'),
('email_verification_required', 'true', '注册是否需要邮箱验证', 'boolean'),
('platform_name', 'Flexes', '平台名称', 'string'),
('support_email', 'support@flexes.work', '客服邮箱', 'string'),
('max_resume_size', '5242880', '简历文件最大大小(字节)', 'integer'),
('allowed_resume_types', 'pdf,doc,docx', '允许的简历文件类型', 'string');

-- 创建管理员账户 (密码需要在应用层加密)
INSERT INTO users (email, password, role, status, email_verified) VALUES
('admin@flexes.work', '$2b$10$placeholder_hash', 0, 1, true);

-- =============================================
-- 9. 创建视图
-- =============================================

-- 职位详情视图
CREATE VIEW job_details_view AS
SELECT
    j.job_id,
    j.title,
    j.description,
    j.requirements,
    j.salary_min,
    j.salary_max,
    j.location,
    j.remote_type,
    j.employment_type,
    j.experience_level,
    j.status,
    j.view_count,
    j.application_count,
    j.created_at,
    e.company_name,
    e.company_logo,
    e.industry,
    e.location as company_location,
    c.name as category_name,
    u.email as employer_email
FROM jobs j
LEFT JOIN employers e ON j.employer_id = e.employer_id
LEFT JOIN job_categories c ON j.category_id = c.category_id
LEFT JOIN users u ON e.user_id = u.user_id;

-- 候选人详情视图
CREATE VIEW candidate_details_view AS
SELECT
    c.candidate_id,
    c.name,
    c.phone,
    c.avatar_url,
    c.location,
    c.current_position,
    c.experience_years,
    c.education_level,
    c.expected_salary_min,
    c.expected_salary_max,
    c.job_status,
    c.bio,
    c.skills,
    u.email,
    u.last_login_at,
    u.created_at as registered_at
FROM candidates c
LEFT JOIN users u ON c.user_id = u.user_id
WHERE u.status = 1;

-- =============================================
-- 10. 创建存储过程
-- =============================================

DELIMITER //

-- 检查每日申请限制的存储过程
CREATE PROCEDURE CheckDailyApplicationLimit(
    IN p_candidate_id BIGINT,
    IN p_application_date DATE,
    OUT p_current_count INT,
    OUT p_can_apply BOOLEAN
)
BEGIN
    DECLARE v_limit INT DEFAULT 20;

    -- 获取配置的限制数量
    SELECT CAST(config_value AS UNSIGNED) INTO v_limit
    FROM system_configs
    WHERE config_key = 'daily_application_limit';

    -- 获取当日申请次数
    SELECT COALESCE(application_count, 0) INTO p_current_count
    FROM daily_application_limits
    WHERE candidate_id = p_candidate_id AND application_date = p_application_date;

    -- 判断是否可以申请
    SET p_can_apply = (p_current_count < v_limit);
END //

-- 更新每日申请计数的存储过程
CREATE PROCEDURE UpdateDailyApplicationCount(
    IN p_candidate_id BIGINT,
    IN p_application_date DATE
)
BEGIN
    INSERT INTO daily_application_limits (candidate_id, application_date, application_count)
    VALUES (p_candidate_id, p_application_date, 1)
    ON DUPLICATE KEY UPDATE
        application_count = application_count + 1,
        updated_at = CURRENT_TIMESTAMP;
END //

DELIMITER ;

-- =============================================
-- 11. 创建触发器
-- =============================================

DELIMITER //

-- 职位申请后更新申请计数触发器
CREATE TRIGGER after_job_application_insert
AFTER INSERT ON job_applications
FOR EACH ROW
BEGIN
    -- 更新职位申请计数
    UPDATE jobs SET application_count = application_count + 1
    WHERE job_id = NEW.job_id;

    -- 更新每日申请限制计数
    CALL UpdateDailyApplicationCount(NEW.candidate_id, DATE(NEW.applied_at));
END //

-- 用户注册后自动创建对应角色信息触发器
CREATE TRIGGER after_user_insert
AFTER INSERT ON users
FOR EACH ROW
BEGIN
    IF NEW.role = 1 THEN
        -- 创建求职者信息记录
        INSERT INTO candidates (user_id, name)
        VALUES (NEW.user_id, SUBSTRING_INDEX(NEW.email, '@', 1));
    ELSEIF NEW.role = 2 THEN
        -- 创建雇主信息记录
        INSERT INTO employers (user_id, company_name)
        VALUES (NEW.user_id, CONCAT('Company_', NEW.user_id));
    END IF;
END //

DELIMITER ;

-- =============================================
-- 结束
-- =============================================
