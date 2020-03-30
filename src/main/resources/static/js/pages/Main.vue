<template>
<v-container>
    <v-content>
    Список исполнителей и карта
    </v-content>
    <v-content>
        <h4>Оборудование</h4>
        <ul class="list-group">
            <li class="list-group-item"
                 v-for="(equip,index) in equipments"
                 :class="{ active: index == currentIndex }"
                 :key="index"
                 @click="setActiveEquipment(equip, index)"
            >
            <v-list-item>
                  <v-list-item-content>
                    <v-list-item-title><strong>Наименование:  {{equip.equipName}}</strong></v-list-item-title>
                    <v-list-item-subtitle>
                     <strong>Описание:   {{equip.equipDesc}}</strong>
                    </v-list-item-subtitle>
                    <v-list-item-subtitle>
                     <strong>Размеры:
                        {{equip.height}} x
                        {{equip.width}} x
                        {{equip.length}}
                     </strong>
                    </v-list-item-subtitle>
                  </v-list-item-content>
            </v-list-item>


            </li>
        </ul>
    </v-content>

    <v-content>
        <h4>Equipment</h4>
                <div v-if="currentEquip">
                  <label><strong>Equipment Name:</strong></label> {{ currentEquip.equipName }}
                </div>
    </v-content>

</v-container>
</template>

<script>
import {AXIOS} from "../pages/http-common";
export default {
props:[],
 data(){
 return{
    equipments:[],
    currentEquip:null,
    currentIndex:-1,


 }
 },
    created:function(){
         AXIOS.get('/equipment').then((responce) =>{
             this.equipments = responce.data;
             console.log(response.data);

         }).catch(error => console.log(error));;
    },
    methods:{
     setActiveEquipment(equip, index) {
          this.currentEquip = equip;
          this.currentIndex = index;
        },
    }
}
</script>

<style scoped>



</style>