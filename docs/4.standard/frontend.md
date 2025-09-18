# Flexes 前端技术规范

## 📋 概述

本文档定义了 Flexes 远程工程招聘平台前端开发的技术规范和最佳实践，确保代码质量、可维护性和团队协作效率。

## 🏗️ 技术栈

### 核心技术
- **React**: 18.2.0 - 现代化前端框架
- **TypeScript**: 4.9.5 - 类型安全的JavaScript超集
- **Ant Design**: 5.12.8 - 企业级UI组件库
- **Redux Toolkit**: 1.9.7 - 状态管理
- **React Router**: 6.20.1 - 路由管理
- **React Query**: 3.39.3 - 数据获取和缓存
- **Styled Components**: 6.1.6 - CSS-in-JS样式方案

### 开发工具
- **Create React App**: 5.0.1 - 项目脚手架
- **ESLint**: 代码质量检查
- **Prettier**: 代码格式化
- **Husky**: Git hooks管理
- **lint-staged**: 提交前代码检查

## 📁 项目结构规范

```
src/
├── components/          # 通用组件
│   ├── common/         # 基础组件
│   │   ├── Button/
│   │   ├── Input/
│   │   └── Modal/
│   ├── business/       # 业务组件
│   │   ├── JobCard/
│   │   ├── UserProfile/
│   │   └── ApplicationForm/
│   └── layout/         # 布局组件
│       ├── Header/
│       ├── Sidebar/
│       └── Footer/
├── pages/              # 页面组件
│   ├── Home/
│   ├── Jobs/
│   ├── Profile/
│   └── Dashboard/
├── hooks/              # 自定义Hooks
│   ├── useAuth.ts
│   ├── useApi.ts
│   └── useLocalStorage.ts
├── services/           # API服务
│   ├── api.ts          # API配置
│   ├── auth.ts         # 认证服务
│   ├── jobs.ts         # 职位服务
│   └── users.ts        # 用户服务
├── store/              # Redux状态管理
│   ├── index.ts        # Store配置
│   └── slices/         # Redux Slices
│       ├── authSlice.ts
│       ├── jobsSlice.ts
│       └── uiSlice.ts
├── types/              # TypeScript类型定义
│   ├── api.ts          # API类型
│   ├── user.ts         # 用户类型
│   └── job.ts          # 职位类型
├── utils/              # 工具函数
│   ├── constants.ts    # 常量定义
│   ├── helpers.ts      # 辅助函数
│   └── validators.ts   # 验证函数
├── styles/             # 样式文件
│   ├── theme.ts        # 主题配置
│   ├── global.css      # 全局样式
│   └── variables.css   # CSS变量
├── assets/             # 静态资源
│   ├── images/
│   ├── icons/
│   └── fonts/
├── App.tsx             # 根组件
└── index.tsx           # 应用入口
```

## 🎯 编码规范

### 1. 命名规范

#### 文件命名
- **组件文件**: PascalCase，如 `UserProfile.tsx`
- **Hook文件**: camelCase，以use开头，如 `useAuth.ts`
- **工具文件**: camelCase，如 `apiHelpers.ts`
- **类型文件**: camelCase，如 `userTypes.ts`

#### 变量命名
```typescript
// ✅ 推荐
const userName = 'john';
const isLoading = false;
const userList = [];
const API_BASE_URL = 'https://api.example.com';

// ❌ 不推荐
const user_name = 'john';
const loading = false;
const users = [];
const apiBaseUrl = 'https://api.example.com';
```

#### 组件命名
```typescript
// ✅ 推荐 - PascalCase
const UserProfile: React.FC = () => {
  return <div>User Profile</div>;
};

// ✅ 推荐 - 具有描述性
const JobApplicationForm: React.FC = () => {
  return <form>Job Application</form>;
};

// ❌ 不推荐
const userprofile: React.FC = () => {
  return <div>User Profile</div>;
};
```

### 2. TypeScript 规范

#### 类型定义
```typescript
// ✅ 推荐 - 明确的接口定义
interface User {
  id: number;
  name: string;
  email: string;
  role: 'candidate' | 'employer' | 'admin';
  createdAt: Date;
}

// ✅ 推荐 - 泛型使用
interface ApiResponse<T> {
  data: T;
  message: string;
  success: boolean;
}

// ✅ 推荐 - 联合类型
type LoadingState = 'idle' | 'loading' | 'success' | 'error';
```

