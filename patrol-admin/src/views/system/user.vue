<template>
  <div class="page">
    <el-card>
      <template #header>
        <div class="page-header">
          <span>用户管理</span>
          <div>
            <el-button type="primary" @click="handleAdd">新增用户</el-button>
          </div>
        </div>
      </template>
      <el-table :data="tableData" border stripe v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="username" label="用户名" />
        <el-table-column prop="nickname" label="昵称" />
        <el-table-column prop="email" label="邮箱" />
        <el-table-column prop="phone" label="手机号" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">
              {{ row.status === 1 ? '启用' : '禁用' }}
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

    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑用户' : '新增用户'" width="500px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="用户名"><el-input v-model="form.username" /></el-form-item>
        <el-form-item v-if="!isEdit" label="密码"><el-input v-model="form.password" type="password" /></el-form-item>
        <el-form-item label="昵称"><el-input v-model="form.nickname" /></el-form-item>
        <el-form-item label="邮箱"><el-input v-model="form.email" /></el-form-item>
        <el-form-item label="手机号"><el-input v-model="form.phone" /></el-form-item>
        <el-form-item label="状态">
          <el-switch v-model="form.status" :active-value="1" :inactive-value="0" />
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
import { getUserList, createUser, updateUser, deleteUser } from '@/api/system'

const tableData = ref<any[]>([])
const loading = ref(false)
const dialogVisible = ref(false)
const isEdit = ref(false)
const form = ref<any>({ status: 1 })

async function fetchData() {
  loading.value = true
  try {
    const res = await getUserList()
    tableData.value = res.data.data || []
  } finally { loading.value = false }
}

function handleAdd() { isEdit.value = false; form.value = { status: 1 }; dialogVisible.value = true }
function handleEdit(row: any) { isEdit.value = true; form.value = { ...row }; dialogVisible.value = true }

async function handleSubmit() {
  if (isEdit.value) {
    await updateUser(form.value); ElMessage.success('更新成功')
  } else {
    await createUser(form.value); ElMessage.success('新增成功')
  }
  dialogVisible.value = false
  fetchData()
}

async function handleDelete(id: number) {
  await ElMessageBox.confirm('确定删除该用户？', '提示', { type: 'warning' })
  await deleteUser(id)
  ElMessage.success('删除成功')
  fetchData()
}

onMounted(fetchData)
</script>

<style scoped>
.page-header { display: flex; justify-content: space-between; align-items: center; }
</style>
