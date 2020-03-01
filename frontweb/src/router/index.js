import Vue from 'vue'
import VueRouter from 'vue-router'

import MainContent from '../components/layout/MainContent.vue'

import Home from '../components/views/Home'
import DomainList from '../components/views/DomainList'
import Me from '../components/views/Me'

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
        name: 'DomainList',
        path: '/domain-list',
        breadcrumbName: '领域分析',
        component: DomainList
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