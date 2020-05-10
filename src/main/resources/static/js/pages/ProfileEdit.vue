<template>


            <v-container>
                <v-layout justify-space-around column>

                   <v-layout row>
                    <user-data-edit v-bind:user='user' @testMethod="testValid"></user-data-edit>
                   </v-layout>
                    <div class="mt-2"></div>
                   <v-flex xs6>
                   <add-address></add-address>
                   </v-flex>

                   <div class="my-2">
                           <v-btn  large color="primary" @click="edit" :disabled="!validP">Сохранить изменения</v-btn>
                   </div>
                </v-layout>
            </v-container>


</template>

<script>
import UserDataEdit from 'components/UserDataEdit.vue'
import AddAddress from 'pages/AddAddress.vue'
import {AXIOS} from "../pages/http-common";
export default {
    components:{
        UserDataEdit,
        AddAddress
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
            testUserRole:'',
            validP:true

            }
          },
    methods:{
        edit:function(){
            if(this.$data.validP){
                var user=this.user;
                var id=this.user.id;
                AXIOS.put('/user/update/'+id,{
                    id:user.id,
                    email:user.email,
                    information:user.information,
                    name:user.name,
                    phone:user.phone,
                    role:user.role,
                     surname:user.surname
                    });

                this.$router.push('/profile');
                location.reload();
                console.log(user);
            }
        },
        testValid(validP) {
            this.$data.validP =validP;
            console.log("testMethod");
        }
    }


}
</script>

<style scoped>

</style>