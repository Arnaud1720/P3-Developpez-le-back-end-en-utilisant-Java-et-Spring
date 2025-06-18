CREATE TABLE `users` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `email` varchar(255),
  `name` varchar(255),
  `password` varchar(255),
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `rentals` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255),
  `surface` numeric,
  `price` numeric,
  `picture` varchar(255),
  `description` varchar(2000),
  `owner_id` integer NOT NULL,
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `messages` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `rental_id` integer,
  `user_id` integer,
  `message` varchar(2000),
  `created_at` timestamp,
  `updated_at` timestamp
);
CREATE TABLE `role` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(50) NOT NULL UNIQUE
);
CREATE TABLE `user_roles` (
                            `user_id` INT NOT NULL,
                            `role_id` INT NOT NULL,
                            PRIMARY KEY (`user_id`, `role_id`),
                            FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
                            FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE
);

INSERT INTO `role` (`name`) VALUES ('ADMIN'), ('USER');

CREATE UNIQUE INDEX `USERS_index` ON `users` (`email`);

ALTER TABLE `rentals` ADD FOREIGN KEY (`owner_id`) REFERENCES `users` (`id`);

ALTER TABLE `messages` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `messages` ADD FOREIGN KEY (`rental_id`) REFERENCES `rentals` (`id`);
