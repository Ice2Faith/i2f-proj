import Config from '@/framework/config'
import Error404 from '@/framework/error/Error404'
import Login from '@/views/Login'
import HomeLayout from '@/components/HomeLayout'
import HomeView from '@/views/nav/HomeView'
import MenuView from '@/views/nav/MenuView'
import MsgView from '@/views/nav/MsgView'
import MineView from '@/views/nav/MineView'
import Hello from '@/views/Hello'
import PageLayout from "@/components/PageLayout";

const Routes = [
  {
    path: '/',
    redirect: Config.LOGIN_ROUTE
  },
  {
    path: Config.LOGIN_ROUTE,
    component: Login,
    meta: {
      // 明确指定，不需要访问权限
      permission: false
    }
  },
  {
    path: '/',
    component: HomeLayout,
    children: [
      {
        path: '/home',
        name: 'HomeView',
        component: HomeView,
        meta: {
          // 明确指定，不需要访问权限
          permission: false
        }
      },
      {
        path: '/menu',
        name: 'MenuView',
        component: MenuView,
        meta: {
          // 明确指定，不需要访问权限
          permission: false
        }
      },
      {
        path: '/msg',
        name: 'MsgView',
        component: MsgView,
        meta: {
          // 明确指定，不需要访问权限
          permission: false
        }
      },
      {
        path: '/mine',
        name: 'MineView',
        component: MineView,
        meta: {
          // 明确指定，不需要访问权限
          permission: false
        }
      },

    ]
  },

  {
    path: '/',
    component: PageLayout,
    children: [
      {
        path: '/sys/role',
        name: 'sysRole',
        component: () => import('@/views/sys/role/SysRole.vue')
      },
      {
        path: '/sys/resources',
        name: 'sysResources',
        component: () => import('@/views/sys/resources/SysResources.vue')
      },
      {
        path: '/sys/dept',
        name: 'sysDept',
        component: () => import('@/views/sys/dept/SysDept.vue')
      },
      {
        path: '/sys/config',
        name: 'sysConfig',
        component: () => import('@/views/sys/config/SysConfig.vue')
      },
      {
        path: '/sys/user',
        name: 'sysUser',
        component: () => import('@/views/sys/user/SysUser.vue')
      },
      {
        path: '/sys/perf',
        name: 'sysPerf',
        component: () => import('@/views/sys/perf/SysPerf.vue')
      },


      {
        path: '/about',
        name: 'about',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '@/views/AboutView.vue')
      },
      {
        path: '/hello',
        component: Hello
      },

      {
        path: '/pri/noteBook',
        name: 'noteBook',
        component: () => import('@/views/pri/noteBook/NoteBook.vue')
      },
      {
        path: '/pri/passwordBook',
        name: 'passwordBook',
        component: () => import('@/views/pri/passwordBook/PasswordBook.vue')
      },
      {
        path: '/pri/taskList',
        name: 'taskList',
        component: () => import('@/views/pri/taskList/TaskList.vue')
      },

      // 定义错误页
      {
        path: '/404',
        component: Error404,
        meta: {
          // 明确指定，不需要访问权限
          // 未指定的活明确指定为true的，则都需要对应的权限
          permission: false
        }
      }
    ]
  },

  // 最后捕获所有路径，走到这里，表示没有任何路由匹配，也就是404了
  {
    path: '/:pathMatch(.*)*',
    redirect: '/404'
  }
]

export default Routes
