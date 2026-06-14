/**
 * uni-app 请求工具
 * 自动携带 JWT Token，统一错误处理
 */

// 后端 API 基础地址
const BASE_URL = 'http://localhost:8080'

interface RequestOptions {
  url: string
  method?: 'GET' | 'POST' | 'PUT' | 'DELETE'
  data?: any
  header?: any
}

/** 发起请求 */
export function request<T = any>(options: RequestOptions): Promise<T> {
  const token = uni.getStorageSync('token') || ''

  return new Promise((resolve, reject) => {
    uni.request({
      url: BASE_URL + options.url,
      method: options.method || 'GET',
      data: options.data,
      header: {
        'Content-Type': 'application/json',
        ...(token ? { 'Authorization': `Bearer ${token}` } : {}),
        ...options.header,
      },
      success: (res: any) => {
        const data = res.data
        if (data.code === 200) {
          resolve(data.data)
        } else if (data.code === 401) {
          uni.removeStorageSync('token')
          uni.removeStorageSync('userInfo')
          uni.reLaunch({ url: '/pages/login/index' })
          reject(new Error(data.msg || '未授权'))
        } else {
          uni.showToast({ title: data.msg || '请求失败', icon: 'none' })
          reject(new Error(data.msg))
        }
      },
      fail: (err) => {
        uni.showToast({ title: '网络错误', icon: 'none' })
        reject(err)
      },
    })
  })
}

// 便捷方法
export const get = <T>(url: string) => request<T>({ url, method: 'GET' })
export const post = <T>(url: string, data?: any) => request<T>({ url, method: 'POST', data })
export const put = <T>(url: string, data?: any) => request<T>({ url, method: 'PUT', data })
export const del = <T>(url: string) => request<T>({ url, method: 'DELETE' })
