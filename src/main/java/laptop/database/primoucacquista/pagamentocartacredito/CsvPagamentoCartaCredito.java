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

    public CsvPagamentoCartaCredito() {
        this.filePagamento=new File(PAGAMENTO);
        if(!this.filePagamento.exists()) {
            try {
                Files.createFile(Path.of(this.filePagamento.toURI()));
            } catch (IOException e) {
                Logger.getLogger("costruttore").log(Level.SEVERE,"File not created {0}",e);
            }
        }
        this.cachePagamento=new HashMap<>();

    }
    private static void cleanUp(Path path) throws IOException {
        Files.delete(path);
    }
    @Override
    public boolean inserisciPagamentoCartaCredito(PagamentoCartaCredito p)  {
        return creaPagamento(p);

    }

    @Override
    public PagamentoCartaCredito ultimoPagamentoCartaCredito()  {
        ObservableList<PagamentoCartaCredito> list=FXCollections.observableArrayList();
        try(CSVReader reader=new CSVReader(new BufferedReader(new FileReader(this.filePagamento))))
        {
            list=FXCollections.observableArrayList();
            String[] gVector;
            while ((gVector=reader.readNext())!=null)
            {
                PagamentoCartaCredito pCC = getPagamentoCartaCredito(gVector);
                list.add(pCC);
            }
        }catch (IOException e){
            Logger.getLogger("ultimoPagamentoCC io").log(Level.SEVERE,"ultimoPagamento io exception {0}",e);
        }catch (CsvValidationException e1){
            Logger.getLogger("ultimoPagamento csv").log(Level.SEVERE,"ultimoPagamento csv exception {0}",e1);

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

    private boolean creaPagamento(PagamentoCartaCredito p) {
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
        }catch (IOException e)
        {
            Logger.getLogger("creaPagamento").log(Level.SEVERE,"makePayment exception {0}",e);
        }
        if (p.getNomeUtente()!=null) stauts=true;
        return stauts;

    }
    private static int getIdMaxPagamento()  {
        String[] gVector;
        int id = 0;


        try {



            try(CSVReader reader = new CSVReader(new FileReader(PAGAMENTO))) {
                while ((gVector = reader.readNext()) != null) {
                    id = Integer.parseInt(gVector[GETINDEXIDP]);
                }
            }catch (IOException e){
                Logger.getLogger("idPagamento").log(Level.SEVERE,"idPayment io exception {0}",e);
            }catch (CsvValidationException e1){
                Logger.getLogger("idPagamento csv").log(Level.SEVERE,"idPayment csv exception {0}",e1);

            }

            if (id == 0)
                throw new IdException("id == 0 ");

        }catch (IdException  e1)
        {

            Logger.getLogger(IDWRONG).log(Level.SEVERE, IDERROR+"{0}",e1);

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
    public boolean cancellaPagamentoCartaCredito(PagamentoCartaCredito p)  {
        boolean status = false;
        try {
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
        }catch (IOException e){
            Logger.getLogger("cancellaPagCC").log(Level.SEVERE,"delPaymentCC io exception {0}",e);
        }catch (CsvValidationException e1){
            Logger.getLogger("cancellaPagCC csv").log(Level.SEVERE,"delPaymentCC csv exception {0}",e1);

        }
        return status;

    }

    @Override
    public void inizializza()  {
        Path path = Path.of(PAGAMENTO);
        if(!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                Logger.getLogger("inizializza pagamentoCC csv").log(Level.SEVERE,"exception in initialize {0}",e);
            }
        }
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

    private static  int getIdMax(){
        //used for insert correct idOgg
       return id();

    }


    private static synchronized int id()  {
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

        }catch (IdException | IOException |CsvValidationException e)
        {

            Logger.getLogger(IDWRONG).log(Level.SEVERE, IDERROR);

        }

        return id;
    }



    @Override
    public ObservableList<PagamentoCartaCredito> listaPagamentiUserByCC(PagamentoCartaCredito pcc)  {
        ObservableList<PagamentoCartaCredito> list=FXCollections.observableArrayList();
        try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(filePagamento)))) {
            String[] gVector;
            list = FXCollections.observableArrayList();


            while ((gVector = csvReader.readNext()) != null) {


                boolean recordFound = gVector[GETINDEXEIAMILP].equals(String.valueOf(pcc.getEmail()));
                if (recordFound) {


                    list.add(getCartaCredito(gVector));

                }

            }
        }catch (IOException e){
            Logger.getLogger("listPagamento").log(Level.SEVERE,"listPayment io exception {0}",e);
        }catch (CsvValidationException e1){
            Logger.getLogger("listPagamento csv").log(Level.SEVERE,"listPayment csv exception {0}",e1);

        }
        try {
            if (list.isEmpty()) {
                throw new IdException("list pagamenti cc vuota!!");
            }
        }catch (IdException e)
        {
            Logger.getLogger("lista pagamentu by user cc").log(Level.SEVERE," list payment cc is empty!!");
        }


        return list;

    }
}
