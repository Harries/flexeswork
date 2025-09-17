-- =============================================
-- Flexes 平台测试数据
-- 版本: v2.0
-- 创建日期: 2025-09-17
-- 用途: 开发和测试环境数据
-- 说明: 包含完整的关联数据，确保表间关系正确
-- =============================================

USE flexes_platform;

-- 清理现有测试数据（按依赖关系倒序删除）
SET FOREIGN_KEY_CHECKS = 0;
DELETE FROM operation_logs WHERE user_id IN (SELECT user_id FROM users WHERE email LIKE '%@example.com' OR email LIKE '%@techcorp.com' OR email LIKE '%@innovate.io' OR email LIKE '%@startup.dev' OR email = 'admin@flexes.work');
DELETE FROM reviews WHERE reviewer_id IN (SELECT user_id FROM users WHERE email LIKE '%@example.com' OR email LIKE '%@techcorp.com' OR email LIKE '%@innovate.io' OR email LIKE '%@startup.dev' OR email = 'admin@flexes.work');
DELETE FROM job_alerts WHERE user_id IN (SELECT user_id FROM users WHERE email LIKE '%@example.com' OR email LIKE '%@techcorp.com' OR email LIKE '%@innovate.io' OR email LIKE '%@startup.dev' OR email = 'admin@flexes.work');
DELETE FROM user_favorites WHERE user_id IN (SELECT user_id FROM users WHERE email LIKE '%@example.com' OR email LIKE '%@techcorp.com' OR email LIKE '%@innovate.io' OR email LIKE '%@startup.dev' OR email = 'admin@flexes.work');
DELETE FROM article_tags WHERE article_id IN (SELECT article_id FROM articles WHERE author_id IN (SELECT user_id FROM users WHERE email LIKE '%@example.com' OR email LIKE '%@techcorp.com' OR email LIKE '%@innovate.io' OR email LIKE '%@startup.dev' OR email = 'admin@flexes.work'));
DELETE FROM articles WHERE author_id IN (SELECT user_id FROM users WHERE email LIKE '%@example.com' OR email LIKE '%@techcorp.com' OR email LIKE '%@innovate.io' OR email LIKE '%@startup.dev' OR email = 'admin@flexes.work');
DELETE FROM notifications WHERE user_id IN (SELECT user_id FROM users WHERE email LIKE '%@example.com' OR email LIKE '%@techcorp.com' OR email LIKE '%@innovate.io' OR email LIKE '%@startup.dev' OR email = 'admin@flexes.work');
DELETE FROM messages WHERE sender_id IN (SELECT user_id FROM users WHERE email LIKE '%@example.com' OR email LIKE '%@techcorp.com' OR email LIKE '%@innovate.io' OR email LIKE '%@startup.dev' OR email = 'admin@flexes.work') OR receiver_id IN (SELECT user_id FROM users WHERE email LIKE '%@example.com' OR email LIKE '%@techcorp.com' OR email LIKE '%@innovate.io' OR email LIKE '%@startup.dev' OR email = 'admin@flexes.work');
DELETE FROM daily_application_limits WHERE candidate_id IN (SELECT candidate_id FROM candidates WHERE user_id IN (SELECT user_id FROM users WHERE email LIKE '%@example.com'));
DELETE FROM job_applications WHERE candidate_id IN (SELECT candidate_id FROM candidates WHERE user_id IN (SELECT user_id FROM users WHERE email LIKE '%@example.com'));
DELETE FROM jobs WHERE employer_id IN (SELECT employer_id FROM employers WHERE user_id IN (SELECT user_id FROM users WHERE email LIKE '%@techcorp.com' OR email LIKE '%@innovate.io' OR email LIKE '%@startup.dev'));
DELETE FROM candidates WHERE user_id IN (SELECT user_id FROM users WHERE email LIKE '%@example.com');
DELETE FROM employers WHERE user_id IN (SELECT user_id FROM users WHERE email LIKE '%@techcorp.com' OR email LIKE '%@innovate.io' OR email LIKE '%@startup.dev');
DELETE FROM users WHERE email LIKE '%@example.com' OR email LIKE '%@techcorp.com' OR email LIKE '%@innovate.io' OR email LIKE '%@startup.dev' OR email = 'admin@flexes.work';
SET FOREIGN_KEY_CHECKS = 1;

