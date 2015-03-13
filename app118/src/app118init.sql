CREATE DATABASE  IF NOT EXISTS `app118` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `app118`;
-- MySQL dump 10.13  Distrib 5.6.11, for Win32 (x86)
--
-- Host: 123.57.34.22    Database: app118
-- ------------------------------------------------------
-- Server version	5.1.73

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `sys_code`
--

DROP TABLE IF EXISTS `sys_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_code` (
  `code_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '代码类别标识',
  `code_name` varchar(64) DEFAULT NULL COMMENT '代码类别名称',
  `p_code` int(11) DEFAULT NULL COMMENT '代码类别父标识',
  `status` varchar(8) DEFAULT NULL COMMENT '状态  \n0：废弃\n\n1：启用',
  `type` varchar(8) DEFAULT NULL COMMENT '所属种类2, 过滤器品牌4, 过滤器型号6, 汽车品牌8, 汽车型号',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `descr` varchar(512) DEFAULT NULL COMMENT '描述',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `remark1` varchar(32) DEFAULT NULL COMMENT '备用字段1',
  `remark2` varchar(32) DEFAULT NULL COMMENT '备用字段2',
  `code_value` varchar(64) DEFAULT NULL COMMENT '代码编码',
  PRIMARY KEY (`code_id`),
  UNIQUE KEY `code_id_UNIQUE` (`code_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='代码类别表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_code`
--

LOCK TABLES `sys_code` WRITE;
/*!40000 ALTER TABLE `sys_code` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_log`
--

DROP TABLE IF EXISTS `sys_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_log` (
  `log_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '日志标识',
  `user_id` int(11) DEFAULT NULL COMMENT '用户标识',
  `login_name` varchar(32) DEFAULT NULL COMMENT '登录名',
  `log_type` varchar(8) DEFAULT NULL COMMENT '日志类型\n1：后台\n3：app',
  `ip_address` varchar(32) DEFAULT NULL COMMENT 'IP地址',
  `op_content` varchar(1024) DEFAULT NULL COMMENT '操作内容',
  `op_time` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  `terminal_type` varchar(32) DEFAULT NULL COMMENT '终端型号',
  `localization` varchar(256) DEFAULT NULL COMMENT '地址定位（经纬度）',
  `remark1` varchar(32) DEFAULT NULL COMMENT '扩展字段1',
  `remark2` varchar(64) DEFAULT NULL COMMENT '扩展字段2',
  `org_id` int(11) DEFAULT NULL COMMENT '所属门店',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_log`
--

LOCK TABLES `sys_log` WRITE;
/*!40000 ALTER TABLE `sys_log` DISABLE KEYS */;
INSERT INTO `sys_log` VALUES (1,1,'18810790739','1','127.0.0.1','{\"message\":\"后台登录成功\"}','2015-03-13 05:58:44',NULL,NULL,NULL,NULL,1),(2,1,'18810790739','1','127.0.0.1','{\"message\":\"后台登录成功\"}','2015-03-13 05:59:44',NULL,NULL,NULL,NULL,1),(3,1,'18810790739','1','127.0.0.1','{\"message\":\"后台登录成功\"}','2015-03-13 05:59:46',NULL,NULL,NULL,NULL,1),(4,1,'18810790739','1','127.0.0.1','{\"message\":\"后台登录成功\"}','2015-03-13 06:01:37',NULL,NULL,NULL,NULL,1),(5,1,'18810790739','1','127.0.0.1','{\"message\":\"后台登录成功\"}','2015-03-13 06:02:05',NULL,NULL,NULL,NULL,1),(6,1,'18810790739','1','127.0.0.1','{\"message\":\"后台登录成功\"}','2015-03-13 06:02:21',NULL,NULL,NULL,NULL,1),(7,1,'18810790739','1','127.0.0.1','{\"message\":\"后台登录成功\"}','2015-03-13 06:02:54',NULL,NULL,NULL,NULL,1),(8,1,'18810790739','1','127.0.0.1','{\"message\":\"后台登录成功\"}','2015-03-13 06:05:50',NULL,NULL,NULL,NULL,1);
/*!40000 ALTER TABLE `sys_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_menu` (
  `menu_id` int(11) NOT NULL COMMENT '本实体记录的唯一标识。',
  `menu_code` varchar(16) NOT NULL COMMENT '菜单编码',
  `menu_name` varchar(256) DEFAULT NULL COMMENT '菜单名称',
  `menu_title` varchar(32) DEFAULT NULL COMMENT '菜单标题',
  `menu_pid` int(11) DEFAULT NULL COMMENT '直接上级菜单的菜单标识。',
  `menu_folder_flag` varchar(8) DEFAULT NULL COMMENT '是否菜单夹，是：菜单夹；否：菜单项。',
  `menu_sort` varchar(8) DEFAULT NULL COMMENT '菜单对应的资源的类别。01 组件 02 HTML 03 报表',
  `menu_represent` varchar(256) DEFAULT NULL COMMENT '记录菜单链接资源的名称。',
  `sort_no` int(4) DEFAULT NULL COMMENT '在同级中的排列顺序的序号，用自然数标识，如，1、2、3。',
  `menu_level` int(4) DEFAULT NULL COMMENT '菜单级别',
  `menu_path` varchar(256) DEFAULT NULL COMMENT '菜单路径',
  `isactive` varchar(8) DEFAULT NULL COMMENT '是否可用标识 0：无效  1：有效',
  `user_id` varchar(32) DEFAULT NULL COMMENT '菜单添加人员',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '菜单添加时间',
  `remark` varchar(256) DEFAULT NULL COMMENT '菜单的备注',
  `menu_icon` varchar(16) DEFAULT NULL COMMENT '菜单图标',
  `remark1` varchar(32) DEFAULT NULL COMMENT '备用字段',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT=' 保存菜单定义的一些基本信息。本实体主要包括菜单标识、菜单名称、菜单标题、上级菜单标识、是否菜单夹等属性。';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` VALUES (1,'1','用户管理',NULL,0,'1','1',NULL,NULL,1,NULL,'1',NULL,'2014-07-16 16:00:00',NULL,NULL,NULL),(4,'4','系统管理',NULL,0,'1','4',NULL,NULL,1,NULL,'1',NULL,'2014-07-16 16:00:00',NULL,NULL,NULL),(5,'5','消息管理',NULL,0,'1','5',NULL,NULL,1,NULL,'1',NULL,'2014-08-18 07:00:00',NULL,NULL,NULL),(8,'8','个人中心',NULL,0,'1','9',NULL,NULL,1,NULL,'1',NULL,NULL,NULL,NULL,NULL),(18,'51','消息管理',NULL,5,'0','3',NULL,NULL,2,'/app118/messageAction/initMessage','1',NULL,'2014-08-18 07:27:00',NULL,NULL,NULL),(19,'41','角色管理',NULL,4,'0','1',NULL,NULL,2,'/app118/roleAction/initRole','1',NULL,'2014-08-18 07:27:00',NULL,NULL,NULL),(30,'42','组织机构管理',NULL,4,'0','2',NULL,NULL,2,'/app118/orgAction/initOrg','1',NULL,'2014-09-09 07:27:00',NULL,NULL,NULL),(31,'13','后台用户管理',NULL,1,'0','3',NULL,NULL,2,'/app118/accountAction/initAccount','1',NULL,'2014-08-18 07:27:00',NULL,NULL,NULL),(32,'81','修改密码',NULL,8,'0','1',NULL,NULL,2,'/app118/accountAction/toUpdAcountPwd','1',NULL,NULL,NULL,NULL,NULL),(34,'43','代码管理',NULL,4,'0','3',NULL,NULL,2,'/app118/codeAction/initCode','1',NULL,'2014-09-09 07:27:00',NULL,NULL,NULL),(36,'52','短信发送',NULL,5,'0','2',NULL,NULL,2,'/app118/messageAction/initSms','1',NULL,'2014-08-18 07:27:00',NULL,NULL,NULL),(44,'14','后台日志',NULL,4,'0','4',NULL,NULL,2,'/app118/logAction/initLog?logType=1','1',NULL,'2014-12-01 07:27:00',NULL,NULL,NULL),(45,'15','app日志',NULL,4,'0','4',NULL,NULL,2,'/app118/logAction/initLog?logType=3','1',NULL,'2014-12-01 07:27:00',NULL,NULL,NULL),(48,'14','app用户管理',NULL,1,'0','1',NULL,NULL,2,'/app118/agentUserManagerAction/initAgentUser','1',NULL,'2015-01-12 02:27:00',NULL,NULL,NULL),(50,'53','发送消息',NULL,5,'0','1',NULL,NULL,2,'/app118/messageAction/initMsgPush','1',NULL,'2014-08-18 07:27:00',NULL,NULL,NULL);
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_message`
--

DROP TABLE IF EXISTS `sys_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_message` (
  `msg_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '消息标识',
  `msg_title` varchar(256) DEFAULT NULL COMMENT '消息标题',
  `msg_content` varchar(2048) DEFAULT NULL COMMENT '消息内容',
  `msg_type` varchar(8) DEFAULT NULL COMMENT '消息类型\n2：系统消息\n4：活动消息\n6：推送消息\n\n9:短信',
  `msg_create_time` timestamp NULL DEFAULT NULL COMMENT '消息创建时间',
  `msg_expiry_time` timestamp NULL DEFAULT NULL COMMENT '失效时间',
  `is_stick` varchar(8) DEFAULT NULL COMMENT '短信类型\n1:表示行业信息  3:表示营销信息\n',
  `user_id` int(11) DEFAULT NULL COMMENT '发布人标识',
  PRIMARY KEY (`msg_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息表:记录系统消息及其他类型的消息表\n\n\n\n\n\n';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_message`
--

LOCK TABLES `sys_message` WRITE;
/*!40000 ALTER TABLE `sys_message` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_message_send`
--

DROP TABLE IF EXISTS `sys_message_send`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_message_send` (
  `msg_send_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '消息发送标识',
  `msg_id` int(11) DEFAULT NULL COMMENT '消息标识',
  `receiver_id` int(11) DEFAULT NULL COMMENT '消息接收者标识',
  `sender_id` int(11) DEFAULT NULL COMMENT '消息发送者标识',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '消息创建时间',
  `status` varchar(8) DEFAULT NULL COMMENT '消息状态\n0：未查看\n1：已查看\n4：删除		',
  `remark1` varchar(32) DEFAULT NULL COMMENT '备用字段1',
  `remark2` varchar(32) DEFAULT NULL COMMENT '备用字段2',
  `org_id` int(11) DEFAULT NULL COMMENT '所属门店',
  PRIMARY KEY (`msg_send_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息发送表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_message_send`
--

LOCK TABLES `sys_message_send` WRITE;
/*!40000 ALTER TABLE `sys_message_send` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_message_send` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_org`
--

DROP TABLE IF EXISTS `sys_org`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_org` (
  `org_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '本实体记录的唯一标识，创建组织机构的唯一标识。',
  `org_no` varchar(16) DEFAULT NULL COMMENT '机构、单位等编码',
  `org_pid` int(11) DEFAULT NULL COMMENT '直属上级机构编码。',
  `org_name` varchar(256) DEFAULT NULL COMMENT '机构单位详细的名称。',
  `abbr` varchar(128) DEFAULT NULL COMMENT '机构名称简写,如北京简写bj。',
  `org_type` varchar(8) DEFAULT NULL COMMENT '所属品牌：具体类型根据标准代码维护为依据。',
  `user_id` int(11) DEFAULT NULL COMMENT '负责人',
  `mobile` varchar(16) DEFAULT NULL COMMENT '联系电话',
  `fax` varchar(8) DEFAULT NULL COMMENT '传真',
  `sys_type` varchar(8) DEFAULT '01' COMMENT '组织机构的一个系统来源方向：01 后台管理系统\n具体系统来源根据标准代码维护为依据。',
  `sort_no` int(4) DEFAULT '1' COMMENT '在同级中的排列顺序的序号，用自然数阿拉伯数字标识，如，1、2、3。',
  `isactive` varchar(8) DEFAULT '1' COMMENT '描述该机构是否有效的一个状态标识  0：无效  1：有效',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`org_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='组织机构单位的基本信息。本实体主要包括机构标识、机构名称、上级机构标识、机构类型等属性。';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_org`
--

LOCK TABLES `sys_org` WRITE;
/*!40000 ALTER TABLE `sys_org` DISABLE KEYS */;
INSERT INTO `sys_org` VALUES (1,'1',0,'莎琪美妆','','',1,'',NULL,'01',1,'1','后台管理系统','2015-03-10 09:14:31'),(18,'2',1,'研发部','116.22946,40.188099','',1,'18810790739',NULL,'01',1,'1','','2015-03-10 09:30:21');
/*!40000 ALTER TABLE `sys_org` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '本实体记录的唯一标识。',
  `role_code` varchar(32) DEFAULT NULL COMMENT '角色编码',
  `role_name` varchar(32) DEFAULT NULL COMMENT '角色名称',
  `user_id` int(11) DEFAULT NULL COMMENT '创建该角色的操作人员，即系统登录用户标识',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `isactive` varchar(8) DEFAULT NULL COMMENT '描述该角色是否可用的一个状态标识   0：无效  1：有效',
  `role_desc` varchar(256) DEFAULT NULL COMMENT '角色描述。',
  `remark1` varchar(32) DEFAULT NULL COMMENT '备用字段',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='系统角色的信息。角色是一组资源权限项的集合，这一组资源权限项可以被一起授予或回收。本实体主要包括角色标识、角色名称';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (1,'superAdmin','超级管理员',1,'2014-09-05 04:53:02','1','超级管理员可执行本系统的一切操作。',NULL,10000);
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_menu_rela`
--

DROP TABLE IF EXISTS `sys_role_menu_rela`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role_menu_rela` (
  `rm_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键标识',
  `role_id` int(11) DEFAULT NULL COMMENT '角色标识。',
  `menu_id` int(11) DEFAULT NULL COMMENT '菜单标识。',
  `menu_code` varchar(16) DEFAULT NULL COMMENT '菜单编码',
  `user_id` int(11) DEFAULT NULL COMMENT '操作人员',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`rm_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='用于记录菜单功能信息与角色信息之间关系的信息，本实体主要包括菜单角色关系标识、菜单唯一标识、角色唯一标识等属性';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu_rela`
--

LOCK TABLES `sys_role_menu_rela` WRITE;
/*!40000 ALTER TABLE `sys_role_menu_rela` DISABLE KEYS */;
INSERT INTO `sys_role_menu_rela` VALUES (3,1,1,NULL,1,'2015-03-13 06:02:17'),(4,1,18,NULL,1,'2015-03-13 06:02:17'),(5,1,19,NULL,1,'2015-03-13 06:02:17'),(6,1,31,NULL,1,'2015-03-13 06:02:17'),(7,1,32,NULL,1,'2015-03-13 06:02:17'),(8,1,36,NULL,1,'2015-03-13 06:02:17'),(9,1,4,NULL,1,'2015-03-13 06:02:17'),(10,1,48,NULL,1,'2015-03-13 06:02:17'),(11,1,5,NULL,1,'2015-03-13 06:02:17'),(12,1,50,NULL,1,'2015-03-13 06:02:17'),(13,1,8,NULL,1,'2015-03-13 06:02:17');
/*!40000 ALTER TABLE `sys_role_menu_rela` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户标识',
  `login_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '登录名',
  `password` varchar(32) DEFAULT NULL COMMENT '密码',
  `nickname` varchar(32) DEFAULT NULL COMMENT '昵称',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `last_change_pwd_time` timestamp NULL DEFAULT NULL COMMENT '最后修改密码时间',
  `last_login_time` timestamp NULL DEFAULT NULL COMMENT '最后登录时间',
  `real_name` varchar(32) DEFAULT NULL COMMENT '真实姓名',
  `signature` varchar(512) DEFAULT NULL COMMENT '签名',
  `photo_icon_url` varchar(512) DEFAULT NULL COMMENT '头像',
  `birthday` timestamp NULL DEFAULT NULL COMMENT '生日',
  `sex` varchar(8) DEFAULT NULL COMMENT '性别',
  `phone_number` varchar(32) DEFAULT NULL COMMENT '手机号码',
  `car_type` varchar(64) DEFAULT NULL COMMENT '汽车型号',
  `country` varchar(32) DEFAULT NULL COMMENT '所属国家',
  `city` varchar(32) DEFAULT NULL COMMENT '所在省/市',
  `district` varchar(32) DEFAULT NULL COMMENT '所在区/县',
  `car_category` varchar(8) DEFAULT NULL COMMENT '汽车种类:\n2:出租车\n4：私家车',
  `org_id` int(11) DEFAULT NULL COMMENT '所属机构',
  `modify_time` timestamp NULL DEFAULT NULL COMMENT '最后修改时间',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `user_type` varchar(8) DEFAULT NULL COMMENT '用户类型\n1：后台\n3：app',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户表:用户基本信息，通过APP端注册、修改维护，登录名称需做唯一性验证。';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,'18810790739','733354ce8abbe01b',NULL,'2014-07-21 07:20:34','2014-09-02 03:50:49','2015-03-11 06:46:25','吴理琪',NULL,NULL,NULL,'男','18810790739','139',NULL,'北京',NULL,'4',1,'2015-03-12 08:59:27',NULL,'1'),(2,'13161780783','733354ce8abbe01b',NULL,'2015-03-12 08:56:49','2015-03-12 08:56:49','2015-03-12 08:56:49','app118',NULL,NULL,NULL,'男','13161780783',NULL,NULL,NULL,NULL,NULL,1,'2015-03-12 09:15:22','转后台用户','1');
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_binding`
--

DROP TABLE IF EXISTS `sys_user_binding`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user_binding` (
  `ub_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '绑定标识',
  `user_id` int(11) DEFAULT NULL COMMENT '用户标识',
  `open_id` varchar(64) DEFAULT NULL COMMENT '第三方登录账号唯一标识',
  `binding_type` varchar(8) DEFAULT NULL COMMENT '绑定类型\n\n2:个推出租车\n4/8:个推私家车（4:个推私家车appstore  8:个推私家车企业版 主要为IOS区分，android为4）',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '绑定时间',
  `os_type` varchar(16) DEFAULT NULL COMMENT '操作系统列型\nandroid:安卓\nios:苹果',
  PRIMARY KEY (`ub_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户绑定表：第三方登录绑定表，暂时不用';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_binding`
--

LOCK TABLES `sys_user_binding` WRITE;
/*!40000 ALTER TABLE `sys_user_binding` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_user_binding` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_gps_info`
--

DROP TABLE IF EXISTS `sys_user_gps_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user_gps_info` (
  `gps_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '地地信息标识',
  `longitude` varchar(32) DEFAULT NULL COMMENT '经度',
  `latitude` varchar(32) DEFAULT NULL COMMENT '纬度',
  `location_time` timestamp NULL DEFAULT NULL COMMENT '定位时间',
  `user_id` int(11) DEFAULT NULL COMMENT '用户标识',
  PRIMARY KEY (`gps_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户GPS信息表:每次用户启动APP时，记录GPS信息，APP与蓝牙设备无连接时，记录GPS信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_gps_info`
--

LOCK TABLES `sys_user_gps_info` WRITE;
/*!40000 ALTER TABLE `sys_user_gps_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_user_gps_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role_rela`
--

DROP TABLE IF EXISTS `sys_user_role_rela`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user_role_rela` (
  `user_role_ref_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户角色关联关系标识。',
  `user_id` int(11) DEFAULT NULL COMMENT '用户标识',
  `role_id` int(11) DEFAULT NULL COMMENT '角色标识。',
  `op_user_id` int(11) DEFAULT NULL COMMENT '操作人员标识',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建操作时间',
  PRIMARY KEY (`user_role_ref_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='系统用户与角色的多对多关系。本实体主要包括系统用户与角色对照标识等属性。';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role_rela`
--

LOCK TABLES `sys_user_role_rela` WRITE;
/*!40000 ALTER TABLE `sys_user_role_rela` DISABLE KEYS */;
INSERT INTO `sys_user_role_rela` VALUES (1,1,1,1,'2015-03-13 06:02:02');
/*!40000 ALTER TABLE `sys_user_role_rela` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_weather`
--

DROP TABLE IF EXISTS `sys_weather`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_weather` (
  `weather_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '天气标识',
  `aqi` varchar(32) DEFAULT NULL COMMENT 'aqi指数',
  `co` varchar(32) DEFAULT NULL COMMENT '一氧化碳值',
  `no2` varchar(32) DEFAULT NULL COMMENT '二氧化氮值',
  `o3` varchar(32) DEFAULT NULL COMMENT '臭氧值',
  `so2` varchar(32) DEFAULT NULL COMMENT '二氧化硫值',
  `pm10` varchar(32) DEFAULT NULL COMMENT 'PM10值',
  `pm25` varchar(32) DEFAULT NULL COMMENT 'PM2.5值',
  `quality` varchar(32) DEFAULT NULL COMMENT '空气质量',
  `humidity` varchar(32) DEFAULT NULL COMMENT '湿度',
  `temperature` varchar(32) DEFAULT NULL COMMENT '温度',
  `weather_text` varchar(32) DEFAULT NULL COMMENT '天气文本',
  `wind_scale` varchar(32) DEFAULT NULL COMMENT '风级',
  `us_embassy_aqi` varchar(32) DEFAULT NULL COMMENT '美大使馆aqi值',
  `think_page_weather` varchar(4096) DEFAULT NULL COMMENT '心知天气JSON文本',
  `last_update` timestamp NULL DEFAULT NULL COMMENT '最后更新时间',
  `remark1` varchar(32) DEFAULT NULL COMMENT '城市编号',
  `remark2` varchar(512) DEFAULT NULL COMMENT '城市名称',
  `user_id` int(11) DEFAULT NULL,
  `us_embassy_aqi_time` timestamp NULL DEFAULT NULL COMMENT '美国大使馆天气预报发布时间',
  `us_embassy_aqi_pm25` varchar(32) DEFAULT NULL COMMENT '美使馆pm25 µg/m³',
  `forecast` varchar(2048) DEFAULT NULL COMMENT '一周天气预报json',
  `weather_code` varchar(8) DEFAULT NULL COMMENT '天气图标编码',
  PRIMARY KEY (`weather_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='天气表\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_weather`
--

LOCK TABLES `sys_weather` WRITE;
/*!40000 ALTER TABLE `sys_weather` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_weather` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-03-13 14:11:02
