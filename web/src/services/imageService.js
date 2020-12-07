import authorizationHeader from "../helpers/authorizationHeader";
import axios from "../helpers/axiosInterceptor";
import Vue from "vue";

const GIVTS_API_HOST = process.env.VUE_APP_GIVTS_API_URL;

export default {
  getImage(image_id) {
    const authHeader = authorizationHeader();
    const config = {
      headers: authHeader,
      responseType: 'blob',
    };
    return axios
      .get(`${GIVTS_API_HOST}/files/${image_id}`, config)
      .then((res) => {
        return res.data;
      })
      .catch((err) => {
        Vue.$toast("Failed fetching image", { type: "error" });
        console.log(err);
      });
  },
};
