package com.plm.projectservice.service;

import com.plm.projectservice.entity.Project;
import com.plm.projectservice.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
    
    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }
    
    public Project createProject(Project project) {
        Project savedProject = projectRepository.save(project);
        
        // Publish event to Kafka
        kafkaTemplate.send("project-events", "CREATE", savedProject.getId().toString());
        
        return savedProject;
    }
    
    public Project updateProject(Long id, Project projectDetails) {
        Optional<Project> optionalProject = projectRepository.findById(id);
        
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            project.setName(projectDetails.getName());
            project.setDescription(projectDetails.getDescription());
            project.setStartDate(projectDetails.getStartDate());
            project.setEndDate(projectDetails.getEndDate());
            project.setStatus(projectDetails.getStatus());
            project.setManagerId(projectDetails.getManagerId());
            
            Project updatedProject = projectRepository.save(project);
            
            // Publish event to Kafka
            kafkaTemplate.send("project-events", "UPDATE", updatedProject.getId().toString());
            
            return updatedProject;
        } else {
            throw new RuntimeException("Project not found with id: " + id);
        }
    }
    
    public void deleteProject(Long id) {
        Optional<Project> optionalProject = projectRepository.findById(id);
        
        if (optionalProject.isPresent()) {
            projectRepository.deleteById(id);
            
            // Publish event to Kafka
            kafkaTemplate.send("project-events", "DELETE", id.toString());
        } else {
            throw new RuntimeException("Project not found with id: " + id);
        }
    }
    
    public List<Project> getProjectsByStatus(String status) {
        try {
            Project.ProjectStatus projectStatus = Project.ProjectStatus.valueOf(status.toUpperCase());
            return projectRepository.findByStatus(projectStatus);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + status);
        }
    }
    
    public List<Project> getProjectsByManager(Long managerId) {
        return projectRepository.findByManagerId(managerId);
    }
    
    public List<Project> searchProjectsByName(String name) {
        return projectRepository.findByNameContainingIgnoreCase(name);
    }
}