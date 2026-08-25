package com.promtechsoft.repository;

import com.promtechsoft.entity.ApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Long> {
    List<ApplicationEntity> findByStatus(String status);
    List<ApplicationEntity> findByEmail(String email);
}