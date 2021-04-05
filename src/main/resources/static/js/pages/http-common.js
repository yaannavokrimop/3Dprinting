import axios from 'axios'
import Vue from 'vue'
import Notification from 'vue-notification';

Vue.use(Notification);
export const AXIOS = axios.create({
    baseURL: `/api`
    /*baseURL: `http://localhost:8080/api`,
    headers: {
        'Access-Control-Allow-Origin': 'http://localhost:8080',
        'Access-Control-Allow-Methods': 'POST,GET',
        'Access-Control-Allow-Headers': '*'
      }*/
});
AXIOS.interceptors.request.use(
    config => {
        const accessToken = localStorage.getItem('accessToken');
        if (accessToken) {
            config.headers['Authorization'] = 'Bearer ' + accessToken;
        }
        return config;
    },
    error => {
        Promise.reject(error).then(r => console.log("Error in recieving token info"))
    });
AXIOS.interceptors.response.use(
    function (response) {
        return response;
    },
    function (error) {
        Vue.notify({
            group: 'error',
            title: 'Error',
            classes: 'error-notification',
            text: error.response.data.message
        });
        return Promise.reject(error);
    });