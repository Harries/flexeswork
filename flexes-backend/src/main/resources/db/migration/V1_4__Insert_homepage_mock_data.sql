-- 插入首页所需的模拟数据

-- 插入职位分类数据
INSERT INTO job_categories (name, slug, description, sort_order, status) VALUES
('Design & Creative', 'design-creative', 'UI/UX Design, Graphic Design, Creative roles', 1, 1),
('Development & IT', 'development-it', 'Software Development, IT Support, DevOps', 2, 1),
('Sales & Marketing', 'sales-marketing', 'Sales, Digital Marketing, Business Development', 3, 1),
('Writing & Translation', 'writing-translation', 'Content Writing, Translation, Copywriting', 4, 1),
('Admin & Customer Support', 'admin-support', 'Administrative, Customer Service, Virtual Assistant', 5, 1),
('Finance & Accounting', 'finance-accounting', 'Accounting, Financial Analysis, Bookkeeping', 6, 1),
('Engineering & Architecture', 'engineering-architecture', 'Civil Engineering, Architecture, Mechanical Engineering', 7, 1),
('Legal', 'legal', 'Legal Counsel, Paralegal, Compliance', 8, 1);

-- 插入用户数据
INSERT INTO users (email, password, role, status, email_verified, created_at) VALUES
('admin@flexes.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9P2.nRwHRfzwNw.', 0, 1, TRUE, NOW()),
('employer1@company.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9P2.nRwHRfzwNw.', 2, 1, TRUE, NOW()),
('employer2@techcorp.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9P2.nRwHRfzwNw.', 2, 1, TRUE, NOW()),
('employer3@startup.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9P2.nRwHRfzwNw.', 2, 1, TRUE, NOW()),
('employer4@bigtech.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9P2.nRwHRfzwNw.', 2, 1, TRUE, NOW()),
('employer5@design.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9P2.nRwHRfzwNw.', 2, 1, TRUE, NOW()),
('employer6@finance.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9P2.nRwHRfzwNw.', 2, 1, TRUE, NOW()),
('employer7@marketing.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9P2.nRwHRfzwNw.', 2, 1, TRUE, NOW()),
('employer8@consulting.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9P2.nRwHRfzwNw.', 2, 1, TRUE, NOW()),
('candidate1@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9P2.nRwHRfzwNw.', 1, 1, TRUE, NOW()),
('candidate2@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9P2.nRwHRfzwNw.', 1, 1, TRUE, NOW()),
('candidate3@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9P2.nRwHRfzwNw.', 1, 1, TRUE, NOW()),
('author@flexes.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9P2.nRwHRfzwNw.', 0, 1, TRUE, NOW());

-- 插入雇主数据
INSERT INTO employers (user_id, company_name, company_logo, industry, company_size, founded_year, website, location, description, contact_person, contact_phone, verified, created_at) VALUES
(2, 'TechCorp Solutions', 'https://via.placeholder.com/100x100?text=TC', 'Technology', 4, 2015, 'https://techcorp.com', 'San Francisco, CA', 'Leading technology solutions provider', 'John Smith', '+1-555-0101', TRUE, NOW()),
(3, 'Digital Innovations', 'https://via.placeholder.com/100x100?text=DI', 'Software', 3, 2018, 'https://digitalinnovations.com', 'New York, NY', 'Innovative software development company', 'Sarah Johnson', '+1-555-0102', TRUE, NOW()),
(4, 'Creative Studio', 'https://via.placeholder.com/100x100?text=CS', 'Design', 2, 2020, 'https://creativestudio.com', 'Los Angeles, CA', 'Award-winning design agency', 'Mike Davis', '+1-555-0103', TRUE, NOW()),
(5, 'Global Enterprises', 'https://via.placeholder.com/100x100?text=GE', 'Consulting', 5, 2010, 'https://globalenterprises.com', 'Chicago, IL', 'International business consulting', 'Lisa Wilson', '+1-555-0104', TRUE, NOW()),
(6, 'FinTech Pro', 'https://via.placeholder.com/100x100?text=FP', 'Finance', 3, 2019, 'https://fintechpro.com', 'Boston, MA', 'Financial technology solutions', 'David Brown', '+1-555-0105', TRUE, NOW()),
(7, 'Marketing Masters', 'https://via.placeholder.com/100x100?text=MM', 'Marketing', 2, 2017, 'https://marketingmasters.com', 'Austin, TX', 'Digital marketing excellence', 'Emma Taylor', '+1-555-0106', TRUE, NOW()),
(8, 'Data Analytics Inc', 'https://via.placeholder.com/100x100?text=DA', 'Analytics', 3, 2016, 'https://dataanalytics.com', 'Seattle, WA', 'Big data and analytics solutions', 'Robert Miller', '+1-555-0107', TRUE, NOW()),
(9, 'Cloud Systems', 'https://via.placeholder.com/100x100?text=CL', 'Cloud Computing', 4, 2014, 'https://cloudsystems.com', 'Denver, CO', 'Cloud infrastructure and services', 'Jennifer Garcia', '+1-555-0108', TRUE, NOW());

