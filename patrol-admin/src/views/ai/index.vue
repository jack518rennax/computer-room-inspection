<template>
  <div class="page">
    <el-card>
      <template #header><span>AI 识别记录</span></template>
      <el-table :data="tableData" border stripe v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="taskId" label="任务ID" width="80" />
        <el-table-column prop="locationId" label="点位ID" width="80" />
        <el-table-column prop="imageUrl" label="图片" show-overflow-tooltip />
        <el-table-column prop="modelName" label="模型" width="120" />
        <el-table-column prop="confidence" label="置信度" width="100">
          <template #default="{ row }">
            {{ row.confidence ? (row.confidence * 100).toFixed(1) + '%' : '--' }}
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="statusType(row.status)" size="small">
              {{ { 0: '处理中', 1: '已完成', 2: '失败' }[row.status] }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="100">
          <template #default="{ row }">
            <el-button size="small" type="danger" @click="handleDelete(row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getAiList, deleteAi } from '@/api/ai'

const tableData = ref<any[]>([])
const loading = ref(false)
const statusType = (s: number) => ({ 0: 'warning', 1: 'success', 2: 'danger' }[s] || 'info')

async function fetchData() {
  loading.value = true
  try { const res = await getAiList(); tableData.value = res.data.data || [] }
  finally { loading.value = false }
}

async function handleDelete(id: number) {
  await ElMessageBox.confirm('确定删除？', '提示', { type: 'warning' })
  await deleteAi(id); ElMessage.success('删除成功'); fetchData()
}

onMounted(fetchData)
</script>

<style scoped>
.page-header { display: flex; justify-content: space-between; align-items: center; }
</style>
