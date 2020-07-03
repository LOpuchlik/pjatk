import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/response")
public class ResponseServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' >");
        out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        out.println("<link href=\"https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.2/css/bootstrap.css\" rel=\"stylesheet\" media=\"screen\">");
        out.println("<title>Response for user's request</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>Matching results</h2>");


        out.println("<table class=\"table\">");

            out.println("<thead>");
                out.println("<tr>");
                    out.println("<th>TYPE</th>");
                    out.println("<th>BRAND</th>");
                    out.println("<th>MODEL</th>");
                    out.println("<th>FUEL TYPE</th>");
                out.println("</tr>");
            out.println("</thead>");

            out.println("<tbody>");
            for (Car c : ProcessingServlet.matchingCars){
                out.println("<tr>");
                    out.println("<td>"+c.getType()+"</td>");
                    out.println("<td>"+c.getManufacturer()+"</td>");
                    out.println("<td>"+c.getModel()+"</td>");
                    out.println("<td>"+c.getEngine()+"</td>");
                out.println("</tr>");
            }
            out.println("</tbody>");

        out.println("</table>");

        ProcessingServlet.matchingCars = new ArrayList<>();

        out.println("</body>");
        out.println("</html>");
    }

}