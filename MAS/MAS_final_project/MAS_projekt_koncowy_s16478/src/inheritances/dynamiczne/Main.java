package inheritances.dynamiczne;

public class Main {
    public static void main(String[] args) {
        // create Junior
        Developer dev = new JuniorDeveloper("Stefan", "Burczymucha",8) {
        };
        System.out.println(dev);

        System.out.println();

        // create Senior based on Junior
        dev = new SeniorDeveloper(dev, 15);
        System.out.println(dev);
    }
}
