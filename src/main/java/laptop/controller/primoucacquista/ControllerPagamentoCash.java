package laptop.controller.primoucacquista;

import java.util.logging.Level;
import java.util.logging.Logger;


import laptop.controller.ControllerSystemState;


import laptop.database.primoucacquista.giornale.CsvGiornale;
import laptop.database.primoucacquista.giornale.GiornaleDao;
import laptop.database.primoucacquista.giornale.MemoriaGiornale;
import laptop.database.primoucacquista.giornale.PersistenzaGiornale;
import laptop.database.primoucacquista.libro.CsvLibro;
import laptop.database.primoucacquista.libro.LibroDao;
import laptop.database.primoucacquista.libro.MemoriaLibro;
import laptop.database.primoucacquista.libro.PersistenzaLibro;
import laptop.database.primoucacquista.pagamentofattura.CsvFattura;
import laptop.database.primoucacquista.pagamentofattura.MemoriaFattura;
import laptop.database.primoucacquista.pagamentofattura.PersistenzaPagamentoFattura;
import laptop.database.primoucacquista.pagamentototale.PersistenzaPagamentoTotale;
import laptop.database.primoucacquista.pagamentototale.PagamentoTotaleCsv;
import laptop.database.primoucacquista.pagamentototale.PagamentoTotaleDao;
import laptop.database.primoucacquista.pagamentototale.PagamentoTotaleMemoria;
import laptop.database.primoucacquista.rivista.CsvRivista;
import laptop.database.primoucacquista.rivista.MemoriaRivista;
import laptop.database.primoucacquista.rivista.PersistenzaRivista;
import laptop.database.primoucacquista.rivista.RivistaDao;
import laptop.database.terzoucgestioneprofiloggetto.report.CsvReport;
import laptop.database.terzoucgestioneprofiloggetto.report.MemoriaReport;
import laptop.database.terzoucgestioneprofiloggetto.report.PersistenzaReport;
import laptop.database.terzoucgestioneprofiloggetto.report.ReportDao;
import laptop.model.Report;
import laptop.model.pagamento.PagamentoFattura;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;
import laptop.model.user.User;

import laptop.database.primoucacquista.pagamentofattura.ContrassegnoDao;


public class ControllerPagamentoCash {

	private final ControllerSystemState vis= ControllerSystemState.getInstance();
	private PersistenzaPagamentoTotale pT;
	private PersistenzaPagamentoFattura pF;
    private static final String DATABASE="database";
	private static final String FILE="file";
	private static final String MEMORIA="memoria";
	private static final String LIBRO="libro";
	private static final String GIORNALE="giornale";
	private static final String RIVISTA="rivista";
	private PersistenzaLibro pL;
	private PersistenzaGiornale pG;
	private PersistenzaRivista pR;
	private PersistenzaReport pRepo;

	public ControllerPagamentoCash() {
		Logger.getLogger("controlla pagamento cash").log(Level.INFO,"costructor");
	}

