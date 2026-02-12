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
    errorGet.value = err.response.data
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

  <div id="app">
    <!-- Page Controls -->
    <div id="page-info">
      <div class="page-controls">
        <div class="control-group">
          <label for="pageNumber">Текущая страница: {{ currentPageNumber }}</label>
          <input
              id="pageNumber"
              type="text"
              v-model="pageNumber"
              @change="validatePageNumber"
              class="control-input"
              placeholder="Номер страницы"
          >
          <div v-if="errorPageNumber" class="error mt-1">{{ errorPageNumber }}</div>
        </div>

        <div class="control-group">
          <label for="pageSize">Размер страницы: {{ currentPageSize }}</label>
          <input
              id="pageSize"
              type="text"
              v-model="pageSize"
              @change="validatePageSize"
              class="control-input"
              placeholder="Размер"
          >
          <div v-if="errorPageSize" class="error mt-1">{{ errorPageSize }}</div>
        </div>
      </div>

      <div class="page-buttons">
        <button
            @click.prevent="prevPage"
            :disabled="currentPageNumber <= 1"
            class="btn btn-secondary"
        >
          ← Предыдущая
        </button>

        <button
            @click.prevent="updateTable"
            class="btn btn-primary"
        >
          Обновить таблицу
        </button>

        <button
            @click.prevent="nextPage"
            class="btn btn-secondary"
        >
          Следующая →
        </button>
      </div>
    </div>

    <div class="table-container">
      <table>
        <table-head-component
            :sort="true"
            @updated-sorting="args => getHumansWithSorting(args)"
        />
        <tbody>
        <filter-tr-component @update="updateWithFilter($event)"/>
        <table-data-component
            :can-delete="true"
            :humans="humans"
            @deleted="getHumans"
        />
        </tbody>
      </table>

      <div v-if="errorGet" class="error mt-2">{{ errorGet }}</div>
    </div>
  </div>

  <give-car-component :teams="teams" @added-cars="getHumans"/>

</template>

<style>
/* === CSS Variables for consistent theming === */
:root {
  --primary-color: #4361ee;
  --primary-dark: #3a56d4;
  --secondary-color: #7209b7;
  --success-color: #4cc9f0;
  --danger-color: #f72585;
  --warning-color: #f8961e;
  --light-color: #f8f9fa;
  --dark-color: #212529;
  --gray-light: #e9ecef;
  --gray: #adb5bd;
  --border-radius: 8px;
  --box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  --transition: all 0.3s ease;
}

/* === Base styles === */
#app {
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, sans-serif;
  line-height: 1.6;
  color: var(--dark-color);
  padding: 20px;
  max-width: 2000px;
  margin: 0 auto;

}

/* === Page Controls Styling === */
#page-info {
  background: white;
  padding: 1.5rem;
  border-radius: var(--border-radius);
  box-shadow: var(--box-shadow);
  margin-bottom: 2rem;
  border: 1px solid var(--gray-light);

}

#page-info > * {
  margin-bottom: 1rem;
}

#page-info > *:last-child {
  margin-bottom: 0;
}

.page-controls {
  display: flex;
  flex-wrap: wrap;
  gap: 1rem;
  align-items: center;
  margin-bottom: 1.5rem;
}

.control-group {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.control-group label {
  font-size: 0.9rem;
  font-weight: 500;
  color: #555;
}

.control-input {
  padding: 0.6rem 0.8rem;
  border: 1px solid var(--gray);
  border-radius: var(--border-radius);
  font-size: 1rem;
  transition: var(--transition);
  width: 120px;
}

.control-input:focus {
  outline: none;
  border-color: var(--primary-color);
  box-shadow: 0 0 0 3px rgba(67, 97, 238, 0.1);
}

.page-buttons {
  display: flex;
  gap: 0.75rem;
  margin-top: 1rem;
}

/* === Button Styles === */
.btn {
  padding: 0.6rem 1.5rem;
  border: none;
  border-radius: var(--border-radius);
  font-weight: 500;
  cursor: pointer;
  transition: var(--transition);
  font-size: 0.95rem;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
}

.btn-primary {
  background-color: var(--primary-color);
  color: white;
}

.btn-primary:hover {
  background-color: var(--primary-dark);
  transform: translateY(-1px);
}

.btn-secondary {
  background-color: var(--light-color);
  color: var(--dark-color);
  border: 1px solid var(--gray);
}

.btn-secondary:hover {
  background-color: var(--gray-light);
}

.btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
  transform: none !important;
}

