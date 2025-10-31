<script setup>
import {onMounted, reactive, ref} from "vue"
import axios from "axios"
import {validateCoordinates, validateFloatNumber, validateNumber, validateString} from "@/validators.js";
import FilterOperatorComponent from "@/components/filter/FilterOperatorComponent.vue";


let humans = ref([])
const baseUrl = 'http://localhost:8080/human-service/api'
let pageNumber = ref(1)
let currentPageNumber = ref(1)
let errorPageNumber = ref()
let pageSize = ref(10)
let currentPageSize = ref(10)
let errorPageSize = ref()
let filter = ref()
let sortBy = ref('id')
let sortOrder = ref('asc')
let errorGet = ref()
let response = ref()

let filterIdOperator = ref()
let filterNameOperator = ref()
let filterCreationDateOperator = ref()
let filterRealHeroOperator = ref()
let filterTeamNumberOperator = ref()
let filterHasToothpickOperator = ref()
let filterImpactSpeedOperator = ref()
let filterMinutesOfWaitingOperator = ref()
let filterWeaponTypeOperator = ref()
let filterMoodOperator = ref()
let filterCarNameOperator = ref()
let filterCarCoolOperator = ref()
let filterCoordinatesXOperator = ref()
let filterCoordinatesYOperator = ref()

let filterIdValue = ref()
let filterNameValue = ref()
let filterCreationDateValue = ref()
let filterRealHeroValue = ref()
let filterTeamNumberValue = ref()
let filterHasToothpickValue = ref()
let filterImpactSpeedValue = ref()
let filterMinutesOfWaitingValue = ref()
let filterWeaponTypeValue = ref()
let filterMoodValue = ref()
let filterCarNameValue = ref()
let filterCarCoolValue = ref()
let filterCoordinatesXValue = ref()
let filterCoordinatesYValue = ref()


let filterIdError = ref("")
let filterNameError = ref()
let filterCreationDateError = ref()
let filterRealHeroError = ref()
let filterTeamNumberError = ref()
let filterHasToothpickError = ref()
let filterImpactSpeedError = ref()
let filterMinutesOfWaitingError = ref()
let filterWeaponTypeError = ref()
let filterMoodError = ref()
let filterCarNameError = ref()
let filterCarCoolError = ref()
let filterCoordinatesXError = ref()
let filterCoordinatesYError = ref()

const getHumans = async () => {
  try {
    collectFilter()
    console.log("in get after collect filter")
    const response = await axios.get(urlForGet());
    humans.value = response.data;
    console.log(humans.value)
    currentPageNumber.value = pageNumber.value
    currentPageSize.value = pageSize.value
    if (!pageNumber.value) currentPageNumber.value = 1
    if (!pageSize.value) currentPageSize.value = humans.value.length
    errorGet.value = undefined
  } catch (err) {
    console.log("problem with get")
    humans.value = undefined
    switch (err.response.status){
      case 422:
        response.value = err.response.data
        errorGet.value = response.value.message
    }
  }
}

