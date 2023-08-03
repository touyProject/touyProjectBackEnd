package dev.ioexception.touyProjectBackEnd.entity.comment;

import dev.ioexception.touyProjectBackEnd.entity.Post;
import dev.ioexception.touyProjectBackEnd.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @Column(name = "content", nullable = false)
    private String content;

    @ColumnDefault("FALSE")
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Column(name = "comment_create_at", nullable = false)
    private LocalDateTime commentCreateAt;

    @Builder
    public Comment(String content, User user, Post post, Comment parent) {
        this.content = content;
        this.user = user;
        this.post = post;
        this.parent = parent;
        this.isDeleted = false;
        this.commentCreateAt = LocalDateTime.now();
    }

    public void updateIsDeleted() {
        this.isDeleted = true;
    }

}
