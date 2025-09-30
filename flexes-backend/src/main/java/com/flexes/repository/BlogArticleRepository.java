package com.flexes.repository;

import com.flexes.entity.BlogArticle;
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
 * 博客文章数据访问接口
 * 
 * @author Flexes Team
 * @version 1.0.0
 */
@Repository
public interface BlogArticleRepository extends JpaRepository<BlogArticle, Long> {

    /**
     * 根据标识查找文章
     */
    Optional<BlogArticle> findBySlug(String slug);

    /**
     * 根据作者ID查找文章
     */
    Page<BlogArticle> findByAuthorId(Long authorId, Pageable pageable);

    /**
     * 根据状态查找文章
     */
    Page<BlogArticle> findByStatus(BlogArticle.ArticleStatus status, Pageable pageable);

    /**
     * 根据分类查找文章
     */
    Page<BlogArticle> findByCategory(String category, Pageable pageable);

    /**
     * 查找精选文章
     */
    Page<BlogArticle> findByFeaturedTrueAndStatus(BlogArticle.ArticleStatus status, Pageable pageable);

    /**
     * 根据标题搜索文章
     */
    Page<BlogArticle> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    /**
     * 全文搜索文章
     */
    @Query("SELECT a FROM BlogArticle a WHERE " +
           "LOWER(a.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(a.summary) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(a.content) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<BlogArticle> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    /**
     * 查找最新发布的文章
     */
    @Query("SELECT a FROM BlogArticle a WHERE a.status = :status ORDER BY a.publishedAt DESC")
    List<BlogArticle> findLatestPublishedArticles(@Param("status") BlogArticle.ArticleStatus status, Pageable pageable);

    /**
     * 查找热门文章（按阅读数排序）
     */
    @Query("SELECT a FROM BlogArticle a WHERE a.status = :status ORDER BY a.readCount DESC")
    List<BlogArticle> findPopularArticles(@Param("status") BlogArticle.ArticleStatus status, Pageable pageable);

    /**
     * 查找最受欢迎的文章（按点赞数排序）
     */
    @Query("SELECT a FROM BlogArticle a WHERE a.status = :status ORDER BY a.likeCount DESC")
    List<BlogArticle> findMostLikedArticles(@Param("status") BlogArticle.ArticleStatus status, Pageable pageable);

    /**
     * 查找指定时间后发布的文章
     */
    @Query("SELECT a FROM BlogArticle a WHERE a.publishedAt >= :since AND a.status = :status")
    List<BlogArticle> findArticlesPublishedSince(@Param("since") LocalDateTime since, 
                                                 @Param("status") BlogArticle.ArticleStatus status);

    /**
     * 统计各分类的文章数量
     */
    @Query("SELECT a.category, COUNT(a) FROM BlogArticle a WHERE a.status = :status GROUP BY a.category")
    List<Object[]> countArticlesByCategory(@Param("status") BlogArticle.ArticleStatus status);

    /**
     * 统计各作者的文章数量
     */
    @Query("SELECT a.authorId, a.authorName, COUNT(a) FROM BlogArticle a WHERE a.status = :status GROUP BY a.authorId, a.authorName")
    List<Object[]> countArticlesByAuthor(@Param("status") BlogArticle.ArticleStatus status);

    /**
     * 查找相关文章（基于分类）
     */
    @Query("SELECT a FROM BlogArticle a WHERE a.category = :category AND a.articleId != :excludeArticleId AND a.status = :status")
    List<BlogArticle> findRelatedArticles(@Param("category") String category,
                                         @Param("excludeArticleId") Long excludeArticleId,
                                         @Param("status") BlogArticle.ArticleStatus status,
                                         Pageable pageable);
}
