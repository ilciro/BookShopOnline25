package laptop.controller.primoucacquista;

import java.io.File;
import java.io.IOException;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.controller.ControllerSystemState;

import laptop.database.primoucacquista.cartacredito.CartaCreditoDao;
import laptop.database.primoucacquista.cartacredito.CsvCartaCredito;
import laptop.database.primoucacquista.cartacredito.PersistenzaCC;
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
import laptop.database.primoucacquista.pagamentototale.PersistenzzaPagamentoTotale;
import laptop.database.primoucacquista.pagamentototale.PagamentoTotaleDao;
import laptop.database.terzoucgestioneprofiloggetto.report.CsvReport;
import laptop.database.terzoucgestioneprofiloggetto.report.MemoriaReport;
import laptop.database.terzoucgestioneprofiloggetto.report.PersistenzaReport;
import laptop.database.terzoucgestioneprofiloggetto.report.ReportDao;
import laptop.exception.IdException;
import laptop.model.CartaDiCredito;
import laptop.model.Report;
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
	private static final String CCREDITO="cCredito";
	private static final String LIBRO="libro";
	private static final String GIORNALE="giornale";
	private static final String RIVISTA="rivista";

	private  PersistenzaLibro pL;
	private PersistenzaGiornale pG;
	private PersistenzaRivista pR;
    private PersistenzzaPagamentoTotale pT;
	private PersistenzaPagamentoCartaCredito pCC;
	private PersistenzaReport pRepo;

	private CartaDiCredito cc;
	private PersistenzaCC persistenzaCC;



	public ControllerPagamentoCC(){}



	public boolean aggiungiCartaDB(String n, String c, String cod, java.sql.Date data, String civ, float prezzo,String persistenza)
            throws CsvValidationException, IOException, ClassNotFoundException, SQLException {
   			cc= new CartaDiCredito(n, c, cod,  data, civ, prezzo);



			switch (persistenza)
			{
				case DATABASE->persistenzaCC=new CartaCreditoDao();
				case FILE->persistenzaCC=new CsvCartaCredito();
				default -> Logger.getLogger("aggiungi carta db").log(Level.SEVERE," error in persistency");
			}
				persistenzaCC.inizializza();
			return persistenzaCC.insCC(cc);

	}




	public ObservableList<CartaDiCredito> ritornaElencoCC(String nomeU, String persistenza,String numero) throws IOException, ClassNotFoundException, CsvValidationException, SQLException {

		cc=new CartaDiCredito();
		cc.setNomeUser(nomeU);
		cc.setNumeroCC(numero);

		switch (persistenza)
		{
			case DATABASE->persistenzaCC=new CartaCreditoDao();
			case FILE->persistenzaCC=new CsvCartaCredito();
			//case MEMORIA->pCC=new MemoriaCartaCredito();
			default -> Logger.getLogger("elenco cc dal db").log(Level.SEVERE," list is empty");
		}
			persistenzaCC.inizializza();


        return persistenzaCC.getCarteDiCredito(cc);

	}
	


	private PagamentoCartaCredito pagamentoLibroCC(String nome,String database,String cognome,String mail) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
		Libro l=new Libro();
		l.setId(vis.getIdLibro());
		int id=l.getId();
		switch (database)
		{
			case DATABASE -> pL=new LibroDao();
			case FILE -> pL=new CsvLibro();
			case MEMORIA -> pL=new MemoriaLibro();
			default -> Logger.getLogger(" errore cpn la persistenza nel libro").log(Level.SEVERE,"persisentcy book error !!");
		}
		String tipo=pL.getLibroByIdTitoloAutoreLibro(l).get(0).getCategoria();
		PagamentoCartaCredito p =new PagamentoCartaCredito(0,CCREDITO,nome, vis.getSpesaT(),mail,tipo,id );
		p.setCognomeUtente(cognome);
		return p;
	}

	private PagamentoCartaCredito pagamentoGiornaleCC(String nome,String database,String cognome,String mail) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
		Giornale g=new Giornale();
		g.setId(vis.getIdGiornale());
		int id=g.getId();
		switch (database)
		{
			case DATABASE -> pG=new GiornaleDao();
			case FILE -> pG=new CsvGiornale();
			case MEMORIA -> pG=new MemoriaGiornale();
			default -> Logger.getLogger(" errore cpn la persistenza nel giornale").log(Level.SEVERE,"persisentcy daily error !!");
		}
		String tipo=pG.getGiornaleByIdTitoloAutoreLibro(g).get(0).getCategoria();
		PagamentoCartaCredito p =new PagamentoCartaCredito(0,CCREDITO,nome, vis.getSpesaT(),mail,tipo,id );
		p.setCognomeUtente(cognome);
		return p;
	}

	private PagamentoCartaCredito pagamentoRivistaCC(String nome,String database,String cognome,String mail) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
		Rivista r=new Rivista();
		r.setId(vis.getIdRivista());
		int id=r.getId();
		switch (database)
		{
			case DATABASE -> pR=new RivistaDao();
			case FILE -> pR=new CsvRivista();
			case MEMORIA -> pR=new MemoriaRivista();
			default -> Logger.getLogger(" errore cpn la persistenza nella rivista").log(Level.SEVERE,"persisentcy magazine error !!");
		}
		String tipo= pR.getRivistaByIdTitoloAutoreRivista(r).get(0).getCategoria();
		PagamentoCartaCredito p =new PagamentoCartaCredito(0,CCREDITO,nome, vis.getSpesaT(),mail,tipo,id );
		p.setCognomeUtente(cognome);
		return p;

	}


	public void pagamentoCC(String nome,String database,String cognome) throws IdException, IOException, CsvValidationException, ClassNotFoundException, SQLException {
  		String mail;
		  if(vis.getIsLogged())
			  mail=User.getInstance().getEmail();
		  else  mail=null;
		//effettuo pagamento

		vis.setTipoModifica("insert");


        PagamentoCartaCredito p ;

        switch (vis.getType()) {
			case LIBRO-> p=pagamentoLibroCC(nome,database,cognome,mail);
			case GIORNALE ->p= pagamentoGiornaleCC(nome,database,cognome,mail);
			case RIVISTA -> p=pagamentoRivistaCC(nome,database,cognome,mail);


			default -> throw new IdException(" id not found");

		}

		//effettua pagamento

		switch (database)
		{
			case DATABASE -> pT=new PagamentoTotaleDao();
			case FILE -> pT=new PagamentoTotaleCsv();
			case MEMORIA -> pT=new PagamentoTotaleMemoria();
			default -> Logger.getLogger("pagamento totale ").log(Level.SEVERE," error total payment  persistency");

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
			Logger.getLogger("pagamento effettuato ").log(Level.INFO," payment success with id . {0}", p.getIdPagCC());
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
		//creo report cc
		switch (database) {
			case FILE -> pRepo = new CsvReport();
			case MEMORIA -> pRepo = new MemoriaReport();
			case DATABASE -> pRepo = new ReportDao();
			default -> Logger.getLogger("inizializza report").log(Level.SEVERE," error with repo cc");
		}
		pRepo.inizializza();
		Report r=new Report();
		String titolo = "";
		float prezzo=0;
		String tipologia = "";
		switch (vis.getType())
		{
			case LIBRO ->
			{
				Libro l=new Libro();
				l.setId(vis.getIdLibro());
				titolo=pL.getLibroByIdTitoloAutoreLibro(l).get(0).getTitolo();
				prezzo=pL.getLibroByIdTitoloAutoreLibro(l).get(0).getPrezzo();
				tipologia=pL.getLibroByIdTitoloAutoreLibro(l).get(0).getCategoria();
			}
			case GIORNALE -> {
				Giornale g=new Giornale();
				g.setId(vis.getIdGiornale());
				titolo=pG.getGiornaleByIdTitoloAutoreLibro(g).get(0).getTitolo();
				prezzo=pG.getGiornaleByIdTitoloAutoreLibro(g).get(0).getPrezzo();
				tipologia=pG.getGiornaleByIdTitoloAutoreLibro(g).get(0).getCategoria();
			}
			case RIVISTA -> {
				Rivista riv=new Rivista();
				riv.setId(vis.getIdRivista());
				titolo=pR.getRivistaByIdTitoloAutoreRivista(riv).get(0).getTitolo();
				prezzo=pR.getRivistaByIdTitoloAutoreRivista(riv).get(0).getPrezzo();
				tipologia=pR.getRivistaByIdTitoloAutoreRivista(riv).get(0).getCategoria();
			}
		}
		r.setTipologiaOggetto(tipologia);
		r.setTitoloOggetto(titolo);
		r.setNrPezzi(vis.getQuantita());
		r.setPrezzo(prezzo);
		r.setTotale(r.getPrezzo()*vis.getQuantita());

		if(pRepo.insertReport(r))
			Logger.getLogger("insert repo cc").log(Level.INFO, "repo cc correct inserted");


	}

	public String[] getInfo()
	{
		String [] dati=new String[2];
		dati[0]= User.getInstance().getNome();
		dati[1]=User.getInstance().getCognome();
		return dati;
	}


	private boolean checkCorrettezzaSplitCodice(String[] tokensCod) throws IdException {
		int i = 0;
		for (String token : tokensCod) {
			if (token.length() == 4) {

				i++;

			} else throw new IdException("split exception ");
		}
		return i==4;
	}
	private boolean checkCorrettezzaAnno(String[] tokensData, int i) throws IdException {
		if ((tokensData[0].length() == 4) && (Integer.parseInt(tokensData[0]) >= 2025)) {
			Logger.getLogger("anno controllato").log(Level.INFO, " year is correct");
			i++;
		} else throw new IdException("anno è sbagliato");
		return i==1;
	}
	private boolean checkCorrettezzaMese(String[] tokensData,int i) throws IdException {
		if (tokensData[1].length() == 2)
		{	tokensData[1]=getMeseGiorno(tokensData[1]);
			if ((Integer.parseInt(tokensData[1]) >= 1 && Integer.parseInt(tokensData[1]) <= 12)) {
				Logger.getLogger(" mese controllato").log(Level.INFO, " month is correct . {0}",tokensData[1]);
				i++;
			}else throw new IdException(" mese è sbagliato");
		}
		return i==1;
	}
	private boolean checkCorrettezzaGiorno(String[] tokensData,int i) throws IdException {
		if (tokensData[2].length() == 2)
		{	tokensData[2]=getMeseGiorno(tokensData[1]);
			if ((Integer.parseInt(tokensData[1]) >= 1 && Integer.parseInt(tokensData[1]) <= 12)) {
				Logger.getLogger("giorno controllato").log(Level.INFO, " giorno is correct . {0}",tokensData[1]);
				i++;
			}else throw new IdException("giorno è sbagliato");
		}
		return i==1;
	}

	public boolean correttezza(String codice, String scadenza,String civ) throws IdException {
		boolean correttezzaSplit;
		boolean correttezzaAnno;
		boolean correttezzaMese;
		boolean correttezzaGiorno;
		boolean correttezzaData=false;
		boolean correttezzaCiv = false;


		String delimiter = "-"; // Space as delimiter
		String[] tokensCod = codice.split(delimiter);

		correttezzaSplit=checkCorrettezzaSplitCodice(tokensCod);

		int i = 0;
		String[] tokensData = scadenza.split("/");

			correttezzaAnno=checkCorrettezzaAnno(tokensData,i);
			correttezzaMese=checkCorrettezzaMese(tokensData,i);
			correttezzaGiorno=checkCorrettezzaGiorno(tokensData,i);





		if(correttezzaSplit&&correttezzaAnno&&correttezzaMese&&correttezzaGiorno) correttezzaData=true;


		if(civ.length()==3)
		{
			correttezzaCiv=checkInteger(civ);
			if(!correttezzaCiv) throw new IdException(" civ format is worng");
		}








		return  correttezzaData&&correttezzaCiv;
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
			default -> Logger.getLogger("getMeseGiorno").log(Level.SEVERE,"month/day is incorrect");

		}
		return finale;
	}

	private boolean checkInteger(String civ)
	{
		return civ.matches("\\d+");
	}
}







		

		


	

