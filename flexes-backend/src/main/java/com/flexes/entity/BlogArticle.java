package com.flexes.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 博客文章实体类
 * 
 * @author Flexes Team
 * @version 1.0.0
 */
@Entity
@Table(name = "blog_articles", indexes = {
    @Index(name = "idx_slug", columnList = "slug"),
    @Index(name = "idx_author_id", columnList = "author_id"),
    @Index(name = "idx_category", columnList = "category"),
    @Index(name = "idx_status", columnList = "status"),
    @Index(name = "idx_published_at", columnList = "published_at"),
    @Index(name = "idx_featured", columnList = "featured")
})
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogArticle extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long articleId;

    @NotBlank(message = "文章标题不能为空")
    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @NotBlank(message = "文章标识不能为空")
    @Column(name = "slug", nullable = false, unique = true, length = 200)
    private String slug;

    @NotBlank(message = "文章摘要不能为空")
    @Column(name = "summary", nullable = false, length = 500)
    private String summary;

    @NotBlank(message = "文章内容不能为空")
    @Column(name = "content", nullable = false, columnDefinition = "LONGTEXT")
    private String content;

    @Column(name = "cover_image", length = 500)
    private String coverImage;

    @NotNull(message = "作者ID不能为空")
    @Column(name = "author_id", nullable = false)
    private Long authorId;

    @Column(name = "author_name", length = 100)
    private String authorName;

    @Column(name = "author_avatar", length = 500)
    private String authorAvatar;

    @Column(name = "category", length = 50)
    private String category;

    @Column(name = "tags", columnDefinition = "JSON")
    private String tags;

    @Column(name = "read_count")
    @Builder.Default
    private Integer readCount = 0;

    @Column(name = "like_count")
    @Builder.Default
    private Integer likeCount = 0;

    @Column(name = "comment_count")
    @Builder.Default
    private Integer commentCount = 0;

    @Column(name = "read_time")
    private Integer readTime;

    @Column(name = "featured")
    @Builder.Default
    private Boolean featured = false;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    @Builder.Default
    private ArticleStatus status = ArticleStatus.DRAFT;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    // 关联关系
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", insertable = false, updatable = false)
    @JsonIgnore
    private User author;

    /**
     * 文章状态枚举
     */
    public enum ArticleStatus {
        DRAFT(0, "草稿"),
        PUBLISHED(1, "已发布"),
        ARCHIVED(2, "已归档");

        private final int code;
        private final String description;

        ArticleStatus(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public int getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }

        public static ArticleStatus fromCode(int code) {
            for (ArticleStatus status : values()) {
                if (status.code == code) {
                    return status;
                }
            }
            throw new IllegalArgumentException("未知的文章状态代码: " + code);
        }
    }

    /**
     * 检查文章是否已发布
     */
    public boolean isPublished() {
        return ArticleStatus.PUBLISHED.equals(this.status);
    }

    /**
     * 检查是否为精选文章
     */
    public boolean isFeatured() {
        return Boolean.TRUE.equals(this.featured);
    }

    /**
     * 增加阅读数
     */
    public void incrementReadCount() {
        this.readCount = (this.readCount == null ? 0 : this.readCount) + 1;
    }

    /**
     * 增加点赞数
     */
    public void incrementLikeCount() {
        this.likeCount = (this.likeCount == null ? 0 : this.likeCount) + 1;
    }

    /**
     * 增加评论数
     */
    public void incrementCommentCount() {
        this.commentCount = (this.commentCount == null ? 0 : this.commentCount) + 1;
    }
}
