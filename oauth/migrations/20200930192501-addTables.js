'use strict';

var dbm;
var type;
var seed;

/**
 * We receive the dbmigrate dependency from dbmigrate initially.
 * This enables us to not have to rely on NODE_PATH.
 */
exports.setup = function (options, seedLink) {
    dbm = options.dbmigrate;
    type = dbm.dataType;
    seed = seedLink;
};

exports.up = function (db, callback) {
    db.createTable('users', {
        columns: {
            id: {type: 'bigint', primaryKey: true, autoIncrement: true},
            username: {type: 'string', unique: true, notNull: true},
            user_password: {type: 'string', notNull: true},
        },
        ifNotExists: true,
    }, callback);
    db.createTable('access_tokens', {
        columns: {
            id: {type: 'int', primaryKey: true, autoIncrement: true},
            access_token: {type: 'string', notNull: true},
            user_id: {type: 'bigint', notNull: true, foreignKey: {
                    name: 'fk_access_tokens_users',
                    table: 'users',
                    rules: {
                        onDelete: 'CASCADE'
                    },
                    mapping: 'id'
                }}
        },
        ifNotExists: true,
    });
    // db.addForeignKey('access_tokens', 'users', 'fk_user_access_token',
    //     {
    //         'user_id': 'id',
    //     },
    //     {
    //         onDelete: 'CASCADE'
    //     });
};

exports.down = function (db, callback) {
    db.dropTable('access_tokens', {
        ifExists: true,
    }, callback)
    db.dropTable('users', {
        ifExists: true,
    }, callback)
};

exports._meta = {
    "version": 1
};
