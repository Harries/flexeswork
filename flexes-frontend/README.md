# Flexes Frontend

Flexes 远程工程招聘平台前端应用，基于 React + TypeScript + Ant Design 构建。

## 📋 项目概述

- **项目名称**: Flexes Frontend
- **技术栈**: React 18 + TypeScript + Ant Design + Redux Toolkit
- **构建工具**: Create React App
- **包管理器**: npm/yarn
- **开发环境**: Node.js 16+

## 🚀 快速开始

### 环境要求

- Node.js >= 16.0.0
- npm >= 8.0.0 或 yarn >= 1.22.0
- Git

### 安装依赖

```bash
# 克隆项目（如果需要）
git clone <repository-url>
cd flexes-frontend

# 安装依赖
npm install
# 或
yarn install
```

### 启动开发服务器

```bash
# 启动开发服务器
npm start
# 或
yarn start
```

应用将在 http://localhost:3000 启动，支持热重载。

### 构建生产版本

```bash
# 构建生产版本
npm run build
# 或
yarn build
```

构建文件将输出到 `build/` 目录。

## 📁 项目结构

```
flexes-frontend/
├── public/                 # 静态资源
│   ├── index.html         # HTML 模板
│   ├── manifest.json      # PWA 配置
│   └── robots.txt         # 搜索引擎配置
├── src/
│   ├── components/        # 通用组件
│   │   ├── Layout/        # 布局组件
│   │   ├── ProtectedRoute/ # 路由保护
│   │   └── LoadingSpinner/ # 加载组件
│   ├── pages/             # 页面组件
│   │   ├── Home/          # 首页
│   │   ├── Auth/          # 认证页面
│   │   ├── Jobs/          # 职位相关页面
│   │   ├── Candidates/    # 候选人页面
│   │   ├── Companies/     # 公司页面
│   │   ├── Dashboard/     # 仪表板
│   │   └── Profile/       # 个人资料
│   ├── services/          # API 服务
│   │   ├── api.ts         # API 客户端配置
│   │   ├── auth.ts        # 认证服务
│   │   └── jobs.ts        # 职位服务
│   ├── store/             # Redux 状态管理
│   │   ├── index.ts       # Store 配置
│   │   └── slices/        # Redux Slices
│   ├── hooks/             # 自定义 Hooks
│   ├── utils/             # 工具函数
│   ├── types/             # TypeScript 类型定义
│   ├── styles/            # 样式文件
│   │   ├── theme.ts       # 主题配置
│   │   └── global.css     # 全局样式
│   ├── App.tsx            # 根组件
│   └── index.tsx          # 应用入口
├── .env                   # 环境变量
├── .env.development       # 开发环境变量
├── .env.production        # 生产环境变量
├── .eslintrc.js          # ESLint 配置
├── .prettierrc           # Prettier 配置
├── tsconfig.json         # TypeScript 配置
└── package.json          # 项目配置
```

## 🛠️ 开发工具

### 可用脚本

```bash
# 启动开发服务器
npm start

# 构建生产版本
npm run build

# 运行测试
npm test

# 代码检查
npm run lint

# 代码格式化
npm run format

# 类型检查
npm run type-check

# 修复代码问题
npm run lint:fix
```

### 代码规范

项目使用以下工具确保代码质量：

- **ESLint**: JavaScript/TypeScript 代码检查
- **Prettier**: 代码格式化
- **Husky**: Git hooks 管理
- **lint-staged**: 提交前代码检查

### Git 提交规范

```bash
# 提交前会自动运行代码检查和格式化
git add .
git commit -m "feat: 添加新功能"
git push
```

## 🔧 配置说明

### 环境变量

项目支持多环境配置，通过 `.env` 文件管理：

```bash
# 应用配置
REACT_APP_NAME=Flexes
REACT_APP_VERSION=1.0.0

# API 配置
REACT_APP_API_BASE_URL=http://localhost:8080/api
REACT_APP_API_TIMEOUT=10000

# 功能开关
REACT_APP_ENABLE_MOCK=false
REACT_APP_ENABLE_DEVTOOLS=true

# 业务配置
REACT_APP_DAILY_APPLICATION_LIMIT=20
```

### 代理配置

开发环境下，API 请求会自动代理到后端服务器：

```json
{
  "proxy": "http://localhost:8080"
}
```

### 路径别名

项目配置了路径别名，可以使用 `@/` 前缀导入模块：

```typescript
import { Button } from '@/components/Button';
import { authService } from '@/services/auth';
import { User } from '@/types';
```

