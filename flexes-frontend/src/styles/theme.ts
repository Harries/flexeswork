// 主题类型定义
export interface Theme {
  colors: {
    primary: string;
    secondary: string;
    success: string;
    warning: string;
    error: string;
    info: string;
    text: {
      primary: string;
      secondary: string;
      disabled: string;
    };
    background: {
      primary: string;
      secondary: string;
      paper: string;
    };
    border: {
      primary: string;
      secondary: string;
    };
    shadow: {
      primary: string;
      secondary: string;
    };
  };
  spacing: {
    xs: string;
    sm: string;
    md: string;
    lg: string;
    xl: string;
    xxl: string;
  };
  breakpoints: {
    xs: string;
    sm: string;
    md: string;
    lg: string;
    xl: string;
    xxl: string;
  };
  typography: {
    fontFamily: string;
    fontSize: {
      xs: string;
      sm: string;
      md: string;
      lg: string;
      xl: string;
      xxl: string;
    };
    fontWeight: {
      light: number;
      normal: number;
      medium: number;
      semibold: number;
      bold: number;
    };
    lineHeight: {
      tight: number;
      normal: number;
      relaxed: number;
    };
  };
  borderRadius: {
    sm: string;
    md: string;
    lg: string;
    full: string;
  };
  zIndex: {
    dropdown: number;
    sticky: number;
    fixed: number;
    modal: number;
    popover: number;
    tooltip: number;
  };
}

// 基础主题配置
const baseTheme = {
  spacing: {
    xs: '4px',
    sm: '8px',
    md: '16px',
    lg: '24px',
    xl: '32px',
    xxl: '48px',
  },
  breakpoints: {
    xs: '480px',
    sm: '576px',
    md: '768px',
    lg: '992px',
    xl: '1200px',
    xxl: '1600px',
  },
  typography: {
    fontFamily: "'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif",
    fontSize: {
      xs: '12px',
      sm: '14px',
      md: '16px',
      lg: '18px',
      xl: '20px',
      xxl: '24px',
    },
    fontWeight: {
      light: 300,
      normal: 400,
      medium: 500,
      semibold: 600,
      bold: 700,
    },
    lineHeight: {
      tight: 1.2,
      normal: 1.5,
      relaxed: 1.8,
    },
  },
  borderRadius: {
    sm: '4px',
    md: '6px',
    lg: '8px',
    full: '50%',
  },
  zIndex: {
    dropdown: 1000,
    sticky: 1020,
    fixed: 1030,
    modal: 1040,
    popover: 1050,
    tooltip: 1060,
  },
};

// 浅色主题
export const lightTheme: Theme = {
  ...baseTheme,
  colors: {
    primary: '#1890ff',
    secondary: '#722ed1',
    success: '#52c41a',
    warning: '#faad14',
    error: '#f5222d',
    info: '#13c2c2',
    text: {
      primary: '#262626',
      secondary: '#595959',
      disabled: '#bfbfbf',
    },
    background: {
      primary: '#ffffff',
      secondary: '#fafafa',
      paper: '#ffffff',
    },
    border: {
      primary: '#d9d9d9',
      secondary: '#f0f0f0',
    },
    shadow: {
      primary: '0 2px 8px rgba(0, 0, 0, 0.15)',
      secondary: '0 1px 2px rgba(0, 0, 0, 0.1)',
    },
  },
};

// 深色主题
export const darkTheme: Theme = {
  ...baseTheme,
  colors: {
    primary: '#1890ff',
    secondary: '#722ed1',
    success: '#52c41a',
    warning: '#faad14',
    error: '#f5222d',
    info: '#13c2c2',
    text: {
      primary: '#ffffff',
      secondary: '#a6a6a6',
      disabled: '#595959',
    },
    background: {
      primary: '#141414',
      secondary: '#1f1f1f',
      paper: '#262626',
    },
    border: {
      primary: '#434343',
      secondary: '#303030',
    },
    shadow: {
      primary: '0 2px 8px rgba(0, 0, 0, 0.45)',
      secondary: '0 1px 2px rgba(0, 0, 0, 0.3)',
    },
  },
};

// 主题工具函数
export const getTheme = (isDark: boolean): Theme => {
  return isDark ? darkTheme : lightTheme;
};

// 媒体查询工具
export const mediaQueries = {
  xs: `@media (max-width: ${baseTheme.breakpoints.xs})`,
  sm: `@media (max-width: ${baseTheme.breakpoints.sm})`,
  md: `@media (max-width: ${baseTheme.breakpoints.md})`,
  lg: `@media (max-width: ${baseTheme.breakpoints.lg})`,
  xl: `@media (max-width: ${baseTheme.breakpoints.xl})`,
  xxl: `@media (max-width: ${baseTheme.breakpoints.xxl})`,
  
  minXs: `@media (min-width: ${baseTheme.breakpoints.xs})`,
  minSm: `@media (min-width: ${baseTheme.breakpoints.sm})`,
  minMd: `@media (min-width: ${baseTheme.breakpoints.md})`,
  minLg: `@media (min-width: ${baseTheme.breakpoints.lg})`,
  minXl: `@media (min-width: ${baseTheme.breakpoints.xl})`,
  minXxl: `@media (min-width: ${baseTheme.breakpoints.xxl})`,
};
