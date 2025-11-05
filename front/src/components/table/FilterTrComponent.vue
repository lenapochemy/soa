<script setup>

import FilterOperatorComponent from "@/components/table/FilterOperatorComponent.vue";
import {validateNumber, validateFloatNumber, validatePositiveNumber, validateString} from "@/validators.js";
import {ref} from "vue";

let filter = ref()
const emit = defineEmits(['update']);

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


function validateId() {
  console.log("in validate id=" + filterIdValue.value)
  if (filterIdValue.value && !validatePositiveNumber(filterIdValue.value)) {
    filterIdError.value = "Значение должно быть целым числом больше 0"
    return false
  } else {
    filterIdError = undefined
    return true
  }
}

function validateImpactSpeed() {
  if (filterImpactSpeedValue.value && (!validateFloatNumber(filterImpactSpeedValue.value) || parseFloat(filterImpactSpeedValue.value) > 981)) {
    filterImpactSpeedError.value = "Значение должно быть числом меньше 982"
    return false
  } else {
    filterImpactSpeedError = undefined
    return true
  }
}

function validateCoordinatesX() {
  if (filterCoordinatesXValue.value && !validateNumber(filterCoordinatesXValue.value)) {
    filterCoordinatesXError.value = "Значение должно быть целым числом"
    return false
  } else {
    filterCoordinatesXError = undefined
    return true
  }
}

function validateCoordinatesY() {
  if (filterCoordinatesYValue.value && (!validateNumber(filterCoordinatesYValue.value) || parseInt(filterCoordinatesYValue.value) < -312)) {
    filterCoordinatesYError.value = "Значение должно быть целым числом больше -313"
    return false
  } else {
    filterCoordinatesYError = undefined
    return true
  }
}

function validateMinutesOfWaiting() {
  if (filterMinutesOfWaitingValue.value && !validateFloatNumber(filterMinutesOfWaitingValue.value)) {
    filterMinutesOfWaitingError.value = "Значение должно быть числом"
    return false
  } else {
    filterMinutesOfWaitingError = undefined
    return true
  }
}

function validateTeamNumber() {
  if (filterTeamNumberValue.value && !validatePositiveNumber(filterTeamNumberValue.value)) {
    filterTeamNumberError.value = "Значение должно быть целым числом больше 0"
    return false
  } else {
    filterTeamNumberError = undefined
    return true
  }
}

function validateName() {
  if (filterNameValue.value && !validateString(filterNameValue.value)) {
    filterNameError.value = "Значение должно быть не пустой строкой"
    return false
  } else {
    filterNameError = undefined
    return true
  }
}

function validateCreationDate() { //TODO
  if (filterCreationDateValue.value && !validateString(filterCreationDateValue.value)) {
    filterCreationDateError.value = "Значение должно быть не пустой строкой"
    return false
  } else {
    filterCreationDateError = undefined
    return true
  }
}

function validateCarName() {
  if (filterCarNameValue.value && !validateString(filterCarNameValue.value)) {
    filterCarNameError.value = "Значение должно быть не пустой строкой"
    return false
  } else {
    filterCarNameError = undefined
    return true
  }
}


