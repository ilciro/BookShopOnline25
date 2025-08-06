package laptop.database.primoucacquista.pagamentototale;

import com.opencsv.exceptions.CsvValidationException;
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



    @Override
    public void inizializza() {
        DaoInitialize dI=new DaoInitialize();
        dI.inizializza("pagamentoTotale");


    }

    @Override
    public boolean inserisciPagamentoFattura(PagamentoFattura p) throws CsvValidationException, IOException, ClassNotFoundException {
        Logger.getLogger("inseirmento pagamento fattura dao").log(Level.INFO,"fattura inserted {0}",p.getIdFattura());

        return super.inserisciPagamentoFattura(p);
    }

    @Override
    public boolean inserisciPagamentoCartaCredito(PagamentoCartaCredito pCC) throws CsvValidationException, IOException, ClassNotFoundException {
        Logger.getLogger("inseirmento pagamento cc dao").log(Level.INFO,"cc inserted {0}",pCC.getIdPagCC());

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
          Logger.getLogger("cacnella fattura").log(Level.INFO,"exception in delete fattura .{0}",e);

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
            Logger.getLogger("cancella pagamento cc dao").log(Level.INFO,"payment deleted with exception . {0}",e);
        }
        return row==1;
    }


}
