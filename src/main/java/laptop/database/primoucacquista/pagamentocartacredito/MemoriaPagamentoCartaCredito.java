package laptop.database.primoucacquista.pagamentocartacredito;

import com.opencsv.exceptions.CsvValidationException;
import laptop.model.pagamento.PagamentoCartaCredito;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MemoriaPagamentoCartaCredito extends PersistenzaPagamentoCartaCredito{
    private  ArrayList<PagamentoCartaCredito> list= new ArrayList<>();
    private static final String SERIALIZZAZIONE="memory/serializzazionePagamentoCartaCredito.ser";
    @Override
    public void inizializza() throws IOException, ClassNotFoundException, SQLException {
        try
        {
            if(!Files.exists(Path.of(SERIALIZZAZIONE))) throw new IOException(" file not exists");
        }catch (IOException e)
        {
            Files.createFile(Path.of(SERIALIZZAZIONE));
            Logger.getLogger("inizializza memoria pagamentoCC").log(Level.INFO," file has been created");
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean inserisciPagamentoCartaCredito(PagamentoCartaCredito p) throws CsvValidationException, IOException, ClassNotFoundException {
        try (FileInputStream fin = new FileInputStream(SERIALIZZAZIONE);
             ObjectInputStream ois = new ObjectInputStream(fin)) {

            list = (ArrayList<PagamentoCartaCredito>) ois.readObject();

        }catch (EOFException e)
        {
            Logger.getLogger("inserisci pagamento cartacredito").log(Level.SEVERE," excepion :.",e);
        }
        p.setIdPagCC(list.size()+1);
        list.add(p);


        try (FileOutputStream fout = new FileOutputStream(SERIALIZZAZIONE);
             ObjectOutputStream oos = new ObjectOutputStream(fout)) {
            oos.writeObject(list);
        }



        Logger.getLogger("insert payment in memory").log(Level.INFO, "payment is wrote");

        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean cancellaPagamentoCartaCredito(PagamentoCartaCredito p) throws IOException, CsvValidationException, ClassNotFoundException {
        boolean status = false;
        try (FileInputStream fis = new FileInputStream(SERIALIZZAZIONE);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            list = (ArrayList<PagamentoCartaCredito>) ois.readObject();
        }
        for (int i = 1; i <= list.size(); i++) {
            if (i == p.getIdPagCC()) {
                Logger.getLogger("cancella fattura").log(Level.INFO,"id payment {0}.",p.getIdPagCC());

                status = list.remove(list.get(i - 1));
                break;
            }
        }
        return status;
    }

    @Override
    @SuppressWarnings("unchecked")
    public PagamentoCartaCredito ultimoPagamentoCartaCredito() throws IOException, ClassNotFoundException {
        try (FileInputStream fis = new FileInputStream(SERIALIZZAZIONE);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            list = (ArrayList<PagamentoCartaCredito>) ois.readObject();


        }
        return list.get(list.size() - 1);
    }
}
