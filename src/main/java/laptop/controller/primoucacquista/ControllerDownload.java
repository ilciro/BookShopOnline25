package laptop.controller.primoucacquista;


import java.io.*;
import java.net.URISyntaxException;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


import com.itextpdf.text.DocumentException;


import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerSystemState;

import laptop.database.giornale.CsvGiornale;
import laptop.database.giornale.GiornaleDao;
import laptop.database.giornale.MemoriaGiornale;
import laptop.database.giornale.PersistenzaGiornale;
import laptop.database.libro.CsvLibro;
import laptop.database.libro.LibroDao;
import laptop.database.libro.MemoriaLibro;
import laptop.database.libro.PersistenzaLibro;
import laptop.database.rivista.CsvRivista;
import laptop.database.rivista.MemoriaRivista;
import laptop.database.rivista.PersistenzaRivista;
import laptop.database.rivista.RivistaDao;
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



	private void acquistaLibro(String persistenza) throws DocumentException, IOException, URISyntaxException, CsvValidationException, IdException, ClassNotFoundException {
  		PersistenzaLibro pL;
		l.setId(vis.getId());
		l.scarica(vis.getId());
		l.leggi(vis.getId());



		if(persistenza.equals(DATABASE)) pL=new LibroDao();
		else if(persistenza.equals(FILE)) pL=new CsvLibro();
		else pL=new MemoriaLibro();

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
		tempLibro.setNrCopie(tempLibro.getNrCopie()-vis.getQuantita());
		tempLibro.setId(vis.getId());

		if(pL.inserisciLibro(tempLibro)) Logger.getLogger("aggiorno libro").log(Level.INFO,"update book succesfully!!");

	}
	private void acquistaGiornale(String persistenza) throws IOException, DocumentException, CsvValidationException, SQLException, IdException, ClassNotFoundException {
  PersistenzaGiornale pG;
		g.setId(vis.getId());
		g.scarica(vis.getId());
		g.leggi(vis.getId());
		if(persistenza.equals(DATABASE))  pG=new GiornaleDao();
		else if(persistenza.equals(FILE)) pG=new CsvGiornale();
		else pG=new MemoriaGiornale();
		Giornale tempG=new Giornale();
		for (int i=0;i<pG.getGiornali().size();i++)
		{

			if(i==g.getId()-1)
			{


				tempG=pG.getGiornaleByIdTitoloAutoreLibro(g).get(0);
				pG.removeGiornaleById(g);
				Logger.getLogger("giornale eliminato").log(Level.INFO," id eliminated daily{0}",i);

			}


		}
		tempG.setCopieRimanenti(tempG.getCopieRimanenti()-vis.getQuantita());
		tempG.setId(vis.getId());
		if(pG.inserisciGiornale(tempG)) Logger.getLogger("aggiorno giornale").log(Level.INFO,"update daily succesfully!!");

	}
	private void acquistaRivista(String persistenza) throws IOException, DocumentException, CsvValidationException, SQLException, IdException, ClassNotFoundException {
  	PersistenzaRivista pR;
		r.setId(vis.getId());
		r.scarica(vis.getId());
		r.leggi(vis.getId());

		if (persistenza.equals(DATABASE)) pR = new RivistaDao();
		else if (persistenza.equals(FILE)) pR = new CsvRivista();
		else pR=new MemoriaRivista();

		Rivista tempR=new Rivista();
		for(int i=0;i<pR.getRiviste().size();i++) {
			if (i==r.getId()-1) {

				 tempR=pR.getRivistaByIdTitoloAutoreRivista(r).get(0);
				pR.removeRivistaById(r);
				Logger.getLogger("rivista eliminata").log(Level.INFO," id eliminated magazine{0}",i);

			}

		}
		tempR.setCopieRim(tempR.getCopieRim()-vis.getQuantita());

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