#### 组件Props类型
```typescript
// ✅ 推荐 - 明确的Props接口
interface UserCardProps {
  user: User;
  onEdit?: (user: User) => void;
  onDelete?: (id: number) => void;
  className?: string;
}

const UserCard: React.FC<UserCardProps> = ({
  user,
  onEdit,
  onDelete,
  className
}) => {
  // 组件实现
};
```

### 3. React 组件规范

#### 函数组件结构
```typescript
// ✅ 推荐的组件结构
import React, { useState, useEffect } from 'react';
import { Button, Card } from 'antd';
import { useAppSelector, useAppDispatch } from '@/store';
import { User } from '@/types/user';
import styles from './UserProfile.module.css';

interface UserProfileProps {
  userId: number;
  onUpdate?: (user: User) => void;
}

const UserProfile: React.FC<UserProfileProps> = ({ userId, onUpdate }) => {
  // 1. Hooks
  const [loading, setLoading] = useState(false);
  const dispatch = useAppDispatch();
  const user = useAppSelector(state => state.auth.user);

  // 2. Effects
  useEffect(() => {
    // 副作用逻辑
  }, [userId]);

  // 3. Event handlers
  const handleUpdate = () => {
    // 处理更新逻辑
  };

  // 4. Render helpers
  const renderUserInfo = () => {
    return <div>User Info</div>;
  };

  // 5. Main render
  return (
    <Card className={styles.container}>
      {renderUserInfo()}
      <Button onClick={handleUpdate}>Update</Button>
    </Card>
  );
};

export default UserProfile;
```

#### 自定义Hooks
```typescript
// ✅ 推荐的Hook结构
import { useState, useEffect } from 'react';
import { User } from '@/types/user';
import { userService } from '@/services/users';

interface UseUserReturn {
  user: User | null;
  loading: boolean;
  error: string | null;
  refetch: () => void;
}

export const useUser = (userId: number): UseUserReturn => {
  const [user, setUser] = useState<User | null>(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const fetchUser = async () => {
    try {
      setLoading(true);
      setError(null);
      const userData = await userService.getUser(userId);
      setUser(userData);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Unknown error');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (userId) {
      fetchUser();
    }
  }, [userId]);

  return {
    user,
    loading,
    error,
    refetch: fetchUser,
  };
};
```

### 4. 状态管理规范

#### Redux Slice 结构
```typescript
// ✅ 推荐的Slice结构
import { createSlice, createAsyncThunk, PayloadAction } from '@reduxjs/toolkit';
import { User } from '@/types/user';
import { authService } from '@/services/auth';

interface AuthState {
  user: User | null;
  token: string | null;
  loading: boolean;
  error: string | null;
}

const initialState: AuthState = {
  user: null,
  token: null,
  loading: false,
  error: null,
};

// Async thunks
export const loginUser = createAsyncThunk(
  'auth/loginUser',
  async (credentials: LoginCredentials) => {
    const response = await authService.login(credentials);
    return response.data;
  }
);

const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    logout: (state) => {
      state.user = null;
      state.token = null;
    },
    clearError: (state) => {
      state.error = null;
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(loginUser.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(loginUser.fulfilled, (state, action) => {
        state.loading = false;
        state.user = action.payload.user;
        state.token = action.payload.token;
      })
      .addCase(loginUser.rejected, (state, action) => {
        state.loading = false;
        state.error = action.error.message || 'Login failed';
      });
  },
});

export const { logout, clearError } = authSlice.actions;
export default authSlice.reducer;
```

## 🎨 样式规范

### 1. CSS Modules
```typescript
// UserProfile.module.css
.container {
  padding: 24px;
  background: #fff;
  border-radius: 8px;
}

.title {
  font-size: 24px;
  font-weight: 600;
  margin-bottom: 16px;
}

.button {
  margin-top: 16px;
}

// UserProfile.tsx
import styles from './UserProfile.module.css';

const UserProfile: React.FC = () => {
  return (
    <div className={styles.container}>
      <h1 className={styles.title}>User Profile</h1>
      <button className={styles.button}>Edit</button>
    </div>
  );
};
```

### 2. Styled Components
```typescript
// ✅ 推荐的Styled Components使用
import styled from 'styled-components';

const Container = styled.div`
  padding: 24px;
  background: ${props => props.theme.colors.background};
  border-radius: 8px;
  
  @media (max-width: 768px) {
    padding: 16px;
  }
