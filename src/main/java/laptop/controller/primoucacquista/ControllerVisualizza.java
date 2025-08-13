package laptop.controller.primoucacquista;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import laptop.controller.ControllerSystemState;
import laptop.database.primoucacquista.giornale.CsvGiornale;
import laptop.database.primoucacquista.giornale.GiornaleDao;
import laptop.database.primoucacquista.giornale.MemoriaGiornale;
import laptop.database.primoucacquista.giornale.PersistenzaGiornale;
import laptop.database.primoucacquista.libro.MemoriaLibro;
import laptop.database.primoucacquista.rivista.CsvRivista;
import laptop.database.primoucacquista.rivista.MemoriaRivista;
import laptop.database.primoucacquista.rivista.PersistenzaRivista;
import laptop.database.primoucacquista.rivista.RivistaDao;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;
import laptop.database.primoucacquista.libro.CsvLibro;
import laptop.database.primoucacquista.libro.LibroDao;
import laptop.database.primoucacquista.libro.PersistenzaLibro;


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

	public ControllerVisualizza()  {
		l = new Libro();
		g = new Giornale();
		r = new Rivista();

	}



	public int getID()
	{
		int id=0;
		switch (vis.getType())
		{
			case "libro"-> id=vis.getIdLibro();
			case "giornale"->id=vis.getIdGiornale();
			case "rivista"->id=vis.getIdRivista();
			default -> Logger.getLogger("id").log(Level.SEVERE,"get id error!!");

		}

		return id;
	}

	public ObservableList<Libro> getListLibro(String type)  {

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



	public ObservableList<Giornale> getListGiornale(String type)  {

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

	public ObservableList<Rivista> getListRivista(String type) {
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

