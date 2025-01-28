package laptop.controller.primoucacquista;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.ObservableList;
import laptop.controller.ControllerSystemState;
import laptop.database.giornale.GiornaleDao;
import laptop.database.giornale.MemoriaGiornale;
import laptop.database.libro.MemoriaLibro;
import laptop.database.rivista.MemoriaRivista;
import laptop.exception.IdException;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;
import laptop.database.giornale.CsvGiornale;
import laptop.database.giornale.PersistenzaGiornale;
import laptop.database.libro.CsvLibro;
import laptop.database.libro.LibroDao;
import laptop.database.libro.PersistenzaLibro;
import laptop.database.rivista.CsvRivista;
import laptop.database.rivista.PersistenzaRivista;
import laptop.database.rivista.RivistaDao;


public class ControllerVisualizza {

	private final Libro l;
	private final Giornale g;
	private final Rivista r;
	private static final String DATABASE="database";
	private static final String FILE="file";
	private static final String MEMORIA="memoria";
	private PersistenzaLibro pL ;
	private PersistenzaGiornale pG;
	private PersistenzaRivista pR;


	private final ControllerSystemState vis = ControllerSystemState.getInstance();

	public ControllerVisualizza() throws IOException {
		l = new Libro();
		g = new Giornale();
		r = new Rivista();

	}



	public int getID()
	{
		Logger.getLogger("Test getId").log(Level.INFO, "id {0}",vis.getId());

		return vis.getId();
	}

	public ObservableList<Libro> getListLibro(String type) throws CsvValidationException, IOException, IdException, ClassNotFoundException {

		ObservableList<Libro> list;
		l.setId(getID());

        switch (type) {
            case DATABASE -> pL = new LibroDao();
            case FILE -> pL = new CsvLibro();
            case MEMORIA -> pL = new MemoriaLibro();
			default -> Logger.getLogger("list libro").log(Level.SEVERE,"book persistency is wrong!!");
        }
		list=pL.getLibroByIdTitoloAutoreLibro(l);
		return list;
	}

	public ObservableList<Giornale> getListGiornale(String type) throws CsvValidationException, IOException, IdException, ClassNotFoundException {

		ObservableList<Giornale> list;
		g.setId(getID());
        switch (type) {
            case DATABASE -> pG = new GiornaleDao();
            case FILE -> pG = new CsvGiornale();
            case MEMORIA -> pG = new MemoriaGiornale();
			default -> Logger.getLogger("list giornale").log(Level.SEVERE,"daily persistency is wrong!!");

		}
		list= pG.getGiornaleByIdTitoloAutoreLibro(g);
		return list;

	}

	public ObservableList<Rivista> getListRivista(String type) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
		ObservableList<Rivista> list;
		r.setId(getID());
        switch (type) {
            case DATABASE -> pR = new RivistaDao();
            case FILE -> pR = new CsvRivista();
            case MEMORIA -> pR = new MemoriaRivista();
			default -> Logger.getLogger("list rivista").log(Level.SEVERE,"magazine persisyency is wrong!!");

		}
		list=pR.getRivistaByIdTitoloAutoreRivista(r);
		return list;

	}



}

