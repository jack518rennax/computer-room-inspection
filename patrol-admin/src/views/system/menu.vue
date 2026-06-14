<template>
  <div class="page">
    <el-card>
      <template #header>
        <div class="page-header"><span>菜单管理</span>
          <el-button type="primary" @click="handleAdd">新增菜单</el-button>
        </div>
      </template>
      <el-table :data="tableData" border stripe row-key="id" v-loading="loading"
        default-expand-all :tree-props="{ children: 'children' }">
        <el-table-column prop="name" label="名称" />
        <el-table-column prop="perms" label="权限标识" />
        <el-table-column prop="type" label="类型" width="80">
          <template #default="{ row }">
            <el-tag size="small">{{ { 0: '目录', 1: '菜单', 2: '按钮' }[row.type] }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="path" label="路径" />
        <el-table-column prop="sort" label="排序" width="60" />
        <el-table-column prop="status" label="状态" width="70">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'" size="small">
              {{ row.status === 1 ? '启用' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getMenuList } from '@/api/system'

const tableData = ref<any[]>([])
const loading = ref(false)

async function fetchData() {
  loading.value = true
  try { const res = await getMenuList(); tableData.value = res.data.data || [] }
  finally { loading.value = false }
}

function handleAdd() { /* TODO */ }

onMounted(fetchData)
</script>

<style scoped>
.page-header { display: flex; justify-content: space-between; align-items: center; }
</style>
