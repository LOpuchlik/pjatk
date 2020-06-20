import java.util.ArrayList;
import java.util.List;

public class Manager  extends Employee {
    private static final long serialVersionUID = 2L;

    // do asocjacji 1-* Manager-Projekt
    private List<Project> projects = new ArrayList<>();

    private static double maxBonus = 5000; // in PLN
    private double grantedBonus; // optional
    private double baseSalary;

    public Manager(String login, String firstName, String lastName, String email) {
        super(login, firstName, lastName, email);

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
        info+= "Manager: "+ getFirstName() + " " + getLastName() + " nadzoruje projekt: \n";
        for (Project p : projects)
            info += "         - " + p.getName();
        return info;
    }



    // getters setters
    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public static double getMaxBonus() {
        return maxBonus;
    }

    public static void setMaxBonus(double maxBonus) {
        Manager.maxBonus = maxBonus;
    }

    public double getGrantedBonus() {
        return grantedBonus;
    }

    public void setGrantedBonus(double grantedBonus) {
        this.grantedBonus = grantedBonus;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(double baseSalary) {
        this.baseSalary = baseSalary;
    }
}