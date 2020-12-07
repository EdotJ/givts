const OAuth2Server = require("oauth2-server");
const config = require("../config");
module.exports = (router, app, authenticator, clientService) => {
    function checkForSession(sess) {
        return !sess.user_id;
    }

    router.post("/register", authenticator.registerUser);
    router.post("/token", (req, res) => {
        if (req.body.grant_type !== 'refresh_token' && !req.body.code_verifier) {
            return res.status(400).json({
                error: "invalid_request",
                error_code: "code_verifier is missing",
            });
        }
        console.log(req.body);
        if (!req.body.client_secret) {
            req.body.client_secret = null;
        }
        app.oauth.token(new OAuth2Server.Request(req), new OAuth2Server.Response(res))
            .then((token) =>
                res
                    .set('Cache-Control', 'no-store')
                    .set('Pragma', 'no-cache')
                    .json({
                        access_token: token.accessToken,
                        expires_in: config.getConfig().accessTokenExpiration,
                        token_type: 'bearer',
                        refresh_token: token.refreshToken,
                        refresh_token_expires_in: config.getConfig().refreshTokenExpiration,
                    })
            )
            .catch(err => {
                console.log(err);
                return res.status(err.status || 500).json({
                    error: err.name,
                    error_description: err.message,
                });
            })
    });

    router.get("/authorize", (req, res) => {
        if (req.session.user_id) {
            return res.status(200).json({id: req.session.user_id});
        } else {
            return res.status(401).end();
        }
    });

    router.post("/authorize", (req, res) => {
        if (req.body.code_challenge_method && req.body.code_challenge_method !== 'S256') {
            return res.status(400).json({
                error: "invalid_request",
                error_code: "unsupported code challenge method",
            });
        }
        const options = {
            authenticateHandler: {
                handle: () => {
                    if (!req.session.user_id) {
                        if (!req.headers.authorization) {
                            return res.status(401).json({error: "No credentials sent!", error_code: "NO_AUTH_HEADER"});
                        }
                        return authenticator.login(req).then(res => {
                            if (!res) {
                                throw new OAuth2Server.UnauthorizedRequestError("Invalid credentials or user does not exist!");
                            }
                            return {id: res.userId};
                        });
                    } else {
                        return {
                            id: req.session.user_id
                        };
                    }
                }
            }
        };
        app.oauth.authorize(new OAuth2Server.Request(req), new OAuth2Server.Response(res), options)
            .then(success => {
                    return res.json({
                        code: success.authorizationCode,
                        expiresAt: success.expiresAt.format("YYYY-MM-DD HH:mm:ss"),
                        state: req.body.state ? req.body.state : null,
                    });
                }
            )
            .catch(err => {
                    return res.status(err.status || 500).json({
                        error: err.name,
                        error_description: err.message,
                        state: req.body.state ? req.body.state : null,
                    });
                }
            )
    });

    router.post('/login', (req, res) => {
        if (!req.body.username) {
            return res.status(401).json({error: "Please provide a username!", error_code: "NO_USERNAME"});
        }
        if (!req.body.password) {
            return res.status(401).json({error: "Please provide a password!", error_code: "NO_PASSWORD"});
        }
        return authenticator.login(req).then(resp => {
            if (!resp) {
                return res.status(400).json({
                    error: "Invalid credentials or user does not exist!",
                    error_code: "INVALID_CREDENTIALS"
                });
            }
            req.session.user_id = resp.userId;
            return res.status(200).json({
                id: resp.userId,
                username: resp.username,
            });
        }).catch(err => {
            console.log("Login request -> ", err);
            return res.status(500).json({error: "An error has occured!", error_code: "ERROR"});
        });
    });

    router.post('/logout', (req, res) => {
            req.session.destroy();
            return res.status(200).json({
                message: "Successfully logged out!",
            });
        }
    );

    router.post('/client', (req, res) => {
        if (checkForSession(req.session)) {
            return res.status(401).end();
        }
        const re = new RegExp('https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)');
        if (!re.test(req.body.redirectUri) || !req.body.redirectUri) {
            return res.status(400).json({
                error: "Invalid URL",
                error_code: "INVALID_URL",
            });
        }
        const client = {
            redirectUri: req.body.redirectUri,
            grants: req.body.grants,
        }
        clientService.save(req.body.name, req.body.description, client, {id: req.session.user_id}).then(resp => {
            if (resp) {
                return res.status(200).json(resp);
            } else {
                return res.status(500).json({
                    error: "Failed adding new client!",
                    error_code: "FAILED_SAVE_CLIENT",
                })
            }
        }).catch(err => {
            console.log(`Error when adding new client ${req.data.name} `, err);
        });

    });

    router.get('/client', (req, res) => {
        if (checkForSession(req.session)) {
            return res.status(401).end();
        }
        clientService.getAll(req.session.user_id).then(resp => {
            return res.status(200).json(resp);
        }).catch(err => {
            console.log(err);
        });
    });

    router.get('/client/:id', (req, res) => {
        if (checkForSession(req.session)) {
            return res.status(401).end();
        }
        clientService.getSingle({clientId: req.params.id}).then(resp => {
            if (!resp.error) {
                if (resp.client.userId === req.session.user_id) {
                    return res.status(200).json(resp);
                } else {
                    return res.status(200).json({
                        client: {
                            name: resp.client.name,
                            redirectUri: resp.client.redirectUri,
                        }
                    });
                }
            } else {
                return res.status(404).json(resp);
            }
        }).catch(err => {
            console.log(`Fetching client ${req.params.id} failed`, err);
            return res.status(500).json(err);
        })
    });

    router.delete('/client/:id', (req, res) => {
        if (checkForSession(req.session)) {
            return res.status(401).end();
        }
        clientService.deleteClient({
            clientId: req.params.id,
            userId: req.session.user_id,
        }).then(() => {
            return res.status(200).end();
        }).catch(err => {
            return res.status(500).json(err);
        })
    });

    router.post('/authenticate', (req, res) => {
        return app.oauth.authenticate(new OAuth2Server.Request(req), new OAuth2Server.Response(res))
            .then(resp => {
                return res.status(302).end();
            }).catch(err => {
                return res.status(err.status || 500).json({
                    error: err.name,
                    error_description: err.message,
                });
            });
    });

    return router;
};