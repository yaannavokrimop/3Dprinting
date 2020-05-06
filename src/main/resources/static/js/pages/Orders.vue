<template>
    <v-container>
        <div v-if="currentExecutor">
            <b-alert show dismissible fade>
                Вы выбираете заказ для исполнителя {{currentExecutorName}}
                <b-button class="mt-3" variant="outline-danger" block @click="clearExecutor">
                    Очистить
                </b-button>
            </b-alert>

        </div>
        <div v-if="!currentOrder">
            <b-alert show dismissible fade>Выберите заказ.</b-alert>

        </div>

        <v-content>
            <div class="col-md-8">
                <div class="input-group mb-5">
                    <input type="text" class="form-control" placeholder="Имя заказа"/>
                    <div class="input-group-append">
                        <button class="btn btn-outline-secondary" type="button"> Найти</button>
                        <button class="btn btn-outline-secondary" type="button"> Показать Всех</button>
                    </div>
                </div>
            </div>

        </v-content>

        <v-card max-width="1200" tile>
            <h4>Заказы:</h4>
            <ul class="list-group">
                <li class="list-group-item"
                    v-for="(order,index) in orders"
                    :class="{ active: index == currentIndex }"
                    :key="index"
                    @click="setActiveOrder(order, index, order.status)"
                    @dblclick="goToOrder(order)"
                >

                    <v-list-item>
                        <v-list-item-content three-line>
                            <v-list-item-title>
                                <strong>Имя заказа: {{order.name}}</strong>
                            </v-list-item-title>
                            <v-list-item-subtitle>
                                <strong>Статус: {{order.status}}</strong>
                            </v-list-item-subtitle>
                            <v-list-item-subtitle>
                                <strong>Сумма: {{order.sum}}</strong>
                            </v-list-item-subtitle>
                            <v-list-item-subtitle>
                                <div>
                                    <strong>Материалы: <span
                                            v-for="material of order.materials"> {{material.matTitle}}  </span></strong>
                                </div>
                            </v-list-item-subtitle>
                        </v-list-item-content>
                    </v-list-item>
                </li>
                <v-pagination
                        v-model="pagination.page"
                        :length="pagination.total"
                        total-visible=6
                        @input="getOrders"
                ></v-pagination>
            </ul>

            <div v-if="currentOrder">
                <div class="mt-2"></div>
                <label><strong>Текущий заказ:</strong></label> {{ currentOrder.name }}
            </div>
        </v-card>

        <v-content>

            <div class="mt-2">
                <v-btn to="/order/create">Разместить заказ</v-btn>

                <div class="mt-2"></div>
                <div v-if="currentStatus === 'DRAFT'">
                    <b-button variant="danger" @click="showModal">Удалить заказ</b-button>

                    <b-modal ref="my-modal" hide-footer title="Подтверждение">
                        <div class="d-block text-center">
                            <h3>Удалить заказ?</h3>
                        </div>
                        <b-button class="mt-3" variant="outline-danger" block @click="deleteOrder">Удалить</b-button>
                    </b-modal>
                    <div class="mt-2"></div>
                    <b-alert show dismissible fade>Вы не можете назначать исполнителя для шаблона.</b-alert>


                </div>
                <div v-else-if="currentOrder">
                    <div v-if="!currentExecutor">
                        <v-btn class="red--text" @click="selectExecutor">Выбрать исполнителя для заказа</v-btn>
                    </div>
                    <div v-if="currentExecutor">
                        <b-button v-b-modal.modal-1>Предложить заказ исполнителю</b-button>

                        <b-modal
                                id="modal-1"
                                title="Введите подробности заказа"
                                @ok="sendResponse"
                        >
                            <b-form-input type="text" placeholder="Сумма заказа" v-model="sum"/>
                            <div class="mt-2"></div>
                        </b-modal>
                    </div>
                    <div class="mt-2"></div>
                    <b-button variant="danger" @click="showModal">Удалить заказ</b-button>

                    <b-modal ref="my-modal" hide-footer title="Подтверждение">
                        <div class="d-block text-center">
                            <h3>Удалить заказ?</h3>
                        </div>
                        <b-button class="mt-3" variant="outline-danger" block @click="deleteOrder">Удалить</b-button>
                    </b-modal>
                </div>

            </div>

        </v-content>
    </v-container>

</template>

<script>
    import {AXIOS} from "../pages/http-common";

    export default {
        props: [],
        data() {
            return {
                orders: [],
                currentOrder: null,
                currentIndex: -1,
                currentStatus: null,
                accessToken: localStorage.getItem('accessToken'),
                currentExecutor: localStorage.getItem('currentExecutor'),
                currentExecutorName: localStorage.getItem('currentExecutorName'),
                pagination: {
                    page: 1,
                    total: 0,
                    perPage: 4
                }

            }
        },
        created: function () {
            this.getOrders();
        },
        methods: {
            getOrders() {
                AXIOS.get('/order/user?page=' + this.pagination.page + '&perPage=' + this.pagination.perPage).then((response) => {
                    this.orders = response.data.content;
                    this.pagination.total = response.data.pageCount;
                    console.log(response.data);
                }).catch(error => console.log(error));
            },
            showModal() {
                this.$refs['my-modal'].show()
            },
            setActiveOrder(order, index, status) {
                this.currentOrder = order;
                this.currentIndex = index;
                this.currentStatus = status
            },
            goToOrder(order) {
                var s = "/orders/" + order.id;
                this.$router.push(s)
            },
            selectExecutor() {
                localStorage.setItem('currentOrder', JSON.stringify(this.currentOrder));
                this.$router.push('/')
            },
            clearExecutor() {
                localStorage.removeItem('currentExecutor');
                location.reload();
            },
            sendResponse() {
                AXIOS.post("/chat", {
                    'executorId': this.currentExecutor,
                    'customerId': this.currentOrder.user.id
                }).then((response) => {
                    console.log(response);
                }).catch(error => console.log(error));

                AXIOS.post("/response", {
                    'orderId': this.currentOrder.id,
                    'executorId': localStorage.getItem('currentExecutor'),
                    'sum': this.sum
                }).then((response) => {
                    console.log(response);
                    localStorage.removeItem('currentExecutor');

                    this.$router.push('/chatList');
                    location.reload();
                }).catch(error => console.log(error));

            },
            deleteOrder() {
                AXIOS.delete('/order/' + this.currentOrder.id);
                location.reload()
            },
        }
    }
</script>

<style scoped>


</style>