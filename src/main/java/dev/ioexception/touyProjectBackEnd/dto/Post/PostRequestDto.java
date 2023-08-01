package dev.ioexception.touyProjectBackEnd.dto.Post;

import dev.ioexception.touyProjectBackEnd.entity.Category;
import dev.ioexception.touyProjectBackEnd.entity.Post;
import dev.ioexception.touyProjectBackEnd.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestDto {
    private User user;
    private Category category;
    private String title;
    private String content;
    private String tag;

    public Post toEntity() {
        return Post.builder()
                .user(user)
                .category(category)
                .title(title)
                .content(content)
                .tag(tag)
                .build();
    }
}
