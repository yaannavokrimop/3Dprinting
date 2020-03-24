import Vue from 'vue'
import VueResource from 'vue-resource'
import router from 'router/router'
import App from 'pages/App.vue'
import vuetify from 'plagins/vuetify'
import 'vuetify/dist/vuetify.min.css'
// import Axios from 'axios'

// Vue.use(Vuetify)
Vue.use(VueResource);
// Vue.prototype.$http = Axios;

new Vue({
    vuetify,
    router,
    render: a => a(App)
}).$mount('#app')