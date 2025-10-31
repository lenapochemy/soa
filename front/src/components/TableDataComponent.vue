<script setup>
import DeleteHumanComponent from "@/components/DeleteHumanComponent.vue";

const props = defineProps({
  humans: [Object]
})

const emit = defineEmits(['deleted']);

function deleteHuman(){
  emit('deleted')
}


</script>

<template>

  <tr v-for="human in humans">
    <td>{{ human.id }}</td>
    <td>{{ human.name }}</td>
    <td>{{ human.creationDate }}</td>
    <td>{{ human.realHero }}</td>
    <td>{{ human.teamNumber }}</td>
    <td>{{ human.hasToothpick }}</td>
    <td>{{ human.impactSpeed }}</td>
    <td>{{ human.minutesOfWaiting }}</td>
    <td>{{ human.weaponType }}</td>
    <td>{{ human.mood }}</td>
    <td>{{ human.car.name }}</td>
    <td>{{ human.car.cool }}</td>
    <td>{{ human.coordinates.x }}</td>
    <td>{{ human.coordinates.y }}</td>
    <td>
      <delete-human-component @deleted="deleteHuman" :id="human.id"></delete-human-component>
    </td>
  </tr>
</template>

<style scoped>

</style>