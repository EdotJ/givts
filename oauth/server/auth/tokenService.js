let userDb;
let tokenDb;
let authCodeDb;
let clientDb;

module.exports = (injectedUserDb, injectedTokenDb, injectedAuthCodeDb, injectedClientDb) => {
    userDb = injectedUserDb;
    tokenDb = injectedTokenDb;
    authCodeDb = injectedAuthCodeDb;
    clientDb = injectedClientDb;

    return {
        getClient: getClient,
        saveToken: saveToken,
        getUser: getUser,
        getUserByToken: getUserByToken,
        revokeAuthorizationCode: revokeAuthorizationCode,
        getAuthorizationCode: getAuthorizationCode,
        saveAuthorizationCode: saveAuthorizationCode,
        getAccessToken: getAccessToken,
        revokeToken: revokeToken,
        getRefreshToken: getRefreshToken,
        introspectToken: introspectToken,
    };
};

function getClient(clientID, clientSecret) {
    return clientDb.getClient({clientId: clientID, clientSecret: clientSecret})
        .then(res => {
            if (!res.client) {
                return false;
            }
            return {
                id: res.client.clientId,
                db_id: res.client.id,
                grants: res.client.grants.split(","),
                redirectUris: [res.client.redirectUri],
            };
        });
}

function getUser(username, password) {
    return userDb.getUser(username, password);
}

function getUserByToken(token) {
    return userDb.getUserByToken(token);
}

function getAccessToken(accessToken) {
    return tokenDb.getAccessToken(accessToken).then(res => {
        console.log("getAccessToken -> ", res);
        return res;
    });

}

function saveToken(token, client, user) {
    return tokenDb.saveToken(token, client, user);
}

function revokeToken(token) {
    return tokenDb.revokeToken(token);
}

function saveAuthorizationCode(code, client, user) {
    return authCodeDb.saveAuthCode(code, client, user);
}

function revokeAuthorizationCode(code) {
    return authCodeDb.revokeAuthCode(code);
}

function getAuthorizationCode(authorizationCode) {
    return authCodeDb.getAuthCode(authorizationCode);
}

function getRefreshToken(token) {
    return tokenDb.getRefreshToken(token);
}

function introspectToken(token) {
    return tokenDb.introspectToken(token);
}