`;

const Title = styled.h1`
  font-size: 24px;
  font-weight: 600;
  color: ${props => props.theme.colors.text};
  margin-bottom: 16px;
`;

const UserProfile: React.FC = () => {
  return (
    <Container>
      <Title>User Profile</Title>
    </Container>
  );
};
```

### 3. Ant Design 主题定制
```typescript
// theme.ts
import { ThemeConfig } from 'antd';

export const lightTheme: ThemeConfig = {
  token: {
    colorPrimary: '#1890ff',
    colorSuccess: '#52c41a',
    colorWarning: '#faad14',
    colorError: '#ff4d4f',
    borderRadius: 6,
    fontSize: 14,
  },
  components: {
    Button: {
      borderRadius: 6,
      controlHeight: 40,
    },
    Input: {
      borderRadius: 6,
      controlHeight: 40,
    },
  },
};

export const darkTheme: ThemeConfig = {
  ...lightTheme,
  token: {
    ...lightTheme.token,
    colorBgContainer: '#141414',
    colorText: '#ffffff',
  },
};
```

## 🔧 工具配置

### 1. ESLint 配置
```javascript
// .eslintrc.js
module.exports = {
  env: {
    browser: true,
    es2021: true,
    node: true,
    jest: true,
  },
  extends: [
    'react-app',
    'react-app/jest',
    '@typescript-eslint/recommended',
    'prettier',
  ],
  parser: '@typescript-eslint/parser',
  parserOptions: {
    ecmaFeatures: {
      jsx: true,
    },
    ecmaVersion: 12,
    sourceType: 'module',
  },
  plugins: [
    'react',
    '@typescript-eslint',
    'react-hooks',
  ],
  rules: {
    // React规则
    'react/jsx-uses-react': 'off',
    'react/react-in-jsx-scope': 'off',
    'react-hooks/rules-of-hooks': 'error',
    'react-hooks/exhaustive-deps': 'warn',
    
    // TypeScript规则
    '@typescript-eslint/no-unused-vars': 'error',
    '@typescript-eslint/explicit-function-return-type': 'off',
    '@typescript-eslint/explicit-module-boundary-types': 'off',
    '@typescript-eslint/no-explicit-any': 'warn',
    
    // 通用规则
    'no-console': process.env.NODE_ENV === 'production' ? 'error' : 'warn',
    'no-debugger': process.env.NODE_ENV === 'production' ? 'error' : 'warn',
    'prefer-const': 'error',
    'no-var': 'error',
  },
};
```

### 2. Prettier 配置
```json
// .prettierrc
{
  "semi": true,
  "trailingComma": "es5",
  "singleQuote": true,
  "printWidth": 80,
  "tabWidth": 2,
  "useTabs": false,
  "bracketSpacing": true,
  "bracketSameLine": false,
  "arrowParens": "avoid",
  "endOfLine": "lf"
}
```

### 3. TypeScript 配置
```json
// tsconfig.json
{
  "compilerOptions": {
    "target": "es5",
    "lib": ["dom", "dom.iterable", "es6"],
    "allowJs": true,
    "skipLibCheck": true,
    "esModuleInterop": true,
    "allowSyntheticDefaultImports": true,
    "strict": true,
    "forceConsistentCasingInFileNames": true,
    "noFallthroughCasesInSwitch": true,
    "module": "esnext",
    "moduleResolution": "node",
    "resolveJsonModule": true,
    "isolatedModules": true,
    "noEmit": true,
    "jsx": "react-jsx",
    "baseUrl": "src",
    "paths": {
      "@/*": ["*"],
      "@/components/*": ["components/*"],
      "@/pages/*": ["pages/*"],
      "@/hooks/*": ["hooks/*"],
      "@/services/*": ["services/*"],
      "@/store/*": ["store/*"],
      "@/utils/*": ["utils/*"],
      "@/types/*": ["types/*"],
      "@/styles/*": ["styles/*"],
      "@/assets/*": ["assets/*"]
    }
  },
  "include": ["src"],
  "exclude": ["node_modules", "build"]
}
```

## 🧪 测试规范

