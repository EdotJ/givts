<template>
  <AuthTemplate>
    <form
      class="flex-auto rounded px-8 pt-6 pb-8 mb-4"
      @keyup.enter="login"
    >
      <div
        :class="{'mb-5': !isEmptyUsername}"
        class="mb-3"
      >
        <label
          class="block text-gray-300 text-sm font-bold mb-2"
          for="username"
        >
          Username
        </label>
        <input
          id="username"
          v-model="username"
          :class="{'border-red-500': isEmptyUsername}"
          class="appearance-none rounded w-full py-2 px-3 mb-2 text-gray-300 placeholder-gray-100 leading-tight focus:outline-none focus:shadow-outline bg-blue-800"
          type="text"
          placeholder="Username"
        >
        <p
          v-if="isEmptyUsername"
          class="text-red-500 text-xs italic"
        >
          Please enter a username.
        </p>
      </div>
      <div :class="{'mb-5': !isEmptyPassword && !hasError}">
        <label
          class="block text-gray-300 text-sm font-bold mb-2"
          for="password"
        >
          Password
        </label>
        <input
          id="password"
          v-model="password"
          :class="{'border-red-500': isEmptyPassword, 'mb-1': !isEmptyPassword}"
          class="appearance-none rounded w-full py-2 px-3 text-gray-300 mb-1 leading-tight focus:outline-none focus:shadow-outline bg-blue-800"
          type="password"
          placeholder="******************"
        >
        <p
          v-if="isEmptyPassword"
          class="text-red-500 text-xs italic"
        >
          Please enter a password.
        </p>
      </div>
      <p
        v-if="hasError"
        class="text-red-500 text-xs italic"
      >
        {{ error }}
      </p>
      <div class="flex items-center justify-between">
        <button
          class="bg-gray-300 text-blue-800 hover:bg-blue-800 hover:text-gray-300 font-bold text-sm py-2 px-4 rounded focus:outline-none"
          type="button"
          @click="login"
        >
          Sign In
        </button>
        <router-link
          to="/register"
          class="inline-block align-baseline font-bold text-xs xl:text-sm text-gray-300 hover:text-blue-400"
        >
          Not registered?
        </router-link>
      </div>
    </form>
  </AuthTemplate>
</template>

<script>
import AuthTemplate from "./AuthTemplate";
import axios from "axios";
import qs from "querystring";
import configuration from "../config";

export default {
  name: 'Login',
  components: {
    AuthTemplate,
  },
  data() {
    return {
      username: '',
      password: '',
      isEmptyPassword: false,
      isEmptyUsername: false,
      hasError: false,
      error: '',
      store: this.$root.$data.store,
    }
  },
  methods: {
    login() {
      this.isEmptyPassword = !this.password;
      this.isEmptyUsername = !this.username;
      if (!this.isEmptyPassword && !this.isEmptyUsername) {
        const config = {
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
          }
        }
        const requestBody = {
          username: this.username,
          password: this.password,
        }
        axios.post(`${configuration.hostname}/oauth/login`, qs.stringify(requestBody), config).then((res) => {
          if (res.data.error) {
            this.hasError = true;
            this.error = res.data.error;
          } else {
            this.store.setUsernameAction(res.data.username);
            if (this.store.state.isAuthorizingClient) {
              this.$router.push({
                path: '/oauth/authorize', query: {
                  client_id: this.store.state.clientId,
                  state: this.store.state.clientState,
                  redirect_uri: this.store.state.redirect_uri,
                  code_challenge: this.store.state.codeChallenge,
                  code_challenge_method: this.store.state.codeChallengeMethod
                }
              });
            } else {
              this.$router.push({path: '/'});
            }
          }
        }).catch(err => {
          if (err.response && err.response.status === 400) {
            this.hasError = true;
            this.error = "Invalid credentials or user does not exist!"
          } else {
            this.hasError = true;
            this.error = "An error occurred. Please try again.";
          }
        });
      }

    },
  }
}
</script>