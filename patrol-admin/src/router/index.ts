import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'

const routes: RouteRecordRaw[] = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/login/index.vue'),
    meta: { title: '登录' },
  },
  {
    path: '/',
    component: () => import('@/layout/index.vue'),
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('@/views/dashboard/index.vue'),
        meta: { title: '首页', icon: 'HomeFilled' },
      },
      {
        path: 'system/user',
        name: 'SystemUser',
        component: () => import('@/views/system/user.vue'),
        meta: { title: '用户管理', icon: 'User' },
      },
      {
        path: 'system/role',
        name: 'SystemRole',
        component: () => import('@/views/system/role.vue'),
        meta: { title: '角色管理', icon: 'Avatar' },
      },
      {
        path: 'system/menu',
        name: 'SystemMenu',
        component: () => import('@/views/system/menu.vue'),
        meta: { title: '菜单管理', icon: 'Menu' },
      },
      {
        path: 'task',
        name: 'Task',
        component: () => import('@/views/task/index.vue'),
        meta: { title: '巡检任务', icon: 'Clock' },
      },
      {
        path: 'location',
        name: 'Location',
        component: () => import('@/views/location/index.vue'),
        meta: { title: '巡检点位', icon: 'Location' },
      },
      {
        path: 'rectify',
        name: 'Rectify',
        component: () => import('@/views/rectify/index.vue'),
        meta: { title: '整改记录', icon: 'Warning' },
      },
      {
        path: 'ai',
        name: 'Ai',
        component: () => import('@/views/ai/index.vue'),
        meta: { title: 'AI 记录', icon: 'Cpu' },
      },
    ],
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

/** 路由守卫：未登录跳转登录页 */
router.beforeEach((to, _from, next) => {
  const token = localStorage.getItem('token')
  if (to.path !== '/login' && !token) {
    next('/login')
  } else if (to.path === '/login' && token) {
    next('/dashboard')
  } else {
    next()
  }
})

export default router
