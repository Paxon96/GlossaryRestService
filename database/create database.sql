CREATE DATABASE Glossary;
USE Glossary;

CREATE TABLE `words` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	`pl_word` TEXT NOT NULL,
	`en_word` TEXT NOT NULL,
	PRIMARY KEY (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;