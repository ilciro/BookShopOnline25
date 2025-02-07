package laptop.database.cartacredito;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.model.CartaDiCredito;

import java.io.*;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MemoriaCartaCredito extends PersistenzaCC  {


    private static final String SERIALIZZAZIONE="memory/serializzazioneCartaCredito.ser";
    private ArrayList<CartaDiCredito> lista=new ArrayList<>();




    @Override
    @SuppressWarnings("unchecked")
    public boolean insCC(CartaDiCredito cc) throws IOException, CsvValidationException, ClassNotFoundException {
        boolean status=false;

        try(FileInputStream fis=new FileInputStream(SERIALIZZAZIONE);
        ObjectInputStream ois=new ObjectInputStream(fis))
        {
            lista= (ArrayList<CartaDiCredito>) ois.readObject();
        }
        lista.add(cc);


        try (FileOutputStream fout = new FileOutputStream(SERIALIZZAZIONE);
             ObjectOutputStream oos = new ObjectOutputStream(fout)) {
            oos.writeObject(lista);
        }


        Logger.getLogger("insert fattura in memory").log(Level.INFO, "fattura is wrote");


        if (cc.getNumeroCC() != null) status = true;
        return status;
    }


    @Override
    @SuppressWarnings("unchecked")
    public void inizializza(String persistenza) throws IOException, ClassNotFoundException {

            for (int i = 1; i <= 6; i++) {
                String line;


                ArrayList<String> listaG = new ArrayList<>();


                try (FileReader fileReader = new FileReader("src/main/resources/tmpFiles/cartacredito" + i + ".txt");
                     BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                    while ((line = bufferedReader.readLine()) != null) {
                        listaG.add(line);

                    }
                }
                CartaDiCredito cc = new CartaDiCredito();
                cc.setNomeUser(listaG.get(0));
                cc.setCognomeUser(listaG.get(1));
                cc.setNumeroCC(listaG.get(2));


                try {
                    DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                    Date sqlDate = new Date(df.parse(listaG.get(3)).getTime());
                    cc.setScadenza(sqlDate);
                } catch (ParseException e) {
                    Logger.getLogger("data non valida").log(Level.SEVERE, " parse is incorrect!!");
                }

                cc.setCiv(listaG.get(4));
                cc.setAmmontare(Double.parseDouble(listaG.get(5)));

                lista.add(cc);

            }
            try (FileOutputStream fos = new FileOutputStream(SERIALIZZAZIONE);
                 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(lista);
            }

            try (FileInputStream fis = new FileInputStream(SERIALIZZAZIONE);
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
                lista = (ArrayList<CartaDiCredito>) ois.readObject();
            }
    }

    @Override
    @SuppressWarnings("unchecked")
    public ObservableList<CartaDiCredito> getCarteDiCredito(CartaDiCredito cc) throws IOException, ClassNotFoundException {
        ObservableList<CartaDiCredito> listaRec= FXCollections.observableArrayList();
        try(FileInputStream fis=new FileInputStream(SERIALIZZAZIONE);
            ObjectInputStream ois=new ObjectInputStream(fis))
        {
            lista= (ArrayList<CartaDiCredito>) ois.readObject();
        }
        if(cc.getNomeUser()!=null) {
            int j = 0;

            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getNomeUser().equals(cc.getNomeUser()) && j == i) {
                    listaRec.add(lista.get(i));

                }
                j++;
            }
        }
        if(cc.getNumeroCC()!=null)
        {
            int j = 0;

            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getNumeroCC().equals(cc.getNumeroCC()) && j == i) {
                    listaRec.add(lista.get(i));

                }
                j++;
            }
        }
        return listaRec;
    }




}
