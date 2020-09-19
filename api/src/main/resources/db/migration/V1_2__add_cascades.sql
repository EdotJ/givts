ALTER TABLE giftees DROP CONSTRAINT IF EXISTS user_fk;
ALTER TABLE giftees ADD CONSTRAINT `user_fk` FOREIGN KEY (user_id) REFERENCES `users` (`id`) ON DELETE CASCADE;
ALTER TABLE occasions DROP CONSTRAINT IF EXISTS giftee_fk;
ALTER TABLE occasions ADD CONSTRAINT `giftee_fk` FOREIGN KEY (giftee_id) REFERENCES `giftees` (`id`) ON DELETE CASCADE;
ALTER TABLE gifts DROP CONSTRAINT IF EXISTS occasion_fk;
ALTER TABLE gifts ADD CONSTRAINT `occasion_fk` FOREIGN KEY (occasion_id) REFERENCES `occasions` (`id`) ON DELETE CASCADE;