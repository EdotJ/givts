const config = require("../config").getConfig();

module.exports = {
        query: query,
        safeQuery: safeQuery,
}

const mariadb = require('mariadb');
const pool = mariadb.createPool({
    host: config.databaseHost,
    port: config.databasePort,
    user: config.databaseUser,
    password: config.databasePassword,
    database: config.database,
    connectionLimit: 5,
    dateStrings: true,
});

function query(queryString) {
    return pool.query(queryString)
        .then(res => {
            return setResponse(null, res);
        })
        .catch(err => {
            return setResponse(err, null);
        });
}

function safeQuery(queryString, ...args) {
    return pool.query(queryString, args)
        .then(res => {
            return setResponse(null, res);
        })
        .catch(err => {
            return setResponse(err, null);
        });

}

function setResponse(error, results) {
    return {
        error: error,
        results: results ? results : null,
    };
}