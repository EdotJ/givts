import authorizationHeader from "../helpers/authorizationHeader";
import store from "../store";
import axios from "@/helpers/axiosInterceptor";
import Vue from "vue";
import Router from "../router";

const GIVTS_API_HOST = process.env.VUE_APP_GIVTS_API_URL;

export default {
  getUserInfo() {
    if (!store.state.currentUser.data.accessToken) {
      return;
    }
    let authHeader = authorizationHeader();
    const config = {
      headers: authHeader,
    };
    return axios
      .get(`${GIVTS_API_HOST}/user`, config)
      .then(({ data }) => {
        return {
          id: data.id,
          name: data.name,
          email: data.email,
          username: data.username,
          role: data.role,
        };
      })
      .catch((err) => {
        throw err;
      });
  },
  updateName(name) {
    const config = {
      headers: authorizationHeader(),
    };
    const body = {
      email: store.state.currentUser.data.email,
      username: store.state.currentUser.data.username,
      name: name,
    };
    const userId = store.state.currentUser.data.id;
    return axios
      .put(`${GIVTS_API_HOST}/users/${userId}`, body, config)
      .then((res) => {
        return res.data;
      })
      .catch(() => {
        Vue.$toast.error("Name update failed :(");
      });
  },
  getAll() {
    const config = {
      headers: authorizationHeader(),
    };
    return axios
      .get(`${GIVTS_API_HOST}/users`, config)
      .then((res) => {
        return res.data.users;
      })
      .catch((err) => {
        if (err.response.status === 403 || err.response.status === 401) {
          if (err.response.status === 403) {
            Vue.$toast("You should not be doing this!", { type: "error" });
          }
          Router.push("/");
        }
      });
  },
  delete(id) {
    const config = {
      headers: authorizationHeader(),
    };
    return axios
      .delete(`${GIVTS_API_HOST}/users/${id}`, config)
      .catch((err) => {
        if (err.response.status === 403 || err.response.status === 401) {
          if (err.response.status === 403) {
            Vue.$toast("You should not be doing this!", { type: "error" });
          }
          Router.push("/");
        }
      });
  },
  getSingle(id) {
    const config = {
      headers: authorizationHeader(),
    };
    return axios.get(`${GIVTS_API_HOST}/users/${id}`, config).then((res) => {
      return res.data;
    });
  },
};
