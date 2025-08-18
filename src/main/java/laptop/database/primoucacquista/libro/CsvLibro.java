package laptop.database.primoucacquista.libro;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.controller.ControllerSystemState;
import laptop.exception.IdException;
import laptop.model.raccolta.Libro;
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

public class CsvLibro extends PersistenzaLibro{

    private static final String LOCATIONL = "report/reportLibro.csv";
    private static final int GETINDEXTITOLOL = 0;
    private static final int GETINDEXNRPL = 1;
    private static final int GETINDEXISBNL = 2;
    private static final int GETINDEXEDITOREL = 3;
    private static final int GETINDEXAUTOREL = 4;
    private static final int GETINDEXLINGUAL = 5;
    private static final int GETINDEXCATEGORIAL = 6;
    private static final int GETINDEXDATAL = 7;
    private static final int GETINDEXRECENSIONEL = 8;
    private static final int GETINDEXCOPIEL = 9;
    private static final int GETINDEXDESCL = 10;
    private static final int GETINDEXDISPL = 11;
    private static final int GETINDEXPREZZOL = 12;
    private static final int GETINDEXIDL = 13;

    private static final String LIBROP="src/main/resources/csvfiles/libro.csv";

    private static final ControllerSystemState vis = ControllerSystemState.getInstance();


    private final HashMap<Integer, Libro> cacheLibro;

    private final File fdL=new File(LOCATIONL);
    private static final String APPOGGIO="report/appoggio.csv";
    private static final String PERMESSI="rwx------";
    private static final String PREFIX="prefix";
    private static final String SUFFIX="suffix";

    public CsvLibro()  {
        this.cacheLibro = new HashMap<>();
       

    }




