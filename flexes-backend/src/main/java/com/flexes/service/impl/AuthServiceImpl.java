package com.flexes.service.impl;

import com.flexes.dto.auth.LoginRequest;
import com.flexes.dto.auth.LoginResponse;
import com.flexes.dto.auth.RegisterRequest;
import com.flexes.entity.Candidate;
import com.flexes.entity.Employer;
import com.flexes.entity.User;
import com.flexes.exception.BusinessException;
import com.flexes.repository.CandidateRepository;
import com.flexes.repository.EmployerRepository;
import com.flexes.security.JwtTokenProvider;
import com.flexes.security.UserPrincipal;
import com.flexes.service.AuthService;
import com.flexes.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 认证服务实现类
 * 
 * @author Flexes Team
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final CandidateRepository candidateRepository;
    private final EmployerRepository employerRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(RegisterRequest request) {
        log.info("用户注册: {}", request.getEmail());
        
        // 验证密码确认
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new BusinessException("密码确认不匹配");
        }
        
        // 检查邮箱是否已存在
        if (userService.existsByEmail(request.getEmail())) {
            throw new BusinessException("邮箱已被注册: " + request.getEmail());
        }
        
        // 创建用户
        User user = User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .role(request.getRole())
                .status(User.UserStatus.ACTIVE)
                .emailVerified(false)
                .build();
        
        User savedUser = userService.createUser(user);
        
        // 根据角色创建对应的详细信息
        if (request.getRole() == User.UserRole.CANDIDATE) {
            createCandidateProfile(savedUser, request);
        } else if (request.getRole() == User.UserRole.EMPLOYER) {
            createEmployerProfile(savedUser, request);
        }
        
        log.info("用户注册成功: ID={}, Email={}", savedUser.getUserId(), savedUser.getEmail());
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        log.info("用户登录: {}", request.getEmail());
        
        // 认证用户
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        // 生成令牌
        String accessToken = jwtTokenProvider.generateToken(authentication);
        String refreshToken = jwtTokenProvider.generateRefreshToken(authentication);
        
        // 更新最后登录时间
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        userService.updateLastLoginTime(userPrincipal.getId());
        
        // 构建响应
        LoginResponse response = LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(86400L) // 24小时
                .userId(userPrincipal.getId())
                .email(userPrincipal.getEmail())
                .role(User.UserRole.valueOf(userPrincipal.getRole()))
                .emailVerified(userPrincipal.isEmailVerified())
                .build();
        
        // 获取用户详细信息
        User user = userService.findById(userPrincipal.getId()).orElse(null);
        if (user != null) {
            if (user.isCandidate() && user.getCandidate() != null) {
                response.setName(user.getCandidate().getName());
                response.setAvatarUrl(user.getCandidate().getAvatarUrl());
            } else if (user.isEmployer() && user.getEmployer() != null) {
                response.setName(user.getEmployer().getContactPerson());
            }
        }
        
        log.info("用户登录成功: ID={}", userPrincipal.getId());
        return response;
    }

    @Override
    public LoginResponse refreshToken(String refreshToken) {
        log.info("刷新令牌");
        
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new BusinessException("无效的刷新令牌");
        }
        
        String username = jwtTokenProvider.getUsernameFromToken(refreshToken);
        User user = userService.findByEmail(username)
                .orElseThrow(() -> new BusinessException("用户不存在"));
        
        // 创建新的认证对象
        UserPrincipal userPrincipal = UserPrincipal.create(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userPrincipal, null, userPrincipal.getAuthorities());
        
        // 生成新令牌
        String newAccessToken = jwtTokenProvider.generateToken(authentication);
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(authentication);
        
        LoginResponse response = LoginResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .tokenType("Bearer")
                .expiresIn(86400L)
                .userId(user.getUserId())
                .email(user.getEmail())
                .role(user.getRole())
                .emailVerified(user.isEmailVerified())
                .build();
        
        log.info("令牌刷新成功: UserID={}", user.getUserId());
        return response;
    }

    @Override
    public void logout(String token) {
        log.info("用户登出");
        // TODO: 将令牌加入黑名单（Redis）
        // 这里可以实现令牌黑名单机制
        log.info("用户登出成功");
    }

    @Override
    public void verifyEmail(String token) {
        log.info("验证邮箱");
        // TODO: 实现邮箱验证逻辑
        // 1. 验证令牌有效性
        // 2. 获取用户信息
        // 3. 更新邮箱验证状态
        throw new BusinessException("邮箱验证功能暂未实现");
    }

    @Override
    public void forgotPassword(String email) {
        log.info("忘记密码: {}", email);
        // TODO: 实现忘记密码逻辑
        // 1. 检查用户是否存在
        // 2. 生成重置令牌
        // 3. 发送重置邮件
        throw new BusinessException("忘记密码功能暂未实现");
    }

    @Override
    public void resetPassword(String token, String newPassword) {
        log.info("重置密码");
        // TODO: 实现重置密码逻辑
        // 1. 验证重置令牌
        // 2. 更新用户密码
        throw new BusinessException("重置密码功能暂未实现");
    }

    /**
     * 创建求职者档案
     */
    private void createCandidateProfile(User user, RegisterRequest request) {
        Candidate candidate = Candidate.builder()
                .userId(user.getUserId())
                .name(request.getName())
                .phone(request.getPhone())
                .experienceYears(0)
                .jobStatus(Candidate.JobStatus.OPEN_TO_OPPORTUNITIES)
                .build();
        
        candidateRepository.save(candidate);
        log.info("求职者档案创建成功: UserID={}", user.getUserId());
    }

    /**
     * 创建雇主档案
     */
    private void createEmployerProfile(User user, RegisterRequest request) {
        Employer employer = Employer.builder()
                .userId(user.getUserId())
                .companyName(request.getCompanyName() != null ? request.getCompanyName() : "未设置")
                .industry(request.getIndustry())
                .website(request.getWebsite())
                .contactPerson(request.getName())
                .contactPhone(request.getPhone())
                .verified(false)
                .build();
        
        employerRepository.save(employer);
        log.info("雇主档案创建成功: UserID={}", user.getUserId());
    }
}
