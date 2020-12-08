<template>
  <div class="template-root">
    <Navbar />
    <GreetingModal v-if="loggedIn && !isLoading && !hasName" />
    <Loader class="loader" v-if="isLoading" />
    <div class="content">
      <router-view />
    </div>
    <footer class="default-layout-footer">
      <span class="made-with">Made with 😠 in Quarantine</span>
      <a class="github" href="https://www.github.com/EdotJ">GitHub</a>
    </footer>
  </div>
</template>

<script>
import { mapState, mapGetters, mapActions } from "vuex";
import Navbar from "./Navbar";
import GreetingModal from "./GreetingModal";
import Loader from "./Loader";
import { GET_USER_INFO } from "../store/actions.types";
import { GET_LOGGED_IN, HAS_NAME } from "../store/getters.types";
import { USER_MODULE } from "../store/modules";
import userService from "../services/userService";

export default {
  name: "Layout",
  components: { Navbar, GreetingModal, Loader },
  data() {
    return {
      show: false,
      triedFetchingData: false,
    };
  },
  methods: {
    toggleBurger() {
      this.show = !this.show;
    },
    ...mapActions(USER_MODULE, [GET_USER_INFO]),
  },
  computed: {
    ...mapState({
      isLoading: (state) => state.isLoading,
      name: (state) => state.currentUser.data.name,
      wasNameUpdated: (state) => state.currentUser.status.wasNameUpdated,
    }),
    ...mapGetters(USER_MODULE, [GET_LOGGED_IN, HAS_NAME]),
  },
  mounted() {
    userService.getUserInfo();
  },
  watch: {
    hasName: {
      handler(hasName, oldVal) {
        if (!hasName && this.loggedIn) {
          this.getUserInfo(true).then(() => {
            this.triedFetchingData = true;
          });
        }
      },
      immediate: true,
    },
  },
};
</script>

<style scoped>

.template-root {
  height: 100%;
  width: 100%;
  display: flex;
  flex-direction: column;
}

.content {
  /* height: calc(100% - 60px - 1.6rem); */
  width: 100%;
  flex-basis: auto;
  flex-grow: 1;
}

.default-layout-footer {
  width: 100%;
  display: flex;
  align-items: center;
  height: 1.5rem;
  font-size: 1rem;
  padding: 0 0.5rem;
  justify-content: space-between;
  border-top: 1px solid #201b22;
  max-height: 1.5rem;
}

.made-with {
  font-size: 0.75rem;
}

.github {
  margin-right: 1rem;
  text-decoration: none;
  color: tomato;
  font-weight: 900;
}

.loader {
  position: absolute;
  top: 30%;
  left: 40%;
}

@media only screen and (min-width: 961px) {
  .loader {
    left: 50%;
  }
}

</style>
