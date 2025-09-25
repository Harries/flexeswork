# Day 14 - 实现主页的基本布局和样式

## 今日目标
设计并实现招聘网站主页的基本布局和样式，创建用户友好的首页体验。

## 任务详情

### 1. 主页结构设计
**目标**: 设计主页的整体布局结构

**具体任务**:
- 设计主页布局结构：
  - **Header区域**: 导航栏、Logo、用户登录/注册入口
  - **Hero区域**: 主标语、搜索框、核心价值展示
  - **特色功能区**: 热门职位、推荐公司、平台优势
  - **统计数据区**: 职位数量、注册用户、成功案例
  - **Footer区域**: 联系信息、友情链接、版权声明
- 制定响应式布局方案
- 确定各区域的内容优先级

### 2. Header导航栏实现
**目标**: 实现网站顶部导航栏

**具体任务**:
- 创建响应式导航栏组件
- 实现Logo和品牌标识
- 添加主要导航菜单：
  - 首页
  - 职位搜索
  - 公司列表
  - 关于我们
- 实现用户操作区域：
  - 登录/注册按钮
  - 用户头像下拉菜单（登录后）
  - 发布职位按钮（HR用户）
- 添加移动端汉堡菜单

### 3. Hero区域开发
**目标**: 创建吸引用户的主要展示区域

**具体任务**:
- 设计主标语和副标语：
  - "连接优秀人才与理想工作"
  - "专业的远程工作招聘平台"
- 实现职位搜索框：
  - 关键词搜索输入框
  - 地点选择下拉框
  - 职位类别筛选
  - 搜索按钮
- 添加背景图片或渐变效果
- 实现搜索建议功能

### 4. 特色功能区实现
**目标**: 展示平台核心功能和热门内容

**具体任务**:
- **热门职位区域**:
  - 显示最新发布的6-8个职位
  - 职位卡片设计（公司Logo、职位名称、薪资、地点）
  - "查看更多"链接
- **推荐公司区域**:
  - 展示认证企业Logo
  - 公司简介和招聘职位数
  - 公司详情页面链接
- **平台优势区域**:
  - 图标+文字形式展示平台特色
  - 如：海量职位、精准匹配、快速响应等

### 5. 统计数据区实现
**目标**: 通过数据展示平台实力

**具体任务**:
- 设计数据展示卡片
- 实现动态数字动画效果
- 展示关键指标：
  - 累计职位数量
  - 注册用户数
  - 成功匹配案例
  - 合作企业数量
- 添加数据更新时间说明

## 技术实现

### 主页组件结构
```jsx
// HomePage.jsx
import React from 'react';
import { Layout } from 'antd';
import Header from './components/Header';
import HeroSection from './components/HeroSection';
import FeaturedJobs from './components/FeaturedJobs';
import CompanyShowcase from './components/CompanyShowcase';
import Statistics from './components/Statistics';
import Footer from './components/Footer';

const { Content } = Layout;

const HomePage = () => {
  return (
    <Layout>
      <Header />
      <Content>
        <HeroSection />
        <FeaturedJobs />
        <CompanyShowcase />
        <Statistics />
      </Content>
      <Footer />
    </Layout>
  );
};

export default HomePage;
```

### Hero区域搜索框
```jsx
// HeroSection.jsx
import React from 'react';
import { Input, Select, Button, Row, Col } from 'antd';
import { SearchOutlined, EnvironmentOutlined } from '@ant-design/icons';

const HeroSection = () => {
  return (
    <div className="hero-section">
      <div className="hero-content">
        <h1>连接优秀人才与理想工作</h1>
        <p>专业的远程工作招聘平台</p>
        <div className="search-box">
          <Row gutter={16}>
            <Col span={8}>
              <Input 
                placeholder="搜索职位、公司或技能"
                prefix={<SearchOutlined />}
                size="large"
              />
            </Col>
            <Col span={6}>
              <Select 
                placeholder="选择地点"
                size="large"
                style={{ width: '100%' }}
              >
                <Option value="remote">远程工作</Option>
                <Option value="beijing">北京</Option>
                <Option value="shanghai">上海</Option>
              </Select>
            </Col>
            <Col span={6}>
              <Select 
                placeholder="职位类别"
                size="large"
                style={{ width: '100%' }}
              >
                <Option value="frontend">前端开发</Option>
                <Option value="backend">后端开发</Option>
                <Option value="fullstack">全栈开发</Option>
              </Select>
            </Col>
            <Col span={4}>
              <Button type="primary" size="large" block>
                搜索职位
              </Button>
            </Col>
          </Row>
        </div>
      </div>
    </div>
  );
};
```

### 样式配置
```css
/* HomePage.css */
.hero-section {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 100px 0;
  text-align: center;
  color: white;
}

.hero-content h1 {
  font-size: 48px;
  font-weight: bold;
  margin-bottom: 16px;
}

.hero-content p {
  font-size: 20px;
  margin-bottom: 40px;
  opacity: 0.9;
}

.search-box {
  max-width: 800px;
  margin: 0 auto;
  background: white;
  padding: 24px;
  border-radius: 8px;
  box-shadow: 0 4px 20px rgba(0,0,0,0.1);
}

.featured-section {
  padding: 80px 0;
  background: #f5f5f5;
}

.statistics-section {
  padding: 60px 0;
  background: #001529;
  color: white;
  text-align: center;
}
```

## 验收标准

### 功能验收
- [ ] 主页各区域正确显示和布局
- [ ] 导航栏在不同设备上正常工作
- [ ] 搜索框能接收用户输入
- [ ] 热门职位卡片正确展示
- [ ] 统计数据动画效果正常
- [ ] 响应式设计在移动端正常显示

### 设计验收
- [ ] 整体视觉效果符合现代网站标准
- [ ] 色彩搭配协调，符合品牌形象
- [ ] 字体大小和间距合理
- [ ] 图片和图标清晰美观
- [ ] 交互效果流畅自然

### 性能验收
- [ ] 页面加载速度小于3秒
- [ ] 图片优化，文件大小合理
- [ ] CSS和JS文件压缩
- [ ] 无控制台错误或警告

## 输出物
1. **主页组件代码**: 完整的React组件实现
2. **样式文件**: CSS/SCSS样式定义
3. **设计规范文档**: 主页设计说明和规范
4. **响应式测试报告**: 不同设备的显示效果截图

## 注意事项
- 确保主页加载速度，避免过多大图片
- 考虑SEO优化，添加合适的meta标签
- 预留A/B测试的空间，方便后续优化
- 确保无障碍访问支持

## 下一步预告
明天将实现职位浏览页面的基本布局，包括职位列表和筛选功能。
