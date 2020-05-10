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
                            label="Имя"
                            :rules="[v => ( v.length <= 50 && v.length>0) || 'Должно быть от 1 до 50 символов']"
                            required>

                    </v-text-field>
                    <v-text-field
                            v-model="order.status"
                            label="Статус">
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
                            label="Сумма"
                            :rules="[v => ( v <= 999999 && v>0 ) || 'Должно быть от 0 до 999999']">
                    </v-text-field>

                    <v-text-field
                            v-model="order.width"
                            type="number"
                            label="Ширина"
                            :rules="[v => ( v <= 2000 && v>0 ) || 'Должно быть от 0 до 2000']">
                    </v-text-field>
                    <v-text-field
                            v-model="order.length"
                            type="number"
                            label="Длина"
                            :rules="[v => ( v <= 2000 && v>0 ) || 'Должно быть от 0 до 2000']">
                    </v-text-field>
                    <v-text-field
                        v-model="order.height"
                        type="number"
                        label="Высота"
                        :rules="[v => ( v <= 2000 && v>0 ) || 'Должно быть от 0 до 2000']">
                    </v-text-field>
                    <v-text-field
                            v-model="order.file"
                            label="Ссылка на файл">
                    </v-text-field>
                </v-form>
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
                valid:true
            }
        },
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
        }
    }
</script>

<style scoped>

</style>