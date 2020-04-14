<template>

    <v-container>
        <v-content>
            <div class="col-md-8">
                <div class="input-group mb-3">
                    <input type="text" class="form-control" placeholder="Search by address" v-model="city"/>
                    <div class="input-group-append">
                        <button class="btn btn-outline-secondary" type="button"
                                @click="searchByAddress"
                        >
                            Search
                        </button>
                        <button class="btn btn-outline-secondary" type="button"
                                @click="showAll"
                        >
                            Показать Всех
                        </button>
                    </div>
                </div>
            </div>

        </v-content>
        <div v-if="currentOrder">
            <b-alert show dismissible fade>
                Вы выбираете заказчика для заказа № {{currentOrder}}
                <b-button class="mt-3" variant="outline-danger" block @click="clearOrder">
                    Очистить
                </b-button>
            </b-alert>

            <b-alert
                    variant="danger"
                    dismissible
                    fade
                    :show="showDismissibleAlert"
                    @dismissed="showDismissibleAlert=false"
            >
                Dismissible Alert!
            </b-alert>

            <b-alert
                    :show="dismissCountDown"
                    dismissible
                    fade
                    variant="warning">
                <!--                        @dismiss-count-down="countDownChanged"-->

                This alert will dismiss after {{ dismissCountDown }} seconds...
            </b-alert>

            <!--                <b-button @click="showAlert" variant="info" class="m-1">-->
            <!--                    Show alert with count-down timer-->
            <!--                </b-button>-->
            <!--<b-button @click="showDismissibleAlert=true" variant="info" class="m-1">
                Show dismissible alert ({{ showDismissibleAlert ? 'visible' : 'hidden' }})
            </b-button>-->
        </div>
        <div class="mt-2"></div>
        <v-content>
            <h3>Исполнители</h3>
            <ul class="list-group">
                <li class="list-group-item"
                    v-for="(executor,index) in executorsFilter"
                    :class="{ active: index == currentIndex }"
                    :key="index"
                    @click="setActiveExecutor(executor, index)"
                    @dblclick="goToProfile(executor)"
                >
                    <v-list-item three-line>
                        <v-list-item-content>
                            <v-list-item-title><strong> {{executor.name+' '+executor.surname}}</strong>
                            </v-list-item-title>
                            <v-list-item-subtitle>
                                <strong>Адреса:
                                    <ul class="demo">
                                        <li v-for="address of executor.addresses">
                                            {{ address.city }}, {{address.description}}
                                        </li>
                                    </ul>
                                </strong>
                            </v-list-item-subtitle>
                        </v-list-item-content>
                    </v-list-item>
                </li>
            </ul>

        </v-content>
        <v-content>
            <div v-if="currentExecutor">
                <div class="mt-2"></div>
                <label><strong>Current executor:</strong></label> {{ currentExecutor.name }}
                <div class="mt-2"></div>

               <div v-if="currentOrder">
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
                <div v-else>
                    <b-button variant="light" @click="selectOrder">Предложить заказ исполнителю</b-button>
                </div>
            </div>
            <div class="mt-2"></div>

        </v-content>


    </v-container>
</template>

<script>
    import {AXIOS} from "../pages/http-common";
    import StackModal from '@innologica/vue-stackable-modal';

    export default {
        props: [],
        components: {StackModal},
        data() {
            return {
                executors: [],
                executorsFilter: [],
                currentExecutor: null,
                currentIndex: -1,
                city: '',
                currentOrder: localStorage.getItem("currentOrder")
            }
        },
        created: function () {
            AXIOS.get('/user/executors').then((responce) => {
                this.executors = responce.data;
                this.executorsFilter = this.executors;
                console.log("Данные проверка2");
                console.log(responce.data);
            }).catch(error => console.log(error));


        },

        methods: {
            setActiveExecutor(executor, index) {
                this.currentExecutor = executor;
                this.currentIndex = index;
            },
            goToProfile(executor) {
                console.log('пока не реализовано');
            },
            searchByAddress() {
                var w = this.$data.city;
                console.log("search by address start.............." + w)
                this.executorsFilter = this.executors.filter(function (executor) {
                    if (executor.addresses.find(item => item.city === w) != null) {
                        return executor;
                    }

                });

            },
            showAll() {
                this.executorsFilter = this.$data.executors;
            },
            clearOrder() {
                localStorage.removeItem('currentOrder');
                location.reload()
            },
            sendResponse() {

                AXIOS.post("/response", {
                    'executorId': this.currentExecutor.id,
                    'orderId': localStorage.getItem('currentOrder'),
                    'sum': this.sum
                }).then((response) => {
                    console.log(response);
                    localStorage.removeItem('currentOrder');
                    this.$router.push('/orders');
                }).catch(error => console.log(error));

            },
            selectOrder() {
                localStorage.setItem('currentExecutor', this.currentExecutor.id);
                this.$router.push('/orders');
            }
        }

    }
</script>

<style scoped>
    .list {
        text-align: left;
        max-width: 900px;
        margin: auto;
    }

</style>