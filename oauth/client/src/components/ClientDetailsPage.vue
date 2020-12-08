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
      <table class="flex flex-col text-gray-300 w-1/2 table-fixed">
        <tr class="mt-10 flex">
          <td class="font-bold">
            Name
          </td>
          <td class="w-5/6">
            {{ client ? client.name : "" }}
          </td>
        </tr>
        <tr class="mt-2 flex">
          <td class="font-bold">
            Description
          </td>
          <td class="w-5/6">
            {{ client ? client.description: "" }}
          </td>
        </tr>
        <tr class="mt-2 flex">
          <td class="font-bold">
            Client ID
          </td>
          <td class="w-5/6">
            {{ client ? client.clientId : "" }}
          </td>
        </tr>
        <tr class="mt-2 flex">
          <td class="font-bold">
            Redirect URI
          </td>
          <td class="w-4/6">
            {{ client ? client.redirectUri : "" }}
          </td>
        </tr>
        <tr class="mt-2 flex">
          <td class="font-bold">
            Grant Types
          </td>
          <td class="w-4/6">
            {{ client ? client.grants.replaceAll("_", " ").replaceAll(",", ", ").toUpperCase() : "" }}
          </td>
        </tr>
        <tr class="mt-2 flex items-center">
          <td class="font-bold">
            <font-awesome-icon
              v-if="!showSecret"
              class="mr-1"
              icon="eye-slash"
              @click="toggleSecret"
            />
            <font-awesome-icon
              v-if="showSecret"
              class="mr-1"
              icon="eye"
              @click="toggleSecret"
            />
            Client Secret
          </td>
          <td class="w-4/6">
            {{ showSecret ? client.clientSecret : "••••••••••••••" }}
          </td>
        </tr>
      </table>
    </div>
  </div>
</template>

<script>
import configuration from "../config";
import axios from "axios";

export default {
  name: 'ClientDetails',
  props: {
    // eslint-disable-next-line vue/require-default-prop
    id: {
      type: String
    }
  },
  data() {
    return {
      client: null,
      showSecret: false,
    }
  },
  mounted() {
    axios.get(`${configuration.hostname}/oauth/client/${this.$props.id}`).then(res => {
      this.client = res.data.client;
    }).catch(err => {
      if (err.response && err.response.status === 401) {
        this.$router.push({path: '/login'});
      } else if (err.response && err.response.status === 404) {
        this.$router.push({path: '/client'});
      }
      console.log(err);
    });
  },
  methods: {
    toggleSecret() {
      this.showSecret = !this.showSecret;
    }
  }
}
</script>