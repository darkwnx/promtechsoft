package com.promtechsoft.service;

import com.promtechsoft.dto.BlogPostRequest;
import com.promtechsoft.dto.BlogPostResponse;
import com.promtechsoft.entity.BlogPostEntity;
import com.promtechsoft.exception.ResourceNotFoundException;
import com.promtechsoft.repository.BlogPostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlogPostServiceTest {

    @Mock
    private BlogPostRepository blogPostRepository;

    @InjectMocks
    private BlogPostService blogPostService;

    private BlogPostEntity testPost;
    private BlogPostRequest testRequest;

    @BeforeEach
    void setUp() {
        testPost = new BlogPostEntity();
        testPost.setId(1L);
        testPost.setTitle("Test Post");
        testPost.setContent("Test Content");
        testPost.setPreview("Test Preview");
        testPost.setCategory("Test");
        testPost.setReadTime(5);
        testPost.setPublished(true);
        testPost.setCreatedAt(LocalDateTime.now());
        testPost.setUpdatedAt(LocalDateTime.now());

        testRequest = new BlogPostRequest();
        testRequest.setTitle("Test Post");
        testRequest.setContent("Test Content");
        testRequest.setPreview("Test Preview");
        testRequest.setCategory("Test");
        testRequest.setReadTime(5);
        testRequest.setPublished(true);
    }

    @Test
    @DisplayName("Should return all published posts")
    void getAllPosts_ShouldReturnPublishedPosts() {
        // Arrange
        when(blogPostRepository.findByPublishedTrue())
                .thenReturn(Arrays.asList(testPost));

        // Act
        List<BlogPostResponse> result = blogPostService.getAllPosts();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Post", result.get(0).getTitle());

        verify(blogPostRepository, times(1)).findByPublishedTrue();
    }

    @Test
    @DisplayName("Should return post by ID")
    void getPostById_ShouldReturnPost() {
        // Arrange
        when(blogPostRepository.findById(1L))
                .thenReturn(Optional.of(testPost));

        // Act
        BlogPostResponse result = blogPostService.getPostById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Post", result.getTitle());

        verify(blogPostRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should throw exception when post not found")
    void getPostById_ShouldThrowException_WhenNotFound() {
        // Arrange
        when(blogPostRepository.findById(99L))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            blogPostService.getPostById(99L);
        });

        verify(blogPostRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Should create new post")
    void createPost_ShouldReturnCreatedPost() {
        // Arrange
        when(blogPostRepository.save(any(BlogPostEntity.class)))
                .thenReturn(testPost);

        // Act
        BlogPostResponse result = blogPostService.createPost(testRequest);

        // Assert
        assertNotNull(result);
        assertEquals("Test Post", result.getTitle());
        assertEquals("Test", result.getCategory());

        verify(blogPostRepository, times(1)).save(any(BlogPostEntity.class));
    }

    @Test
    @DisplayName("Should update existing post")
    void updatePost_ShouldReturnUpdatedPost() {
        // Arrange
        when(blogPostRepository.findById(1L))
                .thenReturn(Optional.of(testPost));
        when(blogPostRepository.save(any(BlogPostEntity.class)))
                .thenReturn(testPost);

        // Act
        BlogPostResponse result = blogPostService.updatePost(1L, testRequest);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(blogPostRepository, times(1)).findById(1L);
        verify(blogPostRepository, times(1)).save(any(BlogPostEntity.class));
    }

    @Test
    @DisplayName("Should delete post")
    void deletePost_ShouldDeletePost() {
        // Arrange
        when(blogPostRepository.findById(1L))
                .thenReturn(Optional.of(testPost));

        // Act
        blogPostService.deletePost(1L);

        // Assert
        verify(blogPostRepository, times(1)).findById(1L);
        verify(blogPostRepository, times(1)).delete(testPost);
    }

    @Test
    @DisplayName("Should search posts")
    void searchPosts_ShouldReturnMatchingPosts() {
        // Arrange
        when(blogPostRepository.searchPosts("Test"))
                .thenReturn(Arrays.asList(testPost));

        // Act
        List<BlogPostResponse> result = blogPostService.searchPosts("Test");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());

        verify(blogPostRepository, times(1)).searchPosts("Test");
    }
}