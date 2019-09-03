-- MySQL dump 10.13  Distrib 5.7.27, for Linux (x86_64)
--
-- Host: localhost    Database: strs_hiber
-- ------------------------------------------------------
-- Server version	5.7.27-0ubuntu0.18.04.1

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
-- Table structure for table `Department`
--

DROP TABLE IF EXISTS `Department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Department` (
  `code` varchar(15) DEFAULT NULL,
  `id` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_biw7tevwc07g3iutlbmkes0cm` (`name`),
  UNIQUE KEY `UK_17kbfc5vur9ube8cks6abbm23` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Department`
--

LOCK TABLES `Department` WRITE;
/*!40000 ALTER TABLE `Department` DISABLE KEYS */;
INSERT INTO `Department` VALUES ('ICT',7,'ICT'),('FCT_MGT',8,'Facility Management'),('TR_HSPTL',9,'Travel and Hospitality');
/*!40000 ALTER TABLE `Department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Priority`
--

DROP TABLE IF EXISTS `Priority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Priority` (
  `code` varchar(10) DEFAULT NULL,
  `id` int(11) NOT NULL,
  `value` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_tru8bumvrj28l7h099b8fgygc` (`value`),
  UNIQUE KEY `UK_tavmluwo4jfgilsdbheyh86kl` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Priority`
--

LOCK TABLES `Priority` WRITE;
/*!40000 ALTER TABLE `Priority` DISABLE KEYS */;
INSERT INTO `Priority` VALUES ('LOW',10,'Low'),('MED',11,'Medium'),('HIG',12,'High');
/*!40000 ALTER TABLE `Priority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Role`
--

DROP TABLE IF EXISTS `Role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Role` (
  `code` varchar(15) DEFAULT NULL,
  `id` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_n2qtohcsqhwbdejun0ggvgfbl` (`code`),
  UNIQUE KEY `UK_7d8a768x6aiuvmsa24hqiharg` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Role`
--

LOCK TABLES `Role` WRITE;
/*!40000 ALTER TABLE `Role` DISABLE KEYS */;
INSERT INTO `Role` VALUES ('END_U',1,'End User'),('SER_ENGG',3,'Service Engineer'),('ADMN',56,'Admin');
/*!40000 ALTER TABLE `Role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ServiceEngineer`
--

DROP TABLE IF EXISTS `ServiceEngineer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ServiceEngineer` (
  `area_of_expertise_id` int(11) DEFAULT NULL,
  `current_high_priority_ticket_id` int(11) DEFAULT NULL,
  `current_ticket_start_date` date DEFAULT NULL,
  `priority_id` int(11) DEFAULT NULL,
  `service_engineer_id` int(11) NOT NULL,
  `total_tickets_worked_on` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`service_engineer_id`),
  UNIQUE KEY `UK_gtss3rlidhfm16if5r37ti2c8` (`user_id`),
  KEY `FKi3kjiydoxda7iyo88schslv9n` (`priority_id`),
  KEY `FKm8s5r615pekf1o6okshdbmpor` (`current_high_priority_ticket_id`),
  KEY `FKfitpm1q9r3s7jdiw1w9vn3bk6` (`area_of_expertise_id`),
  CONSTRAINT `FKfitpm1q9r3s7jdiw1w9vn3bk6` FOREIGN KEY (`area_of_expertise_id`) REFERENCES `Department` (`id`),
  CONSTRAINT `FKi3kjiydoxda7iyo88schslv9n` FOREIGN KEY (`priority_id`) REFERENCES `Priority` (`id`),
  CONSTRAINT `FKm8s5r615pekf1o6okshdbmpor` FOREIGN KEY (`current_high_priority_ticket_id`) REFERENCES `Ticket` (`id`),
  CONSTRAINT `FKt216rb1mq9star87m7d77fpmu` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ServiceEngineer`
--

LOCK TABLES `ServiceEngineer` WRITE;
/*!40000 ALTER TABLE `ServiceEngineer` DISABLE KEYS */;
INSERT INTO `ServiceEngineer` VALUES (7,94,'2019-08-30',11,16,12,4),(7,NULL,NULL,NULL,17,13,6),(8,NULL,NULL,NULL,34,6,30),(8,NULL,NULL,NULL,35,6,31),(9,NULL,NULL,NULL,78,0,77);
/*!40000 ALTER TABLE `ServiceEngineer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Status`
--

DROP TABLE IF EXISTS `Status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Status` (
  `code` varchar(15) DEFAULT NULL,
  `id` int(11) NOT NULL,
  `value` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_da9ode5mags8exr2uu1xcnp4d` (`value`),
  UNIQUE KEY `UK_fl4v3tptxjs4hiytdd6c4onq1` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Status`
--

LOCK TABLES `Status` WRITE;
/*!40000 ALTER TABLE `Status` DISABLE KEYS */;
INSERT INTO `Status` VALUES ('ON_GO',13,'On going'),('PEND',14,'Pending'),('CLSD',15,'Closed');
/*!40000 ALTER TABLE `Status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Ticket`
--

DROP TABLE IF EXISTS `Ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Ticket` (
  `assigned_to_id` int(11) DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  `closed_date` date DEFAULT NULL,
  `id` int(11) NOT NULL,
  `message` varchar(255) DEFAULT NULL,
  `priority_id` int(11) DEFAULT NULL,
  `requested_by_id` int(11) DEFAULT NULL,
  `requested_end_date` date DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `status_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtbjoudq14iw6jfktcs1urbhwu` (`requested_by_id`),
  KEY `FK7sdfm4r3qkvstu2ao9xccohpn` (`priority_id`),
  KEY `FKk3rot1wc4rk331l3re52obcp9` (`assigned_to_id`),
  KEY `FKdfvhyjvivrws79inb4ects79a` (`category_id`),
  KEY `FKbh4wv8a2tq88i3mjgeoqxag3n` (`status_id`),
  CONSTRAINT `FK7sdfm4r3qkvstu2ao9xccohpn` FOREIGN KEY (`priority_id`) REFERENCES `Priority` (`id`),
  CONSTRAINT `FKbh4wv8a2tq88i3mjgeoqxag3n` FOREIGN KEY (`status_id`) REFERENCES `Status` (`id`),
  CONSTRAINT `FKdfvhyjvivrws79inb4ects79a` FOREIGN KEY (`category_id`) REFERENCES `Department` (`id`),
  CONSTRAINT `FKk3rot1wc4rk331l3re52obcp9` FOREIGN KEY (`assigned_to_id`) REFERENCES `User` (`id`),
  CONSTRAINT `FKtbjoudq14iw6jfktcs1urbhwu` FOREIGN KEY (`requested_by_id`) REFERENCES `User` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Ticket`
--

LOCK TABLES `Ticket` WRITE;
/*!40000 ALTER TABLE `Ticket` DISABLE KEYS */;
INSERT INTO `Ticket` VALUES (4,7,'2019-08-21',27,'zxcv',10,2,'2019-08-20','2019-08-20',15),(6,7,'2019-08-21',28,'Hi sandeep',10,2,'2019-08-22','2019-08-21',15),(4,7,'2019-08-21',29,'asdf',12,2,'2019-08-22','2019-08-21',15),(4,7,'2019-08-21',36,'High Priority ticket',12,2,'2019-08-22','2019-08-21',15),(30,8,'2019-08-23',37,'Need Green Tea packets!',10,5,'2019-08-21','2019-08-21',15),(31,8,'2019-08-23',38,'Need Red Label Tea bags in pantry!',10,2,'2019-08-24','2019-08-21',15),(4,7,'2019-08-23',39,'Do not sleep !',12,2,'2019-08-22','2019-08-22',15),(6,7,'2019-08-22',40,'Need new VGI cable',12,5,'2019-08-22','2019-08-22',15),(6,7,'2019-08-22',41,'this should be assigned to Sandeep',10,2,'2019-08-26','2019-08-22',15),(30,8,'2019-08-23',42,'Need Red lable powder!',10,5,'2019-08-22','2019-08-22',15),(6,7,'2019-08-22',43,'TESTING TESTING',12,2,'2019-08-22','2019-08-22',15),(6,7,'2019-08-23',44,'this should goto sandeep',12,5,'2019-08-22','2019-08-22',15),(4,7,'2019-08-22',45,'this should goto sahit',12,5,'2019-08-22','2019-08-22',15),(4,7,'2019-08-23',46,'where will this go ?',12,5,'2019-08-23','2019-08-23',15),(6,7,'2019-08-23',47,'This should goto Sahit',12,2,'2019-08-25','2019-08-23',15),(30,8,'2019-08-23',48,'10 46 AN',12,2,'2019-08-23','2019-08-23',15),(31,8,'2019-08-23',49,'10 47 AM',12,2,'2019-08-24','2019-08-23',15),(4,8,'2019-08-23',50,'10 48 AM',10,2,'2019-08-24','2019-08-23',15),(30,8,'2019-08-23',51,'10 57 Am',11,2,'2019-08-23','2019-08-23',15),(31,8,'2019-08-23',52,'10 58',11,2,'2019-08-24','2019-08-23',15),(31,8,'2019-08-23',53,'11 00 am',12,2,'2019-08-23','2019-08-23',15),(31,8,'2019-08-29',54,'Hey Shravya',12,2,'2019-08-24','2019-08-23',15),(30,8,'2019-08-29',55,'Hey Sainath',12,2,'2019-08-25','2019-08-23',15),(4,7,'2019-08-30',64,'Need new chair',12,2,'2019-08-26','2019-08-26',15),(6,7,'2019-08-29',65,'Need new book',12,5,'2019-08-26','2019-08-26',15),(4,7,'2019-08-29',66,'Need new keyboard',12,2,'2019-08-26','2019-08-26',15),(6,7,'2019-08-30',67,'hey 10 56 AM',12,2,'2019-08-29','2019-08-29',15),(4,7,'2019-08-30',81,'11 25 AM 30th August',12,5,'2019-08-30','2019-08-30',15),(4,7,'2019-08-30',82,'this should goto sandeep',10,2,'2019-08-30','2019-08-30',15),(30,8,'2019-08-30',83,'Hey sainath 11 52 AM',10,2,'2019-08-30','2019-08-30',15),(31,8,'2019-08-30',84,'Hey Shravya 11 53 AM',11,2,'2019-08-30','2019-08-30',15),(30,8,'2019-08-30',85,'1 PM',12,5,'2019-08-30','2019-08-30',15),(30,8,'2019-08-30',86,'this should goto shravya, priority = high',10,2,'2019-08-30','2019-08-30',15),(30,8,'2019-08-30',87,'This should GOTO Shravya',10,5,'2019-08-30','2019-08-30',15),(4,7,'2019-08-30',88,'5 : 07 PM',11,5,'2019-08-30','2019-08-30',15),(6,7,'2019-08-30',89,'5 8 PM',11,5,'2019-08-31','2019-08-30',15),(6,7,'2019-08-30',90,'Hey sandeep vanga',10,5,'2019-08-30','2019-08-30',15),(4,7,NULL,91,'Hey Sandeep!',10,2,'2019-08-30','2019-08-30',14),(6,7,'2019-08-30',92,'Where will this go? (Sandeep)',10,2,'2019-08-30','2019-08-30',15),(6,7,'2019-08-30',93,'This should also goto sandeep',12,5,'2019-08-30','2019-08-30',15),(4,7,NULL,94,'Where will this GO ?',11,5,'2019-08-30','2019-08-30',13),(6,7,'2019-08-30',95,'HEY ?',11,2,'2019-08-30','2019-08-30',15);
/*!40000 ALTER TABLE `Ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_bxs8c2q8tbrkh2hdweuly6psa` (`user_name`),
  KEY `FK84qlpfci484r1luck11eno6ec` (`role_id`),
  CONSTRAINT `FK84qlpfci484r1luck11eno6ec` FOREIGN KEY (`role_id`) REFERENCES `Role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (2,'Akash Macha','Ak123',1,'Akash'),(4,'Sahit Katta','Sa123',3,'Sahit'),(5,'Mahihar','Ma123',1,'Mani'),(6,'Sandeep Vanga','Sa123',3,'Sandeep'),(30,'Sainath Jonnala','Sa123',3,'Sainath'),(31,'Shravya Dharmapuri','Sh123',3,'Shravya'),(32,'Ahishek Jaksani','Ab123',3,'Ahishek'),(33,'John Doe','Jo123',3,'John'),(57,'Admin','admin',56,'Admin'),(63,'Macha Pruthvi','Pr123',1,'Pruthvi'),(71,'Haneesh','Ha123',1,'Haneesh'),(74,'Ravi','Ra123',3,'Ravi'),(77,'Maneesh','Ma123',3,'Maneesh');
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (96),(96),(96),(96),(96),(96),(96),(96),(96),(96),(96),(96),(96),(96);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_entity`
--

DROP TABLE IF EXISTS `role_entity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_entity` (
  `id` int(11) NOT NULL,
  `code` varchar(15) NOT NULL,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_fcglmetr56ps8ns6rnf7uftbc` (`code`),
  UNIQUE KEY `UK_2uqxlfg1dlwv0mtewrokr23ou` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_entity`
--

LOCK TABLES `role_entity` WRITE;
/*!40000 ALTER TABLE `role_entity` DISABLE KEYS */;
/*!40000 ALTER TABLE `role_entity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_entity`
--

DROP TABLE IF EXISTS `user_entity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_entity` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_9chx1dmnxuaapik68vwo6gvo7` (`user_name`),
  KEY `FKc50fb2m5pqs8711tbas2jljlu` (`role_id`),
  CONSTRAINT `FKc50fb2m5pqs8711tbas2jljlu` FOREIGN KEY (`role_id`) REFERENCES `role_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_entity`
--

LOCK TABLES `user_entity` WRITE;
/*!40000 ALTER TABLE `user_entity` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_entity` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-09-03 10:49:10
