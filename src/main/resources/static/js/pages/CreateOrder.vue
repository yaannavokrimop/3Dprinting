<template>
    <v-container>
        <div class="create-order">
            <b-card
                    title="Добавление заказа"
                    tag="article"
                    style="max-width: 20rem;"
                    class="mb-2"
            >
                <form class="needs-validation" novalidate >

                    <b-form-input type="number" placeholder="Сумма заказа" v-model="sum"  max="999999" min="0"/>
                    <div class="invalid-feedback">Нужно ввести число от 0 до 999999</div>
                    <div class="mt-2"></div>

                    <b-form-input type="text" placeholder="Название заказа" v-model="name"  required />
                    <div class="invalid-feedback">Не должно быть пустым</div>
                    <div class="mt-2"></div>

                    <b-form-input type="text" placeholder="Описание" v-model="description"/>
                    <div class="mt-2"></div>

                    <b-form-input type="number" placeholder="Высота изделия (в мм)" v-model="height" max="999999" min="0"/>
                    <div class="invalid-feedback">Нужно ввести число от 0 до 999999</div>
                    <div class="mt-2"></div>

                    <b-form-input type="number" placeholder="Ширина изделия (в мм)" v-model="width" max="999999" min="0"/>
                    <div class="invalid-feedback">Нужно ввести число от 0 до 999999</div>
                    <div class="mt-2"></div>

                    <b-form-input type="number" placeholder="Длина изделия (в мм)" v-model="length" max="999999" min="0"/>
                    <div class="invalid-feedback">Нужно ввести число от 0 до 999999</div>
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
                    <v-btn v-on:click="addOrder" type="submit" variant="primary">Добавить</v-btn>
                    <div class="mt-2"></div>
                    <v-btn v-on:click="addDraft" type="submit" variant="primary">Сохранить как черновик</v-btn>
                    <div class="mt-2"></div>
                </form>
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
            }
        },

        created:function(){
            this.querySelections();
        },
        methods: {
            addOrder() {
            var valid;
                var forms = document.getElementsByClassName('needs-validation');
                   var validation = Array.prototype.filter.call(forms, function(form) {
                      form.addEventListener('submit', function(event) {
                        if (form.checkValidity() === false) {
                          event.preventDefault();
                          event.stopPropagation();
                        }
                        form.classList.add('was-validated');
                      }, false);
                      valid=form.checkValidity();

                   });
                if(valid){
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
                    //this.$router.push('/orders');
                    //location.reload()
                }

            },
            addDraft() {
                var valid;
                var forms = document.getElementsByClassName('needs-validation');
                var validation = Array.prototype.filter.call(forms, function(form) {
                    form.addEventListener('submit', function(event) {
                        if (form.checkValidity() === false) {
                        event.preventDefault();
                        event.stopPropagation();
                        }
                    form.classList.add('was-validated');
                    }, false);
                    valid=form.checkValidity();
                });
                if(valid){

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