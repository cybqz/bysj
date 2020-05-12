import Vue from 'vue'
import Router from 'vue-router'
Vue.use(Router)

const router =  new Router({
  //mode:"history",
  base: '/cleg',
  routes: [
    {//材料管理模块
      path: '/',
      name: 'material',
      component: (resove) => require(['@/components/material/index'], resove),
      meta: {
        requireAuth: true,  // 添加该字段，表示进入这个路由是需要登录才能进入的
      },
    },
     {//材料管理模块
      path: '/material',
      name: 'material',
      component: (resove) => require(['@/components/material/index'], resove),
      meta: {
        requireAuth: true,  // 添加该字段，表示进入这个路由是需要登录才能进入的
      },
    },
    {//供应商管理模块
      path: '/supplier',
      name: 'supplier',
      component: (resove) => require(['@/components/supplier/index'], resove),
      meta: {
        requireAuth: true,  // 添加该字段，表示进入这个路由是需要登录才能进入的
      },
    },
    {//询价管理模块
      path:'/enquiry',
      name: 'enquiry',
      component: (resove) => require(['@/components/enquiry/index'], resove),
      meta: {
        requireAuth: true,  // 添加该字段，表示进入这个路由是需要登录才能进入的
      },
    },
    {//询价管理模块
      path:'/enquiryHistory',
      name: 'enquiryHistory',
      component: (resove) => require(['@/components/enquiry/history'], resove),
      meta: {
        requireAuth: true,  // 添加该字段，表示进入这个路由是需要登录才能进入的
      },
    },
    {//报价模块
      path: '/quote',
      name: 'quote',
      component: (resove) => require(['@/components/quote/index'], resove),
      meta: {
        requireAuth: true,  // 添加该字段，表示进入这个路由是需要登录才能进入的
      },
    },
    {//报价模块
      path: '/quoteDetail',
      name: 'quoteDetail',
      component: (resove) => require(['@/components/quote/detail'], resove),
      meta: {
        requireAuth: true,  // 添加该字段，表示进入这个路由是需要登录才能进入的
      },
    },
    {
      path: '/password',
      name: 'password',
      component: (resove) => require(['@/components/password'], resove),
      meta: {
        requireAuth: true,  // 添加该字段，表示进入这个路由是需要登录才能进入的
      },
    },
    {
      path: '/login',
      name: 'login',
      component: (resove) => require(['@/components/login'], resove),
    },
    {
      path: '/register',
      name: 'register',
      component: (resove) => require(['@/components/register'], resove),
    },
  ]
})


//登录鉴权
router.beforeEach((to, from, next) => {
  let token = sessionStorage.getItem("token");
  if (to.meta.requireAuth) {  // 需要权限,进一步进行判断
    if (token) {  // 通过sessionStorage获取当前的token是否存在
      next();
    }
    else {    //如果没有权限,重定向到登录页,进行登录
      next({
        path: '/login',
        // query: {redirect: to.fullPath}  // 将跳转的路由path作为参数，登录成功后跳转到该路由
      })
    }
  }
  else { //不需要权限 直接跳转
    next();
  }
})


export default router