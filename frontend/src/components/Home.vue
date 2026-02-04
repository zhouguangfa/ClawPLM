<template>
  <div class="home">
    <el-row :gutter="20">
      <el-col :span="24">
        <h1>Welcome to PLM System</h1>
        <p>Product Lifecycle Management System</p>
      </el-col>
    </el-row>
    
    <el-row :gutter="20" class="dashboard-cards">
      <el-col :span="6">
        <el-card class="dashboard-card">
          <template #header>
            <div class="card-header">
              <span>Products</span>
            </div>
          </template>
          <div class="card-content">
            <i class="el-icon-document icon-large"></i>
            <h3>{{ productCount }}</h3>
            <p>Total Products</p>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="6">
        <el-card class="dashboard-card">
          <template #header>
            <div class="card-header">
              <span>Projects</span>
            </div>
          </template>
          <div class="card-content">
            <i class="el-icon-folder icon-large"></i>
            <h3>{{ projectCount }}</h3>
            <p>Total Projects</p>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="6">
        <el-card class="dashboard-card">
          <template #header>
            <div class="card-header">
              <span>Tasks</span>
            </div>
          </template>
          <div class="card-content">
            <i class="el-icon-tickets icon-large"></i>
            <h3>{{ taskCount }}</h3>
            <p>Total Tasks</p>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="6">
        <el-card class="dashboard-card">
          <template #header>
            <div class="card-header">
              <span>Users</span>
            </div>
          </template>
          <div class="card-content">
            <i class="el-icon-user icon-large"></i>
            <h3>{{ userCount }}</h3>
            <p>Total Users</p>
          </div>
        </el-card>
      </el-col>
    </el-row>
    
    <el-row :gutter="20" class="quick-actions">
      <el-col :span="24">
        <h3>Quick Actions</h3>
        <el-button-group>
          <el-button type="primary" @click="$router.push('/products')">View Products</el-button>
          <el-button type="success" @click="$router.push('/projects')">View Projects</el-button>
          <el-button type="warning">Create Product</el-button>
          <el-button type="info">Create Project</el-button>
        </el-button-group>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: 'Home',
  data() {
    return {
      productCount: 0,
      projectCount: 0,
      taskCount: 0,
      userCount: 0
    }
  },
  mounted() {
    this.loadData()
  },
  methods: {
    loadData() {
      // Mock data - in real app, this would come from API
      this.productCount = 24
      this.projectCount = 12
      this.taskCount = 56
      this.userCount = 8
    }
  }
}
</script>

<style scoped>
.home {
  padding: 20px;
}

.dashboard-cards {
  margin: 20px 0;
}

.dashboard-card {
  text-align: center;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.card-content h3 {
  margin: 10px 0;
  font-size: 2em;
}

.icon-large {
  font-size: 3em;
  color: #409EFF;
}

.quick-actions {
  margin-top: 30px;
}

.quick-actions h3 {
  margin-bottom: 15px;
}
</style>