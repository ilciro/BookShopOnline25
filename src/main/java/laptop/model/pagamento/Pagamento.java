package laptop.model.pagamento;



public class Pagamento {

    public boolean controllaPagamentoFattura(PagamentoFattura pF){
       return pF.getIdFattura()!=-1;
    }
    public boolean controllaPagamentCartaCredito(PagamentoCartaCredito pCC){
       return pCC.getIdPagCC()!=-1;
    }

}