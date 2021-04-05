<template>
<v-container>
    <v-content>
        <h4>Моё оборудование</h4>
        <ul class="list-group">
            <li class="list-group-item"
                 v-for="(equip,index) in equipments"
                 :class="{ active: index === currentIndex }"
                 :key="index"
                 @click="setActiveEquipment(equip, index)"
                 @dblclick="goToEquipment(equip)"
            >
            <v-list-item three-line>
                  <v-list-item-content>
                    <v-list-item-title><strong>  {{equip.equipName}}</strong></v-list-item-title>
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
                    <v-list-item-subtitle>
                     <strong>
                        <div>Материалы: <span v-for="material of equip.materialList"> {{material}}  </span></div>
                     </strong>
                    </v-list-item-subtitle>
                  </v-list-item-content>
            </v-list-item>
            </li>
        </ul>
        <div v-if="currentEquip">
            <div class="mt-2"></div>
            <label><strong>Текущее оборудование:</strong></label> {{ currentEquip.equipDesc }}
            <div class="mt-2"></div>
            <b-button variant="danger" @click="showModal">Удалить оборудование</b-button>

            <b-modal ref="my-modal" hide-footer title="Подтверждение">
                <div class="d-block text-center">
                    <h3>Удалить оборудование?</h3>
                </div>
                <b-button class="mt-3" variant="outline-danger" block @click="deleteEquip">Удалить</b-button>
            </b-modal>
        </div>

    </v-content>
    <div class="mt-2"></div>
        <v-content>
        <v-btn  to="/add_equipment">Добавить оборудование</v-btn>
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
        created: function () {
            AXIOS.get('/equipment/my').then((response) => {
                this.equipments = response.data;
                console.log(response.data);
            }).catch(error => console.log(error));

            /*AXIOS.get('/equipment').then((response) => {
                this.equipments = response.data;
                console.log(response.data);

            }).catch(error => console.log(error));*/
        },


    methods:{
     setActiveEquipment(equip, index) {
          this.currentEquip = equip;
          this.currentIndex = index;
        },
        showModal() {
            this.$refs['my-modal'].show()
        },
     goToEquipment(equip){
     var s="/equipment/"+equip.executorEquipId;
             this.$router.push(s)
     },
        deleteEquip() {
            AXIOS.delete('/equipment/' + this.currentEquip.executorEquipId);
            location.reload()
        }
    }
}
</script>

<style scoped>
.list-group-item{
    width: 1052px;
    margin-bottom: 12px;
    border-radius: 16px;
}

.list-group-item:first-child {
    border-top-left-radius: 16px;
    border-top-right-radius: 16px;
}

</style>