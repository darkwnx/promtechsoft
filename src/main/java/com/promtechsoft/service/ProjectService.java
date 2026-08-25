package com.promtechsoft.service;

import com.promtechsoft.dto.ProjectRequest;
import com.promtechsoft.entity.ProjectEntity;
import com.promtechsoft.exception.ResourceNotFoundException;
import com.promtechsoft.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Transactional(readOnly = true)
    @Cacheable(value = "projects", key = "'all'")
    public List<ProjectEntity> getAllProjects() {
        log.info("Fetching all projects");
        return projectRepository.findAll();
    }

    @Transactional(readOnly = true)
    public ProjectEntity getProjectById(Long id) {
        log.info("Fetching project {}", id);
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Проект с id " + id + " не найден"));
    }

    @Transactional
    @CacheEvict(value = "projects", allEntries = true)
    public ProjectEntity createProject(ProjectRequest request) {
        log.info("Creating project: {}", request.getTitle());

        ProjectEntity project = new ProjectEntity();
        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
        project.setClient(request.getClient());
        project.setCategory(request.getCategory());
        project.setStartDate(request.getStartDate());
        project.setEndDate(request.getEndDate());
        project.setCompleted(request.getCompleted());

        return projectRepository.save(project);
    }

    @Transactional
    @CacheEvict(value = "projects", allEntries = true)
    public ProjectEntity updateProject(Long id, ProjectRequest request) {
        ProjectEntity project = getProjectById(id);
        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
        project.setClient(request.getClient());
        project.setCategory(request.getCategory());
        project.setStartDate(request.getStartDate());
        project.setEndDate(request.getEndDate());
        project.setCompleted(request.getCompleted());

        return projectRepository.save(project);
    }

    @Transactional
    @CacheEvict(value = "projects", allEntries = true)
    public void deleteProject(Long id) {
        ProjectEntity project = getProjectById(id);
        projectRepository.delete(project);
    }
}