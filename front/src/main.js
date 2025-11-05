import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'
import axios from "axios";

export const baseHumansUrl = 'http://localhost:8080/human-service/api/humans'
export const baseTeamsUrl = 'http://localhost:1214/team-service/heroes/team/'

axios.defaults.withCredentials = true

createApp(App).mount('#app')
