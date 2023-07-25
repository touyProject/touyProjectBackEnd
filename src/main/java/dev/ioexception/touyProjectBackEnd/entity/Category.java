package dev.ioexception.touyProjectBackEnd.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryPId;

    @Column(length = 10, nullable = false)
    private String topic;

    @OneToMany(mappedBy = "category_pid")
    private List<Category> cartgories = new ArrayList<>();

    @JoinColumn(name = "paret_category_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Category parentId;

    @Builder
    public Category(String topic, Category parentId) {
        this.topic = topic;
        this.parentId = parentId;
    }

    public Category(Long categoryPId) {
        this.categoryPId = categoryPId;
    }
}

