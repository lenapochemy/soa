<script setup>

import axios from "axios";
import {baseTeamsUrl} from "@/main.js";
import {ref} from "vue";

const props = defineProps({
  teamId: Number,
  humanId: Number
})

let deleteError = ref()

const emit = defineEmits(['deletedFromTeam']);
const deleteHumanFromTeam = async (teamId, humanId) => {
  try {
    console.log(teamId + " " + humanId)
    const response = await axios.delete(baseTeamsUrl + teamId + "/remove/" + humanId)
    emit('deletedFromTeam')
  } catch (err) {
    deleteError.value = err.response.data.message
  }
}

</script>

<template>

  <div class="remove-container">
    <button
        class="remove-btn"
        @click.prevent="deleteHumanFromTeam(teamId, humanId)"
        :disabled="deleteError"
    >
      Удалить из команды
    </button>
    <div v-if="deleteError" class="error">{{ deleteError }}</div>
  </div>

</template>

<style scoped>

.remove-container {
  display: inline-block;
  width: 100%;
}

.remove-btn {
  width: 100%;
  padding: 0.6rem 1rem;
  background: linear-gradient(135deg, #ff9a00, #ff5e00);
  color: white;
  border: none;
  border-radius: 6px;
  font-size: 0.9rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
  position: relative;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(255, 154, 0, 0.2);
}

.remove-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(255, 154, 0, 0.3);
}

.remove-btn:active {
  transform: translateY(0);
}

.remove-btn:disabled {
  background: var(--gray);
  cursor: not-allowed;
  transform: none;
  box-shadow: none;
}

/* Эффект волны при наведении */
.remove-btn::before {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.2), transparent);
  transition: left 0.5s;
}

.remove-btn:hover::before {
  left: 100%;
}

/* Сообщение об ошибке */
.error {
  margin-top: 0.5rem;
  padding: 0.5rem;
  background-color: rgba(255, 154, 0, 0.1);
  border-radius: 4px;
  border-left: 3px solid #ff9a00;
  color: #ff9a00;
  font-size: 0.85rem;
  animation: slideIn 0.3s ease;
}

/* Успешное выполнение */
.success {
  background: linear-gradient(135deg, #00b09b, #96c93d) !important;
}

.success::after {
}


/* Для длинного текста кнопки */
@media (max-width: 480px) {
  .remove-btn {
    font-size: 0.8rem;
    padding: 0.4rem 0.6rem;
  }

  .remove-btn::after {
    display: none;
  }
}

/* Убираем стандартный input */
input[type="submit"] {
  display: none;
}
</style>