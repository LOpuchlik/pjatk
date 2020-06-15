package inheritances.abstrakcyjna;

public class Main {
    public static void main(String[] args) {

        Biegacz m = new Maratonista("Eliud", "Kipchoge", 42);
        Biegacz s = new Sprinter("Usain", "Bolt", 100);

        System.out.println(m);
        System.out.println();
        System.out.println(s);
    }
}
