<template>
    <v-list>
        <v-list-item>
            <v-list-item-icon>
                <v-icon>mdi-clipboard-text-multiple-outline</v-icon>
            </v-list-item-icon>

            <v-list-item-title>В обсуждении</v-list-item-title>
        </v-list-item>
        <v-list-group
                v-for="response in responses"
                :key="response.order.name"
                v-model="response.active"
        >
            <template v-slot:activator>
                <v-list-item-content>
                    <v-list-item-title v-text="response.order.name"></v-list-item-title>
                </v-list-item-content>
            </template>
            <v-container>
                <v-list-item-content>
                    <div v-if="response.status ==='REQUESTED'">
                        <v-list-item-title v-text="'Новый заказ'"></v-list-item-title>
                    </div>
                    <div v-if="response.status ==='DISCUSSION'">
                        <v-list-item-title v-text="'Заказ в обсуждении'"></v-list-item-title>
                    </div>
                    <div v-if="!isExecutor">
                        <div v-if="response.status ==='BY_CUSTOMER'">
                            <v-list-item-title v-text="'Вы предложили цену'"></v-list-item-title>
                        </div>
                        <div v-if="response.status ==='BY_EXECUTOR'">
                            <v-list-item-title v-text="'Вам предложили цену'"></v-list-item-title>
                        </div>
                    </div>
                    <div v-if="isExecutor">
                        <div v-if="response.status ==='BY_CUSTOMER'">
                            <v-list-item-title v-text="'Вам предложили цену'"></v-list-item-title>
                        </div>
                        <div v-if="response.status ==='BY_EXECUTOR'">
                            <v-list-item-title v-text="'Вы предложили цену'"></v-list-item-title>
                        </div>
                    </div>
                    <div v-if="response.status ==='AGREED'">
                        <v-list-item-title v-text="'Вы договорились по '"></v-list-item-title>
                        <v-list-item-title v-text="'поводу этого заказа.'"></v-list-item-title>

                    </div>
                    <a :href="'#/orders/'+response.order.id">
                        <div class="details">
                            Подробности о заказе
                        </div>
                    </a>
                    <v-text-field v-model="response.sum" label="Сумма"></v-text-field>
                    <div v-if="response.status ==='AGREED'">
                    </div>
                    <div v-else>
                        <div v-if="!isExecutor">
                            <div v-if="response.status ==='BY_CUSTOMER'">
                                <b-button variant="secondary" disabled block>Вы уже предложили цену</b-button>
                            </div>
                            <div v-else>
                                <div v-if="response.status ==='BY_EXECUTOR'">
                                    <v-btn icon class="green--text accept" @click="$bvModal.show('acceptModal')">
                                        <v-icon>mdi-checkbox-marked-circle</v-icon>
                                    </v-btn>
                                    <b-modal id="acceptModal" hide-footer>
                                        <template v-slot:modal-title>
                                            Подтвердить
                                        </template>
                                        <div class="d-block text-center">
                                            <h3>После этого действия заказ будет согласован</h3>
                                        </div>
                                        <b-button variant="primary" class="mt-3" block @click="acceptModalHide($bvModal, response.id.executorId, response.id.orderId)">Подтвердить</b-button>
                                    </b-modal>
                                    <v-btn icon class="red--text decline" @click="discuss(response.id.executorId, response.id.orderId)">
                                        <v-icon>mdi-close-circle</v-icon>
                                    </v-btn>
                                </div>
                                <div v-else>
                                    <b-button variant="success" block @click="makeOffer(response.id.executorId, response.id.orderId, response.sum)">Предложить цену</b-button>
                                </div>
                            </div>
                        </div>
                        <div v-if="isExecutor">
                            <div v-if="response.status ==='BY_EXECUTOR'">
                                <b-button variant="secondary" disabled block>Вы уже предложили цену</b-button>
                            </div>
                            <div v-else>
                                <div v-if="response.status ==='BY_CUSTOMER'">
                                    <div>
                                        <v-btn icon class="green--text accept" @click="$bvModal.show('acceptModal')">
                                            <v-icon>mdi-checkbox-marked-circle</v-icon>
                                        </v-btn>
                                        <b-modal id="acceptModal" hide-footer>
                                            <template v-slot:modal-title>
                                                Подтвердить
                                            </template>
                                            <div class="d-block text-center">
                                                <h3>После этого действия заказ будет согласован</h3>
                                            </div>
                                            <b-button variant="primary" class="mt-3" block @click="acceptModalHide($bvModal, response.id.executorId, response.id.orderId)">Подтвердить</b-button>
                                        </b-modal>
                                        <v-btn icon class="red--text decline" @click="discuss(response.id.executorId, response.id.orderId)">
                                            <v-icon>mdi-close-circle</v-icon>
                                        </v-btn>
                                    </div>
                                </div>
                                <div v-else>
                                    <b-button variant="success" block @click="makeOffer(response.id.executorId, response.id.orderId, response.sum)">Предложить цену</b-button>
                                </div>
                            </div>
                        </div>

                        <b-button variant="danger" block id="show-btn" @click="$bvModal.show('refuse')">Отказаться от заказа</b-button>

                        <b-modal id="refuse" hide-footer>
                            <template v-slot:modal-title>
                                Подтвердить отказ
                            </template>
                            <div class="d-block text-center">
                                <h3>Вы не сможете обсуждать заказ с этим собеседником!</h3>
                            </div>
                            <b-button variant="danger" class="mt-3" block @click="modalHide($bvModal, response.id.executorId, response.id.orderId)">Подтвердить</b-button>
                        </b-modal>
                    </div>

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
                currentChat: JSON.parse(localStorage.getItem('currentChat')),
                isExecutor: false
            }
        },
        created() {
            AXIOS.get('/response/forchat/' + this.currentChat.chatId).then((response) => {
                this.responses = response.data;
                console.log(response.data);
            }).catch(error => console.log(error));
            this.isExecutor = this.currentChat.isExecutor
        },
        methods: {
            makeOffer(executorId, orderId, sum) {
                AXIOS.post('response/offer', {
                    'executorId' : executorId,
                    'orderId' : orderId,
                    'sum' : sum,
                    'isExecutor' : this.isExecutor
                }).then((response) => {
                    console.log(response);
                    location.reload()
                }).catch(error => console.log(error));
            },
            modalHide (bvModal, executorId, orderId) {
                bvModal.hide('refuse');
                this.refuseOffer(executorId, orderId);
            },
            acceptModalHide (bvModal, executorId, orderId) {
                bvModal.hide('acceptModal');
                this.accept(executorId, orderId);
            },
            refuseOffer(executorId, orderId) {
                AXIOS.patch('response/offer', {
                    'executorId' : executorId,
                    'orderId' : orderId
                }).then((response) => {
                    console.log(response);
                    location.reload()
                }).catch(error => console.log(error));
            },
            discuss(executorId, orderId) {
                AXIOS.patch('response/offer/discuss', {
                    'executorId' : executorId,
                    'orderId' : orderId
                }).then((response) => {
                    console.log(response);
                    location.reload()
                }).catch(error => console.log(error));
            },
            accept(executorId, orderId) {
                AXIOS.patch('response/offer/accept', {
                    'executorId' : executorId,
                    'orderId' : orderId
                }).then((response) => {
                    console.log(response);
                    location.reload()
                }).catch(error => console.log(error));
            }
        }
    }
</script>


<style>
    .details {
        color: #534e4e;
        text-decoration: none;
    }

    .accept {
        margin-left: 20%;
    }

    .decline {
        margin-left: 20%;
    }
</style>