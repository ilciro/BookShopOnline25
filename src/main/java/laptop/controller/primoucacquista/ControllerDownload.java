package laptop.controller.primoucacquista;


import java.io.*;
import java.net.URISyntaxException;

import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;


import com.itextpdf.text.DocumentException;


import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerSystemState;

import laptop.database.primoucacquista.giornale.CsvGiornale;
import laptop.database.primoucacquista.giornale.GiornaleDao;
import laptop.database.primoucacquista.giornale.MemoriaGiornale;
import laptop.database.primoucacquista.giornale.PersistenzaGiornale;
import laptop.database.primoucacquista.libro.CsvLibro;
import laptop.database.primoucacquista.libro.LibroDao;
import laptop.database.primoucacquista.libro.MemoriaLibro;
import laptop.database.primoucacquista.libro.PersistenzaLibro;

import laptop.database.primoucacquista.rivista.CsvRivista;
import laptop.database.primoucacquista.rivista.MemoriaRivista;
import laptop.database.primoucacquista.rivista.PersistenzaRivista;
import laptop.database.primoucacquista.rivista.RivistaDao;
import laptop.exception.IdException;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;



public class ControllerDownload {
    private final ControllerSystemState vis=ControllerSystemState.getInstance();

	private final Giornale g;
	private final Rivista r;
	private final Libro l;

	private static final String LIBRO="libro";
	private static final String GIORNALE="giornale";
	private static final String RIVISTA="rivista";
	private static final String DATABASE="database";
	private static final String FILE="file";
	private static final String MEMORIA="memoria";

	private  PersistenzaLibro pL;
	private  PersistenzaGiornale pG ;
	private  PersistenzaRivista pR;




	private void acquistaLibro(String persistenza) throws DocumentException, IOException, URISyntaxException, CsvValidationException, IdException, ClassNotFoundException {
		l.setId(vis.getIdLibro());
		l.scarica(vis.getIdLibro());
		l.leggi(vis.getIdLibro());


        switch (persistenza) {
            case DATABASE -> pL = new LibroDao();
            case FILE -> pL = new CsvLibro();
            case MEMORIA -> pL = new MemoriaLibro();
			default -> Logger.getLogger("acquista libro").log(Level.SEVERE," error with book persistency ");
        }

		Libro tempLibro =new Libro();


		for(int i=0;i<pL.getLibri().size();i++)
		{

			if(i==l.getId()-1)
			{
				tempLibro=pL.getLibroByIdTitoloAutoreLibro(l).get(0);
				pL.removeLibroById(l);
				Logger.getLogger("libro eliminato").log(Level.INFO," id eliminated book {0}",i);

			}



		}
		tempLibro.setId(vis.getIdLibro());
		tempLibro.setNrCopie(tempLibro.getNrCopie()-vis.getQuantita());

		if(pL.inserisciLibro(tempLibro)) Logger.getLogger("aggiorno libro").log(Level.INFO,"update book succesfully!!");

	}

	private void acquistaGiornale(String persistenza) throws IOException, DocumentException, CsvValidationException, SQLException, IdException, ClassNotFoundException {
		g.setId(vis.getIdGiornale());
		g.scarica(vis.getIdGiornale());
		g.leggi(vis.getIdGiornale());

		switch (persistenza)
		{
			case DATABASE -> pG=new GiornaleDao();
			case FILE -> pG=new CsvGiornale();
			case MEMORIA -> pG=new MemoriaGiornale();
			default -> Logger.getLogger("acquista giornale").log(Level.SEVERE," error with daily persistency ");

		}

		Giornale tempG=new Giornale();
		for (int i = 0; i< Objects.requireNonNull(pG).getGiornali().size(); i++)
		{

			if(i==g.getId()-1)
			{


				tempG=pG.getGiornaleByIdTitoloAutoreLibro(g).get(0);
				pG.removeGiornaleById(g);
				Logger.getLogger("giornale eliminato").log(Level.INFO," id eliminated daily{0}",i);

			}


		}
		tempG.setId(vis.getIdGiornale());
		tempG.setCopieRimanenti(tempG.getCopieRimanenti()-vis.getQuantita());
		if(pG.inserisciGiornale(tempG)) Logger.getLogger("aggiorno giornale").log(Level.INFO,"update daily succesfully!!");

	}
	private void acquistaRivista(String persistenza) throws IOException, DocumentException, CsvValidationException, SQLException, IdException, ClassNotFoundException {
		r.setId(vis.getIdRivista());
		r.scarica(vis.getIdRivista());
		r.leggi(vis.getIdRivista());

        switch (persistenza) {
            case DATABASE -> pR = new RivistaDao();
            case FILE -> pR = new CsvRivista();
            case MEMORIA -> pR = new MemoriaRivista();
			default -> Logger.getLogger("acquista rivista").log(Level.SEVERE," error with magazine persistency ");

		}

		Rivista tempR=new Rivista();
		for(int i=0;i<pR.getRiviste().size();i++) {
			if (i==r.getId()-1) {

				 tempR=pR.getRivistaByIdTitoloAutoreRivista(r).get(0);
				pR.removeRivistaById(r);
				Logger.getLogger("rivista eliminata").log(Level.INFO," id eliminated magazine{0}",i);

			}

		}
		tempR.setId(vis.getIdRivista());
		tempR.setNrCopie(tempR.getNrCopie()-vis.getQuantita());

		if (pR.inserisciRivista(tempR))Logger.getLogger("aggionro rivista").log(Level.INFO, "update magazine succesfully!!");

	}






		public void scarica(String type,String persistenza) throws IOException, URISyntaxException, DocumentException, SQLException, CsvValidationException, IdException, ClassNotFoundException {

			vis.setTipoModifica("im");

			switch (type)
		{
			case LIBRO->acquistaLibro(persistenza);
			case GIORNALE -> acquistaGiornale(persistenza);
			case RIVISTA -> acquistaRivista(persistenza);

			default -> 	Logger.getLogger("Test scarica").log(Level.SEVERE,"download error");

		}
	}


	public ControllerDownload()  {

        l = new Libro();
		g=new Giornale();
		r=new Rivista();

	}




	}



