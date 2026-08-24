package com.promtechsoft.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.promtechsoft.dto.BlogPostRequest;
import com.promtechsoft.entity.BlogPostEntity;
import com.promtechsoft.repository.BlogPostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class BlogPostControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BlogPostRepository blogPostRepository;

    @BeforeEach
    void setUp() {
        blogPostRepository.deleteAll();

        BlogPostEntity post = new BlogPostEntity();
        post.setTitle("Integration Test Post");
        post.setContent("Test Content");
        post.setPreview("Test Preview");
        post.setCategory("Test");
        post.setReadTime(5);
        post.setPublished(true);
        blogPostRepository.save(post);
    }

    @Test
    @DisplayName("GET /api/v1/posts - should return all posts")
    void getAllPosts_ShouldReturnPosts() throws Exception {
        mockMvc.perform(get("/api/v1/posts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value("Integration Test Post"))
                .andExpect(jsonPath("$[0].category").value("Test"));
    }

    @Test
    @DisplayName("POST /api/v1/posts - should create post")
    void createPost_ShouldCreatePost() throws Exception {
        BlogPostRequest request = new BlogPostRequest();
        request.setTitle("New Post");
        request.setContent("New Content");
        request.setPreview("New Preview");
        request.setCategory("New Category");
        request.setReadTime(10);
        request.setPublished(true);

        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("New Post"))
                .andExpect(jsonPath("$.category").value("New Category"));
    }

    @Test
    @DisplayName("GET /api/v1/posts/{id} - should return post by ID")
    void getPostById_ShouldReturnPost() throws Exception {
        BlogPostEntity saved = blogPostRepository.findAll().get(0);

        mockMvc.perform(get("/api/v1/posts/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Integration Test Post"));
    }

    @Test
    @DisplayName("GET /api/v1/posts/{id} - should return 404 for non-existent post")
    void getPostById_ShouldReturn404() throws Exception {
        mockMvc.perform(get("/api/v1/posts/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /api/v1/posts/{id} - should update post")
    void updatePost_ShouldUpdatePost() throws Exception {
        BlogPostEntity saved = blogPostRepository.findAll().get(0);

        BlogPostRequest request = new BlogPostRequest();
        request.setTitle("Updated Post");
        request.setContent("Updated Content");
        request.setPreview("Updated Preview");
        request.setCategory("Updated Category");
        request.setReadTime(15);
        request.setPublished(true);

        mockMvc.perform(put("/api/v1/posts/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Post"));
    }

    @Test
    @DisplayName("DELETE /api/v1/posts/{id} - should delete post")
    void deletePost_ShouldDeletePost() throws Exception {
        BlogPostEntity saved = blogPostRepository.findAll().get(0);

        mockMvc.perform(delete("/api/v1/posts/" + saved.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /api/v1/posts - should validate request")
    void createPost_ShouldValidateRequest() throws Exception {
        BlogPostRequest request = new BlogPostRequest();
        request.setTitle("");  // Empty title - should fail
        request.setContent("");

        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/v1/posts/search - should search posts")
    void searchPosts_ShouldReturnMatchingPosts() throws Exception {
        mockMvc.perform(get("/api/v1/posts/search")
                        .param("query", "Integration"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Integration Test Post"));
    }
}