import {createRouter, createWebHistory} from 'vue-router'
import Login from '@/views/Login'
import Home from "@/views/Home";
import WelCome from "@/views/WelCome";
import SysRes from "@/views/sys/SysRes";
import SysRole from "@/views/sys/SysRole";
import SysDept from "@/views/sys/SysDept";
import SysUser from "@/views/sys/SysUser";
import LoginLog from "@/views/log/LoginLog";
import Druid from "@/views/monitor/Druid";
import OperateLog from "@/views/log/OperateLog";
import NotFound from "@/views/error/NotFound";
import Task from "@/views/monitor/Task";

const routes = [
    {
        path: '/login',
        name: '登录',
        component: Login
    },
    {
        path: '/',
        name: '首页',
        redirect: '/index',
        component: Home,
        children: [
            {
                path: '/index',
                name: '首页',
                component: WelCome
            },
            {
                path: '/sys/res',
                name: '资源管理',
                component: SysRes
            },
            {
                path: '/sys/role',
                name: '角色管理',
                component: SysRole
            },
            {
                path: '/sys/dept',
                name: '部门管理',
                component: SysDept
            },
            {
                path: '/sys/user',
                name: '用户管理',
                component: SysUser
            },
            {
                path: '/log/login',
                name: '登录日志',
                component: LoginLog
            },
            {
                path: '/log/operate',
                name: '操作日志',
                component: OperateLog
            },
            {
                path: '/monitor/druid',
                name: 'Druid监控',
                component: Druid
            },
            {
                path: '/monitor/task',
                name: '定时任务',
                component: Task
            },
        ]
    },
    {
        path: '/:catchAll(.*)',
        name: '404',
        component: NotFound
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router
