package dev.ioexception.touyProjectBackEnd.service.categoryService;

import dev.ioexception.touyProjectBackEnd.repository.categoryRepository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
}