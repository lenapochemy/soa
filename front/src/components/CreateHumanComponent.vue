<script setup>
import {ref} from 'vue';
import axios from "axios";
import {baseHumansUrl} from "@/main.js";
import {validateFloatNumber, validateNumber, validateString} from "@/validators.js";


const weaponTypes = [
  {value: 'PISTOL', name: 'Пистолет'},
  {value: 'SHOTGUN', name: 'Ружье'},
  {value: 'MACHINE_GUN', name: 'Пулемет'},
  {value: 'BAT', name: 'Бита'}
];

const moods = [
  {value: 'SADNESS', name: 'Печаль'},
  {value: 'SORROW', name: 'Грусть'},
  {value: 'RAGE', name: 'Ярость'}
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
  console.log(human.value.name)
  if (!(human.value.name && validateString(human.value.name))) {
    nameError.value = "Имя не может быть пустым"
    return false;
  }
  nameError.value = undefined
  return true;
}

function validateX() {
  if (!(human.value.coordinates.x && validateNumber(human.value.coordinates.x))) {
    coordXError.value = "Значение должно быть целым числом"
    return false
  }
  coordXError.value = undefined
  return true
}

function validateY() {
  if (!(human.value.coordinates.y && validateNumber(human.value.coordinates.y)) && parseInt(human.value.coordinates.y) > -313) {
    coordYError.value = "Значение должно быть целым числом больше -313"
    return false
  }
  coordYError.value = undefined
  return true
}

function validateImpactSpeed() {
  if (!human.value.impactSpeed) {
    impactSpeedError.value = "Значение должно быть числом меньше 981"
    return false
  }
  if (!(validateFloatNumber(human.value.impactSpeed)) && parseFloat(human.value.impactSpeed) < 981) {
    impactSpeedError.value = "Значение должно быть числом меньше 981"
    return false
  }
  impactSpeedError.value = undefined
  return true
}

function validateMinutesOfWaiting() {
  if (!human.value.minutesOfWaiting) {
    minutesOfWaitingError.value = undefined
    return true
  }
  if (!(human.value.minutesOfWaiting && validateFloatNumber(human.value.minutesOfWaiting))) {
    minutesOfWaitingError.value = "Значение должно быть числом"
    return false
  }
  minutesOfWaitingError.value = undefined
  return true
}

function validateMood() {
  if (!human.value.mood || human.value.mood === '') {
    moodError.value = "Нужно выбрать значение"
    return false
  }
  moodError.value = undefined
  return true
}

function validateCarName() {
  if (!human.value.car.name) {
    carNameError.value = undefined
    return true;
  }
  if (!(human.value.car.name && validateString(human.value.car.name))) {
    carNameError.value = "Имя машины не может быть пустым"
    return false;
  }
  carNameError.value = undefined
  return true;
}

function validateAll() {
  return validateName && validateX() && validateY() && validateImpactSpeed() && validateMinutesOfWaiting() && validateMood() && validateCarName()
}

function submitForm() {
  if (validateAll()) {
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
    createError.value = err.response.data
  }
}

</script>

