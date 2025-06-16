package laptop.database.primoucacquista.giornale;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.controller.ControllerSystemState;
import laptop.exception.IdException;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Raccolta;
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

public class CsvGiornale extends PersistenzaGiornale{

    private static final String LOCATIONG = "report/reportGiornale.csv";
    private static final int GETINDEXTITOLOG = 0;
    private static final int GETINDEXTIPOLOGIAG = 1;
    private static final int GETINDEXLINGUAG = 2;
    private static final int GETINDEXEDITOREG = 3;
    private static final int GETINDEXDATAG = 4;
    private static final int GETINDEXCOPIERG = 5;
    private static final int GETINDEXDISPG = 6;
    private static final int GETINDEXPREZZOG = 7;
    private static final int GETINDEXIDG = 8;

    private static final ControllerSystemState vis = ControllerSystemState.getInstance();


    private  final HashMap<Integer, Giornale> cacheGiornale;

    private final File fdG=new File(LOCATIONG);
    private static final String APPOGGIO="report/appoggio.csv";
    private static final String PERMESSI="rwx------";
    private static final String PREFIX="prefix";
    private static final String SUFFIX="suffix";
    private static final String GIORNALEP="src/main/resources/csvfiles/giornale.csv";


    public CsvGiornale()
    {
        this.cacheGiornale=new HashMap<>();
    }

    private static void cleanUp(Path path) throws IOException {
        Files.delete(path);
    }

    private static synchronized int getIdMax() throws IOException, CsvValidationException {
        //used for insert correct idOgg

        String []gVector;
        int max=0;


        try (CSVReader reader = new CSVReader(new FileReader(LOCATIONG))) {
            while ((gVector = reader.readNext()) != null) {
                if(Integer.parseInt(gVector[GETINDEXIDG])>max)
                    max= Integer.parseInt(gVector[GETINDEXIDG]);
            }
        }
        return max;




    }


    @Override
    public boolean inserisciGiornale(Giornale g) throws CsvValidationException, IOException{
        boolean duplicatedG=false;
        boolean duplicatedT=false;
        boolean duplicatedE=false;
        synchronized (this.cacheGiornale)
        {
            for(Map.Entry<Integer,Giornale>mapG:this.cacheGiornale.entrySet())
            {
                if(mapG.getValue().getTitolo()!=null)
                    duplicatedT=mapG.getValue().getTitolo()!=null;
                if(mapG.getValue().getEditore()!=null)
                    duplicatedE=mapG.getValue().getEditore()!=null;
                duplicatedG=duplicatedT&&duplicatedE;

            }

        }
        if(!duplicatedG)
        {
            List<Giornale> list=returnGiornaleByTE(this.fdG,g.getTitolo(),g.getEditore(),g.getId());
            duplicatedG=(!list.isEmpty());
        }
        if(duplicatedG)
            try{
                Logger.getLogger("try giornale").log(Level.INFO,"id giornale sbagliato !!");
                throw new IdException(" id giornale sbagliato or duplicated");
            }catch (IdException e)
            {
                Logger.getLogger("catch giornale").log(Level.SEVERE,"remove giornale...");
                //rimuovo e se lista vuota
                removeGiornaleById(g);
            }
        return inserimentoGiornale(this.fdG,g);
    }

