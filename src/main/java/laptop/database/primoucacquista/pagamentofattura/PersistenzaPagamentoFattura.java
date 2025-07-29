package laptop.database.primoucacquista.pagamentofattura;

import com.opencsv.exceptions.CsvValidationException;
import laptop.model.pagamento.PagamentoFattura;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;

public class PersistenzaPagamentoFattura {

    public boolean inserisciPagamentoFattura(PagamentoFattura f) throws IOException, ClassNotFoundException {

        return f.getIdFattura()!=-1;
    }
    public boolean cancellaPagamentoFattura(PagamentoFattura f) throws CsvValidationException, IOException, ClassNotFoundException {
        return f.getIdFattura()!=-1;
    }
    public void inizializza() throws IOException, ClassNotFoundException, SQLException {
       if(!Files.exists(Path.of("src/main/resources/sql/tablePopulate.properties"))) throw new SQLException("file db not exits");
       if(!Files.exists(Path.of("report/reportPagamentoFattura.csv"))) throw new IOException(" file csv not exists");
       if(!Files.exists(Path.of("memory/serializzazionePagamentoFattura.ser"))) throw new ClassNotFoundException(" class not found in memory");
    }

    public PagamentoFattura ultimaFattura() throws IOException, CsvValidationException, ClassNotFoundException {
        return new PagamentoFattura();
    }

}
