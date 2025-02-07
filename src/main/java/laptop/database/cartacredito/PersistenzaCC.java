package laptop.database.cartacredito;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.model.CartaDiCredito;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersistenzaCC {
    public boolean insCC(CartaDiCredito cc) throws IOException, CsvValidationException, ClassNotFoundException {

        try {

            if (cc.getNumeroCC() == null) throw new IOException("numero carta null");
            else if(cc.getNumeroCC().isEmpty()) throw new CsvValidationException("numero carta sbagliato");
            else throw new ClassNotFoundException("classe non trovata");

        }catch (IOException|CsvValidationException|ClassNotFoundException e)
        {
            Logger.getLogger("ins cc").log(Level.SEVERE,"could not insert a cc");
        }
        return false;
    }
    public ObservableList<CartaDiCredito> getCarteDiCredito(CartaDiCredito cc) throws IOException, ClassNotFoundException, CsvValidationException {
        ObservableList<CartaDiCredito> lista = FXCollections.observableArrayList();
        try {
            lista.add(cc);
            if(lista.get(0).getScadenza()!=null) throw new IOException("lista is empty");
            if(cc.getNumeroCC()==null) throw new CsvValidationException("code is wrong!");
            else throw new ClassNotFoundException("class not found!!");


        }catch (IOException|CsvValidationException|ClassNotFoundException e)
        {
            Logger.getLogger("get carte credito").log(Level.SEVERE,"could not retrive list cc");

        }
        return lista;    }
    public void inizializza(String type) throws IOException, ClassNotFoundException, SQLException {
        switch (type) {
            case "database" -> throw new SQLException(" cold not create table");
            case "file" -> throw new IOException(" cuold not create file");
            case "memoria" -> throw new ClassNotFoundException("could not create class in memory");
            default -> Logger.getLogger("inizializza persistenzaCC").log(Level.INFO," error in initialize");
        }

    }
}
