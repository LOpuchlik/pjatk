package inheritances.overlapping;

public class Main {

    public static void main(String[] args) {
        Pracownik p = new Pracownik("Stefan","Burczymucha");
        p.isDeveloper("Java8");
        p.isTester("Mockito");
        System.out.println(p);

    }
}

