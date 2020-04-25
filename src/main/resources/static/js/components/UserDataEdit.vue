<template>

<v-layout column>

<v-card>
    <v-card-title>
     Edit Profile
    </v-card-title>
    <v-card-text>
     <v-text-field
          v-model="user.name"
          label="Name">
     </v-text-field>
     <v-text-field
          v-model="user.surname"
          label="Surname">
     </v-text-field>
     <v-text-field
          v-model="user.email"
          label="Email Address">
     </v-text-field>

     <v-text-field
          v-model="user.phone"
          label="Tel.">
     </v-text-field>
     <v-text-field
          v-model="user.information"
          label="Information">
     </v-text-field>
     <v-switch v-model="user.role" label="Стать исполнителем" value="EXECUTOR" v-show="user.role=='CUSTOMER'&&testRoleResult"></v-switch>
    </v-card-text>

    <div>
    <h3>Адреса</h3>
    <ul>
      <li v-for="address in user.addresses">
        Город: {{ address.city}}  Адрес:{{address.description}}
      </li>
    </ul>
    </div>
</v-card>

</v-layout>


</template>

<script>
import {AXIOS} from "../pages/http-common";

export default {
    props:['user'],
    data(){
        return{
        testRoleResult:false
        }
    },
    created:function(){
         AXIOS.get('/user').then((responce) =>{
             this.user.id = responce.data.id;
             this.user.email =responce.data.email;
             this.user.information = responce.data.information;
             this.user.name = responce.data.name;
             this.user.phone = responce.data.phone;
             this.user.role = responce.data.role;
             this.user.surname = responce.data.surname;
             this.user.addresses = responce.data.addresses;
         }).catch(error => console.log(error));
         this.checkRole();
    },
    methods:{
        checkRole(){
            AXIOS.get('/user/role').then((response) =>{
                this.$data.testRoleResult=response.data;

            })

        }
    }
}
</script>

<style scoped>

</style>