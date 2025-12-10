<script setup>
import DeleteHumanComponent from "@/components/DeleteHumanComponent.vue";
import DeleteFromTeamComponent from "@/components/DeleteFromTeamComponent.vue";

const props = defineProps({
  humans: [Object],
  canDelete: Boolean
})

const emit = defineEmits(['deleted']);

function deleteHuman() {
  emit('deleted')
}


</script>

<template>
  <tr v-for="human in humans" :key="human.id">
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
    <td v-if="canDelete">
      <div class="action-buttons">
        <DeleteHumanComponent
            @deleted="deleteHuman"
            :id="human.id"
        />
        <DeleteFromTeamComponent
            v-if="human.teamNumber"
            @deleted-from-team="deleteHuman"
            :human-id="human.id"
            :team-id="human.teamNumber"
        />
      </div>
    </td>
  </tr>
</template>

<style scoped>
/* Стили для строк данных */
tr {
  border-bottom: 1px solid var(--gray-light) !important;
  transition: all 0.2s ease;
}

tr:nth-child(even) {
  background-color: #f9f9f9;
}

tr:hover {
  background-color: #f0f5ff;
  transform: translateY(-1px);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

/* Ячейки данных */
td {
  padding: 0.75rem !important;
  vertical-align: middle !important;
  font-size: 0.9rem;
  color: #333;
  border-right: 1px solid var(--gray-light) !important;
}

td:last-child {
  border-right: none !important;
}

/* Стили для данных */
td:first-child {
  font-weight: 600;
  color: var(--primary-color);
}

/* Контейнер для кнопок действий */
.action-buttons {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  min-width: 150px;
}

/* Состояние для пустых ячеек */
td:empty {
  color: var(--gray);
  font-style: italic;
  opacity: 0.7;
}

tr {
  animation: fadeIn 0.3s ease forwards;
}

</style>