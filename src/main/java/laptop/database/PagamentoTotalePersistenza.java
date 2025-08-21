package laptop.database;


import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import laptop.exception.IdException;
import laptop.model.PagamentoTotale;
import laptop.pagamento.PagamentoCartaCredito;
import laptop.pagamento.PagamentoFattura;
import org.apache.commons.lang.SystemUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class PagamentoTotalePersistenza {
    private static final int GETINDEXMETODOP = 0;
    private static final int GETINDEXNOME = 1;
    private static final int GETINDEXCOGNOME = 2;
    private static final int GETINDEXAMMONTAREF = 3;
    private static final int GETINDEXEMAIL = 4;
    private static final int GETINDEXTIPOACQUISTO = 5;
    private static final int GETINDEXIDPRODOTTO = 6;
    private static final int GETINDEXIDFATTURA = 7;
    private static final int GETINDEXIDPAGAMENTOCC = 8;
    private static final String PAGAMENTOTOTALECSV = "report/reportPagamentoTotale.csv";
    private static final String PAGAMENTOTOTALEMEMORIA = "memory/serializzazionePagamentoTotale.ser";

    private File filePagamentoFattura ;


    private static final String APPOGGIO = "report/appoggioTotale.csv";
    private static final String PERMESSI = "rwx------";
    private static final String PREFIX = "prefix";
    private static final String SUFFIX = "suffix";
    private static final String IDWRONG = "id wrong ..!!";
    private static final String IDERROR = "id error !!..";
    private static final String FILE = "file";
    private static final String MEMORIA = "memoria";
    private static final String CCREDITO="cCredito";

    public PagamentoTotalePersistenza(String persistenza) {

        try {
            switch (persistenza) {
                case FILE -> {
                    this.filePagamentoFattura = new File(PAGAMENTOTOTALECSV);
                    Path path = Path.of(PAGAMENTOTOTALECSV);
                    if (!Files.exists(path)) throw new IOException("file csv not exists");
                    Files.createFile(path);
                }
                case MEMORIA -> {
                    this.filePagamentoFattura = new File(PAGAMENTOTOTALEMEMORIA);
                    Path path = Path.of(PAGAMENTOTOTALEMEMORIA);
                    if (!Files.exists(path)) throw new IOException("file ser not exists");
                    Files.createFile(path);
                }
                default -> Logger.getLogger("costruttore totale persistenza ").log(Level.SEVERE, " persistency not correct !! :{0}", persistenza);
            }
        } catch (IOException e) {
            Logger.getLogger("PagamentoTotalePersistenza ").log(Level.SEVERE, "excpetion  has occurred !! :", e);
        }

    }
    private static  int getIdMaxF()  {
        //used for insert correct idOgg
        String[] gVector;
        int id = 0;
        try {

            try(CSVReader reader = new CSVReader(new FileReader(PAGAMENTOTOTALECSV))) {
                while ((gVector = reader.readNext()) != null) {
                    if(gVector[GETINDEXMETODOP].equals("cash"))
                        id = Integer.parseInt(gVector[GETINDEXIDFATTURA]);
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
    private static  int getIdMaxCC()  {
        //used for insert correct idOgg
        String[] gVector;
        int id = 0;
        try {

            try(CSVReader reader = new CSVReader(new FileReader(PAGAMENTOTOTALECSV))) {
                while ((gVector = reader.readNext()) != null) {
                    if(gVector[GETINDEXMETODOP].equals(CCREDITO))
                        id = Integer.parseInt(gVector[GETINDEXIDPAGAMENTOCC]);
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

    public boolean inserisciPagamentoTotaleFattura( PagamentoFattura pF, String persistenza) {
        try {
            switch (persistenza) {
                case FILE -> {
                    try (CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(this.filePagamentoFattura, true)))) {

                        String[] gVectore = new String[9];

                            gVectore[GETINDEXMETODOP] = "cash";
                            gVectore[GETINDEXNOME] = pF.getNome();
                            gVectore[GETINDEXCOGNOME] = pF.getCognome();
                            gVectore[GETINDEXAMMONTAREF] = String.valueOf(pF.getAmmontare());
                            gVectore[GETINDEXEMAIL] = pF.getEmail();
                            gVectore[GETINDEXTIPOACQUISTO] = pF.getTipoAcquisto();
                            gVectore[GETINDEXIDPRODOTTO] = String.valueOf(pF.getIdProdotto());
                            gVectore[GETINDEXIDFATTURA] = String.valueOf(getIdMaxF()+1);
                            gVectore[GETINDEXIDPAGAMENTOCC] = String.valueOf(0);



                        csvWriter.writeNext(gVectore);
                        csvWriter.flush();

                    }

                }
                case MEMORIA -> lista("cash");
                default -> Logger.getLogger("inserisci pagamento totale").log(Level.SEVERE, "inserting payment total error");


            }
        } catch (IOException e) {
            Logger.getLogger("inserisci pagamento totale ").log(Level.SEVERE, "exception :", e);
        }
        return pF.getIdFattura() != 0;
    }

    public boolean inserisciPagamentoTotaleCC(PagamentoCartaCredito pcc, String persistenza) {
        boolean status = false;
        try {
            switch (persistenza) {
                case FILE -> {
                    try (CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(this.filePagamentoFattura, true)))) {

                        String[] gVectore = new String[9];

                        gVectore[GETINDEXMETODOP] = CCREDITO;
                        gVectore[GETINDEXNOME] = pcc.getNomeUtenteCC();
                        gVectore[GETINDEXCOGNOME] = pcc.getCognomeUtenteCC();
                        gVectore[GETINDEXAMMONTAREF] = String.valueOf(pcc.getSpesaTotaleCC());
                        gVectore[GETINDEXEMAIL] = pcc.getEmailCC();
                        gVectore[GETINDEXTIPOACQUISTO] = pcc.getTipoAcquistoCC();
                        gVectore[GETINDEXIDPRODOTTO] = String.valueOf(pcc.getIdProdottoCC());
                        gVectore[GETINDEXIDFATTURA] = String.valueOf(0);
                        gVectore[GETINDEXIDPAGAMENTOCC] = String.valueOf(getIdMaxCC()+1);

                        csvWriter.writeNext(gVectore);
                        csvWriter.flush();


                    }
                }


                case MEMORIA -> lista(CCREDITO);

                default -> Logger.getLogger("inserisci pagamento totale").log(Level.SEVERE, "inserting payment total error");

                }


            } catch (IOException e) {
            Logger.getLogger("inserisci pagamento totale ").log(Level.SEVERE, "exception :", e);
        }
        return status;
    }

    @SuppressWarnings("unchecked")
    private void lista(String metodo) {

        PagamentoTotale pT;
        ArrayList<PagamentoTotale> listaR = new ArrayList<>();

        try (FileInputStream fin = new FileInputStream(PAGAMENTOTOTALEMEMORIA);
             ObjectInputStream ois = new ObjectInputStream(fin)) {

            listaR = (ArrayList<PagamentoTotale>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            Logger.getLogger("pagamento totale mem").log(Level.SEVERE, " error with file payment mem.", e);

        }
        Logger.getLogger("lista").log(Level.INFO," list read {0}",listaR);

        if (metodo.equals("cash")) {
            ArrayList<PagamentoFattura> listaTF;
            try (FileInputStream fis = new FileInputStream("memory/serializzazionePagamentoFattura.ser");
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
                listaTF = (ArrayList<PagamentoFattura>) ois.readObject();

                Logger.getLogger("lista").log(Level.INFO,"list :{0}",listaTF);
                //carico lista
                for (PagamentoFattura pagamentoFattura : listaTF) {
                    pT = new PagamentoTotale();
                    pT.setMetodoT("cash");
                    pT.setNomeT(pagamentoFattura.getNome());
                    pT.setCognomeT(pagamentoFattura.getCognome());
                    pT.setAmmontareT(pagamentoFattura.getAmmontare());
                    pT.setEmailT(pagamentoFattura.getEmail());
                    pT.setTipoAcquistoT(pagamentoFattura.getTipoAcquisto());
                    pT.setIdProdottoT(pagamentoFattura.getIdProdotto());
                    pT.setIdFatturaT(pagamentoFattura.getIdFattura());
                    pT.setIdPagCCT(0);
                    listaR.add(pT);
                }

            } catch (IOException | ClassNotFoundException e) {
                Logger.getLogger("leggo lista da pagaemnto fattura ").log(Level.SEVERE, "payment cash io exception :", e);
            }
            //scrivo
            try (FileOutputStream fout = new FileOutputStream(PAGAMENTOTOTALEMEMORIA);
                 ObjectOutputStream oos = new ObjectOutputStream(fout)) {
                oos.writeObject(listaR);
            } catch (IOException e1) {
                Logger.getLogger("inserisciPagamentofattura").log(Level.SEVERE, "error with write exception :", e1);
            }

        } else if (metodo.equals(CCREDITO)) {
            ArrayList<PagamentoCartaCredito> listaCC;
            try (FileInputStream fis = new FileInputStream("memory/serializzazionePagamentoCartaCredito.ser");
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
                listaCC = (ArrayList<PagamentoCartaCredito>) ois.readObject();
                //carico lista
                for (PagamentoCartaCredito pagamentoCartaCredito : listaCC) {
                    pT = new PagamentoTotale();
                    pT.setMetodoT(CCREDITO);
                    pT.setNomeT(pagamentoCartaCredito.getNomeUtenteCC());
                    pT.setCognomeT(pagamentoCartaCredito.getCognomeUtenteCC());
                    pT.setAmmontareT(pagamentoCartaCredito.getSpesaTotaleCC());
                    pT.setEmailT(pagamentoCartaCredito.getEmailCC());
                    pT.setTipoAcquistoT(pagamentoCartaCredito.getTipoAcquistoCC());
                    pT.setIdProdottoT(pagamentoCartaCredito.getIdProdottoCC());
                    pT.setIdFatturaT(0);
                    pT.setIdPagCCT(pagamentoCartaCredito.getIdPagCC());
                    listaR.add(pT);
                }
            } catch (IOException | ClassNotFoundException e) {
                Logger.getLogger("leggo lista da pagaemnto ccredito").log(Level.SEVERE, "paymentCC io exception :", e);
            }
            //scrivo
            try (FileOutputStream fout = new FileOutputStream(PAGAMENTOTOTALEMEMORIA);
                 ObjectOutputStream oos = new ObjectOutputStream(fout)) {
                oos.writeObject(listaR);
            } catch (IOException e1) {
                Logger.getLogger("inserisciPagamentofattura").log(Level.SEVERE, "error with write exception :", e1);
            }



        }
        Logger.getLogger("lista totale").log(Level.INFO,"list tota payments {0}",listaR);

    }

    public boolean cancellaFatturaFile(PagamentoFattura f)
    {
        boolean status=false;
        try {
            if (SystemUtils.IS_OS_UNIX) {
                FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString(PERMESSI));
                Files.createTempFile(PREFIX, SUFFIX, attr); // Compliant
            }
            File tmpFile = new File(APPOGGIO);
            boolean found = isFoundF(f, tmpFile);
            if (found) {
                Files.move(tmpFile.toPath(), Path.of(PAGAMENTOTOTALECSV), REPLACE_EXISTING);
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

    private static void cleanUp(Path path)  {
        try {
            Files.delete(path);
        } catch (IOException e) {
            Logger.getLogger("cleanUpF").log(Level.SEVERE,"error with del file f :",e);
        }
    }
    private static boolean isFoundF(PagamentoFattura f, File tmpFile) {
        boolean found = false;
        try (CSVReader reader = new CSVReader(new BufferedReader(new FileReader(PAGAMENTOTOTALECSV)));
             CSVWriter writer= new CSVWriter(new BufferedWriter(new FileWriter(tmpFile, true)))) {
            String[] gVector;

            boolean recordFound;
            while ((gVector = reader.readNext()) != null) {

                recordFound = gVector[GETINDEXIDFATTURA].equals(String.valueOf(f.getIdFattura()));


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
        Logger.getLogger("cancella fattura da totale").log(Level.INFO,"deleting fattura with id :{0}",f.getIdFattura());
        return found;
    }

    public boolean cancellaPagamentoFile(PagamentoCartaCredito pcc)
    {
        boolean status=false;
        try {
            if (SystemUtils.IS_OS_UNIX) {
                FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString(PERMESSI));
                Files.createTempFile(PREFIX, SUFFIX, attr); // Compliant
            }
            File tmpFile = new File(APPOGGIO);
            boolean found = isFoundP(pcc, tmpFile);
            if (found) {
                Files.move(tmpFile.toPath(), Path.of(PAGAMENTOTOTALECSV), REPLACE_EXISTING);
                status = true;
            } else {
                cleanUp(Path.of(tmpFile.toURI()));
            }
        }catch (IOException e)
        {
            Logger.getLogger("removeCC").log(Level.SEVERE,"error with remove payment cc :",e);
        }
        return status;
    }
    private static boolean isFoundP(PagamentoCartaCredito pcc , File tmpFile) {
        boolean found = false;
        try (CSVReader reader = new CSVReader(new BufferedReader(new FileReader(PAGAMENTOTOTALECSV)));
             CSVWriter writer= new CSVWriter(new BufferedWriter(new FileWriter(tmpFile, true)))) {
            String[] gVector;

            boolean recordFound;
            while ((gVector = reader.readNext()) != null) {

                recordFound = gVector[GETINDEXIDPAGAMENTOCC].equals(String.valueOf(pcc.getIdPagCC()));


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
        Logger.getLogger("cancella pagamento cc da totale").log(Level.INFO,"deleting cc with id :{0}",pcc.getIdPagCC());
        return found;
    }
    @SuppressWarnings("unchecked")
    public boolean cancellaFatturaMem(PagamentoFattura f)
    {
        ArrayList<PagamentoTotale> list = new ArrayList<>();
        boolean status = false;
        try (FileInputStream fis = new FileInputStream(PAGAMENTOTOTALEMEMORIA);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            list = (ArrayList<PagamentoTotale>) ois.readObject();
        }catch (IOException e)
        {
            Logger.getLogger("cancella pagamento io").log(Level.SEVERE,"del payment io error :",e);
        }catch (ClassNotFoundException e1)
        {
            Logger.getLogger("cancella pagamento csv").log(Level.SEVERE,"del payment csv error :",e1);
        }
        for (int i = 0; i < list.size(); i++) {
            if (i == f.getIdFattura()-1) {
                Logger.getLogger("cancella fattura").log(Level.INFO,"id payment :.",f.getIdFattura());

                status = list.remove(list.get(i));

            }
        }
        scriviSuFileNuovo(list);
        return status;
    }

    @SuppressWarnings("unchecked")
    public boolean cancellaPagCCMem(PagamentoCartaCredito pcc)
    {
        ArrayList<PagamentoTotale> list = new ArrayList<>();
        boolean status = false;
        try (FileInputStream fis = new FileInputStream(PAGAMENTOTOTALEMEMORIA);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            list = (ArrayList<PagamentoTotale>) ois.readObject();
        }catch (IOException e)
        {
            Logger.getLogger("cancella pagamento io").log(Level.SEVERE,"del payment io error :",e);
        }catch (ClassNotFoundException e1)
        {
            Logger.getLogger("cancella pagamento csv").log(Level.SEVERE,"del payment csv error :",e1);
        }
        for (int i = 0; i < list.size(); i++) {
            if (i == pcc.getIdPagCC()-1) {
                Logger.getLogger("cancella fattura").log(Level.INFO,"id payment :.", pcc.getIdPagCC());

                status = list.remove(list.get(i));

            }
        }
       scriviSuFileNuovo(list);
        return status;
    }


    private void scriviSuFileNuovo(ArrayList<PagamentoTotale> list)
    {
        Path path=Path.of(PAGAMENTOTOTALEMEMORIA);
        try{
            Files.delete(path);
            if(!Files.exists(path)) throw new IOException("file "+PAGAMENTOTOTALEMEMORIA+" cancellato");
        }catch (IOException e2)
        {
            try {
                Files.createFile(path);
            } catch (IOException e3) {
                Logger.getLogger("create file").log(Level.SEVERE,"error with creation :",e3);
            }
            try(FileOutputStream fos=new FileOutputStream(PAGAMENTOTOTALEMEMORIA);
                ObjectOutputStream oos=new ObjectOutputStream(fos)){
                oos.writeObject(list);
            }catch (IOException e4)
            {
                Logger.getLogger("scrittura su nuovo file").log(Level.SEVERE,"writing error :",e4);
            }
        }
    }
}
