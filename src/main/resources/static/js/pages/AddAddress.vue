<template>
     <v-dialog v-model="dialog" persistent max-width="600px">
       <template v-slot:activator="{ on }">
         <v-btn color="primary" dark v-on="on">Добавить Адрес</v-btn>
       </template>
       <v-card>
         <v-card-title>
           <span class="headline">Добавление Адреса</span>
         </v-card-title>
         <v-card-text>
           <v-container>
               <v-form
                   ref="form"
                   v-model="valid"
                   lazy-validation
                 >
                     <v-autocomplete
                        v-model="selectCity"
                        :loading="loading"
                        :items="items"
                        :search-input.sync="search"
                        cache-items
                        hide-no-data
                        hide-details
                        label="Город"
                        chips
                        :rules="[v => !!v || 'Выберите один из городов']"
                     ></v-autocomplete>
                     <v-text-field label="Адрес" v-model="description" :rules="[v => ( v.length <= 100) || 'Описание должно быть не более 100 символов']"></v-text-field>
               </v-form>
           </v-container>
         </v-card-text>
         <v-card-actions>
           <v-spacer></v-spacer>
           <v-btn color="blue darken-1" text @click="dialog = false">Закрыть</v-btn>
           <v-btn color="blue darken-1" text @click="addAddress" :disabled="!valid">Добавить</v-btn>
         </v-card-actions>
       </v-card>
     </v-dialog>

</template>

<script>
import {AXIOS} from './http-common'
export default {
    name: "AddAddress",
     data(){
        return{
            dialog:false,

            description: '',
            items: [],
            search: null,
            selectCity: null,
            loading: false,
            valid:true,
        }
     },
     watch: {
        search (val) {
        val && val !== this.selectCity && this.querySelections(val)
              },
         },

    methods: {
        addAddress(){
            if(this.$refs.form.validate()){
                var newAddress = {
                    'cityTitle': this.$data.selectCity,
                    'description': this.$data.description,
                };
                console.log(newAddress);
                AXIOS.post('/address', newAddress)
                     .then(response => {
                        console.log(response)
                      }).catch(error => console.log(error));
                      location.reload()
                this.$data.dialog=false;
            }
        },

        querySelections (cityPartName) {

            setTimeout(()=>{
            if(cityPartName==this.$data.search){
            this.loading = true
            AXIOS.get('/search/cityList/'+cityPartName).then((response) =>{

                this.items=response.data;
            }).catch(error => console.log(error));
            this.loading = false
            }},1200)
            },

    }
}
</script>

<style scoped>

</style>