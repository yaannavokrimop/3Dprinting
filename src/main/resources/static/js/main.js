import Vue from 'vue'
import VueResource from 'vue-resource'
import Profile from 'pages/Profile.vue'

Vue.use(VueResource);
new Vue({
    el:'#app',
    render: a => a(Profile)
});