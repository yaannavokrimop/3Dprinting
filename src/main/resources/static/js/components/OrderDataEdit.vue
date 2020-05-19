<template>
    <v-layout column>

        <v-card>
            <v-card-title>
                Редактировать заказ
            </v-card-title>
            <v-card-text>

                <v-form ref="form" v-model="valid" lazy-validation>
                    <v-text-field
                            v-model="order.name"
                            label="Имя*"
                            :rules="[v => ( v.length <= 50 && v.length>0) || 'Должно быть от 1 до 50 символов']"
                            required>

                    </v-text-field>
                    <v-text-field
                            v-model="order.description"
                            label="Описание"
                            :rules="[v => ( v.length <= 250 ) || 'Должно быть  до 250 символов']"
                            required>
                    </v-text-field>
                    <v-text-field
                            type="number"
                            v-model="order.sum"
                            label="Сумма*"
                            :rules="[v => ( v <= 999999 && v>0 ) || 'Должно быть от 0 до 999999']">
                    </v-text-field>

                    <v-text-field
                            v-model="order.width"
                            type="number"
                            label="Ширина*"
                            :rules="[v => ( v <= 2000 && v>0 ) || 'Должно быть от 0 до 2000']">
                    </v-text-field>
                    <v-text-field
                            v-model="order.length"
                            type="number"
                            label="Длина*"
                            :rules="[v => ( v <= 2000 && v>0 ) || 'Должно быть от 0 до 2000']">
                    </v-text-field>
                    <v-text-field
                        v-model="order.height"
                        type="number"
                        label="Высота*"
                        :rules="[v => ( v <= 2000 && v>0 ) || 'Должно быть от 0 до 2000']">
                    </v-text-field>
                    <v-text-field
                            v-model="order.file"
                            label="Ссылка на файл*">
                    </v-text-field>
                     <v-autocomplete
                         v-model="order.materials"
                         :items="items"
                         cache-items
                         hide-no-data
                         hide-details
                         label="Материалы"
                         multiple
                         chips
                     ></v-autocomplete>

                </v-form>
  
                <div>
                    <div v-if="order.status === 'DRAFT'">
                        <strong>Статус заказа: {{order.status}}</strong>
                        <b-button variant="outline-primary" @click="notDraft(order.id)" :disabled="(changed || !valid)">Заказ больше не черновик</b-button>
                    </div>
                    <div v-else>
                        <strong>Статус заказа: {{order.status}}</strong>
                    </div>

                </div>

            </v-card-text>
        </v-card>

    </v-layout>
</template>

<script>
    import {AXIOS} from "../pages/http-common";

    export default {
        props:['order'],
        data(){
            return{
                valid:true,
                items: [],
                changed:false,
                startedOrder:{
                    sum:0,
                    name:'',
                    height:0,
                    width:0,
                    length:0,
                    file:''
                }

            }
        },
        created:function(){
            AXIOS.get("/order/"+this.$route.params.id).then((response) =>{
                this.order.id = response.data.id;
                this.order.userId = response.data.customerId;
                this.order.status = response.data.status;
                this.order.sum = response.data.sum;
                this.order.name = response.data.name;
                this.order.date = response.data.date;
                this.order.height = response.data.height;
                this.order.width = response.data.width;
                this.order.length = response.data.length;
                this.order.file = response.data.file;
                this.order.description = response.data.description;
                this.order.materials = response.data.materials;

                this.startedOrder.sum=response.data.sum;
                this.startedOrder.name=response.data.name;
                this.startedOrder.height=response.data.height;
                this.startedOrder.width=response.data.width;
                this.startedOrder.length=response.data.length;
                this.startedOrder.file= response.data.file;

                console.log("Материалы из ответа")
                console.log(response.data.materials);
                console.log("Материалы из данных")
                console.log(this.order.materials);
            }).catch(error => console.log(error));

            this.querySelections();
        },
        watch:{
            valid:function(){
                this.$emit('testMethod' ,this.$refs.form.validate());

                if(this.startedOrder.sum!=this.order.sum||
                    this.startedOrder.name!=this.order.name||
                    this.startedOrder.height!=this.order.height||
                    this.startedOrder.width!=this.order.width||
                    this.startedOrder.length!=this.order.length||
                    this.startedOrder.file!=this.order.file){
                        this.changed=true;
                        console.log("changed");
                }

            },
        },

        methods: {
            notDraft(orderId) {
                AXIOS.patch('order/notDraft/'+orderId).
                then((response) => {
                    console.log(response);
                    location.reload()
                }).catch(error => console.log(error));
            },
            querySelections () {
               AXIOS.get('/material').then((response) =>{
                this.items=response.data;
              }).catch(error => console.log(error));
            },
        }
    }
</script>

<style scoped>

</style>