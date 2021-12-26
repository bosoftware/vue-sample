import axios from 'axios';
import Qs from 'qs';
const request = axios.create({
    baseURL: 'http://localhost:8000/',
    headers: {'Content-Type': 'application/json;charset=UTF-8'},
    timeout: 5000,
});

request.interceptors.response.use(
    response => {
        if (response.status === 200) {
            return response.data;
        } else {
            Promise.reject();
        }
    },
    error => {
        console.log(error);
        return Promise.reject();
    }
);

export default request;
