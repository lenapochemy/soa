<script>

import Header from "@/components/Header.vue";
import MainComponent from "@/components/MainComponent.vue";
import UniqueMinutesComponent from "@/components/UniqueMinutesComponent.vue";
import CreationDateGroupingComponent from "@/components/CreationDateGroupingComponent.vue";

export default {
  components: {
    Header,
    MainComponent,
    UniqueMinutesComponent,
    CreationDateGroupingComponent
  },
  data() {
    return {
      buttons: [
        {"value": "Main", "name": "Все элементы"},
        {"value": "UniqueMinutes", "name": "Уникальные значения времени ожидания"},
        {"value": "CreationDateGrouping", "name": "Группировка по дате"},
        // {"value": "Special", "name": "Special operation"},
        // {"value": "AddCoordinate", "name": "Add coordinates"},
        // {"value": "AddChapter", "name": "Add chapter"},
        // {"value": "AddSpaceMarine", "name": "Add space marine"},
        // {"value": "EditChapter", "name": "Chapter editing info"},
        // {"value": "EditCoord", "name": "Coordinates editing info"},
        // {"value": "EditSpaceMarine", "name": "Space marine editing info"},
        // {"value": "Visualization", "name": "Visualization"},
        // {"value": "File", "name": "Import file"},
        // {"value": "ImportHistory", "name": "Imports history"},
      ],
      currentButton: "Main"
    }
  },
  computed: {
    currentButtonComponent() {
      return this.currentButton + "Component";
    }

  }
}

</script>

<template>
  <header>
    <Header/>
<!--    <LogOutComponent/>-->
  </header>
  <body>
  <div>

    <button
        v-for="button in buttons"
        v-bind:key="button"
        v-bind:class="['tab-button', {active: currentButton === button.value}]"
        v-on:click="currentButton = button.value">{{ button.name }}
    </button>
    <component v-bind:is="currentButtonComponent"></component>
  </div>
  <br>
  </body>

</template>

<style scoped>
.tab-button {
  background-color: skyblue;
  font-weight: bold;
  padding: 4px 9px 4px;
  font-size: large;
  margin: auto;
}

.tab-button:hover, .tab-button:focus, .tab-button:active {
  background-color: mediumblue;
}
</style>