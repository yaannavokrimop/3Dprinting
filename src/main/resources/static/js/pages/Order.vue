<template>

    <v-container>

        <v-card max-width="1000" tile>
            <h4 class="orderNameHeader">Заказ "{{order.name}}"</h4>
            <v-divider></v-divider>
            <v-list-item>
                <v-list-item-content>
                    <v-list-item-title><strong>Статус: {{order.status}}</strong></v-list-item-title>
                    <v-list-item-title><strong>Начальная стоимость: {{order.sum}}₽</strong></v-list-item-title>
                    <v-list-item-subtitle>
                        <strong>Длина: {{order.length}} мм; </strong>
                        <strong> Высота: {{order.height}} мм; </strong>
                        <strong> Ширина: {{order.width}} мм </strong>
                    </v-list-item-subtitle>
                    <v-list-item-subtitle>
                        <strong>Материалы: <span v-for="material of order.materials"> {{material}}  </span></strong>
                    </v-list-item-subtitle>
                    <v-list-item-subtitle><strong>Описание: {{order.description}}</strong></v-list-item-subtitle>
                    <v-list-item-subtitle v-if="this.order.file">
                        <strong>Схема изделия: ...{{order.file.substr(-15)}}</strong>
                        <v-btn @click="downloadFile" icon>
                            <v-icon color="dark">mdi-download</v-icon>
                        </v-btn>
                    </v-list-item-subtitle>
                    <v-list-item-subtitle>Дата создания: {{order.date}}</v-list-item-subtitle>
                </v-list-item-content>
            </v-list-item>
        </v-card>
        <v-content>
            <div class="mt-2" align="center">
                <v-btn @click="$router.go(-1)">Назад</v-btn>
                <v-btn v-if="this.order.userId === this.myId" @click="goToEditOrder">Редактировать заказ</v-btn>
                <v-btn v-if="this.order.userId === this.myId" class="red--text" @click="deleteOrder">Удалить заказ</v-btn>
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
                myId: localStorage.getItem('myId'),
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
                let dateStr1 = (dateStr.substring(0, 10));
                let dateStr2 = (dateStr.substring(11, 16));
                this.order.date = dateStr1 + " " + dateStr2;
                this.order.height = response.data.height;
                this.order.width = response.data.width;
                this.order.length = response.data.length;
                this.order.file = response.data.file;
                this.order.description = response.data.description;
                this.order.materials = response.data.materials;
                console.log(response.data);
            }).catch(error => console.log(error));
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
            },

            downloadFile() {
                AXIOS.get('/order/file/' + this.order.file, {responseType: 'blob'}).then((response) => {
                    const url = window.URL.createObjectURL(new Blob([response.data]));
                    const link = document.createElement('a');
                    link.href = url;
                    link.setAttribute('download', this.order.file);
                    document.body.appendChild(link);
                    link.click();
                    link.remove();
                    window.URL.revokeObjectURL(url);
                }).catch(error => console.log(error));

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
