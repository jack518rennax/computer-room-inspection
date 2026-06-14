import request from '@/utils/request'

export function getRectifyList()   { return request.get('/rectify/list') }
export function getRectify(id: number) { return request.get('/rectify/detail/' + id) }
export function createRectify(data: any) { return request.post('/rectify', data) }
export function updateRectify(data: any) { return request.put('/rectify', data) }
export function deleteRectify(id: number) { return request.delete('/rectify/' + id) }
