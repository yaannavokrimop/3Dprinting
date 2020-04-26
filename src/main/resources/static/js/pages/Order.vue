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
        <v-list-item>
            <div>
                <h3>Материалы</h3>
                <ul>
                    <li v-for="material in order.materials">
                        Материал: {{ material.matTitle}} 
                    </li>
                </ul>
            </div>
        </v-list-item>


    </v-card>
    <v-content>
        <div class="mt-2">
            <v-btn  to="/orders">К заказам</v-btn>
            <v-btn  @click="goToEditOrder">Редактировать заказ</v-btn>
            <v-btn class="red--text" @click="deleteOrder">Удалить заказ</v-btn>
        </div>

    </v-content>

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
        description:'',
        materials:'',

    },

    accessToken: localStorage.getItem('accessToken')

 }
 },
    created:function(){

         AXIOS.get("/order/"+this.$route.params.id).then((response) =>{
             this.order.id = response.data.id;
             this.order.userId = response.data.userId;
             this.order.status = response.data.status;
             this.order.sum = response.data.sum;
             this.order.date = response.data.date;
             this.order.height = response.data.height;
             this.order.width = response.data.width;
             this.order.length = response.data.length;
             this.order.file = response.data.file;
             this.order.description = response.data.description;
             this.order.materials = response.data.materials;

         }).catch(error => console.log(error));
    },
    methods:{
        deleteOrder:function () {
            var id = this.order.id;
            AXIOS.delete('order/'+id);
            this.$router.push('/orders');
            location.reload()
        },

        goToEditOrder(){
            var s='/order/edit/'+this.order.id;
            this.$router.push(s)
        }
    }
}
</script>

<style scoped>



</style>
