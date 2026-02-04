<template>
  <div class="project-detail">
    <el-page-header @back="$router.go(-1)" :content="`Project: ${project.name}`" />

    <el-card class="project-card">
      <template #header>
        <div class="card-header">
          <h2>{{ project.name }}</h2>
        </div>
      </template>

      <el-descriptions :column="2" border>
        <el-descriptions-item label="ID">{{ project.id }}</el-descriptions-item>
        <el-descriptions-item label="Manager ID">{{ project.managerId }}</el-descriptions-item>
        <el-descriptions-item label="Status">
          <el-tag :type="getStatusType(project.status)">{{ project.status }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="Start Date">{{ project.startDate }}</el-descriptions-item>
        <el-descriptions-item label="End Date">{{ project.endDate }}</el-descriptions-item>
        <el-descriptions-item label="Created At">{{ project.createdAt }}</el-descriptions-item>
      </el-descriptions>

      <div class="section">
        <h3>Description</h3>
        <p>{{ project.description || 'No description available' }}</p>
      </div>

      <div class="section">
        <h3>Tasks</h3>
        <el-button type="primary" @click="addTask" style="margin-bottom: 20px;">Add Task</el-button>
        <el-table :data="tasks" style="width: 100%">
          <el-table-column prop="id" label="ID" width="80"></el-table-column>
          <el-table-column prop="title" label="Title"></el-table-column>
          <el-table-column prop="assigneeId" label="Assignee" width="100"></el-table-column>
          <el-table-column prop="dueDate" label="Due Date" width="120"></el-table-column>
          <el-table-column prop="status" label="Status" width="120">
            <template #default="scope">
              <el-tag :type="getTaskStatusType(scope.row.status)">{{ scope.row.status }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="priority" label="Priority" width="120">
            <template #default="scope">
              <el-tag :type="getTaskPriorityType(scope.row.priority)">{{ scope.row.priority }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="Actions" width="150">
            <template #default="scope">
              <el-button size="small" @click="editTask(scope.row.id)">Edit</el-button>
              <el-button size="small" type="danger" @click="deleteTask(scope.row.id)">Delete</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>

      <div class="section">
        <h3>Team Members</h3>
        <el-table :data="teamMembers" style="width: 100%">
          <el-table-column prop="id" label="ID" width="80"></el-table-column>
          <el-table-column prop="name" label="Name"></el-table-column>
          <el-table-column prop="role" label="Role"></el-table-column>
          <el-table-column prop="email" label="Email"></el-table-column>
          <el-table-column label="Actions" width="150">
            <template #default>
              <el-button size="small">Contact</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>

      <div class="section">
        <h3>Actions</h3>
        <el-button type="primary" @click="editProject">Edit Project</el-button>
        <el-button type="success" @click="completeProject">Mark Complete</el-button>
        <el-button type="warning" @click="pauseProject">Pause Project</el-button>
        <el-button type="danger" @click="cancelProject">Cancel Project</el-button>
      </div>
    </el-card>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'ProjectDetail',
  props: ['id'],
  data() {
    return {
      project: {},
      tasks: [],
      teamMembers: []
    }
  },
  methods: {
    getStatusType(status) {
      const statusMap = {
        'PLANNING': 'info',
        'ACTIVE': 'primary',
        'ON_HOLD': 'warning',
        'COMPLETED': 'success',
        'CANCELLED': 'danger'
      }
      return statusMap[status] || 'info'
    },
    getTaskStatusType(status) {
      const statusMap = {
        'TO_DO': 'info',
        'IN_PROGRESS': 'primary',
        'REVIEW': 'warning',
        'DONE': 'success'
      }
      return statusMap[status] || 'info'
    },
    getTaskPriorityType(priority) {
      const priorityMap = {
        'LOW': 'success',
        'MEDIUM': 'primary',
        'HIGH': 'warning',
        'URGENT': 'danger'
      }
      return priorityMap[priority] || 'info'
    },
    async fetchProject() {
      // Mock data for demo
      this.project = {
        id: this.id,
        name: `Project ${this.id}`,
        description: `Detailed description for Project ${this.id}. This project aims to achieve specific business objectives within the defined timeline.`,
        startDate: '2023-06-01',
        endDate: '2023-12-31',
        status: 'ACTIVE',
        managerId: 1,
        createdAt: '2023-05-15 09:00:00'
      }
      
      // Mock tasks
      this.tasks = [
        { id: 1, title: 'Requirements Analysis', assigneeId: 2, dueDate: '2023-06-15', status: 'DONE', priority: 'HIGH' },
        { id: 2, title: 'System Design', assigneeId: 3, dueDate: '2023-06-30', status: 'DONE', priority: 'HIGH' },
        { id: 3, title: 'Development Phase 1', assigneeId: 4, dueDate: '2023-07-31', status: 'IN_PROGRESS', priority: 'MEDIUM' },
        { id: 4, title: 'Development Phase 2', assigneeId: 5, dueDate: '2023-08-31', status: 'TO_DO', priority: 'MEDIUM' },
        { id: 5, title: 'Testing', assigneeId: 6, dueDate: '2023-11-30', status: 'TO_DO', priority: 'HIGH' }
      ]
      
      // Mock team members
      this.teamMembers = [
        { id: 1, name: 'John Doe', role: 'Project Manager', email: 'john.doe@company.com' },
        { id: 2, name: 'Jane Smith', role: 'Analyst', email: 'jane.smith@company.com' },
        { id: 3, name: 'Robert Johnson', role: 'Architect', email: 'robert.johnson@company.com' },
        { id: 4, name: 'Emily Davis', role: 'Developer', email: 'emily.davis@company.com' },
        { id: 5, name: 'Michael Wilson', role: 'Developer', email: 'michael.wilson@company.com' },
        { id: 6, name: 'Sarah Brown', role: 'QA Engineer', email: 'sarah.brown@company.com' }
      ]
    },
    editProject() {
      this.$message.info('Edit functionality would open a modal in a real implementation')
    },
    completeProject() {
      this.$confirm('Are you sure you want to mark this project as complete?', 'Confirm Completion', {
        confirmButtonText: 'Yes',
        cancelButtonText: 'No',
        type: 'warning'
      }).then(() => {
        this.project.status = 'COMPLETED'
        this.$message.success('Project marked as completed')
      })
    },
    pauseProject() {
      this.project.status = 'ON_HOLD'
      this.$message.info('Project paused')
    },
    cancelProject() {
      this.$confirm('Are you sure you want to cancel this project?', 'Confirm Cancellation', {
        confirmButtonText: 'Yes',
        cancelButtonText: 'No',
        type: 'warning'
      }).then(() => {
        this.project.status = 'CANCELLED'
        this.$message.warning('Project cancelled')
      })
    },
    addTask() {
      this.$message.info('Add task functionality would open a modal in a real implementation')
    },
    editTask(taskId) {
      this.$message.info(`Edit task ${taskId} functionality would open a modal in a real implementation`)
    },
    deleteTask(taskId) {
      this.$confirm(`Are you sure you want to delete task ${taskId}?`, 'Confirm Delete', {
        confirmButtonText: 'Yes',
        cancelButtonText: 'No',
        type: 'warning'
      }).then(() => {
        this.tasks = this.tasks.filter(t => t.id !== taskId)
        this.$message.success('Task deleted')
      })
    }
  },
  mounted() {
    this.fetchProject()
  }
}
</script>

<style scoped>
.project-detail {
  padding: 20px;
}

.project-card {
  margin-top: 20px;
}

.section {
  margin: 30px 0;
}

.section h3 {
  border-bottom: 1px solid #eee;
  padding-bottom: 10px;
  margin-bottom: 20px;
}
</style>