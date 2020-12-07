<template>
  <div class="top-level-container">
    <div class="container">
      <h1 class="title">Who's getting a gift today?</h1>
      <div class="button-container">
        <router-link to="/giftees/add">
          <Button class="addButton"> Add a new Giftee </Button>
        </router-link>
      </div>
      <div class="giftee-list">
        <router-link
          v-for="giftee in giftees"
          :key="giftee.id"
          :to="`/giftees/${giftee.id}`"
          class="list-item"
        >
          {{ giftee.name }}
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";
import Button from "../components/Button.vue";
import {
  GET_ALL,
  CREATE,
  UPDATE,
  GET_SINGLE,
  DELETE,
} from "../store/actions.types";
import { GIFTEES_MODULE, USER_MODULE } from "../store/modules";

export default {
  components: { Button },
  name: "Giftees",
  methods: {
    ...mapActions(GIFTEES_MODULE, [
      GET_ALL,
      CREATE,
      UPDATE,
      GET_SINGLE,
      DELETE,
    ]),
    getGifteeList() {
      this.delete({ id: 20 });
    },
  },
  computed: {
    ...mapState(USER_MODULE, {
      userId: (state) => state.data.id,
    }),
    ...mapState(GIFTEES_MODULE, {
      giftees: (state) => state.giftees,
    }),
  },
  watch: {
    userId: {
      handler() {
        this.getAll();
      },
      immediate: true,
    },
  },
};
</script>

<style scoped>
.title {
  font-size: 1.5rem;
  font-family: "Fruktur", cursive;
  font-weight: 400;
  margin-top: 0;
}

.addButton {
  margin: 1rem 0;
}

.giftee-list {
  display: flex;
  padding: 0 1rem;
  flex-direction: column;
}

.list-item {
  list-style-type: none;
  padding: 1rem 0;
  margin: 3px 0;
  border: 1px solid #c44748;
  transition: 0.3s;
  text-decoration: none;
  color: #201b22;
}

.list-item:hover {
  background: #c44748;
  color: #fbfaf8;
}

/* Tablet Styles */
@media only screen and (min-width: 415px) and (max-width: 960px) {
  .button-container {
    width: 100%;
    display: flex;
    justify-content: right;
  }

  .addButton {
    margin: 1rem 2rem;
  }

  .top-level-container {
    display: flex;
    justify-content: center;
    width: 100%;
  }

  .container {
    width: 66%;
  }
}

/* Desktop Styles */
@media only screen and (min-width: 961px) {
  .title {
    font-size: 3rem;
  }

  .button-container {
    width: 100%;
    display: flex;
    justify-content: right;
  }

  .addButton {
    margin: 1rem 2rem;
  }

  .button-container {
    width: 100%;
    display: flex;
    justify-content: right;
  }

  .top-level-container {
    display: flex;
    justify-content: center;
    width: 100%;
  }

  .container {
    width: 66%;
  }
}
</style>
