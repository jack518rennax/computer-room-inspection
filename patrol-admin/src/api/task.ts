import request from '@/utils/request'

export function getTaskList()   { return request.get('/task/list') }
export function getTask(id: number) { return request.get('/task/detail/' + id) }
export function createTask(data: any) { return request.post('/task', data) }
export function updateTask(data: any) { return request.put('/task', data) }
export function deleteTask(id: number) { return request.delete('/task/' + id) }
export function exportTask() { return request.get('/task/export', { responseType: 'blob' }) }
export function importTask(file: File) {
  const fd = new FormData(); fd.append('file', file);
  return request.post('/task/import', fd)
}
