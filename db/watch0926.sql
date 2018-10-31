/*
SQLyog Ultimate v11.25 (64 bit)
MySQL - 5.7.17-log : Database - suo
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`suo` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `suo`;

/*Table structure for table `add_friend_info` */

DROP TABLE IF EXISTS `add_friend_info`;

CREATE TABLE `add_friend_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `imei` varchar(256) NOT NULL,
  `add_imei` varchar(250) NOT NULL,
  `status` char(2) NOT NULL DEFAULT '0',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `add_friend_info` */

insert  into `add_friend_info`(`id`,`imei`,`add_imei`,`status`,`createtime`) values (4,'8800000015','1000000000','2','2018-09-12 15:02:39'),(5,'8800000015','1000000001','2','2018-09-12 15:03:27'),(6,'8800000015','1000000000','2','2018-09-12 15:13:08');

/*Table structure for table `apilog` */

DROP TABLE IF EXISTS `apilog`;

CREATE TABLE `apilog` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `req` text,
  `resp` text,
  `imei` varchar(128) DEFAULT NULL,
  `rstatus` int(4) NOT NULL DEFAULT '0',
  `rmsg` text,
  `time` int(11) NOT NULL DEFAULT '0',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19588 DEFAULT CHARSET=utf8;

/*Data for the table `apilog` */

insert  into `apilog`(`id`,`name`,`req`,`resp`,`imei`,`rstatus`,`rmsg`,`time`,`createtime`) values (19481,'OpenDoorController.all(..)','[\"123456\"]','{\"code\":0,\"data\":[{\"side\":1,\"createtime\":1512615737000,\"name\":\"\",\"imei\":\"F9:71:6B:A8:62:55\",\"id\":13013,\"type\":1,\"way\":1},{\"side\":1,\"createtime\":1512616056000,\"name\":\"\",\"imei\":\"F9:71:6B:A8:62:55\",\"id\":13014,\"type\":1,\"way\":1},{\"side\":2,\"createtime\":1512616085000,\"name\":\"\",\"imei\":\"F9:71:6B:A8:62:55\",\"id\":13015,\"type\":2,\"way\":2},{\"side\":1,\"createtime\":1512616100000,\"name\":\"\",\"imei\":\"F9:71:6B:A8:62:55\",\"id\":13016,\"type\":3,\"way\":4},{\"side\":1,\"createtime\":1512616121000,\"name\":\"\",\"imei\":\"F9:71:6B:A8:62:55\",\"id\":13017,\"type\":3,\"way\":4},{\"side\":1,\"createtime\":1512637396000,\"name\":\"\",\"imei\":\"F9:71:6B:A8:62:55\",\"id\":13018,\"type\":1,\"way\":1},{\"side\":1,\"createtime\":1512637462000,\"name\":\"\",\"imei\":\"F9:71:6B:A8:62:55\",\"id\":13019,\"type\":1,\"way\":1}],\"message\":\"操作成功！\"}','',0,'',338,'2017-12-20 14:30:28'),(19482,'OpenDoorController.all1(..)','[\"123456\",\"2017-01-01\",\"2017-12-30\"]','{\"code\":0,\"data\":[{\"side\":1,\"createtime\":1512615737000,\"name\":\"\",\"imei\":\"F9:71:6B:A8:62:55\",\"id\":13013,\"type\":1,\"way\":1},{\"side\":1,\"createtime\":1512616056000,\"name\":\"\",\"imei\":\"F9:71:6B:A8:62:55\",\"id\":13014,\"type\":1,\"way\":1},{\"side\":2,\"createtime\":1512616085000,\"name\":\"\",\"imei\":\"F9:71:6B:A8:62:55\",\"id\":13015,\"type\":2,\"way\":2},{\"side\":1,\"createtime\":1512616100000,\"name\":\"\",\"imei\":\"F9:71:6B:A8:62:55\",\"id\":13016,\"type\":3,\"way\":4},{\"side\":1,\"createtime\":1512616121000,\"name\":\"\",\"imei\":\"F9:71:6B:A8:62:55\",\"id\":13017,\"type\":3,\"way\":4},{\"side\":1,\"createtime\":1512637396000,\"name\":\"\",\"imei\":\"F9:71:6B:A8:62:55\",\"id\":13018,\"type\":1,\"way\":1},{\"side\":1,\"createtime\":1512637462000,\"name\":\"\",\"imei\":\"F9:71:6B:A8:62:55\",\"id\":13019,\"type\":1,\"way\":1}],\"message\":\"操作成功！\"}','',0,'',313,'2017-12-20 14:33:51'),(19483,'com.bracelet.socket.business.impl.OpenDoorService','{\"a\":0,\"no\":\"10000001\",\"data\":{\"side\":2,\"battery_percent\":60,\"userid\":977613882,\"way\":67,\"register\":56},\"imei\":\"CA:39:D5:F9:4E:8C\",\"type\":11,\"timestamp\":1515670989}','','',1,'PreparedStatementCallback; SQL [insert into voltage_info (imei, voltage,upload_time) values (?,?,?)]; Column \'voltage\' cannot be null; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException: Column \'voltage\' cannot be null',591,'2018-01-15 11:09:49'),(19484,'com.bracelet.socket.business.impl.OpenDoorService','{\"a\":0,\"no\":\"10000001\",\"data\":{\"side\":2,\"battery_percent\":60,\"userid\":977613882,\"way\":67,\"register\":56},\"imei\":\"CA:39:D5:F9:4E:8C\",\"type\":11,\"timestamp\":1515670989}','','',0,'',1667,'2018-01-15 11:10:50'),(19485,'LocationController.oldLocation(..)','[\"18735662247|460,00,9338,213575054,-1\"]','null','',1,'Illegal character in query at index 137: http://apilocate.amap.com/position?key=b4a2748e41314ae117645aa9589c6723&output=json&accesstype=0&cdma=0&network=0&bts=8&nearbts=0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0',436,'2018-06-11 16:19:49'),(19486,'LocationController.oldLocation(..)','[\"18735662247|460,00,9338,213575054,-1\"]','null','',1,'Illegal character in query at index 137: http://apilocate.amap.com/position?key=b4a2748e41314ae117645aa9589c6723&output=json&accesstype=0&cdma=0&network=0&bts=8&nearbts=0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0',266,'2018-06-11 16:24:19'),(19487,'LocationController.oldLocation(..)','[\"18735662247|460,00,9338,213575054,-1\"]','null','',1,'Illegal character in query at index 160: http://apilocate.amap.com/position?key=b4a2748e41314ae117645aa9589c6723&output=json&accesstype=0&cdma=0&network=0&bts=460,00,9338,213575054,-1&nearbts=0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0',246,'2018-06-11 16:25:37'),(19488,'LocationController.oldLocation(..)','[\"18735662247||460,00,9338,213575054,-1\"]','null','',1,'Illegal character in query at index 137: http://apilocate.amap.com/position?key=b4a2748e41314ae117645aa9589c6723&output=json&accesstype=0&cdma=0&network=0&bts=8&nearbts=0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0',244,'2018-06-11 17:41:47'),(19489,'LocationController.oldLocation(..)','[\"abcdeff7||460,00,9338,213575054,-1\"]','null','',1,'Illegal character in query at index 137: http://apilocate.amap.com/position?key=b4a2748e41314ae117645aa9589c6723&output=json&accesstype=0&cdma=0&network=0&bts=b&nearbts=0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0',1,'2018-06-11 17:42:57'),(19490,'LocationController.oldLocation(..)','[\"abcdeff7||460,00,9338,213575054,-1\"]','null','',1,'Illegal character in query at index 159: http://apilocate.amap.com/position?key=b4a2748e41314ae117645aa9589c6723&output=json&accesstype=0&cdma=0&network=0&bts=460,0,9338,213575054,-1&nearbts=0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0',241,'2018-06-11 17:45:46'),(19491,'LocationController.oldLocation(..)','[\"abcdeff7||460,00,9338,213575054,-1\"]','\"{\\\"resultCode\\\":1}\"','',0,'',596,'2018-06-11 18:03:10'),(19492,'LocationController.oldLocation(..)','[\"abcdeff7||460,00,9338,213575054,-1\"]','\"{\\\"resultCode\\\":1}\"','',0,'',581,'2018-06-11 18:06:52'),(19493,'LocationController.oldLocation(..)','[\"abcdeff7|460,00,9338,213575054,-1\"]','\"{\\\"resultCode\\\":1}\"','',0,'',70,'2018-06-11 18:07:29'),(19494,'LocationController.oldLocation(..)','[\"abcdeff7\"]','null','',1,'1',11,'2018-06-11 18:10:42'),(19495,'LocationController.searchOldLocation(..)','[\"abcdeff7\"]','\"{\\\"CODES\\\":1,\\\"lng\\\":\\\"113.9217451\\\",\\\"lat\\\":\\\"22.4998996\\\",\\\"uploadtime\\\":1528711649000}\"','',0,'',17,'2018-06-11 18:11:16'),(19496,'LocationController.searchLocationTrack(..)','[\"1231312\",\"2018-06-08 18:03:10\",\"2018-07-11 18:03:10\"]','null','',1,'PreparedStatementCallback; bad SQL grammar [select * from location_old where imei=? and upload_time > ? and upload_time < ? order by upload_time asc]; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'imei\' in \'where clause\'',372,'2018-06-13 14:53:58'),(19497,'LocationController.searchLocationTrack(..)','[\"1231312\",\"2018-06-08 18:03:10\",\"2018-07-11 18:03:10\"]','\"{\\\"result\\\":\\\"[{lng=113.9217412121, lat=22.4998996, timestamp=1528711390000}, {lng=113.9217451, lat=22.4998996, timestamp=1528711612000}, {lng=113.9217451, lat=22.4998996, timestamp=1528711649000}]\\\",\\\"codes\\\":1}\"','',0,'',300,'2018-06-13 14:55:41'),(19498,'LocationController.searchLocationTrack(..)','[\"1231312\",\"2018-06-08 18:03:10\",\"2018-07-11 18:03:10\"]','\"{\\\"result\\\":\\\"[{lng=113.9217412121, lat=22.4998996, timestamp=1528711390000}, {lng=113.9217451, lat=22.4998996, timestamp=1528711612000}, {lng=113.9217451, lat=22.4998996, timestamp=1528711649000}]\\\",\\\"codes\\\":1}\"','',0,'',3,'2018-06-13 14:57:07'),(19499,'LocationController.oldphoneBind(..)','[\"18735662247\",\"123456\",\"å\\u0093\\u0088å\\u0093\\u0088\"]','null','',1,'PreparedStatementCallback; bad SQL grammar [select * from olddevice_bind where  phone = ? and imei = ? order by id desc LIMIT 1]; nested exception is java.sql.SQLException: No value specified for parameter 1',42,'2018-06-13 15:01:41'),(19500,'LocationController.oldphoneBind(..)','[\"18735662247\",\"123456\",\"å\\u0093\\u0088å\\u0093\\u0088\"]','null','',1,'PreparedStatementCallback; bad SQL grammar [select * from olddevice_bind where  phone = ? and imei = ? order by id desc LIMIT 1]; nested exception is java.sql.SQLException: No value specified for parameter 1',3,'2018-06-13 15:04:42'),(19501,'LocationController.oldphoneBind(..)','[\"18735662247\",\"123456\",\"å\\u0093\\u0088å\\u0093\\u0088\"]','\"{\\\"codes\\\":1}\"','',0,'',328,'2018-06-13 15:05:22'),(19502,'LocationController.oldphoneBind(..)','[\"18735662247\",\"123456\",\"å\\u0093\\u0088å\\u0093\\u0088\"]','\"{\\\"codes\\\":0}\"','',0,'',5,'2018-06-13 15:07:35'),(19503,'LocationController.oldphoneBind(..)','[\"18735662247\",\"123456dsd\",\"å\\u0093\\u0088å\\u0093\\u0088\"]','\"{\\\"codes\\\":1}\"','',0,'',60,'2018-06-13 15:08:35'),(19504,'LocationController.searchLocationTrack(..)','[\"18735662247\"]','\"{\\\"result\\\":\\\"[{name=å\\\\u0093\\\\u0088å\\\\u0093\\\\u0088, imei=123456, id=1, timestamp=1528873522000}, {name=å\\\\u0093\\\\u0088å\\\\u0093\\\\u0088, imei=123456dsd, id=2, timestamp=1528873715000}]\\\",\\\"codes\\\":1}\"','',0,'',2,'2018-06-13 15:09:46'),(19505,'LocationController.searchLocationTrack(..)','[\"18735662247\"]','\"{\\\"result\\\":\\\"[{name=aa, imei=123456, id=1, timestamp=1528873522000}, {name=aa, imei=123456dsd, id=2, timestamp=1528873715000}]\\\",\\\"codes\\\":1}\"','',0,'',2,'2018-06-13 15:10:42'),(19506,'LocationController.oldPhoneUpdate(..)','[1,\"bb\"]','null','',1,'PreparedStatementCallback; bad SQL grammar [update user_info set name=? where id = ?]; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'id\' in \'where clause\'',46,'2018-06-13 15:12:40'),(19507,'LocationController.oldPhoneUpdate(..)','[1,\"bb\"]','\"{\\\"codes\\\":1}\"','',0,'',273,'2018-06-13 15:13:52'),(19508,'LocationController.searchLocationTrack(..)','[\"18735662247\"]','\"{\\\"result\\\":[{\\\"name\\\":\\\"bb\\\",\\\"imei\\\":\\\"123456\\\",\\\"id\\\":1,\\\"timestamp\\\":1528873522000},{\\\"name\\\":\\\"aa\\\",\\\"imei\\\":\\\"123456dsd\\\",\\\"id\\\":2,\\\"timestamp\\\":1528873715000}],\\\"codes\\\":1}\"','',0,'',229,'2018-06-13 15:21:22'),(19509,'LocationController.searchLocationTrack(..)','[\"1231312\",\"2018-06-08 18:03:10\",\"2018-07-11 18:03:10\"]','\"{\\\"result\\\":[{\\\"lng\\\":\\\"113.9217412121\\\",\\\"lat\\\":\\\"22.4998996\\\",\\\"timestamp\\\":1528711390000},{\\\"lng\\\":\\\"113.9217451\\\",\\\"lat\\\":\\\"22.4998996\\\",\\\"timestamp\\\":1528711612000},{\\\"lng\\\":\\\"113.9217451\\\",\\\"lat\\\":\\\"22.4998996\\\",\\\"timestamp\\\":1528711649000}],\\\"codes\\\":1}\"','',0,'',284,'2018-06-13 15:23:04'),(19510,'LocationController.oldphoneBind(..)','[\"18735662247\",\"123456\",\"å\\u0093\\u0088å\\u0093\\u0088\"]','\"{\\\"codes\\\":0}\"','',0,'',292,'2018-06-13 15:25:00'),(19511,'LocationController.unOldBindDevice(..)','[1]','\"{\\\"codes\\\":1}\"','',0,'',724,'2018-06-13 17:19:58'),(19512,'LocationController.unOldBindDevice(..)','[1]','\"{\\\"codes\\\":1}\"','',0,'',229,'2018-06-13 17:20:34'),(19513,'LocationController.unOldBindDevice(..)','[2]','\"{\\\"codes\\\":1}\"','',0,'',48,'2018-06-13 17:20:55'),(19514,'LocationController.oldPhoneUpdate(..)','[\"{\\\"id\\\":3,\\\"name\\\":\\\"特体格tet\\\"}\"]','\"{\\\"codes\\\":1}\"','',0,'',599,'2018-06-14 15:25:54'),(19515,'LocationController.oldphoneBind(..)','[\"{\\\"phone\\\":\\\"18735666557\\\",\\\"name\\\":\\\"特体格tet\\\",\\\"imei\\\":\\\"123123213\\\"}\"]','\"{\\\"codes\\\":1}\"','',0,'',82,'2018-06-14 15:27:12'),(19516,'LocationController.oldphoneBind(..)','[\"{\\\"phone\\\":\\\"18735666557\\\",\\\"name\\\":\\\"特体格tet\\\",\\\"imei\\\":\\\"123123213\\\"}\"]','\"{\\\"codes\\\":0}\"','',0,'',4,'2018-06-14 15:27:15'),(19517,'LocationController.searchLocationTrack(..)','[\"18735666557\"]','\"{\\\"result\\\":\\\"[{\\\\\\\"name\\\\\\\":\\\\\\\"特体格tet\\\\\\\",\\\\\\\"imei\\\\\\\":\\\\\\\"1212\\\\\\\",\\\\\\\"id\\\\\\\":3,\\\\\\\"timestamp\\\\\\\":1528881645000},{\\\\\\\"name\\\\\\\":\\\\\\\"哈哈哈\\\\\\\",\\\\\\\"imei\\\\\\\":\\\\\\\"123123213\\\\\\\",\\\\\\\"id\\\\\\\":4,\\\\\\\"timestamp\\\\\\\":1528961232000}]\\\",\\\"codes\\\":1}\"','',0,'',327,'2018-06-14 17:35:29'),(19518,'LocationController.searchLocationTrack(..)','[\"18735666557\"]','\"{\\\"result\\\":[\\\"{\\\\\\\"name\\\\\\\":\\\\\\\"特体格tet\\\\\\\",\\\\\\\"imei\\\\\\\":\\\\\\\"1212\\\\\\\",\\\\\\\"id\\\\\\\":3,\\\\\\\"timestamp\\\\\\\":1528881645000}\\\",\\\"{\\\\\\\"name\\\\\\\":\\\\\\\"哈哈哈\\\\\\\",\\\\\\\"imei\\\\\\\":\\\\\\\"123123213\\\\\\\",\\\\\\\"id\\\\\\\":4,\\\\\\\"timestamp\\\\\\\":1528961232000}\\\"],\\\"codes\\\":1}\"','',0,'',253,'2018-06-14 17:36:34'),(19519,'LocationController.searchLocationTrack(..)','[\"18735666557\"]','\"{\\\"result\\\":[\\\"{\\\\\\\"name\\\\\\\":\\\\\\\"特体格tet\\\\\\\",\\\\\\\"imei\\\\\\\":\\\\\\\"1212\\\\\\\",\\\\\\\"id\\\\\\\":3,\\\\\\\"timestamp\\\\\\\":1528881645000}\\\",\\\"{\\\\\\\"name\\\\\\\":\\\\\\\"哈哈哈\\\\\\\",\\\\\\\"imei\\\\\\\":\\\\\\\"123123213\\\\\\\",\\\\\\\"id\\\\\\\":4,\\\\\\\"timestamp\\\\\\\":1528961232000}\\\"],\\\"codes\\\":1}\"','',0,'',249,'2018-06-14 17:44:02'),(19520,'LocationController.searchLocationTrack(..)','[\"18735666557\"]','\"{\\\"result\\\":[\\\"{\\\\\\\"name\\\\\\\":\\\\\\\"特体格tet\\\\\\\",\\\\\\\"imei\\\\\\\":\\\\\\\"1212\\\\\\\",\\\\\\\"id\\\\\\\":3,\\\\\\\"timestamp\\\\\\\":1528881645000}\\\",\\\"{\\\\\\\"name\\\\\\\":\\\\\\\"哈哈哈\\\\\\\",\\\\\\\"imei\\\\\\\":\\\\\\\"123123213\\\\\\\",\\\\\\\"id\\\\\\\":4,\\\\\\\"timestamp\\\\\\\":1528961232000}\\\"],\\\"codes\\\":1}\"','',0,'',2,'2018-06-14 17:44:04'),(19521,'LocationController.searchLocationTrack(..)','[\"18735666557\"]','\"{\\\"result\\\":[\\\"{\\\\\\\"name\\\\\\\":\\\\\\\"特体格tet\\\\\\\",\\\\\\\"imei\\\\\\\":\\\\\\\"1212\\\\\\\",\\\\\\\"id\\\\\\\":3,\\\\\\\"timestamp\\\\\\\":1528881645000}\\\",\\\"{\\\\\\\"name\\\\\\\":\\\\\\\"哈哈哈\\\\\\\",\\\\\\\"imei\\\\\\\":\\\\\\\"123123213\\\\\\\",\\\\\\\"id\\\\\\\":4,\\\\\\\"timestamp\\\\\\\":1528961232000}\\\"],\\\"codes\\\":1}\"','',0,'',1,'2018-06-14 17:44:08'),(19522,'LocationController.searchLocationTrack(..)','[\"18735666557\"]','\"{\\\"result\\\":[\\\"{name=特体格tet, imei=1212, id=3, timestamp=1528881645000}\\\",\\\"{name=哈哈哈, imei=123123213, id=4, timestamp=1528961232000}\\\"],\\\"codes\\\":1}\"','',0,'',250,'2018-06-14 17:48:27'),(19523,'LocationController.searchLocationTrack(..)','[\"18735666557\"]','\"{\\\"result\\\":[{\\\"name\\\":\\\"特体格tet\\\",\\\"imei\\\":\\\"1212\\\",\\\"id\\\":3,\\\"timestamp\\\":1528881645000},{\\\"name\\\":\\\"哈哈哈\\\",\\\"imei\\\":\\\"123123213\\\",\\\"id\\\":4,\\\"timestamp\\\":1528961232000}],\\\"codes\\\":1}\"','',0,'',243,'2018-06-14 17:55:02'),(19524,'LocationController.searchLocationTrack(..)','[\"18735666557\"]','\"{\\\"result\\\":[{\\\"name\\\":\\\"特体格tet\\\",\\\"imei\\\":\\\"1212\\\",\\\"id\\\":3,\\\"timestamp\\\":1528881645000},{\\\"name\\\":\\\"哈哈哈\\\",\\\"imei\\\":\\\"123123213\\\",\\\"id\\\":4,\\\"timestamp\\\":1528961232000}],\\\"codes\\\":1}\"','',0,'',254,'2018-06-14 18:04:57'),(19525,'LocationController.searchLocationTrack(..)','[\"18735666557\"]','\"{\\\"result\\\":\\\"[\\\\\\\"{\\\\\\\\\\\\\\\"name\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"特体格tet\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"imei\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"1212\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"id\\\\\\\\\\\\\\\":3,\\\\\\\\\\\\\\\"timestamp\\\\\\\\\\\\\\\":1528881645000}\\\\\\\",\\\\\\\"{\\\\\\\\\\\\\\\"name\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"哈哈哈\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"imei\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"123123213\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"id\\\\\\\\\\\\\\\":4,\\\\\\\\\\\\\\\"timestamp\\\\\\\\\\\\\\\":1528961232000}\\\\\\\"]\\\",\\\"codes\\\":1}\"','',0,'',260,'2018-06-14 18:06:06'),(19526,'LocationController.searchLocationTrack(..)','[\"18735666557\"]','\"{\\\"result\\\":\\\"[\\\\\\\"{\\\\\\\\\\\\\\\"name\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"特体格tet\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"imei\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"1212\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"id\\\\\\\\\\\\\\\":3,\\\\\\\\\\\\\\\"timestamp\\\\\\\\\\\\\\\":1528881645000}\\\\\\\",\\\\\\\"{\\\\\\\\\\\\\\\"name\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"哈哈哈\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"imei\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"123123213\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"id\\\\\\\\\\\\\\\":4,\\\\\\\\\\\\\\\"timestamp\\\\\\\\\\\\\\\":1528961232000}\\\\\\\"]\\\",\\\"codes\\\":1}\"','',0,'',257,'2018-06-14 18:07:09'),(19527,'LocationController.searchLocationTrack(..)','[\"18735666557\"]','\"{\\\"result\\\":\\\"[\\\\\\\"{\\\\\\\\\\\\\\\"name\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"特体格tet\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"imei\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"1212\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"id\\\\\\\\\\\\\\\":3,\\\\\\\\\\\\\\\"timestamp\\\\\\\\\\\\\\\":1528881645000}\\\\\\\",\\\\\\\"{\\\\\\\\\\\\\\\"name\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"哈哈哈\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"imei\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"123123213\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"id\\\\\\\\\\\\\\\":4,\\\\\\\\\\\\\\\"timestamp\\\\\\\\\\\\\\\":1528961232000}\\\\\\\"]\\\",\\\"codes\\\":1}\"','',0,'',249,'2018-06-14 18:08:38'),(19528,'LocationController.searchLocationTrack(..)','[\"18735666557\"]','\"{\\\"result\\\":\\\",{\\\\\\\"name\\\\\\\":\\\\\\\"特体格tet\\\\\\\",\\\\\\\"imei\\\\\\\":\\\\\\\"1212\\\\\\\",\\\\\\\"id\\\\\\\":3,\\\\\\\"timestamp\\\\\\\":1528881645000},{\\\\\\\"name\\\\\\\":\\\\\\\"哈哈哈\\\\\\\",\\\\\\\"imei\\\\\\\":\\\\\\\"123123213\\\\\\\",\\\\\\\"id\\\\\\\":4,\\\\\\\"timestamp\\\\\\\":1528961232000}\\\",\\\"codes\\\":1}\"','',0,'',268,'2018-06-14 18:13:09'),(19529,'LocationController.searchLocationTrack(..)','[\"18735666557\"]','\"{\\\"result\\\":\\\"com.bracelet.dto.HttpBaseDto@30748961\\\",\\\"codes\\\":1}\"','',0,'',272,'2018-06-14 18:17:03'),(19530,'LocationController.searchLocationTrack(..)','[\"18735666557\"]','\"{\\\"result\\\":\\\"[{\\\\\\\"name\\\\\\\":\\\\\\\"特体格tet\\\\\\\",\\\\\\\"imei\\\\\\\":\\\\\\\"1212\\\\\\\",\\\\\\\"id\\\\\\\":3,\\\\\\\"timestamp\\\\\\\":1528881645000},{\\\\\\\"name\\\\\\\":\\\\\\\"哈哈哈\\\\\\\",\\\\\\\"imei\\\\\\\":\\\\\\\"123123213\\\\\\\",\\\\\\\"id\\\\\\\":4,\\\\\\\"timestamp\\\\\\\":1528961232000}]\\\",\\\"codes\\\":1}\"','',0,'',279,'2018-06-14 18:18:21'),(19531,'LocationController.searchLocationTrack(..)','[\"18735666557\"]','\"{\\\"result\\\":[{\\\"name\\\":\\\"特体格tet\\\",\\\"imei\\\":\\\"1212\\\",\\\"id\\\":3,\\\"timestamp\\\":1528881645000},{\\\"name\\\":\\\"哈哈哈\\\",\\\"imei\\\":\\\"123123213\\\",\\\"id\\\":4,\\\"timestamp\\\":1528961232000}],\\\"codes\\\":1}\"','',0,'',239,'2018-06-14 18:20:39'),(19532,'LocationController.searchLocationTrack(..)','[\"18735666557\"]','\"{\\\"result\\\":[{\\\"name\\\":\\\"特体格tet\\\",\\\"imei\\\":\\\"1212\\\",\\\"id\\\":3,\\\"timestamp\\\":1528881645000},{\\\"name\\\":\\\"哈哈哈\\\",\\\"imei\\\":\\\"123123213\\\",\\\"id\\\":4,\\\"timestamp\\\":1528961232000}],\\\"codes\\\":1,\\\"name\\\":\\\"特体格tet哈哈哈\\\"}\"','',0,'',264,'2018-06-14 18:25:30'),(19533,'LocationController.searchLocationTrack(..)','[\"18735662247\"]','\"{\\\"result\\\":[],\\\"codes\\\":0}\"','',0,'',250,'2018-06-14 18:38:26'),(19534,'LocationController.searchLocationTrack(..)','[\"18735662247\"]','\"{\\\"result\\\":[],\\\"codes\\\":0}\"','',0,'',1,'2018-06-14 18:39:03'),(19535,'LocationController.searchLocationTrack(..)','[\"18735662247\"]','\"{\\\"result\\\":[],\\\"codes\\\":0}\"','',0,'',2,'2018-06-14 18:39:04'),(19536,'LocationController.searchLocationTrack(..)','[\"18735666557\"]','\"{\\\"result\\\":[{\\\"name\\\":\\\"特体格tet\\\",\\\"imei\\\":\\\"1212\\\",\\\"id\\\":3,\\\"timestamp\\\":1528881645000},{\\\"name\\\":\\\"哈哈哈\\\",\\\"imei\\\":\\\"123123213\\\",\\\"id\\\":4,\\\"timestamp\\\":1528961232000}],\\\"codes\\\":1}\"','',0,'',5,'2018-06-14 18:39:15'),(19537,'LocationController.searchLocationTrack(..)','[\"18735666557\"]','\"{\\\"result\\\":[{\\\"name\\\":\\\"特体格tet\\\",\\\"imei\\\":\\\"1212\\\",\\\"id\\\":3,\\\"timestamp\\\":1528881645000},{\\\"name\\\":\\\"哈哈哈\\\",\\\"imei\\\":\\\"123123213\\\",\\\"id\\\":4,\\\"timestamp\\\":1528961232000}],\\\"codes\\\":1}\"','',0,'',2,'2018-06-14 18:39:17'),(19538,'LocationController.searchLocationTrack(..)','[\"18735666557\"]','\"{\\\"result\\\":[{\\\"name\\\":\\\"特体格tet\\\",\\\"imei\\\":\\\"1212\\\",\\\"id\\\":3,\\\"timestamp\\\":1528881645000},{\\\"name\\\":\\\"哈哈哈\\\",\\\"imei\\\":\\\"123123213\\\",\\\"id\\\":4,\\\"timestamp\\\":1528961232000}],\\\"codes\\\":1}\"','',0,'',234,'2018-06-14 18:46:20'),(19539,'LocationController.searchLocationTrack(..)','[\"18735666557\"]','\"{\\\"result\\\":[{\\\"name\\\":\\\"特体格tet\\\",\\\"imei\\\":\\\"1212\\\",\\\"id\\\":3,\\\"timestamp\\\":1528881645000},{\\\"name\\\":\\\"哈哈哈\\\",\\\"imei\\\":\\\"123123213\\\",\\\"id\\\":4,\\\"timestamp\\\":1528961232000}],\\\"codes\\\":1}\"','',0,'',251,'2018-06-14 18:49:47'),(19540,'','afdsaf\r\nhfghdgfh','','',1,'syntax error, pos 1, json : afdsaf\r\nhfghdgfh',121,'2018-09-06 10:42:25'),(19541,'','asdfadhdsfgsdfgsdfgsdfgfsdgdfsg���Է��˹�','','',1,'syntax error, pos 1, json : asdfadhdsfgsdfgsdfgsdfgfsdgdfsg���Է��˹�',0,'2018-09-06 10:43:00'),(19542,'','fgsgd','','',1,'error parse false',1,'2018-09-06 10:43:19'),(19543,'','[YW*861900039990378*0001*0033*INIT,13256122653,0,D9_WZX_3KEY_V0.1.389.QGJ_V1.0,0002,2300,1','','',1,'syntax error, pos 2, json : [YW*861900039990378*0001*0033*INIT,13256122653,0,D9_WZX_3KEY_V0.1.389.QGJ_V1.0,0002,2300,1',1,'2018-09-06 10:43:46'),(19544,'com.bracelet.socket.business.impl.LoginService','[YW*861900039990378*0001*0033*INIT,13256122653,0,D9_WZX_3KEY_V0.1.389.QGJ_V1.0,0002,2300,1','','',0,'',1,'2018-09-08 15:38:07'),(19545,'com.bracelet.socket.business.impl.LoginService','[YW*861900039990378*0001*0033*INIT,13256122653,0,D9_WZX_3KEY_V0.1.389.QGJ_V1.0,0002,2300,1','[YW*8800000015*0001*0002*INIT,1]','',0,'',2,'2018-09-08 16:09:03'),(19546,'com.bracelet.socket.business.impl.HeartCheck','[YW*8800000015*0001*0002*LK,55','[YW*8800000015*0001*0002*LK ,2018-09-08 16:09:20]','861900039990378',0,'',1,'2018-09-08 16:09:21'),(19547,'com.bracelet.socket.business.impl.HeartCheck','[YW*8800000015*0001*0002*LK,55','[YW*8800000015*0001*0002*LK ,2018-09-08 16:09:33]','',0,'',0,'2018-09-08 16:09:33'),(19548,'com.bracelet.socket.business.impl.LoginService','[YW*861900039990378*0001*0033*INIT,13256122653,0,D9_WZX_3KEY_V0.1.389.QGJ_V1.0,0002,2300,1','[YW*8800000015*0001*0002*INIT,1]','',0,'',2,'2018-09-10 17:44:18'),(19549,'com.bracelet.socket.business.impl.HeartCheck','[YW*8800000015*0001*0002*LK,55','[YW*8800000015*0001*0002*LK ,2018-09-10 17:44:38]','861900039990378',0,'',63,'2018-09-10 17:44:39'),(19550,'com.bracelet.socket.business.impl.HeartCheck','[YW*8800000015*0001*0002*LK,�����ٷ���','未登录','',0,'',3,'2018-09-10 17:44:46'),(19551,'com.bracelet.socket.business.impl.LoginService','[YW*861900039990378*0001*0033*INIT,13256122653,0,D9_WZX_3KEY_V0.1.389.QGJ_V1.0,0002,2300,1','[YW*8800000015*0001*0002*INIT,1]','',0,'',1,'2018-09-11 14:23:42'),(19552,'com.bracelet.socket.business.impl.LoginService','[YW*861900039990378*0001*0033*INIT,13256122653,0,D9_WZX_3KEY_V0.1.389.QGJ_V1.0,0002,2300,1','[YW*8800000015*0001*0002*INIT,1]','',0,'',1,'2018-09-11 14:29:13'),(19553,'com.bracelet.socket.business.impl.LoginService','[YW*861900039990378*0001*0033*INIT,13256122653,0,D9_WZX_3KEY_V0.1.389.QGJ_V1.0,0002,2300,1','[YW*8800000015*0001*0002*INIT,1]','',0,'',1,'2018-09-11 14:31:01'),(19554,'com.bracelet.socket.business.impl.LoginService','[YW*861900039990378*0001*0033*INIT,13256122653,0,D9_WZX_3KEY_V0.1.389.QGJ_V1.0,0002,2300,1','[YW*8800000015*0001*0002*INIT,1]','',0,'',1,'2018-09-11 15:19:35'),(19555,'com.bracelet.socket.business.impl.HeartCheck','[YW*8800000015*0001*0002*LK,55','not init','',0,'',1,'2018-09-11 15:28:01'),(19556,'com.bracelet.socket.business.impl.HeartCheck','[YW*8800000015*0001*0002*LK,11','not init','',0,'',1,'2018-09-11 15:28:13'),(19557,'com.bracelet.socket.business.impl.HeartCheck','[YW*8800000015*0001*0002*LK,55','not init','',0,'',1,'2018-09-11 15:34:04'),(19558,'com.bracelet.socket.business.impl.LocationUdService','[YW*111111111111111*0002*008f*UD,220414,134652,A,22.571707,N,113.8613968,E,0.1,0.0,100,7,60,90,1000,50,0000,4,1,460,0,9360,4082,131,9360,4092,148,9360,4091,143,9360,4153,141','not init','',0,'',1,'2018-09-11 15:34:25'),(19559,'com.bracelet.socket.business.impl.LoginService','[YW*861900039990378*0001*0033*INIT,13256122653,0,D9_WZX_3KEY_V0.1.389.QGJ_V1.0,0002,2300,1','[YW*8800000015*0001*0002*INIT,1]','',0,'',1,'2018-09-11 15:35:31'),(19560,'com.bracelet.socket.business.impl.LocationUdService','[YW*111111111111111*0002*008f*UD,220414,134652,A,22.571707,N,113.8613968,E,0.1,0.0,100,7,60,90,1000,50,0000,4,1,460,0,9360,4082,131,9360,4092,148,9360,4091,143,9360,4153,141','[YW*8800000015*0001*0002*LK ,2016-01-21,00:52:18]','861900039990378',0,'',98,'2018-09-11 15:36:03'),(19561,'com.bracelet.socket.business.impl.LoginService','[YW*861900039990378*0001*0033*INIT,13256122653,0,D9_WZX_3KEY_V0.1.389.QGJ_V1.0,0002,2300,1','[YW*8800000015*0001*0002*INIT,1]','',0,'',14,'2018-09-12 14:58:20'),(19562,'com.bracelet.socket.business.impl.InsertFriendService','[YW*8800000015*0001*000E*MFD,1000000000','[YW*YYYYYYYYYY*NNNN*LEN* MFD,对方设备序列号,0]','861900039990378',0,'',14,'2018-09-12 14:58:40'),(19563,'com.bracelet.socket.business.impl.GetFriendService',' [YW*YYYYYYYYYY*NNNN*LEN*FDL','[YW*8800000015*0001*0002*FDL,0]','861900039990378',0,'',5,'2018-09-12 14:59:38'),(19564,'com.bracelet.socket.business.impl.LoginService','[YW*861900039990378*0001*0033*INIT,13256122653,0,D9_WZX_3KEY_V0.1.389.QGJ_V1.0,0002,2300,1','[YW*8800000015*0001*0002*INIT,1]','',0,'',1,'2018-09-12 15:02:31'),(19565,'com.bracelet.socket.business.impl.InsertFriendService','[YW*8800000015*0001*000E*MFD,1000000000','[YW*YYYYYYYYYY*NNNN*LEN* MFD,1000000000,1]','861900039990378',0,'',61,'2018-09-12 15:02:39'),(19566,'com.bracelet.socket.business.impl.GetFriendService',' [YW*YYYYYYYYYY*NNNN*LEN*FDL','[YW*8800000015*0001*0002*FDL,0]','861900039990378',0,'',3,'2018-09-12 15:03:56'),(19567,'com.bracelet.socket.business.impl.GetFriendService','[YW*YYYYYYYYYY*NNNN*LEN*FDL','[YW*8800000015*0001*0002*FDL,0]','861900039990378',0,'',2,'2018-09-12 15:04:23'),(19568,'com.bracelet.socket.business.impl.LoginService','[YW*861900039990378*0001*0033*INIT,13256122653,0,D9_WZX_3KEY_V0.1.389.QGJ_V1.0,0002,2300,1','[YW*8800000015*0001*0002*INIT,1]','',0,'',1,'2018-09-12 15:04:47'),(19569,'com.bracelet.socket.business.impl.LoginService','[YW*861900039990378*0001*0033*INIT,13256122653,0,D9_WZX_3KEY_V0.1.389.QGJ_V1.0,0002,2300,1','[YW*8800000015*0001*0002*INIT,1]','',0,'',0,'2018-09-12 15:06:51'),(19570,'com.bracelet.socket.business.impl.CostService','[YW*8800000015*0001*0087*COST2,10086,1','[YW*8800000015*0001*0002*COST2,1]','861900039990378',0,'',1,'2018-09-12 15:07:00'),(19571,'com.bracelet.socket.business.impl.LoginService','[YW*861900039990378*0001*0033*INIT,13256122653,0,D9_WZX_3KEY_V0.1.389.QGJ_V1.0,0002,2300,1','[YW*8800000015*0001*0002*INIT,1]','',0,'',1,'2018-09-12 15:11:37'),(19572,'com.bracelet.socket.business.impl.GetFriendService','[YW*8800000015*NNNN*LEN*FDL','[YW*8800000015*0001*0002*FDL,2,1000000000,a,187356622471000000001,b,18735662245]','861900039990378',0,'',9,'2018-09-12 15:12:04'),(19573,'com.bracelet.socket.business.impl.GetFriendService','[YW*8800000015*NNNN*LEN*FDL','[YW*8800000015*0001*0002*FDL,1,1000000000,a,18735662247]','861900039990378',0,'',4,'2018-09-12 15:12:52'),(19574,'com.bracelet.socket.business.impl.GetFriendService','[YW*8800000015*NNNN*LEN*FDL','[YW*8800000015*0001*0002*FDL,3,1000000000,a,187356622471000000001,b,187356622451000000000,a,18735662247]','861900039990378',0,'',6,'2018-09-12 15:13:27'),(19575,'com.bracelet.socket.business.impl.LoginService','[YW*861900039990378*0001*0033*INIT,13256122653,0,D9_WZX_3KEY_V0.1.389.QGJ_V1.0,0002,2300,1','[YW*8800000015*0001*0002*INIT,1]','',0,'',1,'2018-09-12 15:14:49'),(19576,'com.bracelet.socket.business.impl.GetFriendService','[YW*8800000015*NNNN*LEN*FDL','[YW*8800000015*0001*0002*FDL,3,1000000000,a,18735662247,1000000001,b,18735662245,1000000000,a,18735662247]','861900039990378',0,'',19,'2018-09-12 15:14:58'),(19577,'com.bracelet.socket.business.impl.LoginService','[YW*861900039990378*0001*0033*INIT,13256122653,0,D9_WZX_3KEY_V0.1.389.QGJ_V1.0,0002,2300,1','[YW*8800000015*0001*0002*INIT,1]','',0,'',1,'2018-09-12 16:20:44'),(19578,'com.bracelet.socket.business.impl.GetIpService','[YW*8800000015*0001*000E*IPREQ','[YW*YYYYYYYYYY*NNNN*LEN*IPREQ,2,127.0.0.1,5536,127.0.0.6,5956]','861900039990378',0,'',14,'2018-09-12 16:20:54'),(19579,'com.bracelet.socket.business.impl.LoginService','[YW*861900039990378*0001*0033*INIT,13256122653,0,D9_WZX_3KEY_V0.1.389.QGJ_V1.0,0002,2300,1','[YW*8800000015*0001*0002*INIT,1]','',0,'',1,'2018-09-12 16:52:54'),(19580,'com.bracelet.socket.business.impl.SmsToGetLocation','[YW*8800000015*0001*000E*DWREQ','','861900039990378',0,'',0,'2018-09-12 16:53:03'),(19581,'com.bracelet.socket.business.impl.LoginService','[YW*861900039990378*0001*0033*INIT,13256122653,0,D9_WZX_3KEY_V0.1.389.QGJ_V1.0,0002,2300,1','[YW*8800000015*0001*0002*INIT,1]','',0,'',1,'2018-09-12 18:19:58'),(19582,'com.bracelet.socket.business.impl.UploadPhoto','[YW*YYYYYYYYYY*NNNN*LEN*TPBK,1234567812,123.jpg,1,5','[YW*YYYYYYYYYY*NNNN*LEN*TPCF,123.jpg,5,1总包个数,1]','861900039990378',0,'',56,'2018-09-12 18:20:58'),(19583,'com.bracelet.socket.business.impl.LoginService','[YW*861900039990378*0001*0033*INIT,13256122653,0,D9_WZX_3KEY_V0.1.389.QGJ_V1.0,0002,2300,1','[YW*8800000015*0001*0002*INIT,1]','',0,'',1,'2018-09-12 18:45:25'),(19584,'com.bracelet.socket.business.impl.DownLoadFile','[YW*YYYYYYYYYY*0001*0004*FILE,4000','[YW*YYYYYYYYYY*NNNN*LEN*FILE,123.jpg,1,2,1234567812]','861900039990378',0,'',13,'2018-09-12 18:45:45'),(19585,'com.bracelet.socket.business.impl.LoginService','[YW*861900039990378*0001*0033*INIT,13256122653,0,D9_WZX_3KEY_V0.1.389.QGJ_V1.0,0002,2300,1','[YW*8800000015*0001*0002*INIT,1]','',0,'',1,'2018-09-13 18:31:25'),(19586,'WatchAppMakeDeviceUpdateVersionController.controllerVersion(..)','[\"861900039990378\"]','\"{\\\"codes\\\":1}\"','',0,'',37,'2018-09-13 18:32:08'),(19587,'WatchAppMakeDeviceUpdateVersionController.controllerVersion(..)','[\"861900039990378\"]','null','',1,'设备没有登录！',3,'2018-09-13 18:32:31');

/*Table structure for table `authcode` */

DROP TABLE IF EXISTS `authcode`;

CREATE TABLE `authcode` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tel` varchar(13) NOT NULL,
  `code` varchar(10) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

