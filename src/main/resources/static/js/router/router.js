import Vue from 'vue'
import VueRouter from 'vue-router'
import Profile from 'pages/Profile.vue'
import Main from 'pages/Main.vue'
import SignUp from 'pages/SignUp.vue'
import SignIn from 'pages/SignIn.vue'
import ProfileEdit from 'pages/ProfileEdit.vue'
import Orders from 'pages/Orders.vue'
import Order from 'pages/Order.vue'
import Equipment from 'pages/Equipment.vue'
import AddEquipment from 'pages/AddEquipment.vue'
import Equipments from 'pages/Equipments.vue'
import CreateOrder from "pages/CreateOrder.vue";
import OrderEdit from "pages/OrderEdit.vue";
import Addresses from 'pages/Addresses.vue'


Vue.use(VueRouter);
let router = new VueRouter({
    routes: [
        {path: '/', component: Main},
        {path: '/signin', component: SignIn},
        {path: '/signup', component: SignUp},
        {path: '/profile', component: Profile},
        {path: '/profile_edit', component: ProfileEdit},
        {path: '/orders', component: Orders},
        {path: '/orders/:id', component: Order},
        {path: '/equipment/:id', component: Equipment},
        {path: '/equipment', component: Equipments},
        {path: '/add_equipment', component: AddEquipment, meta: {isExecutor: true}},
        {path: '/address', component: Addresses},
        {path:'/order/create', component: CreateOrder},
        {path:'/order/edit', component: OrderEdit}
        // {path:'/profile/:id',component:'Profile'},
        // {path:'*',component:'Main'}
    ]
})
router.beforeEach((to, from, next) => {
    if (to.matched.some(record => record.meta.isExecutor)) {
        if (localStorage.getItem('authority') === 'EXECUTOR') {
            next()
            return
        }
        next('/')
    } else {
        next()
    }
})
export default router