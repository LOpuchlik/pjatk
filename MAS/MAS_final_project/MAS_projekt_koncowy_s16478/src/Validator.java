import java.util.ArrayList;
import java.util.List;

public class Validator extends Employee {

    private List<Task_Validator> taskValidators = new ArrayList<>(); // from association with attribute

    int totalNumberOfFoundBugs;

    public Validator(String login, String password, String firstName, String lastName, String email, int totalNumberOfFoundBugs) {
        super(login, password, firstName, lastName, email);
        this.totalNumberOfFoundBugs = totalNumberOfFoundBugs;
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
}