/* === Table Styling === */

/* Контейнер для таблицы — делаем его видимым целиком */
.table-container {
  margin-top: 1rem;
  max-width: 100%;
  overflow-x: auto;       /* горизонтальный скролл только внутри контейнера */
  overflow-y: auto;       /* если строк много, скроллим внутри контейнера */
  max-height: calc(100vh - 260px); /* высота окна минус панель с кнопками/верх */
  box-sizing: border-box;
}

/* Чтобы таблица не «разъезжалась» */
table {
  width: 100%;
  border-collapse: collapse;
  margin: 0;              /* убираем большой внешний отступ */
  box-shadow: var(--box-shadow);
  border-radius: var(--border-radius);
  /*table-layout: fixed;     опционально, чтобы колонки не раздувались */
}

/*table {
  width: 100%;
  border-collapse: collapse;
  margin: 2rem 0;
  box-shadow: var(--box-shadow);
  border-radius: var(--border-radius);
}*/

thead {
  background: linear-gradient(135deg, var(--primary-color), var(--secondary-color));
  color: white;
}

thead th {
  padding: 1rem;
  text-align: left;
  font-weight: 600;
  font-size: 0.95rem;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

thead th:hover { 
  background-color: rgba(255, 255, 255, 0.1);
  cursor: pointer;
}

tbody tr {
  border-bottom: 1px solid var(--gray-light);
  transition: var(--transition);
}

tbody tr:hover {
  background-color: rgba(67, 97, 238, 0.05);
}

tbody td {
  padding: 1rem;
  vertical-align: middle;
}

/* === Error and Status Messages === */
.error {
  background-color: #fee;
  color: var(--danger-color);
  padding: 1rem;
  border-radius: var(--border-radius);
  border-left: 4px solid var(--danger-color);
  margin: 1rem 0;
  font-weight: 500;
}

/* === Utility Classes === */
.text-center {
  text-align: center;
}

.text-right {
  text-align: right;
}

.mt-1 {
  margin-top: 0.5rem;
}

.mt-2 {
  margin-top: 1rem;
}

.mt-3 {
  margin-top: 1.5rem;
}

.mb-1 {
  margin-bottom: 0.5rem;
}

.mb-2 {
  margin-bottom: 1rem;
}

.mb-3 {
  margin-bottom: 1.5rem;
}

.d-flex {
  display: flex;
}

.align-center {
  align-items: center;
}

.justify-between {
  justify-content: space-between;
}

.gap-1 {
  gap: 0.5rem;
}

.gap-2 {
  gap: 1rem;
}


/* Пустая таблица */
.table-empty {
  text-align: center;
  padding: 3rem;
  color: var(--gray);
  font-size: 1.1rem;
}

.table-empty::before {
  font-size: 3rem;
  display: block;
  margin-bottom: 1rem;
  opacity: 0.5;
}

.row-count {
  color: var(--gray);
}

/* Скроллбар для таблицы */
.table-container::-webkit-scrollbar {
  width: 8px;
  height: 8px;
}

.table-container::-webkit-scrollbar-track {
  background: var(--gray-light);
  border-radius: 4px;
}

.table-container::-webkit-scrollbar-thumb {
  background: var(--primary-color);
  border-radius: 4px;
}

.table-container::-webkit-scrollbar-thumb:hover {
  background: var(--primary-dark);
}
</style>