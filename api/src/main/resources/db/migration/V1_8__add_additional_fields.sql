ALTER TABLE gifts ADD COLUMN `price` DECIMAL NOT NULL;
ALTER TABLE giftees ADD COLUMN `likes` TEXT NULL;
ALTER TABLE giftees ADD COLUMN `dislikes` TEXT NULL;
ALTER TABLE giftees ADD COLUMN `hobbies` VARCHAR(255) NULL;
ALTER TABLE gifts ADD COLUMN `image_path` VARCHAR(255) NULL;