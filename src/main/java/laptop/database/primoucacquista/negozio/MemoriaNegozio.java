package laptop.database.primoucacquista.negozio;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.model.Negozio;

import java.io.*;
import java.util.ArrayList;


public class MemoriaNegozio extends PersistenzaNegozio{
    private static final String SERIALIZZAZIONE="memory/serializzazioneNegozio.ser";
    private ArrayList<Negozio> lista=new ArrayList<>();

    @Override
    @SuppressWarnings("unchecked")
    public ObservableList<Negozio> getNegozi() throws IOException, ClassNotFoundException {

        try(FileInputStream fis=new FileInputStream(SERIALIZZAZIONE);
            ObjectInputStream ois = new ObjectInputStream(fis)){
            lista= (ArrayList<Negozio>) ois.readObject();
           }
        return FXCollections.observableArrayList(lista);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean checkOpen(Negozio shop) throws IOException, ClassNotFoundException {

        boolean status=false;

        try(FileInputStream fis=new FileInputStream(SERIALIZZAZIONE);
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            lista = (ArrayList<Negozio>) ois.readObject();
        }

        for (Negozio negozio : lista) {
            if (negozio.getNome().equals(shop.getNome()))
                status = negozio.getIsOpen();
        }


        return status;



    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean checkRitiro(Negozio shop) throws IOException,  ClassNotFoundException {
        boolean status=false;
        try(FileInputStream fis=new FileInputStream(SERIALIZZAZIONE);
            ObjectInputStream ois = new ObjectInputStream(fis)){
            lista=(ArrayList<Negozio>) ois.readObject();

        }
        for (Negozio negozio : lista) {
            if (negozio.getNome().equals(shop.getNome()))
                status = negozio.getIsValid();
        }
        return status;
    }

    @Override
    public void initializza() throws IOException {
        for(int i=1;i<=4;i++) {
            lista.add(leggiNegozio(i));

        }
        inserisciNegozio();
    }

    private void inserisciNegozio() throws IOException {
        //inserisco lista a posto di libro
        try(FileOutputStream fos=new FileOutputStream(SERIALIZZAZIONE);
            ObjectOutputStream oos=new ObjectOutputStream(fos))
        {
            oos.writeObject(new ArrayList<>(lista));
        }

    }

    private Negozio leggiNegozio(int i) throws IOException {
        String line;
        ArrayList<String> listaR = new ArrayList<>();
        try (FileReader fileReader = new FileReader("src/main/resources/tmpFiles/negozio"+ i +".txt");
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            while ((line = bufferedReader.readLine()) != null) {
                listaR.add(line);

            }
        }
            Negozio n=new Negozio();
            n.setId(Integer.parseInt(listaR.get(0)));
            n.setNome(listaR.get(1));
            n.setVia(listaR.get(2));
            n.setIsValid(listaR.get(3).equals(String.valueOf(1)));

             n.setIsOpen(listaR.get(4).equals(String.valueOf(1)));


            return n;
        }




}
