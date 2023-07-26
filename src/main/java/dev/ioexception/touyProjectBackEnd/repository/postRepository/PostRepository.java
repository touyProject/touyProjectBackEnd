package dev.ioexception.touyProjectBackEnd.repository.postRepository;

import dev.ioexception.touyProjectBackEnd.entity.Category;
import dev.ioexception.touyProjectBackEnd.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByCategoryAndDeleted(Category category);
    List<Post> findByTitleContainingAndDeleted(@Param("title") String title);
    List<Post> findByContentContainingAndDeleted(@Param("content") String content);
    List<Post> findByTagContainingAndDeleted(@Param("tag") String tag);

//    Page<Post> findAllByCategory(Category category, Pageable pageable);
}