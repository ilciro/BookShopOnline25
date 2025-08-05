package laptop.database.primoucacquista.cartacredito;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.model.CartaDiCredito;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Date;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CsvCartaCredito extends PersistenzaCC{
    private static final String LOCATIONCC="report/reportCartaCredito.csv";

    private static final int GETINDEXNOME=0;
    private static final int GETINDEXCOGNOME=1;
    private static final int GETINDEXCODICE=2;
    private static final int GETINDEXSCADENZA=3;
    private static final int GETINDEXPIN=4;
    private static final int GETINDEXAMMONTARE=5;
    private static final int GETINDEXID=6;

    @Override
    public boolean insCC(CartaDiCredito cc) throws IOException, CsvValidationException, ClassNotFoundException {
        try (CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(LOCATIONCC, true)))) {
            String[] gVector = new String[7];
            gVector[GETINDEXNOME] = cc.getNomeUser();
            gVector[GETINDEXCOGNOME] = cc.getCognomeUser();
            gVector[GETINDEXCODICE] = cc.getNumeroCC();
            gVector[GETINDEXSCADENZA] = String.valueOf(cc.getScadenza());
            gVector[GETINDEXPIN] = cc.getCiv();
            gVector[GETINDEXAMMONTARE] = String.valueOf(cc.getAmmontare());
            gVector[GETINDEXID] = String.valueOf(getIdMax() + 1);

            csvWriter.writeNext(gVector);
            csvWriter.flush();
        }
        return getIdMax() != 0;

    }
    private static synchronized int getIdMax() throws IOException, CsvValidationException {
        //used for insert correct idOgg

        String []gVector;
        int max=0;


        try (CSVReader reader = new CSVReader(new FileReader(LOCATIONCC))) {
            while ((gVector = reader.readNext()) != null) {
                if(Integer.parseInt(gVector[GETINDEXID])>max)
                    max= Integer.parseInt(gVector[GETINDEXID]);
            }
        }




        return max;


    }


    @Override
    public ObservableList<CartaDiCredito> getCarteDiCredito(CartaDiCredito cc) throws IOException, CsvValidationException {

        String[] gVector;
        ObservableList<CartaDiCredito>list=FXCollections.observableArrayList();
        boolean recordFound;
        try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(LOCATIONCC)))) {

            while ((gVector = csvReader.readNext()) != null) {
                recordFound = gVector[GETINDEXNOME].equals(cc.getNomeUser()) || gVector[GETINDEXCODICE].equals(cc.getNumeroCC());

                if (recordFound) {
                    CartaDiCredito cc1 = new CartaDiCredito();
                    cc1.setNomeUser(gVector[GETINDEXNOME]);
                    cc1.setNumeroCC(gVector[GETINDEXCODICE]);
                    cc1.setScadenza(Date.valueOf(gVector[GETINDEXSCADENZA]));
                    cc1.setCiv(gVector[GETINDEXPIN]);
                    list.add(cc1);
                }
            }
        }

            return list;
    }

    @Override
    public void inizializza() throws IOException, ClassNotFoundException, SQLException {
        Path path = Path.of(LOCATIONCC);
        try{
           if(!Files.exists(path)) throw new IOException("file not exists");
       }catch (IOException e)
       {
           Logger.getLogger("inizializza").log(Level.SEVERE," file .{0} not exists",LOCATIONCC);
           Files.createFile(path);
           Logger.getLogger("inizializza").log(Level.INFO," file .{0} created",LOCATIONCC);

       }
    }
}
