package zwykla;

public class Main {
    public static void main(String[] args) {

        // zwykła asocjacja - wiele do wiele Person - Exercise

        Person p1 = new Person("Lidia", "Opuchlik");
        Person p2 = new Person("Michał", "Opuchlik");
        Person p3 = new Person("Pola", "Opuchlik");


        Exercise e1 = new Exercise("Crunches", "easy", "abdomen");
        Exercise e2 = new Exercise("Running", "easy to hard", "whole body");
        Exercise e3 = new Exercise("Burpees", "moderate", "whole body");


        p1.addExercise(e2);
        p1.addExercise(e3);


        p2.addExercise(e1);
        p2.addExercise(e2);

        p3.addExercise(e3);

        System.out.println();
        System.out.println("Show info about people:");
        System.out.println();
        System.out.println(p1);
        System.out.println();
        System.out.println(p2);
        System.out.println();
        System.out.println(p3);

        System.out.println();
        System.out.println("Show info about exercises:");
        System.out.println();
        System.out.println(e1);
        System.out.println();
        System.out.println(e2);
        System.out.println();
        System.out.println(e3);


        // asocjacja z atrybutem


    }
}
