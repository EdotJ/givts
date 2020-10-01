var userDb;
var tokenDb;

module.exports = (injectedUserDb, injectedTokenDb) => {
    userDb = injectedUserDb;
    tokenDb = injectedTokenDb;

    return {
        getClient: getClient,
        saveToken: saveToken,
        getUser: getUser,
        grantTypeAllowed: grantTypeAllowed,
        getAccessToken: getAccessToken,
    };
};

function getClient(clientID, clientSecret, callbackFunc) {
    const client = {
        clientID,
        clientSecret,
        grants: ["password"],
        redirectUris: null
    };

    callbackFunc(false, client);
}

function grantTypeAllowed(clientID, grantType, callbackFunc) {

    // TODO: what the fuck?
    callbackFunc(false, true);
}

function getUser(username, password, callbackFunc) {
    userDb.getUser(username, password, callbackFunc);
}

function getAccessToken(bearerToken, callbackFunction) {
    tokenDb.getUserIDFromBearerToken(bearerToken, (userId) => {
        const accessToken = {
            user: {
                id: userId,
            },
            expires: null,
        };

        callbackFunction(userId === null, userId === null ? null : accessToken);
    })
}

function saveToken(token, client, user, callbackFunction) {
    // TODO: add expire time and client
    tokenDb.saveAccessToken(token.accessToken, user.id, callbackFunction);
}