import pkceChallenge from "pkce-challenge";
import store from "../store";
import axios from "@/helpers/axiosInterceptor";
import {
  SET_CODE_VERIFIER, SET_IS_LOADING,
  SET_OAUTH_STATE,
  SET_USER_AUTH_DATA
} from "../store/mutations.types";
import {LOGOUT} from "../store/actions.types";
import qs from "querystring";
const OAUTH_CLIENT_HOST = process.env.VUE_APP_OAUTH_CLIENT_HOST;
const OAUTH_API_HOST = process.env.VUE_APP_OAUTH_API_HOST;
const CLIENT_ID = process.env.VUE_APP_CLIENT_ID;

export default {
  login() {
    const challenge = pkceChallenge();
    // https://medium.com/@dazcyril/generating-cryptographic-random-state-in-javascript-in-the-browser-c538b3daae50
    const validChars =
      "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    let array = new Uint8Array(40);
    window.crypto.getRandomValues(array);
    array = array.map((x) => validChars.charCodeAt(x % validChars.length));
    const randomState = String.fromCharCode.apply(null, array);
    const requestUrl =
      `${OAUTH_CLIENT_HOST}/oauth/authorize` +
      `?client_id=${CLIENT_ID}` +
      `&code_challenge=${challenge.code_challenge}` +
      `&state=${randomState}`;
    store.commit(SET_CODE_VERIFIER, { verifier: challenge.code_verifier });
    store.commit(SET_OAUTH_STATE, { state: randomState });
    window.location.href = requestUrl;
  },
  getAccessToken() {
    const params = new URLSearchParams(window.location.search);
    const authCode = params.get("code");
    const oauthState = params.get("state");
    if (oauthState !== store.state.oauthState) {
      console.log("Returned state:" + oauthState);
      console.log("Current state:" + store.state.oauthState);
      store.commit(SET_IS_LOADING, false);
      window.location = "/";
      throw "State did not match!";
    }
    const body = {
      grant_type: "authorization_code",
      code: authCode,
      redirect_uri: `${location.protocol}//${location.hostname}/authorizer/callback`,
      client_id: CLIENT_ID,
      code_verifier: store.state.codeVerifier,
    };
    return axios
      .post(`${OAUTH_API_HOST}/oauth/token`, qs.stringify(body))
      .then(({ data }) => {
        return {
          accessToken: data.access_token,
          refreshToken: data.refresh_token,
          accessTokenExpiresIn: data.expires_in,
          refreshTokenExpiresIn: data.refresh_token_expires_in,
        };
      });
  },
  refreshToken() {
    const now = new Date();
    if (!store.state.currentUser.data.refreshToken || now > store.state.currentUser.data.refreshToken) {
      return;
    }
    const body = {
      grant_type: "refresh_token",
      refresh_token: store.state.currentUser.data.refreshToken,
      client_id: CLIENT_ID,
    };
    return axios
      .post(`${OAUTH_API_HOST}/oauth/token`, qs.stringify(body))
      .then((res) => {
        const payload = {
          accessToken: res.data.access_token,
          refreshToken: res.data.refresh_token,
          accessTokenExpiresIn: res.data.expires_in,
          refreshTokenExpiresIn: res.data.refresh_token_expires_in,
        };
        store.commit(SET_USER_AUTH_DATA, payload);
      })
      .catch((err) => {
        store.dispatch(LOGOUT).then(() => {
          window.href = '/';
          window.location.reload();
        });
        throw err;
      });
  },
};
