# Day 15 - 实现职位浏览页面的基本布局

## 今日目标
设计并实现职位浏览页面的基本布局，包括筛选区域、职位列表和排序功能。

## 任务详情

### 1. 页面整体布局设计
**目标**: 设计职位浏览页面的整体结构

**具体任务**:
- 设计页面布局结构：
  - **顶部搜索栏**: 关键词搜索、快速筛选
  - **左侧筛选区**: 详细筛选条件
  - **右侧内容区**: 职位列表、排序、分页
  - **面包屑导航**: 当前位置指示
- 制定响应式布局方案
- 确定筛选条件的优先级排序

### 2. 筛选区域实现
**目标**: 创建功能完整的职位筛选系统

**具体任务**:
- **基础筛选条件**:
  - 职位类别（前端、后端、全栈等）
  - 工作地点（远程、城市选择）
  - 薪资范围（滑块选择器）
  - 工作经验（应届、1-3年、3-5年等）
  - 学历要求（不限、本科、硕士等）
- **高级筛选条件**:
  - 公司规模（创业公司、中型、大型）
  - 工作类型（全职、兼职、实习）
  - 发布时间（今天、本周、本月）
  - 技能标签（多选）
- 实现筛选条件的展开/收起功能
- 添加"清除所有筛选"按钮

### 3. 职位列表区域
**目标**: 设计清晰易读的职位列表展示

**具体任务**:
- **职位卡片设计**:
  - 公司Logo和名称
  - 职位标题和描述摘要
  - 薪资范围和工作地点
  - 发布时间和申请人数
  - 技能标签展示
  - 收藏和申请按钮
- **列表功能**:
  - 排序选项（最新发布、薪资高低、相关度）
  - 查看模式切换（列表/网格）
  - 职位详情快速预览
- 实现职位卡片的悬停效果

### 4. 搜索和排序功能
**目标**: 提供灵活的搜索和排序选项

**具体任务**:
- **搜索功能**:
  - 关键词搜索（职位名称、公司名称、技能）
  - 搜索建议和自动完成
  - 搜索历史记录
  - 高级搜索选项
- **排序功能**:
  - 按发布时间排序
  - 按薪资范围排序
  - 按相关度排序
  - 按公司热度排序
- 实现搜索结果高亮显示

## 技术实现

### 页面组件结构
```jsx
// JobListPage.jsx
import React, { useState, useEffect } from 'react';
import { Layout, Row, Col, Breadcrumb } from 'antd';
import JobFilters from './components/JobFilters';
import JobList from './components/JobList';
import SearchHeader from './components/SearchHeader';

const { Content } = Layout;

const JobListPage = () => {
  const [filters, setFilters] = useState({});
  const [sortBy, setSortBy] = useState('latest');
  const [jobs, setJobs] = useState([]);
  const [loading, setLoading] = useState(false);

  return (
    <Layout>
      <Content style={{ padding: '24px' }}>
        <Breadcrumb style={{ marginBottom: '16px' }}>
          <Breadcrumb.Item>首页</Breadcrumb.Item>
          <Breadcrumb.Item>职位搜索</Breadcrumb.Item>
        </Breadcrumb>
        
        <SearchHeader onSearch={handleSearch} />
        
        <Row gutter={24}>
          <Col xs={24} md={6}>
            <JobFilters 
              filters={filters}
              onFiltersChange={setFilters}
            />
          </Col>
          <Col xs={24} md={18}>
            <JobList 
              jobs={jobs}
              loading={loading}
              sortBy={sortBy}
              onSortChange={setSortBy}
            />
          </Col>
        </Row>
      </Content>
    </Layout>
  );
};
```

