import java.time.LocalDate;

public class Test {
    public static void main(String[] args) throws Exception {


    Employee e1 = new Employee("Jan", "Kowalski", "Jan's address, Jan's phone number, Jan's mail", "senior developer");
    Employee e2 = new Employee("Adam", "Nowak", "Adam's address, Adam's phone number, Adam's mail", "junior developer");


    SickLeave s1 = SickLeave.createSickLeave(LocalDate.of(2020,7,5), LocalDate.of(2020,7,15),e1);
    SickLeave s2 = SickLeave.createSickLeave(LocalDate.of(2020,8,5), LocalDate.of(2020,8,15),e1);
    SickLeave s3 = SickLeave.createSickLeave(LocalDate.of(2020,9,5), LocalDate.of(2020,9,15),e1);
    SickLeave s4 = SickLeave.createSickLeave(LocalDate.of(2020,10,5), LocalDate.of(2020,10,15),e2);

    // impossible to create SickLeave object with the use of constructor because constructor is private
    //SickLeave s5 = new SickLeave(LocalDate.of(2020,11,5), LocalDate.of(2020,11,15), e2);


        System.out.println("-----------------------------------------------------------");
        System.out.println("\t\t\t\t\t\tTest - Employee 1");
        System.out.println("-----------------------------------------------------------");
        e1.addSickLeave(s1);
        e1.addSickLeave(s2);
        e1.addSickLeave(s3);
        System.out.println(e1);



        System.out.println("-----------------------------------------------------------");
        System.out.println("\t\t\t\t\t\tTest - Employee 2");
        System.out.println("-----------------------------------------------------------");
        e2.addSickLeave(s4);

        try {
            e2.addSickLeave(s2);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println(e2);



        System.out.println("-----------------------------------------------------------");
        System.out.println("\t\t\t\t\tTest - whole (Employee) does not exist");
        System.out.println("-----------------------------------------------------------");

        Employee e = null;
        SickLeave sl = null;

        try {
            sl = SickLeave.createSickLeave(LocalDate.of(2020, 7, 5), LocalDate.of(2021, 7, 5), e);
            e.addSickLeave(sl);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        System.out.println("Employee == " + e);



        System.out.println("-----------------------------------------------------------");
        System.out.println("\t\t\t\t\tTest - printing the extent of all employees");
        System.out.println("-----------------------------------------------------------");

        for  (Employee emp : Employee.getAllEmployees()) {
            System.out.println(emp);
            System.out.println();
        }
    }
}