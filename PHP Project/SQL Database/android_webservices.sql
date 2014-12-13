-- phpMyAdmin SQL Dump
-- version 4.0.4
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Dec 13, 2014 at 06:01 PM
-- Server version: 5.6.12-log
-- PHP Version: 5.4.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `android_webservices`
--
CREATE DATABASE IF NOT EXISTS `android_webservices` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `android_webservices`;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_username` varchar(255) NOT NULL,
  `user_password` varchar(255) NOT NULL,
  `user_displayname` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `user_username`, `user_password`, `user_displayname`) VALUES
(1, 'abbas', 'ba85b91bf4e0f6b5089217c12d88fea3', 'abbas'),
(2, 'ahnadssb', '47bce5c74f589f4867dbd57e9ca9f808', 'Ahmed SSB'),
(3, 'ahmed', '9193ce3b31332b03f7d8af056c692b84', 'ahmed saleh '),
(4, 'aaa', '74b87337454200d4d33f80c4663dc5e5', 'aaa'),
(5, 'aa', '4124bc0a9335c27f086f24ba207a4912', 'ahmedssb2'),
(6, 'abas', '8462a1a67fbed2bef22d490d88e35944', 'abas bin firnas'),
(7, 'r', '4b43b0aee35624cd95b910189b3dc231', 'r');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
