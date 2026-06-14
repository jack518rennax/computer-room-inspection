<template>
  <view class="login-page">
    <view class="login-header">
      <text class="title">机房巡检系统</text>
      <text class="subtitle">移动端</text>
    </view>
    <view class="login-form">
      <view class="form-item">
        <input v-model="form.username" placeholder="请输入用户名" class="input" />
      </view>
      <view class="form-item">
        <input v-model="form.password" type="password" placeholder="请输入密码" class="input" />
      </view>
      <button class="login-btn" :loading="loading" @tap="handleLogin">登 录</button>
    </view>
  </view>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import { request } from '@/utils/request'

const form = reactive({ username: 'admin', password: 'admin123' })
const loading = ref(false)

async function handleLogin() {
  if (!form.username || !form.password) {
    uni.showToast({ title: '请输入用户名和密码', icon: 'none' })
    return
  }
  loading.value = true
  try {
    const res: any = await request({ url: '/login', method: 'POST', data: form })
    uni.setStorageSync('token', res.token)
    uni.setStorageSync('userInfo', JSON.stringify(res))
    uni.showToast({ title: '登录成功', icon: 'success' })
    setTimeout(() => uni.switchTab({ url: '/pages/index/index' }), 800)
  } catch {
    // 错误已在 request 中处理
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-page {
  min-height: 100vh; display: flex; flex-direction: column; align-items: center;
  justify-content: center; background: linear-gradient(135deg, #1677FF, #0958d9); padding: 40px;
}
.login-header { text-align: center; margin-bottom: 40px; }
.title { font-size: 28px; color: #fff; font-weight: bold; }
.subtitle { font-size: 16px; color: rgba(255,255,255,0.8); margin-top: 8px; display: block; }
.login-form { width: 100%; max-width: 340px; }
.form-item { margin-bottom: 16px; }
.input { background: rgba(255,255,255,0.95); border-radius: 8px; padding: 14px 16px; font-size: 16px; }
.login-btn { width: 100%; background: #fff; color: #1677FF; border-radius: 8px; font-size: 18px;
  font-weight: bold; margin-top: 10px; padding: 12px 0; }
</style>
