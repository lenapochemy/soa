<script setup>
import axios from "axios";
import {baseHumansUrl} from "@/main.js";
import {ref} from "vue";

const props = defineProps({
  id: Number
})

const emit = defineEmits(['deleted']);
let deleteError = ref()
const deleteHuman = async (id) => {
  try {
    const response = await axios.delete(baseHumansUrl + "/" + id)
    emit('deleted')
  } catch (err) {
    deleteError.value = err.response.data
  }
}

</script>

<template>
  <div class="delete-container">
    <button
        class="delete-btn"
        @click.prevent="deleteHuman(id)"
        :disabled="deleteError"
    >
      Удалить
    </button>
    <div v-if="deleteError" class="error">{{ deleteError }}</div>
  </div>


</template>

<style scoped>
.delete-container {
  display: inline-block;
  width: 100%;
}

.delete-btn {
  width: 100%;
  padding: 0.6rem 1rem;
  background: linear-gradient(135deg, #ff416c, #ff4b2b);
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
  box-shadow: 0 2px 8px rgba(255, 65, 108, 0.2);
}

.delete-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(255, 65, 108, 0.3);
}

.delete-btn:active {
  transform: translateY(0);
}

.delete-btn:disabled {
  background: var(--gray);
  cursor: not-allowed;
  transform: none;
  box-shadow: none;
}

/* Эффект пульсации при наведении */
.delete-btn::before {
  content: '';
  position: absolute;
  top: 50%;
  left: 50%;
  width: 0;
  height: 0;
  border-radius: 50%;
  background-color: rgba(255, 255, 255, 0.3);
  transform: translate(-50%, -50%);
  transition: width 0.6s, height 0.6s;
}

.delete-btn:hover::before {
  width: 200px;
  height: 200px;
}

/* Сообщение об ошибке */
.error {
  margin-top: 0.5rem;
  padding: 0.5rem;
  background-color: rgba(255, 65, 108, 0.1);
  border-radius: 4px;
  border-left: 3px solid #ff416c;
  color: #ff416c;
  font-size: 0.85rem;
  animation: slideIn 0.3s ease;
}

/* Убираем стандартный input */
input[type="submit"] {
  display: none;
}
</style>