

package kwalifikowana;

public class Main {
    public static void main(String[] args) throws Exception {
        Programmer p1 = new Programmer("Gynvael", "Coldwind");  // company
        Programmer p2 = new Programmer("Elon", "Musk");

        Application a1 = new Application("BS1232", "Banking System");  // contractor
        Application a2 = new Application("GOF15", "Game Of Fifteen");
        Application a3 = new Application("POE2010", "Path Of Exiles");


        p1.setApplicationQualified(a1);
        // p1.setApplicationQualified(a1); nie mozna 2x dodac tego samego programisty do jednej i tej samej aplikacji
        p1.setApplicationQualified(a3);


        p2.setApplicationQualified(a1);
        p2.setApplicationQualified(a2);


        System.out.println("----------------------------------------------------");
        System.out.println("\t\t\t\t Programmer 1:");
        System.out.println("----------------------------------------------------");
        System.out.println(p1);
        System.out.println();
        System.out.println("----------------------------------------------------");
        System.out.println("\t\t\t\t Programmer 2:");
        System.out.println("----------------------------------------------------");
        System.out.println(p2);
        System.out.println();
        System.out.println();




        System.out.println("----------------------------------------------------");
        System.out.println("\t\t\t Searching map by key:");
        System.out.println("----------------------------------------------------");
        //System.out.println(p1.findApplicationQualified("GOF15")); // rzuca bledem, bo programista nie pracuje w tej aplikacji
        System.out.println(p1.findApplicationQualified("BS1232"));




 /*       System.out.println("------------------- Application ----------------");
        System.out.println(a1);
        System.out.println();
        System.out.println(a2);*/
    }


}



