package dev.ioexception.touyProjectBackEnd.controller.post;

import dev.ioexception.touyProjectBackEnd.dto.Post.PostRequestDto;
import dev.ioexception.touyProjectBackEnd.dto.Post.PostResponseDto;
import dev.ioexception.touyProjectBackEnd.dto.Post.PostUpdateRequestDto;
import dev.ioexception.touyProjectBackEnd.entity.Category;
import dev.ioexception.touyProjectBackEnd.entity.Post;
import dev.ioexception.touyProjectBackEnd.service.postService.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 1. 게시글 작성 기능
    @PostMapping("/post/{categoryId}")
    public ResponseEntity<Post> createPost(@PathVariable Long categoryId, @RequestBody PostRequestDto postRequestDto) {
        Post post = postService.createPost(categoryId, postRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    // 2. 모든 게시글 조회 기능
    @GetMapping("/post/{categoryId}")
    public ResponseEntity<List<Post>> getAllPost(@PathVariable Long categoryId) {
        List<Post> post = postService.getAllPost(new Category(categoryId));
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    // 2-1. 특정 카테고리에 속한 게시글 페이징 조회 기능
    @GetMapping("/post/{categoryId}")
    public ResponseEntity<Page<Post>> getAllPostByCategory(@PathVariable Long categoryId, @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size) {
        Category category = new Category(categoryId);
        Pageable pageable = PageRequest.of(page, size, Sort.by("postCreateAt").descending());

        Page<Post> posts = postService.getAllPostsByCategory(category, pageable);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    // 2-2. 특정 카테고리에 속한 게시글 페이징 조회 기능 (간단하게)
    @GetMapping("/post/{categoryId}")
    public ResponseEntity<Page<Post>> getAllPostByCategory(@PathVariable Long categoryId, Pageable pageable) {
        Category category = new Category(categoryId);
        Page<Post> posts = postService.getAllPostsByCategory(category, pageable);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    // 3. 특정 게시글 상세 조회 기능
    @GetMapping("/post/{postId}")
    public PostResponseDto getPostById(@PathVariable Long postId) {

        return postService.getPostById(postId);
    }

    // 4. 게시글 수정 기능
    @PutMapping("/post/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable Long postId, @RequestBody PostUpdateRequestDto postUpdateResponseDto) {
        postService.updatePost(postId, postUpdateResponseDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 5. 게시글 삭제 기능
    @DeleteMapping("post/{postId}")
    public ResponseEntity<Post> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId, token);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 6. 게시글 검색 기능 - title
    @GetMapping("/post/search")
    public ResponseEntity<List<Post>> searchTitle(@RequestParam String title) {
        List<Post> searchResult = postService.searchTitle(title);
        return new ResponseEntity<>(searchResult, HttpStatus.OK);
    }

    // 6. 게시글 검색 기능 - content
    @GetMapping("/post/search")
    public ResponseEntity<List<Post>> searchContent(@RequestParam String content) {
        List<Post> searchResult = postService.searchContent(content);
        return new ResponseEntity<>(searchResult, HttpStatus.OK);
    }

    // 6. 게시글 검색 기능 - tag
    @GetMapping("/post/search")
    public ResponseEntity<List<Post>> searchTag(@RequestParam String tag) {
        List<Post> searchResult = postService.searchTag(tag);
        return new ResponseEntity<>(searchResult, HttpStatus.OK);
    }
}
