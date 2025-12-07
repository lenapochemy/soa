<script setup>

import axios from "axios";
import {validateFloatNumber} from "@/validators.js";
import {ref} from "vue";
import TableDataComponent from "@/components/table/TableDataComponent.vue";
import TableHeadComponent from "@/components/table/TableHeadComponent.vue";
import {baseHumansUrl} from "@/main.js";

let greaterMinutes = ref()
let errorGreaterMinutes = ref()
let humans = ref()

function validateGreaterMinutes() {
  if (!greaterMinutes.value) {
    errorGreaterMinutes.value = "Время ожидания не может быть пустым"
    return false
  } else if (greaterMinutes.value && !validateFloatNumber(greaterMinutes.value)) {
    errorGreaterMinutes.value = "Время ожидания должно быть числом"
    return false
  } else {
    errorGreaterMinutes.value = undefined
    return true
  }
}

const getHumansWithGreaterMinutes = async () => {
  try {
    const response = await axios.get(baseHumansUrl + "/minutes-of-waiting/" + greaterMinutes.value);
    humans.value = response.data;
    errorGreaterMinutes.value = undefined
  } catch (err) {
    humans.value = undefined
    errorGreaterMinutes.value = err.response.data.message
  }
}

function updateForGreaterMinutes() {
  if (validateGreaterMinutes()) {
    getHumansWithGreaterMinutes()
  }
}
</script>

<template>
  <div>
    <span>Время ожидания больше чем </span>
    <br>
    <input type="text" v-model="greaterMinutes" @change="validateGreaterMinutes">
    <div v-if="errorGreaterMinutes" class="error">{{ errorGreaterMinutes }}</div>
    <input type="submit" @click.prevent="updateForGreaterMinutes" value="показать"/>
  </div>


  <table border="1">
    <table-head-component :sort="false"/>
    <tbody>
    <table-data-component :can-delete="true" :humans="humans" @deleted="getHumansWithGreaterMinutes"/>
    </tbody>
  </table>
</template>

<style scoped>

</style>