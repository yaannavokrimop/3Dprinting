<template>
<v-container>

    <v-content>

    </v-content>

</v-container>
</template>

<script>
import {AXIOS} from "../pages/http-common";
export default {
props:[],
 data(){
 return{
        id:0,


    accessToken: localStorage.getItem('accessToken')
 }
 },
    created:function(){
         AXIOS.post('/order').then((responce) =>{
             console.log(response.data);

         }).catch(error => console.log(error));;
    },
    methods:{
        },
    }
}
</script>

<style scoped>



</style>