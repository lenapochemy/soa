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

  <input type="submit" @click.prevent="deleteHumanFromTeam(teamId, humanId)" value="удалить из команды"/>
  <div v-if="deleteError" class="error">{{deleteError}}</div>
</template>

<style scoped>

input {
  background-color: deeppink;
}
</style>