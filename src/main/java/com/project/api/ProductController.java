package com.project.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.time.LocalDateTime;

import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createProduct(@RequestBody Map<String, Object> body) {
        String name = (String) body.get("name");
        Double price = Double.parseDouble(body.get("price").toString());
        Long categoryId = Long.valueOf(body.get("category_id").toString());



        Product product = new Product();
        product.setName(name);
        product.setPrice(price);

        Product saved = productRepository.save(product);

        return ResponseEntity.ok(Map.of("message", "Product created successfully", "id", saved.getId()));
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getProducts(@RequestParam(required = false) Long id) {
        List<Product> products = (id != null) ?
                List.of(productRepository.findById(id).orElseThrow()) :
                productRepository.findAll();

        return ResponseEntity.ok(Map.of("products", products));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateProduct(
            @PathVariable Long id,
            @RequestBody Map<String, Object> body) {

        Product product = productRepository.findById(id).orElseThrow();
        product.setName((String) body.get("name"));
        product.setPrice(Double.parseDouble(body.get("price").toString()));

        Long categoryId = Long.valueOf(body.get("category_id").toString());


        product.setUpdatedAt(LocalDateTime.now());

        productRepository.save(product);
        return ResponseEntity.ok(Map.of("message", "Product updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteProduct(@PathVariable Long id) {
        Product product = productRepository.findById(id).orElseThrow();
        product.setUpdatedAt(LocalDateTime.now());
        productRepository.save(product);
        return ResponseEntity.ok(Map.of("message", "Product deleted successfully"));
    }
}
