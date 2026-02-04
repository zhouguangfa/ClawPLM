package com.plm.projectservice.repository;

import com.plm.projectservice.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    
    List<Project> findByStatus(Project.ProjectStatus status);
    
    List<Project> findByManagerId(Long managerId);
    
    List<Project> findByNameContainingIgnoreCase(String name);
}