/*Data for the table `authcode` */

insert  into `authcode`(`id`,`tel`,`code`,`createtime`) values (1,'15989568358','9471','2017-12-05 16:32:55'),(2,'15989568358','9224','2017-12-05 16:33:25'),(3,'15989568358','6309','2017-12-05 16:33:45'),(4,'tel','7461','2017-12-05 16:35:32'),(5,'tel','7651','2017-12-05 16:35:38'),(6,'tel','8891','2017-12-05 16:36:08'),(7,'tel','7835','2017-12-05 16:36:34'),(8,'18735662247','4231','2017-12-05 16:44:11'),(9,'15989568358','6721','2017-12-05 16:45:46'),(10,'15989568358','8129','2017-12-05 16:45:55'),(11,'15989568358','4509','2017-12-05 16:46:50'),(12,'15989568358','6505','2017-12-05 16:47:00'),(13,'15989568358','4215','2017-12-05 17:04:15'),(14,'15989568358','4447','2017-12-05 17:23:46'),(15,'15989568358','9407','2017-12-05 17:27:22'),(16,'15989568358','1167','2017-12-05 17:28:14'),(17,'15989568358','8275','2017-12-06 09:12:32'),(18,'13267098103','5596','2017-12-06 09:14:41'),(19,'15989568358','6688','2017-12-06 13:53:16'),(20,'15989568358','6142','2017-12-06 14:14:57'),(21,'15989568358','9250','2017-12-06 15:37:29'),(22,'18735662247','3444','2017-12-06 16:34:34'),(23,'18735662247','3665','2017-12-07 14:48:20'),(24,'15989568358','9867','2017-12-07 15:51:42'),(25,'15989568358','0073','2017-12-07 16:13:07'),(26,'18735662247','0904','2017-12-08 18:41:25'),(27,'15989568358','1589','2017-12-11 09:54:07'),(28,'15989568358','3418','2017-12-11 11:39:49'),(29,'13267098103','5058','2017-12-15 18:11:54'),(30,'18118763557','8361','2017-12-20 11:03:00');

/*Table structure for table `bind_device` */

DROP TABLE IF EXISTS `bind_device`;

CREATE TABLE `bind_device` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `imei` varchar(128) DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '0不是管理员1是管理员',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `bind_device` */

insert  into `bind_device`(`id`,`user_id`,`imei`,`name`,`createtime`,`status`) values (2,1,'2','2','2017-12-11 09:55:17',0),(3,1,'3','3','2017-12-11 09:55:27',0),(4,1,'F9:71:6B:A8:62:55','罢了','2017-12-11 12:02:01',1),(5,1,'CA:39:D5:F9:4E:8C','推荐','2017-12-11 13:39:06',1),(6,1,'CA:39:D5:F9:4E:8C','铁路','2017-12-11 14:25:47',0),(7,1,'CA:39:D5:F9:4E:8C','哈','2017-12-11 17:01:54',0);

/*Table structure for table `blood_oxygen_info` */

DROP TABLE IF EXISTS `blood_oxygen_info`;

