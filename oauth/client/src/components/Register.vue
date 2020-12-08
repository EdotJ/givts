<template>
  <AuthTemplate>
    <form
      class="flex-auto rounded px-8 pt-6 pb-8 mb-4"
      @keyup.enter="register"
    >
      <div :class="{'mb-5': !isEmptyEmail}">
        <label
          class="block text-gray-100 text-sm font-bold mb-2"
          for="email"
        >
          Email
        </label>
        <input
          id="email"
          v-model="email"
          :class="{'border-red-500': isEmptyEmail}"
          class="appearance-none rounded w-full py-2 px-3 text-gray-300 placeholder-gray-300 leading-tight focus:outline-none focus:shadow-outline bg-blue-800"
          type="email"
          placeholder="Email"
        >
        <p
          v-if="isEmptyEmail"
          class="text-red-500 text-xs italic"
        >
          Please choose an email.
        </p>
        <p
            v-if="userInvalidEmail"
            class="text-red-500 text-xs italic"
        >
          Please provide a valid email.
        </p>
      </div>
      <div :class="{'mb-5': !isEmptyUsername}">
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
          class="appearance-none rounded w-full py-2 px-3 text-gray-300 placeholder-gray-300 leading-tight focus:outline-none focus:shadow-outline bg-blue-800"
          type="text"
          placeholder="Username"
        >
        <p
          v-if="isEmptyUsername"
          class="text-red-500 text-xs italic"
        >
          Please choose a username.
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
          :class="{'border-red-500': isEmptyPassword}"
          class="appearance-none rounded w-full py-2 px-3 text-gray-300 placeholder-gray-300 mb-3 leading-tight focus:outline-none focus:shadow-outline bg-blue-800"
          type="password"
          placeholder="******************"
        >
        <p
          v-if="isEmptyPassword"
          class="text-red-500 text-xs italic"
        >
          Please choose a password.
        </p>
      </div>
      <p
          v-if="hasError"
          class="text-red-500 text-xs italic"
      >
        An error occurred. Please try again.
      </p>
      <p
          v-if="userAlreadyExists"
          class="text-red-500 text-xs italic"
      >
        User with those credentials already exists!
      </p>
      <p
          v-if="hasGenericError"
          class="text-red-500 text-xs italic"
      >
        Something went wrong. Please try again later.
      </p>
      <div class="flex items-center justify-between">
        <button
          class="bg-gray-300 text-blue-800 hover:bg-blue-800 hover:text-gray-300 placeholder-gray-300 font-bold text-sm py-2 px-4 rounded focus:outline-none focus:shadow-outline"
          type="button"
          @click="register"
        >
          Submit
        </button>
        <router-link
          to="/login"
          class="inline-block align-baseline font-bold text-xs xl:text-sm text-gray-300 hover:text-blue-400"
        >
          Already registered?
        </router-link>
      </div>
    </form>
  </AuthTemplate>
</template>

<script>
import AuthTemplate from "./AuthTemplate";
import qs from 'querystring';
import axios from 'axios';
import configuration from '../config';

export default {
  name: 'Register',
  components: {
    AuthTemplate,
  },
  data() {
    return {
      password: '',
      email: '',
      username: '',
      hasError: false,
      isEmptyPassword: false,
      isEmptyEmail: false,
      isEmptyUsername: false,
      userAlreadyExists: false,
      userInvalidEmail: false,
      hasGenericError: false,
    }
  },
  methods: {
    register() {
      this.isEmptyPassword = !this.password;
      this.isEmptyEmail = !this.email;
      this.isEmptyUsername = !this.username;
      if (!this.isEmptyEmail && !this.isEmptyPassword && !this.isEmptyUsername) {
        const config = {
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
          }
        }
        const requestBody = {
          email: this.email,
          username: this.username,
          password: this.password,
          grant_type: 'authorization_code',
        }
        axios.post(`${configuration.hostname}/oauth/register`, qs.stringify(requestBody), config).then((res) => {
          if (res.data.error) {
            this.hasError = true;
          } else {
            this.$router.push({path: 'login'})
          }
        }).catch(err => {
          if (err.response.status === 400) {
            if (err.response.data.error === "INVALID_EMAIL") {
              this.userInvalidEmail = true;
            } else if (err.response.data.error === "USER_EXISTS") {
              this.userAlreadyExists = true;
            } else {
              this.hasGenericError = true;
            }
          }
        })
      }
    }
  }
}
</script>