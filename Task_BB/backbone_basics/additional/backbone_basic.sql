-- phpMyAdmin SQL Dump
-- version 3.5.1
-- http://www.phpmyadmin.net
--
-- Хост: localhost
-- Час створення: Чрв 20 2014 р., 09:11
-- Версія сервера: 5.5.24-log
-- Версія PHP: 5.4.3

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- БД: `backbone_basics`
--
CREATE DATABASE `backbone_basics` DEFAULT CHARSET=utf8;
USE `backbone_basics`;
-- --------------------------------------------------------

--
-- Структура таблиці `bookmark`
--

DROP TABLE IF EXISTS `bookmark`;
CREATE TABLE IF NOT EXISTS `bookmark` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(250) NOT NULL,
  `title` varchar(250) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Дамп даних таблиці `bookmark`
--

INSERT INTO `bookmark` (`id`, `url`, `title`) VALUES(1, 'http://jquery.com', 'jQuery');
INSERT INTO `bookmark` (`id`, `url`, `title`) VALUES(2, 'http://underscorejs.org', 'UnderscoreJS');
INSERT INTO `bookmark` (`id`, `url`, `title`) VALUES(3, 'http://backbonejs.org', 'BackboneJS');

-- --------------------------------------------------------

--
-- Структура таблиці `tag`
--

DROP TABLE IF EXISTS `tag`;
CREATE TABLE IF NOT EXISTS `tag` (
  `id_bookmark` bigint(20) NOT NULL,
  `tag` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп даних таблиці `tag`
--

INSERT INTO `tag` (`id_bookmark`, `tag`) VALUES(1, 'Library');
INSERT INTO `tag` (`id_bookmark`, `tag`) VALUES(1, 'JavaScript');
INSERT INTO `tag` (`id_bookmark`, `tag`) VALUES(2, 'Library');
INSERT INTO `tag` (`id_bookmark`, `tag`) VALUES(2, 'JavaScript');
INSERT INTO `tag` (`id_bookmark`, `tag`) VALUES(3, 'Framework');
INSERT INTO `tag` (`id_bookmark`, `tag`) VALUES(3, 'JavaScript');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
