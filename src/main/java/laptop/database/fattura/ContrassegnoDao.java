package laptop.database.fattura;

import laptop.controller.ControllerSystemState;
import laptop.database.DaoInitialize;
import laptop.model.Fattura;
import laptop.utilities.ConnToDb;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContrassegnoDao extends PersistenzaFattura {

    private  static  final String ECCEZIONE="eccezione ottenuta:";
    private String query;
    private final ControllerSystemState vis=ControllerSystemState.getInstance();
    
    @Override
    public boolean inserisciFattura(Fattura f) {
        int row=0;


            query="insert into fattura (nome,cognome,via,comunicazioni,ammontare)values (?,?,?,?,?)";

            try(Connection conn= ConnToDb.connectionToDB();
                PreparedStatement prepQ=conn.prepareStatement(query)){

                prepQ.setString(1, f.getNome());
                prepQ.setString(2, f.getCognome());
                prepQ.setString(3, f.getVia());
                prepQ.setString(4,f.getCom());
                prepQ.setFloat(5,vis.getSpesaT());
              row=prepQ.executeUpdate();


            }catch(SQLException e)
            {
                Logger.getLogger("insert fattura").log(Level.INFO, ECCEZIONE, e);
            }
            return row==1;





        
    }

    @Override
    public boolean cancellaFattura(Fattura f) {
        boolean state=false;
        int row;
        query="delete from fattura where idFattura=?";
        try(Connection conn=ConnToDb.connectionToDB();
            PreparedStatement prepQ=conn.prepareStatement(query))
        {
            prepQ.setInt(1,f.getIdFattura());
            row=prepQ.executeUpdate();
            if(row==1)
                state=true;
        }catch(SQLException e)
        {
            java.util.logging.Logger.getLogger("annulla ordine").log(Level.INFO, ECCEZIONE, e);
        }

        return state;
    }

    @Override
    public Fattura ultimaFattura() {
        Fattura f=new Fattura();
        query="select * from fattura order by idFattura desc limit 1";
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
            }

        }catch(SQLException e)
        {
            java.util.logging.Logger.getLogger("return fattura").log(Level.INFO, ECCEZIONE, e);
        }
        return f;
    }

    @Override
    public void inizializza() throws IOException, ClassNotFoundException, SQLException {
        DaoInitialize daoI=new DaoInitialize();
        daoI.inizializza("fattura");

    }
}
