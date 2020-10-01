
const OAuth2Server = require("oauth2-server");

module.exports = (router, app, authenticator) => {
    router.post("/register", authenticator.registerUser);
    router.post("/login", (req, res) => {
        app.oauth.token(new OAuth2Server.Request(req), new OAuth2Server.Response(res))
    }, authenticator.login);

    return router;
};