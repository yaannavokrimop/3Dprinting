<template>
    <v-container>

        <v-form>
            <v-container>
                <v-row align="center"
                       justify="center">
                    <v-col cols="12" sm="6">
                        <v-autocomplete
                                v-model="selectCity"
                                :loading="loading"
                                :items="items"
                                :search-input.sync="search"
                                cache-items
                                outlined
                                hide-no-data
                                hide-details
                                label="Название города"
                                multiple
                                chips
                        ></v-autocomplete>
                    </v-col>
                    <v-col cols="12" sm="6">
                        <v-autocomplete
                                v-model="materialName"
                                :items="materialItems"
                                deletable-chips
                                cache-items
                                outlined
                                hide-no-data
                                hide-details
                                label="Материалы"
                                multiple
                                chips
                                append
                        ></v-autocomplete>
                    </v-col>
                    <v-col cols="12" sm="4">
                        <v-text-field label="Высота" v-model="height" outlined></v-text-field>
                    </v-col>
                    <v-col cols="12" sm="4">
                        <v-text-field label="Ширина" v-model="width" outlined></v-text-field>
                    </v-col>
                    <v-col cols="12" sm="4">
                        <v-text-field label="Длина" v-model="length" outlined></v-text-field>
                    </v-col>
                    <v-btn class="ma-2" outlined color="primary" @click="findData">
                        <v-icon left>mdi-magnify</v-icon>
                        Найти
                    </v-btn>
                    <v-btn class="ma-2" outlined color="red" @click="showAll">
                        Показать Всех
                    </v-btn>
                </v-row>
            </v-container>
        </v-form>

        <div v-if="currentOrder">
            <b-alert show dismissible fade>
                Вы выбираете исполнителя для заказа "{{currentOrder.name}}" <br>