### 1. 单元测试
```typescript
// UserProfile.test.tsx
import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { Provider } from 'react-redux';
import { BrowserRouter } from 'react-router-dom';
import { store } from '@/store';
import UserProfile from './UserProfile';

// 测试工具函数
const renderWithProviders = (component: React.ReactElement) => {
  return render(
    <Provider store={store}>
      <BrowserRouter>
        {component}
      </BrowserRouter>
    </Provider>
  );
};

describe('UserProfile', () => {
  const mockUser = {
    id: 1,
    name: 'John Doe',
    email: 'john@example.com',
    role: 'candidate' as const,
    createdAt: new Date(),
  };

  beforeEach(() => {
    // 重置mock
    jest.clearAllMocks();
  });

  it('should render user information correctly', () => {
    renderWithProviders(<UserProfile user={mockUser} />);

    expect(screen.getByText('John Doe')).toBeInTheDocument();
    expect(screen.getByText('john@example.com')).toBeInTheDocument();
  });

  it('should handle edit button click', async () => {
    const mockOnEdit = jest.fn();
    renderWithProviders(
      <UserProfile user={mockUser} onEdit={mockOnEdit} />
    );

    const editButton = screen.getByRole('button', { name: /edit/i });
    fireEvent.click(editButton);

    await waitFor(() => {
      expect(mockOnEdit).toHaveBeenCalledWith(mockUser);
    });
  });
});
```

### 2. Hook测试
```typescript
// useAuth.test.ts
import { renderHook, act } from '@testing-library/react';
import { useAuth } from './useAuth';

// Mock API
jest.mock('@/services/auth', () => ({
  authService: {
    login: jest.fn(),
    logout: jest.fn(),
  },
}));

describe('useAuth', () => {
  it('should initialize with default state', () => {
    const { result } = renderHook(() => useAuth());

    expect(result.current.user).toBeNull();
    expect(result.current.loading).toBe(false);
    expect(result.current.error).toBeNull();
  });

  it('should handle login successfully', async () => {
    const mockUser = { id: 1, name: 'John Doe' };
    const mockLogin = jest.fn().mockResolvedValue({ user: mockUser });

    const { result } = renderHook(() => useAuth());

    await act(async () => {
      await result.current.login('john@example.com', 'password');
    });

    expect(result.current.user).toEqual(mockUser);
    expect(result.current.loading).toBe(false);
  });
});
```

## 📦 API 集成规范

### 1. API Service 结构
```typescript
// services/api.ts
import axios, { AxiosInstance, AxiosRequestConfig } from 'axios';

class ApiService {
  private client: AxiosInstance;

  constructor() {
    this.client = axios.create({
      baseURL: process.env.REACT_APP_API_BASE_URL,
      timeout: 10000,
    });

    this.setupInterceptors();
  }

  private setupInterceptors() {
    // 请求拦截器
    this.client.interceptors.request.use(
      (config) => {
        const token = localStorage.getItem('token');
        if (token) {
          config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
      },
      (error) => Promise.reject(error)
    );

    // 响应拦截器
    this.client.interceptors.response.use(
      (response) => response.data,
      (error) => {
        if (error.response?.status === 401) {
          // 处理未授权
          localStorage.removeItem('token');
          window.location.href = '/login';
        }
        return Promise.reject(error);
      }
    );
  }

  async get<T>(url: string, config?: AxiosRequestConfig): Promise<T> {
    return this.client.get(url, config);
  }

  async post<T>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T> {
    return this.client.post(url, data, config);
  }

  async put<T>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T> {
    return this.client.put(url, data, config);
  }

  async delete<T>(url: string, config?: AxiosRequestConfig): Promise<T> {
    return this.client.delete(url, config);
  }
}

export const apiService = new ApiService();
```

### 2. 具体服务实现
```typescript
// services/users.ts
import { apiService } from './api';
import { User, CreateUserRequest, UpdateUserRequest } from '@/types/user';
import { ApiResponse, PaginatedResponse } from '@/types/api';

export class UserService {
  async getUsers(params?: {
    page?: number;
    limit?: number;
    search?: string;
  }): Promise<PaginatedResponse<User>> {
    return apiService.get('/users', { params });
  }

  async getUser(id: number): Promise<ApiResponse<User>> {
    return apiService.get(`/users/${id}`);
  }

  async createUser(data: CreateUserRequest): Promise<ApiResponse<User>> {
    return apiService.post('/users', data);
  }

  async updateUser(id: number, data: UpdateUserRequest): Promise<ApiResponse<User>> {
    return apiService.put(`/users/${id}`, data);
  }

  async deleteUser(id: number): Promise<ApiResponse<void>> {
    return apiService.delete(`/users/${id}`);
  }
}

export const userService = new UserService();
```

