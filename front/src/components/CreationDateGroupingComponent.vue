<script setup>
import {onMounted, ref} from "vue";
import axios from "axios";

let creationDate = ref()
let errorCreationDate = ref()

const baseUrl = 'http://localhost:8080/human-service/api'


const getGrouping = async () => {
  try {
    const response = await axios.get(baseUrl + "/humans/creation-date");
    creationDate.value = response.data;
    errorCreationDate.value = undefined
  } catch (err) {
    creationDate.value = undefined
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