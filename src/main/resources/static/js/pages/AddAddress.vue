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
                 <v-text-field label="Город*" required v-model="city"></v-text-field>
                 <v-text-field label="Адрес" v-model="description"></v-text-field>
                 <v-autocomplete
                   :items="['Skiing', 'Ice hockey', 'Soccer', 'Basketball', 'Hockey', 'Reading', 'Writing', 'Coding', 'Basejump']"
                   label="Город"
                   multiple
                 ></v-autocomplete>
           </v-container>
         </v-card-text>
         <v-card-actions>
           <v-spacer></v-spacer>
           <v-btn color="blue darken-1" text @click="dialog = false">Закрыть</v-btn>
           <v-btn color="blue darken-1" text @click="addEquip">Добавить</v-btn>
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
            city: '',
            description: '',
        }
     },

    methods: {
        addEquip(){
            var newAddress = {
                'cityTitle': this.$data.city,
                'description': this.$data.description,
            };
            console.log(newAddress);
            AXIOS.post('/address', newAddress)
                 .then(response => {
                    console.log(response)
                  }).catch(error => console.log(error));
            this.$data.dialog=false;

        },
        cleanAndRefresh(){
            this.$data.city='',
            this.$data.description=''
        }

    }
}
</script>

<style scoped>

</style>