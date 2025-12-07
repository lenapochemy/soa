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
    deleteError.value = err.response.data.message
  }
}

</script>

<template>

  <input type="submit" @click.prevent="deleteHuman(id)" value="удалить"/>
  <div v-if="deleteError" class="error">{{deleteError}}</div>
</template>

<style scoped>

input {
  background-color: deeppink;
}
</style>