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

    private static final String LOCATIONG="report/reportGiornale.csv";
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

    private static synchronized int getIdMax()  {
        //used for insert correct idOgg

        String []gVector;
        int max=0;


        try (CSVReader reader = new CSVReader(new FileReader(LOCATIONG))) {
            while ((gVector = reader.readNext()) != null) {
                if(Integer.parseInt(gVector[GETINDEXIDG])>max)
                    max= Integer.parseInt(gVector[GETINDEXIDG]);
            }
        }catch (IOException e){
            Logger.getLogger("id max io csv").log(Level.SEVERE,"id exception in io :{0}",e);
        }catch (CsvValidationException e1)
        {
            Logger.getLogger("id max  csv").log(Level.SEVERE,"id exception csv :{0}",e1);
        }
        return max;




    }


    @Override
    public boolean inserisciGiornale(Giornale g) {
        boolean status=false;
        try {
            boolean duplicatedG = false;
            boolean duplicatedT = false;
            boolean duplicatedE = false;
            synchronized (this.cacheGiornale) {
                for (Map.Entry<Integer, Giornale> mapG : this.cacheGiornale.entrySet()) {
                    if (mapG.getValue().getTitolo() != null)
                        duplicatedT = mapG.getValue().getTitolo() != null;
                    if (mapG.getValue().getEditore() != null)
                        duplicatedE = mapG.getValue().getEditore() != null;
                    duplicatedG = duplicatedT && duplicatedE;

                }

            }
            if (!duplicatedG) {
                List<Giornale> list = returnGiornaleByTE(this.fdG, g.getTitolo(), g.getEditore(), g.getId());
                duplicatedG = (!list.isEmpty());
            }
            if (duplicatedG)
                try {
                    Logger.getLogger("try giornale").log(Level.INFO, "id giornale sbagliato !!");
                    throw new IdException(" id giornale sbagliato or duplicated");
                } catch (IdException e) {
                    Logger.getLogger("catch giornale").log(Level.SEVERE, "remove giornale...");
                    //rimuovo e se lista vuota
                    removeGiornaleById(g);
                }

         status= inserimentoGiornale(this.fdG,g);

        }catch (IOException e){
            Logger.getLogger("inserisci giornale io csv").log(Level.SEVERE,"csv insert giornale io exception :{0}",e);

        }catch (CsvValidationException e1)
        {
            Logger.getLogger("inserisci giornale  csv").log(Level.SEVERE,"csv insert giornale csv exception :{0}",e1);

        }
        return status;
    }

    private static synchronized boolean inserimentoGiornale(File fd, Giornale g)  {
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
        }catch (IOException e)
        {
            Logger.getLogger("inserimento giornale csv").log(Level.SEVERE,"csv inserimento giornale io exception :{0}",e);

        }catch (CsvValidationException e1)
        {
            Logger.getLogger("inserimento giornale io csv").log(Level.SEVERE,"inserimento giornale csv exception :{0}",e1);

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
    public boolean removeGiornaleById(Giornale g)  {
        synchronized (this.cacheGiornale) {
            this.cacheGiornale.remove(g.getId(),g);
        }
        return removeGiornaleId(this.fdG, g);
    }
    private static synchronized boolean removeGiornaleId(File fd,Giornale g)  {
        return deleteByType(g,fd);

    }
    private static synchronized  boolean deleteByType(Giornale g,File fd)  {
        boolean status = false;
        try {

            if (SystemUtils.IS_OS_UNIX) {
                FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString(PERMESSI));
                Files.createTempFile(PREFIX, SUFFIX, attr); // Compliant
            }
            File tmpFile = new File(APPOGGIO);
            boolean found = isFound(g, fd, tmpFile);
            if (found) {
                Files.move(tmpFile.toPath(), fd.toPath(), REPLACE_EXISTING);
                status = true;

            } else {
                cleanUp(Path.of(tmpFile.toURI()));
            }
        }catch (IOException e){
            Logger.getLogger("delete by type io").log(Level.SEVERE,"id delete io exception :{0}",e);

        }

        return status;

    }

    private static boolean isFound(Giornale g, File fd, File tmpFile) {
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
        }catch (IOException e)
        {
            Logger.getLogger("isFound io").log(Level.SEVERE,"idFound io excpetion :{0}",e);
        }catch (CsvValidationException e1)
        {
            Logger.getLogger("isFound csv").log(Level.SEVERE,"idFound csv excpetion :{0}",e1);

        }
        return found;
    }

    @Override
    public ObservableList<Giornale> getGiornali()  {
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

    private static synchronized ObservableList<Giornale> retrieveGiornali(File fdG)  {
        ObservableList<Giornale> list = FXCollections.observableArrayList();
        try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fdG)))) {
            String[] gVector;
            list = FXCollections.observableArrayList();


            while ((gVector = csvReader.readNext()) != null) {
                list.add(getGiornale(gVector));
            }
        }catch (IOException e)
        {
            Logger.getLogger("retrieve giornali csv io").log(Level.SEVERE,"retrieve giornali io excpetion :{0}",e);
        }catch (CsvValidationException e1)
        {
            Logger.getLogger("retrieve giornali csv").log(Level.SEVERE,"retrieve giornali csv excpetion :{0}",e1);

        }
        try{
            if (list.isEmpty()) {
                throw new IdException("daily not found!!");
            }
        }catch (IdException e2){
            Logger.getLogger("retrieve giornali").log(Level.SEVERE,"list is empty !!  :{0}",e2);

        }

        return list;
    }

    @Override
    public ObservableList<Raccolta> retrieveRaccoltaData() {
        return retrieveData(this.fdG);
    }
    private static synchronized ObservableList<Raccolta> retrieveData(File fd) {
        ObservableList<Raccolta> gList = FXCollections.observableArrayList();
        try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)))) {
            String[] gVector;


            while ((gVector = csvReader.readNext()) != null) {
                gList.add(getGiornale(gVector));

            }
            if (gList.isEmpty()) {
                throw new IdException("lista giornale is empty");
            }
        }catch (IOException e){
            Logger.getLogger("retrieve data csv io").log(Level.SEVERE,"retrieve data io excpetion :{0}",e);
        }catch (IdException e1)
        {
            Logger.getLogger("retrieve data csv id").log(Level.SEVERE,"id exception :{0}",e1);
        }catch (CsvValidationException e2)
        {
            Logger.getLogger("retrieve data csv").log(Level.SEVERE,"retrieve data csv excpetion :{0}",e2);

        }

        return gList;

    }



    @Override
    public ObservableList<Giornale> getGiornaleByIdTitoloAutoreLibro(Giornale g) {
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

    private static synchronized ObservableList<Giornale> retrieveGiornaleByIdTitoloEditore(File fd,Giornale giornale)  {
        ObservableList<Giornale> list=FXCollections.observableArrayList();
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
        }catch (IOException e)
        {
            Logger.getLogger("giornale by id titolo autore io").log(Level.SEVERE,"giornale not found  :{0}",e);

        }catch (CsvValidationException e1)
        {
            Logger.getLogger("giornale by id titolo autore csv").log(Level.SEVERE,"giornale not found csv  :{0}",e1);

        }
        try {
            if (list.isEmpty()) {
                throw new IdException("giornale not found!!");
            }
        }catch (IdException e2)
        {
            Logger.getLogger("lista").log(Level.SEVERE,"lista is empty :{0}",e2);

        }


        return list;

    }
    @Override
    public void initializza()  {
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

            try {
                Files.copy(Path.of(GIORNALEP), Path.of(LOCATIONG), REPLACE_EXISTING);
            } catch (IOException e) {
                Logger.getLogger("inizializza csv giornale io").log(Level.SEVERE,"inizializza io in csv  :{0}",e);
            }

            Logger.getLogger("crea db file").log(Level.SEVERE, "\n eccezione ottenuta nella modalità file.", eFile);
        }

    }


}
