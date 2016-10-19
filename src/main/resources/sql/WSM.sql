-- database create
create database wms;


-- User Table
CREATE TABLE `user`(
	`id` int AUTO_INCREMENT NOT NULL,
	`email` varchar(255) NOT NULL,
	`enabled` tinyint NOT NULL,
	`fullname` varchar(255) NOT NULL,
	`username` varchar(255) NOT NULL,
	`password` varchar(255) NULL,
	`created_by` varchar(255) NULL,
	`creation_time` datetime NULL,
	`updated_by` varchar(255) NULL,
	`update_time` datetime NULL,
   PRIMARY KEY (id)
    );

-- user to role

CREATE TABLE `user_role`(
	`user_id` int NOT NULL,
	`role_id` int NOT NULL,
        PRIMARY KEY (user_id,role_id));

-- role

CREATE TABLE `role`(
	`id` int AUTO_INCREMENT NOT NULL,
	`created_by` varchar(255) NULL,
	`creation_time` datetime NULL,
	`description` varchar(255) NULL,
	`isDeleted` tinyint NOT NULL,
	`updated_by` varchar(255) NULL,
	`update_time` datetime NULL,
	`role` varchar(255) NOT NULL,
         PRIMARY KEY (id)

);

-- access rights table

CREATE TABLE `access_right`(
	`id` int AUTO_INCREMENT NOT NULL,
	`name` varchar(45) NOT NULL,
	`created_by` varchar(255) NULL,
	`creation_time` datetime NULL,
	`description` varchar(255) NOT NULL,
	`isDeleted` tinyint NOT NULL,
	`updated_by` varchar(255) NULL,
	`update_time` datetime NULL,
	PRIMARY KEY (id)

);

-- role to access right

CREATE TABLE `role_access_right`(
	`role_id` int NOT NULL,
	`access_right_id` int NOT NULL,
	PRIMARY KEY (role_id,access_right_id));

-- access log table
CREATE TABLE `accessLog`(
	`id` int AUTO_INCREMENT NOT NULL,
	`action_date` datetime NULL,
	`activity` varchar(255) NULL,
	`actor_user_id` int NULL,
	`actor_username` varchar(255) NULL,
	`description` varchar(255) NULL,
	`isDeleted` tinyint NOT NULL,
	`level` varchar(255) NULL,
	PRIMARY KEY (id));


-- audit trail table
CREATE TABLE `audittrail`(
	`id` int AUTO_INCREMENT NOT NULL,
	`activity` varchar(255) NOT NULL,
	`actorUserId` int NOT NULL,
	`actorUsername` varchar(255) NOT NULL,
	`actionDate` datetime NOT NULL,
	`description` varchar(255) NOT NULL,
	`isDeleted` tinyint NOT NULL,
	`logLevel` varchar(255) NOT NULL,
	PRIMARY KEY (id));

-- system parameter
CREATE TABLE `systemparameter`(
	`id` int AUTO_INCREMENT NOT NULL,
	`created_by` varchar(255) NULL,
	`creation_time` datetime NULL,
	`isDeleted` tinyint NOT NULL,
	`updated_by` varchar(255) NULL,
	`update_time` datetime NULL,
	`propertyName` varchar(30) NOT NULL,
	`propertyValue` text NOT NULL,
	`description` varchar(255) NOT NULL,
	PRIMARY KEY (id));

-- user creation
insert into user values(1,'admin@gmail.com',1,'name','admin','$2a$10$Kbfny8oauosIy/SN0fa64.OVKJbLmVrbmRmGd.xuQfjH8bss8lr3W','','2015-06-06','','2015-06-06');

-- role data
insert into role values(1,'system','2015-06-06 00:00:00','Role For Login',0,'system','2015-06-06 00:00:00','ROLE_USER')
;

-- user and role
insert into user_role values(1,1);


-- operations

-- customer table

CREATE TABLE `customer`(
	`id` int AUTO_INCREMENT NOT NULL,
	`name` varchar(100) NOT NULL,
	`account_number` varchar(100) NOT NULL,
	`taxid_no` varchar(100) NOT NULL,
	`term` varchar(100) NOT NULL,
	`email` varchar(100) NOT NULL,
	`engine` varchar(100) NOT NULL,
	`chassis_no` varchar(100) NOT NULL,
	`capacity` text NOT NULL,
	`color` varchar(100) NOT NULL,
	`address` varchar(255) NOT NULL,
	`city` varchar(30) NOT NULL,
	`state` varchar(100) NOT NULL,
	`country` varchar(100) NOT NULL,
	`postalcode` int NOT NULL,
	`mobile_number` varchar(255) NOT NULL,
	`home_number` varchar(255) NOT NULL,
	`created_by` varchar(255) NULL,
	`creation_time` datetime NULL,
	`updated_by` varchar(255) NULL,
	`update_time` datetime NULL,
	`isDeleted` tinyint NOT NULL,
	PRIMARY KEY (id));

-- inventory

-- item table

CREATE TABLE `item`(
	`id` int AUTO_INCREMENT NOT NULL,
	`item_code` varchar(45) NOT NULL,
	`item_name` varchar(255) NULL,
	`creation_time` datetime NULL,
	`isDeleted` tinyint NOT NULL,
	`updated_by` varchar(255) NULL,
	`update_time` datetime NULL,
	PRIMARY KEY (id)

);






