package laptop.database.fattura;


import laptop.model.Fattura;
import org.jetbrains.annotations.NotNull;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MemoriaFattura extends PersistenzaFattura  {

    private static final String SERIALIZZAZIONE="memory/serializzazioneFattura.ser";
    private ArrayList<Fattura> list=new ArrayList<>();



    @Override
    @SuppressWarnings("unchecked")
    public boolean inserisciFattura(Fattura f) throws IOException, ClassNotFoundException {

        try (FileInputStream fin = new FileInputStream(SERIALIZZAZIONE);
             ObjectInputStream ois = new ObjectInputStream(fin)) {

            list = (ArrayList<Fattura>) ois.readObject();

        }
        f.setIdFattura(list.size()+1);
        list.add(f);


            try (FileOutputStream fout = new FileOutputStream(SERIALIZZAZIONE);
                 ObjectOutputStream oos = new ObjectOutputStream(fout)) {
                oos.writeObject(list);
            }



            Logger.getLogger("insert fattura in memory").log(Level.INFO, "fattura is wrote");

    return true;

    }



    @Override
    @SuppressWarnings("unchecked")
    public boolean cancellaFattura(Fattura f) throws IOException, ClassNotFoundException {
        boolean status = false;
        try (FileInputStream fis = new FileInputStream(SERIALIZZAZIONE);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            list = (ArrayList<Fattura>) ois.readObject();
        }
        for (int i = 1; i <= list.size(); i++) {
            if (i == f.getIdFattura()) {
                Logger.getLogger("cancella fattura").log(Level.INFO,"id fattura {0}.",f.getIdFattura());

                status = list.remove(list.get(i - 1));
                break;
            }
        }
        return status;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Fattura ultimaFattura() throws IOException, ClassNotFoundException {


        try(FileInputStream fis=new FileInputStream(SERIALIZZAZIONE);
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            list = (ArrayList<Fattura>) ois.readObject();


            return list.get(list.size() - 1);


        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void inizializza(String persistenza) throws IOException, ClassNotFoundException {

        Path path = Path.of(SERIALIZZAZIONE);
        if(!Files.exists(path)) Files.createFile(path);

        Fattura f = getFattura();

        list.add(f);


        try (FileOutputStream fos = new FileOutputStream(SERIALIZZAZIONE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(list);
        }

        try (FileInputStream fis = new FileInputStream(SERIALIZZAZIONE);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            list = (ArrayList<Fattura>) ois.readObject();
        }
    }

    private static @NotNull Fattura getFattura() throws IOException {
        String line;


        ArrayList<String> listaG = new ArrayList<>();


        try (FileReader fileReader = new FileReader("src/main/resources/tmpFiles/fattura1.txt");
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            while ((line = bufferedReader.readLine()) != null) {
                listaG.add(line);

            }
        }
        Fattura f = new Fattura();
        f.setNome(listaG.get(0));
        f.setCognome(listaG.get(1));
        f.setVia(listaG.get(2));
        f.setCom(listaG.get(3));
        f.setAmmontare(Float.parseFloat(listaG.get(4)));
        f.setIdFattura(Integer.parseInt(listaG.get(5)));
        return f;
    }


}
