<template>

<v-layout column>

<v-card>
    <v-card-title>
     Редактирование профиля
    </v-card-title>
    <v-card-text>
     <v-text-field
          v-model="user.name"
          label="Имя">
     </v-text-field>
     <v-text-field
          v-model="user.surname"
          label="Фамилия">
     </v-text-field>
     <v-text-field
          v-model="user.email"
          label="Электронная почта">
     </v-text-field>

     <v-text-field
          v-model="user.phone"
          label="Номер телефона">
     </v-text-field>
     <v-text-field
          v-model="user.information"
          label="Информация">
     </v-text-field>
     <v-switch v-model="user.role" label="Стать исполнителем" value="EXECUTOR" v-show="user.role==='CUSTOMER'&&testRoleResult"></v-switch>
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
         AXIOS.get('/user').then((response) =>{
             this.user.id = response.data.id;
             this.user.email =response.data.email;
             this.user.information = response.data.information;
             this.user.name = response.data.name;
             this.user.phone = response.data.phone;
             this.user.role = response.data.role;
             this.user.surname = response.data.surname;
             this.user.addresses = response.data.addresses;
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