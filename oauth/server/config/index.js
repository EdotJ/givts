module.exports = {
    getConfig: () => {
        switch (process.env.NODE_ENV) {
            case 'development':
                return {
                    databaseHost: 'database-oauth',
                    databasePort: '3306',
                    databaseUser: 'oauthuser',
                    databasePassword: 'dummypassword',
                    database: 'oauth',
                    accessTokenExpiration: 4 * 60 * 60,
                    authCodeExpiration: 5 * 60,
                    refreshTokenExpiration: 14 * 24 * 60 * 60,
                    serverPort: 3000,
                    secret: "SomeSuperScarySecretOverHere",
                    clientUrl: 'http://localhost:81'
                }
            default: // assume production
                return {}
        }
    },
};