import {reactive} from 'vue';

export default {
    debug: true,
    state: reactive({
        clientId: '',
        clientState: '',
        redirectUri: '',
        isAuthorizingClient: false,
        username: '',
        codeChallenge: '',
        codeChallengeMethod: '',
    }),
    setClientIdAction(newClientId) {
        if (this.debug) {
            console.log("setClientId triggered with", newClientId);
        }
        this.state.clientId = newClientId;
    },
    setClientStateAction(newState) {
        if (this.debug) {
            console.log("setClientState triggered with", newState);
        }
        this.state.clientState = newState;
    },
    setRedirectUriAction(newUri) {
        if (this.debug) {
            console.log("setRedirectUri triggered with", newUri);
        }
        this.state.redirectUri = newUri;
    },
    setAuthorizationAction(newState) {
        if (this.debug) {
            console.log("setAuthorizationAction triggered with", newState);
        }
        this.state.isAuthorizingClient = newState;
    },
    setUsernameAction(username) {
        if (this.debug) {
            console.log("New username is being set ", username);
        }
        this.state.username = username;
    },
    setCodeChallengeAction(newState) {
        if (this.debug) {
            console.log("setCodeChallengeAction triggered with", newState);
        }
        this.state.codeChallenge = newState;
    },
    setCodeChallengeMethodAction(newState) {
        if (this.debug) {
            console.log("setCodeChallengeMethodAction triggered with", newState);
        }
        this.state.codeChallengeMethod = newState;
    }
}