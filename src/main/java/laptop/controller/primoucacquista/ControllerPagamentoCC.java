package laptop.controller.primoucacquista;

import java.io.IOException;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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

import laptop.database.primoucacquista.pagamentocartacredito.MemoriaPagamentoCartaCredito;
import laptop.database.primoucacquista.pagamentototale.PagamentoTotaleCsv;
import laptop.database.primoucacquista.pagamentototale.PagamentoTotaleMemoria;
import laptop.database.primoucacquista.rivista.CsvRivista;
import laptop.database.primoucacquista.rivista.MemoriaRivista;
import laptop.database.primoucacquista.rivista.PersistenzaRivista;
import laptop.database.primoucacquista.rivista.RivistaDao;


import laptop.database.primoucacquista.pagamentocartacredito.CsvPagamentoCartaCredito;
import laptop.database.primoucacquista.pagamentocartacredito.PagamentoCartaCreditoDao;
import laptop.database.primoucacquista.pagamentocartacredito.PersistenzaPagamentoCartaCredito;
import laptop.database.primoucacquista.pagamentototale.PagamentoTotale;
import laptop.database.primoucacquista.pagamentototale.PagamentoTotaleDao;
import laptop.exception.IdException;
//import laptop.model.CartaDiCredito;
import laptop.model.pagamento.PagamentoCartaCredito;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Rivista;
import laptop.model.user.User;
import laptop.model.raccolta.Libro;


public class ControllerPagamentoCC {


	private final ControllerSystemState vis= ControllerSystemState.getInstance();




	private static final String DATABASE="database";
	private static final String FILE="file";
	private static final String MEMORIA="memoria";

	private  PersistenzaLibro pL;
	private PersistenzaGiornale pG;
	private PersistenzaRivista pR;
    private PagamentoTotale pT;
	private PersistenzaPagamentoCartaCredito pCC;


    public ControllerPagamentoCC()  {




	}

	/*

	public boolean aggiungiCartaDB(String n, String c, String cod, java.sql.Date data, String civ, float prezzo,String persistenza)
            throws CsvValidationException, IOException, ClassNotFoundException, SQLException {
   			cc= new CartaDiCredito(n, c, cod,  data, civ, prezzo);



			switch (persistenza)
			{
			//	case DATABASE->pCC=new CartaCreditoDao();
			//	case FILE->pCC=new CsvCartaCredito();
			//	case MEMORIA->pCC=new MemoriaCartaCredito();
				default -> Logger.getLogger("aggiungi carta db").log(Level.SEVERE," error in persistency");
			}
			//	pCC.inizializza();
		//	return pCC.insCC(cc);

	}



	public ObservableList<CartaDiCredito> ritornaElencoCC(String nomeU,String persistenza) throws IOException, ClassNotFoundException, CsvValidationException, SQLException {

		cc=new CartaDiCredito();
		cc.setNomeUser(nomeU);

		switch (persistenza)
		{
			case DATABASE->pCC=new CartaCreditoDao();
			//case FILE->pCC=new CsvCartaCredito();
			//case MEMORIA->pCC=new MemoriaCartaCredito();
			default -> Logger.getLogger("elenco cc dal db").log(Level.SEVERE," list is empty");
		}
			pCC.inizializza();

		return pCC.getCarteDiCredito(cc);


	}
	

*/



	public void pagamentoCC(String nome,String database,String cognome) throws IdException, IOException, CsvValidationException, ClassNotFoundException, SQLException {
  		String mail;
		  String tipo;
		  int id;
		  if(vis.getIsLogged())
			  mail=User.getInstance().getEmail();
		  else  mail=null;
		//effettuo pagamento

		vis.setTipoModifica("insert");


        PagamentoCartaCredito p;

        switch (vis.getType()) {
			case "libro" -> {
				Libro l=new Libro();
				l.setId(vis.getIdLibro());
				id=l.getId();
				switch (database)
				{
					case DATABASE -> pL=new LibroDao();
					case FILE -> pL=new CsvLibro();
					case MEMORIA -> pL=new MemoriaLibro();
					default -> Logger.getLogger(" errore cpn la persistenza nel libro").log(Level.SEVERE,"persisentcy book error !!");
				}
				tipo=pL.getLibroByIdTitoloAutoreLibro(l).get(0).getCategoria();
				 p =new PagamentoCartaCredito(0,"cCcredito",nome, vis.getSpesaT(),mail,tipo,id );
				 p.setCognomeUtente(cognome);

			}

			case "giornale" -> {
				Giornale g=new Giornale();
				g.setId(vis.getIdGiornale());
				id=g.getId();
				switch (database)
				{
					case DATABASE -> pG=new GiornaleDao();
					case FILE -> pG=new CsvGiornale();
					case MEMORIA -> pG=new MemoriaGiornale();
					default -> Logger.getLogger(" errore cpn la persistenza nel giornale").log(Level.SEVERE,"persisentcy daily error !!");
				}
				tipo=pG.getGiornaleByIdTitoloAutoreLibro(g).get(0).getCategoria();
				p =new PagamentoCartaCredito(0,"cCcredito",nome, vis.getSpesaT(),mail,tipo,id );
				p.setCognomeUtente(cognome);
				}
			case "rivista" -> {
				Rivista r=new Rivista();
				r.setId(vis.getIdRivista());
				id=r.getId();
				switch (database)
				{
					case DATABASE -> pR=new RivistaDao();
					case FILE -> pR=new CsvRivista();
					case MEMORIA -> pR=new MemoriaRivista();
					default -> Logger.getLogger(" errore cpn la persistenza nella rivista").log(Level.SEVERE,"persisentcy magazine error !!");
				}
				tipo= pR.getRivistaByIdTitoloAutoreRivista(r).get(0).getCategoria();
				p =new PagamentoCartaCredito(0,"cCcredito",nome, vis.getSpesaT(),mail,tipo,id );
				p.setCognomeUtente(cognome);

			}


			default -> throw new IdException(" id not found");

		}

		//effettua pagamento

		switch (database)
		{
			case DATABASE -> pT=new PagamentoTotaleDao();
			case FILE -> pT=new PagamentoTotaleCsv();
			case MEMORIA -> pT=new PagamentoTotaleMemoria();
		}
		pT.inizializza();



		switch (database)
		{
			case DATABASE ->pCC=new PagamentoCartaCreditoDao();
			case FILE -> pCC=new CsvPagamentoCartaCredito();
			case MEMORIA -> pCC=new MemoriaPagamentoCartaCredito();
			default -> Logger.getLogger("controlla tipologia fattura").log(Level.SEVERE," persistency fattura is wrong!!");

		}
		//creo tabella fattuira
		pCC.inizializza();


		if(pCC.inserisciPagamentoCartaCredito(p)) {
			Logger.getLogger("pagamento effettuato ").log(Level.INFO," payment success with id .", p.getIdPagCC());
			if(database.equals(FILE))
			{

				pT=new PagamentoTotaleCsv();
				pT.inizializza();
				pT.inserisciPagamentoCartaCredito(new PagamentoCartaCredito());
			}
			if(database.equals(MEMORIA))
			{
				pT=new PagamentoTotaleMemoria();
				pT.inizializza();
				pT.inserisciPagamentoCartaCredito(new PagamentoCartaCredito());
			}
		}

	}

