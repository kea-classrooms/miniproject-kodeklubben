CREATE TABLE `users` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255),
  `email` varchar(255),
  `password` varchar(255),
  PRIMARY KEY (`id`, `name`)
);

CREATE TABLE `wishLists` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255),
  `userId` int
);

CREATE TABLE `wishes` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255),
  `link` varchar(255),
  `wishListId` int,
  `reservedById` int
);

ALTER TABLE `users` ADD FOREIGN KEY (`id`) REFERENCES `wishLists` (`userId`);

ALTER TABLE `wishLists` ADD FOREIGN KEY (`id`) REFERENCES `wishes` (`wishListId`);

ALTER TABLE `users` ADD FOREIGN KEY (`id`) REFERENCES `wishes` (`reservedById`);
