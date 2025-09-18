# Flexes 后端技术规范

## 📋 概述

本文档定义了 Flexes 远程工程招聘平台后端开发的技术规范和最佳实践，确保代码质量、可维护性和团队协作效率。

## 🏗️ 技术栈

### 核心技术
- **Java**: 8 (JDK 1.8) - 编程语言
- **Spring Boot**: 2.7.18 - 核心框架
- **Spring Security**: 安全框架
- **Spring Data JPA**: 数据访问层
- **MySQL**: 8.0+ - 关系型数据库
- **Redis**: 6.0+ - 缓存和会话存储
- **Maven**: 3.8+ - 项目构建工具

### 开发工具
- **Lombok**: 1.18.30 - 代码生成
- **MapStruct**: 1.5.5.Final - 对象映射
- **JWT**: 0.11.5 - 身份认证
- **SpringDoc OpenAPI**: 1.7.0 - API文档
- **HikariCP**: 数据库连接池
- **TestContainers**: 1.19.3 - 集成测试

## 📁 项目结构规范

```
src/main/java/com/flexes/
├── FlexesApplication.java          # 主应用类
├── config/                         # 配置类
│   ├── SecurityConfig.java         # 安全配置
│   ├── RedisConfig.java           # Redis配置
│   ├── JpaConfig.java             # JPA配置
│   └── SwaggerConfig.java         # API文档配置
├── controller/                     # 控制器层
│   ├── AuthController.java         # 认证控制器
│   ├── UserController.java         # 用户控制器
│   ├── JobController.java          # 职位控制器
│   └── ApplicationController.java  # 申请控制器
├── dto/                           # 数据传输对象
│   ├── ApiResponse.java           # 统一响应格式
│   ├── auth/                      # 认证相关DTO
│   ├── user/                      # 用户相关DTO
│   └── job/                       # 职位相关DTO
├── entity/                        # 实体类
│   ├── BaseEntity.java            # 基础实体
│   ├── User.java                  # 用户实体
│   ├── Job.java                   # 职位实体
│   └── JobApplication.java        # 申请实体
├── exception/                     # 异常处理
│   ├── BusinessException.java     # 业务异常
│   ├── ResourceNotFoundException.java # 资源未找到异常
│   └── GlobalExceptionHandler.java # 全局异常处理器
├── repository/                    # 数据访问层
│   ├── UserRepository.java        # 用户仓库
│   ├── JobRepository.java         # 职位仓库
│   └── JobApplicationRepository.java # 申请仓库
├── service/                       # 服务层接口
│   ├── AuthService.java           # 认证服务接口
│   ├── UserService.java           # 用户服务接口
│   └── JobService.java            # 职位服务接口
├── service/impl/                  # 服务层实现
│   ├── AuthServiceImpl.java       # 认证服务实现
│   ├── UserServiceImpl.java       # 用户服务实现
│   └── JobServiceImpl.java        # 职位服务实现
├── security/                      # 安全相关
│   ├── JwtTokenProvider.java      # JWT令牌提供者
│   ├── JwtAuthenticationFilter.java # JWT认证过滤器
│   ├── UserPrincipal.java         # 用户主体
│   └── CustomUserDetailsService.java # 用户详情服务
└── util/                          # 工具类
    ├── DateUtils.java             # 日期工具
    ├── ValidationUtils.java       # 验证工具
    └── FileUtils.java             # 文件工具
```

## 🎯 编码规范

### 1. 命名规范

#### 包命名
- 全部小写，使用点分隔
- 遵循域名反向规则：`com.flexes.module`

#### 类命名
```java
// ✅ 推荐 - PascalCase
public class UserService {}
public class JobApplicationController {}
public class BusinessException {}

// ❌ 不推荐
public class userService {}
public class jobapplicationcontroller {}
```

#### 方法命名
```java
// ✅ 推荐 - camelCase，动词开头
public User findUserById(Long id) {}
public void updateUserProfile(User user) {}
public boolean isEmailVerified(String email) {}

// ❌ 不推荐
public User FindUserById(Long id) {}
public void UpdateUserProfile(User user) {}
```

#### 变量命名
```java
// ✅ 推荐 - camelCase
private String userName;
private List<Job> activeJobs;
private boolean isEmailVerified;

// ❌ 不推荐
private String user_name;
private List<Job> ActiveJobs;
private boolean EmailVerified;
```

