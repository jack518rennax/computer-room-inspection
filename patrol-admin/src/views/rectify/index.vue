<template>
  <div class="page">
    <el-card>
      <template #header>
        <div class="page-header"><span>整改记录</span>
          <el-button type="primary" @click="handleAdd">新增记录</el-button>
        </div>
      </template>
      <el-table :data="tableData" border stripe v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="title" label="标题" show-overflow-tooltip />
        <el-table-column prop="taskId" label="任务ID" width="80" />
        <el-table-column prop="locationId" label="点位ID" width="80" />
        <el-table-column prop="severity" label="严重程度" width="100">
          <template #default="{ row }">
            <el-tag :type="severityType(row.severity)">{{ row.severity }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="handler" label="处理人" width="100" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="statusType(row.status)">
              {{ { 0: '待整改', 1: '整改中', 2: '已完成' }[row.status] }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180">
          <template #default="{ row }">
            <el-button size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑记录' : '新增记录'" width="500px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="标题"><el-input v-model="form.title" /></el-form-item>
        <el-form-item label="描述"><el-input v-model="form.description" type="textarea" /></el-form-item>
        <el-form-item label="任务ID"><el-input-number v-model="form.taskId" /></el-form-item>
        <el-form-item label="点位ID"><el-input-number v-model="form.locationId" /></el-form-item>
        <el-form-item label="严重程度">
          <el-select v-model="form.severity">
            <el-option label="低" value="低" /><el-option label="中" value="中" /><el-option label="高" value="高" />
          </el-select>
        </el-form-item>
        <el-form-item label="处理人"><el-input v-model="form.handler" /></el-form-item>
        <el-form-item label="状态">
          <el-select v-model="form.status">
            <el-option label="待整改" :value="0" /><el-option label="整改中" :value="1" /><el-option label="已完成" :value="2" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getRectifyList, createRectify, updateRectify, deleteRectify } from '@/api/rectify'

const tableData = ref<any[]>([])
const loading = ref(false)
const dialogVisible = ref(false)
const isEdit = ref(false)
const form = ref<any>({ status: 0 })

const severityType = (s: string) => ({ '高': 'danger', '中': 'warning', '低': 'info' }[s] || 'info')
const statusType = (s: number) => ({ 0: 'danger', 1: 'warning', 2: 'success' }[s] || 'info')

async function fetchData() {
  loading.value = true
  try { const res = await getRectifyList(); tableData.value = res.data.data || [] }
  finally { loading.value = false }
}

function handleAdd() { isEdit.value = false; form.value = { status: 0 }; dialogVisible.value = true }
function handleEdit(row: any) { isEdit.value = true; form.value = { ...row }; dialogVisible.value = true }

async function handleSubmit() {
  if (isEdit.value) { await updateRectify(form.value); ElMessage.success('更新成功') }
  else { await createRectify(form.value); ElMessage.success('新增成功') }
  dialogVisible.value = false; fetchData()
}

async function handleDelete(id: number) {
  await ElMessageBox.confirm('确定删除？', '提示', { type: 'warning' })
  await deleteRectify(id); ElMessage.success('删除成功'); fetchData()
}

onMounted(fetchData)
</script>

<style scoped>
.page-header { display: flex; justify-content: space-between; align-items: center; }
</style>
