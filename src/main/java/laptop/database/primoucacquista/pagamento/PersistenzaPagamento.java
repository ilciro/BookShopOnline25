package laptop.database.primoucacquista.pagamento;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.model.Pagamento;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;

public class PersistenzaPagamento {
    private static final String DATABASE="src/main/resources/sql/tablePopulate.properties";
    private static final String FILE="report/reportPagamento.csv";
    private static final String MEMORIA="memory/serializzazionePagamento.ser";
    private static final String DATABASEXCEPTION="file sql not exists";
    private static final String FILEXCEPTION="file csv not exists";
    private static final String MEMORIAEXCEPTION="class not in memory";

    public boolean inserisciPagamento(Pagamento p) throws CsvValidationException, IOException, ClassNotFoundException {
        if(p.getIdPag()==0) throw new IOException(" file not found or id null");
        if(p.getAmmontare()==0f) throw new CsvValidationException(" codice isbn insert book is null");
        if(p.getIdOggetto()==0) throw new ClassNotFoundException("class not found or id object is null");
        return true;
    }
    public Pagamento ultimoPagamento() throws CsvValidationException, IOException, ClassNotFoundException {
        if(!Files.exists(Path.of(DATABASE))) throw new IOException(DATABASEXCEPTION);
        if(!Files.exists(Path.of(FILE))) throw new  CsvValidationException(FILEXCEPTION);
        if(!Files.exists(Path.of(MEMORIA))) throw new ClassNotFoundException(MEMORIAEXCEPTION);
        return new Pagamento();
    }
    public boolean cancellaPagamento(Pagamento p) throws IOException, CsvValidationException, ClassNotFoundException {
        if(p.getIdPag()==0) throw new IOException(" file not found or id null");
        if(p.getIdOggetto()==0) throw new CsvValidationException(" id oggetto is null");
        if(p.getAmmontare()==0f) throw new ClassNotFoundException("class not found or payment is null");

        return true;}
    public void inizializza() throws IOException, ClassNotFoundException, SQLException {
        if (!Files.exists(Path.of(DATABASE))) throw new SQLException(DATABASEXCEPTION);
        if (!Files.exists(Path.of(FILE))) throw new IOException(FILEXCEPTION);
        if (!Files.exists(Path.of(MEMORIA))) throw new ClassNotFoundException(MEMORIAEXCEPTION);

    }
    public ObservableList<Pagamento> listPagamentiByUser (Pagamento p) throws CsvValidationException, IOException, ClassNotFoundException {
           if(p.getIdPag()==0)throw new IOException(DATABASEXCEPTION);
           if(p.getAmmontare()==0) throw new CsvValidationException(FILEXCEPTION);
           return FXCollections.observableArrayList();
    }


}
