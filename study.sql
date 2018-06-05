/*
SQLyog Ultimate v11.26 (32 bit)
MySQL - 5.6.23-enterprise-commercial-advanced-log : Database - study
*********************************************************************
*/


CREATE DATABASE IF NOT EXISTS `study`  DEFAULT CHARACTER SET utf8;

USE `study`;


DROP TABLE IF EXISTS `t_payment`;

CREATE TABLE `t_payment` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(20) DEFAULT NULL,
  `pay_money` DECIMAL(18,0) DEFAULT NULL,
  `pay_time` DATETIME DEFAULT NULL,
  `pay_status` SMALLINT(1) DEFAULT NULL,
  `pay_msg` VARCHAR(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Data for the table `t_payment` */

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) DEFAULT NULL,
  `age` SMALLINT(3) DEFAULT NULL,
  `address` VARCHAR(256) DEFAULT NULL,
  `user_dept` VARCHAR(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8;

/*Data for the table `t_user` */

INSERT  INTO `t_user`(`id`,`name`,`age`,`address`,`user_dept`) VALUES (1,'张三',12,'上海市徐汇区','人事部');