CREATE TABLE `blood_oxygen_info` (
  `bo_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `pulse_rate` int(10) NOT NULL COMMENT '脉率',
  `blood_oxygen` int(10) NOT NULL COMMENT '血氧',
  `user_id` int(11) unsigned NOT NULL,
  `upload_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`bo_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `blood_oxygen_info` */

insert  into `blood_oxygen_info`(`bo_id`,`pulse_rate`,`blood_oxygen`,`user_id`,`upload_time`) values (1,1,1,1,'2017-11-09 11:44:33');

/*Table structure for table `bloodfat_info` */

DROP TABLE IF EXISTS `bloodfat_info`;

CREATE TABLE `bloodfat_info` (
  `bi_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `blood_fat` int(10) NOT NULL COMMENT '血脂',
  `user_id` int(11) unsigned NOT NULL,
  `upload_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`bi_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `bloodfat_info` */

/*Table structure for table `bloodsugar_info` */

DROP TABLE IF EXISTS `bloodsugar_info`;

CREATE TABLE `bloodsugar_info` (
  `bs_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `blood_sugar` int(10) NOT NULL COMMENT '电压',
  `user_id` int(11) unsigned NOT NULL,
  `upload_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`bs_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

/*Data for the table `bloodsugar_info` */

insert  into `bloodsugar_info`(`bs_id`,`blood_sugar`,`user_id`,`upload_time`) values (1,1,1,'2017-11-09 10:29:50'),(2,1,1,'2017-11-09 10:29:58'),(3,33,1,'2017-11-09 10:30:04'),(4,23,2,'2017-11-09 10:30:09'),(5,3213,32,'2017-11-09 10:30:15'),(6,54,21,'2017-11-09 10:30:23'),(7,45,45,'2017-11-09 10:30:23'),(8,4,43,'2017-11-09 10:30:23'),(9,34,34,'2017-11-09 10:30:23'),(10,45,43,'2017-11-09 10:30:23'),(11,34,234,'2017-11-09 10:30:51'),(13,2,2,'2017-11-09 10:49:38'),(14,3,1,'2017-11-09 10:49:41');

/*Table structure for table `call_info` */

DROP TABLE IF EXISTS `call_info`;

CREATE TABLE `call_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `imei` varchar(128) DEFAULT NULL,
  `phone` varchar(128) NOT NULL,
  `name` varchar(128) DEFAULT NULL,
  `msg` text,
  `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

/*Data for the table `call_info` */

insert  into `call_info`(`id`,`user_id`,`imei`,`phone`,`name`,`msg`,`createtime`) values (1,22,'869758001213175','18735662247','特','OK','2017-10-26 18:11:11'),(2,22,'869758001213175','18735662247','特','OK','2017-10-26 18:13:34'),(3,22,'869758001213175','18735662247','特','OK','2017-10-26 18:34:58'),(4,22,'869758001213175','18735662247','特','OK','2017-10-26 18:36:33'),(5,18,'123456789012350','13475758210','jsksk','OK','2017-10-27 13:36:56'),(6,18,'123456789012350','18574407850','少男 啊','OK','2017-10-27 13:36:56'),(7,18,'123456789012350','18556559880','cgvb','OK','2017-10-27 13:36:56'),(8,18,'123456789012350','13475758210','jsksk','OK','2017-10-27 13:36:58'),(9,18,'123456789012350','18574407850','少男 啊','OK','2017-10-27 13:36:58'),(10,18,'123456789012350','18556559880','cgvb','OK','2017-10-27 13:36:58'),(11,18,'123456789012350','13475758210','jsksk','业务级流控,partnerId=100000013550102,prodId=11000000300006','2017-10-27 13:37:00'),(12,18,'123456789012350','18574407850','少男 啊','业务级流控,partnerId=100000013550102,prodId=11000000300006','2017-10-27 13:37:00'),(13,18,'123456789012350','18556559880','cgvb','业务级流控,partnerId=100000013550102,prodId=11000000300006','2017-10-27 13:37:00'),(14,18,'123456789012350','13475758210','jsksk','OK','2017-10-27 16:03:33'),(15,18,'123456789012350','18574407850','少男 啊','OK','2017-10-27 16:03:33'),(16,18,'123456789012350','18556559880','cgvb','OK','2017-10-27 16:03:33'),(17,18,'123456789012350','13475758210','jsksk','OK','2017-10-27 16:04:09'),(18,18,'123456789012350','18574407850','少男 啊','OK','2017-10-27 16:04:09'),(19,18,'123456789012350','18556559880','cgvb','OK','2017-10-27 16:04:09'),(20,18,'123456789012350','13475758210','jsksk','OK','2017-10-27 16:05:44'),(21,18,'123456789012350','18574407850','少男 啊','OK','2017-10-27 16:05:44'),(22,18,'123456789012350','18556559880','cgvb','OK','2017-10-27 16:05:44'),(23,18,'123456789012350','13475758210','jsksk','OK','2017-10-27 16:57:24'),(24,18,'123456789012350','18574407850','少男 啊','OK','2017-10-27 16:57:24'),(25,18,'123456789012350','18556559880','cgvb','OK','2017-10-27 16:57:24'),(26,22,'869758001213175','18735662247','特','OK','2017-10-30 10:59:51'),(27,22,'869758001213175','18735662247','特','OK','2017-10-30 11:02:03'),(28,22,'869758001213175','18735662247','特','OK','2017-10-30 12:06:46'),(29,22,'869758001213175','18735662247','特','OK','2017-10-31 17:12:31'),(30,16,'123456789012330','18511581610','李伟','OK','2017-11-06 17:11:09');

/*Table structure for table `conf` */

DROP TABLE IF EXISTS `conf`;

CREATE TABLE `conf` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `key` varchar(32) NOT NULL,
  `value` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `conf` */

/*Table structure for table `device_info` */

DROP TABLE IF EXISTS `device_info`;

CREATE TABLE `device_info` (
  `dev_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `dv` varchar(256) NOT NULL COMMENT '设备固件版本号',
  `sd` varchar(256) NOT NULL COMMENT '软件版本号',
  `user_id` int(11) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`dev_id`),
  UNIQUE KEY `unique_idx_dv` (`dv`(191)) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `device_info` */

/*Table structure for table `device_watch_info` */

DROP TABLE IF EXISTS `device_watch_info`;

CREATE TABLE `device_watch_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `imei` varchar(10) NOT NULL,
  `phone` varchar(11) NOT NULL,
  `nickname` varchar(256) DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `dv` varchar(256) DEFAULT NULL,
  `type` tinyint(2) NOT NULL COMMENT '1表示移动21表示联通、3表示电信,0xFF表示其他',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `device_watch_info` */

insert  into `device_watch_info`(`id`,`imei`,`phone`,`nickname`,`createtime`,`dv`,`type`) values (4,'1000000000','18735662247','a','2018-09-12 15:02:04',NULL,0),(5,'1000000001','18735662245','b','2018-09-12 15:03:45',NULL,0);

/*Table structure for table `feedback` */

DROP TABLE IF EXISTS `feedback`;

CREATE TABLE `feedback` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `content` text NOT NULL,
  `contact` varchar(20) DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `feedback` */

insert  into `feedback`(`id`,`user_id`,`content`,`contact`,`createtime`) values (1,16,'阿鲁兔子走路疼','18511581610','2017-10-19 23:04:13'),(2,15,'g哈喽','18516971225','2017-10-22 21:28:30'),(3,16,'测试测试号测试high','18511581610','2017-10-27 22:36:46');

/*Table structure for table `fence` */

DROP TABLE IF EXISTS `fence`;

CREATE TABLE `fence` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `lat` varchar(64) NOT NULL,
  `lng` varchar(64) NOT NULL,
  `radius` int(10) NOT NULL DEFAULT '0',
  `status` int(2) NOT NULL DEFAULT '0',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `fence` */

/*Table structure for table `fencelog` */

DROP TABLE IF EXISTS `fencelog`;

CREATE TABLE `fencelog` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `imei` varchar(128) DEFAULT NULL,
  `lat` varchar(64) NOT NULL,
  `lng` varchar(64) NOT NULL,
  `radius` int(10) NOT NULL DEFAULT '0',
  `lat1` varchar(64) NOT NULL,
  `lng1` varchar(64) NOT NULL,
  `status` int(2) NOT NULL DEFAULT '0',
  `content` varchar(128) DEFAULT NULL,
  `upload_time` timestamp NULL DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `fencelog` */

insert  into `fencelog`(`id`,`user_id`,`imei`,`lat`,`lng`,`radius`,`lat1`,`lng1`,`status`,`content`,`upload_time`,`createtime`) values (1,15,'123456789012380','39.940738','116.304121',500,'22.50036','113.9221072',2,'设备离开电子围栏','2017-09-28 15:15:02','2017-09-28 15:15:06'),(2,27,'869758001213399','22.54489234628956','114.11974981427193',1000,'22.4992849','113.9205553',2,'设备离开电子围栏','2017-10-10 14:46:02','2017-10-10 10:46:03'),(3,28,'869758001213282','22.52730534895971','114.06079620122911',2000,'22.500392','113.9221053',2,'设备离开电子围栏','2017-10-18 23:51:28','2017-10-18 15:51:42'),(4,18,'869758001213175','39.613848','116.647982',2000,'22.500392','113.9221053',2,'设备离开电子围栏','2017-10-25 02:12:02','2017-10-24 18:12:03'),(5,16,'123456789012330','39.930799792366706','116.40475183725358',2000,'22.500392','113.9221053',2,'设备离开电子围栏','2017-11-06 21:08:52','2017-11-06 17:11:08');

/*Table structure for table `finger_info` */

DROP TABLE IF EXISTS `finger_info`;

CREATE TABLE `finger_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `fing_id` int(11) NOT NULL,
  `imei` varchar(128) NOT NULL,
  `type` int(1) DEFAULT '0',
  `createtime` timestamp NOT NULL DEFAULT '2017-01-01 01:01:00',
  `updatetime` timestamp NOT NULL DEFAULT '2017-01-01 01:01:00',
  `name` varchar(128) DEFAULT 'name',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `finger_info` */

/*Table structure for table `heart_pressure` */

DROP TABLE IF EXISTS `heart_pressure`;

CREATE TABLE `heart_pressure` (
  `hp_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `max_heart_pressure` int(10) NOT NULL COMMENT '高压',
  `min_heart_pressure` int(10) NOT NULL COMMENT '低压',
  `user_id` int(11) unsigned NOT NULL,
  `upload_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `imei` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`hp_id`)
) ENGINE=InnoDB AUTO_INCREMENT=282 DEFAULT CHARSET=utf8;

/*Data for the table `heart_pressure` */

insert  into `heart_pressure`(`hp_id`,`max_heart_pressure`,`min_heart_pressure`,`user_id`,`upload_time`,`imei`) values (1,100,120,1,'2017-08-24 14:25:38',NULL),(2,22,333,1,'2017-08-31 23:44:13',NULL),(3,120,78,1,'2017-09-03 23:42:46',NULL),(4,121,78,1,'2017-09-03 23:43:08',NULL),(5,120,78,1,'2017-09-03 23:56:37',NULL),(6,120,78,1,'2017-09-03 23:57:12',NULL),(7,120,78,1,'2017-09-03 23:57:49',NULL),(8,127,78,1,'2017-09-03 23:58:10',NULL),(9,127,78,1,'2017-09-04 00:02:03',NULL),(10,127,78,1,'2017-09-04 00:10:07',NULL),(11,127,78,1,'2017-09-04 00:16:21',NULL),(12,127,78,1,'2017-09-04 00:18:50',NULL),(13,126,78,1,'2017-09-04 00:18:56',NULL),(14,126,78,1,'2017-09-04 00:26:53',NULL),(15,123,67,1,'2017-09-04 06:37:17',NULL),(16,123,69,1,'2017-09-04 06:40:06',NULL),(17,123,69,1,'2017-09-04 06:54:04',NULL),(18,123,69,1,'2017-09-04 07:02:45',NULL),(19,123,69,1,'2017-09-04 07:05:23',NULL),(20,123,69,1,'2017-09-04 11:08:47',NULL),(21,123,69,1,'2017-09-04 11:22:59',NULL),(22,123,69,1,'2017-09-04 11:24:43',NULL),(23,123,90,1,'2017-09-04 11:24:51',NULL),(24,123,69,1,'2017-09-04 12:20:53',NULL),(25,123,69,1,'2017-09-04 12:21:27',NULL),(26,112,75,1,'2017-09-04 13:23:48',NULL),(27,0,0,1,'2017-09-04 13:28:04',NULL),(28,0,0,1,'2017-09-04 13:29:04',NULL),(29,0,0,1,'2017-09-04 13:29:44',NULL),(30,0,0,1,'2017-09-04 13:29:48',NULL),(31,0,0,1,'2017-09-04 13:30:37',NULL),(32,0,0,1,'2017-09-04 13:32:38',NULL),(33,0,0,1,'2017-09-04 13:33:57',NULL),(34,120,80,1,'2017-09-04 13:35:34',NULL),(35,120,80,1,'2017-09-04 13:35:55',NULL),(36,125,87,1,'2017-09-04 20:35:06',NULL),(37,130,76,1,'2017-09-04 20:36:40',NULL),(38,200,100,1,'2017-09-05 17:09:26',NULL),(39,200,100,1,'2017-09-05 20:49:02',NULL),(40,200,100,1,'2017-09-05 20:53:41',NULL),(41,200,100,1,'2017-09-05 21:07:15',NULL),(42,200,100,1,'2017-09-05 21:23:19',NULL),(43,200,100,1,'2017-09-05 21:30:54',NULL),(44,200,100,1,'2017-09-05 22:28:57',NULL),(45,200,100,1,'2017-09-05 22:31:36',NULL),(46,200,100,1,'2017-09-05 22:33:37',NULL),(47,200,100,1,'2017-09-05 22:42:28',NULL),(48,200,100,1,'2017-09-05 22:49:20',NULL),(49,200,100,1,'2017-09-05 22:57:45',NULL),(50,200,100,1,'2017-09-06 09:14:14',NULL),(51,200,100,1,'2017-09-06 10:01:23',NULL),(52,200,100,1,'2017-09-06 10:06:15',NULL),(53,200,100,1,'2017-09-06 10:21:35',NULL),(54,200,100,1,'2017-09-06 10:34:20',NULL),(55,200,100,1,'2017-09-06 10:45:00',NULL),(56,200,100,1,'2017-09-06 10:47:29',NULL),(57,200,100,1,'2017-09-06 10:48:33',NULL),(58,200,100,1,'2017-09-06 11:00:05',NULL),(59,200,100,1,'2017-09-06 11:21:58',NULL),(60,200,100,1,'2017-09-06 11:50:33',NULL),(61,200,100,1,'2017-09-06 13:49:54',NULL),(62,200,100,1,'2017-09-06 13:52:08',NULL),(63,200,100,1,'2017-09-06 15:34:57',NULL),(64,200,100,1,'2017-09-06 15:42:01',NULL),(65,200,100,1,'2017-09-06 15:47:59',NULL),(66,200,100,1,'2017-09-06 15:52:51',NULL),(67,200,100,1,'2017-09-06 15:54:09',NULL),(68,200,100,1,'2017-09-06 15:56:53',NULL),(69,200,100,1,'2017-09-06 15:59:06',NULL),(70,200,100,1,'2017-09-06 16:02:27',NULL),(71,200,100,1,'2017-09-06 16:07:03',NULL),(72,200,100,1,'2017-09-06 16:08:00',NULL),(73,200,100,1,'2017-09-06 16:09:05',NULL),(74,200,100,1,'2017-09-06 16:21:51',NULL),(75,200,100,1,'2017-09-06 16:23:36',NULL),(76,200,100,1,'2017-09-06 16:36:25',NULL),(77,200,100,1,'2017-09-06 16:44:22',NULL),(78,200,100,1,'2017-09-06 16:45:56',NULL),(79,200,100,1,'2017-09-06 17:00:31',NULL),(80,200,100,1,'2017-09-06 17:41:33',NULL),(81,200,100,1,'2017-09-06 18:01:36',NULL),(82,100,79,4,'2017-09-06 19:53:43',NULL),(83,104,76,4,'2017-09-06 19:55:21',NULL),(84,200,100,4,'2017-09-06 21:12:19',NULL),(85,200,100,1,'2017-09-06 21:20:44',NULL),(86,134,84,3,'2017-09-06 22:22:01',NULL),(87,125,81,2,'2017-09-06 22:25:09',NULL),(88,200,100,1,'2017-09-07 09:41:35',NULL),(89,200,100,4,'2017-09-07 09:52:33',NULL),(90,103,77,4,'2017-09-07 09:54:08',NULL),(91,200,100,1,'2017-09-07 10:04:31',NULL),(92,200,100,1,'2017-09-07 10:05:36',NULL),(93,200,100,1,'2017-09-07 10:37:32',NULL),(94,200,100,1,'2017-09-07 11:41:58',NULL),(95,200,100,1,'2017-09-07 11:58:31',NULL),(96,200,100,1,'2017-09-07 13:45:20',NULL),(97,200,100,1,'2017-09-07 13:49:37',NULL),(98,200,100,1,'2017-09-07 13:51:39',NULL),(99,200,100,1,'2017-09-07 13:53:03',NULL),(100,200,100,1,'2017-09-07 15:15:01',NULL),(101,200,100,1,'2017-09-07 15:16:47',NULL),(102,200,100,1,'2017-09-07 15:22:07',NULL),(103,200,100,1,'2017-09-07 15:50:40',NULL),(104,200,100,1,'2017-09-07 16:13:16',NULL),(105,200,100,1,'2017-09-07 16:14:34',NULL),(106,200,100,1,'2017-09-07 16:23:14',NULL),(107,200,100,1,'2017-09-07 16:24:47',NULL),(108,200,100,1,'2017-09-07 16:39:19',NULL),(109,200,100,1,'2017-09-07 17:27:37',NULL),(110,200,100,1,'2017-09-08 10:05:42',NULL),(111,200,100,1,'2017-09-08 10:45:26',NULL),(112,200,100,1,'2017-09-08 10:59:41',NULL),(113,200,100,1,'2017-09-08 11:05:02',NULL),(114,200,100,1,'2017-09-08 11:57:09',NULL),(115,200,100,1,'2017-09-08 12:25:00',NULL),(116,200,100,1,'2017-09-08 15:00:48',NULL),(117,200,100,1,'2017-09-08 15:19:25',NULL),(118,200,100,1,'2017-09-08 15:26:02',NULL),(119,200,100,1,'2017-09-08 15:40:10',NULL),(120,200,100,1,'2017-09-08 15:49:19',NULL),(121,200,100,1,'2017-09-08 16:15:50',NULL),(122,200,100,1,'2017-09-08 16:33:20',NULL),(123,200,100,1,'2017-09-08 16:44:06',NULL),(124,200,100,1,'2017-09-08 16:57:50',NULL),(125,200,100,1,'2017-09-08 16:58:38',NULL),(126,200,100,1,'2017-09-08 17:14:22',NULL),(127,200,100,1,'2017-09-08 17:31:01',NULL),(128,200,100,1,'2017-09-08 17:36:42',NULL),(129,200,100,1,'2017-09-08 17:45:01',NULL),(130,200,100,1,'2017-09-09 10:20:04',NULL),(131,200,100,1,'2017-09-09 12:12:00',NULL),(132,200,100,1,'2017-09-09 14:10:14',NULL),(133,200,100,1,'2017-09-09 14:11:40',NULL),(134,200,100,1,'2017-09-09 14:17:46',NULL),(135,200,100,1,'2017-09-11 09:43:20',NULL),(136,200,100,1,'2017-09-11 09:44:50',NULL),(137,200,100,1,'2017-09-11 09:46:03',NULL),(138,200,100,1,'2017-09-11 09:50:39',NULL),(139,200,100,1,'2017-09-11 09:58:25',NULL),(140,200,100,1,'2017-09-11 10:01:09',NULL),(141,200,100,1,'2017-09-11 10:20:27',NULL),(142,200,100,1,'2017-09-11 10:25:54',NULL),(143,200,100,1,'2017-09-11 14:09:04',NULL),(144,200,100,1,'2017-09-11 14:15:58',NULL),(145,200,100,1,'2017-09-11 14:24:36',NULL),(146,200,100,1,'2017-09-11 14:42:38',NULL),(147,200,100,1,'2017-09-11 14:43:30',NULL),(148,200,100,1,'2017-09-11 14:45:51',NULL),(149,200,100,1,'2017-09-11 15:05:02',NULL),(150,200,100,1,'2017-09-11 15:32:28',NULL),(151,200,100,1,'2017-09-11 15:54:15',NULL),(152,200,100,1,'2017-09-11 16:37:23',NULL),(153,200,100,1,'2017-09-11 16:42:38',NULL),(154,200,100,1,'2017-09-11 16:48:54',NULL),(155,200,100,1,'2017-09-11 17:02:30',NULL),(156,200,100,1,'2017-09-11 17:21:07',NULL),(157,200,100,1,'2017-09-11 17:33:42',NULL),(158,200,100,1,'2017-09-11 17:36:41',NULL),(159,200,100,1,'2017-09-11 17:58:50',NULL),(160,200,100,1,'2017-09-11 18:07:23',NULL),(161,200,100,1,'2017-09-11 18:26:13',NULL),(162,200,100,1,'2017-09-11 19:35:47',NULL),(163,200,100,1,'2017-09-11 20:01:38',NULL),(164,200,100,1,'2017-09-11 20:08:53',NULL),(165,200,100,1,'2017-09-11 20:39:12',NULL),(166,200,100,1,'2017-09-11 21:11:34',NULL),(167,200,100,1,'2017-09-11 21:39:15',NULL),(168,200,100,1,'2017-09-12 09:28:13',NULL),(169,200,100,1,'2017-09-12 09:35:24',NULL),(170,200,100,1,'2017-09-12 09:40:45',NULL),(171,200,100,1,'2017-09-12 10:08:04',NULL),(172,200,100,1,'2017-09-12 10:12:05',NULL),(173,200,100,1,'2017-09-12 10:24:46',NULL),(174,200,100,1,'2017-09-12 10:45:16',NULL),(175,200,100,1,'2017-09-12 16:13:02',NULL),(176,200,100,1,'2017-09-12 16:47:40',NULL),(177,200,100,1,'2017-09-12 17:07:42',NULL),(178,200,100,1,'2017-09-12 17:15:01',NULL),(179,200,100,1,'2017-09-12 17:29:49',NULL),(180,200,100,1,'2017-09-12 17:35:16',NULL),(181,200,100,1,'2017-09-12 17:52:46',NULL),(182,200,100,1,'2017-09-12 19:20:58',NULL),(183,200,100,1,'2017-09-12 19:37:18',NULL),(184,200,100,1,'2017-09-12 19:44:00',NULL),(185,200,100,1,'2017-09-12 19:53:12',NULL),(186,200,100,1,'2017-09-12 19:57:54',NULL),(187,200,100,1,'2017-09-12 20:01:55',NULL),(188,200,100,1,'2017-09-12 20:33:04',NULL),(189,200,100,1,'2017-09-12 20:36:20',NULL),(190,200,100,1,'2017-09-12 20:37:10',NULL),(191,200,100,1,'2017-09-12 20:43:16',NULL),(192,200,100,1,'2017-09-12 20:50:35',NULL),(193,200,100,1,'2017-09-12 21:03:57',NULL),(194,200,100,1,'2017-09-12 21:08:03',NULL),(195,200,100,1,'2017-09-13 09:23:56',NULL),(196,200,100,1,'2017-09-13 09:25:38',NULL),(197,200,100,1,'2017-09-13 09:29:42',NULL),(198,200,100,1,'2017-09-13 10:05:39',NULL),(199,200,100,1,'2017-09-13 10:09:17',NULL),(200,200,100,1,'2017-09-13 10:59:20',NULL),(201,200,100,1,'2017-09-13 11:19:22',NULL),(202,200,100,1,'2017-09-13 11:31:42',NULL),(203,200,100,1,'2017-09-13 11:38:53',NULL),(204,200,100,1,'2017-09-13 15:13:57',NULL),(205,200,100,1,'2017-09-13 15:46:44',NULL),(206,125,72,3,'2017-09-13 21:01:09',NULL),(207,200,100,1,'2017-09-14 09:57:50',NULL),(208,200,100,1,'2017-09-14 15:45:50',NULL),(209,200,100,1,'2017-09-14 15:49:48',NULL),(210,200,100,1,'2017-09-14 15:56:08',NULL),(211,200,100,1,'2017-09-14 15:58:27',NULL),(212,200,100,1,'2017-09-14 16:40:10',NULL),(213,200,100,1,'2017-09-14 17:05:03',NULL),(214,200,100,1,'2017-09-14 17:30:51',NULL),(215,200,100,1,'2017-09-14 17:52:44',NULL),(216,200,100,1,'2017-09-15 09:54:58',NULL),(217,200,100,1,'2017-09-15 10:10:14',NULL),(218,200,100,1,'2017-09-15 10:30:49',NULL),(219,200,100,1,'2017-09-15 10:59:28',NULL),(220,200,100,1,'2017-09-15 11:59:17',NULL),(221,200,100,1,'2017-09-15 13:41:31',NULL),(222,200,100,1,'2017-09-15 13:48:58',NULL),(223,200,100,1,'2017-09-15 14:01:11',NULL),(224,200,100,1,'2017-09-15 14:22:00',NULL),(225,200,100,1,'2017-09-15 14:40:01',NULL),(226,200,100,1,'2017-09-15 14:46:56',NULL),(227,200,100,1,'2017-09-15 15:49:50',NULL),(228,200,100,1,'2017-09-15 17:35:52',NULL),(229,200,100,1,'2017-09-15 17:44:11',NULL),(230,200,100,1,'2017-09-15 18:07:44',NULL),(231,200,100,1,'2017-09-15 23:37:59',NULL),(232,200,100,1,'2017-09-15 19:52:13',NULL),(233,200,100,1,'2017-09-15 16:28:17',NULL),(234,200,100,1,'2017-09-16 00:37:51',NULL),(235,200,100,1,'2017-09-16 01:02:00',NULL),(236,200,100,1,'2017-09-16 10:43:45',NULL),(237,200,100,1,'2017-09-16 11:06:30',NULL),(238,200,100,1,'2017-09-16 11:10:28',NULL),(239,200,100,1,'2017-09-16 11:19:06',NULL),(240,200,100,1,'2017-09-16 11:24:49',NULL),(241,200,100,1,'2017-09-16 11:31:47',NULL),(242,200,100,1,'2017-09-16 11:40:56',NULL),(243,120,80,3,'2017-09-17 15:19:32',NULL),(244,120,80,3,'2017-09-17 15:22:35',NULL),(245,120,80,3,'2017-09-17 15:23:01',NULL),(246,120,80,3,'2017-09-17 15:24:04',NULL),(247,120,80,3,'2017-09-17 15:25:00',NULL),(248,120,80,3,'2017-09-17 15:25:23',NULL),(249,120,80,3,'2017-09-17 15:25:25',NULL),(250,120,80,3,'2017-09-17 15:27:27',NULL),(251,120,90,3,'2017-09-17 15:27:40',NULL),(252,120,98,3,'2017-09-17 15:27:48',NULL),(253,120,98,3,'2017-09-17 15:28:23',NULL),(254,120,98,3,'2017-09-17 15:28:27',NULL),(255,120,98,3,'2017-09-17 15:28:45',NULL),(256,200,100,1,'2017-09-19 17:15:12',NULL),(257,200,100,1,'2017-09-20 11:03:43',NULL),(258,200,100,1,'2017-09-20 11:13:37',NULL),(259,200,100,1,'2017-09-21 09:17:59',NULL),(260,200,100,1,'2017-09-21 09:22:58',NULL),(261,200,100,1,'2017-09-21 09:32:55',NULL),(262,200,100,1,'2017-09-21 09:41:02',NULL),(263,200,100,1,'2017-09-21 09:58:02',NULL),(264,200,100,1,'2017-09-21 10:17:26',NULL),(265,200,100,1,'2017-09-21 10:21:27',NULL),(266,200,100,1,'2017-09-21 10:52:06',NULL),(267,200,100,1,'2017-09-21 11:00:01',NULL),(268,200,100,1,'2017-09-21 11:29:03',NULL),(269,200,100,1,'2017-09-21 13:53:15',NULL),(270,200,100,1,'2017-09-21 14:04:29',NULL),(271,200,100,1,'2017-09-21 15:57:50',NULL),(272,200,100,1,'2017-09-21 16:05:39',NULL),(273,200,100,1,'2017-09-21 16:23:31',NULL),(274,200,100,1,'2017-09-21 16:25:59',NULL),(275,200,100,1,'2017-09-21 16:38:24',NULL),(276,200,100,1,'2017-09-21 18:12:26',NULL),(277,200,100,1,'2017-09-21 19:30:17',NULL),(278,200,100,1,'2017-09-21 19:49:36',NULL),(279,200,100,1,'2017-09-21 19:53:46',NULL),(280,200,100,1,'2017-09-21 20:12:49',NULL),(281,200,100,1,'2017-09-21 20:16:48',NULL);

/*Table structure for table `heart_rate` */

DROP TABLE IF EXISTS `heart_rate`;

CREATE TABLE `heart_rate` (
  `hr_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `heart_rate` int(10) NOT NULL,
  `user_id` int(11) NOT NULL,
  `upload_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `imei` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`hr_id`)
) ENGINE=InnoDB AUTO_INCREMENT=388 DEFAULT CHARSET=utf8;

/*Data for the table `heart_rate` */

