let mariadbPool;

module.exports = (injectedMariadbPool) => {
    mariadbPool = injectedMariadbPool;

    return {
        register: register,
        getUser: getUser,
        isValidUser: isValidUser,
    };
}

const crypto = require("crypto");

function register(username, password, callbackFunc) {
    const shaPass = crypto.createHash("sha256").update(password).digest("hex");
    const query = `INSERT INTO users (username, user_password) VALUES ('${username}', '${shaPass}')`;

    mariadbPool.query(query, callbackFunc);
}

function getUser(username, password, callbackFunc) {
    var shaPass = crypto.createHash("sha256").update(password).digest("hex");
    const getUserQuery = `SELECT * FROM users WHERE username = '${username}' AND user_password = '${shaPass}'`;
    console.log(getUserQuery);

    // TODO: is this correct?
    mariadbPool.query(getUserQuery, (response) => {
        callbackFunc(
            false,
            response.results[0]
                ? response.results[0]
                : null
        );
    })
}

function isValidUser(username, callbackFunc) {
    const query = `SELECT * FROM users WHERE username = '${username}'`;

    const checkUserCallbackFunction = (response) => {
        const isValidUser = response.results
            ? !(response.results.length > 0)
            : null;

        callbackFunc(response.error, isValidUser);
    }

    mariadbPool.query(query, checkUserCallbackFunction);
}