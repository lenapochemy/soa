<script setup>

import axios from "axios";
import {validateFloatNumber} from "@/validators.js";
import {ref} from "vue";
import TableDataComponent from "@/components/table/TableDataComponent.vue";
import TableHeadComponent from "@/components/table/TableHeadComponent.vue";

const baseUrl = 'http://localhost:8080/human-service/api'

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
    const response = await axios.get(baseUrl + "/humans/minutes-of-waiting/" + greaterMinutes.value);
    humans.value = response.data;
    errorGreaterMinutes.value = undefined
  } catch (err) {
    humans.value = undefined
    switch (err.response.status) {
      case 422:
        errorGreaterMinutes.value = err.response.data.message
    }
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
    <table-data-component :humans="humans" @deleted="getHumansWithGreaterMinutes"/>
    </tbody>
  </table>
</template>

<style scoped>

</style>