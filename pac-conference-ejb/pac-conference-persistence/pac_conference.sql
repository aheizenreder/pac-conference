-- MySQL dump 10.13  Distrib 5.5.34, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: pac_conference
-- ------------------------------------------------------
-- Server version	5.5.34-0ubuntu0.13.04.1

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
-- Table structure for table `conference_conference`
--

DROP TABLE IF EXISTS `conference_conference`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `conference_conference` (
  `id` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `end_date` datetime NOT NULL,
  `start_date` datetime NOT NULL,
  `title` varchar(50) NOT NULL,
  `location_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FK787F4C1FCDDB9128` (`location_id`),
  CONSTRAINT `FK787F4C1FCDDB9128` FOREIGN KEY (`location_id`) REFERENCES `location_location` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
-- Table structure for table `location_location`
--

DROP TABLE IF EXISTS `location_location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `location_location` (
  `id` bigint(20) NOT NULL,
  `city` varchar(50) NOT NULL,
  `country` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `house_number` varchar(10) NOT NULL,
  `name` varchar(50) NOT NULL,
  `postalcode` varchar(10) NOT NULL,
  `street` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `location_room`
--

DROP TABLE IF EXISTS `location_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `location_room` (
  `id` bigint(20) NOT NULL,
  `capacity` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  `location_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FK94E92325CDDB9128` (`location_id`),
  CONSTRAINT `FK94E92325CDDB9128` FOREIGN KEY (`location_id`) REFERENCES `location_location` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `speaker_speaker`
--

DROP TABLE IF EXISTS `speaker_speaker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `speaker_speaker` (
  `id` bigint(20) NOT NULL,
  `description` varchar(255) NOT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `talk_talk`
--

DROP TABLE IF EXISTS `talk_talk`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `talk_talk` (
  `id` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `duration` int(11) NOT NULL,
  `start_date` datetime NOT NULL,
  `title` varchar(50) NOT NULL,
  `conference_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FK987CC45F652E91C1` (`conference_id`),
  CONSTRAINT `FK987CC45F652E91C1` FOREIGN KEY (`conference_id`) REFERENCES `conference_conference` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `talk_talk_to_room`
--

DROP TABLE IF EXISTS `talk_talk_to_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `talk_talk_to_room` (
  `room_id` bigint(20) NOT NULL DEFAULT '0',
  `talk_id` bigint(20) NOT NULL DEFAULT '0',
  `end_date` datetime NOT NULL,
  `start_date` datetime NOT NULL,
  PRIMARY KEY (`room_id`,`talk_id`),
  UNIQUE KEY `talk_id` (`talk_id`,`room_id`),
  KEY `FKE9A9C3F390B2E68` (`room_id`),
  KEY `FKE9A9C3F5B49C371` (`talk_id`),
  CONSTRAINT `FKE9A9C3F390B2E68` FOREIGN KEY (`room_id`) REFERENCES `location_room` (`id`),
  CONSTRAINT `FKE9A9C3F5B49C371` FOREIGN KEY (`talk_id`) REFERENCES `talk_talk` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `talk_talk_to_speaker`
--

DROP TABLE IF EXISTS `talk_talk_to_speaker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `talk_talk_to_speaker` (
  `speaker_id` bigint(20) NOT NULL DEFAULT '0',
  `talk_id` bigint(20) NOT NULL DEFAULT '0',
  `end_date` datetime NOT NULL,
  `start_date` datetime NOT NULL,
  PRIMARY KEY (`speaker_id`,`talk_id`),
  UNIQUE KEY `talk_id` (`talk_id`,`speaker_id`),
  KEY `FKB036DA1BE3517AB6` (`speaker_id`),
  KEY `FKB036DA1B5B49C371` (`talk_id`),
  CONSTRAINT `FKB036DA1B5B49C371` FOREIGN KEY (`talk_id`) REFERENCES `talk_talk` (`id`),
  CONSTRAINT `FKB036DA1BE3517AB6` FOREIGN KEY (`speaker_id`) REFERENCES `speaker_speaker` (`id`)
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

-- Dump completed on 2014-01-31  9:24:12
