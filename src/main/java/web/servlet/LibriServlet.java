package web.servlet;

import com.opencsv.exceptions.CsvValidationException;
import jakarta.servlet.RequestDispatcher;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import laptop.database.primoucacquista.libro.LibroDao;
import laptop.database.primoucacquista.libro.PersistenzaLibro;
import laptop.exception.IdException;
import laptop.model.raccolta.Libro;
import web.bean.LibroBean;
import web.bean.SystemBean;

import java.io.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


@WebServlet("/LibriServlet")
public class LibriServlet extends HttpServlet {
    private static final LibroBean lB = new LibroBean();
    private static final String LIBRI = "/libri.jsp";
    private static final PersistenzaLibro pL = new LibroDao();
    private static final Libro l = new Libro();
    private static final String BEANL = "beanL";
    private static final SystemBean sB = SystemBean.getInstance();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String i = req.getParameter("procedi");
        String g = req.getParameter("genera lista");
        String a = req.getParameter("annulla");
        String id = req.getParameter("idOgg");

        try {




            if (g != null && g.equals("genera lista")) {

                if(lB.getListaLibriB()==null)
                    pL.initializza();


                lB.setListaLibriB(pL.retrieveRaccoltaData());

                req.setAttribute(BEANL, lB);
                RequestDispatcher view = getServletContext().getRequestDispatcher(LIBRI);
                view.forward(req, resp);


            }
            if (i != null && i.equals("procedi")) {

                int idO = Integer.parseInt(id);
                if (idO <=pL.retrieveRaccoltaData().size()) {
                    sB.setIdB(Integer.parseInt(id));
                    lB.setIdB(sB.getIdB());
                    l.setId(lB.getIdB());
                    lB.setTitoloB(pL.getLibroByIdTitoloAutoreLibro(l).get(0).getTitolo());
                    lB.setcategoriaB(pL.getLibroByIdTitoloAutoreLibro(l).get(0).getCategoria());
                    sB.setIdB(lB.getIdB());
                    sB.setTitoloB(lB.getTitoloB());
                    sB.setTypeAsBook();
                    req.setAttribute(BEANL, lB);
                    req.setAttribute("beanS", SystemBean.getInstance());

                    RequestDispatcher view = getServletContext().getRequestDispatcher("/acquista.jsp");
                    view.forward(req, resp);
                }


            }
            if (a != null && a.equals("indietro")) {
                RequestDispatcher view = getServletContext().getRequestDispatcher("/index.jsp");
                view.forward(req, resp);
            }


        } catch (CsvValidationException | IdException | ClassNotFoundException | SQLException e) {
            lB.setMexB(new IdException("id nullo o eccede lista"));
            req.setAttribute(BEANL, lB);
            RequestDispatcher view = getServletContext().getRequestDispatcher(LIBRI);
            try {
                view.forward(req, resp);
            }catch (ServletException|IOException  e1)
            {
                Logger.getLogger("eccezione  in libro").log(Level.SEVERE,"error in book",e1);
            }

        }
    }




}



