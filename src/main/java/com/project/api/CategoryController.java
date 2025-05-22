package com.project.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createCategory(@RequestBody Map<String, String> body) {
        String name = body.get("name");
        Category category = new Category();
        category.setName(name);
        Category saved = categoryRepository.save(category);
        return ResponseEntity.ok(Map.of("message", "Category created successfully", "id", saved.getId()));
    }
}
