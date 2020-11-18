let mariadbPool;
let formatter;

module.exports = (injectedMariadbPool, injectedFormatter) => {
    mariadbPool = injectedMariadbPool;
    formatter = injectedFormatter;

    return {
        saveToken: saveToken,
        revokeToken: revokeToken,
        getAccessToken: getAccessToken,
        getRefreshToken: getRefreshToken,
        introspectToken: introspectToken,
    };
}

function saveToken(token, client, user) {
    const insertAccessToken = `INSERT INTO access_tokens (access_token, expires, user_id, client_id)
                               VALUES (?, ?, ?, ?)`;
    const insertRefreshToken = `INSERT INTO refresh_tokens (token, expiration_date, user_id, client_id)
                                VALUES (?, ?, ?, ?)`;
    let queries = [
        mariadbPool.safeQuery(insertAccessToken,
            token.accessToken,
            formatter(token.accessTokenExpiresAt).format("YYYY-MM-DD HH:mm:ss"),
            user.id,
            client.db_id),
        mariadbPool.safeQuery(insertRefreshToken,
            token.refreshToken,
            formatter(token.refreshTokenExpiresAt).format("YYYY-MM-DD HH:mm:ss"),
            user.id,
            client.db_id),
    ];
    return Promise.all(queries)
        .then((response) => {
            const getAccessToken = `
        SELECT at.access_token,
               at.user_id,
               c.client_id,
               r.token as refresh_token,
               r.expiration_date
        FROM access_tokens at 
            INNER JOIN clients c on at.client_id = c.id 
            INNER JOIN refresh_tokens r on at.client_id = r.client_id
        WHERE at.id = ${response[0].results.insertId}`;

            return mariadbPool.query(getAccessToken).then(res => {
                return {
                    accessToken: res.results[0].access_token,
                    accessTokenExpiresAt: new Date(res.results[0].expires),
                    refreshToken: res.results[0].refresh_token,
                    refreshTokenExpiresAt: new Date(res.results[0].expiration_date),
                    tokenType: 'bearer',
                    client: {
                        id: res.results[0].client_id,
                    },
                    user: {
                        id: res.results[0].user_id,
                    }
                };
            });
        });
}

function revokeToken(token) {
    const revokeTokenQuery = `DELETE
                              FROM refresh_tokens
                              WHERE token = ?`;

    return mariadbPool.safeQuery(revokeTokenQuery, token.refreshToken)
        .then(() => true)
        .catch(err => {
            console.log("revokeToken ->", err);
            return false;
        })
}

function getAccessToken(accessToken) {
    const getAccessToken = `SELECT ac.access_token, ac.user_id, ac.expires, c.client_id
                            FROM access_tokens ac
                                     INNER JOIN clients c on ac.client_id = c.id
                            WHERE access_token = ?`;
    return mariadbPool.safeQuery(getAccessToken, accessToken)
        .then(res => {
            if (!res.results || res.results.length !== 1) {
                return null;
            }
            return {
                accessToken: res.results[0].access_token,
                accessTokenExpiresAt: new Date(res.results[0].expires),
                client: {id: res.results[0].client_id},
                user: {id: res.results[0].user_id}
            };
        })
        .catch(err => {
            console.log("getAccessTokenFromDb -> ", err);
            return null;
        });
}

function getRefreshToken(refreshToken) {
    const getRefreshTokenQuery = `SELECT rt.token, rt.expiration_date, c.client_id, rt.user_id
                                  FROM refresh_tokens rt
                                           INNER JOIN clients c on rt.client_id = c.id
                                  WHERE rt.token = ?`;
    return mariadbPool.safeQuery(getRefreshTokenQuery, refreshToken)
        .then(res => {
            if (res.results === null || res.results === undefined || res.results.length !== 1) {
                return false;
            }
            return {
                refreshToken: res.results[0].token,
                refreshTokenExpiresAt: new Date(res.results[0].expiration_date),
                client: {
                    id: res.results[0].client_id,
                },
                user: {
                    id: res.results[0].user_id,
                }
            }
        });
}

function introspectToken(token) {
    return getAccessToken(token);
}