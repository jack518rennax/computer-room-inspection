import request from '@/utils/request'

export function getAiList()   { return request.get('/ai/list') }
export function getAi(id: number) { return request.get('/ai/detail/' + id) }
export function createAi(data: any) { return request.post('/ai', data) }
export function updateAi(data: any) { return request.put('/ai', data) }
export function deleteAi(id: number) { return request.delete('/ai/' + id) }
