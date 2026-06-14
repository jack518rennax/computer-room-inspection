<template>
  <div class="page">
    <el-card>
      <template #header>
        <div class="page-header"><span>巡检任务</span>
          <div style="display:flex;gap:8px">
            <el-upload :show-file-list="false" :before-upload="handleImport" accept=".xlsx">
              <el-button>导入 Excel</el-button>
            </el-upload>
            <el-button @click="handleExport">导出 Excel</el-button>
            <el-button type="primary" @click="handleAdd">新增任务</el-button>
          </div>
        </div>
      </template>
      <el-table :data="tableData" border stripe v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="任务名称" />
        <el-table-column prop="description" label="描述" show-overflow-tooltip />
        <el-table-column prop="locationId" label="点位ID" width="100" />
        <el-table-column prop="cronExpression" label="Cron" width="140" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'info'">
              {{ row.status === 1 ? '运行中' : '已停止' }}
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

    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑任务' : '新增任务'" width="500px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="名称"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="描述"><el-input v-model="form.description" /></el-form-item>
        <el-form-item label="点位ID"><el-input-number v-model="form.locationId" /></el-form-item>
        <el-form-item label="Cron"><el-input v-model="form.cronExpression" placeholder="0 */1 * * * ?" /></el-form-item>
        <el-form-item label="状态">
          <el-switch v-model="form.status" :active-value="1" :inactive-value="0" active-text="运行" inactive-text="停止" />
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
import { getTaskList, createTask, updateTask, deleteTask, exportTask, importTask } from '@/api/task'

const tableData = ref<any[]>([])
const loading = ref(false)
const dialogVisible = ref(false)
const isEdit = ref(false)
const form = ref<any>({ status: 0 })

async function fetchData() {
  loading.value = true
  try { const res = await getTaskList(); tableData.value = res.data.data || [] }
  finally { loading.value = false }
}

function handleAdd() { isEdit.value = false; form.value = { status: 0 }; dialogVisible.value = true }
function handleEdit(row: any) { isEdit.value = true; form.value = { ...row }; dialogVisible.value = true }

async function handleSubmit() {
  if (isEdit.value) { await updateTask(form.value); ElMessage.success('更新成功') }
  else { await createTask(form.value); ElMessage.success('新增成功') }
  dialogVisible.value = false; fetchData()
}

async function handleDelete(id: number) {
  await ElMessageBox.confirm('确定删除？', '提示', { type: 'warning' })
  await deleteTask(id); ElMessage.success('删除成功'); fetchData()
}

async function handleExport() {
  const res = await exportTask()
  const url = URL.createObjectURL(res.data)
  const a = document.createElement('a'); a.href = url; a.download = '巡检任务.xlsx'; a.click()
  URL.revokeObjectURL(url)
  ElMessage.success('导出成功')
}

async function handleImport(file: File) {
  await importTask(file); ElMessage.success('导入成功'); fetchData()
  return false // 阻止 el-upload 默认上传
}

onMounted(fetchData)
</script>

<style scoped>
.page-header { display: flex; justify-content: space-between; align-items: center; }
</style>
