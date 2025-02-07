package primouc;

import com.itextpdf.text.DocumentException;
import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerSystemState;
import laptop.controller.primoucacquista.ControllerDownload;
import laptop.exception.IdException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestControllerScarica {

    //controller download bur renamed fo other purpose

     private static final ControllerSystemState vis=ControllerSystemState.getInstance();
     private final ControllerDownload cD=new ControllerDownload();
    private static final ResourceBundle RBOGGETTO=ResourceBundle.getBundle("configurations/objects");



    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testScaricaLibro(String strings) throws CsvValidationException, SQLException, DocumentException, IOException, URISyntaxException, IdException, ClassNotFoundException {
         vis.setTypeAsBook();
         vis.setId(Integer.parseInt(RBOGGETTO.getString("idL")));
         vis.setQuantita(5);
         cD.scarica(vis.getType(),strings);
         assertEquals(6,vis.getId());
     }
    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testScaricaGiornale(String strings) throws CsvValidationException, SQLException, DocumentException, IOException, URISyntaxException, IdException, ClassNotFoundException {
        vis.setTypeAsDaily();
        vis.setId(Integer.parseInt(RBOGGETTO.getString("idG")));
        vis.setQuantita(3);
        cD.scarica(vis.getType(),strings);
        assertEquals(1,vis.getId());
    }
    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testScaricaRivista(String strings) throws CsvValidationException, SQLException, DocumentException, IOException, URISyntaxException, IdException, ClassNotFoundException {
        vis.setTypeAsMagazine();
        vis.setId(Integer.parseInt(RBOGGETTO.getString("idR")));
        vis.setQuantita(2);
        cD.scarica(vis.getType(),strings);
        assertEquals(5,vis.getId());
    }


}
