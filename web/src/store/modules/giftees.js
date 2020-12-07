import { GET_ALL, GET_SINGLE, UPDATE, CREATE, DELETE } from "../actions.types";
import {
  SET_ALL,
  ADD,
  UPDATE as UPDATE_MUTATION,
  DELETE as DELETE_MUTATION,
  SET_IS_LOADING,
} from "../mutations.types";
import {GET_BY_ID} from "../getters.types";
import gifteeService from "@/services/gifteeService";

const state = {
  isLoading: false,
  giftees: [],
};

const mutations = {
  [SET_ALL](state, payload) {
    state.giftees = payload.map((el) => {
      return {
        name: el.name,
        id: el.id,
        added: el.created_date,
        likes: el.likes,
        dislikes: el.dislikes,
        hobbies: el.hobbies,
      };
    });
  },
  [ADD](state, payload) {
    state.giftees.push({
      name: payload.name,
      id: payload.id,
      added: payload.created_date,
      likes: payload.likes,
      dislikes: payload.dislikes,
      hobbies: payload.hobbies,
    });
  },
  [UPDATE](state, payload) {
    state.giftees = state.giftees.map((giftee) => {
      if (giftee.id !== payload.id) {
        return giftee;
      }
      return {
        ...giftee,
        ...payload,
      };
    });
  },
  [DELETE_MUTATION](state, id) {
    state.giftees = state.giftees.filter((el) => el.id !== id);
  },
};

const actions = {
  [GET_ALL]({ commit, state, rootState }) {
    if (!rootState.currentUser.data.id) {
      return;
    }
    if (state.giftees.length === 0) {
      commit(SET_IS_LOADING, true, { root: true });
    }
    gifteeService
      .getAll()
      .then((res) => {
        commit(SET_ALL, res);
      })
      .finally(() => {
        commit(SET_IS_LOADING, false, { root: true });
      });
  },
  [GET_SINGLE]({ commit, rootState }, { id }) {
    if (!rootState.currentUser.data.id) {
      return;
    }
    gifteeService.getSingle(id).then((res) => {
      commit(ADD, res);
    });
  },
  [UPDATE]({ commit, rootState }, payload) {
    if (!rootState.currentUser.data.id) {
      return;
    }
    return gifteeService.update(payload).then((res) => {
      commit(UPDATE_MUTATION, res);
    });
  },
  [CREATE]({ commit, rootState }, payload) {
    if (!rootState.currentUser.data.id) {
      return;
    }
    return gifteeService.create(payload).then((res) => {
      commit(ADD, res);
      return res;
    });
  },
  [DELETE]({ commit, rootState }, id) {
    if (!rootState.currentUser.data.id) {
      return;
    }
    return gifteeService.delete(id).then(() => {
      commit(DELETE_MUTATION, id);
    });
  },
};

const getters = {
  [GET_BY_ID]: (state) => (id) => {
    return state.giftees.find(el => el.id === id);
  },
}

export default {
  state: state,
  mutations: mutations,
  getters: getters,
  actions: actions,
};
