<template>
  <div class="dashboard">
    <h2>📊 系统概览</h2>
    <el-row :gutter="20">
      <el-col :span="6" v-for="item in stats" :key="item.label">
        <el-card class="stat-card">
          <div class="stat-icon" :style="{ background: item.color }">{{ item.icon }}</div>
          <div class="stat-info">
            <div class="stat-label">{{ item.label }}</div>
            <div class="stat-value">{{ item.value }}</div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-card style="margin-top: 20px">
      <template #header>欢迎使用机房巡检管理系统</template>
      <p>您好，{{ userStore.nickname || userStore.username }}！</p>
      <p>当前系统包含以下模块：系统管理、巡检任务、巡检点位、整改记录、AI 分析。</p>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { reactive } from 'vue'
import { useUserStore } from '@/stores/user'

const userStore = useUserStore()

const stats = reactive([
  { label: '巡检任务', value: '--', icon: '📋', color: '#409EFF' },
  { label: '巡检点位', value: '--', icon: '📍', color: '#67C23A' },
  { label: '整改记录', value: '--', icon: '⚠️', color: '#E6A23C' },
  { label: 'AI 记录', value: '--', icon: '🤖', color: '#F56C6C' },
])
</script>

<style scoped>
.dashboard h2 { margin-bottom: 20px; }
.stat-card { display: flex; align-items: center; gap: 16px; }
.stat-icon { width: 56px; height: 56px; border-radius: 8px; display: flex;
  align-items: center; justify-content: center; font-size: 28px; color: #fff; }
.stat-label { font-size: 14px; color: #909399; }
.stat-value { font-size: 28px; font-weight: bold; color: #303133; }
</style>
