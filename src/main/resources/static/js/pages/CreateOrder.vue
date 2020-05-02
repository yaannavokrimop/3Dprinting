<template>
    <v-container>
        <div class="create-order">
            <b-card
                    title="Добавление заказа"
                    tag="article"
                    style="max-width: 20rem;"
                    class="mb-2"
            >


                <b-form-input type="text" placeholder="Сумма заказа" v-model="sum"/>
                <div class="mt-2"></div>

                <b-form-input type="text" placeholder="Описание" v-model="description"/>
                <div class="mt-2"></div>

                <b-form-input type="text" placeholder="Высота изделия" v-model="height"/>
                <div class="mt-2"></div>

                <b-form-input type="text" placeholder="Ширина изделия" v-model="width"/>
                <div class="mt-2"></div>

                <b-form-input type="text" placeholder="Длина изделия" v-model="length"/>
                <div class="mt-2"></div>
                <v-autocomplete
                    v-model="selectMaterial"
                    :items="items"
                    :search-input.sync="search"
                    cache-items
                    hide-no-data
                    hide-details
                    label="Материалы"
                    multiple
                    chips
                ></v-autocomplete>
                <div class="mt-2"></div>
                <v-btn v-on:click="addOrder" variant="primary">Добавить</v-btn>
                <div class="mt-2"></div>
                <v-btn v-on:click="addDraft" variant="primary">Сохранить как черновик</v-btn>
                <div class="mt-2"></div>

            </b-card>
        </div>
    </v-container>
</template>

<script>
    import {AXIOS} from "../pages/http-common";

    export default {
        name: "CreateOrder",
        data() {
            return {
                sum: '',
                height: '',
                width: '',
                length: '',
                description: '',
                selectMaterial: null,
                items: [],
                search: null,

                accessToken: localStorage.getItem('accessToken')
            }
        },
        created:function(){
            this.querySelections();
        },
        methods: {
            addOrder() {
                let newOrder = {
                    'sum': this.$data.sum,
                    'height': this.$data.height,
                    'width': this.$data.width,
                    'length': this.$data.length,
                    'description': this.$data.description,
                    'material': this.$data.selectMaterial,

                };

                AXIOS.post('order', newOrder)
                    .then(response => {
                        console.log(response);
                        this.successAlert();
                    }).catch(error => console.log(error));
                this.$router.push('/orders');
                location.reload()
            },
            addDraft() {
                let newOrder = {
                    'sum': this.$data.sum,
                    'height': this.$data.height,
                    'width': this.$data.width,
                    'length': this.$data.length,
                    'description': this.$data.description,
                    'material': this.$data.selectMaterial,

                };

                AXIOS.post('order/draft', newOrder)
                    .then(response => {
                        console.log(response);
                        this.successAlert();
                    }).catch(error => console.log(error));

                this.$router.push('/orders');
                location.reload()

            },
            successAlert() {
                this.sum = '';
                this.height = '';
                this.width = '';
                this.length = '';
                this.description = '';
                this.selectMaterial=null;
            },
            querySelections () {
            AXIOS.get('/material').then((response) =>{
                this.items=response.data;
            }).catch(error => console.log(error));
            },
        }
    }
</script>

<style scoped>

    .create-order {
        margin-left: 38%;
        margin-top: 50px;
    }

</style>