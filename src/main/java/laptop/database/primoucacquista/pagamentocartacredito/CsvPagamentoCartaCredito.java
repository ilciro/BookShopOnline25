package laptop.database.primoucacquista.pagamentocartacredito;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.controller.ControllerSystemState;
import laptop.exception.IdException;
import laptop.model.pagamento.PagamentoCartaCredito;
import laptop.model.user.User;
import org.apache.commons.lang.SystemUtils;
import org.jetbrains.annotations.NotNull;

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

public class CsvPagamentoCartaCredito extends PersistenzaPagamentoCartaCredito{
    private static final int GETINDEXIDP=0;
    private static final int GETINDEXMETODOP=1;
    private static final int GETINDEXNOMEP=2;
    private static final int GETINDEXCOGNOMEP=3;
    private static final int GETINDEXSPESAP=4;
    private static final int GETINDEXEIAMILP=5;
    private static final int GETINDEXACQUISTOP=6;
    private static final int GETINDEXIDPRODOTTOP=7;
    private final File filePagamento;
    private final HashMap<String, PagamentoCartaCredito> cachePagamento;
    private static final String APPOGGIO="report/appoggio.csv";
    private static final String PERMESSI="rwx------";
    private static final String PREFIX="prefix";
    private static final String SUFFIX="suffix";
    private static final String PAGAMENTO="report/reportPagamentoCartaCredito.csv";
    private static final String IDWRONG="id wrong ..!!";
    private static final String IDERROR="id error !!..";

    private static final ControllerSystemState vis=ControllerSystemState.getInstance();

    public CsvPagamentoCartaCredito() throws IOException {
        this.filePagamento=new File(PAGAMENTO);
        if(!this.filePagamento.exists())
            Files.createFile(Path.of(this.filePagamento.toURI()));
        this.cachePagamento=new HashMap<>();

    }
    private static void cleanUp(Path path) throws IOException {
        Files.delete(path);
    }
    @Override
    public boolean inserisciPagamentoCartaCredito(PagamentoCartaCredito p) throws CsvValidationException, IOException {
        return creaPagamento(p);

    }

    @Override
    public PagamentoCartaCredito ultimoPagamentoCartaCredito() throws IOException, CsvValidationException {
        ObservableList<PagamentoCartaCredito> list;
        try(CSVReader reader=new CSVReader(new BufferedReader(new FileReader(this.filePagamento))))
        {
            list=FXCollections.observableArrayList();
            String[] gVector;
            while ((gVector=reader.readNext())!=null)
            {
                PagamentoCartaCredito pCC = getPagamentoCartaCredito(gVector);
                list.add(pCC);




            }
        }

        return list.get(list.size()-1);
    }

    private static @NotNull PagamentoCartaCredito getPagamentoCartaCredito(String[] gVector) {
        PagamentoCartaCredito pCC=new PagamentoCartaCredito();
        pCC.setIdPagCC(Integer.parseInt(gVector[GETINDEXIDP]));
        pCC.setMetodo(gVector[GETINDEXMETODOP]);
        pCC.setNomeUtente(gVector[GETINDEXNOMEP]);
        pCC.setCognomeUtente(gVector[GETINDEXCOGNOMEP]);
        pCC.setSpesaTotale(Float.parseFloat(gVector[GETINDEXSPESAP]));
        pCC.setEmail(gVector[GETINDEXEIAMILP]);
        pCC.setTipoAcquisto(gVector[GETINDEXACQUISTOP]);
        pCC.setIdProdotto(Integer.parseInt(gVector[GETINDEXIDPRODOTTOP]));
        return pCC;
    }

