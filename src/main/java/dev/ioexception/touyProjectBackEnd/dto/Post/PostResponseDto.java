package dev.ioexception.touyProjectBackEnd.dto.Post;

import dev.ioexception.touyProjectBackEnd.entity.Category;
import dev.ioexception.touyProjectBackEnd.entity.Post;
import dev.ioexception.touyProjectBackEnd.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;
import lombok.ToString;

@Getter
public class PostResponseDto {
    private Long postId;
    private String nickname;
    private String title;
    private String content;
    private String tag;
    private Long view;
    private LocalDateTime postModifyAt;

    public PostResponseDto (Post post) {
        this.postId = post.getPostId();
        this.nickname = post.getUser().getNickname();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.tag = post.getTag();
        this.view = post.getCountVisit();
        this.postModifyAt = post.getPostModifyAt();
    }
}
