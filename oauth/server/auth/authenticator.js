let userDb;

module.exports = (injectedUserDb) => {
    userDb = injectedUserDb;

    return {
        registerUser: registerUser,
        login: login,
    };
};

function checkRegistrationErrors(username, email) {
    let error = null;
    const emailRegex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    if (!username) {
        error = {
            message: "Please provide a username!",
            errorCode: "NO_USERNAME",
        }
    } else if (!email) {
        error = {
            message: "Please provide an email!",
            errorCode: "NO_EMAIL",
        }
    } else if (!emailRegex.test(String(email).toLowerCase())) {
        error = {
            message: "Please provide a valid email!",
            errorCode: "INVALID_EMAIL",
        }
    }
    return error;
}

function registerUser(req, res) {
    const error = checkRegistrationErrors(req.body.username, req.body.email);
    if (error) {
        sendResponse(res, error.message, error.errorCode);
    } else {
        return userDb.isValidUser(req.body.username, req.body.email)
            .then((isValidUser, error) => {
                if (!isValidUser || error) {
                    const message = error
                        ? "Something is wrong!"
                        : "This user already exists!";
                    if (message) {
                        console.log("registerUser#isValid -> ", message);
                    }
                    if (!error) {
                        error = "USER_EXISTS";
                    }
                    return sendResponse(res, message, error);
                }
                return userDb.register(req.body.username, req.body.email, req.body.password)
                    .then(response => {
                        const isSuccess = response.error === undefined || response.error === null;
                        if (!isSuccess) {
                            console.log("registerUser#register -> ", response.error);
                        }
                        return sendResponse(
                            res,
                            isSuccess ? "Success!" : "Something is wrong!",
                            response.error
                        );
                    });
            });
    }
}

function login(req) {
    const authToken = req.headers.authorization;
    let username;
    let password;
    if (!authToken) {
        username = req.body.username;
        password = req.body.password;
    } else {
        const combo = Buffer.from(authToken.split(" ")[1], 'base64').toString('ascii');
        username = combo.split(":")[0];
        password = combo.split(":")[1];
    }

    return userDb.getUser(username, password)
        .then(res => {
            if (!res) {
                return false;
            }
            return {
                userId: res.id,
                username: res.username,
            }
        });
}

function sendResponse(res, message, error) {
    res.status(error === undefined || error === null ? 200 : 400).json({
        message: message,
        error: error,
    });
}