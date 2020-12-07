import { CREATE, DELETE, GET_ALL, GET_SINGLE, UPDATE } from "../actions.types";
import giftsService from "@/services/giftsService";
import {
  ADD,
  DELETE as DELETE_MUTATION,
  SET_ALL,
  SET_IS_LOADING,
  UPDATE as UPDATE_MUTATION,
} from "../mutations.types";
import { GET_BY_OCCASION } from "../getters.types";
import Router from "@/router";

const state = {
  isLoading: false,
  gifts: {},
};

const mutations = {
  [SET_ALL](state, payload) {
    state.gifts = {
      ...state.gifts,
      [payload.occasionId]: payload.gifts,
    };
  },
  [SET_IS_LOADING](state, payload) {
    state.isLoading = payload;
  },
  [DELETE_MUTATION](state, { occasionId, giftId }) {
    state.gifts = {
      ...state.gifts,
      [occasionId]: state.gifts[occasionId].filter((el) => el.id !== giftId),
    };
  },
  [UPDATE_MUTATION](state, {occasionId, gift}) {
    state.gifts = {
      ...state.gifts,
      [occasionId]: state.gifts[occasionId].map(el => {
        if (el.id !== gift.id) {
          return el;
        }
        return {
          ...el,
          ...gift,
        }
      })
    }
  },
  [ADD](state, {occasionId, gift}) {
    if (state.gifts[occasionId]) {
    state.gifts[occasionId].push(gift);
    } else {
      state.gifts = {
        ...state.gifts,
        [occasionId]: [gift],
      }
    }
  }
};

const actions = {
  [GET_ALL]({ commit, state }, { gifteeId, occasionId }) {
    if (
      !state.gifts[occasionId] ||
      (state.gifts[occasionId] && state.gifts[occasionId].length === 0)
    ) {
      commit(SET_IS_LOADING, true);
    }
    giftsService
      .getAll(gifteeId, occasionId)
      .then((res) => {
        commit(SET_ALL, { occasionId: occasionId, gifts: res });
      })
      .finally(() => {
        commit(SET_IS_LOADING, false);
      });
  },
  [GET_SINGLE]({ commit, rootState }, { gifteeId, occasionId, giftId }) {
    if (!rootState.currentUser.data.id) {
      return;
    }
    giftsService.getSingle(gifteeId, occasionId, giftId).then((res) => {
      commit(ADD, res);
    });
  },
  [UPDATE]({ commit, rootState }, { gifteeId, occasionId, gift, file }) {
    if (!rootState.currentUser.data.id) {
      return;
    }
    return giftsService.update(gifteeId, occasionId, gift, file).then((res) => {
      commit(UPDATE_MUTATION, {occasionId, gift: res});
      Router.push(`/giftees/${gifteeId}`);
    });
  },
  [CREATE]({ commit, rootState }, { gifteeId, occasionId, gift, file }) {
    if (!rootState.currentUser.data.id) {
      return;
    }
    return giftsService.create(gifteeId, occasionId, gift, file).then((res) => {
      commit(ADD, {occasionId, gift: res});
      Router.push(`/giftees/${gifteeId}`);
    });
  },
  [DELETE]({ commit, rootState }, { gifteeId, occasionId, giftId }) {
    if (!rootState.currentUser.data.id) {
      return;
    }
    giftsService.delete(gifteeId, occasionId, giftId).then(() => {
      commit(DELETE_MUTATION, { occasionId, giftId });
    });
  },
};

const getters = {
  [GET_BY_OCCASION]: (state) => (id) => {
    return state.gifts[id];
  },
};

export default {
  state: state,
  mutations: mutations,
  getters: getters,
  actions: actions,
};
