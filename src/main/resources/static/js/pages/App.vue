<template>
    <v-app>
        <div>
            <v-app-bar  dark>
                 <v-app-bar-nav-icon></v-app-bar-nav-icon>
                 <v-toolbar-title>3DPrinting</v-toolbar-title>
                 <v-spacer></v-spacer>
                 <v-btn icon href="/">
                       <v-icon>mdi-card-search-outline</v-icon>
                 </v-btn>

                 <v-btn icon href="/profile">
                       <v-icon>mdi-account</v-icon>
                 </v-btn>
                 <v-btn icon href="/">
                       <v-icon>mdi-email</v-icon>
                 </v-btn>

                 <v-btn icon @click="logout">
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
export default {
    methods:{
            logout:function(){
                this.$resource.dispatch('logout').
                    then(()=>{
                        console.log("logout success")
                    });
            }
         }
}
</script>

<style scoped>

</style>