	public String[] getInfo()
	{
		String [] dati=new String[2];
		dati[0]= User.getInstance().getNome();
		dati[1]=User.getInstance().getCognome();
		return dati;
	}

	public boolean correttezza(String codice, String scadenza,String civ) throws IdException {
		boolean correttezzaCodice = false;
		boolean correttezzaData = false;
		boolean correttezzaCiv = false;


		String delimiter = "-"; // Space as delimiter
		String[] tokensCod = codice.split(delimiter);

		int i = 0;
		for (String token : tokensCod) {
			if (token.length() == 4) {
				i++;

			} else throw new IdException("codice carta non valido");
		}
		if (i == 4) correttezzaCodice = true;

		i = 0;

		String[] tokensData = scadenza.split("/");
		for (String token : tokensData) {
			if ((tokensData[0].length() == 4) && (Integer.parseInt(tokensData[0]) >= 2025)) {
				Logger.getLogger("anno controlalto").log(Level.INFO, " year is correct");
				i++;
			} else throw new IdException("anno è sbagliato");
			if (tokensData[1].length() == 2)
			{	tokensData[1]=getMeseGiorno(tokensData[1]);
				if ((Integer.parseInt(tokensData[1]) >= 1 && Integer.parseInt(tokensData[1]) <= 12)) {
					Logger.getLogger(" mese controllato").log(Level.INFO, " month is correct .",tokensData[1]);
					i++;
				}else throw new IdException(" mese è sbagliato");
			}
			if (tokensData[2].length() == 2)
			{	tokensData[2]=getMeseGiorno(tokensData[1]);
				if ((Integer.parseInt(tokensData[1]) >= 1 && Integer.parseInt(tokensData[1]) <= 12)) {
					Logger.getLogger("giorno controllato").log(Level.INFO, " giorno is correct .",tokensData[1]);
					i++;
				}else throw new IdException("giorno è sbagliato");
			}
			if (i == 3) correttezzaData = true;


		}

		if(civ.length()==3)
		{
			correttezzaCiv=checkInteger(civ);
			if(!correttezzaCiv) throw new IdException(" civ format is worng");
		}








		return correttezzaCodice && correttezzaData&&correttezzaCiv;
	}


	private String getMeseGiorno(String stringa)
	{
		String finale="";
		switch (stringa)
		{
			case "01"-> finale="1";
			case "02"-> finale="2";
			case "03"-> finale="3";
			case "04"-> finale="4";
			case "05"-> finale="5";
			case "06"-> finale="6";
			case "07"-> finale="7";
			case "08"-> finale="8";
			case "09"-> finale="9";

		}
		return finale;
	}

	private boolean checkInteger(String civ)
	{
		return civ.matches("-?\\d+(\\d+\\d+)?");
	}
}


	/*
	public ObservableList<CartaDiCredito> ritornaElencoCByNumero(String numero,String persistenza) throws IOException, ClassNotFoundException, CsvValidationException, SQLException {

		cc=new CartaDiCredito();
		cc.setNumeroCC(numero);

		switch (persistenza)
		{
			case DATABASE->pCC=new CartaCreditoDao();
			case FILE->pCC=new CsvCartaCredito();
			case MEMORIA->pCC=new MemoriaCartaCredito();
			default -> Logger.getLogger("elenco cc dal db").log(Level.SEVERE," list is empty");
		}
			pCC.inizializza();

		return pCC.getCarteDiCredito(cc);


	}



}

	 */

		

		


	

