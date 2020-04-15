<template>
    <div div="signin">
        <div class="login-form">
            <b-card
                    title="Вход"
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
                    <b-form-input type="email" placeholder="Email" v-model="email"/>
                    <div class="mt-2"></div>

                    <b-form-input type="password" placeholder="Пароль" v-model="password"/>
                    <div class="mt-2"></div>
                </div>

                <b-button v-on:click="login" variant="primary">Войти</b-button>

                <hr class="my-4"/>
                <div style=" font-size:85%">
                    Нет аккаунта?
                    <a href="#" @click="goToSignUpPage">
                        Зарегистрируйтесь
                    </a>
                </div>
            </b-card>
        </div>
    </div>
</template>

<script>
    import {AXIOS} from './http-common'

    export default {
        name: 'SignIn',
        data() {
            return {
                email: '',
                password: '',
                dismissSecs: 5,
                dismissCountDown: 0,
                alertMessage: 'Request error',
            }
        },
        methods: {
            login() {
                this.$store.commit('loginStart');
                AXIOS.post(`/auth/signin`, {'email': this.$data.email, 'password': this.$data.password})
                    .then(response => {
                        this.$store.commit('loginStart');
                        localStorage.setItem('accessToken', response.data.accessToken);
                        localStorage.setItem('authority', response.data.authority);
                        this.$store.commit('updateAccessToken', response.data.accessToken);
                        this.$store.commit('setUserAuthority', response.data.authority);
                        this.$router.push('/profile')
                    }, error => {
                        this.$data.alertMessage = (error.response.data.message.length < 150) ? error.response.data.message : 'Request error. Please, report this error website owners';
                        console.log(error)
                    })
                    .catch(e => {
                        this.$store.commit('updateAccessToken', null);
                        console.log(e);
                        this.showAlert();
                    })
            },
            countDownChanged(dismissCountDown) {
                this.dismissCountDown = dismissCountDown
            },
            showAlert() {
                this.dismissCountDown = this.dismissSecs
            },
            goToSignUpPage(event) {
                event.preventDefault();
                this.$router.push("/signup")
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




