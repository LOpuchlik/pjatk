import java.util.ArrayList;
import java.util.List;

public class Task extends  ObjectPlus {
    private static final long serialVersionUID = 2L;

    // for association between Regular Employee and Task
    List<RegularEmployee> regularEmployeeList = new ArrayList<>();

    static List<Task> tasks = new ArrayList<>();
    private List<Task_Validator> taskValidators = new ArrayList<>(); // from association with attribute

    private Project projectAsWhole;
    private int taskNumber; // unique -- numery zadaniom są automatycznie nadawane przez zwiekszanie countera
    private String name;
    private String description;
    private transient Priority priority;
    private transient Status status;

    // konstruktor jest prywatny
    public Task(Project projectAsWhole, String name, String description, Priority priority) throws Exception {
        super();
        this.projectAsWhole = projectAsWhole;
        setTaskNumber();
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.status = Status.waiting;
    }


    public static Task createTask (Project projectAsWhole, String name, String description, Priority priority) throws Exception {
        // sprawdzanie czy calosc istnieje
        if (projectAsWhole == null) {
            throw new Exception("The whole (project) does not exist, therefore you cannot create a part for it");
        }
        //stworzenie nowej czesci
        Task task = new Task (projectAsWhole, name, description, priority);

        // dodanie czesci do calosci
        projectAsWhole.addTask(task);

        return task;
    }

    public String getName() {
        return name;
    }

    public int getTaskNumber() {
        return taskNumber;
    }

    // do automatycznego ustawiania numeru zadania na koeljna liczbe
    public void setTaskNumber() {
        this.taskNumber = projectAsWhole.getTasks().size()+1;
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
        for (Task_Validator tv: taskValidators) {
            info += tv.toString() + " \n";
        }
        for (RegularEmployee re: regularEmployeeList) {
            info += "from RegularEmployee-Task associaton: " + re.toString() + ", task name: " + name + "\n";
        }
        return info;
    }

    // for association between Regular Employee and Task
    void addRegularEmployee (RegularEmployee newRegularEmployee) {
        if (!regularEmployeeList.contains(newRegularEmployee))
            regularEmployeeList.add(newRegularEmployee);

        // add reverse connection
        newRegularEmployee.addTask(this);
    }

}