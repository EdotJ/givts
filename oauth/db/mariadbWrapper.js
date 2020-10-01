module.exports = {
    query: query,
}

const mariadb = require('mariadb');
const pool = mariadb.createPool({
    host: 'localhost', // TODO: refactor this to be a docker env variable
    port: '3307',
    user: 'oauthuser',
    password: 'dummypassword',
    database: 'oauth',
    connectionLimit: 5
});

function query(queryString, callbackFunction) {
    pool.getConnection()
        .then(conn => {
            conn.query(queryString)
                .then(res => {
                    callbackFunction(setResponse(null, res));
                })
                .catch(err => {
                    callbackFunction(setResponse(err, null));
                });
        });
}

function setResponse(error, results) {
    return {
        error: error,
        results: results ? results : null,
    };
}