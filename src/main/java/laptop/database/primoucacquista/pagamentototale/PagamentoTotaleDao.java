package laptop.database.primoucacquista.pagamentototale;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerSystemState;
import laptop.database.DaoInitialize;
import laptop.model.pagamento.PagamentoCartaCredito;
import laptop.model.pagamento.PagamentoFattura;
import laptop.utilities.ConnToDb;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PagamentoTotaleDao extends PersistenzzaPagamentoTotale {

    private static final ControllerSystemState vis=ControllerSystemState.getInstance();


    @Override
    public void inizializza() {
        DaoInitialize dI=new DaoInitialize();
        dI.inizializza("pagamentoTotale");


    }

    @Override
    public boolean inserisciPagamentoFattura(PagamentoFattura p) throws CsvValidationException, IOException, ClassNotFoundException {
        return super.inserisciPagamentoFattura(p);
    }

    @Override
    public boolean inserisciPagamentoCartaCredito(PagamentoCartaCredito pCC) throws CsvValidationException, IOException, ClassNotFoundException {
        return super.inserisciPagamentoCartaCredito(pCC);
    }

    @Override
    public boolean cancellaFattura(PagamentoFattura p) throws IOException, ClassNotFoundException, CsvValidationException {
        int row=0;
        String query="delete from pagamentoFattura where idFattura=?";
      try(Connection conn=ConnToDb.connectionToDB();
      PreparedStatement prepQ=conn.prepareStatement(query))
      {
          prepQ.setInt(1,p.getIdFattura());

          row=prepQ.executeUpdate();

      }catch (SQLException e)
      {
          Logger.getLogger("cancella fattura").log(Level.SEVERE,"exception with cash {0}",e);
      }
      return row==1;

    }

    @Override
    public boolean cancellaPagamentoCC(PagamentoCartaCredito pCC) throws IOException, ClassNotFoundException, CsvValidationException {
        int row=0;
        String query="delete from pagamentoCartaCredito where idPagamento=?";
        try(Connection conn=ConnToDb.connectionToDB();
            PreparedStatement prepQ=conn.prepareStatement(query))
        {
            prepQ.setInt(1,pCC.getIdPagCC());

            row=prepQ.executeUpdate();

        }catch (SQLException e)
        {
            Logger.getLogger("cancella pagaemnto").log(Level.SEVERE,"exception with cc{0}",e);
        }
        return row==1;
    }


}
