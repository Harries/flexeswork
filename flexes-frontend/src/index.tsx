import React from 'react';
import { createRoot } from 'react-dom/client';
import App from './App';
import reportWebVitals from './reportWebVitals';

// 获取根元素
const container = document.getElementById('root');
if (!container) {
  throw new Error('Root element not found');
}

// 创建React根
const root = createRoot(container);

// 渲染应用
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);

// 性能监控
reportWebVitals(process.env.NODE_ENV === 'development' ? console.log : undefined);

// 热模块替换 (HMR) - 在开发环境中启用
if (process.env.NODE_ENV === 'development') {
  const moduleHot = (module as any).hot;
  if (moduleHot) {
    moduleHot.accept('./App', () => {
      const NextApp = require('./App').default;
      root.render(
        <React.StrictMode>
          <NextApp />
        </React.StrictMode>
      );
    });
  }
}
