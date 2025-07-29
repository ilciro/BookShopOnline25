package laptop.database.primoucacquista.pagamentototale;

import com.opencsv.exceptions.CsvValidationException;
import laptop.database.primoucacquista.pagamentocartacredito.MemoriaPagamentoCartaCredito;
import laptop.database.primoucacquista.pagamentofattura.MemoriaFattura;
import laptop.model.pagamento.Pagamento;
import laptop.model.pagamento.PagamentoCartaCredito;
import laptop.model.pagamento.PagamentoFattura;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PagamentoTotaleMemoria extends PagamentoTotale{
    private static final String SERIALIZZAZIONE="memory/serializzazionePagamentoTotale.ser";
    private ArrayList<Pagamento> list=new ArrayList<>();
    @Override
    public void inizializza() throws IOException {
        Path path = Path.of(SERIALIZZAZIONE);
        try
        {
            if(!Files.exists(path)) throw new IOException(" file not exists");
        }catch (IOException e)
        {
            Files.createFile(path);
            Logger.getLogger("inizializza memoria fattura").log(Level.INFO," file has been created");
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean inserisciPagamentoFattura(PagamentoFattura p) throws CsvValidationException, IOException, ClassNotFoundException {
        MemoriaFattura mF = new MemoriaFattura();


        p=mF.ultimaFattura();
        try (FileInputStream fin = new FileInputStream(SERIALIZZAZIONE);
             ObjectInputStream ois = new ObjectInputStream(fin)) {

            list = (ArrayList<Pagamento>) ois.readObject();

        }catch (EOFException e)
        {
           Logger.getLogger("lista è vuota").log(Level.SEVERE," list is empty",list.size());
        }


        list.add(p);
        Logger.getLogger("grandzezza lista dopo insert").log(Level.INFO," list size after insert : .{0}",list.size());



        try (FileOutputStream fout = new FileOutputStream(SERIALIZZAZIONE);
             ObjectOutputStream oos = new ObjectOutputStream(fout)) {
            oos.writeObject(list);
        }

        return true;

    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean inserisciPagamentoCartaCredito(PagamentoCartaCredito p ) throws IOException, CsvValidationException, ClassNotFoundException {
        MemoriaPagamentoCartaCredito pCC=new MemoriaPagamentoCartaCredito();

        p=pCC.ultimoPagamentoCartaCredito();
        try (FileInputStream fin = new FileInputStream(SERIALIZZAZIONE);
             ObjectInputStream ois = new ObjectInputStream(fin)) {

            list = (ArrayList<Pagamento>) ois.readObject();

        }catch (EOFException e)
        {
            Logger.getLogger("inserisci pagamento cc").log(Level.SEVERE," excepion :.",e);
        }

        list.add(p);


        try (FileOutputStream fout = new FileOutputStream(SERIALIZZAZIONE);
             ObjectOutputStream oos = new ObjectOutputStream(fout)) {
            oos.writeObject(list);
        }
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean cancellaFattura(PagamentoFattura p) throws IOException, ClassNotFoundException {
        boolean status = false;
        try (FileInputStream fis = new FileInputStream(SERIALIZZAZIONE);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            list = (ArrayList<Pagamento>) ois.readObject();
        }
        for (int i = 1; i <= list.size(); i++) {
            if (i == p.getIdFattura()) {
                Logger.getLogger("cancella fattura").log(Level.INFO,"id payment {0}.",p.getIdFattura());

                status = list.remove(list.get(i - 1));
                break;
            }
        }
        return status;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean cancellaPagamentoCC(PagamentoCartaCredito pCC) throws IOException, ClassNotFoundException {
        boolean status = false;
        try (FileInputStream fis = new FileInputStream(SERIALIZZAZIONE);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            list = (ArrayList<Pagamento>) ois.readObject();
        }
        for (int i = 1; i <= list.size(); i++) {
            if (i == pCC.getIdPagCC()) {
                Logger.getLogger("cancella pagaemnto cc").log(Level.INFO,"id payment {0}.",pCC.getIdPagCC());

                status = list.remove(list.get(i - 1));
                break;
            }
        }
        return status;
    }


}
