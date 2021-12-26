<template>
    <a-layout-sider width="200" style="background: #fff" v-model="collapsed" theme="light" collapsed-width="0">
        <a-menu
            theme="light"
            mode="inline"
            :open-keys="openNavList"
            @openChange="onOpenNav"
            :inline-collapsed="collapsed"
            :default-selected-keys="['1']"
        >
            <template v-for="item in leftMenuData">
                <a-menu-item v-if="!item.children" :key="item.key">
                    <router-link :to="item.path">{{ item.name }}</router-link>
                </a-menu-item>
                <sub-menu v-else :key="item.key" :menu-info="item" />
            </template>
        </a-menu>
    </a-layout-sider>
</template>

<script>
import bus from '../common/bus';
export default {
    components: {},
    data() {
        return {
            collapsed: false,
            openNavList: [],
            leftMenuData: [
                {
                    name: 'User Search',
                    key: '1',
                    path: '/usersearch',
                    icon: 'deployment-unit'
                }
            ]
        };
    },
    created() {
        bus.$on('collapsed', (msg) => {
            this.collapsed = msg;
        });
    },
    computed: {},
    methods: {
        onOpenNav(e) {
            let endKey = e.pop();
            this.openNavList = endKey ? [endKey] : [];
        }
    }
};
</script>

<style scoped>
</style>
