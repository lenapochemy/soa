<script setup>
import {ref} from 'vue';

// const operatorEqual = "&#61"
// const operatorMore = "&gt"
// const operatorLess = "&lt"

let operator = ref()

</script>

<template>
  <select v-model="operator" @change="$emit('operator', operator)">
    <option value="&#61">&#61</option >
    <option value="&gt">&gt</option>
    <option value="&lt">&lt</option>
  </select>
</template>

<style scoped>

</style>