import React from 'react';
import { Spin } from 'antd';
import { LoadingOutlined } from '@ant-design/icons';

interface LoadingSpinnerProps {
  size?: 'small' | 'default' | 'large';
  tip?: string;
  spinning?: boolean;
  children?: React.ReactNode;
  style?: React.CSSProperties;
}

const LoadingSpinner: React.FC<LoadingSpinnerProps> = ({
  size = 'default',
  tip = '加载中...',
  spinning = true,
  children,
  style,
}) => {
  const antIcon = <LoadingOutlined style={{ fontSize: 24 }} spin />;

  if (children) {
    return (
      <Spin
        indicator={antIcon}
        size={size}
        tip={tip}
        spinning={spinning}
        style={style}
      >
        {children}
      </Spin>
    );
  }

  return (
    <div
      style={{
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        minHeight: '200px',
        ...style,
      }}
    >
      <Spin
        indicator={antIcon}
        size={size}
        tip={tip}
        spinning={spinning}
      />
    </div>
  );
};

export default LoadingSpinner;
