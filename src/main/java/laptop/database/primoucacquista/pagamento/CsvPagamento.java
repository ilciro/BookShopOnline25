package laptop.database.primoucacquista.pagamento;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.controller.ControllerSystemState;
import laptop.exception.IdException;
import laptop.model.Pagamento;
import laptop.model.user.User;
import org.apache.commons.lang.SystemUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class CsvPagamento extends PersistenzaPagamento{
    private static final int GETINDEXIDP=0;
    private static final int GETINDEXMETODOP=1;
    private static final int GETINDEXNOMEP=2;
    private static final int GETINDEXSPESAP=3;
    private static final int GETINDEXEIAMILP=4;
    private static final int GETINDEXACQUISTOP=5;
    private static final int GETINDEXIDPRODOTTOP=6;
    private final File filePagamento;
    private final HashMap<String,Pagamento> cachePagamento;
    private static final String APPOGGIO="report/appoggio.csv";
    private static final String PERMESSI="rwx------";
    private static final String PREFIX="prefix";
    private static final String SUFFIX="suffix";
    private static final String PAGAMENTO="report/reportPagamento.csv";
    private static final String IDWRONG="id wrong ..!!";
    private static final String IDERROR="id error !!..";

    private static final ControllerSystemState vis=ControllerSystemState.getInstance();

    public CsvPagamento() throws IOException {
        this.filePagamento=new File(PAGAMENTO);
        if(!this.filePagamento.exists())
            Files.createFile(Path.of(this.filePagamento.toURI()));
        this.cachePagamento=new HashMap<>();

    }
    private static void cleanUp(Path path) throws IOException {
        Files.delete(path);
    }
    @Override
    public boolean inserisciPagamento(Pagamento p) throws CsvValidationException, IOException {
        return creaPagamento(p);

    }
    private boolean creaPagamento(Pagamento p) throws IOException, CsvValidationException {
        boolean stauts=false;
        try (CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(this.filePagamento, true)))) {
            String[] gVectore = new String[7];
            //fare if su tipo pagamento

            gVectore[GETINDEXIDP] = String.valueOf(getIdMaxPagamento() + 1);
            gVectore[GETINDEXMETODOP] = p.getMetodo();
            gVectore[GETINDEXNOMEP] = p.getNomeUtente();
            gVectore[GETINDEXSPESAP] = String.valueOf(vis.getSpesaT());
            gVectore[GETINDEXEIAMILP] = User.getInstance().getEmail();
            gVectore[GETINDEXACQUISTOP] = p.getTipo();
            gVectore[GETINDEXIDPRODOTTOP] = String.valueOf(p.getIdOggetto());

            csvWriter.writeNext(gVectore);
            csvWriter.flush();
        }
        if (p.getNomeUtente()!=null) stauts=true;
        return stauts;

    }
    private static int getIdMaxPagamento() throws IOException, CsvValidationException {
        int id;
        try (CSVReader reader = new CSVReader(new BufferedReader(new FileReader(PAGAMENTO)))) {
            String[] gVector;
            id = 0;

            try {
                while ((gVector = reader.readNext()) != null) {
                    id = Integer.parseInt(gVector[GETINDEXIDP]);
                }
                if (id == 0)
                    throw new IdException(" id is 0!!");
            } catch (IdException e) {
                Logger.getLogger(IDWRONG).log(Level.SEVERE, IDERROR);

            }
        }
        return id;
    }


    @Override
    public Pagamento ultimoPagamento() throws CsvValidationException,IOException {
        ObservableList<Pagamento> list;
        try (CSVReader reader = new CSVReader(new BufferedReader(new FileReader(this.filePagamento)))) {
            list= FXCollections.observableArrayList();
            String[] gVector;

            while ((gVector = reader.readNext()) != null) {
                Pagamento p = new Pagamento();
                p.setIdPag(Integer.parseInt(gVector[GETINDEXIDP]));
                p.setMetodo(gVector[GETINDEXMETODOP]);
                p.setNomeUtente(gVector[GETINDEXNOMEP]);
                p.setAmmontare(Float.parseFloat(gVector[GETINDEXSPESAP]));
                p.setEmail(gVector[GETINDEXEIAMILP]);
                p.setTipo(gVector[GETINDEXACQUISTOP]);
                p.setIdOggetto(Integer.parseInt(gVector[GETINDEXIDPRODOTTOP]));
                list.add(p);


            }
        }

        return list.get(list.size()-1);
    }

    @Override
    public boolean cancellaPagamento(Pagamento p) throws IOException, CsvValidationException {
        boolean status = false;
        if (SystemUtils.IS_OS_UNIX) {
            FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString(PERMESSI));
            Files.createTempFile(PREFIX, SUFFIX, attr); // Compliant
        }
        File tmpFile = new File(APPOGGIO);
        boolean found = isFound(p, tmpFile);
        if (found) {
            Files.move(tmpFile.toPath(), Path.of(PAGAMENTO), REPLACE_EXISTING);
            status=true;
        } else {
            cleanUp(Path.of(tmpFile.toURI()));
        }
        return status;

    }

    @Override
    public void inizializza() throws IOException, ClassNotFoundException, SQLException {
        Path path = Path.of(PAGAMENTO);
        if(!Files.exists(path)) Files.createFile(path);
    }

    private static boolean isFound(Pagamento p, File tmpFile) throws IOException, CsvValidationException {
        boolean found = false;

        try (CSVReader reader = new CSVReader(new BufferedReader(new FileReader(PAGAMENTO)))) {
            String[] gVector;
            try (CSVWriter writer = new CSVWriter(new BufferedWriter(new FileWriter(tmpFile, true)))
            ) {
                boolean recordFound;
                while ((gVector = reader.readNext()) != null) {

                    recordFound = gVector[GETINDEXIDP].equals(String.valueOf(p.getIdPag()))
                            || gVector[GETINDEXEIAMILP].equals(p.getEmail());


                    if (!recordFound)
                        writer.writeNext(gVector);
                    else
                        found = true;
                }
                writer.flush();
            }
        }
        return found;
    }

    @Override
    public ObservableList<Pagamento> listPagamentiByUser(Pagamento p) throws CsvValidationException, IOException {
        ObservableList<Pagamento> list= FXCollections.observableArrayList();
        synchronized (this.cachePagamento)
        {


            for(Map.Entry<String,Pagamento> id:cachePagamento.entrySet())
            {
                boolean recordFound=(id.getValue().getIdPag()==p.getIdPag());
                if(recordFound)
                    list.add(id.getValue());
            }
        }
        if(list.isEmpty())
        {
            list=retriveListPagamento(this.filePagamento,p);
            synchronized (this.cachePagamento)
            {
                for(Pagamento pagamento : list)
                    this.cachePagamento.put(String.valueOf(p.getEmail()),pagamento);
            }

        }
        return list;
    }
    private static  synchronized  ObservableList<Pagamento> retriveListPagamento(File fd, Pagamento p) throws IOException, CsvValidationException {
        ObservableList<Pagamento> list;
        try (CSVReader reader = new CSVReader(new BufferedReader(new FileReader(fd)))) {
            String[] gVector;
            list = FXCollections.observableArrayList();
            while ((gVector = reader.readNext()) != null) {
                boolean recordFound = gVector[GETINDEXEIAMILP].equals(p.getEmail());
                if (recordFound) {
                    Pagamento pag = new Pagamento();
                    pag.setIdPag(Integer.parseInt(gVector[GETINDEXIDP]));
                    pag.setMetodo(gVector[GETINDEXMETODOP]);
                    pag.setAmmontare(Float.parseFloat(gVector[GETINDEXSPESAP]));
                    pag.setTipo(gVector[GETINDEXACQUISTOP]);
                    pag.setIdOggetto(Integer.parseInt(gVector[GETINDEXIDPRODOTTOP]));
                    list.add(pag);


                }
            }
        }

        return list;

    }
}
