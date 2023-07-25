package dev.ioexception.touyProjectBackEnd.repository.commentRepository.Impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.ioexception.touyProjectBackEnd.dto.comment.CommentResponseDto;
import dev.ioexception.touyProjectBackEnd.entity.QUser;
import dev.ioexception.touyProjectBackEnd.entity.comment.Comment;
import dev.ioexception.touyProjectBackEnd.entity.comment.QComment;
import dev.ioexception.touyProjectBackEnd.repository.commentRepository.CommentCustomRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class CommentCustomRepositoryImpl implements CommentCustomRepository {

    private final EntityManager entityManager;

    @Override
    public List<CommentResponseDto> getComments(Post post) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        QComment comment = QComment.comment;
        QUser user = QUser.user;

        BooleanExpression condition = comment.post.eq(post)
                .and(comment.parent.isNull()); // 대댓글이 아닌 댓글만 조회

        // 댓글을 순서대로 정렬한 리스트를 가져옴
        List<Comment> comments = queryFactory
                .select(comment)
                .from(comment)
                .leftJoin(comment.user, user)
                .where(condition)
                .orderBy(comment.commentCreateAt.asc())
                .fetch();

        // 댓글의 대댓글이 있는 경우, 댓글의 대댓글이 순서대로 나오도록 추가
        List<CommentResponseDto> sortedComments = CommentResponseDto.sortByCommentAndReply(comments);

        return sortedComments;
    }
}
