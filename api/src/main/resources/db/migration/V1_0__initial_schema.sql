CREATE TABLE IF NOT EXISTS `users` (
     `id` BIGINT NOT NULL,
     `name` VARCHAR(50) NOT NULL,
     `email` VARCHAR(50) NOT NULL UNIQUE,
     `created_date` TIMESTAMP NOT NULL,
     CONSTRAINT `users_pk` PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `giftees` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `created_date` TIMESTAMP NOT NULL,
    `user_id` BIGINT NOT NULL,
    CONSTRAINT `giftees_pk` PRIMARY KEY (id),
    CONSTRAINT `user_fk` FOREIGN KEY (user_id) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `occasions` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `date` DATE,
    `created_date` TIMESTAMP NOT NULL,
    `modified_date` TIMESTAMP NOT NULL,
    `giftee_id` BIGINT NOT NULL,
    CONSTRAINT `occasions_pk` PRIMARY KEY (id),
    CONSTRAINT `giftee_fk` FOREIGN KEY (giftee_id) REFERENCES `giftees` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `gift` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `description` TEXT NULL,
    `created_date` TIMESTAMP NOT NULL,
    `modified_date` TIMESTAMP NOT NULL,
    `occasion_id` BIGINT NOT NULL,
    CONSTRAINT `gift_pk` PRIMARY KEY (id),
    CONSTRAINT `occasion_fk` FOREIGN KEY (occasion_id) REFERENCES `occasions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

