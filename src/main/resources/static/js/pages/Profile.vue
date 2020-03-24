<template>
<user-data v-bind:user='user'></user-data>
</template>

<script>
import UserData from 'components/UserData.vue'

export default {
    components:{
        UserData
    },
    data(){
        return{
            user:{
                 id:0,
                 email:'',
                 information:'',
                 name:'',
                 phone:'',
                 role:'',
                 surName:'',

            }
            }
          }
}
</script>

<style scoped>

</style>