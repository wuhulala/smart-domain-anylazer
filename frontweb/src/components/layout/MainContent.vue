<template>
    <a-layout id="components-layout-demo-top" class="layout">
        <a-layout-header>
            <div class="logo">SDA</div>
            <a-menu
                    theme="dark"
                    mode="horizontal"
                    :defaultSelectedKeys="['1']"
                    :style="{ lineHeight: '64px' }"
            >
                <a-menu-item key="1">
                    <router-link to="/">主页</router-link>
                </a-menu-item>
                <a-menu-item key="2">
                    <router-link to="/domain-list">领域分析</router-link>
                </a-menu-item>
                <a-menu-item key="3">
                    <router-link to="/me">个人信息</router-link>
                </a-menu-item>
            </a-menu>
        </a-layout-header>
        <a-layout-content style="padding: 0 50px">
            <a-breadcrumb :routes="routes" style="margin: 16px 0">
                <template slot="itemRender" slot-scope="{route, params, routes, paths}">
                    <span v-if="routes.indexOf(route) === routes.length - 1">
                        {{route.breadcrumbName}}
                    </span>
                    <router-link v-else :to="paths.join('/')">
                        {{route.breadcrumbName}}
                    </router-link>
                </template>
            </a-breadcrumb>
            <div :style="{ background: '#fff', padding: '24px', minHeight: 'calc( 680px )' }">
                <router-view></router-view>
            </div>
        </a-layout-content>
        <a-layout-footer style="text-align: center">
            SmartDomainAnalyzer Created by Wuhulala
        </a-layout-footer>
    </a-layout>
</template>
<style>
    #components-layout-demo-top .logo {
        width: 120px;
        height: 31px;
        background: rgba(243, 165, 89, 0.2);
        margin: 16px 24px 16px 0;
        float: left;
    }
</style>

<script>

  import router from '../../router'

  export default {
    name: "MainContent",
    data() {
      return {
        routes: []
      }
    },
    mounted() {

      this.routes = [router.options.routes];

    }
  }
</script>

<style scoped>

</style>