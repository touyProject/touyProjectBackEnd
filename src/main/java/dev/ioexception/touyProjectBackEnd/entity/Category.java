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
    @Column(name = "category_id")
    private Long categoryPId;

    @Column(name = "topic",length = 10, nullable = false)
    private String topic;

    @OneToMany(mappedBy = "parentId")
    private List<Category> cartgories = new ArrayList<>();

    @JoinColumn(name = "category_pid")
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

