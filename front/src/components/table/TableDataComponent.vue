<script setup>
import DeleteHumanComponent from "@/components/DeleteHumanComponent.vue";
import DeleteFromTeamComponent from "@/components/DeleteFromTeamComponent.vue";

const props = defineProps({
  humans: [Object]
})

const emit = defineEmits(['deleted']);

function deleteHuman() {
  emit('deleted')
}


</script>

<template>
  <tr v-for="human in humans">
    <td>{{ human.id }}</td>
    <td>{{ human.name }}</td>
    <td>{{ human.creationDate }}</td>

    <td v-if="human.realHero === null"></td>
    <td v-if="human.realHero === true">да</td>
    <td v-if="human.realHero === false">нет</td>

    <td>{{ human.teamNumber }}</td>

    <td v-if="human.hasToothpick === null"></td>
    <td v-if="human.hasToothpick === true">да</td>
    <td v-if="human.hasToothpick === false">нет</td>

    <td>{{ human.impactSpeed }}</td>
    <td>{{ human.minutesOfWaiting }}</td>

    <td v-if="human.weaponType === null"></td>
    <td v-if="human.weaponType === 'PISTOL'">пистолет</td>
    <td v-if="human.weaponType === 'SHOTGUN'">ружье</td>
    <td v-if="human.weaponType === 'MACHINE_GUN'">пулемет</td>
    <td v-if="human.weaponType === 'BAT'">бита</td>

    <td v-if="human.mood === null"></td>
    <td v-if="human.mood === 'SADNESS'">печаль</td>
    <td v-if="human.mood === 'SORROW'">грусть</td>
    <td v-if="human.mood === 'RAGE'">ярость</td>

    <td v-if="human.car">{{ human.car.name }}</td>
    <td v-if="!human.car"></td>


    <td v-if="human.car && human.car.cool === null"></td>
    <td v-if="human.car && human.car.cool === true">да</td>
    <td v-if="human.car && human.car.cool === false">нет</td>
    <td v-if="!human.car"></td>

    <td>{{ human.coordinates.x }}</td>
    <td>{{ human.coordinates.y }}</td>
    <td>
      <delete-human-component @deleted="deleteHuman" :id="human.id"></delete-human-component>
      <delete-from-team-component v-if="human.teamNumber" @deleted-from-team="deleteHuman" :human-id="human.id"
                                  :team-id="human.teamNumber"></delete-from-team-component>
    </td>
  </tr>
</template>

<style scoped>

</style>