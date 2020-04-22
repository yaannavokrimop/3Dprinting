<template>
    <v-container>
        <b-card
             title="Добавление Оборудования"
             tag="article"
             style="max-width: 20rem;"
             class="mb-2"
        >
            <div>

                <v-autocomplete
                    v-model="equipName"
                    :items="items"
                    :search-input.sync="search"
                    cache-items
                    hide-no-data
                    hide-details
                    label="Наименование"
                    chips
                ></v-autocomplete>
                <div class="mt-2"></div>

                <b-form-input type="text" placeholder="Описание" v-model="equipDesc" />
                <div class="mt-2"></div>
                <div>Допустимая высота:  {{height}}</div>
                <div class="mt-2"></div>

                <div>Допустимая ширина:  {{width}}</div>
                <div class="mt-2"></div>

                 <div>Допустимая длина:  {{length}}</div>
                 <div class="mt-2"></div>

            </div>
            <v-btn v-on:click="addEquip" variant="primary">Добавить</v-btn>

            <div>Если у вас не получилось найти ваше оборудование при вводе в поле Наименование,нажмите на кнопку ниже и зарегистрируйте  ваше оборудование </div>
            <v-btn to="/create_equipment" variant="primary">Зарегистрировать новое оборудование</v-btn>
        </b-card>
    </v-container>
</template>

<script>
import {AXIOS} from './http-common'
export default {
    name: "AddEquipment",
    data () {
        return {
            equipName: null,
            equipDesc: '',
            height:'',
            width: '',
            length: '',
            items: [],
            search: null,
            equipId:null
        }
     },
    watch: {
        search (val) {
        val && val !== this.equipName && this.querySelections(val)

              },
         equipName:'getEquipment'
         },
     methods: {
        addEquip(){
            if(this.$data.equipName!=null){
            let newEquip = {
               'equipName': this.$data.equipName,
               'equipDesc': this.$data.equipDesc,
            };

            console.log(newEquip);
            AXIOS.post('/equipment/add', newEquip)
               .then(response => {
                    this.successAlert();
                }).catch(error => console.log(error));
            }
        },
        successAlert() {
            this.equipName = null;
            this.equipDesc = '';
            this.height = '';
            this.width = '';
            this.length = '';

        },
        querySelections (equipPartName) {
            setTimeout(()=>{
                if(equipPartName==this.$data.search){
                AXIOS.get('/equipment/equipByPartName/'+equipPartName).then((response) =>{
                    this.items=response.data;
                    }).catch(error => console.log(error));
                      }},1200)

                  },
        getEquipment(equipName){
        AXIOS.get('/equipment/name/'+ equipName)
            .then(response => {
                this.$data.height=response.data.height;
                this.$data.width=response.data.width;
                this.$data.length=response.data.length;
                this.$data.equipId=response.data.id;
            }).catch(error => console.log(error));

        }

     }

}
</script>

<style scoped>

</style>