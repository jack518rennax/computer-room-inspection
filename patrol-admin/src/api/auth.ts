import request from '@/utils/request'

/** 登录 */
export function loginApi(data: { username: string; password: string }) {
  return request.post('/login', data)
}
