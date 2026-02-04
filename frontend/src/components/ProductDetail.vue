<template>
  <div class="product-detail">
    <el-page-header @back="$router.go(-1)" :content="`Product: ${product.name}`" />

    <el-card class="product-card">
      <template #header>
        <div class="card-header">
          <h2>{{ product.name }}</h2>
        </div>
      </template>

      <el-descriptions :column="2" border>
        <el-descriptions-item label="ID">{{ product.id }}</el-descriptions-item>
        <el-descriptions-item label="Version">{{ product.version }}</el-descriptions-item>
        <el-descriptions-item label="Status">
          <el-tag :type="getStatusType(product.status)">{{ product.status }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="Owner ID">{{ product.ownerId }}</el-descriptions-item>
        <el-descriptions-item label="Created At">{{ product.createdAt }}</el-descriptions-item>
        <el-descriptions-item label="Updated At">{{ product.updatedAt }}</el-descriptions-item>
      </el-descriptions>

      <div class="section">
        <h3>Description</h3>
        <p>{{ product.description || 'No description available' }}</p>
      </div>

      <div class="section">
        <h3>Bill of Materials</h3>
        <el-table :data="bomItems" style="width: 100%">
          <el-table-column prop="componentName" label="Component" width="200"></el-table-column>
          <el-table-column prop="quantity" label="Quantity" width="100"></el-table-column>
          <el-table-column prop="unit" label="Unit" width="100"></el-table-column>
          <el-table-column prop="supplier" label="Supplier"></el-table-column>
          <el-table-column prop="cost" label="Cost" width="100"></el-table-column>
        </el-table>
      </div>

      <div class="section">
        <h3>Documents</h3>
        <el-table :data="documents" style="width: 100%">
          <el-table-column prop="name" label="Document Name"></el-table-column>
          <el-table-column prop="type" label="Type" width="120"></el-table-column>
          <el-table-column prop="size" label="Size" width="120">
            <template #default="scope">
              {{ formatFileSize(scope.row.size) }}
            </template>
          </el-table-column>
          <el-table-column prop="version" label="Version" width="120"></el-table-column>
          <el-table-column label="Actions" width="150">
            <template #default>
              <el-button size="small" type="primary">Download</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>

      <div class="section">
        <h3>Actions</h3>
        <el-button type="primary" @click="editProduct">Edit Product</el-button>
        <el-button type="success" @click="approveProduct">Approve</el-button>
        <el-button type="warning" @click="reviewProduct">Send for Review</el-button>
        <el-button type="danger" @click="deleteProduct">Delete</el-button>
      </div>
    </el-card>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'ProductDetail',
  props: ['id'],
  data() {
    return {
      product: {},
      bomItems: [],
      documents: []
    }
  },
  methods: {
    getStatusType(status) {
      const statusMap = {
        'DRAFT': 'info',
        'IN_DESIGN': 'warning',
        'IN_REVIEW': 'primary',
        'APPROVED': 'success',
        'IN_PRODUCTION': 'primary',
        'DISCONTINUED': 'danger'
      }
      return statusMap[status] || 'info'
    },
    formatFileSize(bytes) {
      if (!bytes) return 'N/A'
      const sizes = ['Bytes', 'KB', 'MB', 'GB']
      if (bytes === 0) return '0 Bytes'
      const i = parseInt(Math.floor(Math.log(bytes) / Math.log(1024)))
      return Math.round(bytes / Math.pow(1024, i) * 100) / 100 + ' ' + sizes[i]
    },
    async fetchProduct() {
      // Mock data for demo
      this.product = {
        id: this.id,
        name: `Product ${this.id}`,
        description: `Detailed description for Product ${this.id}. This product is designed to meet high-quality standards and includes advanced features.`,
        version: '1.0.0',
        status: 'IN_DESIGN',
        ownerId: 1,
        createdAt: '2023-06-15 10:30:00',
        updatedAt: '2023-06-20 14:45:00'
      }
      
      // Mock BOM items
      this.bomItems = [
        { id: 1, componentName: 'Processor', quantity: 1, unit: 'pcs', supplier: 'Intel', cost: 250.00 },
        { id: 2, componentName: 'Memory', quantity: 2, unit: 'pcs', supplier: 'Samsung', cost: 89.99 },
        { id: 3, componentName: 'Storage', quantity: 1, unit: 'pcs', supplier: 'WD', cost: 129.99 },
        { id: 4, componentName: 'Battery', quantity: 1, unit: 'pcs', supplier: 'LG', cost: 45.00 }
      ]
      
      // Mock documents
      this.documents = [
        { id: 1, name: 'Design Specifications', type: 'PDF', size: 2048576, version: '1.0' },
        { id: 2, name: 'Technical Drawing', type: 'DWG', size: 5120000, version: '1.0' },
        { id: 3, name: 'Testing Report', type: 'DOCX', size: 1024000, version: '1.0' }
      ]
    },
    editProduct() {
      this.$message.info('Edit functionality would open a modal in a real implementation')
    },
    approveProduct() {
      this.$message.success('Product approved successfully')
    },
    reviewProduct() {
      this.$message.info('Product sent for review')
    },
    deleteProduct() {
      this.$confirm('Are you sure you want to delete this product?', 'Confirm Delete', {
        confirmButtonText: 'OK',
        cancelButtonText: 'Cancel',
        type: 'warning'
      }).then(() => {
        this.$message.success('Product deleted successfully')
        this.$router.push('/products')
      })
    }
  },
  mounted() {
    this.fetchProduct()
  }
}
</script>

<style scoped>
.product-detail {
  padding: 20px;
}

.product-card {
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