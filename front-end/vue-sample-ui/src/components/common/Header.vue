<template>
    <a-layout-header class="header">
        <div class="headerleft">
            <a-icon :type="collapsed ? 'menu-unfold' : 'menu-fold'" class="triggerIcon headTool" @click="collapseChage" />
        </div>
    </a-layout-header>
</template>
<script>
import bus from '../common/bus';
export default {
    data() {
        return {
            collapsed: false,
            fullscreen: false,
            name: 'linxin',
            message: 2
        };
    },
    computed: {
        username() {
            let username = localStorage.getItem('user_token');
            return username ? username : this.name;
        }
    },
    methods: {
        collapseChage() {
            this.collapsed = !this.collapsed;
            bus.$emit('collapsed', this.collapsed);
        },
        handleFullScreen() {
            let element = document.documentElement;
            if (this.fullscreen) {
                if (document.exitFullscreen) {
                    document.exitFullscreen();
                } else if (document.webkitCancelFullScreen) {
                    document.webkitCancelFullScreen();
                } else if (document.mozCancelFullScreen) {
                    document.mozCancelFullScreen();
                } else if (document.msExitFullscreen) {
                    document.msExitFullscreen();
                }
            } else {
                if (element.requestFullscreen) {
                    element.requestFullscreen();
                } else if (element.webkitRequestFullScreen) {
                    element.webkitRequestFullScreen();
                } else if (element.mozRequestFullScreen) {
                    element.mozRequestFullScreen();
                } else if (element.msRequestFullscreen) {
                    // IE11
                    element.msRequestFullscreen();
                }
            }
            this.fullscreen = !this.fullscreen;
        }
    },
    mounted() {
        if (document.body.clientWidth < 1500) {
            this.collapseChage();
        }
    }
};
</script>
<style scoped lang='less'>
.header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    position: relative;
    box-sizing: border-box;
    padding-left: 15px;
    width: 100%;
    height: 70px;
    color: #fff;

    .headerleft {
        display: flex;
        align-items: center;
        .logo {
            width: 150px;
            object-fit: cover;
            height: 50px;
        }
        .headTool {
            margin-left: 15px;
            font-size: 25px;
        }
    }
}
</style>
