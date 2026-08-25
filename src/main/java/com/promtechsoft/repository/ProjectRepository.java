package com.promtechsoft.repository;

import com.promtechsoft.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
    List<ProjectEntity> findByCompleted(Boolean completed);
    List<ProjectEntity> findByCategoryIgnoreCase(String category);
    List<ProjectEntity> findByClientContainingIgnoreCase(String client);
}