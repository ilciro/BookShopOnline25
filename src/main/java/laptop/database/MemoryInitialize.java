package laptop.database;

import laptop.controller.ControllerSystemState;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;
import org.jetbrains.annotations.NotNull;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MemoryInitialize {
    //class used for reduce duplication

    private static final String SERIALIZZAZIONEGIORNALE="memory/serializzazioneGiornale.ser";
    private static final String SERIALIZZAZIONELIBRO="memory/serializzazioneLibro.ser";
    private static final String SERIALIZZAZIONERIVISTA="memory/serializzazioneRivista.ser";
    private static final String GIORNALE="giornale";
    private static final String LIBRO="libro";
    private static final String RIVISTA="rivista";
    private static final String INSERT="insert";
    private  ArrayList<Giornale> listG=new ArrayList<>();
    private  ArrayList<Libro> listL=new ArrayList<>();
    private  ArrayList<Rivista> listR=new ArrayList<>();


    private static final ControllerSystemState vis=ControllerSystemState.getInstance();


    public boolean cancellaGiornale(Giornale g)  {


        boolean status=false;

            listG = leggiDaFileGiornale(SERIALIZZAZIONEGIORNALE);


            for (int i = 0; i < listG.size(); i++) {
                if (i == (g.getId() - 1)) {
                    status = listG.remove(listG.get(i));
                }
            }
            removeFile(SERIALIZZAZIONEGIORNALE, listG, new ArrayList<>(), new ArrayList<>());


        return status;
    }

    public boolean cancellaLibro(Libro l) {

        boolean status=false;
       listL=leggiDaFileLibro(SERIALIZZAZIONELIBRO);


        for(int i=0;i<listL.size();i++)
        {
            if(i== (l.getId()-1)) {
                status = listL.remove(listL.get(i));
            }
        }
        removeFile(SERIALIZZAZIONELIBRO,new ArrayList<>(),listL,new ArrayList<>());

        return status;
    }

    public boolean cancellaRivista(Rivista r) {

        boolean status=false;
        listR=leggiDaFileRivista(SERIALIZZAZIONERIVISTA);

        for(int i=0;i<listR.size();i++)
        {
            if(i== (r.getId()-1)) {
                status = listR.remove(listR.get(i));
            }
        }
        removeFile(SERIALIZZAZIONERIVISTA,new ArrayList<>(),new ArrayList<>(),listR);

        return status;
    }

    private void removeFile(String stringPath,ArrayList<Giornale> listG,ArrayList<Libro> listL,ArrayList<Rivista> listR) {

        Path path = Path.of(stringPath);
        try {
            Files.delete(path);
            if(!Files.exists(path))
            {
                throw new IOException("file daily is deleted!!");
            }
        }catch (IOException e)
        {
            try {
                if (!listG.isEmpty()) {
                    Files.createFile(path);
                    try (FileOutputStream fos = new FileOutputStream(stringPath);
                         ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                        oos.writeObject(listG);
                    }
                }
                if (!listL.isEmpty()) {
                    Files.createFile(path);
                    try (FileOutputStream fos = new FileOutputStream(stringPath);
                         ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                        oos.writeObject(listL);
                    }
                }
                if (!listR.isEmpty()) {
                    Files.createFile(path);
                    try (FileOutputStream fos = new FileOutputStream(stringPath);
                         ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                        oos.writeObject(listR);
                    }
                }
            }catch (IOException e1){
                Logger.getLogger("remove file").log(Level.SEVERE,"remove file mi exception : ",e1);

            }

        }
    }

    public void inizializza(String file)  {

        switch (vis.getType())
        {
            case GIORNALE->
            {
                for(int i=1;i<=12;i++) {
                    Giornale g = getGiornale(i);
                    listG.add(g);
                }
                scriviInFile(file,listG,null,null);
                leggiDaFileGiornale(file);


            }
            case LIBRO -> {


                for(int i=1;i<=19;i++) {


                    Libro l = getLibro(i);

                    listL.add(l);


                }
                scriviInFile(file,null,listL,null);
                leggiDaFileLibro(file);


            }
            case RIVISTA ->
            {
                for(int i=1;i<=5;i++)
                {
                    Rivista r=getRivista(i);
                    listR.add(r);
                }
                scriviInFile(file,null,null,listR);
                leggiDaFileRivista(file);

            }

        }

    }

    public boolean inserisci(Libro l,Giornale g,Rivista r,String file,String appoggio) {
        Path path2 = Path.of(appoggio);
        Path path1=Path.of(file);
        try {
            if (l != null) {
                if (vis.getTipoModifica().equals("im")) l.setId(vis.getIdLibro());
                else if (vis.getTipoModifica().equals(INSERT)) l.setId(listL.size() + 1);
                listL.add(l);

                Logger.getLogger("inserisci libro").log(Level.INFO, "inserted libro in list :", listL.get(0).getTitolo());
                //scrivo lista in appoggio

                scriviInFile(appoggio, null, listL, null);
                listL = leggiDaFileLibro(appoggio);
                Files.delete(path1);
                Files.createFile(path1);
                scriviInFile(file, null, listL, null);

                Files.delete(path2);
            }
            if (g != null) {
                if (vis.getTipoModifica().equals("im")) g.setId(vis.getIdGiornale());
                else if (vis.getTipoModifica().equals(INSERT)) g.setId(listG.size() + 1);
                listG.add(g);
                //scrivo lista in appoggio
                Logger.getLogger("inserisci giiornale").log(Level.INFO, "inserted giornale in list : {0}", listG.get(0).getTitolo());


                scriviInFile(appoggio, listG, null, null);
                listG = leggiDaFileGiornale(appoggio);
                Files.delete(path1);
                Files.createFile(path1);
                scriviInFile(file, listG, null, null);

                Files.delete(path2);
            }
            if (r != null) {
                if (vis.getTipoModifica().equals("im")) r.setId(vis.getIdRivista());
                else if (vis.getTipoModifica().equals(INSERT)) r.setId(listR.size() + 1);
                listR.add(r);
                //scrivo lista in appoggio
                Logger.getLogger("inserisci rivista").log(Level.INFO, "inserted rivista in list : ", listR.get(0).getTitolo());


                scriviInFile(appoggio, null, null, listR);
                listR = leggiDaFileRivista(appoggio);
                Files.delete(path1);
                Files.createFile(path1);
                scriviInFile(file, null, null, listR);

                Files.delete(path2);
            }
        }catch (IOException e)
        {
            Logger.getLogger("inserisci").log(Level.SEVERE,"inserisci io exception : ",e);

        }
        return true;
    }




    private void scriviInFile(String nome,ArrayList<Giornale> listaG,ArrayList<Libro> listaL,ArrayList<Rivista> listaR)
    {
        try(FileOutputStream fos=new FileOutputStream(nome,true);
            ObjectOutputStream oos=new ObjectOutputStream(fos)) {
            switch (vis.getType()) {
                case GIORNALE -> oos.writeObject(listaG);
                case LIBRO -> oos.writeObject(listaL);
                case RIVISTA -> oos.writeObject(listaR);
                default -> Logger.getLogger("scrivo in file").log(Level.SEVERE,"type od object is wrong !!");
            }
            oos.flush();

        }catch (IOException e)
        {
            Logger.getLogger("scrivi in file").log(Level.SEVERE,"scrivi in file mi exception : ",e);

        }



    }
    @SuppressWarnings("unchecked")
    private ArrayList<Giornale> leggiDaFileGiornale(String nome) {
        try(FileInputStream fis=new FileInputStream(nome);
            ObjectInputStream ois=new ObjectInputStream(fis)){
            listG=(ArrayList<Giornale>) ois.readObject();
        }catch (IOException e)
        {
            Logger.getLogger("leggi da file giornale io").log(Level.SEVERE,"leggi da file io giornale exception :",e);

        }catch (ClassNotFoundException e1)
        {
            Logger.getLogger("leggi da file giornale class").log(Level.SEVERE,"leggi da file class giornale exception :",e1);

        }
        return listG;
    }
    @SuppressWarnings("unchecked")
    private ArrayList<Libro> leggiDaFileLibro(String nome)  {
        try(FileInputStream fis=new FileInputStream(nome);
            ObjectInputStream ois=new ObjectInputStream(fis)){
            listL=(ArrayList<Libro>) ois.readObject();
        }catch (IOException e)
        {
            Logger.getLogger("leggi da file libro io").log(Level.SEVERE,"leggi da file io libro exception: ",e);

        }catch (ClassNotFoundException e1)
        {
            Logger.getLogger("leggi da file libro class").log(Level.SEVERE,"leggi da file class libro exception: ",e1);

        }

        return listL;
    }
    @SuppressWarnings("unchecked")
    private ArrayList<Rivista> leggiDaFileRivista(String nome) {
        try(FileInputStream fis=new FileInputStream(nome);
            ObjectInputStream ois=new ObjectInputStream(fis)){
            listR=(ArrayList<Rivista>) ois.readObject();
        }catch (IOException e)
        {
            Logger.getLogger("leggi da file rivista io").log(Level.SEVERE,"leggi da file io rivista exception :",e);

        }catch (ClassNotFoundException e1)
        {
            Logger.getLogger("leggi da file rivista class").log(Level.SEVERE,"leggi da file class rivista exception :",e1);

        }
        return listR;
    }


    public List<Giornale> listaGiornali(String file)  {
        return  leggiDaFileGiornale(file);
    }

    public List<Libro> listaLibri(String file)  {
        return  leggiDaFileLibro(file);
    }
    public List<Rivista> listaRiviste(String file)  {
        return  leggiDaFileRivista(file);
    }



    private static @NotNull Giornale getGiornale(int i) {
        String line;


        ArrayList<String> listaG = new ArrayList<>();


        try (FileReader fileReader = new FileReader("src/main/resources/tmpFiles/giornale" + i + ".txt");
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            while ((line = bufferedReader.readLine()) != null) {
                listaG.add(line);

            }
        }catch (IOException e)
        {
            Logger.getLogger("getGiornale").log(Level.SEVERE," get giornale exception : ",e);
        }
        Giornale g = new Giornale();
        g.setTitolo(listaG.get(0));
        g.setEditore(listaG.get(3));
        g.setLingua(listaG.get(2));
        g.setCategoria(listaG.get(1));
        g.setDataPubb(LocalDate.parse(listaG.get(4)));
        g.setCopieRimanenti(Integer.parseInt(listaG.get(5)));
        g.setDisponibilita(Integer.parseInt(listaG.get(6)));
        g.setPrezzo(Float.parseFloat(listaG.get(7)));
        g.setId(Integer.parseInt(listaG.get(8)));
        return g;
    }

    private static @NotNull Libro getLibro(int i)  {
        String line;
        ArrayList<String> listaL = new ArrayList<>();
        try (FileReader fileReader = new FileReader("src/main/resources/tmpFiles/libro" + i + ".txt");
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            while ((line = bufferedReader.readLine()) != null) {
                listaL.add(line);
            }
        }catch (IOException e)
        {
            Logger.getLogger("getLibro").log(Level.SEVERE,"get libro exception : ",e);
        }
        return getLibro(listaL);

    }

    private static @NotNull Libro getLibro(ArrayList<String> listaL) {
        Libro l = new Libro();
        l.setTitolo(listaL.get(0));
        l.setNrPagine(Integer.parseInt(listaL.get(1)));
        l.setCodIsbn(listaL.get(2));
        l.setEditore(listaL.get(3));
        l.setAutore(listaL.get(4));
        l.setLingua(listaL.get(5));
        l.setCategoria(listaL.get(6));
        l.setDataPubb(LocalDate.parse(listaL.get(7)));
        l.setRecensione(listaL.get(8));
        l.setNrCopie(Integer.parseInt(listaL.get(9)));
        l.setDesc(listaL.get(10));
        l.setDisponibilita(Integer.parseInt(listaL.get(11)));
        l.setPrezzo(Float.parseFloat(listaL.get(12)));
        l.setId(Integer.parseInt(listaL.get(13)));
        return l;
    }

    private static @NotNull Rivista getRivista(int i) {
            String line;
            ArrayList<String> listaR = new ArrayList<>();
            try (FileReader fileReader = new FileReader("src/main/resources/tmpFiles/rivista" + i + ".txt");
                 BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                while ((line = bufferedReader.readLine()) != null) {
                    listaR.add(line);
                }
            }catch (IOException e)
            {
                Logger.getLogger("getRivista").log(Level.SEVERE,"getRivista mi exception : ",e);
            }


        return getRivista(listaR);



            }

    private static @NotNull Rivista getRivista(ArrayList<String> listaR) {
        Rivista r=new Rivista();
        r.setTitolo(listaR.get(0));
        r.setCategoria(listaR.get(1));
        r.setAutore(listaR.get(2));
        r.setLingua(listaR.get(3));
        r.setEditore(listaR.get(4));
        r.setDescrizione(listaR.get(5));
        r.setDataPubb(LocalDate.parse(listaR.get(6)));
        r.setDisp(Integer.parseInt(listaR.get(7)));
        r.setPrezzo(Float.parseFloat(listaR.get(8)));
        r.setNrCopie(Integer.parseInt(listaR.get(9)));
        r.setId(Integer.parseInt(listaR.get(10)));
        return r;
    }
}
