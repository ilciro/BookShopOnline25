package web.servlet;

import java.io.IOException;

import java.util.logging.Level;

import com.opencsv.exceptions.CsvValidationException;
import laptop.database.negozio.NegozioDao;
import laptop.database.negozio.PersistenzaNegozio;
import web.bean.NegozioBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import laptop.model.Negozio;

@WebServlet("/NegozioServlet")
public class NegozioServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final NegozioBean nB=new NegozioBean();
	private static final Negozio n=new Negozio();
	private static final String INDEX="/index.jsp";
	private static final PersistenzaNegozio pN=new NegozioDao();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String neg1=req.getParameter("buttonNeg1");
		String neg2=req.getParameter("buttonNeg2");
		String neg3=req.getParameter("buttonNeg3");
		String neg4=req.getParameter("buttonNeg4");
		
		try {
		if(neg1!=null && neg1.equals("Negozio A"))
		{
			nB.setNomeB("Negozio A");


				n.setNome(nB.getNomeB());
				n.setIsOpen(pN.checkOpen(n));
				n.setIsValid(pN.checkRitiro(n));
				nB.setAperturaB(n.getIsOpen());
				nB.setDisponibileB(n.getIsValid());

			
			checkDisp(req,resp);
			
		
		}
		if(neg2!=null && neg2.equals("Negozio B"))
		{
			nB.setNomeB("Negozio B");

				n.setNome(nB.getNomeB());
				n.setIsOpen(pN.checkOpen(n));
				n.setIsValid(pN.checkRitiro(n));
				nB.setAperturaB(n.getIsOpen());
				nB.setDisponibileB(n.getIsValid());

			
			checkDisp(req,resp);

			
		}
		if(neg3!=null && neg3.equals("Negozio C"))
		{
			nB.setNomeB("Negozio C");

				n.setNome(nB.getNomeB());
				n.setIsOpen(pN.checkOpen(n));
				n.setIsValid(pN.checkRitiro(n));
				nB.setAperturaB(n.getIsOpen());
				nB.setDisponibileB(n.getIsValid());

			
			checkDisp(req,resp);

			
		}
		if(neg4!=null && neg4.equals("Negozio D"))
		{
			nB.setNomeB("Negozio D");

				n.setNome(nB.getNomeB());
				n.setIsOpen(pN.checkOpen(n));
				n.setIsValid(pN.checkRitiro(n));
				nB.setAperturaB(n.getIsOpen());

			nB.setDisponibileB(n.getIsValid());
			
			checkDisp(req,resp);

			
			
		}
		else {
			nB.setMessaggioB(" negozio chiuso o non vi è possibile ritirare");
			req.setAttribute("beanNeg", nB);
			RequestDispatcher view = getServletContext().getRequestDispatcher("/negozi.jsp"); 
			view.forward(req,resp);
		}
		} catch ( CsvValidationException | ClassNotFoundException e ) {
			java.util.logging.Logger.getLogger("post ").log(Level.INFO, "eccezione nel post {0}.",e.getMessage());

		}
		
	}
	//userd for check if negozio is avalaible for pickup
	//and if negozio is open
	private void checkDisp(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		if(NegozioServlet.n.getIsOpen() && NegozioServlet.n.getIsValid())
		{
			RequestDispatcher view = getServletContext().getRequestDispatcher(INDEX);
			view.forward(req,resp);
		}
	}
	
	
}
