import java.util.ArrayList;
import java.util.List;

public class RegularEmployee extends Employee{
    private static final long serialVersionUID = 2L;

    // for RegularEmployee-Team association
    private Team team;

    // for association between Regular Employee and Task
    private List<Task> taskList = new ArrayList<>();

    private transient List<String> skills;

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


    // getters setters


    @Override
    public Team getTeam() {
        return team;
    }

    @Override
    public void setTeam(Team team) {
        this.team = team;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }
}