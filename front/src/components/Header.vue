<script >
// import {api} from "@/axios.js";
// import {errorHandler} from "@/js/utils.js";

export default {
  name: "Header",
  data() {
    return {
      name: String,
      role: String
    }
  },
  mounted() {
    // this.name = localStorage.getItem("userLogin");
    // this.getRole();
  },
  methods:{
    // getRole(){
    //   api.get("/user/role")
    //       .then(response => {
    //         let role = response.data;
    //         this.role = this.getRoleText(role);
    //       })
    //       .catch(error => {
    //         errorHandler(error.response.status, "admin");
    //       })
    // },
    // getRoleText(role){
    //   switch (role){
    //     case "APPROVED_ADMIN":
    //       return "admin";
    //     default:
    //       return "user";
    //   }
    // },
  }
}
</script>

<template>
  <div>
<!--    <span class="my_name">Your login: </span>-->
<!--    <span class="data">{{name}}</span>-->
<!--    <span class="my_name"> and your role: </span>-->
<!--    <span class="data">{{role}}</span>-->
    <span class="header_name">Human Being Collection</span>
  </div>


</template>


<style scoped>

h1 {
  font-weight: 500;
  font-size: 2.6rem;
  position: relative;
  top: -10px;

}

h3 {
  font-size: 1.2rem;
}


.header_name {
  font-size: xxx-large;
  font-weight: bold;
  color: dimgrey;
}


</style>
