import java.util.ArrayList;
import java.util.List;

public class RegularEmployee extends Employee{
    private static final long serialVersionUID = 2L;

    // for RegularEmployee-Team association
    Team team;

    // for association between Regular Employee and Task
    List<Task> taskList = new ArrayList<>();

    List<String> skills;

    public RegularEmployee(String login,  String firstName, String lastName, String email) {
        super(login, firstName, lastName, email);
    }

    // for association between Regular Employee and Task
    void addTask(Task newTask) {
        if(!taskList.contains(newTask)){
            taskList.add(newTask);

            // add reverse connction
            newTask.addRegularEmployee(this);
        }
    }

    /*@Override
    public String toString() {
        String info = "";
        for (Task t : taskList)
            info += "         - "+t + "\n";

        return info;
    }*/
}