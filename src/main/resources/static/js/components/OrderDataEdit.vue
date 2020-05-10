<template>
    <v-layout column>

        <v-card>
            <v-card-title>
                Редактировать заказ
            </v-card-title>
            <v-card-text>
                <v-text-field
                        v-model="order.name"
                        label="Имя">
                </v-text-field>
                <v-text-field
                        v-model="order.description"
                        label="Описание">
                </v-text-field>
                <v-text-field
                        v-model="order.sum"
                        label="Сумма">
                </v-text-field>

                <v-text-field
                        v-model="order.width"
                        label="Ширина">
                </v-text-field>
                <v-text-field
                        v-model="order.length"
                        label="Длина">
                </v-text-field>
                <v-text-field
                    v-model="order.height"
                    label="Высота">
                </v-text-field>
                <v-text-field
                        v-model="order.file"
                        label="Ссылка на файл">
                </v-text-field>
                <div>
                    <div v-if="order.status === 'DRAFT'">
                        <strong>Статус заказа: {{order.status}}</strong>
                        <b-button variant="outline-primary" @click="notDraft(order.id)">Заказ больше не черновик</b-button>
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
        created:function(){
            AXIOS.get("/order/"+this.$route.params.id).then((response) =>{
                this.order.id = response.data.id;
                this.order.userId = response.data.userId;
                this.order.status = response.data.status;
                this.order.sum = response.data.sum;
                this.order.name = response.data.name;
                this.order.date = response.data.date;
                this.order.height = response.data.height;
                this.order.width = response.data.width;
                this.order.length = response.data.length;
                this.order.file = response.data.file;
                this.order.description = response.data.description;

            }).catch(error => console.log(error));
        },
        methods: {
            notDraft(orderId) {
                AXIOS.patch('order/notDraft/'+orderId).
                then((response) => {
                    console.log(response);
                    location.reload()
                }).catch(error => console.log(error));
            }
        }
    }
</script>

<style scoped>

</style>