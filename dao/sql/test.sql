/*
Navicat MySQL Data Transfer

Source Server         : db
Source Server Version : 50642
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50642
File Encoding         : 65001

Date: 2021-04-29 16:37:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for dept
-- ----------------------------
DROP TABLE IF EXISTS `dept`;
CREATE TABLE `dept` (
  `deptid` int(11) NOT NULL,
  `deptname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`deptid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dept
-- ----------------------------
INSERT INTO `dept` VALUES ('1', '研发');
INSERT INTO `dept` VALUES ('2', '测试');

-- ----------------------------
-- Table structure for empl
-- ----------------------------
DROP TABLE IF EXISTS `empl`;
CREATE TABLE `empl` (
  `empname` varchar(255) DEFAULT NULL,
  `empid` int(11) NOT NULL,
  `deptid` int(11) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`empid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of empl
-- ----------------------------
INSERT INTO `empl` VALUES ('zhy', '1', '1', '10', '1');
INSERT INTO `empl` VALUES ('zhy1', '2', '1', '10', '1');
INSERT INTO `empl` VALUES ('zhy2', '3', null, '10', '1');
INSERT INTO `empl` VALUES ('zhy3', '4', '2', '10', '1');
INSERT INTO `empl` VALUES ('zhy4', '5', '2', '10', '1');
INSERT INTO `empl` VALUES ('zhy5', '6', null, '10', '1');

-- ----------------------------
-- Table structure for task_schedule_job
-- ----------------------------
DROP TABLE IF EXISTS `task_schedule_job`;
CREATE TABLE `task_schedule_job` (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '定时任务编号，自增',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `job_name` varchar(255) DEFAULT NULL COMMENT '任务类名（对应需要执行的定时任务的类名）',
  `job_group` varchar(255) DEFAULT NULL COMMENT '任务组名和job_name保持一致即可',
  `job_status` varchar(255) DEFAULT NULL COMMENT '定时任务是否生效0失效1生效',
  `cron_expression` varchar(255) NOT NULL COMMENT '定时任务cron表达式，推荐使用6域',
  `description` varchar(255) DEFAULT NULL COMMENT '定时任务描述',
  `bean_class` varchar(255) DEFAULT NULL COMMENT '定时任务类名全路径',
  `is_concurrent` varchar(255) DEFAULT NULL COMMENT '任务是否有状态（1有，0没有）',
  `spring_id` varchar(255) DEFAULT NULL COMMENT '注册的Bean，所以与类名保持一致，首字母小写',
  `method_name` varchar(255) CHARACTER SET utf8mb4 NOT NULL COMMENT '定时任务的方法名',
  PRIMARY KEY (`job_id`),
  UNIQUE KEY `name_group` (`job_name`,`job_group`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of task_schedule_job
-- ----------------------------
INSERT INTO `task_schedule_job` VALUES ('1', '2021-01-14 15:48:46', '2021-01-14 19:45:15', 'TestSchedule', 'TestSchedule', '1', '0 */1 * * * ?', '产生随机数定时任务', 'com.ziyi.Schedule.TestSchedule', '1', 'testSchedule', 'work');

-- ----------------------------
-- Table structure for test
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test` (
  `name` varchar(10) DEFAULT NULL,
  `age` int(10) DEFAULT NULL,
  `id` int(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of test
-- ----------------------------
INSERT INTO `test` VALUES ('zhy', '1', '1');

-- ----------------------------
-- Table structure for test1
-- ----------------------------
DROP TABLE IF EXISTS `test1`;
CREATE TABLE `test1` (
  `id` int(11) DEFAULT NULL,
  `faid` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of test1
-- ----------------------------
INSERT INTO `test1` VALUES ('1', null, '11');
INSERT INTO `test1` VALUES ('2', null, '12');
INSERT INTO `test1` VALUES ('3', '1', '21');
INSERT INTO `test1` VALUES ('4', '1', '22');
INSERT INTO `test1` VALUES ('5', '2', '23');
INSERT INTO `test1` VALUES ('6', '2', '24');
INSERT INTO `test1` VALUES ('7', '3', '31');
INSERT INTO `test1` VALUES ('8', '6', '32');
INSERT INTO `test1` VALUES ('9', '7', '41');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `userName` varchar(32) NOT NULL,
  `passWord` varchar(50) NOT NULL,
  `realName` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'zhy', '111', 'zhy');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `user_phone` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  `create_user` int(11) DEFAULT NULL,
  `modify_time` date DEFAULT NULL,
  `modify_user` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', 'ZHY', '123456', '123456', '你好', '2021-04-29', '1', '2021-04-29', '1');
