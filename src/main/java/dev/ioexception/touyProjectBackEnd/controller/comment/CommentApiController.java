package dev.ioexception.touyProjectBackEnd.controller.comment;

import dev.ioexception.touyProjectBackEnd.dto.comment.CommentRequestDto;
import dev.ioexception.touyProjectBackEnd.dto.comment.CommentResponseDto;
import dev.ioexception.touyProjectBackEnd.entity.comment.Comment;
import dev.ioexception.touyProjectBackEnd.service.commentService.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/posts/comment/{postId}")
    public ResponseEntity<Comment> commentSave(@PathVariable Long postId, @RequestBody CommentRequestDto requestDto) {
        Comment comment = commentService.commentSave(postId, requestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(comment);
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<Void> deletedComment(@PathVariable Long commentId) {
        Comment comment = commentService.isDeletedUpdate(commentId);

        return ResponseEntity.noContent().build();

    }

    @GetMapping("/post/comment/{postId}")
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable Long postId) {

        List<CommentResponseDto> commentResponseDtos = commentService.getAllComments(postId);
        return ResponseEntity.ok(commentResponseDtos);

    }

}
