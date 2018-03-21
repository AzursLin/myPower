// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import Axios from 'axios'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-default/index.css'
import 'babel-polyfill'

Vue.use(ElementUI);


// http request 拦截器
Axios.interceptors.request.use(
  config => {
    if(config.method=='get'){
      config.params = {
        userId: JSON.parse(sessionStorage.getItem('userId')),
        ...config.params
      }
    }

    return config;
  },
  err => {
    return Promise.reject(err);
  });

Axios.interceptors.response.use(function (response) {

  if (response.data.ret === 200){
    sessionStorage.setItem('code', JSON.stringify(response.data.code));
  } else if(response.data.ret === 400) {      // token 已过期，重定向到登录页面
    sessionStorage.clear();
    router.replace({
      path: '/',
      query: {redirect: router.currentRoute.fullPath}
    })
  }
  return response
}, function (error) {
  // Do something with response error
  return Promise.reject(error)
});

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: { App }
})