function collectFilter(){
  filter.value = ""
  // console.log(filterIdValue.value)
  // console.log(filterIdOperator.value)
  let filters = []
  if(filterIdValue.value && filterIdOperator.value){
    filters.push("id" + filterIdOperator.value + filterIdValue.value)
    // filter.value +=
  }
  if(filterNameValue.value && filterNameOperator.value){
    filters.push("name" + filterNameOperator.value + filterNameValue.value)
    // filter.value += "name" + filterNameOperator.value + filterNameValue.value
  }
  if(filterCreationDateValue.value && filterCreationDateOperator.value){
    filters.push("creationDate" + filterCreationDateOperator.value + filterCreationDateValue.value)
    // filter.value += "name" + filterNameOperator.value + filterNameValue.value
  }
  if(filterRealHeroValue.value && filterRealHeroOperator.value){
    filters.push("realHero" + filterRealHeroOperator.value + filterRealHeroValue.value)
    // filter.value += "realHero" + filterRealHeroOperator.value + filterRealHeroValue.value
  }
  if(filterTeamNumberValue.value && filterTeamNumberOperator.value){
    filters.push("teamNumber" + filterTeamNumberOperator.value + filterTeamNumberValue.value)
    // filter.value += "teamNumber" + filterTeamNumberOperator.value + filterTeamNumberValue.value
  }
  if(filterHasToothpickValue.value && filterHasToothpickOperator.value){
    filters.push("hasToothpick" + filterHasToothpickOperator.value + filterHasToothpickValue.value)
    // filter.value += "hasToothpick" + filterHasToothpickOperator.value + filterHasToothpickValue.value
  }
  if(filterImpactSpeedValue.value && filterImpactSpeedOperator.value){
    filters.push("impactSpeed" + filterImpactSpeedOperator.value + filterImpactSpeedValue.value)
    // filter.value += "impactSpeed" + filterImpactSpeedOperator.value + filterImpactSpeedValue.value
  }
  if(filterMinutesOfWaitingValue.value && filterMinutesOfWaitingOperator.value){
    filters.push("minutesOfWaiting" + filterMinutesOfWaitingOperator.value + filterMinutesOfWaitingValue.value)
    // filter.value += "minutesOfWaiting" + filterMinutesOfWaitingOperator.value + filterMinutesOfWaitingValue.value
  }
  if(filterWeaponTypeValue.value && filterWeaponTypeOperator.value){
    filters.push("weaponType" + filterWeaponTypeOperator.value + filterWeaponTypeValue.value)
    // filter.value += "weaponType" + filterWeaponTypeOperator.value + filterWeaponTypeValue.value
  }
  if(filterMoodValue.value && filterMoodOperator.value){
    filters.push("mood" + filterMoodOperator.value + filterMoodValue.value)
    // filter.value += "mood" + filterMoodOperator.value + filterMoodValue.value
  }
  if(filterCarNameValue.value && filterCarNameOperator.value){
    filters.push("car.name" + filterCarNameOperator.value + filterCarNameValue.value)
    // filter.value += "car.name" + filterCarNameOperator.value + filterCarNameValue.value
  }
  if(filterCarCoolValue.value && filterCarCoolOperator.value){
    filters.push("car.cool" + filterCarCoolOperator.value + filterCarCoolValue.value)
    // filter.value += "car.cool" + filterCarCoolOperator.value + filterCarCoolValue.value
  }
  if(filterCoordinatesXValue.value && filterCoordinatesXOperator.value){
    filters.push("coordinates.x" + filterCoordinatesXOperator.value + filterCoordinatesXValue.value)
    // filter.value += "coordinates.x" + filterCoordinatesXOperator.value + filterCoordinatesXValue.value
  }
  if(filterCoordinatesYValue.value && filterCoordinatesYOperator.value){
    filters.push("coordinates.y" + filterCoordinatesYOperator.value + filterCoordinatesYValue.value)
    // filter.value += "coordinates.y" + filterCoordinatesYOperator.value + filterCoordinatesYValue.value
  }

  for(let i = 0; i < filters.length; i++){
    if(i !== 0) {
      filter.value += ","
    }
    filter.value += filters[i]
  }

  console.log("filter " + filter.value )

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
  if (!validateNumber(pageSize.value)) {
    errorPageSize.value = "Размер страницы должен быть положительным целым числом"
    return false
  } else {
    errorPageSize.value = undefined
    return true
  }
}

function validatePageNumber() {
  if (!validateNumber(pageNumber.value)) {
    errorPageNumber.value = "Номер страницы должен быть положительным целым числом"
    return false
  } else {
    errorPageNumber.value = undefined
    return true
  }
}

function validateId(){
  console.log("in validate id=" + filterIdValue.value)
  if(filterIdValue.value && !validateNumber(filterIdValue.value)) {
    filterIdError.value = "Значение должно быть целым числом больше 0"
    return false
  } else {
    filterIdError = undefined
    return true
  }
}

function validateImpactSpeed(){
  if(filterImpactSpeedValue.value && (!validateFloatNumber(filterImpactSpeedValue.value) || parseFloat(filterImpactSpeedValue.value) > 981)) {
    filterImpactSpeedError.value = "Значение должно быть числом меньше 982"
    return false
  } else {
    filterImpactSpeedError = undefined
    return true
  }
}

function validateCoordinatesX(){
  if(filterCoordinatesXValue.value && !validateCoordinates(filterCoordinatesXValue.value) ) {
    filterCoordinatesXError.value = "Значение должно быть целым числом"
    return false
  } else {
    filterCoordinatesXError = undefined
    return true
  }
}

function validateCoordinatesY(){
  if(filterCoordinatesYValue.value && (!validateCoordinates(filterCoordinatesYValue.value) || parseInt(filterCoordinatesYValue.value) < -312)) {
    filterCoordinatesYError.value = "Значение должно быть целым числом больше -313"
    return false
  } else {
    filterCoordinatesYError = undefined
    return true
  }
}