	public void controlla(String nome, String cognome, String via, String com,String type)  {


		//1
		switch (type)
		{
			case DATABASE -> pT=new PagamentoTotaleDao();
			case FILE -> pT=new PagamentoTotaleCsv();
			case MEMORIA -> pT=new PagamentoTotaleMemoria();
			default -> Logger.getLogger("controlla pagamento totale").log(Level.SEVERE," persistency total payment is wrong!!");

		}
		pT.inizializza();

		//2
		switch (type)
		{
			case DATABASE -> pF=new ContrassegnoDao();
			case FILE -> pF=new CsvFattura();
			case MEMORIA -> pF=new MemoriaFattura();
			default -> Logger.getLogger("controlla tipologia fattura").log(Level.SEVERE," persistency fattura is wrong!!");

		}
		//creo tabella fattuira
		pF.inizializza();

		//3
		int id = switch (vis.getType()) {
            case LIBRO -> vis.getIdLibro();
            case GIORNALE -> vis.getIdGiornale();
            case RIVISTA -> vis.getIdRivista();
            default -> 0;
        };


        PagamentoFattura p = new PagamentoFattura(nome, cognome, via, com, vis.getSpesaT(), 0, id);
		p.setTipoAcquisto(ritornaTipoOggetto(type,vis.getType()));


		if(vis.getIsLogged())
			p.setEmail(User.getInstance().getEmail());
		else p.setEmail("");





		//inserisco in pagamentoTotale
		if(pF.inserisciPagamentoFattura(p))
		{
			Logger.getLogger("pagamento effettuato ").log(Level.INFO,"payment success with id . {0}", p.getIdFattura());
			//database with triggers
			if(type.equals(FILE)) pT=new PagamentoTotaleCsv();
			else if(type.equals(MEMORIA)) pT=new PagamentoTotaleMemoria();
			pT.inserisciPagamentoFattura(p);

		}
		//inserisco in report finale

        switch (type) {
            case FILE -> pRepo = new CsvReport();
            case MEMORIA -> pRepo = new MemoriaReport();
            case DATABASE -> pRepo = new ReportDao();
			default -> Logger.getLogger("inizializza report").log(Level.SEVERE," error with repo cash");
        }
		pRepo.inizializza();
		Report r=new Report();
		r.setTipologiaOggetto(ritornaTipoOggetto(type,vis.getType()));
		String titolo = "";
		float prezzo=0;
		switch (vis.getType())
		{
			case LIBRO ->
					{
						Libro l=new Libro();
						l.setId(vis.getIdLibro());
						titolo=pL.getLibroByIdTitoloAutoreLibro(l).get(0).getTitolo();
						prezzo=pL.getLibroByIdTitoloAutoreLibro(l).get(0).getPrezzo();
					}
			case GIORNALE -> {
				Giornale g=new Giornale();
				g.setId(vis.getIdGiornale());
				titolo=pG.getGiornaleByIdTitoloAutoreLibro(g).get(0).getTitolo();
				prezzo=pG.getGiornaleByIdTitoloAutoreLibro(g).get(0).getPrezzo();
			}
			case RIVISTA -> {
				Rivista riv=new Rivista();
				riv.setId(vis.getIdRivista());
				titolo=pR.getRivistaByIdTitoloAutoreRivista(riv).get(0).getTitolo();
				prezzo=pR.getRivistaByIdTitoloAutoreRivista(riv).get(0).getPrezzo();
			}
		}
		r.setTitoloOggetto(titolo);
		r.setNrPezzi(vis.getQuantita());
		r.setPrezzo(prezzo);
		r.setTotale(r.getPrezzo()*vis.getQuantita());

		if(pRepo.insertReport(r))
			Logger.getLogger("insert repo cash").log(Level.INFO, "repo cash correct inserted");



	}


	public String[] getInfo()
	{
		String [] dati=new String[2];
		dati[0]= User.getInstance().getNome();
		dati[1]=User.getInstance().getCognome();
		return dati;
	}


	private String ritornaTipoOggetto(String persistenza,String type)  {
		String tipologia = "";



		switch (type)
		{
			case LIBRO ->
			{
				Libro l=new Libro();
				switch (persistenza)
				{
					case DATABASE -> pL=new LibroDao();
					case FILE -> pL=new CsvLibro();
					case MEMORIA -> pL=new MemoriaLibro();
					default -> Logger.getLogger("ritorna libro").log(Level.SEVERE,"persistency return book is wrong!!");

				}
				l.setId(vis.getIdLibro());
				tipologia=pL.getLibroByIdTitoloAutoreLibro(l).get(0).getCategoria();
			}
			case GIORNALE -> {
				Giornale g=new Giornale();
				switch (persistenza)
				{
					case DATABASE -> pG=new GiornaleDao();
					case FILE -> pG=new CsvGiornale();
					case MEMORIA -> pG=new MemoriaGiornale();
					default -> Logger.getLogger("ritorna giornale").log(Level.SEVERE,"persistency return daily is wrong!!");

				}
				g.setId(vis.getIdGiornale());
				tipologia=pG.getGiornaleByIdTitoloAutoreLibro(g).get(0).getCategoria();
			}
			case RIVISTA -> {
				Rivista r=new Rivista();
				switch (persistenza)
				{
					case DATABASE -> pR=new RivistaDao();
					case FILE -> pR=new CsvRivista();
					case MEMORIA -> pR=new MemoriaRivista();
					default -> Logger.getLogger("ritorna rivista").log(Level.SEVERE,"persistency return magazine is wrong!!");

				}
				r.setId(vis.getIdRivista());
				tipologia=pR.getRivistaByIdTitoloAutoreRivista(r).get(0).getCategoria();
			}
			default -> Logger.getLogger("ritorna tipo oggetto").log(Level.SEVERE," error with type object");
		}




		return tipologia;
	}


}
