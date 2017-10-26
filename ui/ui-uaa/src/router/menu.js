const menu = [
  {
    path: '/index',
    name: '主页',
    icon: 'icon iconfont icon-homepage',
    level: 1,
    sort: 1,
    children: [],
    fixed: false
  },
  {
    path: '/',
    name: '云权限管理',
    icon: 'icon-service',
    level: 0,
    fixed: false,
    children: [
      {
        path: '/authmgr/userlist',
        name: '用户管理',
        icon: 'icon-group_fill',
        level: 0,
        children: [],
        fixed: false
      },
      {
        path: '/authmgr/resourcelist',
        name: '资源管理',
        icon: 'icon-share_fill',
        level: 0,
        children: [],
        fixed: false
      },
      {
        path: '/authmgr/authoritieslist',
        name: '权限管理',
        icon: 'icon-falvsolid',
        level: 0,
        children: [],
        fixed: false
      },
      {
        path: '/authmgr/roleslist',
        name: '角色管理',
        icon: 'icon-jingcha',
        level: 0,
        children: [],
        fixed: false
      }
    ]
  },
  {
    path: '/',
    name: '列表页',
    icon: 'icon-createtask_fill',
    level: 0,
    fixed: false,
    children: [
      {
        path: '/chart',
        name: 'chart',
        icon: 'icon-dynamic_fill',
        level: 1,
        children: [],
        fixed: false
      },
      {
        path: '/list',
        name: 'list',
        level: 1,
        children: [],
        fixed: false
      },
      {
        path: '/userInfo',
        name: 'userInfo',
        icon: 'icon-addpeople_fill',
        children: [],
        fixed: false,
        level: 1
      },
      {
        level: 1,
        path: '/userList',
        children: [],
        fixed: false,
        name: 'userList'
      },
      {
        level: 1,
        path: '/table',
        name: 'table',
        children: [],
        fixed: false,
        hidden: true
      },
      {
        level: 1,
        path: '/icon',
        name: 'icon',
        children: [],
        fixed: false,
        icon: 'icon-emoji_fill'
      },
      {
        level: 1,
        path: '/filter',
        name: 'filter',
        children: [],
        fixed: false,
        icon: 'icon-emoji_fill'
      }
    ]
  },
  {
    path: '/',
    name: '页面',
    level: 0,
    icon: 'icon-manage_fill',
    fixed: false,
    children: [
      {
        level: 1,
        path: '/error404',
        children: [],
        fixed: false,
        name: '404'
      },
      {
        level: 1,
        path: '/notfound',
        children: [],
        fixed: false,
        name: 'Not found'
      },
      {
        level: 1,
        path: '/login',
        children: [],
        fixed: false,
        name: '登录'
      },
      {
        level: 1,
        path: '/register',
        children: [],
        fixed: false,
        name: '注册'
      }
    ]
  },
  {
    path: '/',
    name: '管理',
    level: 0,
    icon: 'icon-manage_fill',
    fixed: false,
    children: [
      {
        level: 1,
        path: '/setMenu',
        children: [],
        fixed: false,
        name: 'setMenu'
      },
      {
        level: 1,
        path: '/setMenu2',
        children: [],
        fixed: false,
        name: 'setMenu2'
      },
      {
        level: 1,
        path: '/notFound',
        children: [],
        fixed: false,
        name: 'notFound'
      }
    ]
  },
  {
    path: '/index',
    name: '123',
    icon: 'icon-wujiaoxing',
    // hidden: true,
    level: 0,
    sort: 1,
    children: [],
    fixed: false
  },
  {
    path: '/',
    name: '管理',
    level: 0,
    icon: 'icon-manage_fill',
    fixed: false,
    children: [
      {
        level: 1,
        path: '/setMenu',
        children: [],
        fixed: false,
        name: 'setMenu'
      },
      {
        level: 1,
        path: '/setMenu2',
        children: [],
        fixed: false,
        name: 'setMenu2'
      },
      {
        level: 1,
        path: '/notFound',
        children: [],
        fixed: false,
        name: 'notFound'
      }
    ]
  },
  {
    path: '/',
    name: '管理',
    level: 0,
    icon: 'icon-manage_fill',
    fixed: false,
    children: [
      {
        level: 1,
        path: '/setMenu',
        children: [],
        fixed: false,
        name: 'setMenu'
      },
      {
        level: 1,
        path: '/setMenu2',
        children: [],
        fixed: false,
        name: 'setMenu2'
      },
      {
        level: 1,
        path: '/notFound',
        children: [],
        fixed: false,
        name: 'notFound'
      }
    ]
  },
  {
    path: '/',
    name: '管理',
    level: 0,
    icon: 'icon-manage_fill',
    fixed: false,
    children: [
      {
        level: 1,
        path: '/setMenu',
        children: [],
        fixed: false,
        name: 'setMenu'
      },
      {
        level: 1,
        path: '/setMenu2',
        children: [],
        fixed: false,
        name: 'setMenu2'
      },
      {
        level: 1,
        path: '/notFound',
        children: [],
        fixed: false,
        name: 'notFound'
      }
    ]
  }

]

export default menu
