<template>
    <v-list>
        <v-list-item>
            <v-list-item-icon>
                <v-icon>mdi-clipboard-text-multiple-outline</v-icon>
            </v-list-item-icon>

            <v-list-item-title>Заказы</v-list-item-title>
        </v-list-item>
        <v-list-group
                v-for="response in responses"
                :key="response.order.description"
                v-model="response.active"
        >
            <template v-slot:activator>
                <v-list-item-content>
                    <v-list-item-title v-text="response.order.description"></v-list-item-title>
                </v-list-item-content>
            </template>
            <v-container>
                <v-list-item-content>
                    <v-list-item-title v-text="'Статус заказа: '+response.status"></v-list-item-title>
                    <a :href="'#/orders/'+response.order.id">
                        <div class="details">
                            Подробности...
                        </div>
                    </a>
                    <v-text-field label="Сумма"></v-text-field>
                    <b-button variant="success">Предложить цену</b-button>
                    <b-button variant="danger">Отказаться от заказа</b-button>
                </v-list-item-content>
            </v-container>
        </v-list-group>
    </v-list>
</template>

<script>
    import {AXIOS} from "../../http-common";

    export default {
        name: "Responses",
        data() {
            return {
                responses: [],
                currentChat: JSON.parse(localStorage.getItem('currentChat'))
            }
        },
        created() {
            AXIOS.get('/response/forchat/' + this.currentChat.chatId).then((response) => {
                this.responses = response.data;
                console.log(response.data);
            }).catch(error => console.log(error));
        },
    }
</script>


<style>
    .details {
        color: #534e4e;
        text-decoration: none;
    }
</style>