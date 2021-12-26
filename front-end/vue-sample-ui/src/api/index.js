import request from '../utils/request'
export function usersearch(query, page, size) {
    return request({
        url: '/vuesample/openapi/v1/users/query?page=' + page + '&size=' + size,
        method: 'post',
        data: query
    });
}