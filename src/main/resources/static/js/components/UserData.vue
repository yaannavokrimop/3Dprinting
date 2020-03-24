<template>
<div><h1>Hello {{user.name+' '+user.surname+' '+user.email}}!</h1></div>
</template>

<script>
export default {
    props:['user'],
    created:function(){
         this.$resource('/user/').get().then(result =>result.json().then(data=>{
             this.user.id = data.id;
             this.user.email = data.email;
             this.user.information = data.information;
             this.user.name = data.name;
             this.user.phone = data.phone;
             this.user.role = data.role;
             this.user.surName = data.surName;
         }
         ));
    }
}
</script>

<style scoped>

</style>