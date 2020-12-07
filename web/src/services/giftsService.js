import authorizationHeader from "../helpers/authorizationHeader";
import store from "../store";
import axios from "@/helpers/axiosInterceptor";

const GIVTS_API_HOST = process.env.VUE_APP_GIVTS_API_URL;

export default {
  getAll(gifteeId, occasionId) {
    const authHeader = authorizationHeader();
    const config = {
      headers: authHeader,
    };
    const userId = store.state.currentUser.data.id;
    return axios
      .get(
        `${GIVTS_API_HOST}/users/${userId}/giftees/${gifteeId}/occasions/${occasionId}/gifts`,
        config
      )
      .then((res) => {
        return res.data.gifts;
      });
  },
  getSingle(gifteeId, occasionId, giftId) {
    const authHeader = authorizationHeader();
    const config = {
      headers: authHeader,
    };
    const userId = store.state.currentUser.data.id;
    return axios
      .get(
        `${GIVTS_API_HOST}/users/${userId}/giftees/${gifteeId}/occasions/${occasionId}/gifts/${giftId}`,
        config
      )
      .then((res) => {
        return res.data;
      });
  },
  create(gifteeId, occasionId, gift, file) {
    const authHeader = authorizationHeader();
    const config = {
      headers: authHeader,
    };
    const userId = store.state.currentUser.data.id;
    let body = constructFormRequest(gift, file);
    return axios
      .post(
        `${GIVTS_API_HOST}/users/${userId}/giftees/${gifteeId}/occasions/${occasionId}/gifts`,
        body,
        config
      )
      .then((res) => {
        return res.data;
      });
  },
  update(gifteeId, occasionId, gift, file) {
    const authHeader = authorizationHeader();
    const config = {
      headers: authHeader,
    };
    const userId = store.state.currentUser.data.id;
    let body = constructFormRequest(gift, file);
    return axios
      .put(
        `${GIVTS_API_HOST}/users/${userId}/giftees/${gifteeId}/occasions/${occasionId}/gifts/${gift.id}`,
        body,
        config
      )
      .then((res) => {
        return res.data;
      });
  },
  delete(gifteeId, occasionId, giftId) {
    const authHeader = authorizationHeader();
    const config = {
      headers: authHeader,
    };
    const userId = store.state.currentUser.data.id;
    return axios
      .delete(
        `${GIVTS_API_HOST}/users/${userId}/giftees/${gifteeId}/occasions/${occasionId}/gifts/${giftId}`,
        config
      )
      .then((res) => {
        return res;
      });
  },
};

const constructFormRequest = (gift, file) => {
  let formData = new FormData();
  if (file) {
    formData.append("image", file);
  }
  formData.append("data", new Blob([JSON.stringify(gift)], {type: "application/json"}));
  return formData;
};
