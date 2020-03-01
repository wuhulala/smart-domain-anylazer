import Vue from 'vue'
import App from './App.vue'
import "ant-design-vue/dist/antd.css"
import ant from 'ant-design-vue'
import VueRouter from 'vue-router'
import router from './router'
import echarts from 'echarts'
import wordCloud from 'echarts-wordcloud'
Vue.prototype.$echarts = echarts


Vue.use(ant)
Vue.use(VueRouter)
Vue.use(wordCloud)

Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
