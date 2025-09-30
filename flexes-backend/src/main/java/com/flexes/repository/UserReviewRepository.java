package com.flexes.repository;

import com.flexes.entity.UserReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户评价数据访问接口
 * 
 * @author Flexes Team
 * @version 1.0.0
 */
@Repository
public interface UserReviewRepository extends JpaRepository<UserReview, Long> {

    /**
     * 根据用户ID查找评价
     */
    Page<UserReview> findByUserId(Long userId, Pageable pageable);

    /**
     * 根据状态查找评价
     */
    Page<UserReview> findByStatus(UserReview.ReviewStatus status, Pageable pageable);

    /**
     * 根据评分查找评价
     */
    Page<UserReview> findByRating(Integer rating, Pageable pageable);

    /**
     * 根据评分范围查找评价
     */
    Page<UserReview> findByRatingBetween(Integer minRating, Integer maxRating, Pageable pageable);

    /**
     * 查找已通过的评价
     */
    Page<UserReview> findByStatusOrderByCreatedAtDesc(UserReview.ReviewStatus status, Pageable pageable);

    /**
     * 查找高评分的已通过评价
     */
    @Query("SELECT r FROM UserReview r WHERE r.status = :status AND r.rating >= :minRating ORDER BY r.createdAt DESC")
    List<UserReview> findHighRatingApprovedReviews(@Param("status") UserReview.ReviewStatus status, 
                                                   @Param("minRating") Integer minRating, 
                                                   Pageable pageable);

    /**
     * 统计各评分的评价数量
     */
    @Query("SELECT r.rating, COUNT(r) FROM UserReview r WHERE r.status = :status GROUP BY r.rating")
    List<Object[]> countReviewsByRating(@Param("status") UserReview.ReviewStatus status);

    /**
     * 统计平均评分
     */
    @Query("SELECT AVG(r.rating) FROM UserReview r WHERE r.status = :status")
    Double getAverageRating(@Param("status") UserReview.ReviewStatus status);

    /**
     * 查找最有帮助的评价
     */
    @Query("SELECT r FROM UserReview r WHERE r.status = :status ORDER BY r.helpfulCount DESC")
    List<UserReview> findMostHelpfulReviews(@Param("status") UserReview.ReviewStatus status, Pageable pageable);
}
