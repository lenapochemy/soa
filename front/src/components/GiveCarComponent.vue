<script setup>
import axios from "axios";
import {ref} from "vue";
import {baseTeamsUrl} from "@/main.js"

const props = defineProps({
  teams: Array
})

const emit = defineEmits(['addedCars']);


let teamId = ref("")
let errorTeamId = ref()
let giveCarsRes = ref()
let giveCarsError = ref()

function validateTeamId() {
  console.log("aaaa in validate team id " + teamId.value)
  if (!teamId.value || (teamId.value && teamId.value === "")) {
    errorTeamId.value = "Нужно выбрать команду из списка"
    return false
  } else {
    errorTeamId = undefined
    return true
  }
}

const giveCars = async () => {
  if (validateTeamId()) {
    try {
      const response = await axios.post(baseTeamsUrl + teamId.value + "/car/add");
      emit('addedCars')
    } catch (err) {
      giveCarsError.value = err.response.data.message
    }
  }
}
</script>

<template>
  <div class="car-container">
    <div class="car-title">Выдача Красных Лад командам</div>
    <div class="form-group">
      <select
          v-model="teamId"
          @change="validateTeamId"
          class="team-select"
      >
        <option value="">Выберите команду</option>
        <option v-for="team in teams" :value="team">
          Команда {{ team }}
        </option>
      </select>
      <button
          class="give-btn"
          @click.prevent="giveCars"
      >
        Выдать машины
      </button>
    </div>
    <div v-if="errorTeamId" class="error">{{ errorTeamId }}</div>
    <div v-if="giveCarsRes" class="res">{{ giveCarsRes }}</div>
    <div v-if="giveCarsError" class="error">{{ giveCarsError }}</div>
  </div>
</template>

<style scoped>
.car-container {
  background: linear-gradient(135deg, #ffecd2 0%, #fcb69f 100%);
  border-radius: var(--border-radius);
  padding: 1.5rem;
  margin: 2rem 0;
  border: 1px solid rgba(255, 182, 159, 0.3);
  box-shadow: var(--box-shadow);
  position: relative;
  overflow: hidden;
}

.car-container::before {
  position: absolute;
  top: 1rem;
  right: 1rem;
  font-size: 2rem;
  opacity: 0.2;
}

.car-title {
  font-size: 1.2rem;
  font-weight: 600;
  margin-bottom: 1rem;
  color: #e65100;
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.car-title::before {
  font-size: 1.5rem;
}

.form-group {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  gap: 1rem;
  margin-bottom: 1rem;
}

.team-select {
  padding: 0.6rem 2.5rem 0.6rem 0.75rem;
  border: 1px solid #ff9966;
  border-radius: 6px;
  font-size: 1rem;
  background-color: white;
  background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='16' height='16' fill='%23e65100' viewBox='0 0 16 16'%3E%3Cpath d='M7.247 11.14L2.451 5.658C1.885 5.013 2.345 4 3.204 4h9.592a1 1 0 0 1 .753 1.659l-4.796 5.48a1 1 0 0 1-1.506 0z'/%3E%3C/svg%3E");
  background-repeat: no-repeat;
  background-position: right 0.75rem center;
  background-size: 12px;
  cursor: pointer;
  transition: all 0.3s ease;
  min-width: 200px;
  color: #333;
}

.team-select:hover {
  border-color: #ff6600;
  box-shadow: 0 2px 8px rgba(255, 102, 0, 0.1);
}

.team-select:focus {
  outline: none;
  border-color: #ff6600;
  box-shadow: 0 0 0 3px rgba(255, 102, 0, 0.2);
}

.give-btn {
  padding: 0.6rem 1.5rem;
  background: linear-gradient(135deg, #ff6600, #ff3300);
  color: white;
  border: none;
  border-radius: 6px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  gap: 0.5rem;
  box-shadow: 0 2px 8px rgba(255, 102, 0, 0.2);
}

.give-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(255, 102, 0, 0.3);
}

.give-btn:active {
  transform: translateY(0);
}

.give-btn::before {
  font-size: 1.2rem;
}

.give-btn:disabled {
  background: var(--gray);
  cursor: not-allowed;
  transform: none;
  box-shadow: none;
}

/* Сообщения */
.res {
  margin-top: 1rem;
  padding: 1rem;
  background-color: rgba(76, 201, 240, 0.1);
  border-radius: 6px;
  border-left: 4px solid var(--success-color);
  color: var(--success-color);
  font-weight: 500;
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

/* Убираем стандартные элементы */
span {
  display: none;
}

input[type="submit"] {
  display: none;
}
</style>