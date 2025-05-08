package laptop.controller.terzoucgestioneprofilooggetto;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.controller.ControllerSystemState;

import laptop.database.giornale.CsvGiornale;
import laptop.database.giornale.GiornaleDao;
import laptop.database.giornale.MemoriaGiornale;
import laptop.database.giornale.PersistenzaGiornale;
import laptop.database.libro.CsvLibro;
import laptop.database.libro.LibroDao;
import laptop.database.libro.MemoriaLibro;
import laptop.database.libro.PersistenzaLibro;
import laptop.database.rivista.CsvRivista;
import laptop.database.rivista.MemoriaRivista;
import laptop.database.rivista.PersistenzaRivista;
import laptop.database.rivista.RivistaDao;
import laptop.exception.IdException;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Raccolta;
import laptop.model.raccolta.Rivista;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerRaccolta {

    private static final ControllerSystemState vis=ControllerSystemState.getInstance();
    private static final String LIBRO = "libro";
    private static final String RIVISTA = "rivista";
    private static final String GIORNALE = "giornale";

    private final Libro l;
    private final Giornale g;
    private final Rivista r;

    private static final String DATABASE = "database";
    private static final String FILE = "file";
    private static final String MEMORIA = "memoria";

    private PersistenzaLibro pL;
    private PersistenzaRivista pR;
    private PersistenzaGiornale pG;

    public ObservableList<Raccolta> getRaccoltaLista(String type,String persistenza) throws IOException, CsvValidationException, IdException, SQLException, ClassNotFoundException {

        ObservableList <Raccolta> catalogo= FXCollections.observableArrayList();

        switch (type) {
            case LIBRO->catalogo.addAll(prendiLibri(persistenza));
            case GIORNALE->catalogo.addAll(prendiGiornali(persistenza));
            case RIVISTA->catalogo.addAll(prendiRiviste(persistenza));
            default->Logger.getLogger("Test getId db").log(Level.INFO, "error !! list empty");

        }

        return catalogo;
    }

    private ObservableList<Raccolta> prendiRiviste(String persistenza) throws IOException, CsvValidationException, IdException, SQLException, ClassNotFoundException {
            switch (persistenza){
                case DATABASE -> pR=new RivistaDao();
                case FILE -> pR=new CsvRivista();
                case MEMORIA -> pR=new MemoriaRivista();
                default -> Logger.getLogger("prendi riviste").log(Level.SEVERE," list magazine is empty");

            }
            pR.initializza();




            return pR.retrieveRaccoltaData();
        }



    private ObservableList<Raccolta> prendiLibri(String persistenza) throws IOException, CsvValidationException, IdException, ClassNotFoundException, SQLException {
        switch (persistenza){
            case DATABASE -> pL=new LibroDao();
            case FILE -> pL=new CsvLibro();
            case MEMORIA -> pL=new MemoriaLibro();
            default -> Logger.getLogger("prendi libri").log(Level.SEVERE," list book is empty");

        }
       pL.initializza();



        return pL.retrieveRaccoltaData();
    }
    private ObservableList<Raccolta> prendiGiornali(String persistenza) throws IOException, CsvValidationException, IdException, SQLException, ClassNotFoundException {
        switch (persistenza){
            case DATABASE -> pG=new GiornaleDao();
            case FILE -> pG=new CsvGiornale();
            case MEMORIA -> pG=new MemoriaGiornale();
            default -> Logger.getLogger("prendi giornali").log(Level.SEVERE," list daily is empty");

        }
            pG.initializza();


        return pG.retrieveRaccoltaData();
    }

    public ControllerRaccolta()  {

        l=new Libro();
        g=new Giornale();
        r=new Rivista();

    }

    public boolean elimina(String type) throws CsvValidationException, IOException, SQLException, ClassNotFoundException {
        boolean status = false;

            switch (vis.getType())
            {
                case LIBRO -> {
                    l.setId(vis.getIdLibro());
                    switch (type)
                    {
                        case DATABASE -> pL=new LibroDao();
                        case FILE -> pL=new CsvLibro();
                        case MEMORIA -> pL=new MemoriaLibro();
                        default -> Logger.getLogger("elimina libro").log(Level.SEVERE,"error deleting a book");
                    }
                    status=pL.removeLibroById(l);
                }
                case GIORNALE -> {
                    g.setId(vis.getIdGiornale());
                    Logger.getLogger("elimina giornale").log(Level.INFO,"id daily to delete .{0}",g.getId());
                    switch (type)
                    {
                        case DATABASE -> pG=new GiornaleDao();
                        case FILE -> pG=new CsvGiornale();
                        case MEMORIA -> pG=new MemoriaGiornale();
                        default -> Logger.getLogger("elimina giornale").log(Level.SEVERE,"error deleting a daily");
                    }
                    status=pG.removeGiornaleById(g);

                }
                case RIVISTA ->{
                    r.setId(vis.getIdRivista());
                    switch (type)
                    {
                        case DATABASE -> pR=new RivistaDao();
                        case FILE -> pR=new CsvRivista();
                        case MEMORIA -> pR=new MemoriaRivista();
                        default -> Logger.getLogger("elimina rivista").log(Level.SEVERE,"error deleting a magazine");
                    }
                    status=pR.removeRivistaById(r);
                }
                default -> Logger.getLogger("elimina con db").log(Level.SEVERE," error with delete in mysql");
            }





        return status;
    }
}
