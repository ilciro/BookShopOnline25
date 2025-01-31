package laptop.controller.primoucacquista;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.ObservableList;
import laptop.controller.ControllerSystemState;

import laptop.database.cartacredito.CartaCreditoDao;
import laptop.database.cartacredito.CsvCartaCredito;
import laptop.database.cartacredito.MemoriaCartaCredito;
import laptop.database.cartacredito.PersistenzaCC;
import laptop.exception.IdException;
import laptop.model.CartaDiCredito;
import laptop.model.Pagamento;
import laptop.model.user.User;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;


public class ControllerPagamentoCC {


	private final ControllerSystemState vis= ControllerSystemState.getInstance();

	private final ControllerCheckPagamentoData cCPD;

	private PersistenzaCC pCC;
	private CartaDiCredito cc;

	private static final String SERIALIZZAZIONE="memory/serializzazioneCartaCredito.ser";
	private static final String DATABASE="database";
	private static final String FILE="file";
	private static final String MEMORIA="memoria";






	public ControllerPagamentoCC()  {


			cCPD = new ControllerCheckPagamentoData();



	}

	public boolean aggiungiCartaDB(String n, String c, String cod, java.sql.Date data, String civ, float prezzo,String persistenza)
            throws   CsvValidationException, IOException, ClassNotFoundException {
   			cc= new CartaDiCredito(n, c, cod,  data, civ, prezzo);


			cCPD.controllaPag(String.valueOf(data),c,civ);


			switch (persistenza)
			{
				case DATABASE->pCC=new CartaCreditoDao();
				case FILE->pCC=new CsvCartaCredito();
				case MEMORIA->pCC=new MemoriaCartaCredito();
				default -> Logger.getLogger("aggiungi carta db").log(Level.SEVERE," error in persistency");
			}
			if (!Files.exists(Path.of(SERIALIZZAZIONE)))
				pCC.inizializza();
			return pCC.insCC(cc);

	}



	public ObservableList<CartaDiCredito> ritornaElencoCC(String nomeU,String persistenza) throws IOException, ClassNotFoundException, CsvValidationException, IdException {

		cc=new CartaDiCredito();
		cc.setNomeUser(nomeU);

		switch (persistenza)
		{
			case DATABASE->pCC=new CartaCreditoDao();
			case FILE->pCC=new CsvCartaCredito();
			case MEMORIA->pCC=new MemoriaCartaCredito();
			default -> Logger.getLogger("elenco cc dal db").log(Level.SEVERE," list is empty");
		}
		if (!Files.exists(Path.of(SERIALIZZAZIONE)))
			pCC.inizializza();

		return pCC.getCarteDiCredito(cc);


	}
	




	public void pagamentoCC(String nome,String database) throws IdException, IOException, CsvValidationException, ClassNotFoundException {
  Pagamento p ;
		//effettuo pagamento
		p=new Pagamento();
		p.setIdPag(0);
		p.setMetodo("cCredito");
		p.setNomeUtente(nome);
		p.setAmmontare(vis.getSpesaT());
		if(vis.getIsLogged())
			p.setEmail(User.getInstance().getEmail());
		else p.setEmail(null);


		switch (vis.getType()) {
			case "libro" -> {
				Libro l=new Libro();
				l.setId(vis.getId());
				cCPD.checkPagamentoData(nome,database);
			}
			case "giornale" -> {
				Giornale g=new Giornale();
				g.setId(vis.getId());
				cCPD.checkPagamentoData(nome,database);
			}
			case "rivista" -> {
					Rivista r=new Rivista();
					r.setId(vis.getId());
					cCPD.checkPagamentoData(nome,database);

			}
			default -> throw new IdException(" id not found");

		}



		Logger.getLogger("Pagamento effettuato").log(Level.INFO, "Payment  done!!");

	}

	public String[] getInfo()
	{
		String [] dati=new String[2];
		dati[0]= User.getInstance().getNome();
		dati[1]=User.getInstance().getCognome();
		return dati;
	}

	public ObservableList<CartaDiCredito> ritornaElencoCByNumero(String numero,String persistenza) throws IOException, ClassNotFoundException, CsvValidationException, IdException {

		cc=new CartaDiCredito();
		cc.setNumeroCC(numero);

		switch (persistenza)
		{
			case DATABASE->pCC=new CartaCreditoDao();
			case FILE->pCC=new CsvCartaCredito();
			case MEMORIA->pCC=new MemoriaCartaCredito();
			default -> Logger.getLogger("elenco cc dal db").log(Level.SEVERE," list is empty");
		}
		if (!Files.exists(Path.of(SERIALIZZAZIONE)))
			pCC.inizializza();

		return pCC.getCarteDiCredito(cc);


	}



}

		

		


	

