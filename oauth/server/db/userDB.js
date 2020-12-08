let mariadbPool;

module.exports = (injectedMariadbPool) => {
    mariadbPool = injectedMariadbPool;

    return {
        register: register,
        getUser: getUser,
        isValidUser: isValidUser,
        getUserByToken: getUserByToken,
    };
}

const crypto = require("crypto");

function register(username, email, password) {
    const shaPass = crypto.createHash("sha256").update(password).digest("hex");
    const query = `INSERT INTO users (username, email, user_password)
                   VALUES (?, ?, ?)`;

    return mariadbPool.safeQuery(query, username, email, shaPass)
        .then((res) => {
            return res;
        })
        .catch((err) => {
            console.log("register ->", err)
            return {
                error: err,
            }
        });
}

function getUser(username, password) {
    var shaPass = crypto.createHash("sha256").update(password).digest("hex");
    const getUserQuery = `SELECT * FROM users WHERE username = '${username}' AND user_password = '${shaPass}'`;

    return mariadbPool.query(getUserQuery)
        .then((res) => {
            return res && res.results && res.results.length === 1
                ? res.results[0]
                : null;
        })
        .catch((err) => {
            console.log("getUser -> ", err);
            return false;
        });
}

function isValidUser(username, email) {
    const query = `SELECT *
                   FROM users
                   WHERE username = ?
                      OR email = ?`;

    return mariadbPool.safeQuery(query, username, email)
        .then(res => {
            return res.results
                ? !(res.results.length > 0)
                : null;
        })
        .catch(err => {
            console.log("isValidUser -> ", err);
            return {isValidUser: false, error: err};
        });
}

function getUserByToken(token) {
    const query = `SELECT t.user_id, u.username, u.email
                   FROM access_tokens t
                            INNER JOIN users u ON t.user_id = u.id
                   WHERE access_token = ?`

    return mariadbPool.safeQuery(query, token)
        .then(res => {
            return res.results && res.results.length > 0 ? {
                user_id: res.results[0].user_id,
                username: res.results[0].username,
                email: res.results[0].email,
            } : null;
        })
        .catch(err => {
            console.log("getUserByToken -> ", err);
            return {
                error: err
            };
        });
}