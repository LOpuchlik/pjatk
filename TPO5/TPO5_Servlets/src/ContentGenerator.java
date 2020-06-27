import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/contentGenerator")
public class ContentGenerator extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    String prepareCarInfo() throws IOException{
        String file = "carsInfo.txt";
        String line = "";
        String txtSplitBy = ",";

        String type;
        String brand;
        String model;
        String yearOfProduction;
        String engineType;
        String fuelConsumption;

    List<String> result = null;


        BufferedReader br = null;

        br = new BufferedReader(new FileReader(file));

        while ((line = br.readLine()) != null) {
            String [] readContent = line.split(txtSplitBy);
            type = readContent[0];
            brand = readContent[1];
            model = readContent[2];
            yearOfProduction = readContent[3];
            engineType = readContent[4];
            fuelConsumption = readContent[5];
            result.add(type);
            result.add(brand);
            result.add(model);
            result.add(yearOfProduction);
            result.add(engineType);
            result.add(fuelConsumption);
            result.add("\n");
        }
    return result.toString();
    }

}
