package terzoUcGestione.gestioneOggetto;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerSystemState;
import laptop.controller.secondouclogin.ControllerAdmin;
import laptop.controller.secondouclogin.ControllerLogin;
import laptop.controller.terzoucgestioneprofilooggetto.ControllerGestione;
import laptop.controller.terzoucgestioneprofilooggetto.ControllerRaccolta;
import laptop.exception.IdException;
import laptop.model.raccolta.Libro;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestControllerGestioneLibro {
    private final ControllerLogin cL=new ControllerLogin();
    private final ControllerRaccolta cR=new ControllerRaccolta();
    private final ControllerGestione cG=new ControllerGestione();
    private static final ControllerSystemState vis=ControllerSystemState.getInstance();
    private final ControllerAdmin cA=new ControllerAdmin();
    private static final ResourceBundle RBUTENTE=ResourceBundle.getBundle("configurations/users");
    private static final ResourceBundle RBOGGETTO=ResourceBundle.getBundle("configurations/objects");
    private static final String LIBRO="libro";

    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void test1(String strings) throws CsvValidationException, SQLException, IOException, IdException, ClassNotFoundException {
        //giornale
        vis.setTypeAsBook();
        //login as admin
        cL.login(RBUTENTE.getString("emailA"),RBUTENTE.getString("passA"),strings);
        // prendo lista
        cR.getRaccoltaLista(LIBRO,strings);
        //inserisco nuovo giornale
        String []param=new String[13];

        param[0]=RBOGGETTO.getString("titoloLI");
        param[1]=RBOGGETTO.getString("isbnL");
        param[2]=RBOGGETTO.getString("editoreLI");
        param[3]=RBOGGETTO.getString("autoreLI");
        param[4]=RBOGGETTO.getString("linguaLI");
        param[5]=RBOGGETTO.getString("categoriaL");
        param[6]=RBOGGETTO.getString("descrizioneL");
        param[7]=RBOGGETTO.getString("dataPubbL");
        param[8]=RBOGGETTO.getString("recensioneL");
        param[9]=RBOGGETTO.getString("numPagL");
        param[10]=RBOGGETTO.getString("nrCopieL");
        param[11]=RBOGGETTO.getString("dispL");
        param[12]=RBOGGETTO.getString("prezzoLI");



        assertTrue(cG.inserisci(param,strings));
        cA.logout(strings);



    }
    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void test2(String strings) throws CsvValidationException, SQLException, IOException, IdException, ClassNotFoundException {


        String []param=new String[13];

        //giornale
        vis.setTypeAsBook();
        //login as admin
        cL.login(RBUTENTE.getString("emailA"), RBUTENTE.getString("passA"), strings);
        // prendo lista

        ArrayList<Libro> list=new ArrayList<>();

        for (int i=0;i<cR.getRaccoltaLista(LIBRO,strings).size();i++)
        {


            list.add((Libro) cR.getRaccoltaLista(LIBRO,strings).get(i));
        }


        for (Libro libro : list) {
            if (libro.getTitolo().equals(RBOGGETTO.getString("titoloLI")))
                vis.setIdLibro(libro.getId());
        }





        //prendo id


        param[0]=RBOGGETTO.getString("titoloModL");
        param[1]=RBOGGETTO.getString("isbnModL");
        param[2]=RBOGGETTO.getString("editoreModL");
        param[3]=RBOGGETTO.getString("autoreModL");
        param[4]=RBOGGETTO.getString("linguaModL");
        param[5]=RBOGGETTO.getString("categoriaModL");
        param[6]=RBOGGETTO.getString("descrizioneModL");
        param[7]=RBOGGETTO.getString("dataPubbModL");
        param[8]=RBOGGETTO.getString("recensioneModL");
        param[9]=RBOGGETTO.getString("numPagModL");
        param[10]=RBOGGETTO.getString("nrCopieModL");
        param[11]=RBOGGETTO.getString("dispModL");
        param[12]=RBOGGETTO.getString("prezzoModL");
        assertTrue(cG.modifica(param,strings));
        cA.logout(strings);




    }

    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void test3(String strings) throws CsvValidationException, SQLException, IOException, IdException, ClassNotFoundException {
        //giornale
        vis.setTypeAsBook();
        //login as admin
        cL.login(RBUTENTE.getString("emailA"), RBUTENTE.getString("passA"), strings);
        // prendo lista
        ArrayList<Libro> list=new ArrayList<>();

        for (int i=0;i<cR.getRaccoltaLista(LIBRO,strings).size();i++)
        {
            list.add((Libro) cR.getRaccoltaLista(LIBRO,strings).get(i));
        }

        //prendo id
        int id=0;
        for (Libro libro : list) {
            if (libro.getTitolo().equals(RBOGGETTO.getString("titoloModL"))) {
                id = libro.getId();
            }
        }
        vis.setIdGiornale(id);
        assertTrue(cR.elimina(strings));


        cA.logout(strings);
    }


}
