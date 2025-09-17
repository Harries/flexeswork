package com.flexes.service;

import com.flexes.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 用户服务接口
 * 
 * @author Flexes Team
 * @version 1.0.0
 */
public interface UserService {

    /**
     * 创建用户
     */
    User createUser(User user);

    /**
     * 根据ID查找用户
     */
    Optional<User> findById(Long userId);

    /**
     * 根据邮箱查找用户
     */
    Optional<User> findByEmail(String email);

    /**
     * 更新用户信息
     */
    User updateUser(User user);

    /**
     * 删除用户
     */
    void deleteUser(Long userId);

    /**
     * 检查邮箱是否存在
     */
    boolean existsByEmail(String email);

    /**
     * 验证用户邮箱
     */
    void verifyEmail(Long userId);

    /**
     * 更新最后登录时间
     */
    void updateLastLoginTime(Long userId);

    /**
     * 修改用户状态
     */
    void updateUserStatus(Long userId, User.UserStatus status);

    /**
     * 修改用户密码
     */
    void updatePassword(Long userId, String newPassword);

    /**
     * 根据角色查找用户
     */
    List<User> findByRole(User.UserRole role);

    /**
     * 分页查询用户
     */
    Page<User> findUsers(Pageable pageable);

    /**
     * 根据角色和状态分页查询用户
     */
    Page<User> findByRoleAndStatus(User.UserRole role, User.UserStatus status, Pageable pageable);

    /**
     * 搜索用户
     */
    Page<User> searchUsers(String keyword, Pageable pageable);

    /**
     * 查找活跃用户
     */
    List<User> findActiveUsers(LocalDateTime since);

    /**
     * 统计用户数量
     */
    long countUsers();

    /**
     * 统计各角色用户数量
     */
    List<Object[]> countUsersByRole();

    /**
     * 统计各状态用户数量
     */
    List<Object[]> countUsersByStatus();

    /**
     * 查找需要邮箱验证的用户
     */
    List<User> findUsersNeedingEmailVerification();

    /**
     * 批量更新用户状态
     */
    void batchUpdateUserStatus(List<Long> userIds, User.UserStatus status);
}
