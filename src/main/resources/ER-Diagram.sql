drop database if exists miniprojekt;

create database miniprojekt;
use miniprojekt;

CREATE TABLE `users` (
  `id` int AUTO_INCREMENT,
  `name` varchar(255),
  `email` varchar(255),
  `password` varchar(255),
  PRIMARY KEY (`id`)
);

CREATE TABLE `wishLists` (
  `id` int AUTO_INCREMENT,
  `name` varchar(255),
  `userId` int,
  PRIMARY KEY (`id`)
);

CREATE TABLE `wishes` (
  `id` int AUTO_INCREMENT,
  `name` varchar(255),
  `link` varchar(255),
  `wishListId` int,
  `reservedById` int,
  PRIMARY KEY (`id`)
);
