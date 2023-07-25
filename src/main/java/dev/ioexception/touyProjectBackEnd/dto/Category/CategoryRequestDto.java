package dev.ioexception.touyProjectBackEnd.dto.Category;

import dev.ioexception.touyProjectBackEnd.entity.Category;
import dev.ioexception.touyProjectBackEnd.entity.Post;

public class CategoryRequestDto {
    private Post post;
    private String topic;
    private Category parentId;

    public Category toEntity() {
        return Category.builder()
                .post(post)
                .topic(topic)
                .parentId(parentId)
                .build();
    }
}