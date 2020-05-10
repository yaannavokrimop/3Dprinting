<template>

<v-layout column>

<v-card>
    <v-card-title>
    Редактирование профиля
    </v-card-title>
    <v-card-text>
        <v-form ref="form" v-model="valid" lazy-validation>
             <v-text-field
                v-model="user.name"
                label="Имя"
                :rules="[v => ( v.length <= 44 && v.length>0) || 'Должно быть от 1 до 44 символов']"
                required>
             </v-text-field>
             <v-text-field
                  v-model="user.surname"
                  label="Фамилия"
                  :rules="[v => ( v.length <= 59 && v.length>0) || 'Должно быть от 1 до 60 символов']"
                   required>
             </v-text-field>
             <v-text-field
                  v-model="user.email"
                  label="Электронная почта"
                  :rules="emailRules">
             </v-text-field>
             <v-text-field
                  v-model="user.phone"
                  required
                  label="Номер телефона"
                  :rules="[v => v.length<=12 && v.length>=5 || 'Номер введен некорректно']">
             </v-text-field>
             <v-text-field
                  v-model="user.information"
                  label="Информация"
                  :rules="[v => (v.length <= 200)|| 'Должно быть до 200 символов']">
             </v-text-field>
             <v-switch v-model="user.role" label="Стать исполнителем" value="EXECUTOR" v-show="user.role==='CUSTOMER'&&testRoleResult"></v-switch>
        </v-form>
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
        testRoleResult:false,
        valid:true,
        emailRules: [
            v => !!v || 'Поле Электронная почта не должно быть пустым ',
            v => ( /.+@.+\..+/.test(v))  || 'Электронная почта указана не правильно'
        ],
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
    watch:{
        valid:function(){
        this.$emit('testMethod' ,this.$refs.form.validate());
        }
    },
    methods:{
        checkRole(){
            AXIOS.get('/user/role').then((response) =>{
                this.$data.testRoleResult=response.data;
            })

        },

    }
}
</script>

<style scoped>

</style>