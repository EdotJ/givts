'use strict';

var dbm;
var type;
var seed;

/**
  * We receive the dbmigrate dependency from dbmigrate initially.
  * This enables us to not have to rely on NODE_PATH.
  */
exports.setup = function(options, seedLink) {
  dbm = options.dbmigrate;
  type = dbm.dataType;
  seed = seedLink;
};

exports.up = function(db, callback) {
  db.createTable('refresh_tokens', {
    columns: {
      id: {type: 'bigint', primaryKey: 'true', autoIncrement: 'true'},
      token: {type: 'string', notNull: 'true'},
      expiration_date: {type: 'timestamp', notNull: 'true'},
      client_id: {
        type: 'bigint', notNull: true, foreignKey: {
          name: 'fk_refresh_tokens_clients',
          table: 'clients',
          rules: {
            onDelete: 'CASCADE'
          },
          mapping: 'id'
        }
      },
      user_id: {
        type: 'bigint', notNull: true, foreignKey: {
          name: 'fk_refresh_tokens_users',
          table: 'users',
          rules: {
            onDelete: 'CASCADE'
          },
          mapping: 'id'
        }
      }
    },
    ifNotExists: true
  }, callback);
};

exports.down = function(db, callback) {
  db.dropTable('refresh_tokens', {
    ifExists: true
  }, callback);
};

exports._meta = {
  "version": 1
};
