<script setup>
import {onMounted, ref} from "vue";
import axios from "axios";
import {baseHumansUrl} from "@/main.js";

let creationDate = ref()
let errorCreationDate = ref()

const getGrouping = async () => {
  try {
    const response = await axios.get(baseHumansUrl + "/creation-date");
    creationDate.value = response.data;
    errorCreationDate.value = undefined
  } catch (err) {
    creationDate.value = undefined
    errorCreationDate.value = err.response.data.message
  }
}

onMounted(
    getGrouping()
)
</script>

<template>
  <span>Группировка по дате создания:</span>
  <table border="1">
    <thead>
    <tr>
      <th>Дата создания</th>
      <th>Количество</th>
    </tr>
    </thead>
    <tbody>
    <tr v-for="date in creationDate">
      <td>{{ date.creationDate }}</td>
      <td>{{ date.count }}</td>
    </tr>

    </tbody>
  </table>
</template>

<style scoped>

</style>