-- 插入求职者数据
INSERT INTO candidates (user_id, first_name, last_name, phone, location, bio, experience_level, education_level, expected_salary_min, expected_salary_max, created_at) VALUES
(10, 'Thomas', 'Albedin', '+1-555-1001', 'San Francisco, CA', 'Experienced software developer with 5+ years in full-stack development', 3, 4, 80000, 120000, NOW()),
(11, 'Camelia', 'Rose', '+1-555-1002', 'New York, NY', 'Creative UI/UX designer passionate about user-centered design', 2, 4, 60000, 90000, NOW()),
(12, 'John', 'Carter', '+1-555-1003', 'Los Angeles, CA', 'Digital marketing specialist with expertise in SEO and social media', 3, 4, 70000, 100000, NOW());

-- 插入职位数据
INSERT INTO jobs (employer_id, category_id, title, description, requirements, employment_type, experience_level, education_level, salary_min, salary_max, location, remote_type, status, view_count, application_count, featured, created_at) VALUES
(1, 2, 'Senior Frontend Developer', 'We are looking for a skilled Frontend Developer to join our dynamic team. You will be responsible for developing user-facing web applications using modern JavaScript frameworks.', 'React, TypeScript, 3+ years experience', 1, 4, 4, 90000, 130000, 'San Francisco, CA', 2, 1, 245, 12, TRUE, NOW()),
(2, 1, 'UI/UX Designer', 'Join our design team to create beautiful and intuitive user experiences. You will work closely with product managers and developers to bring designs to life.', 'Figma, Adobe Creative Suite, 2+ years experience', 1, 3, 4, 70000, 100000, 'New York, NY', 1, 1, 189, 8, TRUE, NOW()),
(3, 3, 'Digital Marketing Manager', 'Lead our digital marketing efforts across multiple channels. Develop and execute marketing strategies to drive growth and engagement.', 'Google Ads, SEO, Social Media Marketing, 3+ years experience', 1, 3, 4, 75000, 105000, 'Los Angeles, CA', 2, 1, 156, 15, TRUE, NOW()),
(4, 2, 'Full Stack Developer', 'Work on both frontend and backend development for our web applications. Collaborate with cross-functional teams to deliver high-quality software.', 'Node.js, React, MongoDB, 2+ years experience', 1, 3, 4, 80000, 110000, 'Chicago, IL', 1, 1, 203, 9, TRUE, NOW()),
(5, 2, 'iOS Developer', 'Develop and maintain iOS applications for our mobile platform. Work with the latest iOS technologies and frameworks.', 'Swift, iOS SDK, 2+ years experience', 1, 3, 4, 85000, 115000, 'Boston, MA', 2, 1, 178, 6, TRUE, NOW());

-- 插入用户评价数据
INSERT INTO user_reviews (user_id, user_role, user_title, rating, content, helpful_count, status, created_at) VALUES
(10, 'Software Developer', 'Senior Frontend Developer', 5, 'Flexes helped me find my dream job! The platform is user-friendly and the job matching is excellent. I received multiple offers within two weeks of creating my profile.', 24, 1, NOW()),
(11, 'UI/UX Designer', 'Product Designer', 5, 'Amazing platform for creative professionals. The quality of job postings is outstanding, and the application process is smooth. Highly recommend to fellow designers!', 18, 1, NOW()),
(12, 'Marketing Specialist', 'Digital Marketing Manager', 4, 'Great experience overall. Found several interesting opportunities and the communication with employers was seamless. The platform really understands what job seekers need.', 15, 1, NOW());

-- 插入博客文章数据
INSERT INTO blog_articles (title, slug, summary, content, cover_image, author_id, author_name, author_avatar, category, read_count, like_count, comment_count, read_time, featured, status, published_at, created_at) VALUES
('How to Write a Compelling Cover Letter', 'how-to-write-compelling-cover-letter', 'Learn the essential tips and tricks to create a cover letter that stands out from the crowd and gets you noticed by employers.', 'A compelling cover letter can be the difference between landing an interview and having your application overlooked...', 'https://via.placeholder.com/400x250?text=Cover+Letter', 13, 'Career Expert', 'https://via.placeholder.com/50x50?text=CE', 'Career Tips', 1250, 89, 23, 5, TRUE, 1, NOW(), NOW()),
('Top 10 Interview Questions and How to Answer Them', 'top-10-interview-questions-answers', 'Prepare for your next job interview with these commonly asked questions and expert-approved answers that will impress any hiring manager.', 'Job interviews can be nerve-wracking, but preparation is key to success...', 'https://via.placeholder.com/400x250?text=Interview', 13, 'HR Specialist', 'https://via.placeholder.com/50x50?text=HR', 'Interview Tips', 980, 67, 18, 7, TRUE, 1, NOW(), NOW()),
('Remote Work: Benefits and Best Practices', 'remote-work-benefits-best-practices', 'Discover the advantages of remote work and learn the best practices to stay productive and maintain work-life balance while working from home.', 'Remote work has become increasingly popular, offering flexibility and new opportunities...', 'https://via.placeholder.com/400x250?text=Remote+Work', 13, 'Remote Work Expert', 'https://via.placeholder.com/50x50?text=RW', 'Remote Work', 756, 45, 12, 6, TRUE, 1, NOW(), NOW());
