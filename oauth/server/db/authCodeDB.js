let mariadbPool;
let formatter;
module.exports = (injectedMariadbPool, injectedFormatter) => {
    mariadbPool = injectedMariadbPool;
    formatter = injectedFormatter;

    return {
        saveAuthCode: saveAuthCode,
        getAuthCode: getAuthCode,
        revokeAuthCode: revokeAuthCode,
    };
}

function saveAuthCode(authCode, client, user) {
    const saveAuthCodeQuery =
            `INSERT INTO authorization_codes (code, expiration_date, redirect_uri, client_id, user_id, code_challenge)
             VALUES (?, ?, ?, ?, ?, ?)`;
    console.log(authCode);
    return mariadbPool.safeQuery(saveAuthCodeQuery, authCode.authorizationCode, authCode.expiresAt,
        authCode.redirectUri, client.db_id, user.id, authCode.codeChallenge)
        .then(res => {
            if (res.error) {
                console.log("saveAuthCode ->", res.error);
            }
            const getSavedAuthCode = `SELECT ac.code, ac.expiration_date, ac.redirect_uri, c.client_id, ac.user_id
                                      FROM authorization_codes ac 
                                      INNER JOIN clients c on ac.client_id = c.id
                                      WHERE ac.id = ${res.results.insertId}`
            return mariadbPool.query(getSavedAuthCode).then(res => {
                return {
                    authorizationCode: res.results[0].code,
                    expiresAt: formatter(res.results[0].expiration_date),
                    redirectUri: res.results[0].redirectUri,
                    client: {
                        id: res.results[0].client_id,
                    },
                    user: {
                        id: res.results[0].user_id,
                    }
                }
            })
        })
        .catch(err => {
            console.log("saveAuthCode -> ", err);
        });
}

function getAuthCode(authCode) {
    const getAuthCodeQuery =
            `SELECT ac.code,
                    ac.expiration_date,
                    ac.redirect_uri,
                    ac.user_id,
                    ac.code_challenge,
                    c.client_id,
                    c.client_secret,
                    u.username
             FROM authorization_codes ac
                      INNER JOIN clients c ON ac.client_id = c.id
                      INNER JOIN users u ON ac.user_id = u.id
             WHERE code = ?`;
    return mariadbPool.safeQuery(getAuthCodeQuery, authCode)
        .then(res => {
            if (res.results === undefined || res.results === null || res.results.length !== 1) {
                return false;
            }
            return {
                code: res.results[0].code,
                expiresAt: new Date(res.results[0].expiration_date),
                redirectUri: res.results[0].redirect_uri,
                client: {
                    id: res.results[0].client_id,
                },
                user: {
                    id: res.results[0].user_id,
                    username: res.results[0].username,
                },
                codeChallenge: res.results[0].code_challenge,
                codeChallengeMethod: 'S256'
            }
        })
}

function revokeAuthCode(authCode) {
    const deleteAuthCodeQuery = `DELETE
                                 FROM authorization_codes
                                 WHERE code = ?`;

    return mariadbPool.safeQuery(deleteAuthCodeQuery, authCode.code)
        .then(() => true)
        .catch(err => {
            console.log("revokeAuthCode ->", err);
            return false;
        });
}