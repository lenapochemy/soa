<script setup>
import axios from "axios";
import {ref} from "vue";
import {baseTeamsUrl} from "@/main.js"

const props = defineProps({
  teams: Array
})

const emit = defineEmits(['addedCars']);


let teamId = ref("")
let errorTeamId = ref()
let giveCarsRes = ref()

function validateTeamId() {
  console.log("aaaa in validate team id " + teamId.value)
  if (!teamId.value || (teamId.value && teamId.value === "")) {
    errorTeamId.value = "Нужно выбрать команду из списка"
    return false
  } else {
    errorTeamId = undefined
    return true
  }
}

const giveCars = async () => {
  if (validateTeamId()) {
    try {
      const response = await axios.post(baseTeamsUrl + teamId.value + "/car/add");
      // giveCarsRes.value = response.data.message
      emit('addedCars')
      // await getHumans()
    } catch (err) {
      // console.log("problem with get")
      giveCarsRes.value = err.response.data.message
      switch (err.response.status) {
        case 422:
          giveCarsRes.value = err.response.data.message
      }
    }
  }
}
</script>

<template>
  <div>
    <span>Выдать красные лады команде </span>
    <select v-model="teamId" @change="validateTeamId">
      <option value="">Выберите команду</option>
      <option v-for="team in teams" :value="team">{{ team }}</option>
    </select>
    <div v-if="errorTeamId" class="error">{{ errorTeamId }}</div>
    <input type="submit" @click.prevent="giveCars" value="выдать машины"/>
    <div v-if="giveCarsRes" class="error">{{ giveCarsRes }}</div>
  </div>
</template>

<style scoped>

</style>