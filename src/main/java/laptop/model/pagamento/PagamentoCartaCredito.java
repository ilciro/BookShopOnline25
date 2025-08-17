package laptop.model.pagamento;


import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PagamentoCartaCredito extends Pagamento implements Serializable {


    public PagamentoCartaCredito()  {

    }

    @Override
    public boolean controllaPagamentoFattura(PagamentoFattura pF) {
        return false;
    }

    @Override
    public boolean controllaPagamentCartaCredito(PagamentoCartaCredito pCC) {
        Logger.getLogger("pagamentoCC").log(Level.INFO,"check payment cc");
        return super.controllaPagamentCartaCredito(pCC);
    }

    private String nomeUtente;
     private float spesaTotale;
     private String email;
     private String tipoAcquisto;
     private int idProdotto;
     private String metodo;
     private String cognomeUtente;

    public String getNomeUtente() {
        return nomeUtente;
    }

    public void setNomeUtente(String nomeUtente) {
        this.nomeUtente = nomeUtente;
    }

    public float getSpesaTotale() {
        return spesaTotale;
    }

    public void setSpesaTotale(float spesaTotale) {
        this.spesaTotale = spesaTotale;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipoAcquisto() {
        return tipoAcquisto;
    }

    public void setTipoAcquisto(String tipoAcquisto) {
        this.tipoAcquisto = tipoAcquisto;
    }

    public int getIdProdotto() {
        return idProdotto;
    }

    public void setIdProdotto(int idProdotto) {
        this.idProdotto = idProdotto;
    }

    public int getIdPagCC() {
        return idPagCC;
    }

    public void setIdPagCC(int idPagCC) {
        this.idPagCC = idPagCC;
    }

    private int idPagCC;


    public PagamentoCartaCredito(int idPagCC, String metodo, String nomeUtente, float spesaTotale, String email, String tipoAcquisto, int idProdotto){

       this.idPagCC=idPagCC;
       this.metodo=metodo;
       this.nomeUtente=nomeUtente;
       this.spesaTotale=spesaTotale;
       this.email=email;
       this.tipoAcquisto=tipoAcquisto;
       this.idProdotto=idProdotto;


    }


    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public String getCognomeUtente() {
        return cognomeUtente;
    }

    public void setCognomeUtente(String cognomeUtente) {
        this.cognomeUtente = cognomeUtente;
    }

    @Override
    public String toString() {
        return "PagamentoCartaCredito{" +
                "idProdotto=" + idProdotto +
                ", cognomeUtente='" + cognomeUtente + '\'' +
                ", idPagCC=" + idPagCC +
                ", nomeUtente='" + nomeUtente + '\'' +
                ", spesaTotale=" + spesaTotale +
                ", email='" + email + '\'' +
                '}';
    }
}
