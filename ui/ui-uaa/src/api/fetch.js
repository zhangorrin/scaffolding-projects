// 导入模块
import Vue from 'vue'
import axios from 'axios'
import { SERVER_BASE_URL } from './config'
import router from '@/router'
import iView, {Notice } from 'iview'
import Cookies from 'js-cookie'
// import store from '@/store'
Vue.use(iView)
// 设置用户信息action
/* const setUserInfo = function (user) {
 //store.dispatch(SET_USER_INFO, user)
 }; */
// axios.defaults.headers['post'] = 'Content-Type': 'application/json;charset=UTF-8'
// axios.defaults.baseURL = SERVER_BASE_URL
// axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8'

export default function fetch (options) {
  return new Promise((resolve, reject) => {
    const instance = axios.create({
      baseURL: SERVER_BASE_URL,
      timeout: 5000
    })
    // http request 拦截器
    instance.interceptors.request.use(
      config => {
        iView.LoadingBar.start()
        config.headers['Authorization'] = 'Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MDY2NjA1MTIsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiQVVUSF9JTkRFWCIsIkFVVEhfQURNSU4iXSwianRpIjoiNjY5OGE2MDUtNTQzZi00OWZhLTg1YWUtMmUzZDViZTVhZWU1IiwiY2xpZW50X2lkIjoiYWNtZSIsInNjb3BlIjpbIm9wZW5pZCJdfQ.QGEjpvc_KqM8JkWsDlmvOIHen5U2Mq7npN2GOjAJCxXKXaRyu9as0MMJM9xj7xxsHJI960hsHLxm9K5bqQLm_JtBzj9tvQAO5lmLTcprhEAdhyuY696gOrupuhsNpTBJJ4Mlbu-PsHwhjjvxsX0cviDw4bxm2nXfYP7FeO8GyZQMp2pJqLxiyEuAlwX7qgbNYguFAVhwkCBGI1SeO_iRC853_g5BhURaSwlCvbCtKUrF-KbmhphtnfOPDDc84AljguSssyR114V4gchhFwHfyN0m7ThrVQgXtkAFwulRRz9UffGhtkqpNUbNL1k7z25HyPr8C3Hq-rjsZz30qKgQRg'
        // config.headers.post['Content-Type'] = 'application/json;charset=UTF-8'
        return config
      },
      err => {
        iView.LoadingBar.error()
        return Promise.reject(err)
      })

    // http response 拦截器
    instance.interceptors.response.use(
      response => {
        iView.LoadingBar.finish()
        return response
      },
      error => {
        iView.LoadingBar.error()
        if (error) {
          switch (error.response.status) {
            case 401:
              // 返回 401 清除token信息并跳转到登录页面
              // store.commit(types.LOGOUT)
              Cookies.set('Authorization', null)
              router.replace({
                path: 'login',
                query: {redirect: router.currentRoute.fullPath}
              })
          }
        }
        return Promise.reject(error) // 返回接口返回的错误信息
      })

    // 请求处理
    instance(options)
      .then((res) => {
        // 请求成功时,根据业务判断状态
        /*  if (code === port_code.success) {
         resolve({code, msg, data})
         return false
         } else if (code === port_code.unlogin) {
         setUserInfo(null)
         router.replace({name: "login"})
         } */

        resolve(res.data)
        return false
      })
      .catch((error) => {
        // 请求失败时,根据业务判断状态
        Notice.error({
          title: '出错了！',
          desc: '错误原因 ' + JSON.stringify(error),
          duration: 10
        })
        reject(error)
      })
  })
}
