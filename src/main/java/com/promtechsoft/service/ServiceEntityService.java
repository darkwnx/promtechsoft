package com.promtechsoft.service;

import com.promtechsoft.dto.ServiceRequest;
import com.promtechsoft.entity.ServiceEntity;
import com.promtechsoft.exception.ResourceNotFoundException;
import com.promtechsoft.repository.ServiceRepository;
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
public class ServiceEntityService {

    private final ServiceRepository serviceRepository;

    @Transactional(readOnly = true)
    @Cacheable(value = "services", key = "'all'")
    public List<ServiceEntity> getAllServices() {
        log.info("Fetching all services");
        return serviceRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "services", key = "#id")
    public ServiceEntity getServiceById(Long id) {
        log.info("Fetching service {}", id);
        return serviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Услуга с id " + id + " не найдена"));
    }

    @Transactional
    @CacheEvict(value = "services", allEntries = true)
    public ServiceEntity createService(ServiceRequest request) {
        log.info("Creating service: {}", request.getTitle());

        ServiceEntity service = new ServiceEntity();
        service.setTitle(request.getTitle());
        service.setDescription(request.getDescription());
        service.setPrice(request.getPrice());
        service.setActive(request.getActive());

        return serviceRepository.save(service);
    }

    @Transactional
    @CacheEvict(value = "services", allEntries = true)
    public ServiceEntity updateService(Long id, ServiceRequest request) {
        log.info("Updating service {}", id);

        ServiceEntity service = getServiceById(id);
        service.setTitle(request.getTitle());
        service.setDescription(request.getDescription());
        service.setPrice(request.getPrice());
        service.setActive(request.getActive());

        return serviceRepository.save(service);
    }

    @Transactional
    @CacheEvict(value = "services", allEntries = true)
    public void deleteService(Long id) {
        log.info("Deleting service {}", id);

        ServiceEntity service = getServiceById(id);
        serviceRepository.delete(service);
    }
}