let mariadbPool;

module.exports = (injectedMariadbPool) => {
    mariadbPool = injectedMariadbPool;

    return {
        saveClient: saveClient,
        getClient: getClient,
        getAllClients: getAllClients,
        deleteClient: deleteClient,
    };
}
const crypto = require('crypto');
const cryptoRandomString = require('crypto-random-string');

function saveClient(name, description, client, user) {
    const saveClientQuery = `INSERT INTO clients (name, description, client_id, client_secret, redirect_uri,
                                                  grant_types, user_id)
                             VALUES (?, ?, ?, ?, ?, ?, ?)`;
    const clientId = crypto.createHmac('sha256', name + Date.now().toString())
        .digest('base64')
        .replace('/', '-')
        .replace('+', 'xd');
    console.log("Created client:", clientId);
    const clientSecret = cryptoRandomString({length: 64, type: 'hex'});
    return mariadbPool
        .safeQuery(saveClientQuery, name, description, clientId, clientSecret, client.redirectUri, client.grants, user.id)
        .then(() => {
            return {
                ...client,
                client_id: clientId,
                client_secret: clientSecret,
            }
        })
        .catch(err => {
            console.log("saveClient -> ", err);
            return false;
        });
}

function getClient(client) {
    const getClientQuery = `SELECT id, client_id, client_secret, redirect_uri, grant_types, name, description, user_id
                            FROM clients
                            WHERE client_id = ?
                              ${client.clientSecret ? "AND client_secret = ?" : ""}`;
    return mariadbPool
        .safeQuery(getClientQuery, client.clientId, client.clientSecret)
        .then(res => {
            if (res.results === undefined || res.results === null || res.results.length !== 1) {
                return false;
            }
            return {
                client: {
                    id: res.results[0].id,
                    clientId: res.results[0].client_id,
                    redirectUri: res.results[0].redirect_uri,
                    grants: res.results[0].grant_types,
                    name: res.results[0].name,
                    description: res.results[0].description,
                    clientSecret: res.results[0].client_secret,
                    userId: res.results[0].user_id,
                },
                error: null
            };
        })
        .catch(err => {
            console.log("getClientFromDb -> ", err);
            return {
                client: null,
                error: err
            };
        });
}

function getAllClients(userId) {
    const getClientsQuery = `SELECT id, client_id, client_secret, redirect_uri, grant_types, name, description
                             FROM clients
                             WHERE user_id = ?`;
    return mariadbPool
        .safeQuery(getClientsQuery, userId)
        .then(res => {
            if (res.results === undefined || res.results === null) {
                return false;
            }
            return {
                clients: res.results.map(r => r),
            }
        })
        .catch(err => {
            console.log("getClientsFromDb -> ", err);
            return {
                clients: null,
                error: err,
            }
        })
}

function deleteClient(clientId, userId) {
    const deleteClientQuery = `DELETE FROM clients WHERE client_id = ? AND user_id = ?`;

    return mariadbPool.safeQuery(deleteClientQuery, clientId, userId).then(res => {
        return true;
    }).catch(err => {
        return false;
        console.log("Delete client error ->", err);
    })
}