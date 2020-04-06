<template>


            <v-container>
                <v-layout justify-space-around column>
                    <div class="title">Профиль</div>

                   <v-flex xs6>
                        <user-data v-bind:user='user'></user-data>
                   </v-flex>

                    <div class="my-2">
                            <v-btn to="/profile_edit">Редактировать</v-btn>
                    </div>

                    <div class="my-2">
                            <v-btn to="/equipment">Моё оборудование</v-btn>
                    </div>

                    <div> {{accessToken}}</div>
                </v-layout>
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

            },
             accessToken: localStorage.getItem('accessToken'),
            }
          },

}
</script>

<style scoped>

</style>