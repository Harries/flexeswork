// 用户相关类型
export interface User {
  id: number;
  email: string;
  role: UserRole;
  status: UserStatus;
  emailVerified: boolean;
  lastLoginAt?: string;
  createdAt: string;
  updatedAt: string;
}

export enum UserRole {
  ADMIN = 0,
  CANDIDATE = 1,
  EMPLOYER = 2,
}

export enum UserStatus {
  DISABLED = 0,
  ACTIVE = 1,
  PENDING = 2,
}

// 求职者相关类型
export interface Candidate {
  id: number;
  userId: number;
  name: string;
  phone?: string;
  avatarUrl?: string;
  location?: string;
  currentPosition?: string;
  experienceYears: number;
  educationLevel: EducationLevel;
  expectedSalaryMin?: number;
  expectedSalaryMax?: number;
  jobStatus: CandidateJobStatus;
  resumeUrl?: string;
  bio?: string;
  skills: string[];
  createdAt: string;
  updatedAt: string;
}

export enum EducationLevel {
  HIGH_SCHOOL = 1,
  ASSOCIATE = 2,
  BACHELOR = 3,
  MASTER = 4,
  DOCTORATE = 5,
}

export enum CandidateJobStatus {
  NOT_LOOKING = 0,
  OPEN_TO_OPPORTUNITIES = 1,
  ACTIVELY_LOOKING = 2,
}

// 雇主相关类型
export interface Employer {
  id: number;
  userId: number;
  companyName: string;
  companyLogo?: string;
  industry?: string;
  companySize: CompanySize;
  foundedYear?: number;
  website?: string;
  location?: string;
  description?: string;
  contactPerson?: string;
  contactPhone?: string;
  verified: boolean;
  createdAt: string;
  updatedAt: string;
}

export enum CompanySize {
  STARTUP = 1,    // 1-10人
  SMALL = 2,      // 11-50人
  MEDIUM = 3,     // 51-200人
  LARGE = 4,      // 201-1000人
  ENTERPRISE = 5, // 1000+人
}

// 职位相关类型
export interface Job {
  id: number;
  employerId: number;
  categoryId?: number;
  title: string;
  description: string;
  requirements?: string;
  responsibilities?: string;
  skillsRequired: string[];
  skillsPreferred: string[];
  employmentType: EmploymentType;
  experienceLevel: ExperienceLevel;
  educationLevel: EducationLevel;
  salaryMin?: number;
  salaryMax?: number;
  salaryCurrency: string;
  location?: string;
  remoteType: RemoteType;
  benefits?: string;
  applicationDeadline?: string;
  status: JobStatus;
  viewCount: number;
  applicationCount: number;
  featured: boolean;
  createdAt: string;
  updatedAt: string;
  // 关联数据
  employer?: Employer;
  category?: JobCategory;
}

export enum EmploymentType {
  FULL_TIME = 1,
  PART_TIME = 2,
  CONTRACT = 3,
  INTERNSHIP = 4,
}

export enum ExperienceLevel {
  ENTRY = 1,
  JUNIOR = 2,
  MID = 3,
  SENIOR = 4,
  EXPERT = 5,
}

export enum RemoteType {
  FULLY_REMOTE = 1,
  HYBRID = 2,
  ON_SITE = 3,
}

export enum JobStatus {
  PENDING = 0,
  ACTIVE = 1,
  PAUSED = 2,
  CLOSED = 3,
}

// 职位分类
export interface JobCategory {
  id: number;
  name: string;
  slug: string;
  description?: string;
  sortOrder: number;
  status: number;
  createdAt: string;
}

// 职位申请
export interface JobApplication {
  id: number;
  jobId: number;
  candidateId: number;
  coverLetter?: string;
  resumeUrl?: string;
  status: ApplicationStatus;
  employerNotes?: string;
  appliedAt: string;
  updatedAt: string;
  // 关联数据
  job?: Job;
  candidate?: Candidate;
}

export enum ApplicationStatus {
  SUBMITTED = 1,
  VIEWED = 2,
  INTERVIEWING = 3,
  REJECTED = 4,
  HIRED = 5,
}

// API响应类型
export interface ApiResponse<T = any> {
  success: boolean;
  data: T;
  message?: string;
  code?: number;
}

export interface PaginatedResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  first: boolean;
  last: boolean;
}

// 分页参数
export interface PaginationParams {
  page: number;
  size: number;
  sort?: string;
  direction?: 'asc' | 'desc';
}

// 搜索参数
export interface JobSearchParams extends PaginationParams {
  keyword?: string;
  location?: string;
  categoryId?: number;
  employmentType?: EmploymentType;
  experienceLevel?: ExperienceLevel;
  salaryMin?: number;
  salaryMax?: number;
  remoteType?: RemoteType;
}

export interface CandidateSearchParams extends PaginationParams {
  keyword?: string;
  location?: string;
  radius?: number;
  experienceLevel?: ExperienceLevel;
  educationLevel?: EducationLevel;
  skills?: string[];
}

// 表单类型
export interface LoginForm {
  email: string;
  password: string;
  remember?: boolean;
}

export interface RegisterForm {
  email: string;
  password: string;
  confirmPassword: string;
  role: UserRole;
  agreeToTerms: boolean;
}

export interface JobForm {
  title: string;
  categoryId?: number;
  description: string;
  requirements?: string;
  responsibilities?: string;
  skillsRequired: string[];
  skillsPreferred: string[];
  employmentType: EmploymentType;
  experienceLevel: ExperienceLevel;
  educationLevel: EducationLevel;
  salaryMin?: number;
  salaryMax?: number;
  location?: string;
  remoteType: RemoteType;
  benefits?: string;
  applicationDeadline?: string;
}

// 消息通知
export interface Message {
  id: number;
  senderId: number;
  receiverId: number;
  messageType: MessageType;
  subject?: string;
  content: string;
  readStatus: boolean;
  readAt?: string;
  createdAt: string;
}

export enum MessageType {
  GENERAL = 1,
  INTERVIEW_INVITATION = 2,
  JOB_OFFER = 3,
  SYSTEM_NOTIFICATION = 4,
}

export interface Notification {
  id: number;
  userId: number;
  type: string;
  title: string;
  content?: string;
  data?: any;
  readStatus: boolean;
  readAt?: string;
  createdAt: string;
}
