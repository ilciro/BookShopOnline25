package laptop.controller.primoucacquista;


import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.ObservableList;
import laptop.database.negozio.CsvNegozio;
import laptop.database.negozio.MemoriaNegozio;
import laptop.database.negozio.NegozioDao;
import laptop.database.negozio.PersistenzaNegozio;
import laptop.exception.IdException;
import laptop.model.Negozio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerScegliNegozio {
	private PersistenzaNegozio pN;
	private static final String DATABASE="database";
	private static final String FILE="file";
	private static final String MEMORIA="memoria";



	

	
	public ObservableList<Negozio> getNegozi(String type) throws IOException, CsvValidationException, IdException, ClassNotFoundException {
		switch (type) {
			case DATABASE -> pN = new NegozioDao();
			case FILE -> pN = new CsvNegozio();
			case MEMORIA -> pN = new MemoriaNegozio();
			default -> Logger.getLogger("get negozi").log(Level.SEVERE,"  empty list!!");
		}
		if(!Files.exists(Path.of("memory/serializzazioneNegozio.ser")))
		 	pN.initializza();
		return pN.getNegozi();
	}

	public boolean isOpen(String type,int id) throws IOException, CsvValidationException, IdException, ClassNotFoundException {

		switch (type) {
			case DATABASE -> pN = new NegozioDao();
			case FILE -> pN = new CsvNegozio();
			case MEMORIA -> pN = new MemoriaNegozio();
			default -> Logger.getLogger("is open").log(Level.SEVERE,"  is open il null!!");

		}
		Negozio n=pN.getNegozi().get(id-1);
		return pN.checkRitiro(n);
	}
	public boolean isValid(String type,int id) throws IOException, CsvValidationException, IdException, ClassNotFoundException {

		switch (type) {
			case DATABASE -> pN = new NegozioDao();
			case FILE -> pN = new CsvNegozio();
			case MEMORIA -> pN = new MemoriaNegozio();
			default -> Logger.getLogger("is valid").log(Level.SEVERE,"  is open il null!!");

		}
		Negozio n=pN.getNegozi().get(id-1);
		return pN.checkOpen(n);
	}
	

}