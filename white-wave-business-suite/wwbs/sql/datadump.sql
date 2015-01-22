CREATE DATABASE  IF NOT EXISTS `wwbusinesssuite` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `wwbusinesssuite`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win64 (x86_64)
--
-- Host: localhost    Database: wwbusinesssuite
-- ------------------------------------------------------
-- Server version	5.6.21-log

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
-- Table structure for table `asset`
--

DROP TABLE IF EXISTS `asset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset` (
  `Asset_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Asset_Cumulative_Price` double DEFAULT NULL,
  `Asset_DoP` varchar(255) DEFAULT NULL,
  `assetLocation` varchar(255) DEFAULT NULL,
  `Asset_Name` varchar(255) DEFAULT NULL,
  `Asset_Price` double DEFAULT NULL,
  `Model_Number` varchar(255) DEFAULT NULL,
  `Serial_Number` varchar(255) DEFAULT NULL,
  `Warranty_Information` varchar(255) DEFAULT NULL,
  `FK_Client` int(11) DEFAULT NULL,
  PRIMARY KEY (`Asset_ID`),
  KEY `FK_251ln3wmgnkj470eapn05fwcb` (`FK_Client`),
  CONSTRAINT `FK_251ln3wmgnkj470eapn05fwcb` FOREIGN KEY (`FK_Client`) REFERENCES `client` (`Client_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asset`
--

LOCK TABLES `asset` WRITE;
/*!40000 ALTER TABLE `asset` DISABLE KEYS */;
INSERT INTO `asset` VALUES (1,175,'01/22/2010','internal','HP Printer1',120,'HP123456','194019344b','none',1);
/*!40000 ALTER TABLE `asset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `client` (
  `Client_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Client_Name` varchar(255) DEFAULT NULL,
  `Client_Phone_Number` varchar(255) DEFAULT NULL,
  `Comments` varchar(255) DEFAULT NULL,
  `Hourly_Rate` double DEFAULT NULL,
  `Organization` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Client_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES (1,'Jane Doe','(916) 555-5555','faculty',35,'Sacramento State');
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supportevent`
--

DROP TABLE IF EXISTS `supportevent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `supportevent` (
  `Support_Event_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Comments` varchar(255) DEFAULT NULL,
  `Creation_Date` datetime DEFAULT NULL,
  `Hours_Worked` double DEFAULT NULL,
  `Support_Event_Name` varchar(255) DEFAULT NULL,
  `FK_Ticket` int(11) DEFAULT NULL,
  `FK_SupportEvents` int(11) DEFAULT NULL,
  PRIMARY KEY (`Support_Event_ID`),
  KEY `FK_he1m5op95affy3s3lm44b3tr8` (`FK_Ticket`),
  KEY `FK_4a8pe8jtnotdu4qe9vx7t6dor` (`FK_SupportEvents`),
  CONSTRAINT `FK_4a8pe8jtnotdu4qe9vx7t6dor` FOREIGN KEY (`FK_SupportEvents`) REFERENCES `ticket` (`Ticket_ID`),
  CONSTRAINT `FK_he1m5op95affy3s3lm44b3tr8` FOREIGN KEY (`FK_Ticket`) REFERENCES `ticket` (`Ticket_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supportevent`
--

LOCK TABLES `supportevent` WRITE;
/*!40000 ALTER TABLE `supportevent` DISABLE KEYS */;
INSERT INTO `supportevent` VALUES (1,'This was tricky because ljasdfl hasdflj jdkfjjd jladf','2014-12-15 19:18:24',3,'Worked on getting updates',1,1);
/*!40000 ALTER TABLE `supportevent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `technician`
--

DROP TABLE IF EXISTS `technician`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `technician` (
  `Technician_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Is_Admin` bit(1) NOT NULL,
  `Creation_Date` datetime NOT NULL,
  `Email_Address` varchar(255) NOT NULL,
  `Full_Name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `Last_Login` datetime DEFAULT NULL,
  `User_Name` varchar(255) NOT NULL,
  PRIMARY KEY (`Technician_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `technician`
--

LOCK TABLES `technician` WRITE;
/*!40000 ALTER TABLE `technician` DISABLE KEYS */;
INSERT INTO `technician` VALUES (1,'\0','2014-12-15 18:50:13','example@yahoo.com','John Doe','6d598cf27b2d0ace71891c813cd2a9363f64a319163eb2da687536b9b746e5bad86e2f007f2cf998a148f01fbe9a9976ac413f64c8c9e220a1c2846732a939a4',NULL,'TestTech');
/*!40000 ALTER TABLE `technician` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ticket` (
  `Ticket_ID` int(11) NOT NULL AUTO_INCREMENT,
  `is_Open` bit(1) DEFAULT NULL,
  `Ticket_Creator` varchar(255) DEFAULT NULL,
  `Ticket_Description` varchar(255) DEFAULT NULL,
  `Ticket_End` datetime DEFAULT NULL,
  `Ticket_Name` varchar(255) DEFAULT NULL,
  `Ticket_Priority` int(11) DEFAULT NULL,
  `Ticket_Start` datetime DEFAULT NULL,
  `Ticket_Total_Time` double DEFAULT NULL,
  `FK_Client` int(11) DEFAULT NULL,
  `FK_Technician` int(11) DEFAULT NULL,
  PRIMARY KEY (`Ticket_ID`),
  KEY `FK_cb794hg13yq0huid88ikgk2ct` (`FK_Client`),
  KEY `FK_sbdi7viq8163n00uj0bnd04bj` (`FK_Technician`),
  CONSTRAINT `FK_cb794hg13yq0huid88ikgk2ct` FOREIGN KEY (`FK_Client`) REFERENCES `client` (`Client_ID`),
  CONSTRAINT `FK_sbdi7viq8163n00uj0bnd04bj` FOREIGN KEY (`FK_Technician`) REFERENCES `technician` (`Technician_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket`
--

LOCK TABLES `ticket` WRITE;
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
INSERT INTO `ticket` VALUES (1,'','John Doe','Client needs updates for new software compatibility.',NULL,'Install updates on new machines',1,'2014-12-15 18:54:22',0,1,1);
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

-- Dump completed on 2014-12-16 11:30:34
