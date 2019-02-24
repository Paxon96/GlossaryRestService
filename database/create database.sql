CREATE DATABASE Glossary;
USE Glossary;

CREATE TABLE `words` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	`pl_word` TEXT NOT NULL,
	`en_word` TEXT NOT NULL,
    `add_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `users` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	`login` TINYTEXT NOT NULL,
	`pass` TINYTEXT NOT NULL,
    `permission` TINYTEXT NOT NULL,
    primary key (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;