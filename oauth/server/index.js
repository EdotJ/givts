const dayjs = require("dayjs");
const utc = require('dayjs/plugin/utc')
const timezone = require("dayjs/plugin/timezone");
dayjs.extend(utc);
dayjs.extend(timezone)
dayjs.tz.setDefault("Europe/Vilnius")

const configuration = require("./config");

const mariadbPool = require("./db/mariadbWrapper");
const tokenDb = require("./db/tokenDB")(mariadbPool, dayjs);
const userDb = require("./db/userDB")(mariadbPool);
const clientDb = require("./db/clientDB")(mariadbPool);
const authCodeDb = require("./db/authCodeDB")(mariadbPool, dayjs);

const oauthService = require("./auth/tokenService")(userDb, tokenDb, authCodeDb, clientDb);
const OAuth2Server = require("oauth2-server");

const express = require("express");
const session = require("express-session");
const cookieParser = require("cookie-parser")
const app = express();
const cors = require("cors");
const corsOptions = {
    origin: configuration.getConfig().clientUrl,
    optionsSuccessStatus: 200,
    credentials: true,
}

app.use(cors(corsOptions));
app.oauth = new OAuth2Server({
    model: oauthService,
    grants: ["authorization_code", "refresh_token"],
    accessTokenLifetime: configuration.getConfig().accessTokenExpiration,
    authorizationCodeLifetime: configuration.getConfig().authCodeExpiration,
    refreshTokenLifetime: configuration.getConfig().refreshTokenExpiration,
    allowEmptyState: true,
});

const authenticator = require("./auth/authenticator")(userDb);
const clientService = require("./auth/clientService")(clientDb);
const routes = require("./auth/routes")(
    express.Router(),
    app,
    authenticator,
    clientService
);

const bodyParser = require("body-parser");

app.use(bodyParser.urlencoded({extended: false}));
app.use(bodyParser.json());
app.use(cookieParser());
app.use(session({
    secret: configuration.getConfig().secret,
    resave: true,
    saveUninitialized: false,
}));

app.use("/oauth", routes);
app.post('/oauth/tokeninfo', (req, res) => {
    if (!req.body.token) {
        return res.status(400).json({
            error: "invalid_request",
            error_description: "token is missing",
        });
    }
   oauthService.introspectToken(req.body.token).then(resp => {
       return res.status(200).json({
           active: resp.accessTokenExpiresAt > new Date(),
           client_id: resp.client.id,
           user_id: resp.user.id,
           exp: resp.accessTokenExpiresAt.getTime() / 1000,
       });
   }).catch(() => {
       return res.status(200).json({
           active: false,
       });
   });
});
app.post('/oauth/userinfo', (req, res) => {
    if (!req.body.token) {
        return res.status(400).json({
            error: "token is missing",
        });
    }
    oauthService.getUserByToken(req.body.token).then(response => {
        if (response == null) {
            return res.status(404).end();
        }
        return res.status(200).json({
            username: response.username,
            email: response.email,
            oauth_id: response.user_id
        });
    })
});

const port = configuration.getConfig().serverPort;

app.listen(port, () => {
    console.log(`Listening on port ${port}`)
    console.log(`Running in environment ${process.env.NODE_ENV}`)
})