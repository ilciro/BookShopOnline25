package web.servlet;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerSystemState;
import laptop.database.cartacredito.CartaCreditoDao;
import laptop.database.cartacredito.PersistenzaCC;
import laptop.database.giornale.GiornaleDao;
import laptop.database.giornale.PersistenzaGiornale;
import laptop.database.libro.LibroDao;
import laptop.database.libro.PersistenzaLibro;
import laptop.database.pagamento.PagamentoDao;
import laptop.database.pagamento.PersistenzaPagamento;
import laptop.database.rivista.PersistenzaRivista;
import laptop.exception.IdException;
import web.bean.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import laptop.model.CartaDiCredito;
import laptop. model.Pagamento;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;

@WebServlet("/CartaCreditoServlet")
public class CartaCreditoServlet extends HttpServlet {


	/**
	 * 
	 */
	private static final CartaCreditoBean ccB=new CartaCreditoBean();
	private static final CartaDiCredito cc=new CartaDiCredito();
	private static final Libro l=new Libro();
	private static final LibroBean lB=new LibroBean();
	private static final Giornale g=new Giornale();
	private static final GiornaleBean gB=new GiornaleBean();
	private static final Rivista r=new Rivista();
	private static final RivistaBean rB=new RivistaBean();
	private static final PersistenzaPagamento pP=new PagamentoDao();
	private static final PersistenzaCC pCC=new CartaCreditoDao();
	private static final SystemBean sB=SystemBean.getInstance();
	private static final PersistenzaLibro pL=new LibroDao();
	private static final PersistenzaGiornale pG=new GiornaleDao();
	protected static final PersistenzaRivista pR=new PersistenzaRivista();
	private static final String CREDITO="cCredito";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String n=req.getParameter("nomeL");
		String c=req.getParameter("cognomeL");
		String numero=req.getParameter("cartaL");
		String scad=req.getParameter("scadL");
		String civ=req.getParameter("passL");
		String invia=req.getParameter("buttonI");
		String annulla=req.getParameter("buttonA");
		String registra=req.getParameter("regB");
		String generaLista=req.getParameter("prendiDB");
		try {
		if(annulla!=null && annulla.equals("annulla"))
		{
			RequestDispatcher view = getServletContext().getRequestDispatcher("/acquista.jsp"); 
			view.forward(req,resp);
		}
		if(invia!=null && invia.equals("paga"))
		{
			ccB.setNomeB(n);
			ccB.setCivB(c);
			ccB.setNumeroCCB(numero);
			ccB.setCognomeB(c);
			
			ccB.setDataScadB(new SimpleDateFormat("yyyy/MM/dd").parse(scad));
			ccB.setCivB(civ);
			ccB.setPrezzoTransazioneB(SystemBean.getInstance().getSpesaTB());

			if(controllaPag(scad, ccB.getNumeroCCB(), ccB.getCivB()))
			{

					//effettua pagamento

					sB.setTipoModifica("insert");
				ControllerSystemState vis=ControllerSystemState.getInstance();
				vis.setTipoModifica(sB.getTipoModifica());

				pP.inserisciPagamento(creaPagamento(sB.getTypeB(),n));


					

					
					if(SystemBean.getInstance().isNegozioSelezionatoB())
					{
						req.setAttribute("beanS",SystemBean.getInstance());

						RequestDispatcher view = getServletContext().getRequestDispatcher("/negozi.jsp"); 
						view.forward(req,resp);
					}
					else {
						req.setAttribute("beanS",SystemBean.getInstance());
					RequestDispatcher view = getServletContext().getRequestDispatcher("/download.jsp"); 
					view.forward(req,resp);
					}
					
			}
			
			
		}
		if(registra!=null && registra.equals("registra e paga"))
		{
			Date sqlDate ;
			java.util.Date utilDate;
			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");


			utilDate = format.parse(scad);
			sqlDate = new Date(utilDate.getTime());


			cc.setTipo(2);
					cc.setNumeroCC(ccB.getNumeroCCB());
					cc.setAmmontare(1000.0);
					cc.setScadenza(sqlDate);
					cc.setNomeUser(ccB.getNomeB());
					cc.setPrezzoTransazine(SystemBean.getInstance().getSpesaTB());

					pCC.insCC(cc);


		}
		if(generaLista!=null && generaLista.equals("generaLista"))
		{
			Logger.getLogger("post genera").log(Level.INFO, "da fare");

		}
		
	} catch (Exception   e) {
		Logger.getLogger("post ").log(Level.INFO, "eccezione nel post {0}.",e.toString());
	}
	}
	
	private boolean controllaPag(String d, String c,String civ) {
		int x;

		 int anno;
		 int mese;
		 int giorno;
		String[] verifica=null;
		String appoggio="";
		int cont=0;
		boolean state=false;


		

		appoggio = appoggio + d;
		

		  anno = Integer.parseInt((String) appoggio.subSequence(0, 4));
		  mese = Integer.parseInt((String) appoggio.subSequence(5, 7));
		  giorno = Integer.parseInt((String) appoggio.subSequence(8, 10));
		
		if (anno > 2020 && (mese >= 1 && mese <= 12) && (giorno >= 1 && giorno <= 31) && c.length() <= 20 && civ.length()==3 ) {
			
				
					 verifica= c.split("-");
					
					for ( x=0; x<verifica.length; x++) {
							if(verifica[x].length()==4)
							{
								cont++;
							}
					}
					if (cont==4)
					{
						state=true;
					}
					
				

		} 
		
		
		return state;

	}

	private Pagamento creaPagamento(String type,String nome) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
		Pagamento p = null;
		switch (type) {
			case "libro" ->
					{
						lB.setIdB(sB.getIdB());
						l.setId(lB.getIdB());
						p=new Pagamento(0,CREDITO,nome,sB.getSpesaTB(),"",pL.getLibroByIdTitoloAutoreLibro(l).get(0).getCategoria(),
								pL.getLibroByIdTitoloAutoreLibro(l).get(0).getId());
					}
			case "giornale" ->
			{
				gB.setIdB(sB.getIdB());
				g.setId(gB.getIdB());
				p=new Pagamento(0,CREDITO,nome,sB.getSpesaTB(),"",pG.getGiornaleByIdTitoloAutoreLibro(g).get(0).getCategoria(),
						pG.getGiornaleByIdTitoloAutoreLibro(g).get(0).getId());

			}
			case "rivista" -> {
				rB.setIdB(sB.getIdB());
				r.setId(rB.getIdB());
				p=new Pagamento(0,CREDITO,nome,sB.getSpesaTB(),"",pR.getRivistaByIdTitoloAutoreRivista(r).get(0).getCategoria(),
						pR.getRivistaByIdTitoloAutoreRivista(r).get(0).getId());

			}
			default -> Logger.getLogger("crea pagamento").log(Level.SEVERE,"error ");
		}
		return p;

	}
	



}
