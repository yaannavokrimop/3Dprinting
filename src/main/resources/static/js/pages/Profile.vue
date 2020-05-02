<template>
        <v-container>
        <v-flex>
        <user-data v-bind:user='user'></user-data>
        </v-flex>


         </v-container>

</template>

<script>
import UserData from 'components/UserData.vue'

export default {
name: "Profile",
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
                 addresses:[]

                },
                accessToken: localStorage.getItem('accessToken'),
                role: localStorage.getItem('authority'),
            }
          }
}
</script>

<style scoped>

</style>