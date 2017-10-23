-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema smartstart
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `smartstart` ;

-- -----------------------------------------------------
-- Schema smartstart
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `smartstart` DEFAULT CHARACTER SET utf8 ;
USE `smartstart` ;

-- -----------------------------------------------------
-- Table `smartstart`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartstart`.`user` ;

CREATE TABLE IF NOT EXISTS `smartstart`.`user` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(64) NOT NULL,
  `password` VARCHAR(127) NOT NULL,
  `first_name` VARCHAR(32) NOT NULL,
  `last_name` VARCHAR(32) NOT NULL,
  `profile_image` BLOB NULL,
  `description` TEXT NULL,
  `date_created` TIMESTAMP NOT NULL,
  `last_updated` TIMESTAMP NULL,
  `active` TINYINT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `USER_ID_UNIQUE` (`id` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartstart`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartstart`.`role` ;

CREATE TABLE IF NOT EXISTS `smartstart`.`role` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(64) NOT NULL,
  `description` TEXT NULL,
  `active` TINYINT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `ROLE_ID_UNIQUE` (`id` ASC),
  UNIQUE INDEX `ROLE_NAME_UNIQUE` (`name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartstart`.`menu_category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartstart`.`menu_category` ;

CREATE TABLE IF NOT EXISTS `smartstart`.`menu_category` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(64) NOT NULL,
  `index` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `MENU_CATEGORY_ID_UNIQUE` (`id` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartstart`.`menu_item`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartstart`.`menu_item` ;

CREATE TABLE IF NOT EXISTS `smartstart`.`menu_item` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(64) NOT NULL,
  `index` INT UNSIGNED NOT NULL,
  `target` VARCHAR(127) NOT NULL,
  `menu_category_id` BIGINT UNSIGNED NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `MENU_ITEM_ID_UNIQUE` (`id` ASC),
  INDEX `FK_MNTM_MNCG_ID_idx` (`menu_category_id` ASC),
  CONSTRAINT `fk_menu_item_menu_category`
    FOREIGN KEY (`menu_category_id`)
    REFERENCES `smartstart`.`menu_category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartstart`.`permission`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartstart`.`permission` ;

CREATE TABLE IF NOT EXISTS `smartstart`.`permission` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(64) NOT NULL,
  `description` TEXT NULL,
  `active` TINYINT NOT NULL,
  `menu_item_id` BIGINT UNSIGNED NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `PRMS_ID_UNIQUE` (`id` ASC),
  UNIQUE INDEX `PRMS_NAME_UNIQUE` (`name` ASC),
  INDEX `FK_PRMS_MENU_ID_idx` (`menu_item_id` ASC),
  CONSTRAINT `fk_menu_item`
    FOREIGN KEY (`menu_item_id`)
    REFERENCES `smartstart`.`menu_item` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartstart`.`role_permission_mapping`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartstart`.`role_permission_mapping` ;

CREATE TABLE IF NOT EXISTS `smartstart`.`role_permission_mapping` (
  `role_id` BIGINT UNSIGNED NOT NULL,
  `permission_id` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`role_id`, `permission_id`),
  INDEX `fk_RPMP_PRMS_idx` (`permission_id` ASC),
  INDEX `fk_RPMP_ROLE_idx` (`role_id` ASC),
  CONSTRAINT `fk_rpmp_role`
    FOREIGN KEY (`role_id`)
    REFERENCES `smartstart`.`role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rpmp_permission`
    FOREIGN KEY (`permission_id`)
    REFERENCES `smartstart`.`permission` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartstart`.`user_role_mapping`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartstart`.`user_role_mapping` ;

CREATE TABLE IF NOT EXISTS `smartstart`.`user_role_mapping` (
  `user_id` BIGINT UNSIGNED NOT NULL,
  `role_id` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`),
  INDEX `FK_ROLE_idx` (`role_id` ASC),
  CONSTRAINT `fk_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `smartstart`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_role`
    FOREIGN KEY (`role_id`)
    REFERENCES `smartstart`.`role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartstart`.`user_permission_mapping`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartstart`.`user_permission_mapping` ;

CREATE TABLE IF NOT EXISTS `smartstart`.`user_permission_mapping` (
  `user_id` BIGINT UNSIGNED NOT NULL,
  `permission_id` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`user_id`, `permission_id`),
  INDEX `FK_URMP_ROLE_idx` (`permission_id` ASC),
  CONSTRAINT `fk_urmp_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `smartstart`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_urmp_permission`
    FOREIGN KEY (`permission_id`)
    REFERENCES `smartstart`.`permission` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `smartstart`.`user`
-- -----------------------------------------------------
START TRANSACTION;
USE `smartstart`;
INSERT INTO `smartstart`.`user` (`id`, `username`, `password`, `first_name`, `last_name`, `profile_image`, `description`, `date_created`, `last_updated`, `active`) VALUES (1, 'admin', '$2a$10$Ce2HJja0Trha0ee3.rMqQewIzJMVe87.jNi5zF5gDdsyvHjJsnwOm', 'Admin', 'Admin', NULL, NULL, now(), NULL, 1);
INSERT INTO `smartstart`.`user` (`id`, `username`, `password`, `first_name`, `last_name`, `profile_image`, `description`, `date_created`, `last_updated`, `active`) VALUES (2, 'user', '$2a$10$kwTBVDaG5KyoFVed1wWItOSxeyehmtqy2TZ2vvcEzqpXGEoYlRu7W', 'User', 'User', NULL, NULL, now(), NULL, 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `smartstart`.`role`
-- -----------------------------------------------------
START TRANSACTION;
USE `smartstart`;
INSERT INTO `smartstart`.`role` (`id`, `name`, `description`, `active`) VALUES (1, 'ROLE_ADMIN', 'System Admin', 1);
INSERT INTO `smartstart`.`role` (`id`, `name`, `description`, `active`) VALUES (2, 'ROLE_USER', 'Normal User', 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `smartstart`.`menu_category`
-- -----------------------------------------------------
START TRANSACTION;
USE `smartstart`;
INSERT INTO `smartstart`.`menu_category` (`id`, `name`, `index`) VALUES (1, 'userManagement', 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `smartstart`.`menu_item`
-- -----------------------------------------------------
START TRANSACTION;
USE `smartstart`;
INSERT INTO `smartstart`.`menu_item` (`id`, `name`, `index`, `target`, `menu_category_id`) VALUES (1, 'manageUser', 11, 'user/index', 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `smartstart`.`permission`
-- -----------------------------------------------------
START TRANSACTION;
USE `smartstart`;
INSERT INTO `smartstart`.`permission` (`id`, `name`, `description`, `active`, `menu_item_id`) VALUES (1, 'manageUser', NULL, 1, 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `smartstart`.`role_permission_mapping`
-- -----------------------------------------------------
START TRANSACTION;
USE `smartstart`;
INSERT INTO `smartstart`.`role_permission_mapping` (`role_id`, `permission_id`) VALUES (1, 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `smartstart`.`user_role_mapping`
-- -----------------------------------------------------
START TRANSACTION;
USE `smartstart`;
INSERT INTO `smartstart`.`user_role_mapping` (`user_id`, `role_id`) VALUES (1, 1);
INSERT INTO `smartstart`.`user_role_mapping` (`user_id`, `role_id`) VALUES (2, 2);

COMMIT;

