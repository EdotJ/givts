<template>
  <div class="container">
    <h1>{{ this.$route.params.occasionId ? "Edit" : "Add" }} an occasion</h1>
    <div class="form" v-if="!isLoading" v-on:keyup.enter="$route.params.giftId ? updateOccasion() : createOccasion()">
      <div class="form-items">
        <div class="error" v-if="errors.name">
          {{ errors.name }}
        </div>
        <div class="input-group" :class="{ spacing: !errors.name }">
          <label for="name">Name</label>
          <input
            type="text"
            v-model="occasion.name"
            id="name"
            placeholder="Very cool event"
          />
        </div>
        <div class="error" v-if="errors.date">
          {{ errors.date }}
        </div>
        <div class="input-group" :class="{ spacing: !errors.date }">
          <label for="date">Date</label>
          <input type="date" v-model="occasion.date" id="date" />
        </div>
      </div>
      <div class="context-buttons">
        <div
          class="delete-button"
          v-if="$route.params.occasionId"
          @click="deleteOccasion()"
        >
          Delete occasion
        </div>
        <Button
          class="submit-button"
          v-on:click.native="
            $route.params.occasionId ? updateOccasion() : createOccasion()
          "
        >
          Submit
        </Button>
      </div>
    </div>
  </div>
</template>

<script>
import { OCCASIONS_MODULE } from "../store/modules";
import { GET_SINGLE, CREATE, UPDATE, DELETE } from "../store/actions.types";
import occasionService from "../services/occasionService";
import { mapActions, mapState } from "vuex";
import Button from "../components/Button";
import { SET_IS_LOADING } from "../store/mutations.types";

export default {
  name: "Occasion",
  components: {
    Button,
  },
  data() {
    return {
      occasionId: parseInt(this.$route.params.occasionId),
      occasion: {},
      errors: {
        name: "",
        date: "",
      },
    };
  },
  computed: {
    ...mapState({
      isLoading: (state) => state.isLoading,
    }),
  },
  methods: {
    ...mapActions(OCCASIONS_MODULE, {
      create: CREATE,
      update: UPDATE,
      delete: DELETE,
    }),
    createOccasion() {
      if (this.handleErrors()) {
        return;
      }
      this.create({
        gifteeId: this.$route.params.gifteeId,
        occasion: this.occasion,
      }).then(() => {
        this.$router.push(`/giftees/${this.$route.params.gifteeId}`);
      });
    },
    updateOccasion() {
      if (this.handleErrors()) {
        return;
      }
      this.update({
        gifteeId: this.$route.params.gifteeId,
        occasion: this.occasion,
      }).then(() => {
        this.$router.push(`/giftees/${this.$route.params.gifteeId}`);
      });
    },
    deleteOccasion() {
      this.delete({
        gifteeId: this.$route.params.gifteeId,
        occasionId: this.occasionId,
      }).then(() => {
        this.$router.push(`/giftees/${this.$route.params.gifteeId}`);
      });
    },
    getOccasion() {
      this.$store.commit(SET_IS_LOADING, true, { root: true });
      return occasionService
        .getSingle(this.$route.params.gifteeId, this.$route.params.occasionId)
        .then((res) => {
          return res;
        });
    },
    handleErrors() {
      let hasError = false;
      if (!this.occasion.name) {
        this.errors.name = "Name cannot be empty!";
        hasError = true;
      } else {
        this.errors.name = "";
      }
      if (!this.occasion.date) {
        this.errors.date = "Date cannot be empty!";
        hasError = true;
      } else {
        this.errors.date = "";
      }
      return hasError;
    },
  },
  mounted() {
    if (this.$route.params.occasionId) {
      this.getOccasion()
        .then((res) => {
          this.occasion = res;
        })
        .finally(() => {
          this.$store.commit(SET_IS_LOADING, false, { root: true });
        });
    } else {
      this.occasion = {
        name: "",
        date: "",
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
  min-width: 80px;
  text-align: left;
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
  justify-content: space-between;
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
