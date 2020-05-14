<template>
    <div div="signup">
        <div class="login-form">
            <b-card
                    title="Регистрация"
                    tag="article"
                    style="max-width: 20rem;"
                    class="mb-2"
                    align="center"
            >
                <div>
                    <b-alert
                            :show="dismissCountDown"
                            dismissible
                            variant="danger"
                            @dismissed="dismissCountDown=0"
                            @dismiss-count-down="countDownChanged"
                    > {{ alertMessage }}
                    </b-alert>
                </div>

                <div>

                    <b-form-input type="text" placeholder="Имя" v-model="name" />
                    <div class="mt-2"></div>

                    <b-form-input type="text" placeholder="Фамилия" v-model="surname" />
                    <div class="mt-2"></div>

                    <b-form-input type="text" placeholder="Email" v-model="email" />
                    <div class="mt-2"></div>

                    <b-form-input type="password" placeholder="Пароль" v-model="password" />
                    <div class="mt-2"></div>

                    <b-form-input type="text" placeholder="Номер телефона" v-model="phone" />
                    <div class="mt-2"></div>

                    <b-form-input type="text" placeholder="О себе" v-model="information" />
                    <div class="mt-2"></div>

                    <b-form-group label="Выберите роль:" align="left">
                        <b-form-radio v-model="role" name="some-radios" value="CUSTOMER">Заказчик</b-form-radio>
                        <b-form-radio v-model="role" name="some-radios" value="EXECUTOR">Исполнитель</b-form-radio>
                    </b-form-group>

                    <v-autocomplete
                     v-model="cityTitle"
                     :items="items"
                     :search-input.sync="search"
                     cache-items
                     hide-no-data
                     hide-details
                     label="Город"
                     chips
                    ></v-autocomplete>
                    <div class="mt-2"></div>

                    <b-form-input type="text" placeholder="Адрес" v-model="description" />
                    <div class="mt-2"></div>
                </div>

                <b-button v-on:click="register" variant="primary">Зарегистрироваться</b-button>

            </b-card>
        </div>
    </div>
</template>

<script>
    import {AXIOS} from './http-common'

    export default {
        name: 'SignUp',
        data () {
            return {
                email: '',
                name: '',
                surname: '',
                password: '',
                role: '',
                phone: '',
                information: '',
                dismissSecs: 5,
                dismissCountDown: 0,
                alertMessage: '',
                cityTitle:null,
                description:'',
                items: [],
                search: null

            }
        },
        watch: {
            search (val) {
                val && val !== this.cityTitle && this.querySelections(val)
            },
         },
        methods: {
            querySelections (cityPartName) {
                setTimeout(()=>{
                if(cityPartName===this.$data.search){
                AXIOS.get('/search/cityList/'+cityPartName).then((response) =>{

                    this.items=response.data;
                }).catch(error => console.log(error));
                }},1200)
            },

            register: function () {
                if (this.$data.name === '' || this.$data.name == null) {
                    this.$data.alertMessage = 'Пожалуйста, укажите своё имя';
                    this.showAlert();
                } else if (this.$data.surname === '' || this.$data.surname == null) {
                    this.$data.alertMessage = 'Пожалуйста, укажите свою фамилию';
                    this.showAlert();
                } else if (this.$data.email === '' || this.$data.email == null) {
                    this.$data.alertMessage = 'Пожалуйста, укажите электронную почту';
                    this.showAlert();
                } else if (!this.$data.email.includes('@')) {
                    this.$data.alertMessage = 'Проверьте правильность электронной почты';
                    this.showAlert();
                } else if (this.$data.password === '' || this.$data.password == null) {
                    this.$data.alertMessage = 'Пожалуйста, укажите пароль';
                    this.showAlert();
                }else if (this.$data.role == null || this.$data.role === '') {
                    this.$data.alertMessage = 'Пожалуйста, выберите роль';
                    this.showAlert();
                }
                else {
                    AXIOS.post('/auth/signup', {
                        'name': this.$data.name,
                        'surname': this.$data.surname,
                        'email': this.$data.email,
                        'password': this.$data.password,
                        'phone': this.$data.phone,
                        'role': this.$data.role,
                        'information': this.$data.information,
                        'cityTitle':this.$data.cityTitle,
                        'description':this.$data.description
                    })
                        .then(response => {
                            console.log(response);
                            this.$router.push({name: 'SignIn', params: {successfullyRegistered: true}});
                        }, error => {
                            if (error.response.data.message.includes('Validation failed')) {
                                this.$data.alertMessage = 'Проверьте корректность данных';
                                this.showAlert();
                            } else {
                                this.$data.alertMessage = error.response.data.message;
                                this.showAlert();
                            }
                            this.showAlert();
                        })
                        .catch(error => {
                            console.log(error);
                            this.$data.alertMessage = 'Ошибка';
                            this.showAlert();
                        });
                }
            },
            countDownChanged(dismissCountDown) {
                this.dismissCountDown = dismissCountDown
            },
            showAlert() {
                this.dismissCountDown = this.dismissSecs
            }
        }
    }
</script>

<style>
    .login-form {
        margin-left: 38%;
        margin-top: 50px;
    }
</style>