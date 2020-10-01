var mariadbPool;

module.exports = (injectedMariadbPool) => {
    mariadbPool = injectedMariadbPool;

    return {
        saveAccessToken: saveAccessToken,
        getUserIDFromBearerToken: getUserIdFromBearerToken,
    };
}

function saveAccessToken(accessToken, userID, callbackFunc) {
    const insertAccessToken = `INSERT INTO access_tokens (access_token, user_id) VALUES ('${accessToken}', ${userID});`

    mariadbPool.query(insertAccessToken, (response) => {
        callbackFunc(response.error);
    });
}

function getUserIdFromBearerToken(bearerToken, callbackFunc) {
    const getUserIdQuery = `SELECT * FROM access_tokens WHERE access_token = '${bearerToken}';`;

    mariadbPool.query(getUserIdQuery, (response) => {
        const userID =
            response.results && response.results.length === 1
                ? response.results.rows[0].user_id
                : null;

        callbackFunc(userID);
    })
}
