package laptop.model;

import java.io.Serializable;

public class PagamentoTotale implements Serializable {
    private String metodo;
    private String nome;
    private String cognome;
    private float ammontare;
    private String email;
    private String tipoAcquisto;
    private int idProdotto;
    private int idFattura;
    private int idPagCC;

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public float getAmmontare() {
        return ammontare;
    }

    public void setAmmontare(float ammontare) {
        this.ammontare = ammontare;
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

    public int getIdFattura() {
        return idFattura;
    }

    public void setIdFattura(int idFattura) {
        this.idFattura = idFattura;
    }

    public int getIdPagCC() {
        return idPagCC;
    }

    public void setIdPagCC(int idPagCC) {
        this.idPagCC = idPagCC;
    }

    @Override
    public String toString() {
        return "PagamentoTotale{" +
                "metodo='" + metodo + '\'' +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", ammontare=" + ammontare +
                ", email='" + email + '\'' +
                ", tipoAcquisto='" + tipoAcquisto + '\'' +
                ", idProdotto=" + idProdotto +
                ", idFattura=" + idFattura +
                ", idPagCC=" + idPagCC +
                '}';
    }
}
