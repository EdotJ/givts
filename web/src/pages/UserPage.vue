<template>
  <div class="container">
    <div class="form" v-if="!isLoading" v-on:keyup.enter="updateUser()">
      <div class="form-items">
        <div class="error" v-if="errors.name">
          {{ errors.name }}
        </div>
        <div class="input-group" :class="{ spacing: !errors.name }">
          <label for="name">Name</label>
          <input
            type="text"
            v-model="user.name"
            id="name"
            placeholder="Leeroy"
          />
        </div>
      </div>
      <div class="context-buttons">
        <div class="delete-button" @click="bringUpModal()">
          Delete your account...
        </div>
        <Button class="submit-button" v-on:click.native="updateUser()">
          Submit
        </Button>
      </div>
    </div>
    <DeleteModal />
  </div>
</template>

<script>
import { mapMutations, mapState, mapActions } from "vuex";
import userService from "../services/userService";
import { USER_MODULE } from "../store/modules";
import {
  SET_SHOW_DELETE_MODAL,
  SET_IS_LOADING,
} from "../store/mutations.types";
import { UPDATE_NAME } from "../store/actions.types";
import DeleteModal from "../components/DeleteModal";
import Button from "../components/Button";

export default {
  name: "UserPage",
  components: {
    DeleteModal,
    Button,
  },
  data() {
    return {
      user: "",
      errors: {
        name: "",
      },
    };
  },
  computed: {
    ...mapState({
      isLoading: (state) => state.isLoading,
    }),
    ...mapState(USER_MODULE, {
      userId: (state) => state.data.id,
    }),
  },
  methods: {
    ...mapMutations([SET_IS_LOADING, SET_SHOW_DELETE_MODAL]),
    ...mapActions(USER_MODULE, {
      updateUserName: UPDATE_NAME,
    }),
    bringUpModal() {
      this.setShowDeleteModal(true);
    },
    updateUser() {
      this.updateUserName({name: this.user.name}).then(() => {
        this.$router.push("/");
      });
    },
  },
  mounted() {
    this.setIsLoading(true);
    userService
      .getSingle(this.userId)
      .then((res) => {
        this.user = res;
      })
      .finally(() => {
        this.setIsLoading(false);
      });
  },
};
</script>

<style scoped>
.container {
  width: 100%;
  display: flex;
  justify-content: center;
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

@media only screen and (min-width: 961px) {
  .form {
    width: 40%;
  }
}
</style>
