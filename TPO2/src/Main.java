import javafx.application.Application;


public class Main {
    public static void main(String[] args) {
        Service s = new Service("Poland");
        String weatherJson = s.getWeather("Warsaw");
        Double rate1 = s.getRateFor("USD");

        Application.launch(Gui.class);
    }
}
