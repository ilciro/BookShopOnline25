package web.servlet;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.opencsv.exceptions.CsvValidationException;

import laptop.database.primoucacquista.giornale.GiornaleDao;
import laptop.database.primoucacquista.giornale.PersistenzaGiornale;
import laptop.database.primoucacquista.libro.LibroDao;
import laptop.database.primoucacquista.libro.PersistenzaLibro;
import laptop.database.primoucacquista.rivista.PersistenzaRivista;
import laptop.database.primoucacquista.rivista.RivistaDao;
import laptop.exception.IdException;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;
import web.bean.AcquistaBean;
import web.bean.GiornaleBean;
import web.bean.LibroBean;
import web.bean.RivistaBean;
import web.bean.SystemBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AcquistaServlet")
public class AcquistaServlet extends HttpServlet {

	private static final AcquistaBean aB=new AcquistaBean();
	private static final Libro l=new Libro();
	private static final LibroBean lB=new LibroBean();
	private static final GiornaleBean gB=new GiornaleBean();
	private static final RivistaBean rB=new RivistaBean();
	private static final Rivista r=new Rivista();
	private static final Giornale g=new Giornale();
	private static final SystemBean sB=SystemBean.getInstance();
	private static final String BEANS="beanS";
	private static final String LIBRO="libro";
	private static final String GIORNALE="giornale";
	private static final String RIVISTA="rivista";
	private static final PersistenzaLibro pL=new LibroDao();
	private static final PersistenzaGiornale pG=new GiornaleDao();
	private static final PersistenzaRivista pR=new RivistaDao();



	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String q=req.getParameter("quantita");
		String calcola=req.getParameter("totaleB");
		String metodo=req.getParameter("metodoP");
		String negozio=req.getParameter("negozioB");
		SystemBean.getInstance().setMetodoPB(metodo);
		String download=req.getParameter("pdfB");
		float costo=(float)0.0;
		String type=SystemBean.getInstance().getTypeB();
		String pagamento=SystemBean.getInstance().getMetodoPB();
		try {
		
			
		
		if(calcola!=null && calcola.equals("calcola"))
		{
			switch(type)
			{
				case LIBRO->{

					lB.setIdB(sB.getIdB());
					l.setId(lB.getIdB());
					costo=Integer.parseInt(q)*pL.getLibroByIdTitoloAutoreLibro(l).get(0).getPrezzo();
					aB.setPrezzoB(costo);
					sB.setQuantitaB(Integer.parseInt(q));
					sB.setSpesaTB(costo);
					aB.setTitoloB(pL.getLibroByIdTitoloAutoreLibro(l).get(0).getTitolo());

				}
				case  GIORNALE->
				{

						gB.setIdB(sB.getIdB());
						g.setId(gB.getIdB());
						costo=Integer.parseInt(q)*pG.getGiornaleByIdTitoloAutoreLibro(g).get(0).getPrezzo();
						aB.setPrezzoB(costo);
						sB.setQuantitaB(Integer.parseInt(q));
						sB.setSpesaTB(costo);
						aB.setTitoloB(pG.getGiornaleByIdTitoloAutoreLibro(g).get(0).getTitolo());



				}
				case  RIVISTA->
				{
						rB.setIdB(sB.getIdB());
						r.setId(rB.getIdB());
						costo=Integer.parseInt(q)*pR.getRivistaByIdTitoloAutoreRivista(r).get(0).getPrezzo();
						aB.setPrezzoB(costo);
						sB.setQuantitaB(Integer.parseInt(q));
						sB.setSpesaTB(costo);
						aB.setTitoloB(pR.getRivistaByIdTitoloAutoreRivista(r).get(0).getTitolo());


				}
				default-> throw new ServletException("");
			}



			req.setAttribute("beanA",aB);
			    req.setAttribute(BEANS,sB);

				RequestDispatcher view = getServletContext().getRequestDispatcher("/acquista.jsp");
				view.forward(req,resp);
				
				
		}
			
			
		
		if(negozio!=null && negozio.equals("ritiro in negozio"))
		{
			sB.setNegozioSelezionatoB(true);
			switch(pagamento)
			{
				case "cash":
				{
					req.setAttribute(BEANS,sB);

					RequestDispatcher view = getServletContext().getRequestDispatcher("/fattura.jsp"); 
					view.forward(req,resp);
					break;
				}
				case "cCredito":
				{
					req.setAttribute(BEANS,sB);

					RequestDispatcher view = getServletContext().getRequestDispatcher("/cartaCredito.jsp"); 
					view.forward(req,resp);
					break;
				}
				default:break;
			}
			
		}
		if(download!=null && download.equals("scarica il pdf"))
		{
			sB.setNegozioSelezionatoB(false);

			switch(pagamento)
			{
				case "cash"->
				{
					req.setAttribute(BEANS,sB);
					req.setAttribute("beanL",lB);
					req.setAttribute("beanG",gB);
					req.setAttribute("beanR",rB);

					RequestDispatcher view = getServletContext().getRequestDispatcher("/fattura.jsp"); 
					view.forward(req,resp);
				}
				case "cCredito"->
				{
					req.setAttribute(BEANS,sB);
					req.setAttribute("beanL",lB);
					req.setAttribute("beanG",gB);
					req.setAttribute("beanR",rB);

					RequestDispatcher view = getServletContext().getRequestDispatcher("/cartaCredito.jsp"); 
					view.forward(req,resp);

				}
				default-> Logger.getLogger("errore n scarica pdf ").log(Level.SEVERE,"error");

			}
		}
		
		
	} catch (NumberFormatException | CsvValidationException | IdException | ClassNotFoundException e) {
		aB.setMexB(new IdException("quantita eccede la scorta nel magazzino"));
		req.setAttribute("beanA",aB);
		RequestDispatcher view = getServletContext().getRequestDispatcher("/acquista.jsp");
			try {
				view.forward(req, resp);
			} catch (ServletException | IOException e1) {
				Logger.getLogger("eccezione in acquista").log(Level.SEVERE, "excetpion in magazine", e1);
			}
	}
    }

	
}	
		
	
	
