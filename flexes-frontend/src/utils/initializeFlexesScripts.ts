// 初始化Flexes模板的JavaScript功能
export const initializeFlexesScripts = () => {
  // 确保jQuery和其他库已加载
  if (typeof window !== 'undefined' && (window as any).$) {
    const $ = (window as any).$;
    
    // 初始化WOW动画
    if ((window as any).WOW) {
      new (window as any).WOW().init();
    }
    
    // 初始化轮播图
    if ($.fn.owlCarousel) {
      // 用户评价轮播 - 只显示一条，左右滑动
      $('.testimonials-slider').owlCarousel({
        loop: true,
        margin: 30,
        nav: true,
        dots: true,
        autoplay: true,
        autoplayTimeout: 5000,
        navText: ['<i class="ri-arrow-left-s-line"></i>', '<i class="ri-arrow-right-s-line"></i>'],
        responsive: {
          0: {
            items: 1
          },
          768: {
            items: 1
          },
          1200: {
            items: 1
          }
        }
      });
      
      // 最新工作轮播
      $('.recent-job-slider').owlCarousel({
        loop: true,
        margin: 30,
        nav: false,
        dots: true,
        autoplay: true,
        autoplayTimeout: 4000,
        responsive: {
          0: {
            items: 1
          },
          768: {
            items: 2
          },
          992: {
            items: 3
          },
          1200: {
            items: 4
          }
        }
      });
    }
    
    // 初始化其他交互功能
    initializeInteractions();

    // 初始化滚动固定导航栏
    initializeStickyHeader();
  }
};

// 初始化滚动固定导航栏
const initializeStickyHeader = () => {
  const navbar = document.querySelector('.navbar-area');
  if (!navbar) return;

  const stickyOffset = 100; // 滚动多少像素后开始固定

  const handleScroll = () => {
    const scrollTop = window.pageYOffset || document.documentElement.scrollTop;

    if (scrollTop > stickyOffset) {
      navbar.classList.add('is-sticky');
    } else {
      navbar.classList.remove('is-sticky');
    }
  };

  // 添加滚动事件监听
  window.addEventListener('scroll', handleScroll, { passive: true });

  // 添加CSS样式
  if (!document.querySelector('#sticky-header-styles')) {
    const style = document.createElement('style');
    style.id = 'sticky-header-styles';
    style.textContent = `
      /* 确保字体样式一致 */
      * {
        font-family: 'Work Sans', sans-serif !important;
      }

      /* 重置React默认样式 */
      #root {
        font-family: 'Work Sans', sans-serif !important;
        font-size: 16px !important;
        line-height: 1.6 !important;
      }

      /* 导航栏字体大小 - 匹配原页面 */
      .desktop-nav .navbar .navbar-nav .nav-item a {
        font-size: 16px !important;
        font-weight: 500 !important;
      }

      /* Footer字体大小 - 匹配原页面 */
      .footer-widget h3 {
        font-size: 22px !important;
      }

      .footer-widget h3.title {
        font-size: 20px !important;
      }

      .footer-widget p,
      .footer-widget .footer-list li a,
      .footer-widget .footer-contact-list li {
        font-size: 16px !important;
      }

      /* 右侧按钮字体大小 */
      .others-options .optional-item .optional-btn,
      .others-options .optional-item .default-btn {
        font-size: 15px !important;
        font-weight: 500 !important;
      }

      /* 导航栏固定样式 */
      .navbar-area.is-sticky {
        position: fixed !important;
        top: 0 !important;
        left: 0 !important;
        right: 0 !important;
        z-index: 9999 !important;
        background: #fff !important;
        box-shadow: 0 2px 10px rgba(0,0,0,0.1) !important;
        animation: slideDown 0.3s ease-in-out !important;
      }

      @keyframes slideDown {
        from {
          transform: translateY(-100%);
        }
        to {
          transform: translateY(0);
        }
      }

      .navbar-area.is-sticky .desktop-nav {
        padding: 10px 0 !important;
      }

      .navbar-area.is-sticky .navbar-brand img {
        max-height: 40px !important;
      }

      /* 确保导航栏和footer字体大小一致 */
      .navbar-nav .nav-link,
      .footer-area,
      .footer-area p,
      .footer-area h3,
      .footer-area li,
      .footer-area a {
        font-family: 'Work Sans', sans-serif !important;
      }
    `;
    document.head.appendChild(style);
  }
};