#### 常量命名
```java
// ✅ 推荐 - UPPER_SNAKE_CASE
public static final String DEFAULT_ROLE = "CANDIDATE";
public static final int MAX_LOGIN_ATTEMPTS = 5;
public static final long JWT_EXPIRATION_TIME = 86400000L;
```

### 2. 注解使用规范

#### 实体类注解
```java
@Entity
@Table(name = "users", indexes = {
    @Index(name = "idx_email", columnList = "email"),
    @Index(name = "idx_status", columnList = "status")
})
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;
    
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 100, message = "密码长度必须在6-100之间")
    @Column(name = "password", nullable = false)
    private String password;
}
```

#### 控制器注解
```java
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userService.isOwner(#id, authentication.name)")
    public ResponseEntity<ApiResponse<UserDTO>> getUser(@PathVariable Long id) {
        // 实现逻辑
    }
    
    @PostMapping
    @Valid
    public ResponseEntity<ApiResponse<UserDTO>> createUser(@RequestBody @Valid CreateUserRequest request) {
        // 实现逻辑
    }
}
```

#### 服务类注解
```java
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    
    @Transactional
    @Override
    public User createUser(User user) {
        // 实现逻辑
    }
    
    @Cacheable(value = "users", key = "#id")
    @Override
    public User findById(Long id) {
        // 实现逻辑
    }
}
```

### 3. 异常处理规范

#### 自定义异常
```java
// 业务异常基类
public class BusinessException extends RuntimeException {
    private final HttpStatus status;
    
    public BusinessException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }
    
    public BusinessException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
    
    public HttpStatus getStatus() {
        return status;
    }
}

// 具体业务异常
public class ResourceNotFoundException extends BusinessException {
    public ResourceNotFoundException(String resource, Object id) {
        super(String.format("%s not found with id: %s", resource, id), HttpStatus.NOT_FOUND);
    }
}
```

#### 全局异常处理
```java
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException e) {
        log.warn("业务异常: {}", e.getMessage());
        return ResponseEntity.status(e.getStatus())
                .body(ApiResponse.error(e.getMessage()));
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationException(
            MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> 
            errors.put(error.getField(), error.getDefaultMessage())
        );
        
        return ResponseEntity.badRequest()
                .body(ApiResponse.error("参数验证失败", errors));
    }
}
```

## 🗄️ 数据访问层规范

### 1. Repository 接口
```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // ✅ 推荐 - 使用方法名查询
    Optional<User> findByEmail(String email);
    
    List<User> findByStatusAndRole(User.UserStatus status, User.UserRole role);
    
    boolean existsByEmail(String email);
    
    // ✅ 推荐 - 使用@Query注解进行复杂查询
    @Query("SELECT u FROM User u WHERE u.createdAt >= :startDate AND u.status = :status")
    List<User> findActiveUsersCreatedAfter(@Param("startDate") LocalDateTime startDate, 
                                          @Param("status") User.UserStatus status);
    
    // ✅ 推荐 - 使用原生SQL查询（必要时）
    @Query(value = "SELECT COUNT(*) FROM users WHERE DATE(created_at) = CURRENT_DATE", 
           nativeQuery = true)
    long countTodayRegistrations();
    
    // ✅ 推荐 - 更新操作
    @Modifying
    @Query("UPDATE User u SET u.lastLoginAt = :loginTime WHERE u.userId = :userId")
    void updateLastLoginTime(@Param("userId") Long userId, @Param("loginTime") LocalDateTime loginTime);
}
```

### 2. 实体关系映射
```java
@Entity
@Table(name = "job_applications")
public class JobApplication extends BaseEntity {
    
    // 多对一关系 - 延迟加载
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", insertable = false, updatable = false)
    @JsonIgnore
    private Job job;
    
    // 多对一关系 - 延迟加载
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", insertable = false, updatable = false)
    @JsonIgnore
    private Candidate candidate;
    
    // 外键字段
    @NotNull(message = "职位ID不能为空")
    @Column(name = "job_id", nullable = false)
    private Long jobId;
    
    @NotNull(message = "候选人ID不能为空")
    @Column(name = "candidate_id", nullable = false)
    private Long candidateId;
}
```

## 🔒 安全规范

### 1. 认证和授权
```java
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .authorizeRequests()
                // 公开接口
                .antMatchers("/auth/**", "/public/**").permitAll()
                // 角色权限控制
                .antMatchers(HttpMethod.POST, "/jobs").hasRole("EMPLOYER")
                .antMatchers(HttpMethod.POST, "/applications").hasRole("CANDIDATE")
                .antMatchers("/admin/**").hasRole("ADMIN")
                // 其他请求需要认证
                .anyRequest().authenticated()
            .and()
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
}
```

