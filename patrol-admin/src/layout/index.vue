<template>
  <el-container class="layout-container">
    <el-aside :width="isCollapse ? '64px' : '220px'" class="aside">
      <div class="logo">
        <span v-if="!isCollapse">🏢 机房巡检系统</span>
        <span v-else>🏢</span>
      </div>
      <el-menu
        :default-active="activeMenu"
        router
        :collapse="isCollapse"
        background-color="#304156"
        text-color="#bfcbd9"
        active-text-color="#409EFF"
      >
        <el-menu-item index="/dashboard">
          <el-icon><HomeFilled /></el-icon>
          <span>首页</span>
        </el-menu-item>

        <el-sub-menu index="/system">
          <template #title>
            <el-icon><Setting /></el-icon>
            <span>系统管理</span>
          </template>
          <el-menu-item index="/system/user">用户管理</el-menu-item>
          <el-menu-item index="/system/role">角色管理</el-menu-item>
          <el-menu-item index="/system/menu">菜单管理</el-menu-item>
        </el-sub-menu>

        <el-menu-item index="/task">
          <el-icon><Clock /></el-icon>
          <span>巡检任务</span>
        </el-menu-item>

        <el-menu-item index="/location">
          <el-icon><Location /></el-icon>
          <span>巡检点位</span>
        </el-menu-item>

        <el-menu-item index="/rectify">
          <el-icon><Warning /></el-icon>
          <span>整改记录</span>
        </el-menu-item>

        <el-menu-item index="/ai">
          <el-icon><Cpu /></el-icon>
          <span>AI 记录</span>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <el-container>
      <el-header class="header">
        <div class="header-left">
          <el-icon class="collapse-btn" @click="isCollapse = !isCollapse">
            <Fold v-if="!isCollapse" /><Expand v-else />
          </el-icon>
        </div>
        <div class="header-right">
          <span class="username">{{ userStore.nickname || userStore.username }}</span>
          <el-button type="danger" text @click="handleLogout">退出</el-button>
        </div>
      </el-header>

      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRoute } from 'vue-router'
import { useUserStore } from '@/stores/user'

const route = useRoute()
const userStore = useUserStore()
const isCollapse = ref(false)
const activeMenu = computed(() => route.path)

function handleLogout() {
  userStore.logout()
}
</script>

<style scoped>
.layout-container { height: 100vh; }
.aside { background-color: #304156; overflow: hidden; }
.logo { height: 60px; display: flex; align-items: center; justify-content: center;
  color: #fff; font-size: 16px; font-weight: bold; border-bottom: 1px solid rgba(255,255,255,0.1); }
.logo span { white-space: nowrap; }
.header { background: #fff; display: flex; align-items: center; justify-content: space-between;
  border-bottom: 1px solid #e6e6e6; padding: 0 20px; height: 60px; }
.header-left { display: flex; align-items: center; }
.collapse-btn { font-size: 20px; cursor: pointer; }
.header-right { display: flex; align-items: center; gap: 12px; }
.username { color: #606266; }
.el-menu { border-right: none; }
</style>
