import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
    routes: [
         
        {
            path: '/',
            redirect: '/usersearch'
        },
        {
            path: '/',
            component: () => import('../components/common/Home.vue'),
            meta: { title: 'User Search' },
            children: [
                 {
                    path: '/usersearch',
                    component: () => import('../pages/index'),
                    meta: { title: 'User Search' }
                }
            ]
        }
    ]
});
