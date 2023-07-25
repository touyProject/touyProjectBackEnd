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
    private Long postId;

    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "category_id")
    private Category category;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 2000, columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "view", nullable = false)
    private Long countVisit = 0L;

    @Column(name = "tag", nullable = false)
    private String tag;

    @Column(nullable = false)
    private boolean isDeleted;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime postCreateAt;

    @LastModifiedDate
    @Column(nullable = false)
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
    public void updateisDeleted() {
        this.isDeleted = true;
    }
}
