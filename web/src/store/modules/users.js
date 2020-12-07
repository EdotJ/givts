import userService from "../../services/userService";
import { GET_ALL, DELETE } from "../actions.types";
import { DELETE as DELETE_MUTATION, SET_ALL } from "../mutations.types";

const defaultState = {
  isLoading: false,
  userList: [],
  count: 0,
};

const mutations = {
  [SET_ALL](state, payload) {
    state.userList = payload;
    state.count = payload.length;
  },
  [DELETE_MUTATION](state, payload) {
    state.userList = state.userList.filter((el) => el.id !== payload);
    state.count = state.userList.length;
  },
};

const actions = {
  [GET_ALL]({ commit }) {
    return userService.getAll().then((res) => {
      commit(SET_ALL, res);
    });
  },
  [DELETE]({ commit }, id) {
    return userService.delete(id).then(() => {
      commit(DELETE_MUTATION, id);
    });
  },
};

const getters = {};

export default {
  state: defaultState,
  mutations: mutations,
  getters: getters,
  actions: actions,
};
