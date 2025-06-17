package web.servlet;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.itextpdf.text.DocumentException;


import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Rivista;
import web.bean.DownloadBean;

import web.bean.SystemBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import laptop.model.raccolta.Libro;

@WebServlet("/DownloadServlet")
public class DownloadServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final DownloadBean dB=new DownloadBean();
	private static final SystemBean sB=SystemBean.getInstance();
	private static final Libro l=new Libro();
	private static final Giornale g=new Giornale();
	private static final Rivista r=new Rivista();


	
	
	private static final String INDEX="/index.jsp";
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String invia=req.getParameter("downloadB");
		String annulla=req.getParameter("annullaB");
		
		try {
			if(invia!=null && invia.equals("scarica e leggi") )

			{
				
						dB.setIdB(sB.getIdB());
						dB.setTitoloB(sB.getTitoloB());
						switch (sB.getTypeB())
						{
							case "libro"->{
								l.setId(sB.getIdB());
								l.scarica(sB.getIdB());
								l.leggi(l.getId());
							}
							case "giornale"->
							{
								g.setId(sB.getIdB());
								g.scarica(sB.getIdB());
								g.leggi(l.getId());
							}
							case "rivista"->
							{
								r.setId(sB.getIdB());
								r.scarica(sB.getIdB());
								r.leggi(l.getId());
							}
							default -> Logger.getLogger("download tipo sbagliato").log(Level.SEVERE," type error");
						}

				
				req.setAttribute("bean",dB);
				RequestDispatcher view = getServletContext().getRequestDispatcher(INDEX);
				view.forward(req,resp);
			}
			if(annulla!=null && annulla.equals("annulla"))
			{
				
				RequestDispatcher view= getServletContext().getRequestDispatcher("/annullaPagamento.jsp");
					view.forward(req,resp);


					
				
				
				
			}
			
				
		}catch( DocumentException |URISyntaxException   e)
		{
			req.setAttribute("bean",dB);
			RequestDispatcher view = getServletContext().getRequestDispatcher(INDEX);
			view.forward(req,resp);
		
		}

    }
	
	
}
				
		
		
	
	
	


