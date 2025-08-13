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
    public ObservableList<Negozio> getNegozi()  {
        return retrieveNegozi(this.fdn);
    }
    private static synchronized ObservableList<Negozio> retrieveNegozi(File fd)  {
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

        }catch (IOException e){
            Logger.getLogger("retrieveNegozi file").log(Level.SEVERE,"retrieveNegozi io exception {0}",e);
        }catch (CsvValidationException e1){
            Logger.getLogger("retrieveNegozi csv").log(Level.SEVERE,"retrieveNEgozi csv exception {0}",e1);
        }catch (IdException e2)
        {
            Logger.getLogger("retrieveNegozi id").log(Level.SEVERE,"retrieveNegozi id exception {0}",e2);
        }

        return gList;
    }

    @Override
    public boolean checkOpen(Negozio shop)  {
        return checkOpenIsValid(shop,"isOpen");

    }

    @Override
   public boolean checkRitiro(Negozio shop)  {
        return checkOpenIsValid(shop,"isValid");
    }

    @Override
    public void initializza()  {
        try{
            if(!this.fdn.exists()) throw new IOException();
        }catch (IOException e)
        {
            Logger.getLogger("initialize csvNEgozio").log(Level.SEVERE,"file not exists!!");
            try {
                Files.copy(Path.of(NEGOZIOP), Path.of(LOCATIONEGOZIO), REPLACE_EXISTING);
            } catch (IOException ex) {
                Logger.getLogger("inizializza negozi").log(Level.SEVERE,"inizialize shop exception {0}",ex);
            }

            Logger.getLogger("crea negpzio file").log(Level.SEVERE, "\n eccezione ottenuta nella modalità file.", e);

        }
    }


    private boolean checkOpenIsValid(Negozio shop,String s)  {
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
        }catch (IOException e){
            Logger.getLogger("checkOpenValid").log(Level.SEVERE,"openValid io exception {0}",e);
        }catch (CsvValidationException e1){
            Logger.getLogger("checkOpenValid csv").log(Level.SEVERE,"openValid csv exception {0}",e1);

        }

        return status;

    }
}
