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


-- Dumping structure for table mfurtado_timetracker.assignment
CREATE TABLE IF NOT EXISTS `assignment` (
  `ass_id` varchar(50) DEFAULT NULL,
  `ass_name` varchar(255) DEFAULT NULL,
  `proj_id` varchar(50) DEFAULT NULL,
  `user_id` varchar(50) DEFAULT NULL,
  UNIQUE KEY `assignment_id` (`ass_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table mfurtado_timetracker.assignment: ~5 rows (approximately)
/*!40000 ALTER TABLE `assignment` DISABLE KEYS */;
REPLACE INTO `assignment` (`ass_id`, `ass_name`, `proj_id`, `user_id`) VALUES
	('BGmHtdtIHf', 'test', 'BohuRAIX98', '79185817'),
	('LIM2OgODAo', 'testtwo', 'BohuRAIX98', '79185817'),
	('dEM68qrXP2', 'Fuck Yeah!', 'BohuRAIX98', '79185817'),
	('cqnCoNQIxK', 'test three', 'BohuRAIX98', '79185817'),
	('fNlhsDKlm8', 'testasdfasdf', 'BohuRAIX98', '79185817');
/*!40000 ALTER TABLE `assignment` ENABLE KEYS */;


-- Dumping structure for table mfurtado_timetracker.assignment_time
CREATE TABLE IF NOT EXISTS `assignment_time` (
  `assignment_id` varchar(50) DEFAULT NULL,
  `project_id` varchar(50) DEFAULT NULL,
  `entry_date` date DEFAULT NULL,
  `days_time` int(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table mfurtado_timetracker.assignment_time: ~0 rows (approximately)
/*!40000 ALTER TABLE `assignment_time` DISABLE KEYS */;
/*!40000 ALTER TABLE `assignment_time` ENABLE KEYS */;


-- Dumping structure for table mfurtado_timetracker.client
CREATE TABLE IF NOT EXISTS `client` (
  `client_id` varchar(10) DEFAULT NULL,
  `client_name` varchar(255) DEFAULT NULL,
  `user_id` int(9) DEFAULT NULL,
  UNIQUE KEY `client_id` (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table mfurtado_timetracker.client: ~5 rows (approximately)
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
REPLACE INTO `client` (`client_id`, `client_name`, `user_id`) VALUES
	('Bblf0HyApJ', 'First Client', 79185817),
	('vM0f2klfFz', 'First Client Two', 79185817),
	('ro3U1sSQfG', 'asdfasdf', 79185817),
	('A0e8IiyyVy', 'Test Client', 79185817),
	('Pnlzijrlhp', 'New Client', 79185817);
/*!40000 ALTER TABLE `client` ENABLE KEYS */;


-- Dumping structure for table mfurtado_timetracker.project
CREATE TABLE IF NOT EXISTS `project` (
  `project_id` varchar(50) DEFAULT NULL,
  `proj_name` varchar(255) DEFAULT NULL,
  `client_id` varchar(50) DEFAULT NULL,
  UNIQUE KEY `project_id` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table mfurtado_timetracker.project: ~11 rows (approximately)
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
REPLACE INTO `project` (`project_id`, `proj_name`, `client_id`) VALUES
	('r2af29jeUQ', 'First Proj', 'Bblf0HyApJ'),
	('XlOBOoAMn4', 'Hopefully?', 'A0e8IiyyVy'),
	('qBIolqbJkx', 'Round Three', 'A0e8IiyyVy'),
	('7Jm0CAOH80', 'Round Four', 'A0e8IiyyVy'),
	('2TAKEm9o4t', 'Round Five', 'Bblf0HyApJ'),
	('FgWVk0DPrJ', 'asdfasdf', 'Bblf0HyApJ'),
	('V98qtc09HK', 'Test One', 'Pnlzijrlhp'),
	('46a7kqB8L5', 'asdffdsasdf', 'Pnlzijrlhp'),
	('GQKH1cmvrQ', 'hbghsfgbsg', 'Bblf0HyApJ'),
	('NC7mldhePh', '9iouhjnn', 'Pnlzijrlhp'),
	('BohuRAIX98', 'Fuck Yeah!', 'Pnlzijrlhp');
/*!40000 ALTER TABLE `project` ENABLE KEYS */;


-- Dumping structure for table mfurtado_timetracker.reg_users
CREATE TABLE IF NOT EXISTS `reg_users` (
  `regnum` varchar(255) DEFAULT NULL,
  `login` varchar(255) DEFAULT NULL,
  UNIQUE KEY `regnum` (`regnum`),
  UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table mfurtado_timetracker.reg_users: ~2 rows (approximately)
/*!40000 ALTER TABLE `reg_users` DISABLE KEYS */;
REPLACE INTO `reg_users` (`regnum`, `login`) VALUES
	('303f9e95742a0e01ce1cecda2833750cc14a5de2', 'test'),
	('b8e1fdeae889f1db2053076652f95425e388ae0e', 'aerothosis');
/*!40000 ALTER TABLE `reg_users` ENABLE KEYS */;


-- Dumping structure for table mfurtado_timetracker.user_login
CREATE TABLE IF NOT EXISTS `user_login` (
  `user_id` int(9) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `pass` varchar(255) DEFAULT NULL,
  `name_first` varchar(255) DEFAULT NULL,
  `name_last` varchar(255) DEFAULT NULL,
  `account_status` int(3) DEFAULT '0',
  `version` int(3) DEFAULT '0',
  `num_of_a` int(3) DEFAULT '0',
  `regnum` varchar(255) DEFAULT NULL,
  UNIQUE KEY `user_id` (`user_id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `regnum` (`regnum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table mfurtado_timetracker.user_login: ~2 rows (approximately)
/*!40000 ALTER TABLE `user_login` DISABLE KEYS */;
REPLACE INTO `user_login` (`user_id`, `username`, `pass`, `name_first`, `name_last`, `account_status`, `version`, `num_of_a`, `regnum`) VALUES
	(79185817, 'test', '62d28c6874dd675a58ccfc0e3618dec13ccd24ec', 'michael', 'furtado', 1, 2, 0, '303f9e95742a0e01ce1cecda2833750cc14a5de2'),
	(91821703, 'aerothosis', '62d28c6874dd675a58ccfc0e3618dec13ccd24ec', 'Michael', 'Furtado', 5, 1, 0, 'b8e1fdeae889f1db2053076652f95425e388ae0e');
/*!40000 ALTER TABLE `user_login` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