    private boolean creaPagamento(PagamentoCartaCredito p) throws IOException, CsvValidationException {
        boolean stauts=false;
        try (CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(this.filePagamento, true)))) {
            String[] gVectore = new String[8];
            //fare if su tipo pagamento

            gVectore[GETINDEXIDP] = String.valueOf(getIdMaxPagamento() + 1);
            gVectore[GETINDEXMETODOP] = p.getMetodo();
            gVectore[GETINDEXNOMEP] = p.getNomeUtente();
            gVectore[GETINDEXCOGNOMEP]=p.getCognomeUtente();
            gVectore[GETINDEXSPESAP] = String.valueOf(vis.getSpesaT());
            gVectore[GETINDEXEIAMILP] = User.getInstance().getEmail();
            gVectore[GETINDEXACQUISTOP] = p.getTipoAcquisto();
            gVectore[GETINDEXIDPRODOTTOP] = String.valueOf(p.getIdProdotto());

            csvWriter.writeNext(gVectore);
            csvWriter.flush();
            this.cachePagamento.put(String.valueOf(getIdMax()+1),p);
        }
        if (p.getNomeUtente()!=null) stauts=true;
        return stauts;

    }
    private static int getIdMaxPagamento() throws IOException, CsvValidationException {
        String[] gVector;
        int id = 0;


        try {



            try(CSVReader reader = new CSVReader(new FileReader(PAGAMENTO))) {
                while ((gVector = reader.readNext()) != null) {
                    id = Integer.parseInt(gVector[GETINDEXIDP]);
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




    private static @NotNull PagamentoCartaCredito getCartaCredito(String[] gVector) {
        PagamentoCartaCredito p = new PagamentoCartaCredito();
        p.setIdPagCC(Integer.parseInt(gVector[GETINDEXIDP]));
        p.setMetodo(gVector[GETINDEXMETODOP]);
        p.setNomeUtente(gVector[GETINDEXNOMEP]);
        p.setSpesaTotale(Float.parseFloat(gVector[GETINDEXSPESAP]));
        p.setEmail(gVector[GETINDEXEIAMILP]);
        p.setTipoAcquisto(gVector[GETINDEXACQUISTOP]);
        p.setIdProdotto(Integer.parseInt(gVector[GETINDEXIDPRODOTTOP]));
        return p;
    }


    @Override
    public boolean cancellaPagamentoCartaCredito(PagamentoCartaCredito p) throws IOException, CsvValidationException {
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

    private static boolean isFound(PagamentoCartaCredito p, File tmpFile) throws IOException, CsvValidationException {
        boolean found = false;

        try (CSVReader reader = new CSVReader(new BufferedReader(new FileReader(PAGAMENTO)))) {
            String[] gVector;
            try (CSVWriter writer = new CSVWriter(new BufferedWriter(new FileWriter(tmpFile, true)))
            ) {
                boolean recordFound;
                while ((gVector = reader.readNext()) != null) {

                    recordFound = gVector[GETINDEXIDP].equals(String.valueOf(p.getIdPagCC()))
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

    private static  int getIdMax() throws IOException, CsvValidationException {
        //used for insert correct idOgg
       return id();

    }


    private static synchronized int id() throws IOException, CsvValidationException {
        String[] gVector;
        int id = 0;


        try {



            try(CSVReader reader = new CSVReader(new FileReader(PAGAMENTO))) {
                while ((gVector = reader.readNext()) != null) {
                    id = Integer.parseInt(gVector[GETINDEXIDP]);
                }
            }






            if (id == 0)
                throw new IdException("id == 0 ");

        }catch (IdException | FileNotFoundException e)
        {

            Logger.getLogger(IDWRONG).log(Level.SEVERE, IDERROR);

        }

        return id;
    }

    /*

    @Override
    public ObservableList<PagamentoCartaCredito> listPagamentiByUser(PagamentoCartaCredito p) throws CsvValidationException, IOException {
        ObservableList<PagamentoCartaCredito> list= FXCollections.observableArrayList();
        synchronized (this.cachePagamento)
        {


            for(Map.Entry<String,PagamentoCartaCredito> id:cachePagamento.entrySet())
            {
                boolean recordFound=(id.getValue().getIdPagCC()==p.getIdPagCC());
                if(recordFound)
                    list.add(id.getValue());
            }
        }
        if(list.isEmpty())
        {
            list=retriveListPagamento(this.filePagamento,p);
            synchronized (this.cachePagamento)
            {
                for(PagamentoCartaCredito pagamento : list)
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

     */
}
