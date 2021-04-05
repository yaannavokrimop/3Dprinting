<template>

<v-layout column>

<v-card>
    <v-card-title>
     {{user.name }} {{user.surname}}
    </v-card-title>
    <v-card-text>
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
     <v-text-field
          v-model="user.role"
          label="Information">
     </v-text-field>
    </v-card-text>
</v-card>

</v-layout>


</template>

<script>
import {AXIOS} from "../pages/http-common";

export default {
    props:['user'],
    created:function(){
         AXIOS.get('/user').then((responce) =>{
             this.user.id = responce.data.id;
             this.user.email =responce.data.email;
             this.user.information = responce.data.information;
             this.user.name = responce.data.name;
             this.user.phone = responce.data.phone;
             this.user.role = responce.data.role;
             this.user.surname = responce.data.surname;
         }).catch(error => console.log(error));;
    }
}
</script>

<style scoped>

</style>