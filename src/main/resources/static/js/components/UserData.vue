<template>
    <div>
    <v-layout row>
    <v-flex  xs12 sm9 offset-sm2>
            <v-card class="mx-auto" max-width="1150" tile>
                      <v-row>
                        <v-col class="pa-5">
                          <v-avatar class="profile" color="black" size="240" >
                             <span class="white--text headline">АЗ</span>
                          </v-avatar>
                          <v-list-item>
                           <v-list-item-content>
                            <v-list-item-title class="title">{{user.name }} {{user.surname}}</v-list-item-title>
                            <v-list-item-subtitle>{{user.role}}</v-list-item-subtitle>
                            </v-list-item-content>
                            </v-list-item>
        <v-list-item class="text pl-5 pt-5">
        <div class="display-1" >Ali Conners</div>

         <div> Исполнительница</div>
        </v-list-item>
                        </v-col>

                        <v-col class="py-0">
                    <div>
                        <h3> Email: {{user.email}}</h3>
                    </div>
                    <v-spacer></v-spacer>
                    <div>
                        <h3> Телефон: {{user.phone}}</h3>
                    </div>
                    <v-spacer></v-spacer>
                    <div>
                        <h3> {{user.information}}</h3>
                    </div>
                        </v-col>
                      </v-row>

                  </v-card>
                     </v-flex>
                      </v-layout>


        <v-flex xs12 sm6 md12>
            <v-card height="500px" max-width="1150px">
                <v-card-title class="text--primary" justify-center>
                    <h1>{{user.name }} {{user.surname}}</h1>
                </v-card-title>
                <v-card-title class="text--primary" justify-center>
                    <h5>{{user.role}}</h5>
                </v-card-title>
                <v-card-title class="text--primary" justify-center>
                    <h2>Контакты:</h2>
                </v-card-title>
                <v-spacer></v-spacer>
                <v-card-text>
                    <div>
                        <h3> Email: {{user.email}}</h3>
                    </div>
                    <v-spacer></v-spacer>
                    <div>
                        <h3> Телефон: {{user.phone}}</h3>
                    </div>
                    <v-spacer></v-spacer>
                    <div>
                        <h3> {{user.information}}</h3>
                    </div>
                </v-card-text>
                <v-spacer></v-spacer>

                <v-footer
                        absolute
                        class="font-weight-medium"
                >
                    <v-btn to="/profile_edit">Редактировать</v-btn>
                </v-footer>
            </v-card>
        </v-flex>

        <v-spacer></v-spacer>
        <v-container grid-list-md text-xs-center>
            <v-layout row wrap>
                <v-flex xs6 sm6 md6>
                    <v-card id="addrH">
                        <h3>Адреса</h3>
                        <ul>
                            <li v-for="address in user.addresses">
                                Город: {{ address.city}} Адрес: {{address.description}}
                            </li>
                        </ul>

                        <v-btn to="/address">Изменить</v-btn>
                    </v-card>


                </v-flex>
                <v-flex xs6 sm6 md6 v-show="!isCustomer">
                    <v-card>
                        <h3>Оборудование</h3>
                        <ul>
                            <li v-for="equip in equipments">
                                <div><strong>{{equip.equipName}} </strong></div>
                                <div>Размер печати: {{equip.height}} x {{equip.width}} x {{equip.length}}</div>
                                <div>Материалы: <span v-for="material of equip.materialList"> {{material}}  </span>
                                </div>
                            </li>
                        </ul>
                        <v-btn to="/equipment">Изменить</v-btn>
                    </v-card>
                </v-flex>
            </v-layout>
        </v-container>
    </div>
</template>

<script>
    import {AXIOS} from "../pages/http-common";

    export default {
        props: ['user'],
        data() {
            return {
                equipments: [],
                isCustomer: false,
            }
        },
        created: function () {
            AXIOS.get('/user').then((response) => {
                this.user.id = response.data.id;
                this.user.email = response.data.email;
                this.user.information = response.data.information;
                this.user.name = response.data.name;
                this.user.phone = response.data.phone;
                let role = response.data.role;
                if (role === 'EXECUTOR') this.user.role = 'Исполнитель';
                if (role === 'CUSTOMER') this.user.role = 'Заказчик';
                this.user.surname = response.data.surname;
                this.user.addresses = response.data.addresses;
            }).catch(error => console.log(error));
            this.checkRole();
            if (!(this.isCustomer)) {
                this.getEquipments();
            }
        },
        methods: {
            getEquipments() {
                AXIOS.get('/equipment/my').then((response) => {
                    this.equipments = response.data;
                }).catch(error => console.log(error));
            },
            checkRole() {
                AXIOS.get('/user/role').then((response) => {
                    this.$data.isCustomer = response.data;
                    console.log(response.data);
                });


            }
        }
    }
</script>

<style scoped>

</style>