package laptop.database.primoucacquista.pagamentofattura;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.controller.ControllerSystemState;
import laptop.database.DaoInitialize;
import laptop.model.pagamento.PagamentoFattura;
import laptop.utilities.ConnToDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContrassegnoDao extends PersistenzaPagamentoFattura {

    private  static  final String ECCEZIONE="eccezione ottenuta:";
    private String query;
    private final ControllerSystemState vis=ControllerSystemState.getInstance();
    private static final ResourceBundle TRIGGERFATTURA = ResourceBundle.getBundle("sql/triggerFattura");
    private static final ResourceBundle PERMESSI=ResourceBundle.getBundle("sql/cancella");
    private static final ResourceBundle CANCELLAPAGAMENTOFATTURA=ResourceBundle.getBundle("sql/triggerCancellaFattura");

    @Override
    public boolean inserisciPagamentoFattura(PagamentoFattura f) {
        int row=0;


            query="insert into pagamentoFattura values (?,?,?,?,?,?,?,?,?)";

            try(Connection conn= ConnToDb.connectionToDB();
                PreparedStatement prepQ=conn.prepareStatement(query)){

                prepQ.setString(1, f.getNome());
                prepQ.setString(2, f.getCognome());
                prepQ.setString(3, f.getVia());
                prepQ.setString(4,f.getCom());
                prepQ.setFloat(5,vis.getSpesaT());
                prepQ.setInt(6,0);
                prepQ.setInt(7,f.getIdProdotto());
                prepQ.setString(8,f.getTipoAcquisto());
                prepQ.setString(9,f.getEmail());

              row=prepQ.executeUpdate();


            }catch(SQLException e)
            {
                Logger.getLogger("insert fattura").log(Level.SEVERE, ECCEZIONE, e);
            }
            return row==1;





        
    }

    @Override
    public boolean cancellaPagamentoFattura(PagamentoFattura f) {
        boolean state=false;
        int row;
        query="delete from pagamentoFattura where idFattura=?";
        try(Connection conn=ConnToDb.connectionToDB();
            PreparedStatement prepQ=conn.prepareStatement(query))
        {
            prepQ.setInt(1,f.getIdFattura());
            row=prepQ.executeUpdate();
            if(row==1)
                state=true;
        }catch(SQLException e)
        {
            Logger.getLogger("annulla ordine").log(Level.INFO, ECCEZIONE, e);
        }

        return state;
    }


    @Override
    public PagamentoFattura ultimaFattura() {
        PagamentoFattura f=new PagamentoFattura();
        query="select * from pagamentoFattura order by idFattura desc limit 1";
        try(Connection conn=ConnToDb.connectionToDB();
            PreparedStatement prepQ=conn.prepareStatement(query)){

            ResultSet rs=prepQ.executeQuery();
            while (rs.next())
            {
                f.setNome(rs.getString(1));
                f.setCognome(rs.getString(2));
                f.setVia(rs.getString(3));
                f.setCom(rs.getString(4));
                f.setAmmontare(rs.getFloat(5));
                f.setIdFattura(rs.getInt(6));
                f.setIdProdotto(rs.getInt(7));
            }

        }catch(SQLException e)
        {
            Logger.getLogger("return fattura").log(Level.INFO, ECCEZIONE, e);
        }
        return f;
    }





    @Override
    public void inizializza()  {
        super.inizializza();
        DaoInitialize daoI=new DaoInitialize();
        daoI.inizializza("pagamentoFattura");


        creaTrigger();







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
        PreparedStatement prepQ= conn.prepareStatement(TRIGGERFATTURA.getString("queryT"))){
            prepQ.executeUpdate();
        }catch (SQLException e)
        {
            Logger.getLogger("crea trigger inserisci in pagaemnto toale").log(Level.SEVERE," error with fattura trigger",e);
        }
        try (Connection conn=ConnToDb.connectionToDB();
             PreparedStatement prepQ= conn.prepareStatement(CANCELLAPAGAMENTOFATTURA.getString("queryCF"))){
            prepQ.executeUpdate();
        }catch (SQLException e)
        {
            Logger.getLogger("crea trigger cancella in pagaemnto toale").log(Level.SEVERE," error with fattura trigger delete",e);
        }
    }

    @Override
    public ObservableList<PagamentoFattura> listPagamentiByUserF(PagamentoFattura pf) {
        ObservableList<PagamentoFattura> list= FXCollections.observableArrayList();
        query="select idProdotto,ammontare,tipoAcquisto,idFattura from pagamentoFattura where email=?";
        try(Connection conn=ConnToDb.connectionToDB();
        PreparedStatement prepQ= conn.prepareStatement(query))
        {
            prepQ.setString(1, pf.getEmail());
            ResultSet rs= prepQ.executeQuery();
            while(rs.next())
            {
                PagamentoFattura pF=new PagamentoFattura();
                pF.setIdProdotto(rs.getInt(1));
                pF.setAmmontare(rs.getFloat(2));
                pF.setTipoAcquisto(rs.getString(3));
                pF.setIdFattura(rs.getInt(4));
                list.add(pF);
            }
        }catch (SQLException e)
        {
            Logger.getLogger("pagamenti fattura di utente").log(Level.SEVERE,"exception :",e);
        }
        return list;
    }
}
