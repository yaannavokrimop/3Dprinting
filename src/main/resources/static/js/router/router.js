import Vue from 'vue'
import VueRouter from 'vue-router'
import Profile from 'pages/Profile.vue'
import Main from 'pages/Main.vue'


Vue.use(VueRouter)

const routes=[
    {path:'/',component:Main},
    {path:'/profile',component:Profile},
    // {path:'/profile/:id',component:'Profile'},
    // {path:'*',component:'Main'}
]

export default new VueRouter({
    mode:'history',
    routes
})