-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.6.24-log - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             9.1.0.4867
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping database structure for mfurtado_timetracker
CREATE DATABASE IF NOT EXISTS `mfurtado_timetracker` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `mfurtado_timetracker`;


-- Dumping structure for table mfurtado_timetracker.reg_users
CREATE TABLE IF NOT EXISTS `reg_users` (
  `regnum` varchar(255) DEFAULT NULL,
  `login` varchar(255) DEFAULT NULL,
  UNIQUE KEY `regnum` (`regnum`),
  UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table mfurtado_timetracker.reg_users: ~1 rows (approximately)
/*!40000 ALTER TABLE `reg_users` DISABLE KEYS */;
REPLACE INTO `reg_users` (`regnum`, `login`) VALUES
	('123456789e', 'aerothosis'),
	('9597fd526a005b1ec85e98da4ed03c47b2bcaaa7', 'test'),
	('83bb6d4a8a43d918b9af8f897d1a927683173085', 'testtwo'),
	('6fe213d45ec3e8e9a560a1dc6dc5c71ce3fa5f16', 'testtwoTwo');
/*!40000 ALTER TABLE `reg_users` ENABLE KEYS */;


-- Dumping structure for table mfurtado_timetracker.user_login
CREATE TABLE IF NOT EXISTS `user_login` (
  `user_id` int(6) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `pass` varchar(255) DEFAULT NULL,
  `name_first` varchar(255) DEFAULT NULL,
  `name_last` varchar(255) DEFAULT NULL,
  `account_status` int(3) DEFAULT '0',
  `regnum` varchar(255) DEFAULT NULL,
  UNIQUE KEY `user_id` (`user_id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `regnum` (`regnum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table mfurtado_timetracker.user_login: ~1 rows (approximately)
/*!40000 ALTER TABLE `user_login` DISABLE KEYS */;
REPLACE INTO `user_login` (`user_id`, `username`, `pass`, `name_first`, `name_last`, `account_status`, `regnum`) VALUES
	(123456, 'aerothosis', 'halorvb1', 'Michael', 'Furtado', 5, '123456789e'),
	(NULL, 'test', 'halorvb1', 'Test', 'McGee', 1, '9597fd526a005b1ec85e98da4ed03c47b2bcaaa7'),
	(650136, 'testtwo', 'halorvb1', 'Test', 'McGee', 1, '83bb6d4a8a43d918b9af8f897d1a927683173085'),
	(793132, 'testtwoTwo', 'halorvb1', 'Test', 'McGee', 3, '6fe213d45ec3e8e9a560a1dc6dc5c71ce3fa5f16');
/*!40000 ALTER TABLE `user_login` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
