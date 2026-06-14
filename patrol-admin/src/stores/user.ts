import { defineStore } from 'pinia'
import { ref } from 'vue'
import { loginApi } from '@/api/auth'
import router from '@/router'

/** 用户 Store */
export const useUserStore = defineStore('user', () => {
  const token = ref<string>(localStorage.getItem('token') || '')
  const username = ref<string>(localStorage.getItem('username') || '')
  const nickname = ref<string>(localStorage.getItem('nickname') || '')
  const permissions = ref<string[]>(
    JSON.parse(localStorage.getItem('permissions') || '[]')
  )

  /** 登录 */
  async function login(form: { username: string; password: string }) {
    const res = await loginApi(form)
    const data = res.data.data
    token.value = data.token
    username.value = data.username
    nickname.value = data.nickname
    permissions.value = data.permissions || []

    localStorage.setItem('token', data.token)
    localStorage.setItem('username', data.username)
    localStorage.setItem('nickname', data.nickname || '')
    localStorage.setItem('permissions', JSON.stringify(data.permissions || []))

    return res
  }

  /** 登出 */
  function logout() {
    token.value = ''
    username.value = ''
    nickname.value = ''
    permissions.value = []
    localStorage.clear()
    router.push('/login')
  }

  /** 是否有权限 */
  function hasPermission(perm: string): boolean {
    return permissions.value.includes(perm)
  }

  return { token, username, nickname, permissions, login, logout, hasPermission }
})