### 3. React Query 集成
```typescript
// hooks/useUsers.ts
import { useQuery, useMutation, useQueryClient } from 'react-query';
import { userService } from '@/services/users';
import { User, CreateUserRequest } from '@/types/user';

export const useUsers = (params?: {
  page?: number;
  limit?: number;
  search?: string;
}) => {
  return useQuery(
    ['users', params],
    () => userService.getUsers(params),
    {
      keepPreviousData: true,
      staleTime: 5 * 60 * 1000, // 5分钟
    }
  );
};

export const useUser = (id: number) => {
  return useQuery(
    ['user', id],
    () => userService.getUser(id),
    {
      enabled: !!id,
    }
  );
};

export const useCreateUser = () => {
  const queryClient = useQueryClient();

  return useMutation(
    (data: CreateUserRequest) => userService.createUser(data),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['users']);
      },
    }
  );
};
```

## 🚀 性能优化规范

### 1. 代码分割
```typescript
// 路由级别的代码分割
import { lazy, Suspense } from 'react';
import { Routes, Route } from 'react-router-dom';
import LoadingSpinner from '@/components/common/LoadingSpinner';

const Home = lazy(() => import('@/pages/Home'));
const Jobs = lazy(() => import('@/pages/Jobs'));
const Profile = lazy(() => import('@/pages/Profile'));

const AppRoutes: React.FC = () => {
  return (
    <Suspense fallback={<LoadingSpinner />}>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/jobs" element={<Jobs />} />
        <Route path="/profile" element={<Profile />} />
      </Routes>
    </Suspense>
  );
};
```

### 2. 组件优化
```typescript
// 使用React.memo优化组件
import React, { memo } from 'react';

interface UserCardProps {
  user: User;
  onEdit: (user: User) => void;
}

const UserCard: React.FC<UserCardProps> = memo(({ user, onEdit }) => {
  return (
    <div>
      <h3>{user.name}</h3>
      <button onClick={() => onEdit(user)}>Edit</button>
    </div>
  );
});

// 使用useMemo和useCallback优化
import { useMemo, useCallback } from 'react';

const UserList: React.FC<{ users: User[] }> = ({ users }) => {
  const sortedUsers = useMemo(() => {
    return users.sort((a, b) => a.name.localeCompare(b.name));
  }, [users]);

  const handleEdit = useCallback((user: User) => {
    // 编辑逻辑
  }, []);

  return (
    <div>
      {sortedUsers.map(user => (
        <UserCard key={user.id} user={user} onEdit={handleEdit} />
      ))}
    </div>
  );
};
```

### 3. 图片优化
```typescript
// 图片懒加载组件
import React, { useState, useRef, useEffect } from 'react';

interface LazyImageProps {
  src: string;
  alt: string;
  placeholder?: string;
  className?: string;
}

const LazyImage: React.FC<LazyImageProps> = ({
  src,
  alt,
  placeholder = '/images/placeholder.png',
  className
}) => {
  const [loaded, setLoaded] = useState(false);
  const [inView, setInView] = useState(false);
  const imgRef = useRef<HTMLImageElement>(null);

  useEffect(() => {
    const observer = new IntersectionObserver(
      ([entry]) => {
        if (entry.isIntersecting) {
          setInView(true);
          observer.disconnect();
        }
      },
      { threshold: 0.1 }
    );

    if (imgRef.current) {
      observer.observe(imgRef.current);
    }

    return () => observer.disconnect();
  }, []);

  return (
    <img
      ref={imgRef}
      src={inView ? src : placeholder}
      alt={alt}
      className={className}
      onLoad={() => setLoaded(true)}
      style={{
        opacity: loaded ? 1 : 0.5,
        transition: 'opacity 0.3s ease',
      }}
    />
  );
};
```

## 🔒 安全规范

### 1. XSS 防护
```typescript
// 安全的HTML渲染
import DOMPurify from 'dompurify';

interface SafeHtmlProps {
  html: string;
  className?: string;
}

const SafeHtml: React.FC<SafeHtmlProps> = ({ html, className }) => {
  const sanitizedHtml = DOMPurify.sanitize(html);

  return (
    <div
      className={className}
      dangerouslySetInnerHTML={{ __html: sanitizedHtml }}
    />
  );
};
```

