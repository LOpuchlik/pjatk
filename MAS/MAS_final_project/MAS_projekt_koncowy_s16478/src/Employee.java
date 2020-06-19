import java.time.LocalDate;
import java.util.List;

public class Employee extends ObjectPlus {


    String login;
    String password;
    String firstName;
    String lastName;
    LocalDate dateOfBirth;
    String address;
    String phoneNo;
    String email;
    transient int generalWorkExperience; // in months
    List<String> formalEducation;
    double numberOfWorkHoursInAMonth;
    double workRate;
    transient double salary;
    int isLeader;


    public Employee(String login, String password, String firstName, String lastName, String email) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
    @Override
    public String toString() {
        return "" + firstName +
                " " + lastName +
                " (" + this.getClass() + ")";
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName () {
        return firstName + " " + lastName;
    }
}
