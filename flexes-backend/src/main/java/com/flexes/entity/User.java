package com.flexes.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 用户实体类
 * 
 * @author Flexes Team
 * @version 1.0.0
 */
@Entity
@Table(name = "users", indexes = {
    @Index(name = "idx_email", columnList = "email"),
    @Index(name = "idx_role", columnList = "role"),
    @Index(name = "idx_status", columnList = "status"),
    @Index(name = "idx_created_at", columnList = "createdAt")
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

    @Email(message = "邮箱格式不正确")
    @NotBlank(message = "邮箱不能为空")
    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @NotBlank(message = "密码不能为空")
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @NotNull(message = "用户角色不能为空")
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private UserRole role = UserRole.CANDIDATE;

    @NotNull(message = "账户状态不能为空")
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private UserStatus status = UserStatus.ACTIVE;

    @Column(name = "email_verified")
    @Builder.Default
    private Boolean emailVerified = false;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    // 关联关系
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Candidate candidate;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Employer employer;

    /**
     * 用户角色枚举
     */
    public enum UserRole {
        ADMIN(0, "管理员"),
        CANDIDATE(1, "求职者"),
        EMPLOYER(2, "雇主");

        private final int code;
        private final String description;

        UserRole(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public int getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }

        public static UserRole fromCode(int code) {
            for (UserRole role : values()) {
                if (role.code == code) {
                    return role;
                }
            }
            throw new IllegalArgumentException("未知的用户角色代码: " + code);
        }
    }

    /**
     * 用户状态枚举
     */
    public enum UserStatus {
        DISABLED(0, "禁用"),
        ACTIVE(1, "正常"),
        PENDING_VERIFICATION(2, "待验证");

        private final int code;
        private final String description;

        UserStatus(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public int getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }

        public static UserStatus fromCode(int code) {
            for (UserStatus status : values()) {
                if (status.code == code) {
                    return status;
                }
            }
            throw new IllegalArgumentException("未知的用户状态代码: " + code);
        }
    }

    /**
     * 检查用户是否为管理员
     */
    public boolean isAdmin() {
        return UserRole.ADMIN.equals(this.role);
    }

    /**
     * 检查用户是否为求职者
     */
    public boolean isCandidate() {
        return UserRole.CANDIDATE.equals(this.role);
    }

    /**
     * 检查用户是否为雇主
     */
    public boolean isEmployer() {
        return UserRole.EMPLOYER.equals(this.role);
    }

    /**
     * 检查用户是否激活
     */
    public boolean isActive() {
        return UserStatus.ACTIVE.equals(this.status);
    }

    /**
     * 检查邮箱是否已验证
     */
    public boolean isEmailVerified() {
        return Boolean.TRUE.equals(this.emailVerified);
    }
}
