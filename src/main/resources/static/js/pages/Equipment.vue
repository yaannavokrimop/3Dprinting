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

         AXIOS.get("/equipment/"+this.$route.params.id).then((responce) =>{
             this.equipment.id = responce.data.id;
             this.equipment.equipName = responce.data.equipName;
             this.equipment.equipDesc = responce.data.equipDesc;
             this.equipment.height = responce.data.height;
             this.equipment.width = responce.data.width;
             this.equipment.length = responce.data.length;

         }).catch(error => console.log(error));;
    },
    methods:{

    }
}
</script>

<style scoped>



</style>
