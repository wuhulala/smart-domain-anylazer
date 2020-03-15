import Vue from 'vue'
import VueRouter from 'vue-router'

import MainContent from '../components/layout/MainContent.vue'

import Home from '../components/views/Home'
import Domain from '../components/views/Domain'
import DomainOverview from '../components/views/domain/DomainOverview'
import Me from '../components/views/Me'
import DomainManage from "../components/views/domain/DomainManage";
import DomainDetail from "../components/views/domain/detail/DomainDetail";

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