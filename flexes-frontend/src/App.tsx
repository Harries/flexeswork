import React, { useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { Provider } from 'react-redux';
import { QueryClient, QueryClientProvider } from 'react-query';
import { ReactQueryDevtools } from 'react-query/devtools';
import { ConfigProvider, App as AntdApp } from 'antd';
import zhCN from 'antd/locale/zh_CN';
import enUS from 'antd/locale/en_US';
import { ThemeProvider } from 'styled-components';

// Store
import { store } from './store';
import { useAppDispatch, useAppSelector } from './store';
import { initializeAuth } from './store/slices/authSlice';
import { selectTheme, selectLocale } from './store/slices/uiSlice';

// Components
import Layout from './components/Layout';
import ProtectedRoute from './components/ProtectedRoute';


// Pages
// import HomePage from './pages/Home';
import NewHomePage from './pages/Home/NewHomePage';
import JobListPage from './pages/Jobs/JobList';
import JobDetailPage from './pages/Jobs/JobDetail';
import CandidateListPage from './pages/Candidates/CandidateList';
import CandidateDetailPage from './pages/Candidates/CandidateDetail';
import CompanyListPage from './pages/Companies/CompanyList';
import CompanyDetailPage from './pages/Companies/CompanyDetail';
import LoginPage from './pages/Auth/Login';
import RegisterPage from './pages/Auth/Register';
import DashboardPage from './pages/Dashboard';
import CandidateDashboard from './pages/Dashboard/Candidate';
import EmployerDashboard from './pages/Dashboard/Employer';
import AdminDashboard from './pages/Dashboard/Admin';
import ProfilePage from './pages/Profile';
import NotFoundPage from './pages/NotFound';

// Styles
import { lightTheme, darkTheme } from './styles/theme';
import './styles/global.css';

// Types
import { UserRole } from './types';

// 创建React Query客户端
const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      retry: 1,
      refetchOnWindowFocus: false,
      staleTime: 5 * 60 * 1000, // 5分钟
    },
  },
});

// 主应用组件
const AppContent: React.FC = () => {
  const dispatch = useAppDispatch();
  const theme = useAppSelector(selectTheme);
  const locale = useAppSelector(selectLocale);

  // 初始化认证状态
  useEffect(() => {
    dispatch(initializeAuth());
  }, [dispatch]);

  // Ant Design 主题配置
  const antdTheme = {
    token: {
      colorPrimary: '#1890ff',
      borderRadius: 6,
      colorBgContainer: theme === 'dark' ? '#141414' : '#ffffff',
    },
  };

  // Styled Components 主题
  const styledTheme = theme === 'dark' ? darkTheme : lightTheme;

  // Ant Design 国际化
  const antdLocale = locale === 'zh-CN' ? zhCN : enUS;

  return (
    <ConfigProvider theme={antdTheme} locale={antdLocale}>
      <ThemeProvider theme={styledTheme}>
        <AntdApp>
          <Router>
            <Routes>
              {/* 首页路由 - 不使用Layout */}
              <Route path="/" element={<NewHomePage />} />

              {/* 其他公开路由 - 使用Layout */}
              <Route path="/" element={<Layout />}>
                <Route path="jobs" element={<JobListPage />} />
                <Route path="jobs/:id" element={<JobDetailPage />} />
                <Route path="candidates" element={<CandidateListPage />} />
                <Route path="candidates/:id" element={<CandidateDetailPage />} />
                <Route path="companies" element={<CompanyListPage />} />
                <Route path="companies/:id" element={<CompanyDetailPage />} />
              </Route>

              {/* 认证路由 */}
              <Route path="/login" element={<LoginPage />} />
              <Route path="/register" element={<RegisterPage />} />

              {/* 受保护的路由 */}
              <Route
                path="/dashboard"
                element={
                  <ProtectedRoute>
                    <Layout />
                  </ProtectedRoute>
                }
              >
                <Route index element={<DashboardPage />} />
                <Route
                  path="candidate"
                  element={
                    <ProtectedRoute requiredRole={UserRole.CANDIDATE}>
                      <CandidateDashboard />
                    </ProtectedRoute>
                  }
                />
                <Route
                  path="employer"
                  element={
                    <ProtectedRoute requiredRole={UserRole.EMPLOYER}>
                      <EmployerDashboard />
                    </ProtectedRoute>
                  }
                />
                <Route
                  path="admin"
                  element={
                    <ProtectedRoute requiredRole={UserRole.ADMIN}>
                      <AdminDashboard />
                    </ProtectedRoute>
                  }
                />
              </Route>

              <Route
                path="/profile"
                element={
                  <ProtectedRoute>
                    <Layout>
                      <ProfilePage />
                    </Layout>
                  </ProtectedRoute>
                }
              />

              {/* 404页面 */}
              <Route path="/404" element={<NotFoundPage />} />
              <Route path="*" element={<Navigate to="/404" replace />} />
            </Routes>
          </Router>
        </AntdApp>
      </ThemeProvider>
    </ConfigProvider>
  );
};

// 根应用组件
const App: React.FC = () => {
  return (
    <Provider store={store}>
      <QueryClientProvider client={queryClient}>
        <AppContent />
        {process.env.NODE_ENV === 'development' && (
          <ReactQueryDevtools initialIsOpen={false} />
        )}
      </QueryClientProvider>
    </Provider>
  );
};

export default App;
