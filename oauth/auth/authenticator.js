var userDb;

module.exports = (injectedUserDb) => {
    userDb = injectedUserDb;

    return {
        registerUser: registerUser,
        login: login,
    };
};

function registerUser(req, res) {
    userDb.isValidUser(req.body.username, (error, isValidUser) => {
        if (error || !isValidUser) {
            const message = error
                ? "Something is wrong!"
                : "This user already exists";
            sendResponse(res, message, error);
            return;
        }
        userDb.register(req.body.username, req.body.password, (response) => {
            const isSuccess = response.error !== undefined && response.error !== null;
            if (isSuccess) {
                console.log(response.error);
            }
            sendResponse(
                res,
                isSuccess ? "Success!" : "Something is wrong!",
                response.error
            );
        });
    });
}

function login(query, res) {

}

function sendResponse(res, message, error) {
    res.status(error !== undefined ? 400 : 200).json({
        message: message,
        error: error,
    });
}