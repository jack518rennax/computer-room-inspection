import axios, { AxiosResponse, InternalAxiosRequestConfig } from 'axios'
import { ElMessage } from 'element-plus'
import router from '@/router'

/** Axios 实例 */
const service = axios.create({
  baseURL: '/api',
  timeout: 15000,
})

/** 请求拦截器：自动携带 JWT Token */
service.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    const token = localStorage.getItem('token')
    if (token && config.headers) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error) => Promise.reject(error)
)

/** 响应拦截器：统一错误处理 */
service.interceptors.response.use(
  (response: AxiosResponse) => {
    const res = response.data
    // 后端统一返回体 R: { code, msg, data }
    if (res.code !== 200) {
      ElMessage.error(res.msg || '请求失败')
      if (res.code === 401) {
        localStorage.clear()
        router.push('/login')
      }
      return Promise.reject(new Error(res.msg))
    }
    return response
  },
  (error) => {
    if (error.response?.status === 401) {
      localStorage.clear()
      router.push('/login')
      ElMessage.error('登录已过期，请重新登录')
    } else if (error.response?.status === 403) {
      ElMessage.error('没有访问权限')
    } else {
      ElMessage.error(error.message || '网络错误')
    }
    return Promise.reject(error)
  }
)

export default service
