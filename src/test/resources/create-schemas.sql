-- MySQL dump 10.13  Distrib 5.7.20, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: kts_test
-- ------------------------------------------------------
-- Server version	5.7.20-0ubuntu0.17.10.1

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
-- Table structure for table `announcement`
--

DROP TABLE IF EXISTS `announcement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `announcement` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `posted_at` tinyblob,
  `building_id` bigint(20) DEFAULT NULL,
  `poster_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqrjaehdqksi5wdccf8r3nwmj0` (`building_id`),
  KEY `FK2rdmv1f7lvieb80pqwm40yo71` (`poster_id`),
  CONSTRAINT `FK2rdmv1f7lvieb80pqwm40yo71` FOREIGN KEY (`poster_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKqrjaehdqksi5wdccf8r3nwmj0` FOREIGN KEY (`building_id`) REFERENCES `building` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `answer`
--

DROP TABLE IF EXISTS `answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `answer` (
  `dtype` varchar(31) NOT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `question_answered_id` bigint(20) DEFAULT NULL,
  `respondant_id` bigint(20) DEFAULT NULL,
  `content_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgj9w1okh483c4666lo3ogg428` (`question_answered_id`),
  KEY `FKt2xlvsevmfk8lwk597p9dd76w` (`respondant_id`),
  KEY `FKqc1eo0vmk8cb7eaqs0nadjm5a` (`content_id`),
  CONSTRAINT `FKgj9w1okh483c4666lo3ogg428` FOREIGN KEY (`question_answered_id`) REFERENCES `question` (`id`),
  CONSTRAINT `FKqc1eo0vmk8cb7eaqs0nadjm5a` FOREIGN KEY (`content_id`) REFERENCES `offered_answer` (`id`),
  CONSTRAINT `FKt2xlvsevmfk8lwk597p9dd76w` FOREIGN KEY (`respondant_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `building`
--

DROP TABLE IF EXISTS `building`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `building` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `apartment_count` int(11) NOT NULL,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `manager_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpo7ntep3yb67bix8yayxjlaet` (`manager_id`),
  CONSTRAINT `FKpo7ntep3yb67bix8yayxjlaet` FOREIGN KEY (`manager_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `building_pictures`
--

DROP TABLE IF EXISTS `building_pictures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `building_pictures` (
  `building_id` bigint(20) NOT NULL,
  `pictures` varchar(255) DEFAULT NULL,
  KEY `FKrgc9m8cmli8p886hiox6r1bb1` (`building_id`),
  CONSTRAINT `FKrgc9m8cmli8p886hiox6r1bb1` FOREIGN KEY (`building_id`) REFERENCES `building` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `data` varchar(255) DEFAULT NULL,
  `posted_at` tinyblob,
  `commenter_id` bigint(20) DEFAULT NULL,
  `report_commented_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK44qghiqgaeg9imb7fq2ukffy1` (`commenter_id`),
  KEY `FKplverj3i15fp0rde24bspxo5s` (`report_commented_id`),
  CONSTRAINT `FK44qghiqgaeg9imb7fq2ukffy1` FOREIGN KEY (`commenter_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKplverj3i15fp0rde24bspxo5s` FOREIGN KEY (`report_commented_id`) REFERENCES `report` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `forward`
--

DROP TABLE IF EXISTS `forward`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `forward` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `forwarded_report_id` bigint(20) DEFAULT NULL,
  `forwarded_to_id` bigint(20) DEFAULT NULL,
  `forwarder_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3272k8wk25i1y76edipski9bf` (`forwarded_report_id`),
  KEY `FKrp61n4ia3nia4eqr6plveftf3` (`forwarded_to_id`),
  KEY `FKbd8hnhi6cxlx3lnovredt85s6` (`forwarder_id`),
  CONSTRAINT `FK3272k8wk25i1y76edipski9bf` FOREIGN KEY (`forwarded_report_id`) REFERENCES `report` (`id`),
  CONSTRAINT `FKbd8hnhi6cxlx3lnovredt85s6` FOREIGN KEY (`forwarder_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKrp61n4ia3nia4eqr6plveftf3` FOREIGN KEY (`forwarded_to_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `meeting`
--

DROP TABLE IF EXISTS `meeting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `meeting` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `location` varchar(255) DEFAULT NULL,
  `record` varchar(255) DEFAULT NULL,
  `starts_at` tinyblob,
  `building_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKaw8xqei97m9dmfi9iwvvcy3le` (`building_id`),
  CONSTRAINT `FKaw8xqei97m9dmfi9iwvvcy3le` FOREIGN KEY (`building_id`) REFERENCES `building` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `meeting_accepted_proposals`
--

DROP TABLE IF EXISTS `meeting_accepted_proposals`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `meeting_accepted_proposals` (
  `meeting_id` bigint(20) NOT NULL,
  `accepted_proposals_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_nwlgf0bleraqyuaokv5cr5meb` (`accepted_proposals_id`),
  KEY `FKh893mvwi4lxmr1pp859crelnu` (`meeting_id`),
  CONSTRAINT `FKc7kgn3d9noq4nh2p814y189et` FOREIGN KEY (`accepted_proposals_id`) REFERENCES `proposal` (`id`),
  CONSTRAINT `FKh893mvwi4lxmr1pp859crelnu` FOREIGN KEY (`meeting_id`) REFERENCES `meeting` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `offered_answer`
--

DROP TABLE IF EXISTS `offered_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `offered_answer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `question_answered_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKprj5e6ip5q5j079radakh6g73` (`question_answered_id`),
  CONSTRAINT `FKprj5e6ip5q5j079radakh6g73` FOREIGN KEY (`question_answered_id`) REFERENCES `question` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_2ojme20jpga3r4r79tdso17gi` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `proposal`
--

DROP TABLE IF EXISTS `proposal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `proposal` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `suggested_at` tinyblob,
  `attached_report_id` bigint(20) DEFAULT NULL,
  `meeting_id` bigint(20) DEFAULT NULL,
  `proposer_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKh2rln09rke7lhdq3akplcyar0` (`attached_report_id`),
  KEY `FK3ww3br9v1k99jjmststsppj6h` (`meeting_id`),
  KEY `FKg6080fmn2k01buf5wuyap1ob7` (`proposer_id`),
  CONSTRAINT `FK3ww3br9v1k99jjmststsppj6h` FOREIGN KEY (`meeting_id`) REFERENCES `meeting` (`id`),
  CONSTRAINT `FKg6080fmn2k01buf5wuyap1ob7` FOREIGN KEY (`proposer_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKh2rln09rke7lhdq3akplcyar0` FOREIGN KEY (`attached_report_id`) REFERENCES `report` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `proposal_discuss_on_meeting_votes`
--

DROP TABLE IF EXISTS `proposal_discuss_on_meeting_votes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `proposal_discuss_on_meeting_votes` (
  `proposal_id` bigint(20) NOT NULL,
  `discuss_on_meeting_votes_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_tqrxpdu3a3tmxi9j040gjer6p` (`discuss_on_meeting_votes_id`),
  KEY `FK8vnp8h8cs45waelrpniguyyg7` (`proposal_id`),
  CONSTRAINT `FK8vnp8h8cs45waelrpniguyyg7` FOREIGN KEY (`proposal_id`) REFERENCES `proposal` (`id`),
  CONSTRAINT `FKinsrhfg591t9xupho9fq350rh` FOREIGN KEY (`discuss_on_meeting_votes_id`) REFERENCES `proposal_vote` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `proposal_on_meeting_votes`
--

DROP TABLE IF EXISTS `proposal_on_meeting_votes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `proposal_on_meeting_votes` (
  `proposal_id` bigint(20) NOT NULL,
  `on_meeting_votes_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_riv03yn37kbk6see0np05g8ik` (`on_meeting_votes_id`),
  KEY `FK1769um9qqf16dixwtx96pjp39` (`proposal_id`),
  CONSTRAINT `FK1769um9qqf16dixwtx96pjp39` FOREIGN KEY (`proposal_id`) REFERENCES `proposal` (`id`),
  CONSTRAINT `FKdn11qja0wxcs7hutldemnroag` FOREIGN KEY (`on_meeting_votes_id`) REFERENCES `proposal_vote` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `proposal_vote`
--

DROP TABLE IF EXISTS `proposal_vote`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `proposal_vote` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `vote` varchar(255) DEFAULT NULL,
  `voter_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK340464289hfu2b6pw5ubuh38i` (`voter_id`),
  CONSTRAINT `FK340464289hfu2b6pw5ubuh38i` FOREIGN KEY (`voter_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `question` (
  `dtype` varchar(31) NOT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `question_text` varchar(255) DEFAULT NULL,
  `question_form_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcy0bo0ey4fcjalwm5ht1tl9vo` (`question_form_id`),
  CONSTRAINT `FKcy0bo0ey4fcjalwm5ht1tl9vo` FOREIGN KEY (`question_form_id`) REFERENCES `question_form` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `question_form`
--

DROP TABLE IF EXISTS `question_form`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `question_form` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `closed_at` tinyblob,
  `opened_at` tinyblob,
  `started_at` tinyblob,
  `status` int(11) DEFAULT NULL,
  `building_id` bigint(20) DEFAULT NULL,
  `creator_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3agy4fojt7pxe6ff9skuo7vtv` (`building_id`),
  KEY `FK3n1xqwlaitc0orlby1fgsamob` (`creator_id`),
  CONSTRAINT `FK3agy4fojt7pxe6ff9skuo7vtv` FOREIGN KEY (`building_id`) REFERENCES `building` (`id`),
  CONSTRAINT `FK3n1xqwlaitc0orlby1fgsamob` FOREIGN KEY (`creator_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `report`
--

DROP TABLE IF EXISTS `report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `report` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` LONGTEXT DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `location_id` bigint(20) DEFAULT NULL,
  `sender_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3d2517ya9rjyjomcgfxmxxc12` (`location_id`),
  KEY `FKl0s7x00j3s7tr3ds2a85je2h5` (`sender_id`),
  CONSTRAINT `FK3d2517ya9rjyjomcgfxmxxc12` FOREIGN KEY (`location_id`) REFERENCES `building` (`id`),
  CONSTRAINT `FKl0s7x00j3s7tr3ds2a85je2h5` FOREIGN KEY (`sender_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `report_pictures`
--

DROP TABLE IF EXISTS `report_pictures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `report_pictures` (
  `report_id` bigint(20) NOT NULL,
  `pictures` varchar(255) DEFAULT NULL,
  KEY `FK5xn74e76yt29ss869y7mkj5bx` (`report_id`),
  CONSTRAINT `FK5xn74e76yt29ss869y7mkj5bx` FOREIGN KEY (`report_id`) REFERENCES `report` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `residence`
--

DROP TABLE IF EXISTS `residence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `residence` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `apartment_number` int(11) NOT NULL,
  `floor_number` int(11) NOT NULL,
  `apartment_owner_id` bigint(20) DEFAULT NULL,
  `building_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK398yj3k4m42xo0j69d06t5xvf` (`apartment_owner_id`),
  KEY `FK2xgdjkyem1qkweyl2h6vqqy2f` (`building_id`),
  CONSTRAINT `FK2xgdjkyem1qkweyl2h6vqqy2f` FOREIGN KEY (`building_id`) REFERENCES `building` (`id`),
  CONSTRAINT `FK398yj3k4m42xo0j69d06t5xvf` FOREIGN KEY (`apartment_owner_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `resident_residence`
--

DROP TABLE IF EXISTS `resident_residence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resident_residence` (
  `residence_id` bigint(20) NOT NULL,
  `resident_id` bigint(20) NOT NULL,
  KEY `FKd4buhl8ao7k9epq4h1thhdy4r` (`resident_id`),
  KEY `FKcxwbaq0jgau6g8laxukoek0f6` (`residence_id`),
  CONSTRAINT `FKcxwbaq0jgau6g8laxukoek0f6` FOREIGN KEY (`residence_id`) REFERENCES `residence` (`id`),
  CONSTRAINT `FKd4buhl8ao7k9epq4h1thhdy4r` FOREIGN KEY (`resident_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_8sewwnpamngi6b1dwaa88askk` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `role_permissions`
--

DROP TABLE IF EXISTS `role_permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_permissions` (
  `role_id` bigint(20) NOT NULL,
  `permissions_id` bigint(20) NOT NULL,
  KEY `FKclluu29apreb6osx6ogt4qe16` (`permissions_id`),
  KEY `FKlodb7xh4a2xjv39gc3lsop95n` (`role_id`),
  CONSTRAINT `FKclluu29apreb6osx6ogt4qe16` FOREIGN KEY (`permissions_id`) REFERENCES `permission` (`id`),
  CONSTRAINT `FKlodb7xh4a2xjv39gc3lsop95n` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_type` varchar(31) NOT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `pib` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKn82ha3ccdebhokx3a8fgdqeyy` (`role_id`),
  CONSTRAINT `FKn82ha3ccdebhokx3a8fgdqeyy` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_pictures`
--

DROP TABLE IF EXISTS `user_pictures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_pictures` (
  `user_id` bigint(20) NOT NULL,
  `pictures` varchar(255) DEFAULT NULL,
  KEY `FKjf2kxn67n2lfogmm7wnv88mp` (`user_id`),
  CONSTRAINT `FKjf2kxn67n2lfogmm7wnv88mp` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-12-14  5:19:47