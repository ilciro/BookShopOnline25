package laptop.controller.primoucacquista;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerSystemState;
import laptop.database.primoucacquista.giornale.CsvGiornale;
import laptop.database.primoucacquista.giornale.GiornaleDao;
import laptop.database.primoucacquista.giornale.MemoriaGiornale;
import laptop.database.primoucacquista.giornale.PersistenzaGiornale;
import laptop.database.primoucacquista.libro.MemoriaLibro;
import laptop.database.primoucacquista.pagamento.MemoriaPagamento;
import laptop.database.primoucacquista.pagamento.PagamentoDao;
import laptop.database.primoucacquista.rivista.CsvRivista;
import laptop.database.primoucacquista.rivista.MemoriaRivista;
import laptop.database.primoucacquista.rivista.PersistenzaRivista;
import laptop.database.primoucacquista.rivista.RivistaDao;
import laptop.database.terzogestioneprofilooggetto.report.CsvReport;
import laptop.database.terzogestioneprofilooggetto.report.MemoriaReport;
import laptop.database.terzogestioneprofilooggetto.report.PersistenzaReport;
import laptop.database.terzogestioneprofilooggetto.report.ReportDao;
import laptop.exception.IdException;
import laptop.model.Pagamento;

import laptop.model.Report;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;

import laptop.database.primoucacquista.libro.CsvLibro;
import laptop.database.primoucacquista.libro.LibroDao;
import laptop.database.primoucacquista.libro.PersistenzaLibro;
import laptop.database.primoucacquista.pagamento.CsvPagamento;
import laptop.database.primoucacquista.pagamento.PersistenzaPagamento;

