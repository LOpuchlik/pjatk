import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException{

        // tak musi robic servlet response
        for (Car c : prepareCarInfo())
            System.out.println(c);
    }

    static List<Car> prepareCarInfo() throws IOException {
        List<Car>matchingCars = new ArrayList<>();

        Request r = null;
        Car c = null;

        String fileName = "carsInfo.txt";
        StringBuilder sb = new StringBuilder();

        BufferedReader br = null;
        br = new BufferedReader(new FileReader(fileName));
        String line = "";

            while ((line = br.readLine()) != null) {
                String[] readContent = line.split(",");
                if (readContent[3].equals("2012") && readContent[4].equals("diesel")) {
                    c = new Car();
                    c.setType(readContent[0]);
                    c.setBrand(readContent[1]);
                    c.setModel(readContent[2]);
                    c.setYear(Integer.parseInt(readContent[3]));
                    c.setEngine(readContent[4]);
                    c.setConsumption(Double.parseDouble(readContent[5]));

                    matchingCars.add(c);
            }
        }
        return matchingCars;
    }
}