function validateMinutesOfWaiting(){
  if(filterMinutesOfWaitingValue.value && !validateFloatNumber(filterMinutesOfWaitingValue.value)) {
    filterMinutesOfWaitingError.value = "Значение должно быть числом"
    return false
  } else {
    filterMinutesOfWaitingError = undefined
    return true
  }
}
function validateTeamNumber(){
  if(filterTeamNumberValue.value && !validateNumber(filterTeamNumberValue.value)) {
      filterTeamNumberError.value = "Значение должно быть целым числом больше 0"
      return false
  } else {
    filterTeamNumberError = undefined
    return true
  }
}

function validateName(){
  if(filterNameValue.value && !validateString(filterNameValue.value)) {
      filterNameError.value = "Значение должно быть не пустой строкой"
      return false
  } else {
    filterNameError = undefined
    return true
  }
}

function validateCreationDate(){ //TODO
  if(filterCreationDateValue.value && !validateString(filterCreationDateValue.value)) {
    filterCreationDateError.value = "Значение должно быть не пустой строкой"
    return false
  } else {
    filterCreationDateError = undefined
    return true
  }
}

function validateCarName(){
  if(filterCarNameValue.value && !validateString(filterCarNameValue.value)) {
    filterCarNameError.value = "Значение должно быть не пустой строкой"
    return false
  } else {
    filterCarNameError = undefined
    return true
  }
}


function validateRealHero(){
  // console.log("in validate name=" + filterIdValue.value)
  if(filterRealHeroValue.value) {
    if (filterRealHeroValue.value === "") {
      filterRealHeroValue.value = undefined
      filterRealHeroError.value = undefined
      // return true
    }
  // } else {
  //   filterRealHeroError = undefined
  //   return true
  }
  return true
}

function validateHasToothpick(){
  if(filterHasToothpickValue.value && filterHasToothpickValue.value === "") {
      filterHasToothpickValue.value = undefined
      filterHasToothpickError.value = undefined
  }
  return true
}

function validateWeaponType(){
  if(filterWeaponTypeValue.value && filterWeaponTypeValue.value === "") {
    filterWeaponTypeValue.value = undefined
    filterWeaponTypeError.value = undefined
  }
  return true
}

function validateCarCool(){
  if(filterCarCoolValue.value && filterCarCoolValue.value === "") {
    filterCarCoolValue.value = undefined
    filterCarCoolError.value = undefined
  }
  return true
}

function validateMood(){
  if(filterMoodValue.value && filterMoodValue.value === "") {
    filterMoodValue.value = undefined
    filterMoodError.value = undefined
  }
  return true
}
function nextPage(){
  pageNumber.value++
  getHumans()
}

function prevPage(){
  pageNumber.value--
  getHumans()
}




