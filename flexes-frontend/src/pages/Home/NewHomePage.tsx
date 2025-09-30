import React, { useEffect } from 'react';
import { initializeOnMount, cleanupFlexesScripts } from '../../utils/initializeFlexesScripts';

const NewHomePage: React.FC = () => {
  useEffect(() => {
    // 立即隐藏preloader
    const preloader = document.querySelector('.preloader');
    if (preloader) {
      (preloader as HTMLElement).style.display = 'none';
    }

    // 初始化Flexes脚本
    initializeOnMount();

    // 清理函数
    return () => {
      cleanupFlexesScripts();
    };
  }, []);

  return (
    <>
      {/* Preloader - 默认隐藏 */}
      <div className="preloader" style={{ display: 'none' }}>
        <div className="d-table">
          <div className="d-table-cell">
            <div className="spinner"></div>
          </div>
        </div>
      </div>

      {/* Navbar Area */}
      <div className="navbar-area">
        <div className="mobile-responsive-nav">
          <div className="container-fluid">
            <div className="mobile-responsive-menu">
              <div className="logo">
                <a href="index.html">
                  <img src="assets/images/logo.png" alt="logo" />
                </a>
              </div>
            </div>
          </div>
        </div>

        <div className="desktop-nav desktop-nav-one nav-area">
          <div className="container-fluid">
            <nav className="navbar navbar-expand-md navbar-light">
              <a className="navbar-brand" href="index.html">
                <img src="assets/images/logo.png" alt="Logo" />
              </a>
              <div className="collapse navbar-collapse mean-menu" id="navbarSupportedContent">
                <ul className="navbar-nav">
                  <li className="nav-item">
                    <a href="/" className="nav-link active">
                      Home
                    </a>
                  </li>
                  <li className="nav-item">
                    <a href="#" className="nav-link">
                      Jobs
                      <i className="ri-arrow-down-s-line"></i>
                    </a>
                    <ul className="dropdown-menu">
                      <li className="nav-item">
                        <a href="job-listing.html" className="nav-link">Job Listing</a>
                      </li>
                      <li className="nav-item">
                        <a href="job-listing-2.html" className="nav-link">Job Listing 2</a>
                      </li>
                      <li className="nav-item">
                        <a href="job-listing-3.html" className="nav-link">Job Listing 3</a>
                      </li>
                      <li className="nav-item">
                        <a href="job-listing-4.html" className="nav-link">Job Listing 4</a>
                      </li>
                      <li className="nav-item">
                        <a href="job-listing-5.html" className="nav-link">Job Listing 5</a>
                      </li>
                      <li className="nav-item">
                        <a href="job-listing-6.html" className="nav-link">Job Listing 6</a>
                      </li>
                      <li className="nav-item">
                        <a href="job-details.html" className="nav-link">Job Details</a>
                      </li>
                    </ul>
                  </li>
                  <li className="nav-item">
                    <a href="#" className="nav-link">
                      Employers
                      <i className="ri-arrow-down-s-line"></i>
                    </a>
                    <ul className="dropdown-menu">
                      <li className="nav-item">
                        <a href="employers-listing.html" className="nav-link">Employers Listing</a>
                      </li>
                      <li className="nav-item">
                        <a href="employers-details.html" className="nav-link">Employers Details</a>
                      </li>
                      <li className="nav-item">
                        <a href="dashboard.html" className="nav-link">Dashboard</a>
                      </li>
                      <li className="nav-item">
                        <a href="add-job.html" className="nav-link">Add Job</a>
                      </li>
                      <li className="nav-item">
                        <a href="job-post.html" className="nav-link">Job Post</a>
                      </li>
                      <li className="nav-item">
                        <a href="applications.html" className="nav-link">Applications</a>
                      </li>
                      <li className="nav-item">
                        <a href="company-profile.html" className="nav-link">Company Profile</a>
                      </li>
                    </ul>
                  </li>
                  <li className="nav-item">
                    <a href="#" className="nav-link">
                      Candidates
                      <i className="ri-arrow-down-s-line"></i>
                    </a>
                    <ul className="dropdown-menu">
                      <li className="nav-item">
                        <a href="candidates-listing.html" className="nav-link">Candidates Listing</a>
                      </li>
                      <li className="nav-item">
                        <a href="candidates-details.html" className="nav-link">Candidates Details</a>
                      </li>
                      <li className="nav-item">
                        <a href="candidates-dashboard.html" className="nav-link">Dashboard</a>
                      </li>
                      <li className="nav-item">
                        <a href="candidates-applied-job.html" className="nav-link">Applied Job</a>
                      </li>
                      <li className="nav-item">
                        <a href="candidates-resume.html" className="nav-link">My Resume</a>
                      </li>
                    </ul>
                  </li>
                  <li className="nav-item">
                    <a href="#" className="nav-link">
                      Blog
                      <i className="ri-arrow-down-s-line"></i>
                    </a>
                    <ul className="dropdown-menu">
                      <li className="nav-item">
                        <a href="blog-1.html" className="nav-link">Blog Grid</a>
                      </li>
                      <li className="nav-item">
                        <a href="blog-2.html" className="nav-link">Blog Left Sidebar</a>
                      </li>
                      <li className="nav-item">
                        <a href="blog-3.html" className="nav-link">Blog Right Sidebar</a>
                      </li>
                      <li className="nav-item">
                        <a href="blog-details.html" className="nav-link">Blog Details</a>
                      </li>
                    </ul>
                  </li>
                  <li className="nav-item">
                    <a href="#" className="nav-link">
                      Pages
                      <i className="ri-arrow-down-s-line"></i>
                    </a>
                    <ul className="dropdown-menu">
                      <li className="nav-item">
                        <a href="about.html" className="nav-link">About Us</a>
                      </li>
                      <li className="nav-item">
                        <a href="contact.html" className="nav-link">Contact Us</a>
                      </li>
                      <li className="nav-item">
                        <a href="testimonials.html" className="nav-link">Testimonials</a>
                      </li>
                      <li className="nav-item">
                        <a href="pricing.html" className="nav-link">Pricing</a>
                      </li>
                      <li className="nav-item">
                        <a href="faq.html" className="nav-link">FAQ</a>
                      </li>
                      <li className="nav-item">
                        <a href="404.html" className="nav-link">404 Error Page</a>
                      </li>
                      <li className="nav-item">
                        <a href="coming-soon.html" className="nav-link">Coming Soon</a>
                      </li>
                      <li className="nav-item">
                        <a href="terms-condition.html" className="nav-link">Terms & Conditions</a>
                      </li>
                      <li className="nav-item">
                        <a href="privacy-policy.html" className="nav-link">Privacy Policy</a>
                      </li>
                    </ul>
                  </li>
                </ul>

                <div className="others-options d-flex align-items-center">
                  {/* 语言切换 */}
                  <div className="optional-item">
                    <div className="dropdown">
                      <button className="btn dropdown-toggle" type="button" id="languageDropdown" data-bs-toggle="dropdown" aria-expanded="false">
                        <i className="ri-global-line"></i> EN
                      </button>
                      <ul className="dropdown-menu" aria-labelledby="languageDropdown">
                        <li><a className="dropdown-item" href="#">English</a></li>
                        <li><a className="dropdown-item" href="#">中文</a></li>
                        <li><a className="dropdown-item" href="#">Español</a></li>
                        <li><a className="dropdown-item" href="#">Français</a></li>
                      </ul>
                    </div>
                  </div>

                  {/* 登录按钮 */}
                  <div className="optional-item">
                    <a href="/login" className="optional-btn">
                      <i className="ri-login-circle-line"></i> Login
                    </a>
                  </div>

                  {/* 注册按钮 */}
                  <div className="optional-item">
                    <a href="/register" className="default-btn two border-radius-5">Sign Up</a>
                  </div>

                  {/* Post A Job按钮 */}
                  <div className="optional-item">
                    <a href="contact.html" className="default-btn border-radius-5">Post A Job</a>
                  </div>
                </div>
              </div>
            </nav>
          </div>
        </div>
      </div>

      {/* Banner Area */}
      <div className="banner-area-three">
        <div className="container">
          <div className="banner-content-three">
            <h1 className="wow fadeInUp" data-wow-delay="000ms" data-wow-duration="1000ms">
              Searching For A Job? <br /> Find the Perfect Job Near You
            </h1>
            <div className="banner-form-three wow fadeInUp" data-wow-delay="100ms" data-wow-duration="1000ms">
              <form>
                <div className="row">
                  <div className="col-lg-4 col-md-4">
                    <div className="form-group">
                      <input className="form-control" type="text" placeholder="Keywords / Job Title" />
                      <i className="ri-search-line"></i>
                    </div>
                  </div>
                  <div className="col-lg-4 col-md-4">
                    <div className="form-group">
                      <input className="form-control" type="text" placeholder="City or Postcode" />
                      <i className="ri-map-pin-line"></i>
                    </div>
                  </div>
                  <div className="col-lg-4 col-md-4">
                    <button type="submit" className="submit-btn border-radius-5">
                      Find Jobs
                    </button>
                  </div>
                </div>
              </form>
            </div>
            <div className="popular-searches-tag wow fadeInUp" data-wow-delay="200ms" data-wow-duration="1000ms">
              <span className="title">Popular Searches : </span>
              <a href="job-listing.html">Designer</a>,
              <a href="job-listing.html">Developer</a>,
              <a href="job-listing.html">Web</a>,
              <a href="job-listing.html">IOS</a>,
              <a href="job-listing.html">PHP</a>,
              <a href="job-listing.html">Senior</a>,
              <a href="job-listing.html">Engineer</a>
            </div>
            <div className="banner-three-other">
              <div className="other-content1 wow fadeInUp" data-wow-delay="300ms" data-wow-duration="1000ms">
                <img src="assets/images/home-three/user2.jpg" alt="Images" />
              </div>
              <div className="other-content2 wow fadeInUp" data-wow-delay="400ms" data-wow-duration="1000ms">
                <img src="assets/images/home-three/home-three-icon2.png" alt="Images" />
              </div>
              <div className="other-content3 wow fadeInDown" data-wow-delay="500ms" data-wow-duration="1000ms">
                <img src="assets/images/home-three/user1.jpg" alt="Images" />
              </div>
              <div className="other-content4 wow fadeInDown" data-wow-delay="600ms" data-wow-duration="1000ms">
                <img src="assets/images/home-three/home-three-shape1.jpg" alt="Images" />
              </div>
              <div className="other-content5 wow fadeInUp" data-wow-delay="700ms" data-wow-duration="1000ms">
                <img src="assets/images/home-three/home-three-icon4.png" alt="Images" />
              </div>
              <div className="other-content6 wow fadeInDown" data-wow-delay="800ms" data-wow-duration="1000ms">
                <img src="assets/images/home-three/home-three-icon1.png" alt="Images" />
              </div>
              <div className="other-content7 wow fadeInDown" data-wow-delay="900ms" data-wow-duration="1000ms">
                <img src="assets/images/home-three/home-three-shape2.jpg" alt="Images" />
              </div>
              <div className="other-content8 wow fadeInDown" data-wow-delay="1000ms" data-wow-duration="1000ms">
                <img src="assets/images/home-three/user4.jpg" alt="Images" />
              </div>
              <div className="other-content9 wow fadeInDown" data-wow-delay="1100ms" data-wow-duration="1000ms">
                <img src="assets/images/home-three/user3.jpg" alt="Images" />
                <div className="other-into-content">
                  <img src="assets/images/home-three/home-three-shape1.jpg" alt="Images" />
                </div>
              </div>
              <div className="other-content10 wow fadeInDown" data-wow-delay="1200ms" data-wow-duration="1000ms">
                <img src="assets/images/home-three/home-three-icon3.png" alt="Images" />
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* Browse Jobs By Specialisms */}
      <div className="browse-jobs-area-two pt-100 pb-70">
        <div className="container">
          <div className="section-title text-center">
            <h2>Browse Jobs By Specialisms</h2>
            <div className="bar m-auto"></div>
            <p className="m-auto">144 jobs live - 20 added today.</p>
          </div>
          <div className="row pt-45">
            <div className="col-lg-3 col-6">
              <div className="browse-jobs-item">
                <i className="ri-computer-line jobs-card-bg"></i>
                <h3><a href="job-details.html">Accountancy</a></h3>
                <p>( 22 open positions )</p>
              </div>
            </div>
            <div className="col-lg-3 col-6">
              <div className="browse-jobs-item">
                <i className="ri-bus-2-line jobs-card-bg2"></i>
                <h3><a href="job-details.html">Transport</a></h3>
                <p>( 43 open positions )</p>
              </div>
            </div>
            <div className="col-lg-3 col-6">
              <div className="browse-jobs-item">
                <i className="ri-flashlight-line jobs-card-bg3"></i>
                <h3><a href="job-details.html">Project Manager</a></h3>
                <p>( 35 open positions )</p>
              </div>
            </div>
            <div className="col-lg-3 col-6">
              <div className="browse-jobs-item">
                <i className="ri-building-2-line jobs-card-bg4"></i>
                <h3><a href="job-details.html">Construction</a></h3>
                <p>( 90 open positions )</p>
              </div>
            </div>
            <div className="col-lg-3 col-6">
              <div className="browse-jobs-item">
                <i className="ri-cloud-line jobs-card-bg5"></i>
                <h3><a href="job-details.html">Digital Service Jobs</a></h3>
                <p>( 40 open positions )</p>
              </div>
            </div>
            <div className="col-lg-3 col-6">
              <div className="browse-jobs-item">
                <i className="ri-group-line jobs-card-bg6"></i>
                <h3><a href="job-details.html">Human Resource</a></h3>
                <p>( 8 open positions )</p>
              </div>
            </div>
            <div className="col-lg-3 col-6">
              <div className="browse-jobs-item">
                <i className="ri-restaurant-line jobs-card-bg7"></i>
                <h3><a href="job-details.html">Restaurant Jobs</a></h3>
                <p>( 56 open positions )</p>
              </div>
            </div>
            <div className="col-lg-3 col-6">
              <div className="browse-jobs-item">
                <i className="ri-pie-chart-line jobs-card-bg8"></i>
                <h3><a href="job-details.html">Marketing & SEO</a></h3>
                <p>( 33 open positions )</p>
              </div>
            </div>
            <div className="col-lg-12 text-center">
              <div className="browse-btn">
                <a href="job-listing.html"> Browse All Categories <i className="ri-arrow-right-s-line"></i></a>
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* Recent Job Posted */}
      <div className="job-post-area pt-100 pb-70">
        <div className="container">
          <div className="section-title text-center">
            <h2>Recent Job Posted</h2>
            <div className="bar m-auto"></div>
            <p className="m-auto">We collect reviews from our users so you can get an honest opinion of what an experience with our website are really like!</p>
          </div>
          <div className="row pt-45">
            <div className="col-lg-12">
              <div className="job-post-form">
                <form>
                  <div className="row">
                    <div className="col-lg-4 col-md-4">
                      <div className="form-group">
                        <input className="form-control" type="text" placeholder="Keywords / Job Title" />
                        <i className="ri-search-line"></i>
                      </div>
                    </div>
                    <div className="col-lg-3 col-md-4">
                      <div className="form-group">
                        <input className="form-control" type="text" placeholder="City or Postcode" />
                        <i className="ri-map-pin-line"></i>
                      </div>
                    </div>
                    <div className="col-lg-3 col-md-4">
                      <div className="form-group select-group">
                        <select className="form-select form-control">
                          <option data-display='Choose A Category'>Choose A Category</option>
                          <option value="1">UI/UX Designer</option>
                          <option value="2">Web Developer</option>
                          <option value="2">FontEnd Developer</option>
                          <option value="2">Creative Agency</option>
                          <option value="3">Engineer</option>
                        </select>
                      </div>
                    </div>
                    <div className="col-lg-2 col-md-12">
                      <button type="submit" className="submit-btn">
                        Find Jobs
                      </button>
                    </div>
                  </div>
                </form>
              </div>
            </div>
          </div>
          <div className="row">
            <div className="col-lg-12">
              <div className="job-post-card job-post-card-ml">
                <div className="job-post-img">
                  <a href="job-details.html">
                    <img src="assets/images/recent-job/recent-job4.jpg" alt="Images" />
                  </a>
                </div>
                <h3><a href="job-details.html">Product Designer</a></h3>
                <div className="content">
                  <div className="content-item">
                    <ul className="content-list">
                      <li><i className="ri-briefcase-line"></i> Design</li>
                      <li><i className="ri-map-pin-line"></i> 305 Hicks St Brooklyn, NY</li>
                      <li><i className="ri-time-line"></i> 5 Days Ago</li>
                    </ul>
                    <span>$120 - $130 <b> / Per Weeks</b></span>
                  </div>
                  <ul className="content-list2">
                    <li className="time">Full Time</li>
                    <li className="freelance">Freelance</li>
                    <li className="urgent">Urgent</li>
                  </ul>
                </div>
                <button className="bookmark-btn"><i className="ri-bookmark-line"></i></button>
              </div>
            </div>
            <div className="col-lg-12">
              <div className="job-post-card job-post-card-ml">
                <div className="job-post-img">
                  <a href="job-details.html">
                    <img src="assets/images/recent-job/recent-job10.jpg" alt="Images" />
                  </a>
                </div>
                <h3><a href="job-details.html">Finance Manager & Health </a></h3>
                <div className="content">
                  <div className="content-item">
                    <ul className="content-list">
                      <li><i className="ri-briefcase-line"></i> Medical</li>
                      <li><i className="ri-map-pin-line"></i> 305 Hicks St Brooklyn, NY</li>
                      <li><i className="ri-time-line"></i> 5 Weeks Ago</li>
                    </ul>
                    <span>$20 - $30 <b> / Per Hour</b></span>
                  </div>
                  <ul className="content-list2">
                    <li className="time">Full Time</li>
                    <li className="freelance">Freelance</li>
                    <li className="urgent">Urgent</li>
                  </ul>
                </div>
                <button className="bookmark-btn"><i className="ri-bookmark-line"></i></button>
              </div>
            </div>
            <div className="col-lg-12">
              <div className="job-post-card job-post-card-ml">
                <div className="job-post-img">
                  <a href="job-details.html">
                    <img src="assets/images/recent-job/recent-job11.jpg" alt="Images" />
                  </a>
                </div>
                <h3><a href="job-details.html">General Ledger Accountant</a></h3>
                <div className="content">
                  <div className="content-item">
                    <ul className="content-list">
                      <li><i className="ri-briefcase-line"></i> Accounting</li>
                      <li><i className="ri-map-pin-line"></i> 305 Hicks St Brooklyn, NY</li>
                      <li><i className="ri-time-line"></i> 2 Weeks Ago</li>
                    </ul>
                    <span>$30 - $40 <b> / Per Hour</b></span>
                  </div>
                  <ul className="content-list2">
                    <li className="time">Full Time</li>
                    <li className="freelance">Freelance</li>
                    <li className="urgent">Urgent</li>
                  </ul>
                </div>
                <button className="bookmark-btn"><i className="ri-bookmark-line"></i></button>
              </div>
            </div>
            <div className="col-lg-12">
              <div className="job-post-card job-post-card-ml">
                <div className="job-post-img">
                  <a href="job-details.html">
                    <img src="assets/images/recent-job/recent-job12.jpg" alt="Images" />
                  </a>
                </div>
                <h3><a href="job-details.html">Group Marketing Manager</a></h3>
                <div className="content">
                  <div className="content-item">
                    <ul className="content-list">
                      <li><i className="ri-briefcase-line"></i> Design</li>
                      <li><i className="ri-map-pin-line"></i> 305 Hicks St Brooklyn, NY </li>
                      <li><i className="ri-time-line"></i> 3 Weeks Ago</li>
                    </ul>
                    <span>$20 - $30 <b> / Per Hour</b></span>
                  </div>
                  <ul className="content-list2">
                    <li className="time">Full Time</li>
                    <li className="freelance">Freelance</li>
                    <li className="urgent">Urgent</li>
                  </ul>
                </div>
                <button className="bookmark-btn"><i className="ri-bookmark-line"></i></button>
              </div>
            </div>
            <div className="col-lg-12">
              <div className="job-post-card job-post-card-ml">
                <div className="job-post-img">
                  <a href="job-details.html">
                    <img src="assets/images/recent-job/recent-job5.jpg" alt="Images" />
                  </a>
                </div>
                <h3><a href="job-details.html">iOS Developer</a></h3>
                <div className="content">
                  <div className="content-item">
                    <ul className="content-list">
                      <li><i className="ri-briefcase-line"></i> IT</li>
                      <li><i className="ri-map-pin-line"></i> 305 Hicks St Brooklyn, NY </li>
                      <li><i className="ri-time-line"></i> 7 Weeks Ago</li>
                    </ul>
                    <span>$50 - $60 <b> / Per Hour</b></span>
                  </div>
                  <ul className="content-list2">
                    <li className="time">Full Time</li>
                    <li className="freelance">Freelance</li>
                    <li className="urgent">Urgent</li>
                  </ul>
                </div>
                <button className="bookmark-btn"><i className="ri-bookmark-line"></i></button>
              </div>
            </div>
            <div className="col-lg-12 text-center">
              <div className="browse-btn">
                <a href="job-listing.html"> Browse All Job Post <i className="ri-arrow-right-s-line"></i></a>
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* Pricing Area */}
      <div className="pricing-area pt-100 pb-70">
        <div className="container">
          <div className="row align-items-center justify-content-center">
            <div className="col-lg-6">
              <div className="pricing-content pr-20">
                <div className="section-title">
                  <h2>Choose Your Best <b>Pricing Plan</b></h2>
                  <div className="bar"></div>
                  <p>Lorem ipsum dolor sit amet, et alii clita tritani per. Vix ut vidisse pertinacia, ius ut maiorum omittam. Duis ludus no mea, te has delenit maiorum, an ius admodum constituto. Nostrud urbanitas.</p>
                </div>
                <ul>
                  <li><img src="assets/images/user-img/user-img1.jpg" alt="Images" /></li>
                  <li><img src="assets/images/user-img/user-img2.jpg" alt="Images" /></li>
                  <li><img src="assets/images/user-img/user-img3.jpg" alt="Images" /></li>
                  <li><img src="assets/images/user-img/user-img4.jpg" alt="Images" /></li>
                  <li><img src="assets/images/user-img/user-img5.jpg" alt="Images" /></li>
                  <li className="title"> 2,000+ Top Employer </li>
                </ul>
              </div>
            </div>
            <div className="col-lg-3 col-sm-6">
              <div className="pricing-card">
                <div className="pricing-title">
                  <h2>Extended Plan</h2>
                  <span className="sub-title">Monthly Package</span>
                </div>
                <h3>$250<span>/Month</span></h3>
                <ul>
                  <li>8 Featured Job</li>
                  <li>20 Job Post Monthly</li>
                  <li>Displayed for 30 Days</li>
                  <li>Support 24/7</li>
                  <li className="active">Full Service</li>
                </ul>
                <div className="price-btn-area">
                  <a href="pricing.html" className="price-btn">Purchase <i className="ri-shopping-cart-2-line"></i></a>
                </div>
              </div>
            </div>
            <div className="col-lg-3 col-sm-6">
              <div className="pricing-card">
                <div className="pricing-title">
                  <h2>Premium Plan</h2>
                  <span className="sub-title">Yearly Package</span>
                </div>
                <h3>$500<span>/Yearly</span></h3>
                <ul>
                  <li>10 Featured Job</li>
                  <li>20 Job Post Per Monthly</li>
                  <li>Displayed for 30 Days</li>
                  <li>Support 24/7</li>
                  <li className="active">Full Service</li>
                </ul>
                <div className="price-btn-area">
                  <a href="pricing.html" className="price-btn">Purchase <i className="ri-shopping-cart-2-line"></i></a>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* Company Area */}
      <div className="company-area pt-100 pb-70">
        <div className="container">
          <div className="row align-items-center">
            <div className="col-lg-8">
              <div className="section-title">
                <h2>Top Company Registered</h2>
                <div className="bar"></div>
                <p>We collect reviews from our users so you can get an honest opinion of what an experience with our website is really like!</p>
              </div>
            </div>
            <div className="col-lg-4">
              <div className="browse-btn float-end">
                <a href="company.html"> Browse All Companies <i className="ri-arrow-right-s-line"></i></a>
              </div>
            </div>
          </div>
          <div className="row pt-45">
            <div className="col-lg-3 col-6">
              <div className="company-card">
                <img src="assets/images/company/company-img3.jpg" alt="Images" />
                <h3><a href="company-details.html">Technology IT</a></h3>
                <p><i className="ri-map-pin-line"></i> St Brooklyn</p>
                <a href="company-details.html" className="company-btn">Open Jobs - (40)</a>
              </div>
            </div>
            <div className="col-lg-3 col-6">
              <div className="company-card">
                <img src="assets/images/company/company-img2.jpg" alt="Images" />
                <h3><a href="company-details.html">Code Agency</a></h3>
                <p><i className="ri-map-pin-line"></i> New York</p>
                <a href="company-details.html" className="company-btn">Open Jobs - (20)</a>
              </div>
            </div>
            <div className="col-lg-3 col-6">
              <div className="company-card">
                <img src="assets/images/company/company-img5.jpg" alt="Images" />
                <h3><a href="company-details.html">Gradient House</a></h3>
                <p><i className="ri-map-pin-line"></i> Dallas, TX</p>
                <a href="company-details.html" className="company-btn">Open Jobs - (45)</a>
              </div>
            </div>
            <div className="col-lg-3 col-6">
              <div className="company-card">
                <img src="assets/images/company/company-img4.jpg" alt="Images" />
                <h3><a href="company-details.html">99 Code</a></h3>
                <p><i className="ri-map-pin-line"></i> Huston, TX</p>
                <a href="company-details.html" className="company-btn">Open Jobs - (15)</a>
              </div>
            </div>
            <div className="col-lg-3 col-6">
              <div className="company-card">
                <img src="assets/images/company/company-img1.jpg" alt="Images" />
                <h3><a href="company-details.html">Theme Desk</a></h3>
                <p><i className="ri-map-pin-line"></i> Atlanta, GA</p>
                <a href="company-details.html" className="company-btn">Open Jobs - (45)</a>
              </div>
            </div>
            <div className="col-lg-3 col-6">
              <div className="company-card">
                <img src="assets/images/company/company-img6.jpg" alt="Images" />
                <h3><a href="company-details.html">Nixon</a></h3>
                <p><i className="ri-map-pin-line"></i> New York</p>
                <a href="company-details.html" className="company-btn">Open Jobs - (40)</a>
              </div>
            </div>
            <div className="col-lg-3 col-6">
              <div className="company-card">
                <img src="assets/images/company/company-img7.jpg" alt="Images" />
                <h3><a href="company-details.html">Jaint Mart</a></h3>
                <p><i className="ri-map-pin-line"></i> Dallas, TX</p>
                <a href="company-details.html" className="company-btn">Open Jobs - (60)</a>
              </div>
            </div>
            <div className="col-lg-3 col-6">
              <div className="company-card">
                <img src="assets/images/company/company-img8.jpg" alt="Images" />
                <h3><a href="company-details.html">Silkom Tech</a></h3>
                <p><i className="ri-map-pin-line"></i> St Brooklyn</p>
                <a href="company-details.html" className="company-btn">Open Jobs - (20)</a>
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* Testimonials Area */}
      <div className="testimonials-area pt-100 pb-100">
        <div className="container">
          <div className="section-title text-center">
            <h2>Reviews From Our Users</h2>
            <div className="bar m-auto"></div>
            <p className="m-auto">We collect reviews from our users so you can get an honest opinion of what an experience with our website are really like!</p>
          </div>
          <div className="testimonials-slider owl-carousel owl-theme pt-45">
            <div className="testimonials-item">
              <div className="content">
                <div className="content-img">
                  <img src="assets/images/testimonials/testimonials-img1.jpg" alt="Images" />
                  <div className="line1"></div>
                  <div className="line2"></div>
                </div>
                <h3>Thomas Albedin</h3>
                <span>IT Manager</span>
              </div>
              <p>We collect reviews from our users so you can get an honest opinion of what an experience with our webebsite are really like. We collect reviews from our users so you can get an honest opinion of what an here lorem experience with.</p>
              <div className="rating">
                <i className="ri-star-fill"></i>
                <i className="ri-star-fill"></i>
                <i className="ri-star-fill"></i>
                <i className="ri-star-fill"></i>
                <i className="ri-star-fill"></i>
              </div>
            </div>
            <div className="testimonials-item">
              <div className="content">
                <div className="content-img">
                  <img src="assets/images/testimonials/testimonials-img2.jpg" alt="Images" />
                  <div className="line1"></div>
                  <div className="line2"></div>
                </div>
                <h3>Camelia Rose</h3>
                <span>Digital Marketer</span>
              </div>
              <p>We collect reviews from our users so you can get an honest opinion of what an experience with our webebsite are really like. We collect reviews from our users so you can get an honest opinion of what an here lorem experience with.</p>
              <div className="rating">
                <i className="ri-star-fill"></i>
                <i className="ri-star-fill"></i>
                <i className="ri-star-fill"></i>
                <i className="ri-star-fill"></i>
                <i className="ri-star-fill"></i>
              </div>
            </div>
            <div className="testimonials-item">
              <div className="content">
                <div className="content-img">
                  <img src="assets/images/testimonials/testimonials-img3.jpg" alt="Images" />
                  <div className="line1"></div>
                  <div className="line2"></div>
                </div>
                <h3>John Carter</h3>
                <span>UI/UX Designer</span>
              </div>
              <p>We collect reviews from our users so you can get an honest opinion of what an experience with our webebsite are really like. We collect reviews from our users so you can get an honest opinion of what an here lorem experience with.</p>
              <div className="rating">
                <i className="ri-star-fill"></i>
                <i className="ri-star-fill"></i>
                <i className="ri-star-fill"></i>
                <i className="ri-star-fill"></i>
                <i className="ri-star-fill"></i>
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* Blog Area */}
      <div className="blog-area pt-100 pb-70">
        <div className="container">
          <div className="row align-items-center">
            <div className="col-lg-8">
              <div className="section-title">
                <h2>From Our Blogs</h2>
                <div className="bar"></div>
                <p>We collect reviews from our users so you can get an honest opinion of what an experience with our website are really like!</p>
              </div>
            </div>
            <div className="col-lg-4">
              <div className="browse-btn float-end">
                <a href="blog-1.html"> Browse More Blog <i className="ri-arrow-right-s-line"></i></a>
              </div>
            </div>
          </div>
          <div className="row pt-45 justify-content-center">
            <div className="col-lg-4 col-md-6">
              <div className="blog-card">
                <div className="blog-img">
                  <a href="blog-details.html">
                    <img src="assets/images/blog/blog-img1.jpg" alt="Blog Images" />
                  </a>
                  <a href="tags.html" className="tag" target="_blank">
                    Job Board
                  </a>
                </div>
                <div className="content">
                  <ul>
                    <li>
                      <i className="ri-time-line"></i> June 14, 2021
                    </li>
                    <li>
                      <i className="ri-mail-line"></i> 02 Comments
                    </li>
                  </ul>
                  <h3><a href="blog-details.html">How to Make a Perfect Cv That Attracts the Attention</a></h3>
                </div>
              </div>
            </div>
            <div className="col-lg-4 col-md-6">
              <div className="blog-card">
                <div className="blog-img">
                  <a href="blog-details.html">
                    <img src="assets/images/blog/blog-img2.jpg" alt="Blog Images" />
                  </a>
                  <a href="tags.html" className="tag" target="_blank">
                    Marketing
                  </a>
                </div>
                <div className="content">
                  <ul>
                    <li>
                      <i className="ri-time-line"></i> June 16, 2021
                    </li>
                    <li>
                      <i className="ri-mail-line"></i> 03 Comments
                    </li>
                  </ul>
                  <h3><a href="blog-details.html">Out Bound Marketing to Get the Job You Want Within 72 Days</a></h3>
                </div>
              </div>
            </div>
            <div className="col-lg-4 col-md-6">
              <div className="blog-card">
                <div className="blog-img">
                  <a href="blog-details.html">
                    <img src="assets/images/blog/blog-img3.jpg" alt="Blog Images" />
                  </a>
                  <a href="tags.html" className="tag" target="_blank">
                    Job Board
                  </a>
                </div>
                <div className="content">
                  <ul>
                    <li>
                      <i className="ri-time-line"></i> June 17, 2021
                    </li>
                    <li>
                      <i className="ri-mail-line"></i> 02 Comments
                    </li>
                  </ul>
                  <h3><a href="blog-details.html">Your social media accounts will be your new CV</a></h3>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* Let Employers Find You */}
      <div className="employers-cv-area">
        <div className="container">
          <div className="employers-cv-bg">
            <div className="row align-items-center">
              <div className="col-lg-8">
                <div className="employers-cv-content">
                  <h2>Let Employers Find You</h2>
                  <div className="bar"></div>
                  <p>Advertise your jobs to millions of monthly users and search 15.8 million CV in our database.</p>
                </div>
              </div>
              <div className="col-lg-4">
                <div className="employers-cv-btn">
                  <a href="/cdn-cgi/l/email-protection#12717d7c6673716652687d70777c3c717d7f">Upload Your CV</a>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* Footer */}
      <footer className="footer-area">
        <div className="container">
          <div className="footer-top pt-100 pb-70">
            <div className="row">
              <div className="col-lg-3 col-sm-6 col-md-6">
                <div className="footer-widget">
                  <div className="footer-logo">
                    <a href="index.html">
                      <img src="assets/images/logo.png" alt="Footer Logo" />
                    </a>
                  </div>
                  <ul className="footer-contact-list">
                    <li>
                      <i className="ri-map-pin-line"></i>
                      <div className="content">
                        <a href="https://goo.gl/maps/muUSCY6FnusqhrGK9" target="_blank" rel="noopener noreferrer">
                          30 N Gould St Ste R Sheridan, WY 82801
                        </a>
                      </div>
                    </li>
                    <li>
                      <i className="ri-mail-line"></i>
                      <div className="content">
                        <a href="mailto:support@flexes.work">
                          support@flexes.work
                        </a>
                      </div>
                    </li>
                    <li>
                      <i className="ri-phone-line"></i>
                      <div className="content">
                        <a href="tel:+16072470187">
                          +1 607 2470 187
                        </a>
                      </div>
                    </li>
                  </ul>
                </div>
              </div>
              <div className="col-lg-2 col-sm-6 col-md-6">
                <div className="footer-widget">
                  <h3>For Employer</h3>
                  <ul className="footer-list">
                    <li>
                      <a href="candidates-listing.html" target="_blank" rel="noopener noreferrer">
                        Browse Candidates
                      </a>
                    </li>
                    <li>
                      <a href="dashboard.html" target="_blank" rel="noopener noreferrer">
                        Employer Dashboard
                      </a>
                    </li>
                    <li>
                      <a href="dashboard-packages.html" target="_blank" rel="noopener noreferrer">
                        Job Packages
                      </a>
                    </li>
                    <li>
                      <a href="job-listing.html" target="_blank" rel="noopener noreferrer">
                        Jobs Featured
                      </a>
                    </li>
                    <li>
                      <a href="post-job.html" target="_blank" rel="noopener noreferrer">
                        Post A Job
                      </a>
                    </li>
                  </ul>
                </div>
              </div>
              <div className="col-lg-3 col-sm-6 col-md-4">
                <div className="footer-widget ps-5">
                  <h3>For Candidates</h3>
                  <ul className="footer-list">
                    <li>
                      <a href="job-listing.html" target="_blank" rel="noopener noreferrer">
                        Browse Jobs
                      </a>
                    </li>
                    <li>
                      <a href="dashboard-submit-resume.html" target="_blank" rel="noopener noreferrer">
                        Submit Resume
                      </a>
                    </li>
                    <li>
                      <a href="dashboard.html" target="_blank" rel="noopener noreferrer">
                        Candidates Dashboard
                      </a>
                    </li>
                    <li>
                      <a href="job-listing.html" target="_blank" rel="noopener noreferrer">
                        Browse Categories
                      </a>
                    </li>
                    <li>
                      <a href="candidates-listing.html" target="_blank" rel="noopener noreferrer">
                        Candidate Listing
                      </a>
                    </li>
                  </ul>
                </div>
              </div>
              <div className="col-lg-2 col-sm-6 col-md-4">
                <div className="footer-widget">
                  <h3>Company</h3>
                  <ul className="footer-list">
                    <li>
                      <a href="about.html" target="_blank" rel="noopener noreferrer">
                        About Us
                      </a>
                    </li>
                    <li>
                      <a href="contact.html" target="_blank" rel="noopener noreferrer">
                        Contact Us
                      </a>
                    </li>
                    <li>
                      <a href="terms-condition.html" target="_blank" rel="noopener noreferrer">
                        Terms & Conditions
                      </a>
                    </li>
                    <li>
                      <a href="privacy-policy.html" target="_blank" rel="noopener noreferrer">
                        Privacy Policy
                      </a>
                    </li>
                    <li>
                      <a href="candidates-listing.html" target="_blank" rel="noopener noreferrer">
                        Candidate Listing
                      </a>
                    </li>
                  </ul>
                </div>
              </div>
              <div className="col-lg-2 col-sm-6 col-md-4">
                <div className="footer-widget">
                  <h3>Quick Links</h3>
                  <ul className="footer-list">
                    <li>
                      <a href="job-listing.html" target="_blank" rel="noopener noreferrer">
                        Jobs Listing
                      </a>
                    </li>
                    <li>
                      <a href="employers-listing.html" target="_blank" rel="noopener noreferrer">
                        Employer Listing
                      </a>
                    </li>
                    <li>
                      <a href="post-job.html" target="_blank" rel="noopener noreferrer">
                        Post New Job
                      </a>
                    </li>
                    <li>
                      <a href="employers-listing.html" target="_blank" rel="noopener noreferrer">
                        All Employers
                      </a>
                    </li>
                    <li>
                      <a href="job-listing.html" target="_blank" rel="noopener noreferrer">
                        Featured Jobs
                      </a>
                    </li>
                  </ul>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div className="copyright-area">
          <div className="container">
            <div className="row align-items-center">
              <div className="col-lg-6 col-md-7">
                <div className="copy-right-text">
                  <p>
                    © 2021 Flexes. All Rights Reserved by
                    <a href="http://www.flexes.work" target="_blank" rel="noopener noreferrer"> Flexes</a>
                  </p>
                </div>
              </div>
              <div className="col-lg-6 col-md-5">
                {/* Social links commented out in original */}
              </div>
            </div>
          </div>
        </div>
      </footer>
    </>
  );
};

export default NewHomePage;
