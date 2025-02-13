package laptop.database.pagamento;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.database.DaoInitialize;
import laptop.model.Pagamento;
import laptop.model.user.User;
import laptop.utilities.ConnToDb;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PagamentoDao extends PersistenzaPagamento{
    private String query;
    private static final String ECCEZIONE="eccezione ottenuta:";

    @Override
    public boolean inserisciPagamento(Pagamento p) {

        int row = 0;
        query = "INSERT INTO PAGAMENTO(metodo,nomeUtente,spesaTotale,eMail,tipoAcquisto,idProdotto) values (?,?,?,?,?,?)";

        try (Connection conn = ConnToDb.connectionToDB();
             PreparedStatement prepQ = conn.prepareStatement(query)) {


            prepQ.setString(1, p.getMetodo()); //
            prepQ.setString(2, p.getNomeUtente());
            prepQ.setFloat(3, p.getAmmontare());
            prepQ.setString(4, User.getInstance().getEmail());
            prepQ.setString(5, p.getTipo());
            prepQ.setInt(6, p.getIdOggetto());
            row=prepQ.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger("insert pagamento").log(Level.INFO, ECCEZIONE, e);
        }
        return row==1;

    }

    @Override
    public Pagamento ultimoPagamento() {
        Pagamento p=new Pagamento();
        query="select * from PAGAMENTO order by idPagamento desc limit 1";
        try(Connection conn=ConnToDb.connectionToDB();
            PreparedStatement prepQ=conn.prepareStatement(query)){

            ResultSet rs=prepQ.executeQuery();
            while (rs.next())
            {
                p.setIdPag(rs.getInt(1));
                p.setMetodo(rs.getString(2));
                p.setNomeUtente(rs.getString(3));
                p.setAmmontare(rs.getFloat(4));
                p.setEmail(rs.getString(5));
                p.setTipo(rs.getString(6));
                p.setIdOggetto(rs.getInt(7));
            }

        }catch(SQLException e)
        {
            java.util.logging.Logger.getLogger("return pagamento").log(Level.INFO, ECCEZIONE, e);
        }
        return p;
    }

    @Override
    public boolean cancellaPagamento(Pagamento p) {
        int row=0;
        query="delete from PAGAMENTO where idPagamento=?";
        try(Connection conn=ConnToDb.connectionToDB();
            PreparedStatement prepQ=conn.prepareStatement(query))
        {
            prepQ.setInt(1,p.getIdPag());
            row=prepQ.executeUpdate();

        }catch(SQLException e)
        {
            java.util.logging.Logger.getLogger("annulla ordine").log(Level.INFO, ECCEZIONE, e);
        }

        return row==1;
    }

    @Override
    public void inizializza() throws IOException, ClassNotFoundException, SQLException {
        DaoInitialize daoI=new DaoInitialize();
        daoI.inizializza("pagamento");
    }

    @Override
    public ObservableList<Pagamento> listPagamentiByUser(Pagamento pag) {
        ObservableList<Pagamento> list= FXCollections.observableArrayList();

        query="select  idPagamento,metodo,spesaTotale,tipoAcquisto,idProdotto from PAGAMENTO where email=?";
        try(Connection conn=ConnToDb.connectionToDB();
            PreparedStatement prepQ=conn.prepareStatement(query)){
            prepQ.setString(1,pag.getEmail());

            ResultSet rs=prepQ.executeQuery();
            while (rs.next())
            {
                Pagamento p=new Pagamento();
                p.setIdPag(rs.getInt(1));
                p.setMetodo(rs.getString(2));
                p.setAmmontare(rs.getFloat(3));
                p.setTipo(rs.getString(4));
                p.setIdOggetto(rs.getInt(5));
                list.add(p);
            }

        }catch(SQLException e)
        {
            java.util.logging.Logger.getLogger("return pagamento").log(Level.INFO, ECCEZIONE, e);
        }
        return list;
    }
}
