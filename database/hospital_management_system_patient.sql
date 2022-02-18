-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: hospital_management_system
-- ------------------------------------------------------
-- Server version	8.0.22

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `patient`
--

DROP TABLE IF EXISTS `patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patient` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fname` varchar(45) DEFAULT NULL,
  `lname` varchar(45) DEFAULT NULL,
  `dob` varchar(45) DEFAULT NULL,
  `medical_history` varchar(100) DEFAULT NULL,
  `description` longtext,
  `admit_date` varchar(45) DEFAULT NULL,
  `status` varchar(20) DEFAULT 'Pending',
  `doctor` int DEFAULT NULL,
  `nurse` int DEFAULT NULL,
  `diagnosis` varchar(200) DEFAULT 'None',
  `prescription` varchar(200) DEFAULT 'None',
  PRIMARY KEY (`id`),
  KEY `doctor_idx` (`doctor`),
  KEY `nurse_idx` (`nurse`),
  CONSTRAINT `doctor` FOREIGN KEY (`doctor`) REFERENCES `staff` (`idstaff`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `nurse` FOREIGN KEY (`nurse`) REFERENCES `staff` (`idstaff`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient`
--

LOCK TABLES `patient` WRITE;
/*!40000 ALTER TABLE `patient` DISABLE KEYS */;
INSERT INTO `patient` VALUES (24,'sdadasd','asdasd','Mon Sep 13 23:23:12 NPT 2021','asdasd','asdasd','Mon Sep 13 23:23:12 NPT 2021','Pending',3,4,'Broken left rib','Asprin'),(25,'sadsad','asdasd','Mon Sep 13 23:23:37 NPT 2021','asdasd','sadasd','Mon Sep 13 23:23:37 NPT 2021','Pending',3,4,'Not Performed','None'),(27,'sadsadsd','asdsad','Mon Sep 13 23:23:47 NPT 2021','asdasd','asdsdasd','Mon Sep 13 23:23:47 NPT 2021','Admitted',5,6,'Puncture in left lung','None'),(28,'Ruth','Caraway','Thu Sep 07 00:00:00 NPT 1995','Alzimer','Hand covered with blood and mud','Fri Sep 17 09:34:56 NPT 2021','pending',5,4,'Fractured Left Rib','None'),(29,'Bruce','Wayne','Mon Sep 03 00:00:00 NPT 1990','Heart Complications','Seems to have diffuculty breathing','Fri Sep 17 21:58:39 NPT 2021','Pending',3,6,'None','None'),(30,'Tony','Stark','Sun Sep 17 00:00:00 NPT 1995','Alzimer','High Fever','Fri Sep 17 22:09:26 NPT 2021','Pending',5,4,'None','None'),(31,'Abb','Cde','Thu Sep 30 18:17:22 NPT 2021','None','Test Patient','Thu Sep 30 18:17:22 NPT 2021','Pending',3,4,'None','None');
/*!40000 ALTER TABLE `patient` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-10-06 22:24:10
