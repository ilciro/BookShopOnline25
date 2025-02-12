package laptop.controller.primoucacquista;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerSystemState;
import laptop.database.fattura.ContrassegnoDao;
import laptop.exception.IdException;
import laptop.model.Fattura;
import laptop.model.user.User;
import laptop.database.fattura.CsvFattura;
import laptop.database.fattura.MemoriaFattura;
import laptop.database.fattura.PersistenzaFattura;


public class ControllerPagamentoCash {

	private final ControllerSystemState vis= ControllerSystemState.getInstance();
	private final ControllerCheckPagamentoData cCPD;
	private PersistenzaFattura pF;


	public void controlla(String nome, String cognome, String via, String com,String type) throws IdException, IOException, CsvValidationException, ClassNotFoundException, SQLException {



			Fattura f=new Fattura(nome,cognome,via,com,vis.getSpesaT(),0);


			switch (type)
			{
				case "database"->pF=new ContrassegnoDao();
				case "file"->pF=new CsvFattura();
				case "memoria"->pF=new MemoriaFattura();
				default -> Logger.getLogger("controlla").log(Level.SEVERE," persistency is wrong!!");
			}



			pF.inizializza();


			if(pF.inserisciFattura(f))
				cCPD.checkPagamentoData(f.getNome(),type);


	}

	public ControllerPagamentoCash()  { cCPD=new ControllerCheckPagamentoData();}

	public String[] getInfo()
	{
		String [] dati=new String[2];
		dati[0]= User.getInstance().getNome();
		dati[1]=User.getInstance().getCognome();
		return dati;
	}


}
