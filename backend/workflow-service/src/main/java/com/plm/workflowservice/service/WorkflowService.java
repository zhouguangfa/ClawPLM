package com.plm.workflowservice.service;

import com.plm.workflowservice.entity.Workflow;
import com.plm.workflowservice.repository.WorkflowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkflowService {
    
    @Autowired
    private WorkflowRepository workflowRepository;
    
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    
    public List<Workflow> getAllWorkflows() {
        return workflowRepository.findAll();
    }
    
    public Optional<Workflow> getWorkflowById(Long id) {
        return workflowRepository.findById(id);
    }
    
    public Workflow createWorkflow(Workflow workflow) {
        Workflow savedWorkflow = workflowRepository.save(workflow);
        
        // Publish event to Kafka
        kafkaTemplate.send("workflow-events", "CREATE", savedWorkflow.getId().toString());
        
        return savedWorkflow;
    }
    
    public Workflow updateWorkflow(Long id, Workflow workflowDetails) {
        Optional<Workflow> optionalWorkflow = workflowRepository.findById(id);
        
        if (optionalWorkflow.isPresent()) {
            Workflow workflow = optionalWorkflow.get();
            workflow.setName(workflowDetails.getName());
            workflow.setDescription(workflowDetails.getDescription());
            workflow.setType(workflowDetails.getType());
            workflow.setDefinition(workflowDetails.getDefinition());
            
            Workflow updatedWorkflow = workflowRepository.save(workflow);
            
            // Publish event to Kafka
            kafkaTemplate.send("workflow-events", "UPDATE", updatedWorkflow.getId().toString());
            
            return updatedWorkflow;
        } else {
            throw new RuntimeException("Workflow not found with id: " + id);
        }
    }
    
    public void deleteWorkflow(Long id) {
        Optional<Workflow> optionalWorkflow = workflowRepository.findById(id);
        
        if (optionalWorkflow.isPresent()) {
            workflowRepository.deleteById(id);
            
            // Publish event to Kafka
            kafkaTemplate.send("workflow-events", "DELETE", id.toString());
        } else {
            throw new RuntimeException("Workflow not found with id: " + id);
        }
    }
    
    public List<Workflow> getWorkflowsByType(String type) {
        return workflowRepository.findByType(type);
    }
    
    public List<Workflow> searchWorkflowsByName(String name) {
        return workflowRepository.findByNameContainingIgnoreCase(name);
    }
}