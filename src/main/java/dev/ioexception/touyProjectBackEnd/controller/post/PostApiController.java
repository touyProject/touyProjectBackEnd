package dev.ioexception.touyProjectBackEnd.controller.post;

import dev.ioexception.touyProjectBackEnd.dto.Post.PostRequestDto;
import dev.ioexception.touyProjectBackEnd.dto.Post.PostResponseDto;
import dev.ioexception.touyProjectBackEnd.dto.Post.PostUpdateRequestDto;
import dev.ioexception.touyProjectBackEnd.dto.Post.PostsResponseDto;
import dev.ioexception.touyProjectBackEnd.entity.Category;
import dev.ioexception.touyProjectBackEnd.entity.Post;
import dev.ioexception.touyProjectBackEnd.service.postService.PostService;
import dev.ioexception.touyProjectBackEnd.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 1. 게시글 작성 기능
    @PostMapping("/post/{categoryId}")
    public ResponseEntity<PostResponseDto> createPost(@PathVariable Long categoryId,
                                                      @RequestBody PostRequestDto postRequestDto) {
        Post post = postService.createPost(categoryId, postRequestDto);
        PostResponseDto responseDto = new PostResponseDto(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 2. 모든 게시글 조회 기능
    @GetMapping("/posts/{categoryId}")
    public ResponseEntity<List<PostResponseDto>> getAllPost(@PathVariable Long categoryId) {
        List<PostResponseDto> post = postService.getAllPost(categoryId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    // 2-1. 특정 카테고리에 속한 게시글 페이징 조회 기능
//    @GetMapping("/post/{categoryId}")
//    public ResponseEntity<Page<Post>> getAllPostByCategory(@PathVariable Long categoryId, @RequestParam(defaultValue = "0") int page,
//                                                           @RequestParam(defaultValue = "10") int size) {
//        Category category = new Category(categoryId);
//        Pageable pageable = PageRequest.of(page, size, Sort.by("postCreateAt").descending());
//
//        Page<Post> posts = postService.getAllPostsByCategory(category, pageable);
//        return new ResponseEntity<>(posts, HttpStatus.OK);
//    }

//    // 2-2. 특정 카테고리에 속한 게시글 페이징 조회 기능 (간단하게)
//    @GetMapping("/post/{categoryId}")
//    public ResponseEntity<Page<Post>> getAllPostByCategory(@PathVariable Long categoryId, Pageable pageable) {
//        Category category = new Category(categoryId);
//        Page<Post> posts = postService.getAllPostsByCategory(category, pageable);
//        return new ResponseEntity<>(posts, HttpStatus.OK);
//    }

    // 3. 특정 게시글 상세 조회 기능
    @GetMapping("/post/{postId}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long postId) {
        PostResponseDto postResponseDto = postService.getPostById(postId);

        return ResponseEntity.ok(postResponseDto);
    }

    // 4. 게시글 수정 기능
    @PutMapping("/post/{postId}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long postId,
                                                      @RequestBody PostUpdateRequestDto postUpdateResponseDto) {
        PostResponseDto post = postService.updatePost(postId, postUpdateResponseDto);
        return ResponseEntity.ok(post);
    }

    // 5. 게시글 삭제 기능
    @DeleteMapping("/post/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok().build();
    }

    // 6. 게시글 검색 기능 - title
    @GetMapping("/post/{categoryId}/search/title")
    public ResponseEntity<List<PostResponseDto>> searchTitle(@PathVariable("categoryId") Long categoryId,
                                                             @RequestParam String title) {
        List<PostResponseDto> searchResult = postService.searchTitle(title, categoryId);

        return new ResponseEntity<>(searchResult, HttpStatus.OK);
    }

    // 6. 게시글 검색 기능 - content
    @GetMapping("/post/{categoryId}/search/content")
    public ResponseEntity<List<PostResponseDto>> searchContent(@PathVariable("categoryId") Long categoryId,
                                                               @RequestParam String content) {
        List<PostResponseDto> searchResult = postService.searchContent(content, categoryId);

        return new ResponseEntity<>(searchResult, HttpStatus.OK);
    }

    // 6. 게시글 검색 기능 - tag
    @GetMapping("/post/{categoryId}/search/tag")
    public ResponseEntity<List<PostResponseDto>> searchTag(@PathVariable("categoryId") Long categoryId,
                                                @RequestParam String tag) {
        List<PostResponseDto> searchResult = postService.searchTag(tag, categoryId);
        return new ResponseEntity<>(searchResult, HttpStatus.OK);
    }
}
