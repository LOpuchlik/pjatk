package kwalifikowana;

public class Main {

    public static void main(String[] args) throws Exception {

        BoardingPass bp1 = new BoardingPass("NY2342", "Warsaw", "New York");
        BoardingPass bp2 = new BoardingPass("SF2342", "London", "San Francisco");

        Person p1 = new Person("Andrzej", "Nowak");
        Person p2 = new Person("Anna", "Andrzejewska");

        p1.addBoardingPassQualified(bp1);
        p2.addBoardingPassQualified(bp2);


        System.out.println("Dane dla p1:");
        System.out.println(p1);
        System.out.println();
        System.out.println("Dane dla p2:");
        System.out.println(p2);
        System.out.println();


        System.out.println(
                "------------------------------------------------------------\nTestowanie metody findBoardingPersonQualified: "
        );
        BoardingPass boardingPass = p1.findBoardingPassQualified("NY2342"); // Dla Andrzej Nowak
        System.out.println(boardingPass);

    }
}
