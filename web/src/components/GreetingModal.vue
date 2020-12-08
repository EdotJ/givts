<template>
  <div>
    <div class="overlay"></div>
    <div class="base-container">
      <div class="base">
        <div class="greeting">
          Hey! I don't think we've met yet...
        </div>
        <div>
          <div class="input-group">
            <label
              for="name"
              class="input-label"
              :class="{ 'bottom-padding': !error }"
            >
              What should we call you?
            </label>
            <ErrorMessage v-if="error">
              {{ error }}
            </ErrorMessage>
            <input
              id="name"
              name="name"
              v-model="name"
              class="input"
              placeholder="Name"
              @keyup.enter="submitName"
            />
          </div>
          <Button v-on:click.native="submitName">Submit!</Button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Button from "./Button";
import ErrorMessage from "./ErrorMessage";
import { mapActions, mapMutations } from "vuex";
import { SET_USER_DATA } from "@/store/mutations.types";
import { UPDATE_NAME } from "../store/actions.types";
import { USER_MODULE } from "../store/modules";

export default {
  name: "GreetingModal",
  components: { ErrorMessage, Button },
  data() {
    return {
      error: "",
      name: "",
    };
  },
  methods: {
    submitName() {
      this.error = "";
      if (!this.name) {
        this.error = "Name cannot be empty!";
      } else {
        this.updateName({ name: this.name });
      }
    },
    ...mapActions(USER_MODULE, [UPDATE_NAME]),
  },
};
</script>

<style scoped>
.overlay {
  height: calc(100% - 60px);
  width: 100%;
  left: 0;
  position: absolute;
  background-color: black;
  opacity: 0.5;
  display: flex;
  justify-content: center;
}

.base-container {
  position: absolute;
  top: 30%;
  display: flex;
  justify-content: center;
  width: 100%;
  left: 0;
}

.base {
  position: absolute;
  top: 20%;
  width: 75%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  min-height: 4rem;
  background-color: #fbfaf8;
  border: 0.25em solid #cd6565;
  color: tomato;
  border-radius: 5px;
  font-family: "Quicksand", sans-serif;
  font-size: 1rem;
  font-weight: 700;
  letter-spacing: 0.02em;
  text-decoration: none;
  padding: 1em 0.25em;
}

.greeting {
  padding-bottom: 1em;
}

.input-group {
  width: 100%;
  font-size: 1rem;
  color: #201b22;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.bottom-padding {
  padding-bottom: 40px;
}

.button {
  margin-top: 1rem;
}

.error {
  color: red;
}

/* Tablet Styles */
@media only screen and (min-width: 415px) and (max-width: 960px) {
  .base {
    width: 60%;
    font-size: 1.25rem;
  }

  .input {
    width: 60%;
  }
}

/* Desktop Styles */
@media only screen and (min-width: 961px) {
  .base {
    width: 50%;
    font-size: 1.5rem;
  }

  .input {
    width: 50%;
  }
}
</style>
