'use strict';

var dbm;
var type;
var seed;

exports.setup = function(options, seedLink) {
  dbm = options.dbmigrate;
  type = dbm.dataType;
  seed = seedLink;
};

exports.up = function(db, callback) {
  db.createTable('authorization_codes', {
    columns: {
      id: {type: 'bigint', primaryKey: 'true', autoIncrement: 'true'},
      code: {type: 'string', notNull: 'true'},
      expiration_date: {type: 'timestamp', notNull: 'true'},
      redirect_uri: {type: 'string', size: '2000', notNull: 'true'},
      client_id: {
        type: 'bigint', notNull: true, foreignKey: {
          name: 'fk_authCode_clients',
          table: 'clients',
          rules: {
            onDelete: 'CASCADE'
          },
          mapping: 'id'
        }
      },
      user_id: {
        type: 'bigint', notNull: true, foreignKey: {
          name: 'fk_authCode_users',
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
  db.dropTable('authorization_codes', {
    ifExists: true
  }, callback);
};

exports._meta = {
  "version": 1
};
