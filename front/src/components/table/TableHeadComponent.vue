<script setup>

import {ref} from "vue";

const props = defineProps({
  sort: Boolean
})
const emit = defineEmits(['updatedSorting']);

let sortBy = ref('id')
let sortOrder = ref('asc')

function sortButton(param) {
  if (sortBy.value === param) {
    if (sortOrder.value === "asc") {
      return "↓"
    } else {
      return "↑"
    }
  } else return ""
}

function updateSorting(param) {
  if (sortBy.value === param && sortOrder.value === 'asc') {
    sortOrder.value = 'desc'
  } else sortOrder.value = 'asc'
  sortBy.value = param
  // getHumans()
  emit('updatedSorting', {
    'sortBy': sortBy.value,
    'sortOrder': sortOrder.value
  })
}


</script>

<template>
  <thead>
  <tr>
    <th>
      id
      <input v-if="sort" type="submit" @click.prevent="updateSorting('id')" :value="sortButton('id')">
    </th>
    <th>
      имя
      <input v-if="sort" type="submit" @click.prevent="updateSorting('name')" :value="sortButton('name')">
    </th>
    <th>
      дата создания
      <input v-if="sort" type="submit" @click.prevent="updateSorting('creationDate')"
             :value="sortButton('creationDate')">
    </th>
    <th>
      герой
      <input v-if="sort" type="submit" @click.prevent="updateSorting('realHero')" :value="sortButton('realHero')">
    </th>
    <th>
      номер команды
      <input v-if="sort" type="submit" @click.prevent="updateSorting('teamNumber')" :value="sortButton('teamNumber')">
    </th>
    <th>
      есть зубочистка
      <input v-if="sort" type="submit" @click.prevent="updateSorting('hasToothpick')"
             :value="sortButton('hasToothpick')">
    </th>
    <th>
      скорость удара
      <input v-if="sort" type="submit" @click.prevent="updateSorting('impactSpeed')" :value="sortButton('impactSpeed')">
    </th>
    <th>
      минуты ожидания
      <input v-if="sort" type="submit" @click.prevent="updateSorting('minutesOfWaiting')"
             :value="sortButton('minutesOfWaiting')">
    </th>
    <th>
      тип оружия
      <input v-if="sort" type="submit" @click.prevent="updateSorting('weaponType')" :value="sortButton('weaponType')">
    </th>
    <th>
      настроение
      <input v-if="sort" type="submit" @click.prevent="updateSorting('mood')" :value="sortButton('mood')">
    </th>
    <th>
      название машины
      <input v-if="sort" type="submit" @click.prevent="updateSorting('car.name')" :value="sortButton('car.name')">
    </th>
    <th>
      крутая машина
      <input v-if="sort" type="submit" @click.prevent="updateSorting('car.cool')" :value="sortButton('car.cool')">
    </th>
    <th>
      коорд x
      <input v-if="sort" type="submit" @click.prevent="updateSorting('coordinates.x')"
             :value="sortButton('coordinates.x')">
    </th>
    <th>
      коорд y
      <input v-if="sort" type="submit" @click.prevent="updateSorting('coordinates.y')"
             :value="sortButton('coordinates.y')">
    </th>
  </tr>
  </thead>
</template>

<style scoped>
input[type="submit"] {
  border-radius: 8px; /* скругление углов радиусом 8 пикселей */
  //border: none;       /* убрать рамку (опционально) */
  padding: 5px 10px; /* внутренние отступы для удобства */
  background-color: pink; /* цвет кнопки */
  cursor: pointer;    /* курсор при наведении */
}
</style>