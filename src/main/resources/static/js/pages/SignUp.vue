<template>
    <div div="signup">
        <div class="login-form">
            <b-card
                    title="Регистрация"
                    tag="article"
                    style="max-width: 20rem;"
                    class="mb-2"
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
                    <b-alert variant="success" :show="successfullyRegistered">
                        Поздравляем, Вы зарегистрировались! Можете авторизоваться, используя свои данные.
                        <hr/>
                        <router-link to="/signin">
                            <b-button variant="primary">Вход</b-button>
                        </router-link>
                    </b-alert>
                </div>
                <div>
<!--                    <b-form-input type="text" placeholder="Username" v-model="username" />-->
<!--                    <div class="mt-2"></div>-->

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

                    <b-form-group label="Выберите роль">
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


                    <!--<b-form-input type="password" placeholder="Confirm Password" v-model="confirmpassword" />
                    <div class="mt-2"></div>-->
                </div>

                <b-button v-on:click="register" variant="primary">Зарегистрироваться</b-button>

            </b-card>
        </div>
    </div>
</template>

<script>
    import {AXIOS} from '../pages/http-common'

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
                successfullyRegistered: false,
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
                if(cityPartName==this.$data.search){
                AXIOS.get('/search/cityList/'+cityPartName).then((response) =>{

                    this.items=response.data;
                }).catch(error => console.log(error));
                }},1200)
            },

            register: function () {
                if (this.$data.name === '' || this.$data.name == null) {
                    this.$data.alertMessage = 'Please, fill "Username" field';
                    this.showAlert();}
                // } else if (this.$data.firstname === '' || this.$data.firstname == null) {
                //     this.$data.alertMessage = 'Please, fill "First name" field';
                //     this.showAlert();
                // } else if (this.$data.lastname === '' || this.$data.lastname == null) {
                //     this.$data.alertMessage = 'Please, fill "Last name" field';
                //     this.showAlert();
                // } else if (this.$data.email === '' || this.$data.email == null) {
                //     this.$data.alertMessage = 'Please, fill "Email" field';
                //     this.showAlert();
                // } else if (!this.$data.email.includes('@')) {
                //     this.$data.alertMessage = 'Email is incorrect';
                //     this.showAlert();
                // } else if (this.$data.password === '' || this.$data.password == null) {
                //     this.$data.alertMessage = 'Please, fill "Password" field';
                //     this.showAlert();
                // } else if (this.$data.confirmpassword === '' || this.$data.confirmpassword == null) {
                //     this.$data.alertMessage = 'Please, confirm password';
                //     this.showAlert();
                // } else if (this.$data.confirmpassword !== this.$data.password) {
                //     this.$data.alertMessage = 'Passwords are not match';
                //     this.showAlert();
                // }
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
                            this.successAlert();
                        }, error => {
                            this.$data.alertMessage = (error.response.data.message.length < 150) ? error.response.data.message : 'Request error. Please, report this error website owners'
                            this.showAlert();
                        })
                        .catch(error => {
                            console.log(error);
                            this.$data.alertMessage = 'Request error. Please, report this error website owners';
                            this.showAlert();
                        });
                }
            },
            countDownChanged(dismissCountDown) {
                this.dismissCountDown = dismissCountDown
            },
            showAlert() {
                this.dismissCountDown = this.dismissSecs
            },
            successAlert() {
                this.name = '';
                this.surname = '';
                this.email = '';
                this.password = '';
                this.role = '';
                this.phone = '';
                this.information = '';
                this.description='';
                this.cityTitle='';
                this.successfullyRegistered = true;
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