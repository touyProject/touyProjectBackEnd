package dev.ioexception.touyProjectBackEnd.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "content", length = 2000, columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "view", nullable = false)
    private Long countVisit = 0L;

    @Column(name = "tag", nullable = false)
    private String tag;

    @Column(name = "is_deleted",nullable = false)
    private boolean isDeleted;

    @CreatedDate
    @Column(name = "post_create_at", nullable = false)
    private LocalDateTime postCreateAt;

    @LastModifiedDate
    @Column(name = "post_modify_at",nullable = false)
    private LocalDateTime postModifyAt;

    @Builder
    public Post(User user, Category category, String title, String content, Long countVisit, String tag) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.category = category;
        this.countVisit = countVisit;
        this.tag = tag;
        this.isDeleted = false ;
        this.postCreateAt = LocalDateTime.now();
        this.postModifyAt = LocalDateTime.now();
    }

    public void update(String title, String content, String tag) {
        this.title = title;
        this.content = content;
        this.tag = tag;
        this.postModifyAt = LocalDateTime.now();
    }

    public void updateVisit() {
        this.countVisit++;
    }
    public void updateIsDeleted() {
        this.isDeleted = true;
    }
}
