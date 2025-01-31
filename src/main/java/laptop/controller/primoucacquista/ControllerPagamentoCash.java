package laptop.controller.primoucacquista;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;

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


	public void controlla(String nome, String cognome, String via, String com,String type) throws SQLException, IdException, IOException, CsvValidationException, ClassNotFoundException {
   PersistenzaFattura pF;




			Fattura f=new Fattura(nome,cognome,via,com,vis.getSpesaT(),0);

			if(type.equals("database"))pF=new ContrassegnoDao();
			else if(type.equals("file")) pF=new CsvFattura();
			else pF= new MemoriaFattura();
			if(!Files.exists(Path.of("memory/serializzazioneFattura.ser")))
				pF.inizializza();

			if(pF.inserisciFattura(f)) cCPD.checkPagamentoData(f.getNome(),type);


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
