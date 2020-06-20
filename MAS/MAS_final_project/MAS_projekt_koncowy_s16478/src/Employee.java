
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Employee extends ObjectPlus {
    private static final long serialVersionUID = 2L;
    private Team team;

    private Leader l = null;
    private RegularEmployee re = null;


    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String address;
    private String phoneNo;
    private String email;
    private transient int generalWorkExperience; // in months
    private List<String> formalEducation;
    private double numberOfWorkHoursInAMonth;
    private double workRate;
    private transient double salary;
    private int isLeader;


    public Employee(String login, String firstName, String lastName, String email) {
        super();
        this.login = login;
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

    class RegularEmployee implements Serializable {
        transient List<String> skills;

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

    class Leader implements Serializable {
        public double maxBonus;
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



    // for displaying in table view
    public String getFullName () {
        return lastName + " " + firstName;
    }

    // for RegularEmployee-Team association
    void setTeam (Team newTeam){
        team = newTeam;

        //add reverse connction
        newTeam.addEmployee(this);
    }




    // getters setters


    public Team getTeam() {
        return team;
    }

    public Leader getL() {
        return l;
    }

    public void setL(Leader l) {
        this.l = l;
    }

    public RegularEmployee getRe() {
        return re;
    }

    public void setRe(RegularEmployee re) {
        this.re = re;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGeneralWorkExperience() {
        return generalWorkExperience;
    }

    public void setGeneralWorkExperience(int generalWorkExperience) {
        this.generalWorkExperience = generalWorkExperience;
    }

    public List<String> getFormalEducation() {
        return formalEducation;
    }

    public void setFormalEducation(List<String> formalEducation) {
        this.formalEducation = formalEducation;
    }

    public double getNumberOfWorkHoursInAMonth() {
        return numberOfWorkHoursInAMonth;
    }

    public void setNumberOfWorkHoursInAMonth(double numberOfWorkHoursInAMonth) {
        this.numberOfWorkHoursInAMonth = numberOfWorkHoursInAMonth;
    }

    public double getWorkRate() {
        return workRate;
    }

    public void setWorkRate(double workRate) {
        this.workRate = workRate;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getIsLeader() {
        return isLeader;
    }

    public void setIsLeader(int isLeader) {
        this.isLeader = isLeader;
    }
}