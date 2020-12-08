import '@/assets/css/tailwind.css'
import Vue from 'vue';
import App from "./App";
import {library} from "@fortawesome/fontawesome-svg-core"
import {faArrowLeft, faEye, faEyeSlash, faInfo, faTimes} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import axios from "axios";
import VueAxios from "vue-axios";
import VueRouter from "vue-router";
import router from "./routes";

axios.defaults.withCredentials = true;
library.add(faArrowLeft);
library.add(faEyeSlash);
library.add(faTimes);
library.add(faEye);
library.add(faInfo);


Vue.use(VueRouter);
Vue.use(VueAxios, axios);

Vue.component('font-awesome-icon', FontAwesomeIcon)

new Vue({
  router,
  render: (h) => h(App),
}).$mount('#app');