### 筛选组件实现
```jsx
// JobFilters.jsx
import React from 'react';
import { Card, Select, Slider, Checkbox, Button, Divider } from 'antd';
import { ClearOutlined } from '@ant-design/icons';

const JobFilters = ({ filters, onFiltersChange }) => {
  const handleFilterChange = (key, value) => {
    onFiltersChange({
      ...filters,
      [key]: value
    });
  };

  const clearAllFilters = () => {
    onFiltersChange({});
  };

  return (
    <Card title="筛选条件" size="small">
      <div className="filter-section">
        <h4>职位类别</h4>
        <Select
          placeholder="选择职位类别"
          style={{ width: '100%' }}
          value={filters.category}
          onChange={(value) => handleFilterChange('category', value)}
        >
          <Option value="frontend">前端开发</Option>
          <Option value="backend">后端开发</Option>
          <Option value="fullstack">全栈开发</Option>
          <Option value="mobile">移动开发</Option>
        </Select>
      </div>

      <Divider />

      <div className="filter-section">
        <h4>薪资范围 (K/月)</h4>
        <Slider
          range
          min={5}
          max={50}
          value={filters.salaryRange || [5, 50]}
          onChange={(value) => handleFilterChange('salaryRange', value)}
          marks={{
            5: '5K',
            15: '15K',
            30: '30K',
            50: '50K+'
          }}
        />
      </div>

      <Divider />

      <div className="filter-section">
        <h4>工作经验</h4>
        <Checkbox.Group
          value={filters.experience || []}
          onChange={(value) => handleFilterChange('experience', value)}
        >
          <Checkbox value="fresh">应届生</Checkbox>
          <Checkbox value="1-3">1-3年</Checkbox>
          <Checkbox value="3-5">3-5年</Checkbox>
          <Checkbox value="5+">5年以上</Checkbox>
        </Checkbox.Group>
      </div>

      <Divider />

      <Button 
        type="link" 
        icon={<ClearOutlined />}
        onClick={clearAllFilters}
        style={{ padding: 0 }}
      >
        清除所有筛选
      </Button>
    </Card>
  );
};
```

### 职位列表组件
```jsx
// JobList.jsx
import React from 'react';
import { Card, Tag, Button, Avatar, Row, Col, Select, Spin } from 'antd';
import { HeartOutlined, EnvironmentOutlined, ClockCircleOutlined } from '@ant-design/icons';

const JobList = ({ jobs, loading, sortBy, onSortChange }) => {
  return (
    <div className="job-list">
      <div className="list-header">
        <Row justify="space-between" align="middle">
          <Col>
            <span>共找到 {jobs.length} 个职位</span>
          </Col>
          <Col>
            <Select
              value={sortBy}
              onChange={onSortChange}
              style={{ width: 120 }}
            >
              <Option value="latest">最新发布</Option>
              <Option value="salary_high">薪资从高到低</Option>
              <Option value="salary_low">薪资从低到高</Option>
              <Option value="relevance">相关度</Option>
            </Select>
          </Col>
        </Row>
      </div>

      <Spin spinning={loading}>
        <div className="job-cards">
          {jobs.map(job => (
            <Card 
              key={job.id}
              className="job-card"
              hoverable
              actions={[
                <Button type="link" icon={<HeartOutlined />}>收藏</Button>,
                <Button type="primary">立即申请</Button>
              ]}
            >
              <Row gutter={16}>
                <Col span={4}>
                  <Avatar 
                    size={64} 
                    src={job.company.logo}
                    alt={job.company.name}
                  />
                </Col>
                <Col span={20}>
                  <h3 className="job-title">{job.title}</h3>
                  <p className="company-name">{job.company.name}</p>
                  <p className="job-description">{job.description}</p>
                  
                  <div className="job-meta">
                    <Tag color="blue">{job.salaryRange}</Tag>
                    <Tag icon={<EnvironmentOutlined />}>{job.location}</Tag>
                    <Tag icon={<ClockCircleOutlined />}>{job.publishTime}</Tag>
                  </div>
                  
                  <div className="job-skills">
                    {job.skills.map(skill => (
                      <Tag key={skill} color="geekblue">{skill}</Tag>
                    ))}
                  </div>
                </Col>
              </Row>
            </Card>
          ))}
        </div>
      </Spin>
    </div>
  );
};
```

## 验收标准

### 功能验收
- [ ] 筛选条件能正确过滤职位列表
- [ ] 排序功能正常工作
- [ ] 搜索功能能找到相关职位
- [ ] 分页功能正常（如果实现）
- [ ] 响应式设计在移动端正常显示
- [ ] 职位卡片信息完整显示

### 用户体验验收
- [ ] 筛选操作响应迅速
- [ ] 职位列表加载流畅
- [ ] 筛选条件清晰易懂
- [ ] 职位信息一目了然
- [ ] 交互反馈及时

### 性能验收
- [ ] 页面初始加载时间小于2秒
- [ ] 筛选操作响应时间小于500ms
- [ ] 大量职位数据渲染流畅
- [ ] 内存使用合理

## 输出物
1. **页面组件代码**: 完整的职位浏览页面实现
2. **筛选逻辑文档**: 筛选条件和逻辑说明
3. **样式文件**: 页面和组件样式定义
4. **交互原型**: 页面交互流程说明

## 注意事项
- 考虑大数据量的性能优化（虚拟滚动）
- 预留个性化推荐的展示空间
- 确保筛选条件的逻辑合理性
- 考虑SEO优化，URL参数化筛选条件

## 下一步预告
明天将实现职位搜索功能的前端部分，包括智能搜索建议和搜索结果优化。
