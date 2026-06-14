<template>
  <view class="page">
    <view class="form">
      <view class="form-item">
        <text class="label">问题标题</text>
        <input v-model="form.title" placeholder="请输入问题标题" class="input" />
      </view>
      <view class="form-item">
        <text class="label">任务 ID</text>
        <input v-model="form.taskId" type="number" placeholder="关联任务 ID" class="input" />
      </view>
      <view class="form-item">
        <text class="label">点位 ID</text>
        <input v-model="form.locationId" type="number" placeholder="关联点位 ID" class="input" />
      </view>
      <view class="form-item">
        <text class="label">严重程度</text>
        <view class="radio-group">
          <view class="radio-item" v-for="s in severities" :key="s.value"
            :class="{ active: form.severity === s.value }" @tap="form.severity = s.value">
            <text>{{ s.label }}</text>
          </view>
        </view>
      </view>
      <view class="form-item">
        <text class="label">问题描述</text>
        <textarea v-model="form.description" placeholder="请描述发现的问题" class="textarea" />
      </view>
      <view class="form-item">
        <text class="label">处理人</text>
        <input v-model="form.handler" placeholder="请输入处理人" class="input" />
      </view>
      <button class="submit-btn" :loading="loading" @tap="handleSubmit">提交整改记录</button>
    </view>
  </view>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import { post } from '@/utils/request'

const form = reactive({
  title: '', taskId: '', locationId: '', severity: '中', description: '', handler: '',
})
const severities = [
  { label: '低', value: '低' }, { label: '中', value: '中' }, { label: '高', value: '高' },
]
const loading = ref(false)

async function handleSubmit() {
  if (!form.title) { uni.showToast({ title: '请输入问题标题', icon: 'none' }); return }
  loading.value = true
  try {
    await post('/rectify', {
      ...form,
      taskId: Number(form.taskId) || null,
      locationId: Number(form.locationId) || null,
      status: 0,
    })
    uni.showToast({ title: '提交成功', icon: 'success' })
    setTimeout(() => uni.navigateBack(), 1200)
  } catch { /* error handled */ }
  finally { loading.value = false }
}
</script>

<style scoped>
.page { padding: 16px; }
.form { background: #fff; border-radius: 12px; padding: 20px; }
.form-item { margin-bottom: 18px; }
.label { font-size: 14px; color: #333; font-weight: 500; display: block; margin-bottom: 8px; }
.input { border: 1px solid #e8e8e8; border-radius: 8px; padding: 12px; font-size: 14px; background: #fafafa; }
.textarea { border: 1px solid #e8e8e8; border-radius: 8px; padding: 12px; font-size: 14px;
  background: #fafafa; min-height: 100px; width: 100%; box-sizing: border-box; }
.radio-group { display: flex; gap: 12px; }
.radio-item { padding: 8px 20px; border-radius: 6px; border: 1px solid #e8e8e8;
  font-size: 14px; color: #666; }
.radio-item.active { background: #1677FF; color: #fff; border-color: #1677FF; }
.submit-btn { width: 100%; background: #1677FF; color: #fff; border-radius: 8px;
  font-size: 16px; padding: 14px 0; margin-top: 24px; border: none; }
</style>
