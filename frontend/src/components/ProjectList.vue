<template>
  <div class="project-list">
    <el-row :gutter="20" class="header-row">
      <el-col :span="12">
        <h2>Projects</h2>
      </el-col>
      <el-col :span="12" class="text-right">
        <el-button type="primary" @click="createProject">New Project</el-button>
      </el-col>
    </el-row>
    
    <el-row :gutter="20" class="filters-row">
      <el-col :span="6">
        <el-input 
          v-model="searchQuery" 
          placeholder="Search projects..." 
          @keyup.enter="searchProjects"
          clearable>
          <template #append>
            <el-button @click="searchProjects">
              <el-icon><Search /></el-icon>
            </el-button>
          </template>
        </el-input>
      </el-col>
      <el-col :span="6">
        <el-select v-model="statusFilter" placeholder="Filter by status" @change="filterProjects" clearable>
          <el-option label="Planning" value="PLANNING"></el-option>
          <el-option label="Active" value="ACTIVE"></el-option>
          <el-option label="On Hold" value="ON_HOLD"></el-option>
          <el-option label="Completed" value="COMPLETED"></el-option>
          <el-option label="Cancelled" value="CANCELLED"></el-option>
        </el-select>
      </el-col>
    </el-row>
    
    <el-table 
      :data="projects" 
      stripe 
      style="width: 100%" 
      @row-click="viewProject"
      v-loading="loading">
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <el-table-column prop="name" label="Name" width="200"></el-table-column>
      <el-table-column prop="description" label="Description" show-overflow-tooltip></el-table-column>
      <el-table-column prop="startDate" label="Start Date" width="120">
        <template #default="scope">
          {{ formatDate(scope.row.startDate) }}
        </template>
      </el-table-column>
      <el-table-column prop="endDate" label="End Date" width="120">
        <template #default="scope">
          {{ formatDate(scope.row.endDate) }}
        </template>
      </el-table-column>
      <el-table-column prop="status" label="Status" width="120">
        <template #default="scope">
          <el-tag :type="getStatusTagType(scope.row.status)">
            {{ scope.row.status.replace('_', ' ') }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createdAt" label="Created At" width="180">
        <template #default="scope">
          {{ formatDate(scope.row.createdAt) }}
        </template>
      </el-table-column>
    </el-table>
    
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="totalProjects"
      class="pagination">
    </el-pagination>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'ProjectList',
  data() {
    return {
      projects: [],
      loading: true,
      searchQuery: '',
      statusFilter: '',
      currentPage: 1,
      pageSize: 10,
      totalProjects: 0
    }
  },
  mounted() {
    this.loadProjects()
  },
  methods: {
    async loadProjects() {
      this.loading = true
      try {
        const response = await axios.get('/api/projects')
        this.projects = response.data
        this.totalProjects = response.data.length
      } catch (error) {
        console.error('Error fetching projects:', error)
      } finally {
        this.loading = false
      }
    },
    viewProject(row) {
      this.$router.push(`/projects/${row.id}`)
    },
    createProject() {
      // TODO: Implement create project dialog
      this.$message.info('Create project functionality to be implemented')
    },
    searchProjects() {
      if (this.searchQuery.trim() === '') {
        this.loadProjects()
      } else {
        // TODO: Implement search API call
        this.$message.info(`Searching for: ${this.searchQuery}`)
      }
    },
    filterProjects() {
      if (this.statusFilter === '') {
        this.loadProjects()
      } else {
        // TODO: Implement status filter API call
        this.$message.info(`Filtering by status: ${this.statusFilter}`)
      }
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.loadProjects()
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.loadProjects()
    },
    getStatusTagType(status) {
      const statusTypes = {
        'PLANNING': 'info',
        'ACTIVE': 'success',
        'ON_HOLD': 'warning',
        'COMPLETED': 'primary',
        'CANCELLED': 'danger'
      }
      return statusTypes[status] || 'info'
    },
    formatDate(dateString) {
      if (!dateString) return ''
      const date = new Date(dateString)
      return date.toLocaleDateString()
    }
  }
}
</script>

<style scoped>
.header-row {
  margin-bottom: 20px;
}

.filters-row {
  margin-bottom: 20px;
}

.text-right {
  text-align: right;
}

.pagination {
  margin-top: 20px;
  text-align: right;
}
</style>