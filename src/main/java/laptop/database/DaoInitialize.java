package laptop.database;

import laptop.utilities.ConnToDb;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoInitialize {
    //class used for reduce duplication

    private static final String LIBRO = "libro";
    private static final String GIORNALE = "giornale";
    private static final String RIVISTA = "rivista";
    private static final String UTENTE = "utenti";
    private static final String NEGOZIO = "negozio";
    private static final String FATTURA = "pagamentoFattura";
    private static final String PAGAMENTOCARTACREDITO = "pagamentoCartaCredito";
    private static final String CARTACREDITO = "cartacredito";
    private static final String PAGAMENTOTALE = "pagamentoTotale";
    private static final ResourceBundle RBQUERYCREATE = ResourceBundle.getBundle("sql/dbCreate");
    private static final ResourceBundle RBQUERYEXISTS = ResourceBundle.getBundle("sql/tableExist");
    private static final ResourceBundle RBQUERYPOPOLUATED = ResourceBundle.getBundle("sql/tablePopulate");
    private static final ResourceBundle RBQUERYCREATETABLE = ResourceBundle.getBundle("sql/tableCreate");
    private static final String CREA = "crea";
    private static final String ESISTE = "esiste";
    private static final String POPOLA = "popola";
    private static final String QUERY = "query";
    private int presente;

    public int getPresente() {
        return presente;
    }

    public void setPresente(int presente) {
        this.presente = presente;
    }

    private void creaProcedura(String nome) {

        switch (nome) {
            case ESISTE -> {
                try (Connection conn = ConnToDb.connectionToDB();
                     PreparedStatement prepQ = conn.prepareStatement(RBQUERYEXISTS.getString(QUERY))) {
                    prepQ.execute();
                } catch (SQLException e) {
                    Logger.getLogger("esiste procedura ").log(Level.SEVERE, " exists stored error  or yet created :", e);
                }

            }
            case CREA -> {
                try (Connection conn = ConnToDb.connectionToDB();
                     PreparedStatement prepQ = conn.prepareStatement(RBQUERYCREATETABLE.getString(QUERY))) {
                    prepQ.execute();
                } catch (SQLException e) {
                    Logger.getLogger("crea procedura ").log(Level.SEVERE, " create stored error or yet created :", e);
                }

            }
            case POPOLA -> {
                try (Connection conn = ConnToDb.connectionToDB();
                     PreparedStatement prepQ = conn.prepareStatement(RBQUERYPOPOLUATED.getString(QUERY))) {
                    prepQ.execute();
                } catch (SQLException e) {
                    Logger.getLogger("popola procedura ").log(Level.SEVERE, " polpulated stored error or yet created :", e);
                }

            }
            default -> Logger.getLogger("errore in crea stored").log(Level.SEVERE, "error in create stored ");

        }


    }

    private void eseguiProcedura(String nome, String type) {

        switch (nome) {
            case ESISTE -> esiste(type);
            case CREA -> {
                if(getPresente()==0)
                    crea(type);
                else Logger.getLogger("tabella creata").log(Level.WARNING,"table already created : {0}",type);

            }
            case POPOLA -> {
                if (getPresente() == 0)
                    popola(type);
            }
            default -> Logger.getLogger("errore ad esegire procedura").log(Level.SEVERE, "erroro while execute procedure with type : {0}", type);

        }
    }

    private void esiste(String type) {
        try (Connection conn = ConnToDb.connectionToDB();
             CallableStatement callQ = conn.prepareCall("{call tabellaEsiste(?,?)}")) {
            callQ.setString(1, type);
            callQ.setInt(2, 0);
            if (callQ.executeQuery().next()) {
                presente = callQ.getInt(2);
                setPresente(presente);
            }


        } catch (SQLException e) {
            Logger.getLogger("esegui esiste").log(Level.SEVERE, "exists procedure called badly : {0}", e.getMessage());
        }
        Logger.getLogger("tabella presente :").log(Level.INFO, " type is present : {0}", getPresente());
    }

    private void crea(String type)
    {

            try(Connection conn=ConnToDb.connectionToDB();
                CallableStatement callQ= conn.prepareCall("{call creaTabella(?)}"))
            {
                callQ.setString(1,type);

                callQ.execute();
            }catch (SQLException e)
            {
                Logger.getLogger("crea tabella errore").log(Level.SEVERE," create table error : {0}",e.getMessage());
            }
    }

    private void popola(String type)
    {



            try (Connection conn = ConnToDb.connectionToDB();
                 CallableStatement callQ = conn.prepareCall("{call popolaTabella(?)}")) {
                callQ.setString(1, type);

                callQ.execute();
            } catch (SQLException e) {
                Logger.getLogger("popola query").log(Level.SEVERE, " error with populate table");
            }

    }



    public void inizializza(String type) {
        //connetto a sys
        ConnToDb.generalConnection();
        // connetto a ispw e se non esiste creo
        try (Connection conn = ConnToDb.connectionToDB();
             PreparedStatement prepQ = conn.prepareStatement(RBQUERYCREATE.getString(QUERY))) {
            prepQ.execute();
        } catch (SQLException e) {
            Logger.getLogger("create schema ispw ").log(Level.SEVERE, "error with create schema ispw->reuse");
        }
        //uso ispw
        try (Connection conn = ConnToDb.connectionToDB();
             PreparedStatement prepQ = conn.prepareStatement(RBQUERYCREATE.getString("query2"))) {
            prepQ.execute();
        } catch (SQLException e) {
            Logger.getLogger("uso schema ispw").log(Level.SEVERE, "error with ispw use->reuse");
        }

        Logger.getLogger("ispw creato").log(Level.INFO," databse ispw has been created!!");

        switch (type) {
            case LIBRO -> {
                //creo
                creaProcedura(ESISTE);
                creaProcedura(CREA);
                creaProcedura(POPOLA);
                //richiamo
                   eseguiProcedura(ESISTE, LIBRO);
                eseguiProcedura(CREA, LIBRO);
               eseguiProcedura(POPOLA, LIBRO);
            }
            case GIORNALE -> {
                //creo
                creaProcedura(ESISTE );
                creaProcedura(CREA);
                creaProcedura(POPOLA);
                //richiamo
                eseguiProcedura(ESISTE, GIORNALE);
                eseguiProcedura(CREA, GIORNALE);
                eseguiProcedura(POPOLA, GIORNALE);
            }
            case RIVISTA -> {
                //creo
                creaProcedura(ESISTE);
                creaProcedura(CREA);
                creaProcedura(POPOLA);
                //richiamo
                eseguiProcedura(ESISTE, RIVISTA);
                eseguiProcedura(CREA, RIVISTA);
                eseguiProcedura(POPOLA, RIVISTA);
            }
            case NEGOZIO -> {
                //creo
                creaProcedura(ESISTE);
                creaProcedura(CREA);
                creaProcedura(POPOLA);
                //richiamo
                eseguiProcedura(ESISTE, NEGOZIO);
                eseguiProcedura(CREA, NEGOZIO);
                eseguiProcedura(POPOLA, NEGOZIO);
            }
            case UTENTE -> {
                //creo
                creaProcedura(ESISTE);
                creaProcedura(CREA);
                creaProcedura(POPOLA);
                //richiamo
                eseguiProcedura(ESISTE, UTENTE);
                eseguiProcedura(CREA, UTENTE);
                eseguiProcedura(POPOLA, UTENTE);
            }
            case FATTURA -> {
                //creo
                creaProcedura(ESISTE);
                creaProcedura(CREA);
                //richiamo
                eseguiProcedura(ESISTE, FATTURA);
                eseguiProcedura(CREA, FATTURA);
            }
            case PAGAMENTOCARTACREDITO -> {
                //creo
                creaProcedura(ESISTE);
                creaProcedura(CREA);
                //richiamo
                eseguiProcedura(ESISTE, PAGAMENTOCARTACREDITO);
                eseguiProcedura(CREA, PAGAMENTOCARTACREDITO);
            }
            case PAGAMENTOTALE -> {
                //creo
                creaProcedura(ESISTE);
                creaProcedura(CREA);
                //richiamo
                eseguiProcedura(ESISTE, PAGAMENTOTALE);
                eseguiProcedura(CREA, PAGAMENTOTALE);
            }
            case CARTACREDITO -> {
                //creo
                creaProcedura(ESISTE);
                creaProcedura(CREA);
                //richiamo
                eseguiProcedura(ESISTE, CARTACREDITO);
                eseguiProcedura(CREA, CARTACREDITO);
            }
            default -> Logger.getLogger("errore con il tipo in dao initialize").log(Level.SEVERE," type is wrong!!");
        }
    }




}
