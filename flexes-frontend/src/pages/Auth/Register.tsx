import React from 'react';
import { Card, Typography } from 'antd';

const { Title } = Typography;

const RegisterPage: React.FC = () => {
  return (
    <div style={{ minHeight: '100vh', display: 'flex', alignItems: 'center', justifyContent: 'center', background: '#f0f2f5' }}>
      <Card style={{ width: 400 }}>
        <Title level={2} style={{ textAlign: 'center' }}>注册页面</Title>
        <p style={{ textAlign: 'center', color: '#666' }}>注册功能开发中...</p>
      </Card>
    </div>
  );
};

export default RegisterPage;
