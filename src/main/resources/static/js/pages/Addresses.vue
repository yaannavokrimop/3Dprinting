<template>
    <v-container>
        <v-content>
            <h4>Мои Адреса</h4>
            <ul class="list-group">
                <li class="list-group-item"
                    v-for="(addr,index) in addresses"
                    :class="{ active: index == currentIndex }"
                    :key="index"
                    @click="setActiveAddr(addr, index)"
                >
                    <v-list-item three-line>
                        <v-list-item-content>
                            <v-list-item-title><strong>Город: {{addr.city}}</strong></v-list-item-title>
                            <v-list-item-subtitle>
                                <strong>Адрес: {{addr.description}}</strong>
                            </v-list-item-subtitle>
                        </v-list-item-content>
                    </v-list-item>
                </li>
            </ul>

            <div v-if="currentAddr">
                <div class="mt-2"></div>
                <label><strong>Текущий адрес:</strong></label> {{ this.currentAddr.description }}
                <label><strong>Текущий id:</strong></label> {{ this.currentAddr.id }}
                <div class="mt-2"></div>
                <b-button variant="danger" @click="showModal">Удалить адрес</b-button>

                <b-modal ref="my-modal" hide-footer title="Подтверждение">
                    <div class="d-block text-center">
                        <h3>Удалить адрес?</h3>
                    </div>
                    <b-button class="mt-3" variant="outline-danger" block @click="deleteAddress">Удалить</b-button>
                </b-modal>
            </div>


        </v-content>
        <div class="mt-2"></div>
        <v-content>

            <add-address></add-address>
        </v-content>
    </v-container>
</template>

<script>
    import {AXIOS} from "../pages/http-common";
    import AddAddress from 'pages/AddAddress.vue';

    export default {
        components: {
            AddAddress
        },
        props: [],
        data() {
            return {
                addresses: [],
                currentAddr: null,
                currentIndex: -1,


            }
        },
        created: function () {

            AXIOS.get('/address/user').then((responce) => {
                this.addresses = responce.data;
                console.log(responce.data);
            }).catch(error => console.log(error));

        },


        methods: {
            setActiveAddr(addr, index) {
                this.currentAddr = addr;
                this.currentIndex = index;
            },
            showModal() {
                this.$refs['my-modal'].show()
            },
            deleteAddress() {
                AXIOS.delete('/address/' + this.currentAddr.id);
                location.reload()
            }

        }
    }
</script>

<style scoped>

</style>