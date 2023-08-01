package dev.ioexception.touyProjectBackEnd.dto.Post;

import dev.ioexception.touyProjectBackEnd.entity.Post;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class PostsResponseDto {
    private String title;
    private String content;
    private String nickname;
    private String tag;
    private Long view;
    private LocalDateTime postModifyAt;

    public PostsResponseDto (Post post) {
        this.nickname = post.getUser().getNickname();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.tag = post.getTag();
        this.view = post.getCountVisit();
        this.postModifyAt = post.getPostModifyAt();
    }
}