### 2. 密码安全
```java
@Service
public class AuthServiceImpl implements AuthService {
    
    private final PasswordEncoder passwordEncoder;
    
    // ✅ 推荐 - 密码加密存储
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    
    // ✅ 推荐 - 密码验证
    public boolean validatePassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
```

### 3. JWT 令牌管理
```java
@Component
public class JwtTokenProvider {
    
    @Value("${flexes.jwt.secret}")
    private String jwtSecret;
    
    @Value("${flexes.jwt.expiration}")
    private long jwtExpirationInMs;
    
    private Key key;
    
    @PostConstruct
    public void init() {
        // ✅ 推荐 - 使用安全的密钥生成
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }
    
    public String generateToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Date expiryDate = new Date(System.currentTimeMillis() + jwtExpirationInMs);
        
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }
}
```

## 📊 服务层规范

### 1. 服务接口设计
```java
public interface UserService {
    
    /**
     * 创建用户
     * @param user 用户信息
     * @return 创建的用户
     * @throws BusinessException 当邮箱已存在时
     */
    User createUser(User user);
    
    /**
     * 根据ID查找用户
     * @param id 用户ID
     * @return 用户信息
     * @throws ResourceNotFoundException 当用户不存在时
     */
    User findById(Long id);
    
    /**
     * 分页查询用户
     * @param pageable 分页参数
     * @param criteria 查询条件
     * @return 用户分页结果
     */
    Page<User> findUsers(Pageable pageable, UserSearchCriteria criteria);
}
```

### 2. 服务实现规范
```java
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Transactional
    @Override
    public User createUser(User user) {
        log.info("创建用户: {}", user.getEmail());
        
        // 参数验证
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new BusinessException("邮箱已被注册: " + user.getEmail());
        }
        
        // 业务逻辑处理
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(User.UserStatus.ACTIVE);
        user.setEmailVerified(false);
        
        User savedUser = userRepository.save(user);
        
        log.info("用户创建成功: ID={}", savedUser.getUserId());
        return savedUser;
    }
    
    @Cacheable(value = "users", key = "#id")
    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
    }
}
```

## 🌐 控制器层规范

### 1. RESTful API 设计
```java
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {
    
    private final UserService userService;
    private final UserMapper userMapper;
    
    @GetMapping
    public ResponseEntity<ApiResponse<Page<UserDTO>>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String search) {
        
        Pageable pageable = PageRequest.of(page, size);
        UserSearchCriteria criteria = UserSearchCriteria.builder()
                .search(search)
                .build();
        
        Page<User> users = userService.findUsers(pageable, criteria);
        Page<UserDTO> userDTOs = users.map(userMapper::toDTO);
        
        return ResponseEntity.ok(ApiResponse.success(userDTOs));
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userService.isOwner(#id, authentication.name)")
    public ResponseEntity<ApiResponse<UserDTO>> getUser(@PathVariable Long id) {
        User user = userService.findById(id);
        UserDTO userDTO = userMapper.toDTO(user);
        return ResponseEntity.ok(ApiResponse.success(userDTO));
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<UserDTO>> createUser(
            @RequestBody @Valid CreateUserRequest request) {
        
        User user = userMapper.toEntity(request);
        User createdUser = userService.createUser(user);
        UserDTO userDTO = userMapper.toDTO(createdUser);
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(userDTO));
    }
}
```

### 2. 统一响应格式
```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    
    private boolean success;
    private String message;
    private T data;
    private String timestamp;
    
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .message("操作成功")
                .data(data)
                .timestamp(LocalDateTime.now().toString())
                .build();
    }
    
    public static <T> ApiResponse<T> error(String message) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .timestamp(LocalDateTime.now().toString())
                .build();
    }
}
```

## 🧪 测试规范

### 1. 单元测试
```java
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("创建用户 - 成功")
    void createUser_Success() {
        // Given
        User user = User.builder()
                .email("test@example.com")
                .password("password123")
                .role(User.UserRole.CANDIDATE)
                .build();

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encoded_password");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // When
        User result = userService.createUser(user);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("test@example.com");
        verify(userRepository).existsByEmail("test@example.com");
        verify(passwordEncoder).encode("password123");
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("创建用户 - 邮箱已存在")
    void createUser_EmailExists_ThrowsException() {
        // Given
        User user = User.builder()
                .email("existing@example.com")
                .password("password123")
                .build();

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        // When & Then
        assertThatThrownBy(() -> userService.createUser(user))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("邮箱已被注册");
    }
}
```

