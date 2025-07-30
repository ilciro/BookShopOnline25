package laptop.model.pagamento;


import java.io.Serializable;

public class Pagamento implements Serializable {

    public boolean controllaPagamentoFattura(PagamentoFattura pF){
       return pF.getIdFattura()!=-1;
    }
    public boolean controllaPagamentCartaCredito(PagamentoCartaCredito pCC){
       return pCC.getIdPagCC()!=-1;
    }

}