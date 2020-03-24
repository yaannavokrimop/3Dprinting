<template>


            <v-container>
                <v-layout justify-space-around column>
                    <div class="title">Профиль</div>

                   <v-flex xs6>
                        <user-data v-bind:user='user'></user-data>
                   </v-flex>
                </v-layout>
            </v-container>


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
          },

}
</script>

<style scoped>

</style>