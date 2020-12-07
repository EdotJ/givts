import axios from "axios";
import Vue from "vue";
import authService from "../services/authService";
const instance = axios.create();
instance.interceptors.response.use(
  (response) => {
    return response;
  },
  async (error) => {
    if (error.response && error.response.status === 401) {
      await authService.refreshToken();
    } else if (error.response && error.response.status === 403) {
      Vue.$toast.error("You cannot access this resource!");
    }
    return Promise.reject(error);
  }
);
export default instance;
