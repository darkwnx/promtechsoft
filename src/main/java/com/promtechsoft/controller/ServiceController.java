package com.promtechsoft.controller;

import com.promtechsoft.dto.ServiceRequest;
import com.promtechsoft.entity.ServiceEntity;
import com.promtechsoft.service.ServiceEntityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
public class ServiceController {

    private final ServiceEntityService serviceEntityService;

    @GetMapping
    public List<ServiceEntity> getAllServices() {
        return serviceEntityService.getAllServices();
    }

    @GetMapping("/{id}")
    public ServiceEntity getServiceById(@PathVariable Long id) {
        return serviceEntityService.getServiceById(id);
    }

    @PostMapping
    public ResponseEntity<ServiceEntity> createService(@Valid @RequestBody ServiceRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceEntityService.createService(request));
    }

    @PutMapping("/{id}")
    public ServiceEntity updateService(@PathVariable Long id, @Valid @RequestBody ServiceRequest request) {
        return serviceEntityService.updateService(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        serviceEntityService.deleteService(id);
        return ResponseEntity.ok().build();
    }
}