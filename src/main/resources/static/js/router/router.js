import Vue from 'vue'
import VueRouter from 'vue-router'
import Profile from 'pages/Profile.vue'
import Main from 'pages/Main.vue'
import SignUp from 'pages/SignUp.vue'
import SignIn from 'pages/SignIn.vue'


Vue.use(VueRouter);

const routes=[
    {path:'/',component:Main},
    {path:'/profile',component:Profile},
    {path:'/register',component:SignUp},
    {path:'/singin',component:SignIn},
    // {path:'/profile/:id',component:'Profile'},
    // {path:'*',component:'Main'}
];

export default new VueRouter({
    mode:'history',
    routes
})