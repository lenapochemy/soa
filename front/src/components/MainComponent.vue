<script setup>
import {onMounted, ref} from "vue"
import axios from "axios"
import {validateFloatNumber, validatePositiveNumber} from "@/validators.js";
import DeleteHumanComponent from "@/components/DeleteHumanComponent.vue";
import FilterTrComponent from "@/components/filter/FilterTrComponent.vue";
import TableDataComponent from "@/components/TableDataComponent.vue";

let humans = ref([])
const baseUrl = 'http://localhost:8080/human-service/api'
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

let greaterMinutes = ref()
let errorGreaterMinutes = ref()

const getHumans = async () => {
  try {
    // collectFilter()
    // console.log("in get after collect filter")
    const response = await axios.get(urlForGet());
    humans.value = response.data;
    // console.log(humans.value)
    currentPageNumber.value = pageNumber.value
    currentPageSize.value = pageSize.value
    if (!pageNumber.value) currentPageNumber.value = 1
    if (!pageSize.value) {
      if(!pageNumber.value) {
        currentPageSize.value = humans.value.length
      } else currentPageSize.value = 10
    }
    errorGet.value = undefined
  } catch (err) {
    // console.log("problem with get")
    humans.value = undefined
    switch (err.response.status) {
      case 422:
        response.value = err.response.data
        errorGet.value = response.value.message
    }
  }
}



function urlForGet() {
  let url = baseUrl + "/humans?sort-by=" + sortBy.value + "&sort-order=" + sortOrder.value
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

function updateSorting(param) {
  if (sortBy.value === param && sortOrder.value === 'asc') {
    sortOrder.value = 'desc'
  } else sortOrder.value = 'asc'
  sortBy.value = param
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

function validateGreaterMinutes() {
  if (greaterMinutes.value && !validateFloatNumber(greaterMinutes.value)) {
    errorGreaterMinutes.value = "Время ожидания должен быть числом"
    return false
  } else {
    errorGreaterMinutes.value = undefined
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

const getHumansWithGreaterMinutes = async () => {
  try {
    const response = await axios.get(baseUrl + "/humans/minutes-of-waiting/" + greaterMinutes.value);
    humans.value = response.data;
    currentPageNumber.value = pageNumber.value
    currentPageSize.value = pageSize.value
    if (!pageNumber.value) currentPageNumber.value = 1
    if (!pageSize.value) {
      if(!pageNumber.value) {
        currentPageSize.value = humans.value.length
      } else currentPageSize.value = 10
    }
    errorGet.value = undefined
  } catch (err) {
    // console.log("problem with get")
    humans.value = undefined
    switch (err.response.status) {
      case 422:
        response.value = err.response.data
        errorGet.value = response.value.message
    }
  }
}

function updateForGreaterMinutes(){
  if(validateGreaterMinutes()){
    getHumansWithGreaterMinutes()
  }
}

function updateWithFilter(fil){
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
    <div>
      <span>Время ожидания больше чем </span>
      <br>
      <input type="text" v-model="greaterMinutes" @change="validateGreaterMinutes">
      <div v-if="errorGreaterMinutes" class="error">{{ errorGreaterMinutes }}</div>
      <input type="submit" @click.prevent="updateForGreaterMinutes" value="показать"/>
    </div>




    <table border="1">
      <thead>
      <tr>
        <th type="submit" @click.prevent="updateSorting('id')">id</th>
        <th type="submit" @click.prevent="updateSorting('name')">имя</th>
        <th type="submit" @click.prevent="updateSorting('creationDate')">дата создания</th>
        <th type="submit" @click.prevent="updateSorting('realHero')">герой</th>
        <th type="submit" @click.prevent="updateSorting('teamNumber')">номер команды</th>
        <th type="submit" @click.prevent="updateSorting('hasToothpick')">есть зубочистка</th>
        <th type="submit" @click.prevent="updateSorting('impactSpeed')">скорость удара</th>
        <th type="submit" @click.prevent="updateSorting('minutesOfWaiting')">минуты ожидания</th>
        <th type="submit" @click.prevent="updateSorting('weaponType')">тип оружия</th>
        <th type="submit" @click.prevent="updateSorting('mood')">настроение</th>
        <th type="submit" @click.prevent="updateSorting('car.name')">название машины</th>
        <th type="submit" @click.prevent="updateSorting('car.cool')">крутая машина</th>
        <th type="submit" @click.prevent="updateSorting('coordinates.x')">коорд x</th>
        <th type="submit" @click.prevent="updateSorting('coordinates.y')">коорд y</th>
      </tr>
      </thead>
      <tbody>
      <filter-tr-component @update="updateWithFilter($event)" />
      <table-data-component :humans="humans" @deleted="getHumans"/>
      </tbody>
    </table>
    <div class="error">{{ errorGet }}</div>


  </div>
</template>

<style scoped>
.error {
  color: red;
}

thead {
  background-color: deeppink;
  //font-size: large;
  font-weight: bold;
}



</style>