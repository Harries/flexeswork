package com.flexes.dto.auth;

import com.flexes.entity.User;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 注册请求DTO
 * 
 * @author Flexes Team
 * @version 1.0.0
 */
@Data
public class RegisterRequest {

    @Email(message = "邮箱格式不正确")
    @NotBlank(message = "邮箱不能为空")
    private String email;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度必须在6-20位之间")
    private String password;

    @NotBlank(message = "确认密码不能为空")
    private String confirmPassword;

    @NotNull(message = "用户角色不能为空")
    private User.UserRole role;

    @NotBlank(message = "姓名不能为空")
    private String name;

    private String phone;

    // 雇主注册时的公司信息
    private String companyName;
    private String industry;
    private String website;
}
