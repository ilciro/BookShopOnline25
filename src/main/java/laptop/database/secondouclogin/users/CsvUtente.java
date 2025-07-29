package laptop.database.secondouclogin.users;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.controller.ControllerSystemState;
import laptop.exception.IdException;
import laptop.model.user.TempUser;
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

public class CsvUtente extends PersistenzaUtente {

    private static final int GETINDEXIDUSER = 0;
    private static final int GETINDEXRUOLO = 1;
    private static final int GETINDEXNOME = 2;
    private static final int GETINDEXCOGNOME = 3;
    private static final int GETINDEXEMAIL = 4;
    private static final int GETINDEXPWD = 5;
    private static final int GETINDEXDESC = 6;
    private static final int GETINDEXDATAN = 7;
    private static final String LOCATIONU = "report/reportUtente.csv";
    private final HashMap<String, TempUser> cacheU;
    private final File fdU = new File(LOCATIONU);

    private static final String UTENTEP="src/main/resources/csvfiles/utente.csv";
    private static final ControllerSystemState vis= ControllerSystemState.getInstance();


    public CsvUtente() {
        this.cacheU = new HashMap<>();
    }

    private static void cleanUp(Path path) throws IOException {
        Files.delete(path);
    }



     @Override
    public boolean inserisciUtente(TempUser tu) throws IOException, CsvValidationException, IdException {

        boolean duplicated;
        synchronized (this.cacheU) {
            boolean duplicatedM = (this.cacheU.get(tu.getEmailT()) != null);
            boolean duplicatedP = (this.cacheU.get(tu.getPasswordT()) != null);
            duplicated = duplicatedM && duplicatedP;

        }
        if (!duplicated) {
            List<TempUser> list = getUserData(this.fdU, tu.getId(), tu.getEmailT(), tu.getPasswordT());
            duplicated = (!list.isEmpty());

        }
        if (duplicated) {
            try {
                Logger.getLogger("try user").log(Level.INFO, "id sbagliato !!");
                throw new IdException(" id user sbagliato or duplicated");

            } catch (IdException e) {
                Logger.getLogger("catch utente").log(Level.SEVERE, "remove utente...");
                //rimuovo e se lista vuota
                removeUserByIdEmailPwd(tu);
            }
        }

        return insertUser(tu);


    }




