<template>
    <v-container>
        <v-card max-width="1000" tile>
            <v-row class="fill-height">
                <v-card-title>
                    <v-btn text @click="$router.go(-1)">
                        <v-icon>mdi-arrow-left</v-icon>
                        Назад
                    </v-btn>
                    <h4 align="center">Заказ "{{order.name}}"</h4>
                    <v-list-item>
                        <v-list three-line>
                            <v-list-item-content>
                                <v-list-item-title>Статус: {{order.status}}</v-list-item-title>
                                <v-list-item-title> Начальная стоимость: {{order.sum}}₽</v-list-item-title>
                                <v-list-item-subtitle>Дата создания: {{order.date}}</v-list-item-subtitle>
                            </v-list-item-content>
                        </v-list>
                    </v-list-item>
                </v-card-title>
            </v-row>
            <v-list shaped flat>
                <h3 align="center">Отклики</h3>
                <v-list-item-group>
                    <template v-for="(response, i) in responses">
                        <v-list-item :key="i">
                            <template>
                                <v-list-item-content>
                                    <v-list-item-title><strong>{{response.executorInfo}}</strong></v-list-item-title>
                                    <v-list-item-title><strong>{{response.sum}}₽</strong></v-list-item-title>
                                    <v-list-item-subtitle>{{response.status}}</v-list-item-subtitle>
                                    <v-list-item-subtitle>{{response.date}}</v-list-item-subtitle>
                                </v-list-item-content>
                                <v-spacer></v-spacer>
                                <v-list-item-icon>
                                    <v-btn @click="openProfile(response)" class="ma-2" color="deep-purple" fab small>
                                        <v-icon color="white">mdi-account-circle</v-icon>
                                    </v-btn>
                                    <v-btn @click="openChat(response)" class="ma-2" color="primary" fab small>
                                        <v-icon> mdi-message-text</v-icon>
                                    </v-btn>
                                </v-list-item-icon>
                            </template>
                        </v-list-item>
                        <v-divider
                                v-if="i + 1 < responses.length"
                                :key="j"
                        ></v-divider>
                    </template>
                </v-list-item-group>
                <div v-if="pagination.need">
                    <v-pagination
                            v-model="pagination.page"
                            :length="pagination.total"
                            total-visible=6
                            @input="getResponses"
                    ></v-pagination>
                </div>
            </v-list>
        </v-card>
    </v-container>
</template>

<script>
    import {AXIOS} from "../pages/http-common";

    export default {
        data() {
            return {
                order: JSON.parse(localStorage.getItem("order")),
                responses: [],
                pagination: {
                    page: 1,
                    total: 0,
                    perPage: 4,
                    need: false
                }
            }
        },
        created: function () {
            this.getResponses();
        },
        methods: {
            getResponses() {
                AXIOS.get('/response/forcustomer?page=' + this.pagination.page +
                    '&perPage=' + this.pagination.perPage +
                    '&orderId=' + this.$route.params.orderId)
                    .then((response) => {
                        this.responses = response.data.content;
                        this.pagination.total = response.data.pageCount;
                        if (this.pagination.total > 1) this.pagination.need = true;
                        console.log(response.data);
                    }).catch(error => console.log(error));
            },
            openChat(response) {
                AXIOS.get('/chat/response?' +
                    'executorId=' + response.executorId +
                    '&customerId=' + response.customerId).then((response) => {
                    localStorage.setItem('currentChat', JSON.stringify(response.data));
                    this.$router.push('/chat/' + response.data.chatId);
                }).catch(error => console.log(error));
            },
            openProfile(response) {
                this.$router.push('/profile/' + response.executorId);
            }
        }
    }
</script>

<style scoped>

</style>