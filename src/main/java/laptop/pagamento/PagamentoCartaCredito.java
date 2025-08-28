package laptop.pagamento;


import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PagamentoCartaCredito implements Serializable,Pagamento {


    public PagamentoCartaCredito()  {

    }
    private String nomeUtenteCC;
    private float spesaTotaleCC;
    private String emailCC;
    private String tipoAcquistoCC;
    private int idProdottoCC;
    private String metodoCC;
    private String cognomeUtenteCC;
    private int idPagCC;


    public String getNomeUtenteCC() {
        return nomeUtenteCC;
    }

    public void setNomeUtenteCC(String nomeUtenteCC) {
        this.nomeUtenteCC = nomeUtenteCC;
    }

    public float getSpesaTotaleCC() {
        return spesaTotaleCC;
    }

    public void setSpesaTotaleCC(float spesaTotaleCC) {
        this.spesaTotaleCC = spesaTotaleCC;
    }

    public String getEmailCC() {
        return emailCC;
    }

    public void setEmailCC(String emailCC) {
        this.emailCC = emailCC;
    }

    public String getTipoAcquistoCC() {
        return tipoAcquistoCC;
    }

    public void setTipoAcquistoCC(String tipoAcquistoCC) {
        this.tipoAcquistoCC = tipoAcquistoCC;
    }

    public int getIdProdottoCC() {
        return idProdottoCC;
    }

    public void setIdProdottoCC(int idProdottoCC) {
        this.idProdottoCC = idProdottoCC;
    }

    public String getMetodoCC() {
        return metodoCC;
    }

    public void setMetodoCC(String metodoCC) {
        this.metodoCC = metodoCC;
    }

    public String getCognomeUtenteCC() {
        return cognomeUtenteCC;
    }

    public void setCognomeUtenteCC(String cognomeUtenteCC) {
        this.cognomeUtenteCC = cognomeUtenteCC;
    }

    public int getIdPagCC() {
        return idPagCC;
    }

    public void setIdPagCC(int idPagCC) {
        this.idPagCC = idPagCC;
    }

    public PagamentoCartaCredito(int idPagCC, String metodo, String nomeUtente, float spesaTotale, String email, String tipoAcquisto, int idProdotto){

       this.idPagCC=idPagCC;
       this.metodoCC=metodo;
       this.nomeUtenteCC=nomeUtente;
       this.spesaTotaleCC=spesaTotale;
       this.emailCC=email;
       this.tipoAcquistoCC=tipoAcquisto;
       this.idProdottoCC=idProdotto;


    }

    @Override
    public String toString() {
        return "PagamentoCartaCredito{" +
                "idProdotto=" + idProdottoCC +
                ", cognomeUtente='" + cognomeUtenteCC + '\'' +
                ", idPagCC=" + idPagCC +
                ", nomeUtente='" + nomeUtenteCC + '\'' +
                ", spesaTotale=" + spesaTotaleCC +
                ", email='" + emailCC + '\'' +
                '}';
    }

    @Override
    public boolean controllaPagamento(String type) {
        boolean status= type.equals("cCredito");
        Logger.getLogger("controllaPagamentoCC").log(Level.INFO,"checking payment cc");
        return status;
    }
}
