public class Main {
    public static void main(String[] args) {
        Service s = new Service("Poland");
        String weatherJson = s.getWeather("Warsaw");
        Double rate1 = s.getRateFor("USD");
        Double rate2 = s.getNBPRate();

    /*    Gui g = new Gui(s);
        try {
            g.start(Gui.window);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}