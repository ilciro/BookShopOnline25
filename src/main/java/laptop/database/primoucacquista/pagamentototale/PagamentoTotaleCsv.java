package laptop.database.primoucacquista.pagamentototale;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerSystemState;
import laptop.database.primoucacquista.pagamentocartacredito.CsvPagamentoCartaCredito;
import laptop.database.primoucacquista.pagamentofattura.CsvFattura;
import laptop.exception.IdException;
import laptop.model.pagamento.Pagamento;
import laptop.model.pagamento.PagamentoCartaCredito;
import laptop.model.pagamento.PagamentoFattura;
import org.apache.commons.lang.SystemUtils;

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

public class PagamentoTotaleCsv extends PagamentoTotale {

    private static final String PAGAMENTOTOTALE="report/reportPagamentoTotale.csv";
    private static final String APPOGGIO="report/appoggioPagamentoTotale.csv";
    private static final String PREFIX="prefix";
    private static final String SUFFIX="suffix";

    private final HashMap<String, Pagamento> cachePagamentoTotale;
    private File file;
    private static final ControllerSystemState vis=ControllerSystemState.getInstance();
    private CsvFattura csvFattura;
    private CsvPagamentoCartaCredito csvPagamentoCartaCredito;

    private static final int NOMEUTENTE=0;
    private static final int COGNOMEUTENTE=1;
    private static final int VIA=2;
    private static final int COM=3;
    private static final int AMMONTARE=4;
    private static final int EMAIL=5;
    private static final int TIPOACQUISTO=6;
    private static final int IDFATTURA=7;
    private static final int IDPAGAMENTOCC=8;
    private static final int IDPAGAMENTO=9;
    private static final String PERMESSI="rwx------";

    @Override
    public boolean cancellaFattura(PagamentoFattura p) throws IOException, CsvValidationException {
        synchronized (this.cachePagamentoTotale) {
            this.cachePagamentoTotale.remove(String.valueOf(p.getIdFattura()));
        }
       return removeFattura(p);
    }
    private static void cleanUp(Path path) throws IOException {
        Files.delete(path);
    }


