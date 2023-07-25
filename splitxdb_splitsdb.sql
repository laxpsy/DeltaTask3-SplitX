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
-- Table structure for table `splitsdb`
--

DROP TABLE IF EXISTS `splitsdb`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `splitsdb` (
  `splitID` int NOT NULL AUTO_INCREMENT,
  `split_amount` int NOT NULL,
  `total_users` int NOT NULL,
  `split_origin_id` int NOT NULL,
  `reason` varchar(45) NOT NULL,
  PRIMARY KEY (`splitID`),
  UNIQUE KEY `transactionID_UNIQUE` (`splitID`),
  KEY `split_origin_id` (`split_origin_id`),
  CONSTRAINT `splitsdb_ibfk_1` FOREIGN KEY (`split_origin_id`) REFERENCES `userdb` (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=8479 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `splitsdb`
--

LOCK TABLES `splitsdb` WRITE;
/*!40000 ALTER TABLE `splitsdb` DISABLE KEYS */;
INSERT INTO `splitsdb` VALUES (65,600,3,6,'testRunLast'),(69,1000,3,2,'Testing#1'),(732,2500,3,6,'Testing#2'),(792,500,2,6,'Testing#3'),(4875,600,3,6,'testRunLast'),(8145,3100,4,6,'Success!'),(8478,1900,3,7,'finalTestRegister');
/*!40000 ALTER TABLE `splitsdb` ENABLE KEYS */;
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
