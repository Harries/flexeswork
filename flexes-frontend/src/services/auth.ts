import { apiClient } from './api';
import { ApiResponse, User, LoginForm, RegisterForm } from '../types';

// 认证相关API
export const authService = {
  // 用户登录
  login: async (credentials: LoginForm): Promise<ApiResponse<{ user: User; token: string; refreshToken: string }>> => {
    const response = await apiClient.post('/auth/login', credentials);
    
    // 保存token到localStorage
    if (response.success && response.data) {
      localStorage.setItem('access_token', response.data.token);
      localStorage.setItem('refresh_token', response.data.refreshToken);
      localStorage.setItem('user', JSON.stringify(response.data.user));
    }
    
    return response;
  },

  // 用户注册
  register: async (userData: RegisterForm): Promise<ApiResponse<{ user: User }>> => {
    return apiClient.post('/auth/register', userData);
  },

  // 用户登出
  logout: async (): Promise<ApiResponse<void>> => {
    const response = await apiClient.post('/auth/logout');
    
    // 清除本地存储
    localStorage.removeItem('access_token');
    localStorage.removeItem('refresh_token');
    localStorage.removeItem('user');
    
    return response;
  },

  // 刷新token
  refreshToken: async (): Promise<ApiResponse<{ token: string; refreshToken: string }>> => {
    const refreshToken = localStorage.getItem('refresh_token');
    const response = await apiClient.post('/auth/refresh', { refreshToken });
    
    if (response.success && response.data) {
      localStorage.setItem('access_token', response.data.token);
      localStorage.setItem('refresh_token', response.data.refreshToken);
    }
    
    return response;
  },

  // 发送邮箱验证
  sendEmailVerification: async (email: string): Promise<ApiResponse<void>> => {
    return apiClient.post('/auth/send-verification', { email });
  },

  // 验证邮箱
  verifyEmail: async (token: string): Promise<ApiResponse<void>> => {
    return apiClient.post('/auth/verify-email', { token });
  },

  // 发送重置密码邮件
  sendPasswordReset: async (email: string): Promise<ApiResponse<void>> => {
    return apiClient.post('/auth/forgot-password', { email });
  },

  // 重置密码
  resetPassword: async (token: string, password: string): Promise<ApiResponse<void>> => {
    return apiClient.post('/auth/reset-password', { token, password });
  },

  // 修改密码
  changePassword: async (currentPassword: string, newPassword: string): Promise<ApiResponse<void>> => {
    return apiClient.post('/auth/change-password', { currentPassword, newPassword });
  },

  // 获取当前用户信息
  getCurrentUser: async (): Promise<ApiResponse<User>> => {
    return apiClient.get('/auth/me');
  },

  // 检查token有效性
  validateToken: async (): Promise<ApiResponse<{ valid: boolean }>> => {
    return apiClient.get('/auth/validate');
  },
};

// 本地存储工具
export const authStorage = {
  // 获取token
  getToken: (): string | null => {
    return localStorage.getItem('access_token');
  },

  // 获取刷新token
  getRefreshToken: (): string | null => {
    return localStorage.getItem('refresh_token');
  },

  // 获取用户信息
  getUser: (): User | null => {
    const userStr = localStorage.getItem('user');
    return userStr ? JSON.parse(userStr) : null;
  },

  // 检查是否已登录
  isAuthenticated: (): boolean => {
    return !!localStorage.getItem('access_token');
  },

  // 清除认证信息
  clearAuth: (): void => {
    localStorage.removeItem('access_token');
    localStorage.removeItem('refresh_token');
    localStorage.removeItem('user');
  },
};
