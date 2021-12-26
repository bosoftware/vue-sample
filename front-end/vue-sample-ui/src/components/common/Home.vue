<template>
    <a-layout class="pageAll">
        <v-head></v-head>
        <a-layout>
            <v-sidebar></v-sidebar>
            <a-layout style="padding: 0 24px 24px">
                <v-tags></v-tags>
                <a-layout-content :style="{ background: '#fff', padding: '24px', minHeight: '280px' }">
                    <transition name="move" mode="out-in">
                        <keep-alive :include="tagsList">
                            <router-view></router-view>
                        </keep-alive>
                    </transition>
                </a-layout-content>
            </a-layout>
        </a-layout>
    </a-layout>
</template>
<script>
import vHead from './Header.vue';
import vSidebar from './Sidebar.vue';
import vTags from './Tags.vue';
import bus from './bus';
export default {
    data() {
        return {
            tagsList: [],
            collapsed: false
        };
    },
    components: {
        vHead,
        vSidebar,
        vTags
    }
};
</script>

<style lang="less" scoped>
.pageAll {
    width: 100vw;
    height: 100vh;
    .ant-layout-has-sider {
        height: 100%;
    }
    .Navlogo {
        height: 32px;
        opacity: 0.7;
        margin: 16px;
        .anticon {
            margin-right: 5px;
        }
    }
    .pageHead {
        padding: 0;
        display: flex;
        align-items: center;
        .headTool {
            margin: 0 10px;
        }
        .triggerIcon {
            font-size: 18px;
            line-height: 100%;
            cursor: pointer;
            color: #ffffff;
            transition: color 0.3s;
        }
    }
    .pageCont {
        overflow: hidden;
        padding: 10px;
        height: 100%;
        .pgTab/deep/ {
            .ant-tabs-bar {
                margin: 0;
            }
            /deep/.ant-tabs-card-bar,
            .ant-tabs-tab {
                height: 26px;
                line-height: 26px;
                font-size: 12px;
            }
        }
        .moduleAll {
            width: 100%;
            background-color: #ffffff;
            height: calc(100vh - 64px - 20px);
            overflow-y: auto;
            .hasTab {
                height: calc(100vh - 64px - 20px - 26px);
            }
        }
    }
}
</style>
