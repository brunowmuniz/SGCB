CREATE DATABASE  IF NOT EXISTS `gcb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `gcb`;
-- MySQL dump 10.13  Distrib 5.6.10, for Win64 (x86_64)
--
-- Host: localhost    Database: gcb
-- ------------------------------------------------------
-- Server version	5.6.10

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
-- Table structure for table `assistenciatecnica`
--

DROP TABLE IF EXISTS `assistenciatecnica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `assistenciatecnica` (
  `id` int(11) NOT NULL,
  `ocproduto` int(11) NOT NULL,
  `montador` int(11) NOT NULL,
  `datainicio` date NOT NULL,
  `datafim` date NOT NULL,
  `observacoes` text,
  `valor` float(5,2) NOT NULL,
  `deleted` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`id`),
  KEY `fk_assistenciatecnica_ocproduto1_idx` (`ocproduto`),
  KEY `fk_assistenciatecnica_usuario1_idx` (`montador`),
  CONSTRAINT `fk_assistenciatecnica_ocproduto1` FOREIGN KEY (`ocproduto`) REFERENCES `ocproduto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_assistenciatecnica_usuario1` FOREIGN KEY (`montador`) REFERENCES `usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assistenciatecnica`
--

LOCK TABLES `assistenciatecnica` WRITE;
/*!40000 ALTER TABLE `assistenciatecnica` DISABLE KEYS */;
/*!40000 ALTER TABLE `assistenciatecnica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cliente` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(150) NOT NULL,
  `endereco` varchar(150) NOT NULL,
  `cidade` varchar(80) NOT NULL,
  `telefone` varchar(14) NOT NULL,
  `telefoneadicional` varchar(14) DEFAULT NULL,
  `cpf` varchar(14) NOT NULL,
  `rg` varchar(20) NOT NULL,
  `datadenascimento` date DEFAULT NULL,
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (3,'Bruno Muniz','Rua Teixeirinha, 186 - Jardim dos Lagos','Guaíba/RS','(51) 9892-2668','(51) 9757-5929','973.532.640-04','5066278069','1981-10-02',''),(4,'Edson Neumann','Rua dois, 189 - Jardim dos Lagos','Guaiba/RS','(51) 9898-9898','(51) 9797-8877','973.532.640-04','090909090909','1977-11-17','\0');
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comissao`
--

DROP TABLE IF EXISTS `comissao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comissao` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `usuario` int(11) NOT NULL,
  `usuariocomissaoconjunta` int(11) DEFAULT NULL,
  `tipocomissao` int(11) NOT NULL,
  `percentual` float(2,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_comissao_usuario1_idx` (`usuario`),
  KEY `fk_comissao_comissao1_idx` (`usuariocomissaoconjunta`),
  CONSTRAINT `fk_comissao_comissao1` FOREIGN KEY (`usuariocomissaoconjunta`) REFERENCES `comissao` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_comissao_usuario1` FOREIGN KEY (`usuario`) REFERENCES `usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comissao`
--

LOCK TABLES `comissao` WRITE;
/*!40000 ALTER TABLE `comissao` DISABLE KEYS */;
/*!40000 ALTER TABLE `comissao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contacontabil`
--

DROP TABLE IF EXISTS `contacontabil`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contacontabil` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tipo` enum('C','D') NOT NULL,
  `nome` varchar(60) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contacontabil`
--

LOCK TABLES `contacontabil` WRITE;
/*!40000 ALTER TABLE `contacontabil` DISABLE KEYS */;
/*!40000 ALTER TABLE `contacontabil` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `filial`
--

DROP TABLE IF EXISTS `filial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `filial` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `deleted` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `filial`
--

LOCK TABLES `filial` WRITE;
/*!40000 ALTER TABLE `filial` DISABLE KEYS */;
INSERT INTO `filial` VALUES (1,'Matriz','\0'),(2,'Filial 1','\0');
/*!40000 ALTER TABLE `filial` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `formapagamento`
--

DROP TABLE IF EXISTS `formapagamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `formapagamento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  `ehantecipacao` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `formapagamento`
--

LOCK TABLES `formapagamento` WRITE;
/*!40000 ALTER TABLE `formapagamento` DISABLE KEYS */;
INSERT INTO `formapagamento` VALUES (1,'Mastercard','\0','\0');
/*!40000 ALTER TABLE `formapagamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fornecedor`
--

DROP TABLE IF EXISTS `fornecedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fornecedor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(60) NOT NULL,
  `ehrede` bit(1) DEFAULT b'0',
  `cnpj` varchar(18) NOT NULL,
  `deleted` bit(1) DEFAULT b'0',
  `cidade` varchar(80) DEFAULT NULL,
  `telefone` varchar(14) DEFAULT NULL,
  `endereco` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fornecedor`
--

LOCK TABLES `fornecedor` WRITE;
/*!40000 ALTER TABLE `fornecedor` DISABLE KEYS */;
INSERT INTO `fornecedor` VALUES (1,'Kappesberg','','01.010.000/0010-01','\0','Campo Bom/RS','(51) 3540-5988','Rua Projetada Três, 190 - Parque Industrial'),(2,'Carraro','\0','90.909.090/0001-81','','','(51) 4719-0922',''),(3,'Rudnick','\0','99.980.183/0982-03','\0','Caxias do Sul / RS','(55) 3876-0900','Rua Senador Pasqual, 1013 - Vila Alvorada');
/*!40000 ALTER TABLE `fornecedor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `frete`
--

DROP TABLE IF EXISTS `frete`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `frete` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ocproduto` int(11) NOT NULL,
  `valor` float(5,2) NOT NULL,
  `datainicio` date NOT NULL,
  `datafim` date DEFAULT NULL,
  `observacoes` text,
  `freteiro` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_frete_ocproduto1_idx` (`ocproduto`),
  KEY `fk_frete_usuario1_idx` (`freteiro`),
  CONSTRAINT `fk_frete_ocproduto1` FOREIGN KEY (`ocproduto`) REFERENCES `ocproduto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_frete_usuario1` FOREIGN KEY (`freteiro`) REFERENCES `usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `frete`
--

LOCK TABLES `frete` WRITE;
/*!40000 ALTER TABLE `frete` DISABLE KEYS */;
/*!40000 ALTER TABLE `frete` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lancamento`
--

DROP TABLE IF EXISTS `lancamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lancamento` (
  `id` int(11) NOT NULL,
  `contacontabil` int(11) NOT NULL,
  `vale` int(11) DEFAULT NULL,
  `descricao` varchar(80) NOT NULL,
  `valor` float(5,2) NOT NULL,
  `lancamentocol` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_lancamento_contacontabil1_idx` (`contacontabil`),
  KEY `fk_lancamento_vale1_idx` (`vale`),
  CONSTRAINT `fk_lancamento_contacontabil1` FOREIGN KEY (`contacontabil`) REFERENCES `contacontabil` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_lancamento_vale1` FOREIGN KEY (`vale`) REFERENCES `vale` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lancamento`
--

LOCK TABLES `lancamento` WRITE;
/*!40000 ALTER TABLE `lancamento` DISABLE KEYS */;
/*!40000 ALTER TABLE `lancamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `montagem`
--

DROP TABLE IF EXISTS `montagem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `montagem` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ocproduto` int(11) NOT NULL,
  `datainicio` date NOT NULL,
  `datefim` date DEFAULT NULL,
  `montador` int(11) NOT NULL,
  `observações` text,
  `valor` float(5,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_montagem_ocproduto1_idx` (`ocproduto`),
  KEY `fk_montagem_usuario1_idx` (`montador`),
  CONSTRAINT `fk_montagem_ocproduto1` FOREIGN KEY (`ocproduto`) REFERENCES `ocproduto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_montagem_usuario1` FOREIGN KEY (`montador`) REFERENCES `usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `montagem`
--

LOCK TABLES `montagem` WRITE;
/*!40000 ALTER TABLE `montagem` DISABLE KEYS */;
/*!40000 ALTER TABLE `montagem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oc`
--

DROP TABLE IF EXISTS `oc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oc` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cliente` int(11) NOT NULL,
  `vendedor` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `valorfrete` float(5,2) NOT NULL,
  `valormontagem` float(5,2) NOT NULL,
  `prazoentrega` date DEFAULT NULL,
  `observacoes` text,
  `valor` float(6,2) NOT NULL,
  `valorfinanciado` float(6,2) DEFAULT NULL,
  `valorfinal` float(6,2) NOT NULL,
  `valorliquido` float(6,2) NOT NULL,
  `datalancamento` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_oc_cliente_idx` (`cliente`),
  KEY `fk_oc_usuario1_idx` (`vendedor`),
  KEY `fk_oc_status1_idx` (`status`),
  CONSTRAINT `fk_oc_cliente` FOREIGN KEY (`cliente`) REFERENCES `cliente` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_oc_status1` FOREIGN KEY (`status`) REFERENCES `status` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_oc_usuario1` FOREIGN KEY (`vendedor`) REFERENCES `usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oc`
--

LOCK TABLES `oc` WRITE;
/*!40000 ALTER TABLE `oc` DISABLE KEYS */;
/*!40000 ALTER TABLE `oc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ocproduto`
--

DROP TABLE IF EXISTS `ocproduto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ocproduto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `produto` int(11) NOT NULL,
  `oc` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `tiposaida` enum('estoque','showroom','encomenda') DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_ocproduto_oc1_idx` (`oc`),
  KEY `fk_ocproduto_status1_idx` (`status`),
  KEY `fk_ocproduto_produto1` (`produto`),
  CONSTRAINT `fk_ocproduto_oc1` FOREIGN KEY (`oc`) REFERENCES `oc` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_ocproduto_produto1` FOREIGN KEY (`produto`) REFERENCES `produto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_ocproduto_status1` FOREIGN KEY (`status`) REFERENCES `status` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ocproduto`
--

LOCK TABLES `ocproduto` WRITE;
/*!40000 ALTER TABLE `ocproduto` DISABLE KEYS */;
/*!40000 ALTER TABLE `ocproduto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pagamento`
--

DROP TABLE IF EXISTS `pagamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pagamento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `oc` int(11) NOT NULL,
  `formapagamento` int(11) NOT NULL,
  `datalancamento` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_pagamento_oc1_idx` (`oc`),
  KEY `fk_pagamento_formapagamento1_idx` (`formapagamento`),
  CONSTRAINT `fk_pagamento_formapagamento1` FOREIGN KEY (`formapagamento`) REFERENCES `formapagamento` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_pagamento_oc1` FOREIGN KEY (`oc`) REFERENCES `oc` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pagamento`
--

LOCK TABLES `pagamento` WRITE;
/*!40000 ALTER TABLE `pagamento` DISABLE KEYS */;
/*!40000 ALTER TABLE `pagamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parcela`
--

DROP TABLE IF EXISTS `parcela`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parcela` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pagamento` int(11) NOT NULL,
  `dataentrada` date NOT NULL,
  `valor` float(5,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_parcela_pagamento1_idx` (`pagamento`),
  CONSTRAINT `fk_parcela_pagamento1` FOREIGN KEY (`pagamento`) REFERENCES `pagamento` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parcela`
--

LOCK TABLES `parcela` WRITE;
/*!40000 ALTER TABLE `parcela` DISABLE KEYS */;
/*!40000 ALTER TABLE `parcela` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedido`
--

DROP TABLE IF EXISTS `pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pedido` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fornecedor` int(11) NOT NULL,
  `datasolicitacao` date NOT NULL,
  `datachegada` date DEFAULT NULL,
  `observacao` text,
  PRIMARY KEY (`id`),
  KEY `fk_pedido_fornecedor1_idx` (`fornecedor`),
  CONSTRAINT `fk_pedido_fornecedor1` FOREIGN KEY (`fornecedor`) REFERENCES `fornecedor` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedido`
--

LOCK TABLES `pedido` WRITE;
/*!40000 ALTER TABLE `pedido` DISABLE KEYS */;
/*!40000 ALTER TABLE `pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedidoproduto`
--

DROP TABLE IF EXISTS `pedidoproduto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pedidoproduto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `produto` int(11) NOT NULL,
  `pedido` int(11) NOT NULL,
  `quantidade` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_pedidoproduto_produto1_idx` (`produto`),
  KEY `fk_pedidoproduto_pedido1_idx` (`pedido`),
  CONSTRAINT `fk_pedidoproduto_pedido1` FOREIGN KEY (`pedido`) REFERENCES `pedido` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_pedidoproduto_produto1` FOREIGN KEY (`produto`) REFERENCES `produto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedidoproduto`
--

LOCK TABLES `pedidoproduto` WRITE;
/*!40000 ALTER TABLE `pedidoproduto` DISABLE KEYS */;
/*!40000 ALTER TABLE `pedidoproduto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `perfil`
--

DROP TABLE IF EXISTS `perfil`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `perfil` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(45) NOT NULL,
  `deleted` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `perfil`
--

LOCK TABLES `perfil` WRITE;
/*!40000 ALTER TABLE `perfil` DISABLE KEYS */;
INSERT INTO `perfil` VALUES (1,'Administrador','\0'),(2,'Vendedor','\0'),(3,'Financeiro','\0'),(4,'Gerente','\0'),(5,'Montador','\0'),(6,'Administrativo','\0');
/*!40000 ALTER TABLE `perfil` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produto`
--

DROP TABLE IF EXISTS `produto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `produto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(150) NOT NULL,
  `valorsugerido` float(7,2) NOT NULL,
  `fornecedor` int(11) NOT NULL,
  `showroom` int(11) NOT NULL,
  `estoque` int(11) NOT NULL,
  `encomenda` int(11) NOT NULL,
  `codigo` varchar(45) DEFAULT NULL,
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`),
  KEY `fk_produto_fornecedor1_idx` (`fornecedor`),
  CONSTRAINT `fk_produto_fornecedor1` FOREIGN KEY (`fornecedor`) REFERENCES `fornecedor` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produto`
--

LOCK TABLES `produto` WRITE;
/*!40000 ALTER TABLE `produto` DISABLE KEYS */;
INSERT INTO `produto` VALUES (1,'adsasdas',999.00,1,2,2,0,'',''),(2,'Roupeiro 6 portas - branco e preto',999.00,1,1,1,0,'BB-IAEI-9123-Y5','\0'),(3,'DASDADAS',0.00,1,1,1,0,'',''),(4,'dasdsadas',213.00,1,0,0,0,'',''),(5,'teste',1000.00,1,0,0,0,'',''),(6,'ADDASD',10000.00,1,1,0,0,'',''),(7,'teste',10000.00,3,1,0,0,'',''),(8,'laçsdksaçldkç',10000.00,1,1,0,0,'',''),(9,'Roupeiro 4 portas - Tabaco com branco',1360.00,1,1,0,0,'','\0');
/*!40000 ALTER TABLE `produto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `retencao`
--

DROP TABLE IF EXISTS `retencao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `retencao` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `formapagamento` int(11) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `percentual` float(4,2) NOT NULL,
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`),
  KEY `fk_retencao_formapagamento1_idx` (`formapagamento`),
  CONSTRAINT `fk_retencao_formapagamento1` FOREIGN KEY (`formapagamento`) REFERENCES `formapagamento` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `retencao`
--

LOCK TABLES `retencao` WRITE;
/*!40000 ALTER TABLE `retencao` DISABLE KEYS */;
INSERT INTO `retencao` VALUES (1,1,'3 vezes',13.12,'\0'),(2,1,'2 vezes',12.99,'\0'),(3,1,'1 vez',8.51,'\0');
/*!40000 ALTER TABLE `retencao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status`
--

DROP TABLE IF EXISTS `status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `deleted` bit(1) DEFAULT NULL,
  `descricao` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status`
--

LOCK TABLES `status` WRITE;
/*!40000 ALTER TABLE `status` DISABLE KEYS */;
/*!40000 ALTER TABLE `status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `perfil` int(11) NOT NULL,
  `nome` varchar(150) NOT NULL,
  `deleted` bit(1) DEFAULT b'0',
  `login` varchar(45) NOT NULL,
  `senha` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_usuario_perfil1_idx` (`perfil`),
  CONSTRAINT `fk_usuario_perfil1` FOREIGN KEY (`perfil`) REFERENCES `perfil` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,1,'Bruno Wagner Nunes Muniz','\0','bruno','d749a8b4ce024f337310f9270f6b1ef9'),(2,1,'Edson Neumann','\0','edson','698dc19d489c4e4db73e28a713eab07b'),(5,3,'Elmara Baldez','\0','elmara','698dc19d489c4e4db73e28a713eab07b');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuariofilial`
--

DROP TABLE IF EXISTS `usuariofilial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuariofilial` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `usuario` int(11) NOT NULL,
  `filial` int(11) NOT NULL,
  `deleted` bit(1) DEFAULT b'0',
  PRIMARY KEY (`id`),
  KEY `fk_usuariofilial_usuario_idx` (`usuario`),
  KEY `fk_usuariofilial_filial_idx` (`filial`),
  CONSTRAINT `fk_usuariofilial_filial` FOREIGN KEY (`filial`) REFERENCES `filial` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuariofilial_usuario` FOREIGN KEY (`usuario`) REFERENCES `usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuariofilial`
--

LOCK TABLES `usuariofilial` WRITE;
/*!40000 ALTER TABLE `usuariofilial` DISABLE KEYS */;
INSERT INTO `usuariofilial` VALUES (3,1,1,NULL),(4,1,2,NULL),(5,2,1,'\0'),(6,2,2,'\0'),(7,5,1,'\0'),(8,5,2,'\0');
/*!40000 ALTER TABLE `usuariofilial` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vale`
--

DROP TABLE IF EXISTS `vale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vale` (
  `id` int(11) NOT NULL,
  `usuario` int(11) DEFAULT NULL,
  `descricao` varchar(100) NOT NULL,
  `ehparafuncionario` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_vale_usuario1_idx` (`usuario`),
  CONSTRAINT `fk_vale_usuario1` FOREIGN KEY (`usuario`) REFERENCES `usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vale`
--

LOCK TABLES `vale` WRITE;
/*!40000 ALTER TABLE `vale` DISABLE KEYS */;
/*!40000 ALTER TABLE `vale` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'gcb'
--

--
-- Dumping routines for database 'gcb'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-06-24 23:17:50
