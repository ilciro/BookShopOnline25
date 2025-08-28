package laptop.database.primoucacquista.rivista;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.controller.ControllerSystemState;
import laptop.exception.IdException;
import laptop.model.raccolta.Raccolta;
import laptop.model.raccolta.Rivista;
import org.apache.commons.lang.SystemUtils;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class CsvRivista extends PersistenzaRivista {

    private static final String LOCATIONR="report/reportRivista.csv";
    private static final int GETINDEXTITOLOR = 0;
    private static final int GETINDEXTIPOLOGIAR = 1;
    private static final int GETINDEXAUTORER = 2;
    private static final int GETINDEXLINGUAR = 3;
    private static final int GETINDEXEDITORER = 4;
    private static final int GETINDEXDESCRIZIONER = 5;
    private static final int GETINDEXDATAR = 6;
    private static final int GETINDEXDISPR = 7;
    private static final int GETINDEXPREZZOR = 8;
    private static final int GETINDEXCOPIER = 9;
    private static final int GETINDEXIDR = 10;
    private final HashMap<Integer, Rivista> cacheRivista;
    private final File fdR = new File(LOCATIONR);
    private static final String APPOGGIO="report/appoggio.csv";
    private static final String PERMESSI="rwx------";
    private static final String PREFIX="prefix";
    private static final String SUFFIX="suffix";
    private static final ControllerSystemState vis=ControllerSystemState.getInstance();
    private static final String RIVISTAP="src/main/resources/csvfiles/rivista.csv";


    public CsvRivista()  {

        this.cacheRivista = new HashMap<>();


    }


    @Override
    public boolean inserisciRivista(Rivista r)  {
        boolean duplicatedR = false;
        boolean duplicatedT = false;
        boolean duplicatedA = false;
        boolean duplicatedE = false;
        synchronized (this.cacheRivista) {
            for (Map.Entry<Integer, Rivista> mapR : this.cacheRivista.entrySet()) {
                if (mapR.getValue().getTitolo() != null)
                    duplicatedT = mapR.getValue().getTitolo() != null;
                if (mapR.getValue().getAutore() != null)
                    duplicatedA = mapR.getValue().getAutore() != null;
                if (mapR.getValue().getEditore() != null)
                    duplicatedE = mapR.getValue().getEditore() != null;
                duplicatedR = duplicatedT && duplicatedA && duplicatedE;

            }


        }
        if (!duplicatedR) {
            List<Rivista> list = returnRivistaByTAE(this.fdR, r.getTitolo(), r.getAutore(), r.getEditore());
            duplicatedR = (!list.isEmpty());
        }
        if (duplicatedR)
            try {
                Logger.getLogger("try rivista").log(Level.INFO, "id rivista sbagliato !!");
                throw new IdException(" id rivista sbagliato or duplicated");
            } catch (IdException e) {
                Logger.getLogger("catch rivista").log(Level.SEVERE, "remove rivista...");
                //rimuovo e se lista vuota
                removeRivistaById(r);
            }
        return inserimentoRivista(this.fdR, r);
    }

    private static synchronized boolean inserimentoRivista(File fd, Rivista r)  {
        try (CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(fd, true)))) {
            String[] gVector = new String[11];


            gVector[GETINDEXTITOLOR] = r.getTitolo();
            gVector[GETINDEXTIPOLOGIAR] = r.getCategoria();
            gVector[GETINDEXAUTORER] = r.getAutore();
            gVector[GETINDEXLINGUAR] = r.getLingua();
            gVector[GETINDEXEDITORER] = r.getEditore();
            gVector[GETINDEXDESCRIZIONER] = r.getDescrizione();
            gVector[GETINDEXDATAR] = String.valueOf(r.getDataPubb());
            gVector[GETINDEXDISPR] = String.valueOf(r.getDisp());
            gVector[GETINDEXPREZZOR] = String.valueOf(r.getPrezzo());
            gVector[GETINDEXCOPIER] = String.valueOf(r.getNrCopie());
            if(vis.getTipoModifica().equals("im")) gVector[GETINDEXIDR] = String.valueOf(vis.getIdRivista());
            else if(vis.getTipoModifica().equals("insert")) gVector[GETINDEXIDR] = String.valueOf(getIdMax() + 1);
            else throw new CsvValidationException(" type of modif at magazine is wrong !!");
            csvWriter.writeNext(gVector);
            csvWriter.flush();
        }catch (IOException |CsvValidationException e){
            Logger.getLogger("inserimento riviste csv").log(Level.SEVERE,"insert rivista csv exception :",e);
        }
        return getIdMax() != 0;
    }

    private static synchronized List<Rivista> returnRivistaByTAE(File fd, String tit, String autor, String edit)  {
        List<Rivista> rivistaList=new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)))) {
            String[] gVector;
            boolean recordFound;

            rivistaList = new ArrayList<>();
            while ((gVector = csvReader.readNext()) != null) {

                recordFound = gVector[GETINDEXTITOLOR].equals(tit) || gVector[GETINDEXAUTORER].equals(autor)
                        || gVector[GETINDEXEDITORER].equals(edit) || gVector[GETINDEXIDR].equals(String.valueOf(vis.getIdRivista()));
                if (recordFound) {


                    Rivista r = getRivista(gVector);


                    rivistaList.add(r);

                }
            }
        }catch (IOException |CsvValidationException e){
            Logger.getLogger("listarivista").log(Level.SEVERE,"list is empty  exception :",e);
        }
        return rivistaList;


    }

    private static @NotNull Rivista getRivista(String[] gVector) {
        String titolo = gVector[GETINDEXTITOLOR];
        String tipologia = gVector[GETINDEXTIPOLOGIAR];
        String autore = gVector[GETINDEXAUTORER];
        String lingua = gVector[GETINDEXLINGUAR];
        String editore = gVector[GETINDEXEDITORER];
        String desc = gVector[GETINDEXDESCRIZIONER];
        String data = gVector[GETINDEXDATAR];
        String disp = gVector[GETINDEXDISPR];
        String prezzo = gVector[GETINDEXPREZZOR];
        String copie = gVector[GETINDEXCOPIER];
        String id = gVector[GETINDEXIDR];

        Rivista r = new Rivista();

        r.setTitolo(titolo);
        r.setCategoria(tipologia);
        r.setAutore(autore);
        r.setLingua(lingua);
        r.setEditore(editore);
        r.setDescrizione(desc);
        r.setDataPubb(LocalDate.parse(data));
        r.setDisp(Integer.parseInt(disp));
        r.setPrezzo(Float.parseFloat(prezzo));
        r.setNrCopie(Integer.parseInt(copie));
        r.setId(Integer.parseInt(id));

        return r;

    }

    private static synchronized int getIdMax()  {
        //used for insert correct idOgg

        String []gVector;
        int max=0;


        try (CSVReader reader = new CSVReader(new FileReader(LOCATIONR))) {
            while ((gVector = reader.readNext()) != null) {
                if(Integer.parseInt(gVector[GETINDEXIDR])>max)
                    max= Integer.parseInt(gVector[GETINDEXIDR]);
            }
        }catch (IOException |CsvValidationException e)
        {
            Logger.getLogger("idMax rivista").log(Level.SEVERE," id error :",e);
        }


        return max;


    }

    @Override
    public boolean removeRivistaById(Rivista r)  {
        synchronized (this.cacheRivista) {
            this.cacheRivista.remove(r.getId());
        }
        return removeRivistaId(this.fdR, r);
    }

    private static synchronized boolean removeRivistaId(File fd, Rivista r)  {
        return deleteByType(r, fd);
    }

    private static synchronized boolean deleteByType(Rivista r, File fd) {
        boolean status = false;
        try {
            if (SystemUtils.IS_OS_UNIX) {
                FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString(PERMESSI));
                Files.createTempFile(PREFIX, SUFFIX, attr); // Compliant
            }
            File tmpFile = new File(APPOGGIO);
            boolean found = isFound(r, fd, tmpFile);
            if (found) {
                Files.move(tmpFile.toPath(), fd.toPath(), REPLACE_EXISTING);
                status = true;

            } else {
                cleanUp(Path.of(tmpFile.toURI()));
            }
        }catch (IOException e)
        {
            Logger.getLogger("delete by type").log(Level.SEVERE,"error with delete :",e);
        }
        return status;

    }

    private static boolean isFound(Rivista r, File fd, File tmpFile) {
        boolean found = false;
        try (CSVReader reader = new CSVReader(new BufferedReader(new FileReader(fd)));
             CSVWriter writer = new CSVWriter(new BufferedWriter(new FileWriter(tmpFile, true)))) {
            String[] gVector;
            boolean recordFound;
            while ((gVector = reader.readNext()) != null) {

                recordFound = gVector[GETINDEXIDR].equals(String.valueOf(r.getId()))
                        || gVector[GETINDEXIDR].equals(String.valueOf(vis.getIdRivista()))
                        || gVector[GETINDEXTITOLOR].equals(r.getTitolo());

                if (recordFound)
                   found=true;
                else
                    writer.writeNext(gVector);
            }
            writer.flush();
        }catch (IOException | CsvValidationException e){
            Logger.getLogger("isFoundR").log(Level.SEVERE,"listaR empty exception :",e);
        }
        return found;
    }

    @Override
    public ObservableList<Raccolta> retrieveRaccoltaData()  {
        return retrieveData(this.fdR);
    }

    private static synchronized ObservableList<Raccolta> retrieveData(File fd)  {
        ObservableList<Raccolta> gList = FXCollections.observableArrayList();
        try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)))) {
            String[] gVector;


            while ((gVector = csvReader.readNext()) != null) {
                gList.add(getRivista(gVector));

            }
            if (gList.isEmpty()) {
                throw new IdException("lista rivista is empty");
            }


        }catch (IOException | CsvValidationException | IdException e){
            Logger.getLogger("retrieve riviste").log(Level.SEVERE,"retrieve error exception :",e);
        }

        return gList;

    }

    @Override
    public ObservableList<Rivista> getRiviste()  {
        ObservableList<Rivista> list = FXCollections.observableArrayList();
        synchronized (this.cacheRivista) {
            for (Map.Entry<Integer, Rivista> id : cacheRivista.entrySet()) {
                boolean recordT = !id.getValue().getTitolo().isEmpty();

                if (recordT)
                    list.add(id.getValue());
            }
        }
        if (list.isEmpty()) {
            list = retrieveRivista(this.fdR);
            synchronized (this.cacheRivista) {
                for (Rivista rivista : list)
                    this.cacheRivista.put(rivista.getId(), rivista);
            }

        }
        return list;
    }

    private static synchronized ObservableList<Rivista> retrieveRivista(File fdR){
        ObservableList<Rivista> rivistaList=FXCollections.observableArrayList();
        try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fdR)))) {
            String[] gVector;

            rivistaList = FXCollections.observableArrayList();
            while ((gVector = csvReader.readNext()) != null) {


                    rivistaList.add(getRivista(gVector));


            }
        }catch (IOException e){
            Logger.getLogger("listaRiviste io").log(Level.SEVERE,"listaRiviste io exception :",e);
        }catch (CsvValidationException e1){
            Logger.getLogger("listaRiviste csv").log(Level.SEVERE,"listaRiviste csv exception :",e1);

        }
        try{
        if (rivistaList.isEmpty()) {
            throw new IdException("rivista not found!!");
        }
        }catch (IdException e)
        {
            Logger.getLogger("idRivista ").log(Level.SEVERE,"id error magazine :",e);
        }

        return rivistaList;
    }

    @Override
    public ObservableList<Rivista> getRivistaByIdTitoloAutoreRivista(Rivista r) {
        ObservableList<Rivista> list = FXCollections.observableArrayList();
        synchronized (this.cacheRivista) {
            for (Map.Entry<Integer, Rivista> id : cacheRivista.entrySet()) {
                boolean recordT = id.getValue().getTitolo().equals(r.getTitolo());
                boolean recordA = id.getValue().getTitolo().equals(r.getEditore());
                boolean recordFound = recordT && recordA;

                if (recordFound)
                    list.add(id.getValue());
            }
        }
        if (list.isEmpty()) {
            list = retrieveRivistaByIdTitoloEditore(this.fdR, r);
            synchronized (this.cacheRivista) {
                for (Rivista rivista : list)
                    this.cacheRivista.put(r.getId(), rivista);
            }

        }
        return list;
    }

    private static synchronized ObservableList<Rivista> retrieveRivistaByIdTitoloEditore(File fd, Rivista rivista)  {
        ObservableList<Rivista> rivistaList=FXCollections.observableArrayList();
        try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)))) {
            String[] gVector;

            rivistaList = FXCollections.observableArrayList();
            while ((gVector = csvReader.readNext()) != null) {
                boolean recordFound = gVector[GETINDEXIDR].equals(String.valueOf(rivista.getId())) || gVector[GETINDEXIDR].equals(String.valueOf(vis.getIdRivista()))
                        || gVector[GETINDEXTITOLOR].equals(rivista.getTitolo()) || gVector[GETINDEXAUTORER].equals(rivista.getAutore());
                if (recordFound) {

                    rivistaList.add(getRivista(gVector));

                }
            }
        }catch (IOException |CsvValidationException e){
            Logger.getLogger("rivista by titolo id autore").log(Level.SEVERE,"wrong data exception :",e);
        }
        try {
            if (rivistaList.isEmpty()) {
                throw new IdException("rivista not found!!");
            }
        }catch (IdException e1)
        {
            Logger.getLogger("rivistaList").log(Level.SEVERE,"list magazine is empty :",e1);
        }

        return rivistaList;


    }


    @Override
    public void initializza() {
        try {
            File directory=new File("report");

            if(directory.isDirectory())            {
                String[] files = directory.list();

                assert files != null;
                if ( files.length == 0 || !this.fdR.exists()) {
                    throw new IOException("cartella vuota");
                }

            }


        } catch (IOException eFile) {

            Logger.getLogger("creazione db file").log(Level.INFO, "\n creating files ..");

            try {
                Files.copy(Path.of(RIVISTAP), Path.of(LOCATIONR), REPLACE_EXISTING);
            } catch (IOException e) {
                Logger.getLogger("inizializza rivista csv").log(Level.SEVERE,"error with copy :",e);
            }

            Logger.getLogger("crea db file").log(Level.SEVERE, "\n eccezione ottenuta nella modalità file.", eFile);
        }
    }
    private static void cleanUp(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
           Logger.getLogger("cleanupR").log(Level.SEVERE,"error with delete magazine file :",e);
        }
    }
}
