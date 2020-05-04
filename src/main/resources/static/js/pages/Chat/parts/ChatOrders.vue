<template>
    <v-list>
        <v-list-item>
            <v-list-item-icon>
                <v-icon>mdi-check-decagram</v-icon>
            </v-list-item-icon>

            <v-list-item-title>Назначенные заказы</v-list-item-title>
        </v-list-item>
        <v-list-group
                v-for="order in orders"
                :key="order.name"
                v-model="order.active"
        >
            <template v-slot:activator>
                <v-list-item-content>
                    <v-list-item-title v-text="order.name"></v-list-item-title>
                </v-list-item-content>
            </template>
            <v-container>
                <v-list-item-content>
                    <v-list-item-title v-text="'Статус заказа: '+order.status"></v-list-item-title>
                </v-list-item-content>
            </v-container>
        </v-list-group>
    </v-list>

</template>

<script>
    import {AXIOS} from "../../http-common";

    export default {
        name: "Orders",
        data() {
            return {
                orders: [],
                currentChat: JSON.parse(localStorage.getItem('currentChat')),
                isExecutor: false
            }
        },
        created() {
            AXIOS.get('/order/forchat/' + this.currentChat.chatId).then((response) => {
                this.orders = response.data;
                console.log(response.data);
            }).catch(error => console.log(error));
            this.isExecutor = this.currentChat.isExecutor
        },
    }
</script>

<style scoped>

</style>