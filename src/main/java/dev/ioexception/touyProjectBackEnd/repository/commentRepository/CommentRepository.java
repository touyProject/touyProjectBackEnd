package dev.ioexception.touyProjectBackEnd.repository.commentRepository;

import dev.ioexception.touyProjectBackEnd.entity.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, CommentCustomRepository {
}