### 2. 敏感信息处理
```typescript
// 环境变量使用
const config = {
  apiBaseUrl: process.env.REACT_APP_API_BASE_URL!,
  enableDevtools: process.env.NODE_ENV === 'development',
  // 不要在前端存储敏感信息
};

// Token安全存储
class TokenManager {
  private static readonly TOKEN_KEY = 'auth_token';

  static setToken(token: string): void {
    // 使用httpOnly cookie更安全，但这里演示localStorage
    localStorage.setItem(this.TOKEN_KEY, token);
  }

  static getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  static removeToken(): void {
    localStorage.removeItem(this.TOKEN_KEY);
  }
}
```

## 📱 响应式设计规范

### 1. 断点定义
```typescript
// styles/breakpoints.ts
export const breakpoints = {
  xs: '480px',
  sm: '576px',
  md: '768px',
  lg: '992px',
  xl: '1200px',
  xxl: '1600px',
};

export const mediaQueries = {
  xs: `@media (max-width: ${breakpoints.xs})`,
  sm: `@media (max-width: ${breakpoints.sm})`,
  md: `@media (max-width: ${breakpoints.md})`,
  lg: `@media (max-width: ${breakpoints.lg})`,
  xl: `@media (max-width: ${breakpoints.xl})`,
  xxl: `@media (max-width: ${breakpoints.xxl})`,
};
```

### 2. 响应式组件
```typescript
// 响应式Hook
import { useState, useEffect } from 'react';

export const useBreakpoint = () => {
  const [breakpoint, setBreakpoint] = useState('lg');

  useEffect(() => {
    const handleResize = () => {
      const width = window.innerWidth;
      if (width < 576) setBreakpoint('xs');
      else if (width < 768) setBreakpoint('sm');
      else if (width < 992) setBreakpoint('md');
      else if (width < 1200) setBreakpoint('lg');
      else setBreakpoint('xl');
    };

    handleResize();
    window.addEventListener('resize', handleResize);
    return () => window.removeEventListener('resize', handleResize);
  }, []);

  return breakpoint;
};

// 响应式组件使用
const ResponsiveComponent: React.FC = () => {
  const breakpoint = useBreakpoint();
  const isMobile = ['xs', 'sm'].includes(breakpoint);

  return (
    <div>
      {isMobile ? <MobileView /> : <DesktopView />}
    </div>
  );
};
```

## 🌐 国际化规范

### 1. i18n 配置
```typescript
// i18n/index.ts
import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';
import zhCN from './locales/zh-CN.json';
import enUS from './locales/en-US.json';

i18n
  .use(initReactI18next)
  .init({
    resources: {
      'zh-CN': { translation: zhCN },
      'en-US': { translation: enUS },
    },
    lng: 'zh-CN',
    fallbackLng: 'zh-CN',
    interpolation: {
      escapeValue: false,
    },
  });

export default i18n;
```

### 2. 翻译使用
```typescript
// 组件中使用翻译
import { useTranslation } from 'react-i18next';

const LoginForm: React.FC = () => {
  const { t } = useTranslation();

  return (
    <form>
      <h1>{t('login.title')}</h1>
      <input placeholder={t('login.email')} />
      <input placeholder={t('login.password')} />
      <button>{t('login.submit')}</button>
    </form>
  );
};
```

## 📋 代码审查清单

### 提交前检查
- [ ] 代码通过ESLint检查
- [ ] 代码通过Prettier格式化
- [ ] TypeScript类型检查通过
- [ ] 单元测试覆盖率达标
- [ ] 组件Props有明确类型定义
- [ ] 使用了适当的React Hooks
- [ ] 避免了不必要的重渲染
- [ ] 处理了错误边界情况
- [ ] 遵循了命名规范
- [ ] 添加了必要的注释

### 性能检查
- [ ] 使用了代码分割
- [ ] 图片进行了优化
- [ ] 避免了内存泄漏
- [ ] 使用了适当的缓存策略
- [ ] 组件使用了memo优化
- [ ] 避免了不必要的API调用

## 📚 参考资源

- [React 官方文档](https://reactjs.org/docs)
- [TypeScript 官方文档](https://www.typescriptlang.org/docs)
- [Ant Design 官方文档](https://ant.design/docs/react/introduce-cn)
- [Redux Toolkit 官方文档](https://redux-toolkit.js.org/)
- [React Query 官方文档](https://react-query.tanstack.com/)
- [React Testing Library 官方文档](https://testing-library.com/docs/react-testing-library/intro)

---

**版本**: 1.0.0
**最后更新**: 2025-01-18
**维护者**: Flexes 前端团队
```
