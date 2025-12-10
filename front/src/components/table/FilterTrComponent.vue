<script setup>

import FilterOperatorComponent from "@/components/table/FilterOperatorComponent.vue";
import {validateNumber, validateFloatNumber, validatePositiveNumber, validateString} from "@/validators.js";
import {ref} from "vue";

let filter = ref()
const emit = defineEmits(['update']);

let filterIdOperator = ref()
let filterNameOperator = ref()
let filterCreationDateOperator = ref()
let filterRealHeroOperator = ref("")
let filterTeamNumberOperator = ref()
let filterHasToothpickOperator = ref("")
let filterImpactSpeedOperator = ref()
let filterMinutesOfWaitingOperator = ref()
let filterWeaponTypeOperator = ref("")
let filterMoodOperator = ref("")
let filterCarNameOperator = ref()
let filterCarCoolOperator = ref("")
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
  <tr class="filter-row">
    <td>
      <div class="filter-cell">
        <div class="filter-operator-wrapper">
          <FilterOperatorComponent @operator="filterIdOperator = $event"/>
        </div>
        <input
            type="number"
            v-model="filterIdValue"
            @change="validateId"
            placeholder="ID"
        />
        <span v-if="filterIdError" class="error">{{ filterIdError }}</span>
      </div>
    </td>
    <td>
      <div class="filter-cell">
        <div class="filter-operator-wrapper">
          <FilterOperatorComponent @operator="filterNameOperator = $event"/>
        </div>
        <input
            type="text"
            v-model="filterNameValue"
            @change="validateName"
            placeholder="Имя"
        />
        <span v-if="filterNameError" class="error">{{ filterNameError }}</span>
      </div>
    </td>
    <td>
      <div class="filter-cell">
        <div class="filter-operator-wrapper">
          <FilterOperatorComponent @operator="filterCreationDateOperator = $event"/>
        </div>
        <input
            type="date"
            v-model="filterCreationDateValue"
            @change="validateCreationDate"
            placeholder="Дата создания"
        />
        <span v-if="filterCreationDateError" class="error">{{ filterCreationDateError }}</span>
      </div>
    </td>
    <!--    <td>-->
    <!--      <FilterOperatorComponent @operator="filterCreationDateOperator = $event"/>-->
    <!--      <input size="10" type="date" v-model="filterCreationDateValue" @change="validateCreationDate"/>-->
    <!--      <span class="error">{{ filterCreationDateError }}</span>-->
    <!--    </td>-->
    <td>
      <div class="filter-cell">
        <div class="filter-operator-wrapper">
          <FilterOperatorComponent @operator="filterRealHeroOperator = $event"/>
        </div>
        <!--        <input-->
        <!--            type="number"-->
        <!--            v-model="filterRealHeroValue"-->
        <!--            @change="validateRealHero"-->
        <!--            placeholder="Name"-->
        <!--        />-->
        <select v-model="filterRealHeroValue" @change="validateRealHero">
          <option value="">Реальный герой</option>
          <option value="true">Да</option>
          <option value="false">Нет</option>
        </select>
        <span v-if="filterRealHeroError" class="error">{{ filterRealHeroError }}</span>
      </div>
    </td>
    <!--    <td>-->
    <!--      <FilterOperatorComponent @operator="filterRealHeroOperator = $event"/>-->
    <!--      <select v-model="filterRealHeroValue" @change="validateRealHero">-->
    <!--        <option value=""></option>-->
    <!--        <option value="true">да</option>-->
    <!--        <option value="false">нет</option>-->
    <!--      </select>-->
    <!--      <span class="error">{{ filterRealHeroError }}</span>-->
    <!--    </td>-->
    <td>
      <div class="filter-cell">
        <div class="filter-operator-wrapper">
          <FilterOperatorComponent @operator="filterTeamNumberOperator = $event"/>
        </div>
        <input
            type="number"
            v-model="filterTeamNumberValue"
            @change="validateTeamNumber"
            placeholder="Номер команды"
        />
        <span v-if="filterTeamNumberError" class="error">{{ filterTeamNumberError }}</span>
      </div>
    </td>
    <!--    <td>-->
    <!--      <FilterOperatorComponent @operator="filterTeamNumberOperator= $event"/>-->
    <!--      <input size="5" type="number" v-model="filterTeamNumberValue" @change="validateTeamNumber"/>-->
    <!--      <span class="error">{{ filterTeamNumberError }}</span>-->
    <!--    </td>-->
    <td>
      <div class="filter-cell">
        <div class="filter-operator-wrapper">
          <FilterOperatorComponent @operator="filterHasToothpickOperator = $event"/>
        </div>
        <!--        <input-->
        <!--            type="number"-->
        <!--            v-model="filterHasToothpickValue"-->
        <!--            @change="validateHasToothpick"-->
        <!--            placeholder="Name"-->
        <!--        />-->
        <select v-model="filterHasToothpickValue" @change="validateHasToothpick">
          <option value="">Зубочистка</option>
          <option value="true">Да</option>
          <option value="false">Нет</option>
        </select>
        <span v-if="filterHasToothpickError" class="error">{{ filterHasToothpickError }}</span>
      </div>
    </td>
    <!--    <td>-->
    <!--      <FilterOperatorComponent @operator="filterHasToothpickOperator = $event"/>-->
    <!--      <select v-model="filterHasToothpickValue" @change="validateHasToothpick">-->
    <!--        <option value=""></option>-->
    <!--        <option value="true">да</option>-->
    <!--        <option value="false">нет</option>-->
    <!--      </select>-->
    <!--      <span class="error">{{ filterHasToothpickError }}</span>-->
    <!--    </td>-->
    <td>
      <div class="filter-cell">
        <div class="filter-operator-wrapper">
          <FilterOperatorComponent @operator="filterImpactSpeedOperator = $event"/>
        </div>
        <input
            type="number"
            v-model="filterImpactSpeedValue"
            @change="validateImpactSpeed"
            placeholder="Скорость удара"
        />
        <span v-if="filterImpactSpeedError" class="error">{{ filterImpactSpeedError }}</span>
      </div>
    </td>
    <!--    <td>-->
    <!--      <FilterOperatorComponent @operator="filterImpactSpeedOperator = $event"/>-->
    <!--      <input size="5" type="number" v-model="filterImpactSpeedValue" @change="validateImpactSpeed"/>-->
    <!--      <span class="error">{{ filterImpactSpeedError }}</span>-->
    <!--    </td>-->
    <td>
      <div class="filter-cell">
        <div class="filter-operator-wrapper">
          <FilterOperatorComponent @operator="filterMinutesOfWaitingOperator = $event"/>
        </div>
        <input
            type="number"
            v-model="filterMinutesOfWaitingValue"
            @change="validateMinutesOfWaiting"
            placeholder="Минуты ожидания"
        />
        <span v-if="filterMinutesOfWaitingError" class="error">{{ filterMinutesOfWaitingError }}</span>
      </div>
    </td>
    <!--    <td>-->
    <!--      <FilterOperatorComponent @operator="filterMinutesOfWaitingOperator = $event"/>-->
    <!--      <input size="5" type="number" v-model="filterMinutesOfWaitingValue" @change="validateMinutesOfWaiting"/>-->
    <!--      <span class="error">{{ filterMinutesOfWaitingError }}</span>-->
    <!--    </td>-->
    <td>
      <div class="filter-cell">
        <div class="filter-operator-wrapper">
          <FilterOperatorComponent @operator="filterWeaponTypeOperator = $event"/>
        </div>
        <!--        <input-->
        <!--            type="number"-->
        <!--            v-model="filterWeaponTypeValue"-->
        <!--            @change="validateWeaponType"-->
        <!--            placeholder="Name"-->
        <!--        />-->
        <select v-model="filterWeaponTypeValue" @change="validateWeaponType">
          <option value="">Тип оружия</option>
          <option value="PISTOL">Пистолет</option>
          <option value="SHOTGUN">Ружье</option>
          <option value="MACHINE_GUN">Пулемет</option>
          <option value="BAT">Бита</option>
        </select>
        <span v-if="filterWeaponTypeError" class="error">{{ filterWeaponTypeError }}</span>
      </div>
    </td>
    <!--    <td>-->
    <!--      <FilterOperatorComponent @operator="filterWeaponTypeOperator = $event"/>-->
    <!--      <select v-model="filterWeaponTypeValue" @change="validateWeaponType">-->
    <!--        <option value=""></option>-->
    <!--        <option value="PISTOL">пистолет</option>-->
    <!--        <option value="SHOTGUN">ружье</option>-->
    <!--        <option value="MACHINE_GUN">пулемет</option>-->
    <!--        <option value="BAT">бита</option>-->
    <!--      </select>-->
    <!--      <span class="error">{{ filterWeaponTypeError }}</span>-->
    <!--    </td>-->
    <td>
      <div class="filter-cell">
        <div class="filter-operator-wrapper">
          <FilterOperatorComponent @operator="filterMoodOperator = $event"/>
        </div>
        <!--        <input-->
        <!--            type="number"-->
        <!--            v-model="filterNameValue"-->
        <!--            @change="validateName"-->
        <!--            placeholder="Name"-->
        <!--        />-->
        <select v-model="filterMoodValue" @change="validateMood">
          <option value="">Настроение</option>
          <option value="SADNESS">Печаль</option>
          <option value="SORROW">Грусть</option>
          <option value="RAGE">Ярость</option>
        </select>
        <span v-if="filterMoodError" class="error">{{ filterMoodError }}</span>
      </div>
    </td>
    <!--    <td>-->
    <!--      <FilterOperatorComponent @operator="filterMoodOperator = $event"/>-->
    <!--      <select v-model="filterMoodValue" @change="validateMood">-->
    <!--        <option value=""></option>-->
    <!--        <option value="SADNESS">печаль</option>-->
    <!--        <option value="SORROW">грусть</option>-->
    <!--        <option value="RAGE">ярость</option>-->
    <!--      </select>-->
    <!--      <span class="error">{{ filterMoodError }}</span>-->
    <!--    </td>-->
    <td>
      <div class="filter-cell">
        <div class="filter-operator-wrapper">
          <FilterOperatorComponent @operator="filterCarNameOperator = $event"/>
        </div>
        <input
            type="text"
            v-model="filterCarNameValue"
            @change="validateCarName"
            placeholder="Название машины"
        />
        <span v-if="filterCarNameError" class="error">{{ filterCarNameError }}</span>
      </div>
    </td>
    <!--    <td>-->
    <!--      <FilterOperatorComponent @operator="filterCarNameOperator = $event"/>-->
    <!--      <input size="10" type="text" v-model="filterCarNameValue" @change="validateCarName"/>-->
    <!--      <span class="error">{{ filterCarNameError }}</span>-->
    <!--    </td>-->
    <td>
      <div class="filter-cell">
        <div class="filter-operator-wrapper">
          <FilterOperatorComponent @operator="filterCarCoolOperator = $event"/>
        </div>
        <!--        <input-->
        <!--            type="number"-->
        <!--            v-model="filterNameValue"-->
        <!--            @change="validateName"-->
        <!--            placeholder="Name"-->
        <!--        />-->
        <select v-model="filterCarCoolValue" @change="validateCarCool">
          <option value="">Крутая машина</option>
          <option value="true">Да</option>
          <option value="false">Нет</option>
        </select>
        <span v-if="filterCarCoolError" class="error">{{ filterCarCoolError }}</span>
      </div>
    </td>
    <!--    <td>-->
    <!--      <FilterOperatorComponent @operator="filterCarCoolOperator = $event"/>-->
    <!--      <select v-model="filterCarCoolValue" @change="validateCarCool">-->
    <!--        <option value=""></option>-->
    <!--        <option value="true">да</option>-->
    <!--        <option value="false">нет</option>-->
    <!--      </select>-->
    <!--      <span class="error">{{ filterCarCoolError }}</span>-->
    <!--    </td>-->
    <td>
      <div class="filter-cell">
        <div class="filter-operator-wrapper">
          <FilterOperatorComponent @operator="filterCoordinatesXOperator = $event"/>
        </div>
        <input
            type="number"
            v-model="filterCoordinatesXValue"
            @change="validateCoordinatesX"
            placeholder="Координата X"
        />
        <span v-if="filterCoordinatesXError" class="error">{{ filterCoordinatesXError }}</span>
      </div>
    </td>
    <!--    <td>-->
    <!--      <FilterOperatorComponent @operator="filterCoordinatesXOperator = $event"/>-->
    <!--      <input size="5" type="number" v-model="filterCoordinatesXValue" @change="validateCoordinatesX"/>-->
    <!--      <span class="error">{{ filterCoordinatesXError }}</span>-->
    <!--    </td>-->
    <td>
      <div class="filter-cell">
        <div class="filter-operator-wrapper">
          <FilterOperatorComponent @operator="filterCoordinatesYOperator = $event"/>
        </div>
        <input
            type="number"
            v-model="filterCoordinatesYValue"
            @change="validateCoordinatesY"
            placeholder="Координата Y"
        />
        <span v-if="filterCoordinatesYError" class="error">{{ filterCoordinatesYError }}</span>
      </div>
    </td>
    <!--    <td>-->
    <!--      <FilterOperatorComponent @operator="filterCoordinatesYOperator = $event"/>-->
    <!--      <input size="5" type="number" v-model="filterCoordinatesYValue" @change="validateCoordinatesY"/>-->
    <!--      <span class="error">{{ filterCoordinatesYError }}</span>-->
    <!--    </td>-->


    <td colspan="2">
      <button
          class="filter-apply-btn"
          @click.prevent="collectFilter"
      >
        Применить фильтры
      </button>
    </td>

  </tr>

