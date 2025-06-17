package web.servlet;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerSystemState;
import laptop.database.primoucacquista.fattura.ContrassegnoDao;
import laptop.database.primoucacquista.fattura.PersistenzaFattura;
import laptop.database.primoucacquista.giornale.GiornaleDao;
import laptop.database.primoucacquista.giornale.PersistenzaGiornale;
import laptop.database.primoucacquista.libro.LibroDao;
import laptop.database.primoucacquista.libro.PersistenzaLibro;
import laptop.database.primoucacquista.pagamento.PagamentoDao;
import laptop.database.primoucacquista.pagamento.PersistenzaPagamento;
import laptop.database.primoucacquista.rivista.PersistenzaRivista;
import laptop.database.primoucacquista.rivista.RivistaDao;
import laptop.exception.IdException;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;
import web.bean.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import laptop.model.Fattura;
import laptop.model.Pagamento;
@WebServlet("/FatturaServlet")
public class FatturaServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final FatturaBean fB=new FatturaBean();
	private static final Fattura f=new Fattura();
	private static final LibroBean lB=new LibroBean();
	private static final GiornaleBean gB=new GiornaleBean();
	private static final RivistaBean rB=new RivistaBean();
	private static final PersistenzaPagamento pP=new PagamentoDao();
	private static final PersistenzaFattura pF=new ContrassegnoDao();
	private static final Pagamento p=new Pagamento();
	private static final PagamentoBean pB=new PagamentoBean();
	private static final SystemBean sB=SystemBean.getInstance();
	private static final PersistenzaLibro pL=new LibroDao();
	private static final PersistenzaGiornale pG=new GiornaleDao();
	private static final PersistenzaRivista pR=new RivistaDao();
	

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nome=req.getParameter("nomeL");
		String cognome=req.getParameter("cognomeL");
		String indirizzo=req.getParameter("indirizzoL");
		String com=req.getParameter("com");
		String invia=req.getParameter("buttonC");

		if(checkData(fB.getNomeB(),fB.getCognomeB(),fB.getIndirizzoB()) && (invia!=null )&& invia.equals("procedi"))
		{
			
			fB.setNomeB(nome);
			fB.setCognomeB(cognome);
			fB.setIndirizzoB(indirizzo);
			fB.setComunicazioniB(com);
			try {
				f.setNome(fB.getNomeB());
				f.setCognome(fB.getCognomeB());
				f.setVia(fB.getIndirizzoB());
				f.setCom(fB.getComunicazioniB());
				f.setAmmontare(sB.getSpesaTB());



				pB.setIdB(0);
				pB.setMetodoB(sB.getMetodoPB());
				pB.setAmmontareB(sB.getSpesaTB());
				pB.setEsitoB(0);
				pB.setNomeUtenteB(fB.getNomeB());
                switch (sB.getTypeB()) {
                    case "libro" ->
							{
								Libro l=new Libro();
								lB.setIdB(sB.getIdB());
								l.setId(lB.getIdB());
								pB.setTipoB(pL.getLibroByIdTitoloAutoreLibro(l).get(0).getCategoria());
								pB.setIdOggettoB(pL.getLibroByIdTitoloAutoreLibro(l).get(0).getId());
							}
                    case "giornale" ->
							{
								Giornale g=new Giornale();
								gB.setIdB(sB.getIdB());
								g.setId(gB.getIdB());
								pB.setTipoB("QUOTIDIANO");
								pB.setIdOggettoB(pG.getGiornaleByIdTitoloAutoreLibro(g).get(0).getId());
							}
                    case "rivista" ->
							{
								Rivista r=new Rivista();
								rB.setIdB(sB.getIdB());
								r.setId(rB.getIdB());
								pB.setTipoB(pR.getRivistaByIdTitoloAutoreRivista(r).get(0).getCategoria());
								pB.setIdOggettoB(pR.getRivistaByIdTitoloAutoreRivista(r).get(0).getId());
							}
					default -> Logger.getLogger("tipo non valido").log(Level.SEVERE,"errot type object");
                }
			pB.setIdOggettoB(sB.getIdB());
		
			p.setIdPag(pB.getIdB());
			p.setMetodo(pB.getMetodoB());
			p.setAmmontare(pB.getAmmontareB());
			p.setNomeUtente(pB.getNomeUtenteB());
			p.setTipo(pB.getTipoB());
			p.setIdOggetto(pB.getIdOggettoB());

			sB.setTipoModifica("insert");
				ControllerSystemState vis=ControllerSystemState.getInstance();
				vis.setTipoModifica(sB.getTipoModifica());
				vis.setSpesaT(sB.getSpesaTB());

				pF.inserisciFattura(f);
				pP.inserisciPagamento(p);
			} catch (ClassNotFoundException | CsvValidationException | IdException e) {
				Logger.getLogger("post ").log(Level.INFO, "eccezione nel post {0}.",e.getMessage());
			}
		
			if(sB.isNegozioSelezionatoB())
			{
				RequestDispatcher view = getServletContext().getRequestDispatcher("/negozi.jsp");
				try {
					view.forward(req, resp);
				} catch (ServletException | IOException e1) {
					Logger.getLogger("vado in negozio").log(Level.INFO, "Shop scene");
				}
			}
			else {
				req.setAttribute("beanS",sB);
			RequestDispatcher view = getServletContext().getRequestDispatcher("/download.jsp");
				try {
					view.forward(req, resp);
				} catch (ServletException | IOException e1) {
					Logger.getLogger("eccezione in fattura").log(Level.SEVERE, "check dati", e1);
				}
			}
		}
		
		

		}
	
		private  boolean checkData(String nome,String cognome ,String indirizzo)
		{
            return !"".equals(nome) && !"".equals(cognome) && !"".equals(indirizzo);
		}
	
	

}
