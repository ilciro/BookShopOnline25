package laptop.controller.primoucacquista;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerSystemState;

import laptop.database.giornale.MemoriaGiornale;
import laptop.database.libro.MemoriaLibro;
import laptop.database.rivista.MemoriaRivista;
import laptop.database.rivista.RivistaDao;
import laptop.exception.IdException;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;
import laptop.database.giornale.CsvGiornale;
import laptop.database.giornale.GiornaleDao;
import laptop.database.giornale.PersistenzaGiornale;
import laptop.database.libro.CsvLibro;
import laptop.database.libro.PersistenzaLibro;
import laptop.database.libro.LibroDao;
import laptop.database.rivista.CsvRivista;
import laptop.database.rivista.PersistenzaRivista;


public class ControllerAcquista {
	
	// Levatoil agamento in quanto lo faccio dopo a seconda del tipo
	 

	private final Libro l;
	private final Giornale g;
	private final Rivista r;
	private static final ControllerSystemState vis = ControllerSystemState.getInstance() ;

    private static final String LIBRO = "libro";
	private static final String RIVISTA="rivista";
	private static final String GIORNALE="giornale";
	private static final String DATABASE="database";
	private static final String MEMORIA="memoria";
	private static final String FILE="file";

	private PersistenzaLibro pL ;
	private PersistenzaRivista pR;
	private PersistenzaGiornale pG;


	public ControllerAcquista()  {


		l=new Libro();
		g=new Giornale();
		r=new Rivista();

	}

	//uso una stringa di 3
	public String[] getNomeCostoDisp(String string) throws CsvValidationException, IOException, IdException, ClassNotFoundException {


	    String [] dati=new String[3];
		switch (vis.getType())
		{
			case LIBRO->
			{
				l.setId(vis.getIdLibro());
                switch (string) {
                    case DATABASE -> pL = new LibroDao();
                    case FILE -> pL = new CsvLibro();
                    case MEMORIA -> pL = new MemoriaLibro();
					default -> Logger.getLogger("cALibro").log(Level.SEVERE,"cost/name book is empty is null");

				}
				dati[0] = pL.getLibroByIdTitoloAutoreLibro(l).get(0).getTitolo();
				dati[1]= String.valueOf(pL.getLibroByIdTitoloAutoreLibro(l).get(0).getPrezzo());
				dati[2]= String.valueOf(pL.getLibroByIdTitoloAutoreLibro(l).get(0).getNrCopie());

			}

			case GIORNALE->
			{
				g.setId(vis.getIdGiornale());

                switch (string) {
                    case DATABASE -> pG = new GiornaleDao();
                    case FILE -> pG = new CsvGiornale();
                    case MEMORIA -> pG = new MemoriaGiornale();
					default -> Logger.getLogger("cAGiornale").log(Level.SEVERE,"cost/name daily is empty is null");

				}
				dati[0] = pG.getGiornaleByIdTitoloAutoreLibro(g).get(0).getTitolo();
				dati[1]= String.valueOf(pG.getGiornaleByIdTitoloAutoreLibro(g).get(0).getPrezzo());
				dati[2]= String.valueOf(pG.getGiornaleByIdTitoloAutoreLibro(g).get(0).getCopieRimanenti());


			}

			case RIVISTA->{
				r.setId(vis.getIdRivista());
                switch (string) {
                    case DATABASE -> pR = new RivistaDao();
                    case FILE -> pR = new CsvRivista();
                    case MEMORIA -> pR = new MemoriaRivista();
					default -> Logger.getLogger("cARivista").log(Level.SEVERE,"cost/name magazine is empty is null");

				}
				dati[0] = pR.getRivistaByIdTitoloAutoreRivista(r).get(0).getTitolo();
				dati[1]= String.valueOf(pR.getRivistaByIdTitoloAutoreRivista(r).get(0).getPrezzo());
				dati[2]= String.valueOf(pR.getRivistaByIdTitoloAutoreRivista(r).get(0).getCopieRim());


			}


			default -> Logger.getLogger("get nome").log(Level.SEVERE," name is wrong!!");
		}
		return dati;
	}


	public float getPrezzo(String q,String string) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
		int quantita=Integer.parseInt(q);
		float prezzo=Float.parseFloat(getNomeCostoDisp(string)[1]);
		vis.setQuantita(quantita);
		vis.setSpesaT(prezzo);
        return quantita*prezzo;

	}
}