import laptop.model.user.User;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.IllegalFormatException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerCheckPagamentoData {
    private final ControllerSystemState vis = ControllerSystemState.getInstance();
    private final Libro l;
    private final Giornale g;
    private final Rivista r;

    private static final String DATABASE ="database";
    private static final String FILE ="file";
    private static final String MEMORIA="memoria";
    private static final String SERIALIZZAZIONEREPO="memory/serializzazioneReport.ser";


    private PersistenzaPagamento pP;
    private PersistenzaReport pR;
    private PersistenzaLibro pL;
    private PersistenzaGiornale pG;
    private PersistenzaRivista pRiv;


    public ControllerCheckPagamentoData()
    {
        l=new Libro();
        g=new Giornale();
        r=new Rivista();

    }

    private void pagamentoLibro(String type,Pagamento p) throws IOException, CsvValidationException, IdException, ClassNotFoundException, SQLException {



        l.setId(vis.getIdLibro());

        switch (type) {
            case DATABASE -> pL = new LibroDao();
            case FILE -> pL = new CsvLibro();
            case MEMORIA ->pL=new MemoriaLibro();
            default -> Logger.getLogger("CcPD database libro").log(Level.SEVERE,"type of persistency not correct!!");


        }
        p.setTipo(pL.getLibroByIdTitoloAutoreLibro(l).get(0).getCategoria());

        switch (type) {
            case DATABASE -> pP = new PagamentoDao();
            case FILE -> pP = new CsvPagamento();
            case MEMORIA ->pP = new MemoriaPagamento();

            default -> Logger.getLogger("CcPD database").log(Level.SEVERE,"type of book payment  not correct!!");
        }

            pP.inizializza();
        pP.inserisciPagamento(p);


        //fare report


       inserisciReport(type,pL,null,null);

        //modifico qui
        Libro temp;
        vis.setTipoModifica("im");
        switch (type)
        {
            case DATABASE -> pL=new LibroDao();
            case FILE -> pL=new CsvLibro();
            case MEMORIA -> pL=new MemoriaLibro();
            default -> Logger.getLogger("pagamento libro cc").log(Level.INFO,"im paying book {0}",l.getId());

        }

        temp=pL.getLibroByIdTitoloAutoreLibro(l).get(0);
        pL.removeLibroById(l);
        temp.setNrCopie(temp.getNrCopie()-vis.getQuantita());
        pL.inserisciLibro(temp);


    }


    private void pagamentoGiornale(String type,Pagamento p) throws IOException, CsvValidationException, IdException, ClassNotFoundException, SQLException {
        g.setId(vis.getIdGiornale());
        switch (type) {
            case DATABASE -> pG = new GiornaleDao();
            case FILE -> pG = new CsvGiornale();
            case MEMORIA -> pG = new MemoriaGiornale();
            default -> Logger.getLogger("CcPD database giornale").log(Level.SEVERE,"type of persistency of daily not correct!!");

        }
        p.setTipo(pG.getGiornaleByIdTitoloAutoreLibro(g).get(0).getCategoria());




        switch (type) {
            case DATABASE -> pP = new PagamentoDao();
            case FILE -> pP = new CsvPagamento();
            case MEMORIA -> pP = new MemoriaPagamento();
            default -> Logger.getLogger("CcPD database daily").log(Level.SEVERE,"type of daily payment  not correct!!");

        }

            pP.inizializza();
        pP.inserisciPagamento(p);


        inserisciReport(type,null,pG,null);

        //modifico qui
        Giornale temp;
        vis.setTipoModifica("im");
         switch (type)
        {
            case DATABASE -> pG=new GiornaleDao();
            case FILE -> pG=new CsvGiornale();
            case MEMORIA -> pG=new MemoriaGiornale();
            default -> Logger.getLogger("pagamento giornale cc").log(Level.INFO,"im paying daily {0}",g.getId());

        }

        temp=pG.getGiornaleByIdTitoloAutoreLibro(g).get(0);
        pG.removeGiornaleById(g);
        temp.setCopieRimanenti(temp.getCopieRimanenti()-vis.getQuantita());
        pG.inserisciGiornale(temp);
    }

    private void pagamentoRivista(String type,Pagamento p) throws IOException, CsvValidationException, IdException, ClassNotFoundException, SQLException {
        r.setId(vis.getIdRivista());
        switch (type) {
            case DATABASE -> pRiv = new RivistaDao();
            case FILE -> pRiv = new CsvRivista();
            case MEMORIA -> pRiv = new MemoriaRivista();
            default -> Logger.getLogger("CcPD database magazine").log(Level.SEVERE,"type of magazine persistency not correct!!");

        }
        p.setTipo(pRiv.getRivistaByIdTitoloAutoreRivista(r).get(0).getCategoria());


        switch (type) {
            case DATABASE -> pP = new PagamentoDao();
            case FILE -> pP = new CsvPagamento();
            case MEMORIA -> pP = new MemoriaPagamento();
            default -> Logger.getLogger("CcPD database pagamento rivista").log(Level.SEVERE,"type of magazine payment  not correct!!");

        }


            pP.inizializza();
        pP.inserisciPagamento(p);

        //fare report
        inserisciReport(type,null,null,pRiv);

        //modifico qui
        Rivista temp;
        vis.setTipoModifica("im");
        switch (type)
        {
            case DATABASE -> pRiv=new RivistaDao();
            case FILE -> pRiv=new CsvRivista();
            case MEMORIA -> pRiv=new MemoriaRivista();
            default -> Logger.getLogger("pagamento rivista cc").log(Level.INFO,"im paying magazine {0}",r.getId());

        }

        temp=pRiv.getRivistaByIdTitoloAutoreRivista(r).get(0);
        pRiv.removeRivistaById(r);
        temp.setCopieRim(temp.getCopieRim()-vis.getQuantita());
        pRiv.inserisciRivista(temp);


    }



    public void checkPagamentoData(String nome, String type) throws IdException, CsvValidationException, IOException, ClassNotFoundException, SQLException {
        Pagamento p;

        switch (vis.getType()) {
            case "libro" ->
                    {

                        p = new Pagamento(1, vis.getMetodoP(), nome, vis.getSpesaT(), User.getInstance().getEmail(), null, vis.getIdLibro());
                        pagamentoLibro(type,p);
                    }

            case "giornale" ->

                    {
                        p = new Pagamento(1, vis.getMetodoP(), nome, vis.getSpesaT(), User.getInstance().getEmail(), null, vis.getIdGiornale());
                        pagamentoGiornale(type,p);
                    }
            case "rivista" ->
                    {
                        p = new Pagamento(1, vis.getMetodoP(), nome, vis.getSpesaT(), User.getInstance().getEmail(), null, vis.getIdRivista());

                        pagamentoRivista(type,p);
                    }


            default -> Logger.getLogger("pagamento").log(Level.SEVERE, " error in payment");
        }
    }



    private void inserisciReport(String type,PersistenzaLibro pL,PersistenzaGiornale pG,PersistenzaRivista pRiv) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        switch (type) {
            case DATABASE -> pR = new ReportDao();
            case FILE -> pR = new CsvReport();
            case MEMORIA -> pR = new MemoriaReport();
            default -> Logger.getLogger("CcPD report magazine").log(Level.SEVERE,"type of magazine report  not correct!!");

        }
        if(!Files.exists(Path.of(SERIALIZZAZIONEREPO)))
            pR.inizializza();
        Report report =new Report();

        report.setIdReport(0);
        if(pL!=null)
        {
            report.setTipologiaOggetto(pL.getLibroByIdTitoloAutoreLibro(l).get(0).getCategoria());
            report.setTitoloOggetto(pL.getLibroByIdTitoloAutoreLibro(l).get(0).getTitolo());
            report.setPrezzo(pL.getLibroByIdTitoloAutoreLibro(l).get(0).getPrezzo());
            report.setTotale(pL.getLibroByIdTitoloAutoreLibro(l).get(0).getPrezzo() * vis.getQuantita());

        }

        if(pRiv!=null)
        {
            report.setTipologiaOggetto(pRiv.getRivistaByIdTitoloAutoreRivista(r).get(0).getCategoria());
            report.setTitoloOggetto(pRiv.getRivistaByIdTitoloAutoreRivista(r).get(0).getTitolo());
            report.setPrezzo(pRiv.getRivistaByIdTitoloAutoreRivista(r).get(0).getPrezzo());
            report.setTotale(pRiv.getRivistaByIdTitoloAutoreRivista(r).get(0).getPrezzo() * vis.getQuantita());


        }
        if(pG!=null)
        {
            report.setTipologiaOggetto(pG.getGiornaleByIdTitoloAutoreLibro(g).get(0).getCategoria());
            report.setTitoloOggetto(pG.getGiornaleByIdTitoloAutoreLibro(g).get(0).getTitolo());
            report.setPrezzo(pG.getGiornaleByIdTitoloAutoreLibro(g).get(0).getPrezzo());
            report.setTotale(pG.getGiornaleByIdTitoloAutoreLibro(g).get(0).getPrezzo() * vis.getQuantita());

        }


        report.setNrPezzi(vis.getQuantita());



        if(pR.insertReport(report))  Logger.getLogger("report inserito").log(Level.INFO," report inserted");
    }




    public void controllaPag(String d, String c,String civ) {

        String appoggio="";
        int cont=0;
        try {
            int x;

            int anno;
            int mese;
            int giorno;
            String[] verifica;


            appoggio = appoggio + d;
            anno = Integer.parseInt((String) appoggio.subSequence(0, 4));
            mese = Integer.parseInt((String) appoggio.subSequence(5, 7));
            giorno = Integer.parseInt((String) appoggio.subSequence(8, 10));


            if (anno > 2020 && (mese >= 1 && mese <= 12) && (giorno >= 1 && giorno <= 31) && c.length() <= 20 && civ.length() == 3) {


                verifica = c.split("-");

                for (x = 0; x < verifica.length; x++) {
                    if (verifica[x].length() == 4) {
                        cont++;
                    }
                }
                if (cont == 4) {
                    Logger.getLogger("procedi cach check data").log(Level.INFO," data is correct !!");
                }


            }
        }catch (NumberFormatException | IllegalFormatException e)
        {
            Logger.getLogger("procedi cash").log(Level.SEVERE,"\n errore nel pagamento .",e);
        }


    }

}
