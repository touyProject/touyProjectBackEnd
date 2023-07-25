package dev.ioexception.touyProjectBackEnd.dto.comment;

import dev.ioexception.touyProjectBackEnd.entity.comment.Comment;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CommentResponseDto {
    private final Long id;
    private final Long postId;
    private final String userName;
    private final String content;
    private final LocalDateTime commentCreatedAt;
    private final boolean isDeleted;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.postId = comment.getPost().getPostId();
        this.userName = comment.getUser().getNickname();
        // 댓글이 삭제된 경우 content 대신 "삭제된 메시지입니다"로 설정
        this.content = comment.getIsDeleted() ? "삭제된 메시지입니다" : comment.getContent();
        this.commentCreatedAt = comment.getCommentCreateAt();
        this.isDeleted = comment.getIsDeleted();
    }

    // 댓글과 대댓글을 순서대로 정렬하기 위한 메서드
    public static List<CommentResponseDto> sortByCommentAndReply(List<Comment> comments) {
        List<CommentResponseDto> result = new ArrayList<>();

        for (Comment comment : comments) {
            result.add(new CommentResponseDto(comment));

            // 대댓글도 순서대로 추가 (대댓글이 없는 경우, 추가하지 않도록 처리)
            if (comment.getCommentList() != null && !comment.getCommentList().isEmpty()) {
                for (Comment reply : comment.getCommentList()) {
                    result.add(new CommentResponseDto(reply));
                }
            }
        }
        return result;
    }
}