### 2. 集成测试
```java
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Testcontainers
class UserControllerIntegrationTest {

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("flexes_test")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("创建用户 - 集成测试")
    void createUser_IntegrationTest() {
        // Given
        CreateUserRequest request = CreateUserRequest.builder()
                .email("integration@test.com")
                .password("password123")
                .confirmPassword("password123")
                .role(User.UserRole.CANDIDATE)
                .build();

        // When
        ResponseEntity<ApiResponse> response = restTemplate.postForEntity(
                "/api/users", request, ApiResponse.class);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().isSuccess()).isTrue();

        // 验证数据库中的数据
        Optional<User> savedUser = userRepository.findByEmail("integration@test.com");
        assertThat(savedUser).isPresent();
        assertThat(savedUser.get().getRole()).isEqualTo(User.UserRole.CANDIDATE);
    }
}
```

### 3. 测试配置
```properties
# application-test.properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true

# 测试环境JWT配置
flexes.jwt.secret=test-secret-key-for-testing-purposes-only
flexes.jwt.expiration=3600000

# 禁用缓存
spring.cache.type=none

# 日志配置
logging.level.com.flexes=DEBUG
logging.level.org.springframework.security=DEBUG
```

## 🔧 配置管理规范

### 1. 环境配置
```yaml
# application.yml - 通用配置
server:
  port: 8081
  servlet:
    context-path: /api

spring:
  profiles:
    active: dev

  application:
    name: flexes-backend

  jpa:
    hibernate:
      ddl-auto: validate
      naming:
        physical-strategy: org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy
    show-sql: false

# 自定义配置
flexes:
  jwt:
    secret: ${JWT_SECRET:default-secret-change-in-production}
    expiration: ${JWT_EXPIRATION:86400000}

  business:
    daily-application-limit: ${DAILY_APPLICATION_LIMIT:20}
    max-resume-size: ${MAX_RESUME_SIZE:5242880}
```

### 2. 配置类
```java
@ConfigurationProperties(prefix = "flexes")
@Data
@Component
public class FlexesProperties {

    private Jwt jwt = new Jwt();
    private Business business = new Business();
    private File file = new File();

    @Data
    public static class Jwt {
        private String secret;
        private long expiration;
        private long refreshExpiration;
    }

    @Data
    public static class Business {
        private int dailyApplicationLimit;
        private boolean jobApprovalRequired;
        private long maxResumeSize;
        private List<String> allowedResumeTypes;
    }

    @Data
    public static class File {
        private String uploadPath;
        private String baseUrl;
    }
}
```

## 📊 缓存规范

### 1. Redis 配置
```java
@Configuration
@EnableCaching
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // 设置序列化器
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.activateDefaultTyping(LazyLoadingAwareJavassistProxyFactory.class, ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(objectMapper);

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);

        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(30))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .build();
    }
}
```

### 2. 缓存使用
```java
@Service
public class UserServiceImpl implements UserService {

    @Cacheable(value = "users", key = "#id")
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
    }

    @CacheEvict(value = "users", key = "#user.userId")
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @CacheEvict(value = "users", allEntries = true)
    public void clearUserCache() {
        // 清除所有用户缓存
    }
}
```

## 📝 日志规范

### 1. 日志配置
```yaml
# application.yml
logging:
  level:
    com.flexes: INFO
    org.springframework.security: WARN
    org.hibernate.SQL: WARN
  pattern:
    console: "%clr(%d{HH:mm:ss.SSS}){faint} %clr(%5p) %clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %m%n%wEx"
    file: "%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
  file:
    name: logs/flexes-backend.log
    max-size: 100MB
    max-history: 30
```

### 2. 日志使用规范
```java
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    public User createUser(User user) {
        log.info("创建用户开始: email={}", user.getEmail());

        try {
            // 业务逻辑
            User savedUser = userRepository.save(user);
            log.info("用户创建成功: id={}, email={}", savedUser.getUserId(), savedUser.getEmail());
            return savedUser;
        } catch (Exception e) {
            log.error("用户创建失败: email={}, error={}", user.getEmail(), e.getMessage(), e);
            throw e;
        }
    }

    public User findById(Long id) {
        log.debug("查找用户: id={}", id);

        return userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("用户不存在: id={}", id);
                    return new ResourceNotFoundException("User", id);
                });
    }
}
```

## 🔍 监控和健康检查

