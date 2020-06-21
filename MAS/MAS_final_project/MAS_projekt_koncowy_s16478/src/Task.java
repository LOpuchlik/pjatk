import java.util.ArrayList;
import java.util.List;

public class Task extends  ObjectPlus {
    private static final long serialVersionUID = 2L;

    // for association between Regular Employee and Task
    private List<Employee.RegularEmployee> regularEmployeeList = new ArrayList<>();

    //for association between Team and Task
    private Team team;

    private static List<Task> tasks = new ArrayList<>();
    private List<Task_Validator> taskValidators = new ArrayList<>(); // from association with attribute

    private Project projectAsWhole;
    private int taskNumber; // numbers of Tasks are incremented automatically
    private String name;
    private String description;
    private transient Priority priority;
    private transient Status status;

    // private constructor
    private Task(Project projectAsWhole, String name, String description, Priority priority) throws Exception {
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
        for (Employee.RegularEmployee re: regularEmployeeList) {
            info += "from RegularEmployee-Task associaton: " + re.toString() + ", task name: " + name + "\n";
        }

        return info;
    }

    // for association between Regular Employee and Task
    void addRegularEmployee (Employee.RegularEmployee newRegularEmployee) {
        if (!regularEmployeeList.contains(newRegularEmployee))
            regularEmployeeList.add(newRegularEmployee);

        // add reverse connection
        newRegularEmployee.addTask(this);
    }

    // for association between Team and Task
    void setTeamForTask(Team newTeam) {
        if (team != null)
            team = null;
        team = newTeam;

        // add reverse connection
        newTeam.addTaskForTeam(this);
    }



    // getters setters

    public List<Employee.RegularEmployee> getRegularEmployeeList() {
        return regularEmployeeList;
    }

    public void setRegularEmployeeList(List<Employee.RegularEmployee> regularEmployeeList) {
        this.regularEmployeeList = regularEmployeeList;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public static List<Task> getTasks() {
        return tasks;
    }

    public static void setTasks(List<Task> tasks) {
        Task.tasks = tasks;
    }

    public List<Task_Validator> getTaskValidators() {
        return taskValidators;
    }

    public void setTaskValidators(List<Task_Validator> taskValidators) {
        this.taskValidators = taskValidators;
    }

    public Project getProjectAsWhole() {
        return projectAsWhole;
    }

    public void setProjectAsWhole(Project projectAsWhole) {
        this.projectAsWhole = projectAsWhole;
    }

    public int getTaskNumber() {
        return taskNumber;
    }

    // for automatic setting the task number for following number
    public void setTaskNumber() {
        this.taskNumber = projectAsWhole.getTasks().size()+1;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


}