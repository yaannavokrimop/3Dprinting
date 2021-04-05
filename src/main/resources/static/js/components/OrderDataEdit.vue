<template>
    <v-layout column>

        <v-card>
            <v-card-title>
                Редактировать заказ
            </v-card-title>
            <v-card-text>
                <v-text-field
                        v-model="order.status"
                        label="Статус">
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
            </v-card-text>
        </v-card>

    </v-layout>
</template>

<script>
    import {AXIOS} from "../pages/http-common";

    export default {
        props:['order'],
        created:function(){
            AXIOS.get("/order/"+this.$route.params.id).then((responce) =>{
                this.order.id = responce.data.id;
                this.order.userId = responce.data.userId;
                this.order.status = responce.data.status;
                this.order.sum = responce.data.sum;
                this.order.date = responce.data.date;
                this.order.height = responce.data.height;
                this.order.width = responce.data.width;
                this.order.length = responce.data.length;
                this.order.file = responce.data.file;
                this.order.description = responce.data.description;

            }).catch(error => console.log(error));
        }
    }
</script>

<style scoped>

</style>