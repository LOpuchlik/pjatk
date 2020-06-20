// task_validator
// set_exercise

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task_Validator implements Serializable {
    private static final long serialVersionUID = 2L;
    //todo add attributes --> they also go to the constructor
    private Validator validator;
    private Task task;

    // atrybuty walidacji
    String validationCode;
    LocalDate validationDate;
    List<String> bugs = new ArrayList<>();
    Map<String, String> bugDescription = new HashMap<>();
    String validatorsLogin;



    public Task_Validator(Validator validator, Task task) {
        super();
        this.validator = validator;
        this.task = task;

        // dodawanie odwrotnych asocjacji < - w odpowiednich klasach
        validator.addTaskValidator(this);
        task.addTaskValidator(this);
    }


    // usuwanie polaczenia miedzy exercise a set
    public void removeAssociation() {
        if (this.validator == null && this.task == null) {
            return;
        }
        if (this.validator != null) {
            this.validator.removeTaskValidator(this);
            this.validator = null;
        }
        if (this.task != null) {
            this.task.removeTaskValidator(this);
            this.task = null;
        }
    }
    @Override
    public String toString() {
        if (this.task == null || this.validator == null) {
            return "Association has been removed!\n" + "Current task state: " + this.task + "\nCurrent validator state: " + this.validator;
        }
        return "Validation - connecting validator with the task:\n" + this.validator.firstName
                + " " + this.validator.lastName
                + "\ntask name: " + this.task.getName();

    }

    public Validator getValidator() {
        return validator;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}