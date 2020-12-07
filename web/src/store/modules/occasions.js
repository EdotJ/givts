import { GET_ALL, CREATE, UPDATE, DELETE } from "../actions.types";
import {
  SET_ALL,
  ADD,
  UPDATE as UPDATE_MUTATION,
  DELETE as DELETE_MUTATION,
  SET_IS_LOADING,
  SET_SELECTED_OCCASION
} from "../mutations.types";
import occasionService from "@/services/occasionService";

const state = {
  isLoading: false,
  occasions: [],
  selectedOccasion: -1,
};

const mutations = {
  [SET_IS_LOADING](state, payload) {
    state.isLoading = payload;
  },
  [SET_ALL](state, payload) {
    state.occasions = payload;
  },
  [ADD](state, payload) {
    state.occasions.push(payload);
    state.occasions.sort((a, b) => {
      return a.date > b.date;
    });
  },
  [UPDATE_MUTATION](state, payload) {
    state.occasions = state.occasions.map((occasion) => {
      if (occasion.id !== payload.id) {
        return occasion;
      }
      return {
        ...occasion,
        ...payload,
      };
    });
    state.occasions.sort((a, b) => {
      return a.date > b.date;
    });
  },
  [DELETE_MUTATION](state, payload) {
    state.occasions = state.occasions.filter(
      (occasion) => occasion.id !== payload
    );
  },
  [SET_SELECTED_OCCASION](state, payload) {
    state.selectedOccasion = payload;
  }
};

const actions = {
  [GET_ALL]({ commit }, { gifteeId }) {
    commit(SET_IS_LOADING, true);
    occasionService.getAll(gifteeId).then((res) => {
      commit(SET_ALL, res);
    }).finally(() => {
      commit(SET_IS_LOADING, false);
    });
  },
  [CREATE]({ commit }, payload) {
    return occasionService
      .create(payload.gifteeId, payload.occasion)
      .then((res) => {
        commit(ADD, res);
      });
  },
  [UPDATE]({ commit }, payload) {
    return occasionService
      .update(payload.gifteeId, payload.occasion)
      .then((res) => {
        commit(UPDATE_MUTATION, res);
      });
  },
  [DELETE]({ commit }, { gifteeId, occasionId }) {
    occasionService.delete(gifteeId, occasionId).then(() => {
      commit(DELETE_MUTATION, occasionId);
    });
  },
};

const getters = {};

export default {
  state: state,
  mutations: mutations,
  getters: getters,
  actions: actions,
};