## 🎨 UI 组件库

项目使用 Ant Design 作为 UI 组件库：

- **版本**: 5.x
- **主题**: 支持亮色/暗色主题切换
- **国际化**: 支持中文/英文
- **自定义**: 可通过 theme.ts 自定义主题

### 主题配置

```typescript
// src/styles/theme.ts
export const lightTheme = {
  colors: {
    primary: '#1890ff',
    secondary: '#722ed1',
    // ...
  }
};
```

## 📡 API 集成

### API 客户端

项目使用 Axios 作为 HTTP 客户端，配置了：

- 请求/响应拦截器
- 自动 Token 处理
- 错误处理
- 请求重试

### 服务层

API 服务按功能模块组织：

```typescript
// 认证服务
import { authService } from '@/services/auth';
await authService.login(credentials);

// 职位服务
import { jobService } from '@/services/jobs';
await jobService.getJobs(params);
```

## 🗄️ 状态管理

项目使用 Redux Toolkit 进行状态管理：

### Store 结构

```typescript
{
  auth: {
    user: User | null,
    isAuthenticated: boolean,
    loading: boolean
  },
  job: {
    jobs: Job[],
    currentJob: Job | null,
    loading: boolean
  },
  ui: {
    theme: 'light' | 'dark',
    sidebarCollapsed: boolean
  }
}
```

### 使用示例

```typescript
import { useAppSelector, useAppDispatch } from '@/store';
import { loginAsync } from '@/store/slices/authSlice';

const LoginComponent = () => {
  const dispatch = useAppDispatch();
  const { loading } = useAppSelector(state => state.auth);
  
  const handleLogin = (credentials) => {
    dispatch(loginAsync(credentials));
  };
};
```

## 🧪 测试

### 测试框架

- **Jest**: 测试运行器
- **React Testing Library**: React 组件测试
- **Cypress**: 端到端测试（计划中）

### 运行测试

```bash
# 运行所有测试
npm test

# 运行测试并生成覆盖率报告
npm test -- --coverage

# 监听模式运行测试
npm test -- --watch
```

## 📦 构建和部署

### 构建优化

生产构建包含以下优化：

- 代码分割和懒加载
- Tree shaking
- 资源压缩
- 缓存优化

### 部署选项

#### 1. 静态部署

```bash
# 构建
npm run build

# 部署到静态服务器
# 将 build/ 目录内容上传到服务器
```

#### 2. Docker 部署

```dockerfile
# Dockerfile
FROM node:16-alpine as builder
WORKDIR /app
COPY package*.json ./
RUN npm ci --only=production
COPY . .
RUN npm run build

FROM nginx:alpine
COPY --from=builder /app/build /usr/share/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
```

#### 3. Vercel/Netlify 部署

项目支持一键部署到 Vercel 或 Netlify：

```bash
# Vercel
npx vercel

# Netlify
npx netlify deploy --prod --dir=build
```

## 🔍 调试

### 开发工具

- **React Developer Tools**: React 组件调试
- **Redux DevTools**: Redux 状态调试
- **React Query DevTools**: 数据获取调试

### 日志配置

```typescript
// 开发环境启用详细日志
if (process.env.NODE_ENV === 'development') {
  console.log('API Request:', config);
}
```

## 🚨 常见问题

### 1. 端口冲突

如果 3000 端口被占用：

```bash
# 指定其他端口启动
PORT=3001 npm start
```

### 2. 依赖安装失败

```bash
# 清除缓存重新安装
npm cache clean --force
rm -rf node_modules package-lock.json
npm install
```

### 3. 类型错误

```bash
# 运行类型检查
npm run type-check

# 重新生成类型定义
npm run build
```

### 4. 代理问题

如果 API 代理不工作，检查 `package.json` 中的 proxy 配置。

## 📚 学习资源

- [React 官方文档](https://reactjs.org/)
- [TypeScript 官方文档](https://www.typescriptlang.org/)
- [Ant Design 官方文档](https://ant.design/)
- [Redux Toolkit 官方文档](https://redux-toolkit.js.org/)

## 🤝 贡献指南

1. Fork 项目
2. 创建功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 创建 Pull Request

## 📄 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

## 📞 支持

如果遇到问题或需要帮助：

- 📧 邮箱: support@flexes.work
- 🐛 问题反馈: [GitHub Issues](https://github.com/flexes/flexes-frontend/issues)
- 📖 文档: [项目文档](https://docs.flexes.work)

---

**开发团队**: Flexes Team  
**最后更新**: 2025-09-17
