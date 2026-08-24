package com.promtechsoft.repository;

import com.promtechsoft.entity.BlogPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPostEntity, Long> {

    List<BlogPostEntity> findByPublishedTrue();

    List<BlogPostEntity> findByCategoryIgnoreCaseAndPublishedTrue(String category);

    @Query("SELECT p FROM BlogPostEntity p WHERE p.published = true AND " +
            "(LOWER(p.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(p.preview) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(p.content) LIKE LOWER(CONCAT('%', :query, '%')))")
    List<BlogPostEntity> searchPosts(@Param("query") String query);
}