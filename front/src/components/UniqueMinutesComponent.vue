<script setup>

import {onMounted, ref} from "vue";
import axios from "axios";

let unique = ref()
let errorUnique = ref()

const baseUrl = 'http://localhost:8080/human-service/api'


const getUniqueMinutes = async () => {
  try {
    const response = await axios.get(baseUrl + "/humans/minutes-of-waiting/unique");
    unique.value = response.data;
    errorUnique.value = undefined
  } catch (err) {
    // console.log("problem with get")
    unique.value = undefined
    // switch (err.response.status) {
    //   case 422:
    //     response.value = err.response.data
    //     errorGet.value = response.value.message
    // }
  }
}

onMounted(
    getUniqueMinutes()
)

</script>

<template>
<div>
  <span>Уникальные значения времени ожидания:</span>
  <div v-for="minutes in unique">
    <span>{{minutes}}</span>
  </div>

</div>


</template>

<style scoped>

</style>