<script setup>

import HumanComponent from "@/components/HumanComponent.vue";
import {ref} from "vue";
import axios from "axios";
import {baseHumansUrl} from "@/main.js";
 let human = ref({
   id: null,
  name: '',
  coordinates: {
    x: null,
    y: null,
  },
   creationDate: null,
  realHero: false,
  teamNumber: null,
  hasToothpick: false,
  impactSpeed: null,
  minutesOfWaiting: null,
  weaponType: '',
  mood: '',
  car: {
    name: '',
    cool: false,
  },
});

let updateRes = ref()
let updateError = ref()
let humanKey = ref(0)
let id = ref()
// let human = ref()

let updatedHuman = ref({
  name: null,
  coordinates: {
    x: null,
    y: null,
  },
  realHero: null,
  teamNumber: null,
  hasToothpick: null,
  impactSpeed: null,
  minutesOfWaiting: null,
  weaponType: null,
  mood: null,
  car: {
    name: null,
    cool: null,
  },
});

const weaponTypes = [
  {value: 'PISTOL', name: 'пистолет'},
  {value: 'SHOTGUN', name: 'ружье'},
  {value: 'MACHINE_GUN', name: 'пулемет'},
  {value: 'BAT', name: 'бита'}
];

const moods = [
  {value: 'SADNESS', name: 'печаль'},
  {value: 'SORROW', name: 'грусть'},
  {value: 'RAGE', name: 'ярость'}
];

function omitNullsAndEmpty(obj) {
  if (Array.isArray(obj)) {
    return obj.map(omitNullsAndEmpty);
  } else if (obj !== null && typeof obj === 'object') {
    const filteredEntries = Object.entries(obj)
        .map(([k, v]) => [k, omitNullsAndEmpty(v)])     // рекурсивно очистить вложенные значения
        .filter(([_, v]) => v !== null &&                // убрать null
            !(typeof v === 'object' && Object.keys(v).length === 0)); // убрать пустые объекты

    if (filteredEntries.length === 0) {
      return null; // если после фильтрации объект пуст, возвращаем null
    }
    return Object.fromEntries(filteredEntries);
  }
  return obj;
}


function getHuman(event){
  human.value = event

}

const update = async () => {
  try {
    console.log(human.value)
    const response = await axios.patch(baseHumansUrl + "/" + human.value.id, omitNullsAndEmpty(updatedHuman.value))
    if (response.status === 200) {
      console.log("human updated")
      human.value = response.data
      id.value = human.value.id
      humanKey.value++
      console.log("id = " + id.value + " key = " + humanKey.value)
      updateRes.value = "Человек создан!"
    }
  } catch (err) {
    console.error('Ошибка отправки:', err);
    updateError.value = err.response.data.message
  }
}

</script>

<template>
  <human-component :key="humanKey" :human-id="id" :can-delete="false"  @human="getHuman"/>

  <div v-if="updateRes" class="res">{{ updateRes }}</div>
  <div v-if="updateError" class="error">{{ updateError }}</div>

  <div  v-if="human">
  <span>Новые значения для полей:</span>
  <form @submit.prevent="update">
    <div>
      <label for="name">Имя:</label>
      <input type="text" id="name" v-model="updatedHuman.name"/>
    </div>

    <fieldset>
      <legend>Координаты</legend>
      <label for="x">X (число):</label>
      <input type="number" id="x" v-model.number="updatedHuman.coordinates.x" />
      <label for="y">Y (больше -313):</label>
      <input type="number" id="y" v-model.number="updatedHuman.coordinates.y" :min="-312" />
    </fieldset>

    <div>
      <label>Реальный герой:</label>
      <input type="checkbox" v-model="updatedHuman.realHero"/> да
    </div>

    <div>
      <label for="teamNumber">Номер команды:</label>
      <input type="number" id="teamNumber" v-model.number="updatedHuman.teamNumber"/>
    </div>
    <div>
      <label>Есть зубочистка:</label>
      <input id="tooth" type="checkbox" v-model="updatedHuman.hasToothpick"/> да
    </div>

    <div>
      <label for="impactSpeed">Скорость удара (макс 981):</label>
      <input type="number" id="impactSpeed" v-model.number="updatedHuman.impactSpeed" max="981" />
    </div>

    <div>
      <label for="minutesOfWaiting">Минуты ожидания:</label>
      <input type="number" id="minutesOfWaiting" v-model.number="updatedHuman.minutesOfWaiting"/>
    </div>


    <div>
      <label for="weaponType">Тип оружия:</label>
      <select id="weaponType" v-model="updatedHuman.weaponType">
        <option value="">Выберите тип оружия</option>
        <option v-for="option in weaponTypes" :value="option.value">{{ option.name }}</option>
      </select>
    </div>

    <div>
      <label for="mood">Настроение:</label>
      <select id="mood" v-model="updatedHuman.mood" >
        <option value="">Выберите настроение</option>
        <option v-for="option in moods" :value="option.value">{{ option.name }}</option>
      </select>
    </div>

    <fieldset>
      <legend>Машина</legend>
      <label for="carName">Название машины:</label>
      <input type="text" id="carName" v-model="updatedHuman.car.name" />
      <label>Машина крутая :</label>
      <input type="checkbox" v-model="updatedHuman.car.cool"/> да
    </fieldset>

    <button type="submit">Обновить</button>
  </form>
  </div>
</template>

<style scoped>

</style>