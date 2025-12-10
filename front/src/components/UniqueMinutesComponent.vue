<script setup>

import {onMounted, ref} from "vue";
import axios from "axios";
import {baseHumansUrl} from "@/main.js";

let unique = ref()
let errorUnique = ref()


const getUniqueMinutes = async () => {
  try {
    const response = await axios.get(baseHumansUrl + "/minutes-of-waiting/unique");
    unique.value = response.data;
    errorUnique.value = undefined
  } catch (err) {
    unique.value = undefined
    errorUnique.value = err.response.data.message
  }
}

onMounted(
    getUniqueMinutes()
)

</script>

<template>
  <div class="unique-container">
    <div class="unique-title">Уникальные значения времени ожидания</div>

    <!-- Таблица -->
    <div class="unique-table-container">
      <table class="unique-table">
        <thead>
        <tr>
          <th>Время ожидания (минуты)</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="minutes in unique" :key="minutes">
          <td>{{ minutes }}</td>
        </tr>
        </tbody>
      </table>
    </div>

    <!-- Сообщение об ошибке -->
    <div v-if="errorUnique" class="error">
      Ошибка: {{ errorUnique }}
    </div>

    <!-- Пустое состояние -->
    <div v-if="unique && unique.length === 0" class="empty-state">
      <div>Нет уникальных значений времени ожидания</div>
    </div>
  </div>

</template>

<style scoped>
.unique-container {
  background: white;
  border-radius: var(--border-radius);
  padding: 2rem;
  box-shadow: var(--box-shadow);
  animation: fadeIn 0.5s ease;
}

.unique-title {
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

.unique-table-container {
  margin-top: 1.5rem;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
}

.unique-table {
  width: 100%;
  border-collapse: collapse;
}

.unique-table thead {
  background: linear-gradient(135deg, #00b4db, #0083b0);
}

.unique-table thead th {
  padding: 1rem;
  text-align: left;
  color: white;
  font-weight: 600;
  font-size: 0.95rem;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.unique-table tbody tr {
  border-bottom: 1px solid var(--gray-light);
  transition: all 0.2s ease;
}

.unique-table tbody tr:hover {
  background-color: rgba(0, 180, 219, 0.05);
}

.unique-table tbody td {
  padding: 1rem;
  font-size: 1rem;
  font-weight: 500;
  color: #333;
  position: relative;
  padding-left: 0.5rem;
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
}

/* Убираем стандартные элементы */
span {
  display: none;
}

br {
  display: none;
}
</style>