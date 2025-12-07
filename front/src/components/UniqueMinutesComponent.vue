<script setup>

import {onMounted, ref} from "vue";
import axios from "axios";
import {baseHumansUrl} from "@/main.js";

let unique = ref()
let errorUnique = ref()


const getUniqueMinutes = async () => {
  try {
    const response = await axios.get(baseHumansUrl + "/minutes-of-waiting/unique");
    unique.value = response.data;
    errorUnique.value = undefined
  } catch (err) {
    unique.value = undefined
    errorUnique.value = err.response.data.message
  }
}

onMounted(
    getUniqueMinutes()
)

</script>

<template>
  <div>
    <span>Уникальные значения времени ожидания:</span>
    <table border="1">
      <thead>
      <tr>
        <td>Время ожидания</td>
      </tr>
      </thead>
      <tr v-for="minutes in unique">
        <td>{{ minutes }}</td>
      </tr>
    </table>
    <div v-if="errorUnique" class="error">{{errorUnique}}</div>
  </div>

</template>

<style scoped>

</style>