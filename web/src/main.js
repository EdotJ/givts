import Vue from "vue";
import App from "./App.vue";
import VueRouter from "vue-router";
import router from "./router";
import store from "./store";
import Toast, { POSITION } from "vue-toastification";
import "vue-toastification/dist/index.css";

Vue.config.productionTip = false;
Vue.use(VueRouter);
Vue.use(Toast, {
  position: POSITION.BOTTOM_RIGHT,
});

new Vue({
  router,
  store,
  render: (h) => h(App),
  watch: {},
}).$mount("#app");
