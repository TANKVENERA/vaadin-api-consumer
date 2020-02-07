package view;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: M.Belski@sam-solutions.com
 * Date: 27.01.2020
 */

@WebServlet(urlPatterns = "/image")
public class ImageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("image/svg+xml");
        String name = req.getParameter("name");
        if (name == null) {
            name = "";
        }
        String svg = "<svg xmlns='http://www.w3.org/2000/svg' width='35' height='35'>"
                + "<circle cx='17.5' cy='17.5' r='17.5' fill='#C8EEEE' />"
                + "<text  x='50%' y='50%' text-anchor='middle' fill='white' font-size='20px' font-family='Arial' dy='.3em'>"
                + name + "</text>" + "</svg>";
        resp.getWriter().write(svg);
    }
}
