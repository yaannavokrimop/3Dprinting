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
            if(this.$data.validP||this.$data.order.status=='DRAFT'){
                var order=this.order;
                var id=this.order.id;
                console.log(order.fileName);
                if (order.file == null) {
                    order.fileName = null;
                    AXIOS.delete('/order/file/' + id);
                } else if (order.fileName !== order.file.name) {
                    let formData = new FormData();
                    formData.append("file", order.file);
                    AXIOS.put('/order/file/' + id, formData).then((response) => {
                        order.fileName = response.data;
                    }).catch(error => console.log(error));
                }
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
                    file : order.fileName,
                    description : order.description,
                    materials:order.materials
                });

                console.log(order);

                this.$router.push("/orders");
                location.reload()
            }
        },
        testValid(validP) {
            this.$data.validP =validP;
            console.log("testMethod");
            }
    }
    }
</script>

<style scoped>

</style>