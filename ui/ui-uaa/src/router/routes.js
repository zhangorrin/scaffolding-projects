import Home from '@/components/layout/Home'
import Index from '@/components/views/Index'

const Chart = () => import('@/components/views/Chart')
const NotFound = () => import('@/components/pages/NotFound')
const Register = () => import('@/components/pages/Register')
const E404 = () => import('@/components/pages/E404')
const Icon = () => import('@/components/pages/Icon')
const Login = () => import('@/components/pages/Login')
const Table = () => import('@/components/views/Table')
const UserList = () => import('@/components/views/UserList')
const UserInfo = () => import('@/components/views/UserInfo')
const List = () => import('@/components/views/List')
const Filter = () => import('@/components/pages/Filter')
const users = () => import('@/components/views/authmgr/userlist')
const resources = () => import('@/components/views/authmgr/resourcelist')
const authorities = () => import('@/components/views/authmgr/authoritieslist')
const roles = () => import('@/components/views/authmgr/roleslist')

const routes = [
  {
    path: '/',
    name: 'home',
    component: Home,
    redirect: '/index',
    children: [  // 所有只有一级的导航放到这里，并指定level=1
      {
        path: '/index',
        name: 'index',
        component: Index,
        icon: 'icon-wujiaoxing',
        level: 1,
        meta: {
          requiresAuth: true, // 是否需要登录
          search: false // 是否可搜索
        }
      },
      {
        path: '/chart',
        name: 'chart',
        component: Chart
      },
      {
        path: '/list',
        name: 'list',
        component: List,
        meta: {
          requiresAuth: true, // 是否需要登录
          search: true // 是否可搜索
        }
      },
      {
        path: '/userInfo',
        name: 'userInfo',
        component: UserInfo,
        icon: 'icon-addpeople_fill'
      },
      {
        path: '/userList',
        name: 'userList',
        component: UserList
      },
      {
        path: '/table',
        name: 'table',
        component: Table
      },
      {
        path: '/icon',
        name: 'icon',
        component: Icon
      },
      {
        path: '/filter',
        name: 'filter',
        component: Filter
      },
      {
        path: '/notFound',
        name: 'notFound',
        component: NotFound
      },
      {
        path: '/authmgr/userlist',
        name: '用户管理',
        component: users
      },
      {
        path: '/authmgr/resourcelist',
        name: '资源管理',
        component: resources
      },
      {
        path: '/authmgr/authoritieslist',
        name: '权限管理',
        component: authorities
      },
      {
        path: '/authmgr/roleslist',
        name: '角色管理',
        component: roles
      }
    ]
  },
  {
    path: '/login',
    name: 'login',
    component: Login
  },
  {
    path: '/register',
    name: 'register',
    component: Register
  },
  {
    path: '/error404',
    name: 'error404',
    component: E404
  },
  {
    path: '*',
    redirect: '/notFound'
  }

]

export default routes
