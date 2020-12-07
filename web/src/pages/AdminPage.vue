<template>
  <div class="top-level" v-if="!isLoading">
    <h1>
      Admin Page
    </h1>
    <div class="users" >
      <div class="user" v-for="user in users" :key="user.id">
        <span class="id">
          {{ user.id }}
        </span>
        <span class="email">
          {{ user.email }}
        </span>
        <span class="role">
          {{ user.role.split("_")[1] }}
        </span>
        <span class="wide-only name">
          {{ user.name }}
        </span>
        <span class="wide-only username">
          {{ user.username }}
        </span>
        <Button
          name="Delete user"
          class="delete-button"
          @click.native="deleteUser(user.id)"
          v-if="user.role !== 'ROLE_ADMIN'"
        >
          <IconBase
            icon-name="delete-icon"
            viewBox="0 0 16 16"
            height="16"
            width="16"
            class="icon-base"
          >
            <TrashCan />
          </IconBase>
        </Button>
      </div>
    </div>
  </div>
</template>

<script>
import { USERS_MODULE } from "../store/modules";
import { mapActions, mapState } from "vuex";
import userService from "../services/userService";
import { SET_IS_LOADING } from "../store/mutations.types";
import { DELETE, GET_ALL } from "../store/actions.types";
import Loader from "../components/Loader";
import Button from "../components/Button";
import IconBase from "../components/IconBase";
import TrashCan from "../components/icons/TrashCan";

export default {
  name: "AdminPage",
  components: { Loader, Button, IconBase, TrashCan },
  computed: {
    ...mapState({
      isLoading: (state) => state.isLoading,
    }),
    ...mapState(USERS_MODULE, {
      users: (state) => state.userList,
    }),
  },
  methods: {
    ...mapActions(USERS_MODULE, {
      getAllUsers: GET_ALL,
      delete: DELETE,
    }),
    deleteUser(id) {
      const user = this.users.find((el) => el.id === id);
      if (user && user.role !== "ROLE_ADMIN") {
        this.delete(id);
      }
    },
  },
  mounted() {
    this.$store.commit(SET_IS_LOADING, true, { root: true });
    userService
      .getUserInfo()
      .then(async (res) => {
        if (res.data && res.data.role && res.data.role !== "ROLE_ADMIN") {
          this.$router.push("/");
        }
        // await
      })
      .catch(() => {
        this.$router.push("/");
      })
      .finally(() => {
        this.$store.commit(SET_IS_LOADING, false, { root: true });
      });
    this.getAllUsers();
  },
};
</script>

<style scoped>
.top-level {
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
}

h1 {
  margin: 0;
  padding: 1.5rem;
}

.users {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 90%;
}

.user {
  height: 3rem;
  width: 100%;
  margin: 0.5rem 0;
  border: 1px solid #201b22;
  border-radius: 3px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 1rem;
  padding: 0 0.5rem;
}

.delete-button {
  border-radius: 100px;
  height: 2.5rem;
  width: 2.5rem;
}

.wide-only {
  display: none;
}

.email {
  flex-grow: 1;
}

.role {
  padding-right: 1rem;
}

/* Tablet Styles */
@media only screen and (min-width: 600px) and (max-width: 960px) {
  .wide-only {
    display: block;
  }

  .role {
    padding-right: 0;
  }

  .email {
    flex-grow: 0;
  }

  .users {
    width: 80%;
  }

}

/* Desktop Styles */
@media only screen and (min-width: 961px) {
  .wide-only {
    display: block;
  }

  .role {
    padding-right: 0;
  }

  .email {
    flex-grow: 0;
  }

  .users {
    width: 60%;
  }
}
</style>
