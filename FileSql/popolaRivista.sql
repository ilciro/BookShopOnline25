-- -----------------------------------------------------
-- Data for table `ISPW`.`RIVISTA`
-- -----------------------------------------------------
START TRANSACTION;
USE `ISPW`;
INSERT INTO `ISPW`.`RIVISTA` (`titolo`, `categoria`, `autore`, `lingua`, `editore`, `Descrizione`, `dataPubblicazione`, `disp`, `prezzo`, `copieRimanenti`, `idRivista`) VALUES ('Focus', 'MENSILE', 'Focus', 'italiano', 'Bao Publishing', 'Ogni tanto qualcosa e\' simpatica ufo a parte e l articolo di come combattere uno squalo e\' meraviglioso', '2021-01-13', 1, 5.0, 123, 1);
INSERT INTO `ISPW`.`RIVISTA` (`titolo`, `categoria`, `autore`, `lingua`, `editore`, `Descrizione`, `dataPubblicazione`, `disp`, `prezzo`, `copieRimanenti`, `idRivista`) VALUES ('Focus Junior', 'MENSILE', 'Focus', 'italiano', 'Bao Publishing', 'arino magari qualche bambino sviluppa interesse per il complottismo', '2021-01-21', 1, 2.0, 1234, 2);
INSERT INTO `ISPW`.`RIVISTA` (`titolo`, `categoria`, `autore`, `lingua`, `editore`, `Descrizione`, `dataPubblicazione`, `disp`, `prezzo`, `copieRimanenti`, `idRivista`) VALUES ('Tv Sorrisi e canzoni', 'SETTIMANALE', 'Panorama', 'italiano', 'Bao Publishing', 'ok', '2021-02-02', 0, 4.0, 50, 3);
INSERT INTO `ISPW`.`RIVISTA` (`titolo`, `categoria`, `autore`, `lingua`, `editore`, `Descrizione`, `dataPubblicazione`, `disp`, `prezzo`, `copieRimanenti`, `idRivista`) VALUES ('Rivista A', 'SPORTIVO', 'Bao Publishing', 'italiano', 'Bao Publishing', 'okok', '1970-01-01', 1, 2.0, 121, 4);
INSERT INTO `ISPW`.`RIVISTA` (`titolo`, `categoria`, `autore`, `lingua`, `editore`, `Descrizione`, `dataPubblicazione`, `disp`, `prezzo`, `copieRimanenti`, `idRivista`) VALUES ('Rivista B', 'SPORTIVO', 'Bao Publishing', 'italiano', 'Bao Publishing', 'testo casuale', '1970-01-01', 1, 2.0, 131, 5);

COMMIT;



