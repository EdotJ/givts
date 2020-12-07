import Vue from "vue";
import Vuex from "vuex";
import usersMod from "./modules/users";
import currentUserMod from "./modules/currentUser";
import gifteesMod from "./modules/giftees";
import occasionsMod from "./modules/occasions";
import giftsMod from "./modules/gifts";
import createPersistedState from "vuex-persistedstate";
import {
  SET_CODE_VERIFIER,
  SET_OAUTH_STATE,
  SET_IS_LOADING,
  SET_SHOW_DELETE_MODAL,
} from "./mutations.types";
import Router from "../router";

const defaultState = {
  codeVerifier: "",
  oauthState: "",
  isLoading: false,
  showDeletionModal: false,
};

Vue.use(Vuex);
const store = new Vuex.Store({
  state: defaultState,
  mutations: {
    [SET_CODE_VERIFIER]: (state, payload) => {
      state.codeVerifier = payload.verifier;
    },
    [SET_OAUTH_STATE]: (state, payload) => {
      state.oauthState = payload.state;
    },
    [SET_IS_LOADING]: (state, payload) => {
      state.isLoading = payload;
    },
    [SET_SHOW_DELETE_MODAL]: (state, payload) => {
      state.showDeletionModal = payload;
    },
  },
  getters: {},
  actions: {
    logout() {
      localStorage.clear();
      sessionStorage.clear();
      window.location = "/";
    },
  },
  modules: {
    currentUser: {
      namespaced: true,
      ...currentUserMod,
    },
    giftees: {
      namespaced: true,
      ...gifteesMod,
    },
    occasions: {
      namespaced: true,
      ...occasionsMod,
    },
    gifts: {
      namespaced: true,
      ...giftsMod,
    },
    users: {
      namespaced: true,
      ...usersMod,
    },
  },
  plugins: [
    createPersistedState({
      storage: window.sessionStorage,
    }),
  ],
});

export default store;
