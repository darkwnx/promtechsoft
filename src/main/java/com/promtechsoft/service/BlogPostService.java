package com.promtechsoft.service;

import com.promtechsoft.dto.BlogPostRequest;
import com.promtechsoft.dto.BlogPostResponse;
import com.promtechsoft.entity.BlogPostEntity;
import com.promtechsoft.exception.ResourceNotFoundException;
import com.promtechsoft.repository.BlogPostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlogPostService {

    private final BlogPostRepository blogPostRepository;

    @Transactional(readOnly = true)
    @Cacheable(value = "posts", key = "'all'")
    public List<BlogPostResponse> getAllPosts() {
        log.info("Fetching all posts from database");
        return blogPostRepository.findByPublishedTrue().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "posts", key = "#id")
    public BlogPostResponse getPostById(Long id) {
        log.info("Fetching post {} from database", id);
        BlogPostEntity post = blogPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Пост с id " + id + " не найден"));
        return toResponse(post);
    }

    @Transactional
    @CacheEvict(value = "posts", allEntries = true)
    public BlogPostResponse createPost(BlogPostRequest request) {
        log.info("Creating new post: {}", request.getTitle());

        BlogPostEntity post = new BlogPostEntity();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setPreview(request.getPreview());
        post.setCategory(request.getCategory());
        post.setReadTime(request.getReadTime());
        post.setPublished(request.getPublished());

        BlogPostEntity saved = blogPostRepository.save(post);
        log.info("Post created with id: {}", saved.getId());
        return toResponse(saved);
    }

    @Transactional
    @CacheEvict(value = "posts", allEntries = true)
    public BlogPostResponse updatePost(Long id, BlogPostRequest request) {
        log.info("Updating post with id: {}", id);

        BlogPostEntity post = blogPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Пост с id " + id + " не найден"));

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setPreview(request.getPreview());
        post.setCategory(request.getCategory());
        post.setReadTime(request.getReadTime());
        post.setPublished(request.getPublished());

        BlogPostEntity updated = blogPostRepository.save(post);
        log.info("Post updated: {}", id);
        return toResponse(updated);
    }

    @Transactional
    @CacheEvict(value = "posts", allEntries = true)
    public void deletePost(Long id) {
        log.info("Deleting post with id: {}", id);

        BlogPostEntity post = blogPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Пост с id " + id + " не найден"));

        blogPostRepository.delete(post);
        log.info("Post deleted: {}", id);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "search", key = "#query")
    public List<BlogPostResponse> searchPosts(String query) {
        log.info("Searching posts by: {}", query);
        return blogPostRepository.searchPosts(query).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "category", key = "#category")
    public List<BlogPostResponse> getPostsByCategory(String category) {
        log.info("Getting posts by category: {}", category);
        return blogPostRepository.findByCategoryIgnoreCaseAndPublishedTrue(category).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private BlogPostResponse toResponse(BlogPostEntity post) {
        return BlogPostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .preview(post.getPreview())
                .category(post.getCategory())
                .readTime(post.getReadTime())
                .createdAt(post.getCreatedAt())
                .published(post.getPublished())
                .build();
    }
}