import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public abstract class Project extends ObjectPlus {

    //Set<Project> projectsExtent = new HashSet<>();
    List<String> projectNames = new ArrayList<>();

    String name; // unikalna <-- sprawdzac czy kolekcja projects contains takie imie
    String description; // literal
    LocalDate startDate;
    LocalDate desiredEndDate;
    LocalDate endDate; // optional - gdy projekt sie jeszcze nie skończył to będzie null
    transient int duration; // moze lepiej byloby Date i wtedy odejmowac od siebie daty i rzeczywista dlugosc projektu zrobic???
    transient int delay; // wyliczalne jako endDate - desired Enddate
    int totalNumberOfTasks;
    int numberOfFinishedTasks; // changes dynamically
    transient double progress; // wyliczalny jako numberOfFinishedTasks divided by total number of tasks


    public Project(String name, String description, LocalDate startDate, LocalDate desiredEndDate, int totalNumberOfTasks) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.desiredEndDate = desiredEndDate;
        this.totalNumberOfTasks = totalNumberOfTasks;
        getDuration();
    }
    

    // getters and setters
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
        this.progress = (double) getNumberOfFinishedTasks()/getTotalNumberOfTasks();
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

    public void setTotalNumberOfTasks(int totalNumberOfTasks) {
        this.totalNumberOfTasks = totalNumberOfTasks;
    }

}
