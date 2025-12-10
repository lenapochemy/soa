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
      <span class="th-content">ID</span>
      <button
          v-if="sort"
          @click.prevent="updateSorting('id')"
          class="sort-btn"
          :class="{ active: sortBy === 'id' }"
          :data-order="sortBy === 'id' ? sortOrder : ''"
      >
        {{ sortButton('id') }}
      </button>
    </th>

    <th>
      <span class="th-content">имя</span>
      <button
          v-if="sort"
          @click.prevent="updateSorting('name')"
          class="sort-btn"
          :class="{ active: sortBy === 'name' }"
          :data-order="sortBy === 'name' ? sortOrder : ''"
      >
        {{ sortButton('name') }}
      </button>
    </th>
    <th>
      <span class="th-content">дата создания</span>
      <button
          v-if="sort"
          @click.prevent="updateSorting('creationDate')"
          class="sort-btn"
          :class="{ active: sortBy === 'creationDate' }"
          :data-order="sortBy === 'creationDate' ? sortOrder : ''"
      >
        {{ sortButton('creationDate') }}
      </button>
    </th>
    <th>
      <span class="th-content">герой</span>
      <button
          v-if="sort"
          @click.prevent="updateSorting('realHero')"
          class="sort-btn"
          :class="{ active: sortBy === 'realHero' }"
          :data-order="sortBy === 'realHero' ? sortOrder : ''"
      >
        {{ sortButton('realHero') }}
      </button>
    </th>
    <th>
      <span class="th-content">номер команды</span>
      <button
          v-if="sort"
          @click.prevent="updateSorting('teamNumber')"
          class="sort-btn"
          :class="{ active: sortBy === 'teamNumber' }"
          :data-order="sortBy === 'teamNumber' ? sortOrder : ''"
      >
        {{ sortButton('teamNumber') }}
      </button>
    </th>
    <th>
      <span class="th-content">есть зубочистка</span>
      <button
          v-if="sort"
          @click.prevent="updateSorting('hasToothpick')"
          class="sort-btn"
          :class="{ active: sortBy === 'hasToothpick' }"
          :data-order="sortBy === 'hasToothpick' ? sortOrder : ''"
      >
        {{ sortButton('hasToothpick') }}
      </button>
    </th>
    <th>
      <span class="th-content">скорость удара</span>
      <button
          v-if="sort"
          @click.prevent="updateSorting('impactSpeed')"
          class="sort-btn"
          :class="{ active: sortBy === 'impactSpeed' }"
          :data-order="sortBy === 'impactSpeed' ? sortOrder : ''"
      >
        {{ sortButton('impactSpeed') }}
      </button>
    </th>
    <th>
      <span class="th-content">минуты ожидания</span>
      <button
          v-if="sort"
          @click.prevent="updateSorting('minutesOfWaiting')"
          class="sort-btn"
          :class="{ active: sortBy === 'minutesOfWaiting' }"
          :data-order="sortBy === 'minutesOfWaiting' ? sortOrder : ''"
      >
        {{ sortButton('minutesOfWaiting') }}
      </button>
    </th>
    <th>
      <span class="th-content">тип оружия</span>
      <button
          v-if="sort"
          @click.prevent="updateSorting('weaponType')"
          class="sort-btn"
          :class="{ active: sortBy === 'weaponType' }"
          :data-order="sortBy === 'weaponType' ? sortOrder : ''"
      >
        {{ sortButton('weaponType') }}
      </button>
    </th>
    <th>
      <span class="th-content">настроение</span>
      <button
          v-if="sort"
          @click.prevent="updateSorting('mood')"
          class="sort-btn"
          :class="{ active: sortBy === 'mood' }"
          :data-order="sortBy === 'mood' ? sortOrder : ''"
      >
        {{ sortButton('mood') }}
      </button>
    </th>
    <th>
      <span class="th-content">название машины</span>
      <button
          v-if="sort"
          @click.prevent="updateSorting('car.name')"
          class="sort-btn"
          :class="{ active: sortBy === 'car.name' }"
          :data-order="sortBy === 'car.name' ? sortOrder : ''"
      >
        {{ sortButton('car.name') }}
      </button>
    </th>
    <th>
      <span class="th-content">крутая машина</span>
      <button
          v-if="sort"
          @click.prevent="updateSorting('car.cool')"
          class="sort-btn"
          :class="{ active: sortBy === 'car.cool' }"
          :data-order="sortBy === 'car.cool' ? sortOrder : ''"
      >
        {{ sortButton('car.cool') }}
      </button>
    </th>
    <th>
      <span class="th-content">коорд x</span>
      <button
          v-if="sort"
          @click.prevent="updateSorting('coordinates.x')"
          class="sort-btn"
          :class="{ active: sortBy === 'coordinates.x' }"
          :data-order="sortBy === 'coordinates.x' ? sortOrder : ''"
      >
        {{ sortButton('coordinates.x') }}
      </button>
    </th>
    <th>
      <span class="th-content">коорд y</span>
      <button
          v-if="sort"
          @click.prevent="updateSorting('coordinates.y')"
          class="sort-btn"
          :class="{ active: sortBy === 'coordinates.y' }"
          :data-order="sortBy === 'coordinates.y' ? sortOrder : ''"
      >
        {{ sortButton('coordinates.y') }}
      </button>
    </th>
  </tr>
  </thead>
</template>

<style scoped>
thead tr {
  position: relative;
}

th {
  position: relative;
  padding-right: 35px !important;
  white-space: nowrap;
}

.sort-btn {
  position: absolute;
  right: 8px;
  top: 50%;
  transform: translateY(-50%);
  width: 28px;
  height: 28px;
  border: none;
  border-radius: 50%;
  background-color: rgba(255, 255, 255, 0.2);
  color: white;
  font-size: 14px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.3s ease;
  padding: 0;
}

.sort-btn:hover {
  background-color: rgba(255, 255, 255, 0.3);
  transform: translateY(-50%) scale(1.1);
}

.sort-btn:active {
  transform: translateY(-50%) scale(0.95);
}

.sort-btn.active {
  background-color: var(--success-color);
  color: white;
}

.sort-btn[data-order="asc"]::before {
  content: '↑';
  font-weight: bold;
}

.sort-btn[data-order="desc"]::before {
  content: '↓';
  font-weight: bold;
}

/* Убираем стандартный стиль input[type="submit"] */
input[type="submit"] {
  display: none;
}

/* Для кнопок сортировки - стилизация как у .sort-btn */
th > *:last-child {
  display: inline-block;
  width: 28px;
  height: 28px;
  margin-left: 8px;
  vertical-align: middle;
}
</style>