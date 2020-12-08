<template>
  <div class="container">
    <h1>{{ this.$route.params.id ? "Edit" : "Add" }} a giftee</h1>
    <div
      class="form"
      v-if="!isLoading"
      v-on:keyup.enter="$route.params.id ? updateGiftee() : createGiftee()"
    >
      <div class="form-items">
        <div class="error" v-if="errors.name">
          {{ errors.name }}
        </div>
        <div class="input-group" :class="{ spacing: !errors.name }">
          <label for="name">Name</label>
          <input
            type="text"
            v-model="giftee.name"
            id="name"
            placeholder="Eddy"
          />
        </div>
        <div class="error" v-if="errors.likes">
          {{ errors.likes }}
        </div>
        <div class="input-group" :class="{ spacing: !errors.likes }">
          <label for="likes">Likes</label>
          <input
            type="text"
            v-model="giftee.likes"
            id="likes"
            placeholder="Cakes and snakes"
          />
        </div>
        <div class="error" v-if="errors.dislikes">
          {{ errors.dislikes }}
        </div>
        <div class="input-group" :class="{ spacing: !errors.dislikes }">
          <label for="dislikes">Dislikes</label>
          <input
            type="text"
            v-model="giftee.dislikes"
            id="dislikes"
            placeholder="Trains"
          />
        </div>
        <div class="error" v-if="errors.hobbies">
          {{ errors.hobbies }}
        </div>
        <div class="input-group" :class="{ spacing: !errors.hobbies }">
          <label for="hobbies">Hobbies</label>
          <input
            type="text"
            v-model="giftee.hobbies"
            id="hobbies"
            placeholder="Skiing"
          />
        </div>
      </div>
      <div class="context-buttons">
        <Button
          class="submit-button"
          v-on:click.native="$route.params.id ? updateGiftee() : createGiftee()"
        >
          Submit
        </Button>
      </div>
    </div>
  </div>
</template>

<script>
import { GIFTEES_MODULE } from "../store/modules";
import { CREATE, UPDATE } from "../store/actions.types";
import gifteeService from "../services/gifteeService";
import { mapActions, mapState } from "vuex";
import Button from "../components/Button";
import { SET_IS_LOADING } from "../store/mutations.types";

export default {
  name: "AddGiftee",
  components: {
    Button,
  },
  data() {
    return {
      giftee: {},
      errors: {
        name: "",
        likes: "",
        dislikes: "",
        hobbies: "",
      },
    };
  },
  computed: {
    ...mapState({
      isLoading: (state) => state.isLoading,
    }),
  },
  methods: {
    ...mapActions(GIFTEES_MODULE, {
      create: CREATE,
      update: UPDATE,
    }),
    createGiftee() {
      if (this.handleErrors()) {
        return;
      }
      this.create(this.giftee).then((res) => {
        this.$router.push(`/giftees/${res.id}`);
      });
    },
    updateGiftee() {
      if (this.handleErrors()) {
        return;
      }
      this.update(this.giftee).then(() => {
        this.$router.push(`/giftees/${this.$route.params.id}`);
      });
    },
    getGiftee() {
      this.$store.commit(SET_IS_LOADING, true, { root: true });
      return gifteeService.getSingle(this.$route.params.id).then((res) => {
        return res;
      });
    },
    handleErrors() {
      let hasError = false;
      if (!this.giftee.name) {
        this.errors.name = "Name cannot be empty!";
        hasError = true;
      } else {
        this.errors.name = "";
      }
      return hasError;
    },
  },
  mounted() {
    if (this.$route.params.id) {
      this.getGiftee()
        .then((res) => {
          this.giftee = res;
        })
        .finally(() => {
          this.$store.commit(SET_IS_LOADING, false, { root: true });
        });
    } else {
      this.giftee = {
        name: "",
        likes: "",
        dislikes: "",
        hobbies: "",
      };
    }
  },
};
</script>

<style scoped>
h1 {
  margin: 0;
  padding: 2rem 0;
}

.container {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.form {
  display: flex;
  width: 80%;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.form-items {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  width: 100%;
}

.error {
  font-size: 1rem;
  color: red;
  padding: 0;
  border-radius: 3px;
  min-height: 1.25rem;
}

.spacing {
  margin-top: 1.25rem;
}

.input-group {
  margin-bottom: 0.5rem;
  min-height: 3rem;
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

label {
  display: inline-block;
  width: 120px;
  text-align: left;
  padding-right: 1rem;
}

input {
  min-height: 2rem;
  flex-basis: auto;
  flex-grow: 1;
  min-width: 0;
  background: #fbfaf8;
  border: 1px solid #201b22;
  border-radius: 3px;
  padding-left: 0.5rem;
}

.context-buttons {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  width: 100%;
}

.delete-button {
  color: red;
  text-decoration: underline;
  font-size: 1rem;
  cursor: pointer;
}

/* Tablet Styles */
@media only screen and (min-width: 415px) and (max-width: 960px) {
  .form {
    width: 60%;
  }
}

/* Desktop Styles */
@media only screen and (min-width: 961px) {
  .form {
    width: 40%;
  }
}
</style>
