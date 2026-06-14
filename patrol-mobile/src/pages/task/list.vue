<template>
  <view class="page">
    <view class="task-list">
      <view class="task-card" v-for="task in tasks" :key="task.id"
        @tap="goDetail(task.id)">
        <view class="task-top">
          <text class="task-name">{{ task.name }}</text>
          <text class="task-status" :class="task.status === 1 ? 'running' : 'stopped'">
            {{ task.status === 1 ? '运行中' : '已停止' }}
          </text>
        </view>
        <text class="task-desc" v-if="task.description">{{ task.description }}</text>
        <view class="task-bottom">
          <text class="task-location">点位: {{ task.locationId || '--' }}</text>
          <text class="task-cron">{{ task.cronExpression || '--' }}</text>
        </view>
      </view>
      <view v-if="tasks.length === 0" class="empty">暂无巡检任务</view>
    </view>
  </view>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { get } from '@/utils/request'

interface Task { id: number; name: string; description?: string; locationId?: number; cronExpression?: string; status: number }
const tasks = ref<Task[]>([])

async function fetchTasks() {
  try {
    tasks.value = await get<Task[]>('/task/list') || []
  } catch { /* error handled in request util */ }
}

function goDetail(id: number) {
  uni.navigateTo({ url: `/pages/task/detail?id=${id}` })
}

onMounted(fetchTasks)
</script>

<style scoped>
.page { padding: 16px; }
.task-card { background: #fff; border-radius: 12px; padding: 16px; margin-bottom: 12px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.06); }
.task-top { display: flex; justify-content: space-between; align-items: center; margin-bottom: 6px; }
.task-name { font-size: 16px; font-weight: bold; color: #333; }
.task-status { font-size: 12px; padding: 2px 8px; border-radius: 4px; }
.task-status.running { background: #f6ffed; color: #52c41a; }
.task-status.stopped { background: #f5f5f5; color: #999; }
.task-desc { font-size: 13px; color: #666; margin-bottom: 8px; display: block; }
.task-bottom { display: flex; justify-content: space-between; }
.task-location, .task-cron { font-size: 12px; color: #999; }
.empty { text-align: center; padding: 60px; color: #999; }
</style>
