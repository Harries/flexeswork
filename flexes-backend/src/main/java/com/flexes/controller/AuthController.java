package com.flexes.controller;

import com.flexes.dto.ApiResponse;
import com.flexes.dto.auth.LoginRequest;
import com.flexes.dto.auth.LoginResponse;
import com.flexes.dto.auth.RegisterRequest;
import com.flexes.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 认证控制器
 * 
 * @author Flexes Team
 * @version 1.0.0
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "认证管理", description = "用户注册、登录、登出等认证相关接口")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "新用户注册账户")
    public ResponseEntity<ApiResponse<Void>> register(@Valid @RequestBody RegisterRequest request) {
        log.info("用户注册请求: {}", request.getEmail());
        
        authService.register(request);
        
        return ResponseEntity.ok(ApiResponse.success("注册成功"));
    }

    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "用户登录获取访问令牌")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        log.info("用户登录请求: {}", request.getEmail());
        
        LoginResponse response = authService.login(request);
        
        return ResponseEntity.ok(ApiResponse.success("登录成功", response));
    }

    @PostMapping("/refresh")
    @Operation(summary = "刷新令牌", description = "使用刷新令牌获取新的访问令牌")
    public ResponseEntity<ApiResponse<LoginResponse>> refresh(@RequestParam String refreshToken) {
        log.info("刷新令牌请求");
        
        LoginResponse response = authService.refreshToken(refreshToken);
        
        return ResponseEntity.ok(ApiResponse.success("令牌刷新成功", response));
    }

    @PostMapping("/logout")
    @Operation(summary = "用户登出", description = "用户登出，使令牌失效")
    public ResponseEntity<ApiResponse<Void>> logout(@RequestHeader("Authorization") String token) {
        log.info("用户登出请求");
        
        authService.logout(token);
        
        return ResponseEntity.ok(ApiResponse.success("登出成功"));
    }

    @PostMapping("/verify-email")
    @Operation(summary = "验证邮箱", description = "验证用户邮箱地址")
    public ResponseEntity<ApiResponse<Void>> verifyEmail(@RequestParam String token) {
        log.info("邮箱验证请求");
        
        authService.verifyEmail(token);
        
        return ResponseEntity.ok(ApiResponse.success("邮箱验证成功"));
    }

    @PostMapping("/forgot-password")
    @Operation(summary = "忘记密码", description = "发送重置密码邮件")
    public ResponseEntity<ApiResponse<Void>> forgotPassword(@RequestParam String email) {
        log.info("忘记密码请求: {}", email);
        
        authService.forgotPassword(email);
        
        return ResponseEntity.ok(ApiResponse.success("重置密码邮件已发送"));
    }

    @PostMapping("/reset-password")
    @Operation(summary = "重置密码", description = "使用重置令牌重置密码")
    public ResponseEntity<ApiResponse<Void>> resetPassword(@RequestParam String token, 
                                                          @RequestParam String newPassword) {
        log.info("重置密码请求");
        
        authService.resetPassword(token, newPassword);
        
        return ResponseEntity.ok(ApiResponse.success("密码重置成功"));
    }
}
