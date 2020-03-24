import Vue from 'vue'
import VueResource from 'vue-resource'
import Profile from 'pages/Profile.vue'
import vuetify from 'plagins/vuetify'
import 'vuetify/dist/vuetify.min.css'


// Vue.use(Vuetify)
Vue.use(VueResource);

// new Vue({
//     el:'#app',
//     render: a => a(Profile)
// });

new Vue({
    vuetify,
    render: a => a(Profile)
}).$mount('#app')