package com.plm.workflowservice.repository;

import com.plm.workflowservice.entity.Workflow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkflowRepository extends JpaRepository<Workflow, Long> {
    
    List<Workflow> findByType(String type);
    
    List<Workflow> findByNameContainingIgnoreCase(String name);
}