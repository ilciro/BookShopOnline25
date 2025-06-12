package terzoUcGestione.ricerca;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerSystemState;
import laptop.controller.primoucacquista.ControllerRicerca;
import laptop.exception.IdException;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TestControllerRicerca {

     private static final ControllerRicerca cR=new ControllerRicerca();
     private static final ResourceBundle RBOGGETTO=ResourceBundle.getBundle("configurations/objects");
    private static final ControllerSystemState vis=ControllerSystemState.getInstance();
     @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testListaLibri(String strings) throws CsvValidationException, SQLException, IOException, ClassNotFoundException, IdException {

         vis.setTypeAsBook();

         Libro l=new Libro();
         l.setTitolo(RBOGGETTO.getString("titoloL"));
         l.setEditore(RBOGGETTO.getString("editoreL"));
         l.setAutore(RBOGGETTO.getString("autoreL"));
         assertEquals(1,cR.listaLibri(l.getTitolo(),strings).size());
     }
    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testListaGiornali(String strings) throws CsvValidationException, SQLException, IOException, ClassNotFoundException, IdException {

         vis.setTypeAsDaily();
        Giornale g=new Giornale();
        g.setTitolo(RBOGGETTO.getString("titoloG"));
        g.setEditore(RBOGGETTO.getString("editoreG"));
        assertEquals(1,cR.listaGiornali(g.getTitolo(),strings).size());
    }

    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testListaRiviste(String strings) throws CsvValidationException, SQLException, IOException, ClassNotFoundException, IdException {

         vis.setTypeAsMagazine();
        Rivista r=new Rivista();
        r.setTitolo(RBOGGETTO.getString("titoloR"));
        r.setEditore(RBOGGETTO.getString("editoreR"));
        r.setAutore(RBOGGETTO.getString("autoreR"));
        assertNotEquals(0,cR.listaRiviste(r.getEditore(),strings).size());
    }



}
