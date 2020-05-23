package zAtrybutem;

public class Main {

    public static void main(String[] args) {
        Set s1 = new Set(3);
        Set s2 = new Set(4);
        Set s3 = new Set (6);

        Exercise e1 = new Exercise("Crunches", "easy", "abdomen");
        Exercise e2 = new Exercise("Running", "easy to hard", "whole body");
        Exercise e3 = new Exercise("Burpees", "moderate", "whole body");

        Set_Exercise se1 = new Set_Exercise(10);
        Set_Exercise se2 = new Set_Exercise(30);
        Set_Exercise se3 = new Set_Exercise(8);

        s1.addSetExercise(se1);
        s2.addSetExercise(se3);
        s3.addSetExercise(se2);

        e1.addSetExercise(se2);
        e2.addSetExercise(se3);
        e3.addSetExercise(se1);

        System.out.println("Sets:\n");
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);
        System.out.println();

        System.out.println("Exercises:\n");
        System.out.println(e1);
        System.out.println(e2);
        System.out.println(e3);
        System.out.println();
    }

}
