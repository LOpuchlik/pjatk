import java.util.ArrayList;
import java.util.List;

public class Validator extends Employee {
    private static final long serialVersionUID = 2L;

    private List<Task_Validator> taskValidators = new ArrayList<>(); // from association with attribute

    private int totalNumberOfFoundBugs;

    public Validator(String login, String firstName, String lastName, String email) {
        super(login,  firstName, lastName, email);
    }


    void addTaskValidator(Task_Validator newTaskValidator) {
        if (!taskValidators.contains(newTaskValidator)) {
            taskValidators.add(newTaskValidator);
        }
    }

    void removeTaskValidator(Task_Validator delTaskValidator) {
        if (this.taskValidators.contains(delTaskValidator)) {
            this.taskValidators.remove(delTaskValidator);
            delTaskValidator.removeAssociation();
        }
    }


    @Override
    public String toString() {
        String info = "";
        info += super.toString();
        for (Task_Validator tv: taskValidators) {
            info += tv.toString() + " \n";
        }
        return info;
    }


    // getters setters

    public List<Task_Validator> getTaskValidators() {
        return taskValidators;
    }

    public void setTaskValidators(List<Task_Validator> taskValidators) {
        this.taskValidators = taskValidators;
    }

    public int getTotalNumberOfFoundBugs() {
        return totalNumberOfFoundBugs;
    }

    public void setTotalNumberOfFoundBugs(int totalNumberOfFoundBugs) {
        this.totalNumberOfFoundBugs = totalNumberOfFoundBugs;
    }
}