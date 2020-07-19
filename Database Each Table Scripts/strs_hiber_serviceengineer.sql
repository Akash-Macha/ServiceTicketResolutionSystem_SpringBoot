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
-- Table structure for table `serviceengineer`
--

DROP TABLE IF EXISTS `serviceengineer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `serviceengineer` (
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
  CONSTRAINT `FKfitpm1q9r3s7jdiw1w9vn3bk6` FOREIGN KEY (`area_of_expertise_id`) REFERENCES `department` (`id`),
  CONSTRAINT `FKi3kjiydoxda7iyo88schslv9n` FOREIGN KEY (`priority_id`) REFERENCES `priority` (`id`),
  CONSTRAINT `FKm8s5r615pekf1o6okshdbmpor` FOREIGN KEY (`current_high_priority_ticket_id`) REFERENCES `ticket` (`id`),
  CONSTRAINT `FKt216rb1mq9star87m7d77fpmu` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `serviceengineer`
--

LOCK TABLES `serviceengineer` WRITE;
/*!40000 ALTER TABLE `serviceengineer` DISABLE KEYS */;
INSERT INTO `serviceengineer` VALUES (7,167,'2019-10-08',12,16,26,4),(7,171,'2019-10-09',10,17,21,6),(8,NULL,NULL,NULL,34,13,30),(8,NULL,NULL,NULL,35,8,31),(9,NULL,NULL,NULL,78,10,77);
/*!40000 ALTER TABLE `serviceengineer` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-07-19 17:40:11
