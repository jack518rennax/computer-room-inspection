import request from '@/utils/request'

export function getLocationList()   { return request.get('/location/list') }
export function getLocation(id: number) { return request.get('/location/detail/' + id) }
export function createLocation(data: any) { return request.post('/location', data) }
export function updateLocation(data: any) { return request.put('/location', data) }
export function deleteLocation(id: number) { return request.delete('/location/' + id) }
export function exportLocation() { return request.get('/location/export', { responseType: 'blob' }) }
export function importLocation(file: File) {
  const fd = new FormData(); fd.append('file', file);
  return request.post('/location/import', fd)
}
