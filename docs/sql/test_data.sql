-- =============================================
-- Flexes 平台测试数据
-- 版本: v1.0
-- 创建日期: 2025-09-17
-- 用途: 开发和测试环境数据
-- =============================================

USE flexes_platform;

-- =============================================
-- 1. 测试用户数据
-- =============================================

-- 插入测试求职者用户 (密码: password123)
INSERT INTO users (email, password, role, status, email_verified, last_login_at) VALUES
('john.doe@example.com', '$2b$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 1, 1, true, NOW()),
('jane.smith@example.com', '$2b$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 1, 1, true, NOW()),
('mike.johnson@example.com', '$2b$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 1, 1, true, NOW()),
('sarah.wilson@example.com', '$2b$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 1, 1, true, NOW()),
('david.brown@example.com', '$2b$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 1, 1, true, NOW());

-- 插入测试雇主用户
INSERT INTO users (email, password, role, status, email_verified, last_login_at) VALUES
('hr@techcorp.com', '$2b$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 2, 1, true, NOW()),
('recruiter@innovate.io', '$2b$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 2, 1, true, NOW()),
('jobs@startup.dev', '$2b$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 2, 1, true, NOW());

-- =============================================
-- 2. 求职者详细信息
-- =============================================

-- 更新求职者信息 (触发器会自动创建基础记录)
UPDATE candidates SET 
    name = 'John Doe',
    phone = '+1-555-0101',
    location = 'San Francisco, CA',
    current_position = 'Senior Frontend Developer',
    experience_years = 5,
    education_level = 4,
    expected_salary_min = 80000,
    expected_salary_max = 120000,
    job_status = 2,
    bio = 'Experienced frontend developer with expertise in React, TypeScript, and modern web technologies. Passionate about creating user-friendly interfaces and optimizing performance.',
    skills = JSON_ARRAY('JavaScript', 'React', 'TypeScript', 'Node.js', 'CSS', 'HTML', 'Git')
WHERE user_id = (SELECT user_id FROM users WHERE email = 'john.doe@example.com');

UPDATE candidates SET 
    name = 'Jane Smith',
    phone = '+1-555-0102',
    location = 'New York, NY',
    current_position = 'Full Stack Developer',
    experience_years = 3,
    education_level = 4,
    expected_salary_min = 70000,
    expected_salary_max = 100000,
    job_status = 1,
    bio = 'Full stack developer with strong background in Python and JavaScript. Experience with both frontend and backend development.',
    skills = JSON_ARRAY('Python', 'Django', 'JavaScript', 'Vue.js', 'PostgreSQL', 'Docker')
WHERE user_id = (SELECT user_id FROM users WHERE email = 'jane.smith@example.com');

UPDATE candidates SET 
    name = 'Mike Johnson',
    phone = '+1-555-0103',
    location = 'Austin, TX',
    current_position = 'DevOps Engineer',
    experience_years = 7,
    education_level = 4,
    expected_salary_min = 90000,
    expected_salary_max = 130000,
    job_status = 1,
    bio = 'DevOps engineer with extensive experience in cloud infrastructure, CI/CD, and containerization.',
    skills = JSON_ARRAY('AWS', 'Docker', 'Kubernetes', 'Jenkins', 'Python', 'Terraform')
WHERE user_id = (SELECT user_id FROM users WHERE email = 'mike.johnson@example.com');

UPDATE candidates SET 
    name = 'Sarah Wilson',
    phone = '+1-555-0104',
    location = 'Seattle, WA',
    current_position = 'Data Scientist',
    experience_years = 4,
    education_level = 5,
    expected_salary_min = 85000,
    expected_salary_max = 125000,
    job_status = 2,
    bio = 'Data scientist with PhD in Statistics. Specialized in machine learning, data analysis, and predictive modeling.',
    skills = JSON_ARRAY('Python', 'R', 'SQL', 'TensorFlow', 'Pandas', 'Scikit-learn', 'Jupyter')
WHERE user_id = (SELECT user_id FROM users WHERE email = 'sarah.wilson@example.com');

UPDATE candidates SET 
    name = 'David Brown',
    phone = '+1-555-0105',
    location = 'Boston, MA',
    current_position = 'Backend Developer',
    experience_years = 6,
    education_level = 4,
    expected_salary_min = 75000,
    expected_salary_max = 110000,
    job_status = 1,
    bio = 'Backend developer with strong experience in Java and Spring framework. Focus on scalable system design.',
    skills = JSON_ARRAY('Java', 'Spring Boot', 'MySQL', 'Redis', 'Microservices', 'REST API')
WHERE user_id = (SELECT user_id FROM users WHERE email = 'david.brown@example.com');

-- =============================================
-- 3. 雇主公司信息
-- =============================================

-- 更新雇主信息
UPDATE employers SET 
    company_name = 'TechCorp Solutions',
    industry = 'Software Development',
    company_size = 4,
    founded_year = 2015,
    website = 'https://techcorp.com',
    location = 'San Francisco, CA',
    description = 'Leading software development company specializing in enterprise solutions and cloud technologies.',
    contact_person = 'Alice Johnson',
    contact_phone = '+1-555-1001',
    verified = true
WHERE user_id = (SELECT user_id FROM users WHERE email = 'hr@techcorp.com');

UPDATE employers SET 
    company_name = 'Innovate.io',
    industry = 'Technology Startup',
    company_size = 2,
    founded_year = 2020,
    website = 'https://innovate.io',
    location = 'Austin, TX',
    description = 'Fast-growing startup focused on AI and machine learning solutions for businesses.',
    contact_person = 'Bob Chen',
    contact_phone = '+1-555-1002',
    verified = true
WHERE user_id = (SELECT user_id FROM users WHERE email = 'recruiter@innovate.io');

UPDATE employers SET 
    company_name = 'Startup Dev',
    industry = 'Web Development',
    company_size = 1,
    founded_year = 2022,
    website = 'https://startup.dev',
    location = 'Remote',
    description = 'Remote-first development agency creating modern web applications for clients worldwide.',
    contact_person = 'Carol Martinez',
    contact_phone = '+1-555-1003',
    verified = false
WHERE user_id = (SELECT user_id FROM users WHERE email = 'jobs@startup.dev');

-- =============================================
-- 4. 测试职位数据
-- =============================================

-- 插入测试职位
INSERT INTO jobs (employer_id, category_id, title, description, requirements, responsibilities, skills_required, skills_preferred, employment_type, experience_level, education_level, salary_min, salary_max, location, remote_type, benefits, status, featured) VALUES

-- TechCorp 职位
((SELECT employer_id FROM employers WHERE company_name = 'TechCorp Solutions'), 1, 'Senior React Developer', 
'We are looking for a Senior React Developer to join our frontend team and help build next-generation web applications.',
'5+ years of React experience, Strong JavaScript/TypeScript skills, Experience with modern frontend tools',
'Develop and maintain React applications, Collaborate with design and backend teams, Code review and mentoring',
JSON_ARRAY('React', 'JavaScript', 'TypeScript', 'CSS', 'HTML'),
JSON_ARRAY('Next.js', 'GraphQL', 'Jest', 'Webpack'),
1, 4, 4, 100000, 140000, 'San Francisco, CA', 2, 'Health insurance, 401k, Flexible PTO, Stock options', 1, true),

-- Innovate.io 职位
((SELECT employer_id FROM employers WHERE company_name = 'Innovate.io'), 6, 'Machine Learning Engineer',
'Join our AI team to develop cutting-edge machine learning models and deploy them at scale.',
'3+ years ML experience, Python expertise, Experience with TensorFlow/PyTorch',
'Design and implement ML models, Optimize model performance, Deploy models to production',
JSON_ARRAY('Python', 'TensorFlow', 'Machine Learning', 'Statistics'),
JSON_ARRAY('PyTorch', 'Kubernetes', 'MLOps', 'AWS'),
1, 3, 4, 90000, 130000, 'Austin, TX', 1, 'Equity, Health insurance, Learning budget', 1, false),

-- Startup Dev 职位
((SELECT employer_id FROM employers WHERE company_name = 'Startup Dev'), 3, 'Full Stack Developer',
'Remote full stack developer position for building modern web applications using latest technologies.',
'2+ years full stack experience, Node.js and React knowledge, Database experience',
'Build full stack applications, API development, Database design, Client communication',
JSON_ARRAY('JavaScript', 'Node.js', 'React', 'MongoDB'),
JSON_ARRAY('TypeScript', 'GraphQL', 'Docker', 'AWS'),
1, 2, 3, 60000, 85000, 'Remote', 1, 'Flexible hours, Remote work, Professional development', 1, false);

-- =============================================
-- 5. 测试申请数据
-- =============================================

-- 插入职位申请
INSERT INTO job_applications (job_id, candidate_id, cover_letter, status, applied_at) VALUES
(1, (SELECT candidate_id FROM candidates WHERE name = 'John Doe'), 
'I am very interested in the Senior React Developer position. My 5 years of experience with React and TypeScript make me a perfect fit for this role.', 
2, DATE_SUB(NOW(), INTERVAL 2 DAY)),

(2, (SELECT candidate_id FROM candidates WHERE name = 'Sarah Wilson'), 
'As a data scientist with machine learning expertise, I would love to contribute to your AI initiatives.', 
1, DATE_SUB(NOW(), INTERVAL 1 DAY)),

(3, (SELECT candidate_id FROM candidates WHERE name = 'Jane Smith'), 
'I am excited about the full stack developer opportunity and believe my Python and JavaScript skills align well with your requirements.', 
3, DATE_SUB(NOW(), INTERVAL 3 DAY));

-- =============================================
-- 6. 测试消息数据
-- =============================================

-- 插入测试消息
INSERT INTO messages (sender_id, receiver_id, message_type, subject, content, read_status) VALUES
((SELECT user_id FROM users WHERE email = 'hr@techcorp.com'),
 (SELECT user_id FROM users WHERE email = 'john.doe@example.com'),
 2, 'Interview Invitation - Senior React Developer',
 'Hi John, We were impressed with your application and would like to invite you for an interview. Please let us know your availability for next week.',
 false),

((SELECT user_id FROM users WHERE email = 'john.doe@example.com'),
 (SELECT user_id FROM users WHERE email = 'hr@techcorp.com'),
 1, 'Re: Interview Invitation',
 'Thank you for the invitation! I am available Monday through Wednesday next week. Looking forward to hearing from you.',
 true);

-- =============================================
-- 7. 测试通知数据
-- =============================================

-- 插入系统通知
INSERT INTO notifications (user_id, type, title, content, data, read_status) VALUES
((SELECT user_id FROM users WHERE email = 'john.doe@example.com'),
 'application_status', 'Application Status Update',
 'Your application for Senior React Developer at TechCorp Solutions has been viewed by the employer.',
 JSON_OBJECT('job_id', 1, 'status', 'viewed'),
 false),

((SELECT user_id FROM users WHERE email = 'sarah.wilson@example.com'),
 'job_match', 'New Job Match',
 'We found a new job that matches your profile: Data Scientist at AI Corp.',
 JSON_OBJECT('job_id', 2, 'match_score', 85),
 false);

-- =============================================
-- 8. 测试博客文章
-- =============================================

-- 插入测试文章
INSERT INTO articles (author_id, title, slug, content, excerpt, status, view_count, published_at) VALUES
((SELECT user_id FROM users WHERE email = 'admin@flexes.work'),
 'Top 10 Remote Work Tips for Developers',
 'top-10-remote-work-tips-developers',
 'Working remotely as a developer comes with unique challenges and opportunities. Here are our top 10 tips to help you succeed in a remote development role...',
 'Essential tips for developers working remotely, covering productivity, communication, and work-life balance.',
 1, 150, NOW()),

((SELECT user_id FROM users WHERE email = 'admin@flexes.work'),
 'The Future of Remote Engineering Jobs',
 'future-remote-engineering-jobs',
 'The landscape of remote engineering work is rapidly evolving. In this article, we explore the trends shaping the future of remote work in tech...',
 'An analysis of trends and predictions for the future of remote engineering positions.',
 1, 89, DATE_SUB(NOW(), INTERVAL 1 WEEK));

-- =============================================
-- 9. 测试收藏和提醒
-- =============================================

-- 插入用户收藏
INSERT INTO user_favorites (user_id, target_type, target_id) VALUES
((SELECT user_id FROM users WHERE email = 'john.doe@example.com'), 1, 2),
((SELECT user_id FROM users WHERE email = 'jane.smith@example.com'), 1, 1),
((SELECT user_id FROM users WHERE email = 'sarah.wilson@example.com'), 2, (SELECT candidate_id FROM candidates WHERE name = 'John Doe'));

-- 插入职位提醒
INSERT INTO job_alerts (user_id, name, keywords, location, category_id, employment_type, experience_level, salary_min, frequency, status) VALUES
((SELECT user_id FROM users WHERE email = 'john.doe@example.com'),
 'React Developer Jobs',
 'React, Frontend, JavaScript',
 'San Francisco, CA',
 1, 1, 4, 90000, 1, 1),

((SELECT user_id FROM users WHERE email = 'sarah.wilson@example.com'),
 'Data Science Opportunities',
 'Machine Learning, Python, Data Science',
 'Remote',
 6, 1, 3, 80000, 2, 1);

-- =============================================
-- 结束测试数据插入
-- =============================================
