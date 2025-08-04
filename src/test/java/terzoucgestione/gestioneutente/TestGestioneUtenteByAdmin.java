package terzoucgestione.gestioneutente;


import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerSystemState;
import laptop.controller.secondouclogin.ControllerLogin;
import laptop.controller.terzoucgestioneprofiloggetto.*;
import laptop.exception.IdException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.MethodName.class)

class TestGestioneUtenteByAdmin {
    private static final ControllerSystemState vis=ControllerSystemState.getInstance();
    private static final ControllerLogin cL=new ControllerLogin();
    private static final ControllerAdmin cA=new ControllerAdmin();
    private static final ControllerUtenti cU=new ControllerUtenti();
    private static final ControllerGestioneUtente cGU=new ControllerGestioneUtente();
    private static final ResourceBundle RBUTENTE=ResourceBundle.getBundle("configurations/users");

    @ParameterizedTest
    @ValueSource(strings = {"database","file"})
    void testInserisciUtente(String strings) throws CsvValidationException, SQLException, IOException, IdException, ClassNotFoundException {
        vis.setTipologiaApplicazione("full");
        //login
        cL.login(RBUTENTE.getString("emailA"),RBUTENTE.getString("passA"),strings);
        //prendo lista
        cU.getList(strings);
        String[] data  =new String[7];

        data[0]=RBUTENTE.getString("ruoloIns");
        data[1]=RBUTENTE.getString("nomeIns");
        data[2]=RBUTENTE.getString("cognomeIns");
        data[3]=RBUTENTE.getString("emailIns");
        data[4]=RBUTENTE.getString("pwdIns");
        data[5]=RBUTENTE.getString("descIns");
        data[6]=RBUTENTE.getString("dataIns");
        //inserisco
        cGU.inserisciUtente(data,strings);
        assertTrue(cA.logout(strings));


    }
    @ParameterizedTest
    @ValueSource(strings = {"database","file"})
    void testModifUtente(String strings) throws CsvValidationException, SQLException, IOException, IdException, ClassNotFoundException {
        vis.setTipologiaApplicazione("full");
        //login
        cL.login(RBUTENTE.getString("emailA"),RBUTENTE.getString("passA"),strings);
        //prendo lista
        cU.getList(strings);

        String[] data  =new String[7];

        data[0]=RBUTENTE.getString("ruoloInsM");
        data[1]=RBUTENTE.getString("nomeInsM");
        data[2]=RBUTENTE.getString("cognomeInsM");
        data[3]=RBUTENTE.getString("emailInsM");
        data[4]=RBUTENTE.getString("pwdInsM");
        data[5]=RBUTENTE.getString("descInsM");
        data[6]=RBUTENTE.getString("dataIns");
        //inserisco
        cGU.modifica(data,strings,RBUTENTE.getString("emailIns"));
        assertTrue(cA.logout(strings));


    }
    @ParameterizedTest
    @ValueSource(strings = {"database","file"})
    void testRemoveUtente(String strings) throws CsvValidationException, SQLException, IOException, IdException, ClassNotFoundException {
        vis.setTipologiaApplicazione("full");
        //login
        cL.login(RBUTENTE.getString("emailA"),RBUTENTE.getString("passA"),strings);
        //prendo lista
        cU.getList(strings);
        //elimino
        cU.elimina(RBUTENTE.getString("emailInsM"),strings);
        assertTrue(cA.logout(strings));


    }
}
