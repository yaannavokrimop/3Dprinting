<template>
    <v-container>
        <v-card max-width="1000" tile>
            <h3 align="center">Заявки</h3>
            <v-list>
                <v-list-item-group>
                    <template v-for="(response, i) in responses">
                        <v-list-item :key="i">
                            <template v-slot>
                                <v-list-item-content>
                                    <v-list-item-title><h5>Заказ: {{response.orderName}}</h5></v-list-item-title>
                                    <v-list-item-title><strong>{{response.sum}}₽</strong></v-list-item-title>
                                    <v-list-item-title><strong>Заказчик: {{response.customerInfo}}</strong></v-list-item-title>
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
                                    <v-btn @click="openOrder(response)" class="ma-2" color="blue" fab small>
                                        <v-icon color="white"> mdi-information</v-icon>
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
            </v-list>
            <div v-if="pagination.need">
                <v-pagination
                        v-model="pagination.page"
                        :length="pagination.total"
                        total-visible=6
                        @input="getResponses"
                ></v-pagination>
            </div>

        </v-card>
    </v-container>
</template>

<script>
    import {AXIOS} from "../pages/http-common";

    export default {
        data() {
            return {
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
                AXIOS.get('/response/forexecutor?page=' + this.pagination.page +
                    '&perPage=' + this.pagination.perPage)
                    .then((response) => {
                        this.responses = response.data.content;
                        this.pagination.total = response.data.pageCount;
                        if (this.pagination.total > 1) this.pagination.need = true;
                        console.log(response.data);
                    }).catch(error => console.log(error));
            },
            openOrder(response) {
                this.$router.push('/orders/' + response.orderId);
            },
            openChat(response) {
                localStorage.removeItem('currentChat');
                AXIOS.get('/chat/response?' +
                    'executorId=' + response.executorId +
                    '&customerId=' + response.customerId).then((response) => {
                    localStorage.setItem('currentChat', JSON.stringify(response.data));
                    this.$router.push('/chat/' + response.data.chatId);
                }).catch(error => console.log(error));
            },
            openProfile(response) {
                this.$router.push('/profile/' + response.customerId);
            }
        }
    }

</script>

<style scoped>

</style>