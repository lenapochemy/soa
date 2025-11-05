<script setup>
import {onMounted, ref} from "vue"
import axios from "axios"
import {validatePositiveNumber} from "@/validators.js";
import FilterTrComponent from "@/components/table/FilterTrComponent.vue";
import TableDataComponent from "@/components/table/TableDataComponent.vue";
import GiveCarComponent from "@/components/GiveCarComponent.vue";
import TableHeadComponent from "@/components/table/TableHeadComponent.vue";
import {baseHumansUrl} from "@/main.js";

let humans = ref([])

let pageNumber = ref()
let currentPageNumber = ref(1)
let errorPageNumber = ref()
let pageSize = ref()
let currentPageSize = ref(10)
let errorPageSize = ref()
let filter = ref()
let sortBy = ref('id')
let sortOrder = ref('asc')
let errorGet = ref()
let response = ref()
let teams = ref([])


const getHumans = async () => {
  try {
    const response = await axios.get(urlForGet());
    humans.value = response.data;
    updateTeamsList(response.data)
    currentPageNumber.value = pageNumber.value
    currentPageSize.value = pageSize.value
    if (!pageNumber.value) currentPageNumber.value = 1
    if (!pageSize.value) {
      if (!pageNumber.value) {
        currentPageSize.value = humans.value.length
      } else currentPageSize.value = 10
    }
    errorGet.value = undefined
  } catch (err) {
    humans.value = undefined
    switch (err.response.status) {
      case 422:
        response.value = err.response.data
        errorGet.value = response.value.message
    }
  }
}


function updateTeamsList() {
  teams.value = []
  const uniqueTeams = new Set()
  humans.value.forEach(human => {
    if (human.teamNumber) {
      uniqueTeams.add(human.teamNumber)
    }
  })
  teams.value = [...uniqueTeams]
}

function urlForGet() {
  let url = baseHumansUrl + "?sort-by=" + sortBy.value + "&sort-order=" + sortOrder.value
  if (pageSize.value) {
    url += "&page-size=" + pageSize.value
  }
  if (pageNumber.value) {
    url += "&page=" + pageNumber.value
  }
  if (filter.value) {
    url += "&filter=" + filter.value
  }
  return url
}

function getHumansWithSorting(args) {
  sortBy.value = args.sortBy
  sortOrder.value = args.sortOrder
  getHumans()
}


function validatePageSize() {
  if (pageSize.value && !validatePositiveNumber(pageSize.value)) {
    errorPageSize.value = "Размер страницы должен быть положительным целым числом"
    return false
  } else {
    errorPageSize.value = undefined
    return true
  }
}

function validatePageNumber() {
  if (pageNumber.value && !validatePositiveNumber(pageNumber.value)) {
    errorPageNumber.value = "Номер страницы должен быть положительным целым числом"
    return false
  } else {
    errorPageNumber.value = undefined
    return true
  }
}

function nextPage() {
  currentPageNumber.value++
  pageNumber.value = currentPageNumber.value
  getHumans()
}

function prevPage() {
  currentPageNumber.value--
  pageNumber.value = currentPageNumber.value
  getHumans()
}


function updateTable() {
  console.log("in update table")
  if (validatePageNumber() && validatePageSize()) {
    console.log("in update table before get")
    getHumans()
  }
}

function updateWithFilter(fil) {
  console.log(fil)
  filter = fil
  console.log(filter.value)
  updateTable()
}

onMounted(
    getHumans()
)


</script>

<template>

  <div>
    <div id="page-info">
      <span>Номер страницы {{ currentPageNumber }}</span>
      <br>
      <input type="text" v-model="pageNumber" @change="validatePageNumber">
      <div v-if="errorPageNumber" class="error">{{ errorPageNumber }}</div>
      <br>
      <button @click.prevent="prevPage" v-if="currentPageNumber > 1">Предыдущая страница</button>
      <button @click.prevent="nextPage">Следующая страница</button>
      <br>
      <span>Размер страницы {{ currentPageSize }}</span>
      <br>
      <input type="text" v-model="pageSize" @change="validatePageSize">
      <div v-if="errorPageSize" class="error">{{ errorPageSize }}</div>
      <br>
      <button @click.prevent="updateTable">Показать</button>
    </div>

    <give-car-component :teams="teams" @added-cars="getHumans"/>

    <table border="1">
      <table-head-component :sort="true" @updated-sorting="args => getHumansWithSorting(args)"/>
      <tbody>
      <filter-tr-component @update="updateWithFilter($event)"/>
      <table-data-component :can-delete="true" :humans="humans" @deleted="getHumans"/>
      </tbody>
    </table>
    <div class="error">{{ errorGet }}</div>


  </div>
</template>

<style>
.error {
  color: red;
}

thead {
  background-color: deeppink;
  //font-size: large;
  font-weight: bold;
}
form > div, fieldset {
  margin-bottom: 1em;
}

label {
  display: block;
  margin-bottom: 0.2em;
}

input[type="text"],
input[type="number"],
select {
  width: 100px;
  padding: 0.3em;
  box-sizing: border-box;
}

button {
  padding: 0.5em 2em;
  cursor: pointer;
}

.res {
  color: blue;
}
</style>