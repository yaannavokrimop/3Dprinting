<template>
    <v-container>
        <div class="create-order">
            <b-card
                    title="Добавление заказа"
                    tag="article"
                    style="max-width: 20rem;"
                    class="mb-2"
            >
                <v-form ref="form" v-model="valid" lazy-validation>

                    <v-text-field outlined type="number" placeholder="Сумма заказа" v-model="sum" dense  :rules="[v => v<=999999 && v>=0 || 'Нужно ввести число от 0 до 999999']"></v-text-field>

                    <v-text-field outlined type="text" placeholder="Название заказа" v-model="name" dense required :rules="NameRules" ></v-text-field>

                    <v-text-field outlined type="text" placeholder="Описание" v-model="description" dense :rules="[v => v.length<=200 || 'Должно быть не больше 200 символов']"></v-text-field>

                    <v-text-field  outlined type="number" placeholder="Высота изделия (в мм)" v-model="height" dense :rules="[v => v<=2000 && v>=0 || 'Нужно ввести число от 0 до 2000']"></v-text-field>

                    <v-text-field outlined type="number" placeholder="Ширина изделия (в мм)" v-model="width" dense :rules="[v => v<=2000 && v>=0 || 'Нужно ввести число от 0 до 2000']"></v-text-field>

                    <v-text-field outlined type="number" placeholder="Длина изделия (в мм)" v-model="length" dense :rules="[v => v<=2000 && v>=0 || 'Нужно ввести число от 0 до 2000']"></v-text-field>

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
                    <v-btn v-on:click="addOrder" type="submit" variant="primary" :disabled="!valid">Добавить</v-btn>
                    <div class="mt-2"></div>
                    <v-btn v-on:click="addDraft" type="submit" variant="primary" :disabled="!valid">Сохранить как черновик</v-btn>
                    <div class="mt-2"></div>
                </v-form>
            </b-card>
        </div>
    </v-container>
</template>

<script>
    import {AXIOS} from "./http-common";

    export default {
        name: "CreateOrder",
        data() {
            return {
                sum: '',
                height: '',
                width: '',
                length: '',
                name: '',
                description: '',
                selectMaterial: null,
                items: [],
                search: null,
                accessToken: localStorage.getItem('accessToken'),
                valid:true,
                NameRules: [
                            v => !!v || 'Не должно быть пустым ',
                            v => v.length<=50 || 'Должно быть не больше 50 символов'
                        ],
            }
        },

        created:function(){
            this.querySelections();
        },
        methods: {
            addOrder() {
                if(this.$refs.form.validate()){
                    let newOrder = {
                        'sum': this.$data.sum,
                        'height': this.$data.height,
                        'width': this.$data.width,
                        'length': this.$data.length,
                        'name': this.$data.name,
                        'description': this.$data.description,
                        'materials': this.$data.selectMaterial,
                    };

                    AXIOS.post('order', newOrder)
                        .then(response => {
                            console.log(response);
                            this.successAlert();
                        }).catch(error => console.log(error));
                    this.$router.push('/orders');
                        location.reload()
                }

            },
            addDraft() {
                if(this.$refs.form.validate()){

                    let newOrder = {
                        'sum': this.$data.sum,
                        'height': this.$data.height,
                        'width': this.$data.width,
                        'name': this.$data.name,
                        'length': this.$data.length,
                        'description': this.$data.description,
                        'materials': this.$data.selectMaterial,

                    };

                    AXIOS.post('order/draft', newOrder)
                        .then(response => {
                            console.log(response);
                            this.successAlert();
                        }).catch(error => console.log(error));

                    this.$router.push('/orders');
                    location.reload()
                }
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