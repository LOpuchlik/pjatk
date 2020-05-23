package kompozycja;


import java.util.*;

public class Project {

    private String projectName;
    private List<Task> tasks = new ArrayList<>();
    private static Set<Task> allTasks = new HashSet<>();
    private static Set<Project> allProjects = new HashSet<>();

    Project(String projectName) {
        this.projectName = projectName;
        //dodawanie projektu do ekstensji projektow
        allProjects.add(this);
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

    private List<Task> getTasks() {
        return tasks;
    }

/*    static void deleteProject(Project project) {
        Iterator itr = project.getTasks().iterator();
        while (itr.hasNext()) {
            Task t = (Task)itr.next();
            itr.remove();
        }
        allProjects.remove(project); // tego nie umiem usunac :(
    }*/

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