    @Override
    public void initializza() throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        try {
            File directory=new File("report");


            if(directory.isDirectory())
            {
                String[] files = directory.list();


                assert files != null;
                if ( files.length == 0 || !this.fdU.exists()) {
                    throw new IOException("cartella vuota");
                }




            }


        } catch (IOException eFile) {

            Logger.getLogger("creazione db file").log(Level.INFO, "\n creating files ..");

            Files.copy(Path.of(UTENTEP), Path.of(LOCATIONU), REPLACE_EXISTING);

            Logger.getLogger("crea db file").log(Level.SEVERE, "\n eccezione ottenuta nella modalità file.", eFile);
        }
    }


    @Override
    public boolean removeUserByIdEmailPwd(TempUser tu) throws CsvValidationException, IOException {
        synchronized (this.cacheU) {
            this.cacheU.remove(tu.getEmailT());
        }
        return cancellaUserById(this.fdU, tu);
    }


    private static synchronized boolean insertUser(TempUser u) throws IOException, CsvValidationException {
        try (CSVWriter writer = new CSVWriter(new BufferedWriter(new FileWriter(LOCATIONU, true)))) {
            String[] gVector = new String[8];

            if(vis.getTipoModifica().equals("im")) gVector[GETINDEXIDUSER] = String.valueOf(u.getId());
            else if(vis.getTipoModifica().equals("insert")) gVector[GETINDEXIDUSER] = String.valueOf(getIdMax() + 1);
            gVector[GETINDEXRUOLO] = u.getIdRuoloT();
            gVector[GETINDEXNOME] = u.getNomeT();
            gVector[GETINDEXCOGNOME] = u.getCognomeT();
            gVector[GETINDEXEMAIL] = u.getEmailT();
            gVector[GETINDEXPWD] = u.getPasswordT();
            gVector[GETINDEXDESC] = u.getDescrizioneT();
            gVector[GETINDEXDATAN] = String.valueOf(u.getDataDiNascitaT());
            writer.writeNext(gVector);
            writer.flush();

        }

        return getIdMax() != 0;


    }

    private static synchronized List<TempUser> getUserData(File fd, int id, String mail, String pass) throws IOException, CsvValidationException {
        List<TempUser> list;
        try (CSVReader reader = new CSVReader(new BufferedReader(new FileReader(fd)))) {
            String[] gVector;
            boolean recordFound;
            list = new ArrayList<>();


            while ((gVector = reader.readNext()) != null) {


                recordFound = gVector[GETINDEXIDUSER].equals(String.valueOf(id)) || gVector[GETINDEXEMAIL].equals(mail) || gVector[GETINDEXPWD].equals(pass);

                if (recordFound) {

                    TempUser tu = getTempUser(gVector);




                    list.add(tu);
                }

            }


        }
        return list;
    }



    private static synchronized int getIdMax() throws IOException, CsvValidationException {
        //used for insert correct idOgg
        int id;
        try (CSVReader reader = new CSVReader(new FileReader(LOCATIONU))) {
            String[] gVector;
            id = 0;
            while ((gVector = reader.readNext()) != null)
                id = Integer.parseInt(gVector[GETINDEXIDUSER]);
        }

        return id;

    }

    private static synchronized boolean cancellaUserById(File fd, TempUser u1) throws IOException, CsvValidationException {
        boolean status = false;
        if (SystemUtils.IS_OS_UNIX) {
            FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwx------"));
            Files.createTempFile("prefix", "suffix", attr); // Compliant
        }
        File tmpFD = new File("report/appoggioUser.csv");
        boolean found = isFound(fd, u1, tmpFD);
        if (found) {
            Files.move(tmpFD.toPath(), fd.toPath(), REPLACE_EXISTING);
            status = true;
        } else {
            cleanUp(Path.of(tmpFD.toURI()));
        }
        return status;

    }

    private static boolean isFound(File fd, TempUser u1, File tmpFD) throws IOException, CsvValidationException {
        boolean found = false;
        // create csvReader object passing file reader as a parameter
        try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)));
             CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(tmpFD, true)));
            ) {
            String[] giornaleVector;
            //check on path
            boolean userVectorFound;


            while ((giornaleVector = csvReader.readNext()) != null) {

                userVectorFound = giornaleVector[GETINDEXIDUSER].equals(String.valueOf(u1.getId())) || giornaleVector[GETINDEXEMAIL].equals(u1.getEmailT());

                if (!userVectorFound) {
                    csvWriter.writeNext(giornaleVector);
                } else {
                    found = userVectorFound;
                }
            }


            csvWriter.flush();
        }
        return found;
    }


@Override
    public synchronized ObservableList<TempUser> getUserData() throws IOException, CsvValidationException {
        ObservableList<TempUser> list;
        try (CSVReader reader = new CSVReader(new BufferedReader(new FileReader(this.fdU)))) {
            String[] gVector;

            list = FXCollections.observableArrayList();


            while ((gVector = reader.readNext()) != null) {

                TempUser tu = getTempUser(gVector);


                list.add(tu);


            }


        }
        return list;
    }

    private static @NotNull TempUser getTempUser(String[] gVector) {
        TempUser tu = new TempUser();

        tu.setId(Integer.parseInt(gVector[GETINDEXIDUSER]));
        tu.setIdRuoloT(gVector[GETINDEXRUOLO]);
        tu.setNomeT(gVector[GETINDEXNOME]);
        tu.setCognomeT(gVector[GETINDEXCOGNOME]);
        tu.setEmailT(gVector[GETINDEXEMAIL]);
        tu.setPasswordT(gVector[GETINDEXPWD]);
        tu.setDescrizioneT(gVector[GETINDEXDESC]);
        tu.setDataDiNascitaT(LocalDate.parse(gVector[GETINDEXDATAN]));
        return tu;
    }


}