<template>
  <div class="create-container">
    <div class="create-title">Создание нового человека</div>

    <div v-if="createRes" class="res success-animation">{{ createRes }}</div>
    <div v-if="createError" class="error">{{ createError }}</div>

    <div class="creation-form">
      <div class="form-sections">
        <div class="form-section">
          <div class="section-title" data-section="basic">Основная информация</div>
          <div class="form-group">
            <label for="name">Имя:</label>
            <input
                type="text"
                id="name"
                v-model="human.name"
                @change="validateName"
                class="form-control"
                placeholder="Введите имя"
                required
            />
            <div v-if="nameError" class="error">{{ nameError }}</div>
          </div>

          <div class="form-group">
            <label for="mood">Настроение:</label>
            <select
                id="mood"
                v-model="human.mood"
                @change="validateMood"
                class="form-control"
                required
            >
              <option value="">Выберите настроение</option>
              <option v-for="option in moods" :value="option.value">{{ option.name }}</option>
            </select>
            <div v-if="moodError" class="error">{{ moodError }}</div>
          </div>

          <div class="form-group">
            <div class="checkbox-group">
              <input type="checkbox" id="realHero" v-model="human.realHero"/>
              <label for="realHero">Реальный герой</label>
            </div>
          </div>

          <div class="form-group">
            <div class="checkbox-group">
              <input id="tooth" type="checkbox" v-model="human.hasToothpick"/>
              <label for="tooth">Есть зубочистка</label>
            </div>
          </div>
        </div>

        <!-- Координаты -->
        <div class="form-section">
          <div class="section-title" data-section="coordinates">Координаты</div>
          <div class="form-group">
            <label for="x">X (целое число):</label>
            <input
                type="number"
                id="x"
                v-model.number="human.coordinates.x"
                @change="validateX"
                class="form-control"
                placeholder="Например: 100"
                required
            />
            <div v-if="coordXError" class="error">{{ coordXError }}</div>
          </div>

          <div class="form-group">
            <label for="y">Y (больше -313):</label>
            <input
                type="number"
                id="y"
                v-model.number="human.coordinates.y"
                :min="-312"
                @change="validateY"
                class="form-control"
                placeholder="Например: 50"
                required
            />
            <div v-if="coordYError" class="error">{{ coordYError }}</div>
          </div>
        </div>

        <!-- Детали -->
        <div class="form-section">
          <div class="section-title" data-section="details">Детали</div>

          <div class="form-group">
            <label for="teamNumber">Номер команды:</label>
            <input
                type="number"
                id="teamNumber"
                v-model.number="human.teamNumber"
                class="form-control"
                placeholder="Необязательно"
            />
          </div>

          <div class="form-group">
            <label for="impactSpeed">⚡ Скорость удара (макс 981):</label>
            <input
                type="text"
                id="impactSpeed"
                v-model="human.impactSpeed"
                @change="validateImpactSpeed"
                class="form-control"
                placeholder="Например: 500.5"
                required
            />
            <div v-if="impactSpeedError" class="error">{{ impactSpeedError }}</div>
          </div>

          <div class="form-group">
            <label for="minutesOfWaiting">Минуты ожидания:</label>
            <input
                type="text"
                id="minutesOfWaiting"
                v-model="human.minutesOfWaiting"
                @change="validateMinutesOfWaiting"
                class="form-control"
                placeholder="Необязательно"
            />
            <div v-if="minutesOfWaitingError" class="error">{{ minutesOfWaitingError }}</div>
          </div>

          <div class="form-group">
            <label for="weaponType">Тип оружия:</label>
            <select id="weaponType" v-model="human.weaponType" class="form-control">
              <option value="">Выберите тип оружия (опционально)</option>
              <option v-for="option in weaponTypes" :value="option.value">{{ option.name }}</option>
            </select>
          </div>
        </div>

        <!-- Машина -->
        <div class="form-section">
          <div class="section-title" data-section="car">Машина</div>

          <div class="form-group">
            <label for="carName">Название машины:</label>
            <input
                type="text"
                id="carName"
                v-model="human.car.name"
                @change="validateCarName"
                class="form-control"
                placeholder="Необязательно"
            />
            <div v-if="carNameError" class="error">{{ carNameError }}</div>
          </div>

          <div class="form-group">
            <div class="checkbox-group">
              <input type="checkbox" id="carCool" v-model="human.car.cool"/>
              <label for="carCool">Машина крутая</label>
            </div>
          </div>
        </div>
      </div>

      <button
          type="button"
          class="create-btn"
          @click.prevent="submitForm"
          :disabled="!validateAll()"
      >
        Создать человека
      </button>
    </div>
  </div>

</template>

<style scoped>
.create-container {
  background: white;
  border-radius: var(--border-radius);
  padding: 2rem;
  box-shadow: var(--box-shadow);
  animation: fadeIn 0.5s ease;
}

.create-title {
  font-size: 1.3rem;
  font-weight: 600;
  margin-bottom: 1.5rem;
  color: var(--primary-color);
  display: flex;
  align-items: center;
  gap: 0.75rem;
  padding-bottom: 0.75rem;
  border-bottom: 2px solid var(--gray-light);
}

