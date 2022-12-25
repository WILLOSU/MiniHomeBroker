-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: database2011
-- ------------------------------------------------------
-- Server version	8.0.31

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
-- Table structure for table `ativoconta`
--

DROP TABLE IF EXISTS `ativoconta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ativoconta` (
  `idAtivoConta` int NOT NULL AUTO_INCREMENT,
  `conta` int DEFAULT NULL,
  `ativo` int DEFAULT NULL,
  `totalAtivos` int DEFAULT NULL,
  `valorCompra` float DEFAULT NULL,
  `dataCriacao` date DEFAULT NULL,
  `dataModificacao` date DEFAULT NULL,
  PRIMARY KEY (`idAtivoConta`),
  KEY `idContaFK_idx` (`conta`),
  KEY `idAtivoFK1_idx` (`ativo`),
  CONSTRAINT `idAtivoFK1` FOREIGN KEY (`ativo`) REFERENCES `ativos` (`idativos`),
  CONSTRAINT `idContaFK2` FOREIGN KEY (`conta`) REFERENCES `conta` (`idConta`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ativoconta`
--

LOCK TABLES `ativoconta` WRITE;
/*!40000 ALTER TABLE `ativoconta` DISABLE KEYS */;
INSERT INTO `ativoconta` VALUES (1,1,1,400,11.47,'2022-12-18','2022-12-20'),(2,1,2,452,20.41,'2022-12-18','2022-12-18'),(3,1,3,142,13.18,'2022-12-18','2022-12-18'),(4,1,4,100,3.92,'2022-12-18','2022-12-18'),(5,1,5,29,21.79,'2022-12-18','2022-12-18'),(6,1,6,45,8.75,'2022-12-18','2022-12-20'),(7,1,7,20,167.21,'2022-12-18','2022-12-18'),(8,1,8,30,106.03,'2022-12-18','2022-12-18'),(12,1,9,45,115.91,'2022-12-18','2022-12-18'),(13,1,10,48,124.8,'2022-12-18','2022-12-18'),(23,12,6,10,8.75,'2022-12-20','2022-12-20');
/*!40000 ALTER TABLE `ativoconta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ativos`
--

DROP TABLE IF EXISTS `ativos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ativos` (
  `idativos` int NOT NULL AUTO_INCREMENT,
  `nomeEmpresa` varchar(45) DEFAULT NULL,
  `ticker` varchar(45) DEFAULT NULL,
  `precoInicial` float DEFAULT NULL,
  `precoAtual` float DEFAULT NULL,
  `dataCriacao` date DEFAULT NULL,
  `dataModificacao` date DEFAULT NULL,
  `tipoUsuario` int DEFAULT NULL,
  `totalAtivos` int DEFAULT NULL,
  PRIMARY KEY (`idativos`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ativos`
--

LOCK TABLES `ativos` WRITE;
/*!40000 ALTER TABLE `ativos` DISABLE KEYS */;
INSERT INTO `ativos` VALUES (1,'ITAUSA','ITSA4',11.47,11.47,'2022-02-02','2022-02-02',1,400),(2,'BRADESCO','BBDC3',20.41,20.41,'2022-02-02','2022-02-02',2,452),(3,'EDENRED','ITAFE4',13.18,13.18,'2022-02-02','2022-02-02',2,142),(4,'SANEPAR','ITAFE4',3.92,3.92,'2022-02-02','2022-02-02',2,100),(5,'SEGURIDADE ON NM','BBSE3',21.79,21.79,'2022-02-02','2022-02-02',2,29),(6,'WHIRLPOOL','WHLR4',8.75,8.75,'2022-02-02','2022-02-02',2,55),(7,'LOGISTICA','HGLG11',167.21,167.21,'2022-02-02','2022-02-02',2,20),(8,'REAL ESTATE CAPITAL','RECRI1',106.03,106.03,'2022-02-02','2022-02-02',2,30),(9,'COVEP RENDA FDO','GGRC11',115.91,115.91,'2022-02-02','2022-02-02',2,45),(10,'BANRISUL NOVAS FRONTEIRAS','BNFS11',124.8,124.8,'2022-02-02','2022-02-02',2,48);
/*!40000 ALTER TABLE `ativos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clientes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) DEFAULT NULL,
  `endereco` varchar(45) DEFAULT NULL,
  `cpf` varchar(45) DEFAULT NULL,
  `telefone` varchar(45) DEFAULT NULL,
  `login` varchar(45) DEFAULT NULL,
  `senha` varchar(45) DEFAULT NULL,
  `tipoUsuario` int DEFAULT NULL,
  `dataCriacao` date DEFAULT NULL,
  `dataModificacao` date DEFAULT NULL,
  `temConta` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
INSERT INTO `clientes` VALUES (1,'AdmMASTER','AV. DOUTOR FLORESTAN FERNANDES , 131','123-231-888-01','034-98808-88-88','0','0',1,'2022-12-11','2022-12-11',1),(2,'LUCAS','RUA JOSE FERNANDO DA SILVA, 876','846.884.546.72','34-8741-45-89','1','1',2,'2022-12-11','2022-12-11',1),(3,'WILLIAM','RUA GORDONHA FIELIS, 0187','945.884.546.72','35-8791-45-89','2','2',2,'2022-12-11','2022-12-11',1),(5,'William2','dd','1','1','3','3',2,'2022-12-11','2022-12-11',0),(6,'Teste','Teste','18','18','4','4',2,'2022-12-11','2022-12-11',0),(7,'teste2','teste','18','18','5','5',2,'2022-12-11','2022-12-11',0),(8,'Joaquim2','jhkj','lkj','456','6','6',2,'2022-12-13','2022-12-18',1),(13,'fdg','sfd','sdfg','sdf','9','9',2,'2022-12-18','2022-12-18',1),(15,'Eduardonota10','Rua das acacias','159.789','123','20','20',2,'2022-12-20','2022-12-20',1);
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `conta`
--

DROP TABLE IF EXISTS `conta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `conta` (
  `idConta` int NOT NULL AUTO_INCREMENT,
  `cliente` int DEFAULT NULL,
  `contaBolsa` tinyint(1) DEFAULT NULL,
  `saldo` float DEFAULT NULL,
  `dataCriacao` date DEFAULT NULL,
  `dataModificacao` date DEFAULT NULL,
  PRIMARY KEY (`idConta`),
  KEY `idClienteFK_idx` (`cliente`),
  CONSTRAINT `idClienteFK` FOREIGN KEY (`cliente`) REFERENCES `clientes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conta`
--

LOCK TABLES `conta` WRITE;
/*!40000 ALTER TABLE `conta` DISABLE KEYS */;
INSERT INTO `conta` VALUES (1,1,1,440078,'2022-12-12','2023-01-19'),(2,2,0,19000,'2022-12-12','2022-12-20'),(3,3,0,22000,'2022-12-12','2022-12-20'),(12,15,0,19922.5,'2022-12-20','2023-01-19');
/*!40000 ALTER TABLE `conta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `execucao`
--

DROP TABLE IF EXISTS `execucao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `execucao` (
  `idExecucao` int NOT NULL AUTO_INCREMENT,
  `ContaCompra` int DEFAULT NULL,
  `ContaVenda` int DEFAULT NULL,
  `quantidade` int DEFAULT NULL,
  `ordem` int DEFAULT NULL,
  `dataModificacao` date DEFAULT NULL,
  `dataCriacao` date DEFAULT NULL,
  PRIMARY KEY (`idExecucao`),
  KEY `idOrdemFK_idx` (`ordem`),
  KEY `idContaCompraFK_idx` (`ContaCompra`),
  KEY `idContaVendaFK_idx` (`ContaVenda`),
  CONSTRAINT `idContaCompraFK` FOREIGN KEY (`ContaCompra`) REFERENCES `conta` (`idConta`),
  CONSTRAINT `idContaVendaFK` FOREIGN KEY (`ContaVenda`) REFERENCES `conta` (`idConta`),
  CONSTRAINT `idOrdemFK` FOREIGN KEY (`ordem`) REFERENCES `ordem` (`idOrdem`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `execucao`
--

LOCK TABLES `execucao` WRITE;
/*!40000 ALTER TABLE `execucao` DISABLE KEYS */;
INSERT INTO `execucao` VALUES (1,1,2,9,1,'2022-12-17','2022-12-17'),(30,12,1,10,6,'2022-12-20','2022-12-20'),(31,12,1,10,12,'2022-12-20','2022-12-20');
/*!40000 ALTER TABLE `execucao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movimentacaoconta`
--

DROP TABLE IF EXISTS `movimentacaoconta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movimentacaoconta` (
  `idMovimentacao` int NOT NULL AUTO_INCREMENT,
  `contaCorrente` int DEFAULT NULL,
  `tipoMov` int DEFAULT NULL,
  `descricao` varchar(45) DEFAULT NULL,
  `valor` float DEFAULT NULL,
  `dataCriacao` date DEFAULT NULL,
  `dataModificacao` date DEFAULT NULL,
  PRIMARY KEY (`idMovimentacao`),
  KEY `idContaFK4_idx` (`contaCorrente`),
  CONSTRAINT `idContaFK4` FOREIGN KEY (`contaCorrente`) REFERENCES `conta` (`idConta`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movimentacaoconta`
--

LOCK TABLES `movimentacaoconta` WRITE;
/*!40000 ALTER TABLE `movimentacaoconta` DISABLE KEYS */;
INSERT INTO `movimentacaoconta` VALUES (1,1,1,'Ferrou',123.56,'2022-12-25','2022-12-25'),(45,2,1,'Depósito',1000,'2022-12-20','2022-12-20'),(46,2,2,'Transferencia entre contas',2000,'2022-12-20','2022-12-20'),(47,3,1,'Transferencia entre contas',2000,'2022-12-20','2022-12-20'),(48,12,2,'Pagamento para Bolsa',10,'2022-12-20','2022-12-20'),(49,1,1,'Pagamento para Bolsa',10,'2022-12-20','2022-12-20'),(50,12,2,'Transferencia entre contas',87.5,'2022-12-20','2022-12-20'),(51,1,1,'Transferencia entre contas',87.5,'2022-12-20','2022-12-20'),(52,1,2,'Pagamento de Dividendos',20,'2023-01-19','2023-01-19'),(53,12,1,'Pagamento de Dividendos',20,'2023-01-19','2023-01-19');
/*!40000 ALTER TABLE `movimentacaoconta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ordem`
--

DROP TABLE IF EXISTS `ordem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ordem` (
  `idOrdem` int NOT NULL AUTO_INCREMENT,
  `conta` int DEFAULT NULL,
  `tipoOrdem` int DEFAULT NULL,
  `valor` float DEFAULT NULL,
  `ticker` varchar(45) DEFAULT NULL,
  `qtde` int DEFAULT NULL,
  `estadoOrdem` int DEFAULT NULL,
  `valorTotal` float DEFAULT NULL,
  `dataCriacao` date DEFAULT NULL,
  `dataModificacao` date DEFAULT NULL,
  PRIMARY KEY (`idOrdem`),
  KEY `idContaFK6_idx` (`conta`),
  CONSTRAINT `idContaFK6` FOREIGN KEY (`conta`) REFERENCES `conta` (`idConta`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ordem`
--

LOCK TABLES `ordem` WRITE;
/*!40000 ALTER TABLE `ordem` DISABLE KEYS */;
INSERT INTO `ordem` VALUES (1,1,2,11.47,'ITSA4',400,2,4588,'2022-12-10','2022-12-10'),(2,1,2,20.41,'BBDC',452,0,9225.32,'2022-12-18','2022-12-18'),(3,1,2,13.18,'ITAFE4',142,0,1871.56,'2022-12-18','2022-12-18'),(4,1,2,3.92,'ITAFE4',100,0,392,'2022-12-18','2022-12-18'),(5,1,2,21.79,'BBSE3',29,0,631.91,'2022-12-18','2022-12-18'),(6,1,2,8.75,'WHLR4',45,1,393.75,'2022-12-18','2022-12-18'),(7,1,2,167.21,'HGLG11',20,0,3344.2,'2022-12-18','2022-12-18'),(8,1,2,106.03,'RECR11',30,0,318.09,'2022-12-18','2022-12-18'),(9,2,1,12,'ITSA4',200,2,2400,'2022-12-19','2022-12-19'),(11,1,1,107,'RECR11',10,0,1070,'2022-12-19','2022-12-19'),(12,12,1,9,'WHLR4',10,2,90,'2022-12-20','2022-12-20');
/*!40000 ALTER TABLE `ordem` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-19 22:11:30
