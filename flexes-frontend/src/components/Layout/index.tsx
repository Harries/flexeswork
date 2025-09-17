import React from 'react';
import { Outlet } from 'react-router-dom';
import { Layout as AntdLayout } from 'antd';
import Header from './Header';
import Footer from './Footer';
import Sidebar from './Sidebar';
import { useAppSelector } from '../../store';
import { selectSidebarCollapsed } from '../../store/slices/uiSlice';
import { selectIsAuthenticated } from '../../store/slices/authSlice';

const { Content } = AntdLayout;

interface LayoutProps {
  children?: React.ReactNode;
}

const Layout: React.FC<LayoutProps> = ({ children }) => {
  const isAuthenticated = useAppSelector(selectIsAuthenticated);
  const sidebarCollapsed = useAppSelector(selectSidebarCollapsed);

  return (
    <AntdLayout style={{ minHeight: '100vh' }}>
      <Header />
      <AntdLayout>
        {isAuthenticated && <Sidebar collapsed={sidebarCollapsed} />}
        <AntdLayout>
          <Content
            style={{
              padding: '24px',
              margin: 0,
              minHeight: 280,
              background: '#ffffff',
            }}
          >
            {children || <Outlet />}
          </Content>
        </AntdLayout>
      </AntdLayout>
      <Footer />
    </AntdLayout>
  );
};

export default Layout;
