<template>

<v-container>

    <v-card  max-width="1000" tile>
        <h4>Заказ № {{order.id}}</h4>

        <v-list-item>
            <v-list-item-content>
                <v-list-item-title><strong>Статус:  {{order.status}}</strong></v-list-item-title>
            </v-list-item-content>
        </v-list-item>
        <v-list-item>
            <v-list-item-content>
                <v-list-item-title><strong>Описание:  {{order.description}}</strong></v-list-item-title>
            </v-list-item-content>
        </v-list-item>
        <v-list-item>
            <v-list-item-content>
                <v-list-item-title><strong>Сумма:  {{order.sum}}</strong></v-list-item-title>
            </v-list-item-content>
        </v-list-item>
        <v-list-item>
            <v-list-item-content>
                <v-list-item-title><strong>Дата:  {{order.date}}</strong></v-list-item-title>
            </v-list-item-content>
        </v-list-item>
        <v-list-item>
            <v-list-item-content>
                <v-list-item-title><strong>Ширина:  {{order.width}}</strong></v-list-item-title>
            </v-list-item-content>
        </v-list-item>
        <v-list-item>
            <v-list-item-content>
                <v-list-item-title><strong>Длина:  {{order.length}}</strong></v-list-item-title>
            </v-list-item-content>
        </v-list-item>
        <v-list-item>
            <v-list-item-content>
                <v-list-item-title><strong>Высота:  {{order.height}}</strong></v-list-item-title>
            </v-list-item-content>
        </v-list-item>
        <v-list-item>
            <v-list-item-content>
                <v-list-item-title><strong>Файл:  {{order.file}}</strong></v-list-item-title>
            </v-list-item-content>
        </v-list-item>



    </v-card>

</v-container>
</template>

<script>
import {AXIOS} from "../pages/http-common";
export default {
props:[],
 data(){
 return{
    order:{
        id:0,
        userId:0,
        status:'',
        sum:0,
        date:null,
        height:0,
        width:0,
        length:0,
        file:'',
        description:''

    },

    accessToken: localStorage.getItem('accessToken')

 }
 },
    created:function(){

         AXIOS.get("/order/"+this.$route.params.id).then((responce) =>{
             this.order.id = responce.data.id;
             this.order.userId = responce.data.userId;
             this.order.status = responce.data.status;
             this.order.sum = responce.data.sum;
             this.order.date = responce.data.date;
             this.order.height = responce.data.height;
             this.order.width = responce.data.width;
             this.order.length = responce.data.length;
             this.order.file = responce.data.file;
             this.order.description = responce.data.description;

         }).catch(error => console.log(error));;
    },
    methods:{

    }
}
</script>

<style scoped>



</style>
