package web.servlet;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


import com.opencsv.exceptions.CsvValidationException;
import laptop.database.rivista.PersistenzaRivista;
import laptop.database.rivista.RivistaDao;
import laptop.exception.IdException;
import laptop.model.raccolta.Rivista;
import web.bean.RivistaBean;
import web.bean.SystemBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RivisteServlet")
public class RivisteServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final RivistaBean rB=new RivistaBean();
	private static final String RIVISTE="/riviste.jsp";
	private static final Rivista r=new Rivista();
	private static final PersistenzaRivista pR=new RivistaDao();
	private static final SystemBean sB=SystemBean.getInstance();
	private static final String BEANR="beanR";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String i=req.getParameter("procedi");
		String g=req.getParameter("genera lista");
		String a=req.getParameter("annulla");
		String id=req.getParameter("idOgg");
		

		try {
			if(g!=null && g.equals("genera lista"))
			{

					rB.setListaRivisteB(pR.retrieveRaccoltaData());
					req.setAttribute(BEANR, rB);
					RequestDispatcher view = getServletContext().getRequestDispatcher(RIVISTE);
					view.forward(req, resp);

				
				
			}

			if(i!=null && i.equals("procedi"))
			{
				
				int idO=Integer.parseInt(id);
				if(idO<=pR.retrieveRaccoltaData().size())
				{
					
					sB.setIdB(Integer.parseInt(id));
					rB.setIdB(sB.getIdB());
					r.setId(rB.getIdB());
					sB.setTypeAsMagazine();
					rB.setTitoloB(pR.getRivistaByIdTitoloAutoreRivista(r).get(0).getTitolo());
					rB.setTipologiaB(pR.getRivistaByIdTitoloAutoreRivista(r).get(0).getCategoria());
					sB.setIdB(rB.getIdB());
					sB.setTitoloB(rB.getTitoloB());
					req.setAttribute(BEANR,rB);
					req.setAttribute("beanS",sB);
				
					RequestDispatcher view = getServletContext().getRequestDispatcher("/acquista.jsp"); 
					view.forward(req,resp);
				}
				
				
				
			}
			if(a!=null && a.equals("indietro"))
			{
				RequestDispatcher view = getServletContext().getRequestDispatcher("/index.jsp");
				view.forward(req,resp);
			}
			
		
		} catch ( CsvValidationException | IdException | ClassNotFoundException e) {
			rB.setMexB(new IdException("id nullo o eccede lista"));
			req.setAttribute(BEANR,rB);
			RequestDispatcher view = getServletContext().getRequestDispatcher(RIVISTE);
			try {
				view.forward(req, resp);
			} catch (ServletException | IOException e1) {
				Logger.getLogger("eccezione in rivista").log(Level.SEVERE, "excetpion in magazine", e1);
			}
		}
	}
	


}
