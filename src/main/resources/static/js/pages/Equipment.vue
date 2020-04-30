<template>

<v-container>

    <v-card  max-width="1000" tile>
        <h4>Оборудование {{equipment.equipName}}</h4>

        <v-list-item>
            <v-list-item-content>
                <v-list-item-title><strong>Описание:  {{equipment.equipDesc}}</strong></v-list-item-title>
            </v-list-item-content>
        </v-list-item>

        <v-list-item>
            <v-list-item-content>
                <v-list-item-title><strong>Ширина:  {{equipment.width}}</strong></v-list-item-title>
            </v-list-item-content>
        </v-list-item>
        <v-list-item>
            <v-list-item-content>
                <v-list-item-title><strong>Длина:  {{equipment.length}}</strong></v-list-item-title>
            </v-list-item-content>
        </v-list-item>
        <v-list-item>
            <v-list-item-content>
                <v-list-item-title><strong>Высота:  {{equipment.height}}</strong></v-list-item-title>
            </v-list-item-content>
        </v-list-item>


    </v-card>

</v-container>
</template>

<script>
import {AXIOS} from "../pages/http-common";
export default {
props:[],
 data(){
 return{
    equipment:{
        id:0,
        equipName:'',
        equipDesc:'',
        height:0,
        weight:0,
        length:0
    },

    accessToken: localStorage.getItem('accessToken')

 }
 },
    created:function(){

         AXIOS.get("/equipment/"+this.$route.params.id).then((response) =>{
             this.equipment.id = response.data.id;
             this.equipment.equipName = response.data.equipName;
             this.equipment.equipDesc = response.data.equipDesc;
             this.equipment.height = response.data.height;
             this.equipment.width = response.data.width;
             this.equipment.length = response.data.length;

         }).catch(error => console.log(error));;
    },
    methods:{

    }
}
</script>

<style scoped>



</style>
