package zAtrybutem;

public class Main {

    public static void main(String[] args) {
        Exercise e1 = new Exercise("Crunches", "easy");
        Exercise e2 = new Exercise("100 m running intervals", "hard");
        Exercise e3 = new Exercise("Burpees", "moderate");

        Set s1 = new Set(3);
        Set s2 = new Set(4);
        Set s3 = new Set (6);

        Set_Exercise se1 = new Set_Exercise(10, e3, s2);  // Crunches - 4 sets
        Set_Exercise se2 = new Set_Exercise(30, e1, s1); // Burpees - 3 sets
        Set_Exercise se3 = new Set_Exercise(5, e2, s3); // Running - 6 sets

        System.out.println("----------------------------------------------------");
        System.out.println("\t\t\t\t Sets_Exercises:");
        System.out.println("----------------------------------------------------");
        System.out.println(se1);
        System.out.println(se2);
        System.out.println(se3);
        System.out.println();

        System.out.println("----------------------------------------------------");
        System.out.println("\t\t\t\t\tExercises:");
        System.out.println("----------------------------------------------------");
        System.out.println(e1);
        System.out.println(e2);
        System.out.println(e3);
        System.out.println();

        System.out.println("----------------------------------------------------");
        System.out.println("\t\t\t\t\t Sets:");
        System.out.println("----------------------------------------------------");
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);
        System.out.println();


        System.out.println("----------------------------------------------------");
        System.out.println("   Removing association between Set and Exercise");
        System.out.println("----------------------------------------------------");
        se1.removeAssociation();
        System.out.println(se1);


    }

}
