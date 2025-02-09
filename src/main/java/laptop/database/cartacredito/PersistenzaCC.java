package laptop.database.cartacredito;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.model.CartaDiCredito;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;


public class PersistenzaCC {
    public boolean insCC(CartaDiCredito cc) throws IOException, CsvValidationException, ClassNotFoundException {



            if (cc.getNumeroCC() == null) throw new IOException("numero carta null");
            else if(cc.getNumeroCC().isEmpty()) throw new CsvValidationException("numero carta sbagliato");
            else throw new ClassNotFoundException("classe non trovata");


    }
    public ObservableList<CartaDiCredito> getCarteDiCredito(CartaDiCredito cc) throws IOException, ClassNotFoundException, CsvValidationException {
        ObservableList<CartaDiCredito> lista = FXCollections.observableArrayList();

            lista.add(cc);
            if(lista.get(0).getScadenza()!=null) throw new IOException("lista is empty");
            if(cc.getNumeroCC()==null) throw new CsvValidationException("code is wrong!");
            else throw new ClassNotFoundException("class not found!!");


         }
    public void inizializza()throws IOException, ClassNotFoundException, SQLException {

        if(!Files.exists(Path.of("FileSql/cartacredito.sql"))) throw new SQLException("db file not found");
        if(!Files.exists(Path.of("report/reportCartaCredito.csv"))) throw new IOException(" file csv not exists");
        if(!Files.exists(Path.of("memory/serializzazioneCartaCredito.ser"))) throw new ClassNotFoundException("class not loaded in memory");

    }
}
