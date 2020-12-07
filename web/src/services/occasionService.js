import authorizationHeader from "../helpers/authorizationHeader";
import store from "../store";
import axios from "@/helpers/axiosInterceptor";
import Router from "../router";
import Vue from "vue";

const GIVTS_API_HOST = process.env.VUE_APP_GIVTS_API_URL;

export default {
  getAll(gifteeId) {
    const authHeader = authorizationHeader();
    const config = {
      headers: authHeader,
    };
    const userId = store.state.currentUser.data.id;
    return axios
      .get(
        `${GIVTS_API_HOST}/users/${userId}/giftees/${gifteeId}/occasions`,
        config
      )
      .then((res) => {
        return res.data.occasions.sort((a, b) => {
          return a.date > b.date;
        });
      })
      .catch((err) => {
        if (err.response.status == 404) {
          Router.push(`/giftees`);
          Vue.$toast("Giftee does not exist!", { type: "error" });
        }
      });
  },
  getSingle(gifteeId, occasionId) {
    const authHeader = authorizationHeader();
    const config = {
      headers: authHeader,
    };
    const userId = store.state.currentUser.data.id;
    return axios
      .get(
        `${GIVTS_API_HOST}/users/${userId}/giftees/${gifteeId}/occasions/${occasionId}`,
        config
      )
      .then((res) => {
        return {
          id: res.data.id,
          name: res.data.name,
          date: res.data.date,
        };
      })
      .catch((err) => {
        if (err.response.status == 404) {
          Router.push(`/giftees/${gifteeId}`);
          Vue.$toast("The occasion was not found", { type: "error" });
        }
      });
  },
  create(gifteeId, occasion) {
    const authHeader = authorizationHeader();
    const config = {
      headers: authHeader,
    };
    const userId = store.state.currentUser.data.id;
    const body = {
      name: occasion.name,
      date: occasion.date,
    };
    return axios
      .post(
        `${GIVTS_API_HOST}/users/${userId}/giftees/${gifteeId}/occasions`,
        body,
        config
      )
      .then((res) => {
        return {
          id: res.id,
          name: res.data.name,
          date: res.data.date,
        };
      });
  },
  update(gifteeId, occasion) {
    const authHeader = authorizationHeader();
    const config = {
      headers: authHeader,
    };
    const userId = store.state.currentUser.data.id;
    const body = {
      name: occasion.name,
      date: occasion.date,
    };
    return axios
      .put(
        `${GIVTS_API_HOST}/users/${userId}/giftees/${gifteeId}/occasions/${occasion.id}`,
        body,
        config
      )
      .then((res) => {
        return {
          id: res.data.id,
          name: res.data.name,
          date: res.data.date,
        };
      });
  },
  delete(gifteeId, occasionId) {
    const authHeader = authorizationHeader();
    const config = {
      headers: authHeader,
    };
    const userId = store.state.currentUser.data.id;
    return axios
      .delete(
        `${GIVTS_API_HOST}/users/${userId}/giftees/${gifteeId}/occasions/${occasionId}`,
        config
      )
      .then((res) => {
        return res;
      });
  },
};
