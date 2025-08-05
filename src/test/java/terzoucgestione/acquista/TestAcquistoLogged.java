package terzoucgestione.acquista;

import com.itextpdf.text.DocumentException;
import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.ObservableList;
import laptop.controller.ControllerSystemState;
import laptop.controller.primoucacquista.*;
import laptop.controller.secondouclogin.ControllerLogin;
import laptop.exception.IdException;
import laptop.model.CartaDiCredito;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestAcquistoLogged {
    private static final ControllerSystemState vis=ControllerSystemState.getInstance();
    private static final ControllerHomePage cHP=new ControllerHomePage();
    private static final ControllerCompravendita cCV=new ControllerCompravendita();
    private static final ControllerAcquista cA=new ControllerAcquista();
    private static final ControllerPagamentoCC cPCC=new ControllerPagamentoCC();
    private static final ControllerVisualizza cV=new ControllerVisualizza();
    private static final ControllerScegliNegozio cSN=new ControllerScegliNegozio();
    private static final ControllerLogin cL=new ControllerLogin();

    private static final ResourceBundle RBUTENTE=ResourceBundle.getBundle("configurations/users");
    private static final ResourceBundle RBCC=ResourceBundle.getBundle("configurations/cartaCredito");


    @ParameterizedTest
    @ValueSource(strings = {"database","file"})
    void testAcquistaGiornaleCCNegozio(String strings) throws CsvValidationException, SQLException, IOException, ClassNotFoundException, IdException, DocumentException, URISyntaxException, ParseException {
        vis.setTipologiaApplicazione("full");
        //login
        cL.login(RBUTENTE.getString("emailS"),RBUTENTE.getString("passS"),strings);
        vis.setTypeAsDaily();
        vis.setMetodoP("cCredito");
        //inizializzo lista
        cHP.persistenza(strings);
        //scelgo id
        cCV.checkId(2,strings,vis.getType());
        //setto id
        vis.setIdGiornale(2);
        //visualizza
        cV.getID();
        cV.getListGiornale(strings);
        //scelgo quantita e prezzo
        cA.getPrezzo("3",strings);
        //inserisco 2 carte

        DateFormat df = new SimpleDateFormat("yyyy-dd-MM");
        java.sql.Date sqlDate = new java.sql.Date(df.parse(RBCC.getString("data")).getTime());

        DateFormat df1 = new SimpleDateFormat("yyyy-dd-MM");
        java.sql.Date sqlDate1 = new java.sql.Date(df1.parse(RBCC.getString("data1")).getTime());



        //aggiungo carta 1
        cPCC.aggiungiCartaDB(RBUTENTE.getString("nomeS"),RBUTENTE.getString("nomeS"), RBCC.getString("codice"), sqlDate,RBCC.getString("civ"), Float.parseFloat(RBCC.getString("prezzo")),strings);
        //aggiungo carta 2
        cPCC.aggiungiCartaDB(RBUTENTE.getString("nomeS"),RBUTENTE.getString("nomeS"), RBCC.getString("codice1"), sqlDate1,RBCC.getString("civ1"), Float.parseFloat(RBCC.getString("prezzo1")),strings);
        //prendo una carta
        ObservableList<CartaDiCredito> lista=cPCC.ritornaElencoCC(RBUTENTE.getString("nomeS"),strings,null);

        cPCC.correttezza(lista.get(0).getNumeroCC(), sqlDate.toLocalDate().toString().replace("-","/"),lista.get(0).getCiv());

        //prendo una carta
        //effettuo pagamento
        cPCC.pagamentoCC(RBUTENTE.getString("nomeS"),strings,RBUTENTE.getString("nomeS"));
        //scarico oggetto
        cSN.getNegozi(strings);
        boolean status= cSN.isOpen(strings,2)&&cSN.isValid(strings,2);
        assertTrue(status);





    }
}
