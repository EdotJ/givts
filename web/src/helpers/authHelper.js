import authService from "../services/authService";
import userService from "../services/userService";
import store from "../store";
import {SET_IS_LOADING} from "@/store/mutations.types";

export default () => {
        authService.getAccessToken().then(() => {
            userService.getUserInfo().then(() => {
                window.location.replace('/');
            });
        }).catch(err => {
            console.log(err);
            store.commit(SET_IS_LOADING, false);
            console.log(store.state.isLoading);
            window.location.replace('/');
        });
}