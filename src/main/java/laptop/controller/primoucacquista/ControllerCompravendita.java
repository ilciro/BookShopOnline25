package laptop.controller.primoucacquista;

import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import laptop.database.giornale.MemoriaGiornale;
import laptop.database.libro.MemoriaLibro;
import laptop.database.rivista.MemoriaRivista;
import laptop.exception.IdException;

import laptop.model.raccolta.Raccolta;
import laptop.database.giornale.CsvGiornale;
import laptop.database.giornale.GiornaleDao;
import laptop.database.giornale.PersistenzaGiornale;
import laptop.database.libro.CsvLibro;
import laptop.database.libro.LibroDao;
import laptop.database.libro.PersistenzaLibro;
import laptop.database.rivista.CsvRivista;
import laptop.database.rivista.PersistenzaRivista;
import laptop.database.rivista.RivistaDao;


public class ControllerCompravendita {

	private static final String DATABASE="database";
	private static final String FILE="file";
	private static final String MEMORIA="memoria";
	private PersistenzaLibro pL;
	private PersistenzaGiornale pG;
	private PersistenzaRivista pR;

	public ObservableList<Raccolta> getLista(String type,String database) throws IOException, CsvValidationException, IdException, ClassNotFoundException {

	 ObservableList <Raccolta> catalogo=FXCollections.observableArrayList();

	 switch (type)
	 {
		 case "libro" -> {

             switch (database) {
                 case DATABASE -> pL = new LibroDao();
                 case FILE -> pL = new CsvLibro();
                 case MEMORIA -> pL = new MemoriaLibro();
				 default -> Logger.getLogger("lista libro").log(Level.SEVERE,"book persistency not correct !!");
             }

			catalogo.addAll(pL.retrieveRaccoltaData());



		 }

		 case "giornale"->{
			 switch (database) {
				 case DATABASE -> pG = new GiornaleDao();
				 case FILE -> pG = new CsvGiornale();
				 case MEMORIA -> pG = new MemoriaGiornale();
				 default -> Logger.getLogger("lista giornale").log(Level.SEVERE,"daily persistency not correct !!");
			 }


			 catalogo.addAll(pG.retrieveRaccoltaData());

		 }

		 case "rivista"->{
			 switch (database) {
				 case DATABASE -> pR = new RivistaDao();
				 case FILE -> pR = new CsvRivista();
				 case MEMORIA -> pR = new MemoriaRivista();
				 default -> Logger.getLogger("lista rivista").log(Level.SEVERE,"magazine persistency not correct !!");
			 }

			 catalogo.addAll(pR.retrieveRaccoltaData());

		 }



		 default-> Logger.getLogger("get lista").log(Level.SEVERE, " list is empty");

	 }



	return catalogo;
	}

	
}
