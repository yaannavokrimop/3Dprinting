<template>
<v-container>
    <v-content>
        <h4>Мои Адреса</h4>
        <ul class="list-group">
            <li class="list-group-item"
                 v-for="(addr,index) in addresses"
                 :class="{ active: index == currentIndex }"
                 :key="index"
                 @click="setActiveAddr(addr, index)"
            >
            <v-list-item three-line>
                  <v-list-item-content>
                    <v-list-item-title><strong>Город:  {{addr.city}}</strong></v-list-item-title>
                    <v-list-item-subtitle>
                     <strong>Адрес:   {{addr.description}}</strong>
                    </v-list-item-subtitle>
                  </v-list-item-content>
            </v-list-item>
            </li>
        </ul>

    </v-content>
        <v-content>
        <add-address></add-address>
    </v-content>
</v-container>
</template>

<script>
import {AXIOS} from "../pages/http-common";
import AddAddress from 'pages/AddAddress.vue';
export default {
    components:{
        AddAddress
    },
props:[],
 data(){
 return{
    addresses:[],
    currentAddr:null,
    currentIndex:-1,


            }
        },
        created: function () {

            AXIOS.get('/address/user').then((responce) => {
                this.addresses = responce.data;
                console.log(responce.data);
            }).catch(error => console.log(error));

        },


    methods:{
     setActiveAddr(addr, index) {
          this.currentAddr = addr;
          this.currentIndex = index;
        }

    }
}
</script>

<style scoped>

</style>