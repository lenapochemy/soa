<script setup>
import {ref} from 'vue';
import axios from "axios";
import {baseHumansUrl} from "@/main.js";
import {validateFloatNumber, validateNumber, validateString} from "@/validators.js";



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
  weaponType: null,
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
let teamNumberError = ref()
let hasToothpickError = ref()
let impactSpeedError = ref()
let minutesOfWaitingError = ref()
let weaponTypeError = ref()
let moodError = ref()
let carNameError = ref()
let carCoolError = ref()


function validateName() {
  if(!(human.value.name && validateString(human.value.name) )){
    nameError.value = "Имя не может быть пустым"
    return false;
  }
  nameError.value = undefined
  return true;
}

function validateX(){
  if(!(human.value.coordinates.x && validateNumber(human.value.coordinates.x))){
    coordXError.value = "Значение должно быть целым числом"
    return false
  }
  coordXError.value = undefined
  return true
}

function validateY(){
  if(!(human.value.coordinates.y && validateNumber(human.value.coordinates.y)) && parseInt(human.value.coordinates.y) > -313){
    coordYError.value = "Значение должно быть целым числом больше -313"
    return false
  }
  coordYError.value = undefined
  return true
}

function validateImpactSpeed(){
  if(!human.value.impactSpeed){
    impactSpeedError.value = "Значение должно быть числом меньше 981"
    return false
  }
  if(!(validateFloatNumber(human.value.impactSpeed)) && parseFloat(human.value.impactSpeed) < 981){
    impactSpeedError.value = "Значение должно быть числом меньше 981"
    return false
  }
  impactSpeedError.value = undefined
  return true
}

function validateMinutesOfWaiting(){
  if(!human.value.minutesOfWaiting){
    minutesOfWaitingError.value = undefined
    return true
  }
  if(!(human.value.minutesOfWaiting && validateFloatNumber(human.value.minutesOfWaiting))){
    minutesOfWaitingError.value = "Значение должно быть числом"
    return false
  }
  minutesOfWaitingError.value = undefined
  return true
}

function validateMood(){
  if(!human.value.mood || human.value.mood === ''){
    moodError.value = "Нужно выбрать значение"
    return false
  }
  moodError.value = undefined
  return true
}

function validateCarName(){
  if(!human.value.car.name){
    carNameError.value = undefined
    return true;
  }
  if(!(human.value.car.name && validateString(human.value.car.name) )){
    carNameError.value = "Имя машины не может быть пустым"
    return false;
  }
  carNameError.value = undefined
  return true;
}

function validateAll(){
  return validateName && validateX() && validateY() && validateImpactSpeed() && validateMinutesOfWaiting() && validateMood() && validateCarName()
}

function submitForm() {
  if(validateAll()) {
    const createdObject = JSON.parse(JSON.stringify(human.value));
    console.log('Созданный объект:', createdObject);
    submitHuman()
  }
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
      <input type="text" id="name" v-model="human.name" @change="validateName" required/>
      <div v-if="nameError" class="error">{{nameError}}</div>
    </div>

    <fieldset>
      <legend>Координаты</legend>
      <label for="x">X:</label>
      <input type="number" id="x" v-model.number="human.coordinates.x" @change="validateX" required/>
      <div v-if="coordXError" class="error">{{coordXError}}</div>
      <label for="y">Y (больше -313):</label>
      <input type="number" id="y" v-model.number="human.coordinates.y" :min="-312" @change="validateY" required/>
      <div v-if="coordYError" class="error">{{coordYError}}</div>
    </fieldset>

    <div>
      <label>Реальный герой:</label>
      <input type="checkbox" v-model="human.realHero" /> да
      <div v-if="realHeroError" class="error">{{realHeroError}}</div>
    </div>

    <div>
      <label for="teamNumber">Номер команды:</label>
      <input type="number" id="teamNumber" v-model.number="human.teamNumber"/>
      <div v-if="teamNumberError" class="error">{{teamNumberError}}</div>
    </div>
    <div>
      <label>Есть зубочистка:</label>
      <input id="tooth" type="checkbox" v-model="human.hasToothpick"/> да
      <div v-if="hasToothpickError" class="error">{{hasToothpickError}}</div>
    </div>

    <div>
      <label for="impactSpeed">Скорость удара (макс 981):</label>
      <input type="text" id="impactSpeed" v-model="human.impactSpeed"  @change="validateImpactSpeed" required/>
      <div v-if="impactSpeedError" class="error">{{impactSpeedError}}</div>
    </div>

    <div>
      <label for="minutesOfWaiting">Минуты ожидания:</label>
      <input type="text" id="minutesOfWaiting" v-model="human.minutesOfWaiting" @change="validateMinutesOfWaiting"/>
      <div v-if="minutesOfWaitingError" class="error">{{minutesOfWaitingError}}</div>
    </div>


    <div>
      <label for="weaponType">Тип оружия:</label>
      <select id="weaponType" v-model="human.weaponType">
        <option value="">Выберите тип оружия</option>
        <option v-for="option in weaponTypes" :value="option.value">{{ option.name }}</option>
      </select>
      <div v-if="weaponTypeError" class="error">{{weaponTypeError}}</div>
    </div>

    <div>
      <label for="mood">Настроение:</label>
      <select id="mood" v-model="human.mood" @change="validateMood" required>
        <option value="">Выберите настроение</option>
        <option v-for="option in moods" :value="option.value">{{ option.name }}</option>
      </select>
      <div v-if="moodError" class="error">{{moodError}}</div>
    </div>

    <fieldset>
      <legend>Машина</legend>
      <label for="carName">Название машины:</label>
      <input type="text" id="carName" v-model="human.car.name" @change="validateCarName"/>
      <div v-if="carNameError" class="error">{{carNameError}}</div>
      <label>Машина крутая :</label>
      <input type="checkbox" v-model="human.car.cool"/> да
      <div v-if="carCoolError" class="error">{{carCoolError}}</div>
    </fieldset>

    <button type="submit" >Создать</button>
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