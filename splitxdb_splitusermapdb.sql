-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: splitxdb
-- ------------------------------------------------------
-- Server version	8.0.33

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
-- Table structure for table `splitusermapdb`
--

DROP TABLE IF EXISTS `splitusermapdb`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `splitusermapdb` (
  `splitusermapdbID` int unsigned NOT NULL AUTO_INCREMENT,
  `splitID` int NOT NULL,
  `userID` int NOT NULL,
  `settled` tinyint(1) NOT NULL,
  PRIMARY KEY (`splitusermapdbID`),
  KEY `userID_idx` (`userID`),
  KEY `splitID_idx` (`splitID`),
  CONSTRAINT `splitID` FOREIGN KEY (`splitID`) REFERENCES `splitsdb` (`splitID`),
  CONSTRAINT `userID` FOREIGN KEY (`userID`) REFERENCES `userdb` (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='	';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `splitusermapdb`
--

LOCK TABLES `splitusermapdb` WRITE;
/*!40000 ALTER TABLE `splitusermapdb` DISABLE KEYS */;
INSERT INTO `splitusermapdb` VALUES (5,69,2,0),(6,69,3,1),(7,69,4,0),(8,69,5,1),(9,69,6,0),(10,732,6,0),(11,732,4,0),(12,732,2,0),(13,792,6,0),(14,792,2,0),(17,8145,3,0),(18,8145,4,0),(19,8145,2,0),(20,8145,6,0),(21,4875,6,0),(22,4875,3,0),(23,4875,4,0),(24,8478,7,0),(25,8478,5,0),(26,8478,6,0);
/*!40000 ALTER TABLE `splitusermapdb` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-07-25  4:21:20
