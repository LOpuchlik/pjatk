import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task_Validator implements Serializable {
    private static final long serialVersionUID = 2L;

    private Validator validator;
    private Task task;

    // atributes for Validation class
    private String validationCode;
    private LocalDate validationDate;
    private List<String> bugs = new ArrayList<>();
    private Map<String, String> bugDescription = new HashMap<>();
    private String validatorsLogin;



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
        return "Validation - connecting validator with the task:\n" + this.validator.getFirstName()
                + " " + this.validator.getLastName()
                + "\ntask name: " + this.task.getName();

    }

    // getters and setters

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

    public String getValidationCode() {
        return validationCode;
    }

    public void setValidationCode(String validationCode) {
        this.validationCode = validationCode;
    }

    public LocalDate getValidationDate() {
        return validationDate;
    }

    public void setValidationDate(LocalDate validationDate) {
        this.validationDate = validationDate;
    }

    public List<String> getBugs() {
        return bugs;
    }

    public void setBugs(List<String> bugs) {
        this.bugs = bugs;
    }

    public Map<String, String> getBugDescription() {
        return bugDescription;
    }

    public void setBugDescription(Map<String, String> bugDescription) {
        this.bugDescription = bugDescription;
    }

    public String getValidatorsLogin() {
        return validatorsLogin;
    }

    public void setValidatorsLogin(String validatorsLogin) {
        this.validatorsLogin = validatorsLogin;
    }
}