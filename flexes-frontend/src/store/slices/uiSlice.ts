import { createSlice, PayloadAction } from '@reduxjs/toolkit';

// UI状态接口
interface UIState {
  // 侧边栏
  sidebarCollapsed: boolean;
  
  // 主题
  theme: 'light' | 'dark';
  
  // 语言
  locale: 'zh-CN' | 'en-US';
  
  // 加载状态
  globalLoading: boolean;
  
  // 移动端菜单
  mobileMenuVisible: boolean;
  
  // 搜索框
  searchVisible: boolean;
  
  // 通知
  notifications: Notification[];
  unreadCount: number;
  
  // 页面标题
  pageTitle: string;
  
  // 面包屑
  breadcrumbs: BreadcrumbItem[];
}

interface Notification {
  id: string;
  type: 'success' | 'info' | 'warning' | 'error';
  title: string;
  message: string;
  timestamp: number;
  read: boolean;
}

interface BreadcrumbItem {
  title: string;
  path?: string;
}

// 初始状态
const initialState: UIState = {
  sidebarCollapsed: false,
  theme: 'light',
  locale: 'zh-CN',
  globalLoading: false,
  mobileMenuVisible: false,
  searchVisible: false,
  notifications: [],
  unreadCount: 0,
  pageTitle: 'Flexes',
  breadcrumbs: [],
};

// 创建slice
const uiSlice = createSlice({
  name: 'ui',
  initialState,
  reducers: {
    // 切换侧边栏
    toggleSidebar: (state) => {
      state.sidebarCollapsed = !state.sidebarCollapsed;
    },
    
    // 设置侧边栏状态
    setSidebarCollapsed: (state, action: PayloadAction<boolean>) => {
      state.sidebarCollapsed = action.payload;
    },
    
    // 切换主题
    toggleTheme: (state) => {
      state.theme = state.theme === 'light' ? 'dark' : 'light';
    },
    
    // 设置主题
    setTheme: (state, action: PayloadAction<'light' | 'dark'>) => {
      state.theme = action.payload;
    },
    
    // 设置语言
    setLocale: (state, action: PayloadAction<'zh-CN' | 'en-US'>) => {
      state.locale = action.payload;
    },
    
    // 设置全局加载状态
    setGlobalLoading: (state, action: PayloadAction<boolean>) => {
      state.globalLoading = action.payload;
    },
    
    // 切换移动端菜单
    toggleMobileMenu: (state) => {
      state.mobileMenuVisible = !state.mobileMenuVisible;
    },
    
    // 设置移动端菜单状态
    setMobileMenuVisible: (state, action: PayloadAction<boolean>) => {
      state.mobileMenuVisible = action.payload;
    },
    
    // 切换搜索框
    toggleSearch: (state) => {
      state.searchVisible = !state.searchVisible;
    },
    
    // 设置搜索框状态
    setSearchVisible: (state, action: PayloadAction<boolean>) => {
      state.searchVisible = action.payload;
    },
    
    // 添加通知
    addNotification: (state, action: PayloadAction<Omit<Notification, 'id' | 'timestamp' | 'read'>>) => {
      const notification: Notification = {
        ...action.payload,
        id: Date.now().toString(),
        timestamp: Date.now(),
        read: false,
      };
      state.notifications.unshift(notification);
      state.unreadCount += 1;
    },
    
    // 标记通知为已读
    markNotificationAsRead: (state, action: PayloadAction<string>) => {
      const notification = state.notifications.find(n => n.id === action.payload);
      if (notification && !notification.read) {
        notification.read = true;
        state.unreadCount = Math.max(0, state.unreadCount - 1);
      }
    },
    
    // 删除通知
    removeNotification: (state, action: PayloadAction<string>) => {
      const index = state.notifications.findIndex(n => n.id === action.payload);
      if (index !== -1) {
        const notification = state.notifications[index];
        if (!notification.read) {
          state.unreadCount = Math.max(0, state.unreadCount - 1);
        }
        state.notifications.splice(index, 1);
      }
    },
    
    // 清除所有通知
    clearAllNotifications: (state) => {
      state.notifications = [];
      state.unreadCount = 0;
    },
    
    // 标记所有通知为已读
    markAllNotificationsAsRead: (state) => {
      state.notifications.forEach(notification => {
        notification.read = true;
      });
      state.unreadCount = 0;
    },
    
    // 设置页面标题
    setPageTitle: (state, action: PayloadAction<string>) => {
      state.pageTitle = action.payload;
      // 更新浏览器标题
      document.title = `${action.payload} - Flexes`;
    },
    
    // 设置面包屑
    setBreadcrumbs: (state, action: PayloadAction<BreadcrumbItem[]>) => {
      state.breadcrumbs = action.payload;
    },
    
    // 重置UI状态
    resetUI: (state) => {
      return { ...initialState, theme: state.theme, locale: state.locale };
    },
  },
});

// 导出actions
export const {
  toggleSidebar,
  setSidebarCollapsed,
  toggleTheme,
  setTheme,
  setLocale,
  setGlobalLoading,
  toggleMobileMenu,
  setMobileMenuVisible,
  toggleSearch,
  setSearchVisible,
  addNotification,
  markNotificationAsRead,
  removeNotification,
  clearAllNotifications,
  markAllNotificationsAsRead,
  setPageTitle,
  setBreadcrumbs,
  resetUI,
} = uiSlice.actions;

// 选择器
export const selectSidebarCollapsed = (state: { ui: UIState }) => state.ui.sidebarCollapsed;
export const selectTheme = (state: { ui: UIState }) => state.ui.theme;
export const selectLocale = (state: { ui: UIState }) => state.ui.locale;
export const selectGlobalLoading = (state: { ui: UIState }) => state.ui.globalLoading;
export const selectMobileMenuVisible = (state: { ui: UIState }) => state.ui.mobileMenuVisible;
export const selectSearchVisible = (state: { ui: UIState }) => state.ui.searchVisible;
export const selectNotifications = (state: { ui: UIState }) => state.ui.notifications;
export const selectUnreadCount = (state: { ui: UIState }) => state.ui.unreadCount;
export const selectPageTitle = (state: { ui: UIState }) => state.ui.pageTitle;
export const selectBreadcrumbs = (state: { ui: UIState }) => state.ui.breadcrumbs;

export default uiSlice.reducer;
