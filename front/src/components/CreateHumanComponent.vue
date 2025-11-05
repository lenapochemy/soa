<script setup>
import {ref} from 'vue';
import axios from "axios";
import {baseHumansUrl} from "@/main.js";



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

const human = ref({
  name: '',
  coordinates: {
    x: null,
    y: null,
  },
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

let createRes = ref()
let createError = ref()

let nameError = ref()
let coordXError = ref()
let coordYError = ref()
let realHeroError = ref()
let minutesOfWaitingError = ref()
let teamNumberError = ref()


function submitForm() {
  if (human.value.coordinates.y <= -313) {
    return;
  }
  if (!human.value.name || !human.value.mood || !human.value.impactSpeed || !human.value.car.name) {
    return;
  }

  const createdObject = JSON.parse(JSON.stringify(human.value));
  console.log('Созданный объект:', createdObject);
  submitHuman()

}

const submitHuman = async () => {
  try {
    console.log(human.value)
    const response = await axios.post(baseHumansUrl, human.value)
    if (response.status === 201) {
      console.log("human created")
      createRes.value = "Человек создан!"
    }
  } catch (err) {
    console.error('Ошибка отправки:', err);
    createError.value = err.response.data.message
  }
}
</script>

<template>

  <div v-if="createRes" class="res">{{ createRes }}</div>
  <div v-if="createError" class="error">{{ createError }}</div>

  <form @submit.prevent="submitForm">
    <div>
      <label for="name">Имя:</label>
      <input type="text" id="name" v-model="human.name" required/>
    </div>

    <fieldset>
      <legend>Координаты</legend>
      <label for="x">X (число):</label>
      <input type="number" id="x" v-model.number="human.coordinates.x" required/>
      <label for="y">Y (больше -313):</label>
      <input type="number" id="y" v-model.number="human.coordinates.y" :min="-312" required/>
    </fieldset>

    <div>
      <label>Реальный герой:</label>
      <input type="checkbox" v-model="human.realHero"/> да
    </div>

    <div>
      <label for="teamNumber">Номер команды:</label>
      <input type="number" id="teamNumber" v-model.number="human.teamNumber"/>
    </div>
    <div>
      <label>Есть зубочистка:</label>
      <input id="tooth" type="checkbox" v-model="human.hasToothpick"/> да
    </div>

    <div>
      <label for="impactSpeed">Скорость удара (макс 981):</label>
      <input type="number" id="impactSpeed" v-model.number="human.impactSpeed" max="981" required/>
    </div>

    <div>
      <label for="minutesOfWaiting">Минуты ожидания:</label>
      <input type="number" id="minutesOfWaiting" v-model.number="human.minutesOfWaiting"/>
    </div>


    <div>
      <label for="weaponType">Тип оружия:</label>
      <select id="weaponType" v-model="human.weaponType">
        <option value="">Выберите тип оружия</option>
        <option v-for="option in weaponTypes" :value="option.value">{{ option.name }}</option>
      </select>
    </div>

    <div>
      <label for="mood">Настроение:</label>
      <select id="mood" v-model="human.mood" required>
        <option value="">Выберите настроение</option>
        <option v-for="option in moods" :value="option.value">{{ option.name }}</option>
      </select>
    </div>

    <fieldset>
      <legend>Машина</legend>
      <label for="carName">Название машины:</label>
      <input type="text" id="carName" v-model="human.car.name" />
      <label>Машина крутая :</label>
      <input type="checkbox" v-model="human.car.cool"/> да
    </fieldset>

    <button type="submit">Создать</button>
  </form>



</template>

<style scoped>
input[type="text"],
input[type="number"],
select {
  width: 100%;
  padding: 0.3em;
  box-sizing: border-box;
}

</style>