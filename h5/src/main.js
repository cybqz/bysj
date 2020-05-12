// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import iView from 'iview' // 引入iview依赖
import 'iview/dist/styles/iview.css' // 引入iview css样式
import axios from 'axios' // 1、在这里引入axios
import qs from 'qs' // 解决参数无法传递

// 引入echarts
import echarts from 'echarts'
Vue.prototype.$echarts = echarts

axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8'
// axios.defaults.baseURL = 'http://114.115.134.1:9090/';
axios.defaults.baseURL = 'http://127.0.0.1:9099/'
axios.defaults.withCredentials = true // 允许携带token ,这个是解决跨域产生的相关问题
axios.defaults.timeout = 6000

Vue.use(iView);
Vue.prototype.qs = qs;
Vue.prototype.$axios = axios;

Vue.config.productionTip = false

// request 拦截器实现
axios.interceptors.request.use(
  config => {
    let token = sessionStorage.getItem('token')
    if (token) {
      config.headers.authToken = token;
    }
    if (config.url === 'refresh') {
      config.headers['refresh-token'] = sessionStorage.getItem('refresh_token');
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// 在 response 拦截器实现
axios.interceptors.response.use(
  response => {
    // 定时刷新access-token
    if (!response.data.value && response.data.msg === 'token invalid') {
    // 刷新token
    store.dispatch('refresh').then(response => {
      sessionStorage.setItem('access_token', response.data)
    }).catch(error => {
      throw new Error('token刷新' + error)
    })
    }

    // 消息提示
    if(response.data.validate){
      // Vue.prototype.$Message.success(response.data.msg);
    }else{
      // Vue.prototype.$Message.error(response.data.msg);
    }
    return response
  },
  error => {
    return Promise.reject(error)
  }
)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
