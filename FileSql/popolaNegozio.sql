-- -----------------------------------------------------
-- Data for table `ISPW`.`NEGOZIO`
-- -----------------------------------------------------
START TRANSACTION;
USE `ISPW`;
INSERT INTO `ISPW`.`NEGOZIO` (`idNegozio`, `nome`, `via`, `isValid`, `isOpen`) VALUES (1, 'Negozio A', 'via A', true, false);
INSERT INTO `ISPW`.`NEGOZIO` (`idNegozio`, `nome`, `via`, `isValid`, `isOpen`) VALUES (2, 'Negozio B', 'via B', true, true);
INSERT INTO `ISPW`.`NEGOZIO` (`idNegozio`, `nome`, `via`, `isValid`, `isOpen`) VALUES (3, 'Negozio C', 'via C', false, true);
INSERT INTO `ISPW`.`NEGOZIO` (`idNegozio`, `nome`, `via`, `isValid`, `isOpen`) VALUES (4, 'Negozio D', 'via D', false, false);

COMMIT;