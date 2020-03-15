<template>
    <a-layout style="padding: 24px 0; background: #fff">
        <a-layout-sider width="200" style="background: #fff">
            <a-menu
                    mode="inline"
                    :defaultSelectedKeys="['0']"
                    :defaultOpenKeys="['sub1']"
                    style="height: 100%"
            >
                <a-sub-menu key="sub1">
                    <span slot="title">
                        <a-icon type="user"/>领域元数据</span>
                    <a-menu-item key="0">
                        <router-link to="/domain">领域概览</router-link>
                    </a-menu-item>
                    <a-menu-item key="1">
                        <router-link to="/domain/manage">领域管理</router-link>
                    </a-menu-item>
                    <a-menu-item key="2">领域资料管理</a-menu-item>
                    <a-menu-item key="3">领域分组管理</a-menu-item>
                </a-sub-menu>
                <a-sub-menu key="sub2">
                    <span slot="title"><a-icon type="laptop"/>基础管理</span>
                    <a-menu-item key="5">读取任务管理</a-menu-item>
                    <a-menu-item key="6">抽取任务管理</a-menu-item>
                    <a-menu-item key="7">分析任务管理</a-menu-item>
                    <a-menu-item key="8">任务监控</a-menu-item>
                </a-sub-menu>
            </a-menu>
        </a-layout-sider>
        <a-layout-content :style="{ padding: '0 24px', minHeight: '280px' }">
            <router-view></router-view>
        </a-layout-content>
    </a-layout>
</template>

<script>
    export default {
        name: "Domain"
    }
</script>

<style scoped>

</style>