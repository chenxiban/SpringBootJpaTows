/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.5.19 : Database - springjpacrm
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`springjpacrm` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `springjpacrm`;

/*Table structure for table `hibernate_sequence` */

DROP TABLE IF EXISTS `hibernate_sequence`;

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `hibernate_sequence` */

insert  into `hibernate_sequence`(`next_val`) values (30),(30);

/*Table structure for table `tb_modules` */

DROP TABLE IF EXISTS `tb_modules`;

CREATE TABLE `tb_modules` (
  `modules_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '备注:模块自动增长主键',
  `create_time` datetime DEFAULT NULL COMMENT '备注:模块创建时间',
  `founder` varchar(50) DEFAULT NULL COMMENT '备注:创建人',
  `last_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '备注:模块最后一次修改时间',
  `name` varchar(50) DEFAULT NULL COMMENT '备注:模块名称',
  `parent_id` int(10) unsigned NOT NULL COMMENT '备注:父模块编号',
  `path` varchar(120) DEFAULT NULL COMMENT '备注:模块路径',
  `update_man` varchar(50) DEFAULT NULL COMMENT '备注:修改人',
  `weight` int(10) unsigned DEFAULT NULL COMMENT '备注:权重',
  PRIMARY KEY (`modules_id`),
  UNIQUE KEY `UK_sjhvj2rlcj5dubwo7o0q7abo0` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

/*Data for the table `tb_modules` */

insert  into `tb_modules`(`modules_id`,`create_time`,`founder`,`last_update_time`,`name`,`parent_id`,`path`,`update_man`,`weight`) values (1,'2018-11-14 13:46:07','小佳','2018-11-14 13:46:17','权限管理',0,NULL,'小佳',100),(2,'2018-11-14 13:46:07','小佳','2018-11-14 13:47:18','用户管理',1,'yh.html','小佳',99),(3,'2018-11-14 13:46:07','小佳','2018-11-14 13:48:44','角色管理',1,'jdgl.html','小佳',98),(4,'2018-11-14 13:46:07','小佳','2018-11-14 13:48:46','模块管理',1,'modules.html','小佳',97),(5,'2018-11-14 13:46:07','小佳','2018-11-14 13:49:14','权限查看',1,'permission.html','小佳',96),(28,'2018-11-15 21:25:03','小佳','2018-11-15 21:25:03','啊啊',5,'',NULL,33);

/*Table structure for table `tb_permission` */

DROP TABLE IF EXISTS `tb_permission`;

CREATE TABLE `tb_permission` (
  `permission_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `permission_last_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '权限最近一次修改时间',
  `permission_module` varchar(50) NOT NULL COMMENT '权限所属模块',
  `permission_name` varchar(50) NOT NULL COMMENT '权限名称',
  `permission_value` varchar(50) DEFAULT NULL COMMENT '权限资源对象',
  PRIMARY KEY (`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

/*Data for the table `tb_permission` */

insert  into `tb_permission`(`permission_id`,`permission_last_update_time`,`permission_module`,`permission_name`,`permission_value`) values (1,'2018-05-20 22:13:21','用户模块','添加用户','users:addUsers'),(2,'2018-05-20 22:13:29','用户模块','查询用户','users:getAllPageUsers'),(3,'2018-05-20 22:13:31','用户模块','删除用户','users:delUsers'),(4,'2018-05-20 22:13:32','用户模块','修改用户','users:updUsers'),(5,'2018-05-20 22:13:37','用户模块','锁定用户','users:lockUsers'),(6,'2018-11-13 17:53:47','用户模块','重置密码','users:clearUsers'),(7,'2018-11-13 17:54:32','用户模块','修改密码','users:updUserPass'),(8,'2018-11-14 16:51:35','角色模块','不分页查询角色','roles:getRolesList'),(9,'2018-11-14 17:04:12','角色模块','获取用户角色','roles:getUserRolesByUserId'),(10,'2018-11-14 17:28:50','角色模块','查询角色','roles:getAllPageRoles'),(11,'2018-11-14 17:33:06','角色模块','添加角色','roles:addRoles'),(12,'2018-11-14 17:33:24','角色模块','删除角色','roles:delRoles'),(13,'2018-11-14 17:33:39','角色模块','修改角色','roles:updRoles'),(14,'2018-11-14 21:15:59','角色模块','移除角色','roles:delRolesId'),(15,'2018-11-14 21:23:49','角色模块','增加角色','roles:raddByRole'),(16,'2018-11-14 22:06:40','菜单模块','查询左侧菜单树','modules:queryMenuTree'),(17,'2018-11-14 22:07:08','菜单模块','查询模块','modules:getModules'),(18,'2018-11-14 22:13:37','菜单模块','添加模块','modules:addModules'),(19,'2018-11-14 22:13:48','菜单模块','修改模块','modules:updModules'),(20,'2018-11-14 22:16:45','菜单模块','删除模块','modules:delModules'),(21,'2018-11-15 20:41:03','菜单模块','查询角色设置模块','modules:queryRoleSetModuleTree'),(22,'2018-11-15 21:21:03','角色模块','角色设置菜单模块','modules:setRoleModule'),(23,'2018-11-15 21:49:16','权限模块','查询所有权限','permission:queryNodess'),(24,'2018-11-16 13:38:52','权限模块','角色设置操作权限','permission:setRolePermission');

/*Table structure for table `tb_rolemodules` */

DROP TABLE IF EXISTS `tb_rolemodules`;

CREATE TABLE `tb_rolemodules` (
  `role_id` int(10) unsigned NOT NULL COMMENT '备注:角色自动增长主键',
  `modules_id` int(10) unsigned NOT NULL COMMENT '备注:模块自动增长主键',
  PRIMARY KEY (`modules_id`,`role_id`),
  KEY `FKhb29mdbx6e0icqxaut4l25fcx` (`role_id`),
  CONSTRAINT `FKhb29mdbx6e0icqxaut4l25fcx` FOREIGN KEY (`role_id`) REFERENCES `tb_roles` (`role_id`),
  CONSTRAINT `FKev7wlpd3ew18vq5i5garxieal` FOREIGN KEY (`modules_id`) REFERENCES `tb_modules` (`modules_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_rolemodules` */

insert  into `tb_rolemodules`(`role_id`,`modules_id`) values (2,1),(2,2),(2,3),(2,4),(12,1),(12,2),(12,3),(12,4);

/*Table structure for table `tb_rolepermission` */

DROP TABLE IF EXISTS `tb_rolepermission`;

CREATE TABLE `tb_rolepermission` (
  `role_id` int(10) unsigned NOT NULL COMMENT '备注:角色自动增长主键',
  `permission_id` int(10) unsigned NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`permission_id`,`role_id`),
  KEY `FKctrkpw96n1lfjf6nmpmxxjkjr` (`role_id`),
  CONSTRAINT `FK30lcsmod8av77oje9180e8h3j` FOREIGN KEY (`permission_id`) REFERENCES `tb_permission` (`permission_id`),
  CONSTRAINT `FKctrkpw96n1lfjf6nmpmxxjkjr` FOREIGN KEY (`role_id`) REFERENCES `tb_roles` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_rolepermission` */

insert  into `tb_rolepermission`(`role_id`,`permission_id`) values (2,1),(2,2),(2,3),(2,4),(2,5),(2,6),(2,7),(2,8),(2,9),(2,10),(2,11),(2,12),(2,13),(2,14),(2,15),(2,16),(2,17),(2,18),(2,19),(2,20),(2,21),(2,22),(2,23),(2,24),(17,8),(17,9),(17,10),(17,11);

/*Table structure for table `tb_roles` */

DROP TABLE IF EXISTS `tb_roles`;

CREATE TABLE `tb_roles` (
  `role_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '备注:角色自动增长主键',
  `create_time` datetime DEFAULT NULL COMMENT '备注:角色创建时间',
  `explains` varchar(100) DEFAULT NULL COMMENT '备注:角色XXXXX',
  `last_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '备注:最后一次操作时间',
  `name` varchar(20) DEFAULT NULL COMMENT '备注:角色名称',
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `UK_8ghpmg7galg4xu5qu0feu2cmi` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

/*Data for the table `tb_roles` */

insert  into `tb_roles`(`role_id`,`create_time`,`explains`,`last_update_time`,`name`) values (2,'2018-11-13 12:49:39',NULL,'2018-11-13 12:49:40','管理员'),(12,'2018-11-14 17:42:03',NULL,'2018-11-14 21:03:14','教员'),(15,'2018-11-14 21:09:06',NULL,'2018-11-14 21:09:06','学习委员'),(16,'2018-11-14 21:09:13',NULL,'2018-11-14 21:09:13','学霸'),(17,'2018-11-14 21:09:30',NULL,'2018-11-14 21:09:30','技术总监'),(18,'2018-11-14 21:09:40',NULL,'2018-11-14 21:09:40','学员'),(19,'2018-11-14 21:09:51',NULL,'2018-11-14 21:09:51','HR面试官'),(20,'2018-11-14 21:10:06',NULL,'2018-11-14 21:10:06','项目组长'),(21,'2018-11-14 21:10:14',NULL,'2018-11-14 21:10:14','项目经理'),(22,'2018-11-14 21:10:25',NULL,'2018-11-14 21:10:25','数据库专家'),(23,'2018-11-14 21:10:34',NULL,'2018-11-14 21:10:34','院长');

/*Table structure for table `tb_user` */

DROP TABLE IF EXISTS `tb_user`;

CREATE TABLE `tb_user` (
  `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '备注:用户自动增长主键',
  `create_time` datetime DEFAULT NULL COMMENT '备注:用户创建时间',
  `is_lookout` char(1) DEFAULT '否' COMMENT '备注:用户是否锁定',
  `last_login_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '备注:用户最后一次登陆时间',
  `leave_boolean` varchar(20) DEFAULT '是' COMMENT '备注:是否可以再次申请请假',
  `leave_chi` int(10) unsigned DEFAULT '0' COMMENT '备注:迟到次数',
  `leave_state` varchar(20) DEFAULT '否' COMMENT '备注:是否可以进行请假',
  `leave_zao` int(10) unsigned DEFAULT '0' COMMENT '备注:早退次数',
  `lock_time` datetime DEFAULT NULL COMMENT '备注:用户被锁定时间',
  `login_name` varchar(20) DEFAULT NULL COMMENT '备注:用户登录名称',
  `pass_words` varchar(50) DEFAULT NULL COMMENT '备注:用户密码',
  `protect_email` varchar(50) DEFAULT NULL COMMENT '备注:用户密保邮箱',
  `protect_mtel` varchar(11) DEFAULT NULL COMMENT '备注:用户密保手机号',
  `psd_wrong_time` tinyint(3) unsigned DEFAULT '0' COMMENT '备注:密码错误次数',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_782grrlecegih6wvvbpeokjx` (`login_name`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*Data for the table `tb_user` */

insert  into `tb_user`(`user_id`,`create_time`,`is_lookout`,`last_login_time`,`leave_boolean`,`leave_chi`,`leave_state`,`leave_zao`,`lock_time`,`login_name`,`pass_words`,`protect_email`,`protect_mtel`,`psd_wrong_time`) values (1,'2018-11-13 12:49:39','否','2018-11-19 13:52:50','是',0,'否',0,NULL,'小佳','20b8a217877fc0977f5f6be50fab7aa0','chen867647213@163.com','15638589820',0),(3,'2018-11-13 12:49:57','否','2018-11-14 14:25:03','是',0,'否',0,NULL,'梦梦','c790ff0533c86956fcd0ea8eea29051f',NULL,NULL,0),(4,'2018-11-13 12:50:18','否','2018-11-13 13:42:48','是',0,'否',0,NULL,'大炮','9b807ae69e686806cd9bb21168ab3104',NULL,NULL,0),(10,'2018-11-13 15:34:38','否','2018-11-14 22:43:33','是',0,'否',0,NULL,'大炮啊','7d839b8ccfa9b30435b889c98ec1cf00',NULL,'15638589820',0);

/*Table structure for table `tb_userroles` */

DROP TABLE IF EXISTS `tb_userroles`;

CREATE TABLE `tb_userroles` (
  `role_id` int(10) unsigned NOT NULL COMMENT '备注:角色自动增长主键',
  `user_id` int(10) unsigned NOT NULL COMMENT '备注:用户自动增长主键',
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKk17c0n22kgu4bg4wwmqyqhsdw` (`role_id`),
  CONSTRAINT `FK730f67ccl0mes2okuap0v7sbe` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`user_id`),
  CONSTRAINT `FKk17c0n22kgu4bg4wwmqyqhsdw` FOREIGN KEY (`role_id`) REFERENCES `tb_roles` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_userroles` */

insert  into `tb_userroles`(`role_id`,`user_id`) values (2,1),(15,10),(18,4);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
