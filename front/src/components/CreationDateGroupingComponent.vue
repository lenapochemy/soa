<script setup>
import {onMounted, ref} from "vue";
import axios from "axios";
import {baseHumansUrl} from "@/main.js";

let creationDate = ref()
let errorCreationDate = ref()

const getGrouping = async () => {
  try {
    const response = await axios.get(baseHumansUrl + "/creation-date");
    creationDate.value = response.data;
    errorCreationDate.value = undefined
  } catch (err) {
    creationDate.value = undefined
    errorCreationDate.value = err.response.data
  }
}

onMounted(
    getGrouping()
)
</script>

<template>
  <div class="grouping-container">
    <div class="grouping-title">Группировка по дате создания</div>

    <!-- Таблица -->
    <table>
      <thead>
      <tr>
        <th>Дата создания</th>
        <th>Количество</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="date in creationDate" :key="date.creationDate">
        <td>{{ date.creationDate }}</td>
        <td>{{ date.count }}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>
.grouping-container {
  background: white;
  border-radius: var(--border-radius);
  padding: 2rem;
  box-shadow: var(--box-shadow);
  animation: fadeIn 0.5s ease;
}

.grouping-title {
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

.grouping-title::before {
  font-size: 1.5rem;
}

/* Таблица группировки */
table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 1rem;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
}

thead {
  background: linear-gradient(135deg, #00b4db, #0083b0);
}

thead th {
  padding: 1rem;
  text-align: left;
  color: white;
  font-weight: 600;
  font-size: 0.95rem;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

tbody tr {
  border-bottom: 1px solid var(--gray-light);
  transition: all 0.2s ease;
}

tbody tr:hover {
  background-color: rgba(0, 180, 219, 0.05);
  transform: translateX(5px);
}

tbody td {
  padding: 0.9rem 1rem;
  font-size: 0.95rem;
}

/* Стили для даты */
td:first-child {
  font-weight: 500;
  color: #333;
  position: relative;
  padding-left: 1.5rem;
}

td:first-child::before {
  position: absolute;
  left: 0;
  opacity: 0.5;
}

/* Стили для количества */
td:last-child {
  font-weight: 600;
  color: var(--secondary-color);
  text-align: right;
  position: relative;
}

td:last-child::after {
  content: '';
  position: absolute;
  right: -1rem;
  top: 50%;
  transform: translateY(-50%);
  width: 10px;
  height: 10px;
  border-radius: 50%;
  background-color: var(--secondary-color);
  opacity: 0.2;
}

/* Градиент для разных строк */
tbody tr:nth-child(odd) {
  background-color: rgba(0, 180, 219, 0.02);
}

tbody tr:nth-child(even) {
  background-color: rgba(0, 131, 176, 0.02);
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
</style>