/*
SQLyog Enterprise v12.09 (64 bit)
MySQL - 10.0.36-MariaDB-1~trusty : Database - watch
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`watch` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `watch`;

/*Table structure for table `add_friend_info` */

DROP TABLE IF EXISTS `add_friend_info`;

CREATE TABLE `add_friend_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `imei` varchar(256) NOT NULL,
  `add_imei` varchar(250) NOT NULL,
  `status` char(2) NOT NULL DEFAULT '0',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `api_applog` */

DROP TABLE IF EXISTS `api_applog`;

CREATE TABLE `api_applog` (
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
) ENGINE=InnoDB AUTO_INCREMENT=3098 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=13890 DEFAULT CHARSET=utf8;

/*Table structure for table `app_voice_info` */

DROP TABLE IF EXISTS `app_voice_info`;

CREATE TABLE `app_voice_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `sender` varchar(128) NOT NULL DEFAULT '0',
  `receiver` varchar(128) NOT NULL DEFAULT '0',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `voice_content` text NOT NULL,
  `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0表示未发送1表示已经发送2表示设备已接收',
  `source_name` varchar(128) NOT NULL,
  `no` varchar(128) NOT NULL,
  `updatetime` timestamp NULL DEFAULT NULL,
  `this_number` tinyint(2) NOT NULL COMMENT '当前包',
  `all_number` tinyint(2) NOT NULL COMMENT '总包',
  `voice_length` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;

/*Table structure for table `authcode` */

DROP TABLE IF EXISTS `authcode`;

CREATE TABLE `authcode` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tel` varchar(13) NOT NULL,
  `code` varchar(10) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8;

/*Table structure for table `blood_oxygen_info` */

DROP TABLE IF EXISTS `blood_oxygen_info`;

