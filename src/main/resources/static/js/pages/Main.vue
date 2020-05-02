<template>
    <v-container>
        <v-content>
            <div class="col-md-8">
                <div class="input-group mb-3">
                                    <v-autocomplete
                                          v-model="selectCity"
                                          :loading="loading"
                                          :items="items"
                                          :search-input.sync="search"
                                          cache-items
                                          hide-no-data
                                          hide-details
                                          label="Название города"
                                          multiple
                                          chips
                                        ></v-autocomplete>
                    <div class="input-group-append">
                    </div>
                </div>
            </div>

        </v-content>
        <v-content>
            <div class="col-md-8">
                <div class="input-group mb-3">
                    <input type="text" class="form-control" placeholder="Высота" v-model="height"/>
                    <input type="text" class="form-control" placeholder="Ширина" v-model="width"/>
                    <input type="text" class="form-control" placeholder="Длина" v-model="length"/>
                    <div class="input-group-append">
                    <v-autocomplete
                        v-model="materialName"
                        :items="materialItems"
                        deletable-chips
                        dense
                        outlined
                        hide-no-data
                        hide-details
                        label="Материалы"
                        multiple
                        chips
                        small-chips
                        append
                    ></v-autocomplete>

                        <button class="btn btn-outline-secondary" type="button" @click="getData"> Найти</button>
                        <button class="btn btn-outline-secondary" type="button" @click="showAll"> Показать Всех </button>
                    </div>
                </div>
            </div>

        </v-content>
        <div v-if="currentOrder">
            <b-alert show dismissible fade>
                Вы выбираете исполнителя для заказа "{{currentOrder.name}}"  <br>
                Ширина {{currentOrder.width}}, длина {{currentOrder.length}}, высота {{currentOrder.height}}
                <b-button class="mt-3" variant="outline-danger" block @click="clearOrder">
                    Очистить
                </b-button>
            </b-alert>

        </div>
        <div class="mt-2"></div>
        <v-content>
            <h3>Исполнители</h3>
            <ul class="list-group">
                <li class="list-group-item"
                    v-for="(executor,index) in executors"
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
                <label><strong>Текущий исполнитель:</strong></label> {{ currentExecutor.name }}
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
        <v-pagination
                v-model="pagination.page"
                :length="pagination.total"
                total-visible=6
                @input="getData"
        ></v-pagination>
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
                materialName:null,
                materialItems:[],

                pagination: {
                    page: 1,
                    total: 0,
                    perPage: 4
                }
            }
        },
        created: function () {
            if (this.$data.currentOrder != null) {
                AXIOS.get('/address/user/city').then((response) => {
                    this.$data.items = response.data;
                    this.$data.selectCity = response.data;
                }).catch(error => console.log(error));
                this.$data.height = this.$data.currentOrder.height;
                this.$data.width = this.$data.currentOrder.width;
                this.$data.length = this.$data.currentOrder.length;
            }
            this.getData();
            this. materialSelections();
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
                    'height' : this.$data.height,
                    'width' : this.$data.width,
                    'length' : this.$data.length,
                    'currentPage' : this.pagination.page,
                    'perPage' : this.pagination.perPage
                }
            },
            getData() {
                AXIOS.post('/search/executors', this.getParams()).then((response) => {
                    this.pagination.total = response.data.pageCount;
                    this.executors = response.data.content;
                    console.log("Данные проверка2");
                    console.log(response.data);
                }).catch(error => console.log(error));
            },
            setActiveExecutor(executor, index) {
                this.currentExecutor = executor;
                this.currentIndex = index;
            },
            goToProfile(executor) {
                console.log('пока не реализовано');
            },
            showAll() {
                this.$data.selectCity = null;
                this.$data.items = [],
                this.$data.height = null;
                this.$data.width = null;
                this.$data.length = null;
                this.pagination.page = 1;
                this.getData();
            },
            clearOrder() {
                localStorage.removeItem('currentOrder');
                location.reload()
            },
            sendResponse() {
                AXIOS.post("/chat", {
                    'executorId': this.currentExecutor.id,
                    'customerId': this.currentOrder.user.id
                }).then((response) => {
                    console.log(response);
                }).catch(error => console.log(error));

                AXIOS.post("/response", {
                    'executorId': this.currentExecutor.id,
                    'orderId': this.currentOrder.id,
                    'sum': this.sum
                }).then((response) => {
                    console.log(response);
                }).catch(error => console.log(error));

                localStorage.removeItem('currentOrder');
                this.$router.push('/orders');

            },
            selectOrder() {
                localStorage.setItem('currentExecutor', this.currentExecutor.id);
                this.$router.push('/orders');
            },

                  querySelections (cityPartName) {

                    setTimeout(()=>{
                    if(cityPartName==this.$data.search){
                    this.loading = true
                      AXIOS.get('/search/cityList/'+cityPartName).then((response) =>{

                            this.items=response.data;
                                    }).catch(error => console.log(error));
                      this.loading = false

                      }},1500)

                  },
                  materialSelections(){
                    AXIOS.get('/material').then((response) =>{
                        this.materialItems=response.data;
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

</style>