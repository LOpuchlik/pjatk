import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet("/index")
public class RequestServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader br = new BufferedReader(new FileReader("/Users/jagoodka/Desktop/TPO5_OL_S16478/src/carsInfo.txt"));
        String line;

        List<Car> cars = new ArrayList<>();


        while ((line = br.readLine()) != null) {
            String[] read = line.split(",");
            cars.add(new Car(read[0], read[1], read[2], read[4]));
        }

        List<String> types = cars.stream().map(Car::getType).distinct().collect(Collectors.toList());
        Collections.sort(types, String.CASE_INSENSITIVE_ORDER);

        List<String> manufacturers = cars.stream().map(Car::getManufacturer).distinct().collect(Collectors.toList());
        Collections.sort(manufacturers, String.CASE_INSENSITIVE_ORDER);

        List<String> models = cars.stream().map(Car::getModel).distinct().collect(Collectors.toList());
        Collections.sort(models, String.CASE_INSENSITIVE_ORDER);


        List<String> engines = cars.stream().map(Car::getEngine).distinct().collect(Collectors.toList());
        Collections.sort(engines, String.CASE_INSENSITIVE_ORDER);


        request.setAttribute("types", types);
        request.setAttribute("manufacturers", manufacturers);
        request.setAttribute("models", models);
        request.setAttribute("engines", engines);
        request.setAttribute("cars", cars);

        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);


    }


}
