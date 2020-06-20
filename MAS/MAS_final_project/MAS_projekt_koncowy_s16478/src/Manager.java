import java.util.ArrayList;
import java.util.List;

public class Manager  extends Employee{

    // do asocjacji 1-* Manager-Projekt
    public List<Project> projects = new ArrayList<>();

    static double maxBonus = 5000; // in PLN
    double grantedBonus; // optional
    double baseSalary;

    public Manager(String login, String password, String firstName, String lastName, String email) {
        super(login, password, firstName, lastName, email);

    }


    // do asocjacji 1-* Manager-Projekt
    public void addProject (Project newProject) {
        if(!projects.contains(newProject)) {
            projects.add(newProject);

            //add reverse connection
            newProject.setManager(this);
        }

    }

    @Override
    public String toString() {
        String info= "";
        info+= "Manager: "+ firstName + " " + lastName + " nadzoruje projekt: \n";
        for (Project p : projects)
            info += "         - " + p.getName();
        return info;
    }

    double countSalary (double baseSalary, double grantedBonus) {
        return baseSalary + grantedBonus*maxBonus;
    }
}