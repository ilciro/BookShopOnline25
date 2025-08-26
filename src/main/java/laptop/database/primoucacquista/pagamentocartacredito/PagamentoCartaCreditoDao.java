package laptop.database.primoucacquista.pagamentocartacredito;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.database.DaoInitialize;
import laptop.pagamento.PagamentoCartaCredito;
import laptop.utilities.ConnToDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PagamentoCartaCreditoDao extends PersistenzaPagamentoCartaCredito{

    private static final ResourceBundle TRIGGERCC=ResourceBundle.getBundle("sql/triggerCartaCredito");
    private static final ResourceBundle CANCELLAPAGAMENTOCARTACREDITO=ResourceBundle.getBundle("sql/triggerCancellaPagamentoCCredito");
    private static final ResourceBundle PERMESSI=ResourceBundle.getBundle("sql/cancella");


    private String query;

    @Override
    public ObservableList<PagamentoCartaCredito> listaPagamentiUserByCC(PagamentoCartaCredito pcc) {
        super.listaPagamentiUserByCC(pcc);
        query="select idProdotto,spesaTotale,tipoAcquisto,idPagamento from pagamentoCartaCredito where email=?";
        ObservableList<PagamentoCartaCredito>list = FXCollections.observableArrayList();
        try(Connection conn=ConnToDb.connectionToDB();
        PreparedStatement prepQ=conn.prepareStatement(query))
        {
            prepQ.setString(1, pcc.getEmailCC());
            ResultSet rs= prepQ.executeQuery();
            while(rs.next())
            {
                PagamentoCartaCredito pCC=new PagamentoCartaCredito();
                pCC.setIdProdottoCC(rs.getInt(1));
                pCC.setSpesaTotaleCC(rs.getFloat(2));
                pCC.setTipoAcquistoCC(rs.getString(3));
                pCC.setIdPagCC(rs.getInt(4));
                list.add(pCC);
            }
        }catch (SQLException e)
        {
            Logger.getLogger("get libro id autore obes").log(Level.SEVERE, "eccezione :", e);
        }
        return list;

    }

    @Override
    public void inizializza() {
        DaoInitialize dI=new DaoInitialize();
        dI.inizializza("pagamentoTotale");
        dI.inizializza("pagamentoCartaCredito");

        creaTrigger();
        super.inizializza();
    }

    @Override
    public boolean cancellaPagamentoCartaCredito(PagamentoCartaCredito p)  {
        super.cancellaPagamentoCartaCredito(p);
        int row=0;
       query="delete from pagamentoCartaCredito where idPagamento=?";
       try(Connection conn=ConnToDb.connectionToDB();
       PreparedStatement prep= conn.prepareStatement(query))
       {
           prep.setInt(1,p.getIdPagCC());
           row=prep.executeUpdate();
       }catch (SQLException e)
       {
           Logger.getLogger("cancello pagamentoCC").log(Level.SEVERE,"exception with delete paymentCC",e);
       }
       return row==1;
    }

    @Override
    public PagamentoCartaCredito ultimoPagamentoCartaCredito()  {
        super.ultimoPagamentoCartaCredito();
        PagamentoCartaCredito pCC = null;
        query = "select * from pagamentoCartaCredito order by idPagamento desc limit 1";
        try (Connection conn=ConnToDb.connectionToDB();
             PreparedStatement prepQ= conn.prepareStatement(query)) {

            ResultSet rs=prepQ.executeQuery();
            while(rs.next()) {
                pCC = new PagamentoCartaCredito(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getFloat(5), rs.getString(6), rs.getString(7), rs.getInt(8));
                pCC.setCognomeUtenteCC(rs.getString(4));
            }


        } catch (SQLException e)
        {
            Logger.getLogger(" ultimo pagamentoCC").log(Level.SEVERE,"exception with last paymentCC",e);
        }
        return pCC;
    }

    @Override
    public boolean inserisciPagamentoCartaCredito(PagamentoCartaCredito p)  {
        super.inserisciPagamentoCartaCredito(p);
        int row=0;

        query="insert into pagamentoCartaCredito values(?,?,?,?,?,?,?,?)";

        try (Connection conn=ConnToDb.connectionToDB();
        PreparedStatement prepQ=conn.prepareStatement(query)){

            prepQ.setInt(1,0);
            prepQ.setString(2,"cCredito");
            prepQ.setString(3,p.getNomeUtenteCC());
            prepQ.setString(4,p.getCognomeUtenteCC());
            prepQ.setFloat(5,p.getSpesaTotaleCC());
            prepQ.setString(6,p.getEmailCC());
            prepQ.setString(7,p.getTipoAcquistoCC());
            prepQ.setInt(8,p.getIdProdottoCC());

            row=prepQ.executeUpdate();
        }catch (SQLException e)
        {
           Logger.getLogger("inserimento pagamento cc").log(Level.SEVERE," error with cc payment :",e);
        }
        return row==1;

    }

    private void creaTrigger() {
        try(Connection conn=ConnToDb.connectionToDB();
            PreparedStatement prepQ=conn.prepareStatement(PERMESSI.getString("queryP")))
        {
            prepQ.execute();
        }catch (SQLException e)
        {
            Logger.getLogger("setto permessi").log(Level.SEVERE," error with grants permissions");
        }


        try (Connection conn=ConnToDb.connectionToDB();
             PreparedStatement prepQ= conn.prepareStatement(TRIGGERCC.getString("queryTCC"))){
            prepQ.executeUpdate();
        }catch (SQLException e)
        {
            Logger.getLogger("crea trigger inserisci in pagaemnto toale").log(Level.SEVERE," error with fattura trigger",e);
        }
        try (Connection conn=ConnToDb.connectionToDB();
             PreparedStatement prepQ= conn.prepareStatement(CANCELLAPAGAMENTOCARTACREDITO.getString("queryCancCC"))){
            prepQ.executeUpdate();
        }catch (SQLException e)
        {
            Logger.getLogger("crea trigger cancella in pagaemnto toale").log(Level.SEVERE," error with fattura trigger delete",e);
        }
    }


}
