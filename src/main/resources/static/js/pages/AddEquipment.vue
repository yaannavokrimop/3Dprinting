<template>
    <v-container>
        <b-card
             title="Регистрация оборудования"
             tag="article"
             style="max-width: 32rem;"
             class="mb-2"
        >
            <v-form ref="form" v-model="valid" lazy-validation>
                <div>


                    <v-text-field type="text" placeholder="Наименование" v-model="equipName" :rules="[v => ( v.length <= 50 && v.length > 1) || 'Должно быть от 1 до 50']"></v-text-field>
                    <div class="mt-2"></div>

                    <v-text-field type="text" placeholder="Описание" v-model="equipDesc" :rules="[v => ( v.length <= 100) || 'Должно быть не более 100 символов']"></v-text-field>
                    <div class="mt-2"></div>

                    <v-text-field type="number" placeholder="Допустимая высота" v-model="height" :rules="[v => ( v.length <= 5 && v.length > 0) || 'Введите число от 1 до 99999']" ></v-text-field>
                    <div class="mt-2"></div>

                    <v-text-field type="number" placeholder="Допустимая ширина" v-model="width" :rules="[v => ( v.length <= 5 && v.length > 0) || 'Введите число от 1 до 99999']"></v-text-field>
                    <div class="mt-2"></div>

                     <v-text-field type="number" placeholder="Допустимая длина" v-model="length" :rules="[v => ( v.length <= 5 && v.length > 0) || 'Введите число от 1 до 99999']"></v-text-field>
                     <div class="mt-2"></div>
                    <v-autocomplete
                         v-model="selectMaterial"
                        :items="items"
                        cache-items
                        deletable-chips
                        hide-no-data
                        hide-details
                        label="Материалы"
                        multiple
                        chips
                        :rules="[v => !!v || 'Выберите не менее одиного материала']"
                    ></v-autocomplete>
                    <div class="mt-2"></div>

                </div>
                <v-btn v-on:click="addEquip" variant="primary" :disabled="!valid">Добавить</v-btn>

            </v-form>
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
            items: [],
            selectMaterial: null,
            valid:true

        }
     },
     created:function(){
        this.materialSelections();
     },
     watch: {
//        search (val) {
//        val && val !== this.selectMaterial && this.querySelections(val)
//        },
     },
     methods: {
        addEquip(){
            if(this.$refs.form.validate()){
                let newEquip = {
                   'equipName': this.$data.equipName,
                   'height': this.$data.height,
                   'width': this.$data.width,
                   'length': this.$data.length,
                   'equipDesc': this.$data.equipDesc,
                   'materialList':this.$data.selectMaterial
                };

                AXIOS.post('/equipment', newEquip)
                    .then(response => {
                        console.log(response);
                        this.successAlert();
                    }).catch(error => console.log(error));
            }
        },
        successAlert() {
            this.equipName = '';
            this.equipDesc = '';
            this.height = '';
            this.width = '';
            this.length = '';
            this.$router.push('/equipment');
            location.reload()
        },
        materialSelections () {
            AXIOS.get('/material').then((response) =>{
                this.items=response.data;
            }).catch(error => console.log(error));
        },

     }

}
</script>

<style scoped>
.card {
    border-radius: 1.25rem;
    margin-left: 294px;
}
.card-title {
    margin-bottom: -0.25rem;
    margin-left: 71px;
    margin-bottom: 27px;
    margin-top: 9px;
}

</style>