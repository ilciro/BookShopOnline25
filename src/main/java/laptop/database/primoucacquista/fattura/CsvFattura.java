package laptop.database.primoucacquista.fattura;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.exception.IdException;
import laptop.model.Fattura;
import org.apache.commons.lang.SystemUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class CsvFattura extends PersistenzaFattura {

    private static final int GETINDEXNOMEF=0;
    private static final int GETINDEXCOGNOMEF=1;
    private static final int GETINDEXVIAF=2;
    private static final int GETINDEXCOMF=3;
    private static final int GETINDEXAMMONTAREF=4;
    private static final int GETINDEXIDF=5;
    private  final File fileFattura;
    private final HashMap<String,Fattura> cacheFattura;

    private static final String APPOGGIO="report/appoggio.csv";
    private static final String PERMESSI="rwx------";
    private static final String PREFIX="prefix";
    private static final String SUFFIX="suffix";
    private static final String FATTURA="report/reportFattura.csv";
    private static final String IDWRONG=" id wrong ..!!";
    private static final String IDERROR="id error !!..";

    public CsvFattura() throws IOException {
        this.fileFattura=new File(FATTURA);
        if(!this.fileFattura.exists())
            Files.createFile(Path.of(this.fileFattura.toURI()));
        this.cacheFattura=new HashMap<>();

    }

    private static void cleanUp(Path path) throws IOException {
        Files.delete(path);
    }

    @Override
    public void inizializza() throws IOException, ClassNotFoundException, SQLException {
        Path path = Path.of(FATTURA);
        try{
            if(!Files.exists(path)) throw new IOException(" file is empty");

        }catch (IOException e)
        {
            Files.createFile(path);
        }
    }

    @Override
    public boolean inserisciFattura(Fattura f) throws IOException {


        try (CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(this.fileFattura, true)))) {

            String[] gVectore = new String[6];

            gVectore[GETINDEXNOMEF] = f.getNome();
            gVectore[GETINDEXCOGNOMEF] = f.getCognome();
            gVectore[GETINDEXVIAF] = f.getVia();
            gVectore[GETINDEXCOMF] = f.getCom();
            gVectore[GETINDEXAMMONTAREF] = String.valueOf(f.getAmmontare());
            gVectore[GETINDEXIDF] = String.valueOf(getIdMax() + 1);
            csvWriter.writeNext(gVectore);

            csvWriter.flush();

        } catch (CsvValidationException e) {
            Logger.getLogger("inset fattura").log(Level.SEVERE,"error in insert fattura csv");
        }


        return true;




    }

    private static  int getIdMax() throws IOException, CsvValidationException {
        //used for insert correct idOgg
        String[] gVector;
        int id = 0;


        try {



                try(CSVReader reader = new CSVReader(new FileReader(FATTURA))) {
                    while ((gVector = reader.readNext()) != null) {
                        id = Integer.parseInt(gVector[GETINDEXIDF]);
                    }
                }






            if (id == 0)
                throw new IdException("id == 0 ");

        }catch (IdException  e)
        {

            Logger.getLogger(IDWRONG).log(Level.SEVERE, IDERROR);

        }

        return id;

    }

    @Override
    public boolean cancellaFattura(Fattura f) throws CsvValidationException, IOException {
        synchronized (this.cacheFattura) {
            this.cacheFattura.remove(String.valueOf(f.getNome()));
        }
        return removeFattura(f);
    }
    private static synchronized boolean removeFattura(Fattura f) throws IOException, CsvValidationException {
        boolean status=false;
        if (SystemUtils.IS_OS_UNIX) {
            FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString(PERMESSI));
            Files.createTempFile(PREFIX, SUFFIX, attr); // Compliant
        }
        File tmpFile = new File(APPOGGIO);
        boolean found = isFound(f, tmpFile);
        if (found) {
            Files.move(tmpFile.toPath(), Path.of(FATTURA), REPLACE_EXISTING);
            status=true;
        } else {
            cleanUp(Path.of(tmpFile.toURI()));
        }
        return status;


    }

    private static boolean isFound(Fattura f, File tmpFile) throws IOException, CsvValidationException {
        boolean found = false;
        try (CSVReader reader = new CSVReader(new BufferedReader(new FileReader(FATTURA)));
             CSVWriter writer= new CSVWriter(new BufferedWriter(new FileWriter(tmpFile, true)))) {
            String[] gVector;

            boolean recordFound;
            while ((gVector = reader.readNext()) != null) {

                recordFound = gVector[GETINDEXIDF].equals(String.valueOf(f.getIdFattura()));


                if (!recordFound)
                    writer.writeNext(gVector);
                else
                    found = true;
            }
            writer.flush();

        }
        return found;
    }

    @Override
    public Fattura ultimaFattura() throws IOException, CsvValidationException {
        ObservableList<Fattura> list;
        try (CSVReader reader = new CSVReader(new BufferedReader(new FileReader(this.fileFattura)))) {
            list = FXCollections.observableArrayList();
            String[] gVector;
            while ((gVector = reader.readNext()) != null) {
                Fattura f = new Fattura();
                f.setNome(gVector[GETINDEXNOMEF]);
                f.setCognome(gVector[GETINDEXCOGNOMEF]);
                f.setVia(gVector[GETINDEXVIAF]);
                f.setCom(gVector[GETINDEXCOMF]);
                f.setAmmontare(Float.parseFloat(gVector[GETINDEXAMMONTAREF]));
                f.setIdFattura(Integer.parseInt(gVector[GETINDEXIDF]));
                list.add(f);


            }
        }
        return list.get(list.size()-1);
    }
}
