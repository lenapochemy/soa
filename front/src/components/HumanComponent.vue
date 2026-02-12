<script setup>
import {onMounted, ref, watch} from "vue";
import axios from "axios";
import TableDataComponent from "@/components/table/TableDataComponent.vue";
import {validatePositiveNumber} from "@/validators.js";
import TableHeadComponent from "@/components/table/TableHeadComponent.vue";
import {baseHumansUrl} from "@/main.js";

const props = defineProps({
  canDelete: Boolean,
  humanId: Number
})

let id = ref()
let humans = ref([])
let errorGetHuman = ref()
let errorId = ref()

const emit = defineEmits(['human']);

const getHuman = async () => {
  try {
    humans.value = []
    const response = await axios.get(baseHumansUrl + "/" + id.value);
    humans.value.push(response.data)
    errorGetHuman.value = undefined
    emit('human', humans.value[0])
  } catch (err) {
    console.log(err)
    humans.value = undefined
    emit('human', humans.value)
    errorGetHuman.value = err.response.data
  }
}

function validateId() {
  console.log("in validate id=" + id.value)
  if (id.value && !validatePositiveNumber(id.value)) {
    errorId.value = "Значение должно быть целым числом больше 0"
    return false
  } else if (!id.value) {
    errorId.value = "Значение должно быть целым числом больше 0"
    return false
  } else {
    errorId = undefined
    return true
  }
}

watch(() => props.humanId, (newId) => {
  console.log("aaa " + newId + " a " + props.humanId)
  if (newId !== undefined) {
    id.value = newId
    getHuman()
  }
}, {immediate: true, deep: true});

</script>

<template>
  <div class="human-container">
    <div class="human-title">Поиск человека по ID</div>

    <div class="search-form">
      <div class="form-row">
        <div class="search-label">Введите ID человека:</div>
        <div class="id-input-wrapper">
          <input
              type="text"
              v-model="id"
              @change="validateId"
              @keyup.enter="getHuman"
              class="id-input"
              placeholder="Например: 123"
          />
          <span class="id-prefix">ID</span>
        </div>
        <button
            class="search-btn"
            @click.prevent="getHuman"
            :disabled="!id || errorId"
        >
          Найти
        </button>
      </div>
      <div v-if="errorId" class="error">{{ errorId }}</div>
    </div>

    <div v-if="humans && humans.length > 0">

      <div class="table-container">
        <table>
          <table-head-component :sort="false"/>
          <tbody>
          <table-data-component
              :can-delete="canDelete"
              :humans="humans"
              @deleted="getHuman"
          />
          </tbody>
        </table>
      </div>
    </div>

    <div v-if="errorGetHuman" class="error">{{ errorGetHuman }}</div>
  </div>

</template>

<style scoped>
.human-container {
  background: white;
  border-radius: var(--border-radius);
  padding: 2rem;
  box-shadow: var(--box-shadow);
  animation: fadeIn 0.5s ease;
}

.human-title {
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

/* Форма поиска */
.search-form {
  background: linear-gradient(135deg, #f8f9ff 0%, #eef1ff 100%);
  border-radius: 12px;
  padding: 1.5rem;
  margin-bottom: 2rem;
  border: 1px solid rgba(67, 97, 238, 0.1);
}

.form-row {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  gap: 1rem;
  margin-bottom: 1rem;
}

.search-label {
  font-weight: 600;
  color: var(--dark-color);
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.id-input-wrapper {
  position: relative;
  flex: 1;
  max-width: 300px;
}

.id-input {
  width: 100%;
  padding: 0.75rem 1rem 0.75rem 3rem;
  border: 2px solid var(--gray-light);
  border-radius: 8px;
  font-size: 1rem;
  font-weight: 500;
  color: var(--dark-color);
  transition: all 0.3s ease;
  background: white;
}

.id-input:focus {
  outline: none;
  border-color: var(--primary-color);
  box-shadow: 0 0 0 3px rgba(67, 97, 238, 0.15);
  transform: translateY(-1px);
}

.id-input::placeholder {
  color: var(--gray);
  font-weight: normal;
}

.id-prefix {
  position: absolute;
  left: 1rem;
  top: 50%;
  transform: translateY(-50%);
  color: var(--primary-color);
  font-weight: 600;
  font-size: 0.9rem;
}

.search-btn {
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

.search-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 16px rgba(67, 97, 238, 0.3);
}

.search-btn:active {
  transform: translateY(0);
}

.search-btn:disabled {
  background: var(--gray);
  cursor: not-allowed;
  transform: none;
  box-shadow: none;
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

/* Таблица */
.table-container {
  margin-top: 1.5rem;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
}

/* Убираем стандартные элементы */
span {
  display: none;
}

input[type="submit"] {
  display: none;
}
</style>