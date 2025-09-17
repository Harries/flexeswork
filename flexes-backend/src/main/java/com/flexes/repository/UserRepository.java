package com.flexes.repository;

import com.flexes.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 用户数据访问接口
 * 
 * @author Flexes Team
 * @version 1.0.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 根据邮箱查找用户
     */
    Optional<User> findByEmail(String email);

    /**
     * 检查邮箱是否存在
     */
    boolean existsByEmail(String email);

    /**
     * 根据角色查找用户
     */
    List<User> findByRole(User.UserRole role);

    /**
     * 根据状态查找用户
     */
    List<User> findByStatus(User.UserStatus status);

    /**
     * 根据角色和状态查找用户
     */
    Page<User> findByRoleAndStatus(User.UserRole role, User.UserStatus status, Pageable pageable);

    /**
     * 查找已验证邮箱的用户
     */
    List<User> findByEmailVerifiedTrue();

    /**
     * 查找未验证邮箱的用户
     */
    List<User> findByEmailVerifiedFalse();

    /**
     * 根据最后登录时间范围查找用户
     */
    @Query("SELECT u FROM User u WHERE u.lastLoginAt BETWEEN :startTime AND :endTime")
    List<User> findByLastLoginAtBetween(@Param("startTime") LocalDateTime startTime, 
                                       @Param("endTime") LocalDateTime endTime);

    /**
     * 查找活跃用户（最近登录）
     */
    @Query("SELECT u FROM User u WHERE u.lastLoginAt >= :since AND u.status = :status")
    List<User> findActiveUsers(@Param("since") LocalDateTime since, 
                              @Param("status") User.UserStatus status);

    /**
     * 统计各角色用户数量
     */
    @Query("SELECT u.role, COUNT(u) FROM User u GROUP BY u.role")
    List<Object[]> countUsersByRole();

    /**
     * 统计各状态用户数量
     */
    @Query("SELECT u.status, COUNT(u) FROM User u GROUP BY u.status")
    List<Object[]> countUsersByStatus();

    /**
     * 查找指定时间后注册的用户
     */
    @Query("SELECT u FROM User u WHERE u.createdAt >= :since")
    List<User> findUsersRegisteredSince(@Param("since") LocalDateTime since);

    /**
     * 根据邮箱模糊搜索用户
     */
    @Query("SELECT u FROM User u WHERE u.email LIKE %:keyword%")
    Page<User> searchByEmailContaining(@Param("keyword") String keyword, Pageable pageable);

    /**
     * 查找需要邮箱验证的用户
     */
    @Query("SELECT u FROM User u WHERE u.emailVerified = false AND u.status = :status")
    List<User> findUsersNeedingEmailVerification(@Param("status") User.UserStatus status);
}
