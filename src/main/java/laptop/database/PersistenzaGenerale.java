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
        String csvexc="CSVALIDATIONEXCEPTION";
        String sqlexc="sqlexcEPTION";
        String memexc="CLASSNOTFOUNDEXCEPTION";
        //usati per settare i vari path
        String pathReport="";
        String pathMemory="";
        //messaggi exception
        switch (type)
        {
            case LIBRO->
            {
                pathReport=REPORT+LIBRO.substring(0,1).toUpperCase()+".csv";
                pathMemory=SERIALIZZAZIONE+LIBRO.substring(0,1).toUpperCase()+".ser";
                csvexc+=" "+LIBRO;
                sqlexc+="  "+LIBRO;
                memexc+=" "+LIBRO;
            }
            case GIORNALE->
            {
                pathReport=REPORT+GIORNALE.substring(0,1).toUpperCase()+".csv";
                pathMemory=SERIALIZZAZIONE+GIORNALE.substring(0,1).toUpperCase()+".ser";
                csvexc+=" "+GIORNALE;
                sqlexc+="  "+GIORNALE;
                memexc+=" "+GIORNALE;
            }
            case RIVISTA->
            {
                pathReport=REPORT+RIVISTA.substring(0,1).toUpperCase()+".csv";
                pathMemory=SERIALIZZAZIONE+RIVISTA.substring(0,1).toUpperCase()+".ser";
                csvexc+=" "+RIVISTA;
                sqlexc+="  "+RIVISTA;
                memexc+=" "+RIVISTA;
            }
            case UTENTE->{
                pathReport=REPORT+UTENTE.substring(0,1).toUpperCase()+".csv";
                pathMemory=SERIALIZZAZIONE+UTENTE.substring(0,1).toUpperCase()+".ser";
                csvexc+=" "+UTENTE;
                sqlexc+="  "+UTENTE;
                memexc+=" "+UTENTE;
            }
            case "pagamentoFattura"->{
                pathReport=REPORT+PAGAMENTO+FATTURA.substring(0,1).toUpperCase()+".csv";
                pathMemory=SERIALIZZAZIONE+PAGAMENTO+FATTURA.substring(0,1).toUpperCase()+".ser";
                csvexc+=" "+FATTURA;
                sqlexc+="  "+FATTURA;
                memexc+=" "+FATTURA;
            }
            case "pagamentoCC"->{
                pathReport=REPORT+PAGAMENTO+CARTACREDITO.substring(0,1).toUpperCase()+".csv";
                pathMemory=SERIALIZZAZIONE+PAGAMENTO+CARTACREDITO.substring(0,1).toUpperCase()+".ser";
                csvexc+=" "+PAGAMENTO+CARTACREDITO;
                sqlexc+="  "+PAGAMENTO+CARTACREDITO;
                memexc+=" "+PAGAMENTO+CARTACREDITO;
            }
            case "cartacredito"->
            {
                pathReport=REPORT+CARTACREDITO.substring(0,1).toUpperCase()+".csv";
                csvexc+=" "+CARTACREDITO;
                sqlexc+="  "+CARTACREDITO;
                memexc+=" "+CARTACREDITO;
            }
            case NEGOZIO->
            {
                pathReport=REPORT+NEGOZIO.substring(0,1).toUpperCase()+".csv";
                pathMemory=SERIALIZZAZIONE+NEGOZIO.substring(0,1).toUpperCase()+".ser";
                csvexc+=" "+NEGOZIO;
                sqlexc+="  "+NEGOZIO;
                memexc+=" "+NEGOZIO;
            }
            case PAGAMENTOTOTALE->
            {
                pathReport=REPORT+PAGAMENTOTOTALE.substring(0,1).toUpperCase()+".csv";
                pathMemory=SERIALIZZAZIONE+PAGAMENTOTOTALE.substring(0,1).toUpperCase()+".ser";
                csvexc+=" "+PAGAMENTOTOTALE;
                sqlexc+="  "+PAGAMENTOTOTALE;
                memexc+=" "+PAGAMENTOTOTALE;
            }
            case REPORTS -> {
                pathReport=REPORT+"Finale.csv";
                pathMemory=SERIALIZZAZIONE+REPORTS.substring(0,1).toUpperCase()+".ser";
                csvexc+=" "+REPORTS;
                sqlexc+="  "+REPORTS;
                memexc+=" "+REPORTS;
            }
            default -> Logger.getLogger("Persistennza generale").log(Level.SEVERE,"type is incorrect !! :{0}",type);
        }
        Logger.getLogger("persistenza").log(Level.INFO,"checking files of type : {0}",type);
        try {
            if (!Files.exists(Path.of(pathReport)))throw  new CsvValidationException(csvexc);
            if (!Files.exists(Path.of(SQL))) throw  new SQLException(sqlexc);
            if(!Files.exists(Path.of(pathMemory))) throw new ClassNotFoundException(memexc);
        }catch (Exception e)
        {
            Logger.getLogger("exception type").log(Level.SEVERE,"exception type has occurred :{0}",e.getClass());
            Logger.getLogger("excpetion").log(Level.SEVERE,"exception :",e);
        }
    }
}
