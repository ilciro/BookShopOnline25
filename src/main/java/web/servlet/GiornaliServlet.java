package web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


import com.opencsv.exceptions.CsvValidationException;
import laptop.database.giornale.GiornaleDao;
import laptop.database.giornale.PersistenzaGiornale;
import laptop.exception.IdException;
import web.bean.GiornaleBean;
import web.bean.SystemBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import laptop.model.raccolta.Giornale;


@WebServlet("/GiornaliServlet")
public class GiornaliServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final String GIORNALI="/giornali.jsp";
	private static  final GiornaleBean gB=new GiornaleBean();
	private static final PersistenzaGiornale pG=new GiornaleDao();
	private static final Giornale gior=new Giornale();
	private static final String BEANG="beanG";
	private static final SystemBean sB=SystemBean.getInstance();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String i=req.getParameter("procedi");
		String g=req.getParameter("genera lista");
		String a=req.getParameter("annulla");
		String id=req.getParameter("idOgg");
		

		try {
			if(g!=null && g.equals("genera lista"))
			{

				if(gB.getListaGiornaliB()==null)
					pG.initializza();
					
				gB.setListaGiornaliB(pG.retrieveRaccoltaData());
				
				req.setAttribute(BEANG,gB);
				RequestDispatcher view = getServletContext().getRequestDispatcher(GIORNALI);
				view.forward(req,resp);
			
			
			}
			if(i!=null && i.equals("procedi"))
			{
				
				int idO=Integer.parseInt(id);
				if(idO<=pG.getGiornali().size())
				{
					
				
					gB.setIdB(idO);
					gior.setId(gB.getIdB());
					sB.setTypeAsDaily();
					gB.setTitoloB(pG.getGiornaleByIdTitoloAutoreLibro(gior).get(0).getTitolo());
					sB.setIdB(gB.getIdB());
					sB.setTitoloB(gB.getTitoloB());
				
					
					req.setAttribute(BEANG,gB);
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
			
		
		} catch (CsvValidationException | IdException | ClassNotFoundException | SQLException e) {
			gB.setMexB(new IdException("id nullo o eccede lista"));
			req.setAttribute(BEANG,gB);
			RequestDispatcher view = getServletContext().getRequestDispatcher(GIORNALI);
			try {
				view.forward(req, resp);
			} catch (ServletException | IOException e1) {
				Logger.getLogger("eccezione in giornale").log(Level.SEVERE, "excetpion in daily", e1);
			}
		}
	}
	

	
	
	
	

}
