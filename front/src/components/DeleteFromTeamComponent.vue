<script setup>

import axios from "axios";

const props = defineProps({
  teamId: Number,
  humanId: Number
})

const baseUrl = 'http://localhost:1214/team-service/heroes/team/'
const emit = defineEmits(['deletedFromTeam']);
const deleteHumanFromTeam = async (teamId, humanId) => {
  try {
    console.log(teamId + " " + humanId)
    const response = await axios.delete(baseUrl + teamId + "/remove/" + humanId)
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