<template>


            <v-container>
                <v-layout justify-space-around column>
                    <div class="title">Редактирование</div>

                   <v-layout row>
                        <user-data-edit v-bind:user='user'></user-data-edit>
                   </v-layout>

                   <v-flex xs6>
                   <add-address></add-address>
                   </v-flex>

                   <div class="my-2">
                           <v-btn  large color="primary" @click="edit">Сохранить изменения</v-btn>
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
            testUserRole:''

            }
          },
    methods:{
        edit:function(){
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
            console.log(user);

        }
    }


}
</script>

<style scoped>

</style>