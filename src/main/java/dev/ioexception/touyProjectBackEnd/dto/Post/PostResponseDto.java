package dev.ioexception.touyProjectBackEnd.dto.Post;

import dev.ioexception.touyProjectBackEnd.entity.Category;
import dev.ioexception.touyProjectBackEnd.entity.Post;
import dev.ioexception.touyProjectBackEnd.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {
    private User user;
    private String title;
    private String content;
    private Category category;
    private String tag;

    private LocalDateTime postModifyAt;

    public PostResponseDto (Post post) {
        this.user = post.getUser();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.category = post.getCategory();
        this.tag = post.getTag();
        this.postModifyAt = post.getPostModifyAt();
    }
}
