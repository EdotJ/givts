LOCK TABLES
    `users` WRITE,
    `giftees` WRITE;
ALTER TABLE `giftees`
    DROP CONSTRAINT IF EXISTS `user_fk`;

ALTER TABLE `users`
    MODIFY COLUMN `id` BIGINT NOT NULL AUTO_INCREMENT;

ALTER TABLE `giftees`
    ADD CONSTRAINT `user_fk` FOREIGN KEY (user_id) REFERENCES `users` (`id`);

UNLOCK TABLES;