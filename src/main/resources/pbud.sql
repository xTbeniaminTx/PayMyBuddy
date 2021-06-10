-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : lun. 07 juin 2021 à 13:29
-- Version du serveur :  10.4.11-MariaDB
-- Version de PHP : 7.4.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `pbud`
--

-- --------------------------------------------------------

--
-- Structure de la table `bank_account`
--

CREATE TABLE `bank_account` (
  `id_bank_account` int(11) NOT NULL,
  `balance` double NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `bank_account`
--

INSERT INTO `bank_account` (`id_bank_account`, `balance`) VALUES
(1, 1899.5),
(2, 95);

-- --------------------------------------------------------

--
-- Structure de la table `contacts`
--

CREATE TABLE `contacts` (
  `first_user` int(11) NOT NULL,
  `second_user` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `contacts`
--

INSERT INTO `contacts` (`first_user`, `second_user`) VALUES
(2, 1),
(1, 2);

-- --------------------------------------------------------

--
-- Structure de la table `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(5);

-- --------------------------------------------------------

--
-- Structure de la table `transactions`
--

CREATE TABLE `transactions` (
  `id_transaction` int(11) NOT NULL,
  `amount` double NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `transmitter_id` int(11) NOT NULL,
  `receiver_id` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `transactions`
--

INSERT INTO `transactions` (`id_transaction`, `amount`, `description`, `transmitter_id`, `receiver_id`) VALUES
(3, 100, 'test', 1, 2),
(4, 1000, 'test1', 2, 1);

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `bank_account_id_bank_account` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`id`, `email`, `status`, `password`, `role`, `user_name`, `bank_account_id_bank_account`) VALUES
(1, 'toto@gmail.com', b'1', '$2a$10$anffGA/sDNCScqGYFHwPoefBG9iOUbhiM2OyQT4H2XhWvcs5cdC8m', 'USER', 'toto', 1),
(2, 'momo@gmail.com', b'1', '$2a$10$Dq6DmHIabincByPT1Rh/iOV2YTE37N.iwFWo5srI1oXGq8A4hUvBO', 'USER', 'momo', 2);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `bank_account`
--
ALTER TABLE `bank_account`
  ADD PRIMARY KEY (`id_bank_account`);

--
-- Index pour la table `contacts`
--
ALTER TABLE `contacts`
  ADD PRIMARY KEY (`second_user`,`first_user`),
  ADD KEY `FK8vmowvx70c99a874fyntarb9g` (`first_user`);

--
-- Index pour la table `transactions`
--
ALTER TABLE `transactions`
  ADD PRIMARY KEY (`id_transaction`),
  ADD KEY `FKwoqtf7j9cvwo1f2hm7y38994` (`transmitter_id`),
  ADD KEY `FK5nn8ird7idyxyxki68gox2wbx` (`receiver_id`);

--
-- Index pour la table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKmhb8hmmkv0vk1d3cl4x70y0bm` (`bank_account_id_bank_account`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
