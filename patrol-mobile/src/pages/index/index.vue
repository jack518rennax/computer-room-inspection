<template>
  <view class="home-page">
    <view class="header">
      <text class="greeting">你好，{{ userInfo.nickname || userInfo.username || '巡检员' }}</text>
    </view>
    <view class="stats">
      <view class="stat-card" v-for="s in stats" :key="s.label" @tap="s.link && uni.navigateTo({ url: s.link })">
        <text class="stat-value">{{ s.value }}</text>
        <text class="stat-label">{{ s.label }}</text>
      </view>
    </view>
    <view class="menu-grid">
      <view class="menu-item" @tap="goTo('/pages/task/list')">
        <view class="menu-icon">📋</view>
        <text>巡检任务</text>
      </view>
      <view class="menu-item" @tap="goTo('/pages/scan/index')">
        <view class="menu-icon">📷</view>
        <text>扫码巡检</text>
      </view>
      <view class="menu-item" @tap="goTo('/pages/rectify/index')">
        <view class="menu-icon">⚠️</view>
        <text>提交整改</text>
      </view>
      <view class="menu-item" @tap="handleLogout">
        <view class="menu-icon">🚪</view>
        <text>退出登录</text>
      </view>
    </view>
  </view>
</template>

<script setup lang="ts">
import { reactive } from 'vue'

const userInfo = reactive(JSON.parse(uni.getStorageSync('userInfo') || '{}'))
const stats = reactive([
  { label: '待处理任务', value: '--' },
  { label: '待整改', value: '--' },
  { label: '已完成', value: '--' },
])

function goTo(url: string) { uni.navigateTo({ url }) }

function handleLogout() {
  uni.removeStorageSync('token')
  uni.removeStorageSync('userInfo')
  uni.reLaunch({ url: '/pages/login/index' })
}
</script>

<style scoped>
.home-page { padding: 16px; }
.header { padding: 20px 0; }
.greeting { font-size: 22px; font-weight: bold; color: #1677FF; }
.stats { display: flex; gap: 12px; margin-bottom: 24px; }
.stat-card { flex: 1; background: #fff; border-radius: 12px; padding: 16px;
  text-align: center; box-shadow: 0 2px 8px rgba(0,0,0,0.06); }
.stat-value { font-size: 24px; font-weight: bold; color: #1677FF; display: block; }
.stat-label { font-size: 12px; color: #999; margin-top: 4px; display: block; }
.menu-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 12px; }
.menu-item { background: #fff; border-radius: 12px; padding: 24px 16px;
  text-align: center; box-shadow: 0 2px 8px rgba(0,0,0,0.06); }
.menu-icon { font-size: 32px; margin-bottom: 8px; }
.menu-item text { font-size: 14px; color: #333; }
</style>
