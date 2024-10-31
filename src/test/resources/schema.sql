DROP TABLE IF EXISTS "address" CASCADE;
DROP TABLE IF EXISTS "user" CASCADE;

CREATE TABLE "address" (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `city` varchar(255) DEFAULT NULL,
                           `street` varchar(255) DEFAULT NULL,
                           PRIMARY KEY (`id`)
);



CREATE TABLE "user" (
                               `age` int NOT NULL,
                               `address_id` bigint DEFAULT NULL,
                               `id` bigint NOT NULL AUTO_INCREMENT,
                               `email` varchar(255) DEFAULT NULL,
                               `first_name` varchar(255) DEFAULT NULL,
                               `last_name` varchar(255) DEFAULT NULL,
                               `password` varchar(255) DEFAULT NULL,
                               PRIMARY KEY (`id`))
