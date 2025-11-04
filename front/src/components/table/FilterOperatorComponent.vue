<script setup>
import {ref} from 'vue';

let operator = ref("")

</script>

<template>
  <select v-model="operator" @change="$emit('operator', operator)">
    <option value="">Выберите оператор</option>
    <option value="&#61">&#61</option>
    <option value="&gt">&gt</option>
    <option value="&lt">&lt</option>
  </select>
</template>

<style scoped>

</style>