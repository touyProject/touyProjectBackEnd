package dev.ioexception.touyProjectBackEnd.service.postService;

import dev.ioexception.touyProjectBackEnd.dto.Post.PostRequestDto;
import dev.ioexception.touyProjectBackEnd.dto.Post.PostResponseDto;
import dev.ioexception.touyProjectBackEnd.dto.Post.PostUpdateRequestDto;
import dev.ioexception.touyProjectBackEnd.entity.Category;
import dev.ioexception.touyProjectBackEnd.entity.Post;
import dev.ioexception.touyProjectBackEnd.entity.User;
import dev.ioexception.touyProjectBackEnd.repository.categoryRepository.CategoryRepository;
import dev.ioexception.touyProjectBackEnd.repository.postRepository.PostRepository;
import dev.ioexception.touyProjectBackEnd.repository.userRepository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {
    String TAG_REGEX = "^[a-z0-9가-힣]{1,10}$";

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public Post createPost(Long categoryId, PostRequestDto requestDto) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("not foound: " + categoryId));

        User user = userRepository.findById(requestDto.getUser().getUserId())
                .orElseThrow(() -> new IllegalArgumentException("not foound: " + requestDto.getUser().getUserId()));

        String tag = requestDto.getTag();

        // 태그의 정규표현식 패턴
        if (tag == null || tag.trim().isEmpty() || !tag.matches(TAG_REGEX)) {
            throw new IllegalArgumentException("태그는 1~10글자의 한글, 영어, 숫자로 구성되어야 합니다");
        }

        requestDto.setUser(user);
        requestDto.setCategory(category);
        Post post = requestDto.toEntity();

        return postRepository.save(post);
    }

    public List<Post> getAllPost(Category category) {
        return postRepository.findAllByCategoryAndDeleted(category);
    }

    @Transactional
    public PostResponseDto getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + postId));

        post.updateVisit();
        return new PostResponseDto(post);
    }

    @Transactional
    public Post updatePost(Long postId, PostUpdateRequestDto updateRequestDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + postId));

        post.update(updateRequestDto.getTitle(), updateRequestDto.getContent(), updateRequestDto.getTag());

        return post;
    }

    @Transactional
    public Post deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + postId));

        post.updateisDeleted();

        return post;
    }

//    public Page<Post> getAllPostsByCategory(Category category, Pageable pageable) {
//        return postRepository.findAllByCategoryAndDeleted(category, pageable);
//    }

    @Transactional
    public List<Post> searchTitle(String title) {
        List<Post> postList = postRepository.findByTitleContainingAndDeleted(title);
        return postList;
    }

    @Transactional
    public List<Post> searchContent(String content) {
        List<Post> postList = postRepository.findByContentContainingAndDeleted(content);
        return postList;
    }

    @Transactional
    public List<Post> searchTag(String tag) {
        List<Post> postList = postRepository.findByTagContainingAndDeleted(tag);
        return postList;
    }
}