package laptop.pagamento;

public interface Pagamento {
    boolean controllaPagamentoFattura(PagamentoFattura pF);
    boolean controllaPagamentCartaCredito(PagamentoCartaCredito pCC);
}
