import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Layout, Menu, Button, Avatar, Dropdown, Space, Badge } from 'antd';
import {
  UserOutlined,
  BellOutlined,
  SettingOutlined,
  LogoutOutlined,
  MenuFoldOutlined,
  MenuUnfoldOutlined,
} from '@ant-design/icons';
import type { MenuProps } from 'antd';
import { useAppDispatch, useAppSelector } from '../../store';
import { logoutAsync, selectUser, selectIsAuthenticated } from '../../store/slices/authSlice';
import { toggleSidebar, selectSidebarCollapsed, selectUnreadCount } from '../../store/slices/uiSlice';
import { UserRole } from '../../types';

const { Header: AntdHeader } = Layout;

const Header: React.FC = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const user = useAppSelector(selectUser);
  const isAuthenticated = useAppSelector(selectIsAuthenticated);
  const sidebarCollapsed = useAppSelector(selectSidebarCollapsed);
  const unreadCount = useAppSelector(selectUnreadCount);

  // 处理登出
  const handleLogout = async () => {
    await dispatch(logoutAsync());
    navigate('/');
  };

  // 用户菜单
  const userMenuItems: MenuProps['items'] = [
    {
      key: 'profile',
      icon: <UserOutlined />,
      label: <Link to="/profile">个人资料</Link>,
    },
    {
      key: 'settings',
      icon: <SettingOutlined />,
      label: <Link to="/settings">设置</Link>,
    },
    {
      type: 'divider',
    },
    {
      key: 'logout',
      icon: <LogoutOutlined />,
      label: '退出登录',
      onClick: handleLogout,
    },
  ];

  // 主导航菜单
  const navMenuItems: MenuProps['items'] = [
    {
      key: 'home',
      label: <Link to="/">首页</Link>,
    },
    {
      key: 'jobs',
      label: <Link to="/jobs">职位</Link>,
    },
    {
      key: 'candidates',
      label: <Link to="/candidates">人才</Link>,
      style: { display: user?.role === UserRole.EMPLOYER ? 'block' : 'none' },
    },
    {
      key: 'companies',
      label: <Link to="/companies">公司</Link>,
    },
  ];

  return (
    <AntdHeader
      style={{
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'space-between',
        padding: '0 24px',
        background: '#ffffff',
        borderBottom: '1px solid #f0f0f0',
        position: 'sticky',
        top: 0,
        zIndex: 1000,
      }}
    >
      {/* 左侧 */}
      <div style={{ display: 'flex', alignItems: 'center' }}>
        {/* 侧边栏切换按钮 */}
        {isAuthenticated && (
          <Button
            type="text"
            icon={sidebarCollapsed ? <MenuUnfoldOutlined /> : <MenuFoldOutlined />}
            onClick={() => dispatch(toggleSidebar())}
            style={{ marginRight: 16 }}
          />
        )}

        {/* Logo */}
        <Link to="/" style={{ display: 'flex', alignItems: 'center', marginRight: 32 }}>
          <div
            style={{
              width: 32,
              height: 32,
              background: '#1890ff',
              borderRadius: 6,
              display: 'flex',
              alignItems: 'center',
              justifyContent: 'center',
              color: '#ffffff',
              fontWeight: 'bold',
              fontSize: 16,
              marginRight: 8,
            }}
          >
            F
          </div>
          <span style={{ fontSize: 20, fontWeight: 600, color: '#262626' }}>
            Flexes
          </span>
        </Link>

        {/* 主导航 */}
        <Menu
          mode="horizontal"
          items={navMenuItems}
          style={{
            border: 'none',
            background: 'transparent',
            minWidth: 300,
          }}
        />
      </div>

      {/* 右侧 */}
      <div style={{ display: 'flex', alignItems: 'center' }}>
        {isAuthenticated ? (
          <Space size="middle">
            {/* 通知 */}
            <Badge count={unreadCount} size="small">
              <Button
                type="text"
                icon={<BellOutlined />}
                onClick={() => navigate('/notifications')}
              />
            </Badge>

            {/* 用户菜单 */}
            <Dropdown menu={{ items: userMenuItems }} placement="bottomRight">
              <Space style={{ cursor: 'pointer' }}>
                <Avatar
                  size="small"
                  icon={<UserOutlined />}
                />
                <span>{user?.email}</span>
              </Space>
            </Dropdown>
          </Space>
        ) : (
          <Space>
            <Button type="text" onClick={() => navigate('/login')}>
              登录
            </Button>
            <Button type="primary" onClick={() => navigate('/register')}>
              注册
            </Button>
          </Space>
        )}
      </div>
    </AntdHeader>
  );
};

export default Header;
