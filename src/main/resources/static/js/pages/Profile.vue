<template>
    <v-app>
        <div><v-app-bar  dark>
         <v-app-bar-nav-icon></v-app-bar-nav-icon>
            <v-toolbar-title>3DPrinting</v-toolbar-title>
            <v-spacer></v-spacer>

             <v-btn icon  href="/logout">
                    <v-icon>mdi-exit-to-app</v-icon>
             </v-btn>
         </v-app-bar>
         </div>

        <v-content>
            <v-container>
                <user-data v-bind:user='user'></user-data>
            </v-container>
        </v-content>
    </v-app>
</template>

<script>
import UserData from 'components/UserData.vue'

export default {
    components:{
        UserData
    },
    data(){
        return{
            user:{
                 id:0,
                 email:'',
                 information:'',
                 name:'',
                 phone:'',
                 role:'',
                 surname:'',

            }
            }
          }
}
</script>

<style scoped>

</style>