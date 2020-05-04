<template>
    <v-container>
        <v-card max-width="1000" tile>
            <h4>Заказ "{{order.description}}"</h4>
            <v-list-item>
                <v-list-item-content>
                    <v-list-item-title>Статус: {{order.status}}</v-list-item-title>
                    <v-list-item-title> Начальная стоимость: {{order.sum}}₽</v-list-item-title>
                    <v-list-item-subtitle>Дата создания: {{order.date}}</v-list-item-subtitle>
                </v-list-item-content>
            </v-list-item>
            <v-list :shaped="true" :flat="true">
                <h3>Отклики:</h3>
                <v-list-item-group v-model="openChat" color="primary">
                    <v-list-item
                            v-for="(response, i) in responses"
                            :key="i"
                    >
                        <v-list-item-content>
                            <v-list-item-title><strong>{{response.executorInfo}}</strong></v-list-item-title>
                            <v-list-item-title><strong>{{response.sum}}₽</strong></v-list-item-title>
                            <v-list-item-subtitle>{{response.status}}</v-list-item-subtitle>
                            <v-list-item-subtitle>{{response.date}}</v-list-item-subtitle>
                        </v-list-item-content>
                    </v-list-item>
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
        <v-content>
            <div class="mt-2">
                <b-button variant="outline-primary" @click="$router.go(-1)"> Вернуться к списку заказов</b-button>
            </div>
        </v-content>
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
            openChat() {

            }
        }
    }
</script>

<style scoped>

</style>