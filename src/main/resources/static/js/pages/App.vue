<template>
    <v-app>
        <div>
            <v-navigation-drawer
                    absolute
                    dark
                    temporary
                    v-model="drawerToggle"
                    color="#212F3D"
            >
                <v-list
                        dense
                        nav
                        class="py-0"
                >
                    <v-list-item>
                        <div class="mt-2"></div>
                        <v-list-item-avatar style="margin: 10px">
                            <v-img src="https://sun9-45.userapi.com/c855628/v855628971/22c8eb/xn_ABhoZ8is.jpg"></v-img>
                        </v-list-item-avatar>

                        <v-list-item-content>
                            <v-list-item-title>
                                Меню пользователя
                            </v-list-item-title>
                        </v-list-item-content>
                    </v-list-item>

                    <v-divider></v-divider>
                    <v-list-item
                            v-for="item in items"
                            :key="item.title"
                            router :to="item.route"
                    >
                        <v-list-item-icon>
                            <v-icon>{{ item.icon }}</v-icon>
                        </v-list-item-icon>

                        <v-list-item-content>
                            <v-list-item-title>{{ item.title }}</v-list-item-title>
                        </v-list-item-content>
                    </v-list-item>
                </v-list>
            </v-navigation-drawer>

            <v-app-bar dark>
                <v-app-bar-nav-icon @click.native.stop="drawerToggle = !drawerToggle"></v-app-bar-nav-icon>
                <v-toolbar-title>3DPrinting</v-toolbar-title>
                <v-spacer></v-spacer>
                <v-btn icon href="/">
                    <v-icon>mdi-card-search-outline</v-icon>
                </v-btn>

                <v-btn icon class="nav-link" to="/profile" v-show="authenticated">
                    <v-icon>mdi-account</v-icon>
                </v-btn>
                <v-btn icon to="/orders" v-show="authenticated">
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
        data () {
            return {
                items: [
                    { title: 'Адреса', icon: 'mdi-home-map-marker', route: "/address" },
                    { title: 'Оборудование', icon: 'mdi-printer-3d', route: "/equipment" },
                    { title: 'Отклики', icon: 'mdi-account-check', route: "/response" },
                    { title: 'Чаты', icon: 'mdi-email', route: "/chatList" },
                ],
                drawerToggle: false
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