function updateTable() {
  console.log("in update table")
  if (validatePageNumber() && validatePageSize() && validateId()) {
    console.log("in update table before get")
    getHumans()
  }
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
        <th type="submit" @click.prevent="updateSorting('coordinates.x')">координата x</th>
        <th type="submit" @click.prevent="updateSorting('coordinates.y')">координата y</th>
      </tr>
      </thead>
      <tbody>
      <tr>
        <td>
          <FilterOperatorComponent @operator="filterIdOperator = $event"/>
          <input type="text" v-model="filterIdValue" @change="validateId"/>
          <span class="error">{{filterIdError}}</span>
        </td>
        <td>
          <FilterOperatorComponent @operator="filterNameOperator = $event"/>
          <input type="text" v-model="filterNameValue" @change="validateName"/>
          <span class="error">{{filterNameError}}</span>
        </td>
        <td>
<!--          creationDate-->
          <FilterOperatorComponent @operator="filterCreationDateOperator = $event"/>
          <input type="text" v-model="filterCreationDateValue" @change="validateCreationDate"/>
          <span class="error">{{filterCreationDateError}}</span>
        </td>
        <td>
          <FilterOperatorComponent @operator="filterRealHeroOperator = $event"/>
          <select v-model="filterRealHeroValue" @change="validateRealHero">
            <option value=""></option>
            <option value="true">да</option>
            <option value="false">нет</option>
          </select>
<!--          <input type="text" v-model="filterRealHeroValue" @change="validateRealHero"/>-->
          <span class="error">{{filterRealHeroError}}</span>
        </td>
        <td>
          <FilterOperatorComponent @operator="filterTeamNumberOperator= $event"/>
          <input type="text" v-model="filterTeamNumberValue" @change="validateTeamNumber"/>
          <span class="error">{{filterTeamNumberError}}</span>
        </td>
        <td>
          <FilterOperatorComponent @operator="filterHasToothpickOperator = $event"/>
          <select v-model="filterHasToothpickValue" @change="validateHasToothpick">
            <option value=""></option>
            <option value="true">да</option>
            <option value="false">нет</option>
          </select>
<!--          <input type="text" v-model="filterHasToothpickValue" @change="validateHasToothpick"/>-->
          <span class="error">{{filterHasToothpickError}}</span>
        </td>
        <td>
          <FilterOperatorComponent @operator="filterImpactSpeedOperator = $event"/>
          <input type="text" v-model="filterImpactSpeedValue" @change="validateImpactSpeed"/>
          <span class="error">{{filterImpactSpeedError}}</span>
        </td>
        <td>
          <FilterOperatorComponent @operator="filterMinutesOfWaitingOperator = $event"/>
          <input type="text" v-model="filterMinutesOfWaitingValue" @change="validateMinutesOfWaiting"/>
          <span class="error">{{filterMinutesOfWaitingError}}</span>
        </td>
        <td>
          <FilterOperatorComponent @operator="filterWeaponTypeOperator = $event"/>
          <select v-model="filterWeaponTypeValue" @change="validateWeaponType">
            <option value=""></option>
            <option value="PISTOL">пистолет</option>
            <option value="SHOTGUN">ружье</option>
            <option value="MACHINE_GUN">пулемет</option>
            <option value="BAT">бита</option>
          </select>
<!--          <input type="text" v-model="filterIdValue" @change="validateId"/>-->
          <span class="error">{{filterWeaponTypeError}}</span>
        </td>
        <td>
          <FilterOperatorComponent @operator="filterMoodOperator = $event"/>
          <select v-model="filterMoodValue" @change="validateMood">
            <option value=""></option>
            <option value="SADNESS">печаль</option>
            <option value="SORROW">грусть</option>
            <option value="RAGE">ярость</option>
          </select>
          <!--          <input type="text" v-model="filterIdValue" @change="validateId"/>-->
          <span class="error">{{filterMoodError}}</span>
        </td>
        <td>
          <FilterOperatorComponent @operator="filterCarNameOperator = $event"/>
          <input type="text" v-model="filterCarNameValue" @change="validateCarName"/>
          <span class="error">{{filterCarNameError}}</span>
        </td>
        <td>
          <FilterOperatorComponent @operator="filterCarCoolOperator = $event"/>
          <select v-model="filterCarCoolValue" @change="validateCarCool">
            <option value=""></option>
            <option value="true">да</option>
            <option value="false">нет</option>
          </select>
          <!--          <input type="text" v-model="filterHasToothpickValue" @change="validateHasToothpick"/>-->
          <span class="error">{{filterCarCoolError}}</span>
        </td>
        <td>
          <FilterOperatorComponent @operator="filterCoordinatesXOperator = $event"/>
          <input type="text" v-model="filterCoordinatesXValue" @change="validateCoordinatesX"/>
          <span class="error">{{filterCoordinatesXError}}</span>
        </td>
        <td>
          <FilterOperatorComponent @operator="filterCoordinatesYOperator = $event"/>
          <input type="text" v-model="filterCoordinatesYValue" @change="validateCoordinatesY"/>
          <span class="error">{{filterCoordinatesYError}}</span>
        </td>



      <td><input type="submit" @click.prevent="updateTable" value="применить фильтры"/></td>
      </tr>

      <tr v-for="human in humans">
        <td>{{ human.id }}</td>
        <td>{{ human.name }}</td>
        <td>{{ human.creationDate }}</td>
        <td>{{ human.realHero }}</td>
        <td>{{ human.teamNumber }}</td>
        <td>{{ human.hasToothpick }}</td>
        <td>{{ human.impactSpeed }}</td>
        <td>{{ human.minutesOfWaiting }}</td>
        <td>{{ human.weaponType }}</td>
        <td>{{ human.mood }}</td>
        <td>{{ human.car.name }}</td>
        <td>{{ human.car.cool }}</td>
        <td>{{ human.coordinates.x }}</td>
        <td>{{ human.coordinates.y }}</td>
      </tr>
      </tbody>
    </table>
    <div class="error">{{errorGet}}</div>


  </div>
</template>

<style scoped>
.error {
  color: red;
}

thead {
  background-color: deeppink;
  font-size: large;
  font-weight: bold;
}
</style>