import React from 'react';
import { Layout, Row, Col, Space, Divider } from 'antd';
import { Link } from 'react-router-dom';
import {
  GithubOutlined,
  TwitterOutlined,
  LinkedinOutlined,
  MailOutlined,
} from '@ant-design/icons';

const { Footer: AntdFooter } = Layout;

const Footer: React.FC = () => {
  const currentYear = new Date().getFullYear();

  return (
    <AntdFooter
      style={{
        background: '#001529',
        color: '#ffffff',
        padding: '48px 24px 24px',
      }}
    >
      <div style={{ maxWidth: 1200, margin: '0 auto' }}>
        <Row gutter={[48, 32]}>
          {/* 公司信息 */}
          <Col xs={24} sm={12} md={6}>
            <div style={{ marginBottom: 16 }}>
              <div
                style={{
                  display: 'flex',
                  alignItems: 'center',
                  marginBottom: 16,
                }}
              >
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
                <span style={{ fontSize: 20, fontWeight: 600 }}>Flexes</span>
              </div>
              <p style={{ color: '#a6a6a6', lineHeight: 1.6 }}>
                专业的远程工程招聘平台，连接优秀的工程师人才与远程工作机会。
              </p>
            </div>
            <Space size="middle">
              <GithubOutlined style={{ fontSize: 20, color: '#a6a6a6' }} />
              <TwitterOutlined style={{ fontSize: 20, color: '#a6a6a6' }} />
              <LinkedinOutlined style={{ fontSize: 20, color: '#a6a6a6' }} />
              <MailOutlined style={{ fontSize: 20, color: '#a6a6a6' }} />
            </Space>
          </Col>

          {/* 求职者 */}
          <Col xs={24} sm={12} md={6}>
            <h4 style={{ color: '#ffffff', marginBottom: 16 }}>求职者</h4>
            <div style={{ display: 'flex', flexDirection: 'column', gap: 8 }}>
              <Link to="/jobs" style={{ color: '#a6a6a6' }}>
                浏览职位
              </Link>
              <Link to="/companies" style={{ color: '#a6a6a6' }}>
                公司列表
              </Link>
              <Link to="/career-advice" style={{ color: '#a6a6a6' }}>
                职业建议
              </Link>
              <Link to="/resume-builder" style={{ color: '#a6a6a6' }}>
                简历制作
              </Link>
            </div>
          </Col>

          {/* 雇主 */}
          <Col xs={24} sm={12} md={6}>
            <h4 style={{ color: '#ffffff', marginBottom: 16 }}>雇主</h4>
            <div style={{ display: 'flex', flexDirection: 'column', gap: 8 }}>
              <Link to="/post-job" style={{ color: '#a6a6a6' }}>
                发布职位
              </Link>
              <Link to="/candidates" style={{ color: '#a6a6a6' }}>
                搜索人才
              </Link>
              <Link to="/pricing" style={{ color: '#a6a6a6' }}>
                定价方案
              </Link>
              <Link to="/employer-resources" style={{ color: '#a6a6a6' }}>
                雇主资源
              </Link>
            </div>
          </Col>

          {/* 支持 */}
          <Col xs={24} sm={12} md={6}>
            <h4 style={{ color: '#ffffff', marginBottom: 16 }}>支持</h4>
            <div style={{ display: 'flex', flexDirection: 'column', gap: 8 }}>
              <Link to="/help" style={{ color: '#a6a6a6' }}>
                帮助中心
              </Link>
              <Link to="/contact" style={{ color: '#a6a6a6' }}>
                联系我们
              </Link>
              <Link to="/about" style={{ color: '#a6a6a6' }}>
                关于我们
              </Link>
              <Link to="/blog" style={{ color: '#a6a6a6' }}>
                博客
              </Link>
            </div>
          </Col>
        </Row>

        <Divider style={{ borderColor: '#434343', margin: '32px 0 24px' }} />

        <Row justify="space-between" align="middle">
          <Col xs={24} md={12}>
            <p style={{ color: '#a6a6a6', margin: 0 }}>
              © {currentYear} Flexes. 保留所有权利。
            </p>
          </Col>
          <Col xs={24} md={12}>
            <div
              style={{
                display: 'flex',
                justifyContent: 'flex-end',
                gap: 24,
                marginTop: 16,
              }}
            >
              <Link to="/privacy" style={{ color: '#a6a6a6' }}>
                隐私政策
              </Link>
              <Link to="/terms" style={{ color: '#a6a6a6' }}>
                服务条款
              </Link>
              <Link to="/cookies" style={{ color: '#a6a6a6' }}>
                Cookie政策
              </Link>
            </div>
          </Col>
        </Row>
      </div>
    </AntdFooter>
  );
};

export default Footer;
