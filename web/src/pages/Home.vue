<template>
  <div class="container">
    <div v-if="loggedIn && !isLoading" class="base-authorized">
      Save your best ideas!
    </div>
    <div v-else-if="!isLoading" class="base-anonymous">
      <Button iconName="EnterIcon" v-on:click.native="redirectToAuth">
        Log in
      </Button>
    </div>
  </div>
</template>

<script>
import Button from "../components/Button";
import authService from "../services/authService";
import { mapState, mapGetters } from "vuex";
import { GET_LOGGED_IN } from "../store/getters.types";
import { USER_MODULE } from "../store/modules";

export default {
  name: "Home",
  components: { Button },
  computed: {
    ...mapState({
      name: (state) => state.currentUser.data.name,
      isLoading: (state) => state.isLoading,
    }),
    ...mapGetters(USER_MODULE, {
      loggedIn: GET_LOGGED_IN,
    }),
  },
  methods: {
    redirectToAuth: () => {
      authService.login();
    },
  },
  mounted() {},
};
</script>

<style scoped>
.container {
  height: 100%;
  width: 100%;
}

.base-anonymous {
  display: flex;
  width: 100%;
  height: 100%;
  align-items: center;
  justify-content: center;
}

.base-authorized {
  width: 100%;
  height: 100%;
  font-size: 3rem;
  display: flex;
  justify-content: center;
  align-items: center;
  font-family: "Fruktur", cursive;
  font-weight: 900;
}
</style>
