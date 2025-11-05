<script setup>
import axios from "axios";
import {baseHumansUrl} from "@/main.js";

const props = defineProps({
  id: Number
})

const emit = defineEmits(['deleted']);
const deleteHuman = async (id) => {
  try {
    const response = await axios.delete(baseHumansUrl + "/" + id)
    emit('deleted')
  } catch (err) {
    console.log(err)
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