    private static synchronized boolean inserimentoGiornale(File fd, Giornale g) throws IOException, CsvValidationException {
        try (CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(fd, true)))) {
            String[] gVector = new String[9];


            gVector[GETINDEXTITOLOG] = g.getTitolo();
            gVector[GETINDEXTIPOLOGIAG] = g.getCategoria();
            gVector[GETINDEXLINGUAG] = g.getLingua();
            gVector[GETINDEXEDITOREG] = g.getEditore();
            gVector[GETINDEXDATAG] = String.valueOf(g.getDataPubb());
            gVector[GETINDEXCOPIERG] = String.valueOf(g.getCopieRimanenti());
            gVector[GETINDEXDISPG] = String.valueOf(g.getDisponibilita());
            gVector[GETINDEXPREZZOG] = String.valueOf(g.getPrezzo());
            if(vis.getTipoModifica().equals("im")) gVector[GETINDEXIDG] = String.valueOf(vis.getIdGiornale());
            else if(vis.getTipoModifica().equals("insert"))gVector[GETINDEXIDG] = String.valueOf(getIdMax() + 1);
            else throw new CsvValidationException("type of modif in daily files is wrong !!");
            csvWriter.writeNext(gVector);
            csvWriter.flush();
        }
        return getIdMax()!=0;
    }


    private static synchronized List<Giornale> returnGiornaleByTE(File fd,String tit,String edit,int id) throws IOException, CsvValidationException {
        List<Giornale> list;
        try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)))) {
            String[] gVector;
            boolean recordFound;

            list = new ArrayList<>();

            while ((gVector = csvReader.readNext()) != null) {

                recordFound = gVector[GETINDEXTITOLOG].equals(tit) || gVector[GETINDEXEDITOREG].equals(edit) || gVector[GETINDEXIDG].equals(String.valueOf(id))
                        || gVector[GETINDEXIDG].equals(String.valueOf(vis.getIdGiornale()));

                if (recordFound) {

                    Giornale g = getGiornale(gVector);

                    list.add(g);
                }
            }
        }

        return list;

    }
    private static @NotNull Giornale getGiornale(String[] gVector) {
        String titolo = gVector[GETINDEXTITOLOG];
        String tipologia = gVector[GETINDEXTIPOLOGIAG];
        String lingua = gVector[GETINDEXLINGUAG];
        String ed = gVector[GETINDEXEDITOREG];
        String data = gVector[GETINDEXDATAG];
        String copie = gVector[GETINDEXCOPIERG];
        String disp = gVector[GETINDEXDISPG];
        String prezzo = gVector[GETINDEXPREZZOG];
        String id=gVector[GETINDEXIDG];

        Giornale g = new Giornale();

        g.setTitolo(titolo);
        g.setCategoria(tipologia);
        g.setLingua(lingua);
        g.setEditore(ed);
        g.setDataPubb(LocalDate.parse(data));
        g.setCopieRimanenti(Integer.parseInt(copie));
        g.setDisponibilita(Integer.parseInt(disp));
        g.setPrezzo(Float.parseFloat(prezzo));
        g.setId(Integer.parseInt(id));
        return g;
    }

    @Override
    public boolean removeGiornaleById(Giornale g) throws CsvValidationException, IOException {
        synchronized (this.cacheGiornale) {
            this.cacheGiornale.remove(g.getId(),g);
        }
        return removeGiornaleId(this.fdG, g);
    }
    private static synchronized boolean removeGiornaleId(File fd,Giornale g) throws IOException, CsvValidationException {
        return deleteByType(g,fd);

    }
    private static synchronized  boolean deleteByType(Giornale g,File fd) throws IOException, CsvValidationException {
        boolean status=false;
        if (SystemUtils.IS_OS_UNIX) {
            FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString(PERMESSI));
            Files.createTempFile(PREFIX, SUFFIX, attr); // Compliant
        }
        File tmpFile = new File(APPOGGIO);
        boolean found = isFound(g, fd, tmpFile);
        if (found) {
            Files.move(tmpFile.toPath(), fd.toPath(), REPLACE_EXISTING);
            status=true;

        } else {
            cleanUp(Path.of(tmpFile.toURI()));
        }
        return status;

    }

    private static boolean isFound(Giornale g, File fd, File tmpFile) throws IOException, CsvValidationException {
        boolean found = false;
        try (CSVReader reader = new CSVReader(new BufferedReader(new FileReader(fd)));
             CSVWriter writer = new CSVWriter(new BufferedWriter(new FileWriter(tmpFile, true))))
        {
            String[] gVector;
            boolean recordFound;
            while ((gVector = reader.readNext()) != null) {


                    recordFound = gVector[GETINDEXIDG].equals(String.valueOf(g.getId()))
                            || gVector[GETINDEXIDG].equals(String.valueOf(vis.getIdGiornale()))
                            || gVector[GETINDEXTITOLOG].equals(g.getTitolo());


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
    public ObservableList<Giornale> getGiornali() throws CsvValidationException, IOException, IdException {
        ObservableList<Giornale> list=FXCollections.observableArrayList();
        synchronized (this.cacheGiornale) {

            for (Map.Entry<Integer, Giornale> id : this.cacheGiornale.entrySet()) {


                boolean recordT = !id.getValue().getTitolo().isEmpty();

                if (recordT)
                    list.add(id.getValue());
            }



        }
        if(list.isEmpty())
        {
            list=retrieveGiornali(this.fdG);
            synchronized (this.cacheGiornale)
            {
                for(Giornale giornale : list)
                    this.cacheGiornale.put(giornale.getId(),giornale);
            }

        }
        return list;
    }

    private static synchronized ObservableList<Giornale> retrieveGiornali(File fdG) throws IOException, CsvValidationException, IdException {
        ObservableList<Giornale> list;
        try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fdG)))) {
            String[] gVector;
            list = FXCollections.observableArrayList();


            while ((gVector = csvReader.readNext()) != null) {
                list.add(getGiornale(gVector));
            }
        }
        if (list.isEmpty()) {
            throw new IdException("daily not found!!");
        }

        return list;
    }

    @Override
    public ObservableList<Raccolta> retrieveRaccoltaData() throws CsvValidationException, IOException, IdException {
        return retrieveData(this.fdG);
    }
    private static synchronized ObservableList<Raccolta> retrieveData(File fd) throws CsvValidationException, IOException, IdException {
        ObservableList<Raccolta> gList = FXCollections.observableArrayList();
        try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)))) {
            String[] gVector;


            while ((gVector = csvReader.readNext()) != null) {
                gList.add(getGiornale(gVector));

            }
            if (gList.isEmpty()) {
                throw new IdException("lista giornale is empty");
            }
        }

        return gList;

    }



    @Override
    public ObservableList<Giornale> getGiornaleByIdTitoloAutoreLibro(Giornale g) throws CsvValidationException, IOException, IdException {
        ObservableList<Giornale> list=FXCollections.observableArrayList();
        synchronized (this.cacheGiornale) {

            for (Map.Entry<Integer, Giornale> id : this.cacheGiornale.entrySet()) {


                boolean recordT = id.getValue().getTitolo().equals(g.getTitolo());
                boolean recordA = id.getValue().getTitolo().equals(g.getEditore());
                boolean recordFound = recordT && recordA;

                if (recordFound)
                    list.add(id.getValue());
            }



        }
        if(list.isEmpty())
        {
            list=retrieveGiornaleByIdTitoloEditore(this.fdG,g);
            synchronized (this.cacheGiornale)
            {
                for(Giornale giornale : list)
                    this.cacheGiornale.put(g.getId(),giornale);
            }

        }
        return list;

    }

    private static synchronized ObservableList<Giornale> retrieveGiornaleByIdTitoloEditore(File fd,Giornale giornale) throws IOException, CsvValidationException, IdException {
        ObservableList<Giornale> list;
        try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)))) {
            String[] gVector;

            list = FXCollections.observableArrayList();

            while ((gVector = csvReader.readNext()) != null) {

                boolean recordFound = gVector[GETINDEXIDG].equals(String.valueOf(giornale.getId())) || gVector[GETINDEXIDG].equals(String.valueOf(vis.getIdGiornale()))
                        || gVector[GETINDEXTITOLOG].equals(giornale.getTitolo()) || gVector[GETINDEXEDITOREG].equals(giornale.getEditore());
                if (recordFound) {


                    list.add(getGiornale(gVector));
                }
            }
        }
        if (list.isEmpty()) {
            throw new IdException("giornale not found!!");
        }


        return list;

    }
    @Override
    public void initializza() throws IOException {
        try {
            File directory=new File("report");


            if(directory.isDirectory())
            {
                String[] files = directory.list();


                assert files != null;
                if ( files.length == 0 || !this.fdG.exists()) {
                    throw new IOException("cartella vuota");
                }

            }


        } catch (IOException eFile) {

            Logger.getLogger("creazione db file").log(Level.INFO, "\n creating files ..");

            Files.copy(Path.of(GIORNALEP), Path.of(LOCATIONG), REPLACE_EXISTING);

            Logger.getLogger("crea db file").log(Level.SEVERE, "\n eccezione ottenuta nella modalità file.", eFile);
        }

    }


}