insert  into `heart_rate`(`hr_id`,`heart_rate`,`user_id`,`upload_time`,`imei`) values (1,87,1,'2017-08-27 18:25:59',NULL),(2,67,1,'2017-08-27 18:26:46',NULL),(3,100,1,'1970-01-18 16:58:43',NULL),(4,100,1,'1970-01-18 16:58:43',NULL),(5,100,1,'1970-01-18 16:58:43',NULL),(6,100,1,'1970-01-18 16:58:43',NULL),(7,100,1,'1970-01-18 16:58:43',NULL),(8,100,1,'1970-01-18 16:58:43',NULL),(9,100,1,'1970-01-18 16:58:43',NULL),(10,100,1,'1970-01-18 16:58:43',NULL),(11,100,1,'1970-01-18 16:58:43',NULL),(12,100,1,'1970-01-18 16:58:43',NULL),(13,100,1,'1970-01-18 16:58:43',NULL),(14,100,1,'1970-01-18 16:58:43',NULL),(15,100,1,'1970-01-18 16:58:43',NULL),(16,100,1,'1970-01-18 16:58:43',NULL),(17,100,1,'1970-01-18 16:58:43',NULL),(18,100,1,'1970-01-18 16:58:43',NULL),(19,100,1,'1970-01-18 16:58:43',NULL),(20,100,1,'1970-01-18 16:58:43',NULL),(21,100,1,'1970-01-18 16:58:43',NULL),(22,100,1,'1970-01-18 16:58:43',NULL),(23,100,1,'1970-01-18 16:58:43',NULL),(24,100,1,'1970-01-18 16:58:43',NULL),(25,100,1,'1970-01-18 16:58:43',NULL),(26,100,1,'1970-01-18 16:58:43',NULL),(27,100,1,'1970-01-18 16:58:43',NULL),(28,100,1,'1970-01-18 16:58:43',NULL),(29,100,1,'1970-01-18 16:58:43',NULL),(30,100,1,'1970-01-18 16:58:43',NULL),(31,100,1,'1970-01-18 16:58:43',NULL),(32,100,1,'1970-01-18 16:58:43',NULL),(33,100,1,'1970-01-18 16:58:43',NULL),(34,100,1,'1970-01-18 16:58:43',NULL),(35,100,1,'1970-01-18 16:58:43',NULL),(36,100,1,'1970-01-18 16:58:43',NULL),(37,100,1,'1970-01-18 16:58:43',NULL),(38,100,1,'1970-01-18 16:58:43',NULL),(39,100,1,'1970-01-18 16:58:43',NULL),(40,100,1,'1970-01-18 16:58:43',NULL),(41,100,1,'1970-01-18 16:58:43',NULL),(42,100,1,'1970-01-18 16:58:43',NULL),(43,100,1,'1970-01-18 16:58:43',NULL),(44,100,1,'1970-01-18 16:58:43',NULL),(45,100,1,'1970-01-18 16:58:43',NULL),(46,100,1,'1970-01-18 16:58:43',NULL),(47,100,1,'1970-01-18 16:58:43',NULL),(48,100,1,'1970-01-18 16:58:43',NULL),(49,100,1,'1970-01-18 16:58:43',NULL),(50,100,1,'1970-01-18 16:58:43',NULL),(51,100,1,'1970-01-18 16:58:43',NULL),(52,100,1,'1970-01-18 16:58:43',NULL),(53,100,1,'1970-01-18 16:58:43',NULL),(54,100,1,'1970-01-18 16:58:43',NULL),(55,100,1,'1970-01-18 16:58:43',NULL),(56,100,1,'1970-01-18 16:58:43',NULL),(57,100,1,'1970-01-18 16:58:43',NULL),(58,100,1,'1970-01-18 16:58:43',NULL),(59,100,1,'1970-01-18 16:58:43',NULL),(60,100,1,'1970-01-18 16:58:43',NULL),(61,100,1,'1970-01-18 16:58:43',NULL),(62,100,1,'1970-01-18 16:58:43',NULL),(63,100,1,'1970-01-18 16:58:43',NULL),(64,100,1,'1970-01-18 16:58:43',NULL),(65,100,1,'1970-01-18 16:58:43',NULL),(66,100,1,'1970-01-18 16:58:43',NULL),(67,100,1,'1970-01-18 16:58:43',NULL),(68,100,1,'1970-01-18 16:58:43',NULL),(69,100,1,'1970-01-18 16:58:43',NULL),(70,100,1,'1970-01-18 16:58:43',NULL),(71,100,1,'1970-01-18 16:58:43',NULL),(72,100,1,'1970-01-18 16:58:43',NULL),(73,100,1,'1970-01-18 16:58:43',NULL),(74,100,1,'1970-01-18 16:58:43',NULL),(75,100,1,'1970-01-18 16:58:43',NULL),(76,100,1,'1970-01-18 16:58:43',NULL),(77,100,1,'1970-01-18 16:58:43',NULL),(78,100,1,'1970-01-18 16:58:43',NULL),(79,100,1,'1970-01-18 16:58:43',NULL),(80,100,1,'1970-01-18 16:58:43',NULL),(81,100,1,'1970-01-18 16:58:43',NULL),(82,100,1,'1970-01-18 16:58:43',NULL),(83,100,1,'1970-01-18 16:58:43',NULL),(84,100,1,'1970-01-18 16:58:43',NULL),(85,100,1,'1970-01-18 16:58:43',NULL),(86,100,1,'1970-01-18 16:58:43',NULL),(87,100,1,'1970-01-18 16:58:43',NULL),(88,100,1,'1970-01-18 16:58:43',NULL),(89,100,1,'1970-01-18 16:58:43',NULL),(90,100,1,'1970-01-18 16:58:43',NULL),(91,100,1,'1970-01-18 16:58:43',NULL),(92,100,1,'1970-01-18 16:58:43',NULL),(93,100,1,'1970-01-18 16:58:43',NULL),(94,100,1,'1970-01-18 16:58:43',NULL),(95,100,1,'1970-01-18 16:58:43',NULL),(96,100,1,'1970-01-18 16:58:43',NULL),(97,100,1,'1970-01-18 16:58:43',NULL),(98,100,1,'1970-01-18 16:58:43',NULL),(99,100,1,'1970-01-18 16:58:43',NULL),(100,100,1,'1970-01-18 16:58:43',NULL),(101,100,1,'1970-01-18 16:58:43',NULL),(102,100,1,'1970-01-18 16:58:43',NULL),(103,100,1,'1970-01-18 16:58:43',NULL),(104,100,1,'1970-01-18 16:58:43',NULL),(105,100,1,'1970-01-18 16:58:43',NULL),(106,100,1,'1970-01-18 16:58:43',NULL),(107,100,1,'1970-01-18 16:58:43',NULL),(108,100,1,'1970-01-18 16:58:43',NULL),(109,100,1,'1970-01-18 16:58:43',NULL),(110,100,1,'1970-01-18 16:58:43',NULL),(111,100,1,'1970-01-18 16:58:43',NULL),(112,100,1,'1970-01-18 16:58:43',NULL),(113,100,1,'1970-01-18 16:58:43',NULL),(114,100,1,'1970-01-18 16:58:43',NULL),(115,100,1,'1970-01-18 16:58:43',NULL),(116,100,1,'1970-01-18 16:58:43',NULL),(117,100,1,'1970-01-18 16:58:43',NULL),(118,100,1,'1970-01-18 16:58:43',NULL),(119,100,1,'1970-01-18 16:58:43',NULL),(120,100,1,'1970-01-18 16:58:43',NULL),(121,100,1,'1970-01-18 16:58:43',NULL),(122,100,1,'1970-01-18 16:58:43',NULL),(123,100,1,'1970-01-18 16:58:43',NULL),(124,100,1,'1970-01-18 16:58:43',NULL),(125,100,1,'1970-01-18 16:58:43',NULL),(126,100,1,'1970-01-18 16:58:43',NULL),(127,79,1,'2017-09-03 17:29:34',NULL),(128,79,1,'2017-09-03 17:31:56',NULL),(129,20,1,'2017-09-03 20:05:32',NULL),(130,110,1,'2017-09-03 20:05:50',NULL),(131,78,1,'2017-09-03 20:06:25',NULL),(132,78,1,'2017-09-03 20:24:16',NULL),(133,79,1,'2017-09-03 21:06:29',NULL),(134,79,1,'2017-09-03 23:42:46',NULL),(135,79,1,'2017-09-03 23:43:08',NULL),(136,76,1,'2017-09-03 23:56:37',NULL),(137,73,1,'2017-09-03 23:57:12',NULL),(138,75,1,'2017-09-03 23:57:49',NULL),(139,75,1,'2017-09-03 23:58:10',NULL),(140,75,1,'2017-09-04 00:02:03',NULL),(141,75,1,'2017-09-04 00:10:07',NULL),(142,75,1,'2017-09-04 00:16:21',NULL),(143,75,1,'2017-09-04 00:18:50',NULL),(144,75,1,'2017-09-04 00:18:56',NULL),(145,75,1,'2017-09-04 00:26:53',NULL),(146,75,1,'2017-09-04 06:37:17',NULL),(147,75,1,'2017-09-04 06:40:06',NULL),(148,75,1,'2017-09-04 06:54:04',NULL),(149,75,1,'2017-09-04 07:02:45',NULL),(150,75,1,'2017-09-04 07:03:19',NULL),(151,75,1,'2017-09-04 07:05:23',NULL),(152,100,1,'1970-01-18 16:58:43',NULL),(153,100,1,'1970-01-18 16:58:43',NULL),(154,100,1,'1970-01-18 16:58:43',NULL),(155,75,1,'2017-09-04 11:08:47',NULL),(156,100,1,'1970-01-18 16:58:43',NULL),(157,75,1,'2017-09-04 11:22:59',NULL),(158,100,1,'1970-01-18 16:58:43',NULL),(159,75,1,'2017-09-04 11:24:43',NULL),(160,75,1,'2017-09-04 11:24:51',NULL),(161,100,1,'1970-01-18 16:58:43',NULL),(162,75,1,'2017-09-04 12:20:53',NULL),(163,75,1,'2017-09-04 12:21:04',NULL),(164,75,1,'2017-09-04 12:21:27',NULL),(165,56,1,'2017-09-04 12:57:04',NULL),(166,56,1,'2017-09-04 12:59:37',NULL),(167,56,1,'2017-09-04 13:10:09',NULL),(168,67,1,'2017-09-04 13:23:48',NULL),(169,0,1,'2017-09-04 13:28:04',NULL),(170,0,1,'2017-09-04 13:29:04',NULL),(171,0,1,'2017-09-04 13:29:44',NULL),(172,0,1,'2017-09-04 13:29:48',NULL),(173,0,1,'2017-09-04 13:30:37',NULL),(174,56,1,'2017-09-04 13:31:07',NULL),(175,56,1,'2017-09-04 13:31:25',NULL),(176,56,1,'2017-09-04 13:31:46',NULL),(177,0,1,'2017-09-04 13:32:38',NULL),(178,0,1,'2017-09-04 13:33:57',NULL),(179,68,1,'2017-09-04 13:35:34',NULL),(180,68,1,'2017-09-04 13:35:55',NULL),(181,100,1,'1970-01-18 16:58:43',NULL),(182,100,1,'1970-01-18 16:58:43',NULL),(183,100,1,'1970-01-18 16:58:43',NULL),(184,100,1,'2017-07-27 10:48:20',NULL),(185,100,1,'2017-07-27 10:48:20',NULL),(186,100,1,'2017-07-27 10:48:20',NULL),(187,100,1,'2017-07-27 10:48:20',NULL),(188,100,1,'2017-07-27 10:48:20',NULL),(189,100,1,'2017-07-27 10:48:20',NULL),(190,100,1,'2017-07-27 10:48:20',NULL),(191,100,1,'2017-07-27 10:48:20',NULL),(192,100,1,'2017-07-27 10:48:20',NULL),(193,100,1,'2017-07-27 10:48:20',NULL),(194,100,1,'2017-07-27 10:48:20',NULL),(195,100,1,'2017-07-27 10:48:20',NULL),(196,100,1,'2017-07-27 10:48:20',NULL),(197,100,1,'2017-07-27 10:48:20',NULL),(198,87,1,'2017-09-04 20:35:06',NULL),(199,81,1,'2017-09-04 20:36:40',NULL),(200,100,1,'2017-07-27 10:48:20',NULL),(201,100,1,'2017-07-27 10:48:20',NULL),(202,100,1,'2017-07-27 10:48:20',NULL),(203,100,1,'2017-07-27 10:48:20',NULL),(204,100,1,'2017-07-27 10:48:20',NULL),(205,100,1,'2017-07-27 10:48:20',NULL),(206,100,1,'2017-07-27 10:48:20',NULL),(207,100,1,'2017-07-27 10:48:20',NULL),(208,100,1,'2017-07-27 10:48:20',NULL),(209,100,1,'2017-07-27 10:48:20',NULL),(210,100,1,'2017-07-27 10:48:20',NULL),(211,100,1,'2017-07-27 10:48:20',NULL),(212,100,1,'2017-07-27 10:48:20',NULL),(213,100,1,'2017-07-27 10:48:20',NULL),(214,100,1,'2017-07-27 10:48:20',NULL),(215,100,1,'2017-07-27 10:48:20',NULL),(216,100,1,'2017-07-27 10:48:20',NULL),(217,100,1,'2017-07-27 10:48:20',NULL),(218,100,1,'2017-07-27 10:48:20',NULL),(219,100,1,'2017-09-05 17:09:26',NULL),(220,100,1,'2017-09-05 20:49:02',NULL),(221,100,1,'2017-09-05 20:53:41',NULL),(222,100,1,'2017-09-05 21:07:15',NULL),(223,100,1,'2017-09-05 21:23:19',NULL),(224,100,1,'2017-09-05 21:30:54',NULL),(225,100,1,'2017-09-05 22:28:57',NULL),(226,100,1,'2017-09-05 22:31:36',NULL),(227,100,1,'2017-09-05 22:33:37',NULL),(228,100,1,'2017-09-05 22:42:28',NULL),(229,100,1,'2017-09-05 22:49:20',NULL),(230,100,1,'2017-09-05 22:57:45',NULL),(231,100,1,'2017-09-06 09:14:14',NULL),(232,100,1,'2017-09-06 10:01:23',NULL),(233,100,1,'2017-09-06 10:06:15',NULL),(234,100,1,'2017-09-06 10:21:35',NULL),(235,100,1,'2017-09-06 10:34:20',NULL),(236,100,1,'2017-09-06 10:45:00',NULL),(237,100,1,'2017-09-06 10:47:29',NULL),(238,100,1,'2017-09-06 10:48:33',NULL),(239,100,1,'2017-09-06 11:00:05',NULL),(240,100,1,'2017-09-06 11:21:58',NULL),(241,100,1,'2017-09-06 11:50:33',NULL),(242,100,1,'2017-09-06 13:49:54',NULL),(243,100,1,'2017-09-06 13:52:08',NULL),(244,100,1,'2017-09-06 15:34:57',NULL),(245,100,1,'2017-09-06 15:42:01',NULL),(246,100,1,'2017-09-06 15:47:59',NULL),(247,100,1,'2017-09-06 15:52:51',NULL),(248,100,1,'2017-09-06 15:54:09',NULL),(249,100,1,'2017-09-06 15:56:53',NULL),(250,100,1,'2017-09-06 15:59:06',NULL),(251,100,1,'2017-09-06 16:02:27',NULL),(252,100,1,'2017-09-06 16:07:03',NULL),(253,100,1,'2017-09-06 16:08:00',NULL),(254,100,1,'2017-09-06 16:09:05',NULL),(255,100,1,'2017-09-06 16:21:51',NULL),(256,100,1,'2017-09-06 16:23:36',NULL),(257,100,1,'2017-09-06 16:36:25',NULL),(258,100,1,'2017-09-06 16:44:22',NULL),(259,100,1,'2017-09-06 16:45:56',NULL),(260,100,1,'2017-09-06 17:00:31',NULL),(261,100,1,'2017-09-06 17:41:33',NULL),(262,100,1,'2017-09-06 18:01:36',NULL),(263,100,1,'2017-09-06 19:53:43',NULL),(264,100,1,'2017-09-06 19:55:21',NULL),(265,100,1,'2017-09-06 21:12:19',NULL),(266,100,4,'2017-09-06 21:20:44',NULL),(267,71,3,'2017-09-06 22:22:01',NULL),(268,71,2,'2017-09-06 22:25:09',NULL),(269,100,1,'2017-09-07 09:41:35',NULL),(270,100,1,'2017-09-07 09:52:33',NULL),(271,100,1,'2017-09-07 09:54:08',NULL),(272,100,1,'2017-09-07 10:04:31',NULL),(273,80,4,'2017-09-07 10:05:36',NULL),(274,78,4,'2017-09-07 10:37:32',NULL),(275,100,1,'2017-09-07 11:41:58',NULL),(276,100,1,'2017-09-07 11:58:31',NULL),(277,100,1,'2017-09-07 13:45:20',NULL),(278,100,1,'2017-09-07 13:49:37',NULL),(279,100,1,'2017-09-07 13:51:39',NULL),(280,100,1,'2017-09-07 13:53:03',NULL),(281,100,1,'2017-09-07 15:15:01',NULL),(282,100,1,'2017-09-07 15:16:47',NULL),(283,100,1,'2017-09-07 15:22:07',NULL),(284,100,1,'2017-09-07 15:50:40',NULL),(285,100,1,'2017-09-07 16:13:16',NULL),(286,100,1,'2017-09-07 16:14:34',NULL),(287,100,1,'2017-09-07 16:23:14',NULL),(288,100,1,'2017-09-07 16:24:47',NULL),(289,100,1,'2017-09-07 16:39:19',NULL),(290,100,1,'2017-09-07 17:27:37',NULL),(291,100,1,'2017-09-08 10:05:42',NULL),(292,100,1,'2017-09-08 10:45:26',NULL),(293,100,1,'2017-09-08 10:59:41',NULL),(294,100,1,'2017-09-08 11:05:02',NULL),(295,100,1,'2017-09-08 11:57:09',NULL),(296,100,1,'2017-09-08 12:25:00',NULL),(297,100,1,'2017-09-08 15:00:48',NULL),(298,100,1,'2017-09-08 15:19:25',NULL),(299,100,1,'2017-09-08 15:26:02',NULL),(300,100,1,'2017-09-08 15:40:10',NULL),(301,100,1,'2017-09-08 15:49:19',NULL),(302,100,1,'2017-09-08 16:15:50',NULL),(303,100,1,'2017-09-08 16:33:20',NULL),(304,100,1,'2017-09-08 16:44:06',NULL),(305,100,1,'2017-09-08 16:57:50',NULL),(306,100,1,'2017-09-08 16:58:38',NULL),(307,100,1,'2017-09-08 17:14:22',NULL),(308,100,1,'2017-09-08 17:31:01',NULL),(309,100,1,'2017-09-08 17:36:42',NULL),(310,100,1,'2017-09-08 17:45:01',NULL),(311,100,1,'2017-09-09 10:20:04',NULL),(312,100,1,'2017-09-09 12:12:00',NULL),(313,100,1,'2017-09-09 14:10:14',NULL),(314,100,1,'2017-09-09 14:11:40',NULL),(315,100,1,'2017-09-09 14:17:46',NULL),(316,100,1,'2017-09-11 09:43:20',NULL),(317,100,1,'2017-09-11 09:44:50',NULL),(318,100,1,'2017-09-11 09:46:03',NULL),(319,100,1,'2017-09-11 09:50:39',NULL),(320,100,1,'2017-09-11 09:58:25',NULL),(321,100,1,'2017-09-11 10:01:09',NULL),(322,100,1,'2017-09-11 10:20:27',NULL),(323,100,1,'2017-09-11 10:25:54',NULL),(324,100,1,'2017-09-11 14:09:04',NULL),(325,100,1,'2017-09-11 14:15:58',NULL),(326,100,1,'2017-09-11 14:24:36',NULL),(327,100,1,'2017-09-11 14:42:38',NULL),(328,100,1,'2017-09-11 14:43:30',NULL),(329,100,1,'2017-09-11 14:45:51',NULL),(330,100,1,'2017-09-11 15:05:02',NULL),(331,100,1,'2017-09-11 15:32:28',NULL),(332,100,1,'2017-09-11 15:54:15',NULL),(333,100,1,'2017-09-11 16:37:23',NULL),(334,100,1,'2017-09-11 16:42:38',NULL),(335,100,1,'2017-09-11 16:48:54',NULL),(336,100,1,'2017-09-11 17:02:30',NULL),(337,100,1,'2017-09-11 17:21:07',NULL),(338,100,1,'2017-09-11 17:33:42',NULL),(339,100,1,'2017-09-11 17:36:41',NULL),(340,100,1,'2017-09-11 17:58:50',NULL),(341,100,1,'2017-09-11 18:07:23',NULL),(342,100,1,'2017-09-11 18:26:13',NULL),(343,100,1,'2017-09-11 19:35:47',NULL),(344,100,1,'2017-09-11 20:01:38',NULL),(345,100,1,'2017-09-11 20:08:53',NULL),(346,100,1,'2017-09-11 20:39:12',NULL),(347,100,1,'2017-09-11 21:11:34',NULL),(348,100,1,'2017-09-11 21:39:15',NULL),(349,100,1,'2017-09-12 09:28:13',NULL),(350,100,1,'2017-09-12 09:35:24',NULL),(351,100,1,'2017-09-12 09:40:45',NULL),(352,100,1,'2017-09-12 10:08:04',NULL),(353,100,1,'2017-09-12 10:12:05',NULL),(354,100,1,'2017-09-12 10:24:46',NULL),(355,100,1,'2017-09-12 10:45:16',NULL),(356,100,1,'2017-09-12 16:13:02',NULL),(357,100,1,'2017-09-12 16:47:40',NULL),(358,100,1,'2017-09-12 17:07:42',NULL),(359,100,1,'2017-09-12 17:15:01',NULL),(360,100,1,'2017-09-12 17:29:49',NULL),(361,100,1,'2017-09-12 17:35:16',NULL),(362,100,1,'2017-09-12 17:52:46',NULL),(363,100,1,'2017-09-12 19:20:58',NULL),(364,100,1,'2017-09-12 19:37:18',NULL),(365,100,1,'2017-09-12 19:44:00',NULL),(366,100,1,'2017-09-12 19:53:12',NULL),(367,100,1,'2017-09-12 19:57:54',NULL),(368,100,1,'2017-09-12 20:01:55',NULL),(369,100,1,'2017-09-12 20:33:04',NULL),(370,100,1,'2017-09-12 20:36:20',NULL),(371,100,1,'2017-09-12 20:37:10',NULL),(372,100,1,'2017-09-12 20:43:16',NULL),(373,100,1,'2017-09-12 20:50:35',NULL),(374,100,1,'2017-09-12 21:03:57',NULL),(375,100,1,'2017-09-12 21:08:03',NULL),(376,100,1,'2017-09-13 09:23:56',NULL),(377,100,1,'2017-09-13 09:25:38',NULL),(378,100,1,'2017-09-13 09:29:42',NULL),(379,100,1,'2017-09-13 10:05:39',NULL),(380,100,1,'2017-09-13 10:09:17',NULL),(381,100,1,'2017-09-13 10:59:20',NULL),(382,100,1,'2017-09-13 11:19:22',NULL),(383,100,1,'2017-09-13 11:31:42',NULL),(384,100,1,'2017-09-13 11:38:53',NULL),(385,100,1,'2017-09-13 15:13:57',NULL),(386,100,1,'2017-09-13 15:46:44',NULL),(387,69,3,'2017-09-13 21:01:09',NULL);

/*Table structure for table `ip_info` */

DROP TABLE IF EXISTS `ip_info`;