</template>

<style scoped>
/* Основной стиль строки фильтра */
tr {
  background-color: #f8f9ff !important;
  border-bottom: 2px solid var(--gray-light) !important;
}

tr:hover {
  background-color: #f0f2ff !important;
}

/* Ячейки фильтра */
td {
  padding: 0.75rem 0.5rem !important;
  vertical-align: top !important;
  border-right: 1px solid var(--gray-light) !important;
}

td:last-child {
  border-right: none !important;
}

/* Контейнер для фильтра */
.filter-cell {
  display: flex;
  flex-direction: column;
  gap: 8px;
  min-height: 80px;
}

/* Стили для полей ввода */
input[type="text"],
input[type="number"],
input[type="date"],
select {
  width: 100%;
  min-width: 120px;
  max-width: 180px;
  padding: 0.5rem 0.75rem;
  border: 1px solid var(--gray);
  border-radius: 6px;
  font-size: 0.9rem;
  transition: all 0.3s ease;
  background-color: white;
  box-shadow: inset 0 1px 2px rgba(0, 0, 0, 0.05);
}

input[type="text"]:focus,
input[type="number"]:focus,
input[type="date"]:focus,
select:focus {
  outline: none;
  border-color: var(--primary-color);
  box-shadow: 0 0 0 3px rgba(67, 97, 238, 0.15);
  background-color: #fff;
}

