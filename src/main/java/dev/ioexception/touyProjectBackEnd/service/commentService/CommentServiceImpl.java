package dev.ioexception.touyProjectBackEnd.service.commentService;

import dev.ioexception.touyProjectBackEnd.dto.comment.CommentRequestDto;
import dev.ioexception.touyProjectBackEnd.dto.comment.CommentResponseDto;
import dev.ioexception.touyProjectBackEnd.entity.Post;
import dev.ioexception.touyProjectBackEnd.entity.User;
import dev.ioexception.touyProjectBackEnd.entity.comment.Comment;
import dev.ioexception.touyProjectBackEnd.repository.commentRepository.CommentRepository;
import dev.ioexception.touyProjectBackEnd.repository.postRepository.PostRepository;
import dev.ioexception.touyProjectBackEnd.repository.userRepository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    public Comment commentSave(Long postId, CommentRequestDto requestDTO) {
        User user = userRepository.findById(requestDTO.getUser().getUserId())
                .orElseThrow(() -> new IllegalArgumentException("not found: " + requestDTO.getUser()));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + postId));

        requestDTO.setUser(user);
        requestDTO.setPost(post);

        // 대댓글인 경우
        if(requestDTO.getParent().getId() != null) {
            Comment parent = commentRepository.findById(requestDTO.getParent().getId())
                    .orElseThrow(() -> new IllegalArgumentException("not found: " + requestDTO.getParent().getId()));

            requestDTO.setParent(parent);
        } else {
            requestDTO.setParent(null);

        }
        Comment comment = requestDTO.toEntity();

        return commentRepository.save(comment);
    }

    @Transactional
    @Override
    public Comment isDeletedUpdate(Long id) {

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        comment.updateIsDeleted();

        return comment;
    }

    @Override
    public List<CommentResponseDto> getAllComments(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + postId));

        return commentRepository.getComments(post);
    }
}

