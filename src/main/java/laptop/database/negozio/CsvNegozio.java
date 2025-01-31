package laptop.database.negozio;

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
    private static final String LOCATIONNEGOZIO = "report/reportNegozio.csv";
    private static final String NEGOZIOP="src/main/resources/csvfiles/negozio.csv";
    private final File fdn=new File(LOCATIONNEGOZIO);


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
                    n.setIsOpen(Boolean.parseBoolean(gVector[GETINDEXNEGOZIOISOPEN]));
                    if(Boolean.TRUE.equals(n.getIsOpen())) status=true;

                }


            }
        }

        return status;

    }

    @Override
   public boolean checkRitiro(Negozio shop) throws IOException, CsvValidationException {
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
                    n.setIsValid(Boolean.parseBoolean(gVector[GETINDEXNEGOZIOISVALID]));
                   if(Boolean.TRUE.equals(n.getIsValid())) status=true;

                }


            }
        }

        return status;
    }

    @Override
    public void initializza() throws IOException {
        try{
            if(!this.fdn.exists()) throw new IOException();
        }catch (IOException e)
        {
            Logger.getLogger("initialize csvNEgozio").log(Level.SEVERE,"file not exists!!");
            Files.copy(Path.of(NEGOZIOP), Path.of(LOCATIONNEGOZIO), REPLACE_EXISTING);

            Logger.getLogger("crea negpzio file").log(Level.SEVERE, "\n eccezione ottenuta nella modalità file.", e);

        }
    }
}
