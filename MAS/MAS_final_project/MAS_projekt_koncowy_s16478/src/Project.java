import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.time.temporal.ChronoUnit.DAYS;

public abstract class Project extends ObjectPlus {

    private static final long serialVersionUID = 2L;

    List<String> projectNames = new ArrayList<>();

    private List<Task> tasks = new ArrayList<>();
    private static Set<Task> allTasks = new HashSet<>();
    private static Set<Project> allProjects = new HashSet<>();

    private String name; // unikalna <-- sprawdzac czy kolekcja projects contains takie imie
    private String description;
    private LocalDate startDate;
    private LocalDate desiredEndDate;
    private LocalDate endDate;
    private transient int duration;
    private transient int delay;
    private int totalNumberOfTasks;
    private int numberOfFinishedTasks;
    private transient double progress; // wyliczalny jako numberOfFinishedTasks divided by total number of tasks


    public Project(String name, String description, LocalDate startDate, LocalDate desiredEndDate, int totalNumberOfTasks) {
        setName(name);
        this.description = description;
        this.startDate = startDate;
        this.desiredEndDate = desiredEndDate;
        this.totalNumberOfTasks = totalNumberOfTasks;
        getDuration();

        // dodanie nazwy projektu do listy nazw, zeby potem sprawdzac, czy juz jest projekt o takiej nazwie
        projectNames.add(name);
    }


    // dodanie czesc ze sprawdzeniem czy dana czesc nie jest juz polaczona z jakas caloscia
    void addTask(Task task) throws Exception {
        if (!tasks.contains(task)) {
            // sprawdzenie czy ta czesc njuz dodana do jakiejs calosci
            if(allTasks.contains(task)) {
                throw new Exception("Zadanie (czesc) jest juz polaczone z jakas caloscia!!! Nie mozesz dodac do tej calosci");
            }
            tasks.add(task);
            // dodanie do listy allTasks
            allTasks.add(task);
        }
    }

    public List<Task> getTasks() {
        return tasks;
    }



    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (!projectNames.contains(name))
            this.name = name;
        else
            System.out.println("You cannot give a name that has already been used");
    }

    public int getDuration() {
        this.duration = (int) DAYS.between(startDate, LocalDate.now());
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getNumberOfFinishedTasks() {
        return numberOfFinishedTasks;
    }

    public void setNumberOfFinishedTasks(int numberOfFinishedTasks) {
        this.numberOfFinishedTasks = numberOfFinishedTasks;
    }

    public double getProgress() {
        this.progress = (double) getNumberOfFinishedTasks()/tasks.size();
        return (double) Math.round(this.progress*10000)/100;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDesiredEndDate() {
        return desiredEndDate;
    }

    public void setDesiredEndDate(LocalDate desiredEndDate) {
        this.desiredEndDate = desiredEndDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getDelay() {
        this.delay = (int) DAYS.between(desiredEndDate, LocalDate.now());
        return Math.max(this.delay, 0);
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getTotalNumberOfTasks() {
        return totalNumberOfTasks;
    }

    public void setTotalNumberOfTasks() {
        this.totalNumberOfTasks = tasks.size();
    }

    @Override
    public String toString() {
        String msg = "";
        msg += "name:\t\t\t\t\t\t" + name +
                "\ndescription:\t\t\t\t" + description +
                "\nstart date:\t\t\t\t\t" + startDate +
                "\ndesired end date:\t\t\t" + desiredEndDate;

        if (endDate != null)
            msg += "\nreal end date:\t\t\t\t\t" + endDate;
        else
            msg += "\nreal end date:\t\t\t\t" + "project is not finished yet";

        msg += "\nduration:\t\t\t\t\t" + getDuration() + " day(s)" +
                "\ndelay:\t\t\t\t\t\t" + getDelay() + " day(s)" +
                "\ntotal number of tasks:\t\t" + tasks.size() +
                "\nnumber of finished tasks:\t" + numberOfFinishedTasks +
                "\nprogress:\t\t\t\t\t" + getProgress() + " %";
        msg += "\nTasks:\n";
        for (Task task : tasks) {
            msg += "      task " + task.getTaskNumber() + ": " + task.getName() + "\n";

        }
        return msg;

    }
}