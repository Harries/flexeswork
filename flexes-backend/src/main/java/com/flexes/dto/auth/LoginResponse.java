package com.flexes.dto.auth;

import com.flexes.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录响应DTO
 * 
 * @author Flexes Team
 * @version 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
    private Long expiresIn;
    
    // 用户信息
    private Long userId;
    private String email;
    private User.UserRole role;
    private String name;
    private String avatarUrl;
    private Boolean emailVerified;
}
