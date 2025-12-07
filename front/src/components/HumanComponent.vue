<script setup>
import {onMounted, ref, watch} from "vue";
import axios from "axios";
import TableDataComponent from "@/components/table/TableDataComponent.vue";
import {validatePositiveNumber} from "@/validators.js";
import TableHeadComponent from "@/components/table/TableHeadComponent.vue";
import {baseHumansUrl} from "@/main.js";

const props = defineProps({
  canDelete: Boolean,
  humanId: Number
})

let id = ref()
let humans = ref([])
let errorGetHuman = ref()
let errorId = ref()

const emit = defineEmits(['human']);

const getHuman = async () => {
  try {
    humans.value = []
    const response = await axios.get(baseHumansUrl + "/" + id.value);
    humans.value.push(response.data)
    errorGetHuman.value = undefined
    emit('human', humans.value[0])
  } catch (err) {
    console.log(err)
    humans.value = undefined
    emit('human', humans.value)
    errorGetHuman.value = err.response.data.message
  }
}

function validateId() {
  console.log("in validate id=" + id.value)
  if (id.value && !validatePositiveNumber(id.value)) {
    errorId.value = "Значение должно быть целым числом больше 0"
    return false
  } else if (!id.value) {
    errorId.value = "Значение должно быть целым числом больше 0"
    return false
  } else {
    errorId = undefined
    return true
  }
}

watch(() => props.humanId,  (newId) => {
  console.log("aaa " + newId + " a " + props.humanId)
  if(newId !== undefined) {
    id.value = newId
    getHuman()
  }
}, { immediate: true, deep: true });

</script>

<template>
  <div>
    <span>id: </span>
    <input type="text" v-model="id" @change="validateId"/>
    <input type="submit" @click.prevent="getHuman" value="показать">
  </div>


  <table border="1" v-if="humans">
    <table-head-component :sort="false"/>
    <tbody>
    <table-data-component :can-delete="canDelete" :humans="humans" @deleted="getHuman"/>
    </tbody>
  </table>
  <div v-if="errorGetHuman" class="error">{{ errorGetHuman }}</div>
</template>

<style scoped>

</style>