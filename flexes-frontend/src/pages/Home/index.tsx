import React, { useEffect } from 'react';
import { Row, Col, Card, Button, Input, Select, Typography, Space } from 'antd';
import { SearchOutlined, RocketOutlined, TeamOutlined, SafetyOutlined } from '@ant-design/icons';
import { useNavigate } from 'react-router-dom';
import { useAppDispatch, useAppSelector } from '../../store';
import {
  fetchHotJobsAsync,
  fetchLatestJobsAsync,
  fetchCategoriesAsync,
  selectHotJobs,
  selectLatestJobs,
  selectCategories,
} from '../../store/slices/jobSlice';
import { setPageTitle } from '../../store/slices/uiSlice';
import LoadingSpinner from '../../components/LoadingSpinner';


const { Title, Paragraph } = Typography;
const { Option } = Select;

const HomePage: React.FC = () => {
  const navigate = useNavigate();
  const dispatch = useAppDispatch();
  
  const hotJobs = useAppSelector(selectHotJobs);
  const latestJobs = useAppSelector(selectLatestJobs);
  const categories = useAppSelector(selectCategories);

  useEffect(() => {
    dispatch(setPageTitle('首页'));
    dispatch(fetchHotJobsAsync(6));
    dispatch(fetchLatestJobsAsync(6));
    dispatch(fetchCategoriesAsync());
  }, [dispatch]);

  const handleSearch = (values: any) => {
    const searchParams = new URLSearchParams();
    if (values.keyword) searchParams.set('keyword', values.keyword);
    if (values.location) searchParams.set('location', values.location);
    if (values.category) searchParams.set('category', values.category);
    
    navigate(`/jobs?${searchParams.toString()}`);
  };

  return (
    <div>
      {/* Hero Section */}
      <section
        style={{
          background: 'linear-gradient(135deg, #1890ff 0%, #722ed1 100%)',
          color: '#ffffff',
          padding: '80px 0',
          textAlign: 'center',
        }}
      >
        <div style={{ maxWidth: 1200, margin: '0 auto', padding: '0 24px' }}>
          <Title level={1} style={{ color: '#ffffff', fontSize: 48, marginBottom: 16 }}>
            找到你的理想远程工作
          </Title>
          <Paragraph style={{ color: '#ffffff', fontSize: 20, marginBottom: 40 }}>
            连接优秀的工程师人才与远程工作机会，开启你的远程职业生涯
          </Paragraph>

          {/* 搜索框 */}
          <Card
            style={{
              maxWidth: 800,
              margin: '0 auto',
              borderRadius: 12,
              boxShadow: '0 8px 32px rgba(0, 0, 0, 0.1)',
            }}
          >
            <Row gutter={16} align="middle">
              <Col xs={24} sm={8}>
                <Input
                  size="large"
                  placeholder="搜索职位关键词"
                  prefix={<SearchOutlined />}
                />
              </Col>
              <Col xs={24} sm={6}>
                <Input
                  size="large"
                  placeholder="工作地点"
                />
              </Col>
              <Col xs={24} sm={6}>
                <Select
                  size="large"
                  placeholder="职位分类"
                  style={{ width: '100%' }}
                >
                  {categories.map(category => (
                    <Option key={category.id} value={category.id}>
                      {category.name}
                    </Option>
                  ))}
                </Select>
              </Col>
              <Col xs={24} sm={4}>
                <Button
                  type="primary"
                  size="large"
                  block
                  icon={<SearchOutlined />}
                  onClick={handleSearch}
                >
                  搜索
                </Button>
              </Col>
            </Row>
          </Card>
        </div>
      </section>

      {/* 特色功能 */}
      <section style={{ padding: '80px 0', background: '#fafafa' }}>
        <div style={{ maxWidth: 1200, margin: '0 auto', padding: '0 24px' }}>
          <Title level={2} style={{ textAlign: 'center', marginBottom: 48 }}>
            为什么选择 Flexes？
          </Title>
          <Row gutter={[32, 32]}>
            <Col xs={24} md={8}>
              <Card
                style={{ textAlign: 'center', height: '100%' }}
                bodyStyle={{ padding: 32 }}
              >
                <RocketOutlined style={{ fontSize: 48, color: '#1890ff', marginBottom: 16 }} />
                <Title level={4}>优质职位</Title>
                <Paragraph>
                  精选优质远程工程职位，涵盖前端、后端、全栈、DevOps等多个技术领域
                </Paragraph>
              </Card>
            </Col>
            <Col xs={24} md={8}>
              <Card
                style={{ textAlign: 'center', height: '100%' }}
                bodyStyle={{ padding: 32 }}
              >
                <TeamOutlined style={{ fontSize: 48, color: '#52c41a', marginBottom: 16 }} />
                <Title level={4}>专业匹配</Title>
                <Paragraph>
                  智能匹配算法，根据技能和经验为你推荐最合适的工作机会
                </Paragraph>
              </Card>
            </Col>
            <Col xs={24} md={8}>
              <Card
                style={{ textAlign: 'center', height: '100%' }}
                bodyStyle={{ padding: 32 }}
              >
                <SafetyOutlined style={{ fontSize: 48, color: '#faad14', marginBottom: 16 }} />
                <Title level={4}>安全可靠</Title>
                <Paragraph>
                  严格的企业认证流程，确保每一个职位都来自可信赖的雇主
                </Paragraph>
              </Card>
            </Col>
          </Row>
        </div>
      </section>

      {/* 热门职位 */}
      <section style={{ padding: '80px 0' }}>
        <div style={{ maxWidth: 1200, margin: '0 auto', padding: '0 24px' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: 32 }}>
            <Title level={2}>热门职位</Title>
            <Button type="link" onClick={() => navigate('/jobs')}>
              查看更多 →
            </Button>
          </div>
          
          {hotJobs.length > 0 ? (
            <Row gutter={[24, 24]}>
              {hotJobs.map(job => (
                <Col xs={24} md={12} lg={8} key={job.id}>
                  <Card
                    hoverable
                    onClick={() => navigate(`/jobs/${job.id}`)}
                    style={{ height: '100%' }}
                  >
                    <Space direction="vertical" size="small" style={{ width: '100%' }}>
                      <Title level={4} ellipsis={{ rows: 2 }}>
                        {job.title}
                      </Title>
                      <Paragraph type="secondary">
                        {job.employer?.companyName}
                      </Paragraph>
                      <Paragraph ellipsis={{ rows: 2 }}>
                        {job.description}
                      </Paragraph>
                      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                        <span style={{ color: '#1890ff', fontWeight: 500 }}>
                          ${job.salaryMin?.toLocaleString()} - ${job.salaryMax?.toLocaleString()}
                        </span>
                        <span style={{ color: '#595959', fontSize: 12 }}>
                          {job.location}
                        </span>
                      </div>
                    </Space>
                  </Card>
                </Col>
              ))}
            </Row>
          ) : (
            <LoadingSpinner />
          )}
        </div>
      </section>

      {/* 最新职位 */}
      <section style={{ padding: '80px 0', background: '#fafafa' }}>
        <div style={{ maxWidth: 1200, margin: '0 auto', padding: '0 24px' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: 32 }}>
            <Title level={2}>最新职位</Title>
            <Button type="link" onClick={() => navigate('/jobs?sort=createdAt&direction=desc')}>
              查看更多 →
            </Button>
          </div>
          
          {latestJobs.length > 0 ? (
            <Row gutter={[24, 24]}>
              {latestJobs.map(job => (
                <Col xs={24} md={12} lg={8} key={job.id}>
                  <Card
                    hoverable
                    onClick={() => navigate(`/jobs/${job.id}`)}
                    style={{ height: '100%' }}
                  >
                    <Space direction="vertical" size="small" style={{ width: '100%' }}>
                      <Title level={4} ellipsis={{ rows: 2 }}>
                        {job.title}
                      </Title>
                      <Paragraph type="secondary">
                        {job.employer?.companyName}
                      </Paragraph>
                      <Paragraph ellipsis={{ rows: 2 }}>
                        {job.description}
                      </Paragraph>
                      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                        <span style={{ color: '#1890ff', fontWeight: 500 }}>
                          ${job.salaryMin?.toLocaleString()} - ${job.salaryMax?.toLocaleString()}
                        </span>
                        <span style={{ color: '#595959', fontSize: 12 }}>
                          {new Date(job.createdAt).toLocaleDateString()}
                        </span>
                      </div>
                    </Space>
                  </Card>
                </Col>
              ))}
            </Row>
          ) : (
            <LoadingSpinner />
          )}
        </div>
      </section>

      {/* CTA Section */}
      <section
        style={{
          background: 'linear-gradient(135deg, #722ed1 0%, #1890ff 100%)',
          color: '#ffffff',
          padding: '80px 0',
          textAlign: 'center',
        }}
      >
        <div style={{ maxWidth: 800, margin: '0 auto', padding: '0 24px' }}>
          <Title level={2} style={{ color: '#ffffff', marginBottom: 16 }}>
            准备开始你的远程职业生涯？
          </Title>
          <Paragraph style={{ color: '#ffffff', fontSize: 18, marginBottom: 32 }}>
            加入数千名工程师，找到你的理想远程工作
          </Paragraph>
          <Space size="large">
            <Button
              type="primary"
              size="large"
              onClick={() => navigate('/register')}
              style={{ background: '#ffffff', color: '#1890ff', border: 'none' }}
            >
              立即注册
            </Button>
            <Button
              size="large"
              onClick={() => navigate('/jobs')}
              style={{ color: '#ffffff', borderColor: '#ffffff' }}
            >
              浏览职位
            </Button>
          </Space>
        </div>
      </section>
    </div>
  );
};

export default HomePage;
