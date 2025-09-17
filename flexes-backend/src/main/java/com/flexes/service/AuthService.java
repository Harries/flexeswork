package com.flexes.service;

import com.flexes.dto.auth.LoginRequest;
import com.flexes.dto.auth.LoginResponse;
import com.flexes.dto.auth.RegisterRequest;

/**
 * 认证服务接口
 * 
 * @author Flexes Team
 * @version 1.0.0
 */
public interface AuthService {

    /**
     * 用户注册
     */
    void register(RegisterRequest request);

    /**
     * 用户登录
     */
    LoginResponse login(LoginRequest request);

    /**
     * 刷新令牌
     */
    LoginResponse refreshToken(String refreshToken);

    /**
     * 用户登出
     */
    void logout(String token);

    /**
     * 验证邮箱
     */
    void verifyEmail(String token);

    /**
     * 忘记密码
     */
    void forgotPassword(String email);

    /**
     * 重置密码
     */
    void resetPassword(String token, String newPassword);
}
