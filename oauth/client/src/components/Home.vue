<template>
  <div class="top-level">
    <div class="flex justify-center">
      <img
          src="../assets/logo.png"
          class="mt-10 center"
          alt="Project Logo"
      >
    </div>
    <div class="mb-4 text-2xl">
      {{ msg }}
    </div>
    <div :class="{invisible: isHidden}">
      <div
          v-if="!isLoggedIn"
          class="mt-6"
      >
        <router-link
            to="/login"
            class="bg-gray-300 text-blue-800 hover:bg-blue-800 hover:text-gray-300 font-bold py-2 px-4 mx-4 rounded focus:outline-none focus:shadow-outline"
        >
          Login
        </router-link>
        <router-link
            to="/register"
            class="bg-gray-300 text-blue-800 hover:bg-blue-800 hover:text-gray-300 font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
        >
          Register
        </router-link>
      </div>
      <div
          v-if="isLoggedIn"
          class="mt-6"
      >
        <router-link
            to="/client"
            class="bg-gray-300 text-blue-800 hover:bg-blue-800 hover:text-gray-300 font-bold py-2 px-4 mx-4 rounded focus:outline-none focus:shadow-outline"
        >
          Client management
        </router-link>
      </div>
      <div
          v-if="isLoggedIn"
          class="mt-6"
      >
        <button
            @click="logout()"
            class="bg-gray-300 text-blue-800 hover:bg-blue-800 hover:text-gray-300 font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
        >
          Log out!
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import configuration from "../config";

export default {
  name: 'Home',
  props: {
    msg: {type: String, default: ''},
  },
  data() {
    return {
      isLoggedIn: false,
      isHidden: true,
      componentKey: 0,
    }
  },
  mounted() {
    axios.get(`${configuration.hostname}/oauth/authorize`).then((res) => {
      if (res.data.error) {
        this.isLoggedIn = false;
        console.log(res.data.error);
      } else if (res.data.id) {
        if (res.data.id !== -1) {
          this.isLoggedIn = true;
        } else {
          this.isLoggedIn = false;
        }
      }
      this.isHidden = false;
    }).catch(err => {
      this.isLoggedIn = false;
      this.isHidden = false;
      console.log(err);
    });
  },
  methods: {
    logout() {
      axios.post(`${configuration.hostname}/oauth/logout`).then(() => {
        location.reload();
      })
    }
  }
}
</script>