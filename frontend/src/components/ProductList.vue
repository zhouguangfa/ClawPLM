<template>
  <div class="product-list">
    <el-row :gutter="20" class="header-row">
      <el-col :span="12">
        <h2>Products</h2>
      </el-col>
      <el-col :span="12" class="text-right">
        <el-button type="primary" @click="createProduct">New Product</el-button>
      </el-col>
    </el-row>
    
    <el-row :gutter="20" class="filters-row">
      <el-col :span="6">
        <el-input 
          v-model="searchQuery" 
          placeholder="Search products..." 
          @keyup.enter="searchProducts"
          clearable>
          <template #append>
            <el-button @click="searchProducts">
              <el-icon><Search /></el-icon>
            </el-button>
          </template>
        </el-input>
      </el-col>
      <el-col :span="6">
        <el-select v-model="statusFilter" placeholder="Filter by status" @change="filterProducts" clearable>
          <el-option label="Draft" value="DRAFT"></el-option>
          <el-option label="In Design" value="IN_DESIGN"></el-option>
          <el-option label="In Review" value="IN_REVIEW"></el-option>
          <el-option label="Approved" value="APPROVED"></el-option>
          <el-option label="In Production" value="IN_PRODUCTION"></el-option>
          <el-option label="Discontinued" value="DISCONTINUED"></el-option>
        </el-select>
      </el-col>
    </el-row>
    
    <el-table 
      :data="products" 
      stripe 
      style="width: 100%" 
      @row-click="viewProduct"
      v-loading="loading">
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <el-table-column prop="name" label="Name" width="200"></el-table-column>
      <el-table-column prop="description" label="Description" show-overflow-tooltip></el-table-column>
      <el-table-column prop="version" label="Version" width="100"></el-table-column>
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
      :total="totalProducts"
      class="pagination">
    </el-pagination>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'ProductList',
  data() {
    return {
      products: [],
      loading: true,
      searchQuery: '',
      statusFilter: '',
      currentPage: 1,
      pageSize: 10,
      totalProducts: 0
    }
  },
  mounted() {
    this.loadProducts()
  },
  methods: {
    async loadProducts() {
      this.loading = true
      try {
        const response = await axios.get('/api/products')
        this.products = response.data
        this.totalProducts = response.data.length
      } catch (error) {
        console.error('Error fetching products:', error)
      } finally {
        this.loading = false
      }
    },
    viewProduct(row) {
      this.$router.push(`/products/${row.id}`)
    },
    createProduct() {
      // TODO: Implement create product dialog
      this.$message.info('Create product functionality to be implemented')
    },
    searchProducts() {
      if (this.searchQuery.trim() === '') {
        this.loadProducts()
      } else {
        // TODO: Implement search API call
        this.$message.info(`Searching for: ${this.searchQuery}`)
      }
    },
    filterProducts() {
      if (this.statusFilter === '') {
        this.loadProducts()
      } else {
        // TODO: Implement status filter API call
        this.$message.info(`Filtering by status: ${this.statusFilter}`)
      }
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.loadProducts()
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.loadProducts()
    },
    getStatusTagType(status) {
      const statusTypes = {
        'DRAFT': 'info',
        'IN_DESIGN': 'warning',
        'IN_REVIEW': 'primary',
        'APPROVED': 'success',
        'IN_PRODUCTION': 'danger',
        'DISCONTINUED': 'info'
      }
      return statusTypes[status] || 'info'
    },
    formatDate(dateString) {
      const date = new Date(dateString)
      return date.toLocaleDateString() + ' ' + date.toLocaleTimeString()
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