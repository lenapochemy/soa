<script>

import MainComponent from "@/components/MainComponent.vue";
import UniqueMinutesComponent from "@/components/UniqueMinutesComponent.vue";
import CreationDateGroupingComponent from "@/components/CreationDateGroupingComponent.vue";
import HumanComponent from "@/components/HumanComponent.vue";
import CreateHumanComponent from "@/components/CreateHumanComponent.vue";
import GreaterMinutesComponent from "@/components/GreaterMinutesComponent.vue";
import UpdateHumanComponent from "@/components/UpdateHumanComponent.vue";

export default {
  components: {
    MainComponent,
    UniqueMinutesComponent,
    CreationDateGroupingComponent,
    HumanComponent,
    CreateHumanComponent,
    GreaterMinutesComponent,
    UpdateHumanComponent
  },
  data() {
    return {
      buttons: [
        {"value": "Main", "name": "Все элементы"},
        {"value": "UniqueMinutes", "name": "Уникальные значения времени ожидания"},
        {"value": "CreationDateGrouping", "name": "Группировка по дате"},
        {"value": "Human", "name": "Получить по id"},
        {"value": "CreateHuman", "name": "Создать"},
        {"value": "GreaterMinutes", "name": "Элементы с большим временем ожидания"},
        {"value": "UpdateHuman", "name": "Обновить"},
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
  <div class="app-container">
    <!-- Декоративный фон -->
    <div class="app-background"></div>

    <!-- Верхняя панель навигации -->
    <nav class="top-navbar">
      <div class="nav-content">
        <!-- Заголовок -->
        <div class="header-section">
          <div class="header_name">Human Being Collection</div>
        </div>

        <!-- Горизонтальное меню кнопок -->
        <div class="nav-menu">
          <button
              v-for="button in buttons"
              :key="button"
              :class="['tab-button', {active: currentButton === button.value}]"
              @click="currentButton = button.value"
              :data-value="button.value"
          >
            <span class="current-tab-indicator"></span>
            {{ button.name }}
          </button>
        </div>
      </div>
    </nav>

    <!-- Основной контент -->
    <main class="main-content">
      <div class="content-container">
        <component :is="currentButtonComponent" :can-delete="true" :key="currentButton"/>
      </div>
    </main>

  </div>
</template>

<style scoped>
/* Сбрасываем стили для body */
body {
  margin: 0;
  padding: 0;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, sans-serif;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  min-height: 100vh;
}

/* Основной контейнер приложения */
.app-container {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

/* Верхняя панель навигации */
.top-navbar {
  background: rgba(255, 255, 255, 0.98);
  backdrop-filter: blur(10px);
  box-shadow: 0 2px 20px rgba(0, 0, 0, 0.1);
  position: sticky;
  top: 0;
  z-index: 1000;
  border-bottom: 3px solid;
  border-image: linear-gradient(90deg, var(--primary-color), var(--secondary-color)) 1;
}

/* Контейнер для содержимого навигации */
.nav-content {
  max-width: 1400px;
  margin: 0 auto;
  padding: 0 2rem;
  display: flex;
  flex-direction: column;
}

/* Заголовок вверху */
.header-section {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 1rem 0;
  border-bottom: 1px solid rgba(0, 0, 0, 0.05);
}

.header_name {
  font-size: 1.8rem;
  font-weight: 800;
  background: linear-gradient(135deg, var(--primary-color), var(--secondary-color));
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  letter-spacing: 0.5px;
  display: flex;
  align-items: center;
  gap: 0.75rem;
}


/* Горизонтальное меню кнопок */
.nav-menu {
  display: flex;
  overflow-x: auto;
  padding: 0.75rem 0;
  gap: 0.5rem;
  scrollbar-width: thin;
  scrollbar-color: var(--primary-color) transparent;
}

.nav-menu::-webkit-scrollbar {
  height: 4px;
}

.nav-menu::-webkit-scrollbar-track {
  background: transparent;
}

.nav-menu::-webkit-scrollbar-thumb {
  background-color: var(--primary-color);
  border-radius: 2px;
}

/* Кнопки вкладок */
.tab-button {
  padding: 0.75rem 0.5rem;
  border: none;
  border-radius: 50px;
  font-size: 0.9rem;
  font-weight: 600;
  cursor: pointer;
  background: linear-gradient(135deg, #f6f7ff 0%, #eef0ff 100%);
  color: var(--primary-color);
  border: 1px solid rgba(67, 97, 238, 0.1);
  display: flex;
  align-items: center;
  gap: 0.5rem;
  position: relative;
}

.tab-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(67, 97, 238, 0.15);
  background: linear-gradient(135deg, #eef0ff 0%, #e0e5ff 100%);
}

/* Активная вкладка */
.tab-button.active {
  background: linear-gradient(135deg, var(--primary-color), var(--secondary-color));
  color: white;
  box-shadow: 0 4px 12px rgba(67, 97, 238, 0.25);
}

.tab-button.active:hover {
  background: linear-gradient(135deg, var(--primary-dark), var(--secondary-color));
  transform: translateY(-2px);
}

/* Основной контент */
.main-content {
  flex: 1;
  max-width: 2400px;
  margin: 0 auto;
  padding: 2rem;
  width: 100%;
  box-sizing: border-box;
}

/*.content-container {
  background: white;
  border-radius: var(--border-radius);
  padding: 2rem;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
  min-height: calc(100vh - 200px);
  animation: fadeIn 0.5s ease;
}*/

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* Индикатор текущей вкладки */
.current-tab-indicator {
  position: absolute;
  bottom: -0.75rem;
  left: 50%;
  transform: translateX(-50%);
  width: 30px;
  height: 4px;
  background: linear-gradient(90deg, var(--primary-color), var(--secondary-color));
  border-radius: 2px;
  opacity: 0;
  transition: opacity 0.3s ease;
}

.tab-button.active .current-tab-indicator {
  opacity: 1;
}


/* Анимация переключения вкладок */
.tab-transition-enter-active,
.tab-transition-leave-active {
  transition: opacity 0.3s, transform 0.3s;
}

.tab-transition-enter-from {
  opacity: 0;
  transform: translateY(10px);
}

.tab-transition-leave-to {
  opacity: 0;
  transform: translateY(-10px);
}
</style>