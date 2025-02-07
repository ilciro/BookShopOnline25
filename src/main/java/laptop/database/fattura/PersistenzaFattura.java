package laptop.database.fattura;

import com.opencsv.exceptions.CsvValidationException;
import laptop.model.Fattura;

import java.io.IOException;
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
    public void inizializza(String persistenza) throws IOException, ClassNotFoundException, SQLException {
        if(persistenza.equals("database")) throw new SQLException(" table not created");
        else if(persistenza.equals("file")) throw new IOException(" file not find");
        else throw new ClassNotFoundException(" memory error");
    }
}
