/*
Navicat MySQL Data Transfer

Source Server         : openid-dev
Source Server Version : 50077
Source Host           : 10.241.14.39:3306
Source Database       : sdo-openid

Target Server Type    : MYSQL
Target Server Version : 50077
File Encoding         : 65001

Date: 2010-12-27 11:13:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `audit`
-- ----------------------------
DROP TABLE IF EXISTS `audit`;
CREATE TABLE `audit` (
  `id` varchar(255) NOT NULL,
  `created_at` datetime default NULL,
  `updated_at` datetime default NULL,
  `version` bigint(20) default NULL,
  `app_id` varchar(255) NOT NULL,
  `auth_result` varchar(255) NOT NULL,
  `behaviour` varchar(255) default NULL,
  `op_domain` varchar(255) NOT NULL,
  `open_id_identifier` varchar(255) default NULL,
  `request_from` varchar(255) NOT NULL,
  `result_detail` text,
  `returnurl` varchar(255) default NULL,
  `sso_token` varchar(255) default NULL,
  `next_operation` varchar(255) default NULL,
  `previous_operation` varchar(255) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK58D9BDB41666DB` (`previous_operation`),
  KEY `FK58D9BDB731F6BD7` (`next_operation`),
  CONSTRAINT `FK58D9BDB731F6BD7` FOREIGN KEY (`next_operation`) REFERENCES `audit` (`id`),
  CONSTRAINT `FK58D9BDB41666DB` FOREIGN KEY (`previous_operation`) REFERENCES `audit` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(255) NOT NULL,
  `created_at` datetime default NULL,
  `updated_at` datetime default NULL,
  `version` bigint(20) default NULL,
  `clientip` varchar(255) NOT NULL,
  `digital_account` varchar(255) NOT NULL,
  `op_domain` varchar(255) NOT NULL,
  `open_id_identifier` varchar(255) NOT NULL,
  `pt_account` varchar(255) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `open_id_identifier` (`open_id_identifier`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

