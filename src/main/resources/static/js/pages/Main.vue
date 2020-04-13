<template>
    <v-container>
        <v-content>
            <div class="col-md-8">
                <div class="input-group mb-3">
                    <input type="text" class="form-control" placeholder="Search by address" v-model="city"/>
                    <div class="input-group-append">
                        <button class="btn btn-outline-secondary" type="button"
                        @click="searchByAddress"
                        >
                            Search
                        </button>
                        <button class="btn btn-outline-secondary" type="button"
                        @click="showAll"
                        >
                            Показать Всех
                        </button>
                    </div>
                </div>
            </div>

        </v-content>
        <v-content>
            <h3>Исполнители</h3>
            <ul class="list-group">
                <li class="list-group-item"
                v-for="(executor,index) in executorsFilter"
                :class="{ active: index == currentIndex }"
                :key="index"
                @click="setActiveExecutor(executor, index)"
                @dblclick="goToProfile(executor)"
                >
                    <v-list-item three-line>
                        <v-list-item-content>
                            <v-list-item-title><strong> {{executor.name+'  '+executor.surname}}</strong></v-list-item-title>
                            <v-list-item-subtitle>
                                <strong>Адреса: <ul class="demo">
                                                     <li v-for="address of executor.addresses">
                                                   {{ address.city }},  {{address.description}}
                                                    </li>
                                                </ul>  </strong>
                            </v-list-item-subtitle>
                        </v-list-item-content>
                    </v-list-item>
                </li>
            </ul>
        </v-content>
        <v-content>
            <div v-if="currentExecutor">
                <div class="mt-2"></div>
                <label><strong>Current executor:</strong></label> {{ currentExecutor.name }}
                <div class="mt-2"></div>
                <v-btn class="red--text" @click="sendResponse">Предложить заказ исполнителю</v-btn>
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
    executors:[],
    executorsFilter:[],
    currentExecutor:null,
    currentIndex:-1,
    city:''
 }
 },
    created:function(){
    AXIOS.get('/user/executors').then((responce) =>{
        this.executors = responce.data;
        this.executorsFilter=this.executors;
        console.log("Данные проверка2");
        console.log(responce.data);
    }).catch(error => console.log(error));


    },

    methods:{
     setActiveExecutor(executor, index) {
          this.currentExecutor = executor;
          this.currentIndex = index;
        },
     goToProfile(executor){
     console.log('пока не реализовано');
     },
     searchByAddress(){
     var w=this.$data.city;
     console.log("search by address start.............."+w)
      this.executorsFilter=this.executors.filter(function (executor) {
            if(executor.addresses.find(item => item.city === w)!=null){return executor;}

         });

     },
     showAll(){
     this.executorsFilter=this.$data.executors;
     },
        sendResponse() {
         AXIOS.post("/response", {
             'executorId': this.currentExecutor.id,
             'orderId' : localStorage.getItem('currentOrder')
         }).then ((response) =>  {
             console.log(response);
             localStorage.removeItem('currentOrder');
             this.$router.push('/orders');
         }).catch(error => console.log(error));

         },
    }

}
</script>

<style scoped>
.list {
  text-align: left;
  max-width: 900px;
  margin: auto;
}
</style>