CREATE TABLE `ip_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `ip` varchar(256) NOT NULL,
  `port` varchar(5) NOT NULL,
  `status` char(2) NOT NULL DEFAULT '0' COMMENT '0暂停使用 1使用',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `ip_info` */

insert  into `ip_info`(`id`,`ip`,`port`,`status`,`createtime`) values (7,'127.0.0.1','5536','1','2018-09-12 15:38:03'),(8,'127.0.0.6','5956','1','2018-09-12 15:38:12');

/*Table structure for table `location` */

DROP TABLE IF EXISTS `location`;

CREATE TABLE `location` (
  `l_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `location_type` varchar(4) NOT NULL COMMENT '类型，0：gps， 1：基站',
  `lat` varchar(64) NOT NULL,
  `lng` varchar(64) NOT NULL,
  `accuracy` int(10) NOT NULL DEFAULT '0',
  `status` int(4) NOT NULL DEFAULT '0',
  `upload_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `imei` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`l_id`)
) ENGINE=InnoDB AUTO_INCREMENT=440 DEFAULT CHARSET=utf8;

/*Data for the table `location` */

insert  into `location`(`l_id`,`user_id`,`location_type`,`lat`,`lng`,`accuracy`,`status`,`upload_time`,`imei`) values (1,1,'0','34.876','65.9998',0,0,'2017-08-27 19:19:29',NULL),(2,1,'0','12.306','123.569',10,0,'1970-01-18 16:58:43',NULL),(3,1,'0','12.306','123.569',10,0,'1970-01-18 16:58:43',NULL),(4,1,'0','12.306','123.569',10,0,'1970-01-18 16:58:43',NULL),(5,1,'0','12.306','123.569',10,0,'1970-01-18 16:58:43',NULL),(6,1,'0','12.306','123.569',10,0,'1970-01-18 16:58:43',NULL),(7,1,'0','12.306','123.569',10,0,'1970-01-18 16:58:43',NULL),(8,1,'1','22.500472','113.9221332',0,0,'1970-01-12 07:24:11',NULL),(10,1,'1','22.500472','113.9221332',0,0,'1970-01-12 07:24:11',NULL),(12,1,'1','22.500472','113.9221332',0,0,'2017-09-04 14:18:00',NULL),(14,1,'1','22.500472','113.9221332',0,0,'2017-09-04 14:35:00',NULL),(16,1,'1','22.500472','113.9221332',0,0,'2017-09-04 14:35:00',NULL),(19,1,'1','22.500472','113.9221332',0,0,'2004-01-01 04:04:00',NULL),(20,1,'1','22.500472','113.9221332',0,0,'2017-09-04 15:31:00',NULL),(22,1,'1','22.500472','113.9221332',0,0,'2017-09-04 15:32:00',NULL),(24,1,'1','22.500472','113.9221332',0,0,'2017-09-04 15:39:00',NULL),(25,1,'1','22.500472','113.9221332',0,0,'2017-09-04 15:39:00',NULL),(26,1,'1','22.500472','113.9221332',0,0,'2017-09-04 15:40:00',NULL),(27,1,'1','22.500472','113.9221332',0,0,'2017-09-04 15:40:00',NULL),(28,1,'1','22.500472','113.9221332',0,0,'2017-09-04 15:40:00',NULL),(29,1,'1','22.500472','113.9221332',0,0,'2017-09-04 15:40:00',NULL),(30,1,'1','22.500472','113.9221332',0,0,'2017-09-04 15:40:00',NULL),(31,1,'1','22.500472','113.9221332',0,0,'2017-09-04 15:40:00',NULL),(32,1,'1','22.500472','113.9221332',0,0,'2017-09-04 15:40:00',NULL),(33,1,'1','22.500472','113.9221332',0,0,'2017-09-04 15:40:00',NULL),(34,1,'1','22.500472','113.9221332',0,0,'2017-09-04 15:40:00',NULL),(36,1,'1','22.500472','113.9221332',0,0,'2017-09-04 15:41:00',NULL),(38,1,'1','22.500472','113.9221332',0,0,'2017-09-04 15:52:00',NULL),(39,1,'1','22.500472','113.9221332',0,0,'2017-09-04 15:52:00',NULL),(40,1,'1','22.500472','113.9221332',0,0,'2017-09-04 15:52:00',NULL),(42,1,'1','22.500472','113.9221332',0,0,'2017-09-04 15:53:00',NULL),(43,1,'1','22.500472','113.9221332',0,0,'2017-09-04 15:53:00',NULL),(44,1,'1','22.500472','113.9221332',0,0,'2017-09-04 15:53:00',NULL),(45,1,'1','22.500472','113.9221332',0,0,'2017-09-04 15:53:00',NULL),(46,1,'1','22.500472','113.9221332',0,0,'2017-09-04 15:53:00',NULL),(49,1,'1','22.500472','113.9221332',0,0,'2017-09-04 15:54:22',NULL),(51,1,'1','22.500472','113.9221332',0,0,'2017-09-04 20:00:23',NULL),(52,1,'1','22.500472','113.9221332',0,0,'2017-09-04 20:00:28',NULL),(53,1,'1','22.500472','113.9221332',0,0,'2017-09-04 20:00:30',NULL),(54,1,'1','22.500472','113.9221332',0,0,'2017-09-04 20:00:32',NULL),(55,1,'1','22.500472','113.9221332',0,0,'2017-09-04 20:00:34',NULL),(57,1,'1','22.500472','113.9221332',0,0,'2017-09-04 20:04:43',NULL),(58,1,'1','22.500472','113.9221332',0,0,'2017-09-04 20:04:45',NULL),(60,1,'1','22.500472','113.9221332',0,0,'2017-09-04 20:30:08',NULL),(61,1,'1','22.500472','113.9221332',0,0,'2017-09-04 20:30:10',NULL),(62,1,'1','22.500472','113.9221332',0,0,'2017-09-04 20:30:14',NULL),(65,1,'1','22.500472','113.9221332',0,0,'2017-09-04 20:46:53',NULL),(66,1,'1','22.500472','113.9221332',0,0,'2017-09-04 20:47:35',NULL),(67,1,'1','22.500472','113.9221332',0,0,'2017-09-04 20:47:39',NULL),(69,1,'1','22.500472','113.9221332',0,0,'2017-09-04 20:49:11',NULL),(71,1,'1','22.500472','113.9221332',0,0,'2017-09-04 20:51:52',NULL),(72,1,'1','22.500472','113.9221332',0,0,'2017-09-04 20:51:57',NULL),(74,1,'1','22.500472','113.9221332',0,0,'2017-09-04 20:52:56',NULL),(75,1,'1','22.500472','113.9221332',0,0,'2017-09-04 20:53:02',NULL),(76,1,'1','22.500472','113.9221332',0,0,'2017-09-04 20:53:04',NULL),(77,1,'1','22.500472','113.9221332',0,0,'2017-09-04 20:53:06',NULL),(78,1,'1','22.500472','113.9221332',0,0,'2017-09-04 20:53:07',NULL),(79,1,'1','22.500472','113.9221332',0,0,'2017-09-04 20:53:09',NULL),(80,1,'1','22.500472','113.9221332',0,0,'2017-09-04 20:53:11',NULL),(81,1,'1','22.500472','113.9221332',0,0,'2017-09-04 20:53:12',NULL),(82,1,'1','22.500472','113.9221332',0,0,'2017-09-04 20:53:18',NULL),(83,1,'1','22.500472','113.9221332',0,0,'2017-09-04 20:53:19',NULL),(84,1,'1','22.500472','113.9221332',0,0,'2017-09-04 20:53:21',NULL),(85,1,'1','22.500472','113.9221332',0,0,'2017-09-04 20:53:29',NULL),(87,1,'1','22.500472','113.9221332',0,0,'2017-09-04 20:54:08',NULL),(88,1,'1','22.500472','113.9221332',0,0,'2017-09-04 20:54:24',NULL),(90,1,'1','22.500472','113.9221332',0,0,'2017-09-04 20:54:47',NULL),(91,1,'1','22.500472','113.9221332',0,0,'2017-09-04 20:54:48',NULL),(93,1,'1','22.500472','113.9221332',0,0,'2017-09-04 20:55:27',NULL),(95,1,'1','22.500472','113.9221332',0,0,'2017-09-04 20:56:11',NULL),(97,1,'1','22.500472','113.9221332',0,0,'2017-09-04 20:57:22',NULL),(100,1,'1','22.500472','113.9221332',0,0,'2017-09-04 21:09:58',NULL),(102,1,'1','22.500472','113.9221332',0,0,'2017-09-04 21:15:56',NULL),(104,1,'1','22.500472','113.9221332',0,0,'2017-09-04 21:17:23',NULL),(106,1,'1','22.500472','113.9221332',0,0,'2017-09-04 21:25:27',NULL),(107,1,'1','22.500472','113.9221332',0,0,'2017-09-04 21:26:53',NULL),(109,1,'1','22.500472','113.9221332',0,0,'2017-09-04 21:27:50',NULL),(111,1,'1','22.500472','113.9221332',0,0,'2017-09-04 21:33:34',NULL),(113,1,'1','22.500472','113.9221332',0,0,'2017-09-04 21:34:16',NULL),(114,1,'1','22.500472','113.9221332',0,0,'2017-09-04 21:34:18',NULL),(116,1,'1','22.500472','113.9221332',0,0,'2017-09-04 21:36:27',NULL),(118,1,'1','22.500472','113.9221332',0,0,'2017-09-04 21:45:54',NULL),(120,1,'1','22.500472','113.9221332',0,0,'2017-09-04 21:46:19',NULL),(121,1,'1','22.500472','113.9221332',0,0,'2017-09-04 21:46:28',NULL),(123,1,'1','22.500472','113.9221332',0,0,'2017-09-04 21:47:27',NULL),(126,1,'1','22.500472','113.9221332',0,0,'2017-09-04 21:51:26',NULL),(128,1,'1','22.500472','113.9221332',0,0,'2017-09-04 21:55:25',NULL),(129,1,'1','22.500472','113.9221332',0,0,'2017-09-04 21:55:31',NULL),(130,1,'1','22.4983228','113.9216181',0,0,'2017-09-04 21:55:46',NULL),(131,1,'1','22.4983228','113.9216181',0,0,'2017-09-04 21:55:46',NULL),(133,1,'1','22.4983228','113.9216181',0,0,'2017-09-04 21:57:04',NULL),(134,1,'1','22.4983228','113.9216181',0,0,'2017-09-04 21:57:09',NULL),(135,1,'1','22.4983228','113.9216181',0,0,'2017-09-04 21:57:12',NULL),(136,1,'1','22.4983228','113.9216181',0,0,'2017-09-04 21:57:24',NULL),(139,1,'1','22.500472','113.9221332',0,0,'2017-09-04 21:58:44',NULL),(140,1,'1','22.500472','113.9221332',0,0,'2017-09-04 21:58:44',NULL),(143,1,'1','22.500472','113.9221332',0,0,'2017-09-04 22:02:17',NULL),(144,1,'1','22.500472','113.9221332',0,0,'2017-09-04 22:02:45',NULL),(146,1,'1','22.500472','113.9221332',0,0,'2017-09-04 22:03:16',NULL),(153,1,'1','22.500472','113.9221332',0,0,'2017-09-05 14:45:14',NULL),(154,1,'1','22.500472','113.9221332',0,0,'2017-09-05 14:45:25',NULL),(165,1,'1','22.500472','113.9221332',0,0,'2017-09-05 15:56:24',NULL),(166,1,'1','22.500472','113.9221332',0,0,'2017-09-05 19:53:11',NULL),(167,1,'1','22.500472','113.9221332',0,0,'2017-09-05 20:22:00',NULL),(168,1,'1','22.500472','113.9221332',0,0,'2017-09-05 16:44:29',NULL),(169,1,'1','22.500472','113.9221332',0,1,'2017-09-06 11:00:07',NULL),(170,1,'1','22.500472','113.9221332',0,1,'2017-09-06 11:00:25',NULL),(171,1,'1','22.500472','113.9221332',0,1,'2017-09-06 11:00:40',NULL),(172,1,'1','22.500472','113.9221332',0,1,'2017-09-06 11:00:57',NULL),(173,1,'1','22.500472','113.9221332',0,1,'2017-09-06 11:01:28',NULL),(174,1,'1','22.500472','113.9221332',0,1,'2017-09-06 11:01:34',NULL),(175,1,'1','22.500472','113.9221332',0,1,'2017-09-06 11:01:45',NULL),(176,1,'1','22.500472','113.9221332',0,1,'2017-09-06 11:01:52',NULL),(177,1,'1','22.500472','113.9221332',0,1,'2017-09-06 11:03:15',NULL),(178,1,'1','22.500472','113.9221332',0,1,'2017-09-06 11:04:22',NULL),(179,1,'1','22.500472','113.9221332',0,0,'2017-09-06 11:04:42',NULL),(180,1,'1','22.500472','113.9221332',0,1,'2017-09-06 11:26:23',NULL),(181,1,'1','22.500472','113.9221332',0,1,'2017-09-06 11:26:31',NULL),(182,1,'1','22.500472','113.9221332',0,1,'2017-09-06 14:18:36',NULL),(183,1,'1','22.6244374','113.8129484',0,1,'2017-09-06 21:21:26',NULL),(184,1,'1','22.6244374','113.8129484',0,1,'2017-09-06 21:21:48',NULL),(185,1,'1','22.500472','113.9221332',0,0,'2017-09-07 09:28:01',NULL),(186,1,'1','22.500472','113.9221332',0,0,'2017-09-07 09:28:05',NULL),(187,1,'1','22.500472','113.9221332',0,1,'2017-09-07 09:41:39',NULL),(188,1,'1','22.500472','113.9221332',0,1,'2017-09-07 09:41:54',NULL),(189,1,'1','22.500472','113.9221332',0,1,'2017-09-07 09:41:57',NULL),(190,1,'1','22.500472','113.9221332',0,1,'2017-09-07 09:42:00',NULL),(191,1,'1','22.500472','113.9221332',0,1,'2017-09-07 09:42:19',NULL),(192,1,'1','22.500472','113.9221332',0,1,'2017-09-07 09:42:19',NULL),(193,1,'1','22.500472','113.9221332',0,1,'2017-09-07 09:42:41',NULL),(194,1,'1','22.500472','113.9221332',0,1,'2017-09-07 09:43:30',NULL),(195,1,'1','22.500472','113.9221332',0,1,'2017-09-07 09:44:14',NULL),(196,1,'1','22.500472','113.9221332',0,1,'2017-09-07 09:45:28',NULL),(197,1,'1','22.500472','113.9221332',0,1,'2017-09-07 09:47:22',NULL),(198,1,'1','22.500472','113.9221332',0,1,'2017-09-07 09:48:17',NULL),(199,4,'1','22.500472','113.9221332',0,1,'2017-09-07 10:37:47',NULL),(200,4,'1','22.500472','113.9221332',0,1,'2017-09-07 10:38:15',NULL),(201,4,'1','22.500472','113.9221332',0,1,'2017-09-07 10:39:38',NULL),(202,4,'1','22.500472','113.9221332',0,1,'2017-09-07 10:39:42',NULL),(203,4,'1','22.500472','113.9221332',0,1,'2017-09-07 10:39:49',NULL),(204,4,'1','22.500472','113.9221332',0,1,'2017-09-07 10:40:18',NULL),(205,4,'1','22.500472','113.9221332',0,1,'2017-09-07 10:42:17',NULL),(206,4,'1','22.500472','113.9221332',0,1,'2017-09-07 10:42:18',NULL),(207,4,'1','22.500472','113.9221332',0,1,'2017-09-07 10:42:20',NULL),(208,4,'1','22.500472','113.9221332',0,1,'2017-09-07 10:42:20',NULL),(209,4,'1','22.500472','113.9221332',0,1,'2017-09-07 10:42:56',NULL),(210,4,'1','22.500472','113.9221332',0,1,'2017-09-07 11:10:38',NULL),(211,4,'1','22.500472','113.9221332',0,1,'2017-09-07 11:11:23',NULL),(212,4,'1','22.500472','113.9221332',0,1,'2017-09-07 11:11:27',NULL),(213,4,'1','22.500472','113.9221332',0,1,'2017-09-07 11:11:27',NULL),(214,4,'1','22.500472','113.9221332',0,1,'2017-09-07 11:11:28',NULL),(215,4,'1','22.500472','113.9221332',0,1,'2017-09-07 11:11:28',NULL),(216,4,'1','22.500472','113.9221332',0,1,'2017-09-07 11:11:28',NULL),(217,4,'1','22.500472','113.9221332',0,1,'2017-09-07 11:11:29',NULL),(218,4,'1','22.500472','113.9221332',0,1,'2017-09-07 11:11:29',NULL),(219,4,'1','22.500472','113.9221332',0,1,'2017-09-07 11:11:29',NULL),(220,4,'1','22.500472','113.9221332',0,1,'2017-09-07 11:11:30',NULL),(221,4,'1','22.500472','113.9221332',0,1,'2017-09-07 11:11:30',NULL),(222,4,'1','22.500472','113.9221332',0,1,'2017-09-07 11:11:30',NULL),(223,4,'1','22.500472','113.9221332',0,1,'2017-09-07 11:11:31',NULL),(224,4,'1','22.500472','113.9221332',0,1,'2017-09-07 11:11:31',NULL),(225,4,'1','22.500472','113.9221332',0,1,'2017-09-07 11:11:32',NULL),(226,4,'1','22.500472','113.9221332',0,1,'2017-09-07 17:46:31',NULL),(227,4,'1','22.500472','113.9221332',0,1,'2017-09-07 20:11:07',NULL),(228,4,'1','22.500472','113.9221332',0,1,'2017-09-07 20:42:04',NULL),(229,4,'1','22.500472','113.9221332',0,1,'2017-09-07 21:17:01',NULL),(230,4,'1','22.500472','113.9221332',0,1,'2017-09-07 21:17:32',NULL),(231,4,'1','22.500472','113.9221332',0,1,'2017-09-07 21:17:41',NULL),(232,4,'1','22.500472','113.9221332',0,1,'2017-09-07 21:17:51',NULL),(233,4,'1','22.500472','113.9221332',0,1,'2017-09-07 21:18:23',NULL),(234,4,'1','22.500472','113.9221332',0,1,'2017-09-07 21:18:34',NULL),(235,4,'1','22.500472','113.9221332',0,1,'2017-09-07 21:18:41',NULL),(236,4,'1','22.500472','113.9221332',0,1,'2017-09-07 21:19:57',NULL),(237,4,'1','22.500472','113.9221332',0,1,'2017-09-07 21:20:19',NULL),(238,4,'1','22.500472','113.9221332',0,1,'2017-09-07 21:21:41',NULL),(239,4,'1','22.500472','113.9221332',0,1,'2017-09-07 21:21:48',NULL),(240,4,'1','22.500472','113.9221332',0,1,'2017-09-07 21:21:55',NULL),(241,4,'1','22.500472','113.9221332',0,1,'2017-09-07 21:22:00',NULL),(242,4,'1','22.500472','113.9221332',0,1,'2017-09-07 21:22:07',NULL),(243,4,'1','22.500472','113.9221332',0,1,'2017-09-07 21:22:09',NULL),(244,4,'1','22.500472','113.9221332',0,1,'2017-09-07 21:23:55',NULL),(245,4,'1','22.500472','113.9221332',0,1,'2017-09-07 21:49:47',NULL),(246,4,'1','22.500472','113.9221332',0,1,'2017-09-07 21:50:04',NULL),(247,4,'1','22.500472','113.9221332',0,1,'2017-09-07 21:50:34',NULL),(248,4,'1','22.500472','113.9221332',0,1,'2017-09-07 21:50:58',NULL),(249,4,'1','22.500472','113.9221332',0,1,'2017-09-07 21:52:01',NULL),(250,4,'1','22.500472','113.9221332',0,1,'2017-09-07 21:52:08',NULL),(251,4,'1','22.500472','113.9221332',0,1,'2017-09-07 21:52:21',NULL),(252,4,'1','22.500472','113.9221332',0,1,'2017-09-07 21:52:24',NULL),(253,4,'1','22.500472','113.9221332',0,1,'2017-09-07 21:52:30',NULL),(254,4,'1','22.500472','113.9221332',0,1,'2017-09-07 21:52:58',NULL),(255,4,'1','22.500472','113.9221332',0,1,'2017-09-07 21:53:01',NULL),(256,4,'1','22.500472','113.9221332',0,1,'2017-09-07 21:53:04',NULL),(257,4,'1','22.500472','113.9221332',0,1,'2017-09-07 21:54:19',NULL),(258,4,'1','22.500472','113.9221332',0,1,'2017-09-07 21:54:21',NULL),(259,4,'1','22.500472','113.9221332',0,1,'2017-09-07 21:54:23',NULL),(260,4,'1','22.500472','113.9221332',0,1,'2017-09-07 21:55:59',NULL),(261,4,'1','22.500472','113.9221332',0,1,'2017-09-07 21:56:17',NULL),(262,4,'1','22.500472','113.9221332',0,1,'2017-09-07 21:56:26',NULL),(263,4,'1','22.500472','113.9221332',0,1,'2017-09-07 21:56:30',NULL),(264,4,'1','22.500472','113.9221332',0,1,'2017-09-07 21:56:35',NULL),(265,4,'1','22.500472','113.9221332',0,1,'2017-09-07 21:56:35',NULL),(266,4,'1','22.500472','113.9221332',0,1,'2017-09-07 21:56:42',NULL),(267,4,'1','22.500472','113.9221332',0,1,'2017-09-07 21:56:47',NULL),(268,4,'1','22.500472','113.9221332',0,1,'2017-09-07 21:56:54',NULL),(269,4,'1','22.500472','113.9221332',0,1,'2017-09-07 21:57:08',NULL),(270,4,'1','22.500472','113.9221332',0,1,'2017-09-07 21:57:20',NULL),(271,4,'1','22.500472','113.9221332',0,1,'2017-09-07 21:57:20',NULL),(272,4,'1','22.500472','113.9221332',0,1,'2017-09-07 21:57:30',NULL),(273,4,'1','22.500472','113.9221332',0,1,'2017-09-07 21:57:41',NULL),(274,4,'1','22.500472','113.9221332',0,1,'2017-09-07 21:58:10',NULL),(275,4,'1','22.500472','113.9221332',0,1,'2017-09-07 22:00:58',NULL),(276,4,'1','22.500472','113.9221332',0,1,'2017-09-07 22:06:02',NULL),(277,4,'1','22.500472','113.9221332',0,1,'2017-09-07 22:06:09',NULL),(278,4,'1','22.500472','113.9221332',0,1,'2017-09-07 22:06:13',NULL),(279,4,'1','22.500472','113.9221332',0,1,'2017-09-07 22:08:07',NULL),(280,4,'1','22.500472','113.9221332',0,1,'2017-09-07 22:08:10',NULL),(281,4,'1','22.500472','113.9221332',0,1,'2017-09-07 22:08:28',NULL),(282,4,'1','22.500472','113.9221332',0,1,'2017-09-07 22:19:05',NULL),(283,4,'1','22.500472','113.9221332',0,1,'2017-09-07 22:38:42',NULL),(284,4,'1','22.500472','113.9221332',0,1,'2017-09-07 22:39:11',NULL),(285,4,'1','22.500472','113.9221332',0,1,'2017-09-08 09:38:21',NULL),(286,4,'1','22.500472','113.9221332',0,1,'2017-09-08 09:38:56',NULL),(287,4,'1','22.500472','113.9221332',0,1,'2017-09-08 09:43:44',NULL),(288,4,'1','22.500472','113.9221332',0,1,'2017-09-08 09:43:50',NULL),(289,4,'1','22.500472','113.9221332',0,1,'2017-09-08 09:45:17',NULL),(290,4,'1','22.500472','113.9221332',0,1,'2017-09-08 09:49:53',NULL),(291,4,'1','22.500472','113.9221332',0,0,'2017-09-08 11:54:42',NULL),(292,4,'1','22.500472','113.9221332',0,1,'2017-09-08 11:57:53',NULL),(293,4,'1','22.4992849','113.9205553',0,1,'2017-09-08 15:01:45',NULL),(294,4,'1','22.4992849','113.9205553',0,1,'2017-09-08 15:20:18',NULL),(295,4,'1','22.4996357','113.9231193',0,1,'2017-09-08 15:41:40',NULL),(296,4,'1','22.5000339','113.9204801',0,1,'2017-09-08 17:15:17',NULL),(297,4,'1','22.5003071','113.9219911',0,1,'2017-09-08 17:37:35',NULL),(298,4,'1','22.5003071','113.9219911',0,1,'2017-09-08 17:38:32',NULL),(299,4,'1','22.5000339','113.9204801',0,1,'2017-09-08 17:46:28',NULL),(300,4,'1','22.5000339','113.9204801',0,1,'2017-09-08 17:47:32',NULL),(301,4,'1','22.5000339','113.9204801',0,1,'2017-09-08 17:48:38',NULL),(302,4,'1','22.5000339','113.9204801',0,1,'2017-09-08 17:50:18',NULL),(303,4,'1','22.5000339','113.9204801',0,1,'2017-09-08 17:51:20',NULL),(304,4,'0','22.502996','113.916109',10,0,'2017-09-08 17:52:26',NULL),(305,4,'1','22.5000339','113.9204801',0,1,'2017-09-08 17:55:49',NULL),(306,4,'1','22.5000339','113.9204801',0,1,'2017-09-08 17:56:44',NULL),(307,4,'1','22.5000339','113.9204801',0,1,'2017-09-08 17:57:33',NULL),(308,4,'0','22.502844','113.916282',10,0,'2017-09-08 17:58:42',NULL),(309,4,'1','22.5000339','113.9204801',0,1,'2017-09-08 17:59:41',NULL),(310,4,'0','22.502872','113.916114',10,0,'2017-09-08 18:00:42',NULL),(311,4,'0','22.502898','113.916050',10,0,'2017-09-09 10:21:05',NULL),(312,4,'0','22.502743','113.916145',10,0,'2017-09-09 10:22:40',NULL),(313,4,'0','22.502960','113.916100',10,0,'2017-09-09 10:23:40',NULL),(314,4,'0','22.503040','113.916275',10,0,'2017-09-09 10:24:39',NULL),(315,4,'0','22.502765','113.916116',10,0,'2017-09-09 10:25:35',NULL),(316,4,'0','22.503003','113.915906',10,0,'2017-09-09 10:27:11',NULL),(317,4,'1','22.5000339','113.9204801',0,1,'2017-09-09 14:18:54',NULL),(318,4,'1','22.5000339','113.9204801',0,1,'2017-09-09 14:19:50',NULL),(319,4,'0','22.502827','113.916225',10,0,'2017-09-09 14:20:45',NULL),(320,4,'0','22.502788','113.916142',10,0,'2017-09-09 14:21:41',NULL),(321,4,'1','22.4992849','113.9205553',0,1,'2017-09-11 16:38:35',NULL),(322,4,'1','22.4992849','113.9205553',0,1,'2017-09-11 16:44:24',NULL),(323,4,'1','22.4992849','113.9205553',0,1,'2017-09-11 16:51:02',NULL),(324,4,'1','22.4992849','113.9205553',0,1,'2017-09-11 17:03:57',NULL),(325,4,'1','22.4992849','113.9205553',0,1,'2017-09-11 17:23:02',NULL),(326,4,'1','22.4992849','113.9205553',0,1,'2017-09-11 17:24:24',NULL),(327,4,'1','22.4992849','113.9205553',0,1,'2017-09-11 17:38:33',NULL),(328,4,'1','22.4992849','113.9205553',0,1,'2017-09-11 17:40:50',NULL),(329,4,'1','22.5003071','113.9219911',0,1,'2017-09-11 18:00:50',NULL),(330,4,'1','22.5003071','113.9219911',0,1,'2017-09-11 18:01:49',NULL),(331,4,'1','22.4992849','113.9205553',0,1,'2017-09-11 18:09:05',NULL),(332,4,'1','22.4992849','113.9205553',0,1,'2017-09-11 18:10:22',NULL),(333,4,'1','22.4992849','113.9205553',0,1,'2017-09-11 18:12:05',NULL),(334,4,'1','22.4992849','113.9205553',0,1,'2017-09-11 18:12:57',NULL),(335,4,'1','22.4992849','113.9205553',0,1,'2017-09-11 18:16:45',NULL),(336,4,'1','22.4992849','113.9205553',0,1,'2017-09-11 18:23:22',NULL),(337,4,'1','22.4992849','113.9205553',0,1,'2017-09-11 18:27:57',NULL),(338,4,'1','22.5003071','113.9219911',0,0,'2017-09-11 19:35:50',NULL),(339,4,'1','22.5003071','113.9219911',0,1,'2017-09-11 19:36:55',NULL),(340,4,'1','22.4992849','113.9205553',0,0,'2017-09-11 20:01:57',NULL),(341,4,'1','22.4992849','113.9205553',0,1,'2017-09-11 20:02:39',NULL),(342,4,'1','22.4992849','113.9205553',0,0,'2017-09-11 20:09:01',NULL),(343,4,'1','22.4992849','113.9205553',0,1,'2017-09-11 20:09:41',NULL),(344,4,'1','22.4992849','113.9205553',0,1,'2017-09-11 20:40:16',NULL),(345,4,'1','22.4992849','113.9205553',0,0,'2017-09-11 21:11:50',NULL),(346,4,'1','22.4992849','113.9205553',0,1,'2017-09-11 21:12:29',NULL),(347,4,'1','22.4992849','113.9205553',0,1,'2017-09-11 21:12:55',NULL),(348,4,'1','22.4992849','113.9205553',0,0,'2017-09-11 21:39:37',NULL),(349,4,'1','22.4992849','113.9205553',0,1,'2017-09-11 21:40:19',NULL),(350,4,'1','22.4992849','113.9205553',0,1,'2017-09-11 21:40:42',NULL),(351,4,'1','22.4992849','113.9205553',0,0,'2017-09-11 21:41:54',NULL),(352,4,'1','22.4992849','113.9205553',0,0,'2017-09-11 21:42:00',NULL),(353,4,'1','22.4992849','113.9205553',0,0,'2017-09-11 21:42:16',NULL),(354,4,'1','22.4992849','113.9205553',0,1,'2017-09-11 21:42:55',NULL),(355,4,'1','22.4992849','113.9205553',0,1,'2017-09-11 21:43:21',NULL),(356,4,'1','22.4992849','113.9205553',0,0,'2017-09-12 10:11:25',NULL),(357,4,'1','22.4992849','113.9205553',0,0,'2017-09-12 10:11:26',NULL),(358,4,'1','22.4992849','113.9205553',0,0,'2017-09-12 10:12:06',NULL),(359,4,'1','22.4992849','113.9205553',0,0,'2017-09-12 10:12:17',NULL),(360,4,'1','22.4992849','113.9205553',0,1,'2017-09-12 10:13:22',NULL),(361,4,'1','22.4992849','113.9205553',0,0,'2017-09-12 10:32:31',NULL),(362,4,'1','22.4992849','113.9205553',0,0,'2017-09-12 10:32:39',NULL),(363,4,'1','22.4992849','113.9205553',0,0,'2017-09-12 10:32:42',NULL),(364,4,'1','22.4992849','113.9205553',0,1,'2017-09-12 10:33:47',NULL),(365,4,'1','22.4992849','113.9205553',0,0,'2017-09-12 10:37:30',NULL),(366,4,'1','22.4992849','113.9205553',0,0,'2017-09-12 10:37:32',NULL),(367,4,'1','22.4992849','113.9205553',0,0,'2017-09-12 10:37:34',NULL),(368,4,'1','22.4992849','113.9205553',0,1,'2017-09-12 10:38:39',NULL),(369,4,'1','22.4992849','113.9205553',0,0,'2017-09-12 10:46:01',NULL),(370,4,'1','22.4992849','113.9205553',0,0,'2017-09-12 16:26:48',NULL),(371,4,'1','22.4992849','113.9205553',0,0,'2017-09-12 16:26:50',NULL),(372,4,'1','22.4992849','113.9205553',0,1,'2017-09-12 16:27:55',NULL),(373,4,'1','22.4992849','113.9205553',0,0,'2017-09-12 16:28:30',NULL),(374,4,'1','22.4992849','113.9205553',0,1,'2017-09-12 16:29:35',NULL),(375,4,'1','22.4992849','113.9205553',0,0,'2017-09-12 16:47:51',NULL),(376,4,'1','22.4992849','113.9205553',0,1,'2017-09-12 16:48:56',NULL),(377,4,'1','22.4992849','113.9205553',0,0,'2017-09-12 17:07:51',NULL),(378,4,'1','22.4992849','113.9205553',0,1,'2017-09-12 17:08:56',NULL),(379,4,'1','22.4992849','113.9205553',0,0,'2017-09-12 17:15:31',NULL),(380,4,'1','22.4992849','113.9205553',0,0,'2017-09-12 17:16:36',NULL),(381,4,'1','22.5001858','113.9202252',0,0,'2017-09-12 17:18:24',NULL),(382,4,'1','22.5001858','113.9202252',0,0,'2017-09-12 17:19:29',NULL),(383,4,'1','22.5000339','113.9204801',0,0,'2017-09-12 17:22:16',NULL),(384,4,'0','22.502973','113.916173',10,0,'2017-09-12 17:23:21',NULL),(385,4,'1','22.5000339','113.9204801',0,0,'2017-09-12 17:24:35',NULL),(386,4,'0','22.502563','113.916215',10,0,'2017-09-12 17:25:40',NULL),(387,4,'1','22.5001858','113.9202252',0,0,'2017-09-12 17:35:21',NULL),(388,4,'1','22.5000339','113.9204801',0,0,'2017-09-12 17:36:26',NULL),(389,4,'1','22.5000339','113.9204801',0,0,'2017-09-12 17:37:14',NULL),(390,4,'0','22.502592','113.916112',10,0,'2017-09-12 17:38:19',NULL),(391,4,'1','22.4992849','113.9205553',0,0,'2017-09-12 17:53:12',NULL),(392,4,'0','22.500082194011','113.920994194879',10,0,'2017-09-12 17:54:17',NULL),(393,4,'1','22.5000339','113.9204801',0,0,'2017-09-12 17:55:11',NULL),(394,4,'0','22.500014105903','113.920984157987',10,0,'2017-09-12 17:56:16',NULL),(395,4,'1','22.4992849','113.9205553',0,0,'2017-09-12 21:13:33',NULL),(396,4,'1','22.4992849','113.9205553',0,0,'2017-09-12 21:14:39',NULL),(397,4,'1','22.4992849','113.9205553',0,0,'2017-09-13 09:26:22',NULL),(398,4,'0','22.499867350261','113.921094292535',10,0,'2017-09-13 09:30:53',NULL),(399,4,'0','22.499897189671','113.921019151476',10,0,'2017-09-13 09:33:12',NULL),(400,4,'1','22.5000339','113.9204801',0,0,'2017-09-13 09:33:54',NULL),(401,4,'0','22.499818522136','113.92107123481',10,0,'2017-09-13 09:34:59',NULL),(402,4,'0','22.499817165799','113.920952148438',10,0,'2017-09-13 09:36:54',NULL),(403,4,'1','22.4992849','113.9205553',0,0,'2017-09-13 11:18:27',NULL),(404,4,'1','22.5000339','113.9204801',0,0,'2017-09-13 11:37:51',NULL),(405,4,'0','22.499840223525','113.920921223959',10,0,'2017-09-13 15:16:14',NULL),(406,4,'0','22.500746799046','113.921684299046',10,0,'2017-09-13 15:19:29',NULL),(407,4,'0','22.499877115886','113.920947265625',10,0,'2017-09-13 15:22:24',NULL),(408,4,'1','22.4992849','113.9205553',0,1,'2017-09-13 15:24:56',NULL),(409,4,'1','22.4992849','113.9205553',0,1,'2017-09-13 15:39:41',NULL),(410,4,'1','22.4992849','113.9205553',0,1,'2017-09-13 15:48:52',NULL),(411,4,'1','22.4992849','113.9205553',0,0,'2017-09-14 10:01:24',NULL),(412,4,'1','22.4992849','113.9205553',0,0,'2017-09-14 10:01:51',NULL),(413,4,'1','22.4992849','113.9205553',0,0,'2017-09-14 10:01:51',NULL),(414,4,'1','22.4992849','113.9205553',0,1,'2017-09-14 10:03:17',NULL),(415,4,'1','22.4992849','113.9205553',0,0,'2017-09-14 10:04:11',NULL),(416,4,'1','22.4992849','113.9205553',0,0,'2017-09-14 10:04:36',NULL),(417,4,'1','22.4992849','113.9205553',0,0,'2017-09-14 10:05:12',NULL),(418,4,'1','22.5003071','113.9219911',0,1,'2017-09-14 15:51:54',NULL),(419,4,'1','22.5003071','113.9219911',0,1,'2017-09-14 15:53:41',NULL),(420,4,'1','22.5003071','113.9219911',0,1,'2017-09-14 15:59:31',NULL),(421,4,'1','22.5003071','113.9219911',0,1,'2017-09-14 16:01:11',NULL),(422,4,'1','22.5003071','113.9219911',0,1,'2017-09-14 16:04:02',NULL),(423,4,'0','22.49952718099','113.920888129341',10,0,'2017-09-14 16:41:34',NULL),(424,4,'0','22.499642198351','113.920935058594',10,0,'2017-09-14 16:43:42',NULL),(425,4,'0','22.499714084202','113.920854220921',10,0,'2017-09-14 16:45:00',NULL),(426,4,'0','22.499637315539','113.920905219185',10,0,'2017-09-14 16:46:50',NULL),(427,4,'0','22.498972167969','113.920999077691',10,0,'2017-09-14 17:06:15',NULL),(428,4,'0','22.499013400608','113.921193305122',10,0,'2017-09-14 17:32:14',NULL),(429,4,'0','22.498811577691','113.921195203994',10,0,'2017-09-14 17:33:32',NULL),(430,4,'1','22.50001','113.9205732',0,1,'2017-09-14 17:53:02',NULL),(431,4,'1','22.50001','113.9205732',0,1,'2017-09-14 17:53:20',NULL),(432,4,'1','22.5003071','113.9219911',0,1,'2017-09-14 17:53:40',NULL),(433,4,'1','22.5003071','113.9219911',0,1,'2017-09-14 17:54:12',NULL),(434,4,'1','22.5003071','113.9219911',0,1,'2017-09-14 17:54:45',NULL),(435,4,'0','22.499582519532','113.921204155816',10,0,'2017-09-14 17:55:41',NULL),(436,4,'1','22.4992849','113.9205553',0,1,'2017-09-15 13:49:53',NULL),(437,4,'1','22.4992849','113.9205553',0,1,'2017-09-21 19:54:37',NULL),(438,4,'1','22.4992849','113.9205553',0,1,'2017-09-21 20:16:56',NULL),(439,4,'1','22.4992849','113.9205553',0,1,'2017-09-21 20:17:41',NULL);

/*Table structure for table `location_old` */

DROP TABLE IF EXISTS `location_old`;

CREATE TABLE `location_old` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phone` varchar(64) NOT NULL,
  `lat` varchar(64) NOT NULL,
  `lng` varchar(64) NOT NULL,
  `upload_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `location_old` */

insert  into `location_old`(`id`,`phone`,`lat`,`lng`,`upload_time`) values (1,'1231312','22.4998996','113.9217412121','2018-06-11 18:03:10'),(2,'1231312','22.4998996','113.9217451','2018-06-11 18:06:52'),(3,'1231312','22.4998996','113.9217451','2018-06-11 18:07:29');

/*Table structure for table `location_watchinfo` */

DROP TABLE IF EXISTS `location_watchinfo`;

CREATE TABLE `location_watchinfo` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `imei` varchar(128) DEFAULT NULL,
  `location_type` tinyint(2) NOT NULL COMMENT '类型，0：gps， 1：基站',
  `lat` varchar(64) NOT NULL,
  `lng` varchar(64) NOT NULL,
  `status` varchar(64) NOT NULL DEFAULT '0000',
  `upload_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `location_time` varchar(16) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=441 DEFAULT CHARSET=utf8;

