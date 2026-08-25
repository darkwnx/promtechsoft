package com.promtechsoft.service;

import com.promtechsoft.dto.ApplicationRequest;
import com.promtechsoft.dto.ApplicationResponse;
import com.promtechsoft.entity.ApplicationEntity;
import com.promtechsoft.exception.ResourceNotFoundException;
import com.promtechsoft.repository.ApplicationRepository;
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
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    @Transactional
    public ApplicationResponse createApplication(ApplicationRequest request) {
        log.info("Creating new application from: {}", request.getEmail());

        ApplicationEntity entity = new ApplicationEntity();
        entity.setName(request.getName());
        entity.setEmail(request.getEmail());
        entity.setPhone(request.getPhone());
        entity.setMessage(request.getMessage());
        entity.setServiceType(request.getServiceType());
        entity.setStatus("NEW");

        ApplicationEntity saved = applicationRepository.save(entity);
        log.info("Application created with id: {}", saved.getId());

        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "applications", key = "'all'")
    public List<ApplicationResponse> getAllApplications() {
        log.info("Fetching all applications");
        return applicationRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ApplicationResponse getApplicationById(Long id) {
        log.info("Fetching application {}", id);
        ApplicationEntity entity = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Заявка с id " + id + " не найдена"));
        return toResponse(entity);
    }

    @Transactional
    @CacheEvict(value = "applications", allEntries = true)
    public ApplicationResponse updateApplicationStatus(Long id, String status) {
        log.info("Updating application {} status to: {}", id, status);

        ApplicationEntity entity = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Заявка с id " + id + " не найдена"));

        entity.setStatus(status);
        ApplicationEntity updated = applicationRepository.save(entity);

        return toResponse(updated);
    }

    @Transactional
    @CacheEvict(value = "applications", allEntries = true)
    public void deleteApplication(Long id) {
        log.info("Deleting application {}", id);
        ApplicationEntity entity = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Заявка с id " + id + " не найдена"));
        applicationRepository.delete(entity);
    }

    private ApplicationResponse toResponse(ApplicationEntity entity) {
        return ApplicationResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .message(entity.getMessage())
                .serviceType(entity.getServiceType())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}