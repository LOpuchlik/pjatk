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
public class ProcessingServlet extends HttpServlet {
    public static List<Car>matchingCars = new ArrayList<>();

    String requestType;
    String requestManufacturer;
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

        requestManufacturer = request.getParameter("brand");
        request.setAttribute("brand", requestManufacturer);

        requestModel = request.getParameter("model");
        request.setAttribute("model", requestModel);

        requestEngine = request.getParameter("engine");
        request.setAttribute("engine", requestEngine);




// building list with cars matching the search
        Car c = null;

        BufferedReader br = null;
        br = new BufferedReader(new FileReader("/Users/jagoodka/Dropbox/Jagoda/my_github/pjatk/TPO5/TPO5_OL_S16478/src/carsInfo.txt"));
        String line = "";

        while ((line = br.readLine()) != null) {
            String[] readContent = line.split(",");


            if (requestType.equals(readContent[0]) || requestManufacturer.equals(readContent[1]) || requestModel.equals(readContent[2]) || requestEngine.equals(readContent[4])) {
                c = new Car();
                c.setType(readContent[0]);
                c.setManufacturer(readContent[1]);
                c.setModel(readContent[2]);
                c.setEngine(readContent[4]);

                matchingCars.add(c);
            }
        }
// passing everything to another Servlet at /response
        RequestDispatcher rd = request.getRequestDispatcher("/response");
        rd.forward(request, response);

    }
}