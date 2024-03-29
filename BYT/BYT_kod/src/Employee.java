import java.time.LocalDate;
import java.util.*;

public class Employee {

    private String name;
    private String surname;
    private String contactDetails;
    private String position;

    private List<SickLeave> sickLeaves = new ArrayList<>();
    private static Set<SickLeave> allSickLeaves = new HashSet<>();
    private static Set<Employee> allEmployees = new HashSet<>();

    Employee(String name, String surname, String contactDetails, String position) {
        this.name = name;
        this.surname = surname;
        this.contactDetails = contactDetails;
        this.position = position;

        allEmployees.add(this);
    }


    void addSickLeave(SickLeave sickLeave) throws Exception {
        if (!sickLeaves.contains(sickLeave)) {
            // checking if this "part" is not already ascribed to some "whole"
            if(allSickLeaves.contains(sickLeave)) {
                throw new Exception("This sick leave is already ascribed to another employee");
            }
            sickLeaves.add(sickLeave);
            allSickLeaves.add(sickLeave);
        }
    }


    @Override
    public String toString() {
        String info = "Employee (whole): " + name + " " + surname + ", " + position + ", " + contactDetails  + "\n";
        info += "SickLeave(s):\n";
        for (SickLeave sickLeaves : sickLeaves) {
            info += "      ....................\n";
            info += "       start: " + sickLeaves.getStart()+ "\n";
            info += "       finish: " + sickLeaves.getFinish() + "\n";
        }
        return info;
    }

    static Set<Employee> getAllEmployees() {
        return allEmployees;
    }

    /*
    public void fillWorkingTimeForm (LocalDate date, float numberOfHours){
        // code - cannot be implemented because the part of scheme that I've chosen to implements does not allow it due to lack of necessary variables and classes etc.
    }
    public void requestLeave (LocalDate dateFrom, LocalDate dateTo, String type){
        // code - cannot be implemented because the part of scheme that I've chosen to implements does not allow it due to lack of necessary variables and classes etc.
    }
    */

}