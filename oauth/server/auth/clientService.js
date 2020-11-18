let clientDb;

module.exports = (injectedClientDb) => {
    clientDb = injectedClientDb;

    return {
        save: saveClient,
        getSingle: getSingle,
        getAll: getAll,
        deleteClient: deleteClient,
    };
};

const saveClient = (name, description, client, user) => {
    return clientDb.saveClient(name, description, client, user).then(res => {
        if (res) {
            return {
                message: "Successfully saved client",
                res,
            }
        } else {
            return {
                error: "Failed saving client!",
                error_code: "FAILED_SAVE_CLIENT",
            }
        }
    });
}

const getSingle = (client) => {
    return clientDb.getClient(client).then(
        res => {
            if (res) {
                return res;
            } else {
                return {
                    error: "Couldn't find client!",
                    error_code: "NOT_FOUND",
                }
            }
        }
    );
}

const getAll = (userId) => {
    return clientDb.getAllClients(userId).then(
        res => {
            if (res) {
                return res;
            } else {
                return {
                    error: "Couldn't find clients!",
                    error_code: "NOT_FOUND",
                }
            }
        }
    );
}

const deleteClient = (data) => {
    return clientDb.deleteClient(data.clientId, data.userId).then(res => {
            if (res) {
                return res;
            } else {
                return {
                    error: `Failed deleting client ${data.clientId}`,
                    error_code: "DELETE_FAILED",
                }
            }
        }
    );
}