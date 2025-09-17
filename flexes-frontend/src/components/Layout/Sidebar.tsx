import React from 'react';
import { Layout, Menu } from 'antd';
import { useNavigate, useLocation } from 'react-router-dom';
import {
  DashboardOutlined,
  UserOutlined,
  ProjectOutlined,
  TeamOutlined,
  SettingOutlined,
  FileTextOutlined,
  MessageOutlined,
  HeartOutlined,
  BellOutlined,
} from '@ant-design/icons';
import type { MenuProps } from 'antd';
import { useAppSelector } from '../../store';
import { selectUser } from '../../store/slices/authSlice';
import { UserRole } from '../../types';

const { Sider } = Layout;

interface SidebarProps {
  collapsed: boolean;
}

const Sidebar: React.FC<SidebarProps> = ({ collapsed }) => {
  const navigate = useNavigate();
  const location = useLocation();
  const user = useAppSelector(selectUser);

  // 根据用户角色生成菜单项
  const getMenuItems = (): MenuProps['items'] => {
    const commonItems: MenuProps['items'] = [
      {
        key: '/dashboard',
        icon: <DashboardOutlined />,
        label: '仪表板',
        onClick: () => navigate('/dashboard'),
      },
    ];

    if (user?.role === UserRole.CANDIDATE) {
      return [
        ...commonItems,
        {
          key: '/dashboard/candidate',
          icon: <UserOutlined />,
          label: '求职者中心',
          onClick: () => navigate('/dashboard/candidate'),
        },
        {
          key: 'applications',
          icon: <FileTextOutlined />,
          label: '我的申请',
          children: [
            {
              key: '/applications',
              label: '申请记录',
              onClick: () => navigate('/applications'),
            },
            {
              key: '/applications/status',
              label: '申请状态',
              onClick: () => navigate('/applications/status'),
            },
          ],
        },
        {
          key: '/favorites',
          icon: <HeartOutlined />,
          label: '收藏职位',
          onClick: () => navigate('/favorites'),
        },
        {
          key: '/job-alerts',
          icon: <BellOutlined />,
          label: '职位提醒',
          onClick: () => navigate('/job-alerts'),
        },
        {
          key: '/messages',
          icon: <MessageOutlined />,
          label: '消息中心',
          onClick: () => navigate('/messages'),
        },
        {
          key: '/profile',
          icon: <SettingOutlined />,
          label: '个人设置',
          onClick: () => navigate('/profile'),
        },
      ];
    }

    if (user?.role === UserRole.EMPLOYER) {
      return [
        ...commonItems,
        {
          key: '/dashboard/employer',
          icon: <ProjectOutlined />,
          label: '雇主中心',
          onClick: () => navigate('/dashboard/employer'),
        },
        {
          key: 'jobs-management',
          icon: <ProjectOutlined />,
          label: '职位管理',
          children: [
            {
              key: '/jobs/my',
              label: '我的职位',
              onClick: () => navigate('/jobs/my'),
            },
            {
              key: '/jobs/post',
              label: '发布职位',
              onClick: () => navigate('/jobs/post'),
            },
            {
              key: '/jobs/analytics',
              label: '职位统计',
              onClick: () => navigate('/jobs/analytics'),
            },
          ],
        },
        {
          key: 'applications-management',
          icon: <FileTextOutlined />,
          label: '申请管理',
          children: [
            {
              key: '/applications/received',
              label: '收到的申请',
              onClick: () => navigate('/applications/received'),
            },
            {
              key: '/applications/shortlist',
              label: '候选人筛选',
              onClick: () => navigate('/applications/shortlist'),
            },
          ],
        },
        {
          key: '/candidates/search',
          icon: <TeamOutlined />,
          label: '人才搜索',
          onClick: () => navigate('/candidates/search'),
        },
        {
          key: '/company/profile',
          icon: <SettingOutlined />,
          label: '公司设置',
          onClick: () => navigate('/company/profile'),
        },
        {
          key: '/messages',
          icon: <MessageOutlined />,
          label: '消息中心',
          onClick: () => navigate('/messages'),
        },
      ];
    }

    if (user?.role === UserRole.ADMIN) {
      return [
        ...commonItems,
        {
          key: '/dashboard/admin',
          icon: <SettingOutlined />,
          label: '管理中心',
          onClick: () => navigate('/dashboard/admin'),
        },
        {
          key: 'user-management',
          icon: <UserOutlined />,
          label: '用户管理',
          children: [
            {
              key: '/admin/users',
              label: '所有用户',
              onClick: () => navigate('/admin/users'),
            },
            {
              key: '/admin/candidates',
              label: '求职者管理',
              onClick: () => navigate('/admin/candidates'),
            },
            {
              key: '/admin/employers',
              label: '雇主管理',
              onClick: () => navigate('/admin/employers'),
            },
          ],
        },
        {
          key: 'content-management',
          icon: <FileTextOutlined />,
          label: '内容管理',
          children: [
            {
              key: '/admin/jobs',
              label: '职位审核',
              onClick: () => navigate('/admin/jobs'),
            },
            {
              key: '/admin/articles',
              label: '文章管理',
              onClick: () => navigate('/admin/articles'),
            },
            {
              key: '/admin/categories',
              label: '分类管理',
              onClick: () => navigate('/admin/categories'),
            },
          ],
        },
        {
          key: '/admin/analytics',
          icon: <DashboardOutlined />,
          label: '数据分析',
          onClick: () => navigate('/admin/analytics'),
        },
        {
          key: '/admin/settings',
          icon: <SettingOutlined />,
          label: '系统设置',
          onClick: () => navigate('/admin/settings'),
        },
      ];
    }

    return commonItems;
  };

  // 获取当前选中的菜单项
  const getSelectedKeys = (): string[] => {
    const pathname = location.pathname;
    
    // 精确匹配
    if (pathname === '/dashboard') return ['/dashboard'];
    if (pathname === '/dashboard/candidate') return ['/dashboard/candidate'];
    if (pathname === '/dashboard/employer') return ['/dashboard/employer'];
    if (pathname === '/dashboard/admin') return ['/dashboard/admin'];
    
    // 模糊匹配
    if (pathname.startsWith('/applications')) return ['/applications'];
    if (pathname.startsWith('/jobs/my')) return ['/jobs/my'];
    if (pathname.startsWith('/jobs/post')) return ['/jobs/post'];
    if (pathname.startsWith('/admin/users')) return ['/admin/users'];
    
    return [pathname];
  };

  // 获取展开的菜单项
  const getOpenKeys = (): string[] => {
    const pathname = location.pathname;
    const openKeys: string[] = [];
    
    if (pathname.startsWith('/applications')) {
      openKeys.push('applications', 'applications-management');
    }
    if (pathname.startsWith('/jobs/')) {
      openKeys.push('jobs-management');
    }
    if (pathname.startsWith('/admin/users') || pathname.startsWith('/admin/candidates') || pathname.startsWith('/admin/employers')) {
      openKeys.push('user-management');
    }
    if (pathname.startsWith('/admin/jobs') || pathname.startsWith('/admin/articles') || pathname.startsWith('/admin/categories')) {
      openKeys.push('content-management');
    }
    
    return openKeys;
  };

  return (
    <Sider
      trigger={null}
      collapsible
      collapsed={collapsed}
      width={256}
      style={{
        background: '#ffffff',
        borderRight: '1px solid #f0f0f0',
      }}
    >
      <Menu
        mode="inline"
        selectedKeys={getSelectedKeys()}
        defaultOpenKeys={getOpenKeys()}
        items={getMenuItems()}
        style={{
          height: '100%',
          borderRight: 0,
        }}
      />
    </Sider>
  );
};

export default Sidebar;
