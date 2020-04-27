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
            items: [],
            search: null,
            selectMaterial: null,

        }
     },
     watch: {
        search (val) {
        val && val !== this.selectMaterial && this.querySelections(val)
        },
     },
     methods: {
        addEquip(){
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
        querySelections (materialPartName) {
            setTimeout(()=>{
            if(materialPartName==this.$data.search){
            AXIOS.get('/material/materialList/'+materialPartName).then((response) =>{
                this.items=response.data;
            }).catch(error => console.log(error));
            }},1200)
            },

     }

}
</script>

<style scoped>

</style>