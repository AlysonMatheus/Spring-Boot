





-- Copiando estrutura para tabela rest_with_spring_boot_and_java.person
CREATE TABLE IF NOT EXISTS `person` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `first_name` varchar(80) NOT NULL,
    `last_name` varchar(80) NOT NULL,
    `address` varchar(100) NOT NULL,
    `gender` varchar(6) NOT NULL,

    PRIMARY KEY (`id`)
    );


