import React from 'react';
import { Typography } from 'antd';
import { useAppSelector } from '../../store';
import { selectUser } from '../../store/slices/authSlice';
import { UserRole } from '../../types';

const { Title } = Typography;

const DashboardPage: React.FC = () => {
  const user = useAppSelector(selectUser);

  const getDashboardTitle = () => {
    switch (user?.role) {
      case UserRole.ADMIN:
        return '管理员仪表板';
      case UserRole.EMPLOYER:
        return '雇主仪表板';
      case UserRole.CANDIDATE:
        return '求职者仪表板';
      default:
        return '仪表板';
    }
  };

  return (
    <div>
      <Title level={2}>{getDashboardTitle()}</Title>
      <p>仪表板功能开发中...</p>
    </div>
  );
};

export default DashboardPage;
