<template>


<v-card>
    <v-card-title>
     {{user.name }} {{user.surname}}
    </v-card-title>
    <v-card-text>
     <v-text-field
          v-model="user.email"
          label="Email Address">
     </v-text-field>

     <v-text-field
          v-model="user.phone"
          label="Tel.">
     </v-text-field>
     <v-text-field
          v-model="user.information"
          label="Information">
     </v-text-field>
     <v-text-field
          v-model="user.role"
          label="Information">
     </v-text-field>
    </v-card-text>
</v-card>


</template>

<script>
export default {
    props:['user'],
    created:function(){
         this.$resource('/api/user/').get().then(result =>result.json().then(data=>{
             this.user.id = data.id;
             this.user.email = data.email;
             this.user.information = data.information;
             this.user.name = data.name;
             this.user.phone = data.phone;
             this.user.role = data.role;
             this.user.surname = data.surname;
         }
         ));
    }
}
</script>

<style scoped>

</style>