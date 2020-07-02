import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/request")
public class ServletForUserRequest extends HttpServlet {
    public static List<Car>matchingCars = new ArrayList<>();

    String requestType;
    String requestBrand;
    String requestModel;
    int requestYear;
    String requestEngine;
    String yearString;


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doGet(request, response);
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        requestType = request.getParameter("type");
        request.setAttribute("type", requestType);

        requestBrand = request.getParameter("brand");
        request.setAttribute("brand", requestBrand);

        requestModel = request.getParameter("model");
        request.setAttribute("model", requestModel);

        requestYear = Integer.parseInt(request.getParameter("year"));
        request.setAttribute("year", requestYear);

        requestEngine = request.getParameter("engine");
        request.setAttribute("engine", requestEngine);

        yearString = request.getParameter("year");


// building list with cars matching the search
        Car c = null;

        BufferedReader br = null;
        br = new BufferedReader(new FileReader("/Users/jagoodka/Desktop/IntelliJ/TPO5_Servlets/carsInfo.txt"));
        String line = "";

        while ((line = br.readLine()) != null) {
            String[] readContent = line.split(",");


            if (requestType.equals(readContent[0]) || requestBrand.equals(readContent[1]) || requestModel.equals(readContent[2]) || yearString.equals(readContent[3]) || requestEngine.equals(readContent[4])) {
                c = new Car();
                c.setType(readContent[0]);
                c.setBrand(readContent[1]);
                c.setModel(readContent[2]);
                c.setYear(Integer.parseInt(readContent[3]));
                c.setEngine(readContent[4]);

                matchingCars.add(c);
            }
        }
// passing everything to another Servlet at /response
        RequestDispatcher rd = request.getRequestDispatcher("/response");
        rd.forward(request, response);

    }
}
