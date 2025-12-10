<script setup>

import axios from "axios";
import {validateFloatNumber} from "@/validators.js";
import {ref} from "vue";
import TableDataComponent from "@/components/table/TableDataComponent.vue";
import TableHeadComponent from "@/components/table/TableHeadComponent.vue";
import {baseHumansUrl} from "@/main.js";

let greaterMinutes = ref()
let errorGreaterMinutes = ref()
let humans = ref()

function validateGreaterMinutes() {
  if (!greaterMinutes.value) {
    errorGreaterMinutes.value = "Время ожидания не может быть пустым"
    return false
  } else if (greaterMinutes.value && !validateFloatNumber(greaterMinutes.value)) {
    errorGreaterMinutes.value = "Время ожидания должно быть числом"
    return false
  } else {
    errorGreaterMinutes.value = undefined
    return true
  }
}

const getHumansWithGreaterMinutes = async () => {
  try {
    const response = await axios.get(baseHumansUrl + "/minutes-of-waiting/" + greaterMinutes.value);
    humans.value = response.data;
    errorGreaterMinutes.value = undefined
  } catch (err) {
    humans.value = undefined
    errorGreaterMinutes.value = err.response.data.message
  }
}

function updateForGreaterMinutes() {
  if (validateGreaterMinutes()) {
    getHumansWithGreaterMinutes()
  }
}
</script>

<template>
  <div class="greater-minutes-container">
    <div class="greater-title">Фильтр по времени ожидания</div>

    <div class="filter-form">
      <div class="form-group">
        <div class="filter-label">Показать элементы с временем ожидания больше:</div>
        <div class="input-wrapper">
          <input
              type="text"
              v-model="greaterMinutes"
              @change="validateGreaterMinutes"
              @keyup.enter="updateForGreaterMinutes"
              class="time-input"
              placeholder="Введите значение"
          />
          <span class="input-unit">минут</span>
        </div>
        <button
            class="filter-btn"
            @click.prevent="updateForGreaterMinutes"
        >
          Применить фильтр
        </button>
      </div>
      <div v-if="errorGreaterMinutes" class="error">{{ errorGreaterMinutes }}</div>
    </div>

    <div v-if="humans && humans.length > 0">
      <div class="table-container">
        <table>
          <table-head-component :sort="false"/>
          <tbody>
          <table-data-component
              :can-delete="true"
              :humans="humans"
              @deleted="getHumansWithGreaterMinutes"
          />
          </tbody>
        </table>
      </div>
    </div>

  </div>
</template>

<style scoped>
.greater-minutes-container {
  background: white;
  border-radius: var(--border-radius);
  padding: 2rem;
  box-shadow: var(--box-shadow);
  animation: fadeIn 0.5s ease;
}

.greater-title {
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

/* Форма фильтрации */
.filter-form {
  background: linear-gradient(135deg, #f8f9ff 0%, #eef1ff 100%);
  border-radius: 12px;
  padding: 1.5rem;
  margin-bottom: 2rem;
  border: 1px solid rgba(67, 97, 238, 0.1);
}

.form-group {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  gap: 1rem;
  margin-bottom: 1rem;
}

.filter-label {
  font-weight: 600;
  color: var(--dark-color);
  display: flex;
  align-items: center;
  gap: 0.5rem;
}


.input-wrapper {
  position: relative;
  flex: 1;
  max-width: 300px;
}

.time-input {
  width: 100%;
  padding: 0.75rem 1rem;
  border: 2px solid var(--gray-light);
  border-radius: 8px;
  font-size: 1rem;
  font-weight: 500;
  color: var(--dark-color);
  transition: all 0.3s ease;
  background: white;
}

.time-input:focus {
  outline: none;
  border-color: var(--primary-color);
  box-shadow: 0 0 0 3px rgba(67, 97, 238, 0.15);
  transform: translateY(-1px);
}

.time-input::placeholder {
  color: var(--gray);
  font-weight: normal;
}

.input-unit {
  position: absolute;
  right: 1rem;
  top: 50%;
  transform: translateY(-50%);
  color: var(--primary-color);
  font-weight: 600;
  font-size: 0.9rem;
}

.filter-btn {
  padding: 0.75rem 2rem;
  background: linear-gradient(135deg, var(--primary-color), var(--secondary-color));
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  gap: 0.75rem;
  box-shadow: 0 4px 12px rgba(67, 97, 238, 0.2);
}

.filter-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 16px rgba(67, 97, 238, 0.3);
}

.filter-btn:active {
  transform: translateY(0);
}

.filter-btn:disabled {
  background: var(--gray);
  cursor: not-allowed;
  transform: none;
  box-shadow: none;
}

/* Таблица */
.table-container {
  margin-top: 1.5rem;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
}

/* Сообщения */
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


/* Убираем стандартные элементы */
span {
  display: none;
}

input[type="submit"] {
  display: none;
}

br {
  display: none;
}
</style>