import java.time.LocalDate;
import java.util.List;

public class Employee extends ObjectPlus {

    Leader l = null;
    RegularEmployee re = null;


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


    void isRegularEmployee(List<String> skills){
        re = new Employee.RegularEmployee(skills);
    }


    void isLeader(double grantedLeaderBonus){
        l = new Employee.Leader(grantedLeaderBonus);

    }

    @Override
    public String toString() {
        String info="";
        if (re == null & l == null){
            info+= firstName + " " + lastName;
            info += " (" + this.getClass() + ")";
        } else if (l == null){
            info+= firstName + " " + lastName;
            info += " (" + this.getClass() + ")";
            info+="\n"+re;
        } else if (re == null) {
            info+= firstName + " " + lastName;
            info += " (" + this.getClass() + ")";
            info += "\n" + l;
            }else {
            info+= firstName + " " + lastName;
            info += " (" + this.getClass() + ")";
            info+="\n" + l;
            info+="\n" + re;
        }
        return info;
    }

    class RegularEmployee{
        List<String> skills;

        public RegularEmployee(List<String> skills) {
            this.skills = skills;
        }


        @Override
        public String toString() {
            String info = "";
            info += "                  as regular employee, skills: ";
            for (String skill : skills)
                info += skill + " ";
            return info;
        }
    }

    class Leader {
        double maxLeaderBonus;
        double grantedLeaderBonus;


        public Leader(double grantedLeaderBonus) {
            this.maxLeaderBonus = 0.1;
            this.grantedLeaderBonus = grantedLeaderBonus;
        }

        @Override
        public String toString() {
            return "                  as leader" +
                    ", max leader bonus: " + maxLeaderBonus;
        }
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

    // for displaying in table view
    public String getFullName () {
        return lastName + " " + firstName;
    }
}
