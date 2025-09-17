package com.flexes.service.impl;

import com.flexes.entity.User;
import com.flexes.repository.UserRepository;
import com.flexes.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 用户服务实现类
 * 
 * @author Flexes Team
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(User user) {
        log.info("创建用户: {}", user.getEmail());
        
        // 检查邮箱是否已存在
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("邮箱已存在: " + user.getEmail());
        }
        
        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // 设置默认值
        if (user.getRole() == null) {
            user.setRole(User.UserRole.CANDIDATE);
        }
        if (user.getStatus() == null) {
            user.setStatus(User.UserStatus.ACTIVE);
        }
        if (user.getEmailVerified() == null) {
            user.setEmailVerified(false);
        }
        
        User savedUser = userRepository.save(user);
        log.info("用户创建成功: ID={}, Email={}", savedUser.getUserId(), savedUser.getEmail());
        
        return savedUser;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User updateUser(User user) {
        log.info("更新用户信息: ID={}", user.getUserId());
        
        User existingUser = userRepository.findById(user.getUserId())
            .orElseThrow(() -> new IllegalArgumentException("用户不存在: " + user.getUserId()));
        
        // 更新允许修改的字段
        existingUser.setEmail(user.getEmail());
        existingUser.setRole(user.getRole());
        existingUser.setStatus(user.getStatus());
        existingUser.setEmailVerified(user.getEmailVerified());
        
        User updatedUser = userRepository.save(existingUser);
        log.info("用户信息更新成功: ID={}", updatedUser.getUserId());
        
        return updatedUser;
    }

    @Override
    public void deleteUser(Long userId) {
        log.info("删除用户: ID={}", userId);
        
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("用户不存在: " + userId);
        }
        
        userRepository.deleteById(userId);
        log.info("用户删除成功: ID={}", userId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void verifyEmail(Long userId) {
        log.info("验证用户邮箱: ID={}", userId);
        
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("用户不存在: " + userId));
        
        user.setEmailVerified(true);
        userRepository.save(user);
        
        log.info("用户邮箱验证成功: ID={}", userId);
    }

    @Override
    public void updateLastLoginTime(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("用户不存在: " + userId));
        
        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);
    }

    @Override
    public void updateUserStatus(Long userId, User.UserStatus status) {
        log.info("更新用户状态: ID={}, Status={}", userId, status);
        
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("用户不存在: " + userId));
        
        user.setStatus(status);
        userRepository.save(user);
        
        log.info("用户状态更新成功: ID={}, Status={}", userId, status);
    }

    @Override
    public void updatePassword(Long userId, String newPassword) {
        log.info("更新用户密码: ID={}", userId);
        
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("用户不存在: " + userId));
        
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        
        log.info("用户密码更新成功: ID={}", userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findByRole(User.UserRole role) {
        return userRepository.findByRole(role);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> findUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> findByRoleAndStatus(User.UserRole role, User.UserStatus status, Pageable pageable) {
        return userRepository.findByRoleAndStatus(role, status, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> searchUsers(String keyword, Pageable pageable) {
        return userRepository.searchByEmailContaining(keyword, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findActiveUsers(LocalDateTime since) {
        return userRepository.findActiveUsers(since, User.UserStatus.ACTIVE);
    }

    @Override
    @Transactional(readOnly = true)
    public long countUsers() {
        return userRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> countUsersByRole() {
        return userRepository.countUsersByRole();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> countUsersByStatus() {
        return userRepository.countUsersByStatus();
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findUsersNeedingEmailVerification() {
        return userRepository.findUsersNeedingEmailVerification(User.UserStatus.PENDING_VERIFICATION);
    }

    @Override
    public void batchUpdateUserStatus(List<Long> userIds, User.UserStatus status) {
        log.info("批量更新用户状态: UserIds={}, Status={}", userIds, status);
        
        for (Long userId : userIds) {
            updateUserStatus(userId, status);
        }
        
        log.info("批量更新用户状态完成: 共{}个用户", userIds.size());
    }
}
