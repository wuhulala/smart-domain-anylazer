import Vue from 'vue'
import VueRouter from 'vue-router'

import MainContent from '../components/layout/MainContent.vue'

import Home from '../views/Home'
import Domain from '../views/Domain'
import DomainOverview from '../views/domain/DomainOverview'
import Me from '../views/Me'
import DomainManage from "../views/domain/manage/DomainManage";
import DomainDetail from "../views/domain/detail/DomainDetail";
import DomainDetailManage from "../views/domain/detail/DomainDetailManage";

Vue.use(VueRouter);

const routes = [
  {
    name: 'Index',
    path: '/',
    component: MainContent,
    children: [
      {
        name: 'Home',
        path: '/',
        breadcrumbName: '主页',
        component: Home
      },
      {
        name: 'Domain',
        path: '/domain',
        breadcrumbName: '领域管理',
        component: Domain,
        children: [
          {
            name: 'DomainOverview',
            path: '',
            breadcrumbName: '领域总览',
            component: DomainOverview
          },
          {
            name: 'DomainManage',
            path: 'manage',
            breadcrumbName: '领域管理',
            component: DomainManage
          },
          {
            name: 'DomainDetailManage',
            path: 'managedetail/:id',
            breadcrumbName: '领域详情',
            component: DomainDetailManage
          },
          {
            name: 'DomainDetail',
            path: 'detail/:id',
            breadcrumbName: '领域详情',
            component: DomainDetail
          }
        ]
      },
      {
        name: 'Me',
        path: '/me',
        breadcrumbName: '个人主页',
        component: Me
      }]
  }
];

const router = new VueRouter({
  routes
});

export default router