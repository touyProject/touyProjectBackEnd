package dev.ioexception.touyProjectBackEnd.dto.comment;

import dev.ioexception.touyProjectBackEnd.entity.Post;
import dev.ioexception.touyProjectBackEnd.entity.User;
import dev.ioexception.touyProjectBackEnd.entity.comment.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDto {
    private String content;
    private User user;
    private Post post;
    private Comment parent;

    public Comment toEntity() {
        return Comment.builder()
                .content(content)
                .post(post)
                .user(user)
                .parent(parent)
                .build();
    }
}