// 初始化页面交互功能
const initializeInteractions = () => {
  // 添加鼠标悬停效果
  addHoverEffects();

  // 使用原生JavaScript处理交互
  document.addEventListener('click', (e) => {
    const target = e.target as HTMLElement;

    // 书签按钮点击事件
    if (target.closest('.bookmark-btn')) {
      e.preventDefault();
      e.stopPropagation();
      const btn = target.closest('.bookmark-btn') as HTMLElement;
      btn.classList.toggle('bookmarked');
      const icon = btn.querySelector('i');
      if (btn.classList.contains('bookmarked')) {
        icon?.classList.remove('ri-bookmark-line');
        icon?.classList.add('ri-bookmark-fill');
        btn.style.color = '#ff6b6b';
      } else {
        icon?.classList.remove('ri-bookmark-fill');
        icon?.classList.add('ri-bookmark-line');
        btn.style.color = '';
      }
      return;
    }

    // 工作卡片点击事件
    if (target.closest('.job-post-card, .recent-job-item')) {
      // 如果点击的是书签按钮，不触发卡片点击
      if (target.closest('.bookmark-btn')) {
        return;
      }

      const card = target.closest('.job-post-card, .recent-job-item') as HTMLElement;
      const jobLink = card.querySelector('h3 a') as HTMLAnchorElement;
      if (jobLink?.href) {
        window.location.href = jobLink.href;
      }
      return;
    }

    // 公司卡片点击事件
    if (target.closest('.company-card')) {
      // 如果点击的是按钮，不触发卡片点击
      if (target.closest('.company-btn')) {
        return;
      }

      const card = target.closest('.company-card') as HTMLElement;
      const companyLink = card.querySelector('h3 a') as HTMLAnchorElement;
      if (companyLink?.href) {
        window.location.href = companyLink.href;
      }
    }
  });
};

// 添加鼠标悬停效果
const addHoverEffects = () => {
  // 职位卡片悬停效果
  const jobCards = document.querySelectorAll('.job-post-card');
  jobCards.forEach(card => {
    card.addEventListener('mouseenter', () => {
      (card as HTMLElement).style.transform = 'translateY(-5px)';
      (card as HTMLElement).style.boxShadow = '0 10px 30px rgba(0,0,0,0.1)';
      (card as HTMLElement).style.transition = 'all 0.3s ease';
    });

    card.addEventListener('mouseleave', () => {
      (card as HTMLElement).style.transform = 'translateY(0)';
      (card as HTMLElement).style.boxShadow = '';
    });
  });

  // 公司卡片悬停效果
  const companyCards = document.querySelectorAll('.company-card');
  companyCards.forEach(card => {
    card.addEventListener('mouseenter', () => {
      (card as HTMLElement).style.transform = 'translateY(-3px)';
      (card as HTMLElement).style.boxShadow = '0 8px 25px rgba(0,0,0,0.1)';
      (card as HTMLElement).style.transition = 'all 0.3s ease';
    });

    card.addEventListener('mouseleave', () => {
      (card as HTMLElement).style.transform = 'translateY(0)';
      (card as HTMLElement).style.boxShadow = '';
    });
  });

  // 职位分类卡片悬停效果
  const browseJobsItems = document.querySelectorAll('.browse-jobs-item');
  browseJobsItems.forEach(item => {
    item.addEventListener('mouseenter', () => {
      (item as HTMLElement).style.transform = 'translateY(-3px)';
      (item as HTMLElement).style.transition = 'all 0.3s ease';
    });

    item.addEventListener('mouseleave', () => {
      (item as HTMLElement).style.transform = 'translateY(0)';
    });
  });

  // 按钮悬停效果
  const buttons = document.querySelectorAll('.default-btn, .submit-btn, .price-btn, .company-btn, .browse-btn a');
  buttons.forEach(btn => {
    btn.addEventListener('mouseenter', () => {
      (btn as HTMLElement).style.transform = 'translateY(-2px)';
      (btn as HTMLElement).style.transition = 'all 0.3s ease';
    });

    btn.addEventListener('mouseleave', () => {
      (btn as HTMLElement).style.transform = 'translateY(0)';
    });
  });

  // 书签按钮悬停效果
  const bookmarkBtns = document.querySelectorAll('.bookmark-btn');
  bookmarkBtns.forEach(btn => {
    btn.addEventListener('mouseenter', () => {
      (btn as HTMLElement).style.transform = 'scale(1.1)';
      (btn as HTMLElement).style.transition = 'all 0.2s ease';
    });

    btn.addEventListener('mouseleave', () => {
      (btn as HTMLElement).style.transform = 'scale(1)';
    });
  });
};

// 在组件挂载后调用
export const initializeOnMount = () => {
  // 立即隐藏preloader
  const preloader = document.querySelector('.preloader');
  if (preloader) {
    (preloader as HTMLElement).style.display = 'none';
  }

  // 延迟执行以确保DOM完全加载
  setTimeout(() => {
    initializeFlexesScripts();
  }, 100);
};

// 清理函数
export const cleanupFlexesScripts = () => {
  if (typeof window !== 'undefined' && (window as any).$) {
    const $ = (window as any).$;

    // 销毁轮播图实例
    $('.testimonials-slider').trigger('destroy.owl.carousel');
    $('.recent-job-slider').trigger('destroy.owl.carousel');
  }

  // 移除原生事件监听器会在组件卸载时自动处理
};
