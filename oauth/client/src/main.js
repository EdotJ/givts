import '@/assets/css/tailwind.css'
import {createApp} from 'vue';
import App from "./App";
import {createWebHistory, createRouter} from 'vue-router';
import routes from "./routes";
import {library} from "@fortawesome/fontawesome-svg-core"
import {faArrowLeft, faEye, faEyeSlash, faInfo, faTimes} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import axios from "axios";
import VueAxios from "vue-axios";

axios.defaults.withCredentials = true;
library.add(faArrowLeft);
library.add(faEyeSlash);
library.add(faTimes);
library.add(faEye);
library.add(faInfo);

const router = createRouter({
    history: createWebHistory(),
    routes
})

createApp(App).use(router, axios, VueAxios).component('font-awesome-icon', FontAwesomeIcon).mount('#app')
