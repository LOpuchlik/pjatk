package kompozycja;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Project {

    private String projectName;
    private List<Task> tasks = new ArrayList<>();
    private static Set<Task> allTasks = new HashSet<>();
    private static Set<Project> allProjects = new HashSet<>();

    public Project (String projectName) {
        this.projectName = projectName;
        //dodawanie projestu do ekstensji projektow
        allProjects.add(this);
    }


    // dodanie czesc ze sprawdzeniem czy dana czesc nie jest juz polaczona z jakas caloscia
    public void addTask (Task task) throws Exception {
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


    public void deleteProject (Project project) {
        allProjects.remove(project);
        allTasks.removeAll(tasks);
    }

    @Override
    public String toString() {
        String info = "Project (whole): " + projectName + "\n";
        info += "Zadania:\n";
        for (Task task : tasks) {
            info += "     - " + task.getTaskName() + "\n";
        }
        return info;
    }
}
