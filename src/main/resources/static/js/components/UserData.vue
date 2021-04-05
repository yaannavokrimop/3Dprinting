<template>
    <div>

        <v-layout row>
        <v-flex xs12 sm6 md12>

                <v-card class="mx-auto" max-width="1109" tile>
                                                            <v-header
                                                                    absolute
                                                                    class="font-weight-medium"
                                                            >
                                                                <v-btn to="/profile_edit">Редактировать</v-btn>
                                                            </v-header>
                          <v-row>

                            <v-col class="pa-5">
                              <v-avatar class="profile" color="black" size="130" >
                                 <span class="white--text headline">{{charName}}</span>
                              </v-avatar>
                              <v-spacer></v-spacer>
                                </v-col>
                                <v-col class="userTitleName">
                                 <v-list-item>
                                 <v-list-item-content>
                                 <v-list-item-title class="title">{{user.name }} {{user.surname}}</v-list-item-title>
                                 <v-list-item-subtitle>{{user.role}}</v-list-item-subtitle>
                                 </v-list-item-content>
                                 </v-list-item>

                                </v-col>
                                </v-row>
                        <v-row aboutYourself>

                           <v-col class="margRight" >
                         <h4> {{user.information}}</h4>

                        <br />

                        <v-divider class="devI"></v-divider>
                        <h4>Контактная информация </h4>
                        <br />
                        <div>
                            <h4> <v-icon>mdi-email</v-icon>  {{user.email}}</h4>
                        </div>
                        <v-spacer></v-spacer>
                        <div>
                            <h4> <v-icon>mdi-phone-classic</v-icon>  {{user.phone}}</h4>
                        </div>
                       <br />
                                           <v-card id="addrH">
                                                <v-card-title>
                                                                                   <h4> <v-icon>mdi-map-marker-multiple </v-icon> Адреса</h4>
                                                                                   <v-spacer></v-spacer>
                                                                                   <v-btn class="mx-2" to="/address">
                                                                                       Изменить
                                                                                   </v-btn>
                                                                               </v-card-title>
                                               <ul>
                                                   <li v-for="address in user.addresses">
                                                       <h6>Город: {{ address.city}}, адрес: {{address.description}}</h6>
                                                   </li>
                                               </ul>

 <br />
                                           </v-card>

                       <v-divider class="devI"></v-divider>
                <v-flex  v-show="!isCustomer">
                    <v-card>
                    <v-card-title>
                    <h4> <v-icon>mdi-printer-3d </v-icon> Оборудование</h4>
                            <v-spacer></v-spacer>
                          <v-btn class="mx-2" to="/equipment">Изменить</v-btn>
                       </v-card-title>


                        <ul>
                            <li v-for="equip in equipments">
                                <h6><div><strong>{{equip.equipName}} </strong></div></h6>
                               <h6> <div>Размер печати: {{equip.height}} x {{equip.width}} x {{equip.length}}</div></h6>
                               <h6> <div>Материалы: <span v-for="material of equip.materialList"> {{material}}  </span>
                                </div></h6>
                            </li>
                        </ul>
                        <br />
                    </v-card>
                </v-flex>
                       <br />
                       <br />
                        </v-col >
</v-row>
                                    <v-spacer></v-spacer>


                      </v-card>
                         </v-flex>
                          </v-layout>



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
                charName:''
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
                let nameStr = this.user.name;
                let surnameStr = this.user.surname;
                let dateStr1 = (nameStr .substring(0,1));
                let dateStr2 = (surnameStr.substring(0,1));
                this.charName=dateStr1+dateStr2;
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
.v-application .pa-5 {
    margin-left: 63px;
}
.userTitleName{
margin-left: -591px;
    margin-top: 17px;
}

.v-application .title {
    font-size: 2.25rem!important;
}

.v-list-item__subtitle {
    font-size: 1rem;
}

.aboutYourself{
    margin-top: 39px;
}


.v-icon.v-icon {
    font-size: 31px;
}

.margRight{
    margin-right: 63px;
    margin-left: 63px;

}

h6, .h6{
color:"#0b0a0ad4";
font-size: 19px;
}

ul, .ul{
margin-left: 60px;
}
.font-weight-medium{

    margin-left: 915px;

}
</style>