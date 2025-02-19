package testFinali;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerSystemState;
import laptop.controller.primoucacquista.ControllerRicerca;
import laptop.controller.terzoucgestioneprofilooggetto.ControllerGestione;
import laptop.exception.IdException;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;
import laptop.model.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class TestFinaliVari {

    private final ControllerGestione cG=new ControllerGestione();
    private static final ControllerSystemState vis=ControllerSystemState.getInstance();
    private static final ResourceBundle RBUTENTE=ResourceBundle.getBundle("configurations/users");
    private final ControllerRicerca cR=new ControllerRicerca();


    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testLibroById(String strings) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        vis.setIdOggetto(6);
        vis.setId(vis.getIdOggetto());
        assertEquals(1,cG.libroById(strings).size());
    }
    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testGiornaleById(String strings) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        vis.setIdOggetto(8);
        vis.setId(vis.getIdOggetto());
        assertEquals(1,cG.giornaleById(strings).size());
    }
    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testRivistaById(String strings) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        vis.setIdOggetto(1);
        vis.setId(vis.getIdOggetto());
        assertEquals(1,cG.rivistaById(strings).size());
    }

    @Test
    void setCategorieLibro()
    {
        ArrayList<String> catL=new ArrayList<>();
        catL.add("ADOLESCENTI_RAGAZZI");
        catL.add("ARTE");
        catL.add("CINEMA_FOTOGRAFIA");
        catL.add("BIOGRAFIE");
        catL.add("DIARI_MEMORIE");
        catL.add("CALENDARI_AGENDE");
        catL.add("DIRITTO");
        catL.add("DIZINARI_OPERE");
        catL.add("ECONOMIA");
        catL.add("FAMIGLIA");
        catL.add("FANTASCIENZA_FANTASY");
        catL.add("FUMETTI_MANGA");
        catL.add("GIALLI_THRILLER");
        catL.add("COMPUTER_GIOCHI");
        catL.add("HUMOR");
        catL.add("INFORMATICA");
        catL.add("WEB_DIGITAL_MEDIA");
        catL.add("LETTERATURA_NARRATIVA");
        catL.add("LIBRI_BAMBINI");
        catL.add("LIBRI_SCOLASTICI");
        catL.add("LIBRI_UNIVERSITARI");
        catL.add("RICETTARI_GENERALI");
        catL.add("LINGUISTICA_SCRITTURA");
        catL.add("POLITICA");
        catL.add("RELIGIONE");
        catL.add("ROMANZI_ROSA");
        catL.add("SCIENZE");
        catL.add("TECNOLOGIA_MEDICINA");
        catL.add("ALTRO");
        Libro l=new Libro();
        for (String string : catL) l.setCategoria(string);
        assertNotEquals("",l.getCategoria());
    }
    @Test
    void testCategorieRivista()
    {
        ArrayList<String> catR=new ArrayList<>();
        catR.add("SETTIMANALE");
        catR.add("BISETTIMANALE");
        catR.add("MENSILE");
        catR.add("BIMESTRALE");
        catR.add("TRIMESTRALE");
        catR.add("ANNUALE");
        catR.add("ESTIVO");
        catR.add("INVERNALE");
        catR.add("SPORTIVO");
        catR.add("CINEMATOGRAFICA");
        catR.add("GOSSIP");
        catR.add("TELEVISIVO");
        catR.add("MILITARE");
        catR.add("INFORMATICA");
        Rivista r=new Rivista();
        for (String string : catR) r.setCategoria(string);
        assertNotEquals("",r.getCategoria());
    }

    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testLogoutRicerca(String strings) throws CsvValidationException, SQLException, IOException, IdException, ClassNotFoundException {

        User u= User.getInstance();
        u.setEmail(RBUTENTE.getString("emailA"));
        u.setPassword(RBUTENTE.getString("passA"));
        assertTrue(cR.logout(strings));
    }
}
