<script setup>
import {ref} from 'vue'
import axios from "axios";

// let id = ref()
const props = defineProps({
  id: Number
})

const baseUrl = 'http://localhost:8080/human-service/api/humans'
const emit = defineEmits(['deleted']);
const deleteHuman = async (id) => {
  try{
    const response = await axios.delete(baseUrl + "/" + id)
    emit('deleted')
  } catch (err){
    console.log(err.response.status)
  }
}

</script>

<template>

  <input type="submit" @click.prevent="deleteHuman(id)" value="удалить"/>

</template>

<style scoped>

input {
  background-color: deeppink;
}
</style>