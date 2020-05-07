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
import Addresses from "pages/Addresses.vue";
import SelectEquipment from 'pages/SelectEquipment.vue'
import Chat from 'pages/Chat/Chat.vue'
import ChatList from 'pages/Chat/ChatList.vue'
import CurrentProfile from 'pages/CurrentProfile.vue'
import CustomerResponses from "../pages/CustomerResponses.vue"
import ExecutorResponses from "../pages/ExecutorResponses.vue"


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
        {path: '/equipment/:id', component: Equipment, meta: {isExecutor: true}},
        {path: '/equipment', component: Equipments, meta: {isExecutor: true}},
        {path: '/create_equipment', component: AddEquipment, meta: {isExecutor: true}},
        {path: '/order/create', component: CreateOrder},
        {path: '/order/edit/:id', component: OrderEdit},
        {path: '/address', component: Addresses},
        {path: '/add_equipment', component: SelectEquipment, meta: {isExecutor: true}},
        {path: '/chat/:id', component: Chat},
        {path: '/chatList', component: ChatList},
        {path: '/responses/:orderId', component: CustomerResponses},
        {path: '/response', component: ExecutorResponses, meta: {isExecutor: true}},
        {path:'/profile/:id',component:CurrentProfile},
        // {path:'*',component:'Main'}
    ]
});
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
export default router;