/* Форма создания */
.creation-form {
  background: linear-gradient(135deg, #f0f9ff 0%, #e6f7ff 100%);
  border-radius: 12px;
  padding: 2rem;
  border: 1px solid rgba(0, 180, 219, 0.1);
}

.form-sections {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 2rem;
}

.form-section {
  background: white;
  border-radius: 10px;
  padding: 1.5rem;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
  border: 1px solid var(--gray-light);
}

.section-title {
  font-size: 1.1rem;
  font-weight: 600;
  color: var(--primary-color);
  margin-bottom: 1rem;
  padding-bottom: 0.5rem;
  border-bottom: 2px solid var(--gray-light);
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

/* Поля формы */
.form-group {
  margin-bottom: 1.25rem;
}

.form-group label {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  margin-bottom: 0.5rem;
  font-weight: 600;
  color: var(--dark-color);
  font-size: 0.95rem;
}

.form-control {
  width: 100%;
  padding: 0.75rem 1rem;
  border: 2px solid var(--gray-light);
  border-radius: 8px;
  font-size: 1rem;
  color: var(--dark-color);
  transition: all 0.3s ease;
  background: white;
}

.form-control:focus {
  outline: none;
  border-color: var(--primary-color);
  box-shadow: 0 0 0 3px rgba(67, 97, 238, 0.15);
  transform: translateY(-1px);
}

.form-control::placeholder {
  color: var(--gray);
}

/* Чекбоксы и селекты */
.checkbox-group {
  display: flex;
  align-items: center;
  gap: 0.75rem;
}

.checkbox-group input[type="checkbox"] {
  width: 20px;
  height: 20px;
  border-radius: 4px;
  border: 2px solid var(--gray);
  cursor: pointer;
}

.checkbox-group label {
  margin: 0;
  cursor: pointer;
  user-select: none;
}

select.form-control {
  appearance: none;
  background-repeat: no-repeat;
  background-position: right 1rem center;
  background-size: 12px;
  padding-right: 2.5rem;
}

/* Кнопка создания */
.create-btn {
  display: block;
  width: 100%;
  max-width: 300px;
  margin: 2rem auto 0;
  padding: 1rem 2rem;
  background: linear-gradient(135deg, #00b894, #00a085);
  color: white;
  border: none;
  border-radius: 10px;
  font-size: 1.1rem;
  font-weight: 700;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 4px 15px rgba(0, 184, 148, 0.3);
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.75rem;
}

.create-btn:hover {
  transform: translateY(-3px);
  box-shadow: 0 6px 20px rgba(0, 184, 148, 0.4);
}

.create-btn:active {
  transform: translateY(-1px);
}

.create-btn:disabled {
  background: var(--gray);
  cursor: not-allowed;
  transform: none;
  box-shadow: none;
}

/* Сообщения об ошибках */
.error {
  margin-top: 0.5rem;
  padding: 0.5rem 0.75rem;
  background-color: rgba(247, 37, 133, 0.1);
  border-radius: 4px;
  border-left: 3px solid var(--danger-color);
  color: var(--danger-color);
  font-size: 0.85rem;
  font-weight: 500;
  animation: slideIn 0.3s ease;
}

.res {
  margin: 1rem 0;
  padding: 1rem;
  background-color: rgba(76, 201, 240, 0.1);
  border-radius: 6px;
  border-left: 4px solid var(--success-color);
  color: var(--success-color);
  font-weight: 600;
  text-align: center;
  animation: slideIn 0.3s ease;
}

/* Валидация полей */
.form-control:invalid:not(:focus):not(:placeholder-shown) {
  border-color: var(--danger-color);
  background-color: rgba(247, 37, 133, 0.05);
}

.form-control:valid:not(:focus):not(:placeholder-shown) {
  border-color: #00b894;
  background-color: rgba(0, 184, 148, 0.05);
}

/* Убираем стандартные элементы */
fieldset {
  border: none;
  padding: 0;
  margin: 0;
}

legend {
  display: none;
}

</style>