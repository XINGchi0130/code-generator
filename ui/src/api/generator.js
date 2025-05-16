import request from '@/utils/api'

export function getFile(query) {
    return request({
      url: '/getFile',
      method: 'get',
      params: query
    })
}