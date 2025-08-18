package laptop.database.primoucacquista.pagamentofattura;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.exception.IdException;
import laptop.pagamento.PagamentoFattura;
import org.apache.commons.lang.SystemUtils;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class CsvFattura extends PersistenzaPagamentoFattura {

    private static final int GETINDEXNOMEF=0;
    private static final int GETINDEXCOGNOMEF=1;
    private static final int GETINDEXVIAF=2;
    private static final int GETINDEXCOMF=3;
    private static final int GETINDEXAMMONTAREF=4;
    private static final int GETINDEXIDF=5;
    private static final int GETINDEXIDPRODOTTO=6;
    private static final int GETINDEXEMAIL=8;
    private static final int GETINDEXTIPOACQUISTO=9;
    private  final File fileFattura;
    private final HashMap<String, PagamentoFattura> cacheFattura;

    private static final String APPOGGIO="report/appoggio.csv";
    private static final String PERMESSI="rwx------";
    private static final String PREFIX="prefix";
    private static final String SUFFIX="suffix";
    private static final String FATTURA="report/reportPagamentoFattura.csv";
    private static final String IDWRONG="id wrong ..!!";
    private static final String IDERROR="id error !!..";

    public CsvFattura() {
        this.fileFattura=new File(FATTURA);
        if(!this.fileFattura.exists()) {
            try {
                Files.createFile(Path.of(this.fileFattura.toURI()));
            } catch (IOException e) {
                Logger.getLogger("costruttore").log(Level.SEVERE,"error with file creation :",e);
            }
        }
        this.cacheFattura=new HashMap<>();

    }

    private static void cleanUp(Path path)  {
        try {
            Files.delete(path);
        } catch (IOException e) {
            Logger.getLogger("cleanUpF").log(Level.SEVERE,"error with del file f :",e);
        }
    }

    @Override
    public void inizializza() {
        Path path = Path.of(FATTURA);
        try{
            if(!Files.exists(path)) throw new IOException(" file is empty");

        }catch (IOException e)
        {
            try {
                Files.createFile(path);
            } catch (IOException ex) {
                Logger.getLogger("inizializza csv fattura").log(Level.SEVERE,"error with creation file :",ex);
            }
        }
    }

    @Override
    public boolean inserisciPagamentoFattura(PagamentoFattura f)  {


        try (CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(this.fileFattura, true)))) {

            String[] gVectore = new String[10];

            gVectore[GETINDEXNOMEF] = f.getNome();
            gVectore[GETINDEXCOGNOMEF] = f.getCognome();
            gVectore[GETINDEXVIAF] = f.getVia();
            gVectore[GETINDEXCOMF] = f.getCom();
            gVectore[GETINDEXAMMONTAREF] = String.valueOf(f.getAmmontare());
            gVectore[GETINDEXIDF] = String.valueOf(getIdMax() + 1);
            gVectore[GETINDEXIDPRODOTTO]= String.valueOf(f.getIdProdotto());
            gVectore[GETINDEXEMAIL]=f.getEmail();
            gVectore[GETINDEXTIPOACQUISTO]=f.getTipoAcquisto();
            csvWriter.writeNext(gVectore);

            csvWriter.flush();

        } catch ( IOException e) {
            Logger.getLogger("insert fattura").log(Level.SEVERE,"error in insert fattura csv");
        }


        return true;




    }

    private static  int getIdMax()  {
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

        }catch (IdException |IOException |CsvValidationException e)
        {

            Logger.getLogger(IDWRONG).log(Level.SEVERE, IDERROR);

        }

        return id;

    }

    @Override
    public boolean cancellaPagamentoFattura(PagamentoFattura f)  {
        synchronized (this.cacheFattura) {
            this.cacheFattura.remove(String.valueOf(f.getNome()));
        }
        return removeFattura(f);
    }
    private static synchronized boolean removeFattura(PagamentoFattura f) {
        boolean status=false;
        try {
            if (SystemUtils.IS_OS_UNIX) {
                FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString(PERMESSI));
                Files.createTempFile(PREFIX, SUFFIX, attr); // Compliant
            }
            File tmpFile = new File(APPOGGIO);
            boolean found = isFound(f, tmpFile);
            if (found) {
                Files.move(tmpFile.toPath(), Path.of(FATTURA), REPLACE_EXISTING);
                status = true;
            } else {
                cleanUp(Path.of(tmpFile.toURI()));
            }
        }catch (IOException e)
        {
            Logger.getLogger("removeFattura").log(Level.SEVERE,"error with remove fattura :",e);
        }
        return status;


    }

    private static boolean isFound(PagamentoFattura f, File tmpFile) {
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

        }catch (IOException e){
            Logger.getLogger("isFound").log(Level.SEVERE,"isFound io exception :",e);
        }catch (CsvValidationException e1){
            Logger.getLogger("isFound csv").log(Level.SEVERE,"idFound csv exception :",e1);

        }
        return found;
    }

    @Override
    public PagamentoFattura ultimaFattura()  {
        ObservableList<PagamentoFattura> list=FXCollections.observableArrayList();
        try (CSVReader reader = new CSVReader(new BufferedReader(new FileReader(this.fileFattura)))) {
            list = FXCollections.observableArrayList();
            String[] gVector;
            while ((gVector = reader.readNext()) != null) {
                PagamentoFattura f = getPagamentoFattura(gVector);
                list.add(f);
            }
        }catch (IOException e){
            Logger.getLogger("ultimaFattura").log(Level.SEVERE,"ultimaFattura io exception :",e);
        }catch (CsvValidationException e1){
            Logger.getLogger("ultimaFattura csv").log(Level.SEVERE,"ultimaFattura csv exception :",e1);

        }
        return list.get(list.size()-1);
    }

    private static @NotNull PagamentoFattura getPagamentoFattura(String[] gVector) {
        PagamentoFattura f = new PagamentoFattura();
        f.setNome(gVector[GETINDEXNOMEF]);
        f.setCognome(gVector[GETINDEXCOGNOMEF]);
        f.setVia(gVector[GETINDEXVIAF]);
        f.setCom(gVector[GETINDEXCOMF]);
        f.setAmmontare(Float.parseFloat(gVector[GETINDEXAMMONTAREF]));
        f.setIdFattura(Integer.parseInt(gVector[GETINDEXIDF]));
        f.setIdProdotto(Integer.parseInt(gVector[GETINDEXIDPRODOTTO]));
        f.setEmail(gVector[GETINDEXEMAIL]);
        f.setTipoAcquisto(gVector[GETINDEXTIPOACQUISTO]);
        return f;
    }

    @Override
    public ObservableList<PagamentoFattura> listPagamentiByUserF(PagamentoFattura pF)  {
        ObservableList<PagamentoFattura> list=FXCollections.observableArrayList();
        try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fileFattura)))) {
            String[] gVector;
            list = FXCollections.observableArrayList();


            while ((gVector = csvReader.readNext()) != null) {


                boolean recordFound = gVector[GETINDEXEMAIL].equals(String.valueOf(pF.getEmail()));
                if (recordFound) {


                    list.add(getPagamentoFattura(gVector));

                }

            }
        }catch (IOException e){
            Logger.getLogger("ListPagamentiUserF").log(Level.SEVERE,"listFattura io exception :",e);
        }catch (CsvValidationException e1){
            Logger.getLogger("listPagaemntiUserF csv").log(Level.SEVERE,"listFattura csv exception :",e1);

        }
        try {
            if (list.isEmpty()) {
                throw new IdException("list is empty!!");
            }
        }catch (IdException e )
        {
            Logger.getLogger("list pagamenti by user").log(Level.SEVERE," list is empty !!");
        }

        return list;
    }
}
