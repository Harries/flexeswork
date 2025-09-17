import { createSlice, createAsyncThunk, PayloadAction } from '@reduxjs/toolkit';
import { User, LoginForm, RegisterForm, UserRole } from '../../types';
import { authService, authStorage } from '../../services/auth';
import { message } from 'antd';

// 状态接口
interface AuthState {
  user: User | null;
  isAuthenticated: boolean;
  loading: boolean;
  error: string | null;
  loginLoading: boolean;
  registerLoading: boolean;
}

// 初始状态
const initialState: AuthState = {
  user: authStorage.getUser(),
  isAuthenticated: authStorage.isAuthenticated(),
  loading: false,
  error: null,
  loginLoading: false,
  registerLoading: false,
};

// 异步actions
export const loginAsync = createAsyncThunk(
  'auth/login',
  async (credentials: LoginForm, { rejectWithValue }) => {
    try {
      const response = await authService.login(credentials);
      if (response.success) {
        message.success('登录成功');
        return response.data;
      } else {
        return rejectWithValue(response.message || '登录失败');
      }
    } catch (error: any) {
      return rejectWithValue(error.message || '登录失败');
    }
  }
);

export const registerAsync = createAsyncThunk(
  'auth/register',
  async (userData: RegisterForm, { rejectWithValue }) => {
    try {
      const response = await authService.register(userData);
      if (response.success) {
        message.success('注册成功，请查收验证邮件');
        return response.data;
      } else {
        return rejectWithValue(response.message || '注册失败');
      }
    } catch (error: any) {
      return rejectWithValue(error.message || '注册失败');
    }
  }
);

export const logoutAsync = createAsyncThunk(
  'auth/logout',
  async (_, { rejectWithValue }) => {
    try {
      await authService.logout();
      message.success('已退出登录');
    } catch (error: any) {
      // 即使API调用失败，也要清除本地状态
      // Silently handle logout API errors
    }
    // 总是清除本地存储
    authStorage.clearAuth();
  }
);

export const getCurrentUserAsync = createAsyncThunk(
  'auth/getCurrentUser',
  async (_, { rejectWithValue }) => {
    try {
      const response = await authService.getCurrentUser();
      if (response.success) {
        return response.data;
      } else {
        return rejectWithValue(response.message || '获取用户信息失败');
      }
    } catch (error: any) {
      return rejectWithValue(error.message || '获取用户信息失败');
    }
  }
);

export const changePasswordAsync = createAsyncThunk(
  'auth/changePassword',
  async ({ currentPassword, newPassword }: { currentPassword: string; newPassword: string }, { rejectWithValue }) => {
    try {
      const response = await authService.changePassword(currentPassword, newPassword);
      if (response.success) {
        message.success('密码修改成功');
        return response.data;
      } else {
        return rejectWithValue(response.message || '密码修改失败');
      }
    } catch (error: any) {
      return rejectWithValue(error.message || '密码修改失败');
    }
  }
);

// 创建slice
const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    // 清除错误
    clearError: (state) => {
      state.error = null;
    },
    
    // 更新用户信息
    updateUser: (state, action: PayloadAction<Partial<User>>) => {
      if (state.user) {
        state.user = { ...state.user, ...action.payload };
        localStorage.setItem('user', JSON.stringify(state.user));
      }
    },
    
    // 设置认证状态
    setAuthenticated: (state, action: PayloadAction<boolean>) => {
      state.isAuthenticated = action.payload;
    },
    
    // 初始化认证状态（从localStorage恢复）
    initializeAuth: (state) => {
      state.user = authStorage.getUser();
      state.isAuthenticated = authStorage.isAuthenticated();
    },
  },
  extraReducers: (builder) => {
    // 登录
    builder
      .addCase(loginAsync.pending, (state) => {
        state.loginLoading = true;
        state.error = null;
      })
      .addCase(loginAsync.fulfilled, (state, action) => {
        state.loginLoading = false;
        state.user = action.payload.user;
        state.isAuthenticated = true;
        state.error = null;
      })
      .addCase(loginAsync.rejected, (state, action) => {
        state.loginLoading = false;
        state.error = action.payload as string;
        state.isAuthenticated = false;
        state.user = null;
      });

    // 注册
    builder
      .addCase(registerAsync.pending, (state) => {
        state.registerLoading = true;
        state.error = null;
      })
      .addCase(registerAsync.fulfilled, (state) => {
        state.registerLoading = false;
        state.error = null;
      })
      .addCase(registerAsync.rejected, (state, action) => {
        state.registerLoading = false;
        state.error = action.payload as string;
      });

    // 登出
    builder
      .addCase(logoutAsync.fulfilled, (state) => {
        state.user = null;
        state.isAuthenticated = false;
        state.error = null;
      });

    // 获取当前用户
    builder
      .addCase(getCurrentUserAsync.pending, (state) => {
        state.loading = true;
      })
      .addCase(getCurrentUserAsync.fulfilled, (state, action) => {
        state.loading = false;
        state.user = action.payload;
        state.isAuthenticated = true;
        localStorage.setItem('user', JSON.stringify(action.payload));
      })
      .addCase(getCurrentUserAsync.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload as string;
        // 如果获取用户信息失败，可能token已过期
        state.isAuthenticated = false;
        state.user = null;
        authStorage.clearAuth();
      });

    // 修改密码
    builder
      .addCase(changePasswordAsync.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(changePasswordAsync.fulfilled, (state) => {
        state.loading = false;
        state.error = null;
      })
      .addCase(changePasswordAsync.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload as string;
      });
  },
});

// 导出actions
export const { clearError, updateUser, setAuthenticated, initializeAuth } = authSlice.actions;

// 选择器
export const selectAuth = (state: { auth: AuthState }) => state.auth;
export const selectUser = (state: { auth: AuthState }) => state.auth.user;
export const selectIsAuthenticated = (state: { auth: AuthState }) => state.auth.isAuthenticated;
export const selectAuthLoading = (state: { auth: AuthState }) => state.auth.loading;
export const selectAuthError = (state: { auth: AuthState }) => state.auth.error;
export const selectLoginLoading = (state: { auth: AuthState }) => state.auth.loginLoading;
export const selectRegisterLoading = (state: { auth: AuthState }) => state.auth.registerLoading;

// 角色检查选择器
export const selectIsAdmin = (state: { auth: AuthState }) => 
  state.auth.user?.role === UserRole.ADMIN;
export const selectIsCandidate = (state: { auth: AuthState }) => 
  state.auth.user?.role === UserRole.CANDIDATE;
export const selectIsEmployer = (state: { auth: AuthState }) => 
  state.auth.user?.role === UserRole.EMPLOYER;

export default authSlice.reducer;
