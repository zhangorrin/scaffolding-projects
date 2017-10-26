import Vue from 'vue'
import axios from 'axios'
import { SERVER_BASE_URL } from './config'
import router from '@/router'
import iView, {Notice } from 'iview'
import Cookies from 'js-cookie'
Vue.use(iView)

axios.defaults.baseURL = SERVER_BASE_URL
axios.defaults.headers.common['Authorization'] = 'Bearer  eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MDc1Mzc5MTUsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiQVVUSF9JTkRFWCIsIkFVVEhfQURNSU4iXSwianRpIjoiY2Q5OWEyMjAtNzgxNi00ODZlLWFjYjktZWFjN2U1YzIzZjNhIiwiY2xpZW50X2lkIjoiYWNtZSIsInNjb3BlIjpbIm9wZW5pZCJdfQ.EipTOYReDSuYbbcd0_HqEn48d7ej-nyhYeyg_zYgEyAAIEwJE2rXjbz2Qlh5XgrU49K7Be-bhIBzjzGUfgZbrxfCUNTEVLWX-NbWmrMFzU93rSB3NM6Jgw6REo-ZqwuYpKUzLP7srRJbeU6FnZ0bBdK5DcQxeFGAr-u0ifn2eQwd9xD1WfriJT_DqRtgDL3NlYayAl9egchjbkGY9_qvwxSaDuzGvsLROZEV6ORI9G8-U-TVFyr2cNgCB3l1tsKSHgBWsY3DiAo7cHQeWM4MvIluk_dOKZ57BlhmNqoe807oRq2E0wqTHnqf51X3iohAiny5d99eH4vkmwdIS1j74A'
axios.defaults.headers.common['ajaxSource'] = 'ajaxSource'
// axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded'
axios.defaults.timeout = 2500
// http request 拦截器
axios.interceptors.request.use(function (config) {
  console.log('request拦截器config')
  console.log(config)
  if (config.method === 'post') {
    console.log('set Content-Type = application/json;charset=UTF-8')
    config.headers['Content-Type'] = 'application/json;charset=UTF-8'
  }
  iView.LoadingBar.start()
  return config
}, function (error) {
  console.log('request拦截器error')
  console.log(error)
  iView.LoadingBar.error()
  return Promise.reject(error)
})
// http response 拦截器
axios.interceptors.response.use(function (response) {
  iView.LoadingBar.finish()
  console.log('response拦截器')
  console.log(response.status)
  console.log(response)
  if (response.status === 200 && response.data.responseCode !== '00000') {
    Notice.error({
      title: '请求失败1！',
      desc: JSON.stringify(response.data.responseMsg),
      duration: 8
    })
  } else if (response.status === 403 && response.data.responseCode === '20000') {
    Notice.error({
      title: '请求失败2！',
      desc: JSON.stringify(response.data.responseMsg),
      duration: 8
    })
  }
  console.log(JSON.stringify(response.data).indexOf('loginpage'))
  if (JSON.stringify(response.data).indexOf('loginpage') > 0) {
    response.data = ''
    Cookies.set('Authorization', null)
    Notice.error({
      title: '请重新登录！&nbsp;&nbsp;&nbsp; <a href="/login" >点击登录</a>',
      duration: 10
    })
  }
  return response
}, function (error) {
  console.log('response拦截器error')
  console.log(error)
  iView.LoadingBar.error()
  if (error) {
    switch (error.response.status) {
      case 401:
        // 返回 401 清除token信息并跳转到登录页面
        // store.commit(types.LOGOUT)
        Cookies.set('Authorization', null)
        router.replace({
          path: '/login',
          query: {redirect: router.currentRoute.fullPath}
        })
    }
  }
  return Promise.reject(error)
})

export default function requestUtil (url, method, params) {
  return new Promise((resolve, reject) => {
    axios({
      method: method,
      url: url,
      data: params
    }).then((res) => {
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
    }).catch((error) => {
      // 请求失败时,根据业务判断状态
      console.log('catch error')
      console.log(JSON.stringify(error))
      if (error.response.status === 403 && error.response.data.responseCode === '20000') {
        Notice.error({
          title: '请求失败3！',
          desc: JSON.stringify(error.response.data.responseMsg),
          duration: 8
        })
      } else {
        Notice.error({
          title: '出错了！',
          desc: '错误原因<br>' + JSON.stringify(error.response),
          duration: 10
        })
      }
      reject(error)
    })
  })
}
