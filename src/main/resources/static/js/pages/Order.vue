<template>

    <v-container>

        <v-card max-width="1000" tile>
            <h4 class="orderNameHeader">Заказ "{{order.name}}"</h4>
            <v-divider></v-divider>
            <v-list-item>
                <v-list-item-content>
                    <v-list-item-title><strong>Статус: {{order.status}}</strong></v-list-item-title>
                </v-list-item-content>
            </v-list-item>
            <v-list-item>
                <v-list-item-content>
                    <v-list-item-title><strong>Описание: {{order.description}}</strong></v-list-item-title>
                </v-list-item-content>
            </v-list-item>
            <v-list-item>
                <v-list-item-content>
                    <v-list-item-title><strong>Сумма: {{order.sum}}</strong></v-list-item-title>
                </v-list-item-content>
            </v-list-item>
            <v-list-item>
                <v-list-item-content>
                    <v-list-item-title><strong>Дата: {{order.date}}</strong></v-list-item-title>
                </v-list-item-content>
            </v-list-item>
            <v-list-item>
                <v-list-item-content>
                    <v-list-item-title><strong>Ширина: {{order.width}}</strong></v-list-item-title>
                </v-list-item-content>
            </v-list-item>
            <v-list-item>
                <v-list-item-content>
                    <v-list-item-title><strong>Длина: {{order.length}}</strong></v-list-item-title>
                </v-list-item-content>
            </v-list-item>
            <v-list-item>
                <v-list-item-content>
                    <v-list-item-title><strong>Высота: {{order.height}}</strong></v-list-item-title>
                </v-list-item-content>
            </v-list-item>
            <v-list-item>
                <v-list-item-content>
                    <v-list-item-title><strong>Файл: {{order.file}}</strong></v-list-item-title>
                </v-list-item-content>
            </v-list-item>
            <v-list-item>
                <div>
                    <strong>Материалы:  <span v-for="material of order.materials"> {{material}}  </span></strong>
                </div>
            </v-list-item>


        </v-card>
        <v-content>
            <div v-if="this.order.userId === this.myId" class="mt-2">
                <v-btn to="/orders">К заказам</v-btn>
                <v-btn @click="goToEditOrder">Редактировать заказ</v-btn>
                <v-btn class="red--text" @click="deleteOrder">Удалить заказ</v-btn>
            </div>
            <div v-else class="mt-2">
                <b-button variant="outline-primary" @click="$router.go(-1)">Назад</b-button>
            </div>

        </v-content>

    </v-container>
</template>

<script>
    import {AXIOS} from "./http-common";

    export default {
        props: [],
        data() {
            return {
                order: {
                    id: 0,
                    userId: 0,
                    status: '',
                    name: '',
                    sum: 0,
                    date: null,
                    height: 0,
                    width: 0,
                    length: 0,
                    file: '',
                    description: '',
                    materials: [],


                },
                myId: '',
                accessToken: localStorage.getItem('accessToken')

            }
        },
        created: function () {

            AXIOS.get("/order/" + this.$route.params.id).then((response) => {
                this.order.id = response.data.id;
                this.order.userId = response.data.customerId;
                this.order.status = response.data.status;
                this.order.sum = response.data.sum;
                this.order.name = response.data.name;
                let dateStr = response.data.date;
                let dateStr1 = (dateStr.substring(0,10));
                let dateStr2 = (dateStr.substring(11,16));
                this.order.date = dateStr1+" "+dateStr2;
                this.order.height = response.data.height;
                this.order.width = response.data.width;
                this.order.length = response.data.length;
                this.order.file = response.data.file;
                this.order.description = response.data.description;
                this.order.materials = response.data.materials;
                console.log(response.data);
            }).catch(error => console.log(error));

            this.myId = localStorage.getItem('myId')
        },
        methods: {
            deleteOrder: function () {
                var id = this.order.id;
                AXIOS.delete('order/' + id);
                this.$router.push('/orders');
                location.reload()
            },

            goToEditOrder() {
                var s = '/order/edit/' + this.order.id;
                this.$router.push(s)
            }
        }
    }
</script>

<style scoped>
.v-card {
    margin-left: 65px;
    margin-top: 28px;
    border-radius: 20px
}
.btn-outline-primary {
    margin-left: 64px;
}

.orderNameHeader{
    margin-left: 17px;
    padding-top: 21px;
}

</style>
