<template>
    <v-app>
        <div>
            <v-app-bar dark>
                <v-app-bar-nav-icon></v-app-bar-nav-icon>
                <v-toolbar-title>3DPrinting</v-toolbar-title>
                <v-spacer></v-spacer>
                <v-btn icon href="/">
                    <v-icon>mdi-card-search-outline</v-icon>
                </v-btn>

                <v-btn icon class="nav-link" to="/profile" v-show="authenticated">
                    <v-icon>mdi-account</v-icon>
                </v-btn>
                <v-btn icon href="/">
                    <v-icon>mdi-email</v-icon>
                </v-btn>

                <v-btn icon @click="checkLogout" v-show="authenticated">
                    <v-icon>mdi-exit-to-app</v-icon>
                </v-btn>

                <v-btn icon class="nav-link" to="/signin" v-show="!authenticated">
                    <v-icon>mdi-exit-to-app</v-icon>
                </v-btn>
            </v-app-bar>
            <v-content>
                <router-view></router-view>


            </v-content>
        </div>


    </v-app>
</template>

<script>
    import {mapActions} from "vuex";
    import router from "../router/router";

    export default {
        data() {
            return {
                accessToken: localStorage.getItem('accessToken'),
                isAuth: this.$store.getters.authenticated,
                userData: this.$store.getters.userData
            }
        },
        methods: {
            ...mapActions([
                'fetchAccessToken',
                'doLogout'
            ]),
            checkLogout(event) {
                event.preventDefault();
                this.doLogout()
            },
        },
        computed: {
            authenticated() {
                this.$store.commit('updateAccessToken', localStorage.getItem('accessToken'));
                return this.$store.getters.authenticated;
            },
        },
    }
</script>

<style scoped>

</style>