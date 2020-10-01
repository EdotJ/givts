const mariadbPool = require("./db/mariadbWrapper");
const tokenDb = require("./db/tokenDB")(mariadbPool);
const userDb = require("./db/userDB")(mariadbPool);

const oauthService = require("./auth/tokenService")(userDb, tokenDb);
const OAuth2Server = require("oauth2-server");

const express = require("express");
const app = express();

// TODO: change model
app.oauth = new OAuth2Server({
    model: oauthService,
    grants: ["password"]
});

const authenticator = require("./auth/authenticator")(userDb);
const routes = require("./auth/routes")(
    express.Router(),
    app,
    authenticator
);
const bodyParser = require("body-parser");

app.use(bodyParser.urlencoded({extended: false}));
app.use("/auth", routes);

// TODO: change the port to be dynamic
const port = 3000;

app.listen(port, () => {
    console.log(`Listening on port ${port}`)
})