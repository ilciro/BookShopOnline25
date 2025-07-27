package laptop.model.pagamento;


import java.io.Serializable;
import java.util.Objects;

public class PagamentoFattura extends Pagamento implements Serializable {

    private String nome;
    private String cognome;
    private String via;
    private String com;
    private float ammontare;
    private int idFattura;
    private String email;

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

    private String tipoAcquisto;

    public int getIdProdotto() {
        return idProdotto;
    }

    public void setIdProdotto(int idProdotto) {
        this.idProdotto = idProdotto;
    }

    private int idProdotto;

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

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public float getAmmontare() {
        return ammontare;
    }

    public void setAmmontare(float ammontare) {
        this.ammontare = ammontare;
    }

    public int getIdFattura() {
        return idFattura;
    }

    public void setIdFattura(int idFattura) {
        this.idFattura = idFattura;
    }



    @Override
    public boolean controllaPagamentCartaCredito(PagamentoCartaCredito pCC) {
        return false;
    }

    @Override
    public boolean controllaPagamentoFattura(PagamentoFattura pF)  {
        return !Objects.equals(pF.getVia(), "");
    }

    public PagamentoFattura(String nome, String cognome, String via, String com, float ammontare, int idFattura, int idProdotto)
    {
        this.nome=nome;
        this.cognome=cognome;
        this.via=via;
        this.com=com;
        this.ammontare=ammontare;
        this.idFattura=idFattura;
        this.idProdotto=idProdotto;

    }
    public PagamentoFattura(){}
}
