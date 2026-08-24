package com.promtechsoft.controller;

import com.promtechsoft.dto.ApiResponse;
import com.promtechsoft.dto.BlogPostRequest;
import com.promtechsoft.dto.BlogPostResponse;
import com.promtechsoft.service.BlogPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class BlogPostController {

    private final BlogPostService blogPostService;

    @GetMapping
    public ResponseEntity<List<BlogPostResponse>> getAllPosts() {
        return ResponseEntity.ok(blogPostService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogPostResponse> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(blogPostService.getPostById(id));
    }

    @PostMapping
    public ResponseEntity<BlogPostResponse> createPost(@Valid @RequestBody BlogPostRequest request) {
        BlogPostResponse created = blogPostService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlogPostResponse> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody BlogPostRequest request) {
        return ResponseEntity.ok(blogPostService.updatePost(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Long id) {
        blogPostService.deletePost(id);
        return ResponseEntity.ok(ApiResponse.success("Пост удален"));
    }

    @GetMapping("/search")
    public ResponseEntity<List<BlogPostResponse>> searchPosts(@RequestParam String query) {
        return ResponseEntity.ok(blogPostService.searchPosts(query));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<BlogPostResponse>> getByCategory(@PathVariable String category) {
        return ResponseEntity.ok(blogPostService.getPostsByCategory(category));
    }
}