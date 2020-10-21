CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_unique` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `theatres` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `screens` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `seat_layout` json DEFAULT NULL,
  `theatre_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKomykyk08ts7cl7rwxp5qlaear` (`theatre_id`),
  CONSTRAINT `FKomykyk08ts7cl7rwxp5qlaear` FOREIGN KEY (`theatre_id`) REFERENCES `theatres` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `booked_tickets` (
  `seat_number` varchar(255) NOT NULL,
  `show_date` datetime(6) NOT NULL,
  `show_id` bigint(20) NOT NULL,
  PRIMARY KEY (`seat_number`,`show_date`,`show_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `movie_shows` (
  `id` bigint(20) NOT NULL,
  `end_time` time DEFAULT NULL,
  `movie_name` varchar(255) DEFAULT NULL,
  `show_prices_json` json DEFAULT NULL,
  `start_time` time DEFAULT NULL,
  `screen_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKib1wqmodic9ladakypon1rkx6` (`screen_id`),
  CONSTRAINT `FKib1wqmodic9ladakypon1rkx6` FOREIGN KEY (`screen_id`) REFERENCES `screens` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `open_shows` (
  `id` bigint(20) NOT NULL,
  `show_date` date DEFAULT NULL,
  `show_seat_details` json DEFAULT NULL,
  `movie_show_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `duplicate_show_constraint` (`movie_show_id`,`show_date`),
  CONSTRAINT `FKi8hwh3f4ms0p6p2nso3ormlyv` FOREIGN KEY (`movie_show_id`) REFERENCES `movie_shows` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tickets` (
  `id` bigint(20) NOT NULL,
  `amount` float DEFAULT NULL,
  `seat_details` json DEFAULT NULL,
  `ticket_status` int(11) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `show_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7g8ns0vsv4kwux9u00t6s4578` (`show_id`),
  CONSTRAINT `FK7g8ns0vsv4kwux9u00t6s4578` FOREIGN KEY (`show_id`) REFERENCES `open_shows` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO hibernate_sequence (next_val) VALUES (1);

INSERT INTO users values (0, "admin", "admin", 1);