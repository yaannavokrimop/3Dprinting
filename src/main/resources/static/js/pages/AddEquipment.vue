<template>
    <v-container>
        <b-card
             title="Регистрация Оборудования"
             tag="article"
             style="max-width: 20rem;"
             class="mb-2"
        >
            <div>

                <b-form-input type="text" placeholder="Наименование" v-model="equipName" />
                <div class="mt-2"></div>

                <b-form-input type="text" placeholder="Описание" v-model="equipDesc" />
                <div class="mt-2"></div>

                <b-form-input type="text" placeholder="Допустимая высота" v-model="height" />
                <div class="mt-2"></div>

                <b-form-input type="text" placeholder="Допустимая ширина" v-model="width" />
                <div class="mt-2"></div>

                 <b-form-input type="text" placeholder="Допустимая длина" v-model="length" />
                 <div class="mt-2"></div>

            </div>
            <v-btn v-on:click="addEquip" variant="primary">Добавить</v-btn>
        </b-card>
    </v-container>
</template>

<script>
import {AXIOS} from './http-common'
export default {
    name: "AddEquipment",
    data () {
        return {
            equipName: '',
            equipDesc: '',
            height:'',
            width: '',
            length: '',

        }
     },
     methods: {
        addEquip(){
            let newEquip = {
               'equipName': this.$data.equipName,
               'height': this.$data.height,
               'width': this.$data.width,
               'length': this.$data.length,
               'equipDesc': this.$data.equipDesc
            };

            AXIOS.post('/equipment', newEquip)
                .then(response => {
                    console.log(response);
                    this.successAlert();
                }).catch(error => console.log(error));
        },
        successAlert() {
            this.equipName = '';
            this.equipDesc = '';
            this.height = '';
            this.width = '';
            this.length = '';

        }

     }

}
</script>

<style scoped>

</style>