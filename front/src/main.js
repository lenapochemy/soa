import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'
import axios from "axios";

// export const baseHumansUrl = 'https://localhost:8443/human-service/api/humans'
// export const baseTeamsUrl = 'https://localhost:8743/team-service/heroes/team/'
export const baseHumansUrl = 'http://localhost:8091/human-service/api/humans'
export const baseTeamsUrl = 'http://localhost:8082/heroes/team/'
// export const baseTeamsUrl = 'http://localhost:8081/team-service/heroes/team/'

axios.defaults.withCredentials = true

createApp(App).mount('#app')