### 1. Actuator 配置
```yaml
# application.yml
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  endpoint:
    health:
      show-details: when-authorized
  health:
    redis:
      enabled: true
    db:
      enabled: true
  info:
    env:
      enabled: true
    java:
      enabled: true
    os:
      enabled: true
```

### 2. 自定义健康检查
```java
@Component
public class CustomHealthIndicator implements HealthIndicator {

    private final UserRepository userRepository;

    @Override
    public Health health() {
        try {
            long userCount = userRepository.count();
            return Health.up()
                    .withDetail("userCount", userCount)
                    .withDetail("status", "Database connection is healthy")
                    .build();
        } catch (Exception e) {
            return Health.down()
                    .withDetail("error", e.getMessage())
                    .build();
        }
    }
}
```

## 📚 API 文档规范

### 1. OpenAPI 配置
```java
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Flexes API")
                        .version("1.0.0")
                        .description("Flexes 远程工程招聘平台 API 文档")
                        .contact(new Contact()
                                .name("Flexes Team")
                                .email("dev@flexes.work")
                                .url("https://flexes.work")))
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}
```

### 2. API 文档注解
```java
@RestController
@RequestMapping("/api/users")
@Tag(name = "用户管理", description = "用户相关操作接口")
public class UserController {

    @GetMapping("/{id}")
    @Operation(summary = "获取用户信息", description = "根据用户ID获取用户详细信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "获取成功",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "404", description = "用户不存在"),
            @ApiResponse(responseCode = "403", description = "权限不足")
    })
    public ResponseEntity<ApiResponse<UserDTO>> getUser(
            @Parameter(description = "用户ID", required = true) @PathVariable Long id) {
        // 实现逻辑
    }
}
```

## 🚀 性能优化规范

### 1. 数据库优化
```java
// ✅ 推荐 - 使用批量操作
@Service
public class JobServiceImpl implements JobService {

    @Transactional
    public void batchCreateJobs(List<Job> jobs) {
        int batchSize = 20;
        for (int i = 0; i < jobs.size(); i += batchSize) {
            int end = Math.min(i + batchSize, jobs.size());
            List<Job> batch = jobs.subList(i, end);
            jobRepository.saveAll(batch);

            // 清理持久化上下文
            if (i % batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
    }
}

// ✅ 推荐 - 使用分页查询
@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    @Query("SELECT j FROM Job j WHERE j.status = :status ORDER BY j.createdAt DESC")
    Page<Job> findActiveJobs(@Param("status") Job.JobStatus status, Pageable pageable);
}
```

### 2. 异步处理
```java
@Service
@Slf4j
public class EmailService {

    @Async
    @Retryable(value = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public CompletableFuture<Void> sendWelcomeEmail(String email, String name) {
        try {
            log.info("发送欢迎邮件: email={}", email);
            // 邮件发送逻辑
            return CompletableFuture.completedFuture(null);
        } catch (Exception e) {
            log.error("邮件发送失败: email={}, error={}", email, e.getMessage());
            throw e;
        }
    }
}
```

## 📋 代码审查清单

### 提交前检查
- [ ] 代码通过所有单元测试
- [ ] 代码通过集成测试
- [ ] 遵循命名规范
- [ ] 添加了适当的注释和文档
- [ ] 异常处理完整
- [ ] 日志记录合理
- [ ] 安全性检查通过
- [ ] 性能测试通过
- [ ] API 文档更新

### 安全检查
- [ ] 敏感信息不在代码中硬编码
- [ ] 输入参数进行了验证
- [ ] SQL 注入防护
- [ ] XSS 防护
- [ ] 权限控制正确
- [ ] 密码安全存储

### 性能检查
- [ ] 数据库查询优化
- [ ] 避免 N+1 查询问题
- [ ] 合理使用缓存
- [ ] 异步处理耗时操作
- [ ] 分页查询大数据集
- [ ] 连接池配置合理

## 📚 参考资源

- [Spring Boot 官方文档](https://spring.io/projects/spring-boot)
- [Spring Security 官方文档](https://spring.io/projects/spring-security)
- [Spring Data JPA 官方文档](https://spring.io/projects/spring-data-jpa)
- [MySQL 官方文档](https://dev.mysql.com/doc/)
- [Redis 官方文档](https://redis.io/documentation)
- [JWT 官方文档](https://jwt.io/introduction)
- [OpenAPI 3 规范](https://swagger.io/specification/)

---

**版本**: 1.0.0
**最后更新**: 2025-01-18
**维护者**: Flexes 后端团队
```