/*Data for the table `location_watchinfo` */

insert  into `location_watchinfo`(`id`,`imei`,`location_type`,`lat`,`lng`,`status`,`upload_time`,`location_time`) values (440,'111111111111111',1,'22.571707','113.8613968','0000','2018-09-11 15:36:03','220414-134652');

/*Table structure for table `member_info` */

DROP TABLE IF EXISTS `member_info`;

CREATE TABLE `member_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `phone` varchar(128) NOT NULL,
  `name` varchar(128) NOT NULL,
  `imei` varchar(128) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT '2017-01-01 01:01:00',
  `updatetime` timestamp NOT NULL DEFAULT '2017-01-01 01:01:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `member_info` */

/*Table structure for table `moment_pwd_info` */

DROP TABLE IF EXISTS `moment_pwd_info`;

CREATE TABLE `moment_pwd_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `pwd` int(6) NOT NULL,
  `imei` varchar(128) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT '2017-01-01 01:01:00',
  `status` int(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `moment_pwd_info` */

/*Table structure for table `not_register_info` */

DROP TABLE IF EXISTS `not_register_info`;

CREATE TABLE `not_register_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `phone` varchar(128) NOT NULL,
  `name` varchar(128) NOT NULL,
  `imei` varchar(128) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT '2017-01-01 01:01:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

/*Data for the table `not_register_info` */

insert  into `not_register_info`(`id`,`user_id`,`phone`,`name`,`imei`,`createtime`) values (25,35,'18819214952','看看','CA:39:D5:F9:4E:8C','2017-12-11 16:23:57'),(26,35,'18819214952','鹅鹅鹅','CA:39:D5:F9:4E:8C','2017-12-11 16:29:57'),(27,35,'18819214295','咯','CA:39:D5:F9:4E:8C','2017-12-11 16:31:51'),(28,35,'15989568356','的','CA:39:D5:F9:4E:8C','2017-12-11 17:11:17'),(29,35,'15986357823','了','CA:39:D5:F9:4E:8C','2017-12-11 17:11:45'),(30,35,'15883598255','科目二','CA:39:D5:F9:4E:8C','2017-12-11 17:12:23'),(31,35,'18999656856','咯','F9:71:6B:A8:62:55','2017-12-11 17:13:16'),(32,35,'15989635987','记录','F9:71:6B:A8:62:55','2017-12-11 17:15:54'),(33,35,'15989568322','85路','CA:39:D5:F9:4E:8C','2017-12-11 18:26:24'),(34,35,'18118763557','Frank','F9:71:6B:A8:62:55','2017-12-15 18:07:24'),(35,35,'13267098103','555','CA:39:D5:F9:4E:8C','2017-12-15 18:09:00');

/*Table structure for table `olddevice_bind` */

DROP TABLE IF EXISTS `olddevice_bind`;

CREATE TABLE `olddevice_bind` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phone` varchar(64) NOT NULL,
  `imei` varchar(64) NOT NULL,
  `name` varchar(64) NOT NULL,
  `upload_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `olddevice_bind` */

insert  into `olddevice_bind`(`id`,`phone`,`imei`,`name`,`upload_time`) values (3,'18735666557','1212','特体格tet','2018-06-13 17:20:45'),(4,'18735666557','123123213','哈哈哈','2018-06-14 15:27:12');

/*Table structure for table `open_door_info` */

DROP TABLE IF EXISTS `open_door_info`;

CREATE TABLE `open_door_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `type` int(1) NOT NULL DEFAULT '1' COMMENT 'F1开锁记录F2指纹F3撬锁',
  `imei` varchar(22) NOT NULL DEFAULT '0',
  `user_id` int(10) NOT NULL DEFAULT '0' COMMENT '0为门里开锁，用户不明',
  `way` int(1) NOT NULL DEFAULT '0' COMMENT ' 0:门把开锁 1:APP开锁,2:指纹开锁,3:密码开锁4撬开',
  `side` int(1) NOT NULL DEFAULT '1' COMMENT '1门里2外面',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `name` varchar(128) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13054 DEFAULT CHARSET=utf8;

/*Data for the table `open_door_info` */

insert  into `open_door_info`(`id`,`type`,`imei`,`user_id`,`way`,`side`,`createtime`,`name`) values (13020,1,'123546',1,1,1,'2017-12-07 17:05:12',''),(13021,1,'123546',1,1,1,'2017-12-07 17:05:20',''),(13022,1,'123546',1,1,1,'2017-12-07 17:07:56',''),(13023,1,'123546',1,1,1,'2017-12-07 17:08:12',''),(13024,2,'234',1,2,2,'2017-12-07 17:09:58',''),(13029,1,'123546',1,1,1,'2017-12-08 09:57:14',''),(13030,1,'123546',1,1,1,'2017-12-08 09:57:29',''),(13031,1,'123546',1,1,1,'2017-12-08 09:58:05',''),(13037,2,'234',1,2,2,'2017-12-09 11:21:07',''),(13041,2,'234',1,2,2,'2017-12-09 11:23:54',''),(13042,2,'234',1,2,2,'2017-12-09 11:23:55',''),(13043,2,'234',1,2,2,'2017-12-09 11:23:56',''),(13044,2,'234',1,2,2,'2017-12-09 11:23:57',''),(13045,2,'234',1,2,2,'2017-12-09 11:24:00',''),(13046,2,'234',1,2,2,'2017-12-09 11:24:05',''),(13047,2,'234',1,2,2,'2017-12-09 11:24:07',''),(13048,2,'234',1,2,2,'2017-12-09 11:24:08',''),(13049,2,'234',1,2,2,'2017-12-09 11:24:08',''),(13050,2,'234',1,2,2,'2017-12-09 11:24:08',''),(13051,2,'234',1,2,2,'2017-12-09 11:24:09',''),(13052,2,'234',1,2,2,'2017-12-09 11:24:09',''),(13053,2,'234',1,2,2,'2017-12-09 11:24:09','');

/*Table structure for table `pushlog` */

DROP TABLE IF EXISTS `pushlog`;

CREATE TABLE `pushlog` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `imei` varchar(128) DEFAULT NULL,
  `target` varchar(64) DEFAULT NULL,
  `title` varchar(128) DEFAULT NULL,
  `content` text,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `type` tinyint(2) DEFAULT '0' COMMENT '类型，0：sos',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8;

/*Data for the table `pushlog` */

insert  into `pushlog`(`id`,`user_id`,`imei`,`target`,`title`,`content`,`createtime`,`type`) values (1,15,'123456789012380','4C9D4F5B010124A487C4771AC5EEFC78','SOS报警','{\"lat\":\"22.50036\",\"lng\":\"113.9221072\",\"timestamp\":1506582902000}','2017-09-28 15:15:06',0),(2,15,'123456789012380','4C9D4F5B010124A487C4771AC5EEFC78','SOS报警','{\"lat\":\"22.50036\",\"lng\":\"113.9221072\",\"timestamp\":1506584744000}','2017-09-28 15:45:46',0),(3,1,'85988233','123456','SOS报警','{\"lat\":\"22.500472\",\"lng\":\"113.9221332\",\"timestamp\":1504748897000}','2017-09-28 17:05:42',0),(4,1,'85988233','123456','SOS报警','{\"lat\":\"22.500472\",\"lng\":\"113.9221332\",\"timestamp\":1504748897000}','2017-09-28 17:05:49',0),(5,15,'123456789012380','4C9D4F5B010124A487C4771AC5EEFC78','SOS报警','{\"lat\":\"22.50036\",\"lng\":\"113.9221072\",\"timestamp\":1506590279000}','2017-09-28 17:18:01',0),(6,15,'123456789012380','4C9D4F5B010124A487C4771AC5EEFC78','SOS报警','{\"lat\":\"22.50036\",\"lng\":\"113.9221072\",\"timestamp\":1506591239000}','2017-09-28 17:34:00',0),(7,15,'123456789012380','4C9D4F5B010124A487C4771AC5EEFC78','SOS报警','{\"lat\":\"22.50036\",\"lng\":\"113.9221072\",\"timestamp\":1506591244000}','2017-09-28 17:34:05',0),(8,15,'123456789012380','4C9D4F5B010124A487C4771AC5EEFC78','SOS报警','{\"lat\":\"22.50036\",\"lng\":\"113.9221072\",\"timestamp\":1506591246000}','2017-09-28 17:34:07',0),(9,15,'123456789012380','4C9D4F5B010124A487C4771AC5EEFC78','SOS报警','{\"lat\":\"22.50036\",\"lng\":\"113.9221072\",\"timestamp\":1506591249000}','2017-09-28 17:34:10',0),(10,15,'123456789012380','4C9D4F5B010124A487C4771AC5EEFC78','SOS报警','{\"lat\":\"22.50036\",\"lng\":\"113.9221072\",\"timestamp\":1506591251000}','2017-09-28 17:34:12',0),(11,15,'123456789012380','4815B336D1EF5492C1F428084ECDE265','SOS报警','{\"lat\":\"22.50036\",\"lng\":\"113.9221072\",\"timestamp\":1506677878000}','2017-09-29 09:38:01',0),(12,23,'123456789012370','A1E288AD52BA526B5B55B29C3CE8402D','SOS报警','{\"lat\":\"22.50036\",\"lng\":\"113.9221072\",\"timestamp\":1506782298000}','2017-09-30 14:38:21',0),(13,23,'123456789012370','A1E288AD52BA526B5B55B29C3CE8402D','SOS报警','{\"lat\":\"22.50036\",\"lng\":\"113.9221072\",\"timestamp\":1506782347000}','2017-09-30 14:39:10',0),(14,22,'123456789012370','CBE640E0D63022B5A44BA4E204302899','SOS报警','{\"lat\":\"22.50036\",\"lng\":\"113.9221072\",\"timestamp\":1506787260000}','2017-09-30 16:01:01',0),(15,22,'123456789012370','CBE640E0D63022B5A44BA4E204302899','SOS报警','{\"lat\":\"22.50036\",\"lng\":\"113.9221072\",\"timestamp\":1506787266000}','2017-09-30 16:01:07',0),(16,22,'123456789012370','CBE640E0D63022B5A44BA4E204302899','SOS报警','{\"lat\":\"22.50036\",\"lng\":\"113.9221072\",\"timestamp\":1506787270000}','2017-09-30 16:01:16',0),(17,22,'123456789012370','CBE640E0D63022B5A44BA4E204302899','SOS报警','{\"lat\":\"22.50036\",\"lng\":\"113.9221072\",\"timestamp\":1506787275000}','2017-09-30 16:01:17',0),(18,22,'123456789012370','03E11735C17902988F6F8DACAB917176','SOS报警','{\"lat\":\"22.50036\",\"lng\":\"113.9221072\",\"timestamp\":1506787603000}','2017-09-30 16:06:44',0),(19,22,'123456789012370','03E11735C17902988F6F8DACAB917176','SOS报警','{\"lat\":\"22.50036\",\"lng\":\"113.9221072\",\"timestamp\":1506787639000}','2017-09-30 16:07:21',0),(20,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4992849\",\"lng\":\"113.9205553\",\"timestamp\":1507617189000}','2017-10-10 10:33:11',0),(21,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4992849\",\"lng\":\"113.9205553\",\"timestamp\":1507617962000}','2017-10-10 10:46:03',0),(22,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4992849\",\"lng\":\"113.9205553\",\"timestamp\":1507617967000}','2017-10-10 10:46:11',0),(23,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4992849\",\"lng\":\"113.9205553\",\"timestamp\":1507617970000}','2017-10-10 10:46:12',0),(24,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4992849\",\"lng\":\"113.9205553\",\"timestamp\":1507618002000}','2017-10-10 10:46:43',0),(25,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4992849\",\"lng\":\"113.9205553\",\"timestamp\":1507618017000}','2017-10-10 10:46:58',0),(26,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4992849\",\"lng\":\"113.9205553\",\"timestamp\":1507618561000}','2017-10-10 10:56:02',0),(27,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4992849\",\"lng\":\"113.9205553\",\"timestamp\":1507618566000}','2017-10-10 10:56:07',0),(28,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4992849\",\"lng\":\"113.9205553\",\"timestamp\":1507618569000}','2017-10-10 10:56:11',0),(29,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4992849\",\"lng\":\"113.9205553\",\"timestamp\":1507618713000}','2017-10-10 10:58:35',0),(30,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4992849\",\"lng\":\"113.9205553\",\"timestamp\":1507618759000}','2017-10-10 10:59:21',0),(31,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4992849\",\"lng\":\"113.9205553\",\"timestamp\":1507618784000}','2017-10-10 10:59:47',0),(32,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4992849\",\"lng\":\"113.9205553\",\"timestamp\":1507618817000}','2017-10-10 11:00:19',0),(33,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4992849\",\"lng\":\"113.9205553\",\"timestamp\":1507618836000}','2017-10-10 11:00:37',0),(34,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4992849\",\"lng\":\"113.9205553\",\"timestamp\":1507618844000}','2017-10-10 11:00:45',0),(35,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4992849\",\"lng\":\"113.9205553\",\"timestamp\":1507618852000}','2017-10-10 11:00:54',0),(36,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4992849\",\"lng\":\"113.9205553\",\"timestamp\":1507618854000}','2017-10-10 11:00:56',0),(37,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4992849\",\"lng\":\"113.9205553\",\"timestamp\":1507619665000}','2017-10-10 11:14:27',0),(38,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4998842\",\"lng\":\"113.9206462\",\"timestamp\":1507619723000}','2017-10-10 11:15:29',0),(39,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4998842\",\"lng\":\"113.9206462\",\"timestamp\":1507619757000}','2017-10-10 11:16:02',0),(40,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4998842\",\"lng\":\"113.9206462\",\"timestamp\":1507620187000}','2017-10-10 11:23:12',0),(41,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4992849\",\"lng\":\"113.9205553\",\"timestamp\":1507620658000}','2017-10-10 11:30:59',0),(42,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4992849\",\"lng\":\"113.9205553\",\"timestamp\":1507620670000}','2017-10-10 11:31:11',0),(43,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4992849\",\"lng\":\"113.9205553\",\"timestamp\":1507620749000}','2017-10-10 11:32:30',0),(44,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4992849\",\"lng\":\"113.9205553\",\"timestamp\":1507620752000}','2017-10-10 11:32:33',0),(45,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4992849\",\"lng\":\"113.9205553\",\"timestamp\":1507620767000}','2017-10-10 11:32:48',0),(46,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4992849\",\"lng\":\"113.9205553\",\"timestamp\":1507620774000}','2017-10-10 11:32:55',0),(47,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4992849\",\"lng\":\"113.9205553\",\"timestamp\":1507620777000}','2017-10-10 11:32:58',0),(48,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4992849\",\"lng\":\"113.9205553\",\"timestamp\":1507620781000}','2017-10-10 11:33:03',0),(49,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4992849\",\"lng\":\"113.9205553\",\"timestamp\":1507620786000}','2017-10-10 11:33:08',0),(50,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4992849\",\"lng\":\"113.9205553\",\"timestamp\":1507620794000}','2017-10-10 11:33:16',0),(51,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4992849\",\"lng\":\"113.9205553\",\"timestamp\":1507620812000}','2017-10-10 11:33:33',0),(52,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4992849\",\"lng\":\"113.9205553\",\"timestamp\":1507621044000}','2017-10-10 11:37:25',0),(53,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4992849\",\"lng\":\"113.9205553\",\"timestamp\":1507636134000}','2017-10-10 15:48:56',0),(54,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4998842\",\"lng\":\"113.9206462\",\"timestamp\":1507636417000}','2017-10-10 15:54:00',0),(55,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4998842\",\"lng\":\"113.9206462\",\"timestamp\":1507636435000}','2017-10-10 15:54:00',0),(56,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4992849\",\"lng\":\"113.9205553\",\"timestamp\":1507636555000}','2017-10-10 15:55:56',0),(57,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4992849\",\"lng\":\"113.9205553\",\"timestamp\":1507636817000}','2017-10-10 16:00:19',0),(58,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4992849\",\"lng\":\"113.9205553\",\"timestamp\":1507636820000}','2017-10-10 16:00:21',0),(59,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4992849\",\"lng\":\"113.9205553\",\"timestamp\":1507636823000}','2017-10-10 16:00:24',0),(60,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4992849\",\"lng\":\"113.9205553\",\"timestamp\":1507636867000}','2017-10-10 16:01:08',0),(61,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4992849\",\"lng\":\"113.9205553\",\"timestamp\":1507636962000}','2017-10-10 16:02:44',0),(62,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4992849\",\"lng\":\"113.9205553\",\"timestamp\":1507637102000}','2017-10-10 16:05:04',0),(63,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4992849\",\"lng\":\"113.9205553\",\"timestamp\":1507637252000}','2017-10-10 16:07:34',0),(64,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4992849\",\"lng\":\"113.9205553\",\"timestamp\":1507637674000}','2017-10-10 16:14:35',0),(65,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4998842\",\"lng\":\"113.9206462\",\"timestamp\":1507637944000}','2017-10-10 16:19:06',0),(66,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4992849\",\"lng\":\"113.9205553\",\"timestamp\":1507637968000}','2017-10-10 16:19:30',0),(67,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4992849\",\"lng\":\"113.9205553\",\"timestamp\":1507638024000}','2017-10-10 16:20:28',0),(68,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4992849\",\"lng\":\"113.9205553\",\"timestamp\":1507639035000}','2017-10-10 16:37:17',0),(69,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4992849\",\"lng\":\"113.9205553\",\"timestamp\":1507643851000}','2017-10-10 17:57:33',0),(70,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4992849\",\"lng\":\"113.9205553\",\"timestamp\":1507643863000}','2017-10-10 17:57:45',0),(71,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4992849\",\"lng\":\"113.9205553\",\"timestamp\":1507643880000}','2017-10-10 17:58:02',0),(72,27,'869758001213399','6DD45E67C6F9D0E63C239489DB135472','SOS报警','{\"lat\":\"22.4992849\",\"lng\":\"113.9205553\",\"timestamp\":1507643888000}','2017-10-10 17:58:09',0),(73,15,'123456789012380','7C6B471A849C0179D4E5B39FFA104CFC','SOS报警','{\"lat\":\"22.500482\",\"lng\":\"113.9221501\",\"timestamp\":1507905631000}','2017-10-13 14:40:34',0),(74,28,'869758001213282','781C7DA861A4EF94921B54F4ED8A0051','SOS报警','{\"lat\":\"22.500392\",\"lng\":\"113.9221053\",\"timestamp\":1508341961000}','2017-10-18 15:52:47',0),(75,28,'869758001213282','781C7DA861A4EF94921B54F4ED8A0051','SOS报警','{\"lat\":\"22.500392\",\"lng\":\"113.9221053\",\"timestamp\":1508342022000}','2017-10-18 15:53:58',0),(76,22,'869758001213175','D903C9DEFBD1B36B29B20FF80BB8AF91','SOS报警','{\"lat\":\"22.500392\",\"lng\":\"113.9221053\",\"timestamp\":1509040669000}','2017-10-26 18:11:11',0),(77,22,'869758001213175','D903C9DEFBD1B36B29B20FF80BB8AF91','SOS报警','{\"lat\":\"22.500392\",\"lng\":\"113.9221053\",\"timestamp\":1509041611000}','2017-10-26 18:13:34',0),(78,22,'869758001213175','D903C9DEFBD1B36B29B20FF80BB8AF91','SOS报警','{\"lat\":\"22.500392\",\"lng\":\"113.9221053\",\"timestamp\":1509042891000}','2017-10-26 18:34:57',0),(79,22,'869758001213175','D903C9DEFBD1B36B29B20FF80BB8AF91','SOS报警','{\"lat\":\"22.500392\",\"lng\":\"113.9221053\",\"timestamp\":1509042991000}','2017-10-26 18:36:33',0),(80,18,'123456789012350','8FA921B785B281970F47B0D78CE613F6','SOS报警','{\"lat\":\"22.500392\",\"lng\":\"113.9221053\",\"timestamp\":1509111413000}','2017-10-27 13:36:56',0),(81,18,'123456789012350','8FA921B785B281970F47B0D78CE613F6','SOS报警','{\"lat\":\"22.500392\",\"lng\":\"113.9221053\",\"timestamp\":1509111415000}','2017-10-27 13:36:57',0),(82,18,'123456789012350','8FA921B785B281970F47B0D78CE613F6','SOS报警','{\"lat\":\"22.500392\",\"lng\":\"113.9221053\",\"timestamp\":1509111418000}','2017-10-27 13:36:59',0),(83,18,'123456789012350','8FA921B785B281970F47B0D78CE613F6','SOS报警','{\"lat\":\"22.500392\",\"lng\":\"113.9221053\",\"timestamp\":1509120210000}','2017-10-27 16:03:33',0),(84,18,'123456789012350','8FA921B785B281970F47B0D78CE613F6','SOS报警','{\"lat\":\"22.500392\",\"lng\":\"113.9221053\",\"timestamp\":1509120247000}','2017-10-27 16:04:08',0),(85,18,'123456789012350','8FA921B785B281970F47B0D78CE613F6','SOS报警','{\"lat\":\"22.500392\",\"lng\":\"113.9221053\",\"timestamp\":1509120342000}','2017-10-27 16:05:44',0),(86,18,'123456789012350','8FA921B785B281970F47B0D78CE613F6','SOS报警','{\"lat\":\"22.500392\",\"lng\":\"113.9221053\",\"timestamp\":1509123441000}','2017-10-27 16:57:23',0),(87,22,'869758001213175','D903C9DEFBD1B36B29B20FF80BB8AF91','SOS报警','{\"lat\":\"22.500392\",\"lng\":\"113.9221053\",\"timestamp\":1509361188000}','2017-10-30 10:59:51',0),(88,22,'869758001213175','D903C9DEFBD1B36B29B20FF80BB8AF91','SOS报警','{\"lat\":\"22.500392\",\"lng\":\"113.9221053\",\"timestamp\":1509361320000}','2017-10-30 11:02:02',0),(89,22,'869758001213175','D903C9DEFBD1B36B29B20FF80BB8AF91','SOS报警','{\"lat\":\"22.500392\",\"lng\":\"113.9221053\",\"timestamp\":1509365203000}','2017-10-30 12:06:46',0),(90,22,'869758001213175','B3D8A9EA4509416F4197EB720E22CF34','SOS报警','{\"lat\":\"22.500392\",\"lng\":\"113.9221053\",\"timestamp\":1509469950000}','2017-10-31 17:12:31',0),(91,16,'123456789012330','FE8D482CE0F0B22B433243A2A2576EFA','SOS报警','{\"lat\":\"22.500392\",\"lng\":\"113.9221053\",\"timestamp\":1509973732000}','2017-11-06 17:11:08',0),(92,1,'CA:39:D5:F9:4E:8C','123456','开锁','{\"content\":\"门锁imei为CA:39:D5:F9:4E:8C被名字叫的使用门把在门外打开!\",\"imei\":\"CA:39:D5:F9:4E:8C\",\"name\":\"\",\"side\":2,\"timestamp\":1515985848630,\"way\":67}','2018-01-15 11:10:50',0);

/*Table structure for table `pwd_info` */

DROP TABLE IF EXISTS `pwd_info`;

CREATE TABLE `pwd_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `pwd` varchar(128) NOT NULL,
  `imei` varchar(128) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT '2017-01-01 01:01:00',
  `updatetime` timestamp NOT NULL DEFAULT '2017-01-01 01:01:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `pwd_info` */

/*Table structure for table `sensitive_point` */

DROP TABLE IF EXISTS `sensitive_point`;

CREATE TABLE `sensitive_point` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `lat` varchar(64) NOT NULL,
  `lng` varchar(64) NOT NULL,
  `radius` int(10) NOT NULL DEFAULT '0',
  `status` int(2) NOT NULL DEFAULT '0',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` timestamp NULL DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

/*Data for the table `sensitive_point` */

insert  into `sensitive_point`(`id`,`user_id`,`lat`,`lng`,`radius`,`status`,`createtime`,`updatetime`,`name`) values (6,16,'39.95648611137709','116.39590188860895',3690,2,'2017-10-22 15:53:57','2017-10-27 22:41:20','景山公园'),(15,15,'0.000000','0.000000',1000,0,'2017-10-25 15:05:42','2017-10-25 15:05:42','Q'),(19,24,'39.9533341905972','116.41509041190149',5000,0,'2017-11-06 09:27:22','2017-11-06 09:29:09','地坛'),(21,24,'39.92723222816159','116.3877171278',2000,0,'2017-11-06 09:28:26','2017-11-06 09:28:26','北海公园'),(22,16,'39.93574717889407','116.40283808112146',555,2,'2017-11-06 14:21:09','2017-11-06 14:21:09','rgh'),(23,24,'39.97930904268467','116.4492617547512',2000,0,'2017-11-06 14:54:56','2017-11-06 14:54:56','啦啦'),(24,18,'0.000000','0.000000',20,0,'2017-11-06 15:13:53','2017-11-06 15:13:53','vbbb');

/*Table structure for table `sensitive_point_log` */

DROP TABLE IF EXISTS `sensitive_point_log`;

CREATE TABLE `sensitive_point_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `sp_id` int(11) NOT NULL,
  `imei` varchar(128) DEFAULT NULL,
  `lat` varchar(64) NOT NULL,
  `lng` varchar(64) NOT NULL,
  `radius` int(10) NOT NULL DEFAULT '0',
  `lat1` varchar(64) NOT NULL,
  `lng1` varchar(64) NOT NULL,
  `status` int(2) NOT NULL DEFAULT '0',
  `content` varchar(128) DEFAULT NULL,
  `upload_time` timestamp NULL DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `sensitive_point_log` */

insert  into `sensitive_point_log`(`id`,`user_id`,`sp_id`,`imei`,`lat`,`lng`,`radius`,`lat1`,`lng1`,`status`,`content`,`upload_time`,`createtime`) values (1,16,6,'123456789012330','39.95648611137709','116.39590188860895',3690,'22.500392','113.9221053',2,'设备离开敏感区域','2017-11-06 21:08:52','2017-11-06 17:11:08'),(2,16,22,'123456789012330','39.93574717889407','116.40283808112146',555,'22.500392','113.9221053',2,'设备离开敏感区域','2017-11-06 21:08:52','2017-11-06 17:11:08');

/*Table structure for table `smslog` */

DROP TABLE IF EXISTS `smslog`;

CREATE TABLE `smslog` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `mobile` varchar(13) NOT NULL,
  `tpl_code` varchar(20) NOT NULL,
  `tpl_param` text NOT NULL,
  `rstatus` int(4) NOT NULL DEFAULT '0',
  `rmsg` text,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8;

/*Data for the table `smslog` */

insert  into `smslog`(`id`,`name`,`mobile`,`tpl_code`,`tpl_param`,`rstatus`,`rmsg`,`createtime`) values (1,'短信验证码','15210136945','SMS_98965016','{\"number\":\"1561\"}',0,'OK','2017-10-23 22:36:25'),(2,'短信验证码','15210136945','SMS_98965016','{\"number\":\"2705\"}',0,'OK','2017-10-23 22:54:05'),(3,'短信验证码','13471253804','SMS_98965016','{\"number\":\"9346\"}',0,'OK','2017-10-24 13:53:21'),(4,'短信验证码','13471253804','SMS_98965016','{\"number\":\"1216\"}',0,'OK','2017-10-24 13:54:32'),(5,'短信验证码','18575508620','SMS_98965016','{\"number\":\"6099\"}',0,'OK','2017-10-24 16:41:57'),(6,'短信验证码','18575508620','SMS_98965016','{\"number\":\"5946\"}',0,'OK','2017-10-24 17:59:03'),(7,'电子围栏报警','18575508620','SMS_99420011','{\"type\":\"离开\", \"time\":\"2017-10-25 02:12:02\"}',0,'OK','2017-10-24 18:12:03'),(8,'短信验证码','18516971225','SMS_98965016','{\"number\":\"4637\"}',0,'OK','2017-10-25 14:06:09'),(9,'SOS报警','13471253804','SMS_99390009','{\"number\":\"2017-10-27 01:57:49\"}',0,'OK','2017-10-26 18:11:11'),(10,'SOS报警','13471253804','SMS_99390009','{\"number\":\"2017-10-27 01:57:49\"}',0,'OK','2017-10-26 18:11:11'),(11,'SOS报警','13471253804','SMS_99390009','{\"number\":\"2017-10-27 02:13:31\"}',0,'OK','2017-10-26 18:13:34'),(12,'SOS报警','13471253804','SMS_99390009','{\"number\":\"2017-10-27 02:13:31\"}',0,'OK','2017-10-26 18:13:34'),(13,'SOS报警','13471253804','SMS_99390009','{\"number\":\"2017-10-27 02:34:51\"}',0,'OK','2017-10-26 18:34:58'),(14,'SOS报警','13471253804','SMS_99390009','{\"number\":\"2017-10-27 02:34:51\"}',0,'OK','2017-10-26 18:34:58'),(15,'SOS报警','13471253804','SMS_99390009','{\"number\":\"2017-10-27 02:36:31\"}',0,'OK','2017-10-26 18:36:33'),(16,'SOS报警','13471253804','SMS_99390009','{\"number\":\"2017-10-27 02:36:31\"}',0,'OK','2017-10-26 18:36:33'),(17,'SOS报警','18575508620','SMS_99390009','{\"number\":\"2017-10-27 21:36:53\"}',0,'OK','2017-10-27 13:36:56'),(18,'SOS报警','18575508620','SMS_99390009','{\"number\":\"2017-10-27 21:36:53\"}',0,'OK','2017-10-27 13:36:56'),(19,'SOS报警','18575508620','SMS_99390009','{\"number\":\"2017-10-27 21:36:55\"}',0,'OK','2017-10-27 13:36:58'),(20,'SOS报警','18575508620','SMS_99390009','{\"number\":\"2017-10-27 21:36:55\"}',0,'OK','2017-10-27 13:36:58'),(21,'SOS报警','18575508620','SMS_99390009','{\"number\":\"2017-10-27 21:36:58\"}',0,'OK','2017-10-27 13:36:59'),(22,'SOS报警','18575508620','SMS_99390009','{\"number\":\"2017-10-27 21:36:58\"}',0,'OK','2017-10-27 13:37:00'),(23,'短信验证码','18516971225','SMS_98965016','{\"number\":\"7558\"}',0,'OK','2017-10-27 15:58:40'),(24,'SOS报警','18575508620','SMS_99390009','{\"number\":\"2017-10-28 00:03:30\"}',0,'OK','2017-10-27 16:03:33'),(25,'SOS报警','18575508620','SMS_99390009','{\"number\":\"2017-10-28 00:03:30\"}',0,'OK','2017-10-27 16:03:33'),(26,'SOS报警','18575508620','SMS_99390009','{\"number\":\"2017-10-28 00:04:07\"}',0,'OK','2017-10-27 16:04:08'),(27,'SOS报警','18575508620','SMS_99390009','{\"number\":\"2017-10-28 00:04:07\"}',0,'OK','2017-10-27 16:04:09'),(28,'SOS报警','18575508620','SMS_99390009','{\"number\":\"2017-10-28 00:05:42\"}',0,'OK','2017-10-27 16:05:44'),(29,'SOS报警','18575508620','SMS_99390009','{\"number\":\"2017-10-28 00:05:42\"}',0,'OK','2017-10-27 16:05:44'),(30,'短信验证码','15626598828','SMS_98965016','{\"number\":\"1869\"}',0,'OK','2017-10-27 16:55:09'),(31,'SOS报警','18575508620','SMS_99390009','{\"number\":\"2017-10-28 00:57:21\"}',0,'OK','2017-10-27 16:57:24'),(32,'SOS报警','18575508620','SMS_99390009','{\"number\":\"2017-10-28 00:57:21\"}',0,'OK','2017-10-27 16:57:24'),(33,'短信验证码','15989568358','SMS_98965016','{\"number\":\"7821\"}',0,'OK','2017-10-27 17:41:44'),(34,'短信验证码','18819218501','SMS_98965016','{\"number\":\"2873\"}',0,'OK','2017-10-28 00:07:36'),(35,'短信验证码','18819218501','SMS_98965016','{\"number\":\"6261\"}',0,'触发分钟级流控Permits:1','2017-10-28 00:07:51'),(36,'短信验证码','18819218501','SMS_98965016','{\"number\":\"6240\"}',0,'OK','2017-10-28 00:09:07'),(37,'短信验证码','18819218501','SMS_98965016','{\"number\":\"4626\"}',0,'OK','2017-10-28 21:08:50'),(38,'SOS报警','13471253804','SMS_99390009','{\"number\":\"2017-10-30 18:59:48\"}',0,'OK','2017-10-30 10:59:51'),(39,'SOS报警','13471253804','SMS_99390009','{\"number\":\"2017-10-30 18:59:48\"}',0,'OK','2017-10-30 10:59:51'),(40,'SOS报警','13471253804','SMS_99390009','{\"number\":\"2017-10-30 19:02:00\"}',0,'OK','2017-10-30 11:02:02'),(41,'SOS报警','13471253804','SMS_99390009','{\"number\":\"2017-10-30 19:02:00\"}',0,'OK','2017-10-30 11:02:03'),(42,'短信验证码','18516971225','SMS_98965016','{\"number\":\"8800\"}',0,'OK','2017-10-30 11:42:08'),(43,'SOS报警','13471253804','SMS_99390009','{\"number\":\"2017-10-30 20:06:43\"}',0,'OK','2017-10-30 12:06:46'),(44,'SOS报警','13471253804','SMS_99390009','{\"number\":\"2017-10-30 20:06:43\"}',0,'OK','2017-10-30 12:06:46'),(45,'短信验证码','15989568358','SMS_98965016','{\"number\":\"2716\"}',0,'OK','2017-10-30 15:19:33'),(46,'短信验证码','15989568358','SMS_98965016','{\"number\":\"4393\"}',0,'触发分钟级流控Permits:1','2017-10-30 15:20:22'),(47,'短信验证码','15989568358','SMS_98965016','{\"number\":\"9642\"}',0,'OK','2017-10-30 15:21:50'),(48,'短信验证码','18575508620','SMS_98965016','{\"number\":\"0742\"}',0,'OK','2017-10-30 15:33:10'),(49,'短信验证码','15626598828','SMS_98965016','{\"number\":\"0728\"}',0,'OK','2017-10-30 15:42:45'),(50,'短信验证码','18516971225','SMS_98965016','{\"number\":\"7762\"}',0,'OK','2017-10-30 16:24:00'),(51,'短信验证码','15989568358','SMS_98965016','{\"number\":\"2554\"}',0,'OK','2017-10-30 16:33:29'),(52,'短信验证码','15989568358','SMS_98965016','{\"number\":\"0781\"}',0,'OK','2017-10-30 16:42:15'),(53,'短信验证码','18575508620','SMS_98965016','{\"number\":\"5310\"}',0,'OK','2017-10-30 17:14:01'),(54,'短信验证码','18575508620','SMS_98965016','{\"number\":\"2940\"}',0,'OK','2017-10-30 17:43:18'),(55,'短信验证码','13471253804','SMS_98965016','{\"number\":\"8902\"}',0,'OK','2017-10-31 10:45:03'),(56,'短信验证码','18575508620','SMS_98965016','{\"number\":\"7720\"}',0,'OK','2017-10-31 12:33:10'),(57,'SOS报警','13471253804','SMS_99390009','{\"number\":\"2017-11-01 01:12:30\"}',0,'OK','2017-10-31 17:12:31'),(58,'SOS报警','13471253804','SMS_99390009','{\"number\":\"2017-11-01 01:12:30\"}',0,'OK','2017-10-31 17:12:31'),(59,'短信验证码','15626598828','SMS_98965016','{\"number\":\"2158\"}',0,'OK','2017-11-01 13:47:36'),(60,'短信验证码','18516971225','SMS_98965016','{\"number\":\"6578\"}',0,'OK','2017-11-01 16:55:31'),(61,'短信验证码','13471253804','SMS_98965016','{\"number\":\"0268\"}',0,'OK','2017-11-01 18:05:00'),(62,'短信验证码','15626598828','SMS_98965016','{\"number\":\"7698\"}',0,'触发小时级流控Permits:5','2017-11-02 14:55:05'),(63,'短信验证码','15626598828','SMS_98965016','{\"number\":\"9169\"}',0,'触发天级流控Permits:10','2017-11-02 16:12:16'),(64,'短信验证码','13471253804','SMS_98965016','{\"number\":\"8494\"}',0,'OK','2017-11-02 16:13:20'),(65,'短信验证码','13471253804','SMS_98965016','{\"number\":\"0798\"}',0,'OK','2017-11-03 10:03:00'),(66,'短信验证码','15626598828','SMS_98965016','{\"number\":\"9299\"}',0,'OK','2017-11-03 10:15:30'),(67,'短信验证码','18516971225','SMS_98965016','{\"number\":\"8830\"}',0,'OK','2017-11-03 14:57:18'),(68,'短信验证码','2','SMS_98965016','{\"number\":\"6442\"}',0,'2invalid mobile number','2017-11-03 20:22:08'),(69,'短信验证码','18516971225','SMS_98965016','{\"number\":\"5996\"}',0,'OK','2017-11-05 18:10:32'),(70,'短信验证码','18516971225','SMS_98965016','{\"number\":\"0610\"}',0,'OK','2017-11-05 18:16:51'),(71,'短信验证码','15210136945','SMS_98965016','{\"number\":\"6980\"}',0,'OK','2017-11-06 08:55:15'),(72,'短信验证码','15210136945','SMS_98965016','{\"number\":\"2691\"}',0,'OK','2017-11-06 09:37:24'),(73,'短信验证码','15210136945','SMS_98965016','{\"number\":\"3024\"}',0,'OK','2017-11-06 09:39:35'),(74,'短信验证码','18516971225','SMS_98965016','{\"number\":\"4015\"}',0,'OK','2017-11-06 09:47:14'),(75,'短信验证码','18516971225','SMS_98965016','{\"number\":\"0219\"}',0,'OK','2017-11-06 09:51:56'),(76,'短信验证码','18516971225','SMS_98965016','{\"number\":\"1906\"}',0,'OK','2017-11-06 09:53:42'),(77,'短信验证码','15626598828','SMS_98965016','{\"number\":\"2444\"}',0,'OK','2017-11-06 12:30:33'),(78,'短信验证码','18516971225','SMS_98965016','{\"number\":\"7477\"}',0,'OK','2017-11-06 14:50:37'),(79,'短信验证码','15210136945','SMS_98965016','{\"number\":\"8701\"}',0,'OK','2017-11-06 14:55:38'),(80,'短信验证码','15210136945','SMS_98965016','{\"number\":\"9126\"}',0,'OK','2017-11-06 14:57:13'),(81,'短信验证码','15210136945','SMS_98965016','{\"number\":\"6802\"}',0,'OK','2017-11-06 14:59:47'),(82,'短信验证码','18575508620','SMS_98965016','{\"number\":\"7323\"}',0,'OK','2017-11-06 15:10:52'),(83,'短信验证码','18575508620','SMS_98965016','{\"number\":\"6383\"}',0,'触发分钟级流控Permits:1','2017-11-06 15:11:21'),(84,'短信验证码','18575508620','SMS_98965016','{\"number\":\"3282\"}',0,'OK','2017-11-06 15:12:28'),(85,'短信验证码','18516971225','SMS_98965016','{\"number\":\"5743\"}',0,'OK','2017-11-06 15:31:00'),(86,'短信验证码','18516971225','SMS_98965016','{\"number\":\"9815\"}',0,'触发分钟级流控Permits:1','2017-11-06 15:31:44'),(87,'短信验证码','15989568358','SMS_98965016','{\"number\":\"4569\"}',0,'OK','2017-11-06 15:40:13'),(88,'短信验证码','15989568358','SMS_98965016','{\"number\":\"8203\"}',0,'OK','2017-11-06 15:43:58'),(89,'电子围栏报警','18511581610','SMS_99420011','{\"type\":\"离开\", \"time\":\"2017-11-06 21:08:52\"}',0,'OK','2017-11-06 17:11:08'),(90,'敏感区域报警','18511581610','SMS_107920086','{\"type\":\"离开\", \"time\":\"2017-11-06 21:08:52\"}',0,'OK','2017-11-06 17:11:08'),(91,'敏感区域报警','18511581610','SMS_107920086','{\"type\":\"离开\", \"time\":\"2017-11-06 21:08:52\"}',0,'OK','2017-11-06 17:11:08'),(92,'SOS报警','18511581610','SMS_99390009','{\"number\":\"2017-11-06 21:08:52\"}',0,'OK','2017-11-06 17:11:08'),(93,'SOS报警','18511581610','SMS_99390009','{\"number\":\"2017-11-06 21:08:52\"}',0,'OK','2017-11-06 17:11:09'),(94,'短信验证码','15989568358','SMS_98965016','{\"number\":\"4215\"}',0,'OK','2017-12-05 17:04:15'),(95,'短信验证码','15989568358','SMS_98965016','{\"number\":\"4447\"}',0,'OK','2017-12-05 17:23:46'),(96,'短信验证码','15989568358','SMS_98965016','{\"number\":\"9407\"}',0,'触发小时级流控Permits:5','2017-12-05 17:27:22'),(97,'短信验证码','15989568358','SMS_98965016','{\"number\":\"1167\"}',0,'触发小时级流控Permits:5','2017-12-05 17:28:14'),(98,'短信验证码','15989568358','SMS_98965016','{\"number\":\"8275\"}',0,'OK','2017-12-06 09:12:32'),(99,'短信验证码','13267098103','SMS_98965016','{\"number\":\"5596\"}',0,'OK','2017-12-06 09:14:41'),(100,'短信验证码','15989568358','SMS_98965016','{\"number\":\"6688\"}',0,'OK','2017-12-06 13:53:16'),(101,'短信验证码','15989568358','SMS_98965016','{\"number\":\"6142\"}',0,'OK','2017-12-06 14:14:57'),(102,'短信验证码','15989568358','SMS_98965016','{\"number\":\"9250\"}',0,'OK','2017-12-06 15:37:29'),(103,'短信验证码','18735662247','SMS_115095090','3444',0,'OK','2017-12-06 16:34:35'),(104,'短信验证码','18735662247','SMS_115095090','{\"number\":\"3665\"}',0,'OK','2017-12-07 14:48:20'),(105,'短信验证码','15989568358','SMS_115095090','{\"number\":\"9867\"}',0,'OK','2017-12-07 15:51:42'),(106,'短信验证码','15989568358','SMS_115095090','{\"number\":\"0073\"}',0,'OK','2017-12-07 16:13:07'),(107,'短信验证码','18735662247','SMS_115095090','{\"number\":\"0904\"}',0,'OK','2017-12-08 18:41:26'),(108,'短信验证码','15989568358','SMS_115095090','{\"number\":\"1589\"}',0,'OK','2017-12-11 09:54:07'),(109,'短信验证码','15989568358','SMS_115095090','{\"number\":\"3418\"}',0,'OK','2017-12-11 11:39:50'),(110,'短信验证码','13267098103','SMS_115095090','{\"number\":\"5058\"}',0,'OK','2017-12-15 18:11:54'),(111,'短信验证码','18118763557','SMS_115095090','{\"number\":\"8361\"}',0,'OK','2017-12-20 11:03:01');

/*Table structure for table `sos_white_list` */

DROP TABLE IF EXISTS `sos_white_list`;

CREATE TABLE `sos_white_list` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `phone` varchar(128) NOT NULL,
  `name` varchar(128) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT '2017-01-01 01:01:00',
  `updatetime` timestamp NOT NULL DEFAULT '2017-01-01 01:01:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8;

/*Data for the table `sos_white_list` */

insert  into `sos_white_list`(`id`,`user_id`,`phone`,`name`,`createtime`,`updatetime`) values (3,18,'13475758210','jsksk','2017-09-29 14:07:15','2017-09-29 14:07:15'),(8,18,'18574407850','少男 啊','2017-09-29 15:30:32','2017-09-29 15:30:32'),(9,18,'18556559880','cgvb','2017-09-29 16:40:20','2017-09-29 16:40:20'),(11,19,'18819214925','name','2017-09-29 16:57:49','2017-09-29 16:57:49'),(12,19,'13922826529','xin','2017-09-29 17:04:44','2017-09-29 17:04:44'),(24,19,'15989568358','xdax','2017-09-30 13:34:00','2017-09-30 13:34:00'),(25,19,'15989147852','xx','2017-09-30 13:34:25','2017-09-30 13:34:25'),(30,23,'18735662247','啊啊啊啊','2017-09-30 14:19:07','2017-09-30 14:19:07'),(39,17,'15989568358','xdx','2017-09-30 15:52:59','2017-09-30 15:52:59'),(40,17,'15989568359','ghh','2017-09-30 15:54:27','2017-09-30 15:54:27'),(41,17,'15989568352','mjjk','2017-09-30 15:55:31','2017-09-30 15:55:31'),(44,17,'13922826529','xxc','2017-09-30 16:01:02','2017-09-30 16:01:02'),(47,22,'18735662247','特','2017-09-30 16:06:26','2017-09-30 16:06:26'),(49,20,'13484946593','gh','2017-09-30 16:21:23','2017-09-30 16:21:23'),(51,16,'18511581610','李伟','2017-09-30 19:55:27','2017-09-30 19:55:27'),(66,27,'13715391419','花湖嗨嗨霸爱','2017-10-10 16:32:16','2017-10-10 16:32:16'),(67,28,'15626598828','Bill','2017-10-18 15:14:52','2017-10-18 15:14:52');

/*Table structure for table `soslog` */

DROP TABLE IF EXISTS `soslog`;

CREATE TABLE `soslog` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `imei` varchar(128) DEFAULT NULL,
  `lat` varchar(64) DEFAULT NULL,
  `lng` varchar(64) DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `createtimeStr` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

/*Data for the table `soslog` */

insert  into `soslog`(`id`,`user_id`,`imei`,`lat`,`lng`,`createtime`,`createtimeStr`) values (1,22,'869758001213175','22.500392','113.9221053','2017-10-26 18:11:11',NULL),(2,22,'869758001213175','22.500392','113.9221053','2017-10-26 18:13:34',NULL),(3,22,'869758001213175','22.500392','113.9221053','2017-10-26 18:34:58',NULL),(4,22,'869758001213175','22.500392','113.9221053','2017-10-26 18:36:33',NULL),(5,18,'123456789012350','22.500392','113.9221053','2017-10-27 13:36:56',NULL),(6,18,'123456789012350','22.500392','113.9221053','2017-10-27 13:36:58',NULL),(7,18,'123456789012350','22.500392','113.9221053','2017-10-27 13:37:00',NULL),(8,18,'123456789012350','22.500392','113.9221053','2017-10-27 16:03:33',NULL),(9,18,'123456789012350','22.500392','113.9221053','2017-10-27 16:04:09',NULL),(10,18,'123456789012350','22.500392','113.9221053','2017-10-27 16:05:44',NULL),(11,18,'123456789012350','22.500392','113.9221053','2017-10-27 16:57:24',NULL),(12,22,'869758001213175','22.500392','113.9221053','2017-10-30 10:59:51',NULL),(13,22,'869758001213175','22.500392','113.9221053','2017-10-30 11:02:03',NULL),(14,22,'869758001213175','22.500392','113.9221053','2017-10-30 12:06:46',NULL),(15,22,'869758001213175','22.500392','113.9221053','2017-10-31 17:12:31',NULL),(16,16,'123456789012330','22.500392','113.9221053','2017-11-06 17:11:09',NULL);

/*Table structure for table `step` */

DROP TABLE IF EXISTS `step`;

CREATE TABLE `step` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `imei` varchar(128) DEFAULT NULL,
  `step_number` int(11) NOT NULL DEFAULT '0',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `step` */

/*Table structure for table `sys` */

DROP TABLE IF EXISTS `sys`;

CREATE TABLE `sys` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `service_content` text NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `sys` */

insert  into `sys`(`id`,`service_content`,`createtime`,`updatetime`) values (1,'<p>dsafad<br></p>','2017-10-30 14:00:00','2017-11-07 11:06:27');

/*Table structure for table `sys_permission` */

DROP TABLE IF EXISTS `sys_permission`;

CREATE TABLE `sys_permission` (
  `PERM_ID` int(4) NOT NULL AUTO_INCREMENT,
  `PERM_NAME` varchar(32) DEFAULT NULL,
  `PERMISSION` varchar(128) DEFAULT NULL,
  `PERM_DESC` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`PERM_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `sys_permission` */

insert  into `sys_permission`(`PERM_ID`,`PERM_NAME`,`PERMISSION`,`PERM_DESC`) values (1,'所有','*',NULL),(2,'查询','view',NULL),(3,'添加','create',''),(4,'修改','update',NULL),(5,'删除','delete',NULL);

/*Table structure for table `sys_resource` */

DROP TABLE IF EXISTS `sys_resource`;

CREATE TABLE `sys_resource` (
  `RESC_ID` int(11) NOT NULL AUTO_INCREMENT,
  `RESC_NAME` varchar(50) DEFAULT NULL,
  `PARENT_ID` int(11) DEFAULT NULL,
  `IDENTITY` varchar(64) DEFAULT NULL,
  `URL` varchar(200) DEFAULT NULL,
  `TYPE` int(2) DEFAULT NULL,
  `ICON` varchar(64) DEFAULT NULL,
  `SORT` int(2) DEFAULT NULL,
  `RESC_DESC` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`RESC_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8;

/*Data for the table `sys_resource` */

insert  into `sys_resource`(`RESC_ID`,`RESC_NAME`,`PARENT_ID`,`IDENTITY`,`URL`,`TYPE`,`ICON`,`SORT`,`RESC_DESC`) values (1,'系统管理',0,'','',1,'icon-desktop',9,'系统管理'),(2,'用户管理',1,'user','user/listUsers',1,NULL,1,'用户管理'),(3,'角色管理',1,'role','role/listRoles',1,NULL,2,NULL),(4,'资源管理',1,'resc','resc/listRescs',1,NULL,3,NULL),(5,'数据管理',0,NULL,NULL,1,'icon-list-alt',1,'数据管理'),(66,'客户管理',5,'customer','customer/list',1,NULL,1,'客户数据管理1'),(67,'接口访问日志',5,'apilog','apilog/list',1,NULL,19,'接口访问日志'),(68,'语音播报记录',5,'callinfo','callinfo/list',1,NULL,18,'语音播放记录'),(69,'配置管理',5,'conf','conf/list',1,NULL,20,'字典配置'),(70,'反馈管理',5,'feedback','feedback/list',1,NULL,10,'客户反馈管理'),(71,'血压管理',5,'heartPressure','heartPressure/list',1,NULL,2,'血压数据管理'),(72,'脉搏管理',5,'heartRate','heartRate/list',1,NULL,3,'脉搏数据管理'),(73,'位置管理',5,'location','location/list',1,NULL,4,'位置数据管理'),(74,'推送日志',5,'pushlog','pushlog/list',1,NULL,17,'推送日志管理'),(75,'短信发送日志',5,'smslog','smslog/list',1,NULL,16,'短信发送日志管理'),(76,'SOS记录',5,'soslog','soslog/list',1,NULL,15,'SOS日志记录'),(77,'计步管理',5,'step','step/list',1,NULL,5,'步数数据管理'),(78,'系统配置',5,'sys','sys/toEdit',1,NULL,11,'系统配置管理'),(79,'电量管理',5,'voltage','voltage/list',1,NULL,21,'电量管理'),(80,'血糖管理',5,'bloodSugar','bloodSugar/list',1,NULL,22,'血糖管理'),(81,'血氧管理',5,'bloodOxygen','bloodOxygen/list',1,NULL,23,'血氧管理');

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `ROLE_ID` int(4) NOT NULL AUTO_INCREMENT,
  `ROLE_NAME` varchar(64) DEFAULT NULL,
  `ROLE_KEY` varchar(64) DEFAULT NULL,
  `DESC` varchar(200) DEFAULT NULL,
  `STATUS` int(2) DEFAULT NULL,
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `sys_role` */

insert  into `sys_role`(`ROLE_ID`,`ROLE_NAME`,`ROLE_KEY`,`DESC`,`STATUS`) values (1,'超级管理员','super-manager',NULL,0);

/*Table structure for table `sys_role_resource` */

DROP TABLE IF EXISTS `sys_role_resource`;

CREATE TABLE `sys_role_resource` (
  `ROLE_ID` int(8) NOT NULL,
  `RESC_ID` int(8) NOT NULL,
  `PERMISSION_IDS` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ROLE_ID`,`RESC_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_role_resource` */

insert  into `sys_role_resource`(`ROLE_ID`,`RESC_ID`,`PERMISSION_IDS`) values (1,1,'1,2,3,4,5'),(1,2,'1,2,3,4,5,1,2,3,4,5'),(1,3,'1,2,3,4,5,1,2,3,4,5'),(1,4,'1,2,3,4,5,1,2,3,4,5'),(1,5,'1,2,3,4,5'),(1,66,'1,2,3,4,5'),(1,67,'1,2,3,4,5'),(1,68,'1,2,3,4,5'),(1,69,'1,2,3,4,5'),(1,70,'1,2,3,4,5'),(1,71,'1,2,3,4,5'),(1,72,'1,2,3,4,5'),(1,73,'1,2,3,4,5'),(1,74,'1,2,3,4,5'),(1,75,'1,2,3,4,5'),(1,76,'1,2,3,4,5'),(1,77,'1,2,3,4,5'),(1,78,'1,2,3,4,5'),(1,79,'1,2,3,4,5'),(1,80,'1,2,3,4,5'),(1,81,'1,2,3,4,5');

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `USER_ID` int(9) NOT NULL AUTO_INCREMENT,
  `USER_NAME` varchar(32) DEFAULT NULL,
  `PASSWORD` varchar(32) DEFAULT NULL,
  `REAL_NAME` varchar(64) DEFAULT NULL,
  `SALT` varchar(32) DEFAULT NULL,
  `AGE` int(4) DEFAULT NULL,
  `SEX` int(2) DEFAULT NULL,
  `PHONE` varchar(32) DEFAULT NULL,
  `STATUS` int(2) DEFAULT NULL,
  `DEDUCT` double(8,2) DEFAULT '0.00' COMMENT '扣量%',
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `sys_user` */

insert  into `sys_user`(`USER_ID`,`USER_NAME`,`PASSWORD`,`REAL_NAME`,`SALT`,`AGE`,`SEX`,`PHONE`,`STATUS`,`DEDUCT`) values (1,'admin','85409e7259c5dff73c277f5f520f1835','超级管理员','d180f2629d6251a284c6f37a5bd176fd',15,0,'187****2247',0,0.00),(2,'tete','d4ae6c650f9384e869c041a7f956d99e','tete','b2f36dbcf9fd20bb6a67a5a188acdc4a',999,0,'18735662247',0,0.00);

/*Table structure for table `sys_user_role` */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `USER_ID` int(11) NOT NULL DEFAULT '0',
  `ROLE_ID` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`USER_ID`,`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_user_role` */

insert  into `sys_user_role`(`USER_ID`,`ROLE_ID`) values (1,1),(2,1);

/*Table structure for table `token_info` */

DROP TABLE IF EXISTS `token_info`;

CREATE TABLE `token_info` (
  `t_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `token` varchar(128) NOT NULL,
  `user_id` int(11) NOT NULL,
  `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`t_id`),
  UNIQUE KEY `unique_idx_token` (`token`),
  UNIQUE KEY `unique_idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=186 DEFAULT CHARSET=utf8;

/*Data for the table `token_info` */

insert  into `token_info`(`t_id`,`token`,`user_id`,`createtime`) values (1,'123456',1,'2017-08-24 14:24:46'),(2,'1qaz',2,'2017-08-24 15:57:04'),(3,'2wsx',3,'2017-09-06 22:35:03'),(4,'3edc',4,'2017-09-06 22:36:10'),(5,'100001',5,'2017-09-12 14:41:02'),(6,'100002',6,'2017-09-12 14:42:00'),(7,'100003',7,'2017-09-12 14:42:12'),(8,'100004',8,'2017-09-12 14:42:24'),(9,'100005',9,'2017-09-12 14:43:06'),(10,'100006',10,'2017-09-12 14:43:18'),(11,'100007',11,'2017-09-12 14:43:28'),(12,'100008',12,'2017-09-12 14:43:44'),(13,'100009',13,'2017-09-12 14:44:12'),(14,'100010',14,'2017-09-12 14:44:24'),(59,'00CB9D55D5F469C501D55324A515C2F1',15,'2017-12-08 13:49:51'),(164,'2F0A30955221CC4E0BDA6BF4EFF0D248',36,'2017-12-15 18:12:02'),(179,'FF26D29148B0D5CA28ECD02FBC82AAF0',37,'2017-12-20 11:03:19'),(185,'548847601F8FC05E61B0F915BF3D4A0C',35,'2017-12-20 11:37:00');

/*Table structure for table `upload_photo` */

DROP TABLE IF EXISTS `upload_photo`;

CREATE TABLE `upload_photo` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `imei` varchar(10) NOT NULL,
  `photo_name` varchar(256) NOT NULL,
  `source` varchar(128) NOT NULL,
  `this_number` int(2) NOT NULL DEFAULT '0',
  `all_number` int(2) NOT NULL DEFAULT '0',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `upload_photo` */

insert  into `upload_photo`(`id`,`imei`,`photo_name`,`source`,`this_number`,`all_number`,`createtime`) values (1,'YYYYYYYYYY','123.jpg','1234567812',1,2,'2018-09-12 18:20:58');

/*Table structure for table `user_info` */

DROP TABLE IF EXISTS `user_info`;

CREATE TABLE `user_info` (
  `user_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(128) NOT NULL,
  `password` varchar(128) DEFAULT NULL,
  `dv` varchar(128) DEFAULT NULL COMMENT '设备固件版本号',
  `sd` varchar(128) DEFAULT NULL COMMENT '软件版本号',
  `imei` varchar(128) DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `bindingtime` timestamp NULL DEFAULT NULL,
  `avatar` text,
  `nickname` varchar(128) DEFAULT NULL,
  `sex` tinyint(2) DEFAULT NULL COMMENT '性别，0：男性，1: 女性',
  `weight` varchar(8) DEFAULT NULL,
  `height` varchar(8) DEFAULT NULL,
  `address` text,
  `head` varchar(128) DEFAULT NULL COMMENT '头像链接',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `unique_idx_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

/*Data for the table `user_info` */

insert  into `user_info`(`user_id`,`username`,`password`,`dv`,`sd`,`imei`,`createtime`,`bindingtime`,`avatar`,`nickname`,`sex`,`weight`,`height`,`address`,`head`) values (35,'15989568358','123456',NULL,NULL,NULL,'2017-12-11 09:54:33',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(36,'13267098103','123123',NULL,NULL,NULL,'2017-12-15 18:12:02',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(37,'18118763557','QWER1234',NULL,NULL,NULL,'2017-12-20 11:03:19',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `version_info` */

DROP TABLE IF EXISTS `version_info`;

CREATE TABLE `version_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL,
  `download_path` varchar(128) DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `description` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `version_info` */

/*Table structure for table `voltage_info` */

DROP TABLE IF EXISTS `voltage_info`;

CREATE TABLE `voltage_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `voltage` int(10) NOT NULL COMMENT '电压',
  `imei` varchar(128) NOT NULL,
  `upload_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

/*Data for the table `voltage_info` */

insert  into `voltage_info`(`id`,`voltage`,`imei`,`upload_time`) values (3,3,'3','2017-11-08 16:43:53'),(4,4,'4','2017-11-08 16:43:56'),(5,5,'5','2017-11-08 16:44:09'),(6,6,'6','2017-11-08 16:44:13'),(7,7,'7','2017-11-08 16:44:17'),(8,8,'8','2017-11-08 16:44:33'),(9,9,'9','2017-11-08 16:44:36'),(10,10,'1','2017-11-08 16:44:43'),(11,1,'1','2017-11-08 16:44:46'),(12,60,'CA:39:D5:F9:4E:8C','2018-01-15 11:10:48'),(13,55,'8800000015','2018-09-10 17:44:39'),(14,90,'111111111111111','2018-09-11 15:36:03');

/*Table structure for table `watch_addfriend_log` */

DROP TABLE IF EXISTS `watch_addfriend_log`;

CREATE TABLE `watch_addfriend_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `imei` varchar(128) NOT NULL,
  `role_name` varchar(128) NOT NULL,
  `phone` varchar(128) NOT NULL,
  `cornet` varchar(128) NOT NULL,
  `headtype` varchar(2) NOT NULL,
  `set_status` tinyint(2) NOT NULL DEFAULT '0',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `watch_addfriend_log` */

/*Table structure for table `watch_controller_server` */

DROP TABLE IF EXISTS `watch_controller_server`;

CREATE TABLE `watch_controller_server` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `imei` varchar(128) NOT NULL DEFAULT '0',
  `set_status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '1设置成功0失败',
  `c_type` tinyint(2) NOT NULL COMMENT '控制类型',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `watch_controller_server` */

/*Table structure for table `watch_friend_info` */

DROP TABLE IF EXISTS `watch_friend_info`;

CREATE TABLE `watch_friend_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `imei` varchar(128) NOT NULL,
  `role_name` varchar(128) NOT NULL,
  `phone` varchar(128) NOT NULL,
  `cornet` varchar(128) NOT NULL,
  `headtype` varchar(2) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `watch_friend_info` */

/*Table structure for table `watch_parameter_info` */

DROP TABLE IF EXISTS `watch_parameter_info`;

CREATE TABLE `watch_parameter_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `imei` varchar(128) DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `parameter` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `watch_parameter_info` */

/*Table structure for table `watch_setapn_log` */

DROP TABLE IF EXISTS `watch_setapn_log`;

CREATE TABLE `watch_setapn_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `imei` varchar(128) NOT NULL DEFAULT '0',
  `set_status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '1设置成功0失败',
  `apn_name` varchar(128) NOT NULL COMMENT '运营商号码',
  `username` varchar(256) NOT NULL DEFAULT '0' COMMENT '内容',
  `password` varchar(128) NOT NULL DEFAULT '0',
  `data` varchar(256) NOT NULL DEFAULT '0',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `watch_setapn_log` */

/*Table structure for table `watch_setcapt_log` */

DROP TABLE IF EXISTS `watch_setcapt_log`;

CREATE TABLE `watch_setcapt_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `imei` varchar(128) NOT NULL DEFAULT '0',
  `set_status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '1设置成功0失败',
  `come` varchar(128) NOT NULL COMMENT '运营商号码',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `watch_setcapt_log` */

/*Table structure for table `watch_setguard_log` */

DROP TABLE IF EXISTS `watch_setguard_log`;

CREATE TABLE `watch_setguard_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `imei` varchar(128) NOT NULL DEFAULT '0',
  `set_status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '1设置成功0失败',
  `type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '1为录音，格式amr；2为录像，格式mp4；3为拍照，格式jpg。录音，录像默认10秒，拍照默认一张。同时只能启用一项。',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `watch_setguard_log` */

/*Table structure for table `watch_setip_log` */

DROP TABLE IF EXISTS `watch_setip_log`;

CREATE TABLE `watch_setip_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `imei` varchar(128) NOT NULL DEFAULT '0',
  `set_status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '1设置成功0失败',
  `ip` varchar(128) NOT NULL COMMENT 'ip',
  `port` varchar(128) NOT NULL DEFAULT '0' COMMENT '端口',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `watch_setip_log` */

/*Table structure for table `watch_setmessage_log` */

DROP TABLE IF EXISTS `watch_setmessage_log`;

CREATE TABLE `watch_setmessage_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `imei` varchar(128) NOT NULL DEFAULT '0',
  `set_status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '1设置成功0失败',
  `message` varchar(256) NOT NULL DEFAULT '0' COMMENT '内容',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `watch_setmessage_log` */

/*Table structure for table `watch_setmonio_log` */

DROP TABLE IF EXISTS `watch_setmonio_log`;

CREATE TABLE `watch_setmonio_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `imei` varchar(128) NOT NULL DEFAULT '0',
  `set_status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '1设置成功0失败',
  `phone` varchar(128) NOT NULL COMMENT '监听号码',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `watch_setmonio_log` */

/*Table structure for table `watch_setsms_log` */

DROP TABLE IF EXISTS `watch_setsms_log`;

CREATE TABLE `watch_setsms_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `imei` varchar(128) NOT NULL DEFAULT '0',
  `set_status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '1设置成功0失败',
  `o_number` varchar(128) NOT NULL COMMENT '运营商号码',
  `content` varchar(256) NOT NULL DEFAULT '0' COMMENT '内容',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `watch_setsms_log` */

/*Table structure for table `watch_smslog` */

DROP TABLE IF EXISTS `watch_smslog`;

CREATE TABLE `watch_smslog` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `imei` varchar(128) DEFAULT NULL,
  `cmd` varchar(13) NOT NULL,
  `rmsg` text,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `watch_smslog` */

/*Table structure for table `watch_upload_photo` */

DROP TABLE IF EXISTS `watch_upload_photo`;

CREATE TABLE `watch_upload_photo` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `imei` varchar(10) NOT NULL,
  `photo_name` varchar(256) NOT NULL,
  `source` varchar(128) NOT NULL COMMENT '来源  如果是设备就填0，如果是App就填发的人的手机号码',
  `data` text,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `watch_upload_photo` */

insert  into `watch_upload_photo`(`id`,`imei`,`photo_name`,`source`,`data`,`createtime`) values (1,'1','1','1','1','2018-09-25 17:40:01');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
