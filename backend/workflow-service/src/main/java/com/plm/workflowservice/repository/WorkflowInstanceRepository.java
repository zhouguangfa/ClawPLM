package com.plm.workflowservice.repository;

import com.plm.workflowservice.entity.WorkflowInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkflowInstanceRepository extends JpaRepository<WorkflowInstance, Long> {
    
    List<WorkflowInstance> findByWorkflowId(Long workflowId);
    
    List<WorkflowInstance> findByEntityIdAndEntityType(Long entityId, String entityType);
    
    List<WorkflowInstance> findByStatus(WorkflowInstance.WorkflowStatus status);
    
    List<WorkflowInstance> findByInitiatedBy(Long initiatedBy);
}