/* Стили для селектов */
select {
  cursor: pointer;
  appearance: none;
  background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='16' height='16' fill='%234361ee' viewBox='0 0 16 16'%3E%3Cpath d='M7.247 11.14L2.451 5.658C1.885 5.013 2.345 4 3.204 4h9.592a1 1 0 0 1 .753 1.659l-4.796 5.48a1 1 0 0 1-1.506 0z'/%3E%3C/svg%3E");
  background-repeat: no-repeat;
  background-position: right 0.75rem center;
  background-size: 12px;
  padding-right: 2rem;
}

/* Кнопка применения фильтров */
.filter-apply-btn {
  background: linear-gradient(135deg, var(--primary-color), var(--secondary-color));
  color: white;
  border: none;
  border-radius: var(--border-radius);
  padding: 0.75rem 1.5rem;
  font-weight: 600;
  font-size: 0.95rem;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
  margin: 0.5rem auto;
  min-width: 200px;
}

.filter-apply-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(67, 97, 238, 0.3);
}

.filter-apply-btn:active {
  transform: translateY(0);
}


/* Сообщения об ошибках */
.error {
  color: var(--danger-color);
  font-size: 0.8rem;
  margin-top: 0.25rem;
  padding: 0.25rem 0.5rem;
  background-color: rgba(247, 37, 133, 0.05);
  border-radius: 4px;
  border-left: 3px solid var(--danger-color);
}

/* Для последней ячейки с кнопкой */
td:last-child {
  text-align: center;
  vertical-align: middle !important;
}

/* Стиль для FilterOperatorComponent */
.filter-operator-wrapper {
  display: inline-flex;
  align-items: center;
  gap: 4px;
}

</style>