<!--                Ширина {{currentOrder.width}}, длина {{currentOrder.length}}, высота {{currentOrder.height}}-->
                <b-button class="mt-3" variant="outline-danger"  @click="clearOrder">
                    Очистить
                </b-button>
            </b-alert>

        </div>
        <div class="mt-2"></div>
        <v-content>
            <h3 align="center">Исполнители</h3>
            <ul class="list-group">
                <li class="list-group-item"
                    v-for="(executor,index) in executors"
                    :class="{ active: index === currentIndex }"
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
                                    <span v-for="address of executor.addresses">({{ address.city }}, {{address.description}})  </span>
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
                <label><strong>Текущий исполнитель:</strong></label> {{ currentExecutor.name }}
                <div class="mt-2"></div>

                <div v-if="currentOrder">
                    <b-button v-b-modal.modal-1>Предложить заказ исполнителю</b-button>

                    <b-modal
                            id="modal-1"
                            title="Введите подробности заказа"
                            @ok="sendResponse"
                    >
                        <h5>Стоимость заказа:</h5>
                        <b-form-input type="text" placeholder="Сумма заказа" v-model="currentOrder.sum"/>
                        <div class="mt-2"></div>
                    </b-modal>
                </div>
                <div v-else>
                    <b-button variant="light" @click="selectOrder">Предложить заказ исполнителю</b-button>
                </div>
            </div>
            <div class="mt-2"></div>
        </v-content>
        <div v-if="pagination.need">
            <v-pagination
                    v-model="pagination.page"
                    :length="pagination.total"
                    total-visible=6
                    @input="getData"
            ></v-pagination>
        </div>
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
                currentExecutor: null,
                currentIndex: -1,
                height: null,
                width: null,
                length: null,
                currentOrder: JSON.parse(localStorage.getItem("currentOrder")),
                loading: false,
                items: [],
                search: null,
                selectCity: null,
                materialName: null,
                materialItems: [],

                pagination: {
                    page: 1,
                    total: 0,
                    perPage: 4,
                    need: false
                },
                mymodel: false
            }
        },
        created: function () {
            if (this.$data.currentOrder != null) {
                AXIOS.get('/address/user/city').then((response) => {
                    this.$data.items = response.data;
                    this.$data.selectCity = response.data;
                    this.$data.height = this.$data.currentOrder.height;
                    this.$data.width = this.$data.currentOrder.width;
                    this.$data.length = this.$data.currentOrder.length;
                    this.$data.materialName = this.$data.currentOrder.materials;
                    this.getData();
                }).catch(error => console.log(error));

            } else {
                this.getData();
            }
            this.materialSelections();
        },
        watch: {
            search(val) {
                val && val !== this.selectCity && this.querySelections(val)
            },
        },
        methods: {
            getParams() {
                return {
                    'cities': this.$data.selectCity,
                    'height': this.$data.height,
                    'width': this.$data.width,
                    'length': this.$data.length,
                    'materials': this.$data.materialName,
                    'currentPage': this.pagination.page,
                    'perPage': this.pagination.perPage
                }
            },
            getData() {
                AXIOS.post('/search/executors', this.getParams()).then((response) => {
                    this.pagination.total = response.data.pageCount;
                    this.executors = response.data.content;
                    if (this.pagination.total > 1) this.pagination.need = true;
                    console.log("Данные проверка2");
                    console.log(response.data);
                }).catch(error => console.log(error));
            },
            setActiveExecutor(executor, index) {
                this.currentExecutor = executor;
                this.currentIndex = index;
            },
            goToProfile(executor) {
             var s = "/profile/" +  this.$data.currentExecutor.id;
              this.$router.push(s)
            },
            showAll() {
                this.$data.selectCity = null;
                this.$data.items = [];
                this.$data.height = null;
                this.$data.width = null;
                this.$data.length = null;
                this.$data.materialName = null;
                this.pagination.page = 1;
                this.pagination.need = false;
                this.getData();
            },
            findData() {
                this.pagination.page = 1;
                this.pagination.need = false;
                this.getData();
            },
            clearOrder() {
                localStorage.removeItem('currentOrder');
                location.reload()
            },
            sendResponse() {
                AXIOS.post("/chat", {
                    'executorId': this.currentExecutor.id,
                    'customerId': this.currentOrder.customerId
                }).then((response) => {
                    console.log(response);
                }).catch(error => console.log(error));

                AXIOS.post("/response", {
                    'executorId': this.currentExecutor.id,
                    'orderId': this.currentOrder.id,
                    'sum': this.currentOrder.sum
                }).then((response) => {
                    console.log(response);
                }).catch(error => console.log(error));

                localStorage.removeItem('currentOrder');

                this.$router.push('/chatList');
                location.reload();
            },
            selectOrder() {
                localStorage.setItem('currentExecutor', this.currentExecutor.id);
                localStorage.setItem('currentExecutorName', this.currentExecutor.name + " "+ this.currentExecutor.surname);
                this.$router.push('/orders');
            },

            querySelections(cityPartName) {

                setTimeout(() => {
                    if (cityPartName == this.$data.search) {
                        this.loading = true
                        AXIOS.get('/search/cityList/' + cityPartName).then((response) => {

                            this.items = response.data;
                        }).catch(error => console.log(error));
                        this.loading = false

                    }
                }, 1500)

            },
            materialSelections() {
                AXIOS.get('/material').then((response) => {
                    this.materialItems = response.data;
                }).catch(error => console.log(error));
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

    .list-group-item{
        width: 1052px;
        margin-bottom: 12px;
        border-radius: 16px;
    }

    .list-group-item:first-child {
        border-top-left-radius: 16px;
        border-top-right-radius: 16px;
    }

    .v-list-item__title {
        font-size: 1.3rem;
    }

    .v-list-item__subtitle {
        font-size: .9rem;
    }

    .list-group-item:last-child {
        border-bottom-right-radius: 1.25rem;
        border-bottom-left-radius: 1.25rem;
    }

</style>