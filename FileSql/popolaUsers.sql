-- -----------------------------------------------------
-- Data for table `ISPW`.`USERS`
-- -----------------------------------------------------
START TRANSACTION;
USE `ISPW`;
INSERT INTO `ISPW`.`USERS` (`idUser`, `idRuolo`, `nome`, `cognome`, `email`, `pwd`, `descrizione`, `dataNascita`) VALUES (0, 'A', 'admin', 'admin', 'admin@admin.com', 'Admin871', 'utente admin', '1980-10-26');
INSERT INTO `ISPW`.`USERS` (`idUser`, `idRuolo`, `nome`, `cognome`, `email`, `pwd`, `descrizione`, `dataNascita`) VALUES (0, 'A', 'Gianni', 'Morandi', 'giannni@gmail.com', 'BigHand21', 'utente con privilegi massimi', '1940-10-18');
INSERT INTO `ISPW`.`USERS` (`idUser`, `idRuolo`, `nome`, `cognome`, `email`, `pwd`, `descrizione`, `dataNascita`) VALUES (0, 'U', 'Gino', 'Zaia', 'gino@zaia.eu', '25041995Gz', 'utente semplice', '1995-04-25');
INSERT INTO `ISPW`.`USERS` (`idUser`, `idRuolo`, `nome`, `cognome`, `email`, `pwd`, `descrizione`, `dataNascita`) VALUES (0, 'U', 'Giulia', 'Conforto', 'giuliaConforto@gmail.eu', '12345678Gc', 'utente semplice', '1998-09-05');
INSERT INTO `ISPW`.`USERS` (`idUser`, `idRuolo`, `nome`, `cognome`, `email`, `pwd`, `descrizione`, `dataNascita`) VALUES (0,'W','zerocalcare','zerocalcare','zerocalcare@gmail.com','Zerocalcare21','scrittore semplice','1980-05-20');
INSERT INTO `ISPW`.`USERS` (`idUser`, `idRuolo`, `nome`, `cognome`, `email`, `pwd`, `descrizione`, `dataNascita`) VALUES (0, 'E', 'Bao Publishing', 'Bao Publishing', 'baoPublishing@gmail.com', 'BaoPub2021', 'editore semplice', '1960-11-21');
INSERT INTO `ISPW`.`USERS` (`idUser`, `idRuolo`, `nome`, `cognome`, `email`, `pwd`, `descrizione`, `dataNascita`) VALUES (0, 'E', 'Editore Prova', 'Editore Prova', 'editore185@gmail.com', 'EdiP415', 'editore semplice', '1975-04-28');

COMMIT;


