<template>
  <div id="app">
    <el-container>
      <el-header>
        <el-menu
          :default-active="$route.path"
          mode="horizontal"
          router
          background-color="#545c64"
          text-color="#fff"
          active-text-color="#ffd04b">
          <el-menu-item index="/">Home</el-menu-item>
          <el-menu-item index="/products">Products</el-menu-item>
          <el-menu-item index="/projects">Projects</el-menu-item>
        </el-menu>
      </el-header>
      
      <el-main>
        <router-view />
      </el-main>
      
      <el-footer>
        <div class="footer-content">
          <p>PLM System &copy; {{ new Date().getFullYear() }}</p>
        </div>
      </el-footer>
    </el-container>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

.el-header {
  padding: 0;
}

.el-main {
  padding-top: 20px;
  min-height: calc(100vh - 120px);
}

.footer-content {
  text-align: center;
  padding: 20px;
}
</style>