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


function getHuman(event) {
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
      updateRes.value = "Человек обновлен!"
    }
  } catch (err) {
    console.error('Ошибка отправки:', err);
    updateError.value = err.response.data.message
  }
}

</script>

<template>
  <div class="update-container">
    <div class="update-title">Обновление данных человека</div>

    <!-- Компонент поиска -->
    <human-component
        :key="humanKey"
        :human-id="id"
        :can-delete="false"
        @human="getHuman"
    />

    <div v-if="updateRes" class="res update-success">{{ updateRes }}</div>
    <div v-if="updateError" class="error">{{ updateError }}</div>

    <!-- Форма обновления -->
    <div v-if="human && human.id" class="update-form">
      <div class="update-subtitle">Новые значения для полей (оставьте пустым, чтобы не менять):</div>

      <div class="form-grid">
        <!-- Основные поля -->
        <div class="form-group">
          <label for="name">Имя:</label>
          <input
              type="text"
              id="name"
              v-model="updatedHuman.name"
              class="update-input"
              placeholder="Новое имя"
          />
        </div>

        <div class="form-group">
          <label for="teamNumber">Номер команды:</label>
          <input
              type="number"
              id="teamNumber"
              v-model.number="updatedHuman.teamNumber"
              class="update-input"
              placeholder="Новый номер"
          />
        </div>

        <!-- Координаты -->
        <div class="form-group">
          <label for="x">Координата X:</label>
          <input
              type="number"
              id="x"
              v-model.number="updatedHuman.coordinates.x"
              class="update-input"
              placeholder="Новая координата X"
          />
        </div>

        <div class="form-group">
          <label for="y">Координата Y (больше -313):</label>
          <input
              type="number"
              id="y"
              v-model.number="updatedHuman.coordinates.y"
              :min="-312"
              class="update-input"
              placeholder="Новая координата Y"
          />
        </div>

        <!-- Детали -->
        <div class="form-group">
          <label for="impactSpeed">Скорость удара:</label>
          <input
              type="number"
              id="impactSpeed"
              v-model.number="updatedHuman.impactSpeed"
              max="981"
              class="update-input"
              placeholder="Новая скорость"
          />
        </div>

        <div class="form-group">
          <label for="minutesOfWaiting">Минуты ожидания:</label>
          <input
              type="number"
              id="minutesOfWaiting"
              v-model.number="updatedHuman.minutesOfWaiting"
              class="update-input"
              placeholder="Новое время"
          />
        </div>

        <!-- Селекты -->
        <div class="form-group">
          <label for="weaponType">Тип оружия:</label>
          <select id="weaponType" v-model="updatedHuman.weaponType" class="update-input">
            <option value="">Выберите тип оружия</option>
            <option v-for="option in weaponTypes" :value="option.value">{{ option.name }}</option>
          </select>
        </div>

        <div class="form-group">
          <label for="mood">Настроение:</label>
          <select id="mood" v-model="updatedHuman.mood" class="update-input">
            <option value="">Выберите настроение</option>
            <option v-for="option in moods" :value="option.value">{{ option.name }}</option>
          </select>
        </div>

        <!-- Машина -->
        <div class="form-group">
          <label for="carName">Название машины:</label>
          <input
              type="text"
              id="carName"
              v-model="updatedHuman.car.name"
              class="update-input"
              placeholder="Новое название"
          />
        </div>

        <!-- Чекбоксы -->
        <div class="update-checkbox">
          <input type="checkbox" id="realHero" v-model="updatedHuman.realHero"/>
          <label for="realHero">Реальный герой</label>
        </div>

        <div class="update-checkbox">
          <input id="tooth" type="checkbox" v-model="updatedHuman.hasToothpick"/>
          <label for="tooth">Есть зубочистка</label>
        </div>

        <div class="update-checkbox">
          <input type="checkbox" id="carCool" v-model="updatedHuman.car.cool"/>
          <label for="carCool">Машина крутая</label>
        </div>
      </div>

      <button
          type="button"
          class="update-btn"
          @click.prevent="update"
      >
        Обновить данные
      </button>
    </div>
  </div>

</template>

<style scoped>
.update-container {
  background: white;
  border-radius: var(--border-radius);
  padding: 2rem;
  box-shadow: var(--box-shadow);
  animation: fadeIn 0.5s ease;
}

.update-title {
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

/* Форма обновления */
.update-form {
  background: linear-gradient(135deg, #fff4e6 0%, #ffe8cc 100%);
  border-radius: 12px;
  padding: 2rem;
  border: 1px solid rgba(255, 165, 0, 0.1);
  margin-top: 2rem;
}

.update-subtitle {
  font-size: 1.1rem;
  font-weight: 600;
  color: #e65100;
  margin-bottom: 1.5rem;
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.form-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 1.5rem;
}

.form-group {
  margin-bottom: 1.25rem;
}

.form-group label {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  margin-bottom: 0.5rem;
  font-weight: 600;
  color: #333;
  font-size: 0.95rem;
}

.update-input {
  width: 100%;
  padding: 0.75rem 1rem;
  border: 2px solid #ffcc80;
  border-radius: 8px;
  font-size: 1rem;
  color: var(--dark-color);
  transition: all 0.3s ease;
  background: white;
}

.update-input:focus {
  outline: none;
  border-color: #ff9800;
  box-shadow: 0 0 0 3px rgba(255, 152, 0, 0.15);
  transform: translateY(-1px);
}

.update-input::placeholder {
  color: #ccc;
}

/* Чекбоксы для обновления */
.update-checkbox {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  margin-bottom: 1rem;
}

.update-checkbox input[type="checkbox"] {
  width: 22px;
  height: 22px;
  border-radius: 5px;
  border: 2px solid #ff9800;
  cursor: pointer;
  accent-color: #ff9800;
}

.update-checkbox label {
  margin: 0;
  cursor: pointer;
  user-select: none;
  color: #333;
}

/* Селекты */
select.update-input {
  appearance: none;
  background-repeat: no-repeat;
  background-position: right 1rem center;
  background-size: 12px;
  padding-right: 2.5rem;
}

/* Кнопка обновления */
.update-btn {
  display: block;
  width: 100%;
  max-width: 300px;
  margin: 2rem auto 0;
  padding: 1rem 2rem;
  background: linear-gradient(135deg, #ff9800, #f57c00);
  color: white;
  border: none;
  border-radius: 10px;
  font-size: 1.1rem;
  font-weight: 700;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 4px 15px rgba(255, 152, 0, 0.3);
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.75rem;
}

.update-btn:hover {
  transform: translateY(-3px);
  box-shadow: 0 6px 20px rgba(255, 152, 0, 0.4);
}

.update-btn:active {
  transform: translateY(-1px);
}

.update-btn:disabled {
  background: var(--gray);
  cursor: not-allowed;
  transform: none;
  box-shadow: none;
}

/* Сообщения */
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

.error {
  margin-top: 1rem;
  padding: 1rem;
  background-color: rgba(247, 37, 133, 0.1);
  border-radius: 6px;
  border-left: 4px solid var(--danger-color);
  color: var(--danger-color);
  font-weight: 500;
  animation: slideIn 0.3s ease;
}

.update-input:focus {
  opacity: 0.5;
}

.update-success {
  animation: updateSuccess 0.5s ease;
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

span {
  display: none;
}
</style>