import Vue from 'vue';
import App from './App.vue';
import router from './router';
import './assets/css/icon.css';
import 'babel-polyfill';
import Antdvue from 'ant-design-vue/lib'
import 'ant-design-vue/dist/antd.less'; // or 'ant-design-vue/dist/antd.css'
import './components/common/common.css'

Vue.use(Antdvue);

router.beforeEach((to, from, next) => {
    document.title = `${to.meta.title} | vue-sample`;
    next();
});

new Vue({
    router,
    render: h => h(App)
}).$mount('#app');
