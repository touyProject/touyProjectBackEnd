package dev.ioexception.touyProjectBackEnd.controller.category;

import dev.ioexception.touyProjectBackEnd.service.categoryService.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;

public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String list() {
        return "";
    }
}