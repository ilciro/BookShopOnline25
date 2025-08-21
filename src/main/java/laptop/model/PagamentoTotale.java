package laptop.model;

import java.io.Serializable;

public class PagamentoTotale implements Serializable {
    private String metodoT;
    private String nomeT;
    private String cognomeT;
    private float ammontareT;
    private String emailT;
    private String tipoAcquistoT;
    private int idProdottoT;
    private int idFatturaT;
    private int idPagCCT;

    public String getMetodoT() {
        return metodoT;
    }

    public void setMetodoT(String metodoT) {
        this.metodoT = metodoT;
    }

    public String getNomeT() {
        return nomeT;
    }

    public void setNomeT(String nomeT) {
        this.nomeT = nomeT;
    }

    public String getCognomeT() {
        return cognomeT;
    }

    public void setCognomeT(String cognomeT) {
        this.cognomeT = cognomeT;
    }

    public float getAmmontareT() {
        return ammontareT;
    }

    public void setAmmontareT(float ammontareT) {
        this.ammontareT = ammontareT;
    }

    public String getEmailT() {
        return emailT;
    }

    public void setEmailT(String emailT) {
        this.emailT = emailT;
    }

    public String getTipoAcquistoT() {
        return tipoAcquistoT;
    }

    public void setTipoAcquistoT(String tipoAcquistoT) {
        this.tipoAcquistoT = tipoAcquistoT;
    }

    public int getIdProdottoT() {
        return idProdottoT;
    }

    public void setIdProdottoT(int idProdottoT) {
        this.idProdottoT = idProdottoT;
    }

    public int getIdFatturaT() {
        return idFatturaT;
    }

    public void setIdFatturaT(int idFatturaT) {
        this.idFatturaT = idFatturaT;
    }

    public int getIdPagCCT() {
        return idPagCCT;
    }

    public void setIdPagCCT(int idPagCCT) {
        this.idPagCCT = idPagCCT;
    }

    @Override
    public String toString() {
        return "PagamentoTotale{" +
                "metodoT='" + metodoT + '\'' +
                ", nomeT='" + nomeT + '\'' +
                ", cognomeT='" + cognomeT + '\'' +
                ", ammontareT=" + ammontareT +
                ", emailT='" + emailT + '\'' +
                ", tipoAcquistoT='" + tipoAcquistoT + '\'' +
                ", idProdottoT=" + idProdottoT +
                ", idFatturaT=" + idFatturaT +
                ", idPagCCT=" + idPagCCT +
                '}';
    }
}
