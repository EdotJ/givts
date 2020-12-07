import authorizationHeader from "../helpers/authorizationHeader";
import store from "../store";
import axios from "@/helpers/axiosInterceptor";

const GIVTS_API_HOST = process.env.VUE_APP_GIVTS_API_URL;

export default {
  getAll() {
    const authHeader = authorizationHeader();
    const config = {
      headers: authHeader,
    };
    const userId = store.state.currentUser.data.id;
    return axios
      .get(`${GIVTS_API_HOST}/users/${userId}/giftees`, config)
      .then((res) => {
        return res.data.giftees;
      });
  },
  getSingle(id) {
    const authHeader = authorizationHeader();
    const config = {
      headers: authHeader,
    };
    const userId = store.state.currentUser.data.id;
    return axios
      .get(`${GIVTS_API_HOST}/users/${userId}/giftees/${id}`, config)
      .then((res) => {
        return res.data;
      });
  },
  create(giftee) {
    const authHeader = authorizationHeader();
    const config = {
      headers: authHeader,
    };
    const userId = store.state.currentUser.data.id;
    const body = {
      name: giftee.name,
      likes: giftee.likes,
      dislikes: giftee.dislikes,
      hobbies: giftee.hobbies,
    };
    return axios
      .post(`${GIVTS_API_HOST}/users/${userId}/giftees`, body, config)
      .then((res) => {
        return res.data;
      });
  },
  update(giftee) {
    const authHeader = authorizationHeader();
    const config = {
      headers: authHeader,
    };
    const userId = store.state.currentUser.data.id;
    const body = {
      name: giftee.name,
      likes: giftee.likes,
      dislikes: giftee.dislikes,
      hobbies: giftee.hobbies,
    };
    return axios
      .put(`${GIVTS_API_HOST}/users/${userId}/giftees/${giftee.id}`, body, config)
      .then((res) => {
        return res.data;
      });
  },
  delete(id) {
    const authHeader = authorizationHeader();
    const config = {
      headers: authHeader,
    };
    const userId = store.state.currentUser.data.id;
    return axios
      .delete(`${GIVTS_API_HOST}/users/${userId}/giftees/${id}`, config)
      .then((res) => {
        return res;
      });
  },
};
