-- MySQL dump 10.13  Distrib 8.0.16, for Win64 (x86_64)
--
-- Host: localhost    Database: strs_hiber
-- ------------------------------------------------------
-- Server version	8.0.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `ticket` (
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
  CONSTRAINT `FK7sdfm4r3qkvstu2ao9xccohpn` FOREIGN KEY (`priority_id`) REFERENCES `priority` (`id`),
  CONSTRAINT `FKbh4wv8a2tq88i3mjgeoqxag3n` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`),
  CONSTRAINT `FKdfvhyjvivrws79inb4ects79a` FOREIGN KEY (`category_id`) REFERENCES `department` (`id`),
  CONSTRAINT `FKk3rot1wc4rk331l3re52obcp9` FOREIGN KEY (`assigned_to_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKtbjoudq14iw6jfktcs1urbhwu` FOREIGN KEY (`requested_by_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket`
--

LOCK TABLES `ticket` WRITE;
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
INSERT INTO `ticket` VALUES (4,7,'2019-08-21',27,'zxcv',10,2,'2019-08-20','2019-08-20',15),(6,7,'2019-08-21',28,'Hi sandeep',10,2,'2019-08-22','2019-08-21',15),(4,7,'2019-08-21',29,'asdf',12,2,'2019-08-22','2019-08-21',15),(4,7,'2019-08-21',36,'High Priority ticket',12,2,'2019-08-22','2019-08-21',15),(30,8,'2019-08-23',37,'Need Green Tea packets!',10,5,'2019-08-21','2019-08-21',15),(31,8,'2019-08-23',38,'Need Red Label Tea bags in pantry!',10,2,'2019-08-24','2019-08-21',15),(4,7,'2019-08-23',39,'Do not sleep !',12,2,'2019-08-22','2019-08-22',15),(6,7,'2019-08-22',40,'Need new VGI cable',12,5,'2019-08-22','2019-08-22',15),(6,7,'2019-08-22',41,'this should be assigned to Sandeep',10,2,'2019-08-26','2019-08-22',15),(30,8,'2019-08-23',42,'Need Red lable powder!',10,5,'2019-08-22','2019-08-22',15),(6,7,'2019-08-22',43,'TESTING TESTING',12,2,'2019-08-22','2019-08-22',15),(6,7,'2019-08-23',44,'this should goto sandeep',12,5,'2019-08-22','2019-08-22',15),(4,7,'2019-08-22',45,'this should goto sahit',12,5,'2019-08-22','2019-08-22',15),(4,7,'2019-08-23',46,'where will this go ?',12,5,'2019-08-23','2019-08-23',15),(6,7,'2019-08-23',47,'This should goto Sahit',12,2,'2019-08-25','2019-08-23',15),(30,8,'2019-08-23',48,'10 46 AN',12,2,'2019-08-23','2019-08-23',15),(31,8,'2019-08-23',49,'10 47 AM',12,2,'2019-08-24','2019-08-23',15),(4,8,'2019-08-23',50,'10 48 AM',10,2,'2019-08-24','2019-08-23',15),(30,8,'2019-08-23',51,'10 57 Am',11,2,'2019-08-23','2019-08-23',15),(31,8,'2019-08-23',52,'10 58',11,2,'2019-08-24','2019-08-23',15),(31,8,'2019-08-23',53,'11 00 am',12,2,'2019-08-23','2019-08-23',15),(31,8,'2019-08-29',54,'Hey Shravya',12,2,'2019-08-24','2019-08-23',15),(30,8,'2019-08-29',55,'Hey Sainath',12,2,'2019-08-25','2019-08-23',15),(4,7,'2019-08-30',64,'Need new chair',12,2,'2019-08-26','2019-08-26',15),(6,7,'2019-08-29',65,'Need new book',12,5,'2019-08-26','2019-08-26',15),(4,7,'2019-08-29',66,'Need new keyboard',12,2,'2019-08-26','2019-08-26',15),(6,7,'2019-08-30',67,'hey 10 56 AM',12,2,'2019-08-29','2019-08-29',15),(4,7,'2019-08-30',81,'11 25 AM 30th August',12,5,'2019-08-30','2019-08-30',15),(4,7,'2019-08-30',82,'this should goto sandeep',10,2,'2019-08-30','2019-08-30',15),(30,8,'2019-08-30',83,'Hey sainath 11 52 AM',10,2,'2019-08-30','2019-08-30',15),(31,8,'2019-08-30',84,'Hey Shravya 11 53 AM',11,2,'2019-08-30','2019-08-30',15),(30,8,'2019-08-30',85,'1 PM',12,5,'2019-08-30','2019-08-30',15),(30,8,'2019-08-30',86,'this should goto shravya, priority = high',10,2,'2019-08-30','2019-08-30',15),(30,8,'2019-08-30',87,'This should GOTO Shravya',10,5,'2019-08-30','2019-08-30',15),(4,7,'2019-08-30',88,'5 : 07 PM',11,5,'2019-08-30','2019-08-30',15),(6,7,'2019-08-30',89,'5 8 PM',11,5,'2019-08-31','2019-08-30',15),(6,7,'2019-08-30',90,'Hey sandeep vanga',10,5,'2019-08-30','2019-08-30',15),(4,7,'2019-09-07',91,'Hey Sandeep!',10,2,'2019-08-30','2019-08-30',15),(6,7,'2019-08-30',92,'Where will this go? (Sandeep)',10,2,'2019-08-30','2019-08-30',15),(6,7,'2019-08-30',93,'This should also goto sandeep',12,5,'2019-08-30','2019-08-30',15),(4,7,'2019-09-07',94,'Where will this GO ?',12,5,'2019-08-30','2019-08-30',15),(6,7,'2019-08-30',95,'HEY ?',11,2,'2019-08-30','2019-08-30',15),(77,9,'2019-09-03',102,'Hey bro',10,2,'2019-09-05','2019-09-03',15),(30,8,'2019-10-06',126,'Hey!',10,2,'2019-09-11','2019-09-07',15),(4,7,'2019-10-06',128,'Need a wireless mouse !',12,2,'2019-10-10','2019-10-06',15),(77,9,'2019-10-06',131,'From Spring Boot',11,2,'2019-10-15','2019-10-06',15),(6,7,'2019-10-06',134,'Yo From SpringBoot 1:31 PM',12,2,'2019-10-10','2019-10-06',15),(77,9,'2019-10-06',135,'From Angular!',11,2,'2019-10-24','2019-10-06',15),(4,7,'2019-10-06',137,'4 30 PM oct 6th, priority HIGH',12,2,'2019-10-30','2019-10-06',15),(6,7,'2019-10-06',138,'Posting ar 4 : 39 PM, priority medium',11,2,'2019-10-17','2019-10-06',15),(31,8,'2019-10-06',139,'adfasdf',12,2,'2019-10-30','2019-10-06',15),(30,8,'2019-10-06',140,'as',11,2,'2019-10-16','2019-10-06',15),(30,8,'2019-10-06',141,'I Should SEE You!',11,2,'2019-10-22','2019-10-06',15),(77,9,'2019-10-06',142,'asdf',11,2,'2019-10-08','2019-10-06',15),(4,7,'2019-10-06',143,'I shall see you',12,2,'2019-10-06','2019-10-06',15),(30,8,'2019-10-06',144,'Damn',12,2,'2019-10-14','2019-10-06',15),(6,7,'2019-10-06',145,'I\'ll meet you soon',10,2,'2019-10-09','2019-10-06',15),(77,9,'2019-10-06',146,'6 12 PM',11,2,'2019-10-16','2019-10-06',15),(31,8,'2019-10-06',147,'6 15 PM',10,2,'2019-10-06','2019-10-06',15),(4,7,'2019-10-06',148,'6 18 PM',11,2,'2019-10-16','2019-10-06',15),(30,8,'2019-10-07',149,'6 19 PM',12,2,'2019-10-17','2019-10-06',15),(77,9,'2019-10-08',150,'6 23 PM',11,2,'2019-10-17','2019-10-06',15),(4,7,'2019-10-06',151,'6 25 PM',11,2,'2019-10-16','2019-10-06',15),(4,7,'2019-10-07',152,'6 29 PM',11,2,'2019-10-16','2019-10-06',15),(4,7,'2019-10-07',153,'Hey Sahit!',11,5,'2019-10-07','2019-10-07',15),(4,7,'2019-10-07',154,'12:31 AM',10,5,'2019-10-07','2019-10-07',15),(4,7,'2019-10-07',155,'YoHo 12:33 AM',10,5,'2019-10-07','2019-10-07',15),(4,7,'2019-10-07',157,'Test 12:55 AM',11,2,'2019-10-07','2019-10-06',15),(4,7,'2019-10-08',158,'TIME: 12:00 AM 08-10-19',12,5,'2019-10-09','2019-10-08',15),(6,7,'2019-10-08',159,'Hye Boii 12 : 57 AM',11,2,'2019-10-15','2019-10-07',15),(6,7,'2019-10-08',160,'02:09 AM Oct 8th 2019',10,2,'2019-10-08','2019-10-07',15),(30,8,'2019-10-08',161,'2:34 Am 8th Oct 2019',10,2,'2019-10-09','2019-10-08',15),(30,8,'2019-10-08',162,'02:36 Am 8th Oct 2019!',10,2,'2019-10-09','2019-10-07',15),(6,7,'2019-10-08',163,'2:41 Am 8th Oct 2019',10,2,'2019-10-11','2019-10-08',15),(77,9,'2019-10-08',164,'03:08 Am 8th Oct 2019',11,2,'2019-10-30','2019-10-07',15),(6,7,'2019-10-08',165,'3:50 AM oct 8th 2019',10,2,'2019-10-09','2019-10-08',15),(6,7,'2019-10-08',166,'03:52 AM',12,5,'2019-10-09','2019-10-08',15),(4,7,NULL,167,'11:36 AM 8th Oct 2019',12,2,'2019-10-24','2019-10-08',13),(77,9,'2019-10-08',168,'12:04 PM 8th Oct 2019, checking the RequestedEndDate!',12,2,'2019-10-30','2019-10-08',15),(77,9,'2019-10-08',169,'12:13 PM 8th Oct 2019, startDate: 8th Oct 2019, requstedEndDate: 25th Oct 2019, Priortiy: High, TravelAndHospitality',12,5,NULL,NULL,15),(6,7,NULL,170,'9 : 55 PM 9th Oct',10,5,'2019-10-09','2019-10-09',14),(6,7,NULL,171,'9 57 Pm 9th oct',10,2,'2019-10-11','2019-10-09',13);
/*!40000 ALTER TABLE `ticket` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-07-19 17:40:12
