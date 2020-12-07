import authService from "../services/authService";
import userService from "../services/userService";

export default () => {
    try {
        authService.getAccessToken().then(() => {
            userService.getUserInfo().then(() => {
                window.location.replace('/');
            });
        });
    } catch (err) {
        console.log(err);
        window.location.replace('/');
    }
}