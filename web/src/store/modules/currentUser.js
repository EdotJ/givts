import userService from "../../services/userService";
import {
  SET_IS_LOADING,
  SET_NAME_UPDATE_TRIGGER,
  SET_USER_AUTH_DATA,
  SET_USER_DATA,
  SET_ACCESS_TOKEN,
  SET_REFRESH_TOKEN,
} from "../mutations.types";
import {
  GET_ACCESS_TOKEN,
  GET_USER_INFO,
  UPDATE_NAME,
  DELETE,
  LOGOUT,
} from "../actions.types";
import authService from "@/services/authService";
import Vue from "vue";
import Router from "@/router";
import { GET_LOGGED_IN, HAS_NAME, IS_ADMIN } from "../getters.types";

const user = JSON.parse(localStorage.getItem("user"));
const loggedOutState = {
  status: {
    wasNameUpdated: false,
  },
  data: {},
};
const loggedInState = {
  status: {
    wasNameUpdated: false,
  },
  data: user,
};

const state = user ? loggedInState : loggedOutState;
const mutations = {
  [SET_USER_AUTH_DATA](state, payload) {
    let now = new Date();
    state.status = {
      ...state.status,
      wasNameUpdated: false,
    };
    state.data = {
      ...state.data,
      accessToken: payload.accessToken,
      refreshToken: payload.refreshToken,
      accessTokenExpiresAt: new Date(
        now.getTime() + payload.accessTokenExpiresIn
      ),
      refreshTokenExpiresAt: new Date(
        now.getTime() + payload.refreshTokenExpiresIn
      ),
    };
    localStorage.setItem("user", JSON.stringify(state.data));
  },
  [SET_ACCESS_TOKEN](state, token) {
    state.data.accessToken = token;
  },
  [SET_REFRESH_TOKEN](state, token) {
    state.data.refreshToken = token;
  },
  [SET_USER_DATA](state, payload) {
    const localStorageUser = JSON.parse(localStorage.getItem("user"));
    state.data = {
      ...state.data,
      ...localStorageUser,
      ...payload,
    };
    localStorage.setItem("user", JSON.stringify(state.data));
  },
  [SET_NAME_UPDATE_TRIGGER](state, payload) {
    state = {
      ...state,
      status: {
        ...state.status,
        wasNameUpdated: payload,
      },
    };
  },
  resetState(state) {
    state.data = {};
  }
};

const actions = {
  [UPDATE_NAME]({ commit }, { name }) {
    userService.updateName(name).then((name) => {
      commit(SET_NAME_UPDATE_TRIGGER, true);
      commit(SET_USER_DATA, name);
    });
  },
  [GET_ACCESS_TOKEN](context) {
    context.commit(SET_IS_LOADING, true, { root: true });
    authService.getAccessToken().then((res) => {
      context.commit(SET_USER_AUTH_DATA, res);
      context.dispatch(GET_USER_INFO, false).then(() => {
        Router.push("/");
      });
    });
  },
  [GET_USER_INFO](context) {
    if (context.getters.loggedIn) {
      context.commit(SET_IS_LOADING, true, { root: true });
    }
    userService
      .getUserInfo()
      .then((res) => {
        context.commit(SET_USER_DATA, res);
      })
      .catch(() => {
        if (context.getters.loggedIn) {
          Vue.$toast.error("Failed fetching your data from Authenticator");
        }
      })
      .finally(() => {
        context.commit(SET_IS_LOADING, false, { root: true });
      });
  },
  [DELETE]({ state, dispatch }) {
    userService.delete(state.data.id).then(() => {
      dispatch(LOGOUT, null, { root: true });
    });
  },
};

export default {
  state: state,
  mutations: mutations,
  getters: {
    [GET_LOGGED_IN]: (state) => {
      return !!state.data.accessToken;
    },
    [HAS_NAME]: (state) => {
      return !!state.data.name;
    },
    [IS_ADMIN]: (state) => {
      return state.data.role === "ROLE_ADMIN";
    },
  },
  actions: actions,
};
