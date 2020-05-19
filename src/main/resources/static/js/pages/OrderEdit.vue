<template>
    <v-container>
        <v-layout justify-space-around column>
            <v-flex xs6>
                <order-data-edit v-bind:order='order' @testMethod="testValid"></order-data-edit>
            </v-flex>
            <div class="my-2" align="center">
                <v-btn  large color="primary" @click="edit" :disabled="!validP" >Сохранить изменения</v-btn>
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
                file: null,
                fileName: '',
                description:'',
                materials:null
            },
            validP:true,


        }
    },
    //beforeUpdate:function(){if(this.validP==false){startedValid=this.validP;}},
    methods:{
        edit:function(){
            if(this.$data.validP||this.$data.order.status==='Черновик'){
                var order=this.order;
                var id=this.order.id;
                if (order.file == null) {
                    AXIOS.delete('/order/file/' + id).then((response) => {
                        order.fileName = null;
                        console.log('deleted');
                        this.sendOrder(order);
                    })
                } else if (order.fileName !== order.file.name) {
                    let formData = new FormData();
                    formData.append("file", order.file);
                    AXIOS.put('/order/file/' + id, formData).then((response) => {
                        console.log(response.data);
                        order.fileName = response.data;
                        this.sendOrder(order);
                    }).catch(error => console.log(error));
                } else {
                    this.sendOrder(order);
                }
            }
        },
        testValid(validP) {
            this.$data.validP =validP;
            console.log("testMethod");
        },
        sendOrder(order) {
            console.log('sendOrder');
            AXIOS.put('/order/'+order.id,{
                id : order.id,
                userId : order.userId,
                status : order.status,
                sum : order.sum,
                date : order.date,
                name : order.name,
                height : order.height,
                width : order.width,
                length : order.length,
                file : order.fileName,
                description : order.description,
                materials:order.materials
            }).then(response => {
                console.log(response);
                this.$router.push("/orders");
                location.reload()
            });
        }
    }
    }
</script>

<style scoped>

</style>