CREATE TABLE `blood_oxygen_info` (
  `bo_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `pulse_rate` int(10) NOT NULL COMMENT '脉率',
  `blood_oxygen` int(10) NOT NULL COMMENT '血氧',
  `user_id` int(11) unsigned NOT NULL,
  `upload_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`bo_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `bloodfat_info` */

DROP TABLE IF EXISTS `bloodfat_info`;

CREATE TABLE `bloodfat_info` (
  `bi_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `blood_fat` int(10) NOT NULL COMMENT '血脂',
  `user_id` int(11) unsigned NOT NULL,
  `upload_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`bi_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `bloodsugar_info` */

DROP TABLE IF EXISTS `bloodsugar_info`;

CREATE TABLE `bloodsugar_info` (
  `bs_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `blood_sugar` int(10) NOT NULL COMMENT '电压',
  `user_id` int(11) unsigned NOT NULL,
  `upload_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`bs_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

/*Table structure for table `device_location_fequency` */

DROP TABLE IF EXISTS `device_location_fequency`;

CREATE TABLE `device_location_fequency` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `imei` varchar(128) NOT NULL,
  `frequency` tinyint(2) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Table structure for table `device_manage_phone` */

DROP TABLE IF EXISTS `device_manage_phone`;

CREATE TABLE `device_manage_phone` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `imei` varchar(15) NOT NULL,
  `tel` varchar(13) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Table structure for table `device_watch_alarm_info` */

DROP TABLE IF EXISTS `device_watch_alarm_info`;

CREATE TABLE `device_watch_alarm_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `imei` varchar(128) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` timestamp NULL DEFAULT NULL,
  `weekAlarm1` varchar(128) DEFAULT NULL,
  `weekAlarm2` varchar(128) DEFAULT '0',
  `weekAlarm3` varchar(128) DEFAULT '0',
  `alarm1` varchar(128) DEFAULT '',
  `alarm2` varchar(128) DEFAULT '',
  `alarm3` varchar(128) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

/*Table structure for table `device_watch_bak_info` */

DROP TABLE IF EXISTS `device_watch_bak_info`;

CREATE TABLE `device_watch_bak_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `d_id` int(11) NOT NULL,
  `imei` varchar(128) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Table structure for table `device_watch_hf_info` */

DROP TABLE IF EXISTS `device_watch_hf_info`;

CREATE TABLE `device_watch_hf_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `w_id` int(11) NOT NULL,
  `imei` varchar(128) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` timestamp NULL DEFAULT NULL,
  `schoolAddress` varchar(256) DEFAULT '0',
  `classDisable1` varchar(256) NOT NULL DEFAULT '08:00-12:00',
  `classDisable2` varchar(256) NOT NULL DEFAULT '14:00-17:00',
  `weekDisable1` varchar(256) DEFAULT '',
  `schoolLat` varchar(256) NOT NULL DEFAULT '0',
  `schoolLng` varchar(256) NOT NULL DEFAULT '0',
  `latestTime` varchar(256) DEFAULT '',
  `homeAddress` varchar(256) DEFAULT '',
  `homeLng` varchar(256) NOT NULL DEFAULT '0',
  `homeLat` varchar(256) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

/*Table structure for table `device_watch_info` */

DROP TABLE IF EXISTS `device_watch_info`;

CREATE TABLE `device_watch_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `imei` varchar(128) NOT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `nickname` varchar(256) DEFAULT '',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `dv` varchar(256) DEFAULT '',
  `type` tinyint(2) DEFAULT '1' COMMENT '1表示移动21表示联通、3表示电信,0xFF表示其他',
  `sex` tinyint(2) DEFAULT '0' COMMENT '性别0女1男',
  `birday` varchar(128) DEFAULT '0' COMMENT '生日',
  `school_age` varchar(128) DEFAULT '' COMMENT '年纪',
  `school_info` varchar(256) DEFAULT '0',
  `home_info` varchar(256) DEFAULT '0',
  `updatetime` timestamp NULL DEFAULT NULL,
  `head` text,
  `weight` varchar(128) DEFAULT '',
  `height` varchar(128) DEFAULT '',
  `d_id` int(11) DEFAULT '0',
  `short_number` varchar(128) DEFAULT '',
  `family_number` varchar(128) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8;

/*Table structure for table `dialpad_info` */

DROP TABLE IF EXISTS `dialpad_info`;

CREATE TABLE `dialpad_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `imei` varchar(128) NOT NULL,
  `type` int(1) NOT NULL,
  `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_idx_token` (`imei`),
  UNIQUE KEY `unique_idx_user_id` (`type`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

/*Table structure for table `feedback` */

DROP TABLE IF EXISTS `feedback`;

CREATE TABLE `feedback` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `content` text NOT NULL,
  `contact` varchar(20) DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `answer_content` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

/*Table structure for table `healthStepManagement` */

DROP TABLE IF EXISTS `healthStepManagement`;

CREATE TABLE `healthStepManagement` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `imei` varchar(128) NOT NULL,
  `stepCalculate` varchar(64) NOT NULL,
  `sleepCalculate` varchar(64) NOT NULL,
  `hrCalculate` varchar(64) NOT NULL DEFAULT '0',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `heart_rate` */

DROP TABLE IF EXISTS `heart_rate`;

CREATE TABLE `heart_rate` (
  `hr_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `heart_rate` int(10) NOT NULL,
  `user_id` int(11) NOT NULL,
  `upload_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `imei` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`hr_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `instrancy_msg` */

DROP TABLE IF EXISTS `instrancy_msg`;

CREATE TABLE `instrancy_msg` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `msg` text NOT NULL,
  `addname` varchar(64) NOT NULL,
  `time_slot` varchar(64) NOT NULL,
  `rstatus` int(2) NOT NULL DEFAULT '0',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `ip_info` */

DROP TABLE IF EXISTS `ip_info`;

CREATE TABLE `ip_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `ip` varchar(256) NOT NULL,
  `port` varchar(5) NOT NULL,
  `status` char(2) NOT NULL DEFAULT '0' COMMENT '0暂停使用 1使用',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `location_1_watchinfo` */

DROP TABLE IF EXISTS `location_1_watchinfo`;

CREATE TABLE `location_1_watchinfo` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `imei` varchar(128) DEFAULT NULL,
  `location_type` tinyint(2) NOT NULL COMMENT '类型，0：gps， 1：基站 2:wifi',
  `lat` varchar(64) NOT NULL,
  `lng` varchar(64) NOT NULL,
  `status` varchar(64) NOT NULL DEFAULT '0000',
  `upload_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `location_time` varchar(16) NOT NULL,
  `location_style` tinyint(2) NOT NULL COMMENT '1正常2报警3天气4拍照',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1586 DEFAULT CHARSET=utf8;

/*Table structure for table `location_2_watchinfo` */

DROP TABLE IF EXISTS `location_2_watchinfo`;

CREATE TABLE `location_2_watchinfo` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `imei` varchar(128) DEFAULT NULL,
  `location_type` tinyint(2) NOT NULL COMMENT '类型，0：gps， 1：基站 2:wifi',
  `lat` varchar(64) NOT NULL,
  `lng` varchar(64) NOT NULL,
  `status` varchar(64) NOT NULL DEFAULT '0000',
  `upload_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `location_time` varchar(16) NOT NULL,
  `location_style` tinyint(2) NOT NULL COMMENT '1正常2报警3天气4拍照',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `location_3_watchinfo` */

DROP TABLE IF EXISTS `location_3_watchinfo`;

CREATE TABLE `location_3_watchinfo` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `imei` varchar(128) DEFAULT NULL,
  `location_type` tinyint(2) NOT NULL COMMENT '类型，0：gps， 1：基站 2:wifi',
  `lat` varchar(64) NOT NULL,
  `lng` varchar(64) NOT NULL,
  `status` varchar(64) NOT NULL DEFAULT '0000',
  `upload_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `location_time` varchar(16) NOT NULL,
  `location_style` tinyint(2) NOT NULL COMMENT '1正常2报警3天气4拍照',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

/*Table structure for table `location_old` */

DROP TABLE IF EXISTS `location_old`;

CREATE TABLE `location_old` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phone` varchar(64) NOT NULL,
  `lat` varchar(64) NOT NULL,
  `lng` varchar(64) NOT NULL,
  `upload_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `location_watchinfo` */

DROP TABLE IF EXISTS `location_watchinfo`;

CREATE TABLE `location_watchinfo` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `imei` varchar(128) DEFAULT NULL,
  `location_type` tinyint(2) NOT NULL COMMENT '类型，0：gps， 1：基站 2:wifi',
  `lat` varchar(64) NOT NULL,
  `lng` varchar(64) NOT NULL,
  `status` varchar(64) NOT NULL DEFAULT '0000',
  `upload_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `location_time` varchar(16) NOT NULL,
  `location_style` tinyint(2) NOT NULL COMMENT '1正常2报警3天气4拍照',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

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

/*Table structure for table `msg_0_info` */

DROP TABLE IF EXISTS `msg_0_info`;

CREATE TABLE `msg_0_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `imei` varchar(32) NOT NULL,
  `type` int(3) NOT NULL,
  `device_id` int(11) NOT NULL,
  `content` varchar(128) DEFAULT NULL,
  `message` varchar(128) DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

/*Table structure for table `msg_1_info` */

DROP TABLE IF EXISTS `msg_1_info`;

CREATE TABLE `msg_1_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `imei` varchar(32) NOT NULL,
  `type` int(3) NOT NULL,
  `device_id` int(11) NOT NULL,
  `content` varchar(128) DEFAULT NULL,
  `message` varchar(128) DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8;

/*Table structure for table `msg_2_info` */

DROP TABLE IF EXISTS `msg_2_info`;

CREATE TABLE `msg_2_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `imei` varchar(32) NOT NULL,
  `type` int(3) NOT NULL,
  `device_id` int(11) NOT NULL,
  `content` varchar(128) DEFAULT NULL,
  `message` varchar(128) DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `msg_3_info` */

DROP TABLE IF EXISTS `msg_3_info`;

CREATE TABLE `msg_3_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `imei` varchar(32) NOT NULL,
  `type` int(3) NOT NULL,
  `device_id` int(11) NOT NULL,
  `content` varchar(128) DEFAULT NULL,
  `message` varchar(128) DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `notify_info` */

DROP TABLE IF EXISTS `notify_info`;

CREATE TABLE `notify_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `imei` varchar(128) NOT NULL DEFAULT '',
  `notification` varchar(128) NOT NULL DEFAULT '0',
  `notificationSound` varchar(128) NOT NULL DEFAULT '0',
  `notificationVibration` varchar(128) NOT NULL DEFAULT '0',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Table structure for table `olddevice_bind` */

DROP TABLE IF EXISTS `olddevice_bind`;

CREATE TABLE `olddevice_bind` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phone` varchar(64) NOT NULL,
  `imei` varchar(64) NOT NULL,
  `name` varchar(64) NOT NULL,
  `upload_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `photo_location_watchinfo` */

DROP TABLE IF EXISTS `photo_location_watchinfo`;

CREATE TABLE `photo_location_watchinfo` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `imei` varchar(128) DEFAULT NULL,
  `location_type` tinyint(2) NOT NULL COMMENT '类型，0：gps， 1：基站 2:wifi',
  `lat` varchar(64) NOT NULL,
  `lng` varchar(64) NOT NULL,
  `status` varchar(64) NOT NULL DEFAULT '0000',
  `upload_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `location_time` varchar(16) NOT NULL,
  `location_style` tinyint(2) NOT NULL COMMENT '1正常2报警3天气4拍照',
  `photo_name` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

/*Table structure for table `push_message` */

DROP TABLE IF EXISTS `push_message`;

CREATE TABLE `push_message` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `imei` varchar(128) NOT NULL,
  `msg` text NOT NULL,
  `status` tinyint(2) NOT NULL DEFAULT '0',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

/*Table structure for table `school_guard` */

DROP TABLE IF EXISTS `school_guard`;

CREATE TABLE `school_guard` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `deviceId` varchar(128) NOT NULL,
  `offOn` tinyint(1) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `sos_location_watchinfo` */

DROP TABLE IF EXISTS `sos_location_watchinfo`;

CREATE TABLE `sos_location_watchinfo` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `imei` varchar(128) DEFAULT NULL,
  `location_type` tinyint(2) NOT NULL COMMENT '类型，0：gps， 1：基站 2:wifi',
  `lat` varchar(64) NOT NULL,
  `lng` varchar(64) NOT NULL,
  `status` varchar(64) NOT NULL DEFAULT '0000',
  `upload_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `location_time` varchar(16) NOT NULL,
  `location_style` tinyint(2) NOT NULL COMMENT '1正常2报警3天气4拍照',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

/*Table structure for table `sys` */

DROP TABLE IF EXISTS `sys`;

CREATE TABLE `sys` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `service_content` text NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `sys_permission` */

DROP TABLE IF EXISTS `sys_permission`;

CREATE TABLE `sys_permission` (
  `PERM_ID` int(4) NOT NULL AUTO_INCREMENT,
  `PERM_NAME` varchar(32) DEFAULT NULL,
  `PERMISSION` varchar(128) DEFAULT NULL,
  `PERM_DESC` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`PERM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `ROLE_ID` int(4) NOT NULL AUTO_INCREMENT,
  `ROLE_NAME` varchar(64) DEFAULT NULL,
  `ROLE_KEY` varchar(64) DEFAULT NULL,
  `DESC` varchar(200) DEFAULT NULL,
  `STATUS` int(2) DEFAULT NULL,
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `sys_role_resource` */

DROP TABLE IF EXISTS `sys_role_resource`;

CREATE TABLE `sys_role_resource` (
  `ROLE_ID` int(8) NOT NULL,
  `RESC_ID` int(8) NOT NULL,
  `PERMISSION_IDS` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ROLE_ID`,`RESC_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `sys_user_role` */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `USER_ID` int(11) NOT NULL DEFAULT '0',
  `ROLE_ID` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`USER_ID`,`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `token_1_info` */

DROP TABLE IF EXISTS `token_1_info`;

CREATE TABLE `token_1_info` (
  `t_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `token` varchar(128) NOT NULL,
  `user_id` int(11) NOT NULL,
  `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`t_id`),
  UNIQUE KEY `unique_idx_token` (`token`),
  UNIQUE KEY `unique_idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=194 DEFAULT CHARSET=utf8;

/*Table structure for table `token_2_info` */

DROP TABLE IF EXISTS `token_2_info`;

CREATE TABLE `token_2_info` (
  `t_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `token` varchar(128) NOT NULL,
  `user_id` int(11) NOT NULL,
  `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`t_id`),
  UNIQUE KEY `unique_idx_token` (`token`),
  UNIQUE KEY `unique_idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=178 DEFAULT CHARSET=utf8;

/*Table structure for table `token_3_info` */

DROP TABLE IF EXISTS `token_3_info`;

CREATE TABLE `token_3_info` (
  `t_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `token` varchar(128) NOT NULL,
  `user_id` int(11) NOT NULL,
  `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`t_id`),
  UNIQUE KEY `unique_idx_token` (`token`),
  UNIQUE KEY `unique_idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=212 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=409 DEFAULT CHARSET=utf8;

/*Table structure for table `upload_photo` */

DROP TABLE IF EXISTS `upload_photo`;

CREATE TABLE `upload_photo` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `imei` varchar(256) NOT NULL,
  `photo_name` varchar(256) NOT NULL,
  `source` varchar(128) NOT NULL,
  `this_number` int(2) NOT NULL DEFAULT '0',
  `all_number` int(2) NOT NULL DEFAULT '0',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `user_info` */

DROP TABLE IF EXISTS `user_info`;

CREATE TABLE `user_info` (
  `user_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(128) NOT NULL,
  `password` varchar(128) DEFAULT NULL,
  `type` tinyint(2) DEFAULT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8;

/*Table structure for table `user_luru_info` */

DROP TABLE IF EXISTS `user_luru_info`;

CREATE TABLE `user_luru_info` (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

/*Table structure for table `voltage_info` */

DROP TABLE IF EXISTS `voltage_info`;

CREATE TABLE `voltage_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `voltage` int(10) NOT NULL COMMENT '电压',
  `imei` varchar(128) NOT NULL,
  `upload_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

/*Table structure for table `watch_bind_device` */

DROP TABLE IF EXISTS `watch_bind_device`;

CREATE TABLE `watch_bind_device` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `imei` varchar(128) DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '0不是管理员1是管理员',
  `b_imei` varchar(15) NOT NULL COMMENT '谁发起的绑定',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=150 DEFAULT CHARSET=utf8;

/*Table structure for table `watch_carrier` */

DROP TABLE IF EXISTS `watch_carrier`;

CREATE TABLE `watch_carrier` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `deviceId` varchar(128) NOT NULL DEFAULT '',
  `smsNumber` varchar(128) DEFAULT '',
  `smsBalanceKey` varchar(128) DEFAULT '',
  `smsFlowKey` varchar(128) DEFAULT '',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

/*Table structure for table `watch_device_set` */

DROP TABLE IF EXISTS `watch_device_set`;

CREATE TABLE `watch_device_set` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `imei` varchar(256) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` timestamp NULL DEFAULT NULL,
  `setInfo` varchar(128) DEFAULT '0',
  `infoVibration` tinyint(1) DEFAULT '0',
  `infoVoice` tinyint(1) DEFAULT '0',
  `phoneComeVibration` tinyint(1) DEFAULT '0',
  `phoneComeVoice` tinyint(1) DEFAULT '0',
  `watchOffAlarm` tinyint(1) DEFAULT '0',
  `rejectStrangers` tinyint(1) DEFAULT '0',
  `timerSwitch` tinyint(1) DEFAULT '0',
  `disabledInClass` tinyint(1) DEFAULT '0',
  `reserveEmergencyPower` tinyint(2) DEFAULT '0',
  `somatosensory` tinyint(1) DEFAULT '0',
  `reportCallLocation` tinyint(1) DEFAULT '0',
  `automaticAnswering` tinyint(1) DEFAULT '0',
  `sosMsgswitch` tinyint(1) DEFAULT '0',
  `flowerNumber` tinyint(2) DEFAULT '0',
  `brightScreen` tinyint(2) DEFAULT '0',
  `language` tinyint(1) DEFAULT '0',
  `timeZone` tinyint(1) DEFAULT '0',
  `locationMode` tinyint(1) DEFAULT '0',
  `locationTime` tinyint(2) DEFAULT '0',
  PRIMARY KEY (`id`,`user_id`),
  UNIQUE KEY `unique_idx_dv` (`imei`(191)) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*Table structure for table `watch_errorinfo` */

DROP TABLE IF EXISTS `watch_errorinfo`;

CREATE TABLE `watch_errorinfo` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `content` text NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `watch_fence` */

DROP TABLE IF EXISTS `watch_fence`;

CREATE TABLE `watch_fence` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `imei` varchar(128) NOT NULL,
  `lat` varchar(64) NOT NULL,
  `lng` varchar(64) NOT NULL,
  `radius` int(10) NOT NULL DEFAULT '0',
  `status` int(2) NOT NULL DEFAULT '0',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` timestamp NULL DEFAULT NULL,
  `name` varchar(128) DEFAULT '',
  `is_entry` tinyint(1) NOT NULL COMMENT '进电子围栏报警',
  `is_exit` tinyint(1) NOT NULL COMMENT '出电子围栏报警',
  `is_enable` tinyint(1) NOT NULL COMMENT '电子围栏开关',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

/*Table structure for table `watch_friend_info` */

DROP TABLE IF EXISTS `watch_friend_info`;

CREATE TABLE `watch_friend_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `imei` varchar(128) NOT NULL,
  `role_name` varchar(128) NOT NULL,
  `phone` varchar(128) DEFAULT '',
  `cornet` varchar(128) NOT NULL,
  `headtype` varchar(2) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `DeviceFriendId` int(11) NOT NULL DEFAULT '0',
  `updatetime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `watch_parameter_info` */

DROP TABLE IF EXISTS `watch_parameter_info`;

CREATE TABLE `watch_parameter_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `imei` varchar(128) DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `parameter` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `watch_phone_charge` */

DROP TABLE IF EXISTS `watch_phone_charge`;

CREATE TABLE `watch_phone_charge` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `phone` varchar(128) NOT NULL,
  `content` text NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(1) DEFAULT '0',
  `imei` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `watch_phonebook_info` */

DROP TABLE IF EXISTS `watch_phonebook_info`;

CREATE TABLE `watch_phonebook_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `imei` varchar(128) NOT NULL,
  `name` varchar(128) DEFAULT '',
  `phone` varchar(128) NOT NULL,
  `cornet` varchar(128) DEFAULT '',
  `headtype` varchar(2) DEFAULT NULL,
  `status` tinyint(2) DEFAULT '0',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` timestamp NULL DEFAULT NULL,
  `headImg` varchar(256) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8;

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

/*Table structure for table `watch_setsms_log` */

DROP TABLE IF EXISTS `watch_setsms_log`;

CREATE TABLE `watch_setsms_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `imei` varchar(128) NOT NULL DEFAULT '0',
  `set_status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '1设置成功0失败',
  `o_number` varchar(128) NOT NULL COMMENT '运营商号码',
  `content` text COMMENT '内容',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `watch_sms_info` */

DROP TABLE IF EXISTS `watch_sms_info`;

CREATE TABLE `watch_sms_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `deviceId` varchar(128) NOT NULL,
  `type` tinyint(2) NOT NULL,
  `phone` varchar(128) DEFAULT '',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

/*Table structure for table `watch_time_switch` */

DROP TABLE IF EXISTS `watch_time_switch`;

CREATE TABLE `watch_time_switch` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `deviceId` int(11) NOT NULL,
  `timeClose` varchar(128) DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` timestamp NULL DEFAULT NULL,
  `timeOpen` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Table structure for table `watch_upload_photo` */

DROP TABLE IF EXISTS `watch_upload_photo`;

CREATE TABLE `watch_upload_photo` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `imei` varchar(256) NOT NULL,
  `photo_name` varchar(256) NOT NULL,
  `source` varchar(256) NOT NULL COMMENT '来源  如果是设备就填0，如果是App就填发的人的手机号码',
  `data` varchar(128) DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(2) DEFAULT '0',
  `updatetime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

/*Table structure for table `watch_voice_info` */

DROP TABLE IF EXISTS `watch_voice_info`;

CREATE TABLE `watch_voice_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `sender` varchar(128) NOT NULL DEFAULT '0',
  `receiver` varchar(128) NOT NULL DEFAULT '0',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `voice_content` text NOT NULL,
  `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0表示未发送1表示已经发送2表示设备已接收',
  `source_name` varchar(128) NOT NULL,
  `no` varchar(128) NOT NULL,
  `updatetime` timestamp NULL DEFAULT NULL,
  `this_number` tinyint(2) NOT NULL COMMENT '当前包',
  `all_number` tinyint(2) NOT NULL COMMENT '总包',
  `voice_length` tinyint(2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
