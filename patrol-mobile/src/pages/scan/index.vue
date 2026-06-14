<template>
  <view class="page">
    <view class="scan-area">
      <view class="scan-icon">📷</view>
      <text class="scan-title">扫码巡检</text>
      <text class="scan-desc">扫描设备二维码 / 拍照巡检</text>
    </view>
    <view class="actions">
      <button class="btn" @tap="handleScan">📱 扫描二维码</button>
      <button class="btn outline" @tap="handleTakePhoto">📸 拍照</button>
      <button class="btn outline" @tap="handleChoosePhoto">🖼️ 选择照片</button>
    </view>
    <view v-if="photoUrl" class="preview">
      <image :src="photoUrl" mode="aspectFill" class="preview-img" />
    </view>
  </view>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const photoUrl = ref('')

function handleScan() {
  // #ifdef APP-PLUS
  uni.scanCode({
    success: (res: any) => {
      uni.showToast({ title: `扫码结果: ${res.result}`, icon: 'none' })
    },
    fail: () => uni.showToast({ title: '扫码取消', icon: 'none' }),
  })
  // #endif
  // #ifdef H5
  uni.showToast({ title: 'H5 暂不支持扫码，请使用拍照功能', icon: 'none' })
  // #endif
  // #ifdef MP-WEIXIN
  uni.scanCode({
    success: (res: any) => uni.showToast({ title: `扫码结果: ${res.result}`, icon: 'none' }),
    fail: () => uni.showToast({ title: '扫码取消', icon: 'none' }),
  })
  // #endif
}

function handleTakePhoto() {
  uni.chooseImage({
    count: 1,
    sourceType: ['camera'],
    success: (res: any) => {
      photoUrl.value = res.tempFilePaths[0]
      uni.showToast({ title: '拍照成功', icon: 'success' })
    },
    fail: () => uni.showToast({ title: '取消拍照', icon: 'none' }),
  })
}

function handleChoosePhoto() {
  uni.chooseImage({
    count: 1,
    sourceType: ['album'],
    success: (res: any) => {
      photoUrl.value = res.tempFilePaths[0]
      uni.showToast({ title: '已选择照片', icon: 'success' })
    },
  })
}
</script>

<style scoped>
.page { padding: 16px; min-height: 80vh; display: flex; flex-direction: column;
  align-items: center; justify-content: center; }
.scan-area { text-align: center; margin-bottom: 40px; }
.scan-icon { font-size: 72px; margin-bottom: 16px; }
.scan-title { font-size: 22px; font-weight: bold; color: #333; display: block; margin-bottom: 8px; }
.scan-desc { font-size: 14px; color: #999; display: block; }
.actions { width: 100%; max-width: 320px; display: flex; flex-direction: column; gap: 14px; }
.btn { width: 100%; background: #1677FF; color: #fff; border-radius: 8px; font-size: 16px;
  padding: 14px 0; text-align: center; border: none; }
.btn.outline { background: #fff; color: #1677FF; border: 1px solid #1677FF; }
.preview { margin-top: 24px; width: 100%; max-width: 320px; }
.preview-img { width: 100%; height: 240px; border-radius: 12px; }
</style>
