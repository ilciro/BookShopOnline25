package laptop.database.primoucacquista.cartacredito;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.exception.IdException;
import laptop.model.CartaDiCredito;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Date;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CsvCartaCredito extends PersistenzaCC{
    private final File fileCartaCredito;
    private static final int GETINDEXNOMEPCC=0;
    private static final int GETINDEXCOGNOMEPCC=1;
    private static final int GETINDEXCODICECARTA=2;
    private static final int GETINDEXSCADCC=3;
    private static final int GETINDEXPINCC=4;
    private static final int GETINDEXAMMONTARE=5;
    private static final int GETINDEXIDCC=6;
    private static final String CARTACREDITO="report/reportCartaCredito.csv";
    private static final String IDWRONG="id wrong ..!!";
    private static final String IDERROR="id error !!..";

    public CsvCartaCredito() throws IOException {
        this.fileCartaCredito=new File(CARTACREDITO);
        if(!this.fileCartaCredito.exists())
            Files.createFile(Path.of(this.fileCartaCredito.toURI()));


    }

    @Override
    public boolean insCC(CartaDiCredito cc) throws IOException ,CsvValidationException{
       boolean status=false;
        try (CSVWriter writer = new CSVWriter(new BufferedWriter(new FileWriter(CARTACREDITO, true)))) {
            String[] gVector = new String[7];

            gVector[GETINDEXNOMEPCC] = cc.getNomeUser();
            gVector[GETINDEXCOGNOMEPCC] = cc.getCognomeUser();
            gVector[GETINDEXCODICECARTA] = cc.getNumeroCC();
            gVector[GETINDEXSCADCC] = String.valueOf(cc.getScadenza());
            gVector[GETINDEXPINCC] = cc.getCiv();
            gVector[GETINDEXAMMONTARE] = String.valueOf(cc.getPrezzoTransazine());
            gVector[GETINDEXIDCC] = String.valueOf(getIdMaxCC() + 1);
            writer.writeNext(gVector);

            writer.flush();

        }
        if (cc.getNumeroCC()!=null) status=true;
        return status;

    }
    private static int getIdMaxCC() throws IOException, CsvValidationException {
        int id;
        try (CSVReader reader = new CSVReader(new BufferedReader(new FileReader(CARTACREDITO)))) {
            String[] gVector;
            id = 0;

            try {
                while ((gVector = reader.readNext()) != null) {
                    id = Integer.parseInt(gVector[GETINDEXIDCC]);

                }
                if (id == 0)
                    throw new IdException(" id is 0!!");
            } catch (IdException e) {
                Logger.getLogger(IDWRONG).log(Level.SEVERE, IDERROR);

            }
        }
        return id;
    }



    @Override
    public ObservableList<CartaDiCredito> getCarteDiCredito(CartaDiCredito cc) throws IOException, CsvValidationException {
        return retriveCarteCredito(this.fileCartaCredito,cc.getNumeroCC(),cc.getNomeUser());
    }

    private ObservableList<CartaDiCredito> retriveCarteCredito(File fd,String numero,String nome) throws IOException, CsvValidationException {
        ObservableList<CartaDiCredito> gList = FXCollections.observableArrayList();
        try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)))) {
            String[] gVector;
            while ((gVector = csvReader.readNext()) != null) {

                if(gVector[GETINDEXCODICECARTA].equals(numero) || gVector[GETINDEXNOMEPCC].equals(nome))
                {
                    CartaDiCredito cc=new CartaDiCredito();
                    cc.setNomeUser(gVector[GETINDEXNOMEPCC]);
                    cc.setCognomeUser(gVector[GETINDEXCOGNOMEPCC]);
                    cc.setNumeroCC(gVector[GETINDEXCODICECARTA]);
                    cc.setScadenza(Date.valueOf(gVector[GETINDEXSCADCC]));
                    cc.setCiv(gVector[GETINDEXPINCC]);
                    cc.setAmmontare(Double.parseDouble(gVector[GETINDEXAMMONTARE]));
                    gList.add(cc);

                }





            }
            if (gList.isEmpty()) {
                throw new IOException("list libro is empty");
            }



        }

        return gList;
    }

    @Override
    public void inizializza() throws IOException, ClassNotFoundException, SQLException {

        Path path = Path.of(CARTACREDITO);
        try{
            if(!Files.exists(path)) throw new IOException("file not exists");
        } catch (IOException e)
        {
            Files.createFile(path);
        }
    }
}
