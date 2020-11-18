<template>
  <div>
    <div class="object-left-top text-xl absolute mt-3 ml-3">
      <router-link
        to="/client"
        class="text-white"
      >
        <font-awesome-icon icon="arrow-left" />
        Clients
      </router-link>
    </div>
    <div class="flex justify-center">
      <div class="flex md:w-5/6 lg:w-4/6 xl:w-1/2">
        <form
          class="flex-auto rounded px-8 pt-6 pb-8 mb-4 mt-20"
          @keyup.enter="submit"
        >
          <div class="mb-2">
            <label
              class="block text-gray-300 text-md font-bold mb-2 mr-5"
              for="name"
            >
              Name
            </label>
            <input
              id="name"
              v-model="name"
              :class="{'border-red-500': isEmptyName, 'mb-8': !isEmptyName}"
              class="appearance-none rounded w-full py-2 px-3 text-gray-300 mb-3 leading-tight focus:outline-none focus:shadow-outline bg-blue-800"
              type="text"
              placeholder="Name"
            >
            <p
              v-if="isEmptyName"
              class="text-red-500 text-xs italic"
            >
              Please enter a name for the client.
            </p>
          </div>
          <div class="mb-2">
            <label
              class="block text-gray-300 text-md font-bold mb-2 mr-5"
              for="description"
            >
              Description
            </label>
            <textarea
              id="description"
              v-model="description"
              class="appearance-none resize-none rounded w-full py-2 px-3 text-gray-300 mb-3 leading-tight focus:outline-none focus:shadow-outline bg-blue-800 h-20"
              placeholder="Description"
            />
          </div>
          <div class="mb-2">
            <label
              class="block text-gray-300 text-md font-bold mb-2 mr-5"
              for="redirectUri"
            >
              Redirect URI
            </label>
            <input
              id="redirectUri"
              v-model="redirectUri"
              :class="{'border-red-500': isEmptyRedirectUri, 'mb-8': !isEmptyRedirectUri}"
              class="appearance-none rounded w-full py-2 px-3 text-gray-300 mb-3 leading-tight focus:outline-none focus:shadow-outline bg-blue-800"
              type="text"
              placeholder="Redirect URI"
            >
            <p
              v-if="isEmptyRedirectUri"
              class="text-red-500 text-xs italic"
            >
              Please enter a redirect URI for the
              client.
            </p>
          </div>

          <div class="flex items-center">
            <button
              class="bg-gray-300 text-blue-800 hover:bg-blue-800 hover:text-gray-300 font-bold text-sm py-2 px-4 mr-5 rounded focus:outline-none"
              type="button"
              @click="submit"
            >
              Submit
            </button>
            <div class="flex justify-center">
              <p
                v-if="hasError"
                class="text-red-500 text-md italic"
              >
                {{ error }}
              </p>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import configuration from "../config";
import axios from "axios";

export default {
  data() {
    return {
      isEmptyName: false,
      isEmptyDesc: false,
      isEmptyRedirectUri: false,
      name: '',
      description: '',
      redirectUri: '',
      hasError: false,
      error: '',
    }
  },
  mounted() {
    axios.get(`${configuration.hostname}/oauth/authorize`).catch(err => {
      if (err.response && err.response.status === 401) {
        this.$router.push({path: '/login'});
      }
    });
  },
  methods: {
    submit() {
      this.isEmptyName = !this.name;
      this.isEmptyRedirectUri = !this.redirectUri;
      this.isEmptyDesc = !this.description
      if (!this.isEmptyName && !this.isEmptyRedirectUri) {
        const body = {
          name: this.name,
          description: this.description,
          redirectUri: this.redirectUri,
          grants: "authorization_code,refresh_token",
        }
        const config = {
          headers: {
            'Content-Type': 'application/json',
          }
        }
        axios.post(`${configuration.hostname}/oauth/client`, JSON.stringify(body), config).then(() => {
          this.$router.push({path: '/client'});
        }).catch(err => {
          if (err.response && err.response.status === 401) {
            this.$router.push({path: '/login'});
          } else if (err.response && err.response.status === 400) {
            console.log(err.response);
            this.hasError = true;
            if (err.response.data.error_code === "INVALID_URL") {
              this.error = "Invalid Redirect URI";
            }
          }
          console.log(err);
        });
      }
    }
  }
}
</script>
