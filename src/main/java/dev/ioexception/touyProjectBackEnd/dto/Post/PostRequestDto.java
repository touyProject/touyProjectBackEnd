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
    private String title;
    private String content;
    private Category category;
    private String tag;

    public Post toEntity() {
        return Post.builder()
                .user(user)
                .title(title)
                .content(content)
                .category(category)
                .tag(tag)
                .build();
    }
}
