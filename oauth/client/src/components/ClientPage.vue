<template>
  <div>
    <div class="object-left-top text-xl absolute mt-3 ml-3">
      <router-link
          to="/"
          class="text-white"
      >
        <font-awesome-icon icon="arrow-left"/>
        Home
      </router-link>
    </div>

    <div class="text-gray-300 flex justify-center">
      <div class="flex flex-col w-8/12">
        <div class="self-end mt-5">
          <router-link
              to="/client/add"
              class="flex-1 bg-gray-300 text-blue-800 hover:bg-blue-800 hover:text-gray-300 font-bold text-sm py-2 px-4 rounded focus:outline-none"
          >
            Add new
          </router-link>
        </div>
        <div class="flex mt-5">
          <div
              v-if="isLoading"
              class=""
          >
            Please wait... Loading
          </div>
          <table
              v-if="!isLoading"
              class="flex-auto table-auto min-w-full"
          >
            <thead class="bg-gray-900">
            <tr>
              <th class="w-1/4">
                Name
              </th>
              <th class="w-1/2">
                Description
              </th>
              <th class="w-1/4">
                Actions
              </th>
            </tr>
            </thead>
            <tr
                v-for="(client, index) in clients"
                :key="client.id"
                :class="{ 'bg-blue-800': index % 2 === 0 }"
            >
              <td>
                {{ client.name }}
              </td>
              <td class="overflow-x-auto">
                {{ client.description }}
              </td>
              <td class="flex justify-center">
                <router-link
                    :to="{name: 'Client', params: { id: client.client_id}}"
                    class="pl-5 pr-5 hover:bg-blue-900"
                >
                  <font-awesome-icon
                      class="focus:outline-none"
                      icon="info"
                  />
                </router-link>
                <button
                    class="focus:outline-none pl-5 pr-5 hover:bg-blue-900"
                    @click="deleteClient(index)"
                >
                  <font-awesome-icon icon="times"/>
                </button>
              </td>
            </tr>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import configuration from "../config";

export default {
  data() {
    return {
      clients: [],
      isLoading: true,
    };
  },
  mounted() {
    axios.get(`${configuration.hostname}/oauth/client`).then(res => {
      this.isLoading = false;
      if (!res.data.error) {
        this.clients = res.data.clients.map(c => ({
          ...c,
          isVisibleSecret: false,
        }));
      }
    }).catch(err => {
      if (err.response && err.response.status === 401) {
        this.$router.push({path: '/login'});
      } else {
        console.log(err);
      }
    });
  },
  methods: {
    deleteClient(index) {
      const client = this.clients[index];
      this.clients.splice(index, 1);
      axios.delete(`${configuration.hostname}/oauth/client/${client.client_id}`).catch(err => {
        if (err.response && err.response.status === 401) {
          this.$router.push({path: '/login'});
        } else {
          console.log(err);
        }
      })
    }
  }
}
</script>