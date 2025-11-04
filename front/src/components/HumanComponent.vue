<script setup>
import {onMounted, ref} from "vue";
import axios from "axios";
import TableDataComponent from "@/components/table/TableDataComponent.vue";
import {validatePositiveNumber} from "@/validators.js";
import TableHeadComponent from "@/components/table/TableHeadComponent.vue";

let id = ref()
let humans = ref([])
let errorGetHuman = ref()
let errorId = ref()

const baseUrl = 'http://localhost:8080/human-service/api'


const getHuman = async () => {
  try {
    humans.value = []
    const response = await axios.get(baseUrl + "/humans/" + id.value);
    humans.value.push(response.data)
    errorGetHuman.value = undefined
  } catch (err) {
    console.log(err)
    humans.value = undefined
    errorGetHuman.value = err.response.data.message
  }
}

function validateId() {
  console.log("in validate id=" + id.value)
  if (id.value && !validatePositiveNumber(id.value)) {
    errorId.value = "Значение должно быть целым числом больше 0"
    return false
  } else if (!id.value) {
    errorId.value = "Значение должно быть целым числом больше 0"
    return false
  } else {
    errorId = undefined
    return true
  }
}

</script>

<template>
  <div>
    <span>id: </span>
    <input type="text" v-model="id" @change="validateId"/>
    <input type="submit" @click.prevent="getHuman" value="показать">
  </div>


  <table border="1">
    <table-head-component :sort="false"/>
    <tbody>
    <table-data-component :humans="humans" @deleted="getHuman"/>
    </tbody>
  </table>
  <span class="error">{{ errorGetHuman }}</span>
</template>

<style scoped>

</style>