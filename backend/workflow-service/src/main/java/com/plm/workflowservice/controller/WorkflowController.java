package com.plm.workflowservice.controller;

import com.plm.workflowservice.entity.Workflow;
import com.plm.workflowservice.service.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workflows")
@CrossOrigin(origins = "*")
public class WorkflowController {
    
    @Autowired
    private WorkflowService workflowService;
    
    @GetMapping
    public ResponseEntity<List<Workflow>> getAllWorkflows() {
        List<Workflow> workflows = workflowService.getAllWorkflows();
        return ResponseEntity.ok(workflows);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Workflow> getWorkflowById(@PathVariable Long id) {
        return workflowService.getWorkflowById(id)
                .map(workflow -> ResponseEntity.ok(workflow))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Workflow> createWorkflow(@RequestBody Workflow workflow) {
        Workflow createdWorkflow = workflowService.createWorkflow(workflow);
        return ResponseEntity.ok(createdWorkflow);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Workflow> updateWorkflow(@PathVariable Long id, @RequestBody Workflow workflowDetails) {
        try {
            Workflow updatedWorkflow = workflowService.updateWorkflow(id, workflowDetails);
            return ResponseEntity.ok(updatedWorkflow);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkflow(@PathVariable Long id) {
        try {
            workflowService.deleteWorkflow(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/type/{type}")
    public ResponseEntity<List<Workflow>> getWorkflowsByType(@PathVariable String type) {
        List<Workflow> workflows = workflowService.getWorkflowsByType(type);
        return ResponseEntity.ok(workflows);
    }
    
    @GetMapping("/search/{name}")
    public ResponseEntity<List<Workflow>> searchWorkflowsByName(@PathVariable String name) {
        List<Workflow> workflows = workflowService.searchWorkflowsByName(name);
        return ResponseEntity.ok(workflows);
    }
}