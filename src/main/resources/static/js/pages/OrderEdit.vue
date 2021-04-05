<template>
    <v-container>
        <v-layout justify-space-around column>
            <div class="title">Редактирование</div>

            <v-flex xs6>
                <order-data-edit v-bind:order='order'></order-data-edit>
            </v-flex>
            <div class="my-2">
                <v-btn  large color="primary" @click="edit">Сохранить изменения</v-btn>
            </div>
        </v-layout>
    </v-container>
</template>

<script>
    import OrderDataEdit from "components/OrderDataEdit.vue";
    import {AXIOS} from "./http-common";
    export default {
        name: "OrderEdit",
        components: {OrderDataEdit},

    data(){
        return{
            order:{
                id:0,
                userId:0,
                status:'',
                sum:0,
                name:'',
                date:null,
                height:0,
                width:0,
                length:0,
                file:'',
                description:''

            }
        }
    },
    methods:{
        edit:function(){
            var order=this.order;
            var id=this.order.id;
            AXIOS.put('/order/'+id,{
                id : order.id,
            userId : order.userId,
            status : order.status,
            sum : order.sum,
            date : order.date,
            name : order.name,
            height : order.height,
            width : order.width,
            length : order.length,
            file : order.file,
            description : order.description
            });

            console.log(order);

            this.$router.push("/orders");
            location.reload()
        }
    }
    }
</script>

<style scoped>

</style>