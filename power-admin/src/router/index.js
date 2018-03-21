import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import Navigation from '@/components/Navigation'
import UserManage from '@/components/UserManage'
import DormManage from '@/components/DormManage'
import UserElectricity from '@/components/UserElectricity'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: '',
      component: Login,
      hidden: true
    },
    {
      path:'/index',
      name:'俱乐部管理',
      component:Navigation,
      children:[
        {
          path:'/UserManage',
          name:'账户管理',
          component:UserManage,
        },
        {
          path:'/DormManage',
          name:'宿舍管理',
          component:DormManage,
        },
        {
          path:'/UserElectricity',
          name:'用电管理',
          component:UserElectricity,
        }
      ]
    }
  ]
})
