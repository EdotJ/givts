<template>
  <div v-if="showModal && !isLoading">
    <div class="overlay" @click="dismissModal()"></div>
    <div class="base-container">
      <div class="base">
        <div class="warning">
          Are you sure you want to delete your account?
        </div>
        <div class="buttons">
          <Button class="dismissal" @click.native="dismissModal()">
            Cancel
          </Button>
          <Button class="confirmation" @click.native="deleteAccount()">
            Delete
          </Button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Button from "./Button";
import { mapActions, mapMutations, mapState } from "vuex";
import { UPDATE_NAME, DELETE } from "../store/actions.types";
import { USER_MODULE } from "../store/modules";
import { SET_SHOW_DELETE_MODAL } from "../store/mutations.types";

export default {
  name: "DeleteModal",
  components: { Button },
  methods: {
    ...mapActions(USER_MODULE, {
      updateName: UPDATE_NAME,
      delete: DELETE,
    }),
    ...mapMutations([SET_SHOW_DELETE_MODAL]),
    dismissModal() {
      this.setShowDeleteModal(false);
    },
    deleteAccount() {
      this.delete();
    },
  },
  computed: {
    ...mapState({
      showModal: (state) => state.showDeletionModal,
      isLoading: state => state.isLoading,
    }),
  },
};
</script>

<style scoped>
.container {
  position: absolute;
  left: 0;
  top: 60px;
  width: 100%;
  display: flex;
  justify-content: center;
}

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
  width: 75%;
  left: 0%;
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

.warning {
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

.buttons {
  display: flex;
  width: 100%;
  justify-content: space-between;
}

.dismissal {
  margin-left: 2rem;
  font-size: 1rem;
}

.confirmation {
  margin-right: 2rem;
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
