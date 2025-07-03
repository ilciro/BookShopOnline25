package laptop.database.primoucacquista.negozio;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.exception.IdException;
import laptop.model.Negozio;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class CsvNegozio extends PersistenzaNegozio{
    private static final String LOCATIONEGOZIO="report/reportNegozio.csv";
    private static final String NEGOZIOP="src/main/resources/csvfiles/negozio.csv";
    private final File fdn=new File(LOCATIONEGOZIO);


    private static final int GETINDEXNEGOZIOID = 0;
    private static final int GETINDEXNEGOZIONOME = 1;
    private static final int GETINDEXNEGOZIOVIA = 2;
    private static final int GETINDEXNEGOZIOISVALID = 3;
    private static final int GETINDEXNEGOZIOISOPEN = 4;


    @Override
    public ObservableList<Negozio> getNegozi() throws CsvValidationException, IOException, IdException {
        return retrieveNegozi(this.fdn);
    }
    private static synchronized ObservableList<Negozio> retrieveNegozi(File fd) throws IOException, CsvValidationException,IdException {
        ObservableList<Negozio> gList=  FXCollections.observableArrayList();
        try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)))) {
            String[] gVector;

            while ((gVector = csvReader.readNext()) != null) {

                Negozio n=new Negozio();
                n.setId(Integer.parseInt(gVector[GETINDEXNEGOZIOID]));
                n.setNome(gVector[GETINDEXNEGOZIONOME]);
                n.setVia(gVector[GETINDEXNEGOZIOVIA]);
                n.setIsOpen(Boolean.parseBoolean(gVector[GETINDEXNEGOZIOISOPEN]));
                n.setIsValid(Boolean.parseBoolean(gVector[GETINDEXNEGOZIOISVALID]));
                gList.add(n);

            }
            if (gList.isEmpty()) {
                throw new IdException("list libro is empty");
            }



        }

        return gList;
    }

    @Override
    public boolean checkOpen(Negozio shop) throws CsvValidationException, IOException {
        return checkOpenIsValid(shop,"isOpen");

    }

    @Override
   public boolean checkRitiro(Negozio shop) throws IOException, CsvValidationException {
        return checkOpenIsValid(shop,"isValid");
    }

    @Override
    public void initializza() throws IOException {
        try{
            if(!this.fdn.exists()) throw new IOException();
        }catch (IOException e)
        {
            Logger.getLogger("initialize csvNEgozio").log(Level.SEVERE,"file not exists!!");
            Files.copy(Path.of(NEGOZIOP), Path.of(LOCATIONEGOZIO), REPLACE_EXISTING);

            Logger.getLogger("crea negpzio file").log(Level.SEVERE, "\n eccezione ottenuta nella modalità file.", e);

        }
    }


    private boolean checkOpenIsValid(Negozio shop,String s) throws CsvValidationException, IOException {
        // s o per il valid o per isopen
        boolean status=false;
        String[] gVector;
        boolean recordFound;
        try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(this.fdn)))) {

            while ((gVector = csvReader.readNext()) != null) {

                recordFound = gVector[GETINDEXNEGOZIONOME].equals(shop.getNome());
                if (recordFound) {

                    Negozio n=new Negozio();

                    n.setId(Integer.parseInt(gVector[GETINDEXNEGOZIOID]));
                    n.setNome(gVector[GETINDEXNEGOZIONOME]);
                    if(s.equals("isValid")) {
                        if (gVector[GETINDEXNEGOZIOISVALID].equals("1")) {
                            n.setIsValid(true);
                            status = n.getIsValid();
                        }
                    }
                    else if(s.equals("isOpen") && gVector[GETINDEXNEGOZIOISOPEN].equals("1")) {
                            n.setIsOpen(true);
                             status = n.getIsOpen();
                        }


                }


            }
        }

        return status;

    }
}
