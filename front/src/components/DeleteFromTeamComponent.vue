<script setup>

import axios from "axios";
import {baseTeamsUrl} from "@/main.js";

const props = defineProps({
  teamId: Number,
  humanId: Number
})

const emit = defineEmits(['deletedFromTeam']);
const deleteHumanFromTeam = async (teamId, humanId) => {
  try {
    console.log(teamId + " " + humanId)
    const response = await axios.delete(baseTeamsUrl + teamId + "/remove/" + humanId)
    emit('deletedFromTeam')
  } catch (err) {
    console.log(err)
    console.log(err.response.status)
  }
}

</script>

<template>

  <input type="submit" @click.prevent="deleteHumanFromTeam(teamId, humanId)" value="удалить из команды"/>

</template>

<style scoped>

input {
  background-color: deeppink;
}
</style>