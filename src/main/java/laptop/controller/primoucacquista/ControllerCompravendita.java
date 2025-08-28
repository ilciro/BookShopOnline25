package laptop.controller.primoucacquista;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import laptop.database.primoucacquista.giornale.CsvGiornale;
import laptop.database.primoucacquista.giornale.GiornaleDao;
import laptop.database.primoucacquista.giornale.MemoriaGiornale;
import laptop.database.primoucacquista.giornale.PersistenzaGiornale;
import laptop.database.primoucacquista.libro.MemoriaLibro;
import laptop.database.primoucacquista.rivista.CsvRivista;
import laptop.database.primoucacquista.rivista.MemoriaRivista;
import laptop.database.primoucacquista.rivista.PersistenzaRivista;
import laptop.database.primoucacquista.rivista.RivistaDao;
import laptop.exception.IdException;

import laptop.model.raccolta.Raccolta;

import laptop.database.primoucacquista.libro.CsvLibro;
import laptop.database.primoucacquista.libro.LibroDao;
import laptop.database.primoucacquista.libro.PersistenzaLibro;


public class ControllerCompravendita {

	private static final String DATABASE = "database";
	private static final String FILE = "file";
	private static final String MEMORIA = "memoria";
	private PersistenzaLibro pL;
	private PersistenzaGiornale pG;
	private PersistenzaRivista pR;

	public ObservableList<Raccolta> getLista(String type, String database)  {
		ObservableList<Raccolta> catalogo = FXCollections.observableArrayList();
		switch (type) {
			case "libro" -> {
				switch (database) {
					case DATABASE -> pL = new LibroDao();
					case FILE -> pL = new CsvLibro();
					case MEMORIA -> pL = new MemoriaLibro();
					default -> Logger.getLogger("lista libro").log(Level.SEVERE, "book persistency not correct !!");
				}
				pL.initializza();
				catalogo.addAll(pL.retrieveRaccoltaData());
			}
			case "giornale" -> {
				switch (database) {
					case DATABASE -> pG = new GiornaleDao();
					case FILE -> pG = new CsvGiornale();
					 case MEMORIA -> pG = new MemoriaGiornale();
					default -> Logger.getLogger("lista giornale").log(Level.SEVERE, "daily persistency not correct !!");
				}
				pG.initializza();
				catalogo.addAll(pG.retrieveRaccoltaData());
			}
			case "rivista" -> {
				switch (database) {
					case DATABASE -> pR = new RivistaDao();
					case FILE -> pR = new CsvRivista();
					case MEMORIA -> pR = new MemoriaRivista();
					default -> Logger.getLogger("lista rivista").log(Level.SEVERE, "magazine persistency not correct !!");
				}
				pR.initializza();
				catalogo.addAll(pR.retrieveRaccoltaData());
			}
			default -> Logger.getLogger("get lista").log(Level.SEVERE, " list is empty");
		}
		return catalogo;
	}

	public boolean checkId(int id,String type,String typeObj)  {
		 boolean status=true;
		 try {
			if (id <= 0 || id > getLista(typeObj, type).size())
				throw new IdException("id is wrong!! grater than size");
		}catch (IdException  e)
		{
			Logger.getLogger("checkId").log(Level.SEVERE,e.getMessage());
			status=false;
		}
		 return status;
	}
}





