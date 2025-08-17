package laptop.database;

import com.opencsv.exceptions.CsvValidationException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersistenzaGenerale {
    private static final String REPORT="report/report";
    private static final String SERIALIZZAZIONE="memory/serializzazione";
    private static final String SQL="sql/tableCreate.sql";
    private static final String LIBRO="libro";
    private static final String GIORNALE="giornale";
    private static final String RIVISTA="rivista";
    private static final String UTENTE="utente";
    private static final String FATTURA="fattura";
    private static final String CARTACREDITO="CartaCredito";
    private static final String PAGAMENTO="Pagamento";
    private static final String NEGOZIO="negozio";
    private static final String PAGAMENTOTOTALE="pagamentoTotale";
    private static final String REPORTS="report";


    public void getExcepptionInit(String type)
    {
        //usati per settare i vari path
        String pathReport="";
        String pathMemory="";
        //messaggi exception
        String CSVEXC="CSVALIDATIONEXCEPTION";
        String SQLEXC="SQLEXCEPTION";
        String MEMEXC="CLASSNOTFOUNDEXCEPTION";

        switch (type)
        {
            case LIBRO->
            {
                pathReport=REPORT+LIBRO.substring(0,1).toUpperCase()+".csv";
                pathMemory=SERIALIZZAZIONE+LIBRO.substring(0,1).toUpperCase()+".ser";
                CSVEXC+=" "+LIBRO;
                SQLEXC+="  "+LIBRO;
                MEMEXC+=" "+LIBRO;
            }
            case GIORNALE->
            {
                pathReport=REPORT+GIORNALE.substring(0,1).toUpperCase()+".csv";
                pathMemory=SERIALIZZAZIONE+GIORNALE.substring(0,1).toUpperCase()+".ser";
                CSVEXC+=" "+GIORNALE;
                SQLEXC+="  "+GIORNALE;
                MEMEXC+=" "+GIORNALE;
            }
            case RIVISTA->
            {
                pathReport=REPORT+RIVISTA.substring(0,1).toUpperCase()+".csv";
                pathMemory=SERIALIZZAZIONE+RIVISTA.substring(0,1).toUpperCase()+".ser";
                CSVEXC+=" "+RIVISTA;
                SQLEXC+="  "+RIVISTA;
                MEMEXC+=" "+RIVISTA;
            }
            case UTENTE->{
                pathReport=REPORT+UTENTE.substring(0,1).toUpperCase()+".csv";
                pathMemory=SERIALIZZAZIONE+UTENTE.substring(0,1).toUpperCase()+".ser";
                CSVEXC+=" "+UTENTE;
                SQLEXC+="  "+UTENTE;
                MEMEXC+=" "+UTENTE;
            }
            case "pagamentoFattura"->{
                pathReport=REPORT+"Pagamento"+FATTURA.substring(0,1).toUpperCase()+".csv";
                pathMemory=SERIALIZZAZIONE+"Pagamento"+FATTURA.substring(0,1).toUpperCase()+".ser";
                CSVEXC+=" "+FATTURA;
                SQLEXC+="  "+FATTURA;
                MEMEXC+=" "+FATTURA;
            }
            case "pagamentoCC"->{
                pathReport=REPORT+PAGAMENTO+CARTACREDITO.substring(0,1).toUpperCase()+".csv";
                pathMemory=SERIALIZZAZIONE+PAGAMENTO+CARTACREDITO.substring(0,1).toUpperCase()+".ser";
                CSVEXC+=" "+PAGAMENTO+CARTACREDITO;
                SQLEXC+="  "+PAGAMENTO+CARTACREDITO;
                MEMEXC+=" "+PAGAMENTO+CARTACREDITO;
            }
            case "cartacredito"->
            {
                pathReport=REPORT+CARTACREDITO.substring(0,1).toUpperCase()+".csv";
                CSVEXC+=" "+CARTACREDITO;
                SQLEXC+="  "+CARTACREDITO;
                MEMEXC+=" "+CARTACREDITO;
            }
            case "negozio"->
            {
                pathReport=REPORT+NEGOZIO.substring(0,1).toUpperCase()+".csv";
                pathMemory=SERIALIZZAZIONE+NEGOZIO.substring(0,1).toUpperCase()+".ser";
                CSVEXC+=" "+NEGOZIO;
                SQLEXC+="  "+NEGOZIO;
                MEMEXC+=" "+NEGOZIO;
            }
            case "pagamentoTotale"->
            {
                pathReport=REPORT+PAGAMENTOTOTALE.substring(0,1).toUpperCase()+".csv";
                pathMemory=SERIALIZZAZIONE+PAGAMENTOTOTALE.substring(0,1).toUpperCase()+".ser";
                CSVEXC+=" "+PAGAMENTOTOTALE;
                SQLEXC+="  "+PAGAMENTOTOTALE;
                MEMEXC+=" "+PAGAMENTOTOTALE;
            }
            case REPORTS -> {
                pathReport=REPORT+"Finale.csv";
                pathMemory=SERIALIZZAZIONE+REPORTS.substring(0,1).toUpperCase()+".ser";
                CSVEXC+=" "+REPORTS;
                SQLEXC+="  "+REPORTS;
                MEMEXC+=" "+REPORTS;
            }
        }
        Logger.getLogger("persistenza").log(Level.INFO,"checking files of type : {0}",type);

        try {
            if (!Files.exists(Path.of(pathReport)))throw  new CsvValidationException(CSVEXC);
            if (!Files.exists(Path.of(SQL))) throw  new SQLException(SQLEXC);
            if(!Files.exists(Path.of(pathMemory))) throw new ClassNotFoundException(MEMEXC);
        }catch (Exception e)
        {
            Logger.getLogger("exception type").log(Level.SEVERE,"exception type has occurred :{0}",e.getClass());
            Logger.getLogger("excpetion").log(Level.SEVERE,"exception :",e);
        }
    }
}