-- =============================================
-- 1. 测试用户数据
-- =============================================

-- 插入管理员用户
INSERT INTO users (email, password, role, status, email_verified, last_login_at) VALUES
('admin@flexes.work', '$2b$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 0, 1, true, NOW());

-- 插入测试求职者用户 (密码: password123)
INSERT INTO users (email, password, role, status, email_verified, last_login_at) VALUES
('john.doe@example.com', '$2b$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 1, 1, true, DATE_SUB(NOW(), INTERVAL 2 HOUR)),
('jane.smith@example.com', '$2b$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 1, 1, true, DATE_SUB(NOW(), INTERVAL 1 DAY)),
('mike.johnson@example.com', '$2b$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 1, 1, true, DATE_SUB(NOW(), INTERVAL 3 HOUR)),
('sarah.wilson@example.com', '$2b$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 1, 1, true, DATE_SUB(NOW(), INTERVAL 5 HOUR)),
('david.brown@example.com', '$2b$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 1, 1, true, DATE_SUB(NOW(), INTERVAL 1 DAY)),
('lisa.garcia@example.com', '$2b$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 1, 1, true, DATE_SUB(NOW(), INTERVAL 6 HOUR)),
('tom.anderson@example.com', '$2b$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 1, 1, false, NULL),
('emma.taylor@example.com', '$2b$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 1, 2, false, NULL);

-- 插入测试雇主用户
INSERT INTO users (email, password, role, status, email_verified, last_login_at) VALUES
('hr@techcorp.com', '$2b$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 2, 1, true, DATE_SUB(NOW(), INTERVAL 1 HOUR)),
('recruiter@innovate.io', '$2b$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 2, 1, true, DATE_SUB(NOW(), INTERVAL 4 HOUR)),
('jobs@startup.dev', '$2b$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 2, 1, true, DATE_SUB(NOW(), INTERVAL 2 DAY)),
('hiring@bigtech.com', '$2b$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 2, 1, true, DATE_SUB(NOW(), INTERVAL 8 HOUR)),
('talent@fintech.co', '$2b$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 2, 1, false, NULL);

-- =============================================
-- 2. 求职者详细信息
-- =============================================

-- 插入求职者信息
INSERT INTO candidates (user_id, name, phone, avatar_url, location, current_position, experience_years, education_level, expected_salary_min, expected_salary_max, job_status, resume_url, bio, skills) VALUES
((SELECT user_id FROM users WHERE email = 'john.doe@example.com'), 'John Doe', '+1-555-0101', 'https://example.com/avatars/john.jpg', 'San Francisco, CA', 'Senior Frontend Developer', 5, 4, 80000, 120000, 2, 'https://example.com/resumes/john_doe.pdf', 'Experienced frontend developer with expertise in React, TypeScript, and modern web technologies. Passionate about creating user-friendly interfaces and optimizing performance.', JSON_ARRAY('JavaScript', 'React', 'TypeScript', 'Node.js', 'CSS', 'HTML', 'Git', 'Redux', 'Webpack')),

((SELECT user_id FROM users WHERE email = 'jane.smith@example.com'), 'Jane Smith', '+1-555-0102', 'https://example.com/avatars/jane.jpg', 'New York, NY', 'Full Stack Developer', 3, 4, 70000, 100000, 1, 'https://example.com/resumes/jane_smith.pdf', 'Full stack developer with strong background in Python and JavaScript. Experience with both frontend and backend development, API design, and database optimization.', JSON_ARRAY('Python', 'Django', 'JavaScript', 'Vue.js', 'PostgreSQL', 'Docker', 'REST API', 'GraphQL')),

((SELECT user_id FROM users WHERE email = 'mike.johnson@example.com'), 'Mike Johnson', '+1-555-0103', 'https://example.com/avatars/mike.jpg', 'Austin, TX', 'DevOps Engineer', 7, 4, 90000, 130000, 1, 'https://example.com/resumes/mike_johnson.pdf', 'DevOps engineer with extensive experience in cloud infrastructure, CI/CD, and containerization. Expert in AWS services and infrastructure automation.', JSON_ARRAY('AWS', 'Docker', 'Kubernetes', 'Jenkins', 'Python', 'Terraform', 'Ansible', 'Linux', 'Monitoring')),

((SELECT user_id FROM users WHERE email = 'sarah.wilson@example.com'), 'Sarah Wilson', '+1-555-0104', 'https://example.com/avatars/sarah.jpg', 'Seattle, WA', 'Data Scientist', 4, 5, 85000, 125000, 2, 'https://example.com/resumes/sarah_wilson.pdf', 'Data scientist with PhD in Statistics. Specialized in machine learning, data analysis, and predictive modeling. Published researcher with experience in both academia and industry.', JSON_ARRAY('Python', 'R', 'SQL', 'TensorFlow', 'Pandas', 'Scikit-learn', 'Jupyter', 'Statistics', 'Machine Learning', 'Deep Learning')),

((SELECT user_id FROM users WHERE email = 'david.brown@example.com'), 'David Brown', '+1-555-0105', 'https://example.com/avatars/david.jpg', 'Boston, MA', 'Backend Developer', 6, 4, 75000, 110000, 1, 'https://example.com/resumes/david_brown.pdf', 'Backend developer with strong experience in Java and Spring framework. Focus on scalable system design, microservices architecture, and performance optimization.', JSON_ARRAY('Java', 'Spring Boot', 'MySQL', 'Redis', 'Microservices', 'REST API', 'JPA', 'Maven', 'JUnit')),

((SELECT user_id FROM users WHERE email = 'lisa.garcia@example.com'), 'Lisa Garcia', '+1-555-0106', 'https://example.com/avatars/lisa.jpg', 'Los Angeles, CA', 'UX/UI Designer', 4, 4, 65000, 95000, 1, 'https://example.com/resumes/lisa_garcia.pdf', 'Creative UX/UI designer with a passion for user-centered design. Experience in web and mobile app design, user research, and prototyping.', JSON_ARRAY('Figma', 'Sketch', 'Adobe XD', 'Photoshop', 'Illustrator', 'User Research', 'Prototyping', 'Wireframing')),

((SELECT user_id FROM users WHERE email = 'tom.anderson@example.com'), 'Tom Anderson', '+1-555-0107', NULL, 'Chicago, IL', 'Junior Developer', 1, 4, 50000, 70000, 2, 'https://example.com/resumes/tom_anderson.pdf', 'Recent computer science graduate eager to start career in software development. Strong foundation in programming fundamentals and modern web technologies.', JSON_ARRAY('JavaScript', 'Python', 'HTML', 'CSS', 'React', 'Git', 'SQL')),

((SELECT user_id FROM users WHERE email = 'emma.taylor@example.com'), 'Emma Taylor', '+1-555-0108', NULL, 'Denver, CO', 'Student', 0, 3, 40000, 60000, 0, NULL, 'Computer science student looking for internship opportunities. Passionate about web development and eager to learn from experienced professionals.', JSON_ARRAY('JavaScript', 'HTML', 'CSS', 'Python', 'Git'));

-- =============================================
-- 3. 雇主公司信息
-- =============================================

-- 插入雇主信息
INSERT INTO employers (user_id, company_name, company_logo, industry, company_size, founded_year, website, location, description, contact_person, contact_phone, verified) VALUES
((SELECT user_id FROM users WHERE email = 'hr@techcorp.com'), 'TechCorp Solutions', 'https://example.com/logos/techcorp.png', 'Software Development', 4, 2015, 'https://techcorp.com', 'San Francisco, CA', 'Leading software development company specializing in enterprise solutions and cloud technologies. We serve Fortune 500 companies with cutting-edge software solutions.', 'Alice Johnson', '+1-555-1001', true),

((SELECT user_id FROM users WHERE email = 'recruiter@innovate.io'), 'Innovate.io', 'https://example.com/logos/innovate.png', 'Artificial Intelligence', 2, 2020, 'https://innovate.io', 'Austin, TX', 'Fast-growing startup focused on AI and machine learning solutions for businesses. We are building the next generation of intelligent automation tools.', 'Bob Chen', '+1-555-1002', true),

((SELECT user_id FROM users WHERE email = 'jobs@startup.dev'), 'Startup Dev', 'https://example.com/logos/startup.png', 'Web Development', 1, 2022, 'https://startup.dev', 'Remote', 'Remote-first development agency creating modern web applications for clients worldwide. We specialize in React, Node.js, and cloud-native solutions.', 'Carol Martinez', '+1-555-1003', false),

((SELECT user_id FROM users WHERE email = 'hiring@bigtech.com'), 'BigTech Corp', 'https://example.com/logos/bigtech.png', 'Technology', 5, 2010, 'https://bigtech.com', 'Seattle, WA', 'Global technology company providing cloud services, enterprise software, and consumer products. We are at the forefront of digital transformation.', 'David Kim', '+1-555-1004', true),

((SELECT user_id FROM users WHERE email = 'talent@fintech.co'), 'FinTech Solutions', 'https://example.com/logos/fintech.png', 'Financial Technology', 3, 2018, 'https://fintech.co', 'New York, NY', 'Innovative fintech company revolutionizing digital payments and financial services. We combine cutting-edge technology with deep financial expertise.', 'Maria Rodriguez', '+1-555-1005', false);

-- =============================================
-- 4. 测试职位数据
-- =============================================

-- 插入测试职位
INSERT INTO jobs (employer_id, category_id, title, description, requirements, responsibilities, skills_required, skills_preferred, employment_type, experience_level, education_level, salary_min, salary_max, salary_currency, location, remote_type, benefits, application_deadline, status, view_count, application_count, featured, created_at) VALUES

-- TechCorp 职位
((SELECT employer_id FROM employers WHERE company_name = 'TechCorp Solutions'), 1, 'Senior React Developer',
'We are looking for a Senior React Developer to join our frontend team and help build next-generation web applications. You will work on cutting-edge projects using the latest React ecosystem technologies.',
'• 5+ years of React experience\n• Strong JavaScript/TypeScript skills\n• Experience with modern frontend tools\n• Knowledge of state management (Redux/Context)\n• Experience with testing frameworks',
'• Develop and maintain React applications\n• Collaborate with design and backend teams\n• Code review and mentoring junior developers\n• Optimize application performance\n• Participate in architecture decisions',
JSON_ARRAY('React', 'JavaScript', 'TypeScript', 'CSS', 'HTML'),
JSON_ARRAY('Next.js', 'GraphQL', 'Jest', 'Webpack', 'Redux'),
1, 4, 4, 100000, 140000, 'USD', 'San Francisco, CA', 2, 'Health insurance, 401k, Flexible PTO, Stock options, $3000 learning budget', DATE_ADD(NOW(), INTERVAL 30 DAY), 1, 45, 8, true, DATE_SUB(NOW(), INTERVAL 5 DAY)),

-- Innovate.io 职位
((SELECT employer_id FROM employers WHERE company_name = 'Innovate.io'), 6, 'Machine Learning Engineer',
'Join our AI team to develop cutting-edge machine learning models and deploy them at scale. Work on exciting projects in computer vision, NLP, and predictive analytics.',
'• 3+ years ML experience\n• Python expertise\n• Experience with TensorFlow/PyTorch\n• Strong statistics background\n• Experience with cloud platforms',
'• Design and implement ML models\n• Optimize model performance\n• Deploy models to production\n• Collaborate with data scientists\n• Maintain ML infrastructure',
JSON_ARRAY('Python', 'TensorFlow', 'Machine Learning', 'Statistics'),
JSON_ARRAY('PyTorch', 'Kubernetes', 'MLOps', 'AWS', 'Docker'),
1, 3, 4, 90000, 130000, 'USD', 'Austin, TX', 1, 'Equity, Health insurance, Learning budget, Flexible hours', DATE_ADD(NOW(), INTERVAL 45 DAY), 1, 32, 12, false, DATE_SUB(NOW(), INTERVAL 3 DAY)),

-- Startup Dev 职位
((SELECT employer_id FROM employers WHERE company_name = 'Startup Dev'), 3, 'Full Stack Developer',
'Remote full stack developer position for building modern web applications using latest technologies. Perfect opportunity for someone who wants to work with cutting-edge tech stack.',
'• 2+ years full stack experience\n• Node.js and React knowledge\n• Database experience\n• API development skills\n• Remote work experience preferred',
'• Build full stack applications\n• API development and integration\n• Database design and optimization\n• Client communication\n• Code reviews and testing',
JSON_ARRAY('JavaScript', 'Node.js', 'React', 'MongoDB'),
JSON_ARRAY('TypeScript', 'GraphQL', 'Docker', 'AWS', 'Redis'),
1, 2, 3, 60000, 85000, 'USD', 'Remote', 1, 'Flexible hours, Remote work, Professional development, Health stipend', DATE_ADD(NOW(), INTERVAL 60 DAY), 1, 28, 15, false, DATE_SUB(NOW(), INTERVAL 7 DAY)),

-- BigTech 职位
((SELECT employer_id FROM employers WHERE company_name = 'BigTech Corp'), 2, 'Senior Backend Engineer',
'We are seeking a Senior Backend Engineer to join our platform team. You will be responsible for building scalable, high-performance backend systems that serve millions of users.',
'• 6+ years backend development experience\n• Strong knowledge of distributed systems\n• Experience with microservices architecture\n• Proficiency in Java or Python\n• Database optimization skills',
'• Design and implement scalable backend services\n• Optimize system performance and reliability\n• Mentor junior engineers\n• Participate in on-call rotation\n• Collaborate with cross-functional teams',
JSON_ARRAY('Java', 'Spring Boot', 'Microservices', 'PostgreSQL', 'Redis'),
JSON_ARRAY('Kubernetes', 'AWS', 'Kafka', 'Elasticsearch', 'Monitoring'),
1, 4, 4, 120000, 180000, 'USD', 'Seattle, WA', 2, 'Comprehensive health insurance, 401k matching, Stock options, Unlimited PTO', DATE_ADD(NOW(), INTERVAL 21 DAY), 1, 67, 23, true, DATE_SUB(NOW(), INTERVAL 2 DAY)),

-- FinTech 职位
((SELECT employer_id FROM employers WHERE company_name = 'FinTech Solutions'), 2, 'Python Developer',
'Join our fintech team to build secure, scalable financial applications. Work on payment processing, fraud detection, and financial analytics systems.',
'• 3+ years Python development experience\n• Experience with financial systems\n• Knowledge of security best practices\n• Database design skills\n• API development experience',
'• Develop financial applications\n• Implement security measures\n• Build and maintain APIs\n• Work with payment gateways\n• Ensure regulatory compliance',
JSON_ARRAY('Python', 'Django', 'PostgreSQL', 'REST API'),
JSON_ARRAY('FastAPI', 'Celery', 'Redis', 'Docker', 'AWS'),
1, 3, 4, 80000, 110000, 'USD', 'New York, NY', 3, 'Health insurance, 401k, Flexible PTO, Transit benefits', DATE_ADD(NOW(), INTERVAL 14 DAY), 0, 15, 5, false, DATE_SUB(NOW(), INTERVAL 1 DAY)),

-- 更多职位
((SELECT employer_id FROM employers WHERE company_name = 'TechCorp Solutions'), 8, 'UX/UI Designer',
'We are looking for a creative UX/UI Designer to join our design team. You will be responsible for creating intuitive and beautiful user experiences.',
'• 3+ years UX/UI design experience\n• Proficiency in Figma/Sketch\n• Strong portfolio\n• User research experience\n• Prototyping skills',
'• Create user-centered designs\n• Conduct user research\n• Build prototypes and wireframes\n• Collaborate with developers\n• Maintain design systems',
JSON_ARRAY('Figma', 'Sketch', 'User Research', 'Prototyping'),
JSON_ARRAY('Adobe Creative Suite', 'InVision', 'Principle', 'Zeplin'),
1, 3, 4, 75000, 105000, 'USD', 'San Francisco, CA', 2, 'Health insurance, 401k, Flexible PTO, Design conference budget', DATE_ADD(NOW(), INTERVAL 25 DAY), 1, 22, 7, false, DATE_SUB(NOW(), INTERVAL 4 DAY)),

((SELECT employer_id FROM employers WHERE company_name = 'Innovate.io'), 5, 'DevOps Engineer',
'Looking for a DevOps Engineer to help us scale our infrastructure and improve our deployment processes. Work with cutting-edge cloud technologies.',
'• 4+ years DevOps experience\n• Strong AWS knowledge\n• Container orchestration experience\n• CI/CD pipeline expertise\n• Infrastructure as Code experience',
'• Manage cloud infrastructure\n• Build and maintain CI/CD pipelines\n• Monitor system performance\n• Automate deployment processes\n• Ensure security compliance',
JSON_ARRAY('AWS', 'Docker', 'Kubernetes', 'Terraform'),
JSON_ARRAY('Jenkins', 'Ansible', 'Monitoring', 'Python', 'Linux'),
1, 4, 4, 95000, 135000, 'USD', 'Austin, TX', 1, 'Equity, Health insurance, Learning budget, Conference attendance', DATE_ADD(NOW(), INTERVAL 35 DAY), 1, 18, 4, false, DATE_SUB(NOW(), INTERVAL 6 DAY));

-- =============================================
-- 5. 测试申请数据
-- =============================================

-- 插入职位申请
INSERT INTO job_applications (job_id, candidate_id, cover_letter, resume_url, status, employer_notes, applied_at, updated_at) VALUES
-- John Doe 的申请
(1, (SELECT candidate_id FROM candidates WHERE name = 'John Doe'),
'Dear Hiring Manager,\n\nI am very interested in the Senior React Developer position at TechCorp Solutions. My 5 years of experience with React and TypeScript, combined with my passion for creating exceptional user experiences, make me a perfect fit for this role.\n\nI have successfully led frontend development for several large-scale applications and have experience mentoring junior developers. I am excited about the opportunity to contribute to your team.\n\nBest regards,\nJohn Doe',
'https://example.com/resumes/john_doe_updated.pdf', 2, 'Strong candidate, good experience with React. Scheduled for technical interview.', DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY)),

-- Sarah Wilson 的申请
(2, (SELECT candidate_id FROM candidates WHERE name = 'Sarah Wilson'),
'Dear Innovate.io Team,\n\nAs a data scientist with machine learning expertise and a PhD in Statistics, I would love to contribute to your AI initiatives. My experience with TensorFlow, PyTorch, and deploying ML models at scale aligns perfectly with your requirements.\n\nI am particularly excited about working on computer vision and NLP projects. I believe my research background and industry experience would be valuable to your team.\n\nLooking forward to hearing from you,\nSarah Wilson',
'https://example.com/resumes/sarah_wilson_ml.pdf', 1, NULL, DATE_SUB(NOW(), INTERVAL 1 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY)),

-- Jane Smith 的申请
(3, (SELECT candidate_id FROM candidates WHERE name = 'Jane Smith'),
'Hello Startup Dev Team,\n\nI am excited about the full stack developer opportunity and believe my Python and JavaScript skills align well with your requirements. My experience with Django, Vue.js, and API development would be valuable for your projects.\n\nI am particularly interested in remote work and have successfully worked in distributed teams before. I am eager to contribute to your modern web applications.\n\nBest regards,\nJane Smith',
'https://example.com/resumes/jane_smith_fullstack.pdf', 3, 'Good technical skills, proceeding to final interview round.', DATE_SUB(NOW(), INTERVAL 3 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY)),

-- Mike Johnson 的申请
(7, (SELECT candidate_id FROM candidates WHERE name = 'Mike Johnson'),
'Dear Innovate.io,\n\nI am interested in the DevOps Engineer position. With 7 years of experience in AWS, Kubernetes, and infrastructure automation, I can help scale your infrastructure and improve deployment processes.\n\nMy experience with Terraform, Docker, and CI/CD pipelines would be valuable for your growing startup. I am excited about working with cutting-edge cloud technologies.\n\nBest regards,\nMike Johnson',
'https://example.com/resumes/mike_johnson_devops.pdf', 2, 'Excellent DevOps background, good fit for our infrastructure needs.', DATE_SUB(NOW(), INTERVAL 4 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY)),

-- David Brown 的申请
(4, (SELECT candidate_id FROM candidates WHERE name = 'David Brown'),
'Dear BigTech Corp,\n\nI am very interested in the Senior Backend Engineer position. My 6 years of experience with Java, Spring Boot, and microservices architecture make me well-suited for this role.\n\nI have experience building scalable systems that serve millions of users and am comfortable with on-call responsibilities. I would love to contribute to your platform team.\n\nSincerely,\nDavid Brown',
'https://example.com/resumes/david_brown_backend.pdf', 1, NULL, DATE_SUB(NOW(), INTERVAL 5 DAY), DATE_SUB(NOW(), INTERVAL 5 DAY)),

-- Lisa Garcia 的申请
(6, (SELECT candidate_id FROM candidates WHERE name = 'Lisa Garcia'),
'Dear TechCorp Solutions,\n\nI am excited to apply for the UX/UI Designer position. My 4 years of experience in user-centered design, combined with my proficiency in Figma and user research, would be valuable to your design team.\n\nI have a strong portfolio showcasing my work on web and mobile applications. I am passionate about creating intuitive and beautiful user experiences.\n\nBest regards,\nLisa Garcia',
'https://example.com/resumes/lisa_garcia_design.pdf', 2, 'Great portfolio, strong design skills. Moving forward with design challenge.', DATE_SUB(NOW(), INTERVAL 6 DAY), DATE_SUB(NOW(), INTERVAL 3 DAY)),

-- Tom Anderson 的申请
(3, (SELECT candidate_id FROM candidates WHERE name = 'Tom Anderson'),
'Dear Startup Dev,\n\nAs a recent computer science graduate, I am very interested in the Full Stack Developer position. While I may be junior, I have a strong foundation in JavaScript, React, and Python, and I am eager to learn and grow.\n\nI am particularly excited about remote work and the opportunity to work with modern technologies. I am a quick learner and would bring fresh perspectives to your team.\n\nThank you for considering my application,\nTom Anderson',
'https://example.com/resumes/tom_anderson_junior.pdf', 4, 'Junior candidate, not enough experience for current requirements.', DATE_SUB(NOW(), INTERVAL 8 DAY), DATE_SUB(NOW(), INTERVAL 7 DAY));

-- =============================================
-- 6. 测试消息数据
-- =============================================

-- 插入测试消息
INSERT INTO messages (sender_id, receiver_id, message_type, subject, content, read_status, read_at, created_at) VALUES
-- TechCorp 与 John Doe 的对话
((SELECT user_id FROM users WHERE email = 'hr@techcorp.com'),
 (SELECT user_id FROM users WHERE email = 'john.doe@example.com'),
 2, 'Interview Invitation - Senior React Developer',
 'Hi John,\n\nWe were impressed with your application for the Senior React Developer position and would like to invite you for a technical interview.\n\nThe interview will consist of:\n1. Technical discussion (45 minutes)\n2. Code review session (30 minutes)\n3. Team fit conversation (15 minutes)\n\nPlease let us know your availability for next week. We are flexible with timing.\n\nBest regards,\nAlice Johnson\nTechCorp Solutions',
 false, NULL, DATE_SUB(NOW(), INTERVAL 1 DAY)),

((SELECT user_id FROM users WHERE email = 'john.doe@example.com'),
 (SELECT user_id FROM users WHERE email = 'hr@techcorp.com'),
 1, 'Re: Interview Invitation - Senior React Developer',
 'Hi Alice,\n\nThank you for the invitation! I am very excited about this opportunity.\n\nI am available:\n- Monday: 2:00 PM - 5:00 PM\n- Tuesday: 10:00 AM - 4:00 PM\n- Wednesday: 9:00 AM - 12:00 PM\n\nPlease let me know what works best for your team. I look forward to discussing how I can contribute to TechCorp Solutions.\n\nBest regards,\nJohn Doe',
 true, DATE_SUB(NOW(), INTERVAL 12 HOUR), DATE_SUB(NOW(), INTERVAL 18 HOUR)),

-- Startup Dev 与 Jane Smith 的对话
((SELECT user_id FROM users WHERE email = 'jobs@startup.dev'),
 (SELECT user_id FROM users WHERE email = 'jane.smith@example.com'),
 3, 'Job Offer - Full Stack Developer Position',
 'Dear Jane,\n\nCongratulations! We would like to offer you the Full Stack Developer position at Startup Dev.\n\nOffer details:\n- Salary: $75,000 annually\n- Equity: 0.5%\n- Benefits: Health stipend, flexible hours, professional development budget\n- Start date: Flexible\n\nThis offer is valid for 7 days. Please let us know if you have any questions or would like to discuss any aspects of the offer.\n\nWe are excited about the possibility of you joining our team!\n\nBest regards,\nCarol Martinez\nStartup Dev',
 false, NULL, DATE_SUB(NOW(), INTERVAL 2 HOUR)),

-- Innovate.io 与 Sarah Wilson 的对话
((SELECT user_id FROM users WHERE email = 'recruiter@innovate.io'),
 (SELECT user_id FROM users WHERE email = 'sarah.wilson@example.com'),
 1, 'Application Status Update - Machine Learning Engineer',
 'Hi Sarah,\n\nThank you for your application for the Machine Learning Engineer position. We have reviewed your background and are impressed with your PhD in Statistics and ML experience.\n\nWe would like to schedule a preliminary phone screening to discuss your experience and the role in more detail.\n\nAre you available for a 30-minute call this week? Please let me know your preferred times.\n\nBest regards,\nBob Chen\nInnovate.io',
 false, NULL, DATE_SUB(NOW(), INTERVAL 6 HOUR)),

-- BigTech 内部消息
((SELECT user_id FROM users WHERE email = 'hiring@bigtech.com'),
 (SELECT user_id FROM users WHERE email = 'david.brown@example.com'),
 1, 'Thank you for your application',
 'Dear David,\n\nThank you for your interest in the Senior Backend Engineer position at BigTech Corp. We have received your application and our team is currently reviewing it.\n\nWe receive a high volume of applications, so please allow 1-2 weeks for our initial review process. We will contact you if your background matches our current needs.\n\nThank you for considering BigTech Corp as your next career opportunity.\n\nBest regards,\nBigTech Recruiting Team',
 true, DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_SUB(NOW(), INTERVAL 4 DAY));

-- =============================================
-- 7. 测试通知数据
-- =============================================

-- 插入系统通知
INSERT INTO notifications (user_id, type, title, content, data, read_status, read_at, created_at) VALUES
-- 申请状态通知
((SELECT user_id FROM users WHERE email = 'john.doe@example.com'),
 'application_status', 'Application Status Update',
 'Your application for Senior React Developer at TechCorp Solutions has been viewed by the employer.',
 JSON_OBJECT('job_id', 1, 'status', 'viewed', 'company', 'TechCorp Solutions'),
 true, DATE_SUB(NOW(), INTERVAL 1 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY)),

((SELECT user_id FROM users WHERE email = 'jane.smith@example.com'),
 'application_status', 'Interview Scheduled',
 'Great news! Your interview for Full Stack Developer at Startup Dev has been scheduled.',
 JSON_OBJECT('job_id', 3, 'status', 'interview', 'company', 'Startup Dev', 'interview_date', '2025-09-20'),
 false, NULL, DATE_SUB(NOW(), INTERVAL 1 DAY)),

-- 职位匹配通知
((SELECT user_id FROM users WHERE email = 'sarah.wilson@example.com'),
 'job_match', 'New Job Match',
 'We found a new job that matches your profile: Machine Learning Engineer at Innovate.io.',
 JSON_OBJECT('job_id', 2, 'match_score', 92, 'company', 'Innovate.io'),
 false, NULL, DATE_SUB(NOW(), INTERVAL 3 HOUR)),

((SELECT user_id FROM users WHERE email = 'mike.johnson@example.com'),
 'job_match', 'Perfect Job Match',
 'Excellent match found: DevOps Engineer at Innovate.io matches your skills perfectly!',
 JSON_OBJECT('job_id', 7, 'match_score', 95, 'company', 'Innovate.io'),
 false, NULL, DATE_SUB(NOW(), INTERVAL 5 HOUR)),

-- 系统通知
((SELECT user_id FROM users WHERE email = 'david.brown@example.com'),
 'system', 'Profile Views',
 'Your profile has been viewed 5 times this week by potential employers.',
 JSON_OBJECT('view_count', 5, 'period', 'week'),
 true, DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_SUB(NOW(), INTERVAL 3 DAY)),

((SELECT user_id FROM users WHERE email = 'lisa.garcia@example.com'),
 'job_alert', 'New UX Jobs Available',
 '3 new UX/UI Designer positions match your job alert criteria.',
 JSON_OBJECT('job_count', 3, 'alert_name', 'UX Designer Jobs'),
 false, NULL, DATE_SUB(NOW(), INTERVAL 1 HOUR)),

-- 雇主通知
((SELECT user_id FROM users WHERE email = 'hr@techcorp.com'),
 'application_received', 'New Application Received',
 'You have received a new application for Senior React Developer position.',
 JSON_OBJECT('job_id', 1, 'candidate_name', 'John Doe', 'application_id', 1),
 true, DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY)),

((SELECT user_id FROM users WHERE email = 'jobs@startup.dev'),
 'job_expiring', 'Job Posting Expiring Soon',
 'Your job posting "Full Stack Developer" will expire in 7 days.',
 JSON_OBJECT('job_id', 3, 'days_remaining', 7),
 false, NULL, DATE_SUB(NOW(), INTERVAL 4 HOUR));

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
