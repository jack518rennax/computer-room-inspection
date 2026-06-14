import request from '@/utils/request'

// ---- 用户 ----
export function getUserList()   { return request.get('/system/user/list') }
export function getUser(id: number) { return request.get('/system/user/detail/' + id) }
export function createUser(data: any) { return request.post('/system/user', data) }
export function updateUser(data: any) { return request.put('/system/user', data) }
export function deleteUser(id: number) { return request.delete('/system/user/' + id) }

// ---- 角色 ----
export function getRoleList()   { return request.get('/system/role/list') }
export function getRole(id: number) { return request.get('/system/role/detail/' + id) }
export function createRole(data: any) { return request.post('/system/role', data) }
export function updateRole(data: any) { return request.put('/system/role', data) }
export function deleteRole(id: number) { return request.delete('/system/role/' + id) }

// ---- 菜单 ----
export function getMenuList()   { return request.get('/system/menu/list') }
export function getMenu(id: number) { return request.get('/system/menu/detail/' + id) }
export function createMenu(data: any) { return request.post('/system/menu', data) }
export function updateMenu(data: any) { return request.put('/system/menu', data) }
export function deleteMenu(id: number) { return request.delete('/system/menu/' + id) }
