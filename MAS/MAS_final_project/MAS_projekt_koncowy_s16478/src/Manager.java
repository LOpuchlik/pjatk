public class Manager  extends Employee{

    static double maxBonus = 5000; // in PLN
    double grantedBonus; // optional
    double baseSalary;

    public Manager(String login, String password, String firstName, String lastName, String email) {
        super(login, password, firstName, lastName, email);
    }

    double countSalary (double baseSalary, double grantedBonus) {
        return baseSalary + grantedBonus*maxBonus;
    }



}