    private static synchronized boolean removeFattura(PagamentoFattura p) throws IOException, CsvValidationException {
        boolean status=false;
        if (SystemUtils.IS_OS_UNIX) {
            FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString(PERMESSI));
            Files.createTempFile(PREFIX, SUFFIX, attr); // Compliant
        }
        File tmpFile = new File(APPOGGIO);
        boolean found = isFound(p, tmpFile);
        if (found) {
            Files.move(tmpFile.toPath(), Path.of(PAGAMENTOTOTALE), REPLACE_EXISTING);
            status=true;
        } else {
            cleanUp(Path.of(tmpFile.toURI()));
        }
        return status;
    }



    private static boolean isFound(PagamentoFattura f, File tmpFile) throws IOException, CsvValidationException {
        boolean found = false;
        try (CSVReader reader = new CSVReader(new BufferedReader(new FileReader(PAGAMENTOTOTALE)));
             CSVWriter writer= new CSVWriter(new BufferedWriter(new FileWriter(tmpFile, true)))) {
            String[] gVector;

            boolean recordFound;
            while ((gVector = reader.readNext()) != null) {

                recordFound = gVector[IDFATTURA].equals(String.valueOf(f.getIdFattura()));


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
    public boolean cancellaPagamentoCC(PagamentoCartaCredito pCC) throws IOException, ClassNotFoundException, CsvValidationException {
        synchronized (this.cachePagamentoTotale) {
            this.cachePagamentoTotale.remove(String.valueOf(pCC.getIdPagCC()));
        }
        return removePagamentoCC(pCC);
    }

    private static synchronized boolean removePagamentoCC(PagamentoCartaCredito pCC) throws IOException, CsvValidationException {
        boolean status=false;
        if (SystemUtils.IS_OS_UNIX) {
            FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString(PERMESSI));
            Files.createTempFile(PREFIX, SUFFIX, attr); // Compliant
        }
        File tmpFile = new File(APPOGGIO);
        boolean found = isFoundCartaCredito(pCC, tmpFile);
        if (found) {
            Files.move(tmpFile.toPath(), Path.of(PAGAMENTOTOTALE), REPLACE_EXISTING);
            status=true;
        } else {
            cleanUp(Path.of(tmpFile.toURI()));
        }
        return status;
    }
    private static boolean isFoundCartaCredito(PagamentoCartaCredito pCC, File tmpFile) throws IOException, CsvValidationException {
        boolean found = false;
        try (CSVReader reader = new CSVReader(new BufferedReader(new FileReader(PAGAMENTOTOTALE)));
             CSVWriter writer= new CSVWriter(new BufferedWriter(new FileWriter(tmpFile, true)))) {
            String[] gVector;

            boolean recordFound;
            while ((gVector = reader.readNext()) != null) {

                recordFound = gVector[IDPAGAMENTOCC].equals(String.valueOf(pCC.getIdPagCC()));


                if (!recordFound)
                    writer.writeNext(gVector);
                else
                    found = true;
            }
            writer.flush();

        }
        return found;
    }

    private static final int IDPAGAMENTOFATTURA=7;
    private static final int IDOGGETTO=10;

    private static final String IDWRONG="id wrong ..!!";
    private static final String IDERROR="id error !!..";












    public PagamentoTotaleCsv() throws IOException {
        this.file=new File(PAGAMENTOTOTALE);
        if(!this.file.exists())

            Files.createFile(Path.of(this.file.toURI()));
        this.cachePagamentoTotale=new HashMap<>();

        //richiamo entrambi i sottopagamenti dao
        if(vis.getMetodoP().equals("cash")) csvFattura=new CsvFattura();
        else csvPagamentoCartaCredito=new CsvPagamentoCartaCredito();


    }

    @Override
    public boolean inserisciPagamentoFattura(PagamentoFattura p) throws CsvValidationException, IOException, ClassNotFoundException {
         p=csvFattura.ultimaFattura();



        try(CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(this.file, true))))
        {
            String[] gVectore = new String[10];
            gVectore[NOMEUTENTE]=p.getNome();
            gVectore[COGNOMEUTENTE]=p.getCognome();
            gVectore[VIA]=p.getVia();
            gVectore[COM]=p.getCom();
            gVectore[AMMONTARE]= String.valueOf(p.getAmmontare());
            gVectore[EMAIL]=p.getEmail();
            gVectore[TIPOACQUISTO]=p.getTipoAcquisto();
            gVectore[IDPAGAMENTOFATTURA]= String.valueOf(p.getIdFattura());
            gVectore[IDPAGAMENTO]= String.valueOf(getIdMax()+1);

            csvWriter.writeNext(gVectore);


            csvWriter.flush();
            this.cachePagamentoTotale.put(String.valueOf(getIdMax()+1),p);






        }catch (CsvValidationException e)
        {
            Logger.getLogger("inset pagamentoTotaleFattura").log(Level.SEVERE,"error in insert payment total fattura csv");

        }
        return true;
    }

    @Override
    public boolean inserisciPagamentoCartaCredito(PagamentoCartaCredito pCC) throws IOException, CsvValidationException {
         pCC=csvPagamentoCartaCredito.ultimoPagamentoCartaCredito();
        pCC.setMetodo("cCredito");
        try(CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(this.file, true))))
        {
            String[] gVector =new String[12];
            gVector[NOMEUTENTE]= pCC.getNomeUtente();
            gVector[COGNOMEUTENTE]=pCC.getCognomeUtente();
            gVector[AMMONTARE]= String.valueOf(pCC.getSpesaTotale());
            gVector[EMAIL]=pCC.getEmail();
            gVector[TIPOACQUISTO]=pCC.getTipoAcquisto();
            gVector[IDPAGAMENTOCC]= String.valueOf(pCC.getIdPagCC());
            gVector[IDOGGETTO]= String.valueOf(pCC.getIdProdotto());

            csvWriter.writeNext(gVector);
            csvWriter.flush();
            this.cachePagamentoTotale.put(String.valueOf(getIdMax()+1),pCC);


        }



        return true;
    }



    @Override
    public void inizializza() throws IOException {

        Path path = Path.of(PAGAMENTOTOTALE);
        try
        {
            if(!Files.exists(path))
                throw new IOException("il file pagamentoTotale non esiste");
        }catch (IOException e)
        {
            Logger.getLogger(" inizializza pagamentoTotale").log(Level.SEVERE," file not exists .{0}",PAGAMENTOTOTALE);
            Files.createFile(path);
            Logger.getLogger(" inizializza pagamentoTotale creazione").log(Level.INFO," file created .{0}",PAGAMENTOTOTALE);



        }

    }
    private static  int getIdMax() throws IOException, CsvValidationException {
        //used for insert correct idOgg
        String[] gVector;
        int id = 0;


        try {


            try (CSVReader reader = new CSVReader(new BufferedReader(new FileReader(PAGAMENTOTOTALE)))) {
                while ((gVector = reader.readNext()) != null) {
                    id = Integer.parseInt(gVector[IDPAGAMENTO]);
                }
            }

            if (id == 0) throw new IdException("id == 0 ");


        } catch (IdException |NumberFormatException e) {
            Logger.getLogger(IDWRONG).log(Level.SEVERE, IDERROR);
        }


            return id;

        }





}
