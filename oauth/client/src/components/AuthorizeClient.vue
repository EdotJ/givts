<template>
  <div>
    <div
      v-if="error"
      class="mt-10 flex flex-col items-center"
    >
      <div class="text-red-700 text-4xl">
        {{ error }}
      </div>
      <router-link
        to="/"
        class="bg-gray-300 text-blue-800 hover:bg-blue-800 hover:text-gray-300 font-bold text-2xl py-5 px-10 ml-2 rounded focus:outline-none"
      >
        Home
      </router-link>
    </div>
    <div v-if="!error && !isLoading">
      <h1 class="text-gray-300 text-2xl mt-10">
        Hi {{ store.state.username }}! {{ clientName }} is asking for permission to access your data
      </h1>
      <h2 class="text-gray-300 text-xl mt-10">
        Will you grant it?
      </h2>
      <div class="flex flex-col">
        <div class="flex justify-center mt-48">
          <button
            class="bg-gray-300 text-blue-800 hover:bg-blue-800 hover:text-gray-300 font-bold text-2xl py-5 px-10 mr-2 rounded focus:outline-none"
            type="button"
            @click="redirect"
          >
            YES
          </button>
          <button
            class="bg-gray-300 text-blue-800 hover:bg-blue-800 hover:text-gray-300 font-bold text-2xl py-5 px-10 ml-2 rounded focus:outline-none"
            type="button"
            @click="redirectError"
          >
            NO
          </button>
        </div>
        <router-link
          to="/register"
          class="inline-block align-baseline font-bold text-l mt-5 text-gray-300 hover:text-blue-400"
        >
          Not registered? Click here!
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
import configuration from "../config";
import axios from "axios";
import qs from "querystring";

const checkError = (query) => {
  if (!query.client_id) {
    return "No client id provided!";
  }
  if (!query.code_challenge) {
    return "No code challenge provided!";
  }
  return '';
}

export default {
  data() {
    return {
      isLoading: true,
      error: '',
      store: this.$root.$data.store,
      clientName: '',
      clientRedirectUri: '',
    };
  },
  mounted() {
    this.error = checkError(this.$route.query);
    if (this.error && this.$route.query.redirect_uri) {
      location.replace(`${this.$route.query.redirect_uri}?error=invalid_request`);
    }
    if (!this.error) {
      this.store.setClientIdAction(this.$route.query.client_id);
      this.store.setClientStateAction(this.$route.query.state);
      this.store.setRedirectUriAction(this.$route.query.redirect_uri);
      this.store.setCodeChallengeAction(this.$route.query.code_challenge);
      if (!this.$route.query.code_challenge_method) {
        this.store.setCodeChallengeMethodAction("S256");
      } else {
        this.store.setCodeChallengeMethodAction(this.$route.query.code_challenge_method);
      }
      this.store.setAuthorizationAction(true);
      axios.get(`${configuration.hostname}/oauth/authorize`).then(() => {
        axios.get(`${configuration.hostname}/oauth/client/${this.$route.query.client_id}`).then(res => {
          this.isLoading = false;
          this.clientName = res.data.client.name;
          this.clientRedirectUri = res.data.client.redirectUri;

          if (!this.store.state.redirectUri) {
            this.store.setRedirectUriAction(this.clientRedirectUri);
          } else if (!this.clientRedirectUri.startsWith(this.store.state.redirectUri)) {
            this.error = "Redirect URI hosts are not matching!"
          }
        }).catch(err => {
          if (err.response && err.response.status === 404) {
            this.error = "Client with requested client ID not found";
          }
        });
      }).catch(err => {
        this.isLoading = false;
        if (err.response && err.response.status === 401) {
          this.$router.push({path: '/login'});
        }
      })
    }
  },
  methods: {
    redirect() {
      const config = {
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        }
      }
      const body = {
        client_id: this.store.state.clientId,
        response_type: 'code',
        state: this.store.state.clientState,
        code_challenge: this.store.state.codeChallenge,
        code_challenge_method: this.store.state.codeChallengeMethod,
      }
      axios.post(`${configuration.hostname}/oauth/authorize`, qs.stringify(body), config).then(res => {
        const redirectUri = this.store.state.redirectUri;
        const clientState = this.store.state.clientState;
        console.log(res);
        location.replace(`${redirectUri}?code=${res.data.code}${clientState ? `&state=${clientState}` : ''}`);
      }).catch(err => {
        this.error = "Could not authorize request!";
        console.log(err.response);
      });
    },
    redirectError() {
      const redirectUri = this.store.state.redirectUri;
      location.replace(`${redirectUri}?error=access_denied`);
    }
  }
}
</script>