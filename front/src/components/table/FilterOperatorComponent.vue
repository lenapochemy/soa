<script setup>
import {ref} from 'vue';

let operator = ref("")

</script>

<template>
  <div class="filter-operator">
    <select v-model="operator" @change="$emit('operator', operator)">
      <option value="">Выберите оператор</option>
      <option value="&#61">&#61</option>
      <option value="&gt">&gt</option>
      <option value="&lt">&lt</option>
    </select>
  </div>
</template>

<style scoped>
.filter-operator {
  position: relative;
  min-width: 160px;
}

select {
  width: 100%;
  padding: 0.5rem 2.5rem 0.5rem 0.75rem;
  border: 1px solid var(--gray);
  border-radius: 6px;
  font-size: 0.9rem;
  font-weight: 500;
  color: var(--dark-color);
  background-color: white;
  background-repeat: no-repeat;
  background-position: right 0.75rem center;
  background-size: 12px;
  cursor: pointer;
  transition: all 0.3s ease;
  appearance: none;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);
}

select:hover {
  border-color: var(--primary-color);
  box-shadow: 0 2px 5px rgba(67, 97, 238, 0.1);
}

select:focus {
  outline: none;
  border-color: var(--primary-color);
  box-shadow: 0 0 0 3px rgba(67, 97, 238, 0.15);
}

/* Стили для операторов */
option {
  padding: 0.5rem;
  font-size: 0.9rem;
}

option[value=""] {
  color: var(--gray);
  font-style: italic;
}

/* Индикатор выбора оператора */
select:valid {
  border-left: 3px solid var(--primary-color);
  padding-left: 0.5rem;
}

</style>