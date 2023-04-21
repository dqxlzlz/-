import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import VueRouter from "vue-router";
import router from "@/router";
import axios  from "axios";
import x2js from 'x2js' //xml数据处理插件

Vue.prototype.$x2js = new x2js() //全局方法挂载
Vue.config.productionTip = false

Vue.use(ElementUI);
Vue.use(VueRouter);
Vue.prototype.axios = axios
Vue.use(axios);

new Vue({
  render: h => h(App),
  router
}).$mount('#app')
