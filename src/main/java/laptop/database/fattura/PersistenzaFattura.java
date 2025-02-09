package laptop.database.fattura;

import com.opencsv.exceptions.CsvValidationException;
import laptop.model.Fattura;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;

public class PersistenzaFattura {

    public boolean inserisciFattura(Fattura f) throws IOException, ClassNotFoundException {
        if(f.getIdFattura()==0) throw new IOException(" id fattura is null");
        if(f.getAmmontare()<=0) throw new ClassNotFoundException("fattura not finded");
        return true;}
    public boolean cancellaFattura(Fattura f) throws CsvValidationException, IOException, ClassNotFoundException {
        if(f.getIdFattura()==0) throw new IOException("id is null or zero");
        if(f.getAmmontare()<=0) throw new CsvValidationException(" amount is zero");
        if(f.getNome() == null)throw new ClassNotFoundException(" fattura is null");
        return false;}
    public Fattura ultimaFattura() throws CsvValidationException, IOException, ClassNotFoundException {return null;}
    public void inizializza() throws IOException, ClassNotFoundException, SQLException {
       if(!Files.exists(Path.of("FileSql/fattura.sql"))) throw new SQLException("file db not exits");
       if(!Files.exists(Path.of("report/reportFattura.csv"))) throw new IOException(" file csv not exists");
       if(!Files.exists(Path.of("memory/serializzazioneFattura.ser"))) throw new ClassNotFoundException(" class not found in memory");
    }
}
