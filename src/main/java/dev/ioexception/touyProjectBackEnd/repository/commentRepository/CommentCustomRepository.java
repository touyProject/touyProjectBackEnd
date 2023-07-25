package dev.ioexception.touyProjectBackEnd.repository.commentRepository;

import dev.ioexception.touyProjectBackEnd.dto.comment.CommentResponseDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentCustomRepository {
    List<CommentResponseDto> getComments(Post post);
}
