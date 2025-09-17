import { apiClient } from './api';
import { ApiResponse, PaginatedResponse, Job, JobForm, JobSearchParams, JobCategory, JobApplication } from '../types';

// 职位相关API
export const jobService = {
  // 获取职位列表
  getJobs: async (params: JobSearchParams): Promise<ApiResponse<PaginatedResponse<Job>>> => {
    return apiClient.get('/jobs', { params });
  },

  // 获取职位详情
  getJobById: async (id: number): Promise<ApiResponse<Job>> => {
    return apiClient.get(`/jobs/${id}`);
  },

  // 创建职位
  createJob: async (jobData: JobForm): Promise<ApiResponse<Job>> => {
    return apiClient.post('/jobs', jobData);
  },

  // 更新职位
  updateJob: async (id: number, jobData: Partial<JobForm>): Promise<ApiResponse<Job>> => {
    return apiClient.put(`/jobs/${id}`, jobData);
  },

  // 删除职位
  deleteJob: async (id: number): Promise<ApiResponse<void>> => {
    return apiClient.delete(`/jobs/${id}`);
  },

  // 获取热门职位
  getHotJobs: async (limit: number = 10): Promise<ApiResponse<Job[]>> => {
    return apiClient.get('/jobs/hot', { params: { limit } });
  },

  // 获取最新职位
  getLatestJobs: async (limit: number = 10): Promise<ApiResponse<Job[]>> => {
    return apiClient.get('/jobs/latest', { params: { limit } });
  },

  // 获取精选职位
  getFeaturedJobs: async (limit: number = 10): Promise<ApiResponse<Job[]>> => {
    return apiClient.get('/jobs/featured', { params: { limit } });
  },

  // 搜索职位
  searchJobs: async (keyword: string, params?: Partial<JobSearchParams>): Promise<ApiResponse<PaginatedResponse<Job>>> => {
    return apiClient.get('/jobs/search', { 
      params: { keyword, ...params } 
    });
  },

  // 获取相关职位推荐
  getRelatedJobs: async (jobId: number, limit: number = 5): Promise<ApiResponse<Job[]>> => {
    return apiClient.get(`/jobs/${jobId}/related`, { params: { limit } });
  },

  // 增加职位浏览量
  incrementJobView: async (jobId: number): Promise<ApiResponse<void>> => {
    return apiClient.post(`/jobs/${jobId}/view`);
  },

  // 收藏职位
  favoriteJob: async (jobId: number): Promise<ApiResponse<void>> => {
    return apiClient.post(`/jobs/${jobId}/favorite`);
  },

  // 取消收藏职位
  unfavoriteJob: async (jobId: number): Promise<ApiResponse<void>> => {
    return apiClient.delete(`/jobs/${jobId}/favorite`);
  },

  // 申请职位
  applyJob: async (jobId: number, applicationData: { coverLetter?: string; resumeUrl?: string }): Promise<ApiResponse<JobApplication>> => {
    return apiClient.post(`/jobs/${jobId}/apply`, applicationData);
  },

  // 检查是否可以申请职位
  checkCanApply: async (jobId: number): Promise<ApiResponse<{ canApply: boolean; reason?: string; todayCount: number }>> => {
    return apiClient.get(`/jobs/${jobId}/can-apply`);
  },

  // 获取雇主的职位列表
  getEmployerJobs: async (employerId: number, params?: Partial<JobSearchParams>): Promise<ApiResponse<PaginatedResponse<Job>>> => {
    return apiClient.get(`/employers/${employerId}/jobs`, { params });
  },
};

// 职位分类相关API
export const jobCategoryService = {
  // 获取所有职位分类
  getCategories: async (): Promise<ApiResponse<JobCategory[]>> => {
    return apiClient.get('/job-categories');
  },

  // 获取分类详情
  getCategoryById: async (id: number): Promise<ApiResponse<JobCategory>> => {
    return apiClient.get(`/job-categories/${id}`);
  },

  // 获取分类下的职位数量
  getCategoryJobCount: async (id: number): Promise<ApiResponse<{ count: number }>> => {
    return apiClient.get(`/job-categories/${id}/count`);
  },
};

// 职位申请相关API
export const jobApplicationService = {
  // 获取用户的申请列表
  getMyApplications: async (params?: { page?: number; size?: number; status?: number }): Promise<ApiResponse<PaginatedResponse<JobApplication>>> => {
    return apiClient.get('/applications/my', { params });
  },

  // 获取申请详情
  getApplicationById: async (id: number): Promise<ApiResponse<JobApplication>> => {
    return apiClient.get(`/applications/${id}`);
  },

  // 撤回申请
  withdrawApplication: async (id: number): Promise<ApiResponse<void>> => {
    return apiClient.delete(`/applications/${id}`);
  },

  // 获取职位的申请列表（雇主用）
  getJobApplications: async (jobId: number, params?: { page?: number; size?: number; status?: number }): Promise<ApiResponse<PaginatedResponse<JobApplication>>> => {
    return apiClient.get(`/jobs/${jobId}/applications`, { params });
  },

  // 更新申请状态（雇主用）
  updateApplicationStatus: async (id: number, status: number, notes?: string): Promise<ApiResponse<JobApplication>> => {
    return apiClient.patch(`/applications/${id}/status`, { status, notes });
  },

  // 批量更新申请状态（雇主用）
  batchUpdateApplicationStatus: async (ids: number[], status: number): Promise<ApiResponse<void>> => {
    return apiClient.patch('/applications/batch-status', { ids, status });
  },

  // 获取今日申请次数
  getTodayApplicationCount: async (): Promise<ApiResponse<{ count: number; limit: number }>> => {
    return apiClient.get('/applications/today-count');
  },
};
