package dev.ioexception.touyProjectBackEnd.service.commentService;

import dev.ioexception.touyProjectBackEnd.dto.comment.CommentRequestDto;
import dev.ioexception.touyProjectBackEnd.dto.comment.CommentResponseDto;
import dev.ioexception.touyProjectBackEnd.entity.comment.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {

    Comment commentSave(Long postId , CommentRequestDto requestDTO);

    Comment isDeletedUpdate(Long id);

    List<CommentResponseDto> getAllComments(Long postId);

}