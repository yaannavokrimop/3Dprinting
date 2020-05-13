<template>
    <v-container>
        <div class="create-order">
            <b-card
                    align="center"
                    title="Добавление заказа"
                    tag="article"
                    style="max-width: 20rem;"
                    class="mb-2"
            >
                <v-form ref="form" v-model="valid" lazy-validation>

                    <v-text-field outlined type="number" placeholder="Сумма заказа" v-model="sum" dense
                                  :rules="[v => v<=999999 && v>=0 || 'Нужно ввести число от 0 до 999999']"></v-text-field>

                    <v-text-field outlined type="text" placeholder="Название заказа" v-model="name" dense required
                                  :rules="NameRules"></v-text-field>

                    <v-text-field outlined type="text" placeholder="Описание" v-model="description" dense
                                  :rules="[v => v.length<=200 || 'Должно быть не больше 200 символов']"></v-text-field>

                    <v-text-field outlined type="number" placeholder="Высота изделия (в мм)" v-model="height" dense
                                  :rules="[v => v<=2000 && v>=0 || 'Нужно ввести число от 0 до 2000']"></v-text-field>

                    <v-text-field outlined type="number" placeholder="Ширина изделия (в мм)" v-model="width" dense
                                  :rules="[v => v<=2000 && v>=0 || 'Нужно ввести число от 0 до 2000']"></v-text-field>

                    <v-text-field outlined type="number" placeholder="Длина изделия (в мм)" v-model="length" dense
                                  :rules="[v => v<=2000 && v>=0 || 'Нужно ввести число от 0 до 2000']"></v-text-field>

                    <v-autocomplete
                            v-model="selectMaterial"
                            :items="items"
                            :search-input.sync="search"
                            cache-items
                            hide-no-data
                            outlined
                            hide-details
                            label="Материалы"
                            multiple
                            chips
                    ></v-autocomplete>
                    <v-file-input outlined label="Схема изделия" ref="file" v-model="file" show-size></v-file-input>
                    <div class="mt-2" align="center">
                        <v-btn v-on:click="addOrder" type="submit" variant="primary" :disabled="!valid">Добавить</v-btn>
                    </div>
                    <div class="mt-2" align="center">
                        <v-btn v-on:click="addDraft" type="submit" variant="primary" :disabled="!valid">Сохранить как
                            черновик
                        </v-btn>
                    </div>
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
                file: null,
                fileName: null,
                selectMaterial: null,
                items: [],
                search: null,
                accessToken: localStorage.getItem('accessToken'),
                valid: true,
                NameRules: [
                    v => !!v || 'Не должно быть пустым ',
                    v => v.length <= 50 || 'Должно быть не больше 50 символов'
                ],
            }
        },

        created: function () {
            this.querySelections();
        },
        methods: {
            addOrder() {
                if (this.$refs.form.validate()) {
                    let formData = new FormData();
                    if (this.file) {
                        formData.append("file", this.file);
                        this.fileName = this.file.name;
                    }
                    let newOrder = {
                        'sum': this.$data.sum,
                        'height': this.$data.height,
                        'width': this.$data.width,
                        'length': this.$data.length,
                        'name': this.$data.name,
                        'description': this.$data.description,
                        'materials': this.$data.selectMaterial,
                        'fileName': this.fileName
                    };

                    AXIOS.post('order', newOrder)
                        .then(response => {
                            console.log(response);
                            if (this.file) {
                                formData.append("orderId", response.data.id);
                                AXIOS.post('order/file', formData)
                                    .then(response => {
                                        console.log(response);
                                        this.successAlert();
                                        this.$router.push('/orders');
                                        location.reload();
                                    }).catch(error => console.log(error));
                            } else {
                                this.successAlert();
                                this.$router.push('/orders');
                                location.reload()
                            }
                        }).catch(error => console.log(error));
                }

            },
            addDraft() {
                if (this.$refs.form.validate()) {
                    let formData = new FormData();
                    if (this.file) {
                        formData.append("file", this.file);
                        this.fileName = this.file.name;
                    }
                    let newOrder = {
                        'sum': this.$data.sum,
                        'height': this.$data.height,
                        'width': this.$data.width,
                        'name': this.$data.name,
                        'length': this.$data.length,
                        'description': this.$data.description,
                        'materials': this.$data.selectMaterial,
                        'fileName': this.fileName
                    };

                    AXIOS.post('order/draft', newOrder)
                        .then(response => {
                            console.log(response);
                            if (this.file) {
                                formData.append("orderId", response.data.id);
                                AXIOS.post('order/draft/file', formData)
                                    .then(response => {
                                        console.log(response);
                                        this.successAlert();
                                        this.$router.push('/orders');
                                        location.reload()
                                    }).catch(error => console.log(error));
                            } else {
                                this.successAlert();
                                this.$router.push('/orders');
                                location.reload()
                            }
                        }).catch(error => console.log(error));
                }
            },
            successAlert() {
                this.sum = '';
                this.height = '';
                this.width = '';
                this.length = '';
                this.description = '';
                this.selectMaterial = null;
            },
            querySelections() {
                AXIOS.get('/material').then((response) => {
                    this.items = response.data;
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