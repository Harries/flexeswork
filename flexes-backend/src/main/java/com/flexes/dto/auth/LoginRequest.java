package com.flexes.dto.auth;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * 登录请求DTO
 * 
 * @author Flexes Team
 * @version 1.0.0
 */
@Data
public class LoginRequest {

    @Email(message = "邮箱格式不正确")
    @NotBlank(message = "邮箱不能为空")
    private String email;

    @NotBlank(message = "密码不能为空")
    private String password;

    private Boolean rememberMe = false;
}
