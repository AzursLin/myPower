/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.20-log : Database - mypower
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mypower` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin */;

USE `mypower`;

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '用户ID',
  `user_name` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户登录名',
  `user_nickname` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户昵称',
  `password` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户密码',
  `login_date` date DEFAULT NULL COMMENT '上次登录时间',
  `create_date` date DEFAULT NULL COMMENT '创建时间',
  `admin` int(1) DEFAULT NULL COMMENT '1是普通用户 2是管理员',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

/*Data for the table `user` */

insert  into `user`(`user_id`,`user_name`,`user_nickname`,`password`,`login_date`,`create_date`,`admin`) values ('54163f5e-0e31-4c72-9d35-2a7b3e5d20c3','1','1栋','1','2018-03-21','2018-03-21',1),('56284cc2-2038-487b-88d9-208c2331e506','9','9栋','9','2018-03-21','2018-03-21',1),('6dfd6834-4b2e-4bf7-8f03-7cdf04ce00aa','2','2栋','2','2018-03-21','2018-03-21',1),('805a82e9-514d-43e3-b880-30339db00b39','10栋','10栋','10','2018-03-21','2018-03-21',1),('9183739a-bcc5-400c-a748-d858e0a2cbd7','3','3栋','3','2018-03-21','2018-03-21',1),('933ae5a5-210d-48f1-8cb3-8db2831b51fc','4','4栋','4','2018-01-30','2018-03-20',1),('ac186a0f-3e35-4e16-ab99-87b8e92b0692','5','5栋','5','2018-03-21','2018-03-21',1),('b2ee14cc-7f6a-4aa5-a824-9d0a8e458cf5','6','6栋','6','2018-03-21','2018-03-21',1),('d1b1527f-323a-4771-866c-ba2c92fafeea','7','7栋','7','2018-03-21','2018-03-21',1),('e4414153-c396-46de-9c31-cbb969a55f26','8','8栋','8','2018-03-20','2018-03-20',1),('f83c6db4-2a3c-42b1-9164-d6832e182f0d','admin','管理员','admin','2018-03-20','2018-03-20',2);

/*Table structure for table `user_dorm` */

DROP TABLE IF EXISTS `user_dorm`;

CREATE TABLE `user_dorm` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户ID',
  `dorm` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '对应宿舍编号',
  `user_name` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '学生名',
  `college` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '学院',
  `class_info` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '班级',
  `update_date` datetime DEFAULT NULL COMMENT '修改操作时间',
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

/*Data for the table `user_dorm` */

insert  into `user_dorm`(`id`,`user_id`,`dorm`,`user_name`,`college`,`class_info`,`update_date`) values (1,'9183739a-bcc5-400c-a748-d858e0a2cbd7','3101','张三','信息工程','2017届3班','2018-03-20 11:50:59'),(2,'9183739a-bcc5-400c-a748-d858e0a2cbd7','3101','李四','信息工程','2017届3班','2018-03-20 13:37:55'),(3,'56284cc2-2038-487b-88d9-208c2331e506','4305','赵五','信息工程1','2015届3班','2018-03-21 11:38:23'),(4,'ac186a0f-3e35-4e16-ab99-87b8e92b0692','5505','戴66','信息工程1','2018届4班','2018-03-20 15:03:31');

/*Table structure for table `user_electricity` */

DROP TABLE IF EXISTS `user_electricity`;

CREATE TABLE `user_electricity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户ID',
  `user_read` int(11) DEFAULT NULL COMMENT '用电度数',
  `money` int(11) DEFAULT NULL COMMENT '费用',
  `create_date` date DEFAULT NULL COMMENT '月份',
  `pay` int(11) DEFAULT '0' COMMENT '0未缴费 1以缴清',
  PRIMARY KEY (`id`),
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

/*Data for the table `user_electricity` */

insert  into `user_electricity`(`id`,`user_id`,`user_read`,`money`,`create_date`,`pay`) values (1,'e4414153-c396-46de-9c31-cbb969a55f26',95,143,'2018-03-20',0),(2,'56284cc2-2038-487b-88d9-208c2331e506',11,11,'2018-03-05',0),(3,'6dfd6834-4b2e-4bf7-8f03-7cdf04ce00aa',11,11,'2018-03-08',0),(4,'54163f5e-0e31-4c72-9d35-2a7b3e5d20c3',432,1432,'2018-03-14',0),(5,'54163f5e-0e31-4c72-9d35-2a7b3e5d20c3',2544,3434,'2018-01-08',1),(6,'56284cc2-2038-487b-88d9-208c2331e506',123,321,'2018-03-01',0);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
