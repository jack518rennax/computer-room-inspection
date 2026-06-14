<template>
  <view class="page" v-if="task">
    <view class="section">
      <text class="label">任务名称</text>
      <text class="value">{{ task.name }}</text>
    </view>
    <view class="section">
      <text class="label">描述</text>
      <text class="value">{{ task.description || '无' }}</text>
    </view>
    <view class="section">
      <text class="label">关联点位 ID</text>
      <text class="value">{{ task.locationId || '--' }}</text>
    </view>
    <view class="section">
      <text class="label">Cron 表达式</text>
      <text class="value">{{ task.cronExpression || '--' }}</text>
    </view>
    <view class="section">
      <text class="label">状态</text>
      <text class="value" :style="{ color: task.status === 1 ? '#52c41a' : '#999' }">
        {{ task.status === 1 ? '运行中' : '已停止' }}
      </text>
    </view>
    <view class="actions">
      <button class="btn" @tap="goScan">📷 扫码巡检</button>
      <button class="btn outline" @tap="goRectify">⚠️ 提交整改</button>
    </view>
  </view>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { get } from '@/utils/request'

interface Task { id: number; name: string; description?: string; locationId?: number; cronExpression?: string; status: number }
const task = ref<Task | null>(null)

onMounted(async () => {
  const pages = getCurrentPages()
  const id = (pages[pages.length - 1] as any).options?.id
  if (id) {
    try { task.value = await get<Task>('/task/detail/' + id) }
    catch { uni.showToast({ title: '加载失败', icon: 'none' }) }
  }
})

function goScan() { uni.navigateTo({ url: '/pages/scan/index' }) }
function goRectify() { uni.navigateTo({ url: '/pages/rectify/index' }) }
</script>

<style scoped>
.page { padding: 16px; }
.section { background: #fff; border-radius: 8px; padding: 14px 16px; margin-bottom: 10px; }
.label { font-size: 12px; color: #999; display: block; margin-bottom: 4px; }
.value { font-size: 16px; color: #333; display: block; }
.actions { display: flex; gap: 12px; margin-top: 24px; }
.btn { flex: 1; background: #1677FF; color: #fff; border-radius: 8px; font-size: 16px; padding: 12px 0;
  text-align: center; border: none; }
.btn.outline { background: #fff; color: #1677FF; border: 1px solid #1677FF; }
</style>