    @Override
   public boolean inserisciLibro(Libro l)  {
        //provo con titolo ed autore ed editore
        //visto che id non buono in quanto non gli e lo assegno

        boolean duplicatedL = false;
        boolean duplicatedT = false;
        boolean duplicatedA = false;
        boolean duplicatedE = false;
        synchronized (this.cacheLibro)
        {



            for(Map.Entry<Integer,Libro>mapL:this.cacheLibro.entrySet())
            {
                if(mapL.getValue().getTitolo()!=null)
                    duplicatedT=mapL.getValue().getTitolo()!=null;
                if(mapL.getValue().getAutore()!=null)
                    duplicatedA=mapL.getValue().getAutore()!=null;
                if(mapL.getValue().getEditore()!=null)
                    duplicatedE=mapL.getValue().getEditore()!=null;
                duplicatedL=duplicatedT&&duplicatedA&&duplicatedE;

            }



        }
        if(!duplicatedL)
        {
            List<Libro> list=returnLibriByTAE(this.fdL,l.getTitolo(),l.getAutore(),l.getEditore(),l.getId());
            duplicatedL=(!list.isEmpty());
        }
        if(duplicatedL)
        {
            try{
                Logger.getLogger("try libro").log(Level.INFO,"id sbagliato !!");
                throw new IdException(" id libro sbagliato or duplicated");
            }catch (IdException e)
            {
                Logger.getLogger("catch libro").log(Level.SEVERE,"remove libro...");
                //rimuovo e se lista vuota
                removeLibroById(l);
            }
        }
        return inserimentoLibro(this.fdL,l);


    }
    private static synchronized boolean inserimentoLibro(File fd, Libro l)  {

        try (CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(fd, true)))) {
            String[] gVector = new String[14];


            gVector[GETINDEXTITOLOL] = l.getTitolo();
            gVector[GETINDEXNRPL] = String.valueOf(l.getNrPagine());
            gVector[GETINDEXISBNL] = l.getCodIsbn();
            gVector[GETINDEXEDITOREL] = l.getEditore();
            gVector[GETINDEXAUTOREL] = l.getAutore();
            gVector[GETINDEXLINGUAL] = l.getLingua();
            gVector[GETINDEXCATEGORIAL] = l.getCategoria();
            gVector[GETINDEXDATAL] = String.valueOf(l.getDataPubb());
            gVector[GETINDEXRECENSIONEL] = l.getRecensione();
            gVector[GETINDEXCOPIEL] = String.valueOf(l.getNrCopie());
            gVector[GETINDEXDESCL] = l.getDesc();
            gVector[GETINDEXDISPL] = String.valueOf(l.getDisponibilita());
            gVector[GETINDEXPREZZOL] = String.valueOf(l.getPrezzo());
            if(vis.getTipoModifica().equals("im")) gVector[GETINDEXIDL] = String.valueOf(vis.getIdLibro());
            else if (vis.getTipoModifica().equals("insert"))gVector[GETINDEXIDL] = String.valueOf(getIdMax() + 1);
            csvWriter.writeNext(gVector);
            csvWriter.flush();
        }catch (IOException e)
        {
            Logger.getLogger("inserisci libro csv").log(Level.SEVERE,"insert book csv exception :",e);
        }

        return getIdMax()!=0;

    }
    private static synchronized List<Libro> returnLibriByTAE(File fd,String tit,String aut,String edit,int id)  {
        String[] gVector;
        List<Libro> list=new ArrayList<>();
        boolean recordFound;
        try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)))) {

            while ((gVector = csvReader.readNext()) != null) {

                recordFound = gVector[GETINDEXTITOLOL].equals(tit) || gVector[GETINDEXAUTOREL].equals(aut) || gVector[GETINDEXEDITOREL].equals(edit)
                        || gVector[GETINDEXIDL].equals(String.valueOf(id)) || gVector[GETINDEXIDL].equals(String.valueOf(vis.getIdLibro()));

                if (recordFound) {

                    Libro l = getLibro(gVector);
                    list.add(l);

                }
            }
        }catch (IOException e){
            Logger.getLogger("return libro").log(Level.SEVERE,"return libro io exception !! :",e);
        }catch (CsvValidationException e1){
            Logger.getLogger("return libro list").log(Level.SEVERE,"list is empty !! :",e1);
        }

        return list;


    }

    private static @NotNull Libro getLibro(String[] gVector)
    {
        String titolo = gVector[GETINDEXTITOLOL];
        String numeroPagine = gVector[GETINDEXNRPL];
        String isbn = gVector[GETINDEXISBNL];
        String editore = gVector[GETINDEXEDITOREL];
        String autore = gVector[GETINDEXAUTOREL];
        String lingua = gVector[GETINDEXLINGUAL];
        String categoria = gVector[GETINDEXCATEGORIAL];
        String data = gVector[GETINDEXDATAL];
        String recensione = gVector[GETINDEXRECENSIONEL];
        String copie = gVector[GETINDEXCOPIEL];
        String desc = gVector[GETINDEXDESCL];
        String disp = gVector[GETINDEXDISPL];
        String prezzo = gVector[GETINDEXPREZZOL];
        String idL=gVector[GETINDEXIDL];



        Libro l=new Libro();

        l.setTitolo(titolo);
        l.setNrPagine(Integer.parseInt(numeroPagine));
        l.setCodIsbn(isbn);
        l.setEditore(editore);
        l.setAutore(autore);
        l.setLingua(lingua);
        l.setCategoria(categoria);
        l.setDataPubb(LocalDate.parse(data));
        l.setRecensione(recensione);
        l.setNrCopie(Integer.parseInt(copie));
        l.setDesc(desc);
        l.setDisponibilita(Integer.parseInt(disp));
        l.setPrezzo(Float.parseFloat(prezzo));
        l.setId(Integer.parseInt(idL));

        return l;
    }

    private static synchronized int getIdMax() {
        //used for insert correct idOgg

        String []gVector;
        int max=0;
        
    
        try (CSVReader reader = new CSVReader(new FileReader(LOCATIONL))) {
            while ((gVector = reader.readNext()) != null) {
                if (Integer.parseInt(gVector[GETINDEXIDL]) > max)
                    max = Integer.parseInt(gVector[GETINDEXIDL]);
            }
        }catch (IOException e)
        {
            Logger.getLogger("id max").log(Level.SEVERE,"id error file:",e);
        }catch (CsvValidationException e1)
        {
            Logger.getLogger("id max csv").log(Level.SEVERE,"id is null :",e1);
        }
        return max;
    }

    @Override
    public boolean removeLibroById(Libro l)  {
        synchronized (this.cacheLibro) {
            this.cacheLibro.remove(l.getId());
        }
        return removeLibroId(this.fdL, l);
        
    }



    private static synchronized boolean removeLibroId(File fd, Libro l)  {
        return deleteByType(l,fd);
    }
    private static synchronized  boolean deleteByType( Libro l, File fd)  {
        boolean status=false;
        try {
            if (SystemUtils.IS_OS_UNIX) {
                FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString(PERMESSI));
                Files.createTempFile(PREFIX, SUFFIX, attr); // Compliant
            }
            File tmpFile = new File(APPOGGIO);
            boolean found = isFound(l, fd, tmpFile);
            if (found) {
                Files.move(tmpFile.toPath(), fd.toPath(), REPLACE_EXISTING);
                status = true;

            } else {
                cleanUp(Path.of(tmpFile.toURI()));
            }
        }catch (IOException e){
            Logger.getLogger("delete libro").log(Level.SEVERE,"error with delete book :",e);
        }
        return status;

    }

    private static boolean isFound(Libro l, File fd, File tmpFile) {
        boolean found = false;
        try (CSVReader reader = new CSVReader(new BufferedReader(new FileReader(fd)));
             CSVWriter writer = new CSVWriter(new BufferedWriter(new FileWriter(tmpFile, true))))
        {
            String[] gVector;
            boolean recordFound;
            while ((gVector = reader.readNext()) != null) {

                 recordFound = gVector[GETINDEXIDL].equals(String.valueOf(l.getId())) ||
                            gVector[GETINDEXTITOLOL].equals(l.getTitolo()) ||
                            gVector[GETINDEXIDL].equals(String.valueOf(vis.getIdLibro()));


                if (!recordFound)
                    writer.writeNext(gVector);
                else
                    found = true;
            }
            writer.flush();
        }catch (IOException e)
        {
            Logger.getLogger("isFound book").log(Level.SEVERE,"id book file exception :",e);
        }catch (CsvValidationException e1)
        {
            Logger.getLogger("idFound csv libro").log(Level.SEVERE," id book csv error :",e1);
        }
        return found;
    }


    @Override
    public ObservableList<Raccolta> retrieveRaccoltaData() {
        return retrieveData(this.fdL);
    }
    private static synchronized ObservableList<Raccolta> retrieveData(File fd) {
        ObservableList<Raccolta> gList = FXCollections.observableArrayList();
        try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)))) {
            String[] gVector;
                    while ((gVector = csvReader.readNext()) != null) {

                        gList.add(getLibro(gVector));

                    }

        }catch (IOException e){
            Logger.getLogger("retrieveRaccoltaData libro").log(Level.SEVERE,"error with file :",e);
        }catch (CsvValidationException e1) {
            Logger.getLogger("retrieveRaccoltaData libro csv").log(Level.SEVERE, "error with csv :", e1);
        }
        try{
        if (gList.isEmpty()) {
            throw new IdException("list libro is empty");
        }
        }catch (IdException e2){
            Logger.getLogger("retrieveRaccoltaData idLibro").log(Level.SEVERE,"error with idLibro :",e2);
        }

        return gList;

    }



    @Override
    public ObservableList<Libro> getLibroByIdTitoloAutoreLibro(Libro l)  {
        ObservableList<Libro> list=FXCollections.observableArrayList();
        synchronized (this.cacheLibro)
        {
            for(Map.Entry<Integer, Libro> id:this.cacheLibro.entrySet())
            {

                boolean recordT =id.getValue().getTitolo().equals(l.getTitolo());
                boolean recordA = id.getValue().getTitolo().equals(l.getAutore());
                boolean recordFound = recordT && recordA;


                if (recordFound)
                    list.add(id.getValue());

            }
        }
        if(list.isEmpty())
        {
            list=retrieveLibroByIdAutoreTitolo(this.fdL,l);
            synchronized (this.cacheLibro)
            {
                for(Libro libro : list)
                    this.cacheLibro.put(l.getId(),libro);
            }

        }
        return list;
    }
    private static synchronized ObservableList<Libro> retrieveLibroByIdAutoreTitolo(File fd,Libro libro)  {
        ObservableList<Libro> list=FXCollections.observableArrayList();
        try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)))) {
            String[] gVector;
            list = FXCollections.observableArrayList();


            while ((gVector = csvReader.readNext()) != null) {


                boolean recordFound = gVector[GETINDEXIDL].equals(String.valueOf(libro.getId())) || gVector[GETINDEXIDL].equals(String.valueOf(vis.getIdLibro()))
                        || gVector[GETINDEXTITOLOL].equals(libro.getTitolo()) || gVector[GETINDEXAUTOREL].equals(libro.getAutore())
                        || gVector[GETINDEXEDITOREL].equals(libro.getEditore());
                if (recordFound) {


                    list.add(getLibro(gVector));

                }

            }
        }catch (IOException e)
        {
            Logger.getLogger("retrieveLibro").log(Level.SEVERE,"file book error :",e);
        }catch (CsvValidationException e1)
        {
            Logger.getLogger("retriveLibro csv").log(Level.SEVERE,"error with csvLibroFile :",e1);
        }
        try {
        if (list.isEmpty()) {
            throw new IdException("book not found!!");
            }
        } catch (IdException e) {
            Logger.getLogger("libro non ha id").log(Level.SEVERE,"id book is null :",e);
        }

        return list;

    }

    @Override
    public ObservableList<Libro> getLibri()  {
        ObservableList<Libro> list=FXCollections.observableArrayList();
        synchronized (this.cacheLibro)
        {
            for(Map.Entry<Integer, Libro> id:this.cacheLibro.entrySet())
            {

                boolean recordT = !id.getValue().getTitolo().isEmpty();



                if (recordT)
                    list.add(id.getValue());

            }
        }
        if(list.isEmpty())
        {
            list=retrieveLibro(this.fdL);
            synchronized (this.cacheLibro)
            {
                for(Libro libro : list)
                    this.cacheLibro.put(libro.getId(),libro);
            }

        }
        return list;
    }

    private static synchronized ObservableList<Libro> retrieveLibro(File fdL) {
        ObservableList<Libro> list=FXCollections.observableArrayList();
        try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fdL)))) {
            String[] gVector;
            list = FXCollections.observableArrayList();


            while ((gVector = csvReader.readNext()) != null) {
                    list.add(getLibro(gVector));
            }
        }catch (IOException e){
            Logger.getLogger("retrieveLibro io").log(Level.SEVERE,"io exception :",e);
        }catch (CsvValidationException e1){
            Logger.getLogger("retrieveLibro csv").log(Level.SEVERE,"csv exception :",e1);

        }
        try{
            if (list.isEmpty()) {
                throw new IdException("book not found!!");
            }
        }catch (IdException e2){
            Logger.getLogger("retrieveLibro id").log(Level.SEVERE,"id exception :",e2);

        }

        return list;
    }

    @Override
    public void initializza() {
        try {
            File directory=new File("report");


            if(directory.isDirectory())
            {
                String[] files = directory.list();


                assert files != null;
                if ( files.length == 0 || !this.fdL.exists())
                    throw new IOException("cartella vuota");

            }


        } catch (IOException eFile) {

            Logger.getLogger("creazione db file").log(Level.INFO, "\n creating files ..");

            try {
                Files.copy(Path.of(LIBROP), Path.of(LOCATIONL), REPLACE_EXISTING);
            } catch (IOException e) {
                Logger.getLogger("inizializza libro csv").log(Level.SEVERE,"io exception initialize :",e);
            }

            Logger.getLogger("crea db file").log(Level.SEVERE, "\n eccezione ottenuta nella modalità file.", eFile);
        }
        Logger.getLogger("files creati con successo").log(Level.INFO, "\n files successfully created");



    }

    private static void cleanUp(Path path) throws IOException {
        Files.delete(path);
    }
}