function validateRealHero() {
  // console.log("in validate name=" + filterIdValue.value)
  if (filterRealHeroValue.value) {
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

function validateHasToothpick() {
  if (filterHasToothpickValue.value && filterHasToothpickValue.value === "") {
    filterHasToothpickValue.value = undefined
    filterHasToothpickError.value = undefined
  }
  return true
}

function validateWeaponType() {
  if (filterWeaponTypeValue.value && filterWeaponTypeValue.value === "") {
    filterWeaponTypeValue.value = undefined
    filterWeaponTypeError.value = undefined
  }
  return true
}

function validateCarCool() {
  if (filterCarCoolValue.value && filterCarCoolValue.value === "") {
    filterCarCoolValue.value = undefined
    filterCarCoolError.value = undefined
  }
  return true
}

function validateMood() {
  if (filterMoodValue.value && filterMoodValue.value === "") {
    filterMoodValue.value = undefined
    filterMoodError.value = undefined
  }
  return true
}

function collectFilter() {
  filter.value = ""
  let filters = []
  if (filterIdValue.value && filterIdOperator.value) {
    filters.push("id" + filterIdOperator.value + filterIdValue.value)
  }
  if (filterNameValue.value && filterNameOperator.value) {
    filters.push("name" + filterNameOperator.value + filterNameValue.value)
  }
  if (filterCreationDateValue.value && filterCreationDateOperator.value) {
    filters.push("creationDate" + filterCreationDateOperator.value + filterCreationDateValue.value)
  }
  if (filterRealHeroValue.value && filterRealHeroOperator.value) {
    filters.push("realHero" + filterRealHeroOperator.value + filterRealHeroValue.value)
  }
  if (filterTeamNumberValue.value && filterTeamNumberOperator.value) {
    filters.push("teamNumber" + filterTeamNumberOperator.value + filterTeamNumberValue.value)
  }
  if (filterHasToothpickValue.value && filterHasToothpickOperator.value) {
    filters.push("hasToothpick" + filterHasToothpickOperator.value + filterHasToothpickValue.value)
  }
  if (filterImpactSpeedValue.value && filterImpactSpeedOperator.value) {
    filters.push("impactSpeed" + filterImpactSpeedOperator.value + filterImpactSpeedValue.value)
  }
  if (filterMinutesOfWaitingValue.value && filterMinutesOfWaitingOperator.value) {
    filters.push("minutesOfWaiting" + filterMinutesOfWaitingOperator.value + filterMinutesOfWaitingValue.value)
  }
  if (filterWeaponTypeValue.value && filterWeaponTypeOperator.value) {
    filters.push("weaponType" + filterWeaponTypeOperator.value + filterWeaponTypeValue.value)
  }
  if (filterMoodValue.value && filterMoodOperator.value) {
    filters.push("mood" + filterMoodOperator.value + filterMoodValue.value)
  }
  if (filterCarNameValue.value && filterCarNameOperator.value) {
    filters.push("car.name" + filterCarNameOperator.value + filterCarNameValue.value)
  }
  if (filterCarCoolValue.value && filterCarCoolOperator.value) {
    filters.push("car.cool" + filterCarCoolOperator.value + filterCarCoolValue.value)
  }
  if (filterCoordinatesXValue.value && filterCoordinatesXOperator.value) {
    filters.push("coordinates.x" + filterCoordinatesXOperator.value + filterCoordinatesXValue.value)
  }
  if (filterCoordinatesYValue.value && filterCoordinatesYOperator.value) {
    filters.push("coordinates.y" + filterCoordinatesYOperator.value + filterCoordinatesYValue.value)
  }

  for (let i = 0; i < filters.length; i++) {
    if (i !== 0) {
      filter.value += ","
    }
    filter.value += filters[i]
  }

  console.log("filter " + filter.value)
  emit("update", filter)

}

</script>

<template>
  <tr>
    <td>
      <FilterOperatorComponent @operator="filterIdOperator = $event"/>
      <input size="5" type="number" v-model="filterIdValue" @change="validateId"/>
      <span class="error">{{ filterIdError }}</span>
    </td>
    <td>
      <FilterOperatorComponent @operator="filterNameOperator = $event"/>
      <input size="10" type="text" v-model="filterNameValue" @change="validateName"/>
      <span class="error">{{ filterNameError }}</span>
    </td>
    <td>
      <FilterOperatorComponent @operator="filterCreationDateOperator = $event"/>
      <input size="10" type="date" v-model="filterCreationDateValue" @change="validateCreationDate"/>
      <span class="error">{{ filterCreationDateError }}</span>
    </td>
    <td>
      <FilterOperatorComponent @operator="filterRealHeroOperator = $event"/>
      <select v-model="filterRealHeroValue" @change="validateRealHero">
        <option value=""></option>
        <option value="true">да</option>
        <option value="false">нет</option>
      </select>
      <span class="error">{{ filterRealHeroError }}</span>
    </td>
    <td>
      <FilterOperatorComponent @operator="filterTeamNumberOperator= $event"/>
      <input size="5" type="number" v-model="filterTeamNumberValue" @change="validateTeamNumber"/>
      <span class="error">{{ filterTeamNumberError }}</span>
    </td>
    <td>
      <FilterOperatorComponent @operator="filterHasToothpickOperator = $event"/>
      <select v-model="filterHasToothpickValue" @change="validateHasToothpick">
        <option value=""></option>
        <option value="true">да</option>
        <option value="false">нет</option>
      </select>
      <span class="error">{{ filterHasToothpickError }}</span>
    </td>
    <td>
      <FilterOperatorComponent @operator="filterImpactSpeedOperator = $event"/>
      <input size="5" type="number" v-model="filterImpactSpeedValue" @change="validateImpactSpeed"/>
      <span class="error">{{ filterImpactSpeedError }}</span>
    </td>
    <td>
      <FilterOperatorComponent @operator="filterMinutesOfWaitingOperator = $event"/>
      <input size="5" type="number" v-model="filterMinutesOfWaitingValue" @change="validateMinutesOfWaiting"/>
      <span class="error">{{ filterMinutesOfWaitingError }}</span>
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
      <span class="error">{{ filterWeaponTypeError }}</span>
    </td>
    <td>
      <FilterOperatorComponent @operator="filterMoodOperator = $event"/>
      <select v-model="filterMoodValue" @change="validateMood">
        <option value=""></option>
        <option value="SADNESS">печаль</option>
        <option value="SORROW">грусть</option>
        <option value="RAGE">ярость</option>
      </select>
      <span class="error">{{ filterMoodError }}</span>
    </td>
    <td>
      <FilterOperatorComponent @operator="filterCarNameOperator = $event"/>
      <input size="10" type="text" v-model="filterCarNameValue" @change="validateCarName"/>
      <span class="error">{{ filterCarNameError }}</span>
    </td>
    <td>
      <FilterOperatorComponent @operator="filterCarCoolOperator = $event"/>
      <select v-model="filterCarCoolValue" @change="validateCarCool">
        <option value=""></option>
        <option value="true">да</option>
        <option value="false">нет</option>
      </select>
      <span class="error">{{ filterCarCoolError }}</span>
    </td>
    <td>
      <FilterOperatorComponent @operator="filterCoordinatesXOperator = $event"/>
      <input size="5" type="number" v-model="filterCoordinatesXValue" @change="validateCoordinatesX"/>
      <span class="error">{{ filterCoordinatesXError }}</span>
    </td>
    <td>
      <FilterOperatorComponent @operator="filterCoordinatesYOperator = $event"/>
      <input size="5" type="number" v-model="filterCoordinatesYValue" @change="validateCoordinatesY"/>
      <span class="error">{{ filterCoordinatesYError }}</span>
    </td>

    <td><input class="but" type="submit" @click.prevent="collectFilter" value="применить фильтры"/></td>
  </tr>

</template>

<style scoped>
.but {
  background-color: deeppink;
}

input[type="text"],
input[type="number"],
select {
  width: 100px;
  padding: 0.3em;
  box-sizing: border-box;
}

</style>