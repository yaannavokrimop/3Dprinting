<template>
<v-container>

    <v-card  max-width="1200" tile>
        <h4>Заказы:</h4>
        <ul class="list-group">
            <li class="list-group-item"
                 v-for="(order,index) in orders"
                 :class="{ active: index == currentIndex }"
                 :key="index"
                 @click="setActiveOrder(order, index)"
                 @dblclick="goToOrder(order)"
            >

    <v-list-item>
      <v-list-item-content three-line>
        <v-list-item-title><strong>Статус:  {{order.status}}</strong></v-list-item-title>
        <v-list-item-subtitle>
         <strong>Описание:   {{order.description}}</strong>
        </v-list-item-subtitle>
        <v-list-item-subtitle>
         <strong>Сумма:   {{order.sum}}</strong>
        </v-list-item-subtitle>

      </v-list-item-content>
    </v-list-item>

            </li>
        </ul>

                <div v-if="currentOrder">
                    <div class="mt-2"></div>
                  <label><strong>Current order:</strong></label> {{ currentOrder.description }}





                </div>
    </v-card>

    <v-content>
        <div class="mt-2">
            <v-btn  to="/order/create">Разместить заказ</v-btn>
            <div v-if="currentOrder">
                <div class="mt-2"></div>
                <v-btn class="red--text" @click="selectExecutor">Выбрать исполнителя для заказа</v-btn>
            </div>
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
    orders:[],
    currentOrder:null,
    currentIndex:-1,
    accessToken: localStorage.getItem('accessToken'),


 }
 },
    created:function(){
         AXIOS.get('/order/user').then((responce) =>{
             this.orders = responce.data;
             console.log(response.data);

         }).catch(error => console.log(error));
    },
    methods:{
     setActiveOrder(order, index) {
          this.currentOrder = order;
          this.currentIndex = index;
        },
     goToOrder(order){
        var s="/orders/"+order.id;
        this.$router.push(s)
     },
     selectExecutor() {
         localStorage.setItem('currentOrder', this.currentOrder.id);
         this.$router.push('/')
     }
    }
}
</script